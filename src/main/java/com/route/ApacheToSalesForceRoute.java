package com.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApacheToSalesForceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    /*Start this route when another Camel route sends a message to
      direct:sendToSalesforce, I can say Internal routing part. */

        from("direct:sendToSalesforce")
                .routeId("send-name-to-salesforce")
                .log("Received from Controller: ${body}")
                .setHeader("Authorization", simple("Bearer ${header.accessToken}"))
                .setHeader("Content-Type", constant("text/plain"))
                .to("https://orgfarm-c796dc6e9a-dev-ed.develop.my.salesforce.com/services/apexrest/name-splitter/");

    }
}
