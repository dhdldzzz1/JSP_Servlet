<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	include 지시어를 통해 파일을 포함시키면 컴파일 되지 않은 원본상태의
	코드가 그대로 포함된 후 컴파일이 진행된다.
	따라서 아래에서 사용한 변수들은 해당 문서에 포함된 상태이므로 문제
	없이 실행된다.
 --%>
<%@ include file="IncludeFile.jsp" %>  <%-- 다른 JSP파일 포함 --%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>include 지시어</title>
	</head>
	<body>
		<%   	 // IncludeFile.jsp 에서 선언한 변수 사용
		out.println("오늘 날짜 : " + today);
		out.println("<br/>");
		out.println("내일 날짜 : " + tomorrow);
		%>
	</body>
</html>