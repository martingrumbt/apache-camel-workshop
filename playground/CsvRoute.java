import org.apache.camel.builder.RouteBuilder;

public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
                .unmarshal().csv()
                // TODO Map the CSV data to a LoanApplication using LoanApplicationMapper
                .log("Body: ${body}")
                .log("Body type: ${body.class}")
                .to("log:csv");
    }
}