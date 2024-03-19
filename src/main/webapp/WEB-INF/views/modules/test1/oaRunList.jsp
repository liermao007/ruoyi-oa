<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>待办流程</title>
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
<form:form id="searchForm" modelAttribute="oaNews" action="${ctx}/test1/oaRunProcess" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <shiro:hasPermission name="oa:oaNews:edit">
            <li class="btns"><a href="${ctx}/test1/oaRunProcess/form" role="button" class="btn btn-primary">添加</a></li>
        </shiro:hasPermission>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>

        <th>代理人</th>
        <th>被代理人</th>
        <shiro:hasPermission name="test1:oaRunProcess:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="oaRunProcess" begin="0" end="19">
        <tr>

            <td>

                    ${fns:getNameByID(oaRunProcess.agentid)}
            </td>
            <td>
                    <%--${oaRunProcess.principalid}--%>
                    ${fns:getNameByID(oaRunProcess.principalid)}
            </td>
            <shiro:hasPermission name="test1:oaRunProcess:edit"><td>
                <a href="${ctx}/test1/oaRunProcess/form?principalid=${oaRunProcess.principalid}&agentid=${oaRunProcess.agentid}&principal=${oaRunProcess.principal}&agent=${oaRunProcess.agent}&byFlag=1">修改</a>
                <a href="${ctx}/test1/oaRunProcess/delete?agentid=${oaRunProcess.agentid}&byFlag=1">删除</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>