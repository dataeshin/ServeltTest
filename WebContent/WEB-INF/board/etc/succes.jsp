<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가입을 축하합니다.</title>
<c:set var="root" value="${pageContext.request.contextPath}" />

</head>
<body>

	<section>
		<div>
			<a href="/user/login"><img src="${root}/image/getty.jpg"></a>
		</div>
	</section>
</body>
</html>