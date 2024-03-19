<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>标书模板的维护</title>
    <meta name="decorator" content="default"/>
<%--    <link href="${ctxStatic}/uploadify/uploadify.css" type="text/css" rel="stylesheet"/>--%>
<%--    <script type="text/javascript" src="${ctxStatic}/uploadify/swfobject.js"></script>--%>
<%--    <script src="${ctxStatic}/uploadify/jquery.uploadify.js" type="text/javascript"></script>--%>
    <script type="text/javascript">
        $(document).ready(function () {
<%--            $('#file_upload').uploadify({--%>
<%--                'swf': '${ctxStatic}/uploadify/uploadify.swf',--%>
<%--                //'uploader'：若是上传路径--%>
<%--                'uploader': '${ctx}/oa/personSigns/saveContractFile',--%>
<%--                'buttonText': '请选择要上传的个人签名',--%>
<%--                'height': 25,--%>
<%--                'width': 150,--%>
<%--                'auto': false,--%>
<%--                'fileTypeExts': '*.*',--%>
<%--                "fileObjName": 'file',--%>
<%--                'multi': false,--%>
<%--                'onUploadSuccess': function (file, data, response) {--%>
<%--                    console.log(file);--%>
<%--                    //若是上传成功，跳转的页面--%>
<%--//                    alert("222");--%>
<%--                     location.href = "${ctx}/oa/personSigns/list";--%>
<%--                },--%>
<%--                'onCancel': function () {--%>
<%--                    flag = false;--%>
<%--                },--%>
<%--                'onSelect': function () {--%>
<%--                    flag = true;--%>
<%--                },--%>
<%--                'onUploadStart': function (file) {--%>
<%--                    $("#file_upload").uploadify("settings", "formData", {--%>
<%--                        'name': $("#name").val(),'signName':$("#signName").val(),--%>
<%--                        'id':$("#id").val()--%>
<%--                    });--%>
<%--                }--%>
<%--            });--%>
<%--            $("#btnSubmit").on("click", function () {--%>
<%--//                console.log($('#file_upload').val());--%>
<%--                $('#file_upload').uploadify('upload', '*');--%>
<%--            })--%>

<%--        });--%>

            $("#btnSubmit").on("click", function () {
                var formData = new FormData();
                formData.append("file", document.getElementById("file").files[0]);
                formData.append("name", $("#name").val());
                formData.append("signName", $("#signName").val());
                formData.append("id", $("#id").val());
                $.ajax({
                    url: "${ctx}/oa/personSigns/saveContractFile",
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function(response){
                        location.href = "${ctx}/oa/personSigns/list";
                    }
                });
            })
        });
    </script>

</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
    <li class="active">
        <shiro:hasPermission name="oa:personSigns:edit">${oaNotify.status eq '1' ? '查看' : not empty PersonSigns.id ? '修改' : '添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:personSigns:edit">查看</shiro:lacksPermission>
    </li>
</ul>
<form:form id="inputForm" modelAttribute="personSigns" action="${ctx}/oa/personSigns/saveContractFile" method="post" class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">签章名称：</label>
        <div class="controls">
            <form:input path="signName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">签名图片路径：</label>
        <div class="controls">
            <input type="file" name="file" id="file"/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-success" type="button" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>

    </div>
</form:form>
</body>

</html>
