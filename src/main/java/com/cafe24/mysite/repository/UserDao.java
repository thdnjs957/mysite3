package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo vo) {
		System.out.println(vo);
		int count = sqlSession.insert("user.insert",vo);
		System.out.println(vo);
		return 1 == count;
	}

	public boolean update(UserVo vo) {
		
		int count = sqlSession.update("user.update",vo);

		return 1 == count;

	}
	
	public UserVo get(String email) {
		
		UserVo result = sqlSession.selectOne("user.getByEmail",email);
		
		return result;
	}
	
	
	public UserVo get(Long no) {
		
		UserVo result = sqlSession.selectOne("user.getByNo",no);
		
		return result;
	}

	public UserVo get(String email, String password) throws UserDaoException { // 로그인 하니깐 하나 존재
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("email", email);
		map.put("password",password);
		
		UserVo uservo = sqlSession.selectOne("getByEmailAndPassword",map);
		
		return uservo;
		
	}
	

}
