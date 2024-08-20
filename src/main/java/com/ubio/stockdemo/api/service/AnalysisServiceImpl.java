package com.ubio.stockdemo.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {
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

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String analysisData(long code) {
        String url = apiDomain + "/uapi/domestic-stock/v1/quotations/program-trade-by-stock";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_INPUT_ISCD", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = "Bearer " + accessToken;
        headers.add("authorization", token);
        headers.add("appkey", appKey);
        headers.add("appsecret", appSecret);
        headers.add("tr_id", "FHPPG04650100");
        URI uri = uriBuilder.build().encode().toUri();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            entity,
            String.class
        );
        
        // log.info(response.getBody());
        return response.getBody();
    }
    
    @Override
    public String analysisDaily(long code, String date) {
        String url = apiDomain + "/uapi/domestic-stock/v1/quotations/program-trade-by-stock-daily";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
        		.queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_INPUT_ISCD", code)
                .queryParam("FID_INPUT_DATE_1", date);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = "Bearer " + accessToken;
        headers.add("authorization", token);
        headers.add("appkey", appKey);
        headers.add("appsecret", appSecret);
        headers.add("tr_id", "FHPPG04650200");
        headers.add("custtype", "P");
        URI uri = uriBuilder.build().encode().toUri();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            entity,
            String.class
        );
        
        // log.info(response.getBody());
        return response.getBody();
    }
}
