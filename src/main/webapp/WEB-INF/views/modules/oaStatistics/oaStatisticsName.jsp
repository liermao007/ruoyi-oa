<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/5
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

    <title>数据统计</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<c:if test="${not empty message}">
    <div id="messageBox" class="alert alert-success ">
        <a href="#" class="close" data-dismiss="alert">
            &times;
        </a>
        <strong>${message}</strong>
    </div>
</c:if>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>统计名称</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${oaStatisticsName}" var="a" varStatus="i">
            <tr>
                <td>
                        ${a.statisticsName}
                </td>
                <td>
                        ${a.remarks}
                </td>
                <td>
                    <shiro:hasPermission name="sys:oaStatistics:edit">
                    <a href="${ctx}/OaStatistics/oaStatistics/particulars?id=${a.id}">详情</a>
                    <a href="${ctx}/OaStatistics/oaStatistics/updateName?id=${a.id}">修改</a>
                    <a href="${ctx}/OaStatistics/oaStatistics/delete?id=${a.id}" onclick="return confirmx('确认要删除该自定义数据源吗？', this.href)">删除</a>
                    </shiro:hasPermission>
                    <a href="#" id="seeAll" onclick="seeAllData('${a.id}')">查看全部数据</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<shiro:hasPermission name="sys:oaStatistics:edit">
    <div class="pagination">
        <a href="${ctx}/OaStatistics/oaStatistics/addName"><input type="button" class="btn btn-primary" value="添加统计"></a>&nbsp;&nbsp;
    </div>
</shiro:hasPermission>
<script type="text/javascript">
    function seeAllData(oaStatisticsNameId) {
        $.ajax({
            type : "post",
            async : true,
            url : '${ctx}/OaStatistics/oaStatistics/gettingAllVerify?TJMCID='+oaStatisticsNameId,
            success:function(dates){
                if (dates){
                    $(location).attr('href', '${ctx}/OaStatistics/oaStatistics/gettingAllData?TJMCID='+oaStatisticsNameId);
                }else {
                    alert("表字段不同，不能使用查看全部数据")
                }
            },
            error: function() {
            }
        });
    }
</script>
</body>
</html>
