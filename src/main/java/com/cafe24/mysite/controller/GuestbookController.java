package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired  //spring DI를 사용한 것이다
	private GuestbookService guestbookService;
	
	@RequestMapping( value="/add", method=RequestMethod.POST )
	public String add( @ModelAttribute GuestbookVo vo ) {
		
		guestbookService.writeContent(vo);
		
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)	//그냥 get으로 요청 오면 select해서 보여줌
	public String list(Model model) {
		
		List<GuestbookVo> list = guestbookService.getContentList();
		
		model.addAttribute( "list", list );
		
		return "guestbook/list";
	}
	
	
	@RequestMapping(value="/list", method=RequestMethod.POST) // post 방식으로 오면 insert
	public JSONResult list(Model model,@ModelAttribute GuestbookVo guestbookVo) {
		// guestbookService.insert(guestbookVo);
		boolean result = true;
		
		return JSONResult.success(result);
	}	
	
	@RequestMapping("/deleteform") 
	public String deleteform(@RequestParam Long no ,Model model) {
		model.addAttribute("no",no);

		return "guestbook/deleteform";
	}	
	
	@RequestMapping("/delete")
	public String delete(@ModelAttribute GuestbookVo guestbookVo ,Model model) {
		
		boolean result = guestbookService.delete(guestbookVo);
		if(result) {
			return "redirect:/guestbook/list";
		}
		
		return "guestbook/deleteform";
	}
	
	@RequestMapping("/spa")
	public String ajax() {
		
		return "guestbook/index-spa";
	}


}


