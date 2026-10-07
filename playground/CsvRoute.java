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
                        .setHeader("provider", constant("bank-a"))
                    .otherwise()
                        .setHeader("provider", constant("bank-b"))
                .end()
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .toD("http://localhost:8081/providers/${header.provider}/applications")
                .log("Body: ${body}")
                .log("Body type: ${body.class}")
                .to("log:csv");
    }
}
