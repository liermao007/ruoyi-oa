<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>已办流程</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
    <style type="text/css">
        td{
            text-align: center;
        }
        #process{
            color: #2fa4e7;
            cursor: pointer;
        }
        #process:hover{
            color: #2fa4e7;
            text-decoration:underline
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <ul class="breadcrumb">
        <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
        <li id="levelMenu2" class="active"></li>
    </ul>
</ul>

<table id="cavas" class="table table-striped table-bordered table-condensed" style="background: rgb(255, 255, 255);">
    <thead>
    <tr>
        <th  style="text-align: center">姓名</th>
        <th  style="text-align: center">部门</th>
        <th  style="text-align: center">标题</th>
        <th  style="text-align: center">标识</th>
        <th  style="text-align: center">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${fns:findCustomHiTaskinst()}" var="customHiTaskinst">
        <tr>
            <td style="text-align: center">${customHiTaskinst.name}</td>
            <td style="text-align: center">${customHiTaskinst.taskDefKey}</td>
            <td style="text-align: center">${customHiTaskinst.comment}</td>
            <td style="text-align: center">${customHiTaskinst.reason}</td>
            <td style="text-align: center">
                <span id="process" onclick="detailsByProcInstId('${customHiTaskinst.procInstId}')">详情</span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script type="text/javascript">
    function detailsByProcInstId(procInstId){
        window.location="${ctx}/process/customProcess/list?procInstId="+procInstId +"&type=flowView";
    }
</script>
</body>
</html>