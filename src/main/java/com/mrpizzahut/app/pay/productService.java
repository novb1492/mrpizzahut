package com.mrpizzahut.app.pay;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.api.kakaopayService;
import com.mrpizzahut.app.pay.settle.settleService;

import Daos.buketDao;
import Daos.couponDao;
import Daos.payDao;

@Service
public class productService {
	 private final int fullProductMin=10;
	 private final int doneFlag=1;


	@Autowired
	private buketDao buketDao;
	@Autowired
	private couponDao couponDao;
	@Autowired
	private payDao payDao;
	@Autowired
	private settleService settleService;
	@Autowired
	private kakaopayService kakaopay;
	
	public JSONObject getPayInfor(tryBuyDto tryBuyDto,HttpServletRequest request) {
		System.out.println("getPayInfor");
		System.out.println("결제요청정보 "+tryBuyDto.toString());
		String email=utillService.getEmail(request);
		if(utillService.checkNull(tryBuyDto.getMobile1())||utillService.checkNull(tryBuyDto.getMobile2())||utillService.checkNull(tryBuyDto.getMobile3())) {
			throw utillService.makeRuntimeEX("핸드폰 번호를 확인해주세요", "getPayInfor");
		}
		String kind=tryBuyDto.getKind();
		if(utillService.checkNull(kind)) {
			throw utillService.makeRuntimeEX("결제수단을 선택해주세요", "getPayInfor");
		}
		List<Map<String,Object>>maps=confrimbuket(tryBuyDto,email);
		System.out.println(""+maps.toString());
		if(kind.equals("card")||kind.equals("vbank")) {
			System.out.println("세틀뱅크");
			return settleService.makeBuyInfor(tryBuyDto, maps, email);
		}else if(kind.equals("kpay")) {
			System.out.println("카카오페이");
			return kakaopay.getKaKaoPayLink(tryBuyDto, maps, email,request);
		}else {
			throw utillService.makeRuntimeEX("지원하지 않는 결제수단입니다", "getPayInfor");
		}
		
	}
	public List<Map<String,Object>> confrimbuket(tryBuyDto tryBuyDto,String email){
		System.out.println("confrimbuket");
		///첫 db 접속 select
		 List<Map<String, Object>>carts=buketDao.findByEmail(email);
			if(carts.isEmpty()) {
				throw utillService.makeRuntimeEX("장바구니가 비었습니다", "getPayInfor");
			}
			System.out.println("장바구니 "+carts.toString());
	        int itemArraySize=carts.size();
	        String[] coupons=tryBuyDto.getcoupon().toString().split("/");
	        for(String s:coupons) {
	        	 System.out.println("쿠폰 "+s);
	        }
	        
	        String itemNames="";
	        int onlyCash=0;
	        int totalCash=0;
	        List<Map<String,Object>>maps=new ArrayList<>();
	        List<String>couponNamesAndCodeNames=new ArrayList<>();
	        int temp=0;
	        int temp2=0;
	        String couponNames="";
	        for(Map<String, Object>map:carts){
	        	System.out.println("조회 "+map.toString());
	        	Map<String, Object>product=payDao.findByPizzaName(map);
	        	String productName=map.get("CMENU").toString();
	        	if(product==null) {
	        		throw utillService.makeRuntimeEX("존재하지 않는 상품입니다"+productName, "confrimbuket");
	        	}
	        	System.out.println("결과 "+product.toString());
	        	int dbcount=Integer.parseInt(product.get("MCOUNT").toString());
	        	System.out.println("남은 재고 "+dbcount);
	        	int requestCount=Integer.parseInt(map.get("CCOUNT").toString());
	        	System.out.println("요청 재고 "+requestCount);
	        	int tempCount=0;
	            for(Map<String, Object>map2:carts){
	            	if(map2.get("CMENU").equals(map.get("CMENU"))){
	                       tempCount+=Integer.parseInt(map2.get("CCOUNT").toString());
	                       if(tempCount>dbcount) {
	                    	   System.out.println("총 요청 수량 초과");
	                    	   throw utillService.makeRuntimeEX("주문 가능 수량을 초과합니다 제품 "+map2.get("CMENU")+"요청수량 "+tempCount+"가능수량 "+dbcount, "confrimbuket");
	                       }
	                }
	                    
	            }
	            String co=null;
	            try {
	            	co=coupons[temp];
				} catch (Exception e) {
					co=null;
				}
	            temp+=1;
	            LinkedHashMap<String,LinkedHashMap<String,Object>>eventmap=new LinkedHashMap<>();
	            confrimCoupon(co,requestCount,eventmap,couponNamesAndCodeNames,productName);
	            System.out.println("최종 쿠폰 정보 "+eventmap.toString());
	            onlyCash=getOnlyCash(Integer.parseInt(product.get("PRICE").toString().replace(",", "")),requestCount,eventmap,30,productName);
	            System.out.println("최종가격"+onlyCash);
	            totalCash+=onlyCash;
	            System.out.println("전체가격 "+totalCash);
	            itemNames+=productName;
	            couponNames+=co;
	            if(temp2<itemArraySize-1){
	                itemNames+=",";
	                couponNames+=",";
	            }
	            temp2+=1;
	            Map<String,Object>result=new HashMap<>();
	            result.put("itemName",productName);
	            result.put("count", requestCount);
	            result.put("price",onlyCash);
	            result.put("coupon", co);
	            result.put("size",map.get("CSIZE"));
	            result.put("edge", map.get("CEDGE"));
	            maps.add(result);
	            if(temp2==itemArraySize){
	                maps.add(getTotalPrice(totalCash,itemNames,tryBuyDto,couponNames));
	            };

	        }
	        return maps;
	    

	}
    private Map<String,Object> getTotalPrice(int totalCash ,String itemNames,tryBuyDto tryBuyDto,String couponNames){
       System.out.println("getTotalPrice");
        Map<String,Object>map=new HashMap<>();

       System.out.println(totalCash+" 지불가격");
        map.put("totalCash", totalCash);
        map.put("itemNames", itemNames);
        if(tryBuyDto.getKind().equals("vbank")){
        	System.out.println("가상계좌 요청 입급일 생성");
            map.put("expireDate", getVbankExpriedDate());
        }
        map.put("couponNames", couponNames);
        map.put("phone", tryBuyDto.getMobile1()+tryBuyDto.getMobile2()+tryBuyDto.getMobile3());
        return map;
    }
    private String getVbankExpriedDate() {
        System.out.println("getVbankExpriedDate");
         return utillService.getSettleVBankExpireDate(LocalDateTime.now().plusMinutes(fullProductMin).toString());
    }
    private void confrimCoupon(String couponName,int count,LinkedHashMap<String,LinkedHashMap<String,Object>>eventmap,List<String>couponNamesAndCodeNames,String productName){
        System.out.println("confrimCoupon");
        System.out.println("전체 쿠폰 배열"+couponNamesAndCodeNames);
        boolean flag=utillService.checkNull(couponName);
        if(flag){
            for(int i=0;i<count;i++){
                LinkedHashMap<String,Object>map=new LinkedHashMap<>();
                    map.put("couponaction","minus");
                    map.put("couponnum",0);
                    eventmap.put("coupon"+i, map);
            }
            System.out.println("쿠폰이 없습니다");
            return;
        }
        System.out.println("쿠폰 존재");
        String[] splite=couponName.split(",");
        if(splite.length>count){
        	System.out.println("쿠폰 개수 판수보다 초과");
            throw utillService.makeRuntimeEX("주문 개수보다 쿠폰 개수가 많습니다 제품명 "+productName, "getTotalPrice");
        }
        int temp=0;
            for(String s:splite){
            	System.out.println("조회쿠폰 "+s);
            	Map<String, Object>coupon=couponDao.findByCouponName(s);
            	if(coupon==null) {
            		throw utillService.makeRuntimeEX("존재하지 않는 쿠폰 "+s, "confrimCoupon");
            	}
            	System.out.println("쿠폰 정보"+ coupon.toString());
                LinkedHashMap<String,Object>map=new LinkedHashMap<>();
                if(LocalDateTime.now().isAfter(Timestamp.valueOf(coupon.get("COEXPIRED").toString()).toLocalDateTime())){
                    throw utillService.makeRuntimeEX(s+"기간이 지난 쿠폰입니다", "getTotalPriceAndOther");
                }else if(Integer.parseInt(coupon.get("USEDFLAG").toString())!=0){
                    throw utillService.makeRuntimeEX(s+"이미 사용된 쿠폰입니다", "getTotalPriceAndOther");
                }
                if(couponNamesAndCodeNames.contains(s)){
                    throw utillService.makeRuntimeEX("같은쿠폰이 중복으로 발견되었습니다"+s, "confrimCoupon");
                }
                couponNamesAndCodeNames.add(s);
                map.put("couponaction",coupon.get("COKIND"));
                map.put("couponnum",coupon.get("COPRICE"));
                eventmap.put("coupon"+temp, map);
                temp+=1;
            }
            if(temp<count){
                for(int i=temp;i<count;i++){
                    LinkedHashMap<String,Object>map=new LinkedHashMap<>();
                    map.put("couponaction","minus");
                    map.put("couponnum",0);
                    eventmap.put("coupon"+i, map);
                }
            }
            System.out.println("쿠폰액션 담기완료");
    }
    private int getOnlyCash(int  price,int count,LinkedHashMap<String,LinkedHashMap<String,Object>>eventmap,int maxDiscountPercent,String productName) {
        System.out.println("getOnlyCash");
        System.out.println("원가 "+price);
        int tempPrice=0;
        try {
            for(int i=0;i<count;i++){
                String couponAction=(String)eventmap.get("coupon"+i).get("couponaction");
                System.out.println("할인 종류"+couponAction);
                int couponNum=Integer.parseInt(eventmap.get("coupon"+i).get("couponnum").toString());
                System.out.println("할인퍼센트 아니면 금액"+couponNum);
                double totalDiscountPercent=0.00;
                if(couponAction.equals("per")){
                    System.out.println("퍼센트");
                    totalDiscountPercent=couponNum;
                }else if(couponAction.equals("minus")) {
                	 totalDiscountPercent=(double)couponNum/price*100;
                }else{
                    throw utillService.makeRuntimeEX("지원하는 할인방법이 아닙니다", "getOnlyCash");
                }
                System.out.println(totalDiscountPercent+"할인 페센트");
                if(maxDiscountPercent<totalDiscountPercent){
                    throw utillService.makeRuntimeEX(productName+"상품은 최대 "+maxDiscountPercent+"%까지 할인 가능합니다 현재 "+totalDiscountPercent+"%", "getOnlyCash");
                }
                if(totalDiscountPercent>0.0){
                    tempPrice+=price-price*(totalDiscountPercent*0.01);
                }else{
                    tempPrice+=price;
                }
            }
            System.out.println(tempPrice+" 할인가격");
            return tempPrice;
        } catch (RuntimeException e) {
            throw utillService.makeRuntimeEX(e.getMessage(), "getOnlyCash");
        }catch (Exception e) {
            throw utillService.makeRuntimeEX("금액계산에 실패했습니다", "getOnlyCash");
        }
    }
    public void minusProductCount(String mchtTrdNo) {
		System.out.println("minusProductCount");
		List<Map<String, Object>>maps=payDao.OrderFindByMchtTrdNo(mchtTrdNo);
		System.out.println("결제 요청 제품 정보 "+maps.toString());
		for(Map<String, Object>map:maps) {
			Map<String, Object>map2=new HashMap<String, Object>();
			map2.put("CEDGE", map.get("OEDGE"));
			map2.put("CSIZE", map.get("OSIZE"));
			map2.put("CMENU", map.get("ONAME"));
			Map<String, Object>map3=payDao.findByPizzaName(map2);
			System.out.println("현재 제품 정보 "+map3.toString());
			int dbCount=Integer.parseInt(map3.get("MCOUNT").toString());
			dbCount-=Integer.parseInt(map.get("OCOUNT").toString());
			if(dbCount<0) {
				throw utillService.makeRuntimeEX(map.get("ONAME")+"상품의 재고가 부족합니다", "minusProductCount");
			}
			map2.put("count", dbCount);
			payDao.orderUpdateCount(map2);
			
		}
		System.out.println("재고 유효성 통과");
	}
    public void doneCoupon(String coupon,String email,String mchtTrdNo) {
		System.out.println("doneCoupon");
		String[] coupons=coupon.split(",");
		for(String s: coupons) {
			if(!s.equals("null")) {
				System.out.println("쿠폰 조회"+s);
				Map<String, Object>map=couponDao.findByCouponName(s);
				System.out.println("쿠폰 정보 "+map.toString());
				 if(LocalDateTime.now().isAfter(Timestamp.valueOf(map.get("COEXPIRED").toString()).toLocalDateTime())){
	                    throw utillService.makeRuntimeEX(s+"기간이 지난 쿠폰입니다", "getTotalPriceAndOther");
	              }else if(Integer.parseInt(map.get("USEDFLAG").toString())!=0){
	                    throw utillService.makeRuntimeEX(s+"이미 사용된 쿠폰입니다", "getTotalPriceAndOther");
	              }
				 map.put("mchtTrdNo", mchtTrdNo);
				 map.put("email", email);
				 map.put("doneFlag", doneFlag);
				 map.put("doneDate", Timestamp.valueOf(LocalDateTime.now()));
				 map.put("name", s);
				 couponDao.updateDone(map);
			}
		}
		System.out.println("쿠폰유효성통과");
	}
	

}
