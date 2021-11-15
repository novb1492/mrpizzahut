package com.mrpizzahut.app.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrpizzahut.app.api.aws.awsService;

@Service
public class fileService {
	
	private final String buketName="mrpizzahut/imgs";
	
	@Autowired
	private awsService awsService;
	
	public void uploadImg(MultipartHttpServletRequest request) {
		System.out.println("MultipartHttpServletRequest");
		List<MultipartFile> multipartFiles=new ArrayList<MultipartFile>();
		multipartFiles = request.getFiles("upload");
		System.out.println(multipartFiles.toString());
		awsService.uploadAws(multipartFiles.get(0), buketName);
	}
}
