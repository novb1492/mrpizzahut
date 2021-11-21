package com.mrpizzahut.app.sc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import Daos.productDao;

public class scService {
	@Autowired
	private productDao productDao;
	
	private fakeBatch fakeBatch=new fakeBatch();
	
	  @Scheduled(fixedDelay = 1000*60) // scheduler 끝나는 시간 기준으로 1000*n 간격으로 실행
	   public void aboutVbank() {
	       System.out.println("aboutVbank");
	       fakeBatch.checkNoneVbank(productDao);
	  }
}
