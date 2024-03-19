<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>智慧岗位工作平台</title>
    <meta charset="UTF-8" />
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
    <!-- iOS 设备 begin -->
    <script ></script>
    <meta name="apple-mobile-web-app-title" content="标题">
    <!-- 添加到主屏后的标题（iOS 6 新增） -->
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <!-- 是否启用 WebApp 全屏模式，删除苹果默认的工具栏和菜单栏 -->

    <meta name="apple-itunes-app" content="app-id=myAppStoreID, affiliate-data=myAffiliateData, app-argument=myURL">
    <!-- 添加智能 App 广告条 Smart App Banner（iOS 6+ Safari） -->
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <!-- 设置苹果工具栏颜色 -->
    <meta name="format-detection" content="telphone=no, email=no"/>
    <!-- 忽略页面中的数字识别为电话，忽略email识别 -->
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
    <!-- windows phone 点击无高光 -->
    <meta name="msapplication-tap-highlight" content="no">
    <!-- iOS 图标 begin -->
    <link rel="apple-touch-icon-precomposed" href="/apple-touch-icon-57x57-precomposed.png"/>
    <!-- iPhone 和 iTouch，默认 57x57 像素，必须有 -->
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="/apple-touch-icon-114x114-precomposed.png"/>
    <!-- Retina iPhone 和 Retina iTouch，114x114 像素，可以没有，但推荐有 -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="/apple-touch-icon-144x144-precomposed.png"/>
    <!-- Retina iPad，144x144 像素，可以没有，但推荐有 -->
    <!-- iOS 图标 end -->

    <!-- iOS 启动画面 begin -->
    <link rel="apple-touch-startup-image" sizes="768x1004" href="/splash-screen-768x1004.png"/>
    <!-- iPad 竖屏 768 x 1004（标准分辨率） -->
    <link rel="apple-touch-startup-image" sizes="1536x2008" href="/splash-screen-1536x2008.png"/>
    <!-- iPad 竖屏 1536x2008（Retina） -->
    <link rel="apple-touch-startup-image" sizes="1024x748" href="/Default-Portrait-1024x748.png"/>
    <!-- iPad 横屏 1024x748（标准分辨率） -->
    <link rel="apple-touch-startup-image" sizes="2048x1496" href="/splash-screen-2048x1496.png"/>
    <!-- iPad 横屏 2048x1496（Retina） -->

    <link rel="apple-touch-startup-image" href="/splash-screen-320x480.png"/>
    <!-- iPhone/iPod Touch 竖屏 320x480 (标准分辨率) -->
    <link rel="apple-touch-startup-image" sizes="640x960" href="/splash-screen-640x960.png"/>
    <!-- iPhone/iPod Touch 竖屏 640x960 (Retina) -->
    <link rel="apple-touch-startup-image" sizes="640x1136" href="/splash-screen-640x1136.png"/>
    <!-- iPhone 5/iPod Touch 5 竖屏 640x1136 (Retina) -->
    <!-- iOS 启动画面 end -->

    <!-- iOS 设备 end -->
    <!-- iOS 设备 end -->
    <style>
        .changeOrg {
            font-size: 14px;
            font-weight: bold;
            text-align: left;
            padding: 0 0 0 2rem;
        }
    </style>
</head>
<body>
<div class="me_top">
    <c:set var="user" value="${user}"/>
    <div class="me_top_2">
        <div class="new-update clearfix"><i>
            <c:if test="${user.photo==null || user.photo==''}" >
                <img class="img-circle img-polaroid" style="width: 50px;height: 50px;" src="${ctxStatic}/oaApp/img/photoDefault.jpg">
            </c:if>
            <c:if test="${user.photo!=null && user.photo!=''}">
                <img style="width:50px;height: 50px;" class="img-circle img-polaroid" src="${user.photo}">
            </c:if> </i>
            <div class="update-done"><a title="" href="#"><strong class="fontS_16">${user.name}</strong></a>
                <span>
                    <c:if test="${not empty user.workPost}">
                        ${fns:getDictLabel(user.workPost,"WORK_POST_DICT" ,"")}
                    </c:if>
                        <c:if test="${empty user.workPost}">
                            <c:out value="${user.role.name}"/>
                        </c:if>
                </span></div>
        </div>
    </div>

</div>


<div class="span6 pad_B">
    <div class="widget-box bor_N collapsible">
        <div class="widget-title bg_lo bgC_White" >
            <a class="OA_a"  href="${ctx}/sys/menu/tree?parentId=c8e2ec7ac7e6486abb8998c0dfb85669" >
                <span class="icon"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_1.png"> </i> </span>
                <h5>内部邮箱</h5>
            </a>
        </div>


    </div>
</div>
<div class="span6 pad_B">
    <div class="widget-box bor_N ">
        <div class="widget-title bg_lo bgC_White">
            <a class="OA_a"  href="${ctx}/sys/user/info" >
                <span class="icon"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me2.png"> </i> </span>
                <h5>个人信息</h5>
            </a>
        </div>

    </div>

    <div class="widget-box bor_N marT_0 ">
        <div class="widget-title bg_lo bgC_White"><a class="OA_a"  href="${ctx}/sys/user/modifyPwd" >
            <span class="icon"><i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_3.png"> </i> </span>
            <h5>修改密码</h5>
        </a>
        </div>

    </div>
    <div class="widget-box bor_N marT_0 ">
        <div class="widget-title bg_lo bgC_White"><a class="OA_a" href="${ctx}/oa/personSigns" >
            <span class="icon"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_4.png"> </i> </span>
            <h5>个人签名</h5>
        </a>
        </div>

    </div>




</div>
<div class="span6 pad_B">
    <div class="widget-box bor_N ">
        <div class="widget-title bg_lo bgC_White"><a class="OA_a" href="${ctx}/act/task/todo" >
            <span class="icon"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_5.png"> </i> </span>
            <h5>待办流程</h5>
            <div class="pull-right mysely2"></div>
        </a>
        </div>

    </div>
    <div class="widget-box bor_N marT_0 ">
        <div class="widget-title bg_lo bgC_White"><a class="OA_a"  href="${ctx}/act/task/historicList" >
            <span class="icon"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_6.png"> </i> </span>
            <h5>已办任务</h5>
            <div class="pull-right mysely2"></div>
        </a>
        </div>

    </div>
    <div class="widget-box bor_N marT_0 ">
        <div class="widget-title bg_lo bgC_White"><a class="OA_a"  href="${ctx}/oa/flow/flowList" >
            <span class="icon"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_7.png"> </i></span>
            <h5>已发起流程</h5>
            <div class="pull-right mysely2"></div>
        </a>
        </div>
    </div>
    <div class="widget-box bor_N marT_0 ">
        <div class="widget-title bg_lo bgC_White"><a class="OA_a"  href="${ctx}/process/customProcess/list" >
            <span class="icon"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_7.png"> </i></span>
            <h5>自定义流程</h5>
            <div class="pull-right mysely2"></div>
        </a>
        </div>
    </div>
</div>
<!--切换机构-->
<c:if test="${fns:getCompanyList().size() > 1}">
    <div class="quit">
        <div class="widget-box bor_N collapsible">
            <c:forEach items="${fns:getCompanyList()}" var="company">
                <c:if test="${empty company.hrmFlag}">
                    <input type="hidden" id="hrmId" value="${company.id}">
                </c:if>
                <a class="quit_btn" href="#" onclick="switchOrg('${company.company.id}','${company.id}')">
                    <div class="widget-title bg_lo bgC_White changeOrg" >
                        <!-- <i class="icon-refresh"></i>切换机构:${company.companyId} -->
                        <span class="icon" style="padding:0"> <i><img class="imgS"  src="${ctxStatic}/oaApp/img/me_10.png"> </i></span>
<%--                        <h5>切换机构:${company.companyId}</h5>--%>
                        <div class="pull-right mysely2"></div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
</c:if>
<!---->
<!--退出登录-->
<div class="span6 quit">
    <div class="widget-box bor_N collapsible">
        <a class="quit_btn" href="${ctx}/logMy" >
            <div class="widget-title bg_lo bgC_White" >
                退出登录
            </div>
        </a>
    </div>
</div>
<!---->



<!--end-Footer-part-->


<!--end-尾部-part-->


<script type="text/javascript">
    function goPage (newURL) {
        if (newURL != "") {
            if (newURL == "-" ) {
                resetMenu();
            }
            else {
                document.location.href = newURL;
            }
        }
    }
    function resetMenu() {
        document.gomenu.selector.selectedIndex = 2;
    }
    function switchOrg(companyId, id) {
        location.href = "/a/login?id=" + id;
    }
</script>
</body>
</html>
