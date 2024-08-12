package com.ubio.stockdemo.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubio.stockdemo.model.dto.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class ApiServiceImpl implements ApiService {

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
    public String login() {
        String url = apiDomain + "/oauth2/tokenP";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Map to hold request body
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("grant_type", "client_credentials");
        requestBodyMap.put("appkey", appKey);
        requestBodyMap.put("appsecret", appSecret);

        // Convert Map to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = "";
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyMap);
        } catch (JsonProcessingException e) {
            log.error("Error converting map to JSON", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );
        log.info(response.getBody());
        return response.getBody();

    }

//    appKey, appSecrect, 계좌번호(8자리), access 토큰이 필요
    @Override
    public String getAccount() {

        String url = apiDomain + "/uapi/domestic-stock/v1/trading/inquire-psbl-order";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("CANO", accountNo)
                .queryParam("ACNT_PRDT_CD", "01")
                .queryParam("PDNO", "005930")
                .queryParam("ORD_UNPR", "1000")
                .queryParam("ORD_DVSN", "01")
                .queryParam("OVRS_ICLD_YN", "N")
                .queryParam("CMA_EVLU_AMT_ICLD_YN", "N");

        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + accessToken);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "TTTC8908R");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

    @Override
    public AccessToken getAccessToken(String appKey, String appSecret) {
        String url = apiDomain + "/oauth2/tokenP";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Map to hold request body
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("grant_type", "client_credentials");
        requestBodyMap.put("appkey", appKey);
        requestBodyMap.put("appsecret", appSecret);

        // Convert Map to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = "";
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyMap);
        } catch (JsonProcessingException e) {
            log.error("Error converting map to JSON", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );
        log.info(response.getBody());

        AccessToken accessToken = null;
        try {
            accessToken = objectMapper.readValue(response.getBody(), AccessToken.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON response", e);
        }

        return accessToken;
    }
}
