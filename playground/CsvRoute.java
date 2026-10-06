import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
                .unmarshal().csv()
                .bean(LoanApplicationMapper.class, "map")
                .choice()
                    .when(simple("${body.amount} <= 50000"))
                        .marshal().json(JsonLibrary.Jackson)
                        .setHeader("Content-Type", constant("application/json"))
                        .setHeader("CamelHttpMethod", constant("POST"))
                        .to("http://localhost:8081/providers/bank-a/applications")
                    .otherwise()
                        .marshal().json(JsonLibrary.Jackson)
                        .setHeader("Content-Type", constant("application/json"))
                        .setHeader("CamelHttpMethod", constant("POST"))
                        .to("http://localhost:8081/providers/bank-b/applications")
                .end()
                .log("Body: ${body}")
                .log("Body type: ${body.class}")
                .to("log:csv");
    }
}
