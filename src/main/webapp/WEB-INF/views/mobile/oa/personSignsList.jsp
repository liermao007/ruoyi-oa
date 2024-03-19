<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>个人签名管理</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
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

    <h3 >个人签名</h3>


</div>

<form:form id="searchForm" modelAttribute="personSigns" action="${ctx}/oa/oa/personSigns/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <div align="center" style="margin: 10px">
        <c:if test="${empty page.list}">
               <a href="${ctx}/oa/personSigns/form" style="width: 260px;" role="button" class="btn btn-primary">添加</a>
        </c:if>
    </div>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>姓名</th>
        <th>签章名称</th>
        <th>签章图片</th>
        <shiro:hasPermission name="oa:personSigns:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>

    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="personSigns">
        <%--<c:if test=" ${not empty personSigns.name}">--%>
        <tr>
        <td><a href="${ctx}/oa/personSigns/form?id=${personSigns.id}">
                ${personSigns.name}
        </a></td>
        <td>
                ${personSigns.signName}
        </td>
        <td>
            <img style="width: 90px;height: 60px" src="${personSigns.signUrl}"/>
        </td>
        <shiro:hasPermission name="oa:personSigns:edit">
            <td>
                <a href="${ctx}/oa/personSigns/form?id=${personSigns.id}">修改</a>
                <a href="${ctx}/oa/personSigns/delete?id=${personSigns.id}"
                   onclick="return confirm('确认要删除该个人签名吗？')">删除</a>
            </td>
        </shiro:hasPermission>
    </tr>
        <%--</c:if>--%>
    </c:forEach>
    </tbody>
</table>
</body>
</html>