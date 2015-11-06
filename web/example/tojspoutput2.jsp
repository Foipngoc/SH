<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'test.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	Page test1
	<br> string: ${POJOModel.string }
	<br> integer:${POJOModel.integer }
	<br> double1: ${POJOModel.double1 }
	<br> float1: ${POJOModel.float1 }
	<br> long1: ${POJOModel.long1 }
	<br> int1: ${POJOModel.int1 }
	<br> doublt1: ${POJOModel.doublt1 }
	<br> list: ${POJOModel.list }
	<c:forEach items="${POJOModel.map }" var="item">
		<br> ${item.key }: ${item.value }
	</c:forEach>
</body>
</html>
