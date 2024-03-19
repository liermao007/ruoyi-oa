<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="robots" content="none">
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
</head>


<body>
<script type="text/javascript">
    function proxyLogin(askurl, askData, okurl) {
        var killAjax = true;
        var ajaxCall = jQuery.getJSON(askurl + "?callback=?", {askData:askData}, function(d){
            killAjax = false;
            if(d.msg == "-1"){
               // window.location.href = "http://sso.test.com:8080/login.html?ReturnURL=http%3A%2F%2Fmy.web.com%3A8090%2Fproxylogin.html";
            }else{
                window.location.href =okurl+"?replyTxt="+d.msg;
//                $.post(okurl, {replyTxt:d.msg} , function(e) {
//
//                }, "json");
            }
        });
    }
    proxyLogin("${askurl}", "${askData}", "${okurl}");
</script>
<div align="center" style="margin-top: 180px;">
    <img src="/static/images/loading.gif"> 页面正在加载中，请稍候……
</div>
</body>
</html>
