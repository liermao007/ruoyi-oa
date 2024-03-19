<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻公告管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    if (CKEDITOR.instances.content.getData()==""){
                        top.$.jBox.tip('请填写新闻内容','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
</head>
<body>

<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
    <div style="width:86%; padding-left: 7%;padding-right: 7%">
        <h1 style="color: red;text-align: center;margin-top: 10px;">${oaNotify.title}</h1>
        <%--<HR color=#987cb9 SIZE=3>--%>
        <br>
        <br>
        <span>${oaNotify.content}</span>
        <c:if test="${not empty oaNotify.files}">
            <br>
            <span><b>附 件: </b></span><br/>
            <div >
                <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <sys:ckfinder input="files" type="files" uploadPath="/oa/notify" readonly="true"/>
            </div>
        </c:if>
        <%--<HR color=#987cb9 SIZE=3>--%>
        <br>
        <br>
        <div style="padding-left: 70%">
            <span><b>发布人: </b>${oaNotify.createManName}</span><br/>
            <span><b>时　间: </b><fmt:formatDate value="${oaNotify.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
        </div>
        <%--<HR color=#987cb9 SIZE=3>--%>
    </div>
    <div class="form-actions" style="text-align: center">
        <input id="btnCancel" class="btn" type="button" value="返回首页" onclick="history.go(-1)"/>
    </div>

</form:form>


</body>
</html>