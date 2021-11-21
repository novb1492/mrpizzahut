package com.mrpizzahut.app.pay.settle;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrpizzahut.app.intenum;
import com.mrpizzahut.app.stringenums;
import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.api.requestTo;
import com.mrpizzahut.app.buket.buketService;
import com.mrpizzahut.app.hash.aes256;
import com.mrpizzahut.app.hash.sha256;
import com.mrpizzahut.app.pay.paymentService;
import com.mrpizzahut.app.pay.productService;

import Daos.payDao;

@Service
public class cardService {
	private final String MchtId=stringenums.cardMchtId.getString();
	private final String sucPayNum=stringenums.sucPayNum.getString();
	private final int doneFlag=intenum.doneFlag.getInt();
	private final int cancleFlag=intenum.cancleFlag.getInt();
	private final String buyKind=stringenums.card.getString();
	 

	@Autowired
	private requestTo requestTo;
	@Autowired
	private productService productService;
	@Autowired
	private buketService buketService;
	@Autowired
	private paymentService paymentService;
	
	@Transactional(rollbackFor = Exception.class)
    public JSONObject cardConfrim(settleDto settleDto ) {
        System.out.println("cardConfrim");
        JSONObject reseponse=new JSONObject();
        String mchtTrdNo=settleDto.getMchtTrdNo();
        System.out.println(settleDto.getFnNm()+","+settleDto.getMchtParam());
        String email=utillService.aesToNomal(settleDto.getMchtCustId());
        try {
        	if(!settleDto.getOutStatCd().equals(sucPayNum)){
                System.out.println("결제실패 실패 코드 "+settleDto.getOutRsltCd());
                throw new RuntimeException("결제실패");
            }
        	Map<String, Object>card=paymentService.selectByMchtTrdNo(mchtTrdNo, buyKind);
        	System.out.println("결제정보 "+card.toString());
            settleDto.setTrdAmt(utillService.aesToNomal(settleDto.getTrdAmt()));
            if(Integer.parseInt(card.get("CDONEFLAG").toString())==1) {
            	 return utillService.makeJson(true, "구매가 완료되었습니다");
            }else if(!settleDto.getTrdAmt().equals(card.get("CTRDAMT").toString())) {
            	System.out.println("금액이 일치하지 않음");
            	throw new RuntimeException("금액이 일치하지 않습니다");
            }
          
            Map<String, Object>map=new HashMap<String, Object>();
            map.put("doneDate", Timestamp.valueOf(LocalDateTime.now()));
            map.put("doneFlag", doneFlag);
            map.put("mchtid", settleDto.getMchtId());
            map.put("fnnm", settleDto.getFnNm());
            map.put("trdNo", settleDto.getTrdNo());
            map.put("mchtTrdNo", settleDto.getMchtTrdNo());
            map.put("email", email);
            productService.minusProductCount(mchtTrdNo);
            productService.doneCoupon(card.get("CCOUPON").toString(),email,mchtTrdNo);
            paymentService.updateDonFlag(map, buyKind);
            buketService.deleteBuket(email);
            reseponse.put("flag", true);
            reseponse.put("mchtTrdNo", mchtTrdNo);
            reseponse.put("price", settleDto.getTrdAmt());
            reseponse.put("buykind", buyKind);
            reseponse.put("productNames", settleDto.getMchtParam());
            return reseponse;
            //throw new RuntimeException();
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("카드결제중 예외 발생 자동환불");
        	 Map<String, Object>map=new HashMap<String, Object>();
        	 map.put("cancleDate", Timestamp.valueOf(LocalDateTime.now()));
             map.put("cancleFlag", cancleFlag);
             map.put("mchtTrdNo", settleDto.getMchtTrdNo());
             map.put("cnclord", 1);
             map.put("email", email);
             paymentService.updateBuykindCancleFlag(map, buyKind);
             paymentService.updateOrderCancleFlag(map);
             settleDto.setCnclOrd(1);
             String message=e.getMessage();
             reseponse.put("flag", false);
             reseponse.put("buykind", buyKind);
             JSONObject jsonObject=requestToSettle(cancle(settleDto));
             if((boolean)jsonObject.get("flag")) {
            	 message+=" 환불되었습니다";
             }else {
            	 message+="환불에 실패하였습니다 "+jsonObject.get("message");
             }
             reseponse.put("message", message);
             return reseponse;
        }
        
    }
	public JSONObject canclePay(Map<String, Object>orderAndPay) {
		System.out.println("canclepay");
		int cnclOrd=Integer.parseInt(orderAndPay.get("CCNCLORD").toString());
		cnclOrd+=1;
		int newPrice=Integer.parseInt(orderAndPay.get("CTRDAMT").toString())-Integer.parseInt(orderAndPay.get("OPRICE").toString());
		orderAndPay.put("newPrice", newPrice);
		orderAndPay.put("CCNCLORD", cnclOrd);
		paymentService.updateCardCancle(orderAndPay);
		settleDto settleDto=new settleDto();
		settleDto.setTrdAmt(orderAndPay.get("OPRICE").toString());
		settleDto.setCnclOrd(cnclOrd);
		settleDto.setMchtTrdNo(orderAndPay.get("CMCHTTRDNO").toString());
		settleDto.setTrdNo(orderAndPay.get("CTRDNO").toString());
		return requestToSettle(cancle(settleDto));
	}
	private JSONObject cancle(settleDto settleDto){
        System.out.println("cancle");
        Map<String,String>map=utillService.getTrdDtTrdTm();
        String trdDt=map.get("trdDt");
        String trdTm=map.get("trdTm");
        String pktHash=requestcancleString(trdDt,trdTm,settleDto.getMchtTrdNo(),settleDto.getTrdAmt());
        System.out.println(pktHash+" 해쉬예정문자열");
        JSONObject body=new JSONObject();
        JSONObject params=new JSONObject();
        JSONObject data=new JSONObject();
        params.put("mchtId", MchtId);
        params.put("ver", "0A18");
        params.put("method", "CA");
        params.put("bizType", "C0");
        params.put("encCd", "23");
        params.put("mchtTrdNo", settleDto.getMchtTrdNo());
        params.put("trdDt",trdDt);
        params.put("trdTm",trdTm);
        data.put("cnclOrd", settleDto.getCnclOrd());
        data.put("pktHash", sha256.encrypt(pktHash));
        data.put("orgTrdNo", settleDto.getTrdNo());
        data.put("crcCd", "KRW");
        data.put("cnclAmt", aes256.encrypt(settleDto.getTrdAmt()));
        body.put("params", params);
        body.put("data", data);
        
        return body;
    }
    private String requestcancleString(String trdDt,String trdTm, String mchtTrdNo,String price) {
        System.out.println("requestcancleString");
        return  String.format("%s%s%s%s%s%s",trdDt,trdTm,MchtId,mchtTrdNo,price,"ST1009281328226982205"); 
    }
	  private JSONObject requestToSettle(JSONObject body){
		  System.out.println("requestToSettle");
	        JSONObject  response=requestTo.requestToSettle("https://tbgw.settlebank.co.kr/spay/APICancel.do", body);
	        LinkedHashMap<String,Object>params=(LinkedHashMap<String, Object>) response.get("params");
	        System.out.println("세틀뱅크 통신결과"+response.toString());
	        String message=(String)params.get("outRsltMsg");
	        if(params.get("outStatCd").equals("0021")){
	        	return utillService.makeJson(true,message);
	        }	
	        return utillService.makeJson(false,message);
	    }
}
