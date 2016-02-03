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

    <title>Page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/plugins/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/css/common.css">

    <script type="text/javascript" language="javascript"
            src="common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/plugins/bootstrap/js/bootstrap.min.js"></script>

    <script>
        function upload(ths){
            var filepath = $(ths).val();

            ajaxfileupload("uploadfiles",'uploadfile',["uploadfile"],null,function(ret){
                window.location.reload();
            });
        }
    </script>
</head>

<body>
<input type="hidden" id="basePath" value="<%=basePath%>">
<div class="container" style="margin-top:50px;">
    <div class="row">
        <div class="col-xs-3" id="uploadfileform">
            <button class="btn btn-primary" onclick="$('#uploadfile').click();">上传文件</button>
            <input type="file" class="hide" id="uploadfile" onchange="upload(this);">
        </div>
    </div>
    <div class="row" style="margin-top:8px;">
        <div class="col-xs-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <td>文件名</td>
                    <td>文件类型</td>
                    <td>文件大小</td>
                    <td>上传时间</td>
                    <td>下载次数</td>
                    <td>是否有效</td>
                </tr>
                </thead>
                <c:forEach items="${files}" var="file">
                    <tr>
                        <td>${file.fileName}</td>
                        <td>${file.fileTypeDesc}</td>
                        <td>${file.fileSizeStr}</td>
                        <td>${file.uploadTime}</td>
                        <td>${file.downCnt}</td>
                        <td>${file.valid? '是':'否'}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
