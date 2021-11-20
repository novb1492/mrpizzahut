package com.mrpizzahut.app.file;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrpizzahut.app.utillService;
import com.mrpizzahut.app.api.aws.awsService;

@Service
public class fileService {
	
	private final String buketName="mrpizzahut/imgs";
	
	@Autowired
	private awsService awsService;
	
	public JSONObject uploadImg(MultipartHttpServletRequest request) {
		System.out.println("MultipartHttpServletRequest");
		List<MultipartFile> multipartFiles=new ArrayList<MultipartFile>();
		multipartFiles = request.getFiles("upload");
		System.out.println(multipartFiles.toString());
		System.out.println(multipartFiles.size());
		MultipartFile multipartFile=multipartFiles.get(0);
		if(multipartFile.isEmpty()) {
			System.out.println("이미지가 없음");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("uploaded", false);
			jsonObject.put("url", "empthy");
			return jsonObject; 
		}
		
		return awsService.uploadAws(multipartFiles.get(0), buketName,request.getSession());
	}
	public void deleteImg(List<String>imgs) {
		System.out.println("deleteImg");
		for(String i:imgs) {
			awsService.deleteFile(buketName,i);
		}
	
	}

}
