package demo.app.camel.processor.domain;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CancelInvoiceRequest implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(exchange.getIn().getBody(CancelInvoiceRequest.class));
    }
}
