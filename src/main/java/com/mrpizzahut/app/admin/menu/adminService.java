package com.mrpizzahut.app.admin.menu;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.file.fileService;

import Daos.productDao;

@Service
public class adminService {
	@Autowired
	private fileService fileService;
	@Autowired
	private productDao productDao;
	
	public JSONObject insertMenu(MultipartHttpServletRequest request) {
		System.out.println("insertMenu");
		JSONObject imgName=fileService.uploadImg(request);
		if(!(boolean)imgName.get("uploaded")) {
			return utillService.makeJson(false, "이미지 업로드에 실패했습니다");
		}
		String img=imgName.get("url").toString();
		System.out.println("사진 업로드 경로"+img);
		String title=request.getParameter("title");
		String stitle=request.getParameter("stitle");
		String ititle=request.getParameter("ititle");
		String size=request.getParameter("size");
		String edge=request.getParameter("edge");
		String text=request.getParameter("text");
		String price=request.getParameter("price");
		System.out.println("설명"+text);
		int count=Integer.parseInt(request.getParameter("count"));
		confrimSize(size);
		confrimEdge(edge);
		confrimPriceCountText(price,count,text);
		Map<String, Object>product=new HashMap<String, Object>();
		product.put("img", img);
		product.put("title", title);
				product.put("stitle", stitle);
				product.put("ititle", ititle);
				product.put("text", text);
				product.put("price", price);
				product.put("size", size);
				product.put("edge", edge);
				product.put("count", count);
				System.out.println("저장 예정 제품정보"+product.toString());
				productDao.insertProduct(product);
		return null;
	}
	
	private void confrimPriceCountText(String price,int count,String text) {
		System.out.println("confrimPrice");
		if(utillService.checkNull(price)) {
			throw utillService.makeRuntimeEX("금액이 빈칸입니다", "confrimPrice");
		}else if(price.length()<=3) {
			System.out.println("천원 이하 제품");
			return;
		}else if(!price.contains(",")) {
			throw utillService.makeRuntimeEX("금액 구분문자는 ,입니다 현재"+price, "confrimPrice");
		}
		if(count<=0) {
			throw utillService.makeRuntimeEX("일일 판매최대 수량을 입력해주세요", "confrimPrice");
		}else if(utillService.checkNull(text)) {
			throw utillService.makeRuntimeEX("제품 설명이 없습니다", "confrimPrice");

		}
		System.out.println("금액 유효성 검사 통과");
	}
	private void confrimSize(String size) {
		System.out.println("confrimSize");
		if(utillService.checkNull(size)) {
			throw utillService.makeRuntimeEX("사이즈를 입력해주세요", "confrimSize");
		}

			if(size.length()<1||size.length()>2) {
				throw utillService.makeRuntimeEX("사이즈는 1~2글자입니다 잘못된 사이즈"+size, "confrimSize");
			}
			char a=size.charAt(0);
			int aa=(int)a;
			if(aa<65||aa>90) {
				throw utillService.makeRuntimeEX("사이즈는 대문자만 가능합니다 잘못된 사이즈"+size, "confrimSize");
			}	
			System.out.println("사이즈 유효성 통과");

	}
	private  void confrimEdge(String edge) {
		System.out.println("confrimEdge");
		if(utillService.checkNull(edge)) {
			throw utillService.makeRuntimeEX("엣지를 입력해주세요", "confrimSize");
		}
		System.out.println("엣지유효성 통과");
	}
}
