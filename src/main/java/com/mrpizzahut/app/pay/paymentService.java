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
@Transactional(rollbackFor = Exception.class)
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
}
