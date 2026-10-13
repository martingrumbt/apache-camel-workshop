//DEPS org.apache.camel:camel-jacksonxml:4.22.1

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        JacksonXMLDataFormat xmlFormat = new JacksonXMLDataFormat();
        xmlFormat.setUnmarshalType(LoanApplication.class);

        from("file:data/inbox")
                .unmarshal(xmlFormat)
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:8081/applications")
                .log("Body: ${body}")
                .log("Body type: ${body.class}")
                .to("log:csv");
    }
}
