package com.cafe24.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor02 extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) //handler : scanning해서 얻어낸 정보들 annotation 정보 등등
			throws Exception {
		
		System.out.println("MyInterceptor02.preHandler");
		
		return true;
	}

}
