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
import com.mrpizzahut.app.order.orderService;
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
	@Autowired
	private orderService orderService;

	
	
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
			System.out.println("????????????");
			String productNames=request.getParameter("productNames");
			productNames=productNames.replace("%2C", ",");
			model.addAttribute("productNames",productNames);
			model.addAttribute("price", request.getParameter("price"));
			if(buykind.equals("vbank")) {
				System.out.println("???????????? ????????? ??????");
				model.addAttribute("vbanknum", request.getParameter("vbanknum"));
				model.addAttribute("expireDate", request.getParameter("expireDate"));
			}
		}else {
			System.out.println("????????????");
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
		if(scope.equals("????????????")) {
			return "/admin/adminMenu";
		}else if(scope.equals("??????????????????")) {
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
		if(scope.equals("????????????")) {
			return "/admin/insertCoupon";
		}else if(scope.equals("??????????????????")) {
			couponService.getAllCoupon(request, response,model);
			return "/admin/updateCoupon";
		}else {
			couponService.getCoupon(request, model);
			return "/admin/showCoupon";
		}
	}
	@RequestMapping(value = "/admin/order",method = RequestMethod.GET)
	public String showOrders(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("showOrders");
		String detail=request.getParameter("detail");
		if(detail.equals("all")) {
			orderService.getAllOrders(request, model);
			return "/admin/showOrders";
		}else {
			orderService.getOrder(request,model);
			return "/admin/showOrder";
		}

	}
	@RequestMapping(value = "/admin/sales",method = RequestMethod.GET)
	public String showSales(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("showSales");
		orderService.getAllPrice(request, model);
		return "/admin/showPay";
	}

	
	

}
