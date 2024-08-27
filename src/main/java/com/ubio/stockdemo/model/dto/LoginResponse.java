package com.ubio.stockdemo.model.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String appKey;
    private String appSecret;
    private String realCano;
    private String accessToken;
}
