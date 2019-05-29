package com.cafe24.mysite.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public List<GuestbookVo> getList() {

		//List<GuestbookVo> result = new ArrayList<GuestbookVo>();

		List<GuestbookVo> result = sqlSession.selectList("guestbook.getList");
		return result;
	}

	public boolean insert(GuestbookVo vo) {

		int count = sqlSession.insert("guestbook.insert",vo);
		
		return 1 == count;

	}
	
	public boolean delete(GuestbookVo vo) {

		int count = sqlSession.delete("guestbook.delete",vo);
		
		return 1 == count;

	}
	

}
