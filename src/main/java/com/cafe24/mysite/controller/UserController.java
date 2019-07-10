package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping("/user")//이건 요청 url
public class UserController {
	
	@Autowired  //spring DI를 사용한 것이다
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";//이건 view forward
	} 

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo,BindingResult result, Model model) { //valid하고 만약 에러가 있으면 result에 담음
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error: list) {
				System.out.println(error);
			}
			model.addAllAttributes(result.getModel());
			return "/user/join";
		}
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
		
	} 
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	} 
	

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		
		return "user/login";
	} 
	
	@Auth
	@RequestMapping( value="/update", method=RequestMethod.GET )
	public String update(
		@AuthUser UserVo authUser,
		Model model ){
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute( "userVo", userVo );
		return "user/update";
	}
	
	@RequestMapping( value="/update", method=RequestMethod.POST )
	public String update( HttpSession session, @ModelAttribute UserVo userVo ){
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		userVo.setNo( authUser.getNo() );
		userService.update(userVo);
		
		// session의 authUser 변경
		authUser.setName(userVo.getName());
		
		return "redirect:/user/update?result=success";
	}
	

	@RequestMapping("/myinfo")
	public String myinfo() {
		
		return "user/myinfo";
	} 
	
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public void auth() {

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout() {

	}
	
//interceptor로 구현
//	@RequestMapping("/logout") 
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	} 
	
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(
//			@RequestParam(value="email", required=true, defaultValue="") String email,
//			@RequestParam(value="password", required=true, defaultValue="") String password,
//			HttpSession session,Model model) 
//	{	
//		UserVo authUser = userService.getUser(new UserVo(email,password));
//		
//		if(authUser == null) {
//			model.addAttribute("result","fail");
//			return "user/login";
//		}
//		
//		//session 처리
//		session.setAttribute("authUser", authUser);
//		
//		return "redirect:/"; //main으로 redirect
//	} 
	
	
//	@ExceptionHandler( Exception.class )
//	public String handleUserDaoException() {
//		return "error/exception";
//	}

	
}
