package com.cafe24.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cafe24.mysite.security.CustomUrlAuthenticationSuccessHandler;

//	 Security Filter Chain
//
//	 1. ChannelProcessingFilter
//	 2. SecurityContextPersistenceFilter		( auto-config default, 필수 )
//	 3. ConcurrentSessionFilter
//	 4. LogoutFilter				( auto-config default, 필수 ) 
//	 5. UsernamePasswordAuthenticationFilter	( auto-config default, 필수 )
//	 6. DefaultLoginPageGeneratingFilter		( auto-config default )
//	 7. CasAuthenticationFilter
//	 8. BasicAuthenticationFilter			( auto-config default, 필수 )
//	 9. RequestCacheAwareFilter			( auto-config default )
//	10. SecurityContextHolderAwareRequestFilter	( auto-config default )
//	11. JaasApiIntegrationFilter
//	12. RememberMeAuthenticationFilter      (           추천 )
//	13. AnonymousAuthenticationFilter		( auto-config default )
//	14. SessionManagementFilter			( auto-config default )
//	15. ExceptionTranslationFilter			( auto-config default, 필수 )
//	16. FilterSecurityInterceptor			( auto-config default, 필수 )

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	 * 위에 있는 스프링 시큐리티 필터 연결 작업 
	 * WebSecurity객체는 springSecurityFilterChain 라는 이름의
	 * DelegatingFilterProxy Bean 객체를 생성
	 * DelegatingFilterProxy Bean는 많은 Spring Security Filter chain에 위임한다
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		//super.configure(web);
		// 예외가 웹 접근 URL를 설정한다.
		// ACL(Access Control List)에 등록하지 않을 URL를 
		// 권한이나 인증을 체크 안하겠다 두가지 방법
		//web.ignoring().antMatchers("/assets/**");
		//web.ignoring().antMatchers("/favicon.ico");
		web.ignoring().regexMatchers("\\A/assets/.*\\Z");
		web.ignoring().regexMatchers("\\A/favicon.ico\\Z");
	}
	
	// Interceptor URL의 요청을 안전하게 보호(보안)하는 방법을 설정 (ACL 작성)
	
/*	
  	deny all 
  	
  	user 권한 필요한 부분
	/user/update  -> ( ROLE_USER, ROLE_ADMIN ) -> Authenticated(인증 받음)
	/user/logout  -> ( ROLE_USER, ROLE_ADMIN ) -> Authenticated(인증 받음)
	/board/write  -> ( ROLE_USER, ROLE_ADMIN ) -> Authenticated(인증 받음)
	/board/delete  -> ( ROLE_USER, ROLE_ADMIN ) -> Authenticated(인증 받음)
	/board/modify  -> ( ROLE_USER, ROLE_ADMIN ) -> Authenticated(인증 받음)

	admin 권한 필요한 부분
	/admin/** --> ROLE_ADMIN( Authorized ) 이건 인증 받은거 + admin권한까지 필요함
	allow all
*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//super.configure(http);
		//여기를 통해서 /login 으로 되어있음 이걸 /user/login으로 바꿔야함
		http.authorizeRequests()
		//인증이 되어있을때 (authenticated? 권한 체크가 아님) 
			.antMatchers("/user/update","/user/logout").authenticated()
			.antMatchers("/board/write","/board/delete","/board/modify").authenticated()
			
			// ADMIN Authority ( ADMIN 권한, ROLE_ADMIN )
		
			//.andMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			//.antMatchers("/admin/**").hasRole("ADMIN")
			
			.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/gallery/upload","/gallery/delete").hasAuthority("ROLE_ADMIN")
		
			//모두 접근 , 권한 허용
			//.antMatchers("/**").permitAll()
			.anyRequest().permitAll()
		
	
		//
		// 2. 로그인 설정
		//
		.and()
		.formLogin()
		.loginPage("/user/login") 
		.loginProcessingUrl("/user/auth")//login.jsp action이랑 맞추기
		.failureUrl("/user/login?result=fail")//안맞으면 어디로?
		//.defaultSuccessUrl("/",true)//성공하면 메인
		.successHandler(authenticationSuccessHandler())
		.usernameParameter("email")
		.passwordParameter("password")
		
		//
		// 3. 로그아웃 설정 
		//
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)
		
		//
		// 4. Access Denial Handler
		//
		.and()
		.exceptionHandling()
		.accessDeniedPage("/WEB-INF/views/error/403.jsp") 
		
		//
		// 5. RememberMe 
		//
		.and()
		.rememberMe()
		.key("mysite3")
		.rememberMeParameter("remember-me");
		
		// Temporary for Testing 
		http.csrf().disable();
		
	}
	
	
	//AuthenticationSuccessHandler 등록 
	//로그인 성공했을때 ajax 랑 web으로 요청을 보내기 위해서 
	AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomUrlAuthenticationSuccessHandler();
	}
	
	
	//UserDetailsService를 설정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.and()
		.authenticationProvider(authenicationProvider());
	}

	@Bean
	public AuthenticationProvider authenicationProvider() {
		DaoAuthenticationProvider authProvider = 
				new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
