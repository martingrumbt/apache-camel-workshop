package workshop.airport.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import workshop.airport.model.FlightManifest;
import workshop.airport.model.Shipment;

@Component
@Profile("producer")
public class AirportProducerRoute extends RouteBuilder {
    @Override
    public void configure() {
        // The default file move to .camel uses the original filename.
        // Here we use timestamped folders to support repeatable classroom runs.
        from("file:data/inbox?move=.camel/${date:now:yyyyMMddHHmmssSSS}/${file:onlyname}&moveFailed=.error/${file:onlyname}")
            .routeId("airport-manifests")
            .unmarshal().json(JsonLibrary.Jackson, FlightManifest.class)
            .process(exchange -> {
                FlightManifest manifest = exchange.getMessage().getBody(FlightManifest.class);
                if (manifest.getItems() == null || manifest.getFlightId() == null) {
                    throw new IllegalArgumentException("Missing flightId or items");
                }
                for (Shipment shipment : manifest.getItems()) {
                    shipment.setFlightId(manifest.getFlightId());
                }
            })
            .split(simple("${body.items}"))
                .validate(simple("${body.id} != null && ${body.destination} != null && ${body.route} != null"))
                .idempotentConsumer(simple("${body.id}"), MemoryIdempotentRepository.memoryIdempotentRepository(2000))
                    .setHeader("shipmentId", simple("${body.id}"))
                    .marshal().json(JsonLibrary.Jackson)
                    .convertBodyTo(String.class)
                    .wireTap("direct:airport-audit")
                    .to("jms:queue:airport.dispatch")
                    .log(LoggingLevel.INFO, "Dispatched ${header.shipmentId}")
                .end()
            .end();

        // Wire Tap gets a separate exchange; the body is already JSON.
        from("direct:airport-audit")
            .routeId("airport-audit")
            .toD("file:data/audit?fileName=${header.shipmentId}.json")
            .log("Audit ${header.shipmentId}");
    }
}
