<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta  name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/mail.css" />
</head>

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
<body style="margin-top:0;">

<!--Header-part-->
<%--<div id="header">--%>
    <%--<h1>--%>
        <!--<a href="dashboard.html">OA办公系统</a>-->
    <%--</h1>--%>
<%--</div>--%>
<div class="title_Infor" >
    <a href="${ctx}/login" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>

    <h3 >待审新闻</h3>
    <a href="${ctx}/oa/oaNews/form" class="pull-right title_R" style="font-weight: bold;color:#FFFFFF; ">发新闻</a>
</div>
<!--close-Header-part-->
<!-- 消息通知-->
<!--end-main-container-part-->
<div class="span6">
    <div class="widget-box wek_box">
        <c:forEach items="${fns:getAuditNews()}" var="news">
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix"><%--<i>
                    <c:if test="${news.photo==''}" >
                        <img class="img-circle img-polaroid" style="width: 50px;height: 50px;"
                             src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                    </c:if>
                    <c:if test="${news.photo!=''}">
                        <img class="img-circle img-polaroid" style="width: 50px;height: 50px;" src="${news.photo}">
                    </c:if> </i>
                    <div class="update-done"><a title="" href="#"><strong class="fontS_16">${news.createManName}</strong></a>
                        <span></span></div>--%>





                    <div class="new-update clearfix pad_T_no">
                        <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}"><strong class="fontS_16"> ${fns:abbr(news.title,40)}</strong></a>

                        <span style="padding-top: 10px">状态：
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
                    <div class="update-date" style="width: 26%;" ><span class="update-day" style="font-size: 12px"><fmt:formatDate value="${news.createDate}"
                                                                                                              pattern="yyyy-MM-dd"/></span>新闻审核</div>
                </div>




            </div>
        </c:forEach>
    </div>
</div>
<img class="touchRun" style="position:absolute; bottom:0px;width: 400px;height: 200px;display: none;z-index: 999" src="${ctxStatic}/oaApp/img/loadTouch.gif"/>



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
<!--下滑添加收信箱-->
<script>
    $(function(){
        $(window).scroll(function(){
            tongyongScroll($(this));
        });
    })

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
<script>
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
            var url='${ctx}/oa/oaNews/getNewsMore?pageNo='+pageNo+'&pageSize='+pageSize;
            touchScroll(pageNo,pageSize,time,url,'.wek_box');
            pageNo++;
                t=p;
            }
            else{
                return;
            }
        });
    });
</script>


</body>
</html>
