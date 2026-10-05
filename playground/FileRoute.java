import org.apache.camel.builder.RouteBuilder;

public class FileRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
                .log("Body: ${body}")
                .log("File: ${header.CamelFileName}")
                .to("file:data/outbox");
    }
}