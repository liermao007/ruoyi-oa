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
        .week_Con{ width: 100%; height: auto; float: left;
        }
        .week_Con table tr td{ padding: 0px 20px; line-height: 30px;
            width: 100%; float: left}
        .week_Con input,.week_Inf table tr td input{ height: 30px !important;
            opacity:1 !important ;height: auto;
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
            .week_Btn{padding-left: calc(50% - 144px);
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
        #uniform-flagBy1,#uniform-flagBy2{
            float: left !important;
            width: 30px !important;
            float: left;
            margin-right: 10px;}
        label{    margin: 0;
            width: 100px;
            display: block;
            margin-bottom: 5px;
            line-height: 36px !important;}
            ul{ list-style: none; margin:0;padding: 0;}
        #s2id_login{
            display:none ;}
        ul>li{width: 100%;
            float: left;}

        #login{display: block !important;
            width: 75%;
            float: left;}

       #btnSeaSubmit {float: right;
            margin-top: 21px;
            margin-right: 7px;}
    </style>
</head>
<body>
<!--tou-->

<div class="title_Infor" style="font-size: 12px">
    <a href="${ctx}/sys/menu/tree?parentId=23b5232adcad43b1a9fb89f02047a756" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>月总结评阅</h3>
</div>
<form:form id="searchForm" modelAttribute="oaSummaryWeek" action="${ctx}/oa/oaSummaryPermission/formId" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li> 　　　　　
            <label style="text-align: left;float: left;">&nbsp;被评阅人：</label>
            <form:select path="login" class="input-medium" cssStyle="width: 200px" onchange="showimage()">
                <form:option value="" label=""/>
                <form:options  items="${fns:getAllPermission()}" itemLabel="name"  itemValue="id" htmlEscape="true" />
            </form:select>
            <form:hidden path="weekOfYear" value="${oaSummaryWeek.weekOfYear}"/>
            <form:hidden path="flag" value="${oaSummaryWeek.flag}"/>

        </li>

    <%--<li class="btns"></li>--%>
        <li class="clearfix"></li>
    </ul>

</form:form>

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
                            <form:textarea path="nextPlanTitle" htmlEscape="false" rows="2" maxlength="2000" readonly="true" class="input-xxlarge "  value="${oaSummaryWeek.nextPlanTitle}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>下周工作计划综述 : </td>
                        <td>
                            <form:textarea path="nextPlanContent" htmlEscape="false" rows="2" maxlength="2000" readonly="true" class="input-xxlarge "  value="${oaSummaryWeek.nextPlanContent}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>本周工作总结 : </td>
                        <td>
                            <form:textarea path="content" cssStyle="width: 280px;padding-bottom: 0" htmlEscape="false" rows="2" maxlength="2000" class="input-xxlarge "     readonly="true" value="${oaSummaryWeek.content}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>评阅意见 : </td>
                        <td>
                            <form:textarea path="evaluate" htmlEscape="false" rows="2" maxlength="2000" readonly="true" class="input-xxlarge "  value="${oaSummaryWeek.evaluate}"/>
                            <%--<form:input path="evaluate" htmlEscape="false" rows="1" style="opacity: 0;" maxlength="2000" class="input-xxlarge "  readonly="true"--%>
                                         <%--value="${oaSummaryWeek.evaluate} " />--%>
                        </td>
                    </tr>
                    <tr>
                        <td>评阅是否公开</td>
                        <td>
                            <form:radiobuttons path="flagBy" items="${fns:getDictList('oa_appraise')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>评阅意见 : </td>
                        <td>
                            <form:input path="evaluateContent" htmlEscape="false" rows="1" maxlength="2000" class="input-xxlarge "
                                         value="${oaSummaryWeek.evaluateContent} " />
                        </td>
                    </tr>
                </table>
                <div class="week_Btn">
                    <input id="btnSubmit"  class="btn btn-primary"  type="submit" value="保 存"/>&nbsp;
                    <input id="okBtnSubmit" onclick="location='${ctx}/oa/oaSummaryPermission/lackWeek?flag=1&weekOfYear=${oaSummaryWeek.weekOfYear}&login=${oaSummaryWeek.loginId}'" class="btn btn-primary" type="button" value="上一周"/>
                    <input id="akBtnSubmit" onclick="location='${ctx}/oa/oaSummaryPermission/lackWeek?flag=3&weekOfYear=${oaSummaryWeek.weekOfYear}&login=${oaSummaryWeek.loginId}'" class="btn btn-primary" type="button" value="本  周"/>
                    <input id="blackSubmit" onclick="location='${ctx}/oa/oaSummaryPermission/lackWeek?flag=2&weekOfYear=${oaSummaryWeek.weekOfYear}&login=${oaSummaryWeek.loginId}'" class="btn btn-warning" type="button" value="下一周"/>

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
                            <a><span style="font-size: 14px">任务完成：</span><h4>${oaSummaryDay.content}</h4></a>
                            <span style="font-size: 14px"><span>工作总结：</span>${oaSummaryDay.status}</span>
                            <span style="font-size: 14px"><span>评阅意见：</span> ${oaSummaryDay.appraise}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>

</form:form>
<script type="text/javascript">
  /*  // This function is called from the pop-up menus to transfer to
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
    }*/
  function showimage() {
      var loginId= $("#login").val();
      if(loginId.length == 0){
          alert("您还没有被评阅人！")
      }else{
          document.all.searchForm.submit();
      }

  };
</script>


</body>
</html>
