<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程信息汇管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#procDefId option[value=${actCountInfo.procDefId}]").prop("selected", "selected");
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
		<li class="active"><a href="${ctx}/sys/actCountInfo/">流程信息汇列表</a></li>
		<shiro:hasPermission name="sys:actCountInfo:edit"><li><a href="${ctx}/sys/actCountInfo/form">流程信息汇添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="actCountInfo" action="${ctx}/sys/actCountInfo?procdefId=${actCountInfo.procDefId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<%--	<li><label>流程shi'li：</label>
				<form:input path="procInstId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>proc_def_id：</label>
				<form:input path="procDefId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>--%>
			<%--<li><label>表单名称：</label>
				<form:input path="tableName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>--%>

            <li><label>表单名称：</label>
                <form:select path="procDefId" maxlength="20" class="input-xlarge">
                    <form:option value="" label="请选择"/>
                    <form:options items="${processList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>申请人：</label>
                <sys:treeselect id="loginName" name="loginName"
                                value="" labelName="name" labelValue=""
                                title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                                notAllowSelectParent="true" checked="true" cssStyle="width:180px"/>
			</li>
		<%--	<li><label>机构：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>--%>
			<li><label>流程状态：</label>
                <form:select path="procState" maxlength="20" class="input-xlarge">
                    <form:option value="" label="请选择"/>
                    <form:options items="${fns:getDictList('act_lczt')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>

		<%--	<li><label>驳回状态：</label>
				<form:input path="regectState" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>--%>
			<%--<li><label>创建人：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>--%>
			<li><label>创建日期：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${actCountInfo.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>表单名称</th>
                <th>申请人</th>
				<th>机构</th>
				<th>流程状态</th>
			<%--	<th>驳回状态</th>--%>
			<%--	<th>创建人</th>--%>
                <th>当前审批节点</th>
                <th>当前审批人</th>
				<th>创建日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="actCountInfo">
			<tr>
				<td>
					${actCountInfo.tableName}
				</td>
				<td>
					${actCountInfo.loginName}
				</td>
				<td>
					${actCountInfo.companyId}
				</td>
				<td> <span class="fontS_14">
                           <c:if test="${actCountInfo.procState eq '0'}">
                               进行中
                           </c:if>
                            <c:if test="${actCountInfo.procState eq '1'}">
                                已归档
                            </c:if>
                            <c:if test="${actCountInfo.procState eq '2'}">
                                撤销
                            </c:if>
                      <c:if test="${actCountInfo.procState eq '5'}">
                          驳回
                      </c:if>
                    </span>

				</td>
				<td>
					${actCountInfo.actName}
				</td>
				<td>
					${actCountInfo.assignee}
				</td>
				<td>
					<fmt:formatDate value="${actCountInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			<td>
                <a href="/a/oa/flow/form?id=${actCountInfo.businessKey}&formNo=${actCountInfo.procDefId}&showType=flowView&act.procInsId=${actCountInfo.procInstId}">详情</a>
					<%--<a href="${ctx}/sys/actCountInfo/delete?id=${actCountInfo.id}" onclick="return confirmx('确认要删除该流程信息汇吗？', this.href)">删除</a>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>