<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评阅管理</title>
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
	<form:form id="searchForm" modelAttribute="oaNews" action="${ctx}/oa/oaSummaryPermission/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <shiro:hasPermission name="oa:oaNews:edit">
			    <li class="btns"><a href="${ctx}/oa/oaSummaryPermission/form" role="button" class="btn btn-primary">添加</a></li>
            </shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>评阅人</th>
				<th>被评阅人</th>
				<shiro:hasPermission name="oa:oaSummaryPermission:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
        <c:forEach items="${page.list}" var="oaSummaryPermission" begin="0" end="19">
			<tr>
                <td>
                        ${oaSummaryPermission.evaluateByNames}
                    </a></td>
                <td >
                        ${oaSummaryPermission.evaluateName}
                </td>

				<shiro:hasPermission name="oa:oaSummaryPermission:edit"><td style="width: 7%;text-align: center" >
    				<a href="${ctx}/oa/oaSummaryPermission/form?evaluateById=${oaSummaryPermission.evaluateById}&evaluateId=${oaSummaryPermission.evaluateId}&evaluateByNames=${oaSummaryPermission.evaluateByNames}&evaluateName=${oaSummaryPermission.evaluateName}">修改</a>
    				<a href="javascript:void(0);" onclick="deleteByEvaluate('${oaSummaryPermission.evaluateById}')">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<script type="text/javascript">
    function deleteByEvaluate(evaluateById){
        var msg = "您真的确定要删除吗？";
        if (confirm(msg)==true){
            window.location="${ctx}/oa/oaSummaryPermission/delete?evaluateById="+evaluateById;
        }else{
            return false;
        }
    }
</script>
</body>
</html>