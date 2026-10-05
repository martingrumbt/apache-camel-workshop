import org.apache.camel.builder.RouteBuilder;

public class TimerRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("timer:hello?period=1000")
                .setBody(constant("Hello Camel"))
                .setHeader("source", constant("workshop"))
                .log("${header.source}: ${body}")
                .to("log:hello");
    }
}