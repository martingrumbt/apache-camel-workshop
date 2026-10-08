package workshop.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class ProviderController {

    private static final Logger LOG =
            LoggerFactory.getLogger(ProviderController.class);

    @PostMapping("/providers/bank-a/applications")
    public Map<String, String> bankA(
            @RequestBody LoanApplication application
    ) {
        LOG.info("Bank A received application: {}", application.id());

        return Map.of(
                "provider", "Bank A",
                "status", "ACCEPTED"
        );
    }

    @PostMapping("/providers/bank-b/applications")
    public Map<String, String> bankB(
            @RequestBody LoanApplication application
    ) {
        LOG.info("Bank B received application: {}", application.id());

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Bank B is temporarily unavailable"
        );
    }
}