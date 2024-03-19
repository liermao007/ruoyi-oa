<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>保存医疗运营报表成功管理</title>
    <meta name="decorator" content="blank"/>
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
        }

        .control-group table tr {
            height: 50px;
        }

        .control-group table tr td {
            min-width: 100px;
            text-align: -webkit-right;
        }

        .control-group table {
            margin: 0 5%;
        }

        .title_Infor {
            width: 100%;
            height: 90px;
            position: relative;
            line-height: 90px;
            color: #fff;
            margin: 0 !important;
            padding: 0;
            border-bottom: 1px solid #dbdbdb;
            background: #33aafb;
        }

        .title_Infor a {
            color: #fff;
            font-size: 3em;
            /*line-height: 90px;*/
        }

        .title_Infor h3 {
            color: #fff;
            font-size: 3em;
            font-weight: bold;
            text-align: center;
            padding: 25px 0;
        }

        .control-group {
            font-size: 1.5em;
        }

        .control-group table tr td {
            min-width: 0;
            text-align: left;
            height: 100px;
            font-size: 1.5em;
        }

        .control-group table tr {
            height: 100px;
        }

        .control-label {
            font-size: 1.5em;
        }
        .control-label1 {
            font-size: 1.5em;
        }

        .control-group table tr td span {
            font-size: 1em;
        }

        .row-fluid .span5 {
            width: 100%;
            padding-left: 5%;
        }

        .form-horizontal .control-label {
            width: 40%;
            line-height: 2em;
        }
        .form-horizontal .control-label1 {
            width: 70%;
            line-height: 2em;
        }

        input[disabled], select[disabled], textarea[disabled], input[readonly], select[readonly], textarea[readonly] {
            width: 80%;
            height: 5em;
        }

        .form-search input, .form-inline input, .form-horizontal input, .form-search textarea, .form-inline textarea, .form-horizontal textarea, .form-search select, .form-inline select, .form-horizontal select, .form-search .help-inline, .form-inline .help-inline, .form-horizontal .help-inline, .form-search .uneditable-input, .form-inline .uneditable-input, .form-horizontal .uneditable-input, .form-search .input-prepend, .form-inline .input-prepend, .form-horizontal .input-prepend, .form-search .input-append, .form-inline .input-append, .form-horizontal .input-append {
            display: inline-block;
            margin-bottom: 0;
            vertical-align: middle;
            width: 80%;
            height: 60px;
            font-size: 30px;
        }

        .btn {
            font-size: 4em !important;
            line-height: 100px;
            height: 100px !important;
        }

        .control-group table {
            margin: 0;
        }

        .control-group {
            padding: 0 5%;
        }
    </style>
</head>
<body>
<div class="title_Infor">
    <a href="${ctx}/sys/user/myPhone" class="pull-left"><img
            src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>

    <h3>医疗运营报表</h3>
</div>
<br/>
<form:form id="searchForm" name="searchForm" modelAttribute="medicalOperationReport"
           action="${ctx}/process/medicalOperationReport/form" method="post" class="form-horizontal">
    <div class="control-group row-fluid">
        <div class="span5">
            <label class="control-label">当前日期：</label>

            <div class="controls">
                <input name="currentSumDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${medicalOperationReport.currentSumDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:pickedFunc});"/>
            </div>
        </div>
    </div>
</form:form>
<form:form id="inputForm" modelAttribute="medicalOperationReport" action="${ctx}/process/medicalOperationReport/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <input id="" name="currentSumDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate "
           value="<fmt:formatDate value="${medicalOperationReport.currentSumDate}" pattern="yyyy-MM-dd"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    <sys:message content="${message}"/>
    <div class="control-group row-fluid">
        <table border="0" cellpadding="0" cellspacing="0" align="center" width="100%">
            <tr>
                <td colspan="2" align="left"><label class="control-label"><span class="font">医疗收入：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td width="40%">日收入合计:</td>
                <td width="60%"><form:input type="number" step="0.01" path="dailyIncome" htmlEscape="false"
                                            maxlength="200" readonly="true"
                                            placeholder="日门诊+日住院"/></td>
            </tr>
            <tr>
                <td width="40%">日门诊收入:</td>
                <td width="60%"><form:input type="number" step="0.01" id="dailyOutpatientClinic"
                                            path="dailyOutpatientClinic" htmlEscape="false"
                                            maxlength="200" onchange="daily()"/></td>
            </tr>
            <tr>
                <td width="40%">日住院收入:</td>
                <td width="60%"><form:input type="number" step="0.01" id="dailyHospitalization"
                                            path="dailyHospitalization" htmlEscape="false"
                                            maxlength="200" onchange="daily()"/></td>
            </tr>
            <tr>
                <td colspan="2" align="left"><label class="control-label"><span class="font">门急诊人次：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td width="40%">日门急诊合计:</td>
                <td width="60%"><form:input type="number" id="diurnalUrgency" path="diurnalUrgency" htmlEscape="false"
                                            maxlength="200" readonly="true" placeholder="日门诊人次+日急诊人次"/></td>
            </tr>
            <tr>
                <td width="40%">日门诊人次:</td>
                <td width="60%"><form:input type="number" id="dailyOutpatientNumber" path="dailyOutpatientNumber"
                                            htmlEscape="false"
                                            maxlength="200" onchange="diurnal()"/></td>
            </tr>
            <tr>
                <td width="40%">日急诊人次:</td>
                <td width="60%"><form:input type="number" id="dailyEmergency" path="dailyEmergency" htmlEscape="false"
                                            maxlength="200" onchange="diurnal()"/></td>
            </tr>
            <tr>
                <td colspan="2" align="left"><label class="control-label"><span class="font">住院情况：</span></label></td>
            </tr>
            <tr>
                <td width="40%">入院人数:</td>
                <td width="60%"><form:input type="number" path="admissionNumber" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr>
                <td width="40%">出院人数:</td>
                <td width="60%"><form:input type="number" path="dischargeNumber" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr>
                <td width="40%">在院人数:</td>
                <td width="60%"><form:input type="number" path="hospitalPeople" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td width="40%">月占床日累计:</td>
                <td width="60%"><form:input type="number" path="monthBedDay" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr>
                <td colspan="2" align="left"><label class="control-label"><span class="font">手术台次：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td width="40%">日手术台次合计:</td>
                <td width="60%"><form:input type="number" id="operationNumber" path="operationNumber" htmlEscape="false"
                                            maxlength="200" readonly="true" placeholder="日门诊手术+日住院手术"/></td>
            </tr>
            <tr>
                <td width="40%">日门诊手术:</td>
                <td width="60%"><form:input type="number" id="diurnalOutpatientOperation"
                                            path="diurnalOutpatientOperation"
                                            htmlEscape="false" maxlength="200" onchange="operation()"/></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td width="40%">日住院手术:</td>
                <td width="60%"><form:input type="number" id="dailyOperation" path="dailyOperation" htmlEscape="false"
                                            maxlength="200" onchange="operation()"/></td>
            </tr>
            <tr style="display: none">
                <td colspan="2" align="left"><label class="control-label"><span class="font">均次效率指标：</span></label></td>
            </tr>
            <tr style="display: none">
                <td width="40%">医生人数 :</td>
                <td width="60%"><form:input type="number" path="doctorsNumber" htmlEscape="false" maxlength="200"
                                            onchange="doctor()"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">医均占床日:</td>
                <td width="60%"><form:input type="number" path="bedDay" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">医均门诊量:</td>
                <td width="60%"><form:input type="number" path="outpatientVolumeAverage" htmlEscape="false"
                                            maxlength="200"
                                            readonly="true" placeholder="日门急诊合计/医生人数"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">医均总收入:</td>
                <td width="60%"><form:input type="number" step="0.01" path="totalMedicalIncome" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">护士人数:</td>
                <td width="60%"><form:input type="number" path="nursesNumber" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">护均占床日:</td>
                <td width="60%"><form:input type="number" path="nursesBedDay" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">护均总收入:</td>
                <td width="60%"><form:input type="number" step="0.01" path="nursesMedicalIncome" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">医技人数:</td>
                <td width="60%"><form:input type="number" path="medicalPeopleNumber" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr style="display: none">
                <td width="40%">医技科人均总收入:</td>
                <td width="60%"><form:input type="number" step="0.01" path="medicalDepartmentTotalIncome"
                                            htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">人数与成本：</span></label></td>
            </tr>
            <tr >
                <td width="40%">医院总人数:</td>
                <td width="60%"><form:input type="number" path="totalNumHospital" id="totalNumHospital" htmlEscape="false"
                                            maxlength="200" onchange="logictic()"/></td>
            </tr>
            <tr >
                <td width="40%">医生人数:</td>
                <td width="60%"><form:input type="number" path="doctorNum" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">护士人数:</td>
                <td width="60%"><form:input type="number" path="nurseNum" id="nurseNum" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">医技人数:</td>
                <td width="60%"><form:input type="number" path="medicalNumber" id="medicalNumber" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">医辅人数:</td>
                <td width="60%"><form:input type="number" path="medicalAuxiliaryNum" id="medicalAuxiliaryNum" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">行政人数:</td>
                <td width="60%"><form:input id="administrativeNumber" type="number" path="administrativeNumber" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">后勤人数:</td>
                <td width="60%"><form:input type="number" path="logisticalNumber" id="logisticalNumber" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">人力资源总成本:</td>
                <td width="60%"><form:input type="number" step="0.01" path="totalCostPeople" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">医院总成本:</td>
                <td width="60%"><form:input type="number" step="0.01" path="totalCostHospital" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>


            <tr>
                <td colspan="6" align="left"><label class="control-label1"><span class="font">人力资源大型设备检查人次：</span></label></td>
            </tr>
            <tr >
                <td width="40%">CT检查人次:</td>
                <td width="60%"><form:input type="number"  path="ctCheck" htmlEscape="false"
                                maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">CT医生人数:</td>
                <td width="60%"><form:input type="number"  path="ctDoctor" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">超声检查人次:</td>
                <td width="60%"><form:input type="number" path="ultrasonicCheck" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">超声医生人数:</td>
                <td width="60%"><form:input type="number"  path="ultrasonicDoctor" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">X射线检查人次:</td>
                <td width="60%"><form:input type="number"  path="xCheck" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">X射线医生人数:</td>
                <td width="60%"><form:input type="number" path="xDoctor" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">核磁检查人次:</td>
                <td width="60%"><form:input type="number" path="nmrCheck" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
            <tr >
                <td width="40%">核磁医生人数:</td>
                <td width="60%"><form:input type="number" path="nmrDoctor" htmlEscape="false"
                                            maxlength="200"/></td>
            </tr>
        </table>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script type="application/javascript">
    //计算日收入合计
    function daily() {
        $("#dailyIncome").val(Number($("#dailyOutpatientClinic").val()) + Number($("#dailyHospitalization").val()));
    }
    //计算日门急诊合计
    function diurnal() {
        $("#diurnalUrgency").val(Number($("#dailyOutpatientNumber").val()) + Number($("#dailyEmergency").val()));
        var doctorNumber = $("#doctorsNumber").val()
        var ds = Number($("#dailyOutpatientNumber").val()) + Number($("#dailyEmergency").val())
        if (ds != null && ds != "" && doctorNumber != null && doctorNumber != "") {
            //计算医均门诊量
            var ss = ds / doctorNumber;
            $("#outpatientVolumeAverage").val(ss);
        }
    }

    //计算行政后勤人数:
    function logictic() {
        $("#logisticNum").val(Number($("#totalNumHospital").val()) - Number($("#medicalNum").val()));
    }
    //计算日手术台次合计
    function operation() {
        $("#operationNumber").val(Number($("#diurnalOutpatientOperation").val()) + Number($("#dailyOperation").val()));
    }

    //计算医均门诊量
    function doctor() {
        var doctorNumber = $("#doctorsNumber").val()
        var ds = Number($("#dailyOutpatientNumber").val()) + Number($("#dailyEmergency").val())
        if (ds != null && ds != "" && doctorNumber != null && doctorNumber != "") {
            //计算医均门诊量
            var ss = ds / doctorNumber;
            $("#outpatientVolumeAverage").val(ss);
        }
    }
    $("#btnSubmit").click(function () {
        nullToZero();//把页面中''的标签填入0值，因为后台是int类型，若是不填入0值，保存会报错
        $("#inputForm").submit();//触发提交事件
    });
    //把页面中''的标签填入0值，因为后台是int类型，若是不填入0值，保存会报错(点击“保存”时调用)
    function nullToZero() {
        nullIntToZero('dailyIncome');		// 日收入
        nullIntToZero('dailyOutpatientClinic');		// 日门诊收入
        nullIntToZero('dailyHospitalization');		// 日住院收入
        nullIntToZero('diurnalUrgency');		// 日门急
        nullIntToZero('dailyOutpatientNumber');		// 日门诊人次
        nullIntToZero('dailyEmergency');		// 日急诊人次
        nullIntToZero('admissionNumber');		// 入院人数
        nullIntToZero('dischargeNumber');		// 出院人数
        nullIntToZero('hospitalPeople');		// 在院人数
        nullIntToZero('operationNumber');		// 日手术台次
        nullIntToZero('diurnalOutpatientOperation');		// 日门诊手术
        nullIntToZero('dailyOperation');		// 日住院手术
        nullIntToZero('doctorsNumber');		// 医生人数
        nullIntToZero('outpatientVolumeAverage');		// 医均门诊量
        nullIntToZero('bedDay');		// 医均占床日
        nullIntToZero('totalMedicalIncome');		// 医均总收入
        nullIntToZero('nursesNumber');		// 护士人数
        nullIntToZero('nursesBedDay');		// 护均占床日
        nullIntToZero('nursesMedicalIncome');		// 护均总收入
        nullIntToZero('medicalPeopleNumber');		// 医技人数
        nullIntToZero('medicalDepartmentTotalIncome');		// 医技科人均总收入
        nullIntToZero('monthBedDay');
        nullIntToZero('doctorNum');
        nullIntToZero('totalCostHospital');
        nullIntToZero('totalNumHospital');
        nullIntToZero('totalCostPeople');
        nullIntToZero('ctCheck');
        nullIntToZero('ctDoctor');
        nullIntToZero('ultrasonicCheck');
        nullIntToZero('ultrasonicDoctor');
        nullIntToZero('xCheck');
        nullIntToZero('xDoctor');
        nullIntToZero('nmrCheck');
        nullIntToZero('nmrDoctor');
        nullIntToZero('nurseNum');
        nullIntToZero('medicalAuxiliaryNum');
        nullIntToZero('medicalNumber');
        nullIntToZero('administrativeNumber');
        nullIntToZero('logisticalNumber');
    }
    //把页面中''的标签填入0值，因为后台是int类型，若是不填入0值，保存会报错(点击“保存”时调用)
    function nullIntToZero(flag) {
        if ($("#" + flag).val() == "") {
            $("#" + flag).val(0);
        }
    }

    //把页面中的0显示为空''(刚进入页面时调用)
    function zeroToNull() {
        zeroIntToNull('dailyIncome');		// 日收入
        zeroIntToNull('dailyOutpatientClinic');		// 日门诊收入
        zeroIntToNull('dailyHospitalization');		// 日住院收入
        zeroIntToNull('diurnalUrgency');		// 日门急
        zeroIntToNull('dailyOutpatientNumber');		// 日门诊人次
        zeroIntToNull('dailyEmergency');		// 日急诊人次
        zeroIntToNull('admissionNumber');		// 入院人数
        zeroIntToNull('dischargeNumber');		// 出院人数
        zeroIntToNull('hospitalPeople');		// 在院人数
        zeroIntToNull('operationNumber');		// 日手术台次
        zeroIntToNull('diurnalOutpatientOperation');		// 日门诊手术
        zeroIntToNull('dailyOperation');		// 日住院手术
        zeroIntToNull('doctorsNumber');		// 医生人数
        zeroIntToNull('outpatientVolumeAverage');		// 医均门诊量
        zeroIntToNull('bedDay');		// 医均占床日
        zeroIntToNull('totalMedicalIncome');		// 医均总收入
        zeroIntToNull('nursesNumber');		// 护士人数
        zeroIntToNull('nursesBedDay');		// 护均占床日
        zeroIntToNull('nursesMedicalIncome');		// 护均总收入
        zeroIntToNull('medicalPeopleNumber');		// 医技人数
        zeroIntToNull('medicalDepartmentTotalIncome');		// 医技科人均总收入
        zeroIntToNull('monthBedDay');
        zeroIntToNull('totalNumHospital');
        zeroIntToNull('doctorNum');
        zeroIntToNull('totalCostHospital');
        zeroIntToNull('totalCostPeople');
        zeroIntToNull('ctCheck');
        zeroIntToNull('ctDoctor');
        zeroIntToNull('ultrasonicCheck');
        zeroIntToNull('ultrasonicDoctor');
        zeroIntToNull('xCheck');
        zeroIntToNull('xDoctor');
        zeroIntToNull('nmrCheck');
        zeroIntToNull('nmrDoctor');
        zeroIntToNull('nurseNum');
        zeroIntToNull('medicalAuxiliaryNum');
        zeroIntToNull('medicalNumber');
        zeroIntToNull('logisticalNumber');
        zeroIntToNull('administrativeNumber');
    }
    //把页面中的0显示为空''(刚进入页面时调用)
    function zeroIntToNull(flag) {
        if ($("#" + flag).val() == 0) {
            $("#" + flag).val("");
        }
    }
    zeroToNull();//页面刚加载的时候后台int类型的数据会直接显示为0，但是耽误用户填数据，故需要把0显示为空""


</script>
</body>
</html>