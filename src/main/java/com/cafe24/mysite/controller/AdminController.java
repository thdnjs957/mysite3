package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cafe24.security.Auth;

@Controller
@RequestMapping("/admin")
@Auth(role = Auth.Role.ADMIN)
// Auth에 type(클래스) 추가해야함 , 인증을 받아야하는데 ADMIN 권한임 
public class AdminController {
	
	
	@RequestMapping({"","/main"})
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() { //사이트 관리 페이지
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {//유저 관리 페이지
		return "admin/user";
	}
	
}
