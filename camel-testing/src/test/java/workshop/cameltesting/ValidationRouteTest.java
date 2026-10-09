package workshop.cameltesting;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@CamelSpringBootTest
@SpringBootTest
class ValidationRouteTest {

    @Autowired
    ProducerTemplate template;

    @EndpointInject("mock:valid")
    MockEndpoint valid;

    @Test
    void shouldOnlyForwardValidApplications() throws Exception {

        var validApplication = new LoanApplication(
                "A-1001",
                new BigDecimal("25000"),
                48
        );

        var invalidApplication = new LoanApplication(
                "A-1002",
                new BigDecimal("-100"),
                48
        );

        valid.expectedBodiesReceived(validApplication);

        template.sendBody("direct:applications", validApplication);
        template.sendBody("direct:applications", invalidApplication);

        valid.assertIsSatisfied();
    }
}