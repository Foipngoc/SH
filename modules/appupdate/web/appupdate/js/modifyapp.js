$(document).ready(
		function() {
			document.onkeydown = function(event) {
				var e = event || window.event
						|| arguments.callee.caller.arguments[0];
				if (e && e.keyCode == 27) { // 按 Esc
					document.getElementById("win").style.display = "none";
				}
			};
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
					var appdesc = appinfo.appdesc;
					var weixinpage = appinfo.weixindlpg;
					var newestversion = data.result.map.appinfo.newestvcode;
					var newestappvid = appinfo.newestappvid;
					$("#newestappvid").val(newestappvid);
					$("#appname").val(appname);
					$("#appdesc").val(appdesc);
					$("#weixinpage").val(weixinpage);
					$("#newestversion").val(newestversion);
					document.getElementById("apptitle").innerHTML = "修改应用<"
							+ appname + ">";
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

function modifyappinfo() {
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
		url : 'updateApp',
		secureuri : false, // 是否需要安全协议，一般设置为false
		fileElementId : 'file', // 文件上传域的ID
		dataType : 'json',
		data : {
			'appid' : $("#appid").val(),
			'appname' : $("#appname").val(),
			'appdesc' : $("#appdesc").val(),
			'weixinpage' : weixinpage
		},
		success : function(data) {
			var resultcode = data.result.resultcode;
			var resultdesc = data.result.resultdesc;
			alert(resultdesc);
			if (resultcode == 0) {
				window.location.href = $("#basePath").val()
						+ "module/appversioncheck/appinfo.jsp";
			}
		}
	});
	return;
}

function selversion() {
	$.ajax({
		url : 'queryAppVersions',
		type : 'post',
		dataType : 'json',
		data : {
			'appid' : $("#appid").val()
		},
		success : function(data) {
			var tb = document.getElementById('listTable');
			var rowNum = tb.rows.length;
			for (var i = 1; i < rowNum; i++) {
				tb.deleteRow(i);
				rowNum = rowNum - 1;
				i = i - 1;
			}
			document.getElementById("win").style.display = "";
			var list = data.result.map.appversioninfos;
			for (var i = 0; i < list.length; i++) {
				var newTr = $("<tr class='addTr'></tr>");
				newTr.append($("<td>"
						+ "<input name='selappvid' type='radio' value="
						+ list[i].id + ">" + "</td>"));
				newTr.append($("<td>" + list[i].versioncode + "</td>"));
				newTr.append($("<td name='selversionname'>"
						+ list[i].versionname + "</td>"));
				newTr.append($("<td>" + list[i].updatedate + "</td>"));
				newTr.append($("<td>" + getupdatetype(list[i].updatetype)
						+ "</td>"));
				$(".listTable").append(newTr);
			}

			var rg = document.getElementsByName("selappvid");
			var newestappvid = $("#newestappvid").val();
			for (var i = 0; i < rg.length; i++) {
				if (rg[i].value == newestappvid)
					rg[i].checked = true;
			}
		}
	});
}

function setsel() {
	var rg = document.getElementsByName("selappvid");
	var svn = document.getElementsByName("selversionname");
	var selid = -1;
	var selversionname = "";
	for (var i = 0; i < rg.length; i++) {
		if (rg[i].checked) {
			selid = rg[i].value;
			selversionname = svn[i].innerText;
		}
	}

	if (selid == -1) {
		alert("请选择该应用的最新版本");
		return;
	}
	if (selid != $("#newestappvid").val()) {
		$.ajax({
			url : 'updateNewestAppVersion',
			type : 'post',
			dataType : 'json',
			data : {
				'appid' : $("#appid").val(),
				'appvid' : selid
			},
			success : function(data) {
				var resultcode = data.result.resultcode;
				var resultdesc = data.result.resultdesc;
				alert(resultdesc);
				if (resultcode == 0) {
					document.getElementById("win").style.display = "none";
					if (selversionname != "") {
						$("#newestversion").val(selversionname);
						$("#newestappvid").val(selid);
					}
				}
			}
		});
	} else {
		document.getElementById("win").style.display = "none";
	}
}
