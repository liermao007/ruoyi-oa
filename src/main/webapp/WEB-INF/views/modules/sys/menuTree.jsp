<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%--<script type="text/javascript" src="${ctxStatic}/oaApp/js/amq_jquery_adapter.js"></script>--%>
<!--amq消息中间件js-->
<%--<script type="text/javascript" src="${ctxStatic}/oaApp/js/amq.js"></script>--%>
<script type="text/javascript" src="${ctxStatic}/static/jquery/jquery-1.8.3.min.js"></script>
<style type="text/css">
    #main #left{
        background:none;
        padding-bottom: 40px;
    }
    .menu2 {
        font-size: 12px;
    }
    #main #left>div{
        height: 100%;
    }
    .main_left{
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
    #left li{
         height:auto;
         padding-left: 0;
         padding-top:0;
    }
    #left li>a{
        height:40px;
    }
    #left li ul li{
        text-align: center;
    }
    #left .left_ul_one>.active{
        background:#e6e5ea;
    }
    #left .left_bs_div{
        /*height: -webkit-calc(100% - 35px);*/
        /*height: -moz-calc(100% - 35px);*/
        /*height: calc(100% - 35px);*/
        height: 100%;
        background: #f0f3f8;
        padding: 10px 12px 16px 12px;
    }
    #left .left_ul_one{
        margin-top: 0;
        width:100%;
        height:100%;
        background-color: #fff;
        /*overflow: auto;*/
        -webkit-border-top-left-radius:5px;
        -moz-border-top-left-radius:5px;
        border-top-left-radius:5px;
        -webkit-border-top-right-radius:5px;
        -moz-border-top-right-radius:5px;
        border-top-right-radius:5px;
        border: 1px solid #dbdbdb;
        border-top: 5px solid #30A5FF;
        border-bottom: none;
        position:relative;
        z-index: 1;
        overflow-y: auto;
    }
    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    #left .left_ul_one::-webkit-scrollbar
    {
        width: 8px;
        height: 10px;
        background-color: #F5F5F5;
        border-radius: 10px;
    }
    /*定义滚动条轨道 内阴影+圆角*/
    #left .left_ul_one::-webkit-scrollbar-track
    {
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
        border-radius: 10px;
        background-color: #F5F5F5;
    }
    /*定义滑块 内阴影+圆角*/
    #left .left_ul_one::-webkit-scrollbar-thumb
    {
        border-radius: 10px;
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
        background-color: #555;
    }
    #left .left_bs_div:after{
        content: "";
        display: block;
        width:93%;
        height: 62px;
        background: url("/static/img/bgLeft1.png") no-repeat bottom;
        filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/static/img/bgLeft1.png',  sizingMethod='scale')";
        -ms-filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/static/img/bgLeft1.png',sizingMethod='scale')";
        background-size:100% auto;
        -moz-background-size:100% auto;
        -webkit-background-size:100% auto;
        -o-background-size:100% auto;
        position: absolute;
        bottom: 47px;
        z-index: 0;
    }
    #left .left_ul_one>li>a{
        background: url("/static/img/jianbian2.png") repeat-x bottom;
    }
    #left .left_ul_one>li>ul{
        margin-top:0;
        display: none;
        border-bottom: 1px solid #dbdbdb;
    }
    #left .left_ul_one>.active>ul{
        display: block;
    }
    #left .left_ul_one>.actives>a{
        background:#30A5FF!important;
        color:#fff!important;
    }
    #left .left_ul_one ul li{
        padding-left: 0!important;
    }
    #left .left_ul_one ul li a{
        padding-left: 0!important;
    }
    .main_top.left_top{
        background: none;
    }
    #left .main_bot ul{

    }
    .mianLeft{
        color:#30A5FF;
        height: 100%;
        line-height: 33px;
        padding-left:10px;
        font-size: 16px;
    }
    .mianLeft>b.menu-right{
        display: inline-block;
        height:100%;
        line-height:35px;
        width:40px;
        float: right;
        background: url("/static/img/cloud2.png") no-repeat center;
    }
    .mianLeft>b.menu-left{
        display: inline-block;
        height:100%;
        line-height:35px;
        width:40px;
        float: right;
        display: none;
        background: url("/static/img/cloud3.png") no-repeat center;
    }
    .mianLeft>b.menu-right>i{
        display: inline-block;
        width:2px;
        height:100%;
        background: url("/static/img/fenges.png") no-repeat;
    }
</style>
<div>
    <c:set var="menuList" value="${fns:getMenuList()}"/>
    <c:set var="firstMenu" value="true"/>
    <ul style="display:none;">
        <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
            <c:if test="${menu.parent.id eq (not empty param.parentId ? param.parentId : 1 ) && menu.isShow eq '1'}">
                <li class="menu1" data-id="${menu.id}">
                    <a href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${not empty menu.href ? menu.href : '/404'}" target="${not empty menu.target ? menu.target : 'mainFrame'}" >
                    <c:choose>
                        <c:when test="${menu.name eq '收件箱'}">
                        <c:choose>
                        <c:when test="${remind gt 0}">
                        <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span> &nbsp;<span id="spanEq0">( <span class="infoSize">${remind}</span> )</span></a>
                       </c:when>
                        <c:otherwise>
                        <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span></a>
                       </c:otherwise>
                    </c:choose>
                        </c:when>
                        <c:otherwise>
                            <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span></a>
                        </c:otherwise>
                    </c:choose>
                </li>
                <c:forEach items="${menuList}" var="menu2">
                    <c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
                        <li class="menu2 parent-${menu.id}">
                            <a href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}" target="${not empty menu2.target ? menu2.target : 'mainFrame'}" >
                                <i class="icon-${not empty menu2.icon ? menu2.icon : ''}"></i>&nbsp;<span>${menu2.name}</span></a>
                        </li>
                    </c:if>
                </c:forEach>
            </c:if>
        </c:forEach>
    </ul>
<div class="main_left">
<div class="main_bot">
<div class="personal_inf">
<p class="mianLeft"><i class="icon-home" style="margin-right: 5px;"></i><span>${param.parentName}</span><b class="menu-right"></b><b class="menu-left"></b></p>

</div>
<div class="left_bs_div">
    <ul class="left_ul_one">
        <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
            <c:if test="${menu.parent.id eq (not empty param.parentId ? param.parentId : 1 ) && menu.isShow eq '1'}">
                <c:if test="${menu.name eq '个人办公'}">
                    <li class="nav-item menu1 actives nav-show active">
                        <a href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${not empty menu.href ? menu.href : '/404'}"
                           target="${not empty menu.target ? menu.target : 'mainFrame'}" data-id="${menu.id}" onclick="menuChild('${menu.id}');">
                            <c:choose>
                            <c:when test="${menu.name eq '收件箱'}">
                            <c:choose>
                            <c:when test="${remind gt 0}">
                            <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span> &nbsp;<span id="spanEq2">( <span class="infoSize">${remind}</span> )</span> <i class="myIstyle"><b></b></i></a>
                        </c:when>
                        <c:otherwise>
                            <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span><i class="myIstyle"><b></b></i></a>
                        </c:otherwise>
                        </c:choose>
                        </c:when>
                        <c:otherwise>
                            <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span> <i class="myIstyle"><b></b></i></a>
                        </c:otherwise>
                        </c:choose>
                        <ul style="display: block;">
                            <c:set var="menuChildFirst" value="0"></c:set>
                            <c:forEach items="${menuList}" var="menu2">
                                <c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
                                    <c:set var="menuChildFirst" value="${menuChildFirst+1}"></c:set>
                                    <li class="menu2 parent-${menu.id}">
                                        <a href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}"
                                           target="${not empty menu2.target ? menu2.target : 'mainFrame'}" id="${menu.id}_${menuChildFirst}">
                                            <i class="icon-${not empty menu2.icon ? menu2.icon : ''}"></i>&nbsp;<span>${menu2.name}</span></a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            <c:if test="${menu.name ne '个人办公'}">
                <li class="nav-item menu1 ">
                    <a href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${not empty menu.href ? menu.href : '/404'}"
                       target="${not empty menu.target ? menu.target : 'mainFrame'}" data-id="${menu.id}" onclick="menuChild('${menu.id}');">
                        <c:choose>
                        <c:when test="${menu.name eq '收件箱'}">
                        <c:choose>
                        <c:when test="${remind gt 0}">
                        <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span> &nbsp;<span id="spanEq1">( <span class="infoSize">${remind}</span> )</span> <i class="myIstyle"><b></b></i></a>
                    </c:when>
                    <c:otherwise>
                        <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span><i class="myIstyle"><b></b></i></a>
                    </c:otherwise>
                    </c:choose>
                    </c:when>
                    <c:otherwise>
                        <i class="icon-${not empty menu.icon ? menu.icon : ''}"></i>&nbsp;<span>${menu.name}</span> <i class="myIstyle"><b></b></i></a>
                    </c:otherwise>
                    </c:choose>
                    <ul>
                        <c:set var="menuChildFirst" value="0"></c:set>
                        <c:forEach items="${menuList}" var="menu2">
                            <c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
                                <c:set var="menuChildFirst" value="${menuChildFirst+1}"></c:set>
                                <li class="menu2 parent-${menu.id}">
                                    <a href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}"
                                       target="${not empty menu2.target ? menu2.target : 'mainFrame'}" id="${menu.id}_${menuChildFirst}">
                                        <i class="icon-${not empty menu2.icon ? menu2.icon : ''}"></i>&nbsp;<span>${menu2.name}</span></a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </li>
            </c:if>
            </c:if>
        </c:forEach>
        <%--actives nav-show active--%>

    </ul>
</div>
</div>
<%--</div>--%>
</div>

        <script>
            function menuChild(id){

                $("#"+id+"_1").click();
            }
            $('.nav-item>a').on("click",function(){
                if($(this).next("ul").children("li").length>0){
                        if (!$('.nav').hasClass('nav-mini')) {
                            if ($(this).next().css('display') == "none") {
                                $('.nav-item').children('ul').slideUp(300);
                                $(this).next('ul').slideDown(300);
                                $(this).parent("li").addClass("actives").siblings('li').removeClass('actives');
                                $(this).parent('li').addClass('nav-show').siblings('li').removeClass('nav-show');
                            } else {
                                //������չ��
    //                            $(this).next('ul').slideUp(300);
    //                            $(this).parent("li").removeClass("actives");
    //                            $('.nav-item.nav-show').removeClass('nav-show');
                            }
                        }
                        if($(this).attr("href").indexOf("/404")!=-1){
                            var arf=$(this).next("ul").children("li").eq(0).children("a").attr("href");
                            $(this).attr("href",arf);
                        }
                        console.log($(this).next("ul").children("li")[0]);
                        $(this).next("ul").children("li").eq(0).addClass("active");
                    }else{
                        if (!$('.nav').hasClass('nav-mini')) {
                            if ($(this).next().css('display') == "none") {
                                //չ��δչ��
                                $('.nav-item').children('ul').slideUp(300);
                                $(this).parent("li").addClass("actives").siblings('li').removeClass('actives');
                            }
                        }
                }
            });

            var showMenu = true;
            $('.menu-right').on('click', function() {
                $('#left').width(1)
                wSize()
                showMenu = false;
                $('.menu-right').hide()
                $('.menu-left').show()
            })
            $('.menu-left').on('click', function() {
                $('#left').width(300)
                wSize()
                showMenu = true;
                $('.menu-left').hide()
                $('.menu-right').show()
            })

            $('#left').on('mouseover', function() {
                if(!showMenu) {
                    $('#left').show()
                    $('#left').width(300)
                }

            })

            $('#left').on('mouseout', function() {
                if(!showMenu) {
                    $('#left').show()
                    $('#left').width(1)
                }
            })
        </script>
