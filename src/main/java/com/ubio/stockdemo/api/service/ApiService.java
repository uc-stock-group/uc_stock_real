package com.ubio.stockdemo.api.service;

import com.ubio.stockdemo.model.dto.AccessToken;

public interface ApiService {
    String login();

    String getAccount();

    AccessToken getAccessToken(String appKey, String appSecret);
}
