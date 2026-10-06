package workshop.provider;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProviderController {

    @PostMapping("/providers/bank-a/applications")
    public Map<String, String> bankA(
            @RequestBody LoanApplication application
    ) {
        return Map.of(
                "provider", "Bank A",
                "status", "ACCEPTED"
        );
    }

    @PostMapping("/providers/bank-b/applications")
    public Map<String, String> bankB(
            @RequestBody LoanApplication application
    ) {
        return Map.of(
                "provider", "Bank B",
                "status", "ACCEPTED"
        );
    }
}