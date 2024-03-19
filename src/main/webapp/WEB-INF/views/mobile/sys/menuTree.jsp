<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>智慧岗位工作平台</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/bootstrap.min.css"/>

    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>
</head>
<body>
<div class="title_Infor">
    <a href="${ctx}/login" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>
    <c:set var="menuList" value="${fns:getMenuList()}"/>
    <c:set var="firstMenu" value="true"/>
    <c:set var="parentId" value="${parentId}"/>
    <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
        <c:if test="${menu.id eq parentId &&menu.isShow eq '1'}">
            <h3>${menu.name}</h3>
        </c:if>
    </c:forEach>
</div>
<div class="sp_Forme">
    <c:set value="0" var="flowCount"></c:set>
    <c:forEach items="${fns:getTodo()}" var="act">
        <c:set value="${flowCount + 1}" var="flowCount"></c:set>
    </c:forEach>
    <ul>
        <li style="position: relative">
            <a href="${ctx}/act/task/todo" class="a_style">
                <div style="padding-bottom: 5px"><img src="${ctxStatic}/oaApp/img/icon-will.png" width="60px"/></div>
                <c:if test="${empty flowCount }">
                    <span class="title_icon">${flowCount}</span>
                </c:if>
                <span>待我审批的</span>
                <c:if test="${fns:getTodo().size() > 0}">
                    <span class="shouye_icon">${fns:getTodo().size()}</span>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/oa/flow/flowList" class="a_style">
                <div style="padding-bottom: 5px"><img src="${ctxStatic}/oaApp/img/icon-owen.png" width="60px"/></div>
                <span>我发起的</span>
            </a>
        </li>
        <li>
            <a href="${ctx}/act/task/historicList" class="a_style">
                <div style="padding-bottom: 5px"><img src="${ctxStatic}/oaApp/img/icon-over.png" width="60px"/></div>
                <span>已办的</span>
            </a>
        </li>
    </ul>

</div>
<c:set var="menuCountIconParent" value="0"/>
<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
    <c:if test="${menu.parent.id eq parentId && menu.isShow eq '1'}">
        <c:set var="menuCount" value="0"/>
        <c:forEach items="${menuList}" var="menu2">
            <c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
                <c:set value="${menuCount + 1}" var="menuCount"></c:set>
            </c:if>
        </c:forEach>
        <c:if test="${(menu.href==null or menu.href=='') && menu.isShow eq '1'}">
            <div class="work_Con">
                <div class="work_Con_til">
                    <a href="${ctx}${menu.href}"><span class="pull-left">${menu.name}</span></a>
                    <span class="pull-right more_Btn">更多</span>
                    <span class="pull-right shou_Up">收起</span>
                </div>

                <div class="work_Con_Pic">
                    <ul>
                        <c:set var="menuCountIcon" value="0"/>
                        <c:forEach items="${menuList}" var="menu2">
                            <c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
                                <c:set value="${menuCountIcon + 1}" var="menuCountIcon"></c:set>
                                <li>
                                    <a href="${ctx}${menu2.href}" class="a_style">
                                        <div style="padding-bottom: 5px"><img
                                                src="${ctxStatic}/oaApp/img/liucheng_Icon/liucheng-icon-${menuCountIcon%5}.png"
                                                width="45px"/></div>
                                        <span>${menu2.name}</span>

                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:if>
        <c:if test="${(menu.href!=null and menu.href!='') && menu.isShow eq '1'}">
            <c:set value="${menuCountIconParent + 1}" var="menuCountIconParent"></c:set>
            <li class="menuTreeNoBackGroud">
                <c:if test="${menu.name eq '系统监控'}">
                    <a href="${menu.href}" class="a_style">
                        <div style="padding-bottom: 5px"><img
                                src="${ctxStatic}/oaApp/img/liucheng_Icon/liucheng-icon-${menuCountIconParent%5}.png"
                                width="45px"/></div>
                        <span>${menu.name}</span>
                    </a>
                </c:if>
                <c:if test="${menu.name ne  '系统监控'}">
                    <a href="${ctx}${menu.href}" class="a_style">
                        <div style="padding-bottom: 5px"><img
                                src="${ctxStatic}/oaApp/img/liucheng_Icon/liucheng-icon-${menuCountIconParent%5}.png"
                                width="45px"/></div>
                        <span>${menu.name}</span>
                        <c:if test="${menu.name eq '收件箱'}">
                            <c:if test="${remind gt 0}">
                                <span class="shouye_icon">${remind}</span>
                            </c:if>
                        </c:if>
                    </a>
                </c:if>
            </li>
        </c:if>
    </c:if>
</c:forEach>
<!--end-Footer-part-->
<script src="${ctxStatic}/oaApp/js/jquery.min.js"></script>

<script>
    $(function () {
        $(".more_Btn").each(function (i) {
            $(this).on("click", function () {
                $(this).css("display", "none");
                $(".shou_Up").eq(i).css("display", "block");
                $(".work_Con").eq(i).css({height: "auto"});
            });

        })
        $(".shou_Up").each(function (index) {
            $(this).on("click", function () {
                $(this).css("display", "none");
                $(".more_Btn").eq(index).css("display", "block");
                $(".work_Con").eq(index).css({height: "140px"});
            })
        });
    });
</script>

<script src="${ctxStatic}/oaApp/js/footer.js"></script>
</body>
</html>
