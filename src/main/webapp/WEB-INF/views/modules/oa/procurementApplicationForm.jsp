<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存采购统计表管理</title>
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/procurementApplication/">保存采购统计表列表</a></li>
		<li class="active"><a href="${ctx}/oa/procurementApplication/form?id=${procurementApplication.id}">保存采购统计表<shiro:hasPermission name="oa:procurementApplication:edit">${not empty procurementApplication.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:procurementApplication:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="procurementApplication" action="${ctx}/oa/procurementApplication/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">编号：</label>
			<div class="controls">
				<form:input path="col183" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请人：</label>
			<div class="controls">
				<form:input path="col184" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input path="col185" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请设备（物资）：</label>
			<div class="controls">
				<form:input path="col186" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类别：</label>
			<div class="controls">
				<form:input path="col187" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<form:input path="col188" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请理由：</label>
			<div class="controls">
				<form:input path="col189" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购人：</label>
			<div class="controls">
				<form:input path="col191" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购部门：</label>
			<div class="controls">
				<form:input path="col192" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供货渠道/商品信息：</label>
			<div class="controls">
				<form:input path="col193" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">proc_ins_id：</label>
			<div class="controls">
				<form:input path="procInsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">proc_def_id：</label>
			<div class="controls">
				<form:input path="procDefId" htmlEscape="false" maxlength="150" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否固定资产：</label>
			<div class="controls">
				<form:input path="sfgdzc" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行政部门意见：</label>
			<div class="controls">
				<form:input path="xzbmyj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门领导意见：</label>
			<div class="controls">
				<form:input path="bmldyj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总经理意见：</label>
			<div class="controls">
				<form:input path="zjlyj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注数据：</label>
			<div class="controls">
				<form:input path="bzsj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件名称：</label>
			<div class="controls">
				<form:input path="fjmc" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件路径：</label>
			<div class="controls">
				<form:input path="fjlj" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">财务经办人：</label>
			<div class="controls">
				<form:input path="cwjbr" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供货渠道：</label>
			<div class="controls">
				<form:input path="ghqd" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公家具：</label>
			<div class="controls">
				<form:input path="bgjj" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公用品：</label>
			<div class="controls">
				<form:input path="bgyp" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他：</label>
			<div class="controls">
				<form:input path="qt" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">财务行政部意见：</label>
			<div class="controls">
				<form:input path="cwxzbyj" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:procurementApplication:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>