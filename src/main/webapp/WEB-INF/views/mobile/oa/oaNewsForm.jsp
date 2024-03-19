<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>

    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>

    <style>
        #jbox-content{
            width: 300px;
        }

        select, input[type="file"] {
            width: 80%;
            height: 30px;
            line-height: 24px;
        }
        #cke_top_content{
            display: none;
        }
       #cke_contents_content{
           height: 200px !important;
       }
        #btnMenu{display:none}
    </style>

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
<div class="title_Infor">
    <a onclick="location.href=document.referrer;" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>
    <h3>新闻编辑</h3>
</div>

<form:form id="inputForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="auditFlag"/>
    <sys:message content="${message}"/>
    <%--<c:if test="${oaNews.auditFlag eq '1'}">
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls" style="color: red;font-size: 20px;">
                已审核，不能再次修改。
            </div>
        </div>
    </c:if>--%>
    <div class="control-group" style="margin: 20px 0 10px 0;">
        <label class="control-label">标题：</label>
        <div class="controls">
            <form:input path="title" htmlEscape="false" cssStyle="width:80%;height: 30px" maxlength="200" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">新闻审核官：</label>
        <div class="controls">
            <form:select path="auditMan" >
                <form:option value="" label=""/>
                <form:options items="${fno:getAuditManAllList()}" itemLabel="auditMan" itemValue="auditId" htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> 选择审核人代表此新闻需审核，不选择表示此新闻不需要审核</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" style=" margin-top: 5px;">上传附件：</label>
        <div class="controls">
            <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
            <form:hidden id="fjlj" path="fjlj" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
            <form:hidden id="filesName" path="filesName" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
            <input type="hidden" id="FJMC"  name="FJMC" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
            <input type="hidden" id="FJLJ"  name="FJLJ" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <%--<sys:ckfinder input="files" type="files" uploadPath="/oa/oaNews" selectMultiple="true"/>--%>
            <iframe id="iframe2" scrolling="no" src="/static/stream/infoFJ.html" style="border: 0px currentColor; width: 80%; height:30px;"></iframe>

        </div>
    </div>
    <c:if test="${device.system!='android'}">
        <c:if test="${oaNews.files != ''}">
            <div class="control-group">
                <label class="control-label">附件：</label>
                <a id="old_fj" href="" target="_blank">${fns:urlDecode(oaNews.fileName)}</a>
                <div class="controls">
                    <div id="FJ"></div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty mailInfo.fjlj}">
            <div class="control-group">
                <label class="control-label">附件：</label>
                <div id="hhh"></div>
            </div>
        </c:if>
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
    <div class="control-group">
        <label class="control-label">是否置顶:</label>
        <div class="controls">
            <form:radiobuttons path="isTopic"  items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" value="${isTopic}"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">接收人：</label>
        <div class="controls">
            <sys:treeselect id="oaNewsRecord" name="oaNewsRecordIds" value="${oaNews.oaNewsRecordIds}" labelName="oaNewsRecordNames" labelValue="${oaNews.oaNewsRecordNames}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="required" cssStyle="width:145px;height: 30px;" notAllowSelectParent="true"  checked="true"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">新闻内容</label>
    </div>
    <div class="control-group" style="width: 100%" >
        <div class="controls" style="width: 100%" >
            <form:textarea id="content" htmlEscape="true" style="width: 100%" path="content" rows="3" maxlength="200" class="input-xxlarge"/>
            <sys:ckeditor replace="content" uploadPath="/oa/news"/>


        </div>
    </div>

    <div align="center">
        <c:if test="${oaNews.auditFlag ne '1'|| oaNews.createBy eq fns:getUser().id || oaNews.auditMan eq fns:getUser().id || fns:getUser().id eq '1'}">
            <shiro:hasPermission name="oa:oaNews:edit"><input id="btnSubmit" class="btn btn-primary" style="width: 100%" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        </c:if>
        <input id="btnCancel" class="btn"  style="width:100%" type="button" value="返 回" onclick="location.href=document.referrer;"/>
    </div>
</form:form>
<script src="${ctxStatic}/uploadify/ajaxfileupload.js" type="text/javascript"></script>
<style>
    .form-horizontal .controls{
        margin-left: 0px;
    }
    .form-horizontal .control-label {
        width: 80px;
    }
    #jbox-content{
        width: 300px;
    }
</style>
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
                    $("#btnSubmit").on("click",function(){

                        if(typeof ($(this).attr("disabled"))=="undefined"){
                            $(this).attr("disabled","#dbdbdb")
                        }

                    })


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
<script type="text/javascript">
    if (history.length == 1){
        history.length=0;
    }

</script>
<script>

    function ajaxFileUpload() {
        $.ajaxFileUpload
        ({
            url: '${ctx}/oa/oaNews/saveContractFile',
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'file_upload', //文件上传域的ID
            dataType: 'text', //返回值类型 一般设置为json
            success: function (data) //服务器成功响应处理函数
            {
                console.log(data);
                var reg = /<pre.+?>(.+)<\/pre>/g;
                var result = data.match(reg);
                path = RegExp.$1;
                console.log(path);
                var fileName=path.substring(path.lastIndexOf("/")+1);
                $("#files").val(path);
                $("#old_fj").attr("href",path);
                $("#old_fj").html(fileName);
                alert("文件上传成功！");
            },
            error: function (data,xml, status, e)//服务器响应失败处理函数
            {
                console.log("文件上传失败，请联系管理员！");
            }
        })
        return false;
    }

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