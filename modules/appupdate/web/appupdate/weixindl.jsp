<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>应用下载</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script src="js/common/jquery-1.10.2.min.js"></script>

</head>

<script type="text/javascript">
	function dodownload() {
		window.location.href = $("#basePath").val()
				+ "downloadNewest?clientinfo=weixin&versioncode=-1&appid="
				+ $("#appid").val();
	}
</script>

<body style="font-family: 微软雅黑;">
	<input type="hidden" id="appid"
		value="<%=request.getParameter("appid")%>">
	<input id="basePath" name="basePath" type="hidden"
		value="<%=basePath%>">
	<div>
		<h1>请从右上角点击打开应用</h1>
		<h1>然后点击下方连接进行下载</h1>
	</div>
	<br>
	<br>
	<br>
	<button style="width:400px;height:100px;font-size:20px;"onclick="dodownload();">点击下载</button>
</body>
</html>
