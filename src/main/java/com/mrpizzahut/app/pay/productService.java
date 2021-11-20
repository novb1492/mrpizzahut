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
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.api.kakao.kakaopayService;
import com.mrpizzahut.app.file.fileService;
import com.mrpizzahut.app.pay.settle.settleService;

import Daos.buketDao;
import Daos.couponDao;
import Daos.payDao;
import Daos.productDao;
import oracle.sql.TIMESTAMP;

@Service
public class productService {
	 private final int fullProductMin=10;
	 private final int doneFlag=1;
	 private final int pageSize=10;


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
	@Autowired
	private fileService fileService;
	@Autowired
	private productDao productDao;
	
	public JSONObject deleteProduct(HttpServletRequest request) {
		System.out.println("deleteProduct");
		int mnum=Integer.parseInt(request.getParameter("mnum"));
		productDao.deleteProduct(mnum);
		return utillService.makeJson(true, "제품이 삭제 되었습니다");
	}
	public void getByMnum(HttpServletRequest request,Model model) {
		System.out.println("getByMnum");
		int mnum=Integer.parseInt(request.getParameter("mnum"));
		Map<String, Object>product=productDao.findByMnum(mnum);
		System.out.println("조회한 제품 정보 "+product);
		model.addAttribute("product", product);
	}
	
	public void getAllProducts(HttpServletRequest request,Model model) {
		System.out.println("getAllProducts");
		int page=Integer.parseInt(request.getParameter("page"));
		String keyword=request.getParameter("keyword");
		List<Map<String, Object>>products=getProducts(keyword, page);
		System.out.println("제품 조회"+products.toString());
		int totalCount=Integer.parseInt(products.get(0).get("TOTALCOUNT").toString());
		int totalPage=utillService.getTotalPage(totalCount, pageSize);
		System.out.println("전체페이지 "+totalPage);
		if(page>totalPage) {
			throw utillService.makeRuntimeEX("마지막 페이지입니다", "getAllProducts");
		}
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("products", products);
	}
	private List<Map<String, Object>> getProducts(String keyword,int page) {
		System.out.println("getProducts");
		if(utillService.checkNull(keyword)) {
			System.out.println("키워드없는 요청");
			return productDao.findAll(utillService.getStart(page, pageSize));
		}else {
			System.out.println("검색요청 "+keyword);
			Map<String, Object>search=utillService.getStart(page, pageSize);
			search.put("keyword", keyword);
			return productDao.findAllByKey(search);
		}
	}
	public JSONObject processMenu(MultipartHttpServletRequest request) {
		System.out.println("processMenu");
		return insertMenu(request);
		
	}
	private JSONObject insertMenu(MultipartHttpServletRequest request) {
		System.out.println("insertMenu");
		JSONObject imgName=fileService.uploadImg(request);
		String img=imgName.get("url").toString();
		System.out.println("사진 업로드 경로"+img);
		String title=request.getParameter("title");
		String stitle=request.getParameter("stitle");
		String ititle=request.getParameter("ititle");
		String size=request.getParameter("size");
		String edge=request.getParameter("edge");
		String text=request.getParameter("text");
		String price=request.getParameter("price");
		System.out.println("설명"+text);
		int count=Integer.parseInt(request.getParameter("count"));
		confrimSize(size);
		confrimEdge(edge);
		confrimPriceCountText(price,count,text);
		Map<String, Object>product=new HashMap<String, Object>();
		product.put("img", img);
		product.put("title", title);
				product.put("stitle", stitle);
				product.put("ititle", ititle);
				product.put("text", text);
				product.put("price", price);
				product.put("size", size);
				product.put("edge", edge);
				product.put("count", count);
				System.out.println("저장 예정 제품정보"+product.toString());
	    String scope=request.getParameter("scope");
	    String message="제품등록 성공";
	    if(scope.equals("insert")) {
	    	System.out.println("메뉴등록 시도");
	    	productDao.insertProduct(product);
	    }else if(scope.equals("update")) {
	    	System.out.println("메뉴수정 시도");
	    	product.put("mnum", request.getParameter("mnum"));
	    	productDao.updateProduct(product);
	    	message="제품 수정 성공";
	    }else {
	    	return utillService.makeJson(false, "지원하지 않는 기능입니다");
	    }	
	    request.getSession().removeAttribute("imgs");
		return utillService.makeJson(true, message);
	}
	
	private void confrimPriceCountText(String price,int count,String text) {
		System.out.println("confrimPrice");
		if(utillService.checkNull(price)) {
			throw utillService.makeRuntimeEX("금액이 빈칸입니다", "confrimPrice");
		}else if(price.length()<=3) {
			System.out.println("천원 이하 제품");
			return;
		}else if(!price.contains(",")) {
			throw utillService.makeRuntimeEX("금액 구분문자는 ,입니다 현재"+price, "confrimPrice");
		}
		if(count<=0) {
			throw utillService.makeRuntimeEX("일일 판매최대 수량을 입력해주세요", "confrimPrice");
		}else if(utillService.checkNull(text)) {
			throw utillService.makeRuntimeEX("제품 설명이 없습니다", "confrimPrice");

		}
		System.out.println("금액 유효성 검사 통과");
	}
	private void confrimSize(String size) {
		System.out.println("confrimSize");
		if(utillService.checkNull(size)) {
			System.out.println("사이즈가 없는 상품");
			return;
		}

			if(size.length()<1||size.length()>2) {
				throw utillService.makeRuntimeEX("사이즈는 1~2글자입니다 잘못된 사이즈"+size, "confrimSize");
			}
			char a=size.charAt(0);
			int aa=(int)a;
			if(aa<65||aa>90) {
				throw utillService.makeRuntimeEX("사이즈는 대문자만 가능합니다 잘못된 사이즈"+size, "confrimSize");
			}	
			System.out.println("사이즈 유효성 통과");

	}
	private  void confrimEdge(String edge) {
		System.out.println("confrimEdge");
		/*if(utillService.checkNull(edge)) {
			throw utillService.makeRuntimeEX("엣지를 입력해주세요", "confrimSize");
		}*/
		System.out.println("엣지유효성 통과");
	}
	
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
			map.put("CEDGE", map.get("OEDGE"));
			map.put("CSIZE", map.get("OSIZE"));
			map.put("CMENU", map.get("ONAME"));
			Map<String, Object>map3=payDao.findByPizzaName(map);
			System.out.println("현재 제품 정보 "+map3.toString());
			int dbCount=Integer.parseInt(map3.get("MCOUNT").toString());
			dbCount-=Integer.parseInt(map.get("OCOUNT").toString());
			if(dbCount<0) {
				throw utillService.makeRuntimeEX(map.get("ONAME")+"상품의 재고가 부족합니다", "minusProductCount");
			}
			map.put("count", dbCount);
			payDao.orderUpdateCount(map);
			map.put("doneFlag",doneFlag);
			map.put("doneDate", Timestamp.valueOf(LocalDateTime.now()));
			payDao.updateOrderDoneFlag(map);
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
