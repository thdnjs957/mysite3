package com.cafe24.mysite.controller.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mysite.dto.JSONResult;

@RestController("guestbookAPIController")
@RequestMapping("/api/guest")
public class GuestbookController {

	@RequestMapping(value = "/list/{no}", method = RequestMethod.GET)
	public JSONResult list(@PathVariable(value="no") int no) {
		//Dao 구현 안함 그럼 json으로 넣겠다
		return JSONResult.success(null);
	}
}
