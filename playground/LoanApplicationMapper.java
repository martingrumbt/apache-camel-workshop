import java.math.BigDecimal;
import java.util.Map;

public class LoanApplicationMapper {

    public LoanApplication map(Map<String, String> row) {
        return new LoanApplication(
                row.get("id"),
                new BigDecimal(row.get("amount")),
                Integer.parseInt(row.get("term"))
        );
    }
}
