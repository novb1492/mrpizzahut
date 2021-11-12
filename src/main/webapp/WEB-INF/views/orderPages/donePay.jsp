<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <%
 boolean flag=(boolean)request.getAttribute("flag");
 String buykind=(String)request.getAttribute("buykind");
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% if(flag){
%>
 <%if(buykind.equals("vbank")){
	 %>
	 입금 계좌번호 <%=request.getAttribute("vbanknum") %>
	 입금 기한 <%=request.getAttribute("expireDate") %>
<%	}
 %>
결제금액: <%=request.getAttribute("price") %> 
<Br>
주문내역: <%=request.getAttribute("productNames") %>
	
<%}else{
	%>
	결제에 실패했습니다
	<%=request.getAttribute("message") %>




<%}%>

</body>
<script type="text/javascript">
var flag=<%=flag%>
if(flag){
	 window.opener.location.href="/app/";
}
</script>
</html>