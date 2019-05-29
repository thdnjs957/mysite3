package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	//이거 되는 이유: interceptor가 container안에 들어와있으니깐 가능 근데 바깥에 있으면??? applicationContext ac 로 가져온다
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//여기서 UserService를 new 하면 안된다. 왜냐 userDao가 null이 되기 때문
		
		
//		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());//container를 이름없이 가져옴 
//		
//		UserService userService = ac.getBean(UserService.class);
		
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		
		UserVo authUser = userService.getUser(userVo);
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		//session 처리
		HttpSession session = request.getSession(true); //없으면 달라
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		
		return false; //controller에 들어갈 필요가 없다 ,login은 고정
	}

	
}
