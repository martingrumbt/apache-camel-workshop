package workshop.playground;

import java.math.BigDecimal;

public record LoanApplication(
        String id,
        BigDecimal amount,
        int term
) {}