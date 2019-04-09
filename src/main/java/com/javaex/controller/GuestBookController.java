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

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookDao dao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String addList(Model model) {
		System.out.println("guestbook/addlst 호출");
		List<GuestBookVo> list =  dao.getList();
		model.addAttribute("list", list);
		
		return "guestbook/addlst";
	}
	
	@RequestMapping(value = "/deleteform", method = RequestMethod.GET)
	public String deleteForm() {
		
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.POST)
	public String deleteGuest(@PathVariable("no") int no, 
							  @RequestParam("password") String password) {
		dao.delete(new GuestBookVo(no, password));
		
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(@ModelAttribute GuestBookVo vo) {
		dao.insert(vo);
//		dao.insertMap(vo);
		return "redirect:/guestbook";
	}
	
}
