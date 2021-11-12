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
	private final int doneFlag=1;
	
	@Autowired
	private payDao payDao;
	
	public Map<String, Object> selectByMchtTrdNo(String mchtTrdNo,String buykind,String email) {
		System.out.println("selectByMchtTrdNo");
		System.out.println("조회 거래번호 "+mchtTrdNo);
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("email", email);
		map.put("mchtTrdNo", mchtTrdNo);
		if(buykind.equals("card")) {
			System.out.println("카드조회");
			return payDao.cardFindByMchtTrdNo(mchtTrdNo);
		}else if(buykind.equals("vbank")) {
			System.out.println("가상계좌 조회");
			return null;
		}else if(buykind.equals("kpay")) {
			System.out.println("카카오페이 조회");
			return payDao.kpayFindByyMchtTrdNo(map);
		}else {
			throw utillService.makeRuntimeEX("존재하지 않는 거래테이블 입니다", "selectByMchtTrdNo");
		}
	}
	public void updateDonFlag(Map<String, Object>map,String buykind) {
		System.out.println("updateDonFlag");
		if(buykind.equals("card")) {
			System.out.println("카드조회");
			 payDao.updateCardDonflag(map);
		}else if(buykind.equals("vbank")) {
			System.out.println("가상계좌 조회");
			 
		}else if(buykind.equals("kpay")) {
			System.out.println("카카오페이 조회");
			 payDao.kpayUpdateDoneFlag(map);
		}else {
			throw utillService.makeRuntimeEX("존재하지 않는 거래테이블 입니다", "selectByMchtTrdNo");
		}
		System.out.println(buykind+"테이블 doneFlag완료");
		
	}
	public void updateOrderDoneFlag(String email,String mchtTrdNo) {
		System.out.println("updateOrderDoneFlag");
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("email", email);
		map.put("mchtTrdNo", mchtTrdNo);
		map.put("doneFlag", doneFlag);
		map.put("doneDate", Timestamp.valueOf(LocalDateTime.now()));
		payDao.updateOrderDoneFlag(map);
	}
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
