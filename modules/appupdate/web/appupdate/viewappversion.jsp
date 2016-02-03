<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title id="apptitle">查看应用版本</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">	
	<link rel="stylesheet" type="text/css"
	href="<%=basePath%>module/appversioncheck/css/viewappversion.css">
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>module/appversioncheck/js/viewappversion.js"></script>
	<script type="text/javascript" src="js/common/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

  </head>
  
  <body>
  <jsp:include page="top.jsp" flush="true" />
  <div class='firstplace'> 
<span style='color:#3984e5;cursor: pointer;' onclick="window.location.href='<%=basePath%>/module/appversioncheck/appinfo.jsp'" >所有应用     
</span>>&nbsp;
<span style='color:#3984e5;cursor: pointer;' onclick="window.location.href='<%=basePath%>module/appversioncheck/appversioninfo.jsp?appid=<%=request.getParameter("appid")%>'">
<span id='appname1'></span>的第<span id='appname2'></span>版本</span>&nbsp;>&nbsp;查看
</div>
 	<input id="basePath" name="basePath" type="hidden" value="<%=basePath%>">
  	<input type="hidden" value="<%=request.getParameter("appvid")%>" name="appvid" id="appvid">
 	<input type="hidden" value="<%=request.getParameter("appid")%>" name="appid" id="appid"/>
	<h2>应用名&nbsp;&nbsp;<input class='inputinfo' type="text" name="appname" id="appname" readonly="readonly" style="border: 0px; font-weight: bold;"></h2> <br>
	<h2>版本号&nbsp;&nbsp;<input class='inputinfo' type="text" name="versioncode" id="versioncode" readonly="readonly" style="border: 0px;"></h2> <br>
	<h2>版本名&nbsp;&nbsp;<input class='inputinfo' type="text" name="versionname" id="versionname" readonly="readonly" style="border: 0px;"> </h2><br>
	<h2>更新日志&nbsp;<textarea rows="" cols="" name="updatelog" id="updatelog" readonly="readonly" style=" height: 113px; width: 432px; border: solid 1px #e5ebf1;line-height: 20px;resize:none;font-size: 14px;"></textarea>
	</h2><br>
	<h2>更新方式&nbsp;<select class='inputinfo' name="updatetype" id="updatetype" disabled="disabled"> <option value="0">强制更新</option><option value="1">用户选择</option><option value="2">不弹出更新</option></select> </h2><br>
	<h2>下载APP&nbsp;&nbsp;<input type="text" id="respath" style="border:0px;" readonly="readonly"><input type="button" id="file" onclick="downloadfile()" value="下载APP"></h2><br>
	<h2>下载路径&nbsp;<input class='inputinfo' type="text" name="downloadpath" id="downloadpath" readonly="readonly"> </h2><br>
	<h2>自动安装&nbsp;<select class='inputinfo' name="autoinstall" id="autoinstall" disabled="disabled"><option value="1">自动安装</option><option value="0">不自动安装</option></select> </h2><br>
	<div class='secondplace'>
	<input type="button" value="返回" class='input1' onclick="closeview()">
	
  	</div>
  </body>
</html>
