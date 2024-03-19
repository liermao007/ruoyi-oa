<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>

    <style>
        .MsoNormal b span{font-size:26px !important;line-height: 28px !important;}
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
        //微信附件查看
//        window.onload = function () {
//            var u = navigator.userAgent;
//            if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
//               // alert("安卓手机");
//                function is_weixin(){
//                    var ua = navigator.userAgent.toLowerCase();
//                    if(ua.match(/MicroMessenger/i)=="micromessenger") {
//                        //alert("是");
//                        $(".zp").on("click",function(){
////                            var ahref = window.location.href;
//                           alert("目前不支持安卓在线查看附件，请长按点击使用浏览器打开。")
////                           window.open("ahref");
//                        });
//                    }
//                }
//            }
//
//        }
	</script>
</head>
<body>

    <!--Header-part-->
    <%--<div id="header">--%>
        <%--<h1>--%>
            <!--<a href="dashboard.html">OA办公系统</a>-->
        <%--</h1>--%>
    <%--</div>--%>
    <div class="title_Infor">
        <a onclick="location.href=document.referrer;" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
        <h3 >新闻公告</h3>
    </div>

     <form:form id="inputForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/save" method="post" class="form-horizontal" cssStyle="height:100%;overflow: auto; -webkit-overflow-scrolling: touch;">
         <div style="width:100%; padding-left: 7%;padding-right: 7%;margin-bottom: 180px;">
         <h4 style="color: rgba(20, 20, 20, 0.98);text-align: left;margin-top: 10px;">${oaNews.title}</h4>
         <HR color=#987cb9 SIZE=3>
         <div>${oaNews.content}</div><br/><br/>
         <c:if test="${device.system!='android'}">
             <c:if test="${not empty oaNews.files}">
                 <br/><br/><span><b>附 件: </b></span><br/>
                 <div class="zp">
                     <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                     <sys:ckfinder input="files" type="files" uploadPath="/oa/oaNews" readonly="true"/>
                 </div>
             </c:if>

            <c:if test="${not empty oaNews.fjlj}">
                 <br/><br/><span><b>附 件: </b></span><br/>
                 <div id="hhh">
                 </div>
             </c:if>
         </c:if>
         <c:if test="${device.system=='android'&&device.browser=='micromessenger'}">
             <c:if test="${not empty oaNews.files}">
                 <br/><br/><span><b>附 件: </b></span><br/>
                 <div class="zp">
                     <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                     <sys:ckfinder input="files" type="files" uploadPath="/oa/oaNews" readonly="true"/>
                 </div>
             </c:if>

             <c:if test="${not empty oaNews.fjlj}">
                 <br/><br/><span><b>附 件: </b></span><br/>
                 <div id="hhh">
                 </div>
             </c:if>
         </c:if>
         <HR color=#987cb9 SIZE=3>
         <div style="padding-left: 40%">
             <span><b>发布人: </b>${oaNews.createManName}</span><br/>
             <c:if test="${not empty oaNews.auditManName}">
             <span><b>审核人: </b>${oaNews.auditManName}</span><br/>
             </c:if>
             <span><b>时　间: </b><fmt:formatDate value="${oaNews.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
         </div>
         <HR color=#987cb9 SIZE=3>
         </div>

     </form:form>
    <script>
        //多附件上传，非申请页面附件路径和名称不为空时，切割文件名称和文件路径，显示
        var  fjlj="${oaNews.fjlj}"
        var  fjmc= "${oaNews.filesName}"
        if(fjlj != "" && fjmc != ""){
            var mc=fjmc.split(",")
            var lj=fjlj.split(",")
            $("#hhh").html("");
            for(var i=0;i<mc.length-1;i++){
                $("#hhh").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='" + lj[i] + "'>"+mc[i]+"</a><br>");
            }
        }
    </script>

</body>
</html>