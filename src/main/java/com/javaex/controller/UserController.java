package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.interceptor.Auth;
import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/joinform", method = RequestMethod.GET)
	public String joinform() {
		System.out.println("joinform 호출");
		return "user/joinform";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		System.out.println("join 실행");
		int result = userService.join(vo);
		if(result == 1) {
			System.out.println(result + " 명 join 성공!");
			return "user/joinsuccess";
		}
		else {
			System.out.println("join 실패..");
			return "main";
		}
	}
	
	@RequestMapping(value = "/loginform", method = RequestMethod.GET)
	public String loginform() {
		System.out.println("loginform 호출");
		return "user/loginform";
	}
	/*
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, 
						@RequestParam("password") String password,
						HttpSession session) {
		System.out.println("login 실행");
		UserVo authUser = userService.login(email, password);
		
		if(authUser != null) {
			session.setAttribute("authUser", authUser);
			return "redirect:/main";
		}
		else 
			return "redirect:/user/loginform?result=fail";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		System.out.println("logout 실행");
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/main";
	}
	*/
	
	@Auth
	@RequestMapping(value = "/modifyform", method = RequestMethod.GET)
	public String modifyform(HttpSession session,
							 Model model) {
		System.out.println("modifyform 호출");
		
		UserVo vo = (UserVo)session.getAttribute("authUser");
		model.addAttribute("modiUser", userService.modiFormSel(vo));
		
		return "user/modifyform";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute UserVo vo,
							  HttpSession session) {
		System.out.println("modify 호출");
		
		UserVo modVo = userService.modify(vo);
		session.setAttribute("authUser", new UserVo(modVo.getNo(),modVo.getName()));
		System.out.println("세션 업데이트");
		return "redirect:/main";
	}
	
	@ResponseBody
	@RequestMapping(value = "/emailcheck", method = RequestMethod.POST)
	public boolean emailCheck(@RequestParam("email") String email) {
		System.out.println(email);
		
		boolean result = userService.emailCheck(email);
		
		return result;
	}
}
