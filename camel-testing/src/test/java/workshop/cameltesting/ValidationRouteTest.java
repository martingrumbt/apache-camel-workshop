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

        // TODO Expect exactly one valid application

        // TODO Send one valid and one invalid application

        // TODO Assert that the expectations were satisfied
    }
}