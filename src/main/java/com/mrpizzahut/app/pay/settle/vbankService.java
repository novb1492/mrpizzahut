package com.mrpizzahut.app.pay.settle;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.api.requestTo;
import com.mrpizzahut.app.buket.buketService;
import com.mrpizzahut.app.hash.aes256;
import com.mrpizzahut.app.hash.sha256;
import com.mrpizzahut.app.pay.paymentService;
import com.mrpizzahut.app.pay.productService;

@Service
public class vbankService {
	
	private final String mchtId="nx_mid_il";
	private final String sucPayNum="0051";
	private final int cancleFlag=1;
	private final String buyKind="vbank";
	 

	@Autowired
	private requestTo requestTo;
	@Autowired
	private productService productService;
	@Autowired
	private buketService buketService;
	@Autowired
	private paymentService paymentService;
	
	@Transactional(rollbackFor = Exception.class)
    public JSONObject vbankConfrim(settleDto settleDto,String email) {
        System.out.println("vbankConfrim");
        JSONObject reseponse=new JSONObject();
        String mchtTrdNo=settleDto.getMchtTrdNo();
        System.out.println(settleDto.getFnNm()+","+settleDto.getMchtParam());
        try {
        	if(!settleDto.getOutStatCd().equals(sucPayNum)){
                System.out.println("결제실패 실패 코드 "+settleDto.getOutRsltCd());
                throw new RuntimeException("결제실패");
            }
        	Map<String, Object>vbank=paymentService.selectByMchtTrdNo(mchtTrdNo, buyKind, email);
        	System.out.println("결제정보 "+vbank.toString());
            settleDto.setTrdAmt(utillService.aesToNomal(settleDto.getTrdAmt()));
            if(Integer.parseInt(vbank.get("VDONEFLAG").toString())==1) {
            	 return utillService.makeJson(true, "구매가 완료되었습니다");
            }else if(!settleDto.getTrdAmt().equals(vbank.get("VTRDAMT").toString())) {
            	System.out.println("금액이 일치하지 않음");
            	throw new RuntimeException("금액이 일치하지 않습니다");
            }
            Map<String, Object>map=new HashMap<String, Object>();
            String vtlAcntNo=utillService.aesToNomal(settleDto.getVtlAcntNo());
            map.put("mchtid", settleDto.getMchtId());
            map.put("fnnm", settleDto.getFnNm());
            map.put("trdNo", settleDto.getTrdNo());
            map.put("mchtTrdNo", settleDto.getMchtTrdNo());
            map.put("email", email);
            map.put("vtlAcntNo",vtlAcntNo);
            map.put("fncd",settleDto.getFnCd());
     
            productService.minusProductCount(mchtTrdNo);
            productService.doneCoupon(vbank.get("VCOUPON").toString(),email,mchtTrdNo);
            paymentService.updateDonFlag(map, buyKind);
            buketService.deleteBuket(email);
            reseponse.put("flag", true);
            reseponse.put("mchtTrdNo", mchtTrdNo);
            reseponse.put("price", settleDto.getTrdAmt());
            reseponse.put("buykind", buyKind);
            reseponse.put("productNames", settleDto.getMchtParam());
            reseponse.put("vbanknum", vtlAcntNo);
            reseponse.put("expireDate",vbank.get("VEXPIREDATE"));
            return reseponse;
            //throw new RuntimeException("test");
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("가상꼐좌 예외 발생 채번취소");
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
             settleDto.setVtlAcntNo(utillService.aesToNomal(settleDto.getVtlAcntNo()));
             JSONObject jsonObject=requestToSettle(makeCancleAccountBody(settleDto));
             reseponse.put("message", message+"구매실패");
             return reseponse;
        }
        
    }
	 public JSONObject makeCancleAccountBody(settleDto settleDto) {
	        try {
	            Map<String,String>map=utillService.getTrdDtTrdTm();
	            String pktHash=requestcancleString(settleDto.getMchtTrdNo(),settleDto.getTrdAmt(), settleDto.getMchtId(),map.get("trdDt"),map.get("trdTm"),"0");
	            JSONObject body=new JSONObject();
	            JSONObject params=new JSONObject();
	            JSONObject data=new JSONObject();
	            params.put("mchtId", settleDto.getMchtId());
	            params.put("ver", "0A18");
	            params.put("method", "VA");
	            params.put("bizType", "A2");
	            params.put("encCd", "23");
	            params.put("mchtTrdNo", settleDto.getMchtTrdNo());
	            params.put("trdDt", map.get("trdDt"));
	            params.put("trdTm", map.get("trdTm"));
	            data.put("pktHash", sha256.encrypt(pktHash));
	            data.put("orgTrdNo", settleDto.getTrdNo());
	            data.put("vAcntNo", aes256.encrypt(settleDto.getVtlAcntNo()));
	            body.put("params", params);
	            body.put("data", data);
	        return body;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException();
	        }
	    }
	    private String requestcancleString(String mchtTrdNo,String price,String mchtId,String trdDt,String trdTm,String zero) {
	        System.out.println("requestcancleString zero");
	        return String.format("%s%s%s%s%s%s",trdDt,trdTm,mchtId,mchtTrdNo,zero,"ST1009281328226982205"); 
	    }
	    private JSONObject requestToSettle(JSONObject body){
			  System.out.println("requestToSettle");
		        JSONObject  response=requestTo.requestToSettle("https://tbgw.settlebank.co.kr/spay/APIVBank.do", body);
		        LinkedHashMap<String,Object>params=(LinkedHashMap<String, Object>) response.get("params");
		        System.out.println("세틀뱅크 통신결과"+response.toString());
		        String message=(String)params.get("outRsltMsg");
		        if(params.get("outStatCd").equals("0021")){
		        	return utillService.makeJson(true,message);
		        }	
		        return utillService.makeJson(false,message);
		    }
	/* public JSONObject cancle(settleDto settleDto) {
	        System.out.println("makeCancleBody");
	        try {
	            Map<String,String>map=utillService.getTrdDtTrdTm();
	            String pktHash=requestcancleString(settleDto.getMchtTrdNo(),settleDto.getCnclAmt(), settleDto.getMchtId(),map.get("trdDt"),map.get("trdTm"));
	            JSONObject body=new JSONObject();
	            JSONObject params=new JSONObject();
	            JSONObject data=new JSONObject();
	            params.put("mchtId", settleDto.getMchtId());
	            params.put("ver", "0A17");
	            params.put("method", "VA");
	            params.put("bizType", "C0");
	            params.put("encCd", "23");
	            params.put("mchtTrdNo", settleDto.getMchtTrdNo());
	            params.put("trdDt", map.get("trdDt"));
	            params.put("trdTm", map.get("trdTm"));
	            data.put("pktHash", sha256.encrypt(pktHash));
	            data.put("orgTrdNo", settleDto.getTrdNo());
	            data.put("crcCd","KRW");
	            data.put("cnclOrd",settleDto.getCnclOrd());
	            data.put("cnclAmt",aes256.encrypt(settleDto.getCnclAmt()));
	            data.put("refundBankCd",settleDto.getRefundBankCd());
	            data.put("refundAcntNo",aes256.encrypt(settleDto.getRefundAcntNo()));
	            data.put("refundDpstrNm",settleDto.getUserName());
	            body.put("params", params);
	            body.put("data", data);
	        return body;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException();
	        }
	    }*/
	

	
}
