/**
 * 提供最基本的基于对象的树生成工具,将data中的所有数据都展示成树 注： node的id必须在整个tree中唯一
 * rootdatafn: //function(pnode,fncallback),方法如何获取根节点
 *                    其中fncallback必须由调用方调用，并将最终数据nodes既children传入fncallback中
 */
$.fn.etree = function (rootnode, callbkbeforechildrengenfn, callbkafterchildrengenfn, callbkcompfn) {
    var containerdomid = $(this).attr('id');
    rootnode.createnodefn = rootnodecreatefn;
    rootnode.inited = true;
    rootnode.expanded = true;

    $("#" + containerdomid).attr('treenodedrawing', 0);

    gentree(containerdomid, "-9999999", rootnode, callbkbeforechildrengenfn, callbkafterchildrengenfn, callbkcompfn);
}

function rootnodecreatefn(node, container) {
    container.css('display', 'none');
}

/**
 * 添加节点node到parentid节点下
 * node:{
 * 		id: //节点唯一ID，若不提供ID，则由插件自动生成 
 * 		name: //节点唯一名,如果没有唯一名，则以id为节点名 
 * 	    createnodefn：//function(node,container),方法如何创建当前节点
 * 					//请将每一行内容通过container.append添加进去
 * 					//必须为非匿名方法，并将方法定义为外部公共方法
 * 		childrendatafn: //function(pnode,fncallback),方法如何获取子节点
 * 					其中fncallback必须由调用方调用，并将最终数据nodes既children传入fncallback中
 * 		inited: true/false,1/0，该节点加载后是否使用childrendatafn初始化子节点
 * 		expanded: true/false,1/0, 该节点加载后是否进行子节点展开，只有在require_inited为1时生效
 *		clicked: //加载完成后调用一次click事件
 * 		clickfn: //点击事件
 * 		enterfn: //鼠标移到某一项时响应事件
 * 		leavefn: //鼠标移开时响应事件
 *
 * 	    属性1：值，//节点自定义属性
 * 		属性2：值，//节点自定义属性
 * 		属性3：值，//节点自定义属性
 * 	}
 *
 *  callbkbeforechildrengenfn用于子节点绘制前回调,用于重新加载时使用
 *  callbkafterchildrengenfn用于当所有节点加载完成后回调
 *  callbkcomp绘制完成后回调函数
 */
function gentree(containerdomid, parentid, node, callbkbeforechildrengenfn, callbkafterchildrengenfn, callbkcomp) {
    if (node.id == null || node.id == '')
        node.id = rand(0, 999999999);
    if (node.name == null || node.name == '')
        node.name = node.id;
    if (node.inited != null && (node.inited == true || node.inited == '1'))
        node.inited = '1';
    else
        node.inited = '0';
    if (node.inited == '1' && node.expanded != null && (node.expanded == true || node.expanded == '1'))
        node.expanded = '1';
    else
        node.expanded = '0';
    if (node.selected != null && (node.selected == true || node.selected == '1'))
        node.selected = '1';
    else
        node.selected = '0';

    var parentnode = $("#" + containerdomid).etreegetnode(parentid)
    var li = null;
    if (parentnode == null) { //如果当前节点不存在，则当前节点为父节点
        li = gentreeli(containerdomid, node, 0);
        $("#" + containerdomid).append(li);
    } else {
        var layer = parentnode.layer;
        li = gentreeli(containerdomid, node, parseInt(layer) + 1);

        //父节点的ul中添加li
        li.css("display", "none");
        $("#" + containerdomid + "ul" + parentid).append(li);
        li.fadeIn();
    }

    if (node.clicked != null && (node.clicked == true || node.clicked == '1')) {
        $('#' + containerdomid + 'divcnt' + node.id).click();
    }

    //先+1,表示正在绘制自己
    addcurrdrawing(containerdomid, 1);
    //处理子节点
    if (node.inited != null && (node.inited == '1' || node.inited == true)) {
        //先+1，表示我要绘制子节点
        addcurrdrawing(containerdomid, 1);
        if (node.childrendatafn != null) {
            // 调用serverdatafn获取数据
            node.childrendatafn(node, function (nodes) {
                if (node != null) {
                    for (var i in nodes) {
                        var subnode = nodes[i];
                        if (callbkbeforechildrengenfn != null) {
                            callbkbeforechildrengenfn(subnode);
                        }
                        gentree(containerdomid, node.id, subnode, callbkbeforechildrengenfn, callbkafterchildrengenfn, callbkcomp);
                    }
                }
                //我已经绘制完所有子节点
                addcurrdrawing(containerdomid, -1, callbkcomp, node);
            });
        } else {
            //虽然我要绘制子节点，但是没给我子节点获取的函数
            addcurrdrawing(containerdomid, -1, callbkcomp, node);
        }
    }
    //如果不要求画子节点，表示该节点绘制完成
    addcurrdrawing(containerdomid, -1, callbkcomp, node);

    if (callbkafterchildrengenfn != null) {
        callbkafterchildrengenfn(node);
    }
}

function addcurrdrawing(containerdomid, addval, compfunc, node) {
    var cntdrawing = $("#" + containerdomid).attr('treenodedrawing');
    if (cntdrawing == null)
        cntdrawing = 0;
    cntdrawing = parseInt(cntdrawing) + addval;
    $("#" + containerdomid).attr('treenodedrawing', cntdrawing);
    if (cntdrawing == 0 && compfunc != null) {
        compfunc(node);
    }
    return cntdrawing;
}

function getcurrdrawing(containerdomid) {
    var cntdrawing = $("#" + containerdomid).attr('treenodedrawing');
    if (cntdrawing == null)
        cntdrawing = 0;
    cntdrawing = parseInt(cntdrawing);
    return cntdrawing;
}

function resetcurrdrawing(containerdomid) {
    $("#" + containerdomid).attr('treenodedrawing', 0);
}

function createdefaultnodefn(node, container) {
    container.attr('response-select', true);
    container.attr('response-click', true);
    container.attr('response-expand', true);
    container.css('white-space', 'nowrap');
    container.text(node.name);
}

/**
 *
 * @param containerdomid 容器ID
 * @param node 节点属性
 * @param layer 当前层次
 */
function gentreeli(containerdomid, node, layer) {
    // 生成li标签,并设置当前节点为未初始化未展开
    var li = $('<li id="' + containerdomid + 'li' + node.id
        + '" tree_selected="' + node.selected + '" tree_expanded="' + node.expanded + '" tree_inited="' + node.inited + '" tree_layer="' + layer + '" style="display:block;"></li>');

    // 添加自定义属性
    li.setobj('tree', node);

    // 添加li内容cntdiv
    var cntdiv = $('<div id="' + containerdomid + 'divcnt' + node.id
        + '" style="background:white;height:40px;line-height:40px;padding-left:' + (parseInt(layer) + 1) * 20
        + 'px"></div>');

    //添加子节点窗口divul
    var divul = $('<div id="' + containerdomid + 'divul' + node.id + '"><ul id="'
        + containerdomid
        + 'ul'
        + node.id
        + '" style="display:block;list-style-type:none;padding:0 0 0 0;margin:0 0 0 0;"></ul></div>');

    li.append(cntdiv);
    li.append(divul);
    if (node.createnodefn != null) {
        node.createnodefn(node, cntdiv);
    } else {
        createdefaultnodefn(node, cntdiv);
    }

    if (node.expanded != null && (node.expanded == '1' || node.expanded == true)) {
        divul.css('display', 'block');
    } else {
        divul.css('display', 'none');
    }
    if (node.selected == '1' || node.selected == true) {
        var selectbg = node.selectbg;
        if (selectbg == null || selectbg == '') {
            selectbg = '#e4f0ff';
        }
        cntdiv.css('background', selectbg);
        li.attr('tree_selected', '1');
    }

    // 绑定事件
    cntdiv.click(function (event) {
        var currnode = $("#" + containerdomid).etreegetnode(node.id);
        var selnode = $("#" + containerdomid).etreegetselectednode();

        //如果代码要求自动展开
        if ($(event.target).attr('response-expand') == 'true') {
            //如果代码要求响应选择
            if ($(event.target).attr('response-selected') == 'true'
                || $(event.target).attr('response-select') == 'true') {
                if (selnode != null)
                    $("#" + containerdomid).etreesetselected(selnode.id, false, 'white');
                var selectbg = currnode.selectbg;
                if (selectbg == null || selectbg == '') {
                    selectbg = '#e4f0ff';
                }
                $("#" + containerdomid).etreesetselected(currnode.id, true, selectbg);
            }
            //响应自动展开
            $("#" + containerdomid).etreeexpandauto(currnode.id, function () {
                //如果代码要求响应点击事件
                if ($(event.target).attr('response-click') == 'true') {
                    try {
                        if (node.clickfn != null) {
                            var currnode = $("#" + containerdomid).etreegetnode(node.id);
                            node.clickfn(currnode, event);
                        }
                    } catch (e) {
                    }
                }
            });
        } else {
            //如果代码要求响应选择
            if ($(event.target).attr('response-selected') == 'true'
                || $(event.target).attr('response-select') == 'true') {
                if (selnode != null)
                    $("#" + containerdomid).etreesetselected(selnode.id, false, 'white');
                var selectbg = currnode.selectbg;
                if (selectbg == null || selectbg == '') {
                    selectbg = '#e4f0ff';
                }
                $("#" + containerdomid).etreesetselected(currnode.id, true, selectbg);
            }
            //如果代码要求响应点击事件
            if ($(event.target).attr('response-click') == 'true') {
                try {
                    if (node.clickfn != null)
                        node.clickfn(currnode, event);
                } catch (e) {
                }
            }
        }
    });

    cntdiv.mouseenter(function (event) {
        var currnode = $("#" + containerdomid).etreegetnode(node.id);
        var hoverbg = currnode.hoverbg;
        if (hoverbg == null || hoverbg == '') {
            hoverbg = '#ececec';
        }
        if (currnode.selected == null || currnode.selected == '0' || currnode.selected == false)
            $("#" + containerdomid).etreebgcolor(currnode.id, hoverbg);

        try {
            if (node.enterfn != null)
                node.enterfn(currnode, event);
        } catch (e) {
        }
    });
    cntdiv.mouseleave(function (event) {
        var currnode = $("#" + containerdomid).etreegetnode(node.id);
        if (currnode.selected == null || currnode.selected == '0' || currnode.selected == false)
            $("#" + containerdomid).etreebgcolor(currnode.id, 'white');

        try {
            if (node.leavefn != null)
                node.leavefn(currnode, event);
        } catch (e) {
        }
    });
    return li;
}

/**
 * 自动定位到节点处
 * @param node, 待定位的节点,前提是该节点为已加载节点，否则无法定位
 * @param compcallbk , 完成定位后回调
 * @param speed, 定位时的动画速度
 */
$.fn.etreelocationTonode = function (id, compcallbk, speed) {
    var containerdomid = $(this).attr('id');
    var scrollTo = $('#' + containerdomid + 'li' + id);
    if (speed == null || speed == '')
        speed = 800;
    var scrollval = scrollTo.offset().top - $("#" + containerdomid).offset().top + $("#" + containerdomid).scrollTop();
    $("#" + containerdomid).animate({
        scrollTop: scrollval
    }, speed);
    if (compcallbk != null)
        compcallbk();
}

/**
 * 自动定位到节点处，如果该节点还未加载，则自动加载该节点
 * @param nodepath，待定位的节点路径，工具会尝试自动寻找路径并自动
 *      加载该节点，如果找到则定位到节点处。第一个节点必须是已加载的节点
 *      格式如下[1,2,3,4,5]，每个元素存入节点的id
 * @param compcallbk , 完成定位后回调
 * @param speed, 定位时的动画速度
 */
$.fn.etreelocationTopath = function (nodepath, compcallbk, speed) {
    var containerdomid = $(this).attr('id');
    if (nodepath == null)
        return;
    var nodeid = nodepath[nodepath.length - 1];
    //初始化该节点的路径
    $(this).etreeexpandnodebypath(nodepath, function () {
        $("#" + containerdomid).etreelocationTonode(nodeid, compcallbk, speed);
    });
}

/**
 * 自动定位到节点处，如果该节点还未加载，则自动加载该节点
 * @param nodepathstr，以逗号分隔，待定位的节点路径，工具会尝试自动寻找路径并自动
 *      加载该节点，如果找到则定位到节点处。第一个节点必须是已加载的节点
 *      格式如下1,2,3,4,5，每个元素存入节点的id
 * @param compcallbk , 完成定位后回调
 * @param speed, 定位时的动画速度
 */
$.fn.etreelocationTopathstr = function (nodepathstr, compcallbk, speed) {
    var nodepath = nodepathstr.split(',');
    $(this).etreelocationTopath(nodepath, compcallbk, speed);
}

/**
 * 获取某个节点的属性
 *
 * @param id
 */
$.fn.etreegetnode = function (id) {
    var containerdomid = $(this).attr('id');
    //如果有id对应的页面元素
    if ($("#" + containerdomid).has("#" + containerdomid + "li" + id).length > 0) {
        var li = $("#" + containerdomid + "li" + id);
        if (li != null) {
            var data = li.getobj('tree');
            return data;
        }
    }
    return null;
}

/**
 * 如果路径中节点还未加载子节点，则尝试自动加载但不展开
 * @param nodepath, 路径的id的列表
 * @compfncallback 所有节点初始化完成后的回调函数
 */
$.fn.etreeinitnodebypath = function (nodepath, compfncallback) {
    var containerdomid = $(this).attr('id');
    var node = $(this).etreegetnode(nodepath[0]);
    if (node != null) {
        $("#" + containerdomid).etreeinitednode(node.id, function () {
            if (nodepath.length == 1) {
                if (compfncallback != null)
                    compfncallback();
            }
            nodepath.splice(0, 1);
            if (nodepath.length > 0)
                $("#" + containerdomid).etreeinitnodebypath(nodepath, compfncallback);
        });
    }
}


/**
 * 如果路径中节点还未加载子节点，则尝试自动加载并展开
 * @param nodepath, 路径的id的列表
 * @compfncallback 所有节点展开后的回调函数
 */
$.fn.etreeexpandnodebypath = function (nodepath, compfncallback) {
    var containerdomid = $(this).attr('id');
    var node = $(this).etreegetnode(nodepath[0]);
    if (node != null) {
        $("#" + containerdomid).etreeexpandnode(node.id, function () {
            if (nodepath.length == 1) {
                if (compfncallback != null)
                    compfncallback();
            }
            nodepath.splice(0, 1);
            if (nodepath.length > 0)
                $("#" + containerdomid).etreeexpandnodebypath(nodepath, compfncallback);
        });
    }
}

/**
 * 获得当前树中选中的节点
 */
$.fn.etreegetselectednode = function () {
    var node = null;
    var containerdomid = $(this).attr('id');
    $(this).find('li[tree_selected=\'1\']').each(function () {
        node = $("#" + containerdomid).etreegetnode($(this).attr('tree_id'));
    });
    return node;
}

/**
 * 重新加载某个节点下的树内容，如果该节点保存了如何生成子节点的方法,
 */
$.fn.etreereload = function (id, compfn) {
    var containerdomid = $(this).attr('id');
    //保存该树及子树的展开状态，重新加载时根据该状态进行刷新
    var oridata = $(this).etreegetsubnodesall(id);
    var currdata = $(this).etreegetnode(id);
    if (currdata != null)
        oridata.push(currdata);

    var orinodemaps = {};

    if (oridata != null) {
        for (var i = 0; i < oridata.length; i++) {
            var node = oridata[i];
            orinodemaps[node.id] = node;
        }
    }

    $(this).etreereloadsubnodes(id, orinodemaps, compfn);

}

/**
 * 根据保存状态重新加载当前节点的所有子节点
 */
$.fn.etreereloadsubnodes = function (id, orinodemaps, compfn) {
    var containerdomid = $(this).attr('id');
    var node = $(this).etreegetnode(id);//当前节点
    var orinode = orinodemaps["" + node.id];

    if (node == null) //如果当前节点不存在，直接返回
        return;

    //先清空某一节点的子节点
    $("#" + containerdomid + "ul" + id).empty();
    if (orinode != null && orinode.inited == '1') { //如果原节点为已初始化，则需要加载数据
        if (node.childrendatafn != null) {
            // 调用childrendatafn获取子节点数据
            var childrendatafn = eval(node.childrendatafn);
            childrendatafn(node, function (nodes) {
                for (var i in nodes) {
                    var subnd = nodes[i]; //获取当前节点的子节点

                    var orisubnd = orinodemaps["" + subnd.id];
                    if (orisubnd != null) { //设置子节点状态
                        subnd.inited = orisubnd.inited;
                        subnd.expanded = orisubnd.expanded;
                        subnd.selected = orisubnd.selected;
                    }
                    gentree(containerdomid, node.id, subnd, function reloadcallbk(subsubnd) {
                        var orisubsubnd = orinodemaps["" + subsubnd.id];
                        if (orisubsubnd != null) { //设置子节点状态
                            subsubnd.inited = orisubsubnd.inited;
                            subsubnd.expanded = orisubsubnd.expanded;
                            subsubnd.selected = orisubsubnd.selected;
                        }
                    }, null, compfn);
                }
            });
            return;
        }
    }
}

/**
 * 删除树中某一节点的所有下属节点
 */
$.fn.etreeremovesubnode = function (id) {
    var containerdomid = $(this).attr('id');
    $("#" + containerdomid + "ul" + id).empty();
    $(this).etreeupdateattr(id, 'inited', '0');
    $(this).etreeupdateattr(id, 'expanded', '0');
}

/**
 * 删除树中某一节点及下属节点
 */
$.fn.etreeremovenode = function (id) {
    var containerdomid = $(this).attr('id');
    $("#" + containerdomid + "li" + id).remove();
}

/**
 * 触发某节点的点击事件
 */
$.fn.etreeclicknode = function (id) {
    var containerdomid = $(this).attr('id');
    $("#" + containerdomid + "divcnt" + id).click();
}

/**
 * 递归搜索所有的子节点
 */
$.fn.etreegetsubnodesall = function (id) {
    var nodes = $(this).etreegetsubnodes(id);
    var subnodes = new Array();

    if (nodes != null && nodes.length > 0) {
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];
            var snodes = $(this).etreegetsubnodesall(node.id);
            if (snodes != null && snodes.length > 0) {
                for (var j in snodes) {
                    subnodes.push(snodes[j]);
                }
            }
        }
    }
    for (var i in subnodes) {
        nodes.push(subnodes[i]);
    }
    return nodes;
}

/**
 * 获得节点的所有子节点，返回数组
 */
$.fn.etreegetsubnodes = function (id) {
    var nodes = [];
    var containerdomid = $(this).attr('id');
    var ulid = containerdomid + "ul" + id;
    $("#" + ulid).children('li').each(function () {
        var treeid = $(this).attr('tree_id');
        var node = $("#" + containerdomid).etreegetnode(treeid);
        nodes[nodes.length] = node;
    });
    return nodes;
}

/**
 * 设置某一项是否选中
 *
 * @param id
 */
$.fn.etreesetselected = function (id, selected, bgcolor) {
    if (bgcolor == null || bgcolor == '')
        bgcolor = '#e4f0ff';
    var containerdomid = $(this).attr('id');
    $("#" + containerdomid + "divcnt" + id).css('background', bgcolor);
    $("#" + containerdomid + "li" + id).attr('tree_selected',
        (selected != null && (selected == "1" || selected == 1 || selected == true)) ? '1' : '0');
}

/**
 * 设置某一项的背景颜色
 */
$.fn.etreebgcolor = function (id, bgcolor) {
    if (bgcolor == null || bgcolor == '')
        bgcolor = '#ececec';
    var containerdomid = $(this).attr('id');
    $("#" + containerdomid + "divcnt" + id).css('background', bgcolor);
}

/**
 * 将某个元素设置为高亮选择
 *
 * @param id
 */
$.fn.etreebgcolorall = function (bgcolor) {
    var containerdomid = $(this).attr('id');
    $("#" + containerdomid).find('[id*=' + containerdomid + 'divcnt]').each(function () {
        var id = $(this).attr("tree_id");
        $("#" + containerdomid).etreegetnode(id, bgcolor);
    });

}

/**
 * 更新某一项的属性
 */
$.fn.etreeupdateattr = function (id, key, value) {
    var containerdomid = $(this).attr('id');
    $("#" + containerdomid + "li" + id).attr("tree_" + key, value);
}


/**
 * 如果元素未初始化，手动初始化某个元素
 * @param id
 */
$.fn.etreeinitednode = function (id, funccallbk) {
    var containerdomid = $(this).attr('id');
    var node = $(this).etreegetnode(id);
    if (node == null)
        return;
    var expanded = node.expanded;
    var inited = node.inited;
    if (inited == null || inited == "0") {
        if (node.childrendatafn != null) {
            // 调用serverdatafn获取数据
            var childrendatafn = eval(node.childrendatafn);
            childrendatafn(node, function (nodes) {
                for (var i in nodes) {
                    var subnode = nodes[i];
                    gentree(containerdomid, node.id, subnode, null, null);
                }
                $("#" + containerdomid + "li" + node.id).attr('tree_inited', '1');
                if (funccallbk != null)
                    funccallbk();
            });
        } else {
            if (funccallbk != null)
                funccallbk();
        }
    }
}

/**
 * 手动展开某个元素,如果元素未初始化，则尝试通过childrendatafn请求获取数据
 * @param id
 */
$.fn.etreeexpandnode = function (id, funccallbk) {
    var containerdomid = $(this).attr('id');
    var node = $(this).etreegetnode(id);

    var expanded = node.expanded;
    var inited = node.inited;
    if (inited == null || inited == "0") {
        //自动初始化该节点
        $(this).etreeinitednode(id, function () {
            //初始化化完成的回调函数中进行显示
            $("#" + containerdomid + "divul" + node.id).animate({
                height: 'show'
            });
            $("#" + containerdomid + "li" + node.id).attr('tree_expanded', '1');
            if (funccallbk != null)
                funccallbk();
        });
    } else {
        if (expanded == null || expanded == '0') {
            $("#" + containerdomid + "divul" + node.id).animate({
                height: 'show'
            });
            $("#" + containerdomid + "li" + node.id).attr('tree_expanded', '1');
        }
        if (funccallbk != null)
            funccallbk();
    }
}

/**
 * 手动展开某个元素,如果元素未初始化，则尝试通过childrendatafn请求获取数据
 * 如果已展开，则进行折叠
 * @param id
 */
$.fn.etreeexpandauto = function (id, funccallbk) {
    var containerdomid = $(this).attr('id');
    var node = $(this).etreegetnode(id);

    var expanded = node.expanded;
    var inited = node.inited;
    if (inited == null || inited == "0") {
        //自动初始化该节点
        $(this).etreeinitednode(id, function () {
            //初始化化完成的回调函数中进行显示
            $("#" + containerdomid + "divul" + node.id).animate({
                height: 'show'
            });
            $("#" + containerdomid + "li" + node.id).attr('tree_expanded', '1');
            if (funccallbk != null)
                funccallbk();
        });
    } else {
        if (expanded == null || expanded == '0') {
            $("#" + containerdomid + "divul" + node.id).animate({
                height: 'show'
            });
            $("#" + containerdomid + "li" + node.id).attr('tree_expanded', '1');
        } else {
            $("#" + containerdomid + "divul" + node.id).animate({
                height: 'hide'
            });
            $("#" + containerdomid + "li" + node.id).attr('tree_expanded', '0');
        }
        if (funccallbk != null)
            funccallbk();
    }
}

/**
 * 获得随机数，范围n~m
 */
function rand(n, m) {
    var c = m - n + 1;
    return Math.floor(Math.random() * c + n);
}


/**
 * 将键值对对象设置到某一元素上
 */
$.fn.setobj = function (name, obj) {
    // 添加自定义属性
    if (obj != null && name != null && name != '') {
        for (var key in obj) {
            var val = obj[key];
            if (val instanceof Function)
                $(this).attr(name + "_" + key, val.getName());
            else
                $(this).attr(name + "_" + key, val);
        }
    }
}

/**
 * 从元素上获取某一对象，键值对对象
 */
$.fn.getobj = function (name) {
    var attrs = $(this).get(0).attributes;
    var obj = {};
    try {
        for (var index = 0; index < attrs.length; index++) {
            var key = attrs[index].name;
            if (key.indexOf(name + "_") == 0) {
                var key = key.replace(name + "_", '');
                var val = attrs[index].value;
                obj[key] = val;
            }
        }
    } catch (e) {
    }
    return obj;
}

/**
 * 获得js函数名称
 */
Function.prototype.getName = function () {
    var name = this.toString();
    return this.name || this.toString().match(/function\s*([^(]*)\(/)[1]
}