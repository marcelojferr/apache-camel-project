package demo.app.camel.processor.error;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class HttpOperationFailedExceptionProcessor implements Processor {
    public HttpOperationFailedExceptionProcessor(String error) {
    }

    @Override
    public void process(Exchange exchange) throws Exception {

    }
}
