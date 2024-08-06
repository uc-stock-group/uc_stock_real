package com.ubio.stockdemo.api.service;

public interface ApiMJYService {
	String getProductQuote(String isCode, String inputDate1, String inputDate2, String periodCode);
	String getHoliday(String baseDate, String areaNK, String areaFK);
}
