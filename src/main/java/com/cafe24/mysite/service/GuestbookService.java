package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cafe24.mysite.repository.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookDao guestbookdao;

	public List<GuestbookVo> getContentList() {
		
		List<GuestbookVo> list = guestbookdao.getList();
		return list;
	}

	public List<GuestbookVo> getContentList(Long lastNo) {

		List<GuestbookVo> list = guestbookdao.getList(lastNo);
		return list;
	}
	
	public boolean writeContent(GuestbookVo vo) {
		
		return guestbookdao.insert(vo);
	}
	
	
	public boolean delete(GuestbookVo guestbookVo) {
		
		return guestbookdao.delete(guestbookVo);
	}

	
}
