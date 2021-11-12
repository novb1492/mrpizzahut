package com.mrpizzahut.app.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class kakaoService {
	
	@Autowired
	private kakaopayService kakaopayService;
	
	public void processCallback(HttpServletRequest request) {
		System.out.println("processCallback");
		String scope=request.getParameter("scope"); 
		if(scope.equals("pay")) {
			System.out.println("카카오 페이 요청입니다");
			kakaopayService.requestKakaopay(request);
		}
	}
}
