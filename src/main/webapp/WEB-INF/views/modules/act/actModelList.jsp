<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>模型管理</title>
    <meta name="decorator" content="default"/>
    <script src="/static/layer/layer.js" type="text/javascript"></script>
    <link href="/static/layer/layer.css" rel="stylesheet" type="text/css">
    <style>
        .layui-layer-content {
            height: auto !important;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            top.$.jBox.tip.mess = null;
        });
        function page(n, s) {
            location = '${ctx}/act/model/?pageNo=' + n + '&pageSize=' + s;
        }
        function updateCategory(id, category) {
            $.jBox($("#categoryBox").html(), {
                title: "设置分类", buttons: {"关闭": true}, submit: function () {
                }
            });
            $("#categoryBoxId").val(id);
            $("#categoryBoxCategory").val(category);
        }


        function lineBy(id) {
            $.post('${ctx}/act/model/form?id=' + id, {}, function (str) {
                var p1 = layer.open({
                    type: 1,
                    title: '流程信息',
                    area: ['500px', '200px'],
                    content: str
                });
            });
        }
    </script>
    <script type="text/template" id="categoryBox">
        <form id="categoryForm" action="${ctx}/act/model/updateCategory" method="post" enctype="multipart/form-data"
              style="text-align:center;" class="form-search" onsubmit="loading('正在分类，请稍等...');"><br/>
            <input id="categoryBoxId" type="hidden" name="id" value=""/>
            <select id="categoryBoxCategory" name="category">
                <option value="">无分类</option>
                <c:forEach items="${fns:getDictList('act_category')}" var="dict">
                    <option value="${dict.value}">${dict.label}</option>
                </c:forEach>
            </select>
            <br/><br/><input id="categorySubmit" class="btn btn-primary" type="submit" value="   保    存   "/>　　
        </form>
    </script>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<form id="searchForm" action="${ctx}/act/model/" method="post" class="breadcrumb form-search">
    <%--<select id="category" name="category" class="input-medium">
        <option value="">全部分类</option>
        <c:forEach items="${fns:getDictList('act_category')}" var="dict">
            <option value="${dict.value}" ${dict.value==category?'selected':''}>${dict.label}</option>
        </c:forEach>
    </select>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
    <a href="${ctx}/act/model/create" role="button" class="btn btn-primary">添加</a>
</form>
<sys:message content="${message}"/>
<table class="table table-striped table-bordered table-condensed table-nowrap">
    <thead>
    <tr>
        <th>模型机构</th>
        <th>模型分类</th>
        <th>模型ID</th>
        <th>模型标识</th>
        <th>模型名称</th>
        <th>版本号</th>
        <th>创建时间</th>
        <th>最后更新时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="model">
        <tr>
            <td><a href="javascript:updateCategory('${model.id}', '${model.category}')"
                   title="设置分类">${fns:getDictLabel(model.category,'act_category','无分类')}</a></td>
            <th>${fns:getDictLabel(model.tenantId,'act_type','无分类')}</th>
            <td>${model.id}</td>
            <td>${model.key}</td>
            <td>${model.name}</td>
            <td><b title='流程版本号'>V: ${model.version}</b></td>
            <td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${model.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/act/process-editor/modeler.jsp?modelId=${model.id}"
                   target="_blank">编辑</a>
                <a href="${ctx}/act/model/deploy?id=${model.id}"
                   onclick="return confirmx('确认要部署该模型吗？', this.href)">部署</a>
                <a href="${ctx}/act/model/export?id=${model.id}" target="_blank">导出</a>
                <a onclick="lineBy('${model.id}')">复制</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
