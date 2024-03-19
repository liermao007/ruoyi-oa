<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>智慧岗位工作平台</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/mail.css" />

</head>

<body style="margin-top:0;">
<!--tou-->
<div class="title_Infor">
    <a href="${ctx}/login" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>
    <h3 >内部邮箱</h3>
</div>


<%--<div class=" nopadding updates collapse in bor_N bgC_Blue marT_10">--%>
    <%--<div class="new-update clearfix"><i><img class="img-circle img-polaroid" src="${ctxStatic}/oaApp/img/shouye.png">--%>
    <%--</i>--%>

        <%--<div class="update-done mysely1"><a title="" href="#"><span class="fontS_16">内部邮箱</span></a></div>--%>

    <%--</div>--%>
<%--</div>--%>
<!---->
<!--<div id="search">-->
<!--<input type="text" placeholder="Search here..."/>-->
<!--<button type="submit" class="tip-bottom" title="Search"><i class="icon-search icon-white"></i></button>-->
<!--</div>-->
<!--功能区-->
<div class="span6">
    <div class="widget-content " >
        <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
            <c:if test="${menu.name eq '内部邮箱'}">
                <c:forEach items="${menuList}" var="menu2">
                    <c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
                    <div class="new-update clearfix" style="border-bottom: 1px solid #E1E1E1"><i class="icon-ok-sign marT_5"></i>
                        <div class="update-done"><a title="" href="${ctx}${menu2.href}"><strong>${menu2.name}</strong></a>
                        </div>
                    </div>
                    </c:if>
                </c:forEach>
            </c:if>
        </c:forEach>
        </div>
    </div>
</div>


<!--end-尾部-part-->

<script>
    $(document).ready(function () {
        var a = 0;
        $("#footer").on("click", "#fabu", function () {
            a += 1
            if (a % 2 == 0) {
                $(".menu li:nth-of-type(1)").animate({top: '120%', left: '35%'}, 200);
                $(".menu li:nth-of-type(2)").animate({top: '120%', left: '35%'}, 100);
                $(".menu li:nth-of-type(3)").animate({top: '120%', left: '35%'}, 200);
                a = 0
            } else {

                $(".menu li:nth-of-type(1)").animate({top: '45%', left: '0'}, 200);
                $(".menu li:nth-of-type(2)").animate({top: '0', left: '35%'}, 100);
                $(".menu li:nth-of-type(3)").animate({top: '45%', left: '70%'}, 200);

            }


        })
    })

</script>



<script type="text/javascript">

    function resetMenu() {
        document.gomenu.selector.selectedIndex = 2;
    }
</script>
<script type="text/javascript">
    $(function(){
        $('.new-update').each(function(a){
            $(this).on("click",function(){
                var url =  $(this).find("a").eq(0).attr("href");
                window.location.href=url;

            });
        });
    });

</script>
</body>
</html>
