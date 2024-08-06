package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/program")
    public ModelAndView getAnalysisProgram(@RequestParam("code") long code, @RequestParam("name") String name) {
        // 종목별프로그램매매추이(체결)
        String data = analysisService.analysisData(code);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("programTrade"); // 뷰 페이지
        modelAndView.addObject("tradeList", data);
        modelAndView.addObject("tName", name);
        System.out.println(data);
        return modelAndView;
    }




}
