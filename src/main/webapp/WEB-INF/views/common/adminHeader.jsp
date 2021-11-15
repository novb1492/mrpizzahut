<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.side{position: absolute;width: 150px;height: 100%;}
.contents{margin-left: 300px}
.menuInput{width:300px;}
ul{list-style: none; float:left;  }
li{float: left;}
img{width: 150px;height: 100px; }
#pageArea{position: absolute; top: 90%; left: 20%;}
</style>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="<c:url value="/resources/jslib.js" />"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
 <nav class="navbar navbar-expand-lg navbar-light bg-light rounded">
      <a class="navbar-brand" href="/app/admin/home">MrPizzaHutAdmin</a>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <a class="navbar-brand" href="/shopMainPage?kind=coffee">쿠폰(이벤트)</b-nav-item>
          <a class="navbar-brand"href="/app/admin/menu?scope=메뉴등록" >메뉴설정</b-nav-item>
           <a class="navbar-brand"href="/" >유저정보수정</b-nav-item>
        </b-navbar-nav>

        
        <b-navbar-nav class="ml-auto">
         <!--  <b-nav-item-dropdown text="Dream" center>
            <b-dropdown-item href="#">소원작성하러가기</b-dropdown-item>
            <b-dropdown-item href="#">소원 바구니</b-dropdown-item>
          </b-nav-item-dropdown>-->

          <b-nav-item-dropdown text="User" center>
             
        
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
  </nav>
</body>
</html>