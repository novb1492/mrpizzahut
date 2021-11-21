package com.mrpizzahut.app.sc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mrpizzahut.app.intenum;

import Daos.productDao;

public class fakeBatch {
	private int defaultFlag=intenum.defaultFlag.getInt();
	
	public void checkNoneVbank(productDao productDao) {
		System.out.println("checkNoneVbank");
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("now", Timestamp.valueOf(LocalDateTime.now()));
		map.put("defaultFlag", defaultFlag);
		List<Map<String, Object>>nonePayVbankOrders=productDao.findByCreatedInVbank(map);
		System.out.println("입금시간이 지난 가상계좌들 "+nonePayVbankOrders);
		for(Map<String, Object>order:nonePayVbankOrders) {
			Map<String, Object>product=Optional.ofNullable(productDao.findBySizeAndEdgeAndNameInMenu(order)).orElseGet(null);
			System.out.println("재고 정리 대상 정보 "+product.toString());
			if(product!=null) {
				int dbCount=Integer.parseInt(product.get("MCOUNT").toString());
				dbCount+=Integer.parseInt(order.get("OCOUNT").toString());
				
			}
		}
		System.out.println("미입금 가상계좌 재고정리 완료");
	}
}
