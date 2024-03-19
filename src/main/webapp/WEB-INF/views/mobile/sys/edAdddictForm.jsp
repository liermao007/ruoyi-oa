<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
            <shiro:hasPermission name="sys:dict:edit">修改</shiro:hasPermission><shiro:lacksPermission name="sys:dict:edit">查看</shiro:lacksPermission>
        </li>
    </ul>
	<form:form id="inputForm" modelAttribute="addDict" action="${ctx}/sys/adddict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">键值:</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签:</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<%--<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>--%>
                <label class="control-label">姓名:</label>
                <div class="controls">
                    <sys:treeselect id="name" name="name"
                                    value="${oaSummaryPermission.evaluateById}" labelName="name"
                                    labelValue="${addDict.name}"
                                    title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                                    notAllowSelectParent="true" checked="true" cssStyle="width:500px"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>

               <%-- <div class="controls">
                    <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
                                    title="部门" url="/sys/office/treeData?type=2" cssClass="required" notAllowSelectParent="true"/>
                </div>--%>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>

		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:dict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>