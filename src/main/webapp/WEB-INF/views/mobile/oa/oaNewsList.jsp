<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/mail.css" />
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>

    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>
    <style>
        .update-done{
            overflow: hidden;
            text-overflow:ellipsis;
            white-space: nowrap;
        }
        ul{list-style: none;}
        #auditFlag{
            display: block !important; width: 100%}
        #s2id_auditFlag{
            display: none;}
        label,input{ width: 100% !important;
            float: left; }
        input{
            height: 30px !important;}
        #btnSubmit{ margin-top: 20px;}
    </style>
</head>
<body style="margin-top:0;">

<!--Header-part-->
<%--<div id="header">--%>
<%--<h1>--%>
<!--<a href="dashboard.html">OA办公系统</a>-->
<%--</h1>--%>
<%--</div>--%>
<div class="title_Infor" >
    <a href="${ctx}/sys/menu/tree?parentId=15028ed225a548bf931d27e0ca15f669" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>

    <h3 >新闻公告</h3>
    <a href="${ctx}/oa/oaNews/form" class="pull-right title_R" style="font-weight: bold;color:#FFFFFF;">发新闻</a>
</div>
<!--close-Header-part-->
<!-- 消息通知-->
<!--end-main-container-part-->

<form:form id="searchForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>标题：</label>
            <form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
        </li>
        <li><label style="margin-top: 10px;">审核状态：</label>
            <form:select path="auditFlag" class="ineput-medium" style="display:block">
                <form:option value="" label=""/>
                <form:option value="0" label="待审核"/>
                <form:option value="1" label="已发布"/>
                <form:option value="2" label="拒绝发布"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary " type="submit" value="查询"/></li>
        <%--<shiro:hasPermission name="oa:oaNews:edit">--%>
            <%--<li class="btns"><a href="${ctx}/oa/oaNews/form" role="button" class="btn btn-primary">添加</a></li>--%>
        <%--</shiro:hasPermission>--%>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>

<div class="span6">
    <div class="widget-box wek_box">
        <c:forEach items="${page.list}" var="news" begin="0" end="1000">
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix">
                    <div class="update-done">
                        <a title="" href="${ctx}/oa/oaNews/getMyAuditNews?id=${news.id}">
                            <c:if test="${news.auditFlag eq '0'}">
                                <a href="${ctx}/oa/oaNews/getMyAuditNews?id=${news.id}"><h4> ${news.title}</h4></a>
                            </c:if>
                            <c:if test="${news.auditFlag eq '1'}">
                                <a href="${ctx}/oa/oaNews/getMyAuditNews?id=${news.id}"><h4> ${news.title}</h4></a>
                            </c:if>
                            <c:if test="${news.auditFlag eq '2'}">
                                <a href="${ctx}/oa/oaNews/getMyAuditNews?id=${news.id}"><h4> ${news.title}</h4></a>
                            </c:if>
                        </a>
                    </div>
                    <div class="update-date" style="width: 35%">
                        <span class="update-day" style="font-size: 12px">
                            <fmt:formatDate value="${news.createDate}" pattern="yyyy-MM-dd"/>
                        </span>
                        <c:if test="${news.auditFlag eq '0'}">
                            <shiro:hasPermission name="oa:oaNews:edit">
                                <c:if test="${news.auditFlag eq '0' || news.auditFlag eq '2'}">
                                    <c:if test="${news.isTopic eq '0'}">
                                        <a href="${ctx}/oa/oaNews/toUp?type=1&id=${news.id}">置顶</a>
                                    </c:if>
                                    <c:if test="${news.isTopic eq '1'}">
                                        <a href="${ctx}/oa/oaNews/toUp?type=0&id=${news.id}">取消置顶</a>
                                    </c:if>
                                    <a href="${ctx}/oa/oaNews/delete?id=${news.id}" onclick="return confirmx('确认要删除该新闻公告吗？', this.href)">删除</a>
                                </c:if>
                            </shiro:hasPermission>
                        </c:if>
                    </div>

                    </div>
                </div>
                <div class="new-update clearfix pad_T_no">
                        <span>状态：
                            <c:if test="${news.auditFlag eq '1'}">
                                已发布
                            </c:if>
                            <c:if test="${news.auditFlag eq '0'}">
                                待审核
                            </c:if>
                            <c:if test="${news.auditFlag eq '2'}">
                                拒绝发布
                            </c:if>
                      </span>

                    </div>

            <c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
        </c:forEach>
    </div>
<img class="touchRun" style="position:absolute; bottom:0px;width: 400px;height: 200px;display: none;z-index: 999" src="${ctxStatic}/oaApp/img/loadTouch.gif"/>

<%--<script>--%>
    <%--$(function(){--%>
        <%--$(window).scroll(function(){--%>
            <%--tongyongScroll($(this));--%>
        <%--});--%>
    <%--})--%>


    <script type="text/javascript">
        $(function(){
            $('.widget-content').each(function(a){
                $(this).on("click",function(){
                    var url =  $(".update-done").find("a").eq(a).attr("href");

                    window.location.href=url;

                });
            });
        });

    </script>

<%--<script type="text/javascript">--%>
    <%--function goPage(newURL) {--%>
        <%--if (newURL != "") {--%>
            <%--if (newURL == "-") {--%>
                <%--resetMenu();--%>
            <%--}--%>
            <%--else {--%>
                <%--document.location.href = newURL;--%>
            <%--}--%>
        <%--}--%>
    <%--}--%>
    <%--function resetMenu() {--%>
        <%--document.gomenu.selector.selectedIndex = 2;--%>
    <%--}--%>
<%--</script>--%>
<!--发布按钮-->

<script src="${ctxStatic}/oaApp/js/oaScroll.js"></script>
<%--<script>--%>
    <%--$(function(){--%>
        <%--/*初始化*/--%>
        <%--var pageNo = 2;--%>
        <%--var pageSize = 20;--%>
        <%--var time =Math.ceil(parseInt(${listAmount})/pageSize);--%>
        <%--var p=0,t=0;--%>

        <%--/*监听加载更多*/--%>
        <%--$(window).scroll(function(){--%>
            <%--p = $(this).scrollTop();--%>
            <%--//下滚--%>
            <%--if(t<=p){--%>
            <%--var url='${ctx}/oa/oaNews/getNewsMore?pageNo='+pageNo+'&pageSize='+pageSize;--%>
            <%--touchScroll(pageNo,pageSize,time,url);--%>
            <%--pageNo++;--%>
                <%--t=p;--%>
            <%--}--%>
            <%--else{--%>
                <%--return;--%>
            <%--}--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>

</body>

</html>
