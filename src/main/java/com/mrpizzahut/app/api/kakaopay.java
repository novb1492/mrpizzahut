package com.mrpizzahut.app.api;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.pay.paymentService;
import com.mrpizzahut.app.pay.tryBuyDto;

@Service
public class kakaopay {
	
	private final String readyUrl="https://kapi.kakao.com/v1/payment/ready";
    private final String cid="TC0ONETIME";
    private final String callbackUrl="kakao/callback?scope=pay";
    private final  String backDomain="http://localhost:8085/";
    private final String kakaoAdminKey="a813510779a54f77f1fe028ffd3e1d81";

    @Autowired
    private requestTo requestTo;
    @Autowired
    private paymentService paymentService;

    @Transactional(rollbackFor = Exception.class)
    public JSONObject getKaKaoPayLink(tryBuyDto tryBuyDto,List<Map<String,Object>>maps,String email) {
    	System.out.println("getKaKaoPayLink");
        MultiValueMap<String,Object> body=requestTo.getMultiValueBody();
        HttpHeaders headers=requestTo.getHeaders();
        String mchtTrdNo=maps.get(0).get("bigKind")+utillService.getRandomNum(10);
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
        paymentService.insertOrder(maps, mchtTrdNo, email, mchtTrdNo);
        paymentService.insertPayment(maps, mchtTrdNo, email,tryBuyDto.getKind());
        JSONObject response=requestTo.requestToApi(body, readyUrl, headers);
        System.out.println(response);
        return utillService.makeJson(true,(String)response.get("next_redirect_pc_url"));
    }
}
