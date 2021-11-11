package com.mrpizzahut.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mrpizzahut.app.buket.buketService;
import com.mrpizzahut.app.buket.deleteCartDto;
import com.mrpizzahut.app.pay.productService;
import com.mrpizzahut.app.pay.tryBuyDto;
import com.mrpizzahut.app.pay.settle.settleService;

@RestController
public class restController {
	
	private static final Logger logger = LoggerFactory.getLogger(restController.class);
	@Autowired
	private buketService buketService;
	@Autowired
	private productService productService;
	@Autowired
	private settleService settleService;
	
	@RequestMapping(value = "/changeCount", method = RequestMethod.PUT)
	public JSONObject changeCount(@RequestBody JSONObject jsonObject,HttpServletRequest request,HttpServletResponse response) {
		logger.info("changeCount rest");
		return buketService.changeCount(jsonObject);
	}
	@RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
	public JSONObject deleteCart(@RequestBody deleteCartDto dto,HttpServletRequest request,HttpServletResponse response) {
		logger.info("deleteCart rest");
		return buketService.deleteCart(dto,(request.getSession().getAttribute("email").toString()));
	}
	@RequestMapping(value = "/tryOrder", method = RequestMethod.POST)
	public JSONObject tryOrder(@RequestBody tryBuyDto tryBuyDto,HttpServletRequest request,HttpServletResponse response) {
		logger.info("tryOrder rest");
		return productService.getPayInfor(tryBuyDto,utillService.getEmail(request));
	}
	@RequestMapping(value = "/settle/callback", method = RequestMethod.POST)
	public JSONObject confrimSettle(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("confrimSettle");
		return settleService.confrimPayment(request);
	}
	
	

}
