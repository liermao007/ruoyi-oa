<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
    <title><sitemesh:title/></title>
    <%@include file="/WEB-INF/views/layouts/oaHead.jsp" %>
    <script>var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="//hm.baidu.com/hm.js?82116c626a8d504a5c0675073362ef6f";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s);})();</script>
    <sitemesh:head/>
</head>

<body>
<sitemesh:body/>

<script type="text/javascript">
    var htmlIn = '<div id="footer"><footer>'+
            '<a href="${ctx}/login" id="shouye" class="foot_Btn">'+
            '<i class="active"></i>'+
            '<span class="active">首页</span>'+
            '</a>'+
            '<a href="javascript:;" id="xiaoxi" class="foot_Btn">'+
            '<i></i>'+
            '<span>办公</span>'+
            '</a>'+
            '<a class="menu">'+
    '<div class="menu-wrapper">'+
    '<ul class="menu-items">'+
    '<li class="menu-item">'+
    '<a href="${ctx}/oa/mailInfo/none" class="menu-item-button foot_Btn" style="transform: rotateZ(238deg)">邮件</a>'+
            ' <div class="menu-item-bounce"></div>'+
            '</li>'+



    '<li class="menu-item">'+
    '<a href="${ctx}/oa/oaNews/form" class="menu-item-button" style="transform: rotateZ(182deg)">通知 </a>'+
            '<div class="menu-item-bounce"></div>'+
       '</li>'+



   '<li class="menu-item">'+
    '<a href="${ctx}/sys/menu/apply" class="menu-item-button" style="transform: rotateZ(120deg)">申请</a>'+



    '<div class="menu-item-bounce"></div>'+
    '</li>'+
    '</ul>'+
    '<button class="menu-toggle-button">发布</button>'+



    '</div>'+

    '</a>'+
     '<img class="footer_bg_cicle" src="/static/oaApp/img/yangfanstyle.png"/>'+
            '<a href="${ctx}/sys/user/mobile" id="lianxiren" class="foot_Btn">'+
            '<i></i>'+
            '<span>联系人</span>'+
            '</a>'+
            '<a href="${ctx}/sys/user/myPhone" id="wode" class="foot_Btn">'+
            '<i></i>'+
            '<span>我的</span>'+
            '</a>'+
            '</footer></div>';
    $("body").append(htmlIn);


</script>
<script>
    $(function(){
        var n=window.sessionStorage.getItem("num");

        $(".foot_Btn").eq(n).children().addClass("active").parents().siblings('a').children().removeClass("active");
        $(".foot_Btn").on("click",function(){
            var num=$(this).index()-1;

            if(num<0){num=0}else if(num==1||num==2){num=0}else if(num==3){num=2}else if(num==4){num=3}else if(num==5){num=4}
            window.sessionStorage.setItem("num",num);
        });
        sessionStorage.clear();
    })
</script>
</body>
</html>