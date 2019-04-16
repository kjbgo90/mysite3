package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping(value = "/api/gb")
public class ApiGuestbookController {
	
	@Autowired
	private GuestBookService gbService;
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<GuestBookVo> list(@RequestParam("page") int page,
								  @RequestParam("delCnt") int delCnt,
								  @RequestParam("insertCnt") int insertCnt) {
		System.out.println("/api/gb -> list" + "page = " + page + "del=" + delCnt);
		
		List<GuestBookVo> list = gbService.getList(page, delCnt, insertCnt);
		System.out.println(list.toString());
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public boolean delete(@RequestParam("no") int no,
						  @RequestParam("password") String password) {
		System.out.println("/api/gb -> delete " + no + " " + password);
		int result = gbService.delete(new GuestBookVo(no, password));
		
		if(result == 1) //성공하면 true
			return true;
		else
			return false;
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public GuestBookVo insert(@ModelAttribute GuestBookVo vo) {
		System.out.println("/api/gb -> insert " + vo.toString());
		return gbService.insertGuest(vo);
	}
}
