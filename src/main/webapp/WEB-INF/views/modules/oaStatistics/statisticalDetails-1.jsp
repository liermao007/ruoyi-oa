<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/3/1
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>Title</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/layer/skin/default/layer.css">
    <style>
        .table {
            width:95%;}
    </style>
</head>
<body>
<table id="contentTable" class="table table-striped table-bordered table-condensed"
 style="margin: 2.4%;">
    <thead>
    <tr>
        <c:forEach var="field" items="${field}">
            <th>${field.remarks}</th>
        </c:forEach>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${data}" var="data">
        <tr>
            <c:forEach items="${field}" var="field">
                <c:set var='zd' value="${field.fieldName}" scope="page"/>
                <td>${data[zd]}</td>
            </c:forEach>
            <td><input value="查看详情" type="button" onclick="particulars('${data.id}','${data.procInsId}','${tableName}')"></td>
        </tr>
        </c:forEach>
    </tbody>
</table>

<script type="text/javascript">
    function particulars(id,procInsId,tableName) {
       var url ="/a/oa/flow/form?id="+id+"&formNo="+tableName+"&showType=flowView&act.procInsId="+procInsId;
        layerTree =layer.open({
            title:'申请单详情',
            type: 2,
            area: ['90%', '100%'],
            offset:'0px',
            fixed: false, //不固定
            maxmin: false,
            content: url
        });
    }
</script>
</body>
</html>
