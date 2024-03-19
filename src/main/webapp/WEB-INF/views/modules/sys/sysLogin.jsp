<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>办公自动化平台登录</title>
	<meta name="decorator" content="blank"/>
	<script src="${ctxStatic}/layer/layer.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/layer/skin/default/layer.css">
	<style type="text/css">
        html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
            .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
                -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
            .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
            .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
            .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
            .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
            .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
            label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
			.cut{ width: 100px; height: 50px; margin-left: 270px; margin-top: 125px;
			}
	</style>


	<!-- IE浏览器不兼容问题解决:开始 -->
	<style>
		/*.cDiv{*/
		/*position: fixed;*/
		/*top:0;*/
		/*left: 0;*/
		/*z-index: 999;*/
		/*width: 100%;*/
		/*height: 100%;*/
		/*background-color:rgba(0,0,0,0.3);*/
		/*filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#BF000000,endColorstr=#BF000000);*/
		/*}*/
		img{
			border: none;
		}
		a{
			text-decoration: none;
		}
		.sweet-alert {
			background-color: white;
			border: 2px solid #AEDEF4;
			font-family: 'Open Sans', sans-serif;
			width: 478px;
			padding: 17px;
			-webkit-border-radius:5px ;
			-moz-border-radius: 5px;
			border-radius: 5px;
			text-align: center;
			position: fixed;
			left: 50%;
			top:0;
			margin-left: -256px;
			/*margin-top: -200px;*/
			overflow: hidden;
			display: block;
			z-index: 2000;

			/*background-color:rgba(0,0,0,0.3);*/
			/*filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#BF000000,endColorstr=#BF000000);*/
		}
		.sweet-alert h2 {
			color: #575757;
			font-size: 18px;
			text-align: center;
			font-weight: 600;
			text-transform: none;
			position: relative;
			margin: 25px 0;
			padding: 0;
			line-height: 25px;
			display: block;
		}
		.sweet-alert p {
			color: #797979;
			font-size: 11px;
			text-align: center;
			font-weight: 300;
			position: relative;
			margin: 0;
			padding: 0;
			line-height: normal;
		}
		.sweet-alert button {
			background-color: #AEDEF4;
			color: white;
			border: none;
			box-shadow: none;
			font-size: 17px;
			font-weight: 500;
			-webkit-border-radius:5px ;
			-moz-border-radius: 5px;
			border-radius: 5px;
			padding: 10px 32px;
			margin: 26px 5px 0 5px;
			cursor: pointer;
		}
		.sweet-alert ul{
			list-style: none;
			width: 100%;
			padding-left: 0;
		}
		.sweet-alert ul li{
			float: left;
			width: 20%;
		}
		.sweet-alert ul li a{
			color: #666;

		}
		.sweet-alert ul li a>img{
			width: 40px;
			height: 40px;
		}

	</style>
	<script>

        window.onload = function(){
            var u_agent = navigator.userAgent;
            var browser_name='Failed to identify the browser';
            if(u_agent.indexOf('Firefox')>-1){
                browser_name='Firefox';
            }else if(u_agent.indexOf('Chrome')>-1){
                browser_name='Chrome';
            }else if(u_agent.indexOf('Trident')>-1&&u_agent.indexOf('rv:11')>-1){
                browser_name='IE11';
            }else if(u_agent.indexOf('MSIE')>-1&&u_agent.indexOf('Trident')>-1){
                browser_name='IE(8-10)';
            }else if(u_agent.indexOf('MSIE')>-1){
                browser_name='IE(6-7)';
            }else if(u_agent.indexOf('Opera')>-1){
                browser_name='Opera';
            }else{
                browser_name+=',info:'+u_agent;
            }
            if(browser_name.indexOf("IE") >= 0){
                var oDiv = document.createElement("div");
                oDiv.className='cDiv';
                oDiv.innerHTML = '<div class="sweet-alert showSweetAlert visible" tabindex="-1" data-has-cancel-button="false" data-allow-ouside-click="false" data-has-done-function="false" data-timer="null" style="display: block;"><div class="icon error" style="display: none;"><span class="x-mark"><span class="line left"></span><span class="line right"></span></span></div><div class="icon warning" style="display: none;"> <span class="body"></span> <span class="dot"></span> </div> <div class="icon info" style="display: none;"></div> <div class="icon success" style="display: none;"> <span class="line tip"></span> <span class="line long"></span> <div class="placeholder"></div> <div class="fix"></div> </div> <div class="icon custom" style="display: none;"></div> <h2><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">检测到您的IE浏览器版本不兼容，建议更换浏览器，<br>点击<span style="color:red;"> 关闭 </span>后关闭此浏览器。</font></font></h2><p style="display: block;"><font style="vertical-align: inherit;"><font style="vertical-align: inherit; font-size: 16px;color:#575757;font-weight: 600">建议浏览器：</font></font></p><p><ul style="padding:0 40px;"><li><a href="javascript:;">'+
                    '<img src="${ctxStatic}/images/ie-icon-img/360.png" />   <p>360浏览器</p></a></li><li><a href="javascript:;"><img src="${ctxStatic}/images/ie-icon-img/Chrome.png" /><p>Chrome浏览器</p></a></li><li><a href="javascript:;"><img src="${ctxStatic}/images/ie-icon-img/Firefox.png" />   <p>Firefox浏览器</p></a></li><li><a href="javascript:;"><img src="${ctxStatic}/images/ie-icon-img/QQ.png" />   <p>QQ浏览器</p></a></li></ul></p><p><button class="confirm" tabindex="1" style="background-color: rgb(87,179, 223); box-shadow: rgba(174, 222, 244, 0.8) 0px 0px 2px, rgba(0, 0, 0, 0.0470588) 0px 0px 0px 1px inset;"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;"> 关闭 </font></font></button><button class="confirm_false" tabindex="1" style="background-color: rgb(221, 107, 85); box-shadow: rgba(221, 107, 85, 0.8) 0px 0px 2px, rgba(0, 0, 0, 0.0470588) 0px 0px 0px 1px inset;">取消</button></p></div>';
                document.body.appendChild(oDiv);
                var confirm = document.querySelector(".confirm");
                var confirm_false = document.querySelector(".confirm_false");
                confirm.onclick = function(){
                    window.close();
                    //点击确定打开IE下载地址
                    //window.open("https://support.microsoft.com/zh-cn/help/17621/internet-explorer-downloads");
                }
                confirm_false.onclick = function(){
                    //点击取消弹出框小时
                    document.body.removeChild(oDiv);
//                        window.open("http://work.chinahealth-groups.com/a/login");
                }
            }
        }
	</script>
	<!-- IE浏览器不兼容问题解决:结束 -->


	<script type="text/javascript">
		$(document).ready(function() {
			$("#cut").click(function(){
				var url='sys/user/getPreview';
				layer.open({
					type: 2,
					title: false, //不显示标题
					skin: 'layui-layer-rim', //加上边框
					area: ['300px', '270px'], //宽高
					content: url
				});
			});
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				}
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<h1 class="form-signin-heading">办公自动化平台</h1>
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
		<%--<input type="button" class="input-label" id="cut" value="人脸识别">--%>
		<input type="hidden" id="userlogo" name="userlogo">
		<label class="input-label" for="username">登录名</label>
		<input type="text" id="username" name="username" class="input-block-level required" value="${username}">
		<label class="input-label" for="password">密码</label>
		<input type="password" id="password" name="password" class="input-block-level required">
		<c:if test="${isValidateCodeLogin}"><div class="validateCode">
			<label class="input-label mid" for="validateCode">验证码</label>
			<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
		</div></c:if><%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
		<input class="btn btn-large btn-primary" type="submit" value="登  录"/>&nbsp;&nbsp;
		<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>

	</form>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript">

    </script>
</body>
</html>