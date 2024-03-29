<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日程安排管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
    <ul class="breadcrumb">
        <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
        <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
        <li class="active">
            <shiro:hasPermission name="oa:oaSchedule:edit">${not empty oaSchedule.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaSchedule:edit">查看</shiro:lacksPermission>
        </li>
    </ul>
	<form:form id="inputForm" modelAttribute="oaSchedule" action="${ctx}/oa/oaSchedule/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required "/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重要等级：</label>
			<div class="controls">
				<form:radiobuttons path="importantLevel" items="${fns:getDictList('oa_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缓急程度：</label>
			<div class="controls">
				<form:radiobuttons path="emergencyLevel" items="${fns:getDictList('oa_schedule_import')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日志日期：</label>
			<div class="controls">
				<input name="scheduleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${oaSchedule.scheduleDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成状态：</label>
			<div class="controls">
                <form:radiobuttons path="flag" items="${fns:getDictList('oa_schedule_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
            <c:if test="${oaSchedule.flag ne '1'}">
                <shiro:hasPermission name="oa:oaSchedule:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>