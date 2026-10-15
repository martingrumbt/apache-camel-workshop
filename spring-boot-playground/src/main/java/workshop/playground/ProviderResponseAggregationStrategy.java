package workshop.playground;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.ArrayList;
import java.util.List;

public class ProviderResponseAggregationStrategy
        implements AggregationStrategy {

    @Override
    public Exchange aggregate(
            Exchange oldExchange,
            Exchange newExchange
    ) {
        if (oldExchange == null) {
            List<Object> responses = new ArrayList<>();
            responses.add(newExchange.getMessage().getBody());

            newExchange.getMessage().setBody(responses);
            return newExchange;
        }

        List<Object> responses = oldExchange
                .getMessage()
                .getBody(List.class);

        responses.add(newExchange.getMessage().getBody());

        return oldExchange;
    }
}