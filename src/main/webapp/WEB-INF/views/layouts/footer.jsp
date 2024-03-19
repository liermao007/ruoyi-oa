<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
    <title><sitemesh:title/> - Powered By JeeSite</title>
    <%@include file="/WEB-INF/views/layouts/oaHead.jsp" %>
    <!-- Baidu tongji analytics --><script>var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="//hm.baidu.com/hm.js?82116c626a8d504a5c0675073362ef6f";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s);})();</script>
    <sitemesh:head/>
</head>
<body>
<sitemesh:body/>

<script type="text/javascript">//<!-- 无框架时，左上角显示菜单图标按钮。
var htmlIn = '<div id="footer"><footer>'+
        '<a href="${ctx}/login" id="shouye" class="foot_Btn">'+
        '<i class="active"></i>'+
        '<span class="active">首页</span>'+
        '</a>'+
        '<a href="javascript:;" id="xiaoxi" class="foot_Btn">'+
        '<i></i>'+
        '<span>消息</span>'+
        '</a>'+
        '<a href="javascript:;" id="fabu">'+

        '<span>发布</span>'+
        '</a>'+
        '<a href="${ctx}/sys/user/phone" id="lianxiren" class="foot_Btn">'+
        '<i></i>'+
        '<span>联系人</span>'+
        '</a>'+
        '<a href="${ctx}/sys/user/myPhone" id="wode" class="foot_Btn">'+
        '<i></i>'+
        '<span>我的</span>'+
        '</a>'+
        '<div class="none_bg"></div>'+
        '<div class="none_1_bg"></div>'+
        '<ul>'+
        '<li><a href="${ctx}/oa/mailInfo/none"> 邮件</a></li>'+
        '<li><a href="${ctx}/oa/oaNotify/form" >通知</a></li>'+
        '<li><a href="${ctx}/sys/menu/apply">申请</a>'+
        '</li>'+
        '</ul>'+
        '</footer></div>';
$("body").append(htmlIn);

</script>
<script>
    $(function(){
        var n=window.sessionStorage.getItem("num");
        $(".foot_Btn").eq(n).children().addClass("active").parents().siblings('a').children().removeClass("active");
        $(".foot_Btn").on("click",function(){
            var num=$(this).index()-1;
            if(num<0){num=0}else if(num==0){num=1}
            window.sessionStorage.setItem("num",num);
        });
    })


</script>
</body>
</html>