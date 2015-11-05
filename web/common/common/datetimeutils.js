/** ****************************时间相关函数*********************************** */
function addDays(date, d) {
	date.setDate(date.getDate + d);
}

function addWeeks(date, w) {
	addDays(date, w * 7);
}

function addMonths(date, m) {
	var d = date.getDate();
	date.setMonth(date.getMonth() + m);

	if (date.getDate() < d)
		date.setDate(0);
}

function addYear(date, y) {
	var m = date.getMonth();
	date.setFullYear(date.getFullYear() + y);

	if (m < date.getMonth())
		date.setDate(0);
}

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"H+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

// 将Date转换成字符串
function getTimeFmt(date, datefmt) {
	if (datefmt == null || datefmt == '') {
		datefmt = 'yyyy-MM-dd HH:mm:ss';
	}
	return date.format(datefmt);
}

// 通过字符串获得时间
function getTimeFromStr(dateString, formatString) {
	if (validateDate(dateString, formatString)) {
		var now = new Date();
		var vals = regexp.exec(dateString);
		var index = validateIndex(formatString);
		var year = index[0] >= 0 ? vals[index[0] + 1] : now.getFullYear();
		var month = index[1] >= 0 ? (vals[index[1] + 1] - 1) : now.getMonth();
		var day = index[2] >= 0 ? vals[index[2] + 1] : now.getDate();
		var hour = index[3] >= 0 ? vals[index[3] + 1] : "";
		var minute = index[4] >= 0 ? vals[index[4] + 1] : "";
		var second = index[5] >= 0 ? vals[index[5] + 1] : "";

		var validate;

		if (hour == "")
			validate = new Date(year, month, day);
		else
			validate = new Date(year, month, day, hour, minute, second);

		if (validate.getDate() == day)
			return validate;

	}
	alert("wrong date");
}

/** year : /yyyy/ */
var y4 = "([0-9]{4})";
/** year : /yy/ */
var y2 = "([0-9]{2})";
/** index year */
var yi = -1;
/** month : /MM/ */
var M2 = "(0[1-9]|1[0-2])";
/** month : /M/ */
var M1 = "([1-9]|1[0-2])";
/** index month */
var Mi = -1;
/** day : /dd/ */
var d2 = "(0[1-9]|[1-2][0-9]|30|31)";
/** day : /d/ */
var d1 = "([1-9]|[1-2][0-9]|30|31)";
/** index day */
var di = -1;
/** hour : /HH/ */
var H2 = "([0-1][0-9]|20|21|22|23)";
/** hour : /H/ */
var H1 = "([0-9]|1[0-9]|20|21|22|23)";
/** index hour */
var Hi = -1;
/** minute : /mm/ */
var m2 = "([0-5][0-9])";
/** minute : /m/ */
var m1 = "([0-9]|[1-5][0-9])";
/** index minute */
var mi = -1;
/** second : /ss/ */
var s2 = "([0-5][0-9])";
/** second : /s/ */
var s1 = "([0-9]|[1-5][0-9])";
/** index month */
var si = -1;

var regexp;

function validateDate(dateString, formatString) {
	var dateString = trim(dateString);
	if (dateString == "")
		return;
	var reg = formatString;
	reg = reg.replace(/yyyy/, y4);
	reg = reg.replace(/yy/, y2);
	reg = reg.replace(/MM/, M2);
	reg = reg.replace(/M/, M1);
	reg = reg.replace(/dd/, d2);
	reg = reg.replace(/d/, d1);
	reg = reg.replace(/HH/, H2);
	reg = reg.replace(/H/, H1);
	reg = reg.replace(/mm/, m2);
	reg = reg.replace(/m/, m1);
	reg = reg.replace(/ss/, s2);
	reg = reg.replace(/s/, s1);
	reg = new RegExp("^" + reg + "$");
	regexp = reg;
	return reg.test(dateString);
}

function validateIndex(formatString) {
	var ia = new Array();
	var i = 0;
	yi = formatString.search(/yyyy/);
	if (yi < 0)
		yi = formatString.search(/yy/);
	if (yi >= 0) {
		ia[i] = yi;
		i++;
	}

	Mi = formatString.search(/MM/);
	if (Mi < 0)
		Mi = formatString.search(/M/);
	if (Mi >= 0) {
		ia[i] = Mi;
		i++;
	}

	di = formatString.search(/dd/);
	if (di < 0)
		di = formatString.search(/d/);
	if (di >= 0) {
		ia[i] = di;
		i++;
	}

	Hi = formatString.search(/HH/);
	if (Hi < 0)
		Hi = formatString.search(/H/);
	if (Hi >= 0) {
		ia[i] = Hi;
		i++;
	}

	mi = formatString.search(/mm/);
	if (mi < 0)
		mi = formatString.search(/m/);
	if (mi >= 0) {
		ia[i] = mi;
		i++;
	}

	si = formatString.search(/ss/);
	if (si < 0)
		si = formatString.search(/s/);
	if (si >= 0) {
		ia[i] = si;
		i++;
	}

	var ia2 = new Array(yi, Mi, di, Hi, mi, si);

	for (i = 0; i < ia.length - 1; i++)
		for (var j = 0; j < ia.length - 1 - i; j++)
			if (ia[j] > ia[j + 1]) {
				temp = ia[j];
				ia[j] = ia[j + 1];
				ia[j + 1] = temp;
			}

	for (i = 0; i < ia.length; i++)
		for (var j = 0; j < ia2.length; j++)
			if (ia[i] == ia2[j]) {
				ia2[j] = i;
			}

	return ia2;
}

function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
