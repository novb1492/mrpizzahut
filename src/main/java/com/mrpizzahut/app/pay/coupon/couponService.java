package com.mrpizzahut.app.pay.coupon;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public void getAllCoupon(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("getAllCoupon");
		int page=Integer.parseInt(request.getParameter("page"));
		String keyword=request.getParameter("keyword");
		List<Map<String, Object>>coupons=getAllCoupon(keyword, page);
		System.out.println("제품 조회"+coupons.toString());
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
	public void inserCoupon(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("inserCoupon");
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
		couponDao.insertCoupon(coupon);
		
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
		}else {
			throw utillService.makeRuntimeEX("쿠폰 할인방식은 per/minus입니다 현재 "+action, "confrimActionAndPrice");
		}
		
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
