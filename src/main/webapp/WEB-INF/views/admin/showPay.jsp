<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common/adminHeader.jsp" %>

    <%
    Map<String,Integer>byDayPrice=(Map<String,Integer>)request.getAttribute("days");
	Map<Integer, Integer>byMonthPrice=(Map<Integer,Integer>)request.getAttribute("moths");
	String producName=request.getParameter("productName");
	int nowYear=Integer.parseInt(request.getParameter("year"));
	int nowMonth=Integer.parseInt(request.getParameter("month"));
    %>

    <%@ include file="../common/adminSide.jsp" %>
  <div class="contents">
  연도 <%=nowYear %> 
  <br>
  제품 <%=producName %>
  <br>
 		<ul>
		  <%
		  	for(int i=1;i<=12;i++){
		  		%>
		  		<%=i+"월 매출"+byMonthPrice.get(i) %><br>
		  	<%}
		  %>
 			 
 		</ul>
 		<%
 	
 				for(int ii=1;ii<=31;ii++){
 				%>
 					<%=nowMonth+"월"+ii+"일 매출"+byDayPrice.get(nowMonth+"/"+ii) %><br>
 					
 				<%}
 			
 		%>
  <div id="pageArea">
            <div style="margin-top:10px">
            <%
            	if(nowYear>year){
            		%>
            		 <input type="button" id="beforeButton"  class="btn btn-outline-primary btn-sm" value="이전" disabled="disabled">
            		
            	<%}else{
            		%>
            		 <input type="button" id="beforeButton" onclick="changeYear(-1,)" class="btn btn-outline-primary btn-sm" value="이전연도">
            	<%}
            %>
            <%=nowYear %>/<%=year %>
           <% if(nowYear<year){
            	%>
            	 <input type="button" id="nextButton" onclick="changeYear(1,)"  class="btn btn-outline-primary btn-sm" value="다음연도">
            
            <%}else{
            	%>
            	<input type="button" id="nextButton"   class="btn btn-outline-primary btn-sm" value="다음" disabled="disabled">
            <%}
            
            %>
           	<br>
           	<%
           		if(nowMonth>month2){
           			%>
           			<input type="button" id="beforeButton"  class="btn btn-outline-primary btn-sm" value="이전달" disabled="disabled">
           		<%}else{
           			%>
           			 <input type="button" id="beforeButton" onclick="changeMonth(-1)" class="btn btn-outline-primary btn-sm" value="이전달">
           		<%}
           		if(nowMonth>=month2){
           			%>
           			<input type="button" id="nextButton"   class="btn btn-outline-primary btn-sm" value="다음달" disabled="disabled">
           		<%}else{
           			%>
           			<input type="button" id="nextButton" onclick="changeMonth(1)"  class="btn btn-outline-primary btn-sm" value="다음달">
           		<%}
           	%>	
           		 
            </div>
        </div>
 </div>
