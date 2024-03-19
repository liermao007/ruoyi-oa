<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/8
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="height: 80%">
<head>
    <title>Title</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        li{
            list-style:none;
        }
    </style>
</head>
<body>
<%--<form action="${ctx}/OaStatistics/oaStatistics/saveTable" method="post">--%>
    <%--<input type="hidden" name="TjmcId" value="${TJMC.id}">--%>
    <%--<ul>--%>
        <%--<li class="liTop">--%>
            <%--<ul>--%>
                <%--<li>${TJMC.statisticsName}&nbsp;清单添加</li>--%>
            <%--</ul>--%>
        <%--</li>--%>
        <%--<li class="liTop2">--%>
            <%--<ul>--%>
                <%--<li>统计表名称：<input type="text" name="TJBMC" class="text1" style="width: 256px"></li>--%>
                <%--<li>备--%>
                    <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                    <%--注：<input type="text" id="remarks" name="remarks" class="text1" style="width: 256px"></li>--%>
                <%--<li>--%>
                    <%--数据表对应：--%>
                    <%--<select id="dataTable" name="dataTable" style="width: 256px">--%>
                        <%--<c:forEach items="${tableList}" var="tableList">--%>
                            <%--<option value="${tableList.tableName}">${tableList.tableComment}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</li>--%>
            <%--</ul>--%>
        <%--</li>--%>
        <%--<li class="liTop3">--%>
            <%--<ul>--%>
                <%--<li>--%>
                    <%--<table id="tab1" class="tab1">--%>

                    <%--</table>--%>
                <%--</li>--%>
            <%--</ul>--%>
        <%--</li>--%>
        <%--<li class="tail">--%>
            <%--<ul>--%>
                <%--<li>--%>
                    <%--<input type="submit" class="sub" value="保存">--%>
                    <%--<input type="button" class="sub" onclick="history.go(-1)"  value="返回">--%>
                <%--</li>--%>
            <%--</ul>--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</form>--%>

<script type="text/javascript">

</script>



<%------------------------------------------------------------%>

<ul class="breadcrumb">
    <li id="levelMenu1"><a href="${ctx}/OaStatistics/oaStatistics/index"></a> <span class="divider">统计列表</span></li>
    <li id="levelMenu2"><a href="#"></a> <span class="divider">${TJMC.statisticsName}清单</span></li>
</ul>
<form id="inputForm" action="${ctx}/OaStatistics/oaStatistics/saveTable" onsubmit="return checkForm();" method="post" class="form-horizontal">
    <input type="hidden" name="TjmcId" value="${TJMC.id}">
    </div>
    <div class="control-group">
        <label class="control-label">统计名称:</label>
        <div class="controls">
            <input id="TJBMC"name="TJBMC" type="hidden" value="${TJMC.statisticsName}" htmlEscape="false" maxlength="50" class="required"/>
            <span>${TJMC.statisticsName}</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息:</label>
        <div class="controls">
            <input id="remarks" name="remarks" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
<c:if test="${subordinate != null && fn:length(subordinate)>0}">
    <div class="control-group" id="organization" style="display: none">
        <label class="control-label">机构选择:</label>
        <div class="controls">
            <select id="institution" class="input-xlarge">
                <option value="">请选择</option>
               </select>
        </div>
    </div>
</c:if>
    <div class="control-group">
        <label class="control-label">数据表:</label>
        <div class="controls">
            <input type="hidden" id="dataTableName" name="dataTable">
            <select id="dataTable" name="tableID" class="input-xlarge required">
                <option value="">请选择</option>
               <c:forEach items="${tableList}" var="tableList">
                 <option value="${tableList.id}">${tableList.tableComment}</option>
               </c:forEach>
                </select>
            <c:if test="${subordinate != null && fn:length(subordinate)>1}">
            <span class="help-inline"><input type="button" class="btn btn-primary" id="switchover" value="选择下属机构数据表">
            <input type="button" id="switchoverTwo" class="btn btn-primary" value="选择本机构数据表" style="display: none">
            </span>
            </c:if>
        </div>
        <div class="control-group" id="divTab" style="display: none">
            <table id="tab1" class="table table-striped table-bordered table-condensed">

            </table>
        </div>
        <div class="form-actions">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='/a/OaStatistics/oaStatistics/index'"/>
        </div>
    </div>
</form>

<script type="text/javascript">

    $("#dataTable").change(function () {
        $("#divTab").css("display","block");
        var dataTab=$("#dataTable").val();
        var dataRemarks = $("#dataTable option:selected").text();
        $("#remarks").val(dataRemarks);
        $.ajax({
            type : "post",
            async : true,
            url : '${ctx}/OaStatistics/oaStatistics/columns?tableId='+dataTab,
            success:function(data){
                $("tr[name='columns']").remove();
                $("#tab1").append("<tr name='columns'><td style='font-size: 15px;text-align: center;'>选择</td><td style='font-size: 15px;text-align: center;'>字段名</td>" +
                    "<td style='font-size: 15px;text-align: center;'>关键字</td><td style='font-size: 15px;text-align: center;'>数据类型</td></tr>");
                var columnList=data.oaPersonDefineTableColumnList;
                $("#dataTableName").val(data.tableName);
                $.each(columnList,function (c,d) {
                    if (d.columnName != "id" && d.columnName != "create_by" && d.columnName != "create_date" && d.columnName != "update_by" && d.columnName != "update_date" && d.columnName != "remarks" && d.columnName != "del_flag" && d.columnName != "proc_ins_id" && d.columnName != "proc_def_id"){
                        $("#tab1").append("<tr name='columns' class='tr1'> <td class='td1' style='font-size: 15px;text-align: center;'> " +
                            "<input type='checkbox' value='"+d.columnName+"' name='checkbox'></td> " +
                            "<input type='hidden' name='dataType' value='"+d.remarks+"'>" +
                            "<td class='td2' style='font-size: 15px;text-align: center;'>"+d.columnComment+"</td> <td class='td3' style='font-size: 15px;text-align: center;'>"+d.columnName+"</td><td style='font-size: 15px;text-align: center;'>"+d.columnType+"</td> </tr>");
                    }
                });
            },
            error: function() {
            }
        });
    })
</script>
<%--表单验证提交--%>
<script type="text/javascript">
    var flag=true;

    function checkForm(inputForm) {
        var a=$("input[type=checkbox]:checked");
        if ($("#TJBMC").val() != "" && $("#remarks").val() != "" && a.length>0){
            return flag;
        }else{
            return false;
        }
    }
</script>
<%--选择机构--%>
<script type="text/javascript">
    $("#switchover").click(function () {
        $("#divTab").css("display","none");
        $("#tab1").find("tr").remove();
        $("#dataTable").find("option").remove();
        $("#dataTable").prepend("<option value=''>请选择</option>");
        $("#organization").css("display","block");
        $("#switchoverTwo").css("display","block");
        $("#switchover").css("display","none");
        $.ajax({
            type : "post",
            async : true,
            url : '${ctx}/OaStatistics/oaStatistics/organization',
            success:function(data){
                $.each(data,function (c,d) {
                    if (d.type == '1'){
                        $("#institution").append("<option value='"+d.id+"'>"+d.name+"</option>")
                    }

                })
            }
    })
    });

    $("#institution").change(function () {
        $("#dataTable").find("option").remove();
        $("#dataTable").prepend("<option value=''>请选择</option>");
        var orgId = $("#institution").val();
        $.ajax({
            type : "post",
            async : true,
            url : '${ctx}/OaStatistics/oaStatistics/agencyTable?orgId='+orgId,
            success:function(data){
                $.each(data,function (c,d) {
                    $("#dataTable").append("<option value='"+d.id+"'>"+d.tableComment+"</option>")
                })
            }
        })
    })
    
    $("#switchoverTwo").click(function () {
        $("#divTab").css("display","none");
        $("#tab1").find("tr").remove();
        $("#dataTable").find("option").remove();
        $("#dataTable").prepend("<option value=''>请选择</option>");
        $("#organization").css("display","none");
        $("#switchoverTwo").css("display","none");
        $("#switchover").css("display","block");
        $.ajax({
            type : "post",
            async : true,
            url : '${ctx}/OaStatistics/oaStatistics/thisInstitution',
            success:function(data){
                $.each(data,function (c,d) {
                    $("#dataTable").append("<option value='"+d.id+"'>"+d.tableComment+"</option>")
                })
            }
        })
    })
</script>

</body>
</html>
