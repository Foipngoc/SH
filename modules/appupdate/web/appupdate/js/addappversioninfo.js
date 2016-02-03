$(document).ready(function() {
	$.ajax({
		url : 'queryApp',
		type : 'post',
		dataType : 'json',
		data : {
			'appid': $("#appid").val()
		},
		success : function(data) {
			var appinfo = data.result.map.appinfo.appinfo;
			var appname = appinfo.appname;
			$("#appname1").text(appname);
			document.getElementById("apptitle").innerHTML = "发布<"+appname+">版本";
			$("#appname").val(appname);
		}
	});
});

function publicAppversioninfo() {
	var versioncode = $("#versioncode").val();
	var versionname = $("#versionname").val();
	var updatelog = $("#updatelog").val();
	var updatetype = $("#updatetype").find("option:selected").val();
	var autoset = $("#autoset").find("option:selected").val();
	var downloadpath = $("#downloadpath").val();
	var autoinstall = $("#autoinstall").find("option:selected").val();
	var filename = $("#file").val();
	if (versioncode == ""){
		alert("请输入版本号");
		return;
	}
	if (versionname == ""){
		alert("请输入版本名");
		return;
	}
	if (updatelog == ""){
		alert("请输入更新日志");
		return;
	}
	if (filename == "") {
		alert("请选择APP文件");
		return;
	}
	
	
	$.ajaxFileUpload({
		url : 'publishAppVersion', // 用于文件上传的服务器端请求地址
		secureuri : false, // 是否需要安全协议，一般设置为false
		fileElementId : 'file', // 文件上传域的ID
		dataType : 'json', // 返回值类型 一般设置为json
		data : {
			appid:$("#appid").val(),
			autogenvcode: 0,
			versioncode: versioncode,
			versionname: versionname,
			updatelog: updatelog,
			updatetype: updatetype,
			autoset: autoset,
			downloadpath: downloadpath,
			autoinstall: autoinstall
		},
		success : function(data, status) // 服务器成功响应处理函数
		{
			var resultcode = data.result.resultcode;
			if (resultcode == 0) {
				alert("发布成功");
				window.location.href=$("#basePath").val()
					+"module/appversioncheck/appversioninfo.jsp?&appid="
					+$("#appid").val();
			}else{
				alert(data.result.resultdesc);
			}
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{
			
		}
	});
}