<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="mobile" value="${pageContext.request.contextPath}/static"/>
<html>
<head>

    <title>邮件详情</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/mail.css" />
    <script type="text/javascript">
        $(document).ready(function () {
            var system='${device.system}';
            var browser='${device.browser}';
            if(system=='android'&&browser== 'micromessenger'){
                var urls;
                if(${mailInfo.fjlj!=''}){
                    urls='${mailInfo.fjlj}'.split(",");
                }else if(${mailInfo.files!=''}){
                    urls='${mailInfo.files}'.split("|");
                }
                for(var i=0;i<urls.length;i++){
                    if(urls[i]!=''){
                        var li = "<li><a href=\"${ctx}/oa/mailInfo/getFile?name="+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a></li>";
                        $("#Preview").append(li);
                    }
                }
            }
            $("#inputForm").validate({
                submitHandler: function (form) {
                    if (CKEDITOR.instances.content.getData() == "") {
                        top.$.jBox.tip('请填写新闻内容', 'warning');
                    } else {
                        form.submit();
                    }
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });


        //彻底删除
        function deleteBy(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (confirm('彻底删除后邮件将无法恢复，您确定要删除吗？')) {
                window.location.href = '${ctx}/oa/mailInfo/thoroughDelete?ids=' + chestr + '&state=' + state;
            }
        }

        //已读邮件
        function read(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (chestr) {
                window.location.href = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=1&state=' + state;
            }
        }

        //未读邮件
        function unread(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (chestr) {
                window.location.href = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=0&state=' + state;
            }
        }

        //移动到已发送
        function send(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (state == "SENT") {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>不能移动到相同的目录</div>";
                return;
            }
            window.location.href = '${ctx}/oa/mailInfo/remove?ids=' + chestr + '&state=' + state + '&state1=SENT'
        }
        //移动到收件箱
        function inbox(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (state == "INBOX") {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>不能移动到相同的目录</div>";
                return;
            }
            window.location.href = '${ctx}/oa/mailInfo/remove?ids=' + chestr + '&state=' + state + '&state1=INBOX'
        }


        //返回
        function hostory(state) {
            var flag = document.getElementById("flag").value;
            if (flag==1) {
                window.location.href = '${ctx}/oa/mailInfo/findOut?state=' + state;
            } else {
                window.location.href = '${ctx}/oa/mailInfo/listBySend?state=' + state;
            }


        }

        //回复
        function callback() {

            window.location.href = '${ctx}/oa/mailInfo/callback?id= ${mailInfo.id}';

        }

        //转发
        function forward() {

            window.location.href = '${ctx}/oa/mailInfo/forward?id= ${mailInfo.id}';

        }

        //循环定时删除
        window.setInterval(show, 5000);
        function show() {
            document.getElementById("ss").innerHTML = "";
        }

    </script>
    <style>
        br{
            display: none;;
        }
        table{
            margin-left: 0 !important;
        }
        td{
            padding: 7px;

        }
        td>input{
            border-radius: 5px;
            padding: 5px 10px;
        }
    </style>
</head>
<body>
<div class="title_Infor">
    <a onclick="hostory('${mailInfo.state}')" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>邮件详情</h3>

</div>



<div style="background-color: #ffffff; overflow-x: scroll">
    <form:form modelAttribute="mailInfo" action="" method="post" id="form1">
        <input type="hidden" id="flag" value="${mailInfo.flag}">

        <table style="width: 100%">
            <tr style="background-color: #C1D9F3;height: 35px;">
                <td colspan="2" style="padding-left: 5px">
                    <input style=" border-radius: 5px;padding:5px 10px;" type="button" value="移动到收信箱" class="btn btn-success" onclick="inbox('${mailInfo.state}')">
                    <input type="button" style=" border-radius: 5px;padding:5px 10px;" value="转发" class="btn btn-primary" onclick="forward()">
                    <input type="button" style=" border-radius: 5px;padding:5px 10px;" value="回复" class="btn-info" onclick="callback()">
                    <input type="button" value="移动到已发送" class="btn-info" onclick="send('${mailInfo.state}')">
                    <input type="button" value="彻底删除" class="btn-danger" onclick="deleteBy('${mailInfo.state}')">
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center" style="display: none">
                    <div id="ss"></div>
                    <input type="hidden" value="${mailInfo.id}" id="mailId">
                </td>
            </tr>
            <tr class="findTr">
                <td colspan="2" style="font-size:18px;font-weight:bold;padding-top: 18px; padding-left: 0px;">　　
                    <c:if test="${not empty mailInfo.theme}">
                        ${mailInfo.theme}
                    </c:if>
                    <c:if test="${empty mailInfo.theme}">
                        <font style="font-weight: bold" color="black">(无主题)</font>
                    </c:if>
                </td>
            </tr>
            <tr class="findTr">
                <td  style="font-size: 16px; width: 80px">发件人：</td><br/>
                <td style="font-size: 16px;">${mailInfo.name}</td>
            </tr>
            <tr class="findTr">
                <td  style="font-size: 16px">时　间：</td><br/>
                <td  style="font-size: 16px"><fmt:formatDate value="${mailInfo.time}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
            </tr>
            <c:if test="${not empty mailInfo.ccNames}">
                <tr class="findTr">
                    <td  style="font-size: 16px">抄送人：</td><br/>
                    <td  style="font-size: 16px">${mailInfo.ccNames}</td>
                </tr>
            </c:if>
            <tr style="height: 25px; background-color: #EFF5FB;color: #ABAAAD;border-bottom: 1px solid #C1D9F3">
                <td  style="font-size: 16px">收件人：</td><br/>
                <c:if test="${not empty mailInfo.receiverNames}">
                    <td  style="font-size: 16px">${mailInfo.receiverNames}</td>
                </c:if>
            </tr>


            <tr style="height: 160px;">
                <td colspan="2" style="padding-left: 25px;font-size: 16px" valign="top" >　　
                    <div>${mailInfo.content}</div>
                </td>
            </tr>
            <tr class="findAd">
                <td colspan="2" style="font-size: 16px">
                    <img src="${ctxStatic}/tree/css/mailCss/img/mail080.png">&nbsp;附件:
                </td>
            </tr>
            <c:if test="${device.system!='android'}">
                <tr class="findAdjunct">
                    <td colspan="2" class="td" style="padding-bottom: 15px; height: 25px; font-size: 16px">
                        <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                        <sys:ckfinder input="files" type="files" uploadPath="/oa/mailInfo" selectMultiple="true"
                                      readonly="true"/>
                        <c:if test="${not empty mailInfo.fjlj}">
                            <div id="hhh">
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:if>
            <c:if test="${device.system=='android'&&device.browser=='micromessenger'}">
                <c:if test="${mailInfo.files!=''||not empty mailInfo.fjlj}">
                    <tr class="findAdjunct">
                        <td colspan="2" class="td" style="padding-bottom: 15px; height: 25px">
                            <ul id="Preview"></ul>
                        </td>
                    </tr>
                </c:if>
            </c:if>
            <tr style="height: 8px">
                <td></td>
            </tr>
        </table>
    </form:form>
</div>
<script>
    //多附件上传，非申请页面附件路径和名称不为空时，切割文件名称和文件路径，显示
    var  fjlj="${mailInfo.fjlj}"
    var  fjmc= "${mailInfo.filesName}"
    if(fjlj != "" && fjmc != ""){
        var mc=fjmc.split(",")
        var lj=fjlj.split(",")
        $("#hhh").html("");
        for(var i=0;i<mc.length-1;i++){
            $("#hhh").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='" + lj[i] + "'>"+mc[i]+"</a><br>");
        }
    }
</script>
</body>
</html>