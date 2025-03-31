package demo.app.camel.routes;

import demo.app.camel.aggregation.CancelInvoiceValidation;
import demo.app.camel.aggregation.ConsultInvoiceValidation;
import demo.app.camel.domain.CancelInvoiceRequest;
import demo.app.camel.exception.HttpOperationFailedException;
import demo.app.camel.exception.InvoiceException;
import demo.app.camel.processor.error.ExceptionProcessor;
import demo.app.camel.processor.error.InvoiceExceptionProcessor;
import demo.app.camel.processor.error.SocketTimeoutExceptionProcessor;
import demo.app.camel.processor.error.HttpOperationFailedExceptionProcessor;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.net.SocketTimeoutException;

public class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {

//        from("file:src/data?noop=true")
//            .choice()
//                .when(xpath("/person/city = 'London'"))
//                    .log("UK message")
//                    .to("file:target/messages/uk")
//                .otherwise()
//                    .log("Other message")
//                    .to("file:target/messages/others");

   from("direct:TO_CancelInvoiceDefaultPOST-SERVICE")
           .routeId("id-CancelInvoiceDefaultPOST-SERVICE")
           .doTry()
               .process( new CancelInvoiceRequest())
               .enrich("direct: authentication")
               .to("direct:cancel-invoice")
               .aggregate(,new CancelInvoiceValidation())
               .to("direct:TO_CancelInvoiceDefaultPOST-SERVICE")
           .doCatch(SocketTimeoutException.class)
                .process(new SocketTimeoutExceptionProcessor())
                .stop()
           .doCatch(HttpOperationFailedException.class)
               .process(new HttpOperationFailedExceptionProcessor("Error"))
               .marshal().json(JsonLibrary.Jackson)
               .stop()
           .doCatch(InvoiceException.class)
                .process(new InvoiceExceptionProcessor("Error"))
                .marshal().json(JsonLibrary.Jackson)
                .stop()
           .doCatch(Exception.class)
                .process(new ExceptionProcessor("Error"))
                .marshal().json(JsonLibrary.Jackson)
                .stop()
           .endDoTry()
       .end();

        from("direct:TO_ConsultInvoiceDefaultPOST-SERVICE")
            .routeId("id-ConsultInvoiceDefaultPOST-SERVICE")
            .doTry()
                .process( new CancelInvoiceRequest())
                .enrich("direct: authentication")
                .to("direct:consult-invoice")
                .aggregate(header(""), new ConsultInvoiceValidation())
                .to("direct:TO_ConsultInvoiceDefaultPOST-SERVICE")
            .doCatch(SocketTimeoutException.class)
                .process(new SocketTimeoutExceptionProcessor())
                .stop()
            .doCatch(Exception.class)
                .process(new ExceptionProcessor("Error"))
                .marshal().json(JsonLibrary.Jackson)
                .stop()
            .endDoTry()
        .end();
    }

}
