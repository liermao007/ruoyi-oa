<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>医疗运营报表管理</title>
    <meta name="decorator" content="default"/>
    <style>
        .tablethead {
            font-weight: bold;
            font-size: 18px;
        }
        .accordion-heading, .table th ,.accordion-heading, .table td {
            white-space: inherit;
            font-weight: normal;
            background-color: #fff;
            background-image: none;
            text-align: center;
        }
        .table thead th {
            vertical-align: middle;
        }
        .table-striped tbody>tr:nth-child(odd)>td, .table-striped tbody>tr:nth-child(odd)>th {
            background-color: #fff;
            text-align: center;
        }
        .table-bordered {
            border: 2px solid #6d6d6d;
            width: 100%;
        }
        .table-bordered th, .table-bordered td {
            width: 9%;
            border-left: 1px solid #6d6d6d;
            border-top: 1px solid #6d6d6d;
        }

        .table-bordered1 {
            border: 2px solid #6d6d6d;
            width: 100%;
        }

        .table-bordered1 th, .table-bordered1 td {
            width: 4%;
            border-left: 1px solid #6d6d6d;
            border-top: 1px solid #6d6d6d;
        }
        .table-bordered2{
            border: 2px solid #6d6d6d;
            width: 100%;
        }

        .table-bordered2 th, .table-bordered2 td {
            width: 5%;
            border-left: 1px solid #6d6d6d;
            border-top: 1px solid #6d6d6d;
        }

        h3 {
            color: #000;
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
        .title_Infor a{
            color: #fff;
            font-size: 2em;
            /*line-height: 90px;*/
        }
        .btns{
            font-size: 2em;
        }

        .form-search input, .form-inline input, .form-horizontal input, .form-search textarea, .form-inline textarea, .form-horizontal textarea, .form-search select, .form-inline select, .form-horizontal select, .form-search .help-inline, .form-inline .help-inline, .form-horizontal .help-inline, .form-search .uneditable-input, .form-inline .uneditable-input, .form-horizontal .uneditable-input, .form-search .input-prepend, .form-inline .input-prepend, .form-horizontal .input-prepend, .form-search .input-append, .form-inline .input-append, .form-horizontal .input-append {
            display: inline-block;
            margin-bottom: 0;
            vertical-align: middle;
            height: 60px;
            font-size: 30px;
        }
        .form-search .ul-form ,.but,.btn-primary{
            height: 70px!important;
            line-height: 70px!important;
            font-size: inherit;
        }
        .title_Infor h3{
            color: #fff;
            font-size: 3em;
            font-weight: bold;
            text-align: center;
            padding: 25px 0;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function pickedFunc() {
            $("#searchForm").submit();
        }

        function preview() {

            bdhtml=window.document.body.innerHTML;
            zoom(parseInt($("#printSelect").val()));
            window.document.body.innerHTML=$('table').eq(0).html();
            setTimeout('yscz()',500);
        }
        function zoom(level)
        {
            var i = parseInt(document.body.style.zoom);
            if (isNaN(i)) i=100;
            newZoom= i * level / 100;
            document.body.style.zoom=newZoom+'%';
        }

        function yscz(){
            window.print();
            document.body.style.width = "100%";
            document.body.style.height = "100%";
            window.document.body.innerHTML=bdhtml;
            location.reload();
        }

    </script>

</head>
<body>


<div class="title_Infor">
    <a href="/a/login" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3 >医疗运营报表</h3>
</div>
<form:form id="searchForm" modelAttribute="medicalOperationReport" action="${ctx}/process/medicalOperationReport/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li class="btns">日期: <input id="currentSumDate" name="currentSumDate" type="text" readonly="readonly" maxlength="20"
                                    class="input-medium Wdate "
                                    value="<fmt:formatDate value="${medicalOperationReport.currentSumDate}" pattern="yyyy-MM-dd"/>"
                                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:pickedFunc});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><a href="${ctx}/process/medicalOperationReport/form" role="button"
                            class="btn btn-primary">添加/修改</a></li>

    </ul>
</form:form>
<sys:message content="${message}"/>
<div style="margin: 10px 50px;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody>
        <tr>
            <td>
                <div style="width: 100%; " align="center"><h3><b>中国卫生集团所属医院——医疗运营报表</b></h3></div>
                <div style="width: 95%;font-size: 22px; margin-top: 10px;color:#000000;" align="right"><b>——点击各医院名称，显示各医院“日综合分析”</b></div>
                <div style="width: 95%;font-size: 22px; margin-top: 10px;color:#000000;" align="right"><b>——点击<img src='${ctxStatic}/images/icons/administrative.jpg' width='20px' height='20px'/>图标，显示该院的“行政值班交班表”</b></div>

                <span class="tablethead"><b>表一：医疗收入报表</b></span>
                <span style="padding-left: 15px;">说明：月环比增长率 = (本月处至选定日期的合计值 - 上月初至上月当日的合计值) / 上月初至上月当日的合计值</span><br>
                <span style="padding-left: 195px;">例：5月23号，环比增长率 = (5月1日至5月23日的合计值 - 4月1日至4月23日的合计值 - ) / 4月1日至4月23日的合计值。</span>
                <table id="contentTable1" class="table table-striped table-bordered table-condensed" align="center">
                    <thead>
                    <tr>
                        <th style="border-left:none; width:18%;"></th>
                        <th colspan="3">日医疗收入</th>
                        <th colspan="6">月医疗收入累计</th>
                        <th colspan="3">年医疗收入累计</th>
                    </tr>
                    <tr>
                        <th style="border-left:none;">医院名称</th>
                        <th>日医疗收入</th>
                        <th>日门诊收入</th>
                        <th>日住院收入</th>
                        <th>月收入累计合计</th>
                        <th>月收入环比增长率</th>
                        <th>月门诊收入累计合计</th>
                        <th>月门诊环比增长率</th>
                        <th>月住院收入累计合计</th>
                        <th>月住院环比增长率</th>
                        <th>年收入累计合计</th>
                        <th>年门诊收入累计合计</th>
                        <th>年住院收入累计合计</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="border-left:none; color: red; font-weight: bold;">集团医院收入总计</td>
                        <td id="dailyIncomeSum"></td>
                        <td id="dailyOutpatientClinicSum"></td>
                        <td id="dailyHospitalizationSum"></td>
                        <td id="dailyIncomeMonthsSum"></td>
                        <td id="dailyIncomeMonthsSumRate"></td>
                        <td id="dailyOutpatientClinicMonthsSum"></td>
                        <td id="dailyOutpatientClinicMonthsSumRate"></td>
                        <td id="dailyHospitalizationMonthsSum"></td>
                        <td id="dailyHospitalizationMonthsSumRate"></td>
                        <td id="dailyIncomeYearSum"></td>
                        <td id="dailyOutpatientClinicYearSum"></td>
                        <td id="dailyHospitalizationYearSum"></td>
                    </tr>
                    <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
                        <tr name="income">
                            <td style="border-left:none;">
                            </td>
                            <td id="td1${i.count}">${medicalOperationReport.dailyIncome == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyIncome,2)} </td>
                            <td id="td2${i.count}">${medicalOperationReport.dailyOutpatientClinic == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientClinic,2)}</td>
                            <td id="td3${i.count}">${medicalOperationReport.dailyHospitalization == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyHospitalization,2)}</td>
                            <td id="td4${i.count}">${medicalOperationReport.dailyIncomeMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyIncomeMonths,2)}</td>
                            <td id="td10${i.count}"></td>
                            <td id="td5${i.count}">${medicalOperationReport.dailyOutpatientClinicMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientClinicMonths,2)}</td>
                            <td id="td11${i.count}"></td>
                            <td id="td6${i.count}">${medicalOperationReport.dailyHospitalizationMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyHospitalizationMonths,2)}</td>
                            <td id="td12${i.count}"></td>
                            <td id="td7${i.count}">${medicalOperationReport.dailyIncomeYear == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyIncomeYear,2)}</td>
                            <td id="td8${i.count}">${medicalOperationReport.dailyOutpatientClinicYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.dailyOutpatientClinicYear,2)}</td>
                            <td id="td9${i.count}">${medicalOperationReport.dailyHospitalizationYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.dailyHospitalizationYear,2)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <span class="tablethead"><b>表二：门急诊人次报表</b></span>
                <table id="contentTable2" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th style="border-left:none; width:15%;"></th>
                        <th colspan="4">日门急诊人次</th>
                        <th colspan="8">月门急诊人次累计</th>
                        <th colspan="4">年门急诊人次累计</th>
                    </tr>
                    <tr>
                        <th style="border-left:none;">医院名称</th>
                        <th>日门急诊合计</th>
                        <th>日门诊人次</th>
                        <th>日急诊人次</th>
                        <th>日门诊人均费用</th>
                        <th>月门急诊合计</th>
                        <th>月门急诊环比增长率</th>
                        <th>月门诊人次累计</th>
                        <th>月门诊人次环比增长率</th>
                        <th>月急诊人次累计</th>
                        <th>月急诊人次环比增长率</th>
                        <th>月门诊人均费用</th>
                        <th>月门诊人均费用环比增长率</th>
                        <th>年门急诊合计</th>
                        <th>年门诊人次累计</th>
                        <th>年急诊人次累计</th>
                        <th>年门诊人均费用</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="border-left:none; color: red; font-weight: bold;">集团医院门急诊人次总计</td>
                        <td id="diurnalUrgencySum"></td>
                        <td id="dailyOutpatientNumberSum"></td>
                        <td id="dailyEmergencySum"></td>
                        <td id="dailyPriceSum"></td>
                        <td id="diurnalUrgencyMonthsSum"></td>
                        <td id="diurnalUrgencyMonthsSumRate"></td>
                        <td id="dailyOutpatientNumberMonthsSum"></td>
                        <td id="dailyOutpatientNumberMonthsSumRate"></td>
                        <td id="dailyEmergencyMonthsSum"></td>
                        <td id="dailyEmergencyMonthsSumRate"></td>
                        <td id="dailyPriceMonthsSum"></td>
                        <td id="dailyPriceMonthsSumRate"></td>
                        <td id="diurnalUrgencyYearSum"></td>
                        <td id="dailyOutpatientNumberYearSum"></td>
                        <td id="dailyEmergencyYearSum"></td>
                        <td id="dailyPriceYearSum"></td>
                    </tr>
                    <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
                        <tr name="people">
                            <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                            <td id="people1${i.count}">${medicalOperationReport.diurnalUrgency == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalUrgency,2)}</td>
                            <td id="people2${i.count}">${medicalOperationReport.dailyOutpatientNumber == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientNumber,2)}</td>
                            <td id="people3${i.count}">${medicalOperationReport.dailyEmergency == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyEmergency,2)}</td>
                            <td id="people11${i.count}">${(medicalOperationReport.dailyOutpatientClinic == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientClinic,2))/(medicalOperationReport.diurnalUrgency == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalUrgency,2))}</td>
                            <td id="people4${i.count}">${medicalOperationReport.diurnalUrgencyMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalUrgencyMonths,2)}</td>
                            <td id="people14${i.count}"></td>
                            <td id="people5${i.count}">${medicalOperationReport.dailyOutpatientNumberMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientNumberMonths,2)}</td>
                            <td id="people15${i.count}"></td>
                            <td id="people6${i.count}">${medicalOperationReport.dailyEmergencyMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyEmergencyMonths,2)}</td>
                            <td id="people16${i.count}"></td>
                            <td id="people12${i.count}">${(medicalOperationReport.dailyOutpatientClinicMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientClinicMonths,2))/(medicalOperationReport.diurnalUrgencyMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalUrgencyMonths,2))}</td>
                            <td id="people17${i.count}"></td>
                            <td id="people7${i.count}">${medicalOperationReport.diurnalUrgencyYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.diurnalUrgencyYear,2)}</td>
                            <td id="people8${i.count}">${medicalOperationReport.dailyOutpatientNumberYear == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientNumberYear,2)}</td>
                            <td id="people9${i.count}">${medicalOperationReport.dailyEmergencyYear == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyEmergencyYear,2)}</td>
                            <td id="people13${i.count}">${(medicalOperationReport.dailyOutpatientClinicYear == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOutpatientClinicYear,2))/(medicalOperationReport.diurnalUrgencyYear == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalUrgencyYear,2))}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <span class="tablethead"><b>表三：住院情况报表</b></span>
                <table id="contentTable3" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th style="border-left:none; width:18%;"></th>
                        <th colspan="4">日住院人数</th>
                        <th colspan="8">月住院人数累计</th>
                        <th colspan="4">年住院人数累计</th>
                    </tr>
                    <tr>
                        <th style="border-left:none;">医院名称</th>
                        <th>日入院人数</th>
                        <th>日出院人数</th>
                        <th>日在院人数</th>
                        <th>日住院人均费用</th>
                        <th>月入院人数累计</th>
                        <th>月入院人数环比增长率</th>
                        <th>月出院人数累计</th>
                        <th>月出院人数环比增长率</th>
                        <th>月占床日累计</th>
                        <th>月占床日环比增长率</th>
                        <th>月住院人均费用</th>
                        <th>月住院人均费用环比增长率</th>
                        <th>年入院人数累计</th>
                        <th>年出院人数累计</th>
                        <th>年占床日累计</th>
                        <th>年住院人均费用</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="border-left:none; color: red; font-weight: bold;">集团医院住院人数总计</td>
                        <td id="admissionNumberSum"></td>
                        <td id="dischargeNumberSum"></td>
                        <td id="hospitalPeopleSum"></td>
                        <td id="hospitalPriceSum"></td>
                        <td id="admissionNumberMonthsSum"></td>
                        <td id="admissionNumberMonthsSumRate"></td>
                        <td id="dischargeNumberMonthsSum"></td>
                        <td id="dischargeNumberMonthsSumRate"></td>
                        <td id="hospitalPeopleMonthsSum"></td>
                        <td id="hospitalPeopleMonthsSumRate"></td>
                        <td id="hospitalPriceMonthsSum"></td>
                        <td id="hospitalPriceMonthsSumRate"></td>
                        <td id="admissionNumberYearSum"></td>
                        <td id="dischargeNumberYearSum"></td>
                        <td id="hospitalPeopleYearSum"></td>
                        <td id="hospitalPriceYearSum"></td>
                    </tr>
                    <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
                        <tr name="inHospital">
                            <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                            <td id="inHospital1${i.count}">${medicalOperationReport.admissionNumber  == 0 ? "" : fns:getMathUtils(medicalOperationReport.admissionNumber,2)}</td>
                            <td id="inHospital2${i.count}">${medicalOperationReport.dischargeNumber  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dischargeNumber,2)}</td>
                            <td id="inHospital3${i.count}">${medicalOperationReport.hospitalPeople  == 0 ? "" : fns:getMathUtils(medicalOperationReport.hospitalPeople,2)}</td>
                            <td id="inHospital20${i.count}">${(medicalOperationReport.dailyHospitalization  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyHospitalization,2))/(medicalOperationReport.dischargeNumber  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dischargeNumber,2))}</td>
                            <td id="inHospital4${i.count}">${medicalOperationReport.admissionNumberMonths  == 0 ? "" : fns:getMathUtils(medicalOperationReport.admissionNumberMonths,2)}</td>
                            <td id="inHospital50${i.count}"></td>
                            <td id="inHospital5${i.count}">${medicalOperationReport.dischargeNumberMonths  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dischargeNumberMonths,2)}</td>
                            <td id="inHospital51${i.count}"></td>
                            <td id="inHospital6${i.count}">${medicalOperationReport.hospitalPeopleMonths  == 0 ? "" : fns:getMathUtils(medicalOperationReport.hospitalPeopleMonths,2)}</td>
                            <td id="inHospital52${i.count}"></td>
                            <td id="inHospital21${i.count}">${(medicalOperationReport.dailyHospitalizationMonths  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyHospitalizationMonths,2))/(medicalOperationReport.dischargeNumberMonths  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dischargeNumberMonths,2))}</td>
                            <td id="inHospital53${i.count}"></td>
                            <td id="inHospital7${i.count}">${medicalOperationReport.admissionNumberYear  == 0 ? "" : fns:getMathUtils(medicalOperationReport.admissionNumberYear,2)}</td>
                            <td id="inHospital8${i.count}">${medicalOperationReport.dischargeNumberYear  == 0 ? "" :fns:getMathUtils( medicalOperationReport.dischargeNumberYear,2)}</td>
                            <td id="inHospital9${i.count}">${medicalOperationReport.hospitalPeopleYear  == 0 ? "" : fns:getMathUtils( medicalOperationReport.hospitalPeopleYear,2)}</td>
                            <td id="inHospital22${i.count}">${(medicalOperationReport.dailyHospitalizationYear  == 0 ? "" : fns:getMathUtils( medicalOperationReport.dailyHospitalizationYear,2))/(medicalOperationReport.dischargeNumberYear  == 0 ? "" : fns:getMathUtils( medicalOperationReport.dischargeNumberYear,2))}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <span class="tablethead"><b>表四：手术台次报表</b></span>
                <table id="contentTable4" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th style="border-left:none; width:18%;"></th>
                        <th colspan="3">日手术台次</th>
                        <th colspan="6">月手术台次累计</th>
                        <th colspan="3">年手术台次累计</th>
                    </tr>
                    <tr>
                        <th style="border-left:none;">医院名称</th>
                        <th>日手术台次合计</th>
                        <th>日门诊手术</th>
                        <th>日住院手术</th>
                        <th>月手术台次累计</th>
                        <th>月手术台次环比增长率</th>
                        <th>月门诊手术累计</th>
                        <th>月门诊手术环比增长率</th>
                        <th>月住院手术累计</th>
                        <th>月住院手术环比增长率</th>
                        <th>年手术台次合计</th>
                        <th>年门诊手术累计</th>
                        <th>年住院手术累计</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="border-left:none; color: red; font-weight: bold;">集团医院手术台次总计</td>
                        <td id="operationNumberSum"></td>
                        <td id="diurnalOutpatientOperationSum"></td>
                        <td id="dailyOperationSum"></td>
                        <td id="operationNumberMonthsSum"></td>
                        <td id="operationNumberMonthsSumRate"></td>
                        <td id="diurnalOutpatientOperationMonthsSum"></td>
                        <td id="diurnalOutpatientOperationMonthsSumRate"></td>
                        <td id="dailyOperationMonthsSum"></td>
                        <td id="dailyOperationMonthsSumRate"></td>
                        <td id="operationNumberYearSum"></td>
                        <td id="diurnalOutpatientOperationYearSum"></td>
                        <td id="dailyOperationYearSum"></td>
                    </tr>
                    <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
                        <tr name="operation">
                            <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                            <td id="operation1${i.count}">${medicalOperationReport.operationNumber == 0 ? "" :fns:getMathUtils( medicalOperationReport.operationNumber,2)}</td>
                            <td id="operation2${i.count}">${medicalOperationReport.diurnalOutpatientOperation == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalOutpatientOperation,2)}</td>
                            <td id="operation3${i.count}">${medicalOperationReport.dailyOperation == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOperation,2)}</td>
                            <td id="operation4${i.count}">${medicalOperationReport.operationNumberMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.operationNumberMonths,2)}</td>
                            <td id="operation50${i.count}"></td>
                            <td id="operation5${i.count}">${medicalOperationReport.diurnalOutpatientOperationMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalOutpatientOperationMonths,2)}</td>
                            <td id="operation51${i.count}"></td>
                            <td id="operation6${i.count}">${medicalOperationReport.dailyOperationMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyOperationMonths,2)}</td>
                            <td id="operation52${i.count}"></td>
                            <td id="operation7${i.count}">${medicalOperationReport.operationNumberYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.operationNumberYear,2)}</td>
                            <td id="operation8${i.count}">${medicalOperationReport.diurnalOutpatientOperationYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.diurnalOutpatientOperationYear,2)}</td>
                            <td id="operation9${i.count}">${medicalOperationReport.dailyOperationYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.dailyOperationYear,2)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <span class="tablethead" style="display: none"><b>表五：均次效率指标报表</b></span>
                <table id="contentTable5" class="table table-striped table-bordered table-condensed"
                       style="display: none">
                    <thead>
                    <tr>
                        <th style="border-left:none; width:18%;"></th>
                        <th colspan="4">医疗</th>
                        <th colspan="3">护理</th>
                        <th colspan="2">医技</th>
                    </tr>
                    <tr>
                        <th style="border-left:none;">医院名称</th>
                        <th>医生人数</th>
                        <th>医均门诊量</th>
                        <th>医均占床日</th>
                        <th>医均总收入</th>
                        <th>护士人数</th>
                        <th>护均占床日</th>
                        <th>护均总收入</th>
                        <th>医技人数</th>
                        <th>医技科人均总收入</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="border-left:none; color: red; font-weight: bold;">集团医院总计</td>
                        <td id="doctorsNumberSum"></td>
                        <td id="outpatientVolumeAverageSum"></td>
                        <td id="bedDaySum"></td>
                        <td id="totalMedicalIncomeSum"></td>
                        <td id="nursesNumberSum"></td>
                        <td id="nursesBedDaySum"></td>
                        <td id="nursesMedicalIncomeSum"></td>
                        <td id="medicalPeopleNumberSum"></td>
                        <td id="medicalDepartmentTotalIncomeSum"></td>
                    </tr>
                    <c:forEach items="${list}" var="medicalOperationReport">
                        <tr>
                            <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                            <td>${(medicalOperationReport.doctorsNumber)== 0 ? "" : (medicalOperationReport.doctorsNumber)}</td>
                            <td>${medicalOperationReport.outpatientVolumeAverage == 0 ? "" : medicalOperationReport.outpatientVolumeAverage}</td>
                            <td>${medicalOperationReport.bedDay == 0 ? "" :medicalOperationReport.bedDay}</td>
                            <td>${medicalOperationReport.totalMedicalIncome  == 0 ? "" :medicalOperationReport.totalMedicalIncome}</td>
                            <td>${medicalOperationReport.nursesNumber  == 0 ? "" :medicalOperationReport.nursesNumber}</td>
                            <td>${medicalOperationReport.nursesBedDay  == 0 ? "" :medicalOperationReport.nursesBedDay}</td>
                            <td>${medicalOperationReport.nursesMedicalIncome  == 0 ? "" :medicalOperationReport.nursesMedicalIncome}</td>
                            <td>${medicalOperationReport.medicalPeopleNumber  == 0 ? "" :medicalOperationReport.medicalPeopleNumber}</td>
                            <td>${medicalOperationReport.medicalDepartmentTotalIncome  == 0 ? "" :medicalOperationReport.medicalDepartmentTotalIncome}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
        </tr>

        </tbody>
    </table>
    <span class="tablethead"><b>表五：医均工作情况（医生人数）---月评价指标</b></span>
    <table id="contentTable6" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th style="border-left:none; width:18%;"></th>
            <th colspan="6">医均工作情况（月）</th>
            <th colspan="3">医均工作情况（年累积）</th>
        </tr>
        <tr>
            <th style="border-left:none;">医院名称</th>
            <th>医院总人数</th>
            <th>医生人数</th>
            <th>占总人数%</th>
            <th>医均门诊量</th>
            <th>医均出院病人</th>
            <th>医均收入</th>
            <th>医均门诊量</th>
            <th>医均出院病人数</th>
            <th>医均收入</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="border-left:none; color: red; font-weight: bold;">集团医院总计</td>
            <td id="totalNumHospitalSum"></td>
            <td id="doctorNumSum"></td>
            <td id="totalNumSum"></td>
            <td id="medicalAverageMonthsSum"></td>
            <td id="patientsNumMonthsSum"></td>
            <td id="medicalIncomeMonthsSum"></td>
            <td id="medicalAverageYearSum"></td>
            <td id="patientsNumYearSum"></td>
            <td id="medicalIncomeYearSum"></td>
        </tr>
        <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
            <tr name="total">
                <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                <td id="total11${i.count}">${medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)}</td>
                <td id="total12${i.count}">${medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2)}</td>
                <td id="total3${i.count}">${((medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)))*100}</td>
                <td id="total4${i.count}">${(medicalOperationReport.diurnalUrgencyMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalUrgencyMonths,2))/(medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2))}</td>
                <td id="total5${i.count}">${(medicalOperationReport.dischargeNumberMonths  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dischargeNumberMonths,2))/(medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2))}</td>
                <td id="total6${i.count}">${(medicalOperationReport.dailyIncomeMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyIncomeMonths,2))/(medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2))}</td>
                <td id="total7${i.count}">${(medicalOperationReport.diurnalUrgencyYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.diurnalUrgencyYear,2))/(medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2))}</td>
                <td id="total8${i.count}">${(medicalOperationReport.dischargeNumberYear  == 0 ? "" :fns:getMathUtils( medicalOperationReport.dischargeNumberYear,2))/(medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2))}</td>
                <td id="total9${i.count}">${(medicalOperationReport.dailyIncomeYear == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyIncomeYear,2))/(medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2))}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <span class="tablethead"><b>表六：医院人均工作情况（全院总人数）---月评价指标</b></span>
    <table id="contentTable7" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th style="border-left:none; width:18%;"></th>
            <th colspan="4">人均工作情况（月）</th>
            <th colspan="4">人均工作情况（年累积）</th>
        </tr>
        <tr>
            <th style="border-left:none;">医院名称</th>
            <th>医院总人数</th>
            <th>人均门诊量</th>
            <th>人均出院病人</th>
            <th>人均收入</th>
            <th>医院总人数</th>
            <th>人均门诊量</th>
            <th>人均出院病人</th>
            <th>人均收入</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="border-left:none; color: red; font-weight: bold;">集团医院总计</td>
            <td id="totalNumberHospitalSum"></td>
            <td id="outpatientVolumeSum"></td>
            <td id="dischargedPatientsSum"></td>
            <td id="capitaIncomeSum"></td>
            <td id="totalNumberHospitalYearSum"></td>
            <td id="outpatientVolumeYearSum"></td>
            <td id="dischargedPatientsYearSum"></td>
            <td id="capitaIncomeYearSum"></td>
        </tr>
        <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
            <tr name="capita">
                <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                <td id="capita11${i.count}">${medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)}</td>
                <td id="capita2${i.count}">${(medicalOperationReport.diurnalUrgencyMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.diurnalUrgencyMonths,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2))}</td>
                <td id="capita3${i.count}">${(medicalOperationReport.dischargeNumberMonths  == 0 ? "" : fns:getMathUtils(medicalOperationReport.dischargeNumberMonths,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2))}</td>
                <td id="capita4${i.count}">${(medicalOperationReport.dailyIncomeMonths == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyIncomeMonths,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2))}</td>
                <td id="capita12${i.count}">${medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)}</td>
                <td id="capita6${i.count}">${(medicalOperationReport.diurnalUrgencyYear == 0 ? "" :fns:getMathUtils( medicalOperationReport.diurnalUrgencyYear,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2))}</td>
                <td id="capita7${i.count}">${(medicalOperationReport.dischargeNumberYear  == 0 ? "" :fns:getMathUtils( medicalOperationReport.dischargeNumberYear,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2))}</td>
                <td id="capita8${i.count}">${(medicalOperationReport.dailyIncomeYear == 0 ? "" : fns:getMathUtils(medicalOperationReport.dailyIncomeYear,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2))}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <span class="tablethead"><b>表七：人力成本情况（月度）</b></span>
    <table id="contentTable8" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th style="border-left:none;">医院名称</th>
            <th>医院总人数</th>
            <th>医院总成本</th>
            <th>人力资源总成本</th>
            <th>人力成本占总成本%</th>
            <th>人均成本</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="border-left:none; color: red; font-weight: bold;">集团医院总计</td>
            <td id="totalHospitalSum"></td>
            <td id="totalCostHospitalSum"></td>
            <td id="totalCostPeopleSum"></td>
            <td id="totalCostSum"></td>
            <td id="costManSum"></td>
        </tr>
        <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
            <tr name="cost">
                <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                <td id="cost11${i.count}">${medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)}</td>
                <td id="cost2${i.count}">${medicalOperationReport.totalCostHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalCostHospital,2)}</td>
                <td id="cost3${i.count}">${medicalOperationReport.totalCostPeople == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalCostPeople,2)}</td>
                <td id="cost4${i.count}">${((medicalOperationReport.totalCostPeople == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalCostPeople,2))/(medicalOperationReport.totalCostHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalCostHospital,2)))*100}</td>
                <td id="cost5${i.count}">${(medicalOperationReport.totalCostHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalCostHospital,2))/(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2))}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <span class="tablethead"><b>表八：人力资源报表（月度）</b></span>
    <table id="contentTable9" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th style="border-left:none;" rowspan="2">医院名称</th>
            <th rowspan="2">医院总人数</th>
            <th colspan="5">其中：医药护技</th>
            <th colspan="3">其中：行政后勤</th>
        </tr>
        <tr>
            <th>医生人数</th>
            <th>护士人数</th>
            <th>医技人数</th>
            <th>医辅人数</th>
            <th>占总人数%</th>
            <th>后勤人数</th>
            <th>行政人数</th>
            <th>占总人数%</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="border-left:none; color: red; font-weight: bold;">集团医院总计</td>
            <td id="totalNumsHospitalSum"></td>
            <td id="doctorNumberSum"></td>
            <td id="nurseNumberSum"></td>
            <td id="medicalNumber1Sum"></td>
            <td id="medicalAuxiliaryNumSum"></td>
            <td id="medicalNumberPercentSum"></td>
            <td id="logisticalNumberSum"></td>
            <td id="administrativeNumberSum"></td>
            <td id="logisticsPercentSum"></td>
        </tr>
        <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
            <tr name="hrm">
                <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                <td id="hrm11${i.count}">${medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)}</td>
                <td id="hrm12${i.count}">${medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2)}</td>
                <td id="hrm14${i.count}">${medicalOperationReport.nurseNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.nurseNum,2)}</td>
                <td id="hrm16${i.count}">${medicalOperationReport.medicalNumber == 0 ? "" : fns:getMathUtils(medicalOperationReport.medicalNumber,2)}</td>
                <td id="hrm15${i.count}">${medicalOperationReport.medicalAuxiliaryNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.medicalAuxiliaryNum,2)}</td>
                <td id="hrm3${i.count}">${(((medicalOperationReport.medicalNumber == 0 ? "" : fns:getMathUtils(medicalOperationReport.medicalNumber,2)) +
                        (medicalOperationReport.medicalAuxiliaryNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.medicalAuxiliaryNum,2)) +
                        (medicalOperationReport.nurseNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.nurseNum,2)) + (medicalOperationReport.doctorNum == 0 ? "" : fns:getMathUtils(medicalOperationReport.doctorNum,2)) )
                        /(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)))*100}</td>
                <td id="hrm13${i.count}">${(medicalOperationReport.logisticalNumber == 0 ? "" : fns:getMathUtils(medicalOperationReport.logisticalNumber,2))}</td>
                <td id="hrm17${i.count}">${(medicalOperationReport.administrativeNumber == 0 ? "" : fns:getMathUtils(medicalOperationReport.administrativeNumber,2))}</td>
                <td id="hrm5${i.count}">${(((medicalOperationReport.logisticalNumber == 0 ? "" : fns:getMathUtils(medicalOperationReport.logisticalNumber,2)) +
                        (medicalOperationReport.administrativeNumber == 0 ? "" : fns:getMathUtils(medicalOperationReport.administrativeNumber,2)) )
                        /(medicalOperationReport.totalNumHospital == 0 ? "" : fns:getMathUtils(medicalOperationReport.totalNumHospital,2)))*100}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <span class="tablethead"><b>表九：人力资源大型设备检查人次（月报）</b></span>
    <table id="contentTable10" class="table table-striped table-bordered1 table-condensed">
        <thead>
        <tr>
            <th style="border-left:none; width:14%;"></th>
            <th colspan="3" style="width:20%;">CT</th>
            <th colspan="3" style="width:20%;">超声</th>
            <th colspan="3" style="width:10%;">X射线</th>
            <th colspan="3" style="width:30%;">核磁</th>
        </tr>
        <tr>
            <th style="border-left:none;">医院名称</th>
            <th>检查人次</th>
            <th>医生人数</th>
            <th>人均次数</th>
            <th>检查人次</th>
            <th>医生人数</th>
            <th>人均次数</th>
            <th>检查人次</th>
            <th>医生人数</th>
            <th>人均次数</th>
            <th>检查人次</th>
            <th>医生人数</th>
            <th>人均次数</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="border-left:none; color: red; font-weight: bold;width: 8%">集团医院总计</td>
            <td id="ctCheckSum"></td>
            <td id="ctDocotrSum"></td>
            <td id="averageCtSum"></td>
            <td id="ulCheckSum"></td>
            <td id="ulDoctorSum"></td>
            <td id="averageUlSum"></td>
            <td id="xCheckSum"></td>
            <td id="xDoctorSum"></td>
            <td id="averageXSum"></td>
            <td id="nmrCheckSum"></td>
            <td id="nmrDoctorSum"></td>
            <td id="averageNmrSum"></td>
        </tr>
        <c:forEach items="${list}" var="medicalOperationReport" varStatus="i">
            <tr name="check">
                <td style="border-left:none;">${medicalOperationReport.orgId}</td>
                <td id="check1${i.count}">${medicalOperationReport.ctCheck == 0 ? "" : fns:getMathUtils(medicalOperationReport.ctCheck,2)}</td>
                <td id="check2${i.count}">${medicalOperationReport.ctDoctor == 0 ? "" : fns:getMathUtils(medicalOperationReport.ctDoctor,2)}</td>
                <td id="check30${i.count}">${(medicalOperationReport.ctDoctor == 0 ? "" :((medicalOperationReport.ctCheck  == 0 ? "" : fns:getMathUtils(medicalOperationReport.ctCheck,2))/fns:getMathUtils(medicalOperationReport.ctDoctor,2)))}</td>
                <td id="check4${i.count}">${medicalOperationReport.ultrasonicCheck == 0 ? "" : fns:getMathUtils(medicalOperationReport.ultrasonicCheck,2)}</td>
                <td id="check5${i.count}">${medicalOperationReport.ultrasonicDoctor == 0 ? "" : fns:getMathUtils(medicalOperationReport.ultrasonicDoctor,2)}</td>
                <td id="check31${i.count}">${(medicalOperationReport.ultrasonicDoctor == 0 ? "" : ((medicalOperationReport.ultrasonicCheck == 0 ? "" :fns:getMathUtils( medicalOperationReport.ultrasonicCheck,2))/(fns:getMathUtils(medicalOperationReport.ultrasonicDoctor,2))))}</td>
                <td id="check7${i.count}">${medicalOperationReport.xCheck == 0 ? "" : fns:getMathUtils(medicalOperationReport.xCheck,2)}</td>
                <td id="check8${i.count}">${medicalOperationReport.xDoctor == 0 ? "" : fns:getMathUtils(medicalOperationReport.xDoctor,2)}</td>
                <td id="check32${i.count}">${(medicalOperationReport.xDoctor == 0 ? "" : (medicalOperationReport.xCheck == 0 ? "" : fns:getMathUtils(medicalOperationReport.xCheck,2))/fns:getMathUtils(medicalOperationReport.xDoctor,2))}</td>
                <td id="check10${i.count}">${medicalOperationReport.nmrCheck == 0 ? "" : fns:getMathUtils(medicalOperationReport.nmrCheck,2)}</td>
                <td id="check11${i.count}">${medicalOperationReport.nmrDoctor == 0 ? "" : fns:getMathUtils(medicalOperationReport.nmrDoctor,2)}</td>
                <td id="check33${i.count}">${(medicalOperationReport.nmrDoctor == 0 ? "" : ((medicalOperationReport.nmrCheck == 0 ? "" : fns:getMathUtils(medicalOperationReport.nmrCheck,2))/fns:getMathUtils(medicalOperationReport.nmrDoctor,2)))}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script type="application/javascript">

    var allList = [];
    var beforeAllList =[];
    var beforeDate = getLastMonthYestdy($("#currentSumDate").val());
    var beforeDate1 = getLastDay($("#currentSumDate").val());
    var pome = $.get("${ctx}/process/medicalOperationReport/formData?currentSumDate=" +beforeDate1, function (dataSource) {
        beforeAllList = dataSource});

    var su =  $.get("${ctx}/process/medicalOperationReport/formData?currentSumDate=" + $("#currentSumDate").val(), function (data) {
        allList = data;
        $.each(allList, function (i, e) {
            sumAllTableTD('dailyIncome', e.dailyIncome);
            sumAllTableTD('dailyOutpatientClinic', e.dailyOutpatientClinic);
            sumAllTableTD('dailyHospitalization', e.dailyHospitalization);
            sumAllTableTD('dailyIncomeMonths', e.dailyIncomeMonths);
            sumAllTableTD('dailyOutpatientClinicMonths', e.dailyOutpatientClinicMonths);
            sumAllTableTD('dailyHospitalizationMonths', e.dailyHospitalizationMonths);
            sumAllTableTD('dailyIncomeYear', e.dailyIncomeYear);
            sumAllTableTD('dailyOutpatientClinicYear', e.dailyOutpatientClinicYear);
            sumAllTableTD('dailyHospitalizationYear', e.dailyHospitalizationYear);

            sumAllTableTD('diurnalUrgency', e.diurnalUrgency);
            sumAllTableTD('dailyOutpatientNumber', e.dailyOutpatientNumber);
            sumAllTableTD('dailyEmergency', e.dailyEmergency);
            sumAllTableTD('diurnalUrgencyMonths', e.diurnalUrgencyMonths);
            sumAllTableTD('dailyOutpatientNumberMonths', e.dailyOutpatientNumberMonths);
            sumAllTableTD('dailyEmergencyMonths', e.dailyEmergencyMonths);
            sumAllTableTD('diurnalUrgencyYear', e.diurnalUrgencyYear);
            sumAllTableTD('dailyOutpatientNumberYear', e.dailyOutpatientNumberYear);
            sumAllTableTD('dailyEmergencyYear', e.dailyEmergencyYear);

            sumAllTableTD('admissionNumber', e.admissionNumber);
            sumAllTableTD('dischargeNumber', e.dischargeNumber);
            sumAllTableTD('hospitalPeople', e.hospitalPeople);
            sumAllTableTD('admissionNumberMonths', e.admissionNumberMonths);
            sumAllTableTD('dischargeNumberMonths', e.dischargeNumberMonths);
            sumAllTableTD('hospitalPeopleMonths', e.hospitalPeopleMonths);
            sumAllTableTD('admissionNumberYear', e.admissionNumberYear);
            sumAllTableTD('dischargeNumberYear', e.dischargeNumberYear);
            sumAllTableTD('hospitalPeopleYear', e.hospitalPeopleYear);

            sumAllTableTD('operationNumber', e.operationNumber);
            sumAllTableTD('diurnalOutpatientOperation', e.diurnalOutpatientOperation);
            sumAllTableTD('dailyOperation', e.dailyOperation);
            sumAllTableTD('operationNumberMonths', e.operationNumberMonths);
            sumAllTableTD('diurnalOutpatientOperationMonths', e.diurnalOutpatientOperationMonths);
            sumAllTableTD('dailyOperationMonths', e.dailyOperationMonths);
            sumAllTableTD('operationNumberYear', e.operationNumberYear);
            sumAllTableTD('diurnalOutpatientOperationYear', e.diurnalOutpatientOperationYear);
            sumAllTableTD('dailyOperationYear', e.dailyOperationYear);

            sumAllTableTD('doctorsNumber', e.doctorsNumber);
            sumAllTableTD('outpatientVolumeAverage', e.outpatientVolumeAverage);
            sumAllTableTD('bedDay', e.bedDay);
            sumAllTableTD('totalMedicalIncome', e.totalMedicalIncome);
            sumAllTableTD('nursesNumber', e.nursesNumber);
            sumAllTableTD('nursesBedDay', e.nursesBedDay);
            sumAllTableTD('nursesMedicalIncome', e.nursesMedicalIncome);
            sumAllTableTD('medicalPeopleNumber', e.medicalPeopleNumber);
            sumAllTableTD('medicalDepartmentTotalIncome', e.medicalDepartmentTotalIncome);

            //报表五   月评价指标
            sumAllTableTD('totalNumHospital', e.totalNumHospital);
            sumAllTableTD('doctorNum', e.doctorNum);
            //需要重新写方法相加

            //报表六    月评价指标
            sumAllTableTD('totalNumberHospital', e.totalNumHospital);
            sumAllTableTD('totalNumberHospitalYear', e.totalNumHospital);

            //报表七   人力成本情况（月度）
            sumAllTableTD('totalHospital', e.totalNumHospital);
            sumAllTableTD('totalCostHospital', e.totalCostHospital);
            sumAllTableTD('totalCostPeople', e.totalCostPeople);

            //报表八   人力资源报表（月度）
            sumAllTableTD('totalNumsHospital', e.totalNumHospital);
            sumAllTableTD('doctorNumber', e.doctorNum);
            sumAllTableTD('nurseNumber', e.nurseNum);
            sumAllTableTD('medicalNumber1', e.medicalNumber);
            sumAllTableTD('medicalAuxiliaryNum', e.medicalAuxiliaryNum);
            sumAllTableTD('logisticalNumber', e.logisticalNumber);
            sumAllTableTD('administrativeNumber', e.administrativeNumber);

            //报表九  人力资源大型设备检查人次（月报）
            sumAllTableTD('ctCheck', e.ctCheck);
            sumAllTableTD('ctDocotr', e.ctDoctor);
            sumAllTableTD('ulCheck', e.ultrasonicCheck);
            sumAllTableTD('ulDoctor', e.ultrasonicDoctor);
            sumAllTableTD('xCheck', e.xCheck);
            sumAllTableTD('xDoctor', e.xDoctor);
            sumAllTableTD('nmrCheck', e.nmrCheck);
            sumAllTableTD('nmrDoctor', e.nmrDoctor);
        });
        //页面相加的数据求和 报表二
        residueAllTableTD2("dailyPriceSum","dailyOutpatientClinicSum","diurnalUrgencySum");
        handleFloatWan("dailyPriceSum");
        residueAllTableTD2("dailyPriceMonthsSum","dailyOutpatientClinicMonthsSum","diurnalUrgencyMonthsSum");
        handleFloatWan("dailyPriceMonthsSum");
        residueAllTableTD2("dailyPriceYearSum","dailyOutpatientClinicYearSum","diurnalUrgencyYearSum");
        handleFloatWan("dailyPriceYearSum");
        //页面相加的数据求和  报表三
        residueAllTableTD2("hospitalPriceSum","dailyHospitalizationSum","dischargeNumberSum");
        handleFloatWan("hospitalPriceSum");
        residueAllTableTD2("hospitalPriceMonthsSum","dailyHospitalizationMonthsSum","dischargeNumberMonthsSum");
        handleFloatWan("hospitalPriceMonthsSum");
        residueAllTableTD2("hospitalPriceYearSum","dailyHospitalizationYearSum","dischargeNumberYearSum");
        handleFloatWan("hospitalPriceYearSum");
        var total = 0
        var branches = $("tr [name='income']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdFloat("td" + j + i);
                handleFloatWan("td"+j+i);
            }
        }
        AllTableIdFloat("dailyIncomeSum")
        handleFloatWan("dailyIncomeSum")
        AllTableIdFloat("dailyOutpatientClinicSum")
        handleFloatWan("dailyOutpatientClinicSum")
        AllTableIdFloat("dailyHospitalizationSum")
        handleFloatWan("dailyHospitalizationSum")
        AllTableIdFloat("dailyIncomeMonthsSum")
        handleFloatWan("dailyIncomeMonthsSum")
        AllTableIdFloat("dailyOutpatientClinicMonthsSum")
        handleFloatWan("dailyOutpatientClinicMonthsSum")
        AllTableIdFloat("dailyHospitalizationMonthsSum")
        handleFloatWan("dailyHospitalizationMonthsSum")
        AllTableIdFloat("dailyIncomeYearSum")
        handleFloatWan("dailyIncomeYearSum")
        AllTableIdFloat("dailyOutpatientClinicYearSum")
        handleFloatWan("dailyOutpatientClinicYearSum")
        AllTableIdFloat("dailyHospitalizationYearSum")
        handleFloatWan("dailyHospitalizationYearSum")
        //报表二
        var branches = $("tr [name='people']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdInteger("people" + j + i);
                handleFloatWan("people"+j+i);
            }
        }
        for (var i = 1; i <= branches; i++) {
            for (var j = 11; j <= 13; j++) {
                AllTableIdFloatTo("people" + j + i);
                handleFloatWan("people"+j+i);
            }
        }

        AllTableIdInteger("diurnalUrgencySum")
        handleFloatWan("diurnalUrgencySum")
        AllTableIdInteger("dailyOutpatientNumberSum")
        handleFloatWan("dailyOutpatientNumberSum")
        AllTableIdInteger("dailyEmergencySum")
        handleFloatWan("dailyEmergencySum")
        AllTableIdInteger("diurnalUrgencyMonthsSum")
        handleFloatWan("diurnalUrgencyMonthsSum")
        AllTableIdInteger("dailyOutpatientNumberMonthsSum")
        handleFloatWan("dailyOutpatientNumberMonthsSum")
        AllTableIdInteger("dailyEmergencyMonthsSum")
        handleFloatWan("dailyEmergencyMonthsSum")
        AllTableIdInteger("diurnalUrgencyYearSum")
        handleFloatWan("diurnalUrgencyYearSum")
        AllTableIdInteger("dailyOutpatientNumberYearSum")
        handleFloatWan("dailyOutpatientNumberYearSum")
        AllTableIdInteger("dailyEmergencyYearSum")
        handleFloatWan("dailyEmergencyYearSum")
        AllTableIdFloat("dailyPriceSum")
        handleFloatWan("dailyPriceSum")
        AllTableIdFloat("dailyPriceMonthsSum")
        handleFloatWan("dailyPriceMonthsSum")
        AllTableIdFloat("dailyPriceYearSum")
        handleFloatWan("dailyPriceYearSum")
        //报表三
        var branches = $("tr [name='inHospital']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdInteger("inHospital" + j + i);
                handleFloatWan("inHospital"+j+i);
            }
        }
        for (var i = 1; i <= branches; i++) {
            for (var j = 20; j <= 22; j++) {
                AllTableIdFloatTo("inHospital" + j + i);
                handleFloatWan("inHospital"+j+i);
            }
        }
        AllTableIdInteger("admissionNumberSum");
        handleFloatWan("admissionNumberSum")
        AllTableIdInteger("dischargeNumberSum");
        handleFloatWan("dischargeNumberSum")
        AllTableIdInteger("hospitalPeopleSum");
        handleFloatWan("hospitalPeopleSum")
        AllTableIdInteger("admissionNumberMonthsSum");
        handleFloatWan("admissionNumberMonthsSum")
        AllTableIdInteger("dischargeNumberMonthsSum");
        handleFloatWan("dischargeNumberMonthsSum")
        AllTableIdInteger("hospitalPeopleMonthsSum");
        handleFloatWan("hospitalPeopleMonthsSum")
        AllTableIdInteger("admissionNumberYearSum");
        handleFloatWan("admissionNumberYearSum")
        AllTableIdInteger("dischargeNumberYearSum");
        handleFloatWan("dischargeNumberYearSum")
        AllTableIdInteger("hospitalPeopleYearSum");
        handleFloatWan("hospitalPeopleYearSum")
        AllTableIdFloat("hospitalPriceSum");
        handleFloatWan("hospitalPriceSum")
        AllTableIdFloat("hospitalPriceMonthsSum");
        handleFloatWan("hospitalPriceMonthsSum")
        AllTableIdFloat("hospitalPriceYearSum");
        handleFloatWan("hospitalPriceYearSum")
        //报表四
        var branches = $("tr [name='operation']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdInteger("operation" + j + i);
                handleFloatWan("operation"+j+i);
            }
        }
        AllTableIdInteger("operationNumberSum");
        handleFloatWan("operationNumberSum");
        AllTableIdInteger("diurnalOutpatientOperationSum");
        handleFloatWan("diurnalOutpatientOperationSum");
        AllTableIdInteger("dailyOperationSum");
        handleFloatWan("dailyOperationSum");
        AllTableIdInteger("operationNumberMonthsSum");
        handleFloatWan("operationNumberMonthsSum");
        AllTableIdInteger("diurnalOutpatientOperationMonthsSum");
        handleFloatWan("diurnalOutpatientOperationMonthsSum");
        AllTableIdInteger("dailyOperationMonthsSum");
        handleFloatWan("dailyOperationMonthsSum");
        AllTableIdInteger("operationNumberYearSum");
        handleFloatWan("operationNumberYearSum");
        AllTableIdInteger("diurnalOutpatientOperationYearSum");
        handleFloatWan("diurnalOutpatientOperationYearSum");
        AllTableIdInteger("dailyOperationYearSum");
        handleFloatWan("dailyOperationYearSum");


        ///报表五数据求和
        residueAllTableTD("totalNumSum","doctorNumSum","totalNumHospitalSum");
        sumAllTableContentTable6(total, "medicalAverageMonthsSum", 4, 6)
        sumAllTableContentTable6(total, "patientsNumMonthsSum", 5, 6)
        sumAllTableContentTable6(total, "medicalAverageYearSum", 7, 6)
        sumAllTableContentTable6(total, "patientsNumYearSum", 8, 6)
        residueAllTableTD2("medicalIncomeYearSum","dailyIncomeYearSum","doctorNumSum");
        residueAllTableTD2("medicalIncomeMonthsSum","dailyIncomeMonthsSum","doctorNumSum");
        //报表五
        var branches = $("tr[name='total']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdFloatTo("total" + j + i)
            }
        }
        var branches = $("tr[name='total']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 11; j <= 12; j++) {
                AllTableIdInteger("total" + j + i)
            }
        }
        AllTableIdInteger("totalNumHospitalSum");
        AllTableIdInteger("doctorNumSum");
        AllTableIdFloat("totalNumSum");
        AllTableIdFloat("medicalAverageMonthsSum");
        AllTableIdFloat("patientsNumMonthsSum");
        AllTableIdFloat("medicalIncomeMonthsSum");
        AllTableIdFloat("medicalAverageYearSum");
        AllTableIdFloat("patientsNumYearSum");
        AllTableIdFloat("medicalIncomeYearSum");

        ///报表六数据求和
        sumAllTableContentTable6(total, "outpatientVolumeSum", 2, 7)
        sumAllTableContentTable6(total, "dischargedPatientsSum", 3, 7)
        sumAllTableContentTable6(total, "capitaIncomeSum", 4, 7)
        sumAllTableContentTable6(total, "outpatientVolumeYearSum", 6, 7)
        sumAllTableContentTable6(total, "dischargedPatientsYearSum", 7, 7)
        sumAllTableContentTable6(total, "capitaIncomeYearSum", 8, 7)
        //报表六
        var branches = $("tr[name='capita']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdFloatTo("capita" + j + i)
            }
        }
        var branches = $("tr[name='capita']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 11; j <= 12; j++) {
                AllTableIdInteger("capita" + j + i)
            }
        }
        AllTableIdInteger("totalNumberHospitalSum");
        AllTableIdFloat("outpatientVolumeSum");
        AllTableIdFloat("dischargedPatientsSum");
        AllTableIdFloat("capitaIncomeSum");
        AllTableIdInteger("totalNumberHospitalYearSum");
        AllTableIdFloat("outpatientVolumeYearSum");
        AllTableIdFloat("dischargedPatientsYearSum");
        AllTableIdFloat("capitaIncomeYearSum");


        //报表七数据求和
//        sumAllTableContentTable6(total, "totalCostSum", 4, 8)
//        sumAllTableContentTable6(total, "costManSum", 5, 8)
        residueAllTableTD2("costManSum","totalCostHospitalSum","totalHospitalSum");
        residueAllTableTD("totalCostSum","totalCostPeopleSum","totalCostHospitalSum");
        //报表七
        var branches = $("tr[name='cost']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdFloatTo("cost" + j + i)
//                handleFloatWan("cost" + j + i);
            }
        }
        var branches = $("tr[name='cost']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 11; j <= 11; j++) {
                AllTableIdInteger("cost" + j + i)
            }
        }
        AllTableIdInteger("totalHospitalSum");
        AllTableIdFloat("totalCostHospitalSum");
        AllTableIdFloat("totalCostPeopleSum");
        AllTableIdFloat("totalCostSum");
        AllTableIdFloat("costManSum");


        //报表八数据求和
        residueAllTableTD3("medicalNumberPercentSum","totalNumsHospitalSum","doctorNumberSum","nurseNumberSum","medicalNumber1Sum","medicalAuxiliaryNumSum");
        residueAllTableTD4("logisticsPercentSum","totalNumsHospitalSum","logisticalNumberSum","administrativeNumberSum");
        //报表八
        var branches = $("tr[name='hrm']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 9; j++) {
                AllTableIdFloat("hrm" + j + i)
            }
        }
        var branches = $("tr[name='hrm']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 11; j <= 17; j++) {
                AllTableIdInteger("hrm" + j + i)
            }
        }
        AllTableIdInteger("totalNumsHospitalSum");
        AllTableIdInteger("doctorNumberSum");
        AllTableIdInteger("nurseNumberSum");
        AllTableIdInteger("medicalNumber1Sum");
        AllTableIdInteger("medicalAuxiliaryNumSum");
        AllTableIdInteger("logisticalNumberSum");
        AllTableIdInteger("administrativeNumberSum");
        AllTableIdFloat("medicalNumberPercentSum");
        AllTableIdFloat("logisticsPercentSum");

        //报表九数据求和
//        sumAllTableContentTable6(total, "averageCtSum", 3, 10)
//        sumAllTableContentTable6(total, "averageUlSum", 6, 10)
//        sumAllTableContentTable6(total, "averageXSum", 9, 10)
//        sumAllTableContentTable6(total, "averageNmrSum", 12, 10)
        residueAllTableTD2("averageCtSum","ctCheckSum","ctDocotrSum");
        residueAllTableTD2("averageUlSum","ulCheckSum","ulDoctorSum");
        residueAllTableTD2("averageXSum","xCheckSum","xDoctorSum");
        residueAllTableTD2("averageNmrSum","nmrCheckSum","nmrDoctorSum");
        //报表九
        var branches = $("tr[name='check']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 1; j <= 12; j++) {
                AllTableIdInteger("check" + j + i)
            }
        }

        var branches = $("tr[name='check']").length;
        for (var i = 1; i <= branches; i++) {
            for (var j = 30; j <= 33; j++) {
                AllTableIdFloat("check" + j + i)
            }
        }
        AllTableIdInteger("ctCheckSum");
        AllTableIdInteger("ctDocotrSum");
        AllTableIdFloat("averageCtSum");
        AllTableIdInteger("ulCheckSum");
        AllTableIdInteger("ulDoctorSum");
        AllTableIdFloat("averageUlSum");
        AllTableIdInteger("xCheckSum");
        AllTableIdInteger("xDoctorSum");
        AllTableIdFloat("averageXSum");
        AllTableIdInteger("nmrCheckSum");
        AllTableIdInteger("nmrDoctorSum");
        AllTableIdFloat("averageNmrSum");
    })
    pome.done(function(){
        su.done(function(){
            var b1=0,b2=0,b3=0,b4=0,b5= 0,b6= 0,b7= 0,b8= 0,b9= 0,b10= 0,b11=0,b12= 0,b13= 0,b14= 0,a1=0,a2=0,a3=0,a4=0,a5= 0,a6= 0,a7= 0,a8= 0,a9= 0,a10= 0,a11=0,a12=0,a13=0,a14=0;
            $.each(allList, function (i, e) {
                $.each(beforeAllList, function (j, x) {
                    b1+=Number(e.dailyIncomeMonths);
                    a1+=Number(x.dailyIncomeMonths);
                    b2+=Number(e.dailyOutpatientClinicMonths);
                    a2+=Number(x.dailyOutpatientClinicMonths);
                    b3+=Number(e.dailyHospitalizationMonths);
                    a3+=Number(x.dailyHospitalizationMonths);
                    var branches = $("tr [name='income']").length;
                    for (var u = 1;u <= branches; u++) {
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("td10"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyIncomeMonths),Number(x.dailyIncomeMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("td11"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyOutpatientClinicMonths),Number(x.dailyOutpatientClinicMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("td12"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyHospitalizationMonths),Number(x.dailyHospitalizationMonths));
                        }
                    }
                    b4+=Number(e.diurnalUrgencyMonths);
                    a4+=Number(x.diurnalUrgencyMonths);
                    b5+=Number(e.dailyOutpatientNumberMonths);
                    a5+=Number(x.dailyOutpatientNumberMonths);
                    b6+=Number(e.dailyEmergencyMonths);
                    a6+=Number(x.dailyEmergencyMonths);
                    b7+=Number(e.dailyOutpatientClinicMonths);
                    a7+=Number(x.dailyOutpatientClinicMonths);
                    branches = $("tr [name='people']").length;
                    for (var u = 1;u <= branches; u++) {
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("people14"+u).innerHTML=AllTableIdFloatPrecent(Number(e.diurnalUrgencyMonths),Number(x.diurnalUrgencyMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("people15"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyOutpatientNumberMonths),Number(x.dailyOutpatientNumberMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("people16"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyEmergencyMonths),Number(x.dailyEmergencyMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("people17"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyOutpatientClinicMonths),Number(x.dailyOutpatientClinicMonths));
                        }
                    }
                    b8+=Number(e.admissionNumberMonths);
                    a8+=Number(x.admissionNumberMonths);
                    b9+=Number(e.dischargeNumberMonths);
                    a9+=Number(x.dischargeNumberMonths);
                    b10+=Number(e.hospitalPeopleMonths)
                    a10+=Number(x.hospitalPeopleMonths);
                    b11+=Number(e.dailyHospitalizationMonths);
                    a11+=Number(x.dailyHospitalizationMonths);
                    branches = $("tr [name='inHospital']").length;
                    for (var u = 1;u <= branches; u++) {
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("inHospital50"+u).innerHTML=AllTableIdFloatPrecent(Number(e.admissionNumberMonths),Number(x.admissionNumberMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("inHospital51"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dischargeNumberMonths),Number(x.dischargeNumberMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("inHospital52"+u).innerHTML=AllTableIdFloatPrecent(Number(e.hospitalPeopleMonths),Number(x.hospitalPeopleMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("inHospital53"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyHospitalizationMonths),Number(x.dailyHospitalizationMonths));
                        }
                    }
                    b12+=Number(e.operationNumberMonths);
                    a12+=Number(x.operationNumberMonths);
                    b13+=Number(e.diurnalOutpatientOperationMonths);
                    a13+=Number(x.diurnalOutpatientOperationMonths);
                    b14+=Number(e.dailyOperationMonths);
                    a14+=Number(x.dailyOperationMonths);
                    branches = $("tr [name='operation']").length;
                    for (var u = 1;u <= branches; u++) {
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("operation50"+u).innerHTML=AllTableIdFloatPrecent(Number(e.operationNumberMonths),Number(x.operationNumberMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("operation51"+u).innerHTML=AllTableIdFloatPrecent(Number(e.diurnalOutpatientOperationMonths),Number(x.diurnalOutpatientOperationMonths));
                        }
                        if(i==j && e.orgId == x.orgId && u == i+1){
                            document.getElementById("operation52"+u).innerHTML=AllTableIdFloatPrecent(Number(e.dailyOperationMonths),Number(x.dailyOperationMonths));
                        }
                    }


                });
            });
            document.getElementById("dailyIncomeMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b1,a1);
            document.getElementById("dailyOutpatientClinicMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b2,a2);
            document.getElementById("dailyHospitalizationMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b3,a3);

            document.getElementById("diurnalUrgencyMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b4,a4);
            document.getElementById("dailyOutpatientNumberMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b5,a5);
            document.getElementById("dailyEmergencyMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b6,a6);
            document.getElementById("dailyPriceMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b7,a7);

            document.getElementById("admissionNumberMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b8,a8);
            document.getElementById("dischargeNumberMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b9,a9);
            document.getElementById("hospitalPeopleMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b10,a10);
            document.getElementById("hospitalPriceMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b11,a11);

            document.getElementById("operationNumberMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b12,a12);
            document.getElementById("diurnalOutpatientOperationMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b13,a13);
            document.getElementById("dailyOperationMonthsSumRate").innerHTML=AllTableIdFloatPrecent(b14,a14);
        });
    })
    //处理前四张报表中各个医院的数据，如果位数超过万，则取一位小数并加“万”字
    function handleFloatWan(id){
        var s =document.getElementById(id).innerHTML;
        var s_new  = s.replace(/[","]/g,"");
        if(s_new / 10000 >1){
            var s_new_1 =  ((s_new/10000)*100)/100;
            var t = floatFormat(s_new_1);
            document.getElementById(id).innerHTML = t+"万"
        }
    }


    //把页面中的0显示为空''(刚进入页面时调用)
    function sumAllTableTD(flag1, flag2) {
        document.getElementById(flag1 + "Sum").innerHTML = ((Number((document.getElementById(flag1 + "Sum").innerHTML == "" ? "0" : document.getElementById(flag1 + "Sum").innerHTML)) * 100) + (Number((flag2 == undefined ? "0" : flag2))) * 100 ) / 100;
    }

    function residueAllTableTD(flag1, flag2, flag3) {
        if(Number((document.getElementById(flag3).innerHTML))== 0){
            document.getElementById(flag1).innerHTML ="0"
        }else{
            document.getElementById(flag1).innerHTML =((Number((document.getElementById(flag2).innerHTML == "" ? "0" : document.getElementById(flag2).innerHTML)) / Number((document.getElementById(flag3).innerHTML == "" ? "0" : document.getElementById(flag3).innerHTML))))*100;
        }
    }

    function residueAllTableTD2(flag1, flag2, flag3) {
        var flag2 = document.getElementById(flag2).innerHTML;
        var flag3 = document.getElementById(flag3).innerHTML;
        var flag = flag2.replace(/[","]/g,"");
        var flag31 = flag3.replace(/[","]/g,"");
        if(flag31 == 0){
            document.getElementById(flag1).innerHTML ="0";
        }else{
            document.getElementById(flag1).innerHTML = ((Number((flag == "" ? "0" : flag)) / Number((flag31 == "" ? "0" : flag31))));
        }
    }



    //页面中多个数相加求和
    //flag1  求和之后赋值的id
    //flag2  除数
    function residueAllTableTD3(flag1, flag2, flag3,flag4,flag5,flag6) {
        var flag2 = document.getElementById(flag2).innerHTML;
        var flag3 = document.getElementById(flag3).innerHTML;
        var flag4 = document.getElementById(flag4).innerHTML;
        var flag5 = document.getElementById(flag5).innerHTML;
        var flag6 = document.getElementById(flag6).innerHTML;
        var flag = flag2.replace(/[","]/g,"");
        var flag31 = flag3.replace(/[","]/g,"");
        var flag32 = flag4.replace(/[","]/g,"");
        var flag33 = flag5.replace(/[","]/g,"");
        var flag34 = flag6.replace(/[","]/g,"");
        if(flag2 == 0){
            document.getElementById(flag1).innerHTML ="0";
        }else{
            document.getElementById(flag1).innerHTML = ((Number((flag31 == "" ? "0" : flag31)) + Number((flag32 == "" ? "0" : flag32))
            + Number((flag33 == "" ? "0" : flag33)) +Number((flag34 == "" ? "0" : flag34)))/ (Number((flag == "" ? "0" : flag))))*100;
        }
    }

    //页面中多个数相加求和
    //flag1  求和之后赋值的id
    //flag2  除数
    function residueAllTableTD4(flag1, flag2, flag3,flag4) {
        var flag2 = document.getElementById(flag2).innerHTML;
        var flag3 = document.getElementById(flag3).innerHTML;
        var flag4 = document.getElementById(flag4).innerHTML;
        var flag = flag2.replace(/[","]/g,"");
        var flag31 = flag3.replace(/[","]/g,"");
        var flag32 = flag4.replace(/[","]/g,"");
        if(flag2 == 0){
            document.getElementById(flag1).innerHTML ="0";
        }else{
            document.getElementById(flag1).innerHTML = ((Number((flag31 == "" ? "0" : flag31)) + Number((flag32 == "" ? "0" : flag32)))/ (Number((flag == "" ? "0" : flag))))*100;
        }
    }

    //报表五中后台没有传递数据的单元格的累计相加
    //占总人数%	医均门诊量	医均出院病人  医均收入	医均门诊量	医均出院病人数	医均收入
    function sumAllTableContentTable6(total, id, num, i) {
        total = 0;
        $('#contentTable' + i + ' tr').each(function () {
            $(this).find('td:eq(' + num + ')').each(function () {
                if (!(isNaN($(this).text())) && $(this).text() != '' && $(this).text() != "NaN" && $(this).text() != "Infinity") {
                    total += parseFloat($(this).text());
                }
            });
            document.getElementById(id).innerHTML = total.toFixed(2);
        });
    }

    function sumAllTableFloatContentTable6(total, id, num, i) {
        total = 0;
        $('#contentTable' + i + ' tr').each(function () {
            $(this).find('td:eq(' + num + ')').each(function () {
                if (!(isNaN($(this).text())) && $(this).text() != '' && $(this).text() != "NaN" && $(this).text() != "Infinity") {
                    total += parseInt($(this).text());
                }
            });
            document.getElementById(id).innerHTML = total;
        });
    }


    //置空方法(浮点型),特殊处理
    function AllTableIdFloatTo(id) {
        if (isNaN(Number($("#" + id).html())) || Number($("#" + id).html()) == 0 || $("#" + id).html() == "NaN" || $("#" + id).html() == "Infinity" || $("#"+id).html() == "NaN.undefined") {
            $("#" + id).html("0")
        } else {
            $("#" + id).html(floatFormat($("#" + id).html()))
        }
    }
    //置空方法(浮点型)
    function AllTableIdFloat(id) {
        if (isNaN(Number($("#" + id).html())) || Number($("#" + id).html()) == 0) {
            $("#" + id).html("0")
        } else {
            var s = $("#" + id).html();
            $("#" + id).html(floatFormat(s))
        }

    }

    //置空方法(浮点型) "环比增长率百分号处理"
    function AllTableIdFloatPrecent(flag1,flag2) {
        if(flag1 == 0 && flag2 == 0){
            return "0% -";
        }else if(flag2 == 0){
            return "100% <img src='${ctxStatic}/images/icons/administrative_sjt.jpg' width='10px' height='10px'/></span>"
        }else if(flag1 == 0){
            return "-100% <img src='${ctxStatic}/images/icons/administrative_xjt.jpg' width='10px' height='10px'/></span>"
        }else{
            var   flag = (flag1 - flag2 )/flag2;
            flag = (isNaN(flag)? 0 : flag);
            return (flag*100).toFixed(2)+"% "+(flag >0?"<span style='color:green;'><img src='${ctxStatic}/images/icons/administrative_sjt.jpg' width='10px' height='10px'/></span> ":"<span style='color:red;'><img src='${ctxStatic}/images/icons/administrative_xjt.jpg' width='10px' height='10px'/></span>")
        }
    }
    //置空方法(整型型)
    function AllTableIdInteger(id) {
        if (isNaN(Number($("#" + id).html())) || Number($("#" + id).html()) == 0) {
            $("#" + id).html("0")
        } else {
            $("#" + id).html(numFormat($("#" + id).html()))
        }
    }

    //处理方法，将数值超过万的值，全部四舍五入到一位小数并且加上“万”单位（浮点型）
    function handleWanFloat(id) {
        if (isNaN(Number($("#" + id).html())) || Number($("#" + id).html()) == 0) {
            $("#" + id).html("0")
        } else {
            var s = $("#" + id).html();
            s= ((s /10000) * 100)/ 100;
            var y = $("#" + id).html();
            $("#" + id).html(floatFormatWan(s,y))
        }
    }
    //数字格式化（1000.657--->1,000.66）
    function floatFormat(s) {
        n = 1;
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
        t = "";
        for (i = 0; i < l.length; i++) {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }
        return t.split("").reverse().join("") + "." + r;
    }

    function numFormat(s) {
        var result = [], counter = 0;
        s = (s || 0).toString().split('');
        for (var i = s.length - 1; i >= 0; i--) {
            counter++;
            result.unshift(s[i]);
            if (!(counter % 3) && i != 0) {
                result.unshift(',');
            }
        }
        return result.join('');
    }

    function getLastDay(date) {
        var nowdays = new Date(date);
        var year = nowdays.getFullYear();
        var month = nowdays.getMonth();
        if(month==0)
        {
            month=12;
            year=year-1;
        }
        if (month < 10) {
            month = "0" + month;
        }
        var firstDay = year + "-" + month + "-" + "01";               //上个月的第一天

        var myDate = new Date(year, month, 0);
        var lastDay = year + "-" + month + "-" + myDate.getDate();   //上个月的最后一天
        return lastDay;
    }

    /*总结：获取前一个月的日期之所以不简单，就是因为每个月天数不固定造成的，
     * 而这个方法把12个月的天数都列在一个数组里面，使之变的具体而不再抽象，
     * 用到某个月天数时候，只需要和数组对应位置的元素值比较即可
     * */
    function getLastMonthYestdy(date){
        var date = new Date(date); //        1    2    3    4    5    6    7    8    9   10    11   12月
        var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);
        var strYear = date.getFullYear();
        var strDay = date.getDate();
        var strMonth = date.getMonth()+1;
        if(strYear%4 == 0 && strYear%100 != 0){//一、解决闰年平年的二月份天数   //平年28天、闰年29天//能被4整除且不能被100整除的为闰年
            daysInMonth[2] = 29;
        }
        if(strMonth - 1 == 0) //二、解决跨年问题
        {
            strYear -= 1;
            strMonth = 12;
        }
        else
        {
            strMonth -= 1;
        }
//        strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];
        strDay = Math.min(strDay,daysInMonth[strMonth]);//三、前一个月日期不一定和今天同一号，例如3.31的前一个月日期是2.28；9.30前一个月日期是8.30

        if(strMonth<10)//给个位数的月、日补零
        {
            strMonth="0"+strMonth;
        }
        if(strDay<10)
        {
            strDay="0"+strDay;
        }
        datastr = strYear+"-"+strMonth+"-"+strDay;
        return datastr;
    }
</script>
</body>
</html>