package com.mrpizzahut.app.pay.settle;

import java.util.List;
import java.util.Map;

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
	
	@Autowired
	private paymentService paymentService;
	
    public JSONObject makeBuyInfor(tryBuyDto tryBuyDto,List<Map<String,Object>>maps,String email) {
		System.out.println("makeBuyInfor");
        Map<String,Object>map=maps.get(maps.size()-1);
        Map<String,String>trdDtTrdTm=utillService.getTrdDtTrdTm();
        String mchtTrdNo=maps.get(0).get("bigKind")+utillService.getRandomNum(10);
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
       // paymentService.insertTemp(maps, mchtTrdNo, email);
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
