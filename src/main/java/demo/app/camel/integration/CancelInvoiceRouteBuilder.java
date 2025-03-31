package demo.app.camel.integration;

import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class CancelInvoiceRouteBuilder extends RouteBuilder {

    String url;

    @Override
    public void configure() {
        from("direct:cancel-invoice")
            .circuitBreaker()
                .routeId("cancel-invoice")
                .log(LoggingLevel.INFO, "Start Cancel invoice")
                .removeHeader("accept-encoding")
                .removeHeader( "Accept-encoding")
                .setHeader(Exchange.HTTP_METHOD, constant("PATCH"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application-json"))
                .toD(url
                    + "/collection-bill-management/v1/bankslips"
                    + "?httpClientCOnfigurer=httpClientCOnfigurerTrustAllCacerts"
                    + "&bridgeEndpoint=true"
                    + "&maxTotalCOnnections=1000"
                    + "&connectionsPerRoute=100")
                .id("serviceCancelInvoice");

    }
}
