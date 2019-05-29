package com.cafe24.mysite.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
@Controller
@RequestMapping("/board")
public class BoardController {
  
   @Autowired 
   private BoardService boardService;
  
   @RequestMapping("/list") //아무것도 안쓰면 get, post 다 들어옴
   public String list(Model model,@ModelAttribute BoardVo boardVo,@RequestParam(value="keyword", required=true, defaultValue="") String keyword,
                @RequestParam(value="curPage",required=true ,defaultValue="1") int curPage)
   {
      Map<String,Object> map = boardService.getList(keyword,curPage);
      
      List<BoardVo> list = (List<BoardVo>)map.get("list");
      
      Map<String, Integer> pager = (Map<String, Integer>)map.get("pagerMap");
      
      model.addAttribute("list", list);
      model.addAttribute("pager", pager);
   
       return "board/list";
   }
  
   @Auth(role = Auth.Role.USER) //role 속성 생략 가능
   @RequestMapping(value = "/write",method=RequestMethod.GET)
   public String write(Model model,@RequestParam(value="no", required=true, defaultValue="") Long no,HttpSession session) {

      if(no != null) {
         model.addAttribute("no", no);
      }
//      if(session.getAttribute("authUser") == null) {
//         return "redirect:/user/login";
//      }

      return "board/write";
   }
  
   @RequestMapping(value = "/write",method=RequestMethod.POST)
   public String write(Model model,@ModelAttribute BoardVo boardVo,HttpSession session) {
      if(boardVo.getNo() != null) { //답글이면
         boardService.insertReply(boardVo);
      }else { //새로쓴 글이면
         boardService.insert(boardVo);
      }
     
      return "redirect:/board/list";
   }
  
  
   @RequestMapping(value = "/view", method=RequestMethod.GET)
   public String view(@RequestParam(value = "boardNo") Long boardNo, Model model ) {
      
       boardService.hitIncrease(boardNo);
       BoardVo vo = boardService.getEachBoardVo(boardNo);
       model.addAttribute("vo",vo);
      
       return "board/view";
   }
  
   @Auth(role = Auth.Role.USER)
   @RequestMapping(value = "/modify",method=RequestMethod.GET)
   public String modify(HttpSession session,Model model,@RequestParam(value = "boardNo") Long boardNo, @RequestParam(value = "userNo") Long userNo) {
     
      UserVo authvo = (UserVo) session.getAttribute("authUser");
     
      if(authvo.getNo() != userNo || session.getAttribute("authUser") == null) {
         return "redirect:/board/list";
      }
     
      BoardVo vo = boardService.getEachBoardVo(boardNo);
      model.addAttribute("vo",vo);
     
      return "board/modify";
   }
  
  
   @RequestMapping(value = "/modify",method=RequestMethod.POST)
   public String modify(Model model,@ModelAttribute BoardVo boardVo,@RequestParam(value = "boardNo") Long no) {
     
      boardVo.setNo(no);
      boolean result = boardService.update(boardVo);
     
      return "redirect:/board/list";
   }
  
   @Auth
   @RequestMapping("/delete")
   public String delete(Model model,@RequestParam(value = "boardNo") Long boardNo, @RequestParam(value = "userNo") Long userNo,HttpSession session) {
     
      UserVo authvo = (UserVo) session.getAttribute("authUser");
     
      if(authvo.getNo() != userNo || session.getAttribute("authUser") == null) {
         return "redirect:/board/list";
      }
     
      boolean result = boardService.delete(boardNo);
     
      return "redirect:/board/list";
   }
  
  
}