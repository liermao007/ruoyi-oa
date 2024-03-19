<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>联系人</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/oa/mailInfo/phone/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>姓名：</label>
    <form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    <input type="button" value="写信" class="btn btn-success" onclick="mail()">
</form:form>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr><th align="left"><input id="checkall" type="checkbox"> </th><th>姓名</th><th>邮箱</th><th>电话</th></thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td style="width: 40px">
                <input type="checkbox" name="checkbox" value="${user.id}">
            </td>
            <td>${user.name}</td>
            <td>${fns:abbr(user.email,50)}</td>
            <td>${user.phone}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<script>
    $("#checkall").click(
        function(){
            if(this.checked){
                $("input[name='checkbox']").each(function(){this.checked=true;});
            }else{
                $("input[name='checkbox']").each(function(){this.checked=false;});
            }
        }
    );
    //彻底删除
    function mail() {
        var checked = false;
        var ids = document.getElementsByName("checkbox");
        var chestr = "";
        for (var i = 0; i < ids.length; i++) {
            if (ids[i].checked) {
                checked = true;
                chestr += ids[i].value + ",";
            }
        }
        chestr=chestr.substring(0,chestr.length-1)
        if (!checked) {
            document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 140px;height: 25px;text-align: center;position: relative;bottom: 10px;left: -250px;line-height: 20px'>未选中任何联系人</div>";
            return;
        }
        window.location.href = "${ctx}/oa/mailInfo/phoneWrite?ids=" + chestr;
    }
</script>
</body>
</html>