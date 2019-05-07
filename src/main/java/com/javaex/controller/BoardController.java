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

import com.javaex.interceptor.Auth;
import com.javaex.interceptor.AuthUser;
import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String boardList(Model model,
							@RequestParam(value = "page", required = false, defaultValue = "1") int page,
							@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd) {
		System.out.println("boardlist 호출");
		Map<String, Object> map = boardService.getBoardList(page, kwd);
		model.addAttribute("pageMap", map);
		
		return "board/list";
	}
	
	@Auth
	@RequestMapping(value = "/writeform", method = RequestMethod.GET)
	public String writeForm() {
		System.out.println("writeform 호출");
		
		return "board/writeform";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo) {
		System.out.println("write 실행" + vo.toString());
		
		int result = boardService.writeBoard(vo);
		if (result == 1) 
			System.out.println("write 성공!");
		else 
			System.out.println("write 실패..");
		
		return "redirect:/board?page=1";
	}
	
	@RequestMapping(value = "/read/{no}", method = RequestMethod.GET)
	public String read(@PathVariable("no") int no,
					   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
					   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
	 				   Model model) {
		System.out.println("read 실행");
		BoardVo vo = boardService.readBoard(no);
		model.addAttribute("readVo", vo);
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);
		return "board/read";
	}
	
	@Auth
	@RequestMapping(value = "/modifyform/{no}", method = RequestMethod.GET)
	public String modifyform(@PathVariable("no") int no,
			 				 Model model,
			 				 @AuthUser UserVo userVo) {
		System.out.println("modifyform 호출");
		
		BoardVo vo = boardService.modiGet(no);
		
		if(userVo.getNo() == vo.getUser_no()) {
			model.addAttribute("modiVo", vo);
			return "board/modifyform";
		}
		else
			return "redirect:/board?page=1";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo vo,
						 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd) throws UnsupportedEncodingException {
		System.out.println("modify 실행");
		
		kwd = URLEncoder.encode(kwd, "UTF-8");
		int result = boardService.modify(vo);
		
		if(result == 1) {
			System.out.println("modify 성공!");
			return "redirect:/board/read/" + vo.getNo() + "?page=" + page + "&kwd=" + kwd;
		}
		else {
			System.out.println("modify 실패..");
			return "redirect:/main";
		}
	}
	
	@RequestMapping(value = "/deletec/{no}", method = RequestMethod.GET)
	public String deleteController(@PathVariable("no") int no,
						 @RequestParam("user_no") int write_no, 
						 HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int auth_no = authUser.getNo();
		
		if(write_no == auth_no) {
			int result = boardService.deleteCon(no);
			
			if(result == 1)
				return "redirect:/board?page=1&kwd=";
			else
				return "redirect:/main";
		}
		else {
			return "redirect:/main";
		}
	}
	
	@RequestMapping(value = "/deletes/{no}", method = RequestMethod.GET)
	public String deleteService(@PathVariable("no") int no,
						 @RequestParam("user_no") int write_no, 
						 HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int auth_no = authUser.getNo();
		
		int result = boardService.deleteServ(no, write_no, auth_no);
		
		if(result == 1)
			return "redirect:/board?page=1&kwd=";
		else
			return "redirect:/main";
	}
	
	/*
	@RequestMapping(value = "/board/board" , method = RequestMethod.GET)
	public String insert1000() {
		boardService.insert1000();
		return "redirect:/main";
	}
	*/
}
