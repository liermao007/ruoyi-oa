<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>已办任务</title>
    <meta name="decorator" content="footer"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css" />
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
    <h3 >已办任务</h3>
</div>

<div class="span6">
    <div class="widget-content " >
        <div class="new-update clearfix">
            <div class="update-done">
                <a class="Ahref"  href="${ctx}/act/task/toDoTasks" ><strong style="color: red">最近已办</strong></a>
            </div>
        </div>
        <sys:message content="${message}"/>
        <c:forEach items="${page.list}" var="oaPersonDefineTable">
            <div class="new-update clearfix">
                <div class="update-done">
                    <a class="Ahref"  href="${ctx}/act/task/myHistoricList?procDefId=${oaPersonDefineTable.id}"><strong> ${oaPersonDefineTable.tableComment}</strong></a>
                </div>
            </div>
        </c:forEach>
        <c:if test="${fns:findCustomHiTaskinst().size()>0}">
            <div class="new-update clearfix">
                <div class="update-done">
                    <a class="Ahref"  href="${ctx}/process/customProcess/historyList"><strong>自定义流程</strong></a>
                </div>
            </div>
        </c:if>
        <c:if test="${empty page.list}">
            <div class="daiban_Con11">
                <div>
                    <img src="${ctxStatic}/oaApp/img/daibanliucheng.png"/>
                    <span><font size="5" >您暂时还没有已办流程，休息一下吧！</font></span>
                </div>
            </div>
        </c:if>
        <div class="select_All">
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function(){
        $('.new-update').each(function(i){
            $(this).on("click",function(){
                var url =   $('.new-update').eq(i).find("a").attr("href");
//                alert(i)
                window.location.href=url;

            });
        });
    });

</script>

</body>
</html>
