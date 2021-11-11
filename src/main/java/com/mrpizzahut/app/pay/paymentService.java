package com.mrpizzahut.app.pay;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrpizzahut.app.utillService;

import Daos.payDao;


@Service
public class paymentService {
	
	@Autowired
	private payDao payDao;
	

	public void insertOrder(List<Map<String,Object>>maps,String mchtTrdNo,String email,String method) {
		System.out.println("insertOrder");
			int temp=0;
			int size=maps.size();
			for(Map<String, Object>map:maps) {
				if(temp==size-1) {
					break;
				}
				map.put("mchtTrdNo", mchtTrdNo);
				map.put("email", email);
				map.put("method", method);
				
				String counpon=(String)map.get("coupon");
				System.out.println(counpon);
				if(counpon==null) {
					map.put("coupon", "emthy");
				}else {
					map.put("coupon", counpon);
				}
				map.put("created",Timestamp.valueOf(LocalDateTime.now()));
				payDao.insert(map);
				temp+=1;
			}
		
	
	}
	public void insertPayment(List<Map<String,Object>>maps,String mchtTrdNo,String email,String method) {
		System.out.println("insertPayment");
		if(method.equals("card")) {
			System.out.println("카드 결제 등록");
			insertCard(maps.get(maps.size()-1), mchtTrdNo, email, method);
		}else if(method.equals("vbank")) {
			System.out.println("가상계좌 등록");
		}else if(method.equals("kpay")) {
			System.out.println("카카오페이 등록");
		}else {
			throw utillService.makeRuntimeEX("지원하지 않는 결제수단입니다", "insertPayment");
		}
	}
	private void insertCard(Map<String, Object>infor,String mchtTrdNo,String email,String method) {
		System.out.println("insertCard");
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("email"	, email);
		map.put("mchtTrdNo", mchtTrdNo);
		map.put("method", method);
		map.put("price", infor.get("totalCash"));
		map.put("created", Timestamp.valueOf(LocalDateTime.now()));
		map.put("doneFlag", 0);
		map.put("phone", infor.get("phone"));
		payDao.insertCard(map);
	}
}
