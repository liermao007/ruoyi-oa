<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>标书模板的维护</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <%--<link href="${ctxStatic}/uploadify/uploadify.css" type="text/css" rel="stylesheet"/>--%>
    <script src="${ctxStatic}/uploadify/ajaxfileupload.js" type="text/javascript"></script>
    <script>
        $(function(){
            function ajaxFileUpload() {
                var id="${personSigns.id}";
                var name=$("#name").val();
                var signName=$("#signName").val();
                $.ajaxFileUpload
                ({
                    url: '${ctx}/oa/personSigns/saveContractFile?id='+id+'&name='+name+'&signName='+signName, //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: 'file_upload', //文件上传域的ID
                    dataType: 'text', //返回值类型 一般设置为json
                    success: function (data, status) //服务器成功响应处理函数
                    {
                       alert("签名图片上传成功！");
                        //若是上传成功，跳转的页面
                        location.href = "${ctx}/oa/personSigns/list";
                    },
                    error: function (data,xml, status, e)//服务器响应失败处理函数
                    {
                        alert("签名图片上传失败，请联系管理员！");
                    }
                })
                return false;
            }
            $("#btnSubmit").on("click", function () {
                ajaxFileUpload();
                return false;
            })
        })
    </script>

</head>
<body>
<%--<div id="header">--%>
    <%--<h1>--%>
        <!--<a href="dashboard.html">OA办公系统</a>-->
    <%--</h1>--%>
<%--</div>--%>
<div class="title_Infor">
    <a href="${ctx}/sys/user/myPhone" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3 >个人签名管理</h3>


</div>


<form:form id="inputForm" modelAttribute="personSigns"  method="post" class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input path="name" id="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">签章名称：</label>
        <div class="controls">
            <form:input path="signName" id="signName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">

        <label class="control-label">选择签名图片：</label>
        <p id="ctlOnlyP"><img src="${personSigns.signUrl}" alt="图片" id="ctlImg"/></p>
        <div class="controls">
            <b>上传图片</b>
            <%--<input type="file" name="file"  style="opacity:100;" value=""/>--%>
            <input type="file" class="btn btn-success" name="file" onchange="readAsDataURL()" id="file_upload" value="选择图片"/>
        </div>
    </div>
    <div class="form-actions" style="margin: 20px;padding-left: 10px;">
        <input id="btnSubmit" class="btn btn-primary" style="width: 100%; border-radius: 5px; height: 36px;" type="button" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button"style="width:100%; border-radius: 5px; height: 36px;" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script>
    function readAsDataURL(){
        //检验是否为图像文件
        var file = document.getElementById("file_upload").files[0];
        if(!/image\/\w+/.test(file.type)){
            alert("请上传图片文件！");
            return false;
        }
        var reader = new FileReader();
        //将文件以Data URL形式读入页面
        reader.readAsDataURL(file);
        reader.onload=function(e){
            //显示文件
            $("#ctlImg").attr('src',this.result);
        }
    }
</script>
</body>

</html>
