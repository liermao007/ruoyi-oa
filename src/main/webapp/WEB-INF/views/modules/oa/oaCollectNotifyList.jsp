<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>通知管理</title>
    <meta name="decorator" content="default"/>
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
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<form:form id="searchForm" modelAttribute="oaNotify" action="${ctx}/oa/collectoaNotify/${oaNotify.self?'self':''}" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>标题：</label>
            <form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
        </li>
        <li><label>类型：</label>
            <form:select path="type" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <c:if test="${!requestScope.oaNotify.self}"><li><label>状态：</label>
            <form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
        </li></c:if>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>



        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>标题</th>
        <th>类型</th>
        <th>状态</th>
        <th>查阅状态</th>
        <th>更新时间</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="oaNotify">
        <tr>
            <td><a href="${ctx}/oa/collectoaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}">
                ${fns:abbr(oaNotify.title,50)}
            </a></td>
            <td>
                ${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}
            </td>
            <td>
                ${fns:getDictLabel(oaNotify.status, 'oa_notify_status', '')}
            </td>
            <td>
                <c:if test="${requestScope.oaNotify.self}">
                    ${fns:getDictLabel(oaNotify.readFlag, 'oa_notify_read', '')}
                </c:if>
                <c:if test="${!requestScope.oaNotify.self}">
                    ${oaNotify.readNum} / ${oaNotify.readNum + oaNotify.unReadNum}
                </c:if>
            </td>
            <td>
                <fmt:formatDate value="${oaNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>