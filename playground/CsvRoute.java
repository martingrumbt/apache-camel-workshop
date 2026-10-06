import org.apache.camel.builder.RouteBuilder;

public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
                .unmarshal().csv()
                .bean(LoanApplicationMapper.class, "map")
                .log("Body: ${body}")
                .log("Body type: ${body.class}")
                .to("log:csv");
    }
}