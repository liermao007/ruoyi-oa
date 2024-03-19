<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <style>
        ul{ list-style: none}
        .login_Con{ background:#fff; height:100%;
            position: relative; padding: 0 20px;}
        .login_one{ width: 100%; height:auto; display: block}
        .login_one h3{ text-align: center; line-height: 80px;color:#333;}
        .login_one ul li span{height: 40px; width: 100%; display: inline-block; line-height: 40px; }
        .login_one ul li input{ width: 100%; height: 40px; border: 0; border-bottom: 1px solid #dbdbdb; outline: none}
        .login_two{ width: 100%; height:auto; display: block;}
        .login_two h3{ text-align: center; line-height: 80px;color:#333;}
        .login_two ul li .span{height: 40px; width: 100%; display: inline-block; line-height: 40px; }
        .login_two ul li input{ width: 100%; height: 40px; border: 0; border-bottom: 1px solid #dbdbdb; outline: none}
        /*验证码*/
        .getCode {     /* padding: 20px 0; */
            position: absolute;
            right: 0;
            top: 54px;
            z-index: 22;
            background:#f6f6f6;
            width: 50px;
            text-align: center; }

        .getCode span{     width: 100%;
            height: 30px;
            line-height: 30px;
            text-align: center;}
        .col-xs-6{ padding: 0;}
        /****登陆注册****/

        /**/
        .register{ margin-top: 20px;}
        .register>span:nth-of-type(1)>a{ color: red; }
        input{ width: 100% !important;
            height: 30px !important;}

    </style>
    <script type="text/javascript">
        $(function(){
            var message=$("#message").val();
            if(message!=null&&message!=""){
                alert(message);
            }
            $(".login_one ul li input").each(function(i){
                $(this).one('focus',function(){
                    if($(this).val()==" ")
                    {
                        $(this).siblings('span').html($(this).siblings('span').html());
                    }else
                    {
                        $(this).siblings('span').html($(this).val());
                    }

                    $(this).val(" ");

                });
            });
        });

    </script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});

	</script>

</head>
<body>

    <!--Header-part-->
    <%--<div id="header">--%>
        <%--<h1>--%>
            <!--<a href="dashboard.html">OA办公系统</a>-->
        <%--</h1>--%>
    <%--</div>--%>
    <div class="title_Infor">
        <a href="${ctx}/sys/user/myPhone" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
        <h3 >修改密码</h3>
    </div>

	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="1" class="required"/>
				<span class="help-inline"><font color="red"></font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="1" class="required"/>
				<span class="help-inline"><font color="red"></font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="1"  class="required" equalTo="#newPassword"/>
			    <span class="help-inline"><font color="red"></font> </span>
			</div>
		</div>

		<div class="form-actions" align="center">
			<input id="btnSubmit" class="btn btn-primary" style="width:100%; border-radius: 5px; height: 36px;" type="submit"  value="保 存"/>
		</div>
	</form:form>
  <input type="hidden" id="message" value="${message}">

</body>



</html>