package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestbookService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String addList(Model model) {
		System.out.println("guestbook/addlst (guestbook main) 호출");
		List<GuestBookVo> list = guestbookService.getList();
		model.addAttribute("list", list);
		
		return "guestbook/addlst";
	}
	
	@RequestMapping(value = "/deleteform", method = RequestMethod.GET)
	public String deleteForm() {
		System.out.println("guestbook/deleteform 호출");
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.POST)
	public String deleteGuest(@PathVariable("no") int no, 
							  @RequestParam("password") String password) {
		System.out.println("guestbook/delete 실행");
		int result = guestbookService.delete(new GuestBookVo(no, password));
		if (result == 1) {
			System.out.println("delete 성공!");
			return "redirect:/guestbook";
		}
		else {
			System.out.println("delete 실패..");
			return "main/index";
		}
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(@ModelAttribute GuestBookVo vo) {
		System.out.println("guestbook/insert 실행");
		int result = guestbookService.insert(vo);
		if (result == 1) {
			System.out.println("insert 성공!");
			return "redirect:/guestbook";
		}
		else {
			System.out.println("insert 실패..");
			return "main/index";
		}
	}
	
}
