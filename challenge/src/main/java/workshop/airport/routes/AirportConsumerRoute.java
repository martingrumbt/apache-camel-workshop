package workshop.airport.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import workshop.airport.model.Shipment;

@Component
@Profile("consumer")
public class AirportConsumerRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("jms:queue:airport.dispatch")
            .routeId("airport-dispatch-consumer")
            .unmarshal().json(JsonLibrary.Jackson, Shipment.class)
            .validate(simple("${body.id} != null && ${body.destination} != null && ${body.route} != null"))
            .setHeader("shipmentId", simple("${body.id}"))
            .setHeader("destination", simple("${body.destination}"))
            .setHeader("transportRoute", simple("${body.route}"))
            .marshal().json(JsonLibrary.Jackson)
            .choice()
                .when(header("transportRoute").isEqualTo("VIA_HUB"))
                    .to("direct:airport-hub")
                .otherwise()
                    .to("direct:airport-arrival")
            .end();

        from("direct:airport-hub")
            .routeId("airport-transfer-via-muc")
            .toD("file:data/transfers/MUC?fileName=${header.shipmentId}.json")
            .log("Transfer via MUC: ${header.shipmentId}")
            .to("direct:airport-arrival");

        from("direct:airport-arrival")
            .routeId("airport-final-arrival")
            .toD("file:data/arrivals/${header.destination}?fileName=${header.shipmentId}.json")
            .log("Arrived in ${header.destination}: ${header.shipmentId}");
    }
}
