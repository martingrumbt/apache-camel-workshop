import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class ProviderResponseAggregationStrategy
        implements AggregationStrategy {

    @Override
    public Exchange aggregate(
            Exchange oldExchange,
            Exchange newExchange
    ) {
        // TODO Combine the provider responses

        return oldExchange;
    }
}