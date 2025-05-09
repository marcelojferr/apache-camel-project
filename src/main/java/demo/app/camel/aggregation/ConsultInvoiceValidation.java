package demo.app.camel.aggregation;

import demo.app.camel.exception.InvoiceException;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public final class ConsultInvoiceValidation implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (newExchange != null) {
//          String body = oldExchange.getIn().getBody(String.class) + " " + newExchange.getIn().getBody(String.class);
//          oldExchange.getIn().setBody(body);

            ConsultInvoiceValidation body = newExchange.getIn().getBody(ConsultInvoiceValidation.class);
            oldExchange.setProperty("return", body);
            return oldExchange;
        } else {
            oldExchange.setException(new InvoiceException());
            throw new InvoiceException();
        }
    }
}