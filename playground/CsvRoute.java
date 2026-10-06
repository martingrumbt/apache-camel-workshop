import org.apache.camel.builder.RouteBuilder;

public class CsvRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/inbox")
        // TODO
    }
}