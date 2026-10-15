package workshop.airport.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("consumer")
public class AirportConsumerRoute extends RouteBuilder {
    @Override
    public void configure() {
        // TODO: Consume JSON shipments from airport.dispatch.
        // TODO: Send VIA_HUB shipments through MUC.
        // TODO: Write one arrival file to BER, MAD or LIS.
    }
}
