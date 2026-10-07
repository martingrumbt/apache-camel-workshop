import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CsvMulticastRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
                .unmarshal().csv()
                .bean(LoanApplicationMapper.class, "map")
                .multicast() // TODO add call parameter
                    .to("direct:bank-a")
                    .to("direct:bank-b")
                .end()
                .log("Offers: ${body}");

        from("direct:bank-a")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/providers/bank-a/applications")
                .log("Bank A: ${body}");

        from("direct:bank-b")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/providers/bank-b/applications")
                .log("Bank B: ${body}");
    }
}
