<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>个人签名管理</title>
    <meta name="decorator" content="default"/>
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
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>

<form:form id="searchForm" modelAttribute="personSigns" action="${ctx}/oa/oa/personSigns/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <c:if test="${empty page.list}">
        <ul class="ul-form">                 .
            <li class="btns"><a href="${ctx}/oa/personSigns/form" role="button" class="btn btn-primary">添加</a></li>
            <li class="clearfix"></li>
        </ul>
    </c:if>

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
                       onclick="return confirmx('确认要删除该个人签名吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
        <%--</c:if>--%>
    </c:forEach>
    </tbody>
</table>
<div class="pagination" align="center">${page}</div>
</body>
</html>