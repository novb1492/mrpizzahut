package com.mrpizzahut.app.pay;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
		try {
			for(Map<String, Object>map:maps) {
				map.put("mchtTrdNo", mchtTrdNo);
				map.put("email", email);
				map.put("method", method);
				String[] counpons=(String[])map.get("coupon");
				for(String s:counpons) {
					System.out.println(s);
				}
				map.put("coupon", "test");
				map.put("created",Timestamp.valueOf(LocalDateTime.now()));
				payDao.insert(map);
				throw new RuntimeException();
			}
		} catch (Exception e) {
			throw utillService.makeRuntimeEX("tt", "insertOrder");
		}
	
	}
}
