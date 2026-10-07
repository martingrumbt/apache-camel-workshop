import java.math.BigDecimal;
import java.util.List;

public class LoanApplicationMapper {

    public LoanApplication map(List<String> row) {
        return new LoanApplication(
                row.get(0),
                new BigDecimal(row.get(1)),
                Integer.parseInt(row.get(2))
        );
    }
}