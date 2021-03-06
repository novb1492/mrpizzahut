package com.mrpizzahut.app.api.kakao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.mrpizzahut.app.intenum;
import com.mrpizzahut.app.stringenums;
import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.pay.paymentService;
import com.mrpizzahut.app.pay.productService;
import com.mrpizzahut.app.pay.tryBuyDto;

import Daos.payDao;

@Service
public class kakaopayService {
	
	private final String readyUrl="https://kapi.kakao.com/v1/payment/ready";
    private final String cid="TC0ONETIME";
    private final String callbackUrl="app/kakao/callback?scope=pay";
    private final  String backDomain="http://localhost:8085/";
    private final String kakaoAdminKey="a813510779a54f77f1fe028ffd3e1d81";
    private final int doneFlag=intenum.doneFlag.getInt();
    private final int cancleFlag=intenum.cancleFlag.getInt();
    private final String buykind=stringenums.kakaoPay.getString();
    private final String realCancleUrl="https://kapi.kakao.com/v1/payment/cancel";

    @Autowired
    private com.mrpizzahut.app.api.requestTo requestTo;
    @Autowired
    private paymentService paymentService;
    @Autowired
    private productService productService;


    @Transactional(rollbackFor = Exception.class)
    public JSONObject getKaKaoPayLink(tryBuyDto tryBuyDto,List<Map<String,Object>>maps,String email,HttpServletRequest request) {
    	System.out.println("getKaKaoPayLink");
        MultiValueMap<String,Object> body=requestTo.getMultiValueBody();
        HttpHeaders headers=requestTo.getHeaders();
        String mchtTrdNo=utillService.getRandomNum(10);
        Map<String,Object>map=maps.get(maps.size()-1);
        body.add("cid", cid);
        body.add("partner_order_id",mchtTrdNo);
        body.add("partner_user_id",email);
        body.add("item_name",  map.get("itemNames"));
        body.add("quantity", 10);
        body.add("total_amount",map.get("totalCash"));
        body.add("tax_free_amount", 0);
        body.add("approval_url", backDomain+callbackUrl);
        body.add("cancel_url", backDomain+callbackUrl);
        body.add("fail_url", backDomain+callbackUrl);
        headers.add("Authorization","KakaoAK "+kakaoAdminKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        JSONObject response=requestTo.requestToApi(body, readyUrl, headers);
        System.out.println("??????????????? ???????????? ??????"+response);
        maps.get(maps.size()-1).put("tid", response.get("tid"));
        paymentService.insertOrder(maps, mchtTrdNo, email, buykind);
        paymentService.insertPayment(maps, mchtTrdNo, email,tryBuyDto.getKind());
        request.getSession().setAttribute(email+"mchtTrdNo", mchtTrdNo);
        return utillService.makeJson(true,(String)response.get("next_redirect_pc_url"));
    }
   @Transactional(rollbackFor = Exception.class)
    public JSONObject requestKakaopay(HttpServletRequest request) {
        System.out.println("requestKakaopay");
   	 String email=utillService.getEmail(request);
     String pgtoken=request.getParameter("pg_token");
     String mchtTrdNo=request.getSession().getAttribute(email+"mchtTrdNo").toString();
     Map<String, Object>kpay=paymentService.selectByMchtTrdNo(mchtTrdNo, buykind);
     JSONObject reponse=new JSONObject();
        try {
             System.out.println("??????????????? ?????? ??????"+kpay.toString());
             if(Integer.parseInt(kpay.get("KDONEFLAG").toString())!=0) {
            	 utillService.makeJson(true, "????????? ????????? ???????????????");
             }
             HttpHeaders headers=requestTo.getHeaders();
             MultiValueMap<String, Object>body=requestTo.getMultiValueBody();
             headers.add("Authorization","KakaoAK "+kakaoAdminKey);
             headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
             body.add("cid", cid);
             body.add("tid",kpay.get("KTID"));
             body.add("partner_order_id",kpay.get("KPARTNERORDERID"));
             body.add("partner_user_id", email);
             body.add("pg_token", pgtoken);
             JSONObject jsonObject=requestTo.requestToApi(body,"https://kapi.kakao.com/v1/payment/approve", headers);
             System.out.println("??????????????? ???????????? "+jsonObject.toString());
             LinkedHashMap<String, Object>amount=(LinkedHashMap<String, Object>) jsonObject.get("amount");
             int dbPrice=Integer.parseInt(kpay.get("KPRICE").toString());
             if(dbPrice!=Integer.parseInt(amount.get("total").toString())) {
            	 throw utillService.makeRuntimeEX("????????? ????????? ?????????", "requestKakaopay");
             }
             productService.minusProductCount(mchtTrdNo);
             productService.doneCoupon(kpay.get("KCOUPN").toString() , email, mchtTrdNo);
             kpay.put("doneDate",  Timestamp.valueOf(LocalDateTime.now()));
             kpay.put("doneFlag", doneFlag);
             paymentService.updateDonFlag(kpay,"kpay");
             reponse.put("flag", true);
             reponse.put("buykind", buykind);
             reponse.put("price", dbPrice);
             reponse.put("productNames", jsonObject.get("item_name"));
             return reponse;
             //throw new RuntimeException("test");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("??????????????? ?????? ??????");
			MultiValueMap<String, Object>body=requestTo.getMultiValueBody();
			body.add("cid", cid);
	        body.add("tid",kpay.get("KTID"));
	        body.add("cancel_amount",Integer.parseInt(kpay.get("KPRICE").toString()));
	        body.add("cancel_tax_free_amount",0);
            reponse.put("flag", false);
            reponse.put("buykind", buykind);
            String message=null;
	        if(cancleKakaoPAY(body)) {
	        	 Map<String, Object>map=new HashMap<String, Object>();
	        	 map.put("cancleDate", Timestamp.valueOf(LocalDateTime.now()));
	             map.put("cancleFlag", cancleFlag);
	             map.put("mchtTrdNo", mchtTrdNo);
	             map.put("email", email);
	             paymentService.updateOrderCancleFlag(map);
	             paymentService.updateBuykindCancleFlag(map, buykind);
	             message=e.getMessage()+"???????????????????????????";
	        	
	        }else {
	        	message="????????? ????????????????????? ??????????????? ????????????????????? ??????????????????";
	        }
	        reponse.put("message", message);
			return reponse;
		}
    } 
   public boolean cancleKPAY(Map<String, Object>kpay) {
	   System.out.println("cancleKPAY");
	   int newPrice=Integer.parseInt(kpay.get("KPRICE").toString())-Integer.parseInt(kpay.get("OPRICE").toString());
	   kpay.put("newPrice", newPrice);
	   System.out.println("????????? ?????? ?????? "+newPrice);
	   paymentService.updateKpayCancle(kpay);
	   MultiValueMap<String, Object>body=requestTo.getMultiValueBody();
	   body.add("cid", kpay.get("KCID"));
       body.add("tid",kpay.get("KTID"));
       body.add("cancel_amount",Integer.parseInt(kpay.get("OPRICE").toString()));
       body.add("cancel_tax_free_amount",0);
	   return cancleKakaoPAY(body);
	   
	   
   }
   private boolean cancleKakaoPAY(MultiValueMap<String, Object>body) {
	   System.out.println("cancleKakaoPAY");
	   HttpHeaders headers=requestTo.getHeaders();
	   headers.add("Authorization","KakaoAK "+kakaoAdminKey);
	   headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
       JSONObject response=requestTo.requestToApi(body, realCancleUrl, headers);
	   System.out.println("??????????????? ???????????? "+response.toString());
	   String status=response.get("status").toString();
	   if(status.equals("CANCEL_PAYMENT")||status.equals("PART_CANCEL_PAYMENT")) {
		   return true;
	   }
	   return false;
   }
}
