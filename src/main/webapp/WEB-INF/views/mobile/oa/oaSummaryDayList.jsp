<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>每日总结</title>
	<meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function pickedFunc()
        {
            $("#searchForm").submit();
        }
	</script>
    <style>
        body{
            overflow-x: hidden;
        }
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
            height: 37px;
        }
        .title_Infor>a{
            color: #fff;}
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
        .form-horizontal .controls{
            margin-left: 0px !important;
        }
    </style>
</head>
<body>
<div class="title_Infor" style="margin-top: 0px;margin-bottom: 0px">
    <a href="${ctx}/sys/menu/tree?parentId=23b5232adcad43b1a9fb89f02047a756" class="pull-left" style="margin: 4px"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>每日总结</h3>
</div>
	<form:form id="searchForm" modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/" method="post" class="breadcrumb form-search"
            style="width: 100%;
    padding: 0 0 15px 0px;">
		<ul class="ul-form" style="position: relative; width: 100%;float: left;">
            <li style="width: 100%; float: left; height: auto; line-height: 20px">　　　　
                　<label style="text-align: left;width: 100%; float: left; margin-bottom: 15px">总结日期：</label>
                　<input style="float: left;width: 92%;
    margin-left: 10px;" name="sumDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${oaSummaryDay.sumDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:pickedFunc});"/>
            </li>
			<li class="clearfix"></li>
		</ul>
</form:form>
	<sys:message content="${message}"/>

    <%--<form:hidden path="loginId" value=" ${fns:getUser()}"/>--%>
    <form:form id="inputForm" modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/save" method="post" class="form-horizontal">
        <div class="control-group" >

            <div class="controls">
                <input name="sumDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${oaSummaryDay.sumDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </div>
        </div>
        <div class="control-group" >
            <label style="text-align: left; width: auto" class="control-label">任务完成：</label><br/>
            <div class="controls" style="float: left; width: 100%; ">
                <table style="width:100%;">
                    <c:forEach items="${oaSummaryDay.oaScheduleList}" var="oaSchedule">
                        <tr>
                            <td>
                                    ${fns:abbr(oaSchedule.content,50)}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <form:hidden path="id" value="${oaSummaryDay.id}"/>
        <sys:message content="${message}"/>
        <div class="control-group">
            <label style="text-align: left;" class="control-label">工作总结：</label>
            <div class="controls" style="float:left;margin-left: 0px; width: 101%">
                <form:textarea path="content" id="textarea" style="width: 95%;
    margin-top: 10px;"  htmlEscape="false" rows="4" maxlength="2000" cssClass="required"  value="${oaSummaryDay.content}"/>
            </div>
        </div>

        <div class="control-group">
            <label style="text-align: left;" class="control-label">评阅意见：</label>
            <div class="controls" style="float:left;margin-left: 0px; width: 101%">
                <form:textarea path="evaluate" style="    width: 95%;
    margin-top: 10px;"  htmlEscape="false" rows="3" maxlength="2000" class="input-xxlarge " value="${oaSummaryDay.evaluate}" readonly="true"/>
            </div>
        </div>
        <div class="form-actions" style="margin: 0px; padding: 0; margin-bottom: 60px; padding-left: 0px; border: 0px; background: none">
            <shiro:hasPermission name="oa:oaSummaryDay:edit"><input id="btnSubmit" class="btn btn-primary" style="width: 100%;border-radius: 5px; height: 36px;" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        </div>
    </form:form>
<script type="text/javascript">
    $("#btnSubmit").click(function(){
        var text =$("#textarea").val();
        if(text == ""){
            alert("请输入工作总结");
            return false;
        }
    })



</script>

</body>
</html>