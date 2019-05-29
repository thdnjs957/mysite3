package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {

    public static final int BOARD_CNT = 5;    //페이지당 게시물 수
    public static final int PAGE_CNT = 5;    //화면당 페이지 수 
    
    @Autowired
    private BoardDao boardDao;
    
    public Map<String,Object> getList(String keyword,int curPage) { //map.put("keyword", keyword) , map.put("curPage",curPage);
       
       int pageStart = (curPage-1) * BOARD_CNT ;
       int pageEnd = pageStart+BOARD_CNT-1;
       int totalCount = boardDao.getCount(keyword); 
       
       int end = (int) (Math.ceil( (double)curPage / (double) PAGE_CNT) * PAGE_CNT);
       
        int tempEndPage = (int) (Math.ceil(totalCount / (double) PAGE_CNT ));
        
        if (end > tempEndPage) {
           end = tempEndPage;
        }

       int start = (end - BOARD_CNT) + 1;
        if(start <= 0) start = 1;
       
       int prev = start == 1 ? 0:1; // 페이징 이전 버튼 활성화 여부
       int next = end * BOARD_CNT >= totalCount ? 0 : 1;
       
       Map<String, Integer> pagerMap = new HashMap<String, Integer>();
       pagerMap.put("start", start);
       pagerMap.put("end", end);
       pagerMap.put("prev", prev);
       pagerMap.put("next", next);
       pagerMap.put("pageStart", pageStart);
       pagerMap.put("pageEnd", pageEnd);
       pagerMap.put("curPage", curPage);   
       pagerMap.put("totalCount", totalCount);   
       pagerMap.put("pageCnt", PAGE_CNT);   
       
       
       Map<String, Object> listMap = new HashMap<String, Object>(); //board.xml 
       listMap.put("pageStart",pageStart);
       listMap.put("boardCnt",BOARD_CNT);
      listMap.put("keyword", keyword);

      List<BoardVo> list = boardDao.getList(listMap);
       
      Map<String, Object> map = new HashMap<String, Object>();
      
      map.put("list", list);
      map.put("pagerMap", pagerMap);
      
      return map;
    }

    public boolean insert(BoardVo boardVo) {
        
        return boardDao.insert(boardVo);
    }
    
    public boolean insertReply(BoardVo boardVo) {
        
        boardDao.updateOrderNo(boardVo);
        return boardDao.reply(boardVo);
        
    }
    
    public boolean delete(Long boardNo) {
        return boardDao.delete(boardNo);
    }

    public BoardVo getEachBoardVo(Long boardNo) {
        return boardDao.getByBoardNo(boardNo);
    }

    public boolean update(BoardVo boardVo) {
        return boardDao.modifyVo(boardVo);
    }

    public boolean hitIncrease(Long boardNo) {
        return boardDao.hitUpdate(boardNo);
    }

    public boolean updateOrderNo(BoardVo boardGetVo) {
        return boardDao.updateOrderNo(boardGetVo);
    }
    
    
}