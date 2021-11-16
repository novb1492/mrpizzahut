package com.mrpizzahut.app.order;

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
