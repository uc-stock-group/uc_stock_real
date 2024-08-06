package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.UserService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.Reader;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClassicController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            String name = principal.getAttribute("name");
            String email = principal.getAttribute("email");
            userService.saveOrUpdateUser(name, email);
            model.addAttribute("name", name);
            model.addAttribute("email", email);
        }
        return "home";
    }

    @GetMapping(value = "/codes", produces = "application/json; charset=utf8")
    public ModelAndView codes(Model model) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        // JSON 파일 읽기
        // Reader reader = new FileReader("src\\main\\resources\\static\\themeCode.json"); // 한글 깨짐
        Reader reader = new InputStreamReader(new FileInputStream("src\\main\\resources\\static\\themeCode.json"), StandardCharsets.UTF_8);
        JSONArray dateArray = (JSONArray) parser.parse(reader);

        // for (int i = 0; i < dateArray.size(); i++) {
        //     JSONObject element = (JSONObject) dateArray.get(i);
        //     String themeName = (String) element.get("Name");
        //     long themeCode = (long) element.get("Code");
        //     // int themeCode = (int) element.get("Code"); // cast 오류
        //     System.out.println(themeName + ": " + themeCode);
        // }    

        // ModelAndView로 뷰 페이지와 데이터를 반환
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("analysis"); // 뷰 페이지 이름 설정
        modelAndView.addObject("jsonData", dateArray.toJSONString());
        // System.out.println(dateArray.toJSONString());
        return modelAndView;
    }
}
