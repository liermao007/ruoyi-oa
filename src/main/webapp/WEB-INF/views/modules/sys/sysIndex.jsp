<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>OA办公系统</title>
    <meta name="decorator" content="blank"/>
    <meta content="webkit" name="renderer"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <%--<link href="${ctxStatic}/common/icons.css" type="text/css" rel="stylesheet"/>--%>
    <link href="${ctxStatic}/css/style.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/css/style-new24.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/css/iconfont.css" type="text/css" rel="stylesheet"/>
    <%--<script type="text/javascript" src="${ctxStatic}/uploadify/jquery-1.3.2.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctxStatic}/oaApp/js/amq_jquery_adapter.js"></script>--%>
    <!--amq消息中间件js-->
    <script src="/static/js/nav.js" type="text/javascript"></script>
    <%--<script type="text/javascript" src="${ctxStatic}/oaApp/js/amq.js"></script>--%>
    <script type="text/javascript" src="${ctxStatic}/uploadify/swfobject.js"></script>
    <script type="text/javascript" src="${ctxStatic}/uploadify/jquery.uploadify.js"></script>
    <script src="/static/uploadify/ajaxfileupload.js" type="text/javascript"></script>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/yanue.pop.js"></script>--%>
    <%--弹出框插件--%>
    <script src="/static/layer/layer.js" type="text/javascript"></script>
    <link href="/static/layer/layer.css" rel="stylesheet" type="text/css">
    <!--amq消息中间件js-->
    <style type="text/css">
        body {
            background-color: #EDEFF0;
            margin: 0;
            padding: 0;
            overflow: hidden;

        }

        .item a:hover {
            text-decoration: none;
            color: #000000;
        }

        .mask {
            position: absolute;
            filter: alpha(opacity=60);
            background-color: #777;
            z-index: 1000;
            left: 0px;
            opacity: 0.5;
            -moz-opacity: 0.5;
            display: none;
        }

        #header {
            margin: 0;
            position: static;
        }

        #header li {
            font-size: 14px;
            _font-size: 12px;
        }

        #header .brand {
            font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
            font-size: 26px;
            padding-left: 33px;
        }

        #userControl > li > a {
            /*color:#fff;*/
            text-shadow: none;
        }

        #userControl > li > a:hover, #user #userControl > li.open > a {
            background: transparent;
        }

        #menu {
            width: 100%;
            /*background-color: #FFFFFF;*/
        }

        #menu, #menu > div {
            height: 90px;
        }

        #userLogo {
            float: left;
            width: 230px;
            border-right: 3px solid #EDEFF0;
        }

        #userLogo img {
            width: 70px;
            height: 70px;
            margin-top: 10px;
            margin-left: 15px;
            float: left;
            cursor: pointer;
        }

        #userLogo ul {
            cursor: pointer;
        }

        #loginName {
            font-size: 14px;
        }

        #userLogo div {
            position: absolute;
            left: 95px;
        }

        #userLogo ul {
            padding-top: 15px;
            font-family: "微软雅黑", "黑体", "宋体";
            font-weight: 100;
        }

        #menu ul, #left ul {
            list-style: none;
            margin: 0;
        }

        #topMenus li {
            float: left;
            /*width: 65px;*/
            cursor: pointer;
            /*padding: 10px 10px;*/
        }

        .item li {
            text-align: center;
        }

        #topMenus li a, #left li a {
            font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
            text-decoration: none;
            color: #555555;
        }

        #topMenus li a {
            color: #fff;
        }

        #topMenus li a span {
            margin: 0 auto;
            display: block;
            text-align: center;
        }

        #moreMenu {
            width: 100%;
            display: none;
            background-color: #FFFFFF;
            z-index: 1002;
            filter: alpha(opacity=100);
            opacity: 1;
            height: 400px;
        }

        #moreMenu ul {
            display: inline-block;
        }

        #moreMenu li {
            display: inline-block;
            padding: 25px;
        }

        #left, #right {
            position: absolute;
            bottom: 0px;
        }

        #left {
            left: 0px;
            overflow-x: hidden;
            overflow-y: auto;
            width: 230px;
            background-color: #FFFFFF;
        }

        #right {
            right: 0px;
            padding-right: 0;
        }

        #right .bg_color {
            width: 120%;
            height: 100%;
            position: absolute;
            background: #fff;
            z-index: -1;
            left: -20px;
            top: 35px;
        }

        #left ul {
            margin-top: 20px;
        }

        #left li {
            height: 27px;
            padding-left: 30px;
            padding-top: 5px;
            font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
            font-size: 14px;
            cursor: pointer;
        }

        #left .hover {
            background-color: #CBDBF7;
        }

        #left .active {
            background-color: #30A5FF;
        }

        #mainFrame {
            border: 0px;
            padding-bottom: 40px;
            background: #f0f3f8;
            border-left: 1px solid #868b91;
        }

        .dropdown-menu {
            left: inherit;
            right: 0;
        }

        .personal {
            padding-right: 0;
        }

        .personal .off {
            /*line-height: 45px;*/

        }

        #right {
            padding-left: 0;
        }

        a [class^="icon-"] {
            display: inline-block;
            margin: 0 auto;
        }

    </style>


    <!-- IE浏览器不兼容问题解决样式:开始 -->
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
        img {
            border: none;
        }

        a {
            text-decoration: none;
        }

        .sweet-alert {
            background-color: white;
            border: 2px solid #AEDEF4;
            font-family: 'Open Sans', sans-serif;
            width: 478px;
            padding: 17px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            text-align: center;
            position: fixed;
            left: 50%;
            top: 0;
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
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            padding: 10px 32px;
            margin: 26px 5px 0 5px;
            cursor: pointer;
        }

        .sweet-alert ul {
            list-style: none;
            width: 100%;
            padding-left: 0;
        }

        .sweet-alert ul li {
            float: left;
            width: 20%;
        }

        .sweet-alert ul li a {
            color: #666;

        }

        .sweet-alert ul li a > img {
            width: 40px;
            height: 40px;
        }

        div#myModal {
            z-index: -1;
        }


    </style>
</head>
<body>


<%--遮罩层--%>
<div id="mask" class="mask" onclick="hideMask()"></div>
<!-- IE浏览器不兼容问题解决div:开始 -->
<div id="cDiv" style="display:none">
    <div class="sweet-alert showSweetAlert visible" tabindex="-1" data-has-cancel-button="false"
         data-allow-ouside-click="false" data-has-done-function="false" data-timer="null" style="display: block;">
        <div class="icon error" style="display: none;"><span class="x-mark"><span class="line left"></span><span
                class="line right"></span></span></div>
        <div class="icon warning" style="display: none;"><span class="body"></span> <span class="dot"></span></div>
        <div class="icon info" style="display: none;"></div>
        <div class="icon success" style="display: none;"><span class="line tip"></span> <span class="line long"></span>

            <div class="placeholder"></div>
            <div class="fix"></div>
        </div>
        <div class="icon custom" style="display: none;"></div>
        <h2><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">检测到您的IE浏览器版本不兼容，建议更换浏览器，<br>点击<span
                style="color:red;"> 关闭 </span>后关闭此浏览器。</font></font></h2>

        <p style="display: block;"><font style="vertical-align: inherit;"><font
                style="vertical-align: inherit; font-size: 16px;color:#575757;font-weight: 600">建议浏览器：</font></font></p>

        <p>
        <ul style="padding:0; margin: 10px 0 0 40px; display: flex;">
            <li><a href="javascript:;"><img src="${ctxStatic}/images/ie-icon-img/360.png"/>

                <p>360浏览器</p></a></li>
            <li><a href="javascript:;"><img src="${ctxStatic}/images/ie-icon-img/Chrome.png"/>

                <p>Chrome浏览器</p></a></li>
            <li><a href="javascript:;"><img src="${ctxStatic}/images/ie-icon-img/Firefox.png"/>

                <p>Firefox浏览器</p></a></li>
            <li><a href="javascript:;"><img src="${ctxStatic}/images/ie-icon-img/QQ.png"/>

                <p>QQ浏览器</p></a></li>
        </ul>
        </p>
        <p>
            <button class="confirm" tabindex="1"
                    style="background-color: rgb(87,179, 223); box-shadow: rgba(174, 222, 244, 0.8) 0px 0px 2px, rgba(0, 0, 0, 0.0470588) 0px 0px 0px 1px inset;">
                <font style="vertical-align: inherit;"><font style="vertical-align: inherit;"> 关闭 </font></font>
            </button>
            <button class="confirm_false" tabindex="1"
                    style="background-color: rgb(221, 107, 85); box-shadow: rgba(221, 107, 85, 0.8) 0px 0px 2px, rgba(0, 0, 0, 0.0470588) 0px 0px 0px 1px inset;">
                取消
            </button>
        </p>
    </div>
</div>
<!-- IE浏览器不兼容问题解决div:结束 -->

<div id="main">

    <div id="moreMenu" class="mask">
        <div id="myCarousel" class="carousel slide" style="width: 550px;margin:0 auto;">
            <!-- 轮播（Carousel）项目 -->
            <div class="carousel-inner">
            </div>
        </div>
        <a class="carousel-control left" href="#myCarousel"
           data-slide="prev" style="float: right">&lsaquo;</a>
        <a class="carousel-control right" href="#myCarousel"
           data-slide="next">&rsaquo;</a>

    </div>
    <div id="menu">
        <header>
            <!-- 头部LOGO-->
            <div class="head_logo">
                <a href="javascript:;">
                    <img src="/static/img/logo.png" alt="OA办公系统" style="height: 70px"/>
                </a>
            </div>
            <div class="personal">

                <ul>
                    <li class="personal_photo">
                        <span style="color: #fff;width: 170%;line-height: 45px;">${fns:getUser().name}</span>
                    </li>

                    <li>
                        <a class="off" href="#" onclick="tuichu('/a')">
                            <img src="/static/img/guanbi.png" alt="关闭"/>
                        </a>
                    </li>
                </ul>
            </div>
        </header>
    </div>

    <div>
        <div id="left" style="width:300px; background-color: #F0F3F8; z-index: 999"></div>
        <div id="right" style="width:82%">
            <div class="bg_color">

            </div>
            <div id="main" style="height:100%;">
                <div class="main_right">
                    <%--<div class="main_top">--%>
                    <%--</div>--%>
                    <div class="main_bot">
                        <div class="main_bot_top">
                            <ul>
                                <li>

                                </li>
                                <li>
                                </li>
                                <li>
                                </li>
                            </ul>
                        </div>
                        <iframe id="mainFrame" name="mainFrame" src="" style="position: fixed; width: 100%" scrolling="yes"
                                frameborder="yes"
                                width="82%" height="100%"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:set var="user" value="${user}"></c:set>
    <form id="loginForm" class="form-signin" action="${ctx}/login" method="post" style="display: none">
        <input type="text" id="company" name="company" class="input-block-level required">
        <input type="text" id="username" name="username" class="input-block-level required" value="${user.cardNo}">
        <input type="text" id="companyId" name="companyId" class="input-block-level required">
        <input type="password" id="password" name="password" class="input-block-level required" value="admin">
    </form>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         align="center">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        上传头像
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <div class="form-group">
                            <input type="file" name="file" id="myfile"/>
                        </div>
                    </div>
                </div>

            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>

</div>
<div id="pop" style="display:none;">
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        #pop {
            background: #fff;
            width: 260px;
            border: 1px solid #e0e0e0;
            font-size: 12px;
            position: fixed;
            right: 10px;
            bottom: 10px;
        }

        #popHead {
            line-height: 32px;
            background: #f6f0f3;
            border-bottom: 1px solid #e0e0e0;
            position: relative;
            font-size: 12px;
            padding: 0 0 0 10px;
        }

        #popHead h2 {
            font-size: 14px;
            color: #30A5FF;
            line-height: 32px;
            height: 32px;
        }

        #popHead #popClose {
            position: absolute;
            right: 10px;
            top: 1px;
        }

        #popHead a#popClose:hover {
            color: #f00;
            cursor: pointer;
        }

        #popContent {
            padding: 5px 10px;
        }

        #popTitle a {
            line-height: 24px;
            font-size: 14px;
            font-family: '微软雅黑';
            color: #333;
            font-weight: bold;
            text-decoration: none;
        }

        #popTitle a:hover {
            color: #30A5FF;
        }

        #popIntro {
            text-indent: 24px;
            line-height: 160%;
            margin: 5px 0;
            color: #666;
        }

        #popMore {
            text-align: right;
            border-top: 1px dotted #ccc;
            line-height: 24px;
            margin: 8px 0 0 0;
        }

        #popMore a {
            color: #f60;
        }

        #popMore a:hover {
            color: #f00;
        }
    </style>
    <div id="popHead">
        <a id="popClose" title="关闭">关闭</a>

        <h2 style="color: dodgerblue">温馨提示</h2>
    </div>
    <div id="popContent">
        <dl>
            <dt id="popTitle"><a href="http://yanue.info/" target="_blank">这里是参数</a></dt>
            <dd id="popIntro">这里是内容简介</dd>
        </dl>
        <%--  <p id="popMore"><a href="" target="_blank">查看 »</a></p>--%>
    </div>
</div>
<script>
    var menuObj = $("#menu");
    var headerObj = $("#header");
    var frameObj = $("#left, #right, #right iframe");
    $('.mask').css('top', headerObj.height() + menuObj.height() + 3)
    $(window).resize(function () {
        wSize()
    });

    function switchOrg(companyId, id) {
        location.href = "/a/login?id=" + id;
    }
    <!-- 上传头像开始操作-->
    function toPic() {
        $('#myModal').modal('show');
    }


    function IsRepeat(obj) {
        var filters = "jpg png bmp jpeg";
        var val = obj;
        var arr = val.split('.');
        if (filters.indexOf(arr[arr.length - 1]) < 0) {
            $("#myfile").val("");
            alert("请上传 " + filters + " 格式的图片", "提示");
            return;
        }
    }


    function ajaxFileUpload() {
        $.ajaxFileUpload
        ({
            url: '${ctx}/sys/user/saveContractFilePC', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'myfile', //文件上传域的ID
            dataType: 'text', //返回值类型 一般设置为json
            success: function (data, file, status) //服务器成功响应处理函数
            {
                var url = data.responseText;
                var reStr = url.slice(url.indexOf(">/") + 1, url.indexOf("</"));
                if (reStr.indexOf("static") > 0) {
                    $('#myModal').modal('hide');
                    alert("上传头像成功！")
                    document.getElementById('userPhoto').src = reStr;
                }
            },
            error: function (data, xml, status, e)//服务器响应失败处理函数
            {
                var url = data.responseText;
                var reStr = url.slice(url.indexOf(">/") + 1, url.indexOf("</"));
                if (reStr.indexOf("static") > 0) {
                    $('#myModal').modal('hide');
                    alert("上传头像成功！")
                    document.getElementById('userPhoto').src = reStr;
                }
            }
        })
        return false;
    }

    function wSize() {
        var strs = getWindowSize().toString().split(",");
        $('#right').css('width', strs[1] - $('#left').width());
        $('#mainFrame').css('width', strs[1] - $('#left').width());
        frameObj.css('height', strs[0] - headerObj.height() - menuObj.height() - 3);

        $('#myModal').css({'left': (strs[1] - $('#myModal').width()) / 2})
    }
    function getWindowSize() {
        return ["Height", "Width"].map(function (a) {
            return window["inner" + a] || document.compatMode === "CSS1Compat" && document.documentElement["client" + a] || document.body["client" + a]
        })
    }

    //显示遮罩层
    function showMask() {
        $("#mask").css("height", $('#left').height() + 5);
        $("#mask").css("width", $(document).width());
        $("#mask").show();
    }
    //隐藏遮罩层
    function hideMask() {
        $("#mask").hide();
        $("#moreMenu").slideUp("fast");
    }

    function initMuen() {
        $.cookie('levelMenu1', $('.topMenuText', this).html());
        if ($(this).hasClass('moreMenu')) {
            if ($('#mask').css('display') == 'none') {
                $("#moreMenu").slideDown("fast");
                showMask()
            } else {
                hideMask()
            }
            return false
        }
        if ($('#mask').css('display') == 'block') {
            hideMask()
        }
        $.get('/a/sys/menu/tree?parentId=1', function (data) {
            if (data.indexOf("id=\"loginForm\"") != -1) {
                alert('未登录或登录超时。请重新登录，谢谢！');
                top.location = "${ctx}";
                return false;
            }
            $("#left").empty();
            $("#left").append(data);
            $('#left .menu2').css({fontSize: '12px', paddingLeft: '48px'})

            $('#left a').click(function (e) {
                var thisLi = $(this).parent('li')
                if (thisLi.hasClass('menu1') && thisLi.next().hasClass('parent-' + thisLi.attr('data-id'))) {
                    thisLi.next().click()
                    return false
                }
                $('#left li').removeClass("active")
                $(this).parent('li').addClass("active")
                $('#left a').css({color: '#555'})
                $(this).css({color: '#fff'})
                $.cookie('levelMenu2', $('span', this).html())
                $.cookie('levelMenu2Href', $(this).attr('href'))
                e.stopPropagation()
            })
            $('#left li').mouseover(function () {
                $('#left li').removeClass("hover")
                $(this).addClass("hover")
            })
            $('#left li').mouseout(function () {
                $(this).removeClass("hover")
            })
            $('#left li').click(function (e) {
                $('#left li').removeClass("active")
                $(this).addClass("active")
                $('a', this)[0].click()
            })
            $('#left li:eq(0)').click()
        });
    }

    function tuichu(url) {
        $.get("/a/logMyOA", function (data) {
            if (data == true) {
                window.location = url;
            }
        })
    }

    $(function () {
        initMuen()
        $('.main_right').css('position', 'relative');

        $("#btnSubmit").on("click", function () {
            var url = $("#myfile").val();
            IsRepeat(url);
            ajaxFileUpload();
        });
        wSize()
    })


</script>
</body>
</html>