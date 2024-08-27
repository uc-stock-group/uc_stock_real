package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private ApiService apiService;

//    access token 발급받기
    @GetMapping("/login")
    public String Login(){
        return apiService.login();
    }

//    계좌 조회
    @GetMapping("/getaccount")
    public String getAccount() {
        return apiService.getAccount();
    }


}
