<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    List<Map<String,Object>>orders=(List<Map<String,Object>>)request.getAttribute("orders");
	int nowPage=(int)request.getAttribute("page");
	int totalPage=(int)request.getAttribute("totalPage");
    %>
<!DOCTYPE html>
<html>
<body>
  <%@ include file="../common/adminHeader.jsp" %>
  <%@ include file="../common/adminSide.jsp" %>
  <div class="contents">
 		<ul>
 		<%
 			for(Map<String,Object>order:orders){
 				%>
 				 <li class="itemArea " style="margin-left:20px"> 
 				 <a href="/app/admin/order?detail=one&onum=<%=order.get("ONUM")%>">
                  	<br>
                    <span>품목:<%=order.get("ONAME") %></span>
                    <br>
                    <span>주문자:<%=order.get("OEMAIL") %></span>
                    <br>
                    <span>주문일:<%=order.get("OCREATED") %></span>
           		</a>
              </li>
 	<%		}
 		%>
 			 
 		</ul>
 		  <div id="pageArea">
            <input type="text" id="searchInput"   placeholder="상품 이름을 적어주세요">
            <input type="button" id="searchButton" onclick="doSearch('/app/admin/event?scope=쿠폰수정삭제&page=1&keyword=')" class="btn btn-outline-primary btn-sm" style="margin-left:10px" value="검색">
            <br>
            <div style="margin-top:10px">
            <%
            	if(nowPage<=1){
            		%>
            		 <input type="button" id="beforeButton"  class="btn btn-outline-primary btn-sm" value="이전" disabled="disabled">
            		
            	<%}else{
            		%>
            		 <input type="button" id="beforeButton" onclick="changePage(-1,'/app/admin/order?detail=all')" class="btn btn-outline-primary btn-sm" value="이전">
            	<%}
            %>
            <span class="showPage"><%=nowPage %></span>/ <span class="showPage"><%=totalPage %></span>
           <% if(nowPage<totalPage){
            	%>
            	 <input type="button" id="nextButton" onclick="changePage(1,'/app/admin/order?detail=all')"  class="btn btn-outline-primary btn-sm" value="다음">
            
            <%}else{
            	%>
            	<input type="button" id="nextButton"   class="btn btn-outline-primary btn-sm" value="다음" disabled="disabled">
            <%}
            
            %>
           		
           		 
            </div>
        </div>
 </div>
</body>
</html>