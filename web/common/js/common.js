/**
 * 取随机数
 *
 * @param start
 * @param end
 * @returns {number}
 */
function rand(start, end) {
    var c = end - start + 1;
    return Math.floor(Math.random() * c + start);
}

/** ****************************时间相关函数*********************************** */
Date.prototype.addDays = function (d) {
    this.setDate(this.getDate() + d);
};


Date.prototype.addWeeks = function (w) {
    this.addDays(w * 7);
};


Date.prototype.addMonths = function (m) {
    var d = this.getDate();
    this.setMonth(this.getMonth() + m);

    if (this.getDate() < d)
        this.setDate(0);
};


Date.prototype.addYears = function (y) {
    var m = this.getMonth();
    this.setFullYear(this.getFullYear() + y);

    if (m < this.getMonth()) {
        this.setDate(0);
    }
};

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "H+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
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

// 通过字符串获得时间
function getDateFromStr(dateString, formatString) {
    if (validateDate(dateString, formatString)) {
        var now = new Date();
        var vals = regexp.exec(dateString);
        var index = validateIndex(formatString);
        var year = index[0] >= 0 ? vals[index[0] + 1] : now.getFullYear();
        var month = index[1] >= 0 ? (vals[index[1] + 1] - 1) : now.getMonth();
        var day = index[2] >= 0 ? vals[index[2] + 1] : 1;
        var hour = index[3] >= 0 ? vals[index[3] + 1] : "";
        var minute = index[4] >= 0 ? vals[index[4] + 1] : "";
        var second = index[5] >= 0 ? vals[index[5] + 1] : "";

        var validate;

        if (hour == "")
            validate = new Date(year, month, day);
        else
            validate = new Date(year, month, day, hour, minute, second);

        return validate;
    }
}

function validateDate(dateString, formatString) {
    var dateString = dateString.replace(/(^\s*)|(\s*$)/g, "");

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
    var ret = reg.test(dateString);
    return ret;
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
        for (j = 0; j < ia.length - 1 - i; j++)
            if (ia[j] > ia[j + 1]) {
                temp = ia[j];
                ia[j] = ia[j + 1];
                ia[j + 1] = temp;
            }

    for (i = 0; i < ia.length; i++)
        for (j = 0; j < ia2.length; j++)
            if (ia[i] == ia2[j]) {
                ia2[j] = i;
            }

    return ia2;
}


// 将Date转换成字符串
function getDateFmt(date, datefmt) {
    if (datefmt == null || datefmt == '') {
        datefmt = 'yyyy-MM-dd HH:mm:ss';
    }
    return date.format(datefmt);
}

/** *****************************Cookie相关函数************************************* */

// 添加cookie
function setCookie(name, value, hours, path) {
    var name = escape(name);
    var value = escape(value);
    var expires = new Date();
    expires.setTime(expires.getTime() + hours * 3600000);
    path = path == "" ? "" : ";path=" + path;
    _expires = (typeof hours) == "string" ? "" : ";expires="
    + expires.toUTCString();
    document.cookie = name + "=" + value + _expires + path;
}

// 不设置时间存储cookie
function setTempCookie(name, value, path) {
    var name = escape(name);
    var value = escape(value);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "=" + value + path;
}

// 存入cookie时编码
function CodeCookie(str) {
    var strRtn = "";

    for (var i = str.length - 1; i >= 0; i--) {
        strRtn += str.charCodeAt(i);
        if (i)
            strRtn += "a"; // 用a作分隔符
    }
    return strRtn;
}

// 取出来时解码
function DecodeCookie(str) {
    if (str == "") {
        return;
    } else {
        var strArr;
        var strRtn = "";
        strArr = str.split("a");
        for (var i = strArr.length - 1; i >= 0; i--)
            strRtn += String.fromCharCode(eval(strArr[i]));
        return strRtn;
    }
}

// 获取cookie值
function getCookie(name) {
    var name = escape(name);
    // 读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;
    // 查找名为name的cookie的开始位置
    name += "=";
    var pos = allcookies.indexOf(name);
    // 如果找到了具有该名字的cookie，那么提取并使用它的值
    if (pos != -1) { // 如果pos值为-1则说明搜索"version="失败
        var start = pos + name.length; // cookie值开始的位置
        var end = allcookies.indexOf(";", start); // 从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        if (end == -1)
            end = allcookies.length; // 如果end值为-1说明cookie列表里只有一个cookie
        var value = allcookies.substring(start, end); // 提取cookie的值
        return (value); // 对它解码
    } else
        return ""; // 搜索失败，返回空字符串
}
// 删除cookie
function deleteCookie(name, path) {
    var name = escape(name);
    var expires = new Date(0);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}

/** ************************显示正在加载等字样******************************* */
function showloading(comment) {
    if (comment == null || comment == '') {
        comment = '正在加载 ...';
    }
    if ($('#loadingdiv').length <= 0) {
        var loadingdiv = '<div id="loadingdiv" class="text-center" style="position: fixed;width: 100%;height: 100%;top:0px;left:0px;background: #000;opacity:0.7;z-index: 100000">'
            + '<div style="position:absolute;color:white;height:100px;top:50%;width:200px;left:50%;margin:-50px 0 0 -100px;">'
            + '<i class="icon-spinner icon-spin icon-4x" style="color:white;"></i><br>'
            + '<label style="color:white;" id="loadinglabel">'
            + comment
            + '</label>' + '</div>' + '</div>';

        $('body').append(loadingdiv);
    } else {
        $('#loadinglabel').text(comment);
        $('#loadingdiv').removeClass('hide');
    }
}

function hideloading() {
    $('#loadingdiv').addClass('hide');
}

/** ****************************封装过的ajax提交工具,************************************* */

// 带显示正在加载提示，success返回true，则自动关闭正在加载对话框，可用于多接口调用
function ajaxloading(url, data, success, loadingtext) {
    showloading(loadingtext);
    $.ajax({
        url: url,
        type: 'post',
        //timeout : 15000,
        dataType: 'json',
        data: data,
        success: function (result) {
            var ret = success(result, data);
            if (ret == null || ret == true)
                hideloading();
        },
        error: function (XMLHttpRequest, status) {
            hideloading();
            if (status == 'timeout') {
                alert('请求超时');
            }
        }
    });
}

function ajax(url, data, success, failed) {
    $.ajax({
        url: url,
        type: 'post',
        //timeout : 15000,// 请求超时
        dataType: 'json',
        data: data,
        success: function (result) {
            success(result, data);
        },
        error: function (XMLHttpRequest, status) {
            if (status == 'timeout') {
                alert('请求超时');
            }
            if (failed != null)
                failed();
        }
    });
}

// 文件上传
function ajaxfileupload(url, uploadname, uploadfileids, data, success) {
    for (uploadfileid in uploadfileids) {
        $("#" + uploadfileids[uploadfileid]).prop('name', uploadname);
    }

    $.ajaxFileUpload({
        url: url, // 需要链接到服务器地址
        secureuri: false,
        fileElementId: uploadfileids, // 文件选择框的id属性
        data: data,
        dataType: 'json', // 服务器返回的格式，可以是json
        success: function (result) // 相当于java中try语句块的用法
        {
            success(result, data);
        },
        error: function (XMLHttpRequest, status) {
            if (status == 'timeout') {
                alert('请求超时');
            }
        }
    });
}

// 带正在上传等待窗口的文件上传接口
function ajaxfileuploading(url, uploadname, uploadfileids, data, success) {
    for (uploadfileid in uploadfileids) {
        $("#" + uploadfileids[uploadfileid]).prop('name', uploadname);
    }

    showloading("正在上传,请稍候...");
    $.ajaxFileUpload({
        url: url, // 需要链接到服务器地址
        secureuri: false,
        fileElementId: uploadfileids, // 文件选择框的id属性
        data: data,
        dataType: 'json', // 服务器返回的格式，可以是json
        success: function (result) // 相当于java中try语句块的用法
        {
            var ret = success(result, data);
            if (ret == null || ret == true)
                hideloading();
        },
        error: function (XMLHttpRequest, status) {
            hideloading();
            if (status == 'timeout') {
                alert('请求超时');
            }
        }
    });
}

function Data() {
    this.addObj = function (key, obj) {
        if (key != null && key != '' && obj != null)
            this[key] = obj;
        return this;
    };
    this.addList = function (key, list) {
        if (key != null && key != '' && list != null && list.length > 0) {
            var ll = new Array();
            for (var i = 0; i < list.length; i++) {
                var obj = list[i].trim();
                if (obj != null && obj.length > 0 && obj != '')
                    ll.push(obj);
            }
            for (var i = 0; i < ll.length; i++) {
                var obj = ll[i];
                this[key + '[' + i + ']'] = obj;
            }
        }
        return this;
    };
    this.addListStr = function (key, liststr) {
        if (key != null && key != '' && liststr != null)
            this.addDataList(key, liststr.split(','));
        return this;
    };
    this.getData = function () {
        var obj = {};
        for (var key in this) {
            var val = this[key];
            if (val instanceof Function)
                continue;
            else
                obj[key] = val;
        }
        return obj;
    }
}
