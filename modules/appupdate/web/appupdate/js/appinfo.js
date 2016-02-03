$(document).ready(function() {
	showAppList();
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 27) { // 按 Esc
			hideUrlBar();
		}
		;
	};
});

function showAppList() {
	$
			.ajax({
				url : 'queryApps',
				type : 'post',
				dataType : 'json',
				data : {

				},
				success : function(data) {
					var list = data.result.map.appinfos;
					for (var i = 0; i < list.length; i++) {
						var newTr = $("<tr class='addTr'></tr>");
						newTr.append($("<td>" + list[i].appinfo.id + "</td>"));
						if (list[i].appinfo.applogo != null)
							newTr
									.append($("<td><img width='50' height='50' src='"
											+ $("#basePath").val()
											+ "/downloadAppLogo?appid="
											+ list[i].appinfo.id
											+ "'></img></td>"));
						else
							newTr.append($("<td>暂无LOGO</td>"));
						newTr
								.append($("<td><a style='color:blue;text-decoration:underline' href='"
										+ $("#basePath").val()
										+ "module/appversioncheck/appversioninfo.jsp?appid="
										+ list[i].appinfo.id
										+ "'>"
										+ list[i].appinfo.appname + "</a></td>"));
						newTr.append($("<td>" + list[i].appinfo.appdesc
								+ "</td>"));
						newTr.append($("<td>" + list[i].appinfo.createdate
								+ "</td>"));
						newTr.append($("<td>" + list[i].newestvcode + "</td>"));
						newTr
								.append($("<td><a style='color:blue;text-decoration:underline' onclick='modifyApp("
										+ list[i].appinfo.id
										+ ")'>修改</a>&nbsp;&nbsp;"
										+ "<a style='color:blue;text-decoration:underline' onclick='delApp("
										+ list[i].appinfo.id
										+ ")'>删除</a>&nbsp;&nbsp;"
										+ "<a style='color:red;text-decoration:underline' onclick='showAppDownLink("
										+ list[i].appinfo.id
										+ ")'>下载链接</a>"
										+ "</td>"));
						$(".listTable").append(newTr);
					}
				}
			});
}

function modifyApp(appid) {
	window.location.href = $("#basePath").val()
			+ "module/appversioncheck/modifyapp.jsp?appid=" + appid;
}

function delApp(id) {
	if (confirm("你确定要删除该应用吗？")) {
		$.ajax({
			url : 'delApp',
			type : 'post',
			dataType : 'json',
			data : {
				'appid' : id
			},
			success : function(data) {
				window.location.reload();
			}
		});
	}
}

function showAppDownLink(id) {
	var url = $("#basePath").val()
			+ "downloadNewest?clientinfo=browser&versioncode=-1&appid=" + id;
	document.getElementById("win").style.display = "";
	$("#newdlurl").val(url);

	var urlbarcode = $("#basePath").val()
			+ "downloadNewest?clientinfo=barcode&versioncode=-1&appid=" + id;
	$.ajax({
		url : 'genBarCode',
		type : 'post',
		dataType : 'json',
		data : {
			'appid' : id,
			'url' : urlbarcode,
			'timestamp' : new Date().getTime()
		},
		success : function(data) {
			var resultcode = data.result.resultcode;
			if (resultcode == 0) {
				$("#newdlurlbarcode").attr("src",
						$("#basePath").val() + "/downloadBarCode?appid=" + id);
			} else {
				alert(data.result.resultdesc);
			}
		}
	});
}

function hideUrlBar() {
	$("#newdlurlbarcode").attr("src", "");
	document.getElementById("win").style.display = "none";
}