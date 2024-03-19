<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>每周总结</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>

    <script type="text/javascript">
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
        });
    </script>

    <style>
        table{
            width: 100%;
        }

        .week_Con{ width: 100%; height: auto; float: left;
            }
        .week_Con table tr td{ padding: 0px 20px; line-height: 30px;
            width: 100%; float: left}
        .week_Con input,.week_Inf table tr td input{ width: 100%; height: 30px !important;
        }
        .week_Con textarea,.week_Inf table tr td textarea{ width: 100%;
            height: 60px; }
        .week_Btn{    padding-left: calc(50% - 170px);}
        .week_Btn input{ float: left; padding:5px 15px; width: inherit; border: 0; background: #3daae9 ; margin-right: 20px; color: #fff;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;}


        @media (max-width: 979px) and (min-width: 768px){
            [class*="span"], .row-fluid [class*="span"] {
                width: 100%;
                display: block;
                float: none;
                margin-left: 0;
            }
        }
        @media (width:320px){
            [class*="span"], .row-fluid [class*="span"] {
                width: 100%;
                display: block;
                float: none;
                margin-left: 0;
            }
            .week_Btn{padding-left: calc(50% - 139px);
                float: left;}
            .week_Btn input{ margin-right: 6px;}
        }
        .week_Inf{ padding: 0 20px; margin-top: 20px;
            float: left;}
        .week_Inf table tr{ margin-top: 10px;
            float: left; width: 100%}
        .week_Inf table tr td{ width: 100%; float: left; }
        .week_Week{ width: 100%;
            height: 30px; float: left; line-height: 30px; padding: 0 20px;}
        .week_Week span{ color:#f55}
        .week_Btn>input{
            width: 20%;
            float: left;
            margin-right: 5%;
        }
    </style>
</head>
<body>
<!--tou-->
<div class="title_Infor">
    <a href="${ctx}/sys/menu/tree?parentId=23b5232adcad43b1a9fb89f02047a756" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>周工作总结</h3>

</div>

<form:form id="inputForm" modelAttribute="oaSummaryWeek" action="${ctx}/oa/oaSummaryDay/saveWeek" method="post" style="float:left;width: 100%;
">
    <%--  <form:hidden path="oaVos" value="${oaSummaryWeek.oaVos}"/>--%>
    <form:hidden path="id" value="${oaSummaryWeek.id}"/>
    <form:hidden path="weekOfYear" value="${oaSummaryWeek.weekOfYear}"/>
<!--功能区-->
<div class="span6">

    <div class="week_Con">
        <form name="form1" method="post" action="" style="margin-bottom: 0">
            <label></label>
            <table >
                <tr>
                    <td>下周工作目标 : </td>
                    <td>
                        <form:input path="nextPlanTitle" htmlEscape="false" maxlength="1000" class="input-xlarge " value="${oaSummaryWeek.nextPlanTitle}"/>
                    </td>
                </tr>
                <tr>
                    <td>下周工作计划综述 : </td>
                    <td>
                        <form:textarea path="nextPlanContent" htmlEscape="false" rows="2" maxlength="2000" class="input-xxlarge"  value="${oaSummaryWeek.nextPlanContent}"/>
                    </td>
                </tr>
                <tr>
                    <td>本周工作总结 : </td>

                    <td>
                        <form:textarea path="content" cssStyle="width: 100%;background: #fff;padding-bottom: 0" htmlEscape="false" rows="2" maxlength="2000" class="input-xxlarge "
                                          value="${oaSummaryWeek.content}"/>
                    </td>
                </tr>
                <tr>
                    <td>评阅意见 : </td>

                    <td>
                        <form:textarea path="evaluateContent" htmlEscape="false" rows="2" maxlength="2000" readonly="true" class="input-xxlarge "  value="${oaSummaryWeek.evaluateContent}"/>
                    </td>
                </tr>
            </table>
            <div class="week_Btn">
                <input  id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
                <input id="okBtnSubmit"
                       onclick="location='${ctx}/oa/oaSummaryDay/lackWeek?flag=1&weekOfYear=${oaSummaryWeek.weekOfYear}'"
                       class="btn btn-primary" type="button" value="上一周"/>
                <input id="akBtnSubmit"
                       onclick="location='${ctx}/oa/oaSummaryDay/lackWeek?flag=3&weekOfYear=${oaSummaryWeek.weekOfYear}'"
                       class="btn btn-primary" type="button" value="本  周"/>
                <input id="blackSubmit"
                       onclick="location='${ctx}/oa/oaSummaryDay/lackWeek?flag=2&weekOfYear=${oaSummaryWeek.weekOfYear}'"
                       class="btn btn-warning" type="button" value="下一周"/>
            </div>
        </form>

    </div>
    <div class="week_Week">
        第<span>${oaSummaryWeek.weekOfYear} </span>周
    </div>
    <div class="span6">
        <c:forEach items="${oaSummaryWeek.oaVos}" var="oaSummaryDay">
        <div class="widget-box wek_box">
                <div class="widget-content nopadding updates collapse in bor_N">
                    <div class="new-update clearfix">
                        <div class="update-date" style="float: left; font-size: 15px">${fns:abbr(oaSummaryDay.date,50)}</div>
                    </div>
                    <div class="new-update clearfix pad_T_no">
                        <a><h4>${oaSummaryDay.content}</h4></a>
                        <span class="fontS_14">${oaSummaryDay.status}</span>
                        <span>${oaSummaryDay.appraise}</span>
                    </div>
                </div>
        </div>
        </c:forEach>
    </div>

</div>

</form:form>
<script type="text/javascript">
    // This function is called from the pop-up menus to transfer to
    // a different page. Ignore if the value returned is a null string:
    function goPage (newURL) {
        // if url is empty, skip the menu dividers and reset the menu selection to default
        if (newURL != "") {
            // if url is "-", it is this page -- reset the menu:
            if (newURL == "-" ) {
                resetMenu();
            }
            // else, send page to designated URL
            else {
                document.location.href = newURL;
            }
        }
    }

    // resets the menu selection upon entry to this page:
    function resetMenu() {
        document.gomenu.selector.selectedIndex = 2;
    }
</script>


</body>
</html>
