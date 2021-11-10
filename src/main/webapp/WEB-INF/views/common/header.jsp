<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
 <%
 session.setAttribute("email", "kim@kim.com");
 System.out.print("tt");
 String email="kim@kim.com";
  
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value="/resources/csslib.css" />" rel="stylesheet">
<script src="<c:url value="/resources/jslib.js" />"></script>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
<header class="header">
	<div class="inner_renew">
		<div class="gnb_wrap">
			<h1><a href="/">로고</a></h1>
			<nav class="gnb">
				<ul>
					<li class="order_gnb">
					
						 
						
							<a href="#" onclick="javascript:go_OnlineOrder();"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_gnb_01.png" alt="바로주문"></i><span><strong>바로주문</strong></span></a>
						
						
						
					</li>
					<li>
						<a href="/menu/premium"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_gnb_02.png" alt="메뉴"></i>메뉴</a>
						<ul>
							<li><a href="/menu/premium">피자</a></li>
							<li><a href="/menu/pizzasand">1인용피자</a></li>
							<li><a href="/menu/doublethin">특가세트</a></li>
							<li><a href="/menu/appetizer">샐러드&amp;사이드</a></li>
						</ul>
					</li>
					<li>
						<a href="/store/store_search"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_gnb_03.png" alt="매장"></i>매장</a>
						<ul>
							<li><a href="/store/store_search">매장찾기</a></li>
							<li><a href="/store/storeBuffet_search">뷔페매장</a></li>
							<li><a href="/store/store_menu">매장전용메뉴</a></li>
						</ul>
					</li>
					<li>
						<a href="/event/event_on"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_gnb_04.png" alt="이벤트"></i>이벤트</a>
						<ul>
							<li><a href="/event/new_menu">신제품 소개</a></li>
							<li><a href="/event/event_on">진행중 이벤트</a></li>
							<li><a href="/event/event_off">종료된 이벤트</a></li>
							<li><a href="/discount/allianceDiscount">할인안내</a></li>
						</ul>
					</li>
					<li>
						<!-- 비로그인상태 -->
						<!-- <a href="/login_join/login"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_gnb_05.png" alt="마이미피"></i>마이미피</a> -->
						<!-- 로그인상태 -->
						<!-- <a href="/mypage/myMipi"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_gnb_05.png" alt="마이미피"></i>마이미피</a> -->
						
							 
							
								<a href="/mypage/myMipi"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_gnb_05.png" alt="마이미피"></i>마이미피</a>
								
								<ul>
									<li><a href="/mypage/myMipi">마이  미피</a></li>
									<li><a href="/mypage/orderList">내주문내역</a></li>
									<li><a href="/mypage/myCoupon">내쿠폰</a></li>
									<li><a href="/mypage/myQnaList">내문의내역</a></li>
									<li><a href="/mypage/myInfo_step1">정보수정</a></li>
									<li><a href="/menu/allpizza">장바구니</a></li>
									<li><a href="/mypage/drop_step1">회원탈퇴</a></li>
								</ul>
							
							
						
					</li>
				</ul>
			</nav>
		</div>
		<div class="hd_btn_area">
			
				 
					
				
			
		</div>
	</div>
</header>
</body>
</html>