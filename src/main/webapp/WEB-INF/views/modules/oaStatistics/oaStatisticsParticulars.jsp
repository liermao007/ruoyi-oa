<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/9
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>统计表配置详情</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .mytable{
            width: 100%;
        }
        .mytable .table{
             margin: 10px;
         }

        .table td{
            width: 25%;
        }
        li{
            list-style:none;
        }
    </style>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="${ctx}/OaStatistics/oaStatistics/index"></a></li>
</ul>
    <ul>
        <li>
            ${TJMC.statisticsName}
        </li>
    </ul>
<c:if test="${not empty message}">
    <div id="messageBox" class="alert alert-success ">
        <a href="#" class="close" data-dismiss="alert">
            &times;
        </a>
        <strong>${message}</strong>
    </div>
</c:if>
<div style="width:100%; overflow-x:scroll;">
    <table class="mytable">
        <tr>
            <c:forEach items="${oastaticsName}" var="oastaticsName" varStatus="index">
            <%--<form method="post" action="${ctx}/OaStatistics/oaStatistics/serial">--%>
                <td>
                <form method="post" action="${ctx}/OaStatistics/oaStatistics/serial">
                  <input name="TJID" type="hidden" value="${TJMC.id}">
                <table id="table${oastaticsName.id}" class="table table-striped table-bordered table-condensed">
                    <tr><td colspan="2">数据表</td><td colspan="2">备注</td></tr>
                    <tr>
                        <%--<td>${oastaticsName.statisticsName}</td>--%>
                        <td colspan="2">${oastaticsName.dataTable}</td>
                        <td colspan="2">${oastaticsName.remarks}</td>
                    </tr>
                    <tr>
                        <td colspan="4">已有字段</td>
                    </tr>
                    <tr>
                        <td>排序</td>
                        <td>字段名称</td>
                        <td>数据列</td>
                        <td>操作</td>
                    </tr>
                    <input type="hidden" id="length${index.count}" value="${fn:length(oastaticsName.oaStatisticsFields)}">
                    <c:forEach items="${oastaticsName.oaStatisticsFields}" var="field">
                        <tr name="line">
                            <input type="hidden" name="fieldId" value="${field.id}">
                            <td><input type="hidden" name="serial" id="hidden${field.id}" value="${field.serial}"><span name="spanSerial" id="span${field.id}">${field.serial}</span></td>
                            <td>${field.remarks}</td>
                            <td>${field.fieldName}</td>
                            <td>
                                <input type="button" class="btn btn-primary" id="upMove${field.id}" onclick="upMove('${field.id}','${oastaticsName.id}')" value="上移"/>
                                <input type="button" class="btn" id="downMove${field.id}" onclick="downMove('${field.id}','${oastaticsName.id}')" value="下移"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4">
                            <input type="submit" class="btn btn-primary" value="保存">
                            <a href="${ctx}/OaStatistics/oaStatistics/field?id=${oastaticsName.id}"><input type="button" class="btn" value="修改/查看配置"></a>
                            &nbsp;<a href="${ctx}/OaStatistics/oaStatistics/deleteTable?id=${oastaticsName.id}&TJID=${TJMC.id}" onclick="return confirmx('确认要删除该统计表吗？', this.href,'')"><input type="button" class="btn btn-primary" value="删除该表"></a>
                            <a href="${ctx}/OaStatistics/oaStatistics/gettingData?tableName=${oastaticsName.dataTable}&tableId=${oastaticsName.id}"><input type="button" class="btn" value="查看单表数据"></a>
                        </td>
                    </tr>
                </table>
                </form>
            </td>
            </c:forEach>
        </tr>
    </table>
</div>
    <ul>
        <li class="tagEnd" style="margin-bottom: 50px">
            <c:if test="${fn:length(oastaticsName) > 1}">
            <a href="#" id="seeAll" onclick="seeAllData(${fn:length(oastaticsName)})"><input type="button" class="btn btn-primary" value="查看全部数据"></a>
            </c:if>
            <a href="${ctx}/OaStatistics/oaStatistics/index"><input type="button" class="btn" value="返回"></a>
            <a href="${ctx}/OaStatistics/oaStatistics/addParticulars?TJID=${TJMC.id}"><input type="button" class="btn btn-primary" value="添加"></a>
        </li>
    </ul>

<script type="text/javascript">
    function seeAllData(length) {
            var flag=0;
            for (var i= 0; i<=length-1;i++){
                if ($("#length"+i).val() == $("#length"+(i+1)).val()){
                        flag=flag+1;
                }
            }
            if (flag==length-1){
                $(location).attr('href', '${ctx}/OaStatistics/oaStatistics/gettingAllData?TJMCID=${TJMC.id}');
            }else {
                alert("表字段不同，不能使用查看全部数据")
            }
    }
</script>

<%--排序方式--%>
<script type="text/javascript">
    //上移
    function upMove(id,tableId) {
        var curTr = $("#upMove"+id).parents("tr[name='line']");
        var prevTr = $("#upMove"+id).parents("tr[name='line']").prev("tr[name='line']");
        if(prevTr.length == 0){
            return;
        }else{
            prevTr.before(curTr);
            sortNumber(tableId);//重新排序
        }
    }

    //下移
    function downMove(id,tableId) {
        var curTr = $("#upMove"+id).parents("tr[name='line']");
        var prevTr = $("#upMove"+id).parents("tr[name='line']").next("tr[name='line']");
        if(prevTr.length == 0){
            return;
        }else{
            prevTr.after(curTr);
            sortNumber(tableId);//重新排序
        }
    }

    function sortNumber(tableId) {
        var allInput = $("#table"+tableId).find("input[name='serial']");
        var spanSerial=$("#table"+tableId).find("span[name='spanSerial']");
        if(allInput.length != 0){
            for(var i=0;i<allInput.length;i++){
                var tempInput = allInput[i];
                var span = spanSerial[i];
                tempInput.value = i + 1;
                span.innerHTML = i + 1;
            }
        }
    }

</script>
</body>
</html>
