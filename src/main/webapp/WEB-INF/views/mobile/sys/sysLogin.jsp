<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>智慧岗位工作平台登录</title>
    <meta name="decorator" content="blank"/>

    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <!-- 而如果你的网站不是响应式的，请不要使用 initial-scale 或者禁用缩放。 -->
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <!-- 页面描述 -->
    <meta name="description" content=" 不超过150个字符"/>
    <!-- 页面关键词 -->
    <meta name="keywords" content=""/>
    <!-- iOS 设备 begin -->
    <meta name="apple-mobile-web-app-title" content="标题">
    <!-- 添加到主屏后的标题（iOS 6 新增） -->
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <!-- 是否启用 WebApp 全屏模式，删除苹果默认的工具栏和菜单栏 -->
    <!-- 启用360浏览器的极速模式(webkit) -->
    <meta name="renderer" content="webkit">
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
    <meta name="HandheldFriendly" content="true">
    <!-- 微软的老式浏览器 -->
    <meta name="MobileOptimized" content="320">
    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="portrait">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="portrait">
    <!-- UC强制全屏 -->
    <meta name="full-screen" content="yes">
    <!-- QQ强制全屏 -->
    <meta name="x5-fullscreen" content="true">
    <!-- UC应用模式 -->
    <meta name="browsermode" content="application">
    <!-- QQ应用模式 -->
    <meta name="x5-page-mode" content="app">
    <title>智慧健康服务体系登录页面</title>

    <!-- Bootstrap -->
    <%--<link href="${ctxStatic}/oaApp/css/bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="${ctxStatic}/oaApp/css/common.css" rel="stylesheet">
    <link href="${ctxStatic}/oaApp/css/login.css" rel="stylesheet">
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/layer/skin/default/layer.css">
    <script src="${ctxStatic}/wechat/jweixin-1.2.0.js"></script>
    <style type="text/css">
        html{margin-top: -20px !important;}
        html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
        .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
            -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
        .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
        .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
        .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
        .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
        .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
        label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
        .cut{
            width: 100px;
            height: 50px;
            margin-left: 270px;
            margin-top: 135px;
        }
        .upload{
            padding: 4px 10px;
            width: 100%; height: 50px;
            line-height: 20px;
            position: relative;
            border: 1px solid #999;
            text-decoration: none;
            color: #666;
        }
        .change{
            width: 100%; height: 50px;
            position: absolute;
            overflow: hidden;
            right: 0;
            top: 0;
            opacity: 0;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            var m = '${message}'
            if(m && m != ''){
                alert(m)
            }
            $("#loginForm").validate({
                rules: {
                    validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
                },
                messages: {
                    username: {required: "请填写用户名."},password: {required:"请填写密码."},
                    validateCode: {remote: "验证码不正确.", required:  "请填写验证码."}
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

<section class="login_Con col-sm-12 col-xs-12">
    <div class="login_Logo col-sm-12 col-xs-12">
        <img src="${ctxStatic}/oaApp/images/login/logo.png"/>
    </div>
    <form  id="loginForm" action="${ctx}/login" method="post">
        <div class="login_User">
            <span class="login_User_Img">
                <img src="${ctxStatic}/oaApp/images/login/logon_User.png" alt="登录账号框的Icon"/>
            </span>
            <input type="text" id="username" name="username" value="${username}">
        </div>
        <div class="login_Pass">
            <span class="login_Pass_Img">
                <img src="${ctxStatic}/oaApp/images/login/login_PassWord.png" alt="登录密码框的Icon"/>
            </span>
            <input type="password" id="password" name="password" >
        </div>
        <c:if test="${isValidateCodeLogin}">
            <div class="validateCode">
                <label class="input-label mid" for="validateCode">验证码</label>
                <sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0; width:55px"/>
            </div>
        </c:if>
        <div class="login_Btn">
            <input type="submit" value="登 &nbsp;&nbsp; 录"/>
        </div>
        <div class="reader_Me">
            <div class="pull-left">
                <label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>
            </div>
        </div>

    </form>
</section>
<script>
    $(document).ready(function(){
        var u = navigator.userAgent.toLowerCase();;
        var isAndroid = u.indexOf('android') > -1 || u.indexOf('adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? cpu.+mac os x/); //ios终端
        var html;
        if(isAndroid){
            if(u.match(/micromessenger/i) == 'micromessenger'){
                html='<div class="login_Btn" id="cut" ><input type="button" value="人脸识别">' +
                        '<input type="hidden" id="file"></div>';

                $.ajax({
                    type: "get",
                    url: "getWechatConfig",
                    data: "url="+encodeURIComponent(location.href.split('#')[0]),
                    dataType:"json",
                    success: function(data){
                        if(data&&data.error=='no'){
                            wx.config({
                                debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                                appId : data.appId, // 必填，公众号的唯一标识
                                timestamp : data.timestamp, // 必填，生成签名的时间戳
                                nonceStr : data.nonceStr, // 必填，生成签名的随机串
                                signature : data.signature,// 必填，签名，见附录1
                                jsApiList : ["chooseImage"]

                            });
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(errorThrown)
                    }
                });
            }
            else{
                html='<div class="login_Btn" id="android">' +
                        '<input type="button" value="人脸识别"/></div>';
            }

        }
        if(isiOS){
            html='<div class="login_Btn" id="cut"><input type="button"  value="人脸识别"></div>';
        }
        $(".reader_Me").before(html)

        $("#cut").click(function(){
            if(isAndroid){
                wx.chooseImage({
                    count: 1, // 默认9
                    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                    sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有
                    success: function (res) {

                        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片

                        wx.getLocalImgData({
                            localId: localIds[0], // 图片的localID
                            success: function (res) {

                                var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
                                $("#userlogo").val(localData);
                                $("#loginForm").submit();

                            }
                        });
                    }
                });
            }
            else {
                var url='sys/user/getPreview';
                layer.open({
                    type: 2,
                    title: false, //不显示标题
                    skin: 'layui-layer-rim', //加上边框
                    area: ['300px', '280px'], //宽高
                    content: url
                });
            }
        });

        $('#android').on('click', function() {
            $("#androidFile").remove();
            var text='<div id="androidFile">" <a href="javascript:;"  class="upload ">人脸识别'+
                    '<input class="change" id="file"  type="file"  accept="image/png,image/jpeg,image/gif" capture="camera" />'+
                    '</a></div>';
            $("#android").after(text);
            $("#androidFile").hide();
            $("#file").trigger('click');
            $('#file').on('change',function(){
                drawOnCanvas(this.files[0]);
            });
        })
    });

    function drawOnCanvas(file) {
        var reader = new FileReader();
        //files[0]对象是从input type=file中获取的file对象
        reader.readAsDataURL(file);
        //将file对象base64转码成功后进入
        reader.onload = function () {
            //声明img标签，并将未压缩的图片赋值给src属性
            var image = $('<img/>');
            image.attr('src', this.result);

            image.on('load', function () {
                //定义canvas的宽、高为72px
                var square = 320;
                var canvas = document.createElement('canvas');
                canvas.width = square;
                canvas.height = square;
                var context = canvas.getContext('2d');
                context.clearRect(0, 0, square, square);
                var imageWidth;
                var imageHeight;
                var offsetX = 0;
                var offsetY = 0;
                //判断是根据原图片的宽压缩还是高压缩
                if (this.width > this.height) {
                    imageWidth = Math.round(square * this.width / this.height);
                    imageHeight = square;
                    offsetX = - Math.round((imageWidth - square) / 2);
                } else {
                    imageHeight = Math.round(square * this.height / this.width);
                    imageWidth = square;
                    offsetY = - Math.round((imageHeight - square) / 2);
                }
                context.drawImage(this, offsetX, offsetY, imageWidth, imageHeight);
                //data即为压缩为72x72的压缩图片的base64内容
                var data = canvas.toDataURL('image/jpeg');
                var Pic = data.replace("data:image/jpeg;base64,", "");

                $("#userlogo").val(Pic);
                $("#loginForm").submit();
            });
        };
    }
</script>



<%--<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->--%>
<%--<div class="header">--%>
<%--<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>--%>
<%--<label id="loginError" class="error">${message}</label>--%>
<%--</div>--%>
<%--</div>--%>
<%--<h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>--%>
<%--<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">--%>
<%--<label class="input-label" for="username">登录名</label>--%>
<%--<input type="text" id="username" name="username" class="input-block-level required" value="${username}">--%>
<%--<label class="input-label" for="password">密码</label>--%>
<%--<input type="password" id="password" name="password" class="input-block-level required">--%>
<%--<c:if test="${isValidateCodeLogin}"><div class="validateCode">--%>
<%--<label class="input-label mid" for="validateCode">验证码</label>--%>
<%--<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>--%>
<%--</div></c:if>&lt;%&ndash;--%>
<%--<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> &ndash;%&gt;--%>
<%--<input class="btn btn-large btn-primary" type="submit" value="登  录"/>&nbsp;&nbsp;--%>
<%--<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>--%>

<%--</form>--%>
<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript">

</script>
</body>
</html>