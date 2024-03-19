<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>待办流程</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport"/>
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>
</head>
<body style="margin-top:0;">

<!--Header-part-->
<%--<div id="header">--%>
<%--<h1>--%>

<!--<a href="dashboard.html">OA办公系统</a>-->
<%--</h1>--%>
<%--</div>--%>
<div class="title_Infor">
    <a href="${ctx}/sys/user/myPhone" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3 >待办流程</h3>
</div>

<div class="span6">
    <c:if test="${fns:getTodo().size() == 0 and fns:findCustom().size() == 0}">
        <div class="daiban_Con11">
            <div>
                <img src="${ctxStatic}/oaApp/img/daibanliucheng.png"/>
                <span>您暂时还没有待办流程，休息一下吧！</span>
            </div>
        </div>
    </c:if>

    <form:form id="searchForm" modelAttribute="act" action="${ctx}/act/task/todo/" method="get" class="breadcrumb form-search">
        <div>
            <label style="font-size: 12px; width: 100%">创建时间：</label>
            <input id="beginDate"  name="beginDate"  type="text" readonlygo="readonly" maxlength="20" class="input-medium Wdate" style="width:36%;"
                   value="<fmt:formatDate value="${act.beginDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            -
            <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:36%;"
                   value="<fmt:formatDate value="${act.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            &nbsp;<input id="btnSubmit" style="width: 16%;" class="btn btn-primary" type="submit" value="查询"/>
        </div>
    </form:form>

    <div class="widget-box wek_box">
        <c:forEach items="${fns:findCustom()}" var="customRustTaskinst">
            <a href="${ctx}/process/customProcess/list?procInstId=${customRustTaskinst.procInstId}&taskDefKey=${customRustTaskinst.taskDefKey}&id=${customRustTaskinst.id}">
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix"><i>
                    <img class="img-circle img-polaroid" style="width: 50px;height: 50px;"
                         src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                </i>
                    <div class="update-done"><strong class="fontS_16"> ${customRustTaskinst.apply}</strong>
                    </div>
                    <div class="update-date"><span class="update-day" style="font-size: 12px"><fmt:formatDate
                            value="${customRustTaskinst.startTime}"
                            pattern="yyyy-MM-dd"/></span>待办流程
                    </div>
                </div>
                <div class="new-update clearfix pad_T_no">
                    <a href="${ctx}/process/customProcess/list?procInstId=${customRustTaskinst.procInstId}&taskDefKey=${customRustTaskinst.taskDefKey}&id=${customRustTaskinst.id}">
                        <h4>自由流程</h4>
                    </a>
                </div>
            </div>
            </a>
        </c:forEach>
        <c:forEach items="${list}" var="act">
            <c:set var="task" value="${act.task}" />
            <c:set var="vars" value="${act.vars}" />
            <c:set var="procDef" value="${act.procDef}" />
            <c:set var="status" value="${act.status}" />
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix"><i><c:if test="${act.photo==null||act.photo==''}" >
                    <img class="img-circle img-polaroid" style="width: 50px;height: 50px;"
                         src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                </c:if>
                    <c:if test="${act.photo!=null&&act.photo!=''}">
                        <img class="img-circle img-polaroid" style="width: 50px;height: 50px;" src="${act.photo}">
                    </c:if> </i>
                    <div class="update-done"><a title="" href="#"><strong class="fontS_16"> ${act.title}</strong></a>
                        <span></span></div>
                    <div class="update-date"><span class="update-day" style="font-size: 12px"><fmt:formatDate value="${task.createTime}"
                                                                                                              pattern="yyyy-MM-dd"/></span>待办流程</div>
                </div>
                <div class="new-update clearfix pad_T_no">
                    <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${act.status}">
                        <h4> ${act.procDef.name}</h4>
                        <span class="fontS_14"> </span>
                    </a>
                </div>
            </div>
        </c:forEach>




    </div>

</div>

<script type="text/javascript">
    $(function(){
        $('.widget-content').each(function(a){
            $(this).on("click",function(){
                var url =  $(this).find("a").eq(1).attr("href");
//              alert(url)
                window.location.href=url;

            });
        });
    });

</script>
<script type="text/javascript">
    function goPage(newURL) {
        if (newURL != "") {
            if (newURL == "-") {
                resetMenu();
            }
            else {
                document.location.href = newURL;
            }
        }
    }
    function resetMenu() {
        document.gomenu.selector.selectedIndex = 2;
    }
</script>
<!--发布按钮-->


</body>
</html>
