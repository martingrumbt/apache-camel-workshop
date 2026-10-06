package workshop.provider;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/applications")
public class ProviderController {

    @PostMapping
    public Map<String, String> createApplication(
            @RequestBody LoanApplication application
    ) {
        System.out.println("Received application: " + application);

        return Map.of(
                "provider", "Bank A",
                "status", "ACCEPTED"
        );
    }
}