<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실패</title>
</head>
<body>
 	<h2>입력하신 비밀번호가 일치하지 않습니다.</h2>
 	<a href="/guestbook/gbc?action=deleteForm&no=${no}">다시 입력하기</a>
 
</body>
</html>