$(document).ready(function() {
	$.ajax({
		url : 'queryApp',
		type : 'post',
		dataType : 'json',
		data : {
			'appid' : $("#appid").val()
		},
		success : function(data) {
			var appinfo = data.result.map.appinfo.appinfo;
			var appname = appinfo.appname;
			$("#appname").val(appname);
			$("#appname1").text(appname);
			$.ajax({
				url : 'queryAppVersion',
				type : 'post',
				dataType : 'json',
				data : {
					'appvid' : $("#appvid").val()
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

function getFileName(url) {
	var pos = url.lastIndexOf("/");
	if (pos == -1) {
		pos = url.lastIndexOf("\\");
	}
	var filename = url.substr(pos + 1);
	return filename;
}

function closeview() {
	window.location.href = $("#basePath").val()
			+ "module/appversioncheck/appversioninfo.jsp?appid="
			+ $("#appid").val();
}

function downloadfile() {
	window.location.href = $("#basePath").val()
			+ "downloadAppVersionRes?appvid=" + $("#appvid").val();
}