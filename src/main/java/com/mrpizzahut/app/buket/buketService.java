package com.mrpizzahut.app.buket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.mrpizzahut.app.utillService;

import Daos.buketDao;

@Service
public class buketService {
	
	private static final Logger logger = LoggerFactory.getLogger(buketService.class);
	
	@Autowired
	private buketDao buketDao;
	public boolean getCartByEmail(String email,Model model) {
		logger.info("getCartByEmail");
		List<Map<String, Object>>maps=buketDao.findByEmail(email);
		if(maps.isEmpty()) {
			return true;
		}
		int price=20000;
		int totalPrice=0;
		for(Map<String, Object>map:maps) {
			int countAndPrice=price*Integer.parseInt(map.get("CCOUNT").toString());
			map.put("img", "http://cdn.mrpizza.co.kr/2011/uploadV1/product_new/2021630152735817.jpg");
			map.put("price", countAndPrice);
			totalPrice+=countAndPrice;
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("maps",maps);
		return false;
	}
	public JSONObject changeCount(JSONObject jsonObject) {
		logger.info("changeCount");
		System.out.println(jsonObject);
		int bid=Integer.parseInt(jsonObject.get("bid").toString());
		Map<String, Object>map=buketDao.findByBid(bid);
		logger.info("장바구니 조회결과" +map.toString());
		String dbEmail=(String)map.get("CEMAIL");
		int num=Integer.parseInt(jsonObject.get("num").toString());
		if(!dbEmail.equals("kim@kim.com")) {
			throw utillService.makeRuntimeEX("장바구니 이메일 불일치", "changeCount");
		}/*else if(num!=1||num!=1) {
			throw utillService.makeRuntimeEX("카운트 수량을 조작하였습니다", "changeCount");
		}*/
		int dbCount=Integer.parseInt(map.get("CCOUNT").toString());
		dbCount +=num;
		
		if(dbCount<=0) {
			buketDao.deleteById(bid);
			return utillService.makeJson(false, "0");
		}else {
			Map<String, Object>map2=new HashMap<String, Object>();
			map2.put("count", dbCount);
			map2.put("bid", bid);
			buketDao.updateCount(map2);
		}
		JSONObject response=new JSONObject();
		response.put("flag", true);
		response.put("count", dbCount);
		response.put("price", dbCount*20000);
		return response;
	}
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteCart(deleteCartDto dto,String loginEmail) {
		logger.info("deleteCart");
		List<Map<String, Object>>maps=new ArrayList<Map<String,Object>>();
		List<Integer>integers=dto.getArr();
		for(int i:integers) {
			Map<String, Object>map=buketDao.findByBid(i);
			maps.add(map);
		}
		for(Map<String, Object>map:maps) {
			String dbemail=(String)map.get("CEMAIL");
			if(!dbemail.equals(loginEmail)) {
				throw utillService.makeRuntimeEX("장바구니 이메일이 일치하지 않습니다", "deleteCart");
			}
			
			buketDao.deleteById(Integer.parseInt(map.get("CNUM").toString()));
		}
		return utillService.makeJson(true,"장바구니삭제");
		
	}
	public void totalPriceAndUser(String emil,Model model) {
		logger.info("totalPriceAndUser");
		Map<String, Object>map=buketDao.findUser(emil);
		if(map.isEmpty()) {
			throw utillService.makeRuntimeEX("존재하지 않는 회원입니다", "totalPriceAndUser");
		}
		String mobile=map.get("MOBILE").toString();
		map.put("MOBILE1", mobile.substring(0, 3));
		map.put("MOBILE2", mobile.substring(3, 7));
		map.put("MOBILE3", mobile.substring(7, 11));
		model.addAttribute("user", map);
		getCartByEmail(emil, model);

	}
}
