//DEPS org.apache.camel:camel-jms:4.22.1
//DEPS org.apache.activemq:artemis-jakarta-client-all:2.44.0
//DEPS org.apache.camel:camel-jackson:4.22.1

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class JmsConsumerRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("jms:queue:loan.applications")
                .routeId("loan-application-consumer")
                .unmarshal().json(JsonLibrary.Jackson, LoanApplication.class)
                .log("Received from JMS: ${body}")
                .to("direct:providers");

        from("direct:providers")
                .multicast(new ProviderResponseAggregationStrategy())
                .to("direct:bank-a")
                .to("direct:bank-b")
                .end()
                .log("Offers: ${body}");

        from("direct:bank-a")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/providers/bank-a/applications")
                .convertBodyTo(String.class)
                .log("Bank A: ${body}");

        from("direct:bank-b")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/providers/bank-b/applications")
                .convertBodyTo(String.class)
                .log("Bank B: ${body}");
    }
}