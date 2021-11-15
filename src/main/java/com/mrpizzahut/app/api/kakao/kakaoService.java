package com.mrpizzahut.app.api.kakao;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrpizzahut.app.utillService;

@Service
public class kakaoService {

	
	@Autowired
	private kakaopayService kakaopayService;
	
	public void processCallback(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("processCallback");
		String scope=request.getParameter("scope"); 
		String uri=null;
		String parm=null;
		if(scope.equals("pay")) {
			System.out.println("카카오 페이 요청입니다");
			JSONObject result=kakaopayService.requestKakaopay(request);
			System.out.println("카카오페이 최종 응답 "+result.toString());
			if((boolean) result.get("flag")) {
				parm="?flag="+result.get("flag")+"&buykind="+utillService.makeUtf8(result.get("buykind").toString())+"&productNames="+utillService.makeUtf8(result.get("productNames").toString())+"&price="+utillService.makeUtf8(result.get("price").toString());
			}else {
				parm="?buykind="+utillService.makeUtf8(result.get("buykind").toString())+"&flag="+result.get("flag")+"&message="+utillService.makeUtf8(result.get("message").toString());
			}
			uri="/app/donePay";
			utillService.doRedirect(response, uri,parm);
		}
	}
}
