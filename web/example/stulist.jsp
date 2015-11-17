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

    <link rel="stylesheet" type="text/css" href="common/plugins/bootstrap/css/bootstrap.min.css">


</head>

<body>
Stulist:
<div class="col-xs-12">
    <table class="table table-bordered">
        <thead>
        <th>姓名</th>
        <th>年龄</th>
        <th>出生年月</th>
        <th>班级号</th>
        </thead>
        <c:forEach items="${stu.data}" var="s">
            <tr>
                <td>${s.name }</td>
                <td>${s.age }</td>
                <td>${s.born }</td>
                <td>${s.room.roomnum }</td>
            </tr>
        </c:forEach>

    </table>
    <div>
        <nav>
            <ul class="pagination">
                <li>
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li><a href="example/queryallstu?page=1&rows=10">1</a></li>
                <li><a href="example/queryallstu?page=2&rows=10">2</a></li>
                <li><a href="example/queryallstu?page=3&rows=10">3</a></li>
                <li><a href="example/queryallstu?page=4&rows=10">4</a></li>
                <li><a href="example/queryallstu?page=5&rows=10">5</a></li>
                <li>
                    <a href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
