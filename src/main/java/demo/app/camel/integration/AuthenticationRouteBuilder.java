package demo.app.camel.integration;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class AuthenticationRouteBuilder extends RouteBuilder {

    String urlToken;

    String clientId;

    String secretId;

    String appKey;
    @Override
    public void configure() {
        from("direct:authentication")
            .routeId("authentication")
            .log(LoggingLevel.INFO, "Start authentication")
            .removeHeaders("CamelHttp*")
            .removeHeader("authorization*")
            .process(exchange -> {
                exchange.getIn().setBody(
                        "grant_type=client_credentials&client_id=" + clientId +
                        "&client_secret=" + secretId
                );
            })
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application-json"))
            .setHeader("x-application-key", simple(appKey))
            .setHeader("system", simple("EM"))
            .toD(urlToken
                + "?httpClientConfigurer=httpClientCOnfigurerTrustAllCacerts"
                + "&bridgeEndpoint=true"
                + "&maxTotalConnections=1000"
                + "&connectionsPerRoute=100")
            .id("serviceAuthentication");

    }
}
