<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    Map<String,Object>coupon=(Map<String,Object>)request.getAttribute("coupon");
    %>
<!DOCTYPE html>
<html>
<body>
  <%@ include file="../common/adminHeader.jsp" %>
  <%@ include file="../common/adminSide.jsp" %>
   <div class="contents">
<form  id="minsert" name="minsert">
쿠폰이름:
<br>
<input type="text" name="title" class="form-control  mt-2" placeholder="쿠폰이름을 적어주세요" value="<%=coupon.get("COUPONNAME")%>">
<br>
쿠폰 할인 방식:
<br>
페센트 이면 per 금액 마이너스이면 minus
<br>
 <input type="text" name="action" class="form-control  mt-2" placeholder="쿠폰방식을 적어주세요" value="<%=coupon.get("COKIND")%>">
<br>
쿠폰 할인 금액:
per= 100이하 minus는 금액으로 구분기호 ,없이 적어주세요
<br>
<input type="number" name="price" class="form-control  mt-2" placeholder="할인율/금액을 적어주세요" value="<%=coupon.get("COPRICE")%>">
<br>
쿠폰 만료일을 적어주세요
<input type="datetime-local" name="expireDate" class="form-control  mt-2" placeholder="쿠폰 만료기간을 적어주세요" value="<%=coupon.get("COEXPIRED")%>" >
<br>
쿠폰 사용여부 
<br>
<%=coupon.get("usedFlag")%>
<br>
<%
	int flag=Integer.parseInt(coupon.get("USEDFLAG").toString());
	if(flag!=0){
	%>
		
		쿠폰 사용일 
		<br>
		<%=coupon.get("USEDDATE") %>
		<br>
		쿠폰 사용자
		<br>
		<%=coupon.get("COUSEDEMAIL") %>
		<br>
		쿠폰 사용 결제 번호
		<br>
		<%= coupon.get("COMCHTTRDNO")%>
	<%}%>	
쿠폰 살리기
<br>
쿠폰을 살리려면 1을 입력해주세요
<br>
<input type="number" name="flag" class="form-control  mt-2"  placeholder="쿠폰을 사용가능으로 만드시려면 1을 입력해주세요" min="0" max="1" value="0">
<input type="hidden" id="conum" name="conum" value="<%=coupon.get("CONUM")%>"> 	
<input type="button"  class="form-control  mt-2" value="쿠폰수정" onclick="updateCoupon('minsert')">
<br>
<input type="button"  class="form-control  mt-2" value="쿠폰삭제" onclick="deleteCoupon()">
</form>
</div>
</body>
</html>