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
class DebuggingRouteTest {

    @Autowired
    ProducerTemplate template;

    @EndpointInject("mock:large-applications")
    MockEndpoint largeApplications;

    @Test
    void shouldForwardLargeApplications() throws Exception {

        var application = new LoanApplication(
                "A-1001",
                new BigDecimal("150000"),
                120
        );

        largeApplications.expectedMessageCount(1);

        template.sendBody("direct:debug-applications", application);

        largeApplications.assertIsSatisfied();
    }
}