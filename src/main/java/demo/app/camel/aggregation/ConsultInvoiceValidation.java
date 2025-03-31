package demo.app.camel.aggregation;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public final class ConsultInvoiceValidation implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            String body = oldExchange.getIn().getBody(String.class) + " " + newExchange.getIn().getBody(String.class);
            oldExchange.getIn().setBody(body);
            return oldExchange;
        }
        return newExchange;
    }
}
