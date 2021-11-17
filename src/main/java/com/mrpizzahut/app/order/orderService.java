package com.mrpizzahut.app.order;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mrpizzahut.app.utillService;

import Daos.orderDao;

@Service
public class orderService {
	private final int pageSize=10;

	
	@Autowired
	private orderDao orderDao;
	
	public void getAllPrice(HttpServletRequest request,Model model) {
		System.out.println("getAllPrice");
		String year=request.getParameter("year");
		System.out.println("조회연도 "+year);
		int flag=1;
		int requestmonth=Integer.parseInt(request.getParameter("month"));
		System.out.println("조회달 "+requestmonth);
		String productName=request.getParameter("productName");
		productName.replace("%20", " ");
		System.out.println("조회 제품 "+productName);
		if(utillService.checkNull(productName)) {
			productName=null;
		}
		Map<String,Integer>byDayPrice=new LinkedHashMap<String, Integer>();
		Map<Integer, Integer>byMonthPrice=new LinkedHashMap<Integer, Integer>();
		int yp=0;
		int mp=0;
		for(int i=1;i<=12;i++) {
			mp=0;
			for(int ii=1;ii<=31;ii++) {
				Map<String, Object>period=new HashMap<String, Object>();
				int p=0;
				String month=null;
				String day=null;
				if(i<10) {
					month="0"+Integer.toString(i);
				}else {
					month=Integer.toString(i);
				}
				if(ii<10) {
					day="0"+Integer.toString(ii);
				}else {
					day=Integer.toString(ii);
				}
				period.put("start", Timestamp.valueOf(year+"-"+month+"-"+day+" 00:00:00"));
				period.put("end", Timestamp.valueOf(year+"-"+month+"-"+day+" 23:59:59"));
				period.put("flag", flag);
				period.put("productName", productName);
				List<Integer>allPrice=orderDao.findByDate(period);
				System.out.println(i+"월 "+ii+"일 조회 금액 "+allPrice.toString());
				for(int price:allPrice) {
					p+=price;
				}
				if(requestmonth==i) {
					byDayPrice.put(i+"/"+ii, p);
				}
				mp+=p;
			}
			
			byMonthPrice.put(i, mp);
			yp+=mp;
		}
		
		System.out.println("일별 매출 "+byDayPrice.toString());
		System.out.println("월별 매출 "+byMonthPrice.toString());
		System.out.println("연도별 매출 "+yp);
		model.addAttribute("year", yp);
		model.addAttribute("moths", byMonthPrice);
		model.addAttribute("days", byDayPrice);
		
		
	}
	
	public void getAllOrders(HttpServletRequest request,Model model) {
		System.out.println("getAllOrders");
		int page=Integer.parseInt(request.getParameter("page"));
		String keyword=request.getParameter("keyword");
		List<Map<String, Object>>orders=getOrders(keyword, page);
		System.out.println("제품 조회"+orders.toString());
		int totalCount=Integer.parseInt(orders.get(0).get("TOTALCOUNT").toString());
		int totalPage=utillService.getTotalPage(totalCount, pageSize);
		System.out.println("전체페이지 "+totalPage);
		if(page>totalPage) {
			throw utillService.makeRuntimeEX("마지막 페이지입니다", "getAllorders");
		}
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("orders", orders);
	}
	private List<Map<String, Object>> getOrders(String keyword,int page) {
		System.out.println("getorders");
		if(utillService.checkNull(keyword)) {
			System.out.println("키워드없는 요청");
			return orderDao.findAll(utillService.getStart(page, pageSize));
		}else {
			System.out.println("검색요청 "+keyword);
			Map<String, Object>search=utillService.getStart(page, pageSize);
			search.put("keyword", keyword);
			return orderDao.findAllByKey(search);
		}
	}
}
