package com.ubio.stockdemo.api.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiServiceMJYImpl implements ApiMJYService {

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

    // 국내주식업종기간별시세(일/주/월/년)
    @Override
    public String getProductQuote(String isCode, String inputDate1, String inputDate2, String periodCode) {
    	String url = apiDomain + "/uapi/domestic-stock/v1/quotations/inquire-daily-indexchartprice";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("fid_cond_mrkt_div_code", "U")
                .queryParam("fid_input_iscd", isCode)
                .queryParam("fid_input_date_1", inputDate1)
                .queryParam("fid_input_date_2", inputDate2)
                .queryParam("fid_period_div_code", periodCode);

        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + accessToken);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKUP03500100");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
    @Override
    public String getHoliday(String baseDate, String areaNK, String areaFK) {
    	String url = apiDomain + "/uapi/domestic-stock/v1/quotations/chk-holiday";
    	
    	UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
    			.queryParam("BASS_DT", baseDate)
    			.queryParam("CTX_AREA_NK", areaNK)
    			.queryParam("CTX_AREA_FK", areaFK);
    	
    	URI uri = uriBuilder.build().encode().toUri();
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.set("authorization", "Bearer " + accessToken);
    	headers.set("appkey", appKey);
    	headers.set("appsecret", appSecret);
    	headers.set("tr_id", "CTCA0903R");
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<String> response = restTemplate.exchange(
    			uri,
    			HttpMethod.GET,
    			entity,
    			String.class
    			);
    	
    	return response.getBody();
    }

}