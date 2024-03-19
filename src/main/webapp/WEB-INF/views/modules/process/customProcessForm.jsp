<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>我发起的流程</title>
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

        .process{
            color: #2fa4e7;
            cursor: pointer;
        }
        .process:hover{
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
<label id="message" style="color:red; font-weight:bold;">${message}</label>
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
    <c:forEach items="${fns:findCustomProcess()}" var="customProcess">
        <tr>
            <td style="text-align: center">${customProcess.name}</td>
            <td style="text-align: center">${customProcess.deptId}</td>
            <td style="text-align: center">${customProcess.title}</td>
            <td style="text-align: center">${customProcess.recall}</td>
            <td style="text-align: center">
                <span class="process" onclick="detailsByProcInstId('${customProcess.procInstId}')">详情</span>
                <span class="process" onclick="deleteByProcInstId('${customProcess.procInstId}')">撤回</span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script type="text/javascript">
    function detailsByProcInstId(procInstId){
        window.location="${ctx}/process/customProcess/list?procInstId="+procInstId +"&type=flowView";
    }
    function deleteByProcInstId(procInstId){
        if(confirm('确认要撤回该自由流程数据吗？') == true){
            window.location="${ctx}/process/customProcess/deleteCustomProcess?procInstId="+procInstId ;
        }
    }
</script>
</body>
</html>