package com.ubio.stockdemo.api.service;

import java.util.List;

public interface AnalysisService {
    String analysisData(long code);
    String analysisDaily(long code, String date);
}
