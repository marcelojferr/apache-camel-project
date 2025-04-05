package demo.app.camel.aggregation;

import demo.app.camel.exception.InvoiceException;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class CancelInvoiceValidation implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (newExchange != null) {
//          String body = oldExchange.getIn().getBody(String.class) + " " + newExchange.getIn().getBody(String.class);
//          oldExchange.getIn().setBody(body);

            CancelInvoiceValidation body = newExchange.getIn().getBody(CancelInvoiceValidation.class);
            oldExchange.setProperty("return", body);
            return oldExchange;
        }else {
            oldExchange.setException(new InvoiceException());
            throw new InvoiceException();
        }
    }
}
