package com.model;

import lombok.Data;

@Data
public class SalesForceTokenResponse {

    private String access_token;
    private String instance_url;
    private String token_type;

}
