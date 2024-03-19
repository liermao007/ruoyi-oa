<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/23
  Time: 16:20
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
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
       <c:forEach items="${columnList}" var="column" varStatus="i">
           <c:if test="${column.name != 'id' && column.name != 'create_by' && column.name != 'create_date' && column.name != 'update_by' && column.name != 'update_date' && column.name != 'remarks' && column.name != 'del_flag' && column.name != 'proc_ins_id' && column.name != 'proc_def_id'}">
               <c:set value="${column.name}" var="col"></c:set>
               <th>${column.comments}</th>
            </c:if>
       </c:forEach>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${data}" var="data" varStatus="i">
        <tr>
        <c:forEach items="${columnList}" var="column" varStatus="j">


            <%--<c:if test="${zd != 'id' && zd != 'create_by' && zd != 'create_date' && zd != 'update_by' && zd != 'update_date' && zd != 'del_flag' && zd != 'proc_ins_id' && zd != 'proc_def_id'}">--%>
            <c:if test="${column.name != 'id' && column.name != 'create_by' && column.name != 'create_date' && column.name != 'update_by' && column.name != 'update_date' && column.name != 'remarks' && column.name != 'del_flag' && column.name != 'proc_ins_id' && column.name != 'proc_def_id'}">
                <c:set var='zd' value="${column.name}" scope="page"/>
                <c:if test="${dataType != null && dataType != ''}">
                    <td>${fns:getDictLabel(data[zd],dataType,'')}</td>
                </c:if>
                <c:if test="${dataType == null || dataType == ''}">
                    <td>${data[zd]}</td>
                </c:if>
            </c:if>
        </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
