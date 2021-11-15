<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	List<Map<String,Object>>products=(List<Map<String,Object>>)request.getAttribute("products");
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
 </div>
</body>
</html>