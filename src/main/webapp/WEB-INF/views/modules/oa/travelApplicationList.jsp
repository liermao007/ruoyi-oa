<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存出差统计表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/oa/travelApplication/export?tableName=${tableName}&type=${type}");
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
	<ul class="breadcrumb">
		<li class="active"><a href="${ctx}/oa/travelApplication/">出差统计表列表</a></li>
		<shiro:hasPermission name="oa:travelApplication:edit"><li><a href="${ctx}/oa/travelApplication/form">出差统计表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="travelApplication" action="${ctx}/oa/travelApplication?tableName=${tableName}&type=${type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

		<ul class="ul-form">
            <li><label>出差日期：</label>
                <input name="ccrq" type="text" maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${oaSchedule.scheduleDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </li>
			<li><label>部门：</label>
				<form:input path="bm" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<%--<li><label>出差开始时间：</label>--%>
				<%--<input name="cckssj" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${travelApplication.cckssj}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			<%--</li>--%>

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
				<th>出差人</th>
                <th>出差事由</th>
                <th>出差日期</th>
                <th>出差地点</th>
				<shiro:hasPermission name="oa:travelApplication:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="travelApplication"  begin="0" end="19">
			<tr>
                <td>
                        ${travelApplication.bm}
                </td>
                <td>
                        ${travelApplication.xm}
                </td>  <td>
                    ${travelApplication.ccsy}
            </td>
                <td>
                    <fmt:formatDate value="${travelApplication.ccrq}" pattern="yyyy-MM-dd "/>
                </td>
                <td>
                        ${travelApplication.ccdd}
                </td>

				<shiro:hasPermission name="oa:travelApplication:edit"><td>
    				<a href="${ctx}/oa/travelApplication/form?id=${travelApplication.id}">修改</a>
					<a href="${ctx}/oa/travelApplication/delete?id=${travelApplication.id}" onclick="return confirmx('确认要删除该保存出差统计表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>