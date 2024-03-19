<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡查处理记录管理</title>
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
	<form:form id="searchForm" modelAttribute="oaXccl" action="${ctx}/xc/oaXccl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

		<ul class="ul-form">
			<li><label>部门：</label><sys:treeselect id="office" name="tbks" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
														title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
			<li><label>巡查人：</label><form:input path="xcr" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>科室</th>
				<th>巡查人</th>
				<th>巡查日期</th>
				<th>巡查项目</th>
				<th>项目备注</th>
				<th>处理意见</th>
				<th>处理人</th>
				<th>处理时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaXccl">
			<tr>
				<td>${oaXccl.tbks}</td>
				<td>${oaXccl.xcr}</td>
				<td>${oaXccl.xcrq}</td>
				<td>${oaXccl.recordProject}</td>
				<td>${oaXccl.recordRemark}</td>
				<td>${oaXccl.handleOpinion}</td>
				<td>${oaXccl.handleUsername}</td>
				<td>${oaXccl.handleTime}</td>
				<td>
    				<a href="${ctx}/xc/oaXccl/form?id=${oaXccl.id}">处理</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>