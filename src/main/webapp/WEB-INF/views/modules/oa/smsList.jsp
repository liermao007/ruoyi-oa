<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>短信设置</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<span id="add"><a href="${ctx}/oa/smsSet/smsSave?companyId=" role="button" class="btn btn-primary" style="width: 5%">添加</a></span>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr><th>序号</th><th>机构</th><th>默认发送短信</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
    <c:forEach items="${list}" var="count"  varStatus="num">
        <tr>
            <td>${num.index + 1}</td>
            <td><input type="hidden" id="cName" value="${count.companyName}">${count.companyName}</td>
            <td><c:if test="${count.isSend == 0}">否</c:if><c:if test="${count.isSend == 1}">是</c:if></td>
            <shiro:hasPermission name="sys:role:edit"><td>
                <a href="${ctx}/oa/smsSet/smsSave?companyId=${count.companyId}">修改</a>
                <a href="${ctx}/oa/smsSet/delete?companyId=${count.companyId}" onclick="return confirmx('确认要删除该配置吗？', this.href)" style="width: 20%">删除</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    <tr>
        <td><input type="hidden" id="name1" value="${name1}"></td>
    </tr>
</table>
<script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
<script>
    $(function(){
        var cName = document.getElementById("cName").value;
        var name1 = document.getElementById("name1").value;
        if(cName != ""&&name1 != "董事会"){
            document.getElementById("add").style.display = 'none';
        }else{
            document.getElementById("add").style.display = true;
        }
    })
</script>
</body>
</html>
