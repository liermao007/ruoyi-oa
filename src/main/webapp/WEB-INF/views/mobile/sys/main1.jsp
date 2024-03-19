<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport"/>
    <script type="text/javascript" src="${ctxStatic}/oaApp/js/amq_jquery_adapter.js"></script><!--amq消息中间件js-->
    <script type="text/javascript" src="${ctxStatic}/oaApp/js/amq.js"></script><!--amq消息中间件js-->
    <script type="text/javascript" src="${ctxStatic}/oaApp/getData.js"></script>
    <link href='${ctxStatic}/oaApp/easyui.css' type="text/css" rel='stylesheet' />
    <script type="text/javascript" src="${ctxStatic}/oaApp/jquery.easyui.min.js"></script>
</head>
<body>

<!--Header-part-->
<div id="header">
    <h1>
        <a href="dashboard.html">智慧岗位工作平台</a>
    </h1>
</div>
<!--close-Header-part-->
<!-- 消息通知-->
<div class="con_inf">
    <c:set value="0" var="flowCount"></c:set>
    <c:forEach items="${fns:getAuditNews()}" var="news">
        <c:set value="${flowCount + 1}" var="flowCount"></c:set>
    </c:forEach>
    <c:forEach items="${fns:getTodo()}" var="act">
        <c:set value="${flowCount + 1}" var="flowCount"></c:set>
    </c:forEach>
    <c:set var="show" value="6"/>
    <%--<a href="${ctx}/oa/oaSchedule/calendar">--%>
    <%--<i>--%>
    <%--<img src="${ctxStatic}/oaApp/img/icon-rc.png" style="margin-top: -10px"/>--%>
    <%--</i>--%>
    <%--<span>日程安排</span>--%>
    <%--</a>--%>
    <a href="${ctx}/oa/oaNews/self">
        <i>
            <img src="${ctxStatic}/oaApp/img/icon-rc.png"/>
        </i>
        <span>新闻公告</span>
    </a>
    <a href="${ctx}/oa/mailInfo/listBySend?state=INBOX">
        <i>
            <img src="${ctxStatic}/images/icons/mail.png"/>
        </i>
        <span>未读邮件</span>
        <c:if test="${remind gt 0}">
            <span class="db_inf">${remind}</span>
        </c:if>
    </a>
    <a href="${ctx}/oa/oaNews/selfNews">
        <i>
            <img src="${ctxStatic}/oaApp/img/zytongzhi.png"/>
        </i>
        <span>待审新闻</span>
    </a>
    <a href="${ctx}/act/task/todomain1/">
        <i>
            <img src="${ctxStatic}/oaApp/img/dbliucheng.png"/>
        </i>
        <span>待办流程</span>
        <c:if test="${empty flowCount }">
            <div class="db_inf">${flowCount}</div>
        </c:if>
    </a>
</div>
<!-- 消息通知-->
<!--控制面板-->
<div class="container-fluid">
    <div class="quick-actions_homepage">
        <ul class="quick-actions">
            <c:set var="showMenu" value="1"/>
            <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1' && menu.name ne '我的面板'}">
                    <li style="position: relative"><a href="${ctx}/sys/menu/tree?parentId=${menu.id}"> <i><img
                            src="${ctxStatic}/oaApp/img/icon-${menu.icon}.png"></i> ${menu.name} </a>
                        <c:if test="${menu.name eq '内部邮箱'}">
                            <c:if test="${remind gt 0}">
                                <span class="shouye_icon">${remind}</span>
                            </c:if>
                        </c:if>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
    <!--End-控制面板-->
</div>

<!--end-main-container-part-->
<div class="span6">
    <div class="widget-box wek_box">
        <label id="message" style="color:red; font-weight:bold;display: none;">${message}</label>
        <c:if test="${empty fns:getAuditNews() && empty fns:getTodo()}">
            <c:forEach items="${fns:getFifteenNewsAndRole()}" var="news">
                <div class="widget-content nopadding updates collapse in bor_N">
                    <div class="new-update clearfix"><i>
                        <c:if test="${news.photo==null || news.photo==''}" >
                            <img class="img-circle img-polaroid" style="width: 50px;height: 50px;" src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                        </c:if>
                        <c:if test="${news.photo!=null && news.photo!=''}">
                            <img style="width:50px;height: 50px;" class="img-circle img-polaroid" src="${news.photo}">
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
            </c:forEach>
        </c:if>
        <c:forEach items="${fns:getAuditNews()}" var="news">
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix"><i>
                    <c:if test="${news.photo==null || news.photo==''}" >
                        <img class="img-circle img-polaroid" style="width: 50px;height: 50px;" src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                    </c:if>
                    <c:if test="${news.photo!=null && news.photo!=''}">
                        <img style="width:50px;height: 50px;" class="img-circle img-polaroid" src="${news.photo}">
                    </c:if> </i>

                    <div class="update-done"><a title="" href="#"><strong class="fontS_16">${news.createManName}</strong></a>
                        <span></span></div>
                    <div class="update-date"><span class="update-day" style="font-size: 12px"><fmt:formatDate value="${news.createDate}"
                                                                                                              pattern="yyyy-MM-dd"/></span>新闻审核</div>
                </div>

                <div class="new-update clearfix pad_T_no">
                    <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}"><h4> ${fns:abbr(news.title,40)}</h4></a>
                    <span class="fontS_14"> ${fns:abbr(news.content,45)}</span>
                    </a>
                </div>
            </div>
        </c:forEach>
        <c:forEach items="${fns:getTodo()}" var="act">
            <c:set var="task" value="${act.task}"/>
            <c:set var="act" value="${act}"/>
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix"><i>
                    <c:if test="${news.photo==null || news.photo==''}" >
                        <img class="img-circle img-polaroid" style="width: 50px;height: 50px;" src="${ctxStatic}/oaApp/img/photoDefault.jpg">
                    </c:if>
                    <c:if test="${news.photo!=null && news.photo!=''}">
                        <img style="width:50px;height: 50px;" class="img-circle img-polaroid" src="${news.photo}">
                    </c:if> </i>
                    <div class="update-done"><a title="" href="#"><strong class="fontS_16"> ${act.title}</strong></a>
                        <span></span></div>
                    <div class="update-date"><span class="update-day" style="font-size: 12px"><fmt:formatDate value="${task.createTime}"
                                                                                                              pattern="yyyy-MM-dd"/></span>待办流程
                    </div>
                </div>
                <div class="new-update clearfix pad_T_no">
                    <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${act.status}">
                        <h4> ${act.procDef.name}</h4>
                        <span class="fontS_14"> </span>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>

</div>


<script type="text/javascript">
    window.onload = function(){
        var amq = org.activemq.Amq;
        var myDestination='topic://save_oaNews';
        amq.init({
            uri: 'a/amq',
            logging: true,
            timeout: 20,
            clientId:(new Date()).getTime().toString() //防止多个浏览器窗口标签共享同一个JSESSIONID
        });
        var myHandler =
        {
            rcvMessage: function(message)
            {
                var now=new Date();
                var month = now.getMonth() + 1;
                var day = now.getDate();
                var sec = now.getSeconds();
                var min =now.getMinutes();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (day >= 1 && day <= 9) {
                    day = "0" + day;
                }
                if (min >= 1 && min <= 9) {
                    min = "0" + min;
                }
                if (sec >= 0 && sec <= 9) {
                    sec = "0" + sec;
                }
                if(message==null){
                    return;}
                /* var html='<p align="center" style="font-size:14px;">'+message.data+'</p>'; */
                var list=[];
                ob = JSON.parse(message.data);
                list.push(ob)
                ob.date=now.getFullYear()+"年"+month+"月"+day+"日"+" "+now.getHours() + ":" + min+ ":"+ sec;

                //把list数据填入相应的位置：
//                console.log(list)
//                alert(list);
                $("#aa").datagrid('loadData',list);
            }
        };
        amq.addListener("wzm",myDestination,myHandler.rcvMessage);


    };

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
<!--发布按钮-->


</body>
<link rel="stylesheet" type="text/css" href="/static/css/myjs.css"/>
<script type="text/javascript" src="/static/css/js/myjs.js" ></script>
<script type="text/javascript">
    var mes=document.getElementById('message').innerHTML;
    if(mes!=null&&mes!=""){
        Showbo.Msg.alert(mes);
    }
</script>
</html>
