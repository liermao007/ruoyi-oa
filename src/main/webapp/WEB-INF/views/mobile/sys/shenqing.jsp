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

    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>
</head>
<body>
<!--tou-->
<div class="title_Infor">
    <a href="${ctx}/login" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>

    <h3>申请</h3>
</div>
<%--<div class=" nopadding updates collapse in bor_N bgC_Blue marT_10">--%>
    <%--<div class="new-update clearfix"><i><img class="img-circle img-polaroid" src="${ctxStatic}/oaApp/img/shouye.png">--%>
    <%--</i>--%>

        <%--<div class="update-done mysely1"><a title="" href="#"><span class="fontS_16">申请</span></a></div>--%>

    <%--</div>--%>
<%--</div>--%>

<div class="span6">
    <c:set value="0" var="flowCount"></c:set>
    <c:set var="menuList" value="${fns:getMenuList()}"/>
    <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
        <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1' && menu.name ne '我的面板'
        && menu.name ne '内部邮箱'&& menu.name ne '工作日程' && menu.name ne '系统设置' && menu.name ne '新闻公告' && menu.name ne '通知公告' && menu.name ne '流程管理'}">
            <div class="widget-box bor_N marT_0">
                <div class="widget-title bg_lo bgC_White">
                    <a class="OA_a" data-toggle="collapse" href="#collapseG${menu.id}">
                        <span class="icon"> <i class="icon-chevron-down"></i> </span>
                        <h5>${menu.name}</h5>
                        <div class="pull-right mysely2"><span></span></div>
                    </a>
                </div>
                    <div class="widget-content nopadding updates collapse" id="collapseG${menu.id}">
                    <c:forEach items="${menuList}" var="menu1" varStatus="idxStatus">
                        <c:if test="${menu1.parent.id eq menu.id && menu1.isShow eq '1'}">
                            <c:forEach items="${menuList}" var="menu2">
                                <c:set value="${flowCount + 1}" var="flowCount"></c:set>
                                <c:if test="${menu2.parent.id eq menu1.id && menu2.isShow eq '1'}">
                                    <div class="new-update clearfix"><i class="icon-ok-sign marT_5"></i>
                                        <div class="update-done"><a title="" href="${ctx}${menu2.href}"><strong> ${menu2.name}</strong></a>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                    </div>
                </div>
        </c:if>
    </c:forEach>
</div>

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
</body>
</html>
