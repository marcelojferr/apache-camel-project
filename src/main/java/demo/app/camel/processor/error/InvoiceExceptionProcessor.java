package demo.app.camel.processor.error;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class InvoiceExceptionProcessor implements Processor {
    public InvoiceExceptionProcessor(String error) {
    }

    @Override
    public void process(Exchange exchange) throws Exception {

    }
}
