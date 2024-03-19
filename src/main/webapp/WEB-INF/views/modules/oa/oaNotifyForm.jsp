<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
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
            <shiro:hasPermission name="oa:oaNotify:edit">${oaNotify.status eq '1' ? '查看' : not empty oaNotify.id ? '修改' : '添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaNotify:edit">查看</shiro:lacksPermission>
        </li>
    </ul>
	<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${oaNotify.status ne '1'}">
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<form:hidden id="fjlj" path="fjlj" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
					<form:hidden id="filesName" path="filesName" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
					<input type="hidden" id="FJMC"  name="FJMC"  htmlEscape="false" maxlength="2000" class="input-xlarge" value="${oaNotify.filesName}"/>
					<input type="hidden" id="FJLJ"  name="FJLJ"  htmlEscape="false" maxlength="2000" class="input-xlarge" value="${oaNotify.fjlj}"/>
					<iframe id="iframe2" scrolling="no" src="/static/stream/infoFJ.html" style="border: 0px currentColor; width: 90px; height:30px;"></iframe>
					<div id="FJ">
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">接收人：</label>
				<div class="controls">
	                <sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
						title="用户" url="/sys/office/treeData?type=3" zrFlag="1" cssClass="input-xxlarge required" notAllowSelectParent="true" checked="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${oaNotify.status eq '1'}">
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">
					<div id="hhh">
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">接收人：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>接受人</th>
								<th>接受部门</th>
								<th>阅读状态</th>
								<th>阅读时间</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
							<tr>
								<td>
									${oaNotifyRecord.user.name}
								</td>
								<td>
									${oaNotifyRecord.user.office.name}
								</td>
								<td>
									${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
								</td>
								<td>
									<fmt:formatDate value="${oaNotifyRecord.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					已查阅：${oaNotify.readNum} &nbsp; 未查阅：${oaNotify.unReadNum} &nbsp; 总共：${oaNotify.readNum + oaNotify.unReadNum}
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<c:if test="${oaNotify.status ne '1'}">
				<shiro:hasPermission name="oa:oaNotify:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="发 送"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="self.location=document.referrer;"/>
		</div>
	</form:form>

<script>
	$(function() {
		addFiles()

		var  fjlj="${oaNotify.fjlj}"
		var  fjmc= "${oaNotify.filesName}"
		if(fjlj && fjmc){
			var mc=fjmc.split(",")
			var lj=fjlj.split(",")
			$("#hhh").html("");
			for(var i=0;i<mc.length-1;i++){
				$("#hhh").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='" + lj[i] + "'>"+mc[i]+"</a><br>");
			}
		}
	})
	function deleteHref(u,c,i){
		document.getElementById("MC"+i).remove();
		document.getElementById("LJ"+i).remove();
		var MC = $("#FJMC").val();
		var LJ = $("#FJLJ").val();
		var FJLJ = LJ.replace(c+",","");
		var FJMC =MC.replace(u+",","");
		//如果点击删除按钮，那么需要将附件名称中的这个附件删除
		$("#FJMC").val(FJMC)
		$("#filesName").val(FJMC)
		$("#FJLJ").val(FJLJ)
		$("#fjlj").val(FJLJ)
	}

	function addFiles() {
		let names = $('#filesName').val()
		let fjlj = $('#fjlj').val()
		if(names && fjlj) {
			let arrayMC = names.split(",");
			let arrayLj = fjlj.split(",");
			let fileName="" ;
			for(var i=0;i<arrayMC.length-1;i++){
				fileName =  fileName + '<span id="MC'+i+'"><img src="/static/images/icons/icon-file.png" style="vertical-align:middle;width: 16px; height:13px;"/>'+arrayMC[i]+'</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="LJ'+i+'" onclick="deleteHref(\''+arrayMC[i]+'\',\''+arrayLj[i]+'\','+i+')">删除<br></a>'
			}
			$('#FJ').html(fileName);
		}
	}
</script>
</body>
</html>