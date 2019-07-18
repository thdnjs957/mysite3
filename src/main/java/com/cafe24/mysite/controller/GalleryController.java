package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.mysite.service.GalleryService;
import com.cafe24.mysite.vo.GalleryVo;


@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
		@Autowired
		GalleryService galleryService;
	
	   @RequestMapping("index")
	   public String list(Model model)
	   {
		   
//	      Map<String,Object> map = boardService.getList(keyword,curPage);
//	      
//	      List<BoardVo> list = (List<BoardVo>)map.get("list");
//	      
//	      Map<String, Integer> pager = (Map<String, Integer>)map.get("pagerMap");
//	      
//	      model.addAttribute("list", list);
//	      model.addAttribute("pager", pager);

		   return "gallery/index";
	   }
	   
	   @RequestMapping(value="upload",method=RequestMethod.POST)
	   public String upload(@ModelAttribute GalleryVo galleryVo,@RequestParam(value="gallery-file") MultipartFile logoFile)
	   {
		   String logo = galleryService.restore(logoFile);
		   return "gallery/index";
	   }
	   
	   
	   
}
