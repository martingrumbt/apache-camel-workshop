package workshop.cameltesting;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ValidationRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:applications")
                .routeId("validate-applications")
                .filter(simple("${body.amount} > 0"))
                .to("mock:valid");
    }
}