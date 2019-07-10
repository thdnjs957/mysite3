package com.cafe24.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService2 {

	public List<GuestbookVo> getContentsList(Long no) {

		GuestbookVo first = new GuestbookVo(1L,"user1", "1234","test1","2019-07-19 10:30");
		GuestbookVo second = new GuestbookVo(2L,"user2", "1234","test2","2019-07-19 10:31");
		
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		list.add(first);
		list.add(second);
		
		return list;
	}

	public GuestbookVo addContents(GuestbookVo guestbookVo) {
		
		guestbookVo.setNo(10L);
		guestbookVo.setRegdate("2019-07-10 00:00:00");
		
		return guestbookVo;
	}

	public Long deleteContents(Long no, String password) {

		return no;
	}
	
	
	
	
}
