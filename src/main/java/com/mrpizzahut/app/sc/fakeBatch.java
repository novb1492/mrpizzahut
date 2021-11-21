package com.mrpizzahut.app.sc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrpizzahut.app.intenum;
import com.mrpizzahut.app.utillService;

import Daos.productDao;

public class fakeBatch {
	private int defaultFlag=intenum.defaultFlag.getInt();
	private int doneFlag=intenum.doneFlag.getInt();
	
	public void checkNoneVbank(productDao productDao) {
		System.out.println("checkNoneVbank");
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("now", Timestamp.valueOf(LocalDateTime.now()));
		map.put("defaultFlag", defaultFlag);
		List<Map<String, Object>>nonePayVbankOrders=productDao.findByCreatedInVbank(map);
		if(utillService.checkEmpthy(nonePayVbankOrders)) {
			System.out.println("정리할 가상계좌가 없습니다");
			return;
		}
		System.out.println("입금시간이 지난 가상계좌들 "+nonePayVbankOrders);
		for(Map<String, Object>order:nonePayVbankOrders) {
			int dbCount=productDao.findBySizeAndEdgeAndNameInMenu(order);
			System.out.println("이전재고 "+dbCount);
			order.put("count", dbCount+=Integer.parseInt(order.get("OCOUNT").toString()));
			order.put("doneFlag", doneFlag);
			productDao.updateCount(order);
			productDao.updateVbankToCheckDone(order);
		}
		System.out.println("미입금 가상계좌 재고정리 완료");
	}
}
