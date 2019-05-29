package com.cafe24.mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Log LOGGER = LogFactory.getLog( GlobalExceptionHandler.class  );
	
	@ExceptionHandler( Exception.class )
	public void handleException( HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception,IOException{
		
		//e.printStackTrace(); 이렇게 하면 console로 나오고 끝임 파일로 저장 필요
		//1.로깅
		StringWriter errors = new StringWriter();
		
		e.printStackTrace(new PrintWriter(errors));
		
		System.out.println(errors.toString());
		
		String accept = request.getHeader("accept");
		
		if(accept.matches(".*application/json.*")) { //앞에는 모든 문자 포함 + 중간  + 그담 모든 문자
			// JSON 응답
			response.setStatus(HttpServletResponse.SC_OK);
			
			JSONResult jsonResult = JSONResult.fail(errors.toString()); //이건 통신의 실패 여부다 데이터가 null인게 아니라
			String result = new ObjectMapper().writeValueAsString(jsonResult); //result가 json 포맷에 맞게 나온다
			System.out.println(result);
			OutputStream os = response.getOutputStream(); //socket이 나오는 것
			os.write(result.getBytes("UTF-8")); 
			os.flush();
			os.close();
			
			
		} else {
			//LOGGER.error(errors.toString());
			//2. 안내 페이지 가기 + 정상종료(response)
			request.setAttribute("uri", request.getRequestURI());
			//request.setAttribute("exception", errors.toString());
			LOGGER.error(errors.toString());
			
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
		
	}
	
	
}
