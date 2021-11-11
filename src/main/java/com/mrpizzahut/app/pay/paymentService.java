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

	private final String cid="TC0ONETIME";
	
	
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
			insertVbank(maps.get(maps.size()-1), mchtTrdNo, email, method);
		}else if(method.equals("kpay")) {
			System.out.println("카카오페이 등록");
			insertKpay(maps.get(maps.size()-1), mchtTrdNo, email, method);
		}else {
			throw utillService.makeRuntimeEX("지원하지 않는 결제수단입니다", "insertPayment");
		}
	}
	private void insertCard(Map<String, Object>infor,String mchtTrdNo,String email,String method) {
		System.out.println("insertCard");
		infor.put("email"	, email);
		infor.put("mchtTrdNo", mchtTrdNo);
		infor.put("method", method);
		infor.put("price", infor.get("totalCash"));
		infor.put("created", Timestamp.valueOf(LocalDateTime.now()));
		infor.put("doneFlag", 0);
		infor.put("phone", infor.get("phone"));
		infor.put("couponNames", infor.get("couponNames"));
		payDao.insertCard(infor);
	}
	private void insertVbank(Map<String, Object>infor,String mchtTrdNo,String email,String method) {
		System.out.println("insertVbank");
		infor.put("created", Timestamp.valueOf(LocalDateTime.now()));
		infor.put("expireDate", infor.get("expireDate"));
		infor.put("mchtTrdNo", mchtTrdNo);
		infor.put("method", method);
		infor.put("price", infor.get("totalCash"));
		infor.put("doneFlag", 0);
		infor.put("phone", infor.get("phone"));
		infor.put("email",email);
		infor.put("couponNames", infor.get("couponNames"));
		payDao.insertVbank(infor);
	}
	private void insertKpay(Map<String, Object>infor,String mchtTrdNo,String email,String method) {
		System.out.println("insertKpay");
		infor.put("cid",cid);
		infor.put("created", Timestamp.valueOf(LocalDateTime.now()));
		infor.put("mchtTrdNo", mchtTrdNo);
		infor.put("email",email);
		infor.put("price", infor.get("totalCash"));
		infor.put("doneFlag", 0);
		infor.put("phone", infor.get("phone"));
		infor.put("couponNames", infor.get("couponNames"));
		payDao.insertKpay(infor);
	}
}
