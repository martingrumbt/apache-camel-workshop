//DEPS org.apache.camel:camel-csv:4.22.1
//DEPS org.apache.camel:camel-http:4.22.1

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;

public class CsvMulticastRoute extends RouteBuilder {

    @Override
    public void configure() {
        CsvDataFormat csv = new CsvDataFormat();
        csv.setUseMaps(true);
        csv.setHeader(new String[]{"id", "amount", "term"});
        csv.setSkipHeaderRecord(true);

        errorHandler(
                deadLetterChannel("file:data/dead-letter")
                        .maximumRedeliveries(3)
                        .redeliveryDelay(1000)
        );

        from("file:data/inbox")
                .unmarshal(csv)
                .split(body())
                    .bean(LoanApplicationMapper.class, "map")
                    .to("direct:providers")
                .end()
                .log("Finished processing file");

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
                .onException(HttpOperationFailedException.class)
                    .onWhen(simple("${exception.statusCode} == 503"))
                    .maximumRedeliveries(3)
                    .redeliveryDelay(1000)
                    .handled(true)
                    .setBody(constant(
                        "{\"provider\":\"Bank B\",\"status\":\"UNAVAILABLE\"}"
                    ))
                .end()
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/providers/bank-b/applications")
                .convertBodyTo(String.class)
                .log("Bank B: ${body}");
    }
}
