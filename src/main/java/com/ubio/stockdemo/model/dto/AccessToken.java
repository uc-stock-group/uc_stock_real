package com.ubio.stockdemo.model.dto;

import lombok.Data;

@Data
public class AccessToken {
    private String access_token;
    private String access_token_token_expired;
    private String token_type;
    private int expires_in;
}
