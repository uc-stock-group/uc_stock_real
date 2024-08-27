package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.StockService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

// 클라이언트에서 /api/stock 으로 시작하는 api 전송 시 받아서 처리
@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    // /api/stock/quotations/inquire_price/{divCode}/{isCode} api 받아서 처리
    // 예시: /api/stock/quotations/inquire_price/J/005930 -> 삼성전자 현재 시세
    @GetMapping("/quotations/inquire_price/{divCode}/{isCode}")
    public String getQuotations_InquirePrice(@PathVariable("divCode") String divCode, @PathVariable("isCode") String isCode) {
        System.out.println(divCode);
        //stockService에서 한투로 삼성전자 실시간 시세 api 전송 후 response return
        return stockService.getQuotations_InquirePrice(divCode, isCode);
    }

    @GetMapping("/quotations/inquire_ccnl/{divCode}/{isCode}")
    public String getQuotations_InquireCcnl(@PathVariable("divCode") String divCode, @PathVariable("isCode") String isCode) {
        System.out.println(divCode);
        //stockService에서 한투로 삼성전자 실시간 체결 api 전송 후 response return
        return stockService.getQuotations_InquireCcnl(divCode, isCode);
    }

    @GetMapping("/quotations/inquire_daily/{divCode}/{isCode}")
    public String getQuotations_InquireDaily(@PathVariable("divCode") String divCode, @PathVariable("isCode") String isCode) {
        System.out.println(divCode);
        //stockService에서 한투로 삼성전자 일별 시세 api 전송 후 response return
        return stockService.getQuotations_InquireDaily(divCode, isCode);
    }
}
