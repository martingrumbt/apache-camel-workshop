//DEPS org.apache.camel:camel-jms:4.22.1
//DEPS org.apache.activemq:artemis-jakarta-client-all:2.44.0
//DEPS org.apache.camel:camel-csv:4.22.1
//DEPS org.apache.camel:camel-jackson:4.22.1

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public class JmsProducerRoute extends RouteBuilder {

    @Override
    public void configure() {

        CsvDataFormat csv = new CsvDataFormat();
        csv.setUseMaps(true);
        csv.setSkipHeaderRecord(true);

        from("file:data/inbox")
                .routeId("loan-application-producer")
                .unmarshal(csv)
                .split(body())
                .bean(LoanApplicationMapper.class, "map")
                .marshal().json(JsonLibrary.Jackson)

                .to("jms:queue:loan.applications")

                .end()
                .log("Finished publishing applications");
    }
}