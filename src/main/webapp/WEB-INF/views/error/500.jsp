<%
response.setStatus(500);

// 获取异常类
Throwable ex = Exceptions.getThrowable(request);
if (ex != null){
	LoggerFactory.getLogger("500.jsp").error(ex.getMessage(), ex);
}

// 编译错误信息
StringBuilder sb = new StringBuilder("错误信息：\n");
    String task = "";
if (ex != null) {
	sb.append(Exceptions.getStackTraceAsString(ex));
    if(Exceptions.getStackTraceAsString(ex).startsWith("Cannot find task with id")){
        task = "1";
    }
} else {
	sb.append("未知错误.\n\n");
}

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print(sb);
}

// 输出异常信息页面
else {
%>
<%@page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@page import="com.thinkgem.jeesite.common.web.Servlets"%>
<%@page import="com.thinkgem.jeesite.common.utils.Exceptions"%>
<%@page import="com.thinkgem.jeesite.common.utils.StringUtils"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统错误</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1>系统错误.</h1></div>
		<div class="errorMessage">
			<span style="font-size: 20px;">错误信息：</span><span style="color: red; font-size: 20px;"><%=ex==null?"未知错误.":StringUtils.toHtml(ex.getMessage())%></span> <br/> <br/>
			请点击“查看详细信息”按钮，将详细错误信息发送给系统管理员，谢谢！<br/> <br/>
			<a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a> &nbsp;
			<%--<a href="javascript:" onclick="history.go(-2);" class="btn" hidden="hidden" id="task">返回上二页</a> &nbsp;--%>
			<a href="javascript:" onclick="$('.errorMessage').toggle();" class="btn">查看详细信息</a>
		</div>
		<div class="errorMessage hide">
			<%=StringUtils.toHtml(sb.toString())%> <br/>
			<a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a> &nbsp;
			<a href="javascript:" onclick="$('.errorMessage').toggle();" class="btn">隐藏详细信息</a>
			<br/> <br/>
		</div>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
<script>
    if("<%=task%>"=="1"){
        window.location = history.go(-2);
    }

</script>
</body>
</html>
<%
} out = pageContext.pushBody();
%>