package workshop.cameltesting;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DebuggingRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:debug-applications")
                .routeId("debug-applications")
                .log("exchange: ${exchangeId}")
                .log("Processing application: ${body.id}")
                .setHeader("applicationAmount", simple("${body.amount}"))
                .process(exchange -> {
                    // Breakpoint hier setzen
                    var exchangeId = exchange.getExchangeId();
                    var routeId = exchange.getFromRouteId();
                    var body = exchange.getMessage().getBody();
                    var headers = exchange.getMessage().getHeaders();
                    var exception = exchange.getException();
                })
                .filter(simple("${header.applicationAmount} > 50000"))
                .to("mock:large-applications");
    }
}