<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="../common/adminHeader.jsp" %>
<%
    Map<String,Object>order=(Map<String,Object>)request.getAttribute("order");
int donflag=Integer.parseInt(order.get("ODONEFLAG").toString());
int cancleFlag=Integer.parseInt(order.get("OCANCLEFLAG").toString());
%>
<!DOCTYPE html>
<html>
<body>
 <div class="contents">

주문번호:
<br>
<%=order.get("ONUM") %>
<Br>
주문자 이메일:
<br>
<%=order.get("OEMAIL") %>
<br>
결제 번호:
<br>
<%=order.get("OMCHTTRDNO") %>
<br>
주문 품목 :
<br>
<%=order.get("ONAME") %>
<br>
주문 개수:
<br>
<%=order.get("OCOUNT") %>
<br>
사용 쿠폰:
<br>
<%=order.get("OCOUPONS") %>
<br>
주문 결제 방식:
<br>
<%=order.get("OMETHOD") %>
<br>
사이즈:
<Br>
<%=order.get("OSIZE") %>
<br>
엣지:
<br>
<%=order.get("OEDGE") %>
<br>
금액:
<br>
<%=order.get("OPRICE") %>
<br>
결제 상태:
<br>
<%if(donflag==1&&cancleFlag==0){
%>
결제 완료 상품입니다
<br>
결제 완료일:
<br>
<%=order.get("ODONEDATE") %>
<br>
<input type="hidden"  id="mchttrdno" value="<%=order.get("OMCHTTRDNO")%>">
<input type="hidden"  id="onum" value="<%=order.get("ONUM")%>">
<input type="button"  class="form-control  mt-2" value="주문 취소" onclick="cancleOrder()">
<%}else if(cancleFlag!=0){
%>
결제 취소된 상품입니다
<br>
결제 취소일:
<%=order.get("OCANCLEDATE") %>

<%}else {
	%>
결제가 이뤄지지 않은 상품입니다
<br>
<%} %>



</div>	
</body>
</html>