$(document).ready(function(){
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
			document.getElementById("apptitle").innerHTML = "修改<"+appname+"'";
			$("#appname").val(appname);
			$("#appname1").text(appname);
			$.ajax({
				url : 'queryAppVersion',
				type : 'post',
				dataType : 'json',
				data : {
					'appvid': $("#appvid").val()
				},
				success : function(data) {
					var appvinfo = data.result.map.appVersionInfo;
					var versioncode = appvinfo.versioncode;
					var versionname = appvinfo.versionname;
					var updatelog = appvinfo.updatelog;
					var updatetype = appvinfo.updatetype;
					var downloadpath = appvinfo.downloadpath;
					var autoinstall = appvinfo.autoinstall;
					var respath = appvinfo.respath;
					$("#appname2").text(versioncode);
					document.getElementById("apptitle").innerHTML += versionname+"'>版本";
					
					$("#respath").val(getFileName(respath));
					$("#versioncode").val(versioncode);
					$("#versionname").val(versionname);
					$("#updatelog").val(updatelog);
					$("#updatetype")[0].selectedIndex = updatetype;
					$("#downloadpath").val(downloadpath);
					if (autoinstall == 0)
						$("#autoinstall")[0].selectedIndex = 1;
					if (autoinstall == 1)
						$("#autoinstall")[0].selectedIndex = 0;
				}
			});
		}
	});
});

function getFileName(url){
	var pos = url.lastIndexOf("/");
	if(pos == -1){
	   pos = url.lastIndexOf("\\");
	}
	var filename = url.substr(pos +1);
	return filename;
}

function filesel() {
	var path = $("#file").val();
	$("#respath").val(getFileName(path));
}

function modifyAppversioninfo() {
	var versionname = $("#versionname").val();
	var updatelog = $("#updatelog").val();
	var updatetype = $("#updatetype").find("option:selected").val();
	var downloadpath = $("#downloadpath").val();
	var autoinstall = $("#autoinstall").find("option:selected").val();
	if (versionname == ""){
		alert("请输入版本名");
		return;
	}
	if (updatelog == ""){
		alert("请输入更新日志");
		return;
	}
	
	$.ajaxFileUpload({
		url : 'updateAppVersion', // 用于文件上传的服务器端请求地址
		secureuri : false, // 是否需要安全协议，一般设置为false
		fileElementId : 'file', // 文件上传域的ID
		dataType : 'json', // 返回值类型 一般设置为json
		data : {
			appvid:$("#appvid").val(),
			versionname: versionname,
			updatelog: updatelog,
			updatetype: updatetype,
			downloadpath: downloadpath,
			autoinstall: autoinstall
		},
		success : function(data, status) // 服务器成功响应处理函数
		{
			var resultcode = data.result.resultcode;
			if (resultcode == 0) {
				alert("更新成功");
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