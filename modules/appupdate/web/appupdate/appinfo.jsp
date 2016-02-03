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

<title>查看所有应用</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/table.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>module/appversioncheck/css/appinfo.css">
<script src="js/common/jquery-1.10.2.min.js"></script>
<script src="js/common/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="<%=basePath%>module/appversioncheck/js/appinfo.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body style="font-family: 微软雅黑;">
<jsp:include page="top.jsp" flush="true" />
<div class='firstplace'> 所有应用</div>
	<input id="basePath" name="basePath" type="hidden" value="<%=basePath%>">
	<div align="center" style="width: 100%">
	<div class='secondplace'>
	<input type="button" class='input' value="发布新应用" onclick="window.location.href='<%=basePath%>/module/appversioncheck/addappinfo.jsp'" >
	</div>
	<table class="listTable" id="" cellpadding="0" cellspacing="0">
		<col width="10%" />
		<col width="10%" />
		<col width="10%" />
		<col width="20%" />
		<col width="20%" />
		<col width="10%" />
		<col width="20%" />
		<tr style=''>
			<th>应用ID</th>
			<th>应用LOGO</th>
			<th>应用名</th>
			<th>应用描述</th>
			<th>创建时间</th>
			<th>最新版版本</th>
			<th>操作</th>
		</tr>
		
	</table>
	</div>
	
	<div id="win" style="display:none; POSITION:absolute; left:50%; top:50%; width:600px; height:400px; margin-left:-300px; margin-top:-200px; border:0px solid #888; border: solid 1px #ccc;background: white; text-align:left">
		<div class='win_1'>&nbsp;&nbsp;下载连接<span style='cursor: pointer;margin-left: 500px;'onclick="hideUrlBar()">取消</span>
		</div>
		<textarea id="newdlurl" rows="6" cols="70" style='resize:none;border: 0;margin-left: 50px;font-size: 12px;' readonly="readonly"></textarea>
		<img id="newdlurlbarcode" src="" style='width:200px;height:200px;margin-left: 200px;margin-top: 20px;'>
	</div>
</body>
</html>
