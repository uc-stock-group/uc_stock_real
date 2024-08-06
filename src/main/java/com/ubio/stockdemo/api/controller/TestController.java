package com.ubio.stockdemo.api.controller;



import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
    	log.info("test");
        return "test임";
    }

//    client에서 데이터를 보낼때 url에 데이터를 넣어서 보내면 @PathVariable로 받아서 사용
    @GetMapping("/test/{context}")
    public String test2(@PathVariable("context") String context) {
        log.info("context: {}", context);
        return "test/" + context;

    }



//    client에서 데이처를 json으로 보내면 map으로 받아서 사용
    @GetMapping("/test/requestBody")
    public String test3(@RequestBody Map<String, Object> req) {
        log.info("req: {}", req);
        String userLoginId = (String) req.get("userLoginId");
        log.info("userLoginId: {}", userLoginId);
        String domainName = (String) req.get("domainName");
        log.info("domainName: {}", domainName);
        return "test3";
    }

//    client에서 데이터를 보낼때 url에 데이터를 넣어서 보내면 @PathVariable로 받아서 사용
    @GetMapping("/test/{context}/{context2}")
    public String test4(@PathVariable("context") String context, @PathVariable("context2") String context2) {
        log.info("context: {}", context);
        log.info("context2: {}", context2);
        return "test/" + context + "/" + context2;
    }

}
