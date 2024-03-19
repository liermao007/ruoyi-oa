<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>工作日志管理</title>
    <meta name="decorator" content="default"/>
    <style>
        .font-weight-red {
            color: red;
            font-weight: bold;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            $("#inputForm").submit();
            return false;
        }

        function pickedFunc() {
            $("#searchForm").submit();
        }
    </script>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<form:form id="searchForm" modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/loginId?ids=${ids}"
           method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>评阅日期：</label>
            <input name="sumDate" id="sumDate" type="text" readonly="readonly" maxlength="20"
                   class="input-medium Wdate "
                   value="<fmt:formatDate value="${oaSummaryDay.sumDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:pickedFunc});"/>
        </li>

        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>


<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>

        <c:choose>
            <c:when test="${type eq 1}">
                <th style="text-align: center">部门</th>
                <th style="text-align: center">姓名</th>
                <th style="text-align: center">工作总结</th>
                <th style="text-align: center">评语</th>
            </c:when>
            <c:otherwise>
                <th style="text-align: center">部门</th>
                <th style="text-align: center">姓名</th>
                <th style="text-align: center">工作总结</th>
                <th style="text-align: center">评语</th>
                <th style="text-align: center">评阅</th>
            </c:otherwise>
        </c:choose>
        <shiro:hasPermission name="oa:personSigns:edit">
            <th style="text-align: center;">操作</th>
        </shiro:hasPermission>
    </tr>

    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="oaSummaryDays">
        <%--<c:if test=" ${not empty personSigns.name}">--%>
        <tr>
            <td style="text-align: center;width: 10%">
                    ${oaSummaryDays.officeName}
            </td>
            <td style="text-align: center;width: 7%">
                    ${oaSummaryDays.evaluateByNames}
            </td>
            <td style="text-align: center;width: 30%">
                    ${oaSummaryDays.content}
            </td>
            <td style="text-align: center;width: 20%">
                    ${oaSummaryDays.evaluate}
            </td>


            <c:choose>
                <c:when test="${type eq 1}">
                    <form:form id="inputForm" modelAttribute="oaSummaryDay"
                               action="${ctx}/oa/oaSummaryDay/saveEvel?flag=1&appraiseDate=${sumDate}" method="post"
                               class="form-horizontal">


                        <shiro:hasPermission name="oa:oaSummaryDay:edit">
                            <td style="text-align: center;width: 15%">
                                <c:if test="${oaSummaryDays.isLower eq '1'}">
                                    <a href="${ctx}/oa/oaSummaryDay/loginId?loginId=${oaSummaryDays.loginId}&sumDate=${sumDate}"
                                       role="button" class="btn btn-primary">下级职员</a>
                                </c:if>
                                <input type="hidden" name="loginId" id="loginId" value="${oaSummaryDays.loginId}"/>
                            </td>
                        </shiro:hasPermission>
                    </form:form>
                </c:when>
                <c:otherwise>
                    <form:form id="inputForm" modelAttribute="oaSummaryDay"
                               action="${ctx}/oa/oaSummaryDay/saveEvel?flag=1&appraiseDate=${sumDate}" method="post"
                               class="form-horizontal">
                        <td style="text-align: center;width: 20%">
                            <textarea name="evaluateContent" id="evaluateCon" style="width:90%;margin: 0;"></textarea>
                        </td>
                        <shiro:hasPermission name="oa:oaSummaryDay:edit">
                            <td style="text-align: center;width: 15%">
                                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
                                <c:if test="${oaSummaryDays.isLower eq '1'}">
                                    <a href="${ctx}/oa/oaSummaryDay/loginId?loginId=${oaSummaryDays.loginId}&sumDate=${sumDate}"
                                       role="button" class="btn btn-primary">下级职员</a>
                                </c:if>
                                <input type="hidden" name="loginId" id="loginId" value="${oaSummaryDays.loginId}"/>
                            </td>
                        </shiro:hasPermission>
                    </form:form>
                </c:otherwise>
            </c:choose>
        </tr>
        <%--</c:if>--%>
    </c:forEach>
    </tbody>
</table>
<div class="pagination"></div>
<script>
    $(function () {
        $("#btnSubmit").click(function () {
            var evaluateCon = $("#evaluateCon").val();
            var loginId = $("#loginId").val();
            if (evaluateCon == "") {
                return;
            }
            if (loginId == "") {
                return;
            }
            console.log(window.location.href);
        });
    });
</script>
</body>
</html>