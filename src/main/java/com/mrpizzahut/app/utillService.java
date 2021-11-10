package com.mrpizzahut.app;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

public class utillService {
	
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
