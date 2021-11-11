package com.mrpizzahut.app.api;

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

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.pay.paymentService;
import com.mrpizzahut.app.pay.tryBuyDto;

@Service
public class kakaopay {
	
	private final String readyUrl="https://kapi.kakao.com/v1/payment/ready";
    private final String cid="TC0ONETIME";
    private final String callbackUrl="app/kakao/callback?scope=pay";
    private final  String backDomain="http://localhost:8085/";
    private final String kakaoAdminKey="a813510779a54f77f1fe028ffd3e1d81";

    @Autowired
    private requestTo requestTo;
    @Autowired
    private paymentService paymentService;

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
        System.out.println("카카오페이 결제요청 결과"+response);
        maps.get(maps.size()-1).put("tid", response.get("tid"));
        paymentService.insertOrder(maps, mchtTrdNo, email, mchtTrdNo);
        paymentService.insertPayment(maps, mchtTrdNo, email,tryBuyDto.getKind());
        request.getSession().setAttribute("mchtTrdNo", mchtTrdNo);
        return utillService.makeJson(true,(String)response.get("next_redirect_pc_url"));
    }
  /*  @Transactional(rollbackFor = Exception.class)
    public void requestKakaopay(String pgToken) {
        System.out.println("requestKakaopay");
  
        String[][]itemArray=(String[][])httpSession.getAttribute("itemArray");
        String[]other=(String[])httpSession.getAttribute("other");
        String email=(String)httpSession.getAttribute("email");
        String name=(String)httpSession.getAttribute("name");
        int total_amount=(int)httpSession.getAttribute("totalPrice");
        String kind=(String)httpSession.getAttribute("kind");
        String tid=(String)httpSession.getAttribute("tid");
        List<Integer>timesOrSize=(List<Integer>)httpSession.getAttribute("timesOrSize");
        String partner_order_id=(String)httpSession.getAttribute("partner_order_id");
        String tax_free_amount="0";
        try {
            body.add("cid", cid);
            body.add("tid",tid);
            body.add("partner_order_id",partner_order_id);
            body.add("partner_user_id", email);
            body.add("quantity",httpSession.getAttribute("count"));
            body.add("pg_token", pgToken);
            headers.add("Authorization","KakaoAK "+adminKey);
            JSONObject response=requestToKakao(approveUrl);
            System.out.println(response+" 카카오페이 결제완료");
            String usedKind=aboutPayEnums.kakaoPay.getString();
            kakaopayService.kakaopayInsert(cid, partner_order_id, email, tax_free_amount, tid, total_amount);
            if(kind.equals(aboutPayEnums.reservation.getString())){
                System.out.println("예약 상품 결제");
                resevationService.doReservation(email,name, tid, itemArray, other,timesOrSize,status,usedKind);
            }else if(kind.equals(aboutPayEnums.product.getString())){
                System.out.println("상품결제");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("insertPaymentForkakao error"+e.getMessage());
            throw new failKakaoPay(e.getMessage(),cid,tid,total_amount);
        }
    } */
}
