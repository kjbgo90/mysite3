package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.javaex.service.GalleryService;
import com.javaex.vo.FileVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(Model model) {
		List<FileVo> list = galleryService.getList();
		model.addAttribute("list", list);
		return "gallery/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public FileVo insert(MultipartHttpServletRequest request,
					     HttpSession session) {
		MultipartFile file = request.getFile("file");
		String comments = request.getParameter("comments");
		
		//로그인 된 유저의 정보를 가져옴
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		
		FileVo fileVo = new FileVo();
		fileVo.setUser_no(userVo.getNo());
		fileVo.setComments(comments);
		
		return galleryService.insertImg(file, fileVo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public FileVo view(@RequestParam("no") int no) {
		FileVo fileVo = galleryService.getImg(no);
		return fileVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public boolean delete(@RequestParam("no") int no,
						  @RequestParam("user_no") int user_no,
						  HttpSession session) {
		int result = 0;

		UserVo userVo = (UserVo)session.getAttribute("authUser");
		
		if(user_no == userVo.getNo())
			result = galleryService.delete(no);
		
		if (result == 1)
			return true;
		else
			return false;
	}
}
