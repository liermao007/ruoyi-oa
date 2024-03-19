<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <%--<link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>--%>
	<title>日程管理</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <style>
        #contentTable tr th,#contentTable tr td{
            text-align: center;
        }
        ul{ list-style: none;}
        #s2id_login,#s2id_flag{
            display:none ;}
        #searchForm ul>li{width: 100%;
            float: left;}

        #flag{display: block !important;
            width: 55%;
            float: left;}
        #btnSeaSubmit {float: right;
            margin-top: 21px;
            margin-right: 7px;}
        #btnSubmit{ border-radius: 5px;}
        .update-done, .update-alert, .update-notice{ width: 100% !important; max-width: 100%;}
        .final_Zt{    display: inline-block !important;}
    </style>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
        <script type="text/javascript">
            $(function(){
                $('.widget-content').each(function(a){
                    $(this).on("click",function(){
                        var url =  $(this).find("a").eq(2).attr("href");
//              alert(url)
                        window.location.href=url;

                    });
                });
            });

        </script>
</head>
<body>
<!--tou-->
<div class="title_Infor">
    <a href="${ctx}/sys/menu/tree?parentId=23b5232adcad43b1a9fb89f02047a756" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>日程管理 </h3>
    <shiro:hasPermission name="oa:oaSchedule:edit">
            <a href="${ctx}/oa/oaSchedule/form" role="button" class="pull-right title_R" style="font-weight: bold;color:#FFFFFF;">添加</a>
    </shiro:hasPermission>
</div>
	<form:form id="searchForm" modelAttribute="oaSchedule" action="${ctx}/oa/oaSchedule/" method="post" class="breadcrumb form-search">
        <ul  style="margin-left: 0px;" class="ul-form">
            <li style="margin-bottom: 10px;">
                <label style="text-align: left;float: left;font-size: 12px; width: 25%">完成状态：</label>
                <form:select path="flag" class="input-medium" cssStyle="margin-right: 18px">
                    <form:option value="" label=""/>    <%--htmlEscape="false"--%>
                    <form:options items="${fns:getDictList('oa_schedule_status')}" itemLabel="label" itemValue="value" htmlEscape="true" />
                </form:select>
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
            </li>
            <li class="clearfix"></li>
        </ul>
	</form:form>
	<sys:message content="${message}"/>
    <div class="span6">
        <div class="widget-box wek_box">
            <c:forEach items="${page.list}" var="oaSchedule">
                <div class="widget-content nopadding updates collapse in bor_N">
                    <div class="new-update clearfix pad_T_no">
                        <div class="update-date">
                            <span class="update-day" style="font-size: 12px"><fmt:formatDate value="${oaSchedule.scheduleDate}" pattern="yyyy-MM-dd"/>
                            </span><a href="${ctx}/oa/oaSchedule/delete?id=${oaSchedule.id}" onclick="return confirmx('确认要删除该日程安排吗？', this.href)">删除</a>
                            <a href="${ctx}/oa/oaSchedule/complete?id=${oaSchedule.id}">完成</a>
                        </div>
                        <a href="${ctx}/oa/oaSchedule/form?id=${oaSchedule.id}"><h4>内容：${fns:abbr(oaSchedule.content,50)}</h4></a>
                        <div class="update-done">
                            <span>状态：<span class="final_Zt">${fns:getDictLabel(oaSchedule.flag, 'oa_schedule_status', '')}</span></span>
                            <span> 重要等级：${fns:getDictLabel(oaSchedule.importantLevel, 'oa_schedule', '')}&nbsp&nbsp/&nbsp&nbsp缓急程度：${fns:getDictLabel(oaSchedule.emergencyLevel, 'oa_schedule_import', '')}</span>

                        </div>
                        </a>
                    </div>
                </div>
                <c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
            </c:forEach>
        </div>
    </div>

</body>
</html>