import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
                .unmarshal().json(JsonLibrary.Jackson, LoanApplication.class)
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/applications")
                .log("Body: ${body}")
                .log("Body type: ${body.class}")
                .to("log:csv");
    }
}
