<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻公告</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
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
    <a href="${ctx}/oa/oaNews/form" class="pull-right title_R" style="font-weight: bold;color:#FFFFFF;">发新闻</a>
</div>

<form:form id="searchForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/more" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>标题：</label>
            <form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
        </li>

        <li class="btns"><input id="btnSubmit" class="btn btn-primary " type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>


<div class="span6">
    <div class="widget-box wek_box">
        <c:forEach items="${page.list}" var="news" >
            <div class="widget-content nopadding updates collapse in bor_N">


                <div class="new-update clearfix">
                    <div class="new-update clearfix pad_T_no">
                        <a href="${ctx}/oa/oaNews/getMyAuditNews?id=${news.id}"><strong> ${fns:abbr(news.title,25)}</strong></a>
                        <%--<span class="fontS_14"> ${fns:abbr(news.content,30)}</span>--%>
                        </a>
                    </div>
                    <%--<i>
                        <c:if test="${news.photo==null||news.photo==''}" >
                            <img class="img-circle img-polaroid" style="width: 50px;height: 50px;"
                                 src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                        </c:if>
                        <c:if test="${news.photo!=null&&news.photo!=''}">
                            <img style="width: 50px;height: 50px;" class="img-circle img-polaroid" src="${news.photo}">
                        </c:if>
                    </i>--%>
                   <div class="new-update clearfix pad_T_no" style="padding: 0px 10px">
                          <span class="fontS_14">状  态：
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
                    <div class="update-date" style="margin:0px 0px 0 0; ">
                        <span class="update-day" style="font-size: 12px"><fmt:formatDate value="${news.createDate}"
                                                                                                              pattern="yyyy-MM-dd"/></span>新闻公告
                    </div>
                </div>

            </div>
            <c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
        </c:forEach></div>
</div>
<img class="touchRun" style="position:absolute; bottom:0px;width: 400px;height: 200px;display: none;z-index: 999" src="${ctxStatic}/oaApp/img/loadTouch.gif"/>
<%--<input id="more" type="button" value="加载更多"/>--%>

<script type="text/javascript">
    function goPage(newURL) {
        if (newURL != "") {
            if (newURL == "-") {
                resetMenu();
            }
            else {
                document.location.href = newURL;
            }
        }
    }
    function resetMenu() {
        document.gomenu.selector.selectedIndex = 2;
    }
</script>


<script src="${ctxStatic}/oaApp/js/oaScroll.js"></script>

<script type="text/javascript">
    $(function(){
        $('.widget-content').each(function(a){
            $(this).on("click",function(){
                var url =  $(this).find("a").eq(0).attr("href");
//              alert(url)
                window.location.href=url;

            });
        });
    });

</script>
<%--<script>

    $(function(){
        /*初始化*/
        var pageNo = 2;
        var pageSize = 20;
        var time =Math.ceil(parseInt(${listAmount})/pageSize);
        var p=0,t=0;
        /*监听加载更多*/

        $(window).scroll(function(){
            p = $(this).scrollTop();
            //下滚
            if(t<=p){
                var url='${ctx}/oa/oaNews/getNewsMore?auditFlag=1&pageNo='+pageNo+'&pageSize='+pageSize;
                touchScroll(pageNo,pageSize,time,url,".wek_box");
                pageNo++;
                t=p;
            }
            else{
                return;
            }

        });
    });
</script>--%>
</body>
</html>
