import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
                .unmarshal().csv()
                .bean(LoanApplicationMapper.class, "map")
                // TODO Serialize the LoanApplication as JSON
                // TODO Send it via HTTP POST to the provider API
                .log("Body: ${body}")
                .log("Body type: ${body.class}")
                .to("log:csv");
    }
}