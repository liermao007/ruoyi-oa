<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>转发邮件</title>
    <meta name="decorator" content="default"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <script src="${ctxStatic}/uploadify/ajaxfileupload.js" type="text/javascript"></script>
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
//            document.getElementById("ss").innerHTML = "";
        }
    </script>
    <script type="text/javascript">
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
    <style>
        #btnMenu{
            display: none;}
        .breadcrumb{ padding:25px 15px}
        .title_Infor{
            width: 100%;
            float: left;
            background-color: #33aafb;
            border-bottom: none;
            margin-top:0px !important;
            line-height: 30px;
            height: 40px;
        }
        .title_Infor>a{
            color: #fff;font-size: 14px;}
        .title_Infor img{
            height:18px;
        }
        .title_Infor h3{
            width: calc(100% - 100px);
            color: #fff;
            font-size: 18px;
            text-align: center;
            margin: 0;
            float: left;
        }
        #searchForm{
            float: left;}
        .td,.td1{ width: 100%;float: left; text-align: left}
        .td>input,.td1>input{ width: 87%;}
        body{ overflow-x: hidden   }
        .input-xxlarge{
            width:100%;}
        .input-append{ width: 75%}
        tbody{
            padding: 0 15px;
            float: left;
            width: 100%;
        }
        tr{ width: 100%;
            float: left;}
        a.jbox-close {
            right: 40px!important;
        }
        #cke_top_content{
            display: none;
        }
        #cke_contents_content{
            height: 500px !important;
            width: 100% !important;
        }
    </style>
    <style>
        #jbox-content{
            width: 300px;
        }
        table,table>tr,table>tr>td{ width: 100% !important;
            float: left;}
        #ccIdName,#receiverIdName{
            width:92.5%;
        }
       table{
            margin-left: 0px !important;
           width: 100%;
        }
    </style>
</head>
<body>
<div class="title_Infor" style="margin-top: 0px;margin-bottom: 0px">
    <a href="${ctx}/oa/mailInfo/listBySend?state=INBOX" class="pull-left" style="margin: 4px"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>转发邮件</h3>
</div>

<div style="background-color: #ffffff; float: left; margin-bottom: 60px; width: 100%; margin-top: 20px;">

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
                    <div class="controls" class="1111" style=" overflow-y: scroll;">
                        <sys:treeselect id="receiverId" name="receiverId"
                                        value="${mailInfo.senderId}" labelName="name"
                                        labelValue="${mailInfo.name}" cssClass="required"
                                        title="用户" url="/sys/office/treeData?type=3"
                                        notAllowSelectParent="true" checked="true" cssStyle=""/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
            </tr>

            <tr>
                <td class="td1">抄送人</td>
                <td class="td">
                        <sys:treeselect id="ccId" name="ccId"
                                        value="${mailInfo.ccId}" labelName="name" labelValue="${mailInfo.ccNames}"
                                        title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge"
                                        notAllowSelectParent="true" checked="true" cssStyle=""/>
                    <span class="help-inline"><font color="red"></font> </span>
            </tr>
            <tr>
                <td class="td1">主题</td>
                <td class="td"><form:input path="theme" htmlEscape="false" type="text"
                                           style="width:84%" value="转发：${mailInfo.theme}" id="theme"></form:input></td>
            </tr>

            <tr>
                <td class="td1">上传附件</td>
                <td class="td" >
                    <form:hidden id="fjlj" path="fjlj" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <form:hidden id="filesName" path="filesName" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <input type="hidden" id="FJMC"  name="FJMC" value="${mailInfo.filesName}" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <input type="hidden" id="FJLJ"  name="FJLJ" value="${mailInfo.fjlj}" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                        <%--<sys:ckfinder input="files" type="files" uploadPath="/oa/oaNews" selectMultiple="true"/>--%>
                    <iframe id="iframe2" scrolling="no" src="/static/stream/infoFJ.html" style="border: 0px currentColor; width: 80%; height:65px;"></iframe>
                </td>
                <td class="td1">附件:</td>
                <td class="td">
                    <div id="FJ" style="margin-left: 10px;">
                    </div>
                </td>
            </tr>
            <tr style=" ">
                <td class="td1" valign="top">正文</td>
                <td style="padding-left: 7px;float: left;width: 87%;">
                    <form:textarea id="content" htmlEscape="true" path="content" rows="3" maxlength="200"
                                   class="input-xxlarge"/>
                    <sys:ckeditor replace="content" uploadPath="/oa/mailInfo" height="200px"/>
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
    var MC = $("#filesName").val();
    var LJ = $("#fjlj").val();
    var arrayMC = MC.split(",");
    var arrayLj = LJ.split(",");
    var fileNamedd = "";
    for(var i=0;i<arrayMC.length-1;i++){
        fileNamedd =  fileNamedd + '<a style="margin-left: 10px" id="MC'+i+'" href="'+arrayLj[i]+'">'+arrayMC[i]+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="LJ'+i+'" onclick="deleteHref(\''+arrayMC[i]+'\',\''+arrayLj[i]+'\','+i+')">删除</a><br>'
    }
    document.getElementById("FJ").innerHTML = fileNamedd;
</script>

</body>
</html>