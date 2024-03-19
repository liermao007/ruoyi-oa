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
    <style type="text/css">
        .total td{
            color: #9c0001;
            font-size: 15px;
            text-align: center;
        }
    </style>
    <script type="text/javascript" src="${ctxStatic}/deriveExcel/jquery.base64.js"></script>
    <script type="text/javascript" src="${ctxStatic}/deriveExcel/tableExport.js"></script>
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

<script type="text/javascript">
    var fields=$('td [name=td1]').children().length;
    var width=1/fields;
    $("td").css("width",width)
</script>


   <ul class="breadcrumb">
       <li class="active"><a href="${ctx}/OaStatistics/oaStatistics/index">统计列表</a></li>
   </ul>
   <form:form id="searchForm" modelAttribute="dataParameter" action="${ctx}/OaStatistics/oaStatistics/gettingAllData?TJMCID=${TJMCID}" method="post" class="breadcrumb form-search">
       <%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
       <%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
       <li class="ul-form">
           <%--<li><label>申请日期：</label>--%>
               <%--<input name="applicationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                      <%--value="<fmt:formatDate value="${applicationDate}" pattern="yyyy-MM-dd"/>"--%>
                      <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
           <%--</li>--%>
           <li><label>开始日期：</label>
       <input name="startDate" type="text" readonlygo="readonly" maxlength="20"
              class="input-medium Wdate" style="width:163px;"
              value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"
              onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               <%--<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                      <%--value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"--%>
                      <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
           </li>
           <li><label>结束日期：</label>
               <input name="endDate" type="text" readonlygo="readonly" maxlength="20"
                      class="input-medium Wdate" style="width:163px;"
                      value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"
                      onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               <%--<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                      <%--value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"--%>
                      <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
           </li>
           <li><label>申请人：</label>
               <%--<c:set var="proposer" value="${proposer}"></c:set>--%>
               <sys:treeselect id="agentid" name="proposer"
                               value="${proposer}" labelName="name"
                               labelValue="${fns:getNameByID(proposer)}"
                               title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                               notAllowSelectParent="true" cssStyle="width:80px"/>
           </li>
           <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
           <li class="clearfix"><input id="btnExport" class="btn btn-primary" type="button" onclick="derive()" value="导出"/></li>
       <li class="clearfix"><input id="btnCancel1" class="btn" type="button" value="返 回" onclick="history.go(-1)"/></li>
       </ul>
   </form:form>
   <sys:message content="${message}"/>

   <table id="contentTable" class="table table-striped table-bordered table-condensed">

       <thead>
       <tr>
           <th style="font-size: 15px;text-align: center;">序号</th>
           <input id="fieldsLength" value="${fn:length(fields)}" type="hidden">
           <c:forEach items="${fields}" var="field" varStatus="i">
               <th style="font-size: 15px;text-align: center;">${field.remarks}</th>
               <input type="hidden" id="field${i.count}" value="${field.fieldName}">
           </c:forEach>
       </tr>
       </thead>
       <tbody>
       <tr id="topTr" class="total">

       </tr>
       <c:forEach items="${data}" var="data">
           <tr>
               <td name="lineNum" style="font-size: 15px;text-align: center;"></td>
               <c:forEach items="${fields}" var="fie" varStatus="i">
                   <c:set var='zd' value="${fie.fieldName}" scope="page"/>
                   <c:set var="dataType" value="${fie.dataType}" scope="page"/>
                   <td name="${zd}" style="font-size: 15px;text-align: center;">
                       <c:if test="${dataType != null && dataType != ''}">
                           ${fns:getDictLabel(data[zd],dataType,'')}
                       </c:if>
                       <c:if test="${dataType == null || dataType == ''}">
                           ${data[zd]}
                       </c:if>
                   </td>
               </c:forEach>
           </tr>
       </c:forEach>
       </tbody>
       <tr id="total" class="total">

       </tr>
   </table>
   <div class="pagination">
       <input id="amountTo" class="btn btn-primary" type="button" value="合 计" style="display: none"/>
       <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
   </div>

<%--数据序列--%>
<script type="text/javascript">
    $(document).ready(function(){
        var lineNum=$("td[name='lineNum']");
        for (var i=0;i<lineNum.length;i++)
            lineNum[i].innerHTML=i+1;
    })

    //导出excel
    function derive() {
        $('#contentTable').tableExport({type:'excel',escape:'false'});
    }
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
        $("#total").append("<td>"+"合计"+"</td>");
        $("#topTr").append("<td>"+"合计"+"</td>");
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
                $("#topTr").append("<td>" + dataSet.length + "</td>");
            } else if (isNum > noIsNum) {
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
                        $("#topTr").append("<td>" + sum + "</td>");
                    }
                })
            }
        }
//        $("#total").append("<td>&nbsp;</td>");
//        $("#topTr").append("<td>&nbsp;</td>");
    });
</script>

</body>
</html>
