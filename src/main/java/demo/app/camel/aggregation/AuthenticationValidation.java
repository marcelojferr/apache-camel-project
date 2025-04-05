package demo.app.camel.aggregation;

import demo.app.camel.exception.InvoiceException;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class AuthenticationValidation implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (newExchange != null) {
            String body = newExchange.getIn().getBody(String.class);
            oldExchange.getIn().getHeader("Authorization", "Bearer " + body);

            return oldExchange;
        }else {
            oldExchange.setException(new InvoiceException());
            throw new InvoiceException();
        }
    }
}
