<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    Map<String,Integer>byDayPrice=(Map<String,Integer>)request.getAttribute("days");
	Map<Integer, Integer>byMonthPrice=(Map<Integer,Integer>)request.getAttribute("moths");
    %>
<!DOCTYPE html>
<html>
<body>
  <%@ include file="../common/adminHeader.jsp" %>
  <%@ include file="../common/adminSide.jsp" %>
  <div class="contents">
 		<ul>
		  <%
		  	for(int i=1;i<=12;i++){
		  		%>
		  		<%=i+"월 매출"+byMonthPrice.get(i) %><br>
		  	<%}
		  %>
 			 
 		</ul>
 		<%
 			for(int i=1;i<=12;i++){
 				for(int ii=1;ii<=31;ii++){
 				%>
 					<%=i+"월"+ii+"일 매출"+byDayPrice.get(i+"/"+ii) %><br>
 					
 				<%}
 			}
 		%>
 </div>

</body>
</html>