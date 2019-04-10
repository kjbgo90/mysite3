package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String boardList(Model model) {
		System.out.println("boardlist 호출");
		
		List<BoardVo> list = boardService.getBoardList();
		model.addAttribute("boardlist", list);
		
		return "board/list";
	}
	
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
		
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/read/{no}", method = RequestMethod.GET)
	public String read(@PathVariable("no") int no,
	 				   Model model) {
		System.out.println("read 실행");
		BoardVo vo = boardService.readBoard(no);
		model.addAttribute("readVo", vo);		
		return "board/read";
	}
	
	@RequestMapping(value = "/modifyform/{no}", method = RequestMethod.GET)
	public String modifyform(@PathVariable("no") int no,
			 				 Model model) {
		System.out.println("modifyform 호출");
		
		BoardVo vo = boardService.modiGet(no);
		model.addAttribute("modiVo", vo);		
		return "board/modifyform";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo vo) {
		System.out.println("modify 실행");
		
		int result = boardService.modify(vo);
		
		if(result == 1) {
			System.out.println("modify 성공!");
			return "redirect:/board/read/" + vo.getNo();
		}
		else {
			System.out.println("modify 실패..");
			return "redirect:/main";
		}
	}
	
	@RequestMapping(value = "/deletec/{no}", method = RequestMethod.GET)
	public String deleteControll(@PathVariable("no") int no,
						 @RequestParam("user_no") int write_no, 
						 HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int auth_no = authUser.getNo();
		
		if(write_no == auth_no) {
			int result = boardService.deleteCon(no);
			
			if(result == 1)
				return "redirect:/board";
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
			return "redirect:/board";
		else
			return "redirect:/main";
	}
}
