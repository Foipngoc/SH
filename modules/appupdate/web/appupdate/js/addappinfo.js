$(document).ready(function() {

});

function addappinfo() {
	var appname = $("#appname").val();
	var appdesc = $("#appdesc").val();
	var weixinpage = $("#weixinpage").val();

	if (appname == null || appname == "") {
		alert("应用名不能为空");
		return;
	}
	if (appdesc == null || appdesc == "") {
		alert("应用描述不能为空");
		return;
	}
	if (weixinpage == null) {
		weixinpage = '';
	}

	$.ajaxFileUpload({
		url : 'publishApp', // 用于文件上传的服务器端请求地址
		secureuri : false, // 是否需要安全协议，一般设置为false
		fileElementId : 'file', // 文件上传域的ID
		dataType : 'json', // 返回值类型 一般设置为json
		data : {
			'appname' : appname,
			'appdesc' : appdesc,
			'weixinpage' : weixinpage
		},
		success : function(data, status) // 服务器成功响应处理函数
		{
			var resultcode = data.result.resultcode;
			var resultdesc = data.result.resultdesc;
			alert(resultdesc);
			if (resultcode == 0) {
				window.location.href = $("#basePath").val()
						+ "module/appversioncheck/appinfo.jsp";
			}
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{

		}
	});
}