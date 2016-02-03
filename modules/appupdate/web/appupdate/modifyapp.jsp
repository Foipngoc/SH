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

<title id="apptitle">修改应用</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/table.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>module/appversioncheck/css/modifyapp.css">
<script src="js/common/jquery-1.10.2.min.js"></script>
<script src="js/common/common.js"></script>
<script src="js/common/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="<%=basePath%>module/appversioncheck/js/modifyapp.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body style='font-family: 微软雅黑;'>
	<jsp:include page="top.jsp" flush="true" />
	<div class='firstplace'>
		<span style='color:#3984e5;cursor: pointer;'
			onclick="window.location.href='<%=basePath%>/module/appversioncheck/appinfo.jsp'">所有应用
		</span>>&nbsp;修改
	</div>
	<input id="basePath" name="basePath" type="hidden"
		value="<%=basePath%>">
	<input type="hidden" value="<%=request.getParameter("appid")%>"
		id="appid">
	<input type="hidden" value="" id="newestappvid">
	<h2 style='	margin-top: 30px;'>
		应用名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
			value="请输入应用名称" onfocus="TextFocus(this)" onblur="TextBlur(this)"
			style=" height: 36px; width: 289px; border: solid 1px #e5ebf1;font-size: 14px;"
			type="text" id="appname">
	</h2>
	<br>
	<h2>
		<span style='position: relative;top:-100px;'>应用描述&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<textarea rows="" cols="" id="appdesc"
			style=" height: 113px; width: 432px; border: solid 1px #e5ebf1;line-height: 20px;resize:none;font-size: 14px;"></textarea>
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
	<h2>
		最新版本&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
			style=" height: 36px; width: 289px; border: solid 1px #e5ebf1;font-size: 14px;"
			type="text" readonly=true id="newestversion"><input
			class='input1' type="button" id="selversion" value="选择最新版本"
			onclick="selversion()" style='height:36px;'>
	</h2>
	<br>
	<div class='secondplace'>
		<input type="button" onclick="modifyappinfo()" value="修改应用"
			class='input'> <input type="button" class='input1'
			onclick="window.location.href='<%=basePath%>/module/appversioncheck/appinfo.jsp'"
			value="取消发布">
	</div>
	<div id=win
		style="
  	display:none;white; 
  	POSITION:absolute; 
  	left: 50%;
	top: 50%; 
  	width:600px; height:400px; 
  	margin-left:-300px; 
  	background-color:#ffffff;
  	margin-top:-200px; 
  	border:1px solid #888; 
  	text-align:left">
		<div class='win_1'>&nbsp;&nbsp;选择最新版本</div>
		<table class="listTable" id="listTable" cellpadding="0"
			cellspacing="0" style='width:90%;margin-left: 5%;'>
			<col width="20%" />
			<col width="20%" />
			<col width="30%" />
			<col width="20%" />
			<col width="20%" />
			<tr>
				<th>选择</th>
				<th>版本号</th>
				<th>版本名</th>
				<th>更新时间</th>
				<th>更新方式</th>
			</tr>
		</table>
		<div class='lastplace'>
			<input type="button" class='input' value="确定选择" onclick="setsel()"
				style='margin-left: 400px;'>&nbsp;&nbsp;<input
				class='input1' type="button" value="取消选择"
				onclick="document.getElementById('win').style.display='none';">
		</div>
	</div>

</body>
</html>
