package com.mrpizzahut.app.pay.coupon;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mrpizzahut.app.utillService;

import Daos.couponDao;

@Service
public class couponService {
	
	private final int pageSize=10;
	
	@Autowired
	private couponDao couponDao;
	
	public JSONObject deleteCoupon(HttpServletRequest request) {
		System.out.println("deleteCoupon");
		int conum=Integer.parseInt(request.getParameter("conum"));
		couponDao.deleteByConum(conum);
		return utillService.makeJson(true, "쿠폰을 삭제했습니다");
	}
	
	public void getCoupon(HttpServletRequest request,Model model) {
		System.out.println("getCoupon");
		int conum=Integer.parseInt(request.getParameter("conum"));
		Map<String, Object>coupon=Optional.ofNullable(couponDao.findByConum(5000)).orElseThrow(()->utillService.makeRuntimeEX("쿠폰이 존재하지 않습니다", ""));
		String created= coupon.get("COEXPIRED").toString().replace(" ", "T");
		int usedFlag=Integer.parseInt(coupon.get("USEDFLAG").toString());
		String susedFlag="사용된 쿠폰입니다";
		if(usedFlag!=1) {
			susedFlag="미사용 쿠폰입니다";
		}
		coupon.put("usedFlag", susedFlag);
		coupon.put("COEXPIRED",created.substring(0, 16));
		System.out.println("쿠폰 정보 "+coupon.toString());
		model.addAttribute("coupon", coupon);
	}
	public void getAllCoupon(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("getAllCoupon");
		int page=Integer.parseInt(request.getParameter("page"));
		String keyword=request.getParameter("keyword");
		List<Map<String, Object>>coupons=getAllCoupon(keyword, page);
		System.out.println("제품 조회"+coupons.toString());
		if(utillService.checkEmpthy(coupons)) {
			throw utillService.makeRuntimeEX("검색이 존재하지 않습니다", "getAllCoupon");
		}
		int totalCount=Integer.parseInt(coupons.get(0).get("TOTALCOUNT").toString());
		int totalPage=utillService.getTotalPage(totalCount, pageSize);
		System.out.println("전체페이지 "+totalPage);
		if(page>totalPage) {
			throw utillService.makeRuntimeEX("마지막 페이지입니다", "getAllcoupons");
		}
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("coupons", coupons);
	}
	private List<Map<String, Object>> getAllCoupon(String keyword,int page) {
		System.out.println("getAllCoupon");
		if(utillService.checkNull(keyword)) {
			System.out.println("키워드없는 요청");
			return couponDao.findAll(utillService.getStart(page, pageSize));
		}else {
			System.out.println("검색요청");
			Map<String, Object>search=utillService.getStart(page, pageSize);
			search.put("keyword", keyword);
			return couponDao.findAllByKey(search);
		}
	}
	public JSONObject inserCoupon(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("inserCoupon");
		String scope=request.getParameter("scope");
		String title=request.getParameter("title");
		String action=request.getParameter("action");
		String price=request.getParameter("price");
		Timestamp expireDate=confrimExpriDate(request.getParameter("expireDate"));
		confrimActionAndPriceAndTitle(action, price,title);
		Map<String, Object>coupon=new HashMap<String, Object>();
		coupon.put("title", title);
		coupon.put("action", action);
		coupon.put("price", price);
		coupon.put("expireDate", expireDate);
		coupon.put("created", Timestamp.valueOf(LocalDateTime.now()));
		String message="쿠폰 등록이 완료되었습니다";
		 if(scope.equals("insert")) {
		    	System.out.println("쿠폰등록 시도");
		    	couponDao.insertCoupon(coupon);
		    }else if(scope.equals("update")) {
		    	System.out.println("메뉴수정 시도");
		    	int flag=Integer.parseInt(request.getParameter("flag"));
		    	confrimFlag(flag);
		    	coupon.put("flag", flag);
		    	coupon.put("conum", request.getParameter("conum"));
		    	couponDao.updateCoupon(coupon);
		    	message="쿠폰 수정 성공";
		    }else {
		    	return utillService.makeJson(false, "지원하지 않는 기능입니다");
		    }
		return utillService.makeJson(true, message);
		
	}
	private void confrimFlag(int flag) {
		System.out.println("confrimFlag");
		if(flag>1) {
			throw utillService.makeRuntimeEX("사용여부는 0 아니면 1입니다 현재 "+flag, "confrimFlag");
		}
		System.out.println("플래그 유효성 통과");
	}
	private void confrimActionAndPriceAndTitle(String action,String price,String title) {
		System.out.println("confrimActionAndPrice");
		if(utillService.checkNull(title)) {
			throw utillService.makeRuntimeEX("쿠폰 이름을 적어주세요", "confrimActionAndPrice");
		}else if(utillService.checkNull(action)) {
			throw utillService.makeRuntimeEX("쿠폰 할인 방식이 빈칸입니다", "confrimActionAndPrice");
		}else if(utillService.checkNull(price)) {
			throw utillService.makeRuntimeEX("할인금액이 빈칸입니다", "confrimActionAndPrice");
		}
		int iprice=Integer.parseInt(price);
		if(iprice<=0) {
			throw utillService.makeRuntimeEX("할인금액은 최소 1원입니다 현재 "+iprice, "confrimActionAndPrice");
		}
		if(action.equals("per")) {
			if(iprice>100) {
				throw utillService.makeRuntimeEX("할인금액은 최대 100프로입니다 현재"+iprice, "confrimActionAndPrice");
			}
		}else if(action.equals("minus")) {
			System.out.println("마이너스 쿠폰");
		}else {
			throw utillService.makeRuntimeEX("쿠폰 할인방식은 per/minus입니다 현재 "+action, "confrimActionAndPrice");
		}
		System.out.println("할인 방식 유효성 통과");
		
	}
	private Timestamp confrimExpriDate(String sExpireDate) {
		System.out.println("confrimExpriDate");
		if(utillService.checkNull(sExpireDate)) {
			throw utillService.makeRuntimeEX("쿠폰 종료 기간을 설정해주세요", "confrimExpriDate");
		}
		Timestamp expireDate=Timestamp.valueOf(sExpireDate.replace("T", " ")+":00");
		if(LocalDateTime.now().isAfter(expireDate.toLocalDateTime())) {
			throw utillService.makeRuntimeEX("쿠폰 종료일자는 오늘 보다 커야합니다", "confrimExpriDate");
		}
		System.out.println("쿠폰 기간 유효성 통과");
		return expireDate;
	}
}
