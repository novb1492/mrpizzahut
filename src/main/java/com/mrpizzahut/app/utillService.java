package com.mrpizzahut.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.mrpizzahut.app.hash.aes256;
import com.mrpizzahut.app.pay.settle.settleDto;

public class utillService {
	

	 public static int getTotalPage(int totalCount,int pagesize) {
		 System.out.println("getTotalpages");
		 System.out.println("총 개수"+totalCount);
	        int totalpage=0;
	        totalpage=totalCount/pagesize;
	        if(totalCount%pagesize>0){
	            totalpage++;
	        }
	        System.out.println(totalpage+"전체페이지");
	        if(totalpage==0){
	            totalpage=1;
	        }
	        System.out.println(totalpage+" 전체 페이지");
	        return totalpage;
	}
	    public static Map<String, Object> getStart(int nowPage,int pagesize) {
	    	System.out.println("getPagingStartEnd");
	    	int start=0;
	    	Map<String, Object>map=new HashMap<>();
	    	if(nowPage!=1) {
	    		start=(nowPage-1)*pagesize+1;
			}
			map.put("start", start);
			map.put("end", start+pagesize);
			return map;
		}
	public static boolean checkRole(HttpServletRequest request) {
		System.out.println("checkRole");
		String role=(String)request.getSession().getAttribute("role");
		System.out.println("이계정의 권한은 "+role);
		if(role.equals("admin")) {
			return true;
		}
		return false;
	}
	
	public static String makeUtf8(String param) {
		System.out.println("makeUtf8");
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw utillService.makeRuntimeEX("형식변환에 실패했습니다", "makeUtf8");
		}
	
	}
    public static void doRedirect(HttpServletResponse response,String url,String parm) {
        System.out.println("doRedirect");
        System.out.println(url+"리다이렉트 요청 url");
        try {
            response.sendRedirect(url+parm);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("doRedirect error"+e.getMessage());
        }
    }
	
    public static String aesToNomal(String hash) {
        try {
            byte[] aesCipherRaw2=aes256.decodeBase64(hash);
            return new String(aes256.aes256DecryptEcb(aesCipherRaw2),"UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("복호화 실패");
        }
    }
	 public static settleDto requestToSettleDto(HttpServletRequest request) {
	        System.out.println("requestToSettleDto");
	        settleDto dto=new settleDto();
	        dto.setMchtId(request.getParameter("mchtId"));//상점아이디
	        dto.setOutStatCd(request.getParameter("outStatCd"));          //결과코드
	        dto.setOutRsltCd(request.getParameter("outRsltCd"));          //거절코드
	        dto.setOutRsltMsg( request.getParameter("outRsltMsg"));         //결과메세지
	        dto.setMethod(          request.getParameter("method"));             //결제수단
	        dto.setMchtTrdNo(         request.getParameter("mchtTrdNo"));          //상점주문번호
	        dto.setMchtCustId(request.getParameter("mchtCustId"));         //상점고객아이디
	        dto.setTrdNo(             request.getParameter("trdNo"));              //세틀뱅크 거래번호
	        dto.setTrdAmt(            request.getParameter("trdAmt"));             //거래금액
	        dto.setMchtParam(         request.getParameter("mchtParam"));          //상점 예약필드
	        dto.setAuthDt(            request.getParameter("authDt"));             //승인일시
	        dto.setAuthNo(            request.getParameter("authNo"));             //승인번호
	        dto.setReqIssueDt(     	request.getParameter("reqIssueDt"));       	//채번요청일시
	        dto.setIntMon(            request.getParameter("intMon"));             //할부개월수
	        dto.setFnNm(              request.getParameter("fnNm"));               //카드사명
	        dto.setFnCd(              request.getParameter("fnCd"));               //카드사코드
	        dto.setPointTrdNo(        request.getParameter("pointTrdNo"));         //포인트거래번호
	        dto.setPointTrdAmt(       request.getParameter("pointTrdAmt"));        //포인트거래금액
	        dto.setCardTrdAmt(        request.getParameter("cardTrdAmt"));         //신용카드결제금액
	        dto.setVtlAcntNo(         request.getParameter("vtlAcntNo"));          //가상계좌번호
	        dto.setExpireDt(          request.getParameter("expireDt"));           //입금기한
	        dto.setCphoneNo(          request.getParameter("cphoneNo"));           //휴대폰번호
	        dto.setBillKey(           request.getParameter("billKey"));
	                          
	                                return dto;
	    }
	 
    public static RuntimeException makeRuntimeEX(String message,String methodName) {
         return new RuntimeException("메세지: "+message);
    }
    public static JSONObject makeJson(Boolean flag,String message) {
        JSONObject response=new JSONObject();
        response.put("flag", flag);
        response.put("message", message);
        return response;
    }
    public static boolean checkNull(String s) {
		if(s==null) {
			return true;
		}else if(s.isBlank()) {
			return true;
		}
		return false;
	}
    public static String getEmail(HttpServletRequest request) {
		return (String)request.getSession().getAttribute("email");
	}
    public static String getSettleVBankExpireDate(String expireDate) {
        System.out.println("requestToSettleDto");
        String[] dateAndTime=expireDate.split("T");
        dateAndTime[0]=dateAndTime[0].replace("-", "");
        dateAndTime[1]=dateAndTime[1].replace(":", "");
        dateAndTime[1]=dateAndTime[1].substring(0, 6);
        return dateAndTime[0]+dateAndTime[1];
    }
    public static String getSettleText(String mchtid,String method,String mchtTrdNo,String requestDate,String requestTime,String totalPrice)  {
        return  String.format("%s%s%s%s%s%s%s",mchtid,method,mchtTrdNo,requestDate,requestTime,totalPrice,"ST1009281328226982205");
    }
    public static Map<String,String> getTrdDtTrdTm() {
       System.out.println("getTrdDtTrdTm");
        Timestamp timestamp=Timestamp.valueOf(LocalDateTime.now());
       System.out.println(timestamp+" 전체");
        String[] spl=timestamp.toString().split(" ");
        String trdDt=spl[0].replace("-","");
       System.out.println(trdDt+" 요일");
        String min=LocalDateTime.now().getMinute()+"";
        String second=LocalDateTime.now().getSecond()+"";
        String hour=LocalDateTime.now().getHour()+"";
        if(hour.length()<2){
            hour="0"+hour;
        }
        if(min.length()<2){
            min="0"+min;
        }
        if(second.length()<2){
            second="0"+second;
        }
        String trdTm=hour+min+second;
       System.out.println(trdTm+" 시간");
        Map<String,String>map=new HashMap<>();
        map.put("trdDt", trdDt);
        map.put("trdTm", trdTm);
        return map;
    }
    public static String getRandomNum(int end) {
        String num="";
        Random random=new Random();
        for(int i=0;i<end;i++){
            num+=Integer.toString(random.nextInt(10));
        }
        return num;
    } 
}
