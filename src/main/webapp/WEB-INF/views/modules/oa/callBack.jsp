<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>回复邮件</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function js_method() {
            $("#mytable").append("")
        }

        $(document).ready(function () {
        });


        //存为草稿箱
        function deleteBy() {
            form1.action = '${ctx}/oa/mailInfo/saveDrafts';
            form1.submit();
        }

        function send(){
            var rr = document.getElementById("rr").value;
            var  re= document.getElementById("receiverIdName").value;
            var theme = document.getElementById("theme").value;

            if ((rr == "") && ( re == "")){
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 200px;height: 30px;text-align: center;position: absolute;bottom: 8px;left: -650px;line-height: 30px;'>请输入收件人地址</div>";
                return;
            }
            else{
                if (theme.length > 120) {
                    document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 190px;height: 20px;text-align: center;position: absolute;left: -650px;bottom: 10px'>主题长度不能超过120个中文字符</div>";
                    return;
                }else{
                    if(theme==""){
                        if (confirm('您的邮件没有填写主题，您确定要发送吗？')) {
                            if (rr != null && re!=""){
                                form1.action = '${ctx}/oa/mailInfo/send?in=1&out=1';
                                form1.submit();

                            }
                            if(rr=="" && re != ""){
                                form1.action = '${ctx}/oa/mailInfo/send?in=1&out=0';
                                form1.submit();
                            }
                            if(re=="" && rr != ""){
                                form1.action = '${ctx}/oa/mailInfo/send?in=0&out=1';
                                form1.submit();
                            }

                        }else{
                            return;
                        }
                    }else{
                        if (rr != "" && re!=""){
                            form1.action = '${ctx}/oa/mailInfo/send?in=1&out=1';
                            form1.submit();

                        }
                        if(rr=="" && re != ""){
                            form1.action = '${ctx}/oa/mailInfo/send?in=1&out=0';
                            form1.submit();
                        }
                        if(re=="" && rr != ""){
                            form1.action = '${ctx}/oa/mailInfo/send?in=0&out=1';
                            form1.submit();
                        }
                    }
                }
            }
        }

        //循环定时删除
        window.setInterval(show, 5000);
        function show() {
            document.getElementById("ss").innerHTML = "";
        }

        function deleteHref(u,c,i){
            document.getElementById("MC"+i).remove();
            document.getElementById("LJ"+i).remove();
            var MC = $("#FJMC").val();
            var LJ = $("#FJLJ").val();
            var FJLJ = LJ.replace(c+",","");
            var FJMC =MC.replace(u+",","");
            //如果点击删除按钮，那么需要将附件名称中的这个附件删除
            $("#FJMC").val(FJMC)
            $("#filesName").val(FJMC)
            $("#FJLJ").val(FJLJ)
            $("#fjlj").val(FJLJ)
        }


    </script>
</head>
<body>
<div style="background-color: #ffffff">
    <table class="table">
        <tr class="tr1">
            <td colspan="3" style="padding-left: 15px;">回复邮件</td>
        </tr>
        <tr>
            <td style="padding-left: 69px ; padding-top: 7px">
                <input type="submit" class="btn btn-success" value="发送" onclick="send()">　
                <input type="submit" class="btn btn-success" value="存草稿" onclick="deleteBy()">

            </td>
            <td> <div id="ss"  style="padding-left: 15px;position:relative;"></div></td>

        </tr>
    </table>
    <table style="width: 98.5%;" id="mytable" id="tb01">
        <form:form modelAttribute="mailInfo" action="" method="post" id="form1"
                   class="form-horizontal">

            <tr style="display: none">
                <td class="td1">外部收件人</td>
                <td class="td">
                    <div class="controls">
                        <form:input path="outSide" htmlEscape="true" type="text"
                                    style="width:800px;" id="rr"></form:input>
                    </div>
            </tr>
            <tr>
                <td class="td1" class="person">收件人</td>
                <td class="td">
                    <div class="controls" class="1111">
                        <sys:treeselect id="receiverId" name="receiverId"
                                        value="${mailInfo.senderId}" labelName="name"
                                        labelValue="${mailInfo.name}"
                                        title="用户" url="/sys/office/treeData?type=3"
                                        notAllowSelectParent="true" checked="true" cssStyle="width:800px"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
            </tr>
            <tr>
                <td class="td1">抄送人</td>
                <td class="td">
                        <sys:treeselect id="ccId" name="ccId"
                                        value="${mailInfo.ccId}" labelName="name" labelValue="${mailInfo.ccNames}"
                                        title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required"
                                        notAllowSelectParent="true" checked="true" cssStyle="width:800px"/>
                    <span class="help-inline"><font color="red">*</font> </span>
            </tr>
            <tr>
                <td class="td1">主题</td>
                <td class="td"><form:input path="theme" htmlEscape="false" type="text"
                                           style="width:800px" value="回复：${mailInfo.theme}" id="theme"></form:input></td>
            </tr>
            <tr>
                <td class="td1">附件</td>
                <td class="td" style="padding-bottom: 15px; height: 25px">
                    <c:if test="${not empty mailInfo.files}">
                        <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                        <sys:ckfinder input="files" type="files" uploadPath="/oa/mailInfo" selectMultiple="true"/>
                    </c:if>
                    <form:hidden id="fjlj" path="fjlj" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <form:hidden id="filesName" path="filesName" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <input type="hidden" id="FJMC"   name="FJMC" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <input type="hidden" id="FJLJ"  name="FJLJ" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <iframe id="iframe2" scrolling="no" src="/static/stream/infoFJ.html" style="border: 0px currentColor; width: 20%; height:65px;"></iframe>
                    <div id="FJ">
                    </div>
                </td>

            </tr>
            <tr style=" ">
                <td class="td1" valign="top">正文</td>
                <td style=" padding-left: 7px;">
                    <form:textarea id="content" htmlEscape="true" path="content" rows="3" maxlength="200"
                                   class="input-xxlarge"/>
                    <sys:ckeditor replace="content" uploadPath="/oa/mailInfo" height="300px"/>
                </td>
            </tr>
            <tr style=" ">
                <td colspan="2" style="padding-left: 69px ;">发件人: <span>
                        ${fns:getUser().name}
                </span></td>
            </tr>
            <tr>
                <td colspan="2" style="padding-left: 69px ; padding-top: 7px">
                    <input type="submit" class="btn btn-success" value="发送" onclick="send()">　
                    <input type="submit" class="btn btn-success" value="存草稿" onclick="deleteBy()">
                </td>
            </tr>
        </form:form>
    </table>
</div>
<script type="text/javascript">
    $("#fjlj").val("");
    $("#filesName").val("");
</script>
</body>
</html>