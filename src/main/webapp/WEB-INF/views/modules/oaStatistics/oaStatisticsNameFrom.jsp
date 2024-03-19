<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/8
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

    <title>Title</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
</head>
<body>
    <c:if test="${flag == 'update'}">
    <ul class="breadcrumb">
        <li id="levelMenu1"><a href="${ctx}/OaStatistics/oaStatistics/index">统计列表</a> <span class="divider">/</span></li>
    </ul>
    <form:form id="inputForm" modelAttribute="oastaticsName" action="${ctx}/OaStatistics/oaStatistics/update" method="post" class="form-horizontal" onsubmit="return verification()">
        <form:hidden path="id"/>
        <div class="control-group">
            <label class="control-label">统计名称:</label>
            <div class="controls">
                <form:input path="statisticsName" id="statisticsName" htmlEscape="false" maxlength="50" class="required"/>
            <font style="color: red">*</font>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注信息:</label>
            <div class="controls">
                <form:input path="remarks" htmlEscape="false" maxlength="50" class="required"/>
            </div>
            <div class="form-actions">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
                <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
            </div>
        </div>
    </form:form>
</c:if>

<script type="text/javascript">
    function verification(){
        if ($("#statisticsName").val()==""){
            alert("请填写统计名称");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
