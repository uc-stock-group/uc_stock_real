package com.ubio.stockdemo.api.service.impl;

import java.net.URI;
import com.ubio.stockdemo.api.service.StockService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//실질적으로 한국투자증권으로 api 전송 후 response 받는 부분
@Service
public class StockServiceImpl implements StockService {

    @Value("${apiInfo.address}")
    private String apiDomain;

    @Value("${apiInfo.appKey}")
    private String appKey;

    @Value("${apiInfo.appSecrect}")
    private String appSecret;

    @Value("${apiInfo.access_token}")
    private String accessToken;

    @Value("${apiInfo.accountNo}")
    private String accountNo;

    @Override
    public String getQuotations_InquirePrice(String divCode, String IsCode) {

        //모의투자 uri
        String str_uri = apiDomain + "/uapi/domestic-stock/v1/quotations/inquire-price";

        //전송 uri에 필요데이터 추가
        URI uri = UriComponentsBuilder.fromHttpUrl(str_uri)
        .queryParam("FID_COND_MRKT_DIV_CODE", divCode)
        .queryParam("FID_INPUT_ISCD", IsCode)
        .build().encode().toUri();

        //api 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = "Bearer " + accessToken;
        headers.set("authorization", token);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST01010100");

        RestTemplate restTemplate = new RestTemplate();

        try {
            //전송시도
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            System.out.println(response.toString());
            //성공시 response
            return response.toString();
        } catch (Exception e) {
            //전송실패시 response
            System.out.println("log: " + e.toString());
            return e.toString();
        }
    }

    @Override
    public String getQuotations_InquireCcnl(String divCode, String IsCode) {

        //모의투자 uri
        String str_uri = apiDomain + "/uapi/domestic-stock/v1/quotations/inquire-ccnl";

        //전송 uri에 필요데이터 추가
        URI uri = UriComponentsBuilder.fromHttpUrl(str_uri)
        .queryParam("FID_COND_MRKT_DIV_CODE", divCode)
        .queryParam("FID_INPUT_ISCD", IsCode)
        .build().encode().toUri();

        //api 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = "Bearer " + accessToken;
        headers.set("authorization", token);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST01010300");

        RestTemplate restTemplate = new RestTemplate();

        try {
            //전송시도
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            System.out.println(response.toString());
            //성공시 response
            return response.toString();
        } catch (Exception e) {
            //전송실패시 response
            System.out.println("log: " + e.toString());
            return e.toString();
        }
    }

    @Override
    public String getQuotations_InquireDaily(String divCode, String IsCode) {

        //모의투자 uri
        String str_uri = apiDomain + "/uapi/domestic-stock/v1/quotations/inquire-daily-price";

        //전송 uri에 필요데이터 추가
        URI uri = UriComponentsBuilder.fromHttpUrl(str_uri)
        .queryParam("FID_COND_MRKT_DIV_CODE", divCode)
        .queryParam("FID_INPUT_ISCD", IsCode)
        .queryParam("FID_PERIOD_DIV_CODE", "D") //30일 고정
        .queryParam("FID_ORG_ADJ_PRC", "0") //수정주가 반영 고정
        .build().encode().toUri();

        //api 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = "Bearer " + accessToken;
        headers.set("authorization", token);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST01010400");

        RestTemplate restTemplate = new RestTemplate();

        try {
            //전송시도
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            System.out.println(response.toString());
            //성공시 response
            return response.toString();
        } catch (Exception e) {
            //전송실패시 response
            System.out.println("log: " + e.toString());
            return e.toString();
        }
    }
}
