<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
    <%@ include file="../common/adminHeader.jsp" %>
  <%@ include file="../common/adminSide.jsp" %>
  <div class="contents">
<form  id="minsert" name="minsert">
쿠폰이름:
<br>
<input type="text" name="title" class="form-control menuInput mt-2" placeholder="쿠폰이름을 적어주세요">
<br>
쿠폰 할인 방식:
<br>
페센트 이면 per 금액 마이너스이면 minus
<br>
 <input type="text" name="action" class="form-control menuInput mt-2" placeholder="쿠폰방식을 적어주세요">
<br>
쿠폰 할인 금액:
per= 100이하 minus는 금액으로 구분기호 ,없이 적어주세요
<br>
<input type="number" name="price" class="form-control menuInput mt-2" placeholder="할인율/금액을 적어주세요">
<br>
쿠폰 만료일을 적어주세요
<input type="datetime-local" name="expireDate" class="form-control menuInput mt-2" placeholder="쿠폰 만료기간을 적어주세요">
<br>
<input type="button"  class="form-control menuInput mt-2" value="쿠폰저장" onclick="insertCoupon('minsert')">
</form>
</div>
</body>
</html>