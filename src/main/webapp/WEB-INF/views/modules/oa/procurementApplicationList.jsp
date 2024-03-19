<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存采购统计表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/oa/procurementApplication/export");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/procurementApplication/">保存采购统计表列表</a></li>
		<shiro:hasPermission name="oa:procurementApplication:edit"><li><a href="${ctx}/oa/procurementApplication/form">保存采购统计表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="procurementApplication" action="${ctx}/oa/procurementApplication/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>部门：</label>
				<form:input path="col185" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>类别：</label>

                <form:select path="col187" class="input-xlarge">
                    <form:option value="" label="请选择"/>
                    <form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"> <input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部门</th>
				<th>申请人</th>
                <th>类别</th>
				<shiro:hasPermission name="oa:procurementApplication:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="procurementApplication">
			<tr>
                <td>
                        ${procurementApplication.bm}
                </td>
                <td>
                        ${procurementApplication.sqr}
                </td>
                <td>
                        ${procurementApplication.lb}
                </td>
				<td><a href="${ctx}/oa/procurementApplication/form?id=${procurementApplication.id}">
					<fmt:formatDate value="${procurementApplication.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${procurementApplication.remarks}
				</td>
				<shiro:hasPermission name="oa:procurementApplication:edit"><td>
    				<a href="${ctx}/oa/procurementApplication/form?id=${procurementApplication.id}">修改</a>
					<a href="${ctx}/oa/procurementApplication/delete?id=${procurementApplication.id}" onclick="return confirmx('确认要删除该保存采购统计表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>