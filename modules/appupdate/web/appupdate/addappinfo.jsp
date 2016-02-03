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

<title>发布新应用</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/table.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>module/appversioncheck/css/addappinfo.css">
<script src="js/common/jquery-1.10.2.min.js"></script>
<script src="js/common/ajaxfileupload.js"></script>
<script src="js/common/common.js"></script>
<script type="text/javascript"
	src="<%=basePath%>module/appversioncheck/js/addappinfo.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body>
	<jsp:include page="top.jsp" flush="true" />
	<div class='firstplace'>
		<span style='color:#3984e5;cursor: pointer;'
			onclick="window.location.href='<%=basePath%>/module/appversioncheck/appinfo.jsp'">所有应用
		</span>>&nbsp;发布新应用
	</div>
	<input id="basePath" name="basePath" type="hidden"
		value="<%=basePath%>">
	<h2 style='	margin-top: 30px;'>
		应用名称&nbsp;&nbsp;<input type="text" id="appname" value="请输入应用名称"
			onfocus="TextFocus(this)" onblur="TextBlur(this)"
			style="color:#a3a3a3; height: 36px; width: 432px; border: solid 1px #e5ebf1;">
	</h2>
	<br>
	<h2>
		<span style='position: relative;top:-100px;'>应用描述&nbsp;&nbsp;</span>
		<textarea rows="" cols="" id="appdesc"
			style=" height: 113px; width: 432px; border: solid 1px #e5ebf1;line-height: 20px;resize:none;"></textarea>
	</h2>
	<br>
	<h2>
		应用LOGO&nbsp;&nbsp;<input type="file" id="file" name="file"
			accept="image/jpeg,image/png,image/x-ms-bmp, image/bmp">
	</h2>
	<br>
	<h2>
		微信下载页&nbsp;<input type="text"
			placeholder="请输入微信下载页面地址，如 page/weixindl.jsp" id="weixinpage"
			style="color:#a3a3a3; height: 36px; width: 432px; border: solid 1px #e5ebf1;">
	</h2>
	<br>
	<div class='secondplace'>
		<input type="button" onclick="addappinfo()" class='input'
			value="发布新应用"> <input type="button" class='input1'
			value="取消发布"
			onclick="window.location.href='<%=basePath%>/module/appversioncheck/appinfo.jsp'">
	</div>
</body>
</html>
