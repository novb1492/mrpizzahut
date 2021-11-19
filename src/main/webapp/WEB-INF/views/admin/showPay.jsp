<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common/adminHeader.jsp" %>

    <%
    Map<String,Integer>byDayPrice=(Map<String,Integer>)request.getAttribute("days");
	Map<Integer, Integer>byMonthPrice=(Map<Integer,Integer>)request.getAttribute("months");
	Map<String,Integer>cByDayPrice=(Map<String,Integer>)request.getAttribute("cdays");
	Map<Integer, Integer>cByMonthPrice=(Map<Integer,Integer>)request.getAttribute("cmonths");
	String producName=request.getParameter("productName");
	int nowYear=Integer.parseInt(request.getParameter("year"));
	int nowMonth=Integer.parseInt(request.getParameter("month"));

    %>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <%@ include file="../common/adminSide.jsp" %>
  <div class="contents">
  연도 <%=nowYear %> 
  <br>
  제품 <%=producName %>
  <br>
 		 <div id="columnchart_material" style="width: 800px; height: 500px;"></div>
 		  <div id="columnchart_material2" style="width: 800px; height: 500px;"></div>
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
           		if(nowMonth<=1){
           			%>
           			<input type="button" id="beforeButton"  class="btn btn-outline-primary btn-sm" value="이전달" disabled="disabled">
           		<%}else{
           			%>
           			 <input type="button" id="beforeButton" onclick="changeMonth(-1)" class="btn btn-outline-primary btn-sm" value="이전달">
           		<%}%>
 			 <%=nowMonth %>/12
           		<%if(nowMonth>=12){
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
  

 <script>
 google.charts.load('current', {'packages':['bar']});
 google.charts.setOnLoadCallback(drawChart);
 google.charts.setOnLoadCallback(drawChart2);
 function drawChart() {
   var data = google.visualization.arrayToDataTable([
     ['month', '순판매금액','환불금액'],
     ['1', <%=byMonthPrice.get(1)%>,<%=cByMonthPrice.get(1)%>],
     ['2', <%=byMonthPrice.get(1)%>,<%=cByMonthPrice.get(2)%>],
     ['3', <%=byMonthPrice.get(3)%>,<%=cByMonthPrice.get(3)%>],
     ['4', <%=byMonthPrice.get(4)%>,<%=cByMonthPrice.get(4)%>],
     ['5', <%=byMonthPrice.get(5)%>,<%=cByMonthPrice.get(5)%>],
     ['6', <%=byMonthPrice.get(6)%>,<%=cByMonthPrice.get(6)%>],
     ['7', <%=byMonthPrice.get(7)%>,<%=cByMonthPrice.get(7)%>],
     ['8', <%=byMonthPrice.get(8)%>,<%=cByMonthPrice.get(8)%>],
     ['9', <%=byMonthPrice.get(9)%>,<%=cByMonthPrice.get(9)%>],
     ['10', <%=byMonthPrice.get(10)%>,<%=cByMonthPrice.get(10)%>],
     ['11', <%=byMonthPrice.get(11)%>,<%=cByMonthPrice.get(11)%>],
     ['12', <%=byMonthPrice.get(12)%>,<%=cByMonthPrice.get(12)%>]
   ]);

   var options = {
     chart: {
       title: 'MrPizzaHut '+<%=nowYear%>,
       subtitle: '월 판매차트',
     }
   };

   var chart = new google.charts.Bar(document.getElementById('columnchart_material'));
   var formatter = new google.visualization.NumberFormat(
		 {prefix: '원', negativeColor: 'red', negativeParens: true});
   		formatter.format(data, 1);
		formatter.format(data, 2); // Apply formatter to second column
   chart.draw(data, google.charts.Bar.convertOptions(options));
 }
 function drawChart2() {
 var data2 = google.visualization.arrayToDataTable([
     ['day', '순판매금액','환불금액'],
     ['1', <%=byDayPrice.get(nowMonth+"/"+1)%>,<%=cByDayPrice.get(nowMonth+"/"+1)%>],
     ['2', <%=byDayPrice.get(nowMonth+"/"+2)%>,<%=cByDayPrice.get(nowMonth+"/"+2)%>],
     ['3', <%=byDayPrice.get(nowMonth+"/"+3)%>,<%=cByDayPrice.get(nowMonth+"/"+3)%>],
     ['4', <%=byDayPrice.get(nowMonth+"/"+4)%>,<%=cByDayPrice.get(nowMonth+"/"+4)%>],
     ['5', <%=byDayPrice.get(nowMonth+"/"+5)%>,<%=cByDayPrice.get(nowMonth+"/"+5)%>],
     ['6', <%=byDayPrice.get(nowMonth+"/"+6)%>,<%=cByDayPrice.get(nowMonth+"/"+6)%>],
     ['7', <%=byDayPrice.get(nowMonth+"/"+7)%>,<%=cByDayPrice.get(nowMonth+"/"+7)%>],
     ['8', <%=byDayPrice.get(nowMonth+"/"+8)%>,<%=cByDayPrice.get(nowMonth+"/"+8)%>],
     ['9', <%=byDayPrice.get(nowMonth+"/"+9)%>,<%=cByDayPrice.get(nowMonth+"/"+9)%>],
     ['10', <%=byDayPrice.get(nowMonth+"/"+10)%>,<%=cByDayPrice.get(nowMonth+"/"+10)%>],
     ['11', <%=byDayPrice.get(nowMonth+"/"+11)%>,<%=cByDayPrice.get(nowMonth+"/"+11)%>],
     ['12', <%=byDayPrice.get(nowMonth+"/"+12)%>,<%=cByDayPrice.get(nowMonth+"/"+12)%>],
     ['13', <%=byDayPrice.get(nowMonth+"/"+13)%>,<%=cByDayPrice.get(nowMonth+"/"+13)%>],
     ['14', <%=byDayPrice.get(nowMonth+"/"+14)%>,<%=cByDayPrice.get(nowMonth+"/"+14)%>],
     ['15', <%=byDayPrice.get(nowMonth+"/"+15)%>,<%=cByDayPrice.get(nowMonth+"/"+15)%>],
     ['16', <%=byDayPrice.get(nowMonth+"/"+16)%>,<%=cByDayPrice.get(nowMonth+"/"+16)%>],
     ['17', <%=byDayPrice.get(nowMonth+"/"+17)%>,<%=cByDayPrice.get(nowMonth+"/"+17)%>],
     ['18', <%=byDayPrice.get(nowMonth+"/"+18)%>,<%=cByDayPrice.get(nowMonth+"/"+18)%>],
     ['19', <%=byDayPrice.get(nowMonth+"/"+19)%>,<%=cByDayPrice.get(nowMonth+"/"+19)%>],
     ['20', <%=byDayPrice.get(nowMonth+"/"+20)%>,<%=cByDayPrice.get(nowMonth+"/"+20)%>],
     ['21', <%=byDayPrice.get(nowMonth+"/"+21)%>,<%=cByDayPrice.get(nowMonth+"/"+21)%>],
     ['22', <%=byDayPrice.get(nowMonth+"/"+22)%>,<%=cByDayPrice.get(nowMonth+"/"+22)%>],
     ['23', <%=byDayPrice.get(nowMonth+"/"+23)%>,<%=cByDayPrice.get(nowMonth+"/"+23)%>],
     ['24', <%=byDayPrice.get(nowMonth+"/"+24)%>,<%=cByDayPrice.get(nowMonth+"/"+24)%>],
     ['25', <%=byDayPrice.get(nowMonth+"/"+25)%>,<%=cByDayPrice.get(nowMonth+"/"+25)%>],
     ['26', <%=byDayPrice.get(nowMonth+"/"+26)%>,<%=cByDayPrice.get(nowMonth+"/"+26)%>],
     ['27', <%=byDayPrice.get(nowMonth+"/"+27)%>,<%=cByDayPrice.get(nowMonth+"/"+27)%>],
     ['28', <%=byDayPrice.get(nowMonth+"/"+28)%>,<%=cByDayPrice.get(nowMonth+"/"+28)%>],
     ['29', <%=byDayPrice.get(nowMonth+"/"+29)%>,<%=cByDayPrice.get(nowMonth+"/"+29)%>],
     ['30', <%=byDayPrice.get(nowMonth+"/"+30)%>,<%=cByDayPrice.get(nowMonth+"/"+30)%>],
     ['31', <%=byDayPrice.get(nowMonth+"/"+31)%>,<%=cByDayPrice.get(nowMonth+"/"+31)%>]
   ]);

   var options2 = {
     chart: {
       title: 'MrPizzaHut '+<%=nowYear%>+'년'+<%=nowMonth%>+'월',
       subtitle: '일 판매차트',
     }
   };

   var chart2 = new google.charts.Bar(document.getElementById('columnchart_material2'));
   var formatter = new google.visualization.NumberFormat(
			 {prefix: '원', negativeColor: 'red', negativeParens: true});
	   		formatter.format(data2, 1);
			formatter.format(data2, 2); // Apply formatter to second column
   chart2.draw(data2, google.charts.Bar.convertOptions(options2));
 }

 </script>
