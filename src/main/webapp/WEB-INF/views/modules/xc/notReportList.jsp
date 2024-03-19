<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>未上报科室列表</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<ul class="breadcrumb">
	<li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
	<li id="levelMenu2" class="active"></li>
</ul>
	<form:form id="searchForm" action="${ctx}/xc/oaXccl/notReportDeptList" method="post" class="breadcrumb form-search">
		<div>
			<label style="font-size: 12px;">巡查日期：</label>
			<input id="queryDate"  name="queryDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:100px;"
				   value="${queryDate}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
			&nbsp;<input id="btnSubmit" style="width: 55px;" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>科室编码</th>
				<th>科室名称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="office">
			<tr>
				<td>${office.code}</td>
				<td>${office.name}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>