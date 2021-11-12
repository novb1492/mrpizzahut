package com.mrpizzahut.app.pay.settle;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.hash.aes256;
import com.mrpizzahut.app.hash.sha256;
import com.mrpizzahut.app.pay.paymentService;
import com.mrpizzahut.app.pay.tryBuyDto;

@Service
public class settleService {
	
	private final String cardMchtId="nxca_jt_il";
	
	@Autowired
	private cardService cardService;
	@Autowired
	private paymentService paymentService;
	
	 public void confrimPayment(HttpServletRequest request,HttpServletResponse response) {
	        System.out.println("confrimPayment");
	        try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        settleDto settleDto=utillService.requestToSettleDto(request);
	        String email=utillService.getEmail(request);
	        System.out.println(email+"이메일");
	        System.out.println(settleDto.toString());
	        JSONObject result=new JSONObject();
	        String uri=null;
			String parm=null;
	        if(settleDto.getMchtId().equals(cardMchtId)){
	        	result=cardService.cardConfrim(settleDto,email);
	        }else{
	            throw utillService.makeRuntimeEX("지원하지 않는 결제 형식입니다","confrimPayment");
	        }
	        System.out.println("세틀뱅크 최종 응답 "+result.toString());
			if((boolean) result.get("flag")) {
				System.out.println("성공응답");
				parm="?flag="+result.get("flag")+"&buykind="+utillService.makeUtf8(result.get("buykind").toString())+"&productNames="+utillService.makeUtf8(result.get("productNames").toString())+"&price="+utillService.makeUtf8(result.get("price").toString());
			}else {
				System.out.println("실패응답");
				parm="?buykind="+utillService.makeUtf8(result.get("buykind").toString())+"&flag="+result.get("flag")+"&message="+utillService.makeUtf8(result.get("message").toString());
			}
			uri="/app/donePay";
			utillService.doRedirect(response, uri,parm);
	       
	   }
	
	
    public JSONObject makeBuyInfor(tryBuyDto tryBuyDto,List<Map<String,Object>>maps,String email) {
		System.out.println("makeBuyInfor");
        Map<String,Object>map=maps.get(maps.size()-1);
        Map<String,String>trdDtTrdTm=utillService.getTrdDtTrdTm();
        String mchtTrdNo=utillService.getRandomNum(10);
        String requestDate=trdDtTrdTm.get("trdDt");
        String requestTime=trdDtTrdTm.get("trdTm");
        String totalCash=Integer.toString((int)map.get("totalCash"));
        String buyKind=tryBuyDto.getKind();
        String[] idAndText=getRequestTest(buyKind, mchtTrdNo, requestDate, requestTime, totalCash);
        String settleText=idAndText[1];
        System.out.println(settleText+" 해쉬예정문자열");
        String hashText=sha256.encrypt(settleText);
        System.out.println(hashText+" 해쉬문자열");
        String priceHash=aes256.encrypt(totalCash);
        JSONObject response=new JSONObject();
        String expireDate=null;
        if(buyKind.equals("vbank")){
            System.out.println("가상계좌 만료시간 담기");
            expireDate=(String)map.get("expireDate");
            response.put("expireDt", expireDate);
        }
        response.put("mchtParam", map.get("itemNames"));
        response.put("itemName", map.get("itemNames"));
        response.put("mchtId", idAndText[0]);
        response.put("mchtCustId", aes256.encrypt(email));
        response.put("mchtTrdNo", mchtTrdNo);
        response.put("trdAmt", priceHash);
        response.put("trdDt", requestDate);
        response.put("trdTm", requestTime);
        response.put("pktHash", hashText);
        response.put("flag", true);
        paymentService.insertOrder(maps,mchtTrdNo,email,buyKind);
        paymentService.insertPayment(maps, mchtTrdNo, email, buyKind);
        return response;
    }
	private String[] getRequestTest(String method,String mchtTrdNo,String requestDate,String requestTime,String totalCash) {
        System.out.println("getRequestTest");
        String[] idAndText=new String[2];
        String id="";
        if(method.equals("card")){
            id="nxca_jt_il";
        }else if(method.equals("vbank")){
            id="nx_mid_il";
        }else{
            throw utillService.makeRuntimeEX("지원하지 않는 결제 방식입니다", "getRequestTest");
        }
        idAndText[0]=id;
        idAndText[1]=utillService.getSettleText(id,method, mchtTrdNo, requestDate, requestTime, totalCash);
        return idAndText;
    }
}
