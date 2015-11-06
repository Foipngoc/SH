<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
    <link rel="stylesheet" type="text/css" href="common/plugins/fontawesome/css/font-awesome.min.css">

</head>

<body>
<form action="example/test?_method=delete" method="post">
    <button class="btn btn-primary">提交delete</button>
</form>
<form action="example/test?_method=put" method="post">
    <button class="btn btn-warning"><i class="icon-arrow-down"></i>提交put</button>
</form>
</body>
</html>
