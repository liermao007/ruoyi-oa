<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>待办任务</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#checkall").click(function() {
                if(this.checked){
                    $(".checkOut").each(function(){this.checked=true;});
                }else{
                    $(".checkOut").each(function(){this.checked=false;});
                }
            });

            $(".checkOut").click(function(e) {
                e.stopPropagation();
            })

            $('#applyEnd').click(function() {
                let data = []
                $(".checkOut").each(function(){
                    if(this.checked) {
                        const trObj = $(this).parents('tr')
                        const taskName = trObj.data('name')
                        if(taskName == '归档') {
                            data.push({
                                taskId: trObj.data('id'),
                                taskDefKey: trObj.data('key'),
                                procInsId: trObj.data('insid'),
                                procDefId: trObj.data('defid')
                            })
                        }
                    }
                });
                if(data.length == 0) {
                    alert('没有要归档的数据')
                } else {
                    loading('正在处理数据，请稍等...');
                    $.ajax({
                        url: '${ctx}/oa/flow/fastSaveAudit',
                        type: 'post',
                        data: JSON.stringify(data),
                        dataType: "json",
                        contentType: 'application/json',
                        success: function (result) {
                            if(result == '1') {
                                location.reload()
                            } else {
                                $("#messageBox").text("部分数据处理有误，请刷新重试");
                            }
                            closeLoading()
                        },
                        error : function(){
                            $("#messageBox").text("部分数据处理有误，请刷新重试");
                            closeLoading()
                        }
                    });
                }
            })

        });
        /**
         * 签收任务
         */
        function claim(taskId) {
            $.get('${ctx}/act/task/claim', {taskId: taskId}, function (data) {
                if (data == 'true') {
                    top.$.jBox.tip('签收完成');
                    location = '${ctx}/act/task/todo/';
                } else {
                    top.$.jBox.tip('签收失败');
                }
            });
        }
    </script>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/task/todo/" method="get"
           class="breadcrumb form-search">
    <div>
        <label>流程类型：&nbsp;</label>
        <form:select path="procDefKey" class="input-medium">
            <form:option value="" label="全部流程"/>
            <form:options items="${fns:getDictList('act_type')}" itemLabel="label" itemValue="value"
                          htmlEscape="false"/>
        </form:select>
        <label>创建时间：</label>
        <input id="beginDate" name="beginDate" type="text" readonlygo="readonly" maxlength="20"
               class="input-medium Wdate" style="width:163px;"
               value="<fmt:formatDate value="${act.beginDate}" pattern="yyyy-MM-dd"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        　--　
        <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               style="width:163px;"
               value="<fmt:formatDate value="${act.endDate}" pattern="yyyy-MM-dd"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
        &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        &nbsp;<input id="applyEnd" class="btn btn-primary" type="button" value="批量归档"/>
    </div>
</form:form>
<%--<sys:message content="${message}"/>--%>
<label id="message" style="color:red; font-weight:bold;">${message}</label>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th align="left"><input id="checkall" type="checkbox"> </th>
        <th>申请人</th>
        <th>当前环节</th>
        <th>流程名称</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${fns:getAuditNews()}" var="news">
        <tr>
            <td>
                <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}">${fns:abbr(news.title,40)}</a>
            </td>
            <td>
                审核官审核
            </td>
            <td>新闻审核</td>
            <td><fmt:formatDate value="${news.createDate}" type="both"/></td>
            <td>
                <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}">任务办理</a>
            </td>
        </tr>
    </c:forEach>
    <c:forEach items="${fns:findCustom()}" var="customRustTaskinst">
            <tr>
                <td>
                        ${customRustTaskinst.apply}
                </td>
                <td>
                    自由流程
                </td>
                <td>自由流程</td>
                <td class="dateTd"><fmt:formatDate value="${customRustTaskinst.startTime}"
                                                   type="both"/></td>
                <td>
                    <a href="${ctx}/process/customProcess/list?procInstId=${customRustTaskinst.procInstId}&taskDefKey=${customRustTaskinst.taskDefKey}&id=${customRustTaskinst.id}">任务办理</a>
                </td>
            </tr>
    </c:forEach>
    <c:forEach items="${list}" var="act">
        <c:set var="task" value="${act.task}"/>
        <c:set var="vars" value="${act.vars}"/>
        <c:set var="procDef" value="${act.procDef}"/>
        <c:set var="procDef" value="${act.procDef}"/><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
        <c:set var="status" value="${act.status}"/>
        <c:set var="CSLoginNames" value="${act.CSLoginNames}"/>
        <tr class="a" data-id="${task.id}" data-name="${task.name}" data-key="${task.taskDefinitionKey}" data-insid="${task.processInstanceId}" data-defid="${task.processDefinitionId}" data-status="${status}">
            <td class="reCheckbox">
                <input type="checkbox" name="checkbox" value="${act.task}" class="checkOut">
            </td>
            <td>
                    ${act.title}
            </td>
            <td>
                    ${task.name}
            </td>
                <%--
                                    <td>${task.description}</td> --%>
                    <c:if test="${not empty act.CSLoginNames}">
                        <%--CSLoginNames用来临时存放被代理人ID--%>
                        <td>(${fns:getNameByID(act.CSLoginNames)})${procDef.name}</td>
                    </c:if>
                    <c:if test="${act.CSLoginNames == null}">
                        <td>${procDef.name}</td>
                    </c:if>

            <td><fmt:formatDate value="${task.createTime}" type="both"/></td>
            <td class="aa">
                <c:if test="${empty task.assignee}">
                    <a href="javascript:claim('${task.id}');">签收任务</a>
                </c:if>
                <c:if test="${not empty task.assignee}"><%--
							<a href="${ctx}${procExecUrl}/exec/${task.taskDefinitionKey}?procInsId=${task.processInstanceId}&act.taskId=${task.id}">办理</a> --%>
                    <c:if test="${act.CSLoginNames != null}">
                        <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}&CSLoginNames=${CSLoginNames}">任务办理</a>
                    </c:if>
                    <c:if test="${act.CSLoginNames == null}">
                        <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">任务办理</a>
                    </c:if>
                </c:if>
                <shiro:hasPermission name="act:process:edit">
                    <c:if test="${empty task.executionId}">
                        <a href="${ctx}/act/task/deleteTask?taskId=${task.id}&reason="
                           onclick="return promptx('删除任务','删除原因',this.href);">删除任务</a>
                    </c:if>
                </shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<link rel="stylesheet" type="text/css" href="/static/css/myjs.css"/>
<script type="text/javascript" src="/static/css/js/myjs.js"></script>
<script type="text/javascript">
    var mes = document.getElementById('message').innerHTML;
    if (mes != null && mes != "") {
        Showbo.Msg.alert(mes);
        setTimeout(function () {
            Showbo.Msg.hide();
        }, 3000);
    }


    $(function () {
        $('.a').each(function (a) {
            $(this).on("click", function () {
                var url = $(".a").eq(a).find("a").attr("href");
                window.location.href = url;

            });
        });
    });

</script>
</body>
</html>
