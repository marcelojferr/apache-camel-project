package demo.app.camel.routes;

import demo.app.camel.processor.domain.CancelInvoiceResponse;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;

public class MainRouteBuilder  extends RouteBuilder {
    @Override
    public void configure() throws Exception{
        rest("/invoice")
            .consumes("application/json")
            .produces("application/json")
            .post("/cancel")
                .bindingMode(RestBindingMode.auto)
                .skipBindingOnErrorCode(true)
                .type(CancelInvoiceResponse.class)
                .to("direct:TO_CancelInvoiceDefaultPOST")

            .get("/consult")
                .param()
                    .name("invoiceId")
                    .type(RestParamType.query)
                    .dataType("string")
                .endParam()
                .bindingMode(RestBindingMode.auto)
                .skipBindingOnErrorCode(true)
                .type(CancelInvoiceResponse.class)
                .to("direct:TO_ConsultInvoiceDefaultPOST")
            .responseMessage()
                .code(201)
                .message("Cancel invoice")
                .endResponseMessage()
            .responseMessage()
                .code(400)
                .message("Bad request")
                .endResponseMessage()
            .responseMessage()
                .code(401)
                .message("Unauthorized")
                .endResponseMessage();
    }
}
