<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
	<title>我发起的流程</title>
    <meta name="decorator" content="footer"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css" />
    <script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
    
    <%--<style type="text/css">--%>
        <%--a:hover{--%>
            <%--color: red;--%>
            <%--text-decoration: none;--%>
        <%--}--%>
        <%--#liStyle{--%>
            <%--float: left;--%>
            <%--width: 25%;--%>
            <%--border: 1px solid #dbdbdb;--%>
            <%--box-sizing:--%>
            <%--border-box;padding: 5px 0px;--%>
        <%--}--%>
    <%--</style>--%>
</head>
<body>
<div class="title_Infor">
    <a href="${ctx}/sys/user/myPhone" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>
    <h3 > 我发起的流程</h3>
</div>
<%--<ul class="breadcrumb">--%>
    <%--<li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>--%>
    <%--<li id="levelMenu2" class="active"></li>--%>
<%--</ul>--%>
<div class="span6">
    <div class="widget-content " >
	<sys:message content="${message}"/>
        <c:forEach items="${page.list}" var="oaPersonDefineTable">
            <div class="new-update clearfix"><i class="icon-ok-sign marT_5"></i>
                <div class="update-done"><a title="" href="${ctx}/oa/flow/myFlow?procDefId=${oaPersonDefineTable.procDefId}"><strong>${oaPersonDefineTable.tableName}</strong></a>
                </div>
            </div>
        </c:forEach>
        <c:if test="${fns:findCustomProcess().size()>0}">
            <div class="new-update clearfix"><i class="icon-ok-sign marT_5"></i>
                <div class="update-done"><a title="" href="${ctx}/process/customProcess/form"><strong>自定义流程</strong></a>
                </div>
            </div>
        </c:if>
        <div class="select_All">
    </div>
        </div>
    </div>
<script type="text/javascript">
    $(function(){
        $('.new-update').each(function(a){
            $(this).on("click",function(){
                var url =  $(this).find("a").eq(0).attr("href");
//              alert(url)
                window.location.href=url;

            });
        });
    });

</script>
</body>

</html>