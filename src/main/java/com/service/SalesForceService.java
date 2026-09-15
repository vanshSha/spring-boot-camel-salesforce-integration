package com.service;


import com.model.SalesForceTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SalesForceService {

    private final WebClient webClient;

    public SalesForceService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Value("${salesforce.client-id}")
    private String clientId;

    @Value("${salesforce.client-secret}")
    private String clientSecret;

    public SalesForceTokenResponse getAccessToken() {
    return webClient.post()
            .uri("/services/oauth2/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                    BodyInserters.fromFormData("grant_type", "client_credentials")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret)
            )
            .retrieve()
            .onStatus(
                    status -> status.isError(),
                    response -> response.bodyToMono(String.class)
                            .map(body -> {
                                System.err.println(
                                        "Salesforce OAuth error [" +
                                        response.statusCode() +
                                        "]: " + body
                                );
                                return new RuntimeException(
                                        "Salesforce OAuth failed: " + body
                                );
                            })
            )
            .bodyToMono(SalesForceTokenResponse.class)
            .block();
}
}
