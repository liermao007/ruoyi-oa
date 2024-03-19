<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>保存医疗运营报表成功管理</title>
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
    </style>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
    <li class="active">
        ${not empty medicalOperationReport.id?'修改':'添加'}
    </li>
</ul>
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
        <table border="0" cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">医疗收入：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td>日收入合计:</td>
                <td><form:input type="number" step="0.01" path="dailyIncome" htmlEscape="false" maxlength="200"
                                readonly="true"
                                placeholder="日门诊+日住院"/></td>
                <td>日门诊收入:</td>
                <td><form:input type="number" step="0.01" id="dailyOutpatientClinic" path="dailyOutpatientClinic"
                                htmlEscape="false"
                                maxlength="200" onchange="daily()"/></td>
                <td>日住院收入:</td>
                <td><form:input type="number" step="0.01" id="dailyHospitalization" path="dailyHospitalization"
                                htmlEscape="false"
                                maxlength="200" onchange="daily()"/></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">门急诊人次：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td>日门急诊合计:</td>
                <td><form:input type="number" id="diurnalUrgency" path="diurnalUrgency" htmlEscape="false"
                                maxlength="200" readonly="true" placeholder="日门诊人次+日急诊人次"/></td>
                <td>日门诊人次:</td>
                <td><form:input type="number" id="dailyOutpatientNumber" path="dailyOutpatientNumber" htmlEscape="false"
                                maxlength="200" onchange="diurnal()"/></td>
                <td>日急诊人次:</td>
                <td><form:input type="number" id="dailyEmergency" path="dailyEmergency" htmlEscape="false"
                                maxlength="200" onchange="diurnal()"/></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">住院情况：</span></label></td>
            </tr>
            <tr>
                <td>入院人数:</td>
                <td><form:input type="number" path="admissionNumber" htmlEscape="false" maxlength="200"/></td>
                <td>出院人数:</td>
                <td><form:input type="number" path="dischargeNumber" htmlEscape="false" maxlength="200"/></td>
                <td>在院人数:</td>
                <td><form:input type="number" path="hospitalPeople" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">手术台次：</span></label></td>
            </tr>
            <tr style="border-bottom: 1px solid #d4d4d4;">
                <td>日手术台次合计:</td>
                <td><form:input type="number" id="operationNumber" path="operationNumber" htmlEscape="false"
                                maxlength="200" readonly="true" placeholder="日门诊手术+日住院手术"/></td>
                <td>日门诊手术:</td>
                <td><form:input type="number" id="diurnalOutpatientOperation" path="diurnalOutpatientOperation"
                                htmlEscape="false" maxlength="200" onchange="operation()"/></td>
                <td>日住院手术:</td>
                <td><form:input type="number" id="dailyOperation" path="dailyOperation" htmlEscape="false"
                                maxlength="200" onchange="operation()"/></td>
            </tr>
            <tr style="display: none">
                <td colspan="6" align="left"><label class="control-label"><span class="font">均次效率指标：</span></label></td>
            </tr>
            <tr style="display: none">
                <td>医生人数 :</td>
                <td><form:input type="number" path="doctorsNumber" htmlEscape="false" maxlength="200"
                                onchange="doctor()"/></td>
                <td>医均占床日:</td>
                <td><form:input type="number" path="bedDay" htmlEscape="false" maxlength="200"/></td>
                <td>医均门诊量:</td>
                <td><form:input type="number" path="outpatientVolumeAverage" htmlEscape="false" maxlength="200"
                                readonly="true" placeholder="日门急诊合计/医生人数"/></td>
            </tr>
            <tr style="display: none">
                <td>医均总收入:</td>
                <td><form:input type="number" step="0.01" path="totalMedicalIncome" htmlEscape="false"
                                maxlength="200"/></td>
                <td>护士人数:</td>
                <td><form:input type="number" path="nursesNumber" htmlEscape="false" maxlength="200"/></td>
                <td>护均占床日:</td>
                <td><form:input type="number" path="nursesBedDay" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr style="display: none">
                <td>护均总收入:</td>
                <td><form:input type="number" step="0.01" path="nursesMedicalIncome" htmlEscape="false"
                                maxlength="200"/></td>
                <td>医技人数:</td>
                <td><form:input type="number" path="medicalPeopleNumber" htmlEscape="false" maxlength="200"/></td>
                <td>医技科人均总收入:</td>
                <td><form:input type="number" step="0.01" path="medicalDepartmentTotalIncome" htmlEscape="false"
                                maxlength="200"/></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">人数与成本：</span></label></td>
            </tr>
            <tr>
                <td colspan="6" align="left" style=" text-align: -webkit-left;"><span class="font" style="color: red">人数成本和人力资源大型设备检查人次为月数据，自动生成在显示列表中</span></td>
            </tr>
            <tr>
                <td>医院总人数:</td>
                <td><form:input type="number" path="totalNumHospital" id="totalNumHospital" htmlEscape="false" maxlength="200" onchange="logictic()"/></td>
                <td>医生人数:</td>
                <td><form:input type="number" path="doctorNum" id="doctorNum" htmlEscape="false" maxlength="200" /></td>
                <td>护士人数:</td>
                <td><form:input id="nurseNum" type="number" path="nurseNum" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr>
                <td>医技人数:</td>
                <td><form:input type="number" path="medicalNumber" id="medicalNumber" htmlEscape="false" maxlength="200" /></td>
                <td>医辅人数:</td>
                <td><form:input type="number" path="medicalAuxiliaryNum" id="medicalAuxiliaryNum" htmlEscape="false" maxlength="200" /></td>
                <td>后勤人数:</td>
                <td><form:input id="logisticalNumber" type="number" path="logisticalNumber" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr>
                <td>行政人数:</td>
                <td><form:input type="number" path="administrativeNumber" htmlEscape="false" maxlength="200"/></td>
                <td>人力资源总成本:</td>
                <td><form:input type="number" step="0.01" path="totalCostPeople" htmlEscape="false" maxlength="200"/></td>
                <td>医院总成本:</td>
                <td><form:input type="number" step="0.01" path="totalCostHospital" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr>
                <td colspan="6" align="left"><label class="control-label"><span class="font">人力资源大型设备检查人次：</span></label></td>
            </tr>
            <tr>
                <td>CT检查人次:</td>
                <td><form:input type="number"  path="ctCheck" htmlEscape="false"
                                maxlength="200"/></td>
                <td>CT医生人数:</td>
                <td><form:input type="number"  path="ctDoctor" htmlEscape="false"
                                maxlength="200"/></td>
                <td>超声检查人次:</td>
                <td><form:input type="number" path="ultrasonicCheck" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr>
                <td>超声医生人数:</td>
                <td><form:input type="number"  path="ultrasonicDoctor" htmlEscape="false"
                                maxlength="200"/></td>
                <td>X射线检查人次:</td>
                <td><form:input type="number"  path="xCheck" htmlEscape="false"
                                maxlength="200"/></td>
                <td>X射线医生人数:</td>
                <td><form:input type="number" path="xDoctor" htmlEscape="false" maxlength="200"/></td>
            </tr>
            <tr>
                <td>核磁检查人次:</td>
                <td><form:input type="number" path="nmrCheck" htmlEscape="false"
                                maxlength="200"/></td>
                <td>核磁医生人数:</td>
                <td><form:input type="number" path="nmrDoctor" htmlEscape="false"
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
        nullIntToZero('totalNumHospital');
        nullIntToZero('doctorNum');
        nullIntToZero('totalCostHospital');
        nullIntToZero('totalCostPeople');
        nullIntToZero('medicalNum');
        nullIntToZero('logisticNum');
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
        nullIntToZero('logisticalNumber');
        nullIntToZero('administrativeNumber');
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
        zeroIntToNull('medicalNum');
        zeroIntToNull('logisticNum');
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