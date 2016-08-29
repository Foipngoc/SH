<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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

    <title>Page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/plugins/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/css/common.css">

    <script type="text/javascript" language="javascript"
            src="common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/plugins/bootstrap/js/bootstrap.min.js"></script>

</head>

<body>
<input type="hidden" id="basePath" value="<%=basePath%>">
<h1>This is my firstpage</h1>
<a href="<%=basePath%>/example/queryStuByName?name=123">测试</a>
</body>
</html>
