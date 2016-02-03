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

<title id="apptitle">发布应用版本</title>

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
	href="<%=basePath%>module/appversioncheck/css/addappversioninfo.css">
<script src="js/common/jquery-1.10.2.min.js"></script>
<script src="js/common/ajaxfileupload.js"></script>
<script src="js/common/common.js"></script>
<script type="text/javascript"
	src="<%=basePath%>module/appversioncheck/js/addappversioninfo.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body>
<jsp:include page="top.jsp" flush="true" />
<div class='firstplace'> 
<span style='color:#3984e5;cursor: pointer;' onclick="window.location.href='<%=basePath%>/module/appversioncheck/appinfo.jsp'" >所有应用     
</span>>&nbsp;
<span style='color:#3984e5;cursor: pointer;' onclick="window.location.href='<%=basePath%>module/appversioncheck/appversioninfo.jsp?appid=<%=request.getParameter("appid")%>'">
<span id='appname1'></span>的所有版本</span>&nbsp;>&nbsp;发布新版本
</div>
	<input id="basePath" name="basePath" type="hidden" value="<%=basePath%>">
	<div align="left" style="width: 80%">
	<input type="hidden" value="<%=request.getParameter("appid")%>" name="appid" id="appid"/>
	<h2 style='margin-top: 30px;'>应用名&nbsp;&nbsp;<input type="text" name="appname" id="appname" readonly="readonly" style="border: 0px; font-weight: bold;font-size: 14px;"></h2> <br>
	<h2>版本号&nbsp;&nbsp;<input class='inputinfo' value="请输入对应APP版本号，只能为数字" style="color:#a3a3a3" onfocus="TextFocus(this)" onblur="TextBlur(this)" type="text" name="versioncode" id="versioncode" onkeyup="value=value.replace(/\D/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/\D]/g,''))"></h2> <br>
	<h2>版本名&nbsp;&nbsp;<input class='inputinfo' type="text" name="versionname" id="versionname" value="请输入版本名，如V1.0.1212" style="color:#a3a3a3" onfocus="TextFocus(this)" onblur="TextBlur(this)"> </h2><br>
	<h2>更新日志&nbsp;<textarea rows="" cols="" name="updatelog" id="updatelog" style=" height: 113px; width: 432px; border: solid 1px #e5ebf1;line-height: 20px;resize:none;"></textarea></h2><br>
	<h2>更新方式&nbsp;<select name="updatetype" id="updatetype" class='inputinfo'> <option value="0">强制更新</option><option value="1">用户选择</option><option value="2">不弹出更新</option></select> </h2><br>
	<h2>选择APP&nbsp;&nbsp;<input type="file" name="file" id="file"></h2><br>
	<h2>设为最新&nbsp;<select name="autoset" id="autoset" class='inputinfo'><option value="1">自动设置</option><option value="0">不自动设置</option></select></h2><br>
	<h2>自动安装&nbsp;<select name="autoinstall" id="autoinstall" class='inputinfo'><option value="1">自动安装</option><option value="0">不自动安装</option></select> </h2><br>
	<h2>下载路径&nbsp;<input type="text" name="downloadpath" id="downloadpath" class='inputinfo'> </h2><br>
	<div class='secondplace'>
	<input type="button" value="发布新版本" onclick="publicAppversioninfo()" class='input'>
	<input type="button" value="取消发布" class='input1' onclick="window.location.href='<%=basePath%>module/appversioncheck/appversioninfo.jsp?appid=<%=request.getParameter("appid")%>'">
	</div>
	</div>
</body>
</html>
