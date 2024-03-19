<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻公告管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
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

<form:form id="searchForm" modelAttribute="oaNews" action="${ctx}/oa/collectoaNews/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>标题：</label>
            <form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
        </li>
        <li><label>审核状态：</label>
            <form:select path="auditFlag" class="input-medium">
                <form:option value="" label=""/>
                <form:option value="0" label="待审核"/>
                <form:option value="1" label="已发布"/>
                <form:option value="2" label="拒绝发布"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary " type="submit" value="查询"/></li>

        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>标题</th>
        <th>发布人</th>
        <th>审批人</th>
        <th>发布时间</th>
        <th>发布状态</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="oaNews">
        <tr>
            <td><a href="${ctx}/oa/collectoaNews/form?id=${oaNews.id}">
                ${oaNews.title}
            </a></td>
            <td>
                ${oaNews.createManName}
            </td>
            <td>
                ${oaNews.auditManName}
            </td>
            <td>
                <fmt:formatDate value="${oaNews.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <c:if test="${oaNews.auditFlag eq '1'}">
                    已发布
                </c:if>
                <c:if test="${oaNews.auditFlag eq '0'}">
                    待审核
                </c:if>
                <c:if test="${oaNews.auditFlag eq '2'}">
                    拒绝发布
                </c:if>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>