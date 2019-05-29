<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
   href="${pageContext.servletContext.contextPath }/assets/css/board.css"
   rel="stylesheet" type="text/css">
</head>
<body>
   <div id="container">
      <c:import url="/WEB-INF/views/includes/header.jsp" />
      
      <div id="content">
         <div id="board">
            <form id="search_form"
               action="${pageContext.servletContext.contextPath }/board/list"
               method="post">
               <input type="text" id="keyword" name="keyword" value="${keyword }">
               <input type="submit" value="찾기">
            </form>
            <table class="tbl-ex">
               <tr>
                  <th>번호</th>
                  <th>제목</th>
                  <th>글쓴이</th>
                  <th>조회수</th>
                  <th>작성일</th>
                  <th>&nbsp;</th>
               </tr>

               <!-- 여기서 부터 리스트 뿌리기 -->
               <c:set var='listSize' value='${fn:length(list) }' />

               <c:forEach items='${list }' var='vo' varStatus='status'>
                  <tr>
                     <td>${pager.totalCount - (pager.curPage - 1)*pager.pageCnt - status.index}</td>
                     <td style="text-align:left; padding-left:${15*vo.depth}px">
                        <c:if test="${0 ne vo.depth }">
                           <img
                              src='${pageContext.servletContext.contextPath }/assets/images/reply.png'>
                        </c:if> <a
                        href="${pageContext.servletContext.contextPath }/board/view?boardNo=${vo.no }">${vo.title }</a>
                     </td>
                     <td>${vo.userName }</td>
                     <td>${vo.hit }</td>
                     <td>${vo.regDate }</td>
                     <td><a
                        href="${pageContext.servletContext.contextPath }/board/delete?boardNo=${vo.no }&userNo=${vo.userNo}"
                        class="del">삭제</a></td>
                  </tr>
               </c:forEach>
            </table>

            <div class="pager">
               <ul>
                  <c:if test="${pager.prev == 1 }">
                     <li><a
                        href='<c:url value="/board/list?curPage=${pager.start-1 }"/>'>◀</a></li>
                  </c:if>

                  <c:forEach begin='${pager.start }' end='${pager.end }' var="idx">
                        <a href='<c:url value="/board/list?curPage=${idx }"/>'>
                           <c:choose>
                              <c:when test="${idx == pager.curPage }">
                                     <li class="selected">${idx }</li>
                              </c:when>
                              <c:otherwise>
                                   <li>${idx }</li> 
                              </c:otherwise>
                           </c:choose>
                        </a>
                     </li>
                  </c:forEach>
                  
                  <c:if test="${pager.next == 1 && pager.end > 0}">
                     <li><a
                        href='<c:url value="/board/list?curPage=${pager.end+1 }"/>'>
                        ▶</a></li>
                  </c:if>
               </ul>
            </div>

            <div class="bottom">
               <a href="${pageContext.servletContext.contextPath }/board/write"
                  id="new-book">글쓰기</a>
            </div>
         </div>
      </div>
      <c:import url="/WEB-INF/views/includes/navigation.jsp">
         <c:param name="menu" value="board" />
      </c:import>

      <c:import url="/WEB-INF/views/includes/footer.jsp" />
   </div>
</body>
</html>