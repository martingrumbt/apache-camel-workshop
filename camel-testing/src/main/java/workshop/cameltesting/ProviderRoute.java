package workshop.cameltesting;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class ProviderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:provider")
                .routeId("provider-route")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/providers/bank-a/applications");
    }
}