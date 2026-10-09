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
        // TODO Replace the HTTP endpoint with a MockEndpoint

        context.start();

        MockEndpoint provider = context.getEndpoint(
                "mock:provider", MockEndpoint.class
        );

        // TODO Expect exactly one application

        var application = new LoanApplication(
                "A-1001",
                new BigDecimal("25000"),
                48
        );

        template.sendBody("direct:provider", application);

        // TODO Assert expectations
    }
}