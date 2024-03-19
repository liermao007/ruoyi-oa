<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function pickedFunc()
        {
            $("#searchForm").submit();
        }
	</script>
</head>
<body>
    <ul class="breadcrumb">
        <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
        <li id="levelMenu2" class="active"></li>
    </ul>
    <c:if test="${not empty message}">
        <div id="messageBox" class="alert alert-success ">
            <a href="#" class="close" data-dismiss="alert">
                &times;
            </a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <%--<sys:message content="${message}"/>--%>
	<form:form id="searchForm" name="searchForm" modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/" method="post" class="breadcrumb form-search" >
		<ul class="ul-form">
            <li>　　　　　<label>总结日期：</label>
                　<input name="sumDate" type="text" id="sumDate" readonly="readonly"  maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${oaSummaryDay.sumDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:pickedFunc});"/>
            </li>

			<li class="clearfix"></li>
		</ul>
	</form:form>

    <form:form id="inputForm" modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/save" method="post" class="form-horizontal">

        <div class="control-group" >

            <div class="controls">
                <input name="sumDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${oaSummaryDay.sumDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </div>
        </div>
        <div class="control-group" >
            <label class="control-label">任务完成：</label>
            <div class="controls">
                <table style="width:300px;">
                    <c:forEach items="${oaSummaryDay.oaScheduleList}" var="oaSchedule">
                        <tr>
                            <td>
                                    ${fns:abbr(oaSchedule.content,50)}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <form:hidden path="id" value="${oaSummaryDay.id}"/>
        <div class="control-group">
            <label class="control-label">工作总结：</label>
            <div class="controls">
                <form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000"  class="input-xxlarge " value="${oaSummaryDay.content}"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">评阅意见：</label>
            <div class="controls">
                <form:textarea path="evaluate"  htmlEscape="false" rows="3" maxlength="2000" class="input-xxlarge " value="${oaSummaryDay.evaluate}" readonly="true"/>
            </div>
        </div>
        <div class="form-actions">
            <shiro:hasPermission name="oa:oaSummaryDay:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick=" return checkSubmit()"/>&nbsp;</shiro:hasPermission>
        </div>
    </form:form>
<script type="text/javascript">
        var checkSubmitFlg = false;
        function checkSubmit() {
            if (!checkSubmitFlg) {
                // 第一次提交
                checkSubmitFlg = true;
            } else {
                //重复提交
                return false;
            }
        }
</script>
</body>

</html>