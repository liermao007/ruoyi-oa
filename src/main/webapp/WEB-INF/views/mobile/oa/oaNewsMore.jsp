<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻公告管理</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
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
        $("#btnSubmit").on("click",function(){
           if(("text").val == ""){
               alert("请输入查询条件");
               return;
           }else{

           }

        })

    </script>

    <style>
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
<body>
<div class="title_Infor">
    <a href="${ctx}/sys/menu/tree?parentId=15028ed225a548bf931d27e0ca15f669" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3 >我的新闻</h3>
</div>

<form:form id="searchForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/more" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>标题：</label>
            <form:input id = "text" path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
        </li>

        <li class="btns"><input id="btnSubmit" class="btn btn-primary " type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>


<sys:message content="${message}"/>

<div class="span6">
    <div class="widget-box wek_box">
        <c:forEach items="${page.list}" var="news" >
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix"><i>
                    <c:if test="${news.photo==''}" >
                        <img class="img-circle img-polaroid"
                             src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                    </c:if>
                    <c:if test="${news.photo!=''}">
                        <img class="img-circle img-polaroid" src="${news.photo}">
                    </c:if> </i>
                    <div class="update-done"><a title="" href="#"><strong class="fontS_16">${news.createManName}</strong></a>
                          <span class="fontS_14">状态：
                           <c:if test="${news.auditFlag eq '1'}">
                               已发布
                           </c:if>
                            <c:if test="${news.auditFlag eq '0'}">
                                待审核
                            </c:if>
                            <c:if test="${news.auditFlag eq '2'}">
                                拒绝发布
                            </c:if>
                    </span></div>
                    <div class="update-date"><span class="update-day" style="font-size: 12px"><fmt:formatDate value="${news.createDate}"
                                                                                                              pattern="yyyy-MM-dd"/></span>新闻公告</div>
                </div>
                <div class="new-update clearfix pad_T_no">
                    <a href="${ctx}/oa/oaNews/getMyAuditNews?id=${news.id}"><h4> ${fns:abbr(news.title,25)}</h4></a>
                    <span class="fontS_14"> ${fns:abbr(news.content,30)}</span>
                    </a>
                </div>
            </div>
            <c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
        </c:forEach></div>
</div>


</body>
</html>