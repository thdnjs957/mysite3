package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
         throws Exception {

      // 다 통과하면 return true handler한테 요청 돌려주는게 목적

      // 1. handler 종류 확인 (method, defaultServletMethod)
      if (handler instanceof HandlerMethod == false) { // controller의 handlerMethod가 아니라는 뜻 ex) check.png ->
                                          // defaultServlet이 받아감
         return true; // 이미지 이런 것들
      }

      // 2. casting
      HandlerMethod handlerMethod = (HandlerMethod) handler;

      // 3. Method의 @Auth 받아오기
      Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

      // 4. Method에 @Auth 없으면 만약 auth가 안달려있으면
      // 그러면 class(Type)에 @Auth 를 받아오기 method.getClass 찾아서 그 클래스의 annotation을 가져온다
      // if(auth == null) {
      // auth = ... //여기서 클래스에서 찾아내라고!!
      // }
      // auth.getClass().getAnnotation(annotationClass);

      // 5. 여기까지 @Auth가 안 붙어있는 경우는 method에 안달려있는거 class type에서 찾아오기
      if (auth == null) {
         auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
      }

      if(auth == null){
         return true;
      }

      // 여기까지 오면 auth가 붙여있는것
      // 6. @Auth가 (class 또는 method)에 붙어있기 때문에 인증 여부 체크
      HttpSession session = request.getSession();

      // 여기는 붙어있는데 로그인을 안한거
      if (session == null) { // 인증이 안되어 있음
         response.sendRedirect(request.getContextPath() + "/user/login");
         return false;
      }

      UserVo authUser = (UserVo) session.getAttribute("authUser");

      if (authUser == null) {
         response.sendRedirect(request.getContextPath() + "/user/login");
         return false;
      }

      // 여기까지 온건 handler method고 auth가 붙어있고 인증도 되어있어
      // 7. Role 가져오기
      Auth.Role role = auth.role(); // Auth.Role.USER or Auth.Role.ADMIN

      // 8. role이 Auth.Role.USER 라면,
      // 인증된 모든 사용자는 접근 가능, ADMIN 권한이 없어도 (관리자도 board write 할 수 있)
      if (role == Auth.Role.USER) {
         return true;
      }

      // 9. 여기까지 오면 admin으로 접근한 것
      // Admin Role 권한 체크
      // authUser.getRole().equals("ADMIN");
      // 이것도 과제
      // admin이면
      // return false;
      if (role == Auth.Role.ADMIN) {
         if ("ADMIN".equals(authUser.getRole()) == false) {
            response.sendRedirect(request.getContextPath());
            return false;
         }
      }

      return true;
   }

}