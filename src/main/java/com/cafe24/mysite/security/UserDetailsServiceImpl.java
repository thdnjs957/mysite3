package com.cafe24.mysite.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
												//email
		UserVo userVo = userDao.get(username);
		SecurityUser securityUser = new SecurityUser();
		
		if( userVo != null ) {
			//임시 데이터
			//String role = userVo.getRole(); //원래 디비에서 가져와야함
			//String role = "ROLE_USER";
			securityUser.setNo(userVo.getNo()); 	 // biz data
			securityUser.setName(userVo.getName()); 	 // biz data
			securityUser.setUsername(userVo.getEmail()); // principal
			securityUser.setPassword(userVo.getPassword()); // credential
			
			//Arrays.asList(new SimpleGrantedAuthority(role));//array를 list로 만듦
			securityUser.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(userVo.getRole())));

		}
		
		// return new USer(); name password 같은 기본만 들어감  no, gender 이런건 확장해서 직접 구현해서 씀 = > 이게 securityUser
		return securityUser; //UserDetails 
	}

}
