<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/19
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>统计表数据</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/layer/skin/default/layer.css">
    <script type="text/javascript" src="${ctxStatic}/deriveExcel/jquery.base64.js"></script>
    <script type="text/javascript" src="${ctxStatic}/deriveExcel/tableExport.js"></script>
    <style type="text/css">
        .total td{
            color: #9c0001;
            font-size: 15px;
            text-align: center;
        }
    </style>
    <script type="text/javascript">
        var zTreeObj;
        var zTreeTk;
        var setting = {
            view: {selectedMulti: false, dblClickExpand: false},
            async: {enable: true, url: "${ctx}/sys/user/treeData1", autoParam: ["id=officeId"]},
            data: {simpleData: {enable: true}}, callback: {
                //在单击时
                onClick: function (event, treeId, treeNode) {
                    window.location.href = "${ctx}/test1/oaRunProcess/findById?id=" + treeNode.id
                    $("#evaluate").val(treeNode.name)
                    $("#evaluateBy").val(treeNode.id)
                }, onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    var nodes = zTreeObj.getNodesByParam("pId", treeNode.id, null);
                    for (var i = 0, l = nodes.length; i < l; i++) {
                        try {
                            zTreeObj.checkNode(nodes[i], treeNode.checked, true);
                        } catch (e) {
                        }
                    }
                }, onDblClick: function () {
                    top.$.jBox.getBox().find("button[value='ok']").trigger("click");
                }
            }
        };
        var setting1 = {
            view: {selectedMulti: false, dblClickExpand: false}, check: {enable: "true", nocheckInherit: true},
            async: {enable: true, url: "${ctx}/sys/user/treeData1", autoParam: ["id=officeId"]},
            data: {simpleData: {enable: true}}, callback: {
                //在单击时
                onClick: function (event, treeId, treeNode) {
                }, onCheck: function (e, treeId, treeNode) {

                    var ids = [], names = [], nodes = [];
                    nodes = zTreeTk.getCheckedNodes(true);
                    for (var i = 0; i < nodes.length; i++) {//
                        if (nodes[i].isParent) {
                            continue; // 如果为复选框选择，则过滤掉父节点
                        } else {
                            ids.push(nodes[i].id);
                            names.push(nodes[i].name);
                        }
                    }
                    console.log(names + "," + ids)
                    // $("#evaluateNames").val(names)
                    $("#ids").val(ids)
                }, onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    var nodes = zTreeTk.getNodesByParam("pId", treeNode.id, null);
                    for (var i = 0, l = nodes.length; i < l; i++) {
                        try {
                            zTreeTk.checkNode(nodes[i], treeNode.checked, true);
                        } catch (e) {
                        }
                        tree.selectNode(nodes[i], false);
                    }
                    // selectCheckNode();
                }
            }
        };
        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    // loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $.ajax({
                url: "${ctx}/sys/office/treeData?type=3",
                type: "get",
                dataType: "text",
                success: initZtree
            });

            $("#akBtnSubmit").click(function(){
                $("#inputForm").attr("action","${ctx}/test1/oaRunProcess/save");
                $("#inputForm").submit();
//                var aa=$("sys[name='name']:checked").length;
//                var num=document.getElementsByName("principalid");
//                alert(aa);
////				alert();
//                if(1>1){
//                    $("#inputForm").submit();
//				}else{
//                    alert("被代理人只能选择一个");
//				}

            });


        });

        //初始化ZTree树
        function initZtree(data) {
            var zNodes = eval("(" + data + ")");		//动态js语句
            zTreeObj = $.fn.zTree.init($('#jkTree'), setting, zNodes);	//jkTree 树的id，支持多个树
            zTreeTk = $.fn.zTree.init($('#TkTree'), setting1, zNodes);	//jkTree 树的id，支持多个树

            //展开一级节点
            var nodes = zTreeObj.getNodesByParam("level", 0);
            var node2 = zTreeTk.getNodesByParam("level", 0);
            for (var i = 0; i < nodes.length; i++) {
                zTreeObj.expandNode(nodes[i], true, false, false);
            }

            for (var i = 0; i < node2.length; i++) {
                zTreeTk.expandNode(node2[i], true, false, false);
            }
        }

    </script>
    <script src="${ctxStatic}/tree/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/tree/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
</head>
<body>
<c:if test="${not empty message}">
    <div id="messageBox" class="alert alert-success ">
        <a href="#" class="close" data-dismiss="alert">
            &times;
        </a>
        <strong>${message}</strong>
    </div>
</c:if>
<script type="text/javascript">
    var fields=$('td [name=td1]').children().length;
    var width=1/fields;
    $("td").css("width",width)

</script>

<%--<script type="text/javascript">--%>
    <%--$(document).ready(function() {--%>
        <%--$("#btnExport").click(function() {--%>
            <%----%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>
<%--导出excel表格--%>
   <ul class="breadcrumb">
       <li class="active"><a href="${ctx}/OaStatistics/oaStatistics/index">统计列表</a></li>
   </ul>
   <form:form id="searchForm" modelAttribute="dataParameter" action="${ctx}/OaStatistics/oaStatistics/gettingData?tableName=${tableName}&tableId=${table.id}" method="post" class="breadcrumb form-search">
           <li><label>申请日期：</label>
               <input name="applicationDate" type="text" readonlygo="readonly" maxlength="20"
                      class="input-medium Wdate" style="width:163px;"
                      value="<fmt:formatDate value="${applicationDate}" pattern="yyyy-MM-dd"/>"
                      onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               <%--<input name="applicationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                      <%--value="<fmt:formatDate value="${applicationDate}" pattern="yyyy-MM-dd"/>"--%>
                      <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
           </li>
           <li><label>申请开始日期：</label>
               <input name="startDate" type="text" readonlygo="readonly" maxlength="20"
                      class="input-medium Wdate" style="width:163px;"
                      value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"
                      onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               <%--<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                      <%--value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"--%>
                      <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>--%>
           </li>
           <li><label>申请结束日期：</label>
               <input name="endDate" type="text" readonlygo="readonly" maxlength="20"
                      class="input-medium Wdate" style="width:163px;"
                      value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"
                      onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               <%--<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                      <%--value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"--%>
                      <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>--%>
           </li>
           <li><label>申请人：</label>
               <%--<c:set var="proposer" value="${proposer}"></c:set>--%>
               <sys:treeselect id="agentid" name="proposer"
                               value="${proposer}" labelName="name"
                               labelValue="${fns:getNameByID(proposer)}"
                               title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                               notAllowSelectParent="true" cssStyle="width:80px"/>
           </li>
       <br>
       <%--新加入查询控件--%>
       <li><label>字段类型：</label>
           <select id="fieldType" class="input-xlarge required" style="width: 175px">
               <option></option>
               <option value="0">已有字段</option>
               <option value="1">未选字段</option>
           </select>
       </li>
       <li><label>字段选择：</label>
           <select id="conditionField" class="input-xlarge required" style="width: 175px">
               <option></option>
           </select>
       </li>
       <li><label>条件选择：</label>
           <select id="symbol" class="input-xlarge required" style="width: 175px">
               <option></option>
           </select>
       </li>
       <li id="inputModeDate" style="display: none"><label>选择日期：</label>
           <input id="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                  value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"
                  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"cssStyle="width:135px"/>
       </li>

       <li id="inputModeUser" style="display: none"><label>选择用户：</label>
           <sys:treeselect id="agentid" name="proposerOne"
                           value="" labelName="name"
                           labelValue=""
                           title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                           notAllowSelectParent="true" cssStyle="width:135px"/>
       </li>
       <li id="inputModeDict" style="display: none"><label>选择部门：</label>
           <sys:treeselect id="office" name="office.id" value="" labelName="office.name" labelValue=""
                           title="部门" url="/sys/office/treeData?type=2" cssClass="required" notAllowSelectParent="true" cssStyle="width:135px"/>
       </li>
       <li id="inputModeOrg" style="display: none"><label>选择公司：</label>
           <sys:treeselect id="company2" name="company.id" value="" labelName="company.name" labelValue=""
                           title="公司" url="/sys/office/treeData?type=1" cssClass="required" notAllowSelectParent="true" checked="true" cssStyle="width:135px"/>
       </li>
       <li id="inputText" style="display: none"><label>请输入：</label>
           <input type="text" id="import" value="" class="input-medium" cssStyle="width:175px">
       </li>
       <li id="addCondition" style="display: inline;">
           <a href="#"><img src="${ctxStatic}/images/add.jpg" style="width: 30px;height: 30px"></a>
           <label>添加为条件</label>
       </li>
       <br>
       <li id="existingConditions">
           <label>已有条件：</label>
       </li>
           <c:forEach items="${existingConditions}" var="existing" varStatus="i">
               <c:set var="existingNum" value="${i.index}" scope="page"></c:set>
               <li style="display: block; margin-left: 80px;color: red" id="li${i.index}"><span>${existingConditionsName[existingNum]}</span>
                   <input type='hidden' id="existing${i.index}" name="existingConditions" value="${existing}">
                   <input type='hidden' id="Name${i.index}" name="existingConditionsName" value="${existingConditionsName[existingNum]}" />
                   &nbsp;&nbsp;&nbsp;
                   <a href='#' onclick="subtract('${i.index}')"><img src='${ctxStatic}/images/subtract.jpg' style='width: 30px;height: 30px'>删除条件</a></li>
           </c:forEach>

       <br>
       <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
       <li class="btns"> <input id="btnExport" onclick="derive()" class="btn btn-primary" type="button" value="导出"/></li>
       <li class="btns"> <input type="button" class="btn btn-primary" id="condition" value="统计条件 ↓"></li>
       <li class="btns"><input id="btnCancel2" class="btn" type="button" value="返 回" onclick="history.go(-1)" style="display: none;"/></li>
       <li class="clearfix"></li>

       <div style="display: none" id="conditionDiv">
           <ul>
               <li>
                   <span style="float: left">根据 &nbsp;&nbsp;&nbsp;</span>
                   <%--<c:if test="${newFieldList != null && fn:length(newFieldList) >0}">--%>
                       <%--<c:forEach items="${rawFieldList}" var="rawField">--%>
                           <%--*--%>
                           <%--<c:forEach items="${newFieldList}" var="newField" varStatus="index">--%>
                               <%--+--%>
                               <%--<c:if test="${rawField.fieldName == newField.fieldName}">--%>
                                   <%--<div style="float: left;"><sapn>bbb</sapn>--%>
                                       <%--<input id="${rawField.fieldName}checkbox" checked="checked" name="conditionGroupField" value="${rawField.fieldName}" type="checkbox">${rawField.remarks}--%>
                                   <%--</div>--%>
                                   <%--<c:set var="isDoing" value="1"/>--%>
                               <%--</c:if>--%>
                               <%--<c:if test="${rawField.fieldName != newField.fieldName && index.index == fn:length(newFieldList)-1 && isDoing!=1}">--%>
                                   <%--<div style="float: left;"><sapn>ccc</sapn>--%>
                                       <%--<input id="${rawField.fieldName}checkbox" name="conditionGroupField" value="${rawField.fieldName}" type="checkbox">${rawField.remarks}--%>
                                   <%--</div>--%>
                               <%--</c:if>--%>
                           <%--</c:forEach>--%>
                       <%--</c:forEach>--%>
                   <%--</c:if>--%>
                   <%--<c:if test="${newFieldList == null || fn:length(newFieldList) <= 0}">--%>
                        <c:forEach var="rawField" items="${rawFieldList}">
                            <div style="float: left;">
                                <input id="${rawField.fieldName}checkbox" name="conditionGroupField" value="${rawField.fieldName}" type="checkbox">${rawField.remarks}
                            </div>
                        </c:forEach>
                   <%--</c:if>--%>
                   <%--<c:forEach items="${fields}" var="field" varStatus="i">--%>
                       <%--<div style="float: left;">--%>
                           <%--<input id="${field.fieldName}checkbox" name="conditionGroupField" value="${field.fieldName}" type="checkbox">${field.remarks}--%>
                       <%--</div>--%>
                   <%--</c:forEach>--%>
               </li>
               <li>
                   <span style="float: left">统计 &nbsp;&nbsp;&nbsp;</span>
                   <c:forEach items="${fields}" var="field">
                       <div id="radio${field.fieldName}" style="float: left; display: none">
                           <input type="radio"  name="conditionSumField" value="${field.fieldName}">${field.remarks}
                       </div>
                   </c:forEach>
               </li>
           </ul>
           <li class="btns"><input type="button" id="inquire" class="btn btn-primary" style="float: left" value="查询"></li>
           <li class="btns"> <input id="statisticsExport" onclick="derive()" class="btn btn-primary" style="float: left" type="button" value="导出"/></li>
           <li class="btns"> <input type="button" class="btn btn-primary" id="statisticsCondition" style="float: left" value="统计条件 ↑"></li>
           <li class="btns"><input type="button" id="statistics" class="btn btn-primary" value="统计结果查询" style="float: left"></li>
           <li class="btns"><input id="btnCancel1" class="btn" type="button" value="返 回" onclick="history.go(-1)" style="display: none"/></li>
       </div>
   </form:form>

   <table id="contentTable" class="table table-striped table-bordered table-condensed">
       <thead>
       <tr>
           <th>序号</th>
           <input id="fieldsLength" value="${fn:length(fields)}" type="hidden">
           <c:forEach items="${fields}" var="field" varStatus="i">
               <th>${field.remarks}</th>
               <input type="hidden" name="" id="field${i.count}" value="${field.fieldName}">
           </c:forEach>
               <th style="min-width: 100px; " id="ycClass">操作</th>
       </tr>
       </thead>
       <tbody>
       <tr id="topTr" class="total">

       </tr>
       <c:forEach items="${list}" var="data" varStatus="numericalOrder">
           <tr>
               <td name="lineNum" style='font-size: 15px;text-align: center;'></td>
               <c:forEach items="${fields}" var="fie" varStatus="i">
               <c:set var='zd' value="${fie.fieldName}" scope="page"/>
               <c:set var="dataType" value="${fie.dataType}" scope="page"/>
                   <c:if test="${dataType != null && dataType != ''}">
                       <input type="hidden" id="numerical${zd}${numericalOrder.index}" value="${fns:getDictLabel(data[zd],dataType,'')}">
                   </c:if>
                   <c:if test="${dataType == null || dataType == ''}">
                       <input type="hidden" id="numerical${zd}${numericalOrder.index}" value="${data[zd]}">
                   </c:if>
               <td name="${zd}" style="font-size: 15px;text-align: center;">
                   <c:if test="${dataType != null && dataType != ''}">

                       ${fns:getDictLabel(data[zd],dataType,'')}
                   </c:if>
                   <c:if test="${dataType == null || dataType == ''}">
                       ${data[zd]}
                   </c:if>
               </td>
               </c:forEach>
               <c:if test="${newFieldNameList == null}">
               <td style="font-size: 15px;text-align: center;">
                   <a href="/a/oa/flow/form?id=${data.id}&formNo=${tableName}&showType=flowView&act.procInsId=${data.procInsId}"><input type="button" class="btn" value="查看详情"></a>
               </td>
               </c:if>
               <c:if test="${newFieldNameList != null}">
                   <td style="font-size: 15px;text-align: center;">
                       <input type="button" onclick="statisticalDetails('${numericalOrder.index}','${tableName}','${tableId}')" class="btn" value="查看详情">
                   </td>
               </c:if>
           </tr>
       </c:forEach>
       <tr id="total" class="total">

       </tr>
       </tbody>
   </table>
   <div class="pagination">
       <input id="amountTo" class="btn btn-primary" type="button" value="合 计" style="display: none">
       <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
   </div>
<script type="text/javascript">
    //导出excel
    function derive() {
        document.getElementById("ycClass").style.display = 'none';
        $('#contentTable').tableExport({type:'excel',escape:'false'});
        document.getElementById("ycClass").style.display = '';
    }

    $("#inquire").click(function () {
        $("input[name='conditionSumField']").prop("checked",false);
        $("input[name='conditionGroupField']").prop("checked", false);
        $("#btnSubmit").click();
    })

    $("#condition").click(function () {
        var newFieldList = new Array();
        <c:forEach items="${newFieldNameList}" var="item">
        newFieldList.push("${item}");
        </c:forEach>
        if (newFieldList != null && newFieldList.length){
            $.each(newFieldList,function (index,object) {
                $("#"+object+"checkbox").prop("checked", true);
            })
        }else{
            $("input[name='conditionGroupField']").eq(0).prop("checked", true);
        }
            $("#btnSubmit").css("display","none");
            $("#btnExport").css("display","none");
            $("#condition").css("display","none");
            $("#btnCancel2").css("display","none");
            $("#conditionDiv").css("display","block");
    })
        $("#statisticsCondition").click(function () {
            $("#btnSubmit").css("display","block")
            $("#btnExport").css("display","block")
            $("#condition").css("display","block")
            $("input[name='conditionSumField']").prop("checked",false);
            $("input[name='conditionGroupField']").prop("checked", false);
            $("#conditionDiv").css("display","none");
        })
    $("#statistics").click(function () {
        var par = $("input[name='conditionSumField']:checked").val();
        var p = $('input[name="conditionGroupField"]:checked').val();
        if (par != null && par != "" && p.length >0){
            $("#btnSubmit").click();
        }else {
            alert("请选择统计条件");
        }
    })
</script>

<%--条件分组统计后查看详情--%>
<script type="text/javascript">
    function statisticalDetails(numericalOrder,tableName,tableId) {
        var parameter = "";
        var a;
        <c:forEach items="${newFieldNameList}" var="item">
        a="${item}";
        var zhi = $("#numerical"+a+numericalOrder).val();
        var b = "${item} ='"+zhi+"'";
        parameter = parameter + "&ParameterField="+b;
        </c:forEach>
        var url = "${ctx}/OaStatistics/oaStatistics/statisticalDetails?tableName="+tableName+"&tableId="+tableId+parameter;
        layerTree =layer.open({
            title:'统计详情列表',
            type: 2,
            area: ['95%', '90%'],
            offset:'2%',
            fixed: false, //不固定
            maxmin: false,
            content: url
        });
    }
</script>
<%--数据序列--%>
<script type="text/javascript">
    $(function(){
        var lineNum=$("td[name='lineNum']");
        for (var i=0;i<lineNum.length;i++)
        lineNum[i].innerHTML=i+1;
    })
</script>

<%--合计--%>
<script type="text/javascript">
    function xieyi(){
        $("#amountTo").trigger("click");
    }
    $(document).ready(function(){
        window.onload=xieyi;
    });

    $("#amountTo").click(function () {
        var line = $("#fieldsLength").val();
        var lineNum=$("td[name='lineNum']");
        $("#total").append("<td>合计</td>");
        if (lineNum.length<=20){
            $("#topTr").css("display","none");
        }
        if (lineNum.length>20) {
            $("#topTr").append("<td>合计</td>");
            $("#btnCancel1").css("display","block");
            $("#btnCancel2").css("display","block");
        }
        var flag = 0;
        for (var i = 1; i <= line; i++) {
            var fieldName = $("#field" + i).val();
            var datas = $("td[name=" + fieldName + "]");
            var sum = 0;
            var isNum = 0;    //为数字
            var noIsNum = 0;    //不为数字
            var dataSet = new Array();
                $.each(datas, function (index1, object1) {
                if($.inArray(object1.innerHTML,dataSet)==-1) {
                    dataSet.push(object1.innerHTML);
                }
                if (!isNaN(object1.innerHTML)) {
                    isNum = isNum + 1;
                } else {
                    noIsNum = noIsNum + 1;
                }
            });
            if (isNum <= noIsNum) {
                $("#total").append("<td>" + dataSet.length + "</td>");
                if (lineNum.length>20) {
                    $("#topTr").append("<td>" + dataSet.length + "</td>");
                }
            } else if (isNum > noIsNum) {
                //显示统计字段
                flag=1;
                $("#radio"+fieldName).css("display","block");
                $.each(datas, function (index, object) {
                    var data=object.innerHTML;
                    var num = 0;
                    if (data != null && data != "") {
                        num = parseFloat(object.innerHTML);
                        if (isNaN(num)) {
                            num = 0;
                        }
                    }
                    sum = sum + num;
                    if (index == datas.length - 1) {
                        $("#total").append("<td>" + sum + "</td>");
                        if (lineNum.length>20){
                            $("#topTr").append("<td>" + sum + "</td>");
                        }
                    }
                })
            }
        }
        if (flag == 0){
            $("#condition").css("display","none");
        }
        $("#total").append("<td style='font-size: 15px;text-align: center;'>&nbsp;</td>");
        $("#topTr").append("<td style='font-size: 15px;text-align: center;'>&nbsp;</td>");
    });
</script>
<%--配置查询条件--%>
<%--1、获取已选或未选字段--%>
<script type="text/javascript">
    $("#fieldType").change(function () {
        modality();
        $("#symbol").find("option").remove();
        var logo=$("#fieldType").val()
        $.ajax({
            type : "post",
            async : true,
            url : '${ctx}/OaStatistics/oaStatistics/fieldType?tableId=${table.id}&flag='+logo,
            success:function(data){
                $("#conditionField").find("option").remove();
                $("#conditionField").prepend("<option></option>");
                $.each(data,function (c,d) {
                    $("#conditionField").append("<option value='"+d.fieldName+"' columnType='"+d.columnType+"' controlTypeId='"+d.controlTypeId+"'>"+d.remarks+"" +
                        "</option>")
                })
            },
            error: function() {
            }
        });
    })

//    2、获取条件符号
    $("#conditionField").change(function () {
        modality();
        $("#symbol").find("option").remove();
        $("#symbol").prepend("<option></option>");
        var field = $("#conditionField").val();
        var columnType = $("#conditionField").find('option:selected').attr("columnType");
        var controlTypeId = $("#conditionField").find('option:selected').attr("controlTypeId");
        if (columnType == "DATE" || columnType == "NUMERIC"){
            $("#symbol").append("<option value='>'>大于</option>");
            $("#symbol").append("<option value='<'>小于</option> ");
            $("#symbol").append("<option value='>='>大于等于</option>");
            $("#symbol").append("<option value='<='>小于等于</option>");
            $("#symbol").append("<option value='='>等于</option> ");
            $("#symbol").append("<option value="!=">不等于</option>");
        }else {
            $("#symbol").append("<option value='='>等于</option> ");
            $("#symbol").append("<option value='!='>不等于</option>");
            $("#symbol").append("<option value='like'>类似于</option>");
        }
//        生成控件
        if (controlTypeId == "date" || controlTypeId == "date1" ||controlTypeId == "datetime" ||controlTypeId == "currentDate" ||controlTypeId == "currentDateTime"){
            $("#inputModeDate").css("display","inline");
        }else if (controlTypeId == "selectUsers" || controlTypeId == "selectUser"){
            $("#inputModeUser").css("display","inline");
        }else if (controlTypeId == "selectOffice" || controlTypeId == "currentUserOffice") {
            $("#inputModeDict").css("display","inline");
        }else if (controlTypeId == "currentUserCompany"){
            $("#inputModeOrg").css("display","inline");
        }else {
            $("#inputText").css("display","inline");
        }
    })
    
    function modality() {
        $("#inputModeDate").css("display","none");
        $("#inputText").css("display","none");
        $("#inputModeOrg").css("display","none");
        $("#inputModeUser").css("display","none");
        $("#inputModeDict").css("display","none");
        $("#date").val(null);
        $("#import").val(null);
        $("#company2Name").val(null);
        $("#agentidName").val(null);
        $("#officeName").val(null);
        $("#symbol").find("option").remove();
    }
</script>

<%--添加查询条件--%>
    <script type="text/javascript">
        $("#addCondition").click(function () {
            var field = $("#conditionField").val();
            var fieldTxt = $("#conditionField").find('option:selected').html();
            var symbol = $("#symbol").val();
            var symbolTxt = $("#symbol").find('option:selected').html()
            var statementTxt = fieldTxt+" "+symbolTxt;
            var statement = field +" "+symbol;
            var inputModeDate = $("#date").val();
            var inputText = $("#import").val();
            var inputModeOrg = $("#company2Name").val();
            var inputModeUser = $("#agentidName").val();
            var inputModeDict = $("#officeName").val();
            if (compare(field)&&compare(symbol)&&((compare(inputModeDate)||compare(inputModeDict)||compare(inputModeOrg)|| compare(inputModeUser)||compare(inputText)))) {
                if (compare(inputModeDate)) {
                    statement = statement + " '" + inputModeDate + "'";
                    statementTxt = statementTxt +" "+inputModeDate;
                } else if (compare(inputText)) {
                    if (symbol != "like") {
                        statement = statement + " '" + inputText + "'";
                    } else {
                        statement = statement + " '%" + inputText + "%'";
                    }
                    statementTxt = statementTxt +" "+inputText;
                } else if (compare(inputModeOrg)) {
                    if (symbol != "like") {
                        statement = statement + " '" + inputModeOrg + "'";
                    } else {
                        statement = statement + " '%" + inputModeOrg + "%'";
                    }
                    statementTxt = statementTxt +" "+inputModeOrg;
                } else if (compare(inputModeUser)) {
                    if (symbol != "like") {
                        statement = statement + " '" + inputModeUser + "'";
                    } else {
                        statement = statement + " '%" + inputModeUser + "%'";
                    }
                    statementTxt = statementTxt +" "+inputModeUser;
                } else if (compare(inputModeDict)) {
                    if (symbol != "like") {
                        statement = statement + " '" + inputModeDict + "'";
                    } else {
                        statement = statement + " '%" + inputModeDict + "%'";
                    }
                    statementTxt = statementTxt +" "+inputModeDict;
                }
                    var myDate = new Date();
                    var id = myDate.getTime();
                    $("#existingConditions").append("<input type='hidden' id='"+id+"' name='existingConditions' value=''><input type='hidden' id='Name"+id+"' name='existingConditionsName' value=''><li style='display: block; margin-left: 80px;color: red' id='li"+id+"'><span>" + statementTxt + "</span>" +
                        "" +
                        "<a href='#' onclick='subtract("+id+")'><img src='${ctxStatic}/images/subtract.jpg' style='width: 30px;height: 30px'>删除条件</a></li>")
                    $("#"+id).val(statement);
                    $("#Name"+id).val(statementTxt);
                modality();
                $("#conditionField").find('option:selected').remove();
                $("#fieldType option:first").prop("selected", 'selected');
                } else {
                    alert("某字段为空，请检查")
                }
        })

        function compare(data) {
            if (data != null && data != ""){
                return true;
            }else {
                return false
            }
        }

        function subtract(id) {
            $("#li"+id).remove()
        }
    </script>
</body>
</html>

