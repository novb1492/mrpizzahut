package com.mrpizzahut.app.admin.menu;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.file.fileService;

@Service
public class adminService {
	@Autowired
	private fileService fileService;
	
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
		String[] sizes=confrimSize(size);
		return null;
	}
	private String[] confrimSize(String size) {
		System.out.println("confrimSize");
		if(utillService.checkNull(size)) {
			throw utillService.makeRuntimeEX("사이즈를 입력해주세요", "confrimSize");
		}
		String[] sizes=size.split(",");
		for(String s:sizes) {
			if(s.length()<1||s.length()<2) {
				throw utillService.makeRuntimeEX("사이즈는 1~2글자입니다 잘못된 사이즈"+s, "confrimSize");
			}
			char a=s.charAt(0);
			int aa=(int)a;
			if(aa<65||aa>90) {
				throw utillService.makeRuntimeEX("사이즈는 대문자만 가능합니다 잘못된 사이즈"+s, "confrimSize");
			}	
		}
		System.out.println("사이즈 유효성 검사 통과");
		return sizes;
	}
}
