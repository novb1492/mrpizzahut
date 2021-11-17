<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 	String uri=request.getRequestURI();
 %>
<!DOCTYPE html>
<html>
<body>
<div class="flex-shrink-0 p-3 bg-white side">
    <a href="/" class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
      <svg class="bi me-2" width="30" height="24"><use xlink:href="#bootstrap"></use></svg>
      <span class="fs-5 fw-semibold">기능들</span>
    </a>
    <ul class="list-unstyled ps-0">
        <%
    if(uri.equals("/app/WEB-INF/views//admin/adminHome.jsp")){
    	%>
    	     <!--  <li class="mb-1">
    			<a href="/app/admin/menu?scope=메뉴등록">메뉴등록</a>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#dashboard-collapse" aria-expanded="false">
          Dashboard
        </button>
        <div class="collapse" id="dashboard-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="#" class="link-dark rounded">Overview</a></li>
            <li><a href="#" class="link-dark rounded">Weekly</a></li>
            <li><a href="#" class="link-dark rounded">Monthly</a></li>
            <li><a href="#" class="link-dark rounded">Annually</a></li>
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#orders-collapse" aria-expanded="false">
          Orders
        </button>
        <div class="collapse" id="orders-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="#" class="link-dark rounded">New</a></li>
            <li><a href="#" class="link-dark rounded">Processed</a></li>
            <li><a href="#" class="link-dark rounded">Shipped</a></li>
            <li><a href="#" class="link-dark rounded">Returned</a></li>
          </ul>
        </div>
      </li>
      <li class="border-top my-3"></li>
      <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#account-collapse" aria-expanded="false">
          Account
        </button>
        <div class="collapse" id="account-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="#" class="link-dark rounded">New...</a></li>
            <li><a href="#" class="link-dark rounded">Profile</a></li>
            <li><a href="#" class="link-dark rounded">Settings</a></li>
            <li><a href="#" class="link-dark rounded">Sign out</a></li>
          </ul>
        </div>
      </li>-->
    <%}else if(uri.equals("/app/WEB-INF/views//admin/adminMenu.jsp")||uri.equals("/app/WEB-INF/views//admin/adminMenuUpdate.jsp")||uri.equals("/app/WEB-INF/views//admin/adminMenuShow.jsp")){
    	%>
    	  <li class="mb-1">
           			<a href="/app/admin/menu?scope=메뉴등록">메뉴등록</a>
      	</li>
      <li class="mb-1">
       <a href="/app/admin/menu?scope=메뉴수정삭제&page=1&keyword=">메뉴수정/삭제</a>
      </li>
     <!--   <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#orders-collapse" aria-expanded="false">
          Orders
        </button>
        <div class="collapse" id="orders-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="#" class="link-dark rounded">New</a></li>
            <li><a href="#" class="link-dark rounded">Processed</a></li>
            <li><a href="#" class="link-dark rounded">Shipped</a></li>
            <li><a href="#" class="link-dark rounded">Returned</a></li>
          </ul>
        </div>
      </li>
      <li class="border-top my-3"></li>
      <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#account-collapse" aria-expanded="false">
          Account
        </button>
        <div class="collapse" id="account-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="#" class="link-dark rounded">New...</a></li>
            <li><a href="#" class="link-dark rounded">Profile</a></li>
            <li><a href="#" class="link-dark rounded">Settings</a></li>
            <li><a href="#" class="link-dark rounded">Sign out</a></li>
          </ul>
        </div>
      </li> -->
    <%}else if(uri.equals("/app/WEB-INF/views//admin/insertCoupon.jsp")||uri.equals("/app/WEB-INF/views//admin/updateCoupon.jsp")||uri.equals("/app/WEB-INF/views//admin/showCoupon.jsp")){
    	%>
    	    	  <li class="mb-1">
           			<a href="/app/admin/event?scope=쿠폰등록">쿠폰등록</a>
      	</li>
      <li class="mb-1">
       <a href="/app/admin/event?scope=쿠폰수정삭제&page=1&keyword=">쿠폰수정/삭제</a>
      </li>
    <%}else if(uri.equals("/app/WEB-INF/views//admin/showPay.jsp")){
    	int year2=LocalDateTime.now().getYear();
    	%>
    	 <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#account-collapse" aria-expanded="false">
          	씬크러스트피자
        </button>
        <div class="collapse" id="account-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/app/admin/sales?year=<%=year2 %>&productName=멕시칸 하바네로 피자 세트" class="link-dark rounded">멕시칸 하바네로 피자 세트</a></li>
            <li><a href="/app/admin/sales?year=<%=year2 %>&productName=찐페퍼로니 피자" class="link-dark rounded">찐페퍼로니 피자</a></li>
            <li><a href="/app/admin/sales?year=<%=year2 %>&productName=포슬포슬감자 피자" class="link-dark rounded">포슬포슬감자 피자</a></li>
          </ul>
        </div>
      </li>
       <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#account-collapse2" aria-expanded="false">
          	클래식피자
        </button>
        <div class="collapse" id="account-collapse2">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/app/admin/sales?year=<%=year2 %>&productName=콤비네이션" class="link-dark rounded">콤비네이션 피자</a></li>
            <li><a href="/app/admin/sales?year=<%=year2 %>&productName=불고기" class="link-dark rounded">불고기 피자</a></li>
            <li><a href="/app/admin/sales?year=<%=year2 %>&productName=포테이토" class="link-dark rounded">포테이토 피자</a></li>
             <li><a href="/app/admin/sales?year=<%=year2 %>&productName=페퍼로니플러스" class="link-dark rounded">페퍼로니플러스</a></li>
          </ul>
        </div>
      </li>
    <%}
    
    
    %>

    </ul>
  </div>
</body>
</html>