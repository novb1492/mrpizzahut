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




@Controller
public class controller {
	private static final Logger logger = LoggerFactory.getLogger(controller.class);

	@Autowired
	private buketService buketService;

	
	
	@RequestMapping(value = "/buket", method = RequestMethod.GET)
	public String goBuket(HttpServletRequest request,HttpServletResponse response,Model model) {
		logger.info("goBuket");
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
		if(flag) {
			System.out.println("결제성공");
			String productNames=request.getParameter("productNames");
			productNames=productNames.replace("%2C", ",");
			model.addAttribute("productNames",productNames);
			model.addAttribute("price", request.getParameter("price"));
		}else {
			System.out.println("결제실패");
			model.addAttribute("message", request.getParameter("message"));
		}
		model.addAttribute("flag", flag);
		model.addAttribute("buykind", request.getParameter("buykind"));


		return"/orderPages/donePay";
	}
}
