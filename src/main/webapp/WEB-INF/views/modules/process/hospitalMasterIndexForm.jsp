<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>医院经营指标</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
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

        function pickedFunc() {
            $("#searchForm").submit();
        }
    </script>
    <style type="text/css">
        .font {
            color: #000000;
            font-weight: bold;
            font-size: 16px;
        }

        .form-horizontal .control-label {
            text-align: left;
            width: 220px;
        }

        .form-horizontal .control-label2 {
            text-align: left;
            width: 600px;
        }

        .control-group table tr {
            height: 50px;
        }

        .control-group table tr td {
            min-width: 150px;
            text-align: -webkit-right;
        }

        .control-group table {
            margin: 0 5%;
        }
    </style>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
    <li class="active">
        ${not empty hospitalMasterIndex.id?'修改':'添加'}
    </li>
</ul>
<br/>
<form:form id="searchForm" name="searchForm" modelAttribute="hospitalMasterIndex"
           action="${ctx}/process/hospitalMasterIndex/form" method="post" class="form-horizontal">
    <div class="control-group row-fluid">
        <div class="span5">
            <label class="control-label"   style="text-align: right"  >当前月份：</label>

            <div class="controls">
                <input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${hospitalMasterIndex.month}" pattern="yyyy-MM"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,onpicked:pickedFunc});"/>
            </div>
        </div>
    </div>
</form:form>
<form:form id="inputForm" modelAttribute="hospitalMasterIndex" action="${ctx}/process/hospitalMasterIndex/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <input id="" name="month" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate "
           value="<fmt:formatDate value="${hospitalMasterIndex.month}" pattern="yyyy-MM"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
    <sys:message content="${message}"/>
    <div class="control-group row-fluid">
        <table border="0" cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">上交集团现金：</span></label></td>
            </tr>
            <tr> <td colspan="6" align="left" style="text-align: -webkit-left;"><span style="color: red" class="font">以下所有数据均需填入以“元”为单位，例如：40万，需填入400000</span></td>
                </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td>月预算金额:</td>
                <td><form:input type="number" step="0.01" id="groupCashBudgetaryAmountMonths" path="groupCashBudgetaryAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
                <td>月实际金额:</td>
                <td><form:input type="number" step="0.01" id="groupCashActualAmountMonths" path="groupCashActualAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">医疗收入：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td >月预算金额:</td>
                <td ><form:input type="number" step="0.01" id="medicalIncomeBudgetaryAmountMonths" path="medicalIncomeBudgetaryAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
                <td >月实际金额:</td>
                <td><form:input type="number" step="0.01" id="medicalIncomeActualAmountMonths" path="medicalIncomeActualAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">成本支出：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td >月预算金额:</td>
                <td ><form:input type="number" step="0.01" id="costExpenditureBudgetaryAmountMonths" path="costExpenditureBudgetaryAmountMonths"
                                 htmlEscape="false"
                                 maxlength="200" /></td>
                <td >月实际金额:</td>
                <td><form:input type="number" step="0.01" id="costExpenditureActualAmountMonths" path="costExpenditureActualAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">利润：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td >月预算金额:</td>
                <td ><form:input type="number" step="0.01" id="profitBudgetaryAmountMonths" path="profitBudgetaryAmountMonths"
                                 htmlEscape="false"
                                 maxlength="200" /></td>
                <td >月实际金额:</td>
                <td><form:input type="number" step="0.01" id="profitActualAmountMonths" path="profitActualAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">现金运营指数：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td >月预算金额:</td>
                <td ><form:input type="number" step="0.01" id="operationIndexBudgetaryAmountMonths" path="operationIndexBudgetaryAmountMonths"
                                 htmlEscape="false"
                                 maxlength="200" /></td>
                <td >月实际金额:</td>
                <td><form:input type="number" step="0.01" id="operationIndexActualAmountMonths" path="operationIndexActualAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">主渠道采购率：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td >月预算金额:</td>
                <td ><form:input type="number" step="0.01" id="purchasingRateBudgetaryAmountMonths" path="purchasingRateBudgetaryAmountMonths"
                                 htmlEscape="false"
                                 maxlength="200" /></td>
                <td >月实际金额:</td>
                <td><form:input type="number" step="0.01" id="purchasingRateActualAmountMonths" path="purchasingRateActualAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">质量达标：</span></label></td>
            </tr>
            <tr>
                <td colspan="6" align="left" style="text-align: -webkit-left;"><span class="font" style="color: red">月实际分值只能输入小于或者等于100的数值</span></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td >月预计分:</td>
                <td ><form:input type="number" readonly="true" value="60"  step="0.01" id="qualityStandardBudgetaryAmountMonths" path="qualityStandardBudgetaryAmountMonths"
                                 htmlEscape="false"
                                 maxlength="200" /></td>
                <td >月实际分:</td>
                <td><form:input type="number" id="qualityStandardActualAmountMonths" path="qualityStandardActualAmountMonths"
                                htmlEscape="false"
                                maxlength="200" /></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script type="text/javascript">
    $("#btnSubmit").click(function () {
//        nullToZero();//把页面中''的标签填入0值，因为后台是int类型，若是不填入0值，保存会报错
        $("#inputForm").submit();//触发提交事件
    });

//    function checkInt(){
//        var s =  $("qualityStandardActualAmountMonths").val(n);
//        if(s>100){
//            alert("这不是小于"+max+"的正整数！！")
//            return;
//        }else{
//            $("qualityStandardActualAmountMonths").val(n)
//        }
//      }
</script>
</body>
</html>