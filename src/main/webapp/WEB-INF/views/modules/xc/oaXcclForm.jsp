<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡查处理记录管理</title>
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

	</li>
</ul>
	<form:form id="inputForm" modelAttribute="oaXccl" action="${ctx}/xc/oaXccl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">项目：</label>
			<div class="controls">
				<form:input path="recordProject" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">巡查备注：</label>
			<div class="controls">
				<form:textarea path="recordRemark" htmlEscape="false" rows="4" class="input-xxlarge" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理意见:</label>
			<div class="controls">
				<form:textarea path="handleOpinion" htmlEscape="false" rows="4" style="width: 700px;" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>