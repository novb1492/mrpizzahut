<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	List<Map<String,Object>>products=(List<Map<String,Object>>)request.getAttribute("products");
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
 			for(Map<String,Object>product:products){
 				%>
 				 <li class="itemArea"> 
 				 <a href="/app/admin/showProduct">
                  	<img alt="" src="<%=product.get("IMG") %>">
                    <span><%=product.get("MNAME") %></span>
                    <br>
           		</a>
              </li>
 	<%		}
 		%>
 			 
 		</ul>
 		  <div id="pageArea">
            <input type="text" id="searchInput"   placeholder="상품 이름을 적어주세요">
            <input type="button" id="searchButton" onclick="doSearch" class="btn btn-outline-primary btn-sm" style="margin-left:10px" value="검색">
            <br>
            <div style="margin-top:10px">
             <input type="button" id="beforeButton" onclick="changePage(-1)" class="btn btn-outline-primary btn-sm" value="이전">
           		<span class="showPage"><%=nowPage %></span>/ <span class="showPage"><%=totalPage %></span>
           		  <input type="button" id="nextButton" onclick="changePage(1)" class="btn btn-outline-primary btn-sm" value="다음">
            </div>
        </div>
 </div>
</body>
</html>