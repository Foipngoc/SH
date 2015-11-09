<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<br> name: ${stu.name }
<br> age:${stu.age }
<br> born: ${stu.born }
<br> room: ${stu.room.roomnum }
<br>
该学生的同班同学(lazy加载）
<c:forEach items="${stu.room.students}" var="stu">
    <br> name: ${stu.name }
    <br> age:${stu.age }
    <br> born: ${stu.born }
    <br> room: ${stu.room.roomnum }
    <br>
</c:forEach>
</body>
</html>
