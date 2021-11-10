<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
int totalPrice=(int)request.getAttribute("totalPrice");
List<Map<String, Object>>maps=(List<Map<String, Object>>)request.getAttribute("maps");
Map<String,Object>user=(Map<String,Object>)request.getAttribute("user");
%>
<html>
<head>
	<title>payPage</title>
	<script src="https://tbnpg.settlebank.co.kr/resources/js/SettlePG.js"></script>
</head>
<body onload="setInitialization();" style="">
<%@ include file="../common/header.jsp" %>

	<!-- skipNavi -->

	<!-- //skipNavi -->

	<!-- wrap -->
	<div id="wrap">
		<!-- header -->

<script src="/js/mrpizza/common/rsamode.js"></script>

<script>
	var RSAModulus = "a71f8350dc67f20823e6c6e57b10e2ac08015ed7a0176ac4b8ab3e02030f89c7fa17206c2abb216d79c975838b6f026d0f2553b6eb88f9171bae4f3825446f833ae86f92014b1770feabb8622b8314a0000ad60a13db56f74447759e507f71cd260f49435fbd1ec1744998536b678497832f219c0c37a5386077b590fbe5615b";
	var RSAExponent = "10001";
</script>
<form id="encModeForm" hidden="true">
</form>



	<!-- 무료제공 프로모션 팝업 -->
	

<script type="text/javascript">
	function PromotionTypeChk(type){ //type 노출되는 항목갯수

		var eventchk400 = $("input:radio[name=eventchk400]:checked").val();
		console.log("eventchk400 = "+eventchk400);
		if(typeof eventchk400 === "undefined"){
			alert("피자만 구매하기 또는 증정품 중 한가지를 선택해 주세요.");
			return;			
		}else if(eventchk400 == "other"){
			//일반 진행
			chkSelectionPromotion('other');
			return;
		}else if(eventchk400 == "PM0470_Large_1"){
			setCartDataFreeMenu('sideproductItem_400_Large_1', '', '');	
			/*팝업 닫기*/
			alert("[무료]오븐미트스파게티+베이크윙 8조각은 피자 수량만큼 변경하실 수 있습니다.");
			$("#pop_order_event_Large").hide();
			$(".bgLayer").hide();				
		}
		else if(eventchk400 == "PM0470_Regular_2"){
			setCartDataFreeMenu('sideproductItem_400_Regular_2', '', '');	
			/*팝업 닫기*/
			alert("[무료]증정품은 미피떡볶이, 홈샐러드(9구) 중 한가지만 선택하여 주문 가능합니다.\n미피떡볶이는 피자 수량만큼 변경하실 수 있습니다.");
			$("#pop_order_event_Regular").hide();
			$(".bgLayer").hide();	
		}
		else if(eventchk400 == "PM0470_Regular_3"){
			setCartDataFreeMenu('sideproductItem_400_Regular_3', '', '');	
			/*팝업 닫기*/
			alert("[무료]증정품은 미피떡볶이, 홈샐러드(9구) 중 한가지만 선택하여 주문 가능합니다.\n홈샐러드(9구)는 피자 수량만큼 변경하실 수 있습니다.");
			$("#pop_order_event_Regular").hide();
			$(".bgLayer").hide();	
		}
	}	
	
</script>	

<style>
.pop_newPromotion1906 .select_item label {width:233px}
.pop_newPromotion210219.promotion_L .select_item label.item0 .lbl{background:url(//cdn.mrpizza.co.kr/2014_resources/images/popup/new_menu_imgs/l_20210219_item_1_off.jpg) no-repeat;}
.pop_newPromotion210219.promotion_L .select_item label.item0 input:checked+.lbl{background: url('//cdn.mrpizza.co.kr/2014_resources/images/popup/new_menu_imgs/l_20210219_item_1_on.jpg') no-repeat;}
.pop_newPromotion210219.promotion_L .select_item label.item2 .lbl{background:url(//cdn.mrpizza.co.kr/2014_resources/images/popup/new_menu_imgs/l_20210219_item_2_off.jpg) no-repeat;}
.pop_newPromotion210219.promotion_L .select_item label.item2 input:checked+.lbl{background: url('//cdn.mrpizza.co.kr/2014_resources/images/popup/new_menu_imgs/l_20210219_item_2_on.jpg') no-repeat;}
.pop_newPromotion210219.promotion_L .select_item label.item3 .lbl{background:url(//cdn.mrpizza.co.kr/2014_resources/images/popup/new_menu_imgs/l_20210413_item_3_off.jpg) no-repeat;}
.pop_newPromotion210219.promotion_L .select_item label.item3 input:checked+.lbl{background: url('//cdn.mrpizza.co.kr/2014_resources/images/popup/new_menu_imgs/l_20210413_item_3_on.jpg') no-repeat;}
</style>

<!-- 프리미엄 9종 L 주문시 -->
<article class="pop_wrap pop_layer pop_newPromotion1906 pop_newPromotion210219 promotion_L hide" id="pop_order_event_Large">
	<h2>미스터피자</h2>
	<a href="#" class="btn_close pop_close">닫기</a>
	<div>
		<h5 class="event_tit">
		프리미엄피자 L을 주문하면,<br>
		스페셜 사이드메뉴 세트가 공짜!
		</h5>
		<div class="select_item">
			<label class="item1">
				<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="other">
				<span class="lbl">피자만 구매하기 (제휴 할인 가능)</span>
			</label>
			<div class="item_select">
				<h5>행복드림</h5>
				
				<label class="item3">
					<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="PM0470_Large_1">
					<span class="lbl">오븐치킨</span>
				</label>
				<p style="font-size: 12px;position: absolute;bottom: 11px;left: 0;width: 100%;">※ 일부 매장은 해당 사이드 대신 오븐치킨으로 제공됩니다.</p>
				
			</div>
			<div class="pop_text">
				<p>행복드림은 제휴, 쿠폰 등 다른 할인과 중복 적용되지 않습니다.</p>
				<p>행사제품 복수 주문 시 사이드를 2종 이상 선택하시는 경우 콜센터(1577-0077)주문 부탁드립니다. <br>(온라인주문은 1판씩&nbsp;개별 주문 접수 시 이용 가능)</p>
			</div>
			
		</div>
		<input type="radio" name="sideproductItem_400_Large_1" id="sideproductItem_400_1" value="2305020130|||0|||1" style="display:none;" title="프로모션 메뉴정보 확인(오븐미트스파게티+베이크윙 8조각)" checked="">
		<p class="button_wrap">
			<a href="javascript:PromotionTypeChk('400')" class="btn_submit">확인</a>
		</p>
	</div>
</article>


<!-- 프리미엄 9종 R 주문시 -->
<article class="pop_wrap pop_layer pop_newPromotion1906 pop_newPromotion201117 promotion_R hide" id="pop_order_event_Regular">
	<h2>미스터피자</h2>
	<a href="#" class="btn_close pop_close">닫기</a>
	<div>
		<h5 class="event_tit">
		프리미엄피자 R을 주문하면,<br>
		스페셜 사이드메뉴 1종이 공짜!
		</h5>
		<div class="select_item">
			<label class="item1">
				<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="other">
				<span class="lbl">피자만 구매하기</span>
			</label>
			<div class="item_select">
				<h5>행복드림(택1)</h5>
			 
				<label class="item2">
					<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="PM0470_Regular_2">
					<span class="lbl">미피떡볶이</span>
				</label>
				<label class="item3">
					<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="PM0470_Regular_3">
					<span class="lbl">홈샐러드(9구)</span>
				</label>
				
			</div>
			<div class="pop_text">
				<p>행복드림은 제휴, 쿠폰 등 다른 할인과 중복 적용되지 않습니다.</p>
				<p>행사제품 복수 주문 시 사이드를 2종 이상 선택하시는 경우 콜센터(1577-0077)주문 부탁드립니다. <br> (온라인주문은 1판씩&nbsp;개별 주문 접수 시 이용 가능)</p>
				<p>매장의 샐러드바 마감으로 인해, 홈샐러드는 오후 9시 20분까지만 주문 가능합니다.</p>
			</div>
		</div>
		<input type="radio" name="sideproductItem_400_Regular_2" id="sideproductItem_400_R_2" value="2305010080|||0|||1" style="display:none;" title="프로모션 메뉴정보 확인(미피떡볶이)" checked="">
		<input type="radio" name="sideproductItem_400_Regular_3" id="sideproductItem_400_R_3" value="0204023320|||0|||1" style="display:none;" title="프로모션 메뉴정보 확인(홈샐러드(9구))" checked="">
		<p class="button_wrap">
			<a href="javascript:PromotionTypeChk('400')" class="btn_submit">확인</a>
		</p>
	</div>
</article>



	<!-- //무료제공 프로모션 팝업 -->
	
	<!-- 무료제공 프로모션 팝업_두번째 -->
	

<script type="text/javascript">
	function PetPromotionTypeChk(type){ //type 노출되는 항목갯수

		var eventchk400 = $("input:radio[name=eventchk400]:checked").val();
		
		if(eventchk400 === "undefined"){
			alert("항목을 선택해주세요.");
			return;			
		}else if(eventchk400 == "other"){
			//일반 진행
			chkSelectionPromotion('other');
			return;
		}else if(eventchk400 == "PM0460_Large_1"){
			setCartDataFreeMenu('petsideproductItem_400_Large_1', '', '');	
			/*팝업 닫기*/
			alert("[무료]증정품은 펫치블스, 펫페퍼로니 중 한가지만 선택하여 주문 가능합니다.\n펫치블스는 피자 수량만큼 변경하실 수 있습니다.");
			$("#pop_order_event_pet_Large").hide();
			$(".bgLayer").hide();				
		}else if(eventchk400 == "PM0460_Large_2"){
			setCartDataFreeMenu('petsideproductItem_400_Large_2', '', '');	
			/*팝업 닫기*/
			alert("[무료]증정품은 펫치블스, 펫페퍼로니 중 한가지만 선택하여 주문 가능합니다.\n펫페퍼로니는 피자 수량만큼 변경하실 수 있습니다.");
			$("#pop_order_event_pet_Large").hide();
			$(".bgLayer").hide();				
		}
	}	
	
</script>	

<!-- 핫앤뉴 7종 L 주문시 -->
<article class="pop_wrap pop_layer pop_newPromotion1906 pop_newPromotion1909 promotion_L hide" id="pop_order_event_pet_Large">
	<h2>미스터피자</h2>
	<a href="#" class="btn_close pop_close">닫기</a>
	<div>
		<h5 class="event_tit">
		핫앤뉴 7종 (L)를 주문하면,<br>
		펫피자가 공짜!
		</h5>
		<div class="select_item">
			<label class="item1">
				<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="other">
				<span class="lbl">피자만 구매하기 (제휴 할인 가능)</span>
			</label>
			<div class="item_select">
				<h5>펫피자 받기(택1)</h5>
				<label class="item2">
					<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="PM0460_Large_1">
					<span class="lbl">펫 치블스</span>
				</label>
				<label class="item3">
					<input type="radio" name="eventchk400" id="" title="프로모션 메뉴정보" value="PM0460_Large_2">
					<span class="lbl">펫페러로니</span>
				</label>
			</div>
			<div class="pop_text">
				<p>펫피자 증정 프로모션은 제휴, 쿠폰 등 다른 할인과 중복적용되지 않습니다.</p>
				<p>온라인 주문 시 펫피자 증정 프로모션은 왕창드림 프로모션과 함께 주문이 되지 않습니다. 각각 별도 주문해주시기 바랍니다.</p>
				<p>매장 별 상황에 따라 선택하신 펫피자가 품절일 경우, 다른 품목으로 대체 증정될 수 있습니다.</p>
			</div>
			
		</div>
		<input type="radio" name="petsideproductItem_400_Large_1" id="petsideproductItem_400_1" value="2305020060|||0|||1" style="display:none;" title="프로모션 메뉴정보 확인(펫치블스)" checked="">
		<input type="radio" name="petsideproductItem_400_Large_2" id="petsideproductItem_400_2" value="2305020070|||0|||1" style="display:none;" title="프로모션 메뉴정보 확인(펫페퍼로니)" checked="">
		<p class="button_wrap">
			<a href="javascript:PetPromotionTypeChk('400')" class="btn_submit">확인</a>
		</p>
	</div>
</article>


	<!-- //무료제공 프로모션 팝업 -->
	
		<!-- //header -->

		<script type="text/javascript">
			//StartSmartUpdate(); // inicis
		</script>

		<!-- container -->
		<div id="container" class="full_cont">
			<!-- lnb -->
			<aside id="lnb">
				<h1>장바구니</h1>
			</aside>
			<!-- //lnb -->

			<!-- contents -->
			<section id="contents">
				<ul id="location">
					<li><a class="home" href="/index">HOME</a></li>
					<li><strong>결제하기</strong></li>
				</ul>

				<div class="order_top">
					<div class="tit">
						<h1>결제하기</h1>
						<p>
							
							주문유형 : <span class="t_blue">배달주문</span>
							
							
							
						</p>
						<p>
							<a href="javascript:void(0);" onclick="javscript:setOrderTypeSelection(1, 'move');" class="button h30 p10 white"><img src="//cdn.mrpizza.co.kr/2014_resources/images/order/ic_reset.gif" alt="주문유형변경 아이콘"> 주문유형변경</a>
						</p>
					</div>
					<ul>
						<!-- on클래스로 제어 -->
						<li class="bg1"><p>장바구니</p></li>
						<li class="bg2 on"><p>결제하기</p></li>
						<li class="bg3"><p>주문완료</p></li>
					</ul>
				</div>

				<form id="inicisForm" name="inicisForm" action="/order/inicisNewStart" method="post" target="IfrmProc">
					
					<!-- 
					<input type="hidden" id="tabMenu2Index" name="tabMenu2Index" value="0" />
					<input type="hidden" id="coupon_type" name="coupon_type" value="" />
					<input type="hidden" id="card_number" name="card_number" value="" />
					<input type="hidden" id="cust_lic" name="cust_lic" value="" />
					<input type="hidden" id="card_gubun" name="card_gubun" value="" />
					<input type="hidden" id="card_yymm" name="card_yymm" value="" />
					<input type="hidden" id="mode" name="mode" value="" />
					<input type="hidden" id="gopaymethod" name="gopaymethod" value="" />
					<input type="hidden" id="check_url" name="check_url" value="" />
					<input type="hidden" id="apply_url" name="apply_url" value="" />
					<input type="hidden" id="sms_flag" name="sms_flag" value="Y" />
					<input type="hidden" id="sms_guest_flag" name="sms_guest_flag" value="N" />
					<input type="hidden" id="e_coupon_id" name="e_coupon_id" value="" />
					<input type="hidden" id="e_coupon_cd" name="e_coupon_cd" value="" />
					<input type="hidden" id="e_coupon_type" name="e_coupon_type" value="" />
					<input type="hidden" id="smart_coupon_id" name="smart_coupon_id" value="" />
					<input type="hidden" id="smart_coupon_price" name="smart_coupon_price" value="" />
					<input type="hidden" id="mobile_phone" name="mobile_phone" value="" />
					<input type="hidden" name="kakaopay_cid" value="" />
					<input type="hidden" name="samsungSetFlag" value="N" />
					<input type="hidden" name="GoodsName" value="미스터피자 온라인 주문"/>
					 -->
					<input type="hidden" id="tabMenu2Index" name="tabMenu2Index" value="0">
					<input type="hidden" id="coupon_type" name="coupon_type" value="">
					<input type="hidden" id="card_number" name="card_number" value="">
					<input type="hidden" id="cust_lic" name="cust_lic" value="">
					<input type="hidden" id="card_gubun" name="card_gubun" value="">
					<input type="hidden" id="card_yymm" name="card_yymm" value="">
					<input type="hidden" id="mode" name="mode" value="">
					<input type="hidden" id="gopaymethod" name="gopaymethod" value="">
					<input type="hidden" id="check_url" name="check_url" value="">
					<input type="hidden" id="apply_url" name="apply_url" value="">
					<input type="hidden" id="sms_flag" name="sms_flag" value="Y">
					<input type="hidden" id="sms_guest_flag" name="sms_guest_flag" value="N">
					<input type="hidden" id="e_coupon_id" name="e_coupon_id" value="">
					<input type="hidden" id="e_coupon_cd" name="e_coupon_cd" value="">
					<input type="hidden" id="e_coupon_type" name="e_coupon_type" value="">
					<input type="hidden" id="smart_coupon_id" name="smart_coupon_id" value="">
					<input type="hidden" id="smart_coupon_price" name="smart_coupon_price" value="">
					<input type="hidden" id="mobile_phone" name="mobile_phone" value="">
					<input type="hidden" name="kakaopay_cid" value="">
					<input type="hidden" name="samsungSetFlag" value="N">
					<input type="hidden" name="GoodsName" value="미스터피자 온라인 주문">
					<input type="hidden" name="goodname" value="미스터피자 온라인 주문">
					<input type="hidden" name="oid" value="">
					<input type="hidden" name="version" value="1.0">
					<input type="hidden" name="mid" value="">
					<input type="hidden" name="price" value="">
					<input type="hidden" name="currency" value="WON">
					<input type="hidden" name="buyername" value="">
					<input type="hidden" name="buyertel" value="">
					<input type="hidden" name="buyeremail" value="">
					<input type="hidden" name="timestamp" value="">
					<input type="hidden" name="signature" value="">
					<input type="hidden" name="returnUrl" value="https://www.mrpizza.co.kr/order/inicisNewReturn">
					<input type="hidden" name="mKey" value="">
					<input type="hidden" name="offerPeriod" value="">
					<input type="hidden" name="acceptmethod" value="">
					<input type="hidden" name="languageView" value="">
					<input type="hidden" name="charset" value="">
					<input type="hidden" name="payViewType" value="">
					<input type="hidden" name="closeUrl" value="https://www.mrpizza.co.kr/order/inicisNewClose">
					<input type="hidden" name="quotabase" value="">
					<input type="hidden" name="ini_onlycardcode" value="">
					<input type="hidden" name="ini_cardcode" value="">
					<input type="hidden" name="ansim_quota" value="">
					<input type="hidden" name="INIregno" value="">
					<input type="hidden" name="merchantData" value="">
				</form>

				<form id="paymentForm" name="paymentForm">
					<input type="hidden" id="order_type" name="order_type" value="1"> <input type="hidden" id="branch_id" name="branch_id" value="108910">
					<input type="hidden" id="branch_nm" name="branch_nm" value="압구정점"> <input type="hidden" id="reserv_time" name="reserv_time" value=""> <input type="hidden" id="reserv_time_text" name="reserv_time_text" value=""> <input type="hidden" id="order_memo" name="order_memo" value=""> <input type="hidden" id="naverpay_receipt" name="naverpay_receipt" value=""> <input type="hidden" id="list_price" name="list_price" value="27900"> <input type="hidden" id="disc_price" name="disc_price" value="0"> <input type="hidden" id="payment_price" name="payment_price" value="27900"> <input type="hidden" id="total_price" name="total_price" value="27,900"> <input type="hidden" id="mobileGift_desc_price" name="mobileGift_desc_price" value="0">
					<input type="hidden" id="card_desc_price" name="card_desc_price" value="0"> <input type="hidden" id="promo_desc_price" name="promo_desc_price" value="0"> <input type="hidden" id="coupon_desc_price" name="coupon_desc_price" value="0"> <input type="hidden" id="eCoupon_desc_price" name="eCoupon_desc_price" value="0"> <input type="hidden" id="onlineGift_desc_price" name="onlineGift_desc_price" value=""> <input type="hidden" id="pay_amt" name="pay_amt" value="<%=totalPrice%>"> <input type="hidden" id="guest_flag" name="guest_flag" value="N"> <input type="hidden" id="guest_auth_flag" name="guest_auth_flag" value="N"> <input type="hidden" id="eCoupon_otherDisc" name="eCoupon_otherDisc" value=""> <input type="hidden" id="notSaleProductCount" name="notSaleProductCount" value="0"> <input type="hidden" id="beselo_flag" name="beselo_flag" value="">
					<!-- <input type="hidden" id="honey_flag" name="honey_flag" value=""/> -->
					<input type="hidden" id="alarm_flag" name="alarm_flag" value=""> <input type="hidden" id="select_dc_opt" name="select_dc_opt" value="">
					
					<input type="hidden" id="newYear" name="newYear" value="2016-09-15">
					<input type="hidden" id="delivery_section" name="delivery_section" value="C-2"> <input type="hidden" id="full_address" name="full_address" value="서울 강남구 가로수길 9 어딕나/신사동 536-9">

					<div class="order_wrap">
						<section class="l_order">
							<h2 class="cont_tit tit2">1.고객정보</h2>

							<table class="tbl_style mt10 myInfo_form">
								<caption>고객정보 입력폼</caption>
								<colgroup>
									<col style="width: 170px">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row"><i class="t_red normal">*</i> 주문유형</th>
										
											<td class="t_left"><span class="line35">배달주문</span></td>
										
										
										
									</tr>

									
										<!-- 배달주문 주문시간 -->
										<tr>
											<th scope="row"><i class="t_red normal">*</i> <label for="OrderType">주문시간</label></th>
											<td class="t_left"><label> <input name="OrderType" id="OrderType" type="radio" class="radio" value="N" onclick="javajscript:chkOrderType(this.value);" checked=""> <span class="lbl">바로주문</span>
											</label>  <!-- 
										<select class="sel4" title="배달시간 분 선택">
											<option value="">분</option>
										</select>
										 --> </td>
										</tr>
										<!-- /배달주문 주문시간 -->
									

									

									

									
										<!-- 배달주문, 포장주문 수령인 정보 -->
										<tr>
											<th scope="row"><i class="t_red normal">*</i> <label for="gift_from_nm">수령인</label></th>
											<td class="t_left">
											<input type="text" id="gift_from_nm" name="gift_from_nm" class="inp4" placeholder="수령인이름 입력" title="수령인이름 입력" maxlength="10" value="<%=user.get("NAME")%>">
											</td>
										</tr>
										<!-- /배달주문, 포장주문 수령인 정보 -->
									
									

									<!-- 회원 -->
									
										<!-- 배달주문, 포장주문 연락처 -->
										<tr>
											<th scope="row"><i class="t_red normal">*</i> <label for="view_delivery_phone1">연락처</label></th>
											<td class="t_left"><input type="text" id="view_delivery_phone1" name="view_delivery_phone1" value="<%=user.get("MOBILE1") %>" class="inp6" onkeyup="chkNum(this);"  title="연락처 앞자리 입력"> 
											<input type="text" id="view_delivery_phone2" name="view_delivery_phone2"  value="<%=user.get("MOBILE2") %>" class="inp6" onkeyup="chkNum(this);" maxlength="4" title="연락처 중간자리 입력"> 
											<input type="text" id="view_delivery_phone3" name="view_delivery_phone3"  value="<%=user.get("MOBILE3") %>" class="inp6" onkeyup="chkNum(this);" maxlength="4" title="연락처 뒷자리 입력"> 
											<label class="ml16">
											</label></td>
										</tr>
										<!-- /배달주문, 포장주문 연락처 -->
									
									
									<!-- /회원 -->

									<!-- 비회원 -->
									
									<!-- /비회원 -->

									

									
										<tr>
											<th scope="row"><i class="t_red normal">*</i> 주소</th>
											<td class="t_left">
												<div>
													<span class="line30"><%=user.get("ADDR") %></span>
												</div>
											</td>
										</tr>
									

									<tr>
										<th scope="row"><i class="t_red normal">*</i> 매장정보</th>
										<td>
											<table class="tbl_style2">
												<caption>배달매장 - 매장명, 주소, 전화번호, 매장보기 등 확인</caption>
												<colgroup>
													<col style="width: 100px">
													<col>
													<col style="width: 100px">
													<col style="width: 80px">
												</colgroup>
												<thead>
													<tr>
														<th scope="col" class="t_center">매장명</th>
														<th scope="col" class="t_center">주소</th>
														<th scope="col" class="t_center">전화번호</th>

													</tr>
												</thead>
												<tbody>
													<tr>
														<td>압구정점</td>
														<td class="t_left">서울특별시 강남구 신사동 574-7 2층</td>
														<td>02-512-1161</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>

									
										<!-- 배달주문, 포장주문 요청사항 -->
										<tr>
											<th scope="row"><label for="ordermemo">요청사항</label></th>
											<td class="t_left">
												<p>
													<input type="text" id="ordermemo" name="ordermemo" style="width: 546px;" placeholder="요청 메시지를 입력해주세요" maxlength="30" title="요청 메시지 입력">
												</p>
												<ul class="txt_list mt5">
													<li>특정 프로모션 시 배달 시간을 요청하셔도 해당 시간에 배달이 어렵습니다. 양해 부탁
														드립니다.</li>
												</ul>
											</td>
										</tr>
										<!-- /배달주문, 포장주문 요청사항 -->
									

									

								</tbody>
							</table>

							<h2 class="cont_tit tit2 mt60">
								2.할인수단
								
							</h2>
							<p class="pt5">
								
							</p>
							<ul class="tabmenu2 mt10 tabMotion">
								<li class="w100"><span>쿠폰입력</span></li>
								
							</ul>
							
							<input type="hidden" id="sizeup_flag">


							<script type="text/javascript">
							//임시스크립트
							$(function(){
								$('.dc_opt').click(function(){
									var _val = $(this).val();
									$(this).parents('li').addClass('on').siblings().removeClass('on');	//현재 탭 라디오 버튼 선택 처리 및 나머지 선택 해제
									$('#select_dc_opt').val(_val);		//현재 선택된 할인수단 타입 저장
									
									//html load
									if(_val == 'skti' || _val == 'yt20' || _val == 'sktlt50' || _val == 'ktf' || _val == 'ktf50' || _val == 'ktfdb' || _val == 'lgti' 
											|| _val == 'ocb' || _val == 'skti01' || _val == 'skti02' || _val == 'lgti40' || _val == 'skti30' || _val == 'ocb50') {
										
										getMobileAjaxRequest('discount_mobile_contents', _val);
										setHiddenView('mobile');
									} else if(_val == 'spb' || _val == 'hdc' || _val == 'bct' || _val == 'woori' || _val == 'bcday50' || _val =='bc15' ||_val =='lotte15' || _val =='lotte40') {
										getMobileAjaxRequest('discount_card_contents', _val);
										setHiddenView('card');
										$('input[name="payGroup"]:radio:input[value="2"]').attr('checked', true);
									} else if(_val == 'tsc' || _val == 'wmd' || _val == 'take' || _val == 'take30' || _val == 'take40' || _val == 'top5' || _val == 'osole' 
											|| _val == 'shinhan30' || _val == 'renewal' || _val == 'syrup' || _val == 'sizeup' || _val == 'dream' 
											|| _val == 'hanac' || _val == 'kakaoc' || _val == 'ktc' || _val == 'bcsuc' || _val == 'bcday40' || _val == 'yearend') {
										getMobileAjaxRequest('discount_promotion_contents', _val);
										setHiddenView('promotion');
										if(_val == 'bcday40') {
											$('input[name="payGroup"]:radio:input[value="2"]').attr('checked', true);
										}
									} else if(_val == 'smc' || _val == 'bccoop' || _val == 'lgucoop' || _val == 'ktshow' || _val == 'newbccoop' || _val == 'graduatecoop' || _val == 'specialdaycoop'
											||  _val == 'bc1salecoop' || _val == 'uservip50' || _val == 'hongcho' || _val == 'mpfriends' || _val == 'newmpfriends' || _val == 'mplpizza'
												 || _val == 'zlgoonpay') {
										getMobileAjaxRequest('discount_coupon_contents', _val);
										setHiddenView('coupon');
										if(_val == 'newbccoop' || _val == 'graduatecoop'  || _val == 'bc1salecoop' ) {
											$('input[name="payGroup"]:radio:input[value="2"]').attr('checked', true);
										}										
									} else if (_val == 'familygift') {
										getMobileAjaxRequest('discount_gift_contents', _val);
										setHiddenView('gift');
										
									} else if(_val == "welfare15") {
										getMobileAjaxRequest('discount_welfare_contents', _val);
										setHiddenView('welfare');
									} else {
										
										var frm = document.getElementById("paymentForm");
										
										//주문에 E쿠폰(제품권) 제품이 있을 경우 제휴할인 가능한지 체크함.
										if(frm.eCoupon_otherDisc.value) {
											
											if(frm.eCoupon_otherDisc.value == 'N') {
												$('input[name="dc_opt"]').attr('checked', false);
												$('#select_dc_opt').val("");		//현재 선택된 할인수단 타입 초기화
												alert('E-쿠폰 외 ‘피자’ 포함 추가 주문시에만 통신&제휴/카드 할인 가능합니다.');
												return;
											} else if(frm.eCoupon_otherDisc.value == 'Y') {
												$('input[name="dc_opt"]').attr('checked', false);
												$('#select_dc_opt').val("");		//현재 선택된 할인수단 타입 초기화
												alert('E-쿠폰 사용시에는 프로모션/쿠폰 할인을 이용하실 수 없습니다.');
												return;
											}
										}
										
										if($("#sizeup_flag").val() == "Y"){
											$('input[name="dc_opt"]').attr('checked', false);
											$('#select_dc_opt').val("");		//현재 선택된 할인수단 타입 초기화
											alert('사이즈업 할인시 타 할인 적용 불가 합니다.');
											return;
										}
										
										
										$('#' + _val).show().siblings('.tab_cont').hide();
										setHiddenView('coupon');
									}
									
									//할인수단별 할인 관련 url 가져오기
									if(_val != 'mycoupon' && _val != 'skti01' && _val != 'skti02') {
										getDiscountTypeURL(_val);
									}
									//사이즈업 프로모션인경우에는 변경되지않도록
									if($("#sizeup_flag").val() != 'Y'){
										$('#order_list_price').html($('#total_price').val()+'원');
										$('#order_mobile_desc_price').html('-'+$('#mobileGift_desc_price').val()+'원');
										$('#order_card_desc_price').html('-'+$('#card_desc_price').val()+'원');
										$('#order_promo_desc_price').html('-'+$('#promo_desc_price').val()+'원');
										$('#order_coupon_desc_price').html('-'+$('#coupon_desc_price').val()+'원');
										$('#order_ecoupon_desc_price').html('-'+$('#eCoupon_desc_price').val()+'원');
										$('#order_payment_price').html($('#pay_amt').val());
									}
									
									//다른 탭 라디오 버튼 선택 해제 처리
									$(this).parents('li').parents('div').siblings().find('ul').find('li').removeClass('on');
								});
							});
						</script>

							<!-- 통신&제휴할인 -->
							<!--  
						<div id="tabCon1" class="discount_box tab_cont" style="background:#fff">
							tttt
						</div>
						-->
							
								
								
						<input type="text" id="coupons" placeholder="쿠폰번호를 입력해주세요">
								
							

							<!-- /통신&제휴할인 -->

							<!-- 카드할인 -->
							
								
								
									
							
							<!-- 웰페어 -->
							

							

							<h2 class="cont_tit tit2 mt60">3.결제수단</h2>

							<table class="tbl_style mt10 myInfo_form">
								<caption>결제수단 입력폼</caption>
								<colgroup>
									<col style="width: 170px">
									<col>
								</colgroup>
								<tbody>
									
										

										<!-- 퍼블리셔 임시스크립트: 옴겨주세요 -->
										<script type="text/javascript">
									$(function(){
										$('input[name=cash_user]').change(function(){
											var _val = $(this).val();											
											$('#'+ _val).show().siblings('div').hide();
										});
									});
								</script>
										<tr id="cashBox" class="hide">
											<th scope="row"><label for="cash_user">현금영수증</label></th>
											<td class="t_left">
												<div class="cash_box">
													<ul>
														<li><label> <input id="cash_user" name="cash_user" type="radio" class="radio" value="cashCon1"> <span class="lbl">개인</span>
														</label></li>
														<li><label> <input name="cash_user" type="radio" class="radio" value="cashCon2"> <span class="lbl">사업자</span>
														</label></li>
													</ul>
													<div id="cashCon1" class="hide mt20">
														<p>
															<input type="text" id="cash_phone1" name="cash_phone1" class="inp6" maxlength="3" onkeyup="javascript:chkFocusMove('cash_phone1', 'cash_phone2', 3);" onkeydown="javascript:chkNum(this);" title="연락처 앞자리 입력" placeholder="010" value="010"> 
															<input type="text" id="cash_phone2" name="cash_phone2" class="inp6" maxlength="4" onkeyup="javascript:chkFocusMove('cash_phone2', 'cash_phone3', 4);" onkeydown="javascript:chkNum(this);" title="연락처 중간자리 입력">
															<input type="text" id="cash_phone3" name="cash_phone3" class="inp6" maxlength="4" onkeydown="javascript:chkNum(this);" title="연락처 뒷자리 입력">
														</p>
														<p class="t_red">현금 영수증 받을 전화번호를 입력해주세요.</p>
														<ul class="txt_list mt10">
															<li>현금 영수증 발급 받을 전화번호 입력</li>
															<li>배달직원에게 직접 현금으로 지불하여 주세요.</li>
														</ul>
													</div>
													<div id="cashCon2" class="hide mt20">
														<p>
															<input type="text" id="cash_licensee" name="cash_licensee" class="inp4" maxlength="10" onkeydown="javascript:chkNum(this);" placeholder="사업자번호 입력" title="사업자번호 입력">
														</p>
														<p class="t_red">현금 영수증 받을 사업자 번호를 입력해주세요.</p>
														<ul class="txt_list mt10">
															<li>현금 영수증 발급 받을 사업자 번호 입력</li>
															<li>배달직원에게 직접 현금으로 지불하여 주세요</li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
									
									<tr>
										<th scope="row"><label for="payGroup">인터넷 결제</label></th>
										<td class="t_left">
										<label> 
										<input name="payGroup" id="payGroup" value="1" type="radio" class="radio" onclick="javascript:payment();"> 
										<span class="lbl">신용카드</span>
										</label>
										 <label class="ml30">
										  <input name="payGroup" id="payGroup2" value="2" type="radio" class="radio" onclick="javascript:payment();"> 
										  <span class="lbl">가상계좌</span>
										</label> 
										<label class="ml30"> <input name="payGroup" id="payGroup3" value="3" type="radio" class="radio" onclick="javascript:payment();"> 
										<span class="lbl"><img src="https://cdn.mrpizza.co.kr/2014_resources/images/order/img_kakaopay_new.png" alt="카카오페이"></span>
										</label>
										
												
											
										</td>
									</tr>

									<tr id="naverpaycashBox" class="hide">
										<th scope="row"><label for="cash_user">네이버페이
												현금영수증</label></th>
										<td class="t_left">
											<div class="cash_box">
												<ul>
													<li><label> <input name="navercash_user" type="radio" class="radio" value="navercashCon1"> <span class="lbl">개인</span>
													</label></li>
													<li><label> <input name="navercash_user" type="radio" class="radio" value="navercashCon2"> <span class="lbl">사업자</span>
													</label></li>
													<li><label> <input name="navercash_user" type="radio" class="radio" value="navercashCon3"> <span class="lbl">선택안함</span>
													</label></li>
												</ul>
												<div id="navercashCon1" class="hide mt20">
													<p>
														<input type="text" id="navercash_phone1" name="navercash_phone1" class="inp6" maxlength="3" onkeyup="javascript:chkFocusMove('cash_phone1', 'cash_phone2', 3);" onkeydown="javascript:chkNum(this);" title="연락처 앞자리 입력" value="010"> 
														<input type="text" id="navercash_phone2" name="navercash_phone2" class="inp6" maxlength="4" onkeyup="javascript:chkFocusMove('cash_phone2', 'cash_phone3', 4);" onkeydown="javascript:chkNum(this);" title="연락처 중간자리 입력">
														<input type="text" id="navercash_phone3" name="navercash_phone3" class="inp6" maxlength="4" onkeydown="javascript:chkNum(this);" title="연락처 뒷자리 입력">
													</p>
													<p class="t_red">현금 영수증 받을 전화번호를 입력해주세요.</p>
													<ul class="txt_list mt10">
														<li>현금 영수증 발급 받을 전화번호 입력</li>
													</ul>
												</div>
												<div id="navercashCon2" class="hide mt20">
													<p>
														<input type="text" id="navercash_licensee" name="navercash_licensee" class="inp4" maxlength="10" onkeydown="javascript:chkNum(this);" placeholder="사업자번호 입력" title="사업자번호 입력">
													</p>
													<p class="t_red">현금 영수증 받을 사업자 번호를 입력해주세요.</p>
													<ul class="txt_list mt10">
														<li>현금 영수증 발급 받을 사업자 번호 입력</li>
													</ul>
												</div>

												<div id="navercashCon3" class="hide mt20">
													<ul class="txt_list mt10">
														<li>네이버페이 현금 영수증 발급안함.</li>
													</ul>
												</div>
											</div>
										</td>
									</tr>

								</tbody>
							</table>
							<p class="mt20">
								<a href="/app/buket" class="button h30 w80 white btn_back"><span class="gt">&lt;</span> 뒤로</a>
								<!--a href="javascript:orderLog();" class="button h30 w80 white btn_back"><span class="gt">&lt;</span> 클릭</a> -->
							</p>
						</section>
						<section class="r_order">
							<div class="pay_box" style="top: 30px;">
								<h1>주문계산</h1>
								<div class="store">
									<p>
										<strong>압구정점</strong>
									</p>
									
										
									
									
								</div>
								<ul class="p_list">
									
											<%for(Map<String,Object>map:maps){
											%>
				
										<li>
											<span class="name"><%=map.get("CMENU") %></span>
											
											
												
												
												
														
															<span class="t_org"><%=map.get("CSIZE") %></span>
														
													
												
											
											
											<span class="<%=map.get("CNUM")%>num"><%=map.get("CCOUNT") %></span>
											<strong class="<%=map.get("CNUM")%>price"><%=map.get("price") %></strong>
											
											
										</li>
									<%}%>
									

								</ul>
								<ul class="dc_pay">
									<li>주문금액 <strong id="order_list_price"><%=totalPrice %>원</strong></li>
									<li>쿠폰할인 <strong id="order_coupon_desc_price">-0원</strong></li>									<!--<li class="t_org">온라인제품권 <strong>원</strong></li>-->
								</ul>
								<div class="price">
									<b>결제예정금액</b> <strong><span id="order_payment_price"><%=totalPrice %></span>원</strong>
								</div>
								<p class="t_center">
									<a href="#" onclick="requestOrder()" class="button h40 red pay_btn" id="pay_btn">결제하기 <span class="gt">&gt;</span></a>
								</p>
							</div>
						</section>
						<div class="clear"></div>
					</div>
				</form>
				<input type="hidden" id="item_count" value="1">
				<input type="hidden" id="f_order_payment_price" value="27900">
				<input type="hidden" id="f_order_coupon_desc_price" value="">
			</section>

			<iframe name="IfrmProcDisc" id="IfrmProcDisc" style="width: 0px; height: 0px;" title="빈프레임 내용없음"></iframe>
			<iframe name="IfrmProc" id="IfrmProc" style="width: 0px; height: 0px;" title="빈프레임 내용없음"></iframe>
			<!-- //contents -->

		</div>
		<!-- //container -->

		
		<!-- pop layer -->
		
	
<!-- BC 세트 3종 50% 할인 안내 팝업 -->
<article class="pop_wrap pop_layer pop_newPromotion1802 hide" id="bcSetPopup">
	<h2>미스터피자</h2>
	<a href="#" class="btn_close pop_close">닫기</a>
	<div class="pop_content">
		<img src="//cdn.mrpizza.co.kr/2014_resources/images/popup/img_pop_newPromotion1803_1.jpg" alt="BC카드 인증 필수!!">
		<a href="#" class="pop_close"><img src="//cdn.mrpizza.co.kr/2014_resources/images/popup/img_pop_newPromotion1802_2.jpg" alt="확인"></a>
	</div>
</article>


		





<!-- pop_layer -->
<article id="popLogin" class="pop_wrap pop_layer hide">
	<h1>로그인</h1>			
	<div class="pop_cont">
		<form id="popLoginForm" name="popLoginForm">
		<div class="pop_loginWrap">
			<div class="f_left">
				<h2 class="cont_tit tit0">회원로그인</h2>
				<ul class="pop_login mt20">
					<li><input type="text" id="popUser_id" name="user_id" placeholder="아이디" title="아이디 입력" maxlength="16"></li>
					<li><input type="password" id="popUser_pwd" name="user_pwd" placeholder="비밀번호" title="비밀번호 입력" maxlength="16"></li>
					<li class="btn">
						<a href="javascript:void(0);" onclick="javascript:onPopLogin();" class="button red h80">로그인 <span class="gt">&gt;</span></a>
					</li>
					<li class="chk">
						<label>
							<input id="popIdcheck" name="idcheck" type="checkbox" class="checkbox" title="아이디저장">
							<span class="lbl">아이디저장</span>
						</label>
						<a href="https://sepay.org/spm/join?regSiteCode=NM&amp;ctgCode=1&amp;subCode=1" class="btn_login_phone">
							<img src="//cdn.mrpizza.co.kr/2014_resources/images/member/img_login_btn_phone_m.png" alt="휴대폰간편입력 로그인버튼">
						</a>
						<!-- <div class="sns_login tip_box">
							<a href="#" class="fb" target="_blank" title="새창열림">페이스북 로그인</a>
							<a href="#" class="tip">TIP</a>
							<p class="tip_view">
								<i class="bg"></i>
								페이스북 ID로 첫 로그인 시 20% 할인쿠폰을 드립니다. 로그인 후 나의 쿠폰함에서 확인하세요.
							</p>
						</div> -->
					</li>
				</ul>
				<p class="t_red mt5" id="popLoginResult" style="visibility: hidden">*아이디 또는 비밀번호를 다시 확인해 주세요.</p>
				<p class="mt20 naver_login_btn_wrap">
					<a href="/login_join/findID" class="button grad btn_move w120 f12">아이디/비밀번호찾기</a>
					<a href="/login_join/join" class="button grad btn_move w80 f12">회원가입하기</a>
					<span id="naver_id_login_popup"><a href="https://nid.naver.com/oauth2.0/authorize?response_type=token&amp;client_id=HrPgLEnk0VGTGSOYakKo&amp;redirect_uri=https%3A%2F%2Fwww.mrpizza.co.kr%2Flogin_join%2FNaverResult&amp;state=4aac9cf4-e031-4591-95f5-3cddf62fbd4a" onclick="window.open(this.href, 'naverloginpop', 'titlebar=1, resizable=1, scrollbars=yes, width=600, height=550'); return false" id="naver_id_login_anchor" class="naver_btn"><img src="http://static.nid.naver.com/oauth/big_g.PNG" border="0" title="네이버 아이디로 로그인" width="152.625px" height="33px"></a> </span> <!-- 네이버 로그인 -->
					<!-- 페이코 버튼 노출 영역 -->
					<a href="javascript:order();" class="payco_login_btn">페이코 로그인</a>
					<!-- // 페이코 버튼 노출 영역 -->
				</p>

				<h3 class="cont_tit tit3 mt30">회원혜택안내 <span class="txt">(마이페이지 내 내쿠폰에서 확인하세요)</span></h3>					
				<ul class="login_list">
					<li>
						<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/member/img_login01.gif" alt="회원가입"></p>
						<h4>회원가입</h4>
						<div>신규가입 시 <br><strong class="t_red">프리미엄 피자 20% 할인쿠폰</strong></div>
					</li>
					<li>
						<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/member/img_login02.gif" alt="정보수정"></p>
						<h4>정보수정</h4>
						<div>회원정보 업데이트 시 <br><strong class="t_red">프리미엄 피자 20% 할인쿠폰</strong></div>
					</li>
					<li>
						<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/member/img_login03.gif" alt="생일쿠폰"></p>
						<h4>생일쿠폰</h4>
						<div>회원정보 기준 생일 시 <br><strong class="t_red">프리미엄 피자 20% 할인쿠폰</strong></div>
					</li>
				</ul>
			</div>
 
			<div class="f_right">
				<h2 class="cont_tit tit0">비회원주문</h2>
				<textarea class="mt20" readonly="readonly" title="비회원주문 이용약관">1.   수집하는 개인정보 항목
   - 전화번호, 성명, 주소
2.   수집 목적 
   ① 성명, 주소: 제품의 전달, 청구서, 정확한 제품 배송지의 확보.
3.   개인정보 보유기간 
   ① 계약 또는 청약철회 등에 관한 기록 : 6개월
   ② 대금결제 및 재화 등의 공급에 관한 기록 : 1년
   ③ 소비자의 불만 또는 분쟁처리에 관한 기록 : 1년
4.   비회원 주문 시 제공하신 모든 정보는 상기 목적에 필요한 용도 이외로는 사용되지 않습니다. 기타 자세한 사항은 '개인정보취급방침'을 참고하여주시기 바랍니다.
				</textarea>

				<div class="mt20 p1 hidden">
					<label>
						<input id="guest_agree_chk" name="guest_agree_chk" type="checkbox" class="checkbox">
						<span class="lbl">약관에 동의합니다.</span>
					</label>

					<span class="f_right ">
						<a href="javascript:void(0);" onclick="javascript:onGuestLogin();" class="button red">비회원주문 <span class="gt">&gt;</span></a>
					</span>
					<p class=" clear t_red mt10">* 비회원 이용 약관에 동의하셔야 주문이 가능합니다.</p>
				</div>
			</div>
		</div>
		</form>
	</div>
	<a href="#" class="btn_close pop_close">팝업닫기</a>
	
	<!-- ##############################Payco Login############################## -->
	<script>
		function order() {
			var payco_popup;
			payco_popup = window.open("about:blank", 'popupLogin', 'top=100, left=300, width=727px, height=512px, resizble=no, scrollbars=yes');
			$('#login_request').attr("target", "popupLogin");
			$('#login_request').submit();
		}
	</script>
	<!-- PayCo(페이코) 로그인창 띄우기 -->
	<form id="login_request" name="login_request" target="_self" method="POST" action="https://id.payco.com/oauth2.0/authorize">
		<input type="hidden" id="response_type" name="response_type" value="code">
		<input type="hidden" id="client_id" name="client_id" value="3RDp_ofHRqnOajyx4UUw">
		<input type="hidden" id="redirect_uri" name="redirect_uri" value="https://www.mrpizza.co.kr/login_join/paycoLoginStart"> 
		<input type="hidden" id="serviceProviderCode" name="serviceProviderCode" value="FRIENDS">
		<input type="hidden" id="viewType" name="viewType" value="">
		<input type="hidden" id="userLocale" name="userLocale" value="ko_KR">
		<input type="hidden" id="state" name="state" value="ab42ae">
	</form>
	
	<!-- ##############################Payco Login############################## -->
</article>
<!-- //pop_layer -->



  <script type="text/javascript" src="/js/mrpizza/login/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>

  <script type="text/javascript">
  	var naver_id_login = new naver_id_login("HrPgLEnk0VGTGSOYakKo", "https://www.mrpizza.co.kr/login_join/NaverResult");
  	var state = naver_id_login.getUniqState();
  	naver_id_login.setButton("white", 2,40);
  	naver_id_login.setDomain("YOUR_SERVICE_URL");
  	naver_id_login.setState(state);
  	naver_id_login.setPopup();
  	naver_id_login.init_naver_id_login();

  </script>

		


<article id="popSizeup" class="pop_wrap pop_layer img_layer hide" style="width:779px;margin-left:-390px;">
	<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/event/sizeup/img_sizeup1.jpg" alt="창립 25주년 기념"></p>
	<p class="t_center">
		<a href="javascript:setSizeUpDiscountApply_payment();"><img src="//cdn.mrpizza.co.kr/2014_resources/images/event/sizeup/btn_sizeup1.gif" alt="피자 사이즈업 받기"></a>
		<a href="#" class="pop_close"><img src="//cdn.mrpizza.co.kr/2014_resources/images/event/sizeup/btn_sizeup2.gif" alt="다른 할인받기"></a>
	</p>
	<p class="mt20"><img src="//cdn.mrpizza.co.kr/2014_resources/images/event/sizeup/img_sizeup2.gif" alt="적용 제외 피자 : 허니포테이토, 시카고딥, 고르곤졸라. 타 할인/적립/쿠폰/세트와 중복 적용 불가"></p>
	<a href="#" class="btn_close pop_close">팝업닫기</a>
</article>

<article id="popSizeupAlret1" class="pop_wrap pop_layer img_layer pop_sizeup hide">
	<div>
		<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/event/sizeup/txt_sizeup1.gif" alt="무료사이즈업 참여 매장입니다."></p>
		<p class="mt5"><a href="javascript:chkDeliveryComplete();" class="button red h45">확인</a></p>
	</div>
	<a href="#" class="btn_close pop_close">팝업닫기</a>
</article>

<article id="popSizeupAlret2" class="pop_wrap pop_layer img_layer pop_sizeup hide">
	<div>
		<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/event/sizeup/txt_sizeup2.gif" alt="무료사이즈업 미참여 매장입니다."></p>
		<p class="mt5"><a href="javascript:chkDeliveryComplete();" class="button red h45">확인</a></p>
	</div>
	<a href="#" class="btn_close pop_close">팝업닫기</a>
</article>

<article id="popSizeupAlret3" class="pop_wrap pop_layer img_layer pop_sizeup hide">
	<div>
		<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/event/sizeup/txt_sizeup3.gif" alt="사이즈업 할인을 받았습니다."></p>
		<p class="mt5"><a href="#" class="button red h45 pop_close">확인</a></p>
	</div>
	<a href="#" class="btn_close pop_close">팝업닫기</a>
</article>
		<!-- /pop layer -->

		<!-- quick -->

		<!-- /quick -->

		<!-- footer -->
		




<!-- pop layer -->



<!-- pop_layer -->
<article id="popGroup" class="pop_wrap pop_layer hide">
	<h1 class="no_txt">미스터피자 단체배달 안내</h1>			
	<ul class="popGroup_tab tabMotion">
		<li class="on"><a href="#popGroup1">단체배달</a></li>
		<li><a href="#popGroup2">상품권</a></li>
		<li><a href="#popGroup3">E-쿠폰</a></li>
	</ul>
	<div id="popGroup1" class="pop_cont pop_group pop_group1 tab_cont" style="display: block;">
		<h2><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/tit_group.gif" alt="미스터피자 단체배달안내"></h2>
		<p class="mt25"><strong class="f16">각종 행사, 이벤트, 모임 등 피자를 다량으로 주문하시려면 <span class="t_red">미스터피자 단체 배달 서비스</span>를 이용해 보세요.</strong></p>
		<p><strong class="f16">주문부터 결제 까지 간편하게 이용하실 수 있습니다</strong></p>
		<div>
			<p><strong class="f18 t_dgray">이용안내</strong></p>
			<ul class="txt_list mt10 f14">
				<li>아래 문의전화(02-590-7744)로 주문하실 수 있습니다.</li>
				<li>여러 곳으로 배송하며 결제를 한번에 할 때, 혹은 10판 이상 배송 시 이용 가능합니다.</li> 
				<li>할인금액은 주문수량에 따라 차등 적용되며, 제휴 및 타 할인과 중복적용되지 않습니다.</li>
				<li class="t_red">배달을 원하시는 날짜 2~3일 전에 미리 신청해주셔야 합니다.</li>
				<li class="t_red">당일 주문은 최소 배송 4시간 전에 접수 받습니다.</li>
				<li>결제는 현금 및 신용카드(법인/개인) 모두 가능합니다.</li>
				<li>업무시간 : 09:00 ~ 18:00 / 토, 일, 공휴일 휴무</li>
			</ul>
		</div>
		<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/txt_group_new190318.gif" alt="문의전화 02-590-7744"></p>
	</div>
	
	<div id="popGroup2" class="pop_cont pop_group pop_group2 tab_cont" style="display: none;">
		<h2><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/tit_group2.gif" alt="미스터피자 상품권 안내"></h2>
		<p class="mt25"><strong class="f16">미스터피자 상품권으로 마음을 전하세요.</strong></p>
		<div>
			<p><strong class="f18 t_dgray">이용안내</strong></p>
			<ul class="txt_list mt10 f14">
				<li>아래 문의전화(02-590-7744)로 구매하실 수 있습니다.</li>
				<li>주문 금액에 따라 할인율은 차등 적용됩니다.</li>
				<li>구매하신 상품권은 2~3일 정도의 배달 시간이 소요됩니다.<br>
				(단, 일요일 및 법정 공휴일에는 배송불가)</li>
				<li>상품권은 전국으로 무료 배송해 드립니다.(5만원이상 구매 고객에 한함)</li>
				<li>결제는 현금 및 신용카드(법인/개인) 모두 가능합니다.	</li>
				<li>업무시간 : 09:00 ~ 18:00 /    토, 일, 공휴일 휴무</li>
			</ul>
		</div>
		<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/txt_group_new.gif" alt="문의전화 02-590-7724"></p>
	</div>
	
	<div id="popGroup3" class="pop_cont pop_group pop_group3 tab_cont" style="display: none;">
		<h2><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/tit_group3.gif" alt="E-쿠폰"></h2>
		<p class="f16 mt25 mb50"><strong>간편하게 모바일 쿠폰 (E-쿠폰) 발송 서비스를 이용해 보세요.</strong></p>		
		
		<div class="f14">
			<p class="mt30"><strong class="f18 t_dgray">이용안내</strong></p>
			<ul class="txt_list mt10">
				<li>아래문의전화/이메일/홈페이지를통해문의하실수있습니다.</li>
				<li>결제는 현금 및 신용카드(법인/개인) 모두 가능합니다.</li>
				<li>업무시간 : 09:00 ~ 18:00 / 토, 일, 공휴일 휴무</li>
			</ul>
			<p>모바일 쿠폰마켓 홈페이지 : <a href="//www.mcmarket.co.kr/" target="_blank" class="t_blue" title="쿠폰마켓 새창열림">https://www.mcmarket.co.kr/</a> </p>
			<p class="mb30">모바일쿠폰마켓 접속 &gt; 상품찾기 &gt; 미스터피자 &gt; 상품선택 &gt; 발송신청하기</p>
		</div>
		<p><img src="//cdn.mrpizza.co.kr/2014_resources/images/common/txt_group2.gif" alt="문의전화 070-4020-5289, 대표문의 sec_b2mall@coopnc.com"></p>
	</div>
	
	<a href="#" class="btn_close pop_close">팝업닫기</a>
</article>
<!-- //pop_layer -->


<!-- /pop layer -->

<!-- first promotion pop layer -->
		<div style="display:none;position:absolute;left:0;top:0;z-index:9996;" id="sizeupsaladlayer">
		</div>
		<div style="display:none;position:absolute;left:0;top:0;z-index:9996;" id="sizeuplayer">
		</div>
		<div style="display:none;position:absolute;left:0;top:0;z-index:9996;" id="saladlayer">
		</div>
<!-- /first promotion pop layer -->

<!-- pop topping -->



<a href="#addTopping" id="topping_open" class="pop_open"><span class="blind">토핑추가 팝업 열기</span></a>
<!-- pop_layer -->

<article id="addTopping" class="pop_wrap pop_layer hide">

	<input type="hidden" id="p_class_id" name="p_class_id" value="">
	<input type="hidden" id="p_base_id" name="p_base_id" value="">
	<input type="hidden" id="p_size_id" name="p_size_id" value="">
	<input type="hidden" id="p_product_id" name="p_product_id" value="">
	<input type="hidden" id="p_order_seq" name="p_order_seq" value="">
	
	<h1>추가 토핑 선택</h1>			
	<div class="pop_cont pop_scroll">
		<ul class="txt_list">
			<li>토핑 추가는 피자 한판당 5가지까지 선택할 수 있습니다.</li>
			<li>피자만 할인되는 경우, 토핑 금액에 대해서는 할인이 적용되지 않습니다. </li>
			<li>같은 피자를 2판이상 담는 경우, 추가 토핑은 피자마다 동일하게 적용됩니다.</li>
			<li>피자샌드인 경우 치즈추가는 피자샌드 1개당 1개만 추가할 수 있습니다.</li>
		</ul>
		<hr>				
		<div class="add_topping">
			<table>
				<caption>주문내역</caption>
				<colgroup>					
					<col>
					<col style="width:80px">
					<col style="width:60px">
				</colgroup>
				<tbody id="toppingList">
					<!-- <tr>
						<th scope="row">
							<img src="http://cdn.mrpizza.co.kr/2011/uploadV1/product_new/201531713414146.jpg?timestamp=1431495857383" style="width:100px" alt="토핑 이미지">
						</th>
						<td><strong>과일크림치즈</strong></td>
						<td class="t_right">1,000원</td>
						<td class="small_spin"><input class="spinner" type="text" value="1" title="수량입력" readonly /></td>
						<td class="t_center"><a href="#" class="button h25">추가</a></td>
					</tr> -->
				</tbody>
			</table>
		</div>
	</div>
	<a href="#" class="btn_close pop_close">팝업닫기</a>
</article>

<!-- //pop_layer -->

<!-- // topping -->

<%@include file="../common/footer.jsp" %>
<!-- popup -->
<article id="popGroupOrder" class="pop_wrap pop_layer hide">
	<h1>미스터피자</h1>			
	<div class="pop_cont">
		평일 09:00~18:00에는 <strong>02-590-7731</strong>, <br>그 외 시간에는 일반주문전화 <strong style="color:#e20c3e">1577-0077</strong>로 문의 주세요. <br>
		여러 곳으로 배송하며 결제를 한번에 할 때, <br>혹은 10판 이상 배송 시 이용 가능합니다.<br>
		할인금액은 주문수량에 따라 차등 적용되며,<br> 제휴 및 타 할인과 중복적용되지 않습니다.<br>
		배달을 원하시는 날짜 2~3일 전에 미리 신청해주셔야 합니다.<br>
		결제는 현금 및 신용카드(법인/개인) 모두 가능합니다.
	</div>
	<a href="#" class="btn_close pop_close">팝업닫기</a>
</article>
<form id="recommendForm" name="recommendForm">
	<input type="hidden" id="product_case" name="product_case" value="">
	<input type="hidden" id="product_key" name="product_key" value="">
	<input type="hidden" id="product_gubun" name="product_gubun" value="">
	<input type="hidden" id="dough_id" name="dough_id" value="">
	<input type="hidden" id="option_id" name="option_id" value="">
</form>
<script type="text/javascript">
(function ()
{
    function k(a, b, c)
    {
        if (a.addEventListener) {
            a.addEventListener(b, c, false);
        }
        else {
            a.attachEvent && a.attachEvent("on" + b, c);
        }
    }
    function g(a)
    {
        if (typeof window.onload != "function")
        {
            window.onload = a;
        }
        else {
            var b = window.onload;
            window.onload = function ()
            {
                b();
                a();
            }
        }
    }
    function h()
    {
        var a = {};
        for (type in {
            Top : "", Left : ""
        })
        {
            var b = type == "Top" ? "Y" : "X";
            if (typeof window["page" + b + "Offset"] != "undefined") {
                a[type.toLowerCase()] = window["page" + b + "Offset"];
            }
            else
            {
                b = document.documentElement.clientHeight ? document.documentElement : document.body;
                a[type.toLowerCase()] = b["scroll" + type];
            }
        }
        return a
    }
    function l()
    {
        var a = document.body, b;
        if (window.innerHeight) {
            b = window.innerHeight;
        }
        else if (a.parentElement.clientHeight) {
            b = a.parentElement.clientHeight;
        }
        else if (a && a.clientHeight) {
            b = a.clientHeight;
        }
        return b
    }
    function i(a)
    {
        this.parent = document.body;
        this.createEl(this.parent, a);
        this.size = Math.random() * 4 + 4;
        this.el.style.width = Math.round(this.size) + "px";
        this.el.style.height = Math.round(this.size) + "px";
        this.maxLeft = document.body.offsetWidth - this.size;
        this.maxTop = document.body.offsetHeight - this.size;
        this.left = Math.random() * this.maxLeft;
        this.top = h().top + 1;
        this.angle = 1.4 + 0.2 * Math.random();
        this.minAngle = 1.4;
        this.maxAngle = 1.6;
        this.angleDelta = 0.01 * Math.random();
        this.speed = 2 + Math.random()
    }
    var j = false;
    g(function ()
    {
        j = true;
    });
    var f = true;
    window.createSnow = function (a, b)
    {
        if (j)
        {
            var c = [], m = setInterval(function ()
            {
                f && b > c.length && Math.random() < b * 0.0025 && c.push(new i(a));
                !f && !c.length && clearInterval(m);
                for (var e = h().top, n = l(), d = c.length - 1; d >= 0; d--) if (c[d]) if (c[d].top < e || c[d].top + c[d].size + 1 > e + n) {
                    c[d].remove();
                    c[d] = null;
                    c.splice(d, 1)
                }
                else {
                    c[d].move();
                    c[d].draw()
                }
            }, 40);
            k(window, "scroll", function ()
            {
                for (var e = c.length - 1; e >= 0; e--) {
                    c[e].draw();
                }
            })
        }
        else {
            g(function () 
            {
                createSnow(a, b) 
            });
        }
    };
    window.removeSnow = function ()
    {
        f = false;
    };
    i.prototype = 
    {
        createEl : function (a, b)
        {
            this.el = document.createElement("img");
            this.el.setAttribute("src", b + "snow" + Math.floor(Math.random() * 4) + ".gif");
            this.el.style.position = "absolute";
            this.el.style.display = "block";
            this.el.style.zIndex = "-1";
            this.parent.appendChild(this.el)
        },
        move : function ()
        {
            if (this.angle < this.minAngle || this.angle > this.maxAngle) {
                this.angleDelta =- this.angleDelta;
            }
            this.angle += this.angleDelta;
            this.left += this.speed * Math.cos(this.angle * Math.PI);
            this.top -= this.speed * Math.sin(this.angle * Math.PI)* 1.5;
            if (this.left < 0) {
                this.left = this.maxLeft;
            }
            else if (this.left > this.maxLeft) {
                this.left = 0;
            }
        },
        draw : function ()
        {
            this.el.style.top = Math.round(this.top) + "px";
            this.el.style.left = Math.round(this.left) + "px";
        },
        remove : function ()
        {
            this.parent.removeChild(this.el);
            this.parent = this.el = null;
        }
    }
})();


</script>
<!-- 공통 적용 스크립트 , 모든 페이지에 노출되도록 설치. 단 전환페이지 설정값보다 항상 하단에 위치해야함 --> 
<script type="text/javascript" src="https://wcs.naver.net/wcslog.js"> </script> 
<script type="text/javascript"> 
if (!wcs_add) var wcs_add={};
wcs_add["wa"] = "s_2f6be2a35c45";
if (!_nasa) var _nasa={};
wcs.inflow();
wcs_do(_nasa);
</script>
<!-- 공통 스크립트 끝(네이버 키워드 광고) -->

<!-- 애드베이리타겟팅태그  -->
<iframe src="https://adcheck.about.co.kr/mad/prd/view?shopid=mrpizza_r" scrolling="no" frameborder="0" width="0" height="0" title="애드베이리타겟팅태그"></iframe>
<!-- 애드베이리타겟팅태그  -->


<script type="text/javascript">
    var roosevelt_params = {
        retargeting_id:'j.tTpckL.PwfsNPJxhaxmA00',
        tag_label:'J5IRXH6TReeTry1EPFIROw'
    };
</script>
<!-- 다음 카카오 DDN -->
<script type="text/javascript" src="//adimg.daumcdn.net/rt/roosevelt.js" async=""></script>
<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script><script type="text/javascript">
      kakaoPixel('7829926935338663372').pageView();
      kakaoPixel('2285893234673012525').pageView();
      </script>
      
            <!-- Facebook Pixel Code -->
<script>
  !function(f,b,e,v,n,t,s)
  {if(f.fbq)return;n=f.fbq=function(){n.callMethod?
  n.callMethod.apply(n,arguments):n.queue.push(arguments)};
  if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
  n.queue=[];t=b.createElement(e);t.async=!0;
  t.src=v;s=b.getElementsByTagName(e)[0];
  s.parentNode.insertBefore(t,s)}(window, document,'script',
  'https://connect.facebook.net/en_US/fbevents.js');
  fbq('init', '1388168051328271');
  fbq('track', 'PageView');
</script>
<noscript><img height="1" width="1" style="display:none"
  src="https://www.facebook.com/tr?id=1388168051328271&ev=PageView&noscript=1"
/></noscript>
<!-- End Facebook Pixel Code -->
<!-- Facebook Pixel Code -->
<script>
	function fbqSearchKeyword() {
	var uri = document.referrer;
	if (!uri) return;
	var link = document.createElement('a');
	link.setAttribute('href', uri);
	var pattern = new RegExp('[\\?&](?:q|query)=([^&#]*)');
	var query = link.search.match(pattern);
	if (query && query.length > 0) {
	var keyword = query[1].replace(/\+/gi, "%20");
	}
	if (keyword) {
	fbq('trackCustom', 'SearchKeyword', {
	hostname: link.hostname,
	keyword: decodeURIComponent(keyword),
	});
	} }
fbqSearchKeyword();
</script>
<!-- End Facebook Pixel Code -->
<!-- Enliple Common Tracker v3.5 [공용] start -->
<script type="text/javascript">
   function mobRf(){
       var rf = new EN();
       rf.setData("userid", "mrpizza77");
       rf.setSSL(true);
       rf.sendRf();
   }
</script>
<script src="https://cdn.megadata.co.kr/js/en_script/3.5/enliple_sns_min3.5.js" defer="defer" onload="mobRf()"></script>
<!-- Enliple Common Tracker v3.5 [공용] end -->


<!-- Taboola Pixel Code -->

<script type="text/javascript">

  window._tfa = window._tfa || [];

  window._tfa.push({notify: 'event', name: 'page_view', id: 1177527});

  !function (t, f, a, x) {

         if (!document.getElementById(x)) {

            t.async = 1;t.src = a;t.id=x;f.parentNode.insertBefore(t, f);

         }

  }(document.createElement('script'),

  document.getElementsByTagName('script')[0],

  '//cdn.taboola.com/libtrc/unip/1177527/tfa.js',

  'tb_tfa_script');

</script>

<noscript>

  <img src='//trc.taboola.com/1177527/log/3/unip?en=page_view'

      width='0' height='0' style='display:none'/>

</noscript>

<!-- End of Taboola Pixel Code -->

<!-- Global site tag (gtag.js) - Google Ads: 763537271 -->
<script async="" src="https://www.googletagmanager.com/gtag/js?id=AW-763537271"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'AW-763537271');
</script>

<!-- ADN Tracker[공통] start -->
<script type="text/javascript">
var adn_param = adn_param || [];
adn_param.push([{ 	
 ui:'100736',
 ut:'Home'
}]);
</script>
<script type="text/javascript" async="" src="//fin.rainbownine.net/js/adn_tags_1.0.0.js"></script>
<!-- ADN Tracker[공통] end -->

<!-- ADN 크로징 설치  start -->
<script type="text/javascript">
var adn_panel_param = adn_panel_param || [];
adn_panel_param.push([{
 ui:'100736',  
 ci:'1007360004',
 gi:'6902'
}]);
</script>
<script type="text/javascript" async="" src="//fin.rainbownine.net/js/adn_closingad_1.1.1.js"></script>
<!-- ADN 크로징 설치 end -->

<script src="//tgsc.mediaindex.co.kr/ICoverCookie.js?SCode=NDYwNw%3D%3D&amp;PCode=&amp;MCode="></script>

<script type="text/javascript" src="//static.tagmanager.toast.com/tag/view/1637"></script>
<script type="text/javascript">
 window.ne_tgm_q = window.ne_tgm_q || [];
 window.ne_tgm_q.push(
 {
 	tagType: 'visit',
 	device:'web',
 	uniqValue:'',
 	pageEncoding:'utf-8'
 });
 </script>


		<!-- //footer -->

		<!-- 메조트래킹태그 -->
		<script src="https://vtag5.midas-i.com/vat-tag?cmp_no=205&amp;depth=9"></script>
		<!-- 메조트래킹태그 -->
	</div>
	<!-- //wrap -->
	<!-- 퍼블리셔 임시스크립트: 옴겨주세요 -->
<script>
	var popOpenFunc = function(view) {
		
		var vCont = view;
		var mh =  $(vCont).outerHeight();
	   	$('#contents').after('<span class=bgLayer></span>');
	   	$('.bgLayer').fadeTo('fast', 0.6, function() {
	       $(vCont).css({'margin-top':-(mh/2)}).show(300, function() {
	           $(this).attr('tabIndex',0).focus();
	       });
	
	       $(vCont).find('.pop_close').click(function() {
	           $('.bgLayer').remove();               
				$(this).parents('.pop_layer').hide(300,function(){
					$(this).removeAttr('tabindex');
				});
				$(this).focus();
				return false;
	       });
	   });
	}
	$(function(){
		$('input[name=payGroup]').change(function(){
			var _val = $(this).val();					
			if(_val == "6"){
				$('#naverpaycashBox').show().siblings('div').hide();
				$('.payco_info').hide();
			}else if(_val == "7"){
				$('.payco_info').show();
				$('#naverpaycashBox').hide().siblings('div').show();
			}else{
				$('input[name=navercash_user]').attr('checked', false);
				$('#naverpaycashBox').hide().siblings('div').show();
				$('.payco_info').hide();
				$('#navercash_phone1').val("");
				$('#navercash_phone2').val("");
				$('#navercash_phone3').val("");
				$('#navercash_licensee').val("");
				$('#navercashCon1').hide("");
				$('#navercashCon2').hide("");
			}											
		});
		$('input[name=navercash_user]').change(function(){
			var _val = $(this).val();											
			$('#'+ _val).show().siblings('div').hide();
		});
	});
	/*
	function orderLog(){

		order_type = $("#order_type").val(); 
		
		if(order_type == "3"){ phone = $("#gift_from_tel1").val()+"-"+$("#gift_from_tel2").val()+"-"+$("#gift_from_tel3").val(); }else{ phone = $("#view_delivery_phone1").val()+"-"+$("#view_delivery_phone2").val()+"-"+$("#view_delivery_phone3").val();	}						
		full_address =  $("#full_address").val();
		payGroup = $('input[name=payGroup]:checked').val();
		if(payGroup == "2"){ payGroupNm ="신용카드"; }else if (payGroup == "3"){ payGroupNm ="휴대폰 결제"; }else if(payGroup == "5"){ payGroupNm ="카카오페이"; }else if(payGroup == "6"){ payGroupNm ="네이버페이"; }else if(payGroup == "7"){ payGroupNm ="페이코"; }else{ payGroupNm = "선택안함" }
		coupon_type = $("#coupon_type").val();
		cust_lic = $("#cust_lic").val();
		card_gubun = $("#card_gubun").val();
		card_yymm = $("#card_yymm").val();
		mode = $("#mode").val();
		gopaymethod = $("#gopaymethod").val();
		check_url = $("#check_url").val();
		apply_url = $("#apply_url").val();
		sms_guest_flag = $("#sms_guest_flag").val();
		
		
		alert($("#coupon_type").val());
	}
	*/
</script>

<!-- Event snippet for 주문하기_클릭 conversion page
In your html page, add the snippet and call gtag_report_conversion when someone clicks on the chosen link or button. -->
<script>
function gtag_report_conversion(url) {
  var callback = function () {
    if (typeof(url) != 'undefined') {
      window.location = url;
    }
  };
  gtag('event', 'conversion', {
      'send_to': 'AW-763537271/YG9KCPb595UBEPfOiuwC',
      'transaction_id': '',
      'event_callback': callback
  });
  return false;
}
</script>





<div id="adn_container" style="display:none"></div><iframe id="ne_tgmiframe_0" width="0" height="0" style="position:absolute;width:0px;height:0px;display:none;" src="about:blank"></iframe><div id="adn_panel_wrap" class="closing_bg" style="display: none;"></div></body>
</html>
