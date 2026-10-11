package workshop.cameltesting;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DebuggingRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:debug-applications")
                .routeId("debug-applications")
                .log("Processing application: ${body.id}")
                .setHeader("applicationAmount", simple("${body.amount}"))
                .filter(simple("${header.applicationAmount} > 50000"))
                .to("mock:large-applications");
    }
}