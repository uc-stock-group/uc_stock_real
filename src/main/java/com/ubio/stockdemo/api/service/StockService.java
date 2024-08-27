package com.ubio.stockdemo.api.service;

public interface StockService {
    String getQuotations_InquirePrice(String divCode, String IsCode);
    String getQuotations_InquireCcnl(String divCode, String IsCode);
    String getQuotations_InquireDaily(String divCode, String IsCode);
}
