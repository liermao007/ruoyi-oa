<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻审核官管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    //保存审核官姓名
                    $('#auditMan').val($('#auditIdName').val())
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
</head>
<body>
<form id="inputForm"  action="${ctx}/act/model/copy?id=${id}" method="post" class="form-horizontal" style="padding-top:10px;">

    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">流程标识(英文)：</label>
        <div class="controls">
            <input name="actEng" htmlEscape="false" maxlength="80" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">流程名称：</label>
        <div class="controls">
            <input name="actName" htmlEscape="false" maxlength="80" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div style="text-align: center;">
      <input align="middle" id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        <input align="middle" id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>