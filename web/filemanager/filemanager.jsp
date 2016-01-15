<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>文件管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="common/plugins/bootstrap/css/bootstrap.min.css">

    <script type="text/javascript" language="javascript" src="common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript" src="common/js/ntree.js"></script>
    <script type="text/javascript" language="javascript" src="common/plugins/bootstrap/js/bootstrap.min.js"></script>

    <script>
        function loadtree(rootpath) {
            var tree = inittree("treediv", function list(tree, pnode) {
                return {
                    url: "listfolder",
                    data: {
                        rootpath: pnode.id == "-1" ? rootpath : pnode.fullpath
                    },
                    success: function (ret) {
                        var nodes = new Array();
                        for (var i = 0; i < ret.length; i++) {
                            var nd = tree.getdefaultnode1(null, ret[i].name, ret[i]);
                            nd.childrendatafn = list;
                            nodes.push(nd);
                        }
                        return nodes;
                    }
                };
            });
        }
        $(document).ready(function () {
            loadtree($("#rootpath").val());
        });
    </script>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-4" style="height:400px;">
            <div>
                <input type="text" class="form-control" onblur="loadtree(this.value);" value="c:/" id="rootpath">
            </div>
            <div id="treediv" style="height:400px;">

            </div>
        </div>
    </div>
</div>
</body>
</html>
