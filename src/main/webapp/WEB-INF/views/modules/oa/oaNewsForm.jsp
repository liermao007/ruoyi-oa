<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    if (CKEDITOR.instances.content.getData()==""){
                        top.$.jBox.tip('请填写公告内容','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
	</script>
</head>
<body>
    <ul class="breadcrumb">
        <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
        <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
        <li class="active">
            <shiro:hasPermission name="oa:oaNews:edit">${not empty oaNews.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaNews:edit">查看</shiro:lacksPermission>
        </li>
    </ul>
	<form:form id="inputForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <form:hidden path="auditFlag"/>
		<sys:message content="${message}"/>
        <div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<%--        <div class="control-group">--%>
<%--            <label class="control-label">公告审核官：</label>--%>
<%--            <div class="controls">--%>
<%--                <form:select path="auditMan" >--%>
<%--                    <form:option value="" label=""/>--%>
<%--                    <form:options items="${fno:getAuditManAllList()}" itemLabel="auditMan" itemValue="auditId" htmlEscape="false"/>--%>
<%--                </form:select>--%>
<%--                <span class="help-inline"><font color="red">*</font> 选择审核人代表此公告需审核，不选则表示此公告不需要审核</span>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="control-group">
            <label class="control-label" style=" margin-top: 5px;">附件：</label>
            <div class="controls">
                <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <form:hidden id="fjlj" path="fjlj" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <form:hidden id="filesName" path="filesName" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <input type="hidden" id="FJMC"  name="FJMC" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <input type="hidden" id="FJLJ"  name="FJLJ" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <%--<sys:ckfinder input="files" type="files" uploadPath="/oa/oaNews" selectMultiple="true"/>--%>
                <iframe id="iframe2" scrolling="no" src="/static/stream/infoFJ.html" style="border: 0px currentColor; width: 90px; height:30px;"></iframe>
                <div id="FJ">
                </div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否置顶:</label>
            <div class="controls">
                <form:radiobuttons path="isTopic"  items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" value="${isTopic}"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">接受人：</label>
            <div class="controls">
                <sys:treeselect id="oaNewsRecord" name="oaNewsRecordIds" value="${oaNews.oaNewsRecordIds}" labelName="oaNewsRecordNames" labelValue="${oaNews.oaNewsRecordNames}"
                                title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required" notAllowSelectParent="true" checked="true"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">公告内容</label>
            <div class="controls">
                <form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
                <sys:ckeditor replace="content" uploadPath="/oa/news" />
            </div>
        </div>

		<div class="form-actions">
            <c:if test="${oaNews.auditFlag ne '1'|| oaNews.createBy eq fns:getUser().id || oaNews.auditMan eq fns:getUser().id || fns:getUser().id eq '1'}">
			    <shiro:hasPermission name="oa:oaNews:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>