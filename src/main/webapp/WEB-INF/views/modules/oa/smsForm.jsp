<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>短信设置</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>

</head>
<body>
<script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
<script>
    //提交
    function check(){
        var obj = document.getElementsByName("isSend");
        if(obj[0].checked||obj[1].checked){
            document.getElementById('comId').disabled = false;
            form1.action = '${ctx}/oa/smsSet/save';
            form1.submit();
            return true;
        }else{
            alert("请选择是否发送短信");
            document.getElementsByName("isSend")[0].focus();
            return false;
        }
    }
    //选择机构时触发，判断此机构流程，及是否发短信
    function change(){
        var comId = document.getElementById("comId").value;
        var id = comId.split("-_-")[0];
        $.ajax({
            type:"post",
            url:"${ctx}/oa/smsSet/change?companyId="+id,
            data:"",
            success:function(isSend){
                $("#isSendButton").html(isSend)
            }
        })
    }

    $(document).ready(function() {
        var comId = document.getElementById("comId").value;
        var id = comId.split("-_-")[0];
        $.ajax({
            type:"post",
            url:"${ctx}/oa/smsSet/change?companyId="+id,
            data:"",
            success:function(isSend){
                $("#isSendButton").html(isSend)
            }
        })
    })

    //判断超级管理员，否，则禁止选择机构
    window.onload = function () {
        var companyName = "${company1}";
        if("董事会" != companyName){
            document.getElementById('comId').disabled = true;
        }else{
            var companyId = "${companyId}";
            document.getElementById('comId').disabled = false;
        }
    }
</script>
    <div class="modal-header">
        <h3 id="myModalLabel">机构管理</h3>
    </div>
        <form:form id="form1" modelAttribute="smsSet" method="post" class="form-horizontal">
            <div class="modal-header" id="content">
                <table>
                    <tr>
                        <td class="mailTd" style="padding-left: 20%; text-align: left;font-size: 15px;color: #317EAC;font-weight: bold; width: 5%" colspan="4">
                            机构：<form:select path="companyId" id="comId" onchange="change()">
                                <c:forEach var="comp" items="${companyList}">
                                    <c:choose>
                                        <c:when test="${comp.id == companyId}">
                                            <form:option value="${comp.id}-_-${comp.companyName}" name="companyName" selected="selected">${comp.companyName}</form:option>
                                        </c:when>
                                        <c:when test="${comp.companyName == company1}">
                                            <form:option value="${comp.id}-_-${comp.companyName}" name="companyName" selected="selected">${comp.companyName}</form:option>
                                        </c:when>
                                        <c:otherwise>
                                            <form:option value="${comp.id}-_-${comp.companyName}" name="companyName">${comp.companyName}</form:option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </form:select>
                        </td>
                        <td class="mailTd" style="padding-left: 50px; text-align: left;font-size: 15px;color: #317EAC;font-weight: bold; width: 5%"></td>
                    </tr>
               </table>
           </div>
            <div id="isSendButton" style=" padding-top: 3%; padding-left: 15% text-align: center">
                流程配置
            </div>
            <div class="modal-body" style="padding-bottom: 5%; padding-top: 3%; padding-left: 35%">
                 <form:button onclick="return check()" class="btn btn-primary">提交</form:button>
            </div>
        </form:form>
</body>
</html>
