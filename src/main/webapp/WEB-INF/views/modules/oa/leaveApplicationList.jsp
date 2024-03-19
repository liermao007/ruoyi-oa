<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存请假单统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/oa/leaveApplication/export?tableName=${tableName}&type=${type}");
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
		<li class="active"><a href="${ctx}/oa/leaveApplication/">请假单统计列表</a></li>
		<shiro:hasPermission name="oa:leaveApplication:edit"><li><a href="${ctx}/oa/leaveApplication/form">请假单统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="leaveApplication" action="${ctx}/oa/leaveApplication?tableName=${tableName}&type=${type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>请假类型：</label>--%>
                <%--<form:select path="qjlx" class="input-xlarge">--%>
                    <%--<form:option value="" label="请选择"/>--%>
                    <%--<form:options items="${fns:getDictList('oa_leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                <%--</form:select>--%>
			<%--</li>--%>


                <li><label>开始日期：</label>
                    <input name="qjksrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                           value="<fmt:formatDate value="${leaveApplication.qjksrq}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </li>
                <li><label>结束日期：</label>
                    <input name="qjjsrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                           value="<fmt:formatDate value="${leaveApplication.qjjsrq}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
                <th>所属部门</th>
				<th>申请人</th>
               <th>请假类型</th>
                <th>请假天數</th>
                <th>请假开始日期</th>
                <th>请假结束日期</th>
				<shiro:hasPermission name="oa:leaveApplication:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="leaveApplication"  begin="0" end="19">
			<tr>
                <td>
                        ${leaveApplication.ks}
                </td>
                <td>
                        ${leaveApplication.xm}
               </td>
                <c:if test="${tableName== 'JMY_QJSQD'}">
                <td>
                      ${fns:getDictLabel(leaveApplication.qjlx,'oa_leave_type','')}
              </td>
                </c:if>
                <c:if test="${tableName != 'JMY_QJSQD'}">
                    <td>
                            ${fns:getDictLabel(leaveApplication.qjlx,'slhp_leavle_type','')}
                    </td>
                </c:if>
                <td format="0.0">

                            <fmt:formatNumber  value="${leaveApplication.qjts}" type="number" pattern="0.0" />
                </td>
                <td>
                    <fmt:formatDate value="${leaveApplication.qjksrq}" pattern="yyyy-MM-dd "/>
                </td>
                <td>
                    <fmt:formatDate value="${leaveApplication.qjjsrq}" pattern="yyyy-MM-dd "/>
			    </td>

				<shiro:hasPermission name="oa:leaveApplication:edit"><td>
    				<a href="${ctx}/oa/leaveApplication/form?id=${leaveApplication.id}">修改</a>
					<a href="${ctx}/oa/leaveApplication/delete?id=${leaveApplication.id}" onclick="return confirmx('确认要删除该保存请假单统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>