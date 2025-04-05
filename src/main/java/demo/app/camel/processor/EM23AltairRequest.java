package demo.app.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EM23AltairRequest implements Processor {

    String user;

    public EM23AltairRequest(String user) {
        this.user = user;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

    }
}
