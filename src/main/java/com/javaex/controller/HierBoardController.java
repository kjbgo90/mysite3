package com.javaex.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.HierBoardService;
import com.javaex.vo.HierBoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/hierboard")
public class HierBoardController {
	
	@Autowired
	private HierBoardService hbService;
	
	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String getList(Model model,
						  @RequestParam(value = "page", required = false, defaultValue = "1") int page,
						  @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd) {
		System.out.println("hb-list 출력");
		
		Map<String, Object> map = hbService.getList(page, kwd);
		model.addAttribute("listMap", map);
		
		return "hierboard/list";
	}
	
	@RequestMapping(value = "/writeform", method = RequestMethod.GET)
	public String writeForm() {
		System.out.println("hb-writeform 호출");
		
		return "hierboard/writeform";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute HierBoardVo vo) {
		//writeform에서 authUser의 no를 user_no로 줬기 때문에 vo로 다 넘어와서 세션에 접근 안해도 됨
		System.out.println("hb-write 실행" + vo.toString());
		
		int result = hbService.writeBoard(vo);
		if (result == 1) 
			System.out.println("write 성공!");
		else 
			System.out.println("write 실패..");
		
		return "redirect:/hierboard?page=1&kwd=";
	}
	
	@RequestMapping(value = "/read/{no}", method = RequestMethod.GET)
	public String read(@PathVariable("no") int no,
					   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
					   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
	 				   Model model) {
		System.out.println("hb-read 실행");
		
		HierBoardVo vo = hbService.readBoard(no);
		model.addAttribute("readVo", vo);
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);
		return "hierboard/read";
	}
	
	@RequestMapping(value = "/replyform", method = RequestMethod.GET)
	public String replyform() {
		System.out.println("hb-replyform 호출");
		
		return "hierboard/replyform";
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute HierBoardVo vo,
						@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			   			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd) throws UnsupportedEncodingException {
		System.out.println("hb-reply 실행");
		kwd = URLEncoder.encode(kwd, "UTF-8");
		
		int result = hbService.replyWriteBoard(vo);
		if (result == 1) 
			System.out.println("replyWrite 성공!");
		else 
			System.out.println("replyWrite 실패..");
		
		return "redirect:/hierboard?page=" + page + "&kwd=" + kwd;
	}

	@RequestMapping(value = "/modifyform/{no}", method = RequestMethod.GET)
	public String modifyform(@PathVariable("no") int no,
			 				 Model model) {
		System.out.println("hb-modifyform 호출");
		
		HierBoardVo vo = hbService.modiGet(no);
		model.addAttribute("modiVo", vo);		
		return "hierboard/modifyform";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute HierBoardVo vo,
						 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd) throws UnsupportedEncodingException {
		System.out.println("hb-modify 실행");
		
		kwd = URLEncoder.encode(kwd, "UTF-8");
		int result = hbService.modify(vo);
		
		if(result == 1) {
			System.out.println("modify 성공!");
			return "redirect:/hierboard/read/" + vo.getNo() + "?page=" + page + "&kwd=" + kwd;
		}
		else {
			System.out.println("modify 실패..");
			return "redirect:/main";
		}
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String deleteController(@PathVariable("no") int no,
						 @RequestParam("user_no") int write_no, 
						 HttpSession session,
						 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd) throws UnsupportedEncodingException {
		System.out.println("hb-delete 실행");
		kwd = URLEncoder.encode(kwd, "UTF-8");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int auth_no = authUser.getNo();
		
		if(write_no == auth_no) {
			int result = hbService.delete(no);
			
			if(result == 1)
				return "redirect:/hierboard?page=" + page + "&kwd=" + kwd;
			else
				return "redirect:/main";
		}
		else {
			return "redirect:/main";
		}
	}
}
