/**
 *  node:{
 * 		id: //节点唯一ID，若不提供ID，则由插件自动生成
 * 		name: //节点唯一名,如果没有唯一名，则以id为节点名
 *
 * 	    属性1：值，//节点自定义属性
 * 		属性2：值，//节点自定义属性
 * 		属性3：值，//节点自定义属性
 *
 * 	    createnodefn：//function(tree,node,$container),方法如何创建当前节点，无返回值
 * 					  //请将每一行内容通过$container.append添加进去
 * 					  //为每个dom元素包括$container 添加response_hover,response_select,response_expand来响应相应的回调函数
 * 		childrendatafn: //function(tree,pnode),方法如何获取子节点，
 * 	            	    //如果方法通过异步加载(ajax)取数据，则返回对象{url:'',data:{},success:function(result)};
 * 	            	    //  其中url为请求url，{}为请求参数，function(result)为请求如何解析，该方法返回结构类似node的array数组
 *                      //如果方法同步加载数据，直接返回包含节点的元素array数组
 *      clickfn: //function(tree,node,$containerdom,$targetdom) 点击事件，只有定义了response_click的元素才会响应点击事件，该事件与selectfn/unselectedfn不冲突
 *
 * 		enterfn: //function(tree,node,$containerdom,$targetdom)鼠标移到某一项时响应事件，只有定义了response_hover的元素才会响应鼠标移到或移开某一项时的事件
 * 		leavefn: //function(tree,node,$containerdom,$targetdom)鼠标移开时响应事件
 *
 * 	    selectedfn: //function(tree,node,$containerdom,$targetdom)选中时事件,只有定义了response_select的元素才会响应选中或未选中事件
 *      unselectedfn: //function(tree,node,$containerdom)变为未选中时事件,只有定义了response_select的元素才会响应选中或未选中事件
 *
 *      expandedfn: //function(tree,node,$containerdom,$targetdom)展开时事件,只有定义了response_expand的元素才会响应选中或未选中事件
 *      unexpandedfn: //function(tree,node,$containerdom,$targetdom)折叠时事件,只有定义了response_expand的元素才会响应选中或未选中事件
 *
 *      init_clicked: //初始化时是否点击，同时会触发clickfn事件
 *      init_expanded: //初始化时是否默认展开，同时会触发expandedfn事件
 *      init_selected: //初始化时是否默认选中，同时会触发selectedfn事件
 *
 *      //树控件自定义属性
 *      selected：是否选中
 *      inited：是否已初始化
 *      expanded：是否已展开
 *      layer：当前层次
 *      treepath：当前节点所在路径，从-1开始
 * 	}
 */

/**
 * 初始化化树，并返回树对象
 *
 * @param containerdomid， 树容器ID
 * @param rootnodefn ，加载根节点方法，rootnodefn参考node.childrendatafn
 * @param beforedrawfn，绘制每个节点前回调函数
 * @param afterdrawfn，绘制每个节点后回调函数
 * @param allcompfn，树加载完成后回调函数
 * @returns {*}
 */
function inittree(containerdomid, rootnodefn, beforedrawfn, afterdrawfn, allcompfn) {
    _gentree(containerdomid, rootnodefn, beforedrawfn, afterdrawfn, allcompfn);
    return gettree(containerdomid);
}

/**
 *
 * 获取树对象
 *
 * @param containerdomid
 * @returns 树对象
 */
function gettree(containerdomid) {
    this.containerdomid = containerdomid;
    //如果对象不存在，返回空
    if ($("#" + this.containerdomid).length <= 0)
        return null;

    this.getdefaultnode = function (id, name) {
        return _getdefaultnode(id, name);
    };
    this.getdefaultnode1 = function (id, name, attrobj) {
        return _getdefaultnode1(id, name, attrobj);
    };
    this.getnode = function (nodeid) {
        return _getnode(containerdomid, {id: nodeid});
    };
    this.getnode_r = function (nodeid) {
        return _getnode_r(containerdomid, {id: nodeid});
    };
    this.getsubnodes = function (nodeid) {
        return _getsubnodes(containerdomid, {id: nodeid});
    };
    this.getsubnodes_r = function (nodeid) {
        return _getsubnodes_r(containerdomid, {id: nodeid});
    };
    this.getselectednode = function () {
        return _getselectednode(containerdomid);
    };
    this.ifnodeexpanded = function (nodeid) {
        return _ifnodeexpanded(this.getnode(nodeid));
    };
    this.ifnodeinited = function (nodeid) {
        return _ifnodeinited(this.getnode(nodeid));
    };
    this.ifnodeselected = function (nodeid) {
        return _ifnodeselected(this.getnode(nodeid));
    };
    this.locationtonode = function (nodeid, allcompfn) {
        return _locationtonode(containerdomid, this.getnode(nodeid), allcompfn);
    };
    this.locationtopath = function (treepath, allcompfn) {
        return _locationtopath(containerdomid, treepath, allcompfn);
    };
    this.expandnodebypath = function (treepath, allcompfn) {
        return _expandnodebypath(containerdomid, treepath, allcompfn);
    };
    this.delnode = function (nodeid) {
        return _delnode(containerdomid, this.getnode(nodeid));
    };
    this.delsubnodes = function (nodeid) {
        return _delsubnodes(containerdomid, this.getnode(nodeid));
    };
    this.reloadnode = function (nodeid, compfn) {
        return _reloadnode(containerdomid, this.getnode(nodeid), compfn);
    };
    this.reloadsubnodes = function (nodeid, compfn) {
        return _reloadsubnodes(containerdomid, this.getnode(nodeid), compfn);
    };

    this.trigger_click = function (nodeid, compfn) {
        return _trigger_click(containerdomid, this.getnode(nodeid));
    };
    this.trigger_select = function (nodeid, compfn) {
        return _trigger_select(containerdomid, this.getnode(nodeid));
    };
    this.trigger_unselect = function (nodeid, compfn) {
        return _trigger_unselect(containerdomid, this.getnode(nodeid));
    };
    this.trigger_expandauto = function (nodeid, beforedrawfn, afterdrawfn, compfn, allcompfn) {
        return _trigger_expandauto(containerdomid, this.getnode(nodeid), beforedrawfn, afterdrawfn, compfn, allcompfn);
    };
    this.trigger_expand = function (nodeid, beforedrawfn, afterdrawfn, compfn, allcompfn) {
        return _trigger_expand(containerdomid, this.getnode(nodeid), beforedrawfn, afterdrawfn, compfn, allcompfn);
    };
    this.trigger_unexpand = function (nodeid, beforedrawfn, afterdrawfn, compfn, allcompfn) {
        return _trigger_unexpand(containerdomid, this.getnode(nodeid), beforedrawfn, afterdrawfn, compfn, allcompfn);
    };

    return this;
}

/**
 * 获得默认的node
 */
function _getdefaultnode(id, name) {
    var node = {};
    node.id = id;
    node.name = name;
    node.createnodefn = _defaultcreatenodefn;
    node.childrendatafn = _defaultchildrendatafn;
    node.clickfn = _defaultclickfn;
    node.enterfn = _defaultenterfn;
    node.leavefn = _defaultleavefn;
    node.selectedfn = _defaultselectedfn;
    node.unselectedfn = _defaultunselectedfn;
    node.expandedfn = _defaultexpandedfn;
    node.unexpandedfn = _defaultunexpandedfn;
    node.init_clicked = false;
    node.init_expanded = false;
    node.init_selected = false;
    return node;
}

/**
 * 获得默认的node，并attrobj对象的属性合并为生成的node的属性
 */
function _getdefaultnode1(id, name, attrobj) {
    var node = _getdefaultnode(id, name);

    if (attrobj != null) {
        for (var key in attrobj) {
            var val = attrobj[key];
            if (val instanceof Function)
                continue;
            else
                node[key] = val;
        }
    }
    return node;
}


/**
 * 绘制一颗树
 *
 * @param rootnodefn 加载根节点方法，rootnodefn参考node.childrendatafn
 * @param beforedrawfn 绘制每个节点前回调函数
 * @param afterdrawfn 绘制每个节点后回调函数
 * @param allcompfn 树加载完成后回调函数
 */
function _gentree(containerdomid, rootnodefn, beforedrawfn, afterdrawfn, allcompfn) {
    var rootnode = {};

    rootnode.id = '-1';
    rootnode.childrendatafn = rootnodefn;
    rootnode.createnodefn = function (tree, node, $container) {
        $container.css('display', 'none');
    };
    rootnode.init_expanded = true;

    resetcurrdrawing(containerdomid);

    _gennode(containerdomid, null, rootnode, beforedrawfn, afterdrawfn, allcompfn);
}

/**
 * 添加节点node到parentid节点下
 *
 * @param containerdomid 容器id
 * @param parentid 父节点id
 * @param node 待添加节点
 * @param beforedrawfn 节点绘制前回调
 * @param afterdrawfn 节点绘制后回调
 * @param allcompfn 整个节点树绘制完成后回调
 */
function _gennode(containerdomid, parentid, node, beforedrawfn, afterdrawfn, allcompfn) {
    //绘制节点前回调
    if (beforedrawfn != null) {
        beforedrawfn(node);
    }
    //先+1,表示正在绘制自己
    addcurrdrawing(containerdomid, 1);

    var parentnode = _getnode(containerdomid, {id: parentid});
    if (node.id == null || node.id == '')
        node.id = rand(1, 999999999);
    if (node.name == null || node.name == '')
        node.name = node.id;
    if (node.createnodefn == null)
        node.createnodefn = _defaultcreatenodefn;
    if (node.childrendatafn == null)
        node.childrendatafn = _defaultchildrendatafn;
    if (node.clickfn == null)
        node.clickfn = _defaultclickfn;
    if (node.enterfn == null)
        node.enterfn = _defaultenterfn;
    if (node.leavefn == null)
        node.leavefn = _defaultleavefn;
    if (node.selectedfn == null)
        node.selectedfn = _defaultselectedfn;
    if (node.unselectedfn == null)
        node.unselectedfn = _defaultunselectedfn;
    if (node.expandedfn == null)
        node.expandedfn = _defaultexpandedfn;
    if (node.unexpandedfn == null)
        node.unexpandedfn = _defaultunexpandedfn;
    if (parentnode == null) {
        node.layer = 0;
        node.treepath = node.id;
    }
    else {
        node.layer = parseInt(parentnode.layer) + 1;
        node.treepath = parentnode.treepath + ',' + node.id;
    }
    node.pid = parentid;
    node.selected = 0;//未选中
    node.inited = 0;//未初始化
    node.expanded = 0;//未展开

    var $li = null;
    if (parentnode == null) { //如果当前节点不存在，则当前节点为父节点
        $li = _gennodeli(containerdomid, node, beforedrawfn, afterdrawfn);
        $("#" + containerdomid).append($li);
    } else {
        $li = _gennodeli(containerdomid, node, beforedrawfn, afterdrawfn);

        //父节点的ul中添加li
        $("#" + containerdomid + "ul" + parentid).append($li);
    }

    //绘制完节点后回调函数
    if (afterdrawfn != null) {
        afterdrawfn(node);
    }
    //处理子节点
    if (node.init_expanded != null && (node.init_expanded == '1' || node.init_expanded == true)) {
        //先+1，表示我要绘制子节点
        addcurrdrawing(containerdomid, 1);
        _trigger_expandauto(containerdomid, node, beforedrawfn, afterdrawfn, function () {
            //我已经绘制完所有子节点
            addcurrdrawing(containerdomid, -1, allcompfn, node);
        }, allcompfn);
    }

    if (node.init_selected != null && (node.init_selected == '1' || node.init_selected == true)) {
        _trigger_select(containerdomid, node);
    }
    if (node.init_clicked != null && (node.init_clicked == '1' || node.init_clicked == true)) {
        _trigger_click(containerdomid, node);
    }

    //当前节点已绘制完成
    addcurrdrawing(containerdomid, -1, allcompfn, node);
}

/**
 * 生成树节点的li标签
 *
 * @param containerdomid 容器ID
 * @param node 节点属性
 */
function _gennodeli(containerdomid, node, beforedrawfn, afterdrawfn) {
    // 生成li标签
    var $li = $('<li></li>');
    $li.attr('id', containerdomid + 'li' + node.id);
    $li.css('display', 'block');

    // 添加自定义属性
    _setobj($li, node);
    // 为li标签绑定方法
    $li.on('createnodefn', function (event) {
        node.createnodefn(gettree(containerdomid), _getnode(containerdomid, node), _getnodecontainer(containerdomid, node));
    });
    $li.on('childrendatafn', function (event) {
        return node.childrendatafn(gettree(containerdomid), _getnode(containerdomid, node));
    });
    $li.on('enterfn', function (event) {
        node.enterfn(gettree(containerdomid), _getnode(containerdomid, node), _getnodecontainer(containerdomid, node), $(event.target));
    });
    $li.on('leavefn', function (event) {
        node.leavefn(gettree(containerdomid), _getnode(containerdomid, node), _getnodecontainer(containerdomid, node), $(event.target));
    });
    $li.on('clickfn', function (event) {
        node.clickfn(gettree(containerdomid), _getnode(containerdomid, node), _getnodecontainer(containerdomid, node), $(event.target));
    });
    $li.on('selectedfn', function (event) {
        node.selectedfn(gettree(containerdomid), _getnode(containerdomid, node), _getnodecontainer(containerdomid, node), $(event.target));
    });
    $li.on('unselectedfn', function (event, currnode) {
        node.unselectedfn(gettree(containerdomid), _getnode(containerdomid, currnode), _getnodecontainer(containerdomid, currnode), $(event.target));
    });
    $li.on('expandedfn', function (event) {
        node.expandedfn(gettree(containerdomid), _getnode(containerdomid, node), _getnodecontainer(containerdomid, node), $(event.target));
    });
    $li.on('unexpandedfn', function (event) {
        node.unexpandedfn(gettree(containerdomid), _getnode(containerdomid, node), _getnodecontainer(containerdomid, node), $(event.target));
    });

    // 生成li内容cntdiv
    var $cntdiv = $('<div></div>');
    $cntdiv.attr('id', containerdomid + 'divcnt' + node.id);
    $cntdiv.css('padding-left', (parseInt(node.layer) + 1) * 20 + 'px');

    //生成li下级目录div
    var $divul = $('<div></div>');
    $divul.attr('id', containerdomid + 'divul' + node.id);
    $divul.css('display', 'none');
    var $ul = $('<ul></ul>');
    $divul.append($ul);
    $ul.attr('id', containerdomid + 'ul' + node.id);
    $ul.css('display', 'block');
    $ul.css('list-style-type', 'none');
    $ul.css('padding', '0');
    $ul.css('margin', '0');

    $li.append($cntdiv);
    $li.append($divul);

    //创建节点内容
    node.createnodefn(gettree(containerdomid), node, $cntdiv);

    // 绑定事件
    $cntdiv.click(function (event) {
        if (typeof($(event.target).attr('response-click')) != "undefined") {
            _trigger_click(containerdomid, node);
        }
        if (typeof($(event.target).attr('response-select')) != "undefined") {
            _trigger_select(containerdomid, node);
        }
        if (typeof($(event.target).attr('response-expand')) != "undefined") {
            _trigger_expandauto(containerdomid, node, beforedrawfn, afterdrawfn);
        }
    });

    $cntdiv.mouseenter(function (event) {
        if (typeof($(event.target).attr('response-hover')) != "undefined") {
            $li.triggerHandler('enterfn');
        }
    });

    $cntdiv.mouseleave(function (event) {
        if (typeof($(event.target).attr('response-hover')).toLowerCase() != "undefined") {
            $li.triggerHandler('leavefn');
        }
    });
    return $li;
}

/***********************************************根据node获得该节点对应的$li,$container,$subnodecontainer************************************************************/
function _getnodeli(containerdomid, node) {
    return $("#" + containerdomid + 'li' + node.id);
}

function _getnodecontainer(containerdomid, node) {
    return $("#" + containerdomid + 'divcnt' + node.id);
}

function _getsubnodecontainer(containerdomid, node) {
    return $("#" + containerdomid + 'divul' + node.id);
}

function _getsubnodecontainerul(containerdomid, node) {
    return $("#" + containerdomid + 'ul' + node.id);
}

/**********************************************操作方法**************************************************************/
/**
 * 触发点击事件
 *
 * @param containerdomid
 * @param node
 * @private
 */
function _trigger_click(containerdomid, node) {
    $li = _getnodeli(containerdomid, node);
    if ($li.length > 0) {
        $li.triggerHandler('clickfn');
    }
}

/**
 * 触发选中事件
 *
 * @param containerdomid
 * @param node
 * @private
 */
function _trigger_select(containerdomid, node) {
    $li = _getnodeli(containerdomid, node);
    var currnode = _getnode(containerdomid, node);
    if ($li.length > 0 && !_ifnodeselected(currnode)) { //如果未选中，触发选中事件
        var selectednode = _getselectednode(containerdomid);
        if (selectednode != null) {
            _updateobj(_getnodeli(containerdomid, selectednode), _getnode(containerdomid, selectednode), 'selected', '0');
            $li.triggerHandler('unselectedfn', selectednode);
        }

        _updateobj(_getnodeli(containerdomid, currnode), _getnode(containerdomid, currnode), 'selected', '1');
        $li.triggerHandler('selectedfn');
    }
}

/**
 * 触发未选中事件
 *
 * @param containerdomid
 * @param node
 * @private
 */
function _trigger_unselect(containerdomid, node) {
    $li = _getnodeli(containerdomid, node);
    var currnode = _getnode(containerdomid, node);
    if ($li.length > 0 && _ifnodeselected(currnode)) { //如果当前节点已选中，触发未选中事件
        _updateobj(_getnodeli(containerdomid, currnode), _getnode(containerdomid, currnode), 'selected', '0');
        $li.triggerHandler('unselectedfn', selectednode);
    }
}

/**
 * 触发自动展开折叠事件
 *
 * @param containerdomid
 * @param node
 * @param beforedrawfn 绘制每个节点前回调
 * @param afterdrawfn 绘制每个节点后回调
 * @param expandautocompfn //当前节点展开折叠完成回调
 * @param allcompfn //所有节点展开折叠完成回调，当子节点要求展开时，需要用该回调，区别于compfn
 * @private
 */
function _trigger_expandauto(containerdomid, node, beforedrawfn, afterdrawfn, expandautocompfn, allcompfn) {
    $li = _getnodeli(containerdomid, node);
    var currnode = _getnode(containerdomid, node);
    if ($li.length > 0) {
        _expandnodeauto(containerdomid, currnode, beforedrawfn, afterdrawfn, function (nd) {
            if (nd.expanded == '1' || nd.expanded == true) {
                $li.triggerHandler('expandedfn');
            } else {
                $li.triggerHandler('unexpandedfn');
            }
            if (expandautocompfn != null)
                expandautocompfn();
        }, allcompfn);
    }
}

/**
 * 触发自动折叠事件
 *
 * @param containerdomid
 * @param node
 * @param beforedrawfn 绘制每个节点前回调
 * @param afterdrawfn 绘制每个节点后回调
 * @param expandautocompfn //当前节点展开折叠完成回调
 * @param allcompfn //所有节点展开折叠完成回调，当子节点要求展开时，需要用该回调，区别于compfn
 * @private
 */
function _trigger_expand(containerdomid, node, beforedrawfn, afterdrawfn, compfn, allcompfn) {
    $li = _getnodeli(containerdomid, node);
    var currnode = _getnode(containerdomid, node);
    if ($li.length > 0 && !_ifnodeexpanded(currnode)) {
        _expandnodeauto(containerdomid, currnode, beforedrawfn, afterdrawfn, function (nd) {
            if (nd.expanded == '1' || nd.expanded == true) {
                $li.triggerHandler('expandedfn');
            } else {
                $li.triggerHandler('unexpandedfn');
            }
            if (compfn != null)
                compfn();
        }, allcompfn);
    }
}

/**
 * 触发自动折叠事件
 *
 * @param containerdomid
 * @param node
 * @param beforedrawfn 绘制每个节点前回调
 * @param afterdrawfn 绘制每个节点后回调
 * @param expandautocompfn //当前节点展开折叠完成回调
 * @param allcompfn //所有节点展开折叠完成回调，当子节点要求展开时，需要用该回调，区别于compfn
 * @private
 */
function _trigger_unexpand(containerdomid, node, beforedrawfn, afterdrawfn, compfn, allcompfn) {
    $li = _getnodeli(containerdomid, node);
    var currnode = _getnode(containerdomid, node);
    if ($li.length > 0 && _ifnodeexpanded(currnode)) {
        _expandnodeauto(containerdomid, currnode, beforedrawfn, afterdrawfn, function (nd) {
            if (nd.expanded == '1' || nd.expanded == true) {
                $li.triggerHandler('expandedfn');
            } else {
                $li.triggerHandler('unexpandedfn');
            }
            if (compfn != null)
                compfn();
        }, allcompfn);
    }
}

/**
 * 获得节点
 *
 * @param containerdomid
 * @param node
 * @returns {*}
 * @private
 */
function _getnode(containerdomid, node) {
    //如果有id对应的页面元素
    var $li = _getnodeli(containerdomid, node);
    if ($li.length > 0) {
        var data = _getobj($li);
        return data;
    }
    return null;
}

/**
 * 获得当前节点及所有的子节点
 * @param containerdomid
 * @param node
 * @private
 */
function _getnode_r(containerdomid, node) {
    var nodes = _getsubnodes_r(containerdomid, node);
    nodes.push(node);
    return nodes;
}

/**
 * 获得所有的子节点，不包括子节点的子节点
 *
 * @param containerdomid
 * @param node
 * @private
 */
function _getsubnodes(containerdomid, node) {
    var nodes = [];
    var $subnodecontainerul = _getsubnodecontainerul(containerdomid, node);
    if ($subnodecontainerul.length > 0) {
        $subnodecontainerul.children('li').each(function () {
            var node = _getobj($(this));
            nodes[nodes.length] = node;
        });
    }
    return nodes;
}

/**
 * 递归获得所有的子节点，包括子节点的子节点
 * @param containerdomid
 * @param node
 * @private
 */
function _getsubnodes_r(containerdomid, node) {
    var nodes = new Array();
    var subnodes = _getsubnodes(containerdomid, node);
    if (subnodes != null || subnodes.length > 0) {
        for (var i = 0; i < subnodes.length; i++) {
            nodes.push(subnodes[i]);
            var subnode = subnodes[i];
            var subsubnodes = _getsubnodes_r(containerdomid, subnode);
            for (var j in subsubnodes) {
                nodes.push(subsubnodes[j]);
            }
        }
    }
    return nodes;
}

/**
 * 获得已选择的节点
 *
 * @param containerdomid
 * @returns {*}
 * @private
 */
function _getselectednode(containerdomid) {
    var node = null;
    $("#" + containerdomid).find('li[tree_selected=\'1\']').each(function () {
        var id = $(this).attr('tree_id');
        node = _getnode(containerdomid, {id: id});
    });
    return node;
}

/**
 *  自动展开节点，如果节点未初始化，则初始化并展开，
 *  如果节点已展开，则折叠节点
 *
 * @param containerdomid，容器ID
 * @param node，节点
 * @param compfn，展开折叠完成后回调
 * @param allcompfn, 展开节点，包括所有需要展开的子节点完成后回调函数，适用异步加载
 */
function _expandnodeauto(containerdomid, node, beforedrawfn, afterdrawfn, compfn, allcompfn) {
    if (!_ifnodeinited(node)) { //节点未初始化，则初始化
        _initnode(containerdomid, node, beforedrawfn, afterdrawfn, function (nd) {
            _expandnode(containerdomid, nd, compfn);
        }, allcompfn)
    } else {
        if (_ifnodeexpanded(node)) {
            _unexpandnode(containerdomid, node, compfn);
            if (allcompfn != null)
                allcompfn();
        } else {
            _expandnode(containerdomid, node, compfn);
            if (allcompfn != null)
                allcompfn();
        }
    }
}

/**
 * 初始化节点
 *
 * @param containerdomid 容器ID
 * @param node 节点
 * @param initcompfn 初始化成功后回调函数
 * @param allcompfn, 展开节点，包括所有需要展开的子节点完成后回调函数，适用异步加载
 */
function _initnode(containerdomid, node, beforedrawfn, afterdrawfn, initcompfn, allcompfn) {
    if (!_ifnodeinited(node)) {
        // 调用childrendatafn获取子节点数据
        var ret = _getnodeli(containerdomid, node).triggerHandler('childrendatafn', [_getnode(containerdomid, node)]);
        if (ret != null && ret instanceof Array) { //非异步调用，直接返回子节点数组
            _updateobj(_getnodeli(containerdomid, node), node, 'inited', '1');
            for (var i in ret) {
                var subnode = ret[i];
                _gennode(containerdomid, node.id, subnode, beforedrawfn, afterdrawfn, allcompfn);
            }

            if (initcompfn != null)
                initcompfn(node);
        } else {//返回的为url等信息，通过ajax请求获取子节点数据
            var url = ret.url;
            var data = ret.data;
            var success = ret.success;
            $.ajax({
                url: url,
                type: 'post',
                timeout: 15000,
                dataType: 'json',
                data: data,
                success: function (result) {
                    var subnodes = success(result);
                    if (subnodes != null && subnodes instanceof Array) {
                        _updateobj(_getnodeli(containerdomid, node), node, 'inited', '1');

                        for (var i in subnodes) {
                            var subnode = subnodes[i];
                            _gennode(containerdomid, node.id, subnode, beforedrawfn, afterdrawfn, allcompfn);
                        }
                    }
                    if (initcompfn != null)
                        initcompfn(node);
                },
                error: function () {
                    if (initcompfn != null)
                        initcompfn(node);
                }
            });
        }
    }
}

/**
 * 如果节点已初始化并未展开，则展开节点
 *
 * @param containerdomd 容器ID
 * @param node 节点
 * @param expandedcompfn 展开完成后回调函数function(node)
 */
function _expandnode(containerdomid, node, expandedcompfn) {
    if (_ifnodeinited(node) && !_ifnodeexpanded(node)) {
        _getsubnodecontainer(containerdomid, node).animate({
            height: 'show'
        });
        _updateobj(_getnodeli(containerdomid, node), node, 'expanded', '1');
        if (expandedcompfn != null)
            expandedcompfn(node);
    }
}

/**
 * 折叠节点
 *
 * @param containerdomd 容器ID
 * @param node 节点
 * @param unexpandedcompfn 折叠完成后回调函数function(node)
 */
function _unexpandnode(containerdomid, node, unexpandedcompfn) {
    if (_ifnodeexpanded(node)) {
        _getsubnodecontainer(containerdomid, node).animate({
            height: 'hide'
        });
        _updateobj(_getnodeli(containerdomid, node), node, 'expanded', '0');
        if (unexpandedcompfn != null)
            unexpandedcompfn(node);
    }
}

/**
 * 判断节点是否已展开
 * @param node
 * @returns {boolean}
 */
function _ifnodeexpanded(node) {
    var expanded = node.expanded;
    if (expanded == null || expanded == '0' || expanded == false || expanded == 'false')
        return false;
    else
        return true;
}

/**
 * 判断节点是否初始化
 * @param node
 * @returns {boolean}
 */
function _ifnodeinited(node) {
    var inited = node.inited;
    if (inited == null || inited == '0' || inited == false || inited == 'false')
        return false;
    else
        return true;
}

/**
 * 判断节点是否已选中
 * @param node
 * @returns {boolean}
 */
function _ifnodeselected(node) {
    var selected = node.selected;
    if (selected == null || selected == '0' || selected == false || selected == 'false')
        return false;
    else
        return true;
}

/**
 * 自动定位到某一个节点，前提是该节点已存在
 *
 * @param containerdomid
 * @param node 定位到哪个节点
 * @param allcompfn
 * @private
 */
function _locationtonode(containerdomid, node, allcompfn) {
    node = _getnode(containerdomid, node);
    if (node == null)
        return;
    var scrollToLi = _getnodeli(containerdomid, node);
    var scrollval = scrollToLi.offset().top
        - $("#" + containerdomid).offset().top
        + $("#" + containerdomid).scrollTop();
    $("#" + containerdomid).animate({
        scrollTop: scrollval
    }, 600);
    if (allcompfn != null)
        allcompfn();
}

/**
 * 自动定位到某节点，如果节点不存在，则尝试自动加载
 *
 * @param containerdomid
 * @param treepath 节点路径，以逗号分隔字符串或数组
 * @param allcompfn
 * @private
 */
function _locationtopath(containerdomid, treepath, allcompfn) {
    if (treepath == null)
        return;
    if (typeof treepath == 'string') {
        treepath = treepath.split(',');
    }
    if (treepath.length <= 0)
        return;

    //获取最后一个节点
    var lastnodeid = treepath[treepath.length - 1];
    _expandnodebypath(containerdomid, treepath, function () {
        _locationtonode(containerdomid, _getnode(containerdomid, {id: lastnodeid}), allcompfn);
    })
}

/**
 * 自动加载并展开路径中的节点
 *
 * @param containerdomid
 * @param treepath
 * @param allcompfn
 * @private
 */
function _expandnodebypath(containerdomid, treepath, allcompfn) {
    if (treepath == null)
        return;
    if (typeof treepath == 'string') {
        treepath = treepath.split(',');
    }
    if (treepath.length <= 0)
        return;
    var node = _getnode(containerdomid, {id: treepath[0]});
    if (node != null) {
        if (!_ifnodeexpanded(node)) {//如果节点未展开，则进行展开
            _trigger_expandauto(containerdomid, node, null, null, null, function () {
                if (treepath.length == 1) {
                    if (allcompfn != null)
                        allcompfn();
                }
                treepath.splice(0, 1);

                if (treepath.length > 0)
                    _expandnodebypath(containerdomid, treepath, allcompfn);
            });
        } else {//节点已初始化，已展开
            if (treepath.length == 1) {
                if (allcompfn != null)
                    allcompfn();
            }
            treepath.splice(0, 1);

            if (treepath.length > 0)
                _expandnodebypath(containerdomid, treepath, allcompfn);
        }
    }
}

/**
 * 删除某个节点，包括其下的子节点
 *
 * @param containerdomid
 * @param node
 * @private
 */
function _delnode(containerdomid, node) {
    if (node == null)
        return;
    $nodeli = _getnodeli(containerdomid, node);

    if ($nodeli.length > 0)
        $nodeli.remove();
}

/**
 * 删除某个节点的所有子节点及子节点的子节点，不包括该节点
 *
 * @param containerdomid
 * @param node
 * @private
 */
function _delsubnodes(containerdomid, node) {
    $subnodecontainerul = _getsubnodecontainerul(containerdomid, node);
    if ($subnodecontainerul.length > 0) {
        $subnodecontainerul.empty();
    }
}

/**
 * 重新加载某个节点及子节点
 *
 * @param containerdomid
 * @param node
 * @param compfn
 * @private
 */
function _reloadnode(containerdomid, node, compfn) {
    node = _getnode(containerdomid, node);
    _reloadsubnodes(containerdomid, {id: node.pid}, compfn);
}

/**
 * 重新加载某个节点的子节点，不包含当前节点
 *
 * @param containerdomid
 * @param node
 * @param compfn
 * @private
 */
function _reloadsubnodes(containerdomid, node, compfn) {
    node = _getnode(containerdomid, node);
    var subnodesall = _getsubnodes_r(containerdomid, node);

    var scrollval = $("#" + containerdomid).scrollTop();

    var subnodesallmaps = {};

    if (subnodesall != null && subnodesall.length > 0) {
        for (var i = 0; i < subnodesall.length; i++) {
            subnodesallmaps["" + subnodesall[i].id] = subnodesall[i];
        }
    }
    //如果原节点是已展开的节点
    if (_ifnodeexpanded(node)) {
        //重置节点
        var $li = _getnodeli(containerdomid, node);
        _delsubnodes(containerdomid, node);
        _updateobj($li, node, 'inited', '0');
        _updateobj($li, node, 'expanded', '0');

        //然后重绘节点
        _trigger_expandauto(containerdomid, node,
            function beforedrawfn(node) {
                //在绘制前，根据节点的状态设置节点的属性
                var orinode = subnodesallmaps["" + node.id];
                if (orinode != null) {
                    if (_ifnodeexpanded((orinode)))
                        node.init_expanded = true;
                    else
                        node.init_expanded = false;

                    if (_ifnodeselected(orinode))
                        node.init_selected = true;
                    else
                        node.init_selected = false;

                    node.init_clicked = false;
                }
            }, null, null, function () {
                if (compfn != null)
                    compfn();
                $("#" + containerdomid).animate({
                    scrollTop: scrollval
                }, 600);
            });
    } else {
        if (compfn != null)
            compfn();
        $("#" + containerdomid).animate({
            scrollTop: scrollval
        }, 600);
    }
}

/**********************************************默认回调方法**************************************************************/
//默认创建节点事件
function _defaultcreatenodefn(tree, node, $container) {
    $container.attr('response-select', '');
    $container.attr('response-click', '');
    $container.attr('response-hover', '');
    $container.attr('response-expand', '');
    $container.text(node.name);
}

//默认加载子节点事件
function _defaultchildrendatafn(tree, pnode) {
    return null;
}

//默认移到某一项时事件
function _defaultenterfn(tree, node, $containerdom, $targetdom) {
    if (!(node.selected == '1' || node.selected == true)) {
        var hoverbg = '#c1d7e9';
        $containerdom.css('background', hoverbg);
    }
}

//默认离开某一项时事件
function _defaultleavefn(tree, node, $containerdom, $targetdom) {
    if (!(node.selected == '1' || node.selected == true)) {
        var hoverbg = '#ffffff';
        $containerdom.css('background', hoverbg);
    }
}

//默认点击事件
function _defaultclickfn(tree, node, $containerdom, $targetdom) {

}

//默认选中时事件
function _defaultselectedfn(tree, node, $containerdom, $targetdom) {
    var selectedbg = '#337ab7';
    $containerdom.css('background', selectedbg);
}

//默认变为未选中事件
function _defaultunselectedfn(tree, node, $containerdom) {
    var hoverbg = '#ffffff';
    $containerdom.css('background', hoverbg);
}

//默认展开时事件
function _defaultexpandedfn(tree, node, $containerdom, $targetdom) {

}

//默认收缩时事件
function _defaultunexpandedfn(tree, node, $containerdom, $targetdom) {

}

/*********************************************设置或获取对象到dom元素上**************************************************/
/**
 * 将键值对对象设置到某一元素上
 */
function _setobj($dom, obj) {
    var name = "tree";
    // 添加自定义属性
    if (obj != null && name != null && name != '') {
        for (var key in obj) {
            var val = obj[key];
            if (val instanceof Function)
                continue;
            else
                $dom.attr(name + "_" + key, val);
        }
    }
}

/**
 * 更新键值对数据到某一元素上
 */
function _updateobj($dom, node, key, val) {
    var name = "tree";
    $dom.attr(name + "_" + key, val);
    node[key] = val;
}

/**
 * 从元素上获取某一对象，键值对对象
 */
function _getobj($dom) {
    var name = "tree";
    var attrs = $dom.get(0).attributes;
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

/**************************************************控制同步/异步加载节点绘制情况****************************************/
function addcurrdrawing(containerdomid, addval, compfunc, node) {
    var cntdrawing = $("#" + containerdomid).attr('treenodedrawing');
    if (cntdrawing == null)
        cntdrawing = 0;
    cntdrawing = parseInt(cntdrawing) + addval;
    $("#" + containerdomid).attr('treenodedrawing', cntdrawing);
    if (cntdrawing == 0) {
        if (compfunc != null)
            compfunc(node);
    }
    return cntdrawing;
}

function resetcurrdrawing(containerdomid) {
    $("#" + containerdomid).attr('treenodedrawing', 0);
}

function rand(n, m) {
    var c = m - n + 1;
    return Math.floor(Math.random() * c + n);
}