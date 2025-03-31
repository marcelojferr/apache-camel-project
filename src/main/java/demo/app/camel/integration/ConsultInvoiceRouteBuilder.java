package demo.app.camel.integration;

import demo.app.camel.processor.EM23AltairProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class ConsultInvoiceRouteBuilder extends RouteBuilder {

    String user;

    @Override
    public void configure() {
        from("direct://consult-invoice")
            .routeId("consult-invoice")
            .log(LoggingLevel.INFO, "Start Consult invoice")
            .process(new EM23AltairProcessor(user))
            .toD("altair://em23AltairPS7"
                + "?personado=false"
                + "&transactionName=EM23"
                + "&altairUser=" + user
                + "&requestQueueName=FIN.QR.REQUEST.PSX"
                + "&responseQueueName=FIN.QR.REQUEST.PSX"
                + "&userHeaderPS=false"
                + "&psFormatEnumPs=PsFormatEnum.PS7")
            .id("consult-invoice");

    }
}
