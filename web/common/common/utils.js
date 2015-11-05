//输入框提示
function TextFocus(id) {
	if (id.value == id.defaultValue) {
		id.value = '';
		id.style.color = '#333333';
	}
}
// 输入框提示
function TextBlur(id) {
	if (!id.value) {
		id.value = id.defaultValue;
		id.style.color = '#a3a3a3';
	}
}

function rand(n, m) {
	var c = m - n + 1;
	return Math.floor(Math.random() * c + n);
}

// 获得最根级窗口，常用于iframe超时
function getRootWin() {
	var win = window;
	while (win != win.parent) {
		win = win.parent;
	}
	return win;
}

// 获得datatable服务器分页时的对应name的值
function getDateTableParam(aoData, name) {
	for (var i = 0; i < aoData.length; i++) {
		var item = aoData[i];
		if (item.name == name)
			return item.value;
	}
	return null;
}

// 获得datatable服务器分页时的对应name的值
function getDateTableParamBeginWith(aoData, name) {
	var list = new Array();
	for (var i = 0; i < aoData.length; i++) {
		var item = aoData[i];
		if (item.name.indexOf(name) == 0)
			list.push(item);
	}
	return list;
}

// 获得datatable服务器分页时的行数
function getDateTableRows(aoData) {
	var val = getDateTableParam(aoData, "iDisplayLength");
	if (val == null)
		return -1;
	else
		return val;
}

// 获得datatable服务器分页时的页码
function getDateTablePage(aoData) {
	var rows = getDateTableRows(aoData);
	var start = getDateTableParam(aoData, "iDisplayStart");
	if (start != null && rows != -1 && rows != 0) {
		return start / rows + 1;
	} else
		return -1;
}

// 将用户返回结果解析成DateTable可解析的json数据
function toDateTableJsonResult(dataList, iTotalRecords, iTotalDisplayRecords,sEcho) {
	var data = {
		sEcho : sEcho,
		iTotalRecords : iTotalRecords,
		iTotalDisplayRecords : iTotalDisplayRecords,
		aaData : dataList
	};
	return data;
}

/** ************************* 判断后台返回结果是否成功等通用方法 ****************************** */
// 判断结果集是否为操作成功
function ifResultOK(jsondata) {
	if (jsondata.resultcode == 0) {
		return true;
	} else {
		return false;
	}
}

// 获得结果集描述，常用于当返回码为操作不成功
function getResultDesc(jsondata) {
	return jsondata.resultdesc;
}

// 获得结果集码
function getResultCode(jsondata) {
	return jsondata.resultcode;
}

// 获得结果集map
function getResultMap(jsondata) {
	return jsondata.map;
}

// 获得结果集记录
function getResultRecords(jsondata) {
	return jsondata.records;
}

// 获得结果集obj对象
function getResultObj(jsondata) {
	return jsondata.obj;
}

/**
 * 将键值对对象设置到某一元素上
 */
$.fn.setobj = function(name,obj){
    // 添加自定义属性
    if (obj != null && name != null && name != '') {
        for ( var key in obj) {
            var val = obj[key];
            if (val instanceof Function)
                $(this).attr(name + "_" + key, val.getName());
            else
                $(this).attr(name + "_" + key, val);
        }
    }
};

/**
 * 从元素上获取某一对象，键值对对象
 */
$.fn.getobj = function(name){
    var attrs = $(this).get(0).attributes;
    var obj = {};
    try {
        for (var index = 0; index < attrs.length; index++) {
            var key = attrs[index].name;
            if (key.indexOf(name + "_") == 0) {
                key = key.replace(name + "_", '');
                var val = attrs[index].value;
                obj[key] = val;
            }
        }
    } catch (e) {
    }
    return obj;
};
