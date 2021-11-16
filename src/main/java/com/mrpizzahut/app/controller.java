package com.mrpizzahut.app;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mrpizzahut.app.buket.buketService;
import com.mrpizzahut.app.pay.productService;
import com.mrpizzahut.app.pay.coupon.couponService;




@Controller
public class controller {

	@Autowired
	private buketService buketService;
	@Autowired
	private productService productService;
	@Autowired
	private couponService couponService;

	
	
	@RequestMapping(value = "/buket", method = RequestMethod.GET)
	public String goBuket(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("goBuket"+request.getSession().getAttribute("email"));
		try {
			if(buketService.getCartByEmail(request.getSession().getAttribute("email").toString(), model)) {
				return "/home";
			}
			return "/orderPages/buket";
		} catch (Exception e) {
			return "/home";
		}

	}
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public String goPay(HttpServletRequest request,HttpServletResponse response,Model model) {
		buketService.totalPriceAndUser((String)request.getSession().getAttribute("email"), model);
		return "/orderPages/pay";
	}
	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public String goAddress(HttpServletRequest request,HttpServletResponse response) {
		return "/orderPages/address";
	}
	@RequestMapping(value = "/donePay", method = RequestMethod.GET)
	public String donePay(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("donePay");
		boolean flag=Boolean.parseBoolean(request.getParameter("flag"));
		String buykind=request.getParameter("buykind");
		if(flag) {
			System.out.println("결제성공");
			String productNames=request.getParameter("productNames");
			productNames=productNames.replace("%2C", ",");
			model.addAttribute("productNames",productNames);
			model.addAttribute("price", request.getParameter("price"));
			if(buykind.equals("vbank")) {
				System.out.println("가상계좌 이므로 추가");
				model.addAttribute("vbanknum", request.getParameter("vbanknum"));
				model.addAttribute("expireDate", request.getParameter("expireDate"));
			}
		}else {
			System.out.println("결제실패");
			model.addAttribute("message", request.getParameter("message"));
		}
		model.addAttribute("flag", flag);
		model.addAttribute("buykind", buykind);


		return"/orderPages/donePay";
	}
	@RequestMapping(value = "/admin/home",method =  RequestMethod.GET)
	public String goAdminMain(HttpServletRequest request,HttpServletResponse servletResponse) {
		System.out.println("goAdminMain");
		request.getSession().setAttribute("email", "kim@kim.com");
		request.getSession().setAttribute("role", "admin");
		if(!utillService.checkRole(request)) {
			return "/home";
		}
		return "/admin/adminHome";
	}
	@RequestMapping(value ="/admin/menu",method = RequestMethod.GET)
	public String goAdminMenu(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("goAdminMenu");
		if(!utillService.checkRole(request)) {
			return "/home";
		}
		String scope=request.getParameter("scope");
		if(scope.equals("메뉴등록")) {
			return "/admin/adminMenu";
		}else if(scope.equals("메뉴수정삭제")) {
			productService.getAllProducts(request, model);
			return "/admin/adminMenuUpdate";
		}else {
			productService.getByMnum(request, model);
			return "/admin/adminMenuShow";
		}
	}
	@RequestMapping(value ="/admin/event",method = RequestMethod.GET)
	public String goAdminEvent(HttpServletRequest request, HttpServletResponse response,Model model) {
		System.out.println("goAdminEvent");
		if(!utillService.checkRole(request)) {
			return "/home";
		}
		String scope=request.getParameter("scope");
		if(scope.equals("쿠폰등록")) {
			return "/admin/insertCoupon";
		}else if(scope.equals("쿠폰수정삭제")) {
			couponService.getAllCoupon(request, response,model);
			return "/admin/updateCoupon";
		}else {
			couponService.getCoupon(request, model);
			return "/admin/showCoupon";
		}
	}
	

}
