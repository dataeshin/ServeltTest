<%@page import="com.sun.jdi.request.InvalidRequestStateException"%>
<%@page import="java.util.List"%>
<%@page import="com.mvc.web.entity.content.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:set var="root" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="../../css/bootstrap.min.css" />
<script src="../../js/bootstrap.bundle.js"></script>
<title>Insert title here</title>
    <script src="https://kit.fontawesome.com/9eb162ac0d.js"
      crossorigin="anonymous"
    ></script>
</head>

<body>

${name} 님 반갑습니다.
<div>
<a href="/user/logout">
<i class="fas fa-sign-out-alt">로그아웃</i>
</a>
</div>
<table class="table" border="1">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">제목</th>
      <th scope="col">글쓴이</th>
      <th scope="col">내용</th>
      <th scope="col">등록일자</th>
      <th scope="col">조회수</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach var="li" items="${list}">
			<tr>
				<td>${li.id}</td>
				<td><a href="detail?id=${li.id}&q=${param.q}&f=${param.f}">${li.title}</a></td>
				<td>${li.writeid}</td>
				<td>${li.content}</td>
				<td><fmt:formatDate pattern="yyyy년 MM월 dd일 hh시 mm분" value="${li.regdate}" /></td>
				<td>${li.hit}</td>
			</tr>
		</c:forEach>
  </tbody>
</table>

	
	<div>
	<input type="button" onclick="location.href='regedit'" value="글쓰기" />
	</div>
	<h2>
		${count} 건이 조회되었습니다.

		<!-- 변수선언 -->
		<c:set var="page" value="${empty param.p?1:param.p}"></c:set>
		<c:set var="startNum" value="${page-(page-1)%5}"></c:set>
		<c:set var="lastNum" value="${fn:substringBefore(Math.ceil(count/10),'.')}"></c:set>
	</h2>
	<!-- 현재 페이지 -->
	<div>
		<h3>현재 페이지</h3>
		<div>
			<span> ${page} </span> / ${lastNum} pages
		</div>
	</div>

	<!-- 페이징 처리 시작 -->
	<!-- 이전페이지 -->
	<c:if test="${startNum>1}">
		<a href="?p=${startNum-1}&f=${param.f}&q=${param.q}">Prev</a>
	</c:if>
	<c:if test="${startNum<=1}">
		<a href="#" onclick="alert('첫 페이지입니다.');">Prev</a>
	</c:if>
	<!-- 숫자 페이지 -->
	<ul>
		<c:forEach var="i" begin="0" end="4">
			<c:if test="${param.p==(startNum+i)}">
				<c:set var="style" value="font-weight:bold;color:red;"></c:set>
			</c:if>
			<c:if test="${param.p!=(startNum+i)}">
				<c:set var="style" value=""></c:set>
			</c:if>
			<c:if test="${startNum+i<=lastNum }">
				<li><a style="${style}"
					href="?p=${startNum+i}&f=${param.f}&q=${param.q}">${startNum+i}</a>
				</li>
			</c:if>
		</c:forEach>
	</ul>
	<!-- 다음 페이지 -->
	<c:if test="${startNum+5<=lastNum}">
		<a href="?p=${startNum+5}&f=${param.f}&q=${param.q}">Next</a>
	</c:if>
	<c:if test="${startNum+5>lastNum}">
		<a href="#" onclick="alert('마지막 페이지입니다.');">Next</a>
	</c:if>


	<!-- 검색 -->
	<form action="" method="get">
		<div>
			<select name="f">
				<option ${(param.f=="title")?"selected":""} value="title">제목</option>
				<option ${(param.f=="writeid")?"selected":""} value="writeid">글쓴이</option>
			</select> <input type="text" name="q" /> <span><input type="submit"
				value="검색"></span>
		</div>
	</form>
</body>
</html>