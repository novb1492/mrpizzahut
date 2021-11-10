<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
 int buyCount=2;
%>

<section class="quick_wrap">
	<ul class="quick_menu">
		
			 
			<c:choose>
		         <c:when test = "${email!=null}">
		           		<li><a href="/login_join/logout" class="quick_tit "><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_quick_01.png" alt="로그아웃"></i>로그아웃</a></li>
		         </c:when>
		         <c:otherwise>
						<li><a href="/login_join/logout" class="quick_tit "><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_quick_01.png" alt="로그아웃"></i>로그인</a></li>
		         </c:otherwise>
     		 </c:choose>
			
			
			
			
					
		<!-- <li><a href="/login_join/login" class="quick_tit"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_quick_01.png" alt="로그인" /></i>로그인</a></li> -->
		<li><a href="/login_join/join" class="quick_tit"><i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_quick_02.png" alt="회원가입"></i>회원가입</a></li>
		<li class="inBox">
			<a href="/app/buket" onclick="javascript:cartOpen();" class="quick_tit quick_cart" title="장바구니">
				<i><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/ico_quick_03.png" alt="장바구니"></i>장바구니<em class="cart_num"><%=buyCount %></em>
			</a>
			<section class="quick_box p0">
				<ul class="q_rdoList">
					
					
					<li class="on">
						<label>
							<input id="q_cartRdo_1" name="q_cartRdo" type="radio" class="radio" value="D" checked="">
							<span class="lbl">배달주문</span>
						</label>
					</li>
					<li>
						<label>
							<input id="q_cartRdo_2" name="q_cartRdo" type="radio" class="radio" value="P">
							<span class="lbl">방문포장</span>
						</label>
					</li>
					
				</ul>
				
				
				<p class="q_price">
					<b>주문금액</b>
					<span><strong class="t_red"></strong>원</span>
				</p>
				<ul class="txt_list pl10">
					<li>1회 주문 시 피자는 5판까지 주문 가능합니다.</li>
					<li>1회 주문 시 음료는 5개까지 주문 가능합니다.</li>
					<li>샐러드메뉴 (홈샐러드/케이준샐러드) 제외한 결제금액 16,000원 이상 배달 가능합니다.</li>
					<!--
					<li>사이드만 주문시 샐러드메뉴 (홈샐러드/케이준샐러드/리코타샐러드) 제외한 결제금액 14,000원 이상 배달 가능합니다.</li>
					-->
				</ul>

				<p class="mt20 mb20 t_center">
					<a href="#" class="button q_close" title="창 닫기">창 닫기 <span class="gt">&gt;</span></a>
					<a href="#" onclick="javascript:goCart();" class="button" title="장바구니 가기">장바구니 가기 <span class="gt">&gt;</span></a>
					<a href="#" onclick="javascript:goDirectOrder();" class="button red" title="바로주문">바로주문 <span class="gt">&gt;</span></a>
				</p>
			</section>
		</li>
		<li class="inBox">
			<section class="quick_box">
				<h1 class="tit1">
					
						
							이전 주문 그대로 주문하기
						 
						
								
				</h1>
				<ul class="tabmenu noline tabMotion mt20">
					<li class="on"><a href="#q_tab01" title="배달주문"><span>배달주문</span></a></li>
					<li><a href="#q_tab02" title="방문포장"><span>방문포장</span></a></li>
				</ul>
				<div id="q_tab01" class="tab_cont" style="display: block;">
					
					
					<ul class="q_list">
						<li>이전 주문 내역이 없습니다.</li>
					</ul>
					
				</div>

				<div id="q_tab02" class="tab_cont" style="display: none;">
					
					
					<ul class="q_list">
						<li>이전 주문 내역이 없습니다.</li>
					</ul>
					
				</div>
				<p class="mt10 t_center">
					<a href="#" class="button w180 q_close" title="창닫기">창닫기 <span class="gt">&gt;</span></a>
					<!--<a href="#" class="button red w180">전체메뉴 보기 <span class="gt">&gt;</span></a>-->
				</p>
			</section>
		</li>
	</ul>
</section>
