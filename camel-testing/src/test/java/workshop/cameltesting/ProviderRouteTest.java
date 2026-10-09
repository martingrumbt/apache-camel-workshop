package workshop.cameltesting;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@CamelSpringBootTest
@SpringBootTest
@UseAdviceWith
class ProviderRouteTest {

    @Autowired
    CamelContext context;

    @Autowired
    ProducerTemplate template;

    @Test
    void shouldSendApplicationToProvider() throws Exception {
        AdviceWith.adviceWith(context, "provider-route", advice ->
                advice.weaveByToUri("http://localhost:8081/*")
                        .replace()
                        .to("mock:provider")
        );

        context.start();

        MockEndpoint provider = context.getEndpoint(
                "mock:provider", MockEndpoint.class
        );

        provider.expectedMessageCount(1);

        var application = new LoanApplication(
                "A-1001",
                new BigDecimal("25000"),
                48
        );

        template.sendBody("direct:provider", application);

        provider.assertIsSatisfied();
    }
}