package com.controller;
import com.service.SalesForceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesForceController {

    private final ProducerTemplate producerTemplate;
    private final SalesForceService service;

    public SalesForceController(ProducerTemplate producerTemplate, SalesForceService salesforceService) {
        this.producerTemplate = producerTemplate;
        this.service = salesforceService;
    }

    @Operation(
        summary = "Split a full name",description = "Sends a full name to Salesforce and returns the first and last name")
     @ApiResponses({@ApiResponse(responseCode = "200",description = "Success"),
        @ApiResponse(responseCode = "400",description = "Invalid request"),
        @ApiResponse(responseCode = "500",description = "Internal Server Error"),
        @ApiResponse(responseCode = "502",description = "Salesforce failure")})
    @PostMapping("/name")
    public String sentToSalesforce(@RequestBody String name) {

        String accessToken = service.getAccessToken().getAccess_token();

        return producerTemplate.requestBodyAndHeader(
                "direct:sendToSalesforce",
                name,
                "accessToken",
                accessToken,
                String.class
        );}
}
