import org.apache.camel.builder.RouteBuilder;

public class TimerRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("timer:hello?period=1000")
            .to("log:hello");
    }
}