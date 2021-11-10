package com.mrpizzahut.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mrpizzahut.app.pay.payService;

import Daos.buketDao;

@Controller
public class controller {
	private static final Logger logger = LoggerFactory.getLogger(controller.class);

	@Autowired
	private buketService buketService;
	@Autowired
	private payService payService;
	
	
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
}
