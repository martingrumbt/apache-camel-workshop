package workshop.airport.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("producer")
public class AirportProducerRoute extends RouteBuilder {
    @Override
    public void configure() {
        // TODO: Read JSON manifests from challenge/data/inbox.
        // TODO: Split each manifest into individual shipments.
        // TODO: Ignore duplicate shipment IDs during one application run.
        // TODO: Write an independent audit copy and publish to airport.dispatch.
    }
}
