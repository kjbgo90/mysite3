package com.javaex.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.util.S3Util;

@Controller
@RequestMapping("/aws")
public class AwsController {
	
	@Autowired
	private S3Util s3util;
	
	private String bucketName = "com.javaex.kjbbb.upload";
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init() {
		
//		s3util.createFolder(bucketName, "img_test");
		
		s3util.createFolder(bucketName, "gallery");
		return "aws/form";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file,
			 			 Model model) {
		System.out.println(file.getOriginalFilename());
		String path = "img_test";
		
		s3util.fileUpload(bucketName + "/" + path, file, file.getOriginalFilename());
		
		String url = s3util.getFileURL(bucketName, path ,file.getOriginalFilename());
		model.addAttribute("url", url);
		
		return "aws/result";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form() {
		
		
		return "aws/form";
	}
}
