<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>tt</title>
    <meta name="decorator" content="default"/>
    <link href='${pageContext.request.contextPath}/static/jquery/fancybox.css' type="text/css" rel='stylesheet'/>
    <%--<link href='${pageContext.request.contextPath}/static/fullcalendar1/fullcalendar/fullcalendar.css' type="text/css"--%>
    <%--rel='stylesheet'/>--%>
    <%--<link href='${pageContext.request.contextPath}/static/fullcalendar1/fullcalendar/fullcalendar.print.css'--%>
    <%--type="text/css" rel='stylesheet' media='print'/>--%>
    <%--<link href="${ctxStatic}/css/style.css" type="text/css" rel="stylesheet"/>--%>
    <link href="${ctxStatic}/css/style-new24.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/css/iconfont.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/css/calendar.css" type="text/css" rel="stylesheet"/>
    <%--<script src='${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js'></script>--%>
    <script src='${pageContext.request.contextPath}/static/jquery/jquery-ui-1.10.2.custom.min.js'></script>
    <%--<script src='${pageContext.request.contextPath}/static/fullcalendar1/fullcalendar/fullcalendar.min.js'></script>--%>
    <script src='${pageContext.request.contextPath}/static/js/calendar.js'></script>
    <script src='${pageContext.request.contextPath}/static/jquery/jquery.fancybox-1.3.1.pack.js'></script>

    <style type="text/css">
        body {
            /*background-color: #EDEFF0;*/
            padding-right: 20px;
            height: auto;
        }

        /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
        body::-webkit-scrollbar {
            width: 8px;
            height: 10px;
            background-color: #F5F5F5;
            border-radius: 10px;
        }

        /*定义滚动条轨道 内阴影+圆角*/
        body::-webkit-scrollbar-track {
            -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
            border-radius: 10px;
            background-color: #F5F5F5;
        }

        /*定义滑块 内阴影+圆角*/
        body::-webkit-scrollbar-thumb {
            border-radius: 10px;
            -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
            background-color: #555;
        }

        html {
            height: calc(100% - 0px);
            height: -moz-calc(100% - 0px);
            height: -webkit-calc(100% - 0px);
            background: #f0f3f8;
        }

        #main {
            width: 100%;
            min-width: 954px;
        }

        /*#main ul {*/
        /*float: left;*/
        /*list-style: none;*/
        /*margin: 0px;*/
        /*}*/

        /*#main li {*/
        /*height: 54.5%;*/
        /*margin-left: 25px;*/
        /*margin-top: 25px;*/
        /*}*/

        #main li > div {
            height: 100%;
            /*height: 270px;*/
        }

        #main li:nth-child(2) > div {
            /*padding-bottom: 50px;*/
        }

        #main li:nth-child(1) > div .content {
            height: calc(100% - 50px);
            height: -moz-calc(100% - 50px);
            height: -webkit-calc(100% - 50px);
            /*padding-top:50px;*/
        }

        .content {
            overflow: hidden;
            width: 100%;
            height: calc(100% - 26px);
            height: -moz-calc(100% - 26px);
            height: -webkit-calc(100% - 26px);
            -webkit-box-shadow: 0 0 7px #AAA;
            -moz-box-shadow: 0 0 7px #AAA;
            box-shadow: 0 0 7px #AAA;
            border-radius: 2px;
            -moz-border-radius: 2px; /* 老的 Firefox */
            background-color: #fff;
            padding: 0px 0px 20px 0px;
        }

        .content1 {
            width: 100%;
            min-height: 100%;
            -webkit-box-shadow: 0 0 7px #AAA;
            -moz-box-shadow: 0 0 7px #AAA;
            box-shadow: 0 0 7px #AAA;
            border-radius: 2px;
            -moz-border-radius: 2px; /* 老的 Firefox */
            background-color: #fff;
            padding: 10px 0px 10px 0px;
        }

        /*#calendar {*/
        /*width: 95%;*/
        /*margin: 0 auto;*/
        /*}*/

        table {
            width: 100%;
        }

        table td {
            height: 35px;
            padding-left: 15px;
            font-size: 13px;
            font-family: sans-serif;
            font-weight: 500;
            color: #606060;
        }

        table td i {
            margin-right: 5px;
        }

        /*.title {*/
        /*padding: 1px 10px;*/
        /*border-bottom: 3px solid red;*/
        /*font-size: 18px;*/
        /*font-weight: 600;*/
        /*cursor: pointer;*/
        /*}*/

        .moreBtn {
            margin-right: 10px;
            float: right;
            cursor: pointer;
        }

        hr {
            width: 100%;
            margin-top: 0px;
            height: 1px;
            border: 0px;
            background-color: #D5D5D5;
            color: #D5D5D5;
        }

        .dateTd {
            width: 80px;
        }

        a {
            color: #606060;
        }

        /* .ui-widget-header{
             border: 1px solid #ddd!important;
         }*/
        .fc-header-title > h2 {
            font-size: 23px;
            margin-top: -6px;
        }

        .email_inf_main .email_inf_today > ul {
            overflow: visible;
        }

        .calendar-date {
            height: 73.6%;
        }

        #calendar {
            height: 100%;
        }

        .calendar-week .item, .calendar-date .item {
            height: 30px;
            line-height: 30px;
        }

        .email_inf_today > ul > li, .email_inf_yesterday > ul > li {
            height: 30px;
            line-height: 30px;
        }

        .email_inf_miaoshu {
            width: 60%;
        }

        .today_db_con_left > dl {
            height: 95%;
        }

        /*.today_db_con_left>dl>dd {
            *//*padding: 10%;*//*
            height: 80%;
            -webkit-line-height: 100%;
            -moz-line-height: 100%;
            line-height:100%;
        }*/
        .main_bot_bot {
            -webkit-height: calc(100% + 35px);
            -moz-height: calc(100% + 35px);
            height: calc(100% + 35px);
            /*padding-bottom: 110px;*/
        }

        .history_new > ul > li {
            height: 30px;
            line-height: 30px;
        }

        .today_db_con_left > dl > dt {
            margin-top: 10px;
        }

        .General_matters_con .fabu_data {
            display: block;
            width: 50px;
        }

        .General_matters_con .fabure {
            display: block;
            width: 90px;
        }
    </style>


</head>
<body>

<div class="main_bot_bot">

    <div class="main_bot_f">
        <!-- 新闻公告-->
        <div class="news_bulletin">
            <div class="news_bulletin_con">
                <h3> 通知公告 <span><a href="${ctx}/oa/oaNotify/self" style="color:#6699FF">more</a></span></h3>
                <!-- 最新消息-->

                <div class="history_new">
                    <ul id="oaNewsData">
                    </ul>
                </div>
            </div>
        </div>
        <!-- 日历-->
        <div class="calendars">
            <div id="calendar" class="calendar"></div>
        </div>
    </div>
    <div class="main_bot_s">
        <div class="today_db">
            <div class="today_db_con">
                <h3> 今日待办 <span><a href="${ctx}/act/task/todomain1" style="color:#6699FF">more</a></span></h3>

                <div class="today_db_con_left">
                    <dl>
                        <dt></dt>
                        <dd></dd>
                    </dl>
                </div>
                <div class="today_db_con_right">
                    <div>
                        <p><b class="pull-left">事项</b><b class="pull-right">申请人</b></p>
                        <ul id="actTaskList">
                            <c:forEach items="${list}" var="oaRunProcess">
                                <c:forEach items="${fns:getTodo1(oaRunProcess.principalid)}" var="act">
                                    <li>
                                        <c:set var="task" value="${act.task}"/>
                                        <c:set var="user" value="${fns:getCompanyList1(oaRunProcess.agentid)}"/>
                                            <%--<c:set var="act" value="${act}"/>--%>
                                            <%--CSLoginNames用来临时存储代理人的ID--%>
                                        <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}&CSLoginNames=${oaRunProcess.principalid}">(${oaRunProcess.principal}) ${act.procDef.name}
                                            <c:forEach var="user"
                                                       items="${fns:getCompanyList1(oaRunProcess.principalid)}">
                                                <c:if test="${(user.company.id == '861100468') and (fns:getCompanyList1(oaRunProcess.principalid).size()<=1)}">：${fns:abbr(task.name,35)}</c:if>
                                            </c:forEach>
                                            <span style="color: red"> ${act.title}</span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <div class="email_inf">
            <div class="email_inf_con">
                <h3> 邮件提醒<span><a href="${ctx}/oa/mailInfo/listBySend?state=INBOX" style="color:#6699FF">more</a></span>
                </h3>

                <div class="email_inf_main">
                    <div class="email_inf_today">
                        <ul id="mailInfoData">
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="main_bot_t">
        <!-- 已办任务-->
        <div class="General_matters">
            <div class="General_matters_con">
                <h3> 已办事项 <span><a href="${ctx}/act/task/toDoTasks" style="color:#6699FF">more</a></span></h3>
                <ul id="actListData">
                </ul>
            </div>
        </div>
        <!-- 工作日志-->
        <div class="Job_log">
            <div class="Job_log_con">
                <ul>
                    <li class="select_log active">
                        <a href="javascript:;">
                            我的工作日志
                        </a>
                    </li>
                    <li class="select_log">
                        <a href="javascript:;">
                            工作日志审阅
                        </a>
                    </li>
                    <li class="bianji">
                        <a href="${ctx}/oa/oaSummaryDay">
                            <img src="/static/img/bianji.png" alt=""/>
                        </a>
                    </li>
                </ul>
                <div class="Job_log_con_show">
                    <ul class="Job_log_con_show_ul_one active" id="oaSummaryDayList">
                    </ul>
                    <ul id="oaSummary">
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>
<script>
    $(function () {
        var myDate = new Date();
        var m = new Array("January ", "February ", "March ", "April", "May", "June", "July", "August", "September", "Octorber", "November", "December");
        var month = m[myDate.getMonth()]; //获取当前月份(0-11,0代表1月)
        var date = myDate.getDate(); //获取当前日(1-31)

        $(".today_db_con_left>dl>dt").html(month);
        $(".today_db_con_left>dl>dd").html(date);
    })

    /**
     * 日期格式化
     * */
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;

    }

    /**
     *首页中邮件提醒界面查询
     */
    $(function () {
        $.get("${ctx}/oa/mailInfo/mailInfoList?state=INBOX&readMark=0", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    if (i < 5) {
                        oaHtml += "<li><a href='${ctx}/oa/mailInfo/find?id=" + data[i].id + "'> <span class='geshu'>"
                        if (data[i].readMark == "1") {
                            oaHtml += "<img src='/static/img/youjian3.png' alt='' />"
                        } else {
                            oaHtml += "<img src='/static/img/youjian1.png' alt='' />"
                        }
                        oaHtml += "</span>" + data[i].name + "<span class='fabu_data'>" + new Date(data[i].time).Format("yyyy-MM-dd") + "</span><span class='fabure email_inf_miaoshu'>" + data[i].theme + "</span></a></li>"
                    }
                });
                $("#mailInfoData").append(oaHtml);
            }
        });
    });

    function strToDate(str)
    {
        var val=Date.parse(str);
        var newDate=new Date(val);
        return newDate;
    }

    /**
     *首页中新闻公告界面查询
     */
    $(function () {
        $.get("${ctx}/oa/oaNotify/selfList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    if (i < 6) {
                        strToDate (data[i].createDate) == ""
                        oaHtml += " <li><a href='${ctx}/oa/oaNotify/view?id="+data[i].id+"'>"+data[i].title+" &nbsp;&nbsp;&nbsp;"
                        if (data[i].readFlag == "0") {
                            oaHtml += "<img src='/static/images/new.gif'>"
                        }
                        oaHtml +="<span class='fabu_data'>" +data[i].createDate+ "</span></a></li>"
                    }
                });
                $("#oaNewsData").append(oaHtml);
            }
        });
        $.get("${ctx}/oa/oaNews/oaNewsList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    if (i < 6) {
                        strToDate (data[i].createDate) == ""
                        oaHtml += " <li><a href='${ctx}/oa/oaNews/getMyAuditNews?id="+data[i].id+"'>"+data[i].title+" &nbsp;&nbsp;&nbsp;"
                        if (data[i].readFlag == "0") {
                            oaHtml += "<img src='/static/images/new.gif'>"
                        }
                        oaHtml +="<span class='fabu_data'>" +data[i].createDate+ "</span></a></li>"
                    }
                });
                $("#oaNewsData").append(oaHtml);
            }
        });
    });

    /**
     *首页中已办任务查询
     */
    $(function () {
        $.get("${ctx}/act/task/actList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                        oaHtml += "<li><a href='${ctx}/oa/flow/form?id=&formNo="+data[i].taskDefKey+"&showType=flowView&act.procInsId="+data[i].taskId+"'>"+data[i].title+""
                        if(data[i].comment =="" || data[i].comment == undefined){
                            oaHtml += "<span class='fabu_data'></span>"
                        }else{
                            oaHtml += "<span class='fabu_data'> "+data[i].comment+"</span>"
                        }
                        if (data[i].taskName == "归档") {
                            oaHtml += " <span class='fabure'>待归档</span>"
                        }else if(data[i].taskName == "结束"){
                            oaHtml += " <span class='fabure'>已归档</span>"
                        }else{
                            oaHtml += " <span class='fabure'>待审批/待抄送</span>"
                        }
                        oaHtml +="</a></li>"
                });
                $("#actListData").append(oaHtml);
            }
        });
    });
    /**
     *首页中工作日程界面查询
     */
    $(function () {
        $.get("${ctx}/oa/oaSummaryDay/oaSummaryDayList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    oaHtml += " <li><ol><li><a href='javascript:;'><span class='Job_log_con_show_a'>主要工作内容 :</span>" +
                    "<span class='Job_log_con_show_b'>"+data[i].content+"</span><span class='Job_log_con_show_c'>"+new Date(data[i].sumDate).Format("yyyy-MM-dd")+"</span></a></li></ol></li>"
                });
                $("#oaSummaryDayList").append(oaHtml);
            }
        });
    });

    /**
     *首页中工作日程界面查询
     */
    $(function () {
        $.get("${ctx}/oa/oaSummaryPermission/oaSummaryPermissionList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    oaHtml += " <li><ol><li><a href='${ctx}/oa/oaSummaryDay/loginId'><span class='Job_log_con_show_a'>被评阅人：</span>" +
                    " <span class='Job_log_con_show_b' style='color:#61adf8;'>"+data[i].evaluateByNames+"</span><span class='Job_log_con_show_c'>"+data[i].officeName+"</span> " +
                    "<b style='float:right;'>评阅</b></a></li></ol></li>"
                });
                $("#oaSummary").append(oaHtml);
            }
        });
    });

    /**
     *首页中待办事项界面中待审新闻查询
     */
    $(function () {
        $.get("${ctx}/oa/oaNews/auditNewsList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    var str= data[i].title;
                    oaHtml += " <li><a href='${ctx}/oa/oaNews/getAuditNews?id="+data[i].id+"'>" +
                    " 新闻审核："+str.slice(0,40)+"<span style='color: red'>"+data[i].createManName+"</span></a></li>";
                });
                $("#actTaskList").append(oaHtml);
            }
        });
    });

    /**
     *首页中待办事项界面中自由流程查询
     */
    $(function () {
        $.get("${ctx}/process/customRuskTaskinst/customRuskTaskList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    var str= data[i].title;
                    oaHtml += "<li> <a href='${ctx}/process/customProcess/list?procInstId="+data[i].procInstId+"&taskDefKey="+data[i].taskDefKey+"&id="+data[i].id+"'>自定义流程" +
                    "<span style='color: red'> "+data[i].apply+"</span></a></li>";
                });
                $("#actTaskList").append(oaHtml);
            }
        });
    });
    /**
     *首页中待办事项界面查询
     */
    $(function () {
        $.get("${ctx}/act/task/actTaskList", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i) {
                    oaHtml += "<li><a href='${ctx}/act/task/form?taskId="+data[i].taskId+"&taskName="+data[i].taskName+"&" +
                    "taskDefKey="+data[i].taskDefKey+"&procInsId="+data[i].procInsId+"&procDefId="+data[i].procDefId+"&status="+data[i].status+"'>"
                    oaHtml+=data[i].procDefName+" <span style='color: red'>"+data[i].title+"</span> </a></li>";
                });
                $("#actTaskList").append(oaHtml);
            }
        });
    });





</script>
<script>


    //    $(document).ready(function () {
    //
    //        var date = new Date();
    //        var d = date.getDate();
    //        var m = date.getMonth();
    //        var y = date.getFullYear();
    //
    //        $('#calendar').fullCalendar({
    //            theme: false,
    //            header: {
    //                left: 'prev,next today',
    //                center: 'title',
    //                right: 'month,agendaWeek,agendaDay'
    //
    //            },
    //            buttonIcons: false,
    //            editable: true,
    //            selectHelper: true,
    //            allDayDefault: false,
    //            ignoreTimezone: false,
    //            defaultView: 'agendaWeek',
    //            allDaySlot: false,
    //            defaultEventMinutes: 20,
    //            slotEventOverlap: false,
    //            //查询日程到首页
    //            events: {
    //
    //                url: '/a/oa/oaSchedule/calendarEvents.json',//你的controller的地址
    //                type: 'POST',
    //
    //                color: '#c6e1fa',// 背景色
    //                textColor: 'black'// 文字颜色
    //
    //            },
    //            //弹出框添加日程
    //            dayClick: function (date, allDay, jsEvent, view) {
    //                var selDate = $.fullCalendar.formatDate(date, 'yyyy-MM-dd HH:mm');//格式化日期
    //
    //                $.fancybox({
    //                    'type': 'ajax',
    //                    'href': '/a/oa/oaSchedule/form1?date=' + selDate
    //                });
    //            },
    //
    //            eventClick: function (calEvent, jsEvent, view) {
    //                $.fancybox({
    //                    'type': 'ajax',
    //                    'href': '/a/oa/oaSchedule/form2?id=' + calEvent.id
    //                });
    //            },
    //            //显示日程详细信息
    //            eventMouseover: function (calEvent, jsEvent, view) {
    //                var fstart = $.fullCalendar.formatDate(calEvent.start, "yyyy/MM/dd HH:mm");
    //                var content = calEvent.title;
    //                var title = $('<div/>').html('日程日期 :' + ' ' + $.fullCalendar.formatDate(calEvent.start, "yyyy/MM/dd HH:mm") + '&#10;' + '日程内容 :' + ' ' + content + '&#13;').text();
    //
    //                $(this).attr('title', title);  // 鼠标悬浮到title的时候可以设置展现哪些信息
    //
    //                $(this).css('font-weight', 'normal');
    //
    //            }
    //
    //
    //        });
    //
    //    });


</script>
<script>
    //    var mainWidth = 0
    //    $(window).resize(function () {
    //        changeCss()
    //    });
    //    changeCss()
    //    function changeCss() {
    //        if ($('#main').width() != mainWidth) {
    //            mainWidth = $('#main').width()
    //            $('#main li').width(mainWidth / 2 - 40)
    //        }
    //    }


</script>
<link rel="stylesheet" type="text/css" href="/static/css/myjs.css"/>
<script type="text/javascript" src="/static/css/js/myjs.js"></script>
<script type="text/javascript">
    $(".history_new>ul>li").on("click", function () {
        $(".latest_new_art>div").html($(this).find("div").html());
    });
    $(".Job_log_con ul .select_log").each(function (i) {
        $(this).on("click", function () {
            $(this).addClass("active").siblings().removeClass("active");
            $(".Job_log_con_show>ul").eq(i).addClass("active").siblings().removeClass("active");
        });
    });
    var mes = document.getElementById('message').innerHTML;
    if (mes != null && mes != "") {
        Showbo.Msg.alert(mes);
        setTimeout(function () {
            Showbo.Msg.hide();
        }, 3000);
    }


</script>
</body>

</html>