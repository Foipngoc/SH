$(document).ready(function() {
	var appid = $("#appid").val();
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
			var newestappvid = appinfo.newestappvid;
			
			$("#appname").text(appname);
			
			
			showAppVersionList(appid,newestappvid);
		}
	});
	
});

function getupdatetype(type) {
	if (type == 0)
		return "强制更新";
	if (type == 1)
		return "用户选择";
	if (type == 2)
		return "不弹出更新";
}

function getFileName(url){
	var pos = url.lastIndexOf("/");
	if(pos == -1){
	   pos = url.lastIndexOf("\\");
	}
	var filename = url.substr(pos +1);
	return filename;
}

function showAppVersionList(appid,newestappvid){
	$.ajax({
		url : 'queryAppVersions',
		type : 'post',
		dataType : 'json',
		data : {
			'appid': appid
		},
		success : function(data) {
			var list = data.result.map.appversioninfos;
			for (var i = 0; i < list.length; i++) {
				var newTr = $("<tr class='addTr'></tr>");
				newTr.append($("<td>" + (i + 1) + "</td>"));
				newTr.append($("<td>" + list[i].versioncode + "</td>"));
				newTr.append($("<td><a style='color:blue;text-decoration:underline' onclick='viewAppVersion("
						+list[i].id+")'>" + list[i].versionname + "</a></td>"));
				newTr.append($("<td>" + list[i].updatedate + "</td>"));
				newTr.append($("<td>" + getupdatetype(list[i].updatetype) + "</td>"));
				newTr.append($("<td>" + getFileName(list[i].respath) + "</td>"));
				if (newestappvid == list[i].id) {
					newTr.append($("<td><a style='color:blue;text-decoration:underline' onclick='modifyAppVersion("
							+ list[i].id
							+ ")'>修改</a>&nbsp;<a style='color:blue;text-decoration:underline' onclick='delAppVersion("
							+ list[i].id + ")'>删除</a>&nbsp;<a style='color:red;'>当前最新</a></td>"));
				}else{
					newTr.append($("<td><a style='color:blue;text-decoration:underline' onclick='modifyAppVersion("
							+ list[i].id
							+ ")'>修改</a>&nbsp;<a style='color:blue;text-decoration:underline' onclick='delAppVersion("
							+ list[i].id + ")'>删除</a>&nbsp;<a style='color:blue;text-decoration:underline' onclick='settonewest("
							+ list[i].id + ")'>设为最新</a></td>"));
				}
					
				$(".listTable").append(newTr);
			}
		}
	});
}

function delAppVersion(appvid) {
	if (confirm("你确定要删除该应用吗？")) {
		$.ajax({
			url : 'deleteAppVersion',
			type : 'post',
			dataType : 'json',
			data : {
				'appvid': appvid
			},
			success : function(data) {
				window.location.reload();
			}
		});
	}
}

function modifyAppVersion(appvid) {
	window.location.href=$("#basePath").val()+"module/appversioncheck/modifyappversion.jsp?appvid="+appvid+"&appid="+$("#appid").val();
}

function viewAppVersion(appvid) {
	window.location.href=$("#basePath").val()+"module/appversioncheck/viewappversion.jsp?appvid="+appvid+"&appid="+$("#appid").val();
}

function addAppVersion() {
	window.location.href=$("#basePath").val()+"module/appversioncheck/addappversioninfo.jsp?appid="+$("#appid").val();
}

function settonewest(appvid) {
	$.ajax({
		url : 'updateNewestAppVersion',
		type : 'post',
		dataType : 'json',
		data : {
			'appid': $("#appid").val(),
			'appvid': appvid
		},
		success : function(data) {
			var resultcode = data.result.resultcode;
			var resultdesc = data.result.resultdesc;
			alert(resultdesc);
			if (resultcode == 0){
				window.location.reload();
			}
		}
	});
}
