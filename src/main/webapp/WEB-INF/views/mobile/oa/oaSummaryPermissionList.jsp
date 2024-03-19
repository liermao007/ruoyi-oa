<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>评阅管理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>

    <script type="text/javascript">
        var zTreeObj;
        var zTreeTk;
        var setting = {
            view: {selectedMulti: false, dblClickExpand: false},
            async: {enable: true, url: "${ctx}/sys/user/treeData1", autoParam: ["id=officeId"]},
            data: {simpleData: {enable: true}}, callback: {
                //在单击时
                onClick: function (event, treeId, treeNode) {
                    window.location.href = "${ctx}/oa/oaSummaryPermission/findById?id=" + treeNode.id
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
                    $("#evaluateNames").val(names)
                    $("#ids").val(ids)

                }, onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    var nodes = zTreeTk.getNodesByParam("pId", treeNode.id, null);
                    for (var i = 0, l = nodes.length; i < l; i++) {
                        try {
                            zTreeTk.checkNode(nodes[i], treeNode.checked, true);
                        } catch (e) {
                        }
                        //tree.selectNode(nodes[i], false);
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
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<%--<div style="float: left; width: 150px;"  >
    <ul id="jkTree" class="ztree"></ul>
</div>--%>

<div style="width: 700px;float: left;">
    <form:form id="inputForm" modelAttribute="oaSummaryPermission" action="${ctx}/oa/oaSummaryPermission/save"
               method="post">
        <%--<form:hidden path="evaluateById" id="evaluateBy" ></form:hidden>
        <form:hidden path="evaluateId" id="ids" ></form:hidden>
        <form:hidden path="id"></form:hidden>--%>

        <sys:message content="${message}"/>
        <table align="center">
            <tr style="padding-top: 10px">
                <td>评阅人</td>
                <td>
                    <div class="controls">
                        <sys:treeselect id="evaluateById" name="evaluateById"
                                        value="${oaSummaryPermission.evaluateById}" labelName="name"
                                        labelValue="${oaSummaryPermission.evaluateByNames}"
                                        title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                                        notAllowSelectParent="true" checked="true" cssStyle="width:500px"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td>被评阅人</td>
                <td>
                    <div class="controls">
                        <sys:treeselect id="evaluateId" name="evaluateId"
                                        value="${oaSummaryPermission.evaluateId}" labelName="name"
                                        labelValue="${oaSummaryPermission.evaluateName}"
                                        title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                                        notAllowSelectParent="true" checked="true" cssStyle="width:500px"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input id="akBtnSubmit" class="btn btn-primary" type="submit" value="保 存">
                </td>
            </tr>
        </table>
    </form:form>
</div>
<%--
<div style="float: left;width: 150px;">
    <ul id="TkTree" class="ztree"></ul>
</div>--%>

<script src="${ctxStatic}/tree/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/tree/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
</body>
</html>