package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.ApiMJYService;
import com.ubio.stockdemo.api.service.ApiService;
import com.ubio.stockdemo.api.service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private ApiService apiService;
    
    @Autowired
    private ApiMJYService apiMJYService;

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

    // 국내주식업종기간별시세(일/주/월/년)
    @GetMapping("/getProductQuote")
    public String getProductQuote(@RequestParam(name= "isCode" ,required=false, defaultValue = "0001") String isCode, // 업종 상세코드 (0001:종합, 0002:대형주,, 종목정보 다운로드 업종코드 참조)
    							  @RequestParam(name= "inputDate1" ,required=false,defaultValue = "20220501") String inputDate1, // 조회 시작일자 (20220501)
    							  @RequestParam(name= "inputDate2" ,required=false,defaultValue = "20220530") String inputDate2, // 조회 종료일자 
    							  @RequestParam(name= "periodCode" ,required=false,defaultValue = "D") String periodCode // 기간분류코드 (D:일봉, W:주봉, M:월봉, Y:년봉)
    							  ) {
    	
    	
    	return apiMJYService.getProductQuote(isCode, inputDate1, inputDate2, periodCode);
    }
    @GetMapping("/getHoliday")
    public String getHoliday(@RequestParam(name= "baseDate" ,required=false,defaultValue = "20230302") String baseDate, // 기준일자
							 @RequestParam(name= "areaNK" ,required=false,defaultValue = "") String areaNK, // 연속조회키
							 @RequestParam(name= "areaFK" ,required=false,defaultValue = "") String areaFK // 연속조회검색조건 
							 ) {
    	return apiMJYService.getHoliday(baseDate,areaNK, areaFK);
    }

}
