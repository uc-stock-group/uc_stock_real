package com.ubio.stockdemo.api.service;


import com.ubio.stockdemo.model.dto.StockToken;

public interface ApiService {
    String login();

    String getAccount();

    StockToken getAccessToken(String appKey, String appSecret);
}
