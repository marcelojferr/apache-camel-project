package demo.app.camel.routes;

import demo.app.camel.aggregation.AuthenticationValidation;
import demo.app.camel.aggregation.CancelInvoiceValidation;
import demo.app.camel.aggregation.ConsultInvoiceValidation;
import demo.app.camel.processor.domain.CancelInvoiceRequest;
import demo.app.camel.processor.domain.CancelInvoiceResponse;
import demo.app.camel.processor.domain.ConsultInvoiceRequest;
import demo.app.camel.exception.HttpOperationFailedException;
import demo.app.camel.exception.InvoiceException;
import demo.app.camel.processor.domain.ConsultInvoiceResponse;
import demo.app.camel.processor.error.ExceptionProcessor;
import demo.app.camel.processor.error.InvoiceExceptionProcessor;
import demo.app.camel.processor.error.SocketTimeoutExceptionProcessor;
import demo.app.camel.processor.error.HttpOperationFailedExceptionProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.net.SocketTimeoutException;

public class InvoiceRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:TO_CancelInvoiceDefaultPOST")
            .process(new CancelInvoiceRequest())
            .to("bean-validator://CancelInvoiceRequest")
            .to("direct:TO_CancelInvoiceDefaultPOST-SERVICE");

        from("direct:TO_CancelInvoiceDefaultPOST-SERVICE")
           .routeId("id-CancelInvoiceDefaultPOST-SERVICE")
           .doTry()
               .process( new CancelInvoiceRequest())
               .enrich("direct: authentication")
               .enrich("direct:cancel-invoice", new CancelInvoiceValidation())
               .to("direct:TO_CancelInvoiceDefaultResponse-SERVICE")
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

        from("direct:TO_CancelInvoiceDefaultPOST")
                .process(new CancelInvoiceResponse())
                .marshal().json(JsonLibrary.Jackson);

        from("direct:TO_ConsultInvoiceDefaultPOST")
            .process(new ConsultInvoiceRequest())
            .to("bean-validator://ConsultInvoiceRequest")
            .to("direct:TO_CancelInvoiceDefaultPOST-SERVICE");

        from("direct:TO_ConsultInvoiceDefaultPOST-SERVICE")
            .routeId("id-ConsultInvoiceDefaultPOST-SERVICE")
            .doTry()
                .enrich("direct: authentication", new AuthenticationValidation())
                .enrich("direct:consult-invoice", new ConsultInvoiceValidation())
                .to("direct:TO_ConsultInvoiceDefaultResponse-SERVICE")
            .doCatch(SocketTimeoutException.class)
                .process(new SocketTimeoutExceptionProcessor())
                .stop()
            .doCatch(Exception.class)
                .process(new ExceptionProcessor("Error"))
                .marshal().json(JsonLibrary.Jackson)
                .stop()
            .endDoTry()
        .end();

        from("direct:TO_ConsultInvoiceDefaultPOST")
            .process(new ConsultInvoiceResponse())
            .marshal().json(JsonLibrary.Jackson);
    }

}
