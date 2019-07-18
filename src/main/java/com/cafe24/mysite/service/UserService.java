package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Service
public class UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean existEmail(String email) {
		UserVo userVo = userDao.get(email);
		return userVo != null;
	}
	
	public Boolean join(UserVo userVo) {
	      userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
	      return userDao.insert(userVo);
	}
	
	public UserVo getUser(Long userNo) { 
		return userDao.get(userNo);
	}
	

	public UserVo getUser(UserVo userVo) {
		return userDao.get(userVo.getEmail(), userVo.getPassword());
	}

	public boolean update(UserVo userVo) {
		return userDao.update(userVo);
	}

	

	
}
