<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>个人信息</title>

    <!--<meta name="decorator" content="default  footer"/>-->
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <script src="/jquery-1.7.1.js" type="text/javascript"></script>
    <script src="/static/uploadify/ajaxfileupload.js" type="text/javascript"></script>
    <script src="/static/oaApp/js/exif.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            var message=$("#message").val();
            if(message!=null&&message!=""){
                alert(message);
            }

        });

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
				errorContainer: "",
				errorPlacement: function(error, element) {
					alert("保存个人信息成功！");
				}
			});
		});
	</script>

    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>
    <style>

        input{
            width:100%;
            height: 30px;
            border-radius: 5px;
        }
        form{
            height: 30px;
        }
    </style>
</head>
<body style="margin-top:0;">

<!--Header-part-->
<%--<div id="header">--%>
    <%--<h1>--%>
        <!--<a href="dashboard.html">OA办公系统</a>-->
    <%--</h1>--%>
<%--</div>--%>
<div class="title_Infor">
    <a href="${ctx}/sys/user/myPhone" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>

    <h3 >个人信息</h3>


</div>


	<form:form id="inputForm" modelAttribute="user"  action="${ctx}/sys/user/info"  method="post" class="form-horizontal" style="height:auto;"><%--
		<form:hidden path="email" htmlEscape="false" maxlength="255" class="input-xlarge"/>
		<sys:ckfinder input="email" type="files" uploadPath="/mytask" selectMultiple="false"/> --%>
		<%--<sys:message content="${message}"/>--%>
		<div class="control-group">
			<label class="control-label">头像:</label>
            <p id="ctlOnlyP"><img src="${user.photo}" alt="图片" id="ctlImg"/></p>
            <div class="control-group">
                <div class="controls">
                    <b>上传图片</b>
                    <input type="hidden" id="files" name="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <input type="file" apture="microphone"  name="file" accept="*/*" id="file_upload" onchange="readAsDataURL()"/>
                </div>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">归属公司:</label>
			<div class="controls">
				<label class="lbl">${user.company.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
				<label class="lbl">${user.office.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" readonly="true" style="height: 30px; border-radius: 5px; "/>
			</div>
		</div>
        <div class="control-group" style="display: none">
            <label class="control-label">照片路径:</label>
            <div class="controls">
                <form:input path="photo" htmlEscape="false"  class="required" readonly="true" style="height: 30px; border-radius: 5px; "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="email" style="height: 30px; border-radius: 5px; "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50" style="height: 30px; border-radius: 5px; "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50" style="height: 30px; border-radius: 5px; "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">身份证号:</label>
            <div class="controls">
                <form:input path="cardNo" htmlEscape="false" maxlength="50" style="height: 30px; border-radius: 5px; "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<label class="lbl">${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上次登录:</label>
			<div class="controls">
				<label class="lbl">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" style="width:100%; border-radius: 5px; height: 36px;" type="submit" value="保 存"/>
		</div>
	</form:form>
<input type="hidden" id="message" value="${message}">
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
            ajaxFileUpload("${user.id}");//预览
    }
    //传的参数 和 接收的参数 格式不一样
    function ajaxFileUpload(id) {//保存
        console.log(id);
        $.ajaxFileUpload
        ({
            url:'${ctx}/sys/user/saveContractFile?id='+ id, //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'file_upload', //文件上传域的ID
            dataType: 'text', //返回值类型 一般设置为json
            success: function (data) //服务器成功响应处理函数
            {
                var reg = /<pre.+?>(.+)<\/pre>/g;
                var result = data.match(reg);

                path = RegExp.$1;
                var fileName=path.substring(path.lastIndexOf("/")+1);
                $("#photo").val(path);
                $("#files").val(path);
                $("#preview").attr("href",path);
                $("#preview").html(fileName);
                alert("图片上传成功！");
            },
            error: function (data)//服务器响应失败处理函数
            {
                alert("2")
                console.log("头像图片上传失败，请联系管理员！");
            }
        })
        return false;
    }
</script>
</body>
</html>