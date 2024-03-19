<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>日程安排管理</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
    <style>
        #btnMenu{
            display: none;}
        .breadcrumb{ padding:25px 15px}
        .title_Infor{
            width: 100%;
            float: left;
            background-color: #33aafb;
            border-bottom: none;
            margin-top:0px !important;
            line-height: 30px;
            height: 37px;
        }
        .title_Infor>a{
            color: #fff;}
        .title_Infor img{
            height:18px;
        }
        .title_Infor h3{
            width: calc(100% - 100px);
            color: #fff;
            font-size: 18px;
            text-align: center;
            margin: 0;
            float: left;
        }
        #searchForm{
            float: left;}
        .form-horizontal .control-label {
            float: left;
            width: 65px;
            padding-top: 5px;
            text-align: right;
        }

        .controls{

        }
    </style>
</head>
<body>
<!--tou-->
<div class="title_Infor" style="margin-top: 0px;margin-bottom: 20px">
    <a href="/a/oa/oaSchedule" class="pull-left" style="margin: 4px"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>日程安排管理</h3>

</div>
<form:form id="inputForm" modelAttribute="oaSchedule" action="${ctx}/oa/oaSchedule/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label style="width: 66px">内容：</label>
        <div>
            <form:textarea path="content" cssStyle="width: 97%;" htmlEscape="false" rows="4" maxlength="2000" class="required "/>

        </div>
    </div>
    <div class="control-group">
        <label class="control-label">重要等级：</label>
        <div>
            <form:radiobuttons path="importantLevel" items="${fns:getDictList('oa_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">缓急程度：</label>
        <div>
            <form:radiobuttons path="emergencyLevel" items="${fns:getDictList('oa_schedule_import')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">日志日期：</label>
        <div>
            <input name="scheduleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${oaSchedule.scheduleDate}" pattern="yyyy-MM-dd HH:mm"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">完成状态：</label>
        <div>
            <form:radiobuttons path="flag" items="${fns:getDictList('oa_schedule_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div>
        <c:if test="${oaSchedule.flag ne '1'}">
            <shiro:hasPermission name="oa:oaSchedule:edit">
                <input id="btnSubmit" class="btn btn-primary" style="width: 100%;height: 40px" type="submit" value="保 存"/>&nbsp;
            </shiro:hasPermission>
        </c:if>
        <input id="btnCancel" class="btn" type="button"  style="width: 100%;height: 40px; margin-top: 10px" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>