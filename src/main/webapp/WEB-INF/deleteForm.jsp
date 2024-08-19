<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>삭제</title>
</head>
<body>
	<form action="/guestbook/gbc" method="get">
		<table>
			<tr>
				<td>비밀번호</td>
				<td><input type="text" name="password"></td>
				<td><button type="submit">삭제</button></td>
			</tr>
		</table>
		<input type="hidden" name="action" value="delete">
		<input type="hidden" name="no" value="${param.no}"> <!-- guestVo.no 가능 -->
	</form>
	
	<br><br>
	<a href="/guestbook/gbc?action=addList">메인으로 돌아가기</a>
</body>

</html>