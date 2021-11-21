package com.mrpizzahut.app.order;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.api.kakao.kakaopayService;
import com.mrpizzahut.app.pay.settle.settleService;

import Daos.orderDao;
import Daos.payDao;
import oracle.net.aso.e;

@Service
public class orderService {
	
	private final int pageSize=10;
	private final int cancleFlag=1;
	private final int doneFlag=1;

	
	@Autowired
	private orderDao orderDao;
	@Autowired
	private kakaopayService kakaopayService;
	@Autowired
	private settleService settleService;
	
	@Transactional(rollbackFor = Exception.class)
	public JSONObject cancleOrder(JSONObject jsonObject,HttpServletRequest request) {
		System.out.println("cancleOrder");
		String email=utillService.getEmail(request);
		String role=request.getSession().getAttribute("role").toString();
		int onum=Integer.parseInt(jsonObject.get("onum").toString());
		String mchtTrdNo= jsonObject.get("mchttrdno").toString();
		System.out.println("주문 취소 번호 "+onum);
		Map<String, Object>onumAndMu=new HashMap<String, Object>();
		onumAndMu.put("onum", onum);
		onumAndMu.put("MCHTTRDNO", mchtTrdNo);
		Map<String, Object>orderAndPay=Optional.ofNullable(orderDao.findByMchttrdnoAndOnumJoin(onumAndMu)).orElseThrow(()->utillService.makeRuntimeEX("거래정보가 존재하지 않습니다", "cancleOrder"));
		System.out.println("취소정보 불러오기 성공 "+orderAndPay.toString());
		if(Integer.parseInt(orderAndPay.get("OCANCLEFLAG").toString())!=0) {
			throw utillService.makeRuntimeEX("이미 환불 처리되었던 제품입니다 ", "cancleOrder");
		}else if(!orderAndPay.get("OEMAIL").equals(email)) {
			System.out.println("환불시 이메일 불일치 발생");
			if(role.equals("admin")) {
				System.out.println("관리자가 환불시도");
			}else {
				return utillService.makeJson(false, "관리자도 아닌계정이 이메일이 일치하지 않으므로 환불실패");
			}
		}
		try {
			String coupon=orderAndPay.get("OCOUPONS").toString();
			if(!utillService.checkNull(coupon)) {
				System.out.println("쿠폰이 존재하는 상품 취소시도"+coupon);
				String[] coupons=coupon.split(",");
				orderAndPay.put("doneFlag", 0);
				for(String c:coupons) {
					orderAndPay.put("couponName", c);
					orderDao.updateCouponToDone(orderAndPay);
				}
				System.out.println("쿠폰 살리기 성공");
			}
		} catch (Exception e) {
			System.out.println("쿠폰이 존재 하지 않는 상품 시도");
		}
		
		orderAndPay.put("cancleFlag", cancleFlag);
		orderAndPay.put("cancleDate", Timestamp.valueOf(LocalDateTime.now()));
		orderAndPay.put("onum",onum);
		orderDao.updateOrderCancleFlag(orderAndPay);
		System.out.println("주문 캔슬 플래그 성공");
		int dbCount=orderDao.findByProductName(orderAndPay);
		dbCount+=Integer.parseInt(orderAndPay.get("OCOUNT").toString());
		orderAndPay.put("count", dbCount);
		orderDao.updateProductCount(orderAndPay);
		System.out.println("재고 정리 성공");
		if(orderAndPay.get("OMETHOD").equals("kpay")) {
			System.out.println("카카오로 결제 되었던것");
			if(!kakaopayService.cancleKPAY(orderAndPay)) {
				return utillService.makeJson(false, "환불에 실패했습니다");
			}
		}else {
			System.out.println("세틀뱅크 가야함");
			JSONObject response=settleService.canclePay(orderAndPay);
			if(!(boolean)response.get("flag")) {
				return response;
			}
		
		}
		return utillService.makeJson(true, "환불에 성공하였습니다");
	}
	
	public void getOrder(HttpServletRequest request,Model model) {
		System.out.println("getOrder");
		int onum=Integer.parseInt(request.getParameter("onum"));
		System.out.println("조회 주문 번호 "+onum);
		model.addAttribute("order",  orderDao.findByOnum(onum));
	}
	/*public List<Map<String, Object>> getAllStitleAndProduct() {
		System.out.println("getAllStitleAndProduct");
		 orderDao orderDao2=orderDao;
		List<String>allStitle=orderDao2.getAllStitle();
		System.out.println("모든 stitle"+ allStitle.toString());
		return null;
	}*/
	
	public void getAllPrice(HttpServletRequest request,Model model) {
		System.out.println("getAllPrice");
		String year=request.getParameter("year");
		System.out.println("조회연도 "+year);
		int flag=1;
		int requestmonth=Integer.parseInt(request.getParameter("month"));
		System.out.println("조회달 "+requestmonth);
		String productName=request.getParameter("productName");
		productName.replace("%20", " ");
		System.out.println("조회 제품 "+productName);
		if(utillService.checkNull(productName)) {
			productName=null;
		}
		Map<String,Integer>byDayPrice=new LinkedHashMap<String, Integer>();
		Map<Integer, Integer>byMonthPrice=new LinkedHashMap<Integer, Integer>();
		Map<String,Integer>cByDayPrice=new LinkedHashMap<String, Integer>();
		Map<Integer, Integer>cByMonthPrice=new LinkedHashMap<Integer, Integer>();
		int yp=0;
		int mp=0;
		int cyp=0;
		int cmp=0;
		for(int i=1;i<=12;i++) {
			mp=0;
			cmp=0;
			for(int ii=1;ii<=31;ii++) {
				Map<String, Object>period=new HashMap<String, Object>();
				int p=0;
				int cp=0;
				String month=null;
				String day=null;
				if(i<10) {
					month="0"+Integer.toString(i);
				}else {
					month=Integer.toString(i);
				}
				if(ii<10) {
					day="0"+Integer.toString(ii);
				}else {
					day=Integer.toString(ii);
				}
				period.put("start", Timestamp.valueOf(year+"-"+month+"-"+day+" 00:00:00"));
				period.put("end", Timestamp.valueOf(year+"-"+month+"-"+day+" 23:59:59"));
				period.put("flag", flag);
				period.put("productName", productName);
				List<Map<String, Object>>allPayPrices=orderDao.findByDate(period);
				System.out.println(i+"월 "+ii+"일 거래내역 "+allPayPrices.toString());
				for(Map<String, Object>allpice:allPayPrices) {
					int doneFlag=Integer.parseInt(allpice.get("ODONEFLAG").toString());
					int cancleFlag=Integer.parseInt(allpice.get("OCANCLEFLAG").toString());
					int dbPrice=Integer.parseInt(allpice.get("OPRICE").toString());
					if(doneFlag==1&&cancleFlag==0) {
						p+=dbPrice;
					}else if(cancleFlag!=0) {
						cp+=dbPrice;
					}
				}
				if(requestmonth==i) {
					byDayPrice.put(i+"/"+ii, p);
					cByDayPrice.put(i+"/"+ii, cp);
				}
				mp+=p;
				cmp+=cp;
			}
			
			byMonthPrice.put(i, mp);
			cByMonthPrice.put(i, cmp);
			yp+=mp;
			cyp+=cmp;
		}
		
		System.out.println("일별 매출 "+byDayPrice.toString());
		System.out.println("월별 매출 "+byMonthPrice.toString());
		System.out.println("연도별 매출 "+yp);
		model.addAttribute("year", yp);
		model.addAttribute("months", byMonthPrice);
		model.addAttribute("days", byDayPrice);
		model.addAttribute("cyear", cyp);
		model.addAttribute("cmonths", cByMonthPrice);
		model.addAttribute("cdays", cByDayPrice);
		System.out.println("매출조회 성공");
		
	}
	
	public void getAllOrders(HttpServletRequest request,Model model) {
		System.out.println("getAllOrders");
		int page=Integer.parseInt(request.getParameter("page"));
		String keyword=request.getParameter("keyword");
		List<Map<String, Object>>orders=getOrders(keyword, page);
		System.out.println("제품 조회"+orders.toString());
		int totalCount=Integer.parseInt(orders.get(0).get("TOTALCOUNT").toString());
		int totalPage=utillService.getTotalPage(totalCount, pageSize);
		System.out.println("전체페이지 "+totalPage);
		if(page>totalPage) {
			throw utillService.makeRuntimeEX("마지막 페이지입니다", "getAllorders");
		}
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("orders", orders);
	}
	private List<Map<String, Object>> getOrders(String keyword,int page) {
		System.out.println("getorders");
		if(utillService.checkNull(keyword)) {
			System.out.println("키워드없는 요청");
			return orderDao.findAll(utillService.getStart(page, pageSize));
		}else {
			System.out.println("검색요청 "+keyword);
			Map<String, Object>search=utillService.getStart(page, pageSize);
			search.put("keyword", keyword);
			return orderDao.findAllByKey(search);
		}
	}
}
