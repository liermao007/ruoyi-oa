<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>医院经营指标统计表</title>
    <meta name="decorator" content="default"/>
    <style>
        .tablethead {
            font-weight: bold;
            font-size: 18px;
        }

        .accordion-heading, .table th, .accordion-heading, .table td {
            white-space: inherit;
            font-weight: normal;
            background-color: #fff;
            background-image: none;
            text-align: center;
        }

        .table thead th {
            vertical-align: middle;
        }

        .table-striped tbody > tr:nth-child(odd) > td, .table-striped tbody > tr:nth-child(odd) > th {
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

            bdhtml = window.document.body.innerHTML;
            zoom(parseInt($("#printSelect").val()));
            window.document.body.innerHTML = $('table').eq(0).html();
            setTimeout('yscz()', 500);
        }
        function zoom(level) {
            var i = parseInt(document.body.style.zoom);
            if (isNaN(i)) i = 100;
            newZoom = i * level / 100;
            document.body.style.zoom = newZoom + '%';
        }

        function yscz() {
            window.print();
            document.body.style.width = "100%";
            document.body.style.height = "100%";
            window.document.body.innerHTML = bdhtml;
            location.reload();
        }

    </script>

</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<form:form id="searchForm" modelAttribute="hospitalMasterIndex" action="${ctx}/process/hospitalMasterIndex/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li class="btns">日期: <input id="month" name="month" type="text" readonly="readonly"
                                    maxlength="20"
                                    class="input-medium Wdate "
                                    value="<fmt:formatDate value="${hospitalMasterIndex.month}" pattern="yyyy-MM"/>"
                                    onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,onpicked:pickedFunc});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><a href="${ctx}/process/hospitalMasterIndex/form" role="button"
                            class="btn btn-primary">添加/修改</a></li>
        <li class="btns">
            缩放比例：
            <select id="printSelect" style="width:75px;">
                <option value="27">30%</option>
                <option value="45">50%</option>
                <option value="54">60%</option>
                <option value="63">70%</option>
                <option value="72">80%</option>
                <option value="76">85%</option>
                <option value="81">90%</option>
                <option value="85">95%</option>
                <option value="90" selected="true">100%</option>
                <option value="112">125%</option>
                <option value="135">150%</option>
                <option value="180">200%</option>
            </select></li>
        <li class="btns"><input type="button" class="btn btn-primary" value="打印" onclick="preview()"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div style="margin: 10px 50px 50px;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody>
        <tr>
            <td>
                <div style="width: 100%; " align="center"><h3><b>中国卫生集团所属医院——医院经营指标统计表</b></h3></div>
                <span id="span1"></span>
                <c:set var="quar" value="${hospitalMasterIndex}"></c:set>
                <c:if test="${quar.quarter eq 1}">
                    <c:set value="一" var="yearQuar"></c:set>
                </c:if>
                <c:if test="${quar.quarter eq 2}">
                    <c:set value="二" var="yearQuar"></c:set>
                </c:if>
                <c:if test="${quar.quarter eq 3}">
                    <c:set value="三" var="yearQuar"></c:set>
                </c:if>
                <c:if test="${quar.quarter eq 4}">
                    <c:set value="四" var="yearQuar"></c:set>
                </c:if>
                <c:set var="office861100292" value="${officeSize861100292}"></c:set>
                <c:set var="office861100301" value="${officeSize861100301}"></c:set>
                <c:set var="office861100341" value="${officeSize861100341}"></c:set>
                <c:set var="office864306661" value="${officeSize864306661}"></c:set>
                <c:choose>
                <c:when test="${list861100292 != null && fn:length(list861100292) > 0}">
                <span class="tablethead"><b>${office}2018年第${yearQuar}季度双滦医院执行院长考评指标统计表</b></span>
                <table id="contentTable2" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${list861100292}" var="hospitalMasterIndex" varStatus="i">
                        <c:set var="quarter" value="${hospitalMasterIndex.quarter}"></c:set>
                        <c:if test="${hospitalMasterIndex.org == '861100292'}">
                                <tr name="people">
                                    <td style="border-left:none;">${i.count}</td>
                                    <td id="people1${i.count}">${hospitalMasterIndex.masterId}</td>
                                    <td id="people20${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountMonths == null ? "0" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)}</td>
                                    <td id="people21${i.count}">${hospitalMasterIndex.groupCashActualAmountMonths == null ? "0" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2)}</td>
                                    <td id="people4${i.count}">${((hospitalMasterIndex.groupCashActualAmountMonths == null? 0 : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2))/
                                    (hospitalMasterIndex.groupCashBudgetaryAmountMonths ==null ? 0: fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)))*100}%</td>
                                    <td id="people22${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)}</td>
                                    <td id="people23${i.count}">${hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2)}</td>
                                    <td id="people5${i.count}">${((hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2))/
                                            (hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)))*100}%</td>
                                    <td id="people24${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)}</td>
                                    <td id="people25${i.count}">${hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2)}</td>
                                    <td id="people6${i.count}">${((hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2))/
                                            (hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)))*100}%</td>
                                    <td id="people7${i.count}">
                                        <c:if test="${hospitalMasterIndex.masterId eq '上交集团现金'}">40%</c:if>
                                        <c:if test="${hospitalMasterIndex.masterId eq '医疗收入'}">20%</c:if>
                                        <c:if test="${hospitalMasterIndex.masterId eq '成本支出'}">20%</c:if>
                                        <c:if test="${hospitalMasterIndex.masterId eq '利润'}">10%</c:if>
                                        <c:if test="${hospitalMasterIndex.masterId eq '现金运营指数'}">10%</c:if>
                                        <c:if test="${hospitalMasterIndex.masterId eq '主渠道采购率'}">100%</c:if>
                                        <c:if test="${hospitalMasterIndex.masterId eq '质量达标'}">100%</c:if>
                                    </td>
                                    <td id="people8${i.count}"> </td>
                                    <c:if test="${i.count eq 1}">
                                        <td rowspan="5" id="people13${i.count}">40%</td>
                                    </c:if>
                                    <c:if test="${i.count eq 6}">
                                        <td  id="people13${i.count}">30%</td>
                                    </c:if>
                                    <c:if test="${i.count eq 7}">
                                        <td  id="people13${i.count}">30%</td>
                                    </c:if>
                                    <c:if test="${i.count eq 1}">
                                        <td rowspan="5" id="people14${i.count}"></td>
                                    </c:if>
                                    <c:if test="${i.count eq 6}">
                                        <td  id="people14${i.count}"></td>
                                    </c:if>
                                    <c:if test="${i.count eq 7}">
                                        <td  id="people14${i.count}"></td>
                                    </c:if>
                                </tr>
                        </c:if>
                    </c:forEach>
                    <tr name="people00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td id="total0"></td>
                    </tr>
                    </tbody>
                </table>
                </c:when>
                <c:when test="${(list861100292 == null || fn:length(list861100292) <= 0) && office861100292 eq 1}">
                <span class="tablethead"><b>2018年第${yearQuar}季度双滦医院执行院长考评指标统计表</b></span>
                <table id="contentTable2" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr name="people00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td id=""></td>
                    </tr>
                    </tbody>
                </table>
                </c:when>
                </c:choose>
                <br>
                <br>
                <c:choose>
                <c:when test="${list861100301 != null && fn:length(list861100301) > 0}">
                <span class="tablethead"><b>2018年第${yearQuar}季度安平医院执行院长考评指标统计表</b></span>
                <table id="contentTable2" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${list861100301}" var="hospitalMasterIndex" varStatus="i">
                        <c:set var="quarter" value="${hospitalMasterIndex.quarter}"></c:set>
                        <c:if test="${hospitalMasterIndex.org == '861100301'}">
                            <tr name="check">
                                <td style="border-left:none;">${i.count}</td>
                                <td id="check1${i.count}">${hospitalMasterIndex.masterId}</td>
                                <td id="check20${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)}</td>
                                <td id="check21${i.count}">${hospitalMasterIndex.groupCashActualAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2)}</td>
                                <td id="check4${i.count}">${((hospitalMasterIndex.groupCashActualAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)))*100}%</td>
                                <td id="check22${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)}</td>
                                <td id="check23${i.count}">${hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2)}</td>
                                <td id="check5${i.count}">${((hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)))*100}%</td>
                                <td id="check24${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)}</td>
                                <td id="check25${i.count}">${hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2)}</td>
                                <td id="check6${i.count}">${((hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)))*100}%</td>
                                <td id="check7${i.count}">
                                    <c:if test="${hospitalMasterIndex.masterId eq '上交集团现金'}">40%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '医疗收入'}">20%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '成本支出'}">20%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '利润'}">10%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '现金运营指数'}">10%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '主渠道采购率'}">100%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '质量达标'}">100%</c:if>
                                </td>
                                <td id="check8${i.count}"> </td>
                                <c:if test="${i.count eq 1}">
                                    <td rowspan="5" id="check13${i.count}">40%</td>
                                </c:if>
                                <c:if test="${i.count eq 6}">
                                    <td  id="check13${i.count}">30%</td>
                                </c:if>
                                <c:if test="${i.count eq 7}">
                                    <td  id="check13${i.count}">30%</td>
                                </c:if>
                                <c:if test="${i.count eq 1}">
                                    <td rowspan="5" id="check14${i.count}"></td>
                                </c:if>
                                <c:if test="${i.count eq 6}">
                                    <td  id="check14${i.count}"></td>
                                </c:if>
                                <c:if test="${i.count eq 7}">
                                    <td  id="check14${i.count}"></td>
                                </c:if>
                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr name="check00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td id="total1"></td>
                    </tr>
                    </tbody>
                </table>
                </c:when>
                <c:when test="${(list861100301 == null || fn:length(list861100301) <= 0) && office861100301 eq 1}">
                <span class="tablethead"><b>2018年第${yearQuar}季度安平医院执行院长考评指标统计表</b></span>
                <table id="contentTable2" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr name="check00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td ></td>
                    </tr>
                    </tbody>
                </table>
                </c:when>
                </c:choose>

                <c:choose>
                <c:when test="${list861100341 != null && fn:length(list861100341) > 0}">
                <span class="tablethead"><b>2018年第${yearQuar}季度滦平医院执行院长考评指标统计表</b></span>
                <table id="contentTable62" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${list861100341}" var="hospitalMasterIndex" varStatus="i">
                        <c:set var="quarter" value="${hospitalMasterIndex.quarter}"></c:set>
                        <c:if test="${hospitalMasterIndex.org == '861100341'}">
                            <tr name="luanping">
                                <td style="border-left:none;">${i.count}</td>
                                <td id="luanping1${i.count}">${hospitalMasterIndex.masterId}</td>
                                <td id="luanping20${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)}</td>
                                <td id="luanping21${i.count}">${hospitalMasterIndex.groupCashActualAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2)}</td>
                                <td id="luanping4${i.count}">${((hospitalMasterIndex.groupCashActualAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)))*100}%</td>
                                <td id="luanping22${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)}</td>
                                <td id="luanping23${i.count}">${hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2)}</td>
                                <td id="luanping5${i.count}">${((hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)))*100}%</td>
                                <td id="luanping24${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)}</td>
                                <td id="luanping25${i.count}">${hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2)}</td>
                                <td id="luanping6${i.count}">${((hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)))*100}%</td>
                                <td id="luanping7${i.count}">
                                    <c:if test="${hospitalMasterIndex.masterId eq '上交集团现金'}">40%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '医疗收入'}">20%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '成本支出'}">20%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '利润'}">10%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '现金运营指数'}">10%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '主渠道采购率'}">100%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '质量达标'}">100%</c:if>
                                </td>
                                <td id="luanping8${i.count}"> </td>
                                <c:if test="${i.count eq 1}">
                                    <td rowspan="5" id="luanping13${i.count}">40%</td>
                                </c:if>
                                <c:if test="${i.count eq 6}">
                                    <td  id="luanping13${i.count}">30%</td>
                                </c:if>
                                <c:if test="${i.count eq 7}">
                                    <td  id="luanping13${i.count}">30%</td>
                                </c:if>
                                <c:if test="${i.count eq 1}">
                                    <td rowspan="5" id="luanping14${i.count}"></td>
                                </c:if>
                                <c:if test="${i.count eq 6}">
                                    <td  id="luanping14${i.count}"></td>
                                </c:if>
                                <c:if test="${i.count eq 7}">
                                    <td  id="luanping14${i.count}"></td>
                                </c:if>
                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr name="luanping00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td id="total2"></td>
                    </tr>
                    </tbody>
                </table>
                </c:when>
                <c:when test="${(list861100341 == null || fn:length(list861100341) <= 0) && office861100341 eq 1}">
                <span class="tablethead"><b>2018年第${yearQuar}季度滦平医院执行院长考评指标统计表</b></span>
                <table id="contentTable2" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr name="luangping00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td ></td>
                    </tr>
                    </tbody>
                </table>
                </c:when>
                </c:choose>

                <c:choose>
                <c:when test="${list864306661 != null && fn:length(list864306661) > 0}">
                <span class="tablethead"><b>2018年第${yearQuar}季度巴陵医院执行院长考评指标统计表</b></span>
                <table id="contentTable62" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${list864306661}" var="hospitalMasterIndex" varStatus="i">
                        <c:set var="quarter" value="${hospitalMasterIndex.quarter}"></c:set>
                        <c:if test="${hospitalMasterIndex.org == '864306661'}">
                            <tr name="baling">
                                <td style="border-left:none;">${i.count}</td>
                                <td id="baling1${i.count}">${hospitalMasterIndex.masterId}</td>
                                <td id="baling20${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)}</td>
                                <td id="baling21${i.count}">${hospitalMasterIndex.groupCashActualAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2)}</td>
                                <td id="baling4${i.count}">${((hospitalMasterIndex.groupCashActualAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountMonths,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountMonths == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountMonths,2)))*100}%</td>
                                <td id="baling22${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)}</td>
                                <td id="baling23${i.count}">${hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2)}</td>
                                <td id="baling5${i.count}">${((hospitalMasterIndex.groupCashActualAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountQuarter,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountQuarter == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountQuarter,2)))*100}%</td>
                                <td id="baling24${i.count}">${hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)}</td>
                                <td id="baling25${i.count}">${hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2)}</td>
                                <td id="baling6${i.count}">${((hospitalMasterIndex.groupCashActualAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashActualAmountYear,2))/
                                        (hospitalMasterIndex.groupCashBudgetaryAmountYear == 0 ? "" : fns:getMathUtils(hospitalMasterIndex.groupCashBudgetaryAmountYear,2)))*100}%</td>
                                <td id="baling7${i.count}">
                                    <c:if test="${hospitalMasterIndex.masterId eq '上交集团现金'}">40%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '医疗收入'}">20%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '成本支出'}">20%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '利润'}">10%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '现金运营指数'}">10%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '主渠道采购率'}">100%</c:if>
                                    <c:if test="${hospitalMasterIndex.masterId eq '质量达标'}">100%</c:if>
                                </td>
                                <td id="baling8${i.count}"> </td>
                                <c:if test="${i.count eq 1}">
                                    <td rowspan="5" id="baling13${i.count}">40%</td>
                                </c:if>
                                <c:if test="${i.count eq 6}">
                                    <td  id="baling13${i.count}">30%</td>
                                </c:if>
                                <c:if test="${i.count eq 7}">
                                    <td  id="baling13${i.count}">30%</td>
                                </c:if>
                                <c:if test="${i.count eq 1}">
                                    <td rowspan="5" id="baling14${i.count}"></td>
                                </c:if>
                                <c:if test="${i.count eq 6}">
                                    <td  id="baling14${i.count}"></td>
                                </c:if>
                                <c:if test="${i.count eq 7}">
                                    <td  id="baling14${i.count}"></td>
                                </c:if>
                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr name="baling00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td id="total3"></td>
                    </tr>
                    </tbody>
                </table>
        </c:when>
                <c:when test="${(list864306661 == null || fn:length(list864306661) <= 0) && office864306661 eq 1}">
                <span class="tablethead"><b>2018年第${yearQuar}季度巴陵医院执行院长考评指标统计表</b></span>
                <table id="contentTable2" class="table table-striped table-bordered1 table-condensed">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>月预算金额</th>
                        <th>月实际金额</th>
                        <th>月完成（%）</th>
                        <th>第${yearQuar}季度预算金额</th>
                        <th>第${yearQuar}季度实际金额</th>
                        <th>第${yearQuar}季度完成（%）</th>
                        <th>第${yearQuar}年度预算金额</th>
                        <th>第${yearQuar}年度实际金额</th>
                        <th>第${yearQuar}年度累计完成（%）</th>
                        <th>权重</th>
                        <th>得分</th>
                        <th>整体比例</th>
                        <th>折算分值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr name="baling00">
                        <td colspan="13" ></td>
                        <td>总分：</td>
                        <td ></td>
                    </tr>
                    </tbody>
                </table>
                </c:when>
        </c:choose>
        </tbody>
    </table>

</div>
<script type="text/javascript">

    var su =  $.get("${ctx}/process/hospitalMasterIndex/formData?month=" + $("#month").val(), function (data) {
        allList = data;
        var sl=0,ap=0,lp=0,bl=0;
        $.each(allList, function (i, e) {
            if(e.orgId == "861100292" || e.org == "861100292"){
                sl = 1
            }else  if(e.orgId == "861100301" ||  e.org == "861100301"){
                ap = 1
            }else  if(e.orgId == "861100341" ||  e.org == "861100341"){
                lp  = 1
            }else  if(e.orgId == "864306661" ||  e.org == "864306661"){
                bl = 1
            }
        });
        //双滦
        if(sl > 0){
            var branches = $("tr [name='people']").length;
            for (var i = 1; i <= branches; i++) {
                for (var j = 4; j <=6; j++) {
                    AllTableIdPrecentFloat("people" + j + i);
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 5; j <=7; j++) {
                    if( j == 5){
                        calculateScore("people" + j + i ,"people"+ (j+1)+i,"people"+(j+2)+i,"people"+(j+3)+i);
                    }else if(j == 6){
                        calculateScore("people" + (j-1) + i ,"people"+j+i,"people"+(j+1)+i,"people"+(j+2)+i);
                    }else if(j== 7){
                        calculateScore("people" + (j-2) + i ,"people"+(j-1)+i,"people"+j+i,"people"+(j+1)+i);
                    }
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 20; j <=25; j++) {
                    AllTableIdFloat("people" + j + i);
                    handleFloatWan("people"+j+i);
                }
            }
            //计算折算分值,首先先计算合并单元格的数据
            calculateMerge("people")
            //计算折算分值 ，其他没有合并单元格的数据
            calculateNotMerge("people")
            //计算折算分数总计
            calculateTotal("people","total0")

        }
        if( ap >0){
            //安平
            var branches = $("tr [name='check']").length;
            for (var i = 1; i <= branches; i++) {
                for (var j = 4; j <=6; j++) {
                    AllTableIdPrecentFloat("check" + j + i);
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 5; j <=7; j++) {
                    if( j == 5){
                        calculateScore("check" + j + i ,"check"+ (j+1)+i,"check"+(j+2)+i,"check"+(j+3)+i);
                    }else if(j == 6){
                        calculateScore("check" + (j-1) + i ,"check"+j+i,"check"+(j+1)+i,"check"+(j+2)+i);
                    }else if(j== 7){
                        calculateScore("check" + (j-2) + i ,"check"+(j-1)+i,"check"+j+i,"check"+(j+1)+i);
                    }
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 20; j <=25; j++) {
                    AllTableIdFloat("check" + j + i);
                    handleFloatWan("check"+j+i);
                }
            }

            //计算折算分值,首先先计算合并单元格的数据
            calculateMerge("check")
            //计算折算分值 ，其他没有合并单元格的数据
            calculateNotMerge("check")
            //计算折算分数总计
            calculateTotal("check","total1")
        }
        if (lp >0){
            //滦平
            var branches = $("tr [name='luanping']").length;
            for (var i = 1; i <= branches; i++) {
                for (var j = 4; j <=6; j++) {
                    AllTableIdPrecentFloat("luanping" + j + i);
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 5; j <=7; j++) {
                    if( j == 5){
                        calculateScore("luanping" + j + i ,"luanping"+ (j+1)+i,"luanping"+(j+2)+i,"luanping"+(j+3)+i);
                    }else if(j == 6){
                        calculateScore("luanping" + (j-1) + i ,"luanping"+j+i,"luanping"+(j+1)+i,"luanping"+(j+2)+i);
                    }else if(j== 7){
                        calculateScore("luanping" + (j-2) + i ,"luanping"+(j-1)+i,"luanping"+j+i,"luanping"+(j+1)+i);
                    }
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 20; j <=25; j++) {
                    AllTableIdFloat("luanping" + j + i);
                    handleFloatWan("luanping"+j+i);
                }
            }

            //计算折算分值,首先先计算合并单元格的数据
            calculateMerge("luanping")
            //计算折算分值 ，其他没有合并单元格的数据
            calculateNotMerge("luanping")
            //计算折算分数总计
            calculateTotal("luanping","total2")
        }
        if(bl > 0){
            //巴陵
            var branches = $("tr [name='baling']").length;
            for (var i = 1; i <= branches; i++) {
                for (var j = 4; j <=6; j++) {
                    AllTableIdPrecentFloat("baling" + j + i);
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 5; j <=7; j++) {
                    if( j == 5){
                        calculateScore("baling" + j + i ,"baling"+ (j+1)+i,"baling"+(j+2)+i,"baling"+(j+3)+i);
                    }else if(j == 6){
                        calculateScore("baling" + (j-1) + i ,"baling"+j+i,"baling"+(j+1)+i,"baling"+(j+2)+i);
                    }else if(j== 7){
                        calculateScore("baling" + (j-2) + i ,"baling"+(j-1)+i,"baling"+j+i,"baling"+(j+1)+i);
                    }
                }
            }
            for (var i = 1; i <= branches; i++) {
                for (var j = 20; j <=25; j++) {
                    AllTableIdFloat("baling" + j + i);
                    handleFloatWan("baling"+j+i);
                }
            }

            //计算折算分值,首先先计算合并单元格的数据
            calculateMerge("baling")
            //计算折算分值 ，其他没有合并单元格的数据
            calculateNotMerge("baling")
            //计算折算分数总计
            calculateTotal("baling","total3")
        }
    })

    //计算得分（公式：季度完成 * 年度完成 * 权重）
    function calculateScore(quarterId,yearId,Id,calId) {
        var quar = $("#"+quarterId).html()
        var quar1 = quar.replace(/[","]/g,"")
        //季度完成
        var quarAfter = Number(quar1.replace(/["%"]/g,""));
        var year = $("#"+yearId).html();
        var year1 = year.replace(/[","]/g,"")
        //年完成
        var yearAfter = Number(year1.replace(/["%"]/g,""));
        var idBefore = $("#"+Id).html();
        var idBefore1 = idBefore.replace(/[","]/g,"")
        //权重
        var idAfter = Number(idBefore1.replace(/["%"]/g,""));
        var sum = (quarAfter/100) * (yearAfter/100) * 100
        $("#"+calId).html(floatFormat(sum));
    }

    //计算折算分值,首先先计算合并单元格的数据
    function calculateMerge(id){
//        alert()
        //合并之后的整体比例
        var sum = $("#"+id+"131").html();
        var sum1 = sum.replace(/[","]/g,"")
        var sumAfter = Number(sum1.replace(/["%"]/g,"")/100);
        //没有合并的得分数相加
        var v81 = $("#"+id+"81").html()
        var v82 = $("#"+id+"82").html()
        var v83 = $("#"+id+"83").html()
        var v84 = $("#"+id+"84").html()
        var v85 = $("#"+id+"85").html()
        var total = Number(v81.replace(/[","]/g,""))+ Number(v82.replace(/[","]/g,""))+ Number(v83.replace(/[","]/g,""))+ Number(v84.replace(/[","]/g,""))+ Number(v85.replace(/[","]/g,""))
        var totalSum =Number(sumAfter * total)
        $("#"+id+"141").html(floatFormat(totalSum));
    }

    //计算折算分值,首先先计算合并单元格的数据
    function calculateNotMerge(id){
        for(var i = 6 ;i<=7;i++){
            var sum = $("#"+id+"13"+i).html();
            var sum1= sum.replace(/[","]/g,"")
            var sumAfter = Number(sum1.replace(/["%"]/g,"")/100);
            //没有合并的得分数相加
            var to = $("#"+id+"8"+i).html();
            var to1 = to.replace(/[","]/g,"");
            var total = Number(to1)
            var totalSum =Number(sumAfter * total)
            $("#"+id+"14"+i).html(floatFormat(totalSum));
        }
    }

    //计算折算分值总计
    function calculateTotal(id,totalId){
            //没有合并的得分数相加
            for(var i = 0;i<4;i++){
                var v141 = $("#"+id+"141").html();
                var v146 = $("#"+id+"146").html();
                var v147 = $("#"+id+"147").html();
                var total = Number(v141.replace(/[","]/g,""))+Number(v146.replace(/[","]/g,""))+Number(v147.replace(/[","]/g,""))
                $("#"+totalId).html(floatFormat(total));
            }

    }




    //置空方法(浮点型)
    function AllTableIdFloat(id) {
        if (isNaN(Number($("#" + id).html())) || Number($("#" + id).html()) == 0) {
            $("#" + id).html("0")
        } else {
            var s = $("#" + id).html();
            var s1 = s.replace(/[","]/g,"")
            if(s1.indexOf("-")!= -1){
               var lIndex =  parseFloat((s1 + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
                $("#" + id).html(lIndex)
            }else{
                $("#" + id).html(floatFormat(s1))
            }
        }
    }




    //置空方法(浮点型,特殊处理，里面的数据带有百分号)
    function AllTableIdPrecentFloat(id) {
       var s =$("#"+id).html();
       var s_new  = s.replace(/["%"]/g,"");
//        var sIndex = s_new.in
        if (s_new == "NaN" || s_new == "Infinity" || s_new == "NaN.undefined") {
            $("#" + id).html("0%")
        } else if(s_new.indexOf("-") != -1){
            var fl = parseFloat((s_new + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
            $("#" + id).html(fl + "%")
        }else{
            $("#" + id).html(floatFormat(s_new) + "%")
        }
    }

    //处理前四张报表中各个医院的数据，如果位数超过万，则取一位小数并加“万”字
    function handleFloatWan(id){
        var s =$("#"+id).html();
        if(s != null && s != '') {
            var s_new = s.replace(/[","]/g, "");
            if (s_new / 10000 >= 1) {
                var s_new_1 = ((s_new / 10000) * 100) / 100;
                var t = floatFormat(s_new_1);
                $("#"+id).html(t + "万");
            }else if(s_new.indexOf("-")!= -1){
                var s_new_1 = ((s_new / 10000) * 100) / 100;
                var w =parseFloat((s_new_1 + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
                $("#"+id).html(w + "万");
            }
        }
    }

    //数字格式化（1000.657--->1,000.66）
    function floatFormat(s) {
        n = 2;
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

</script>

</body>
</html>