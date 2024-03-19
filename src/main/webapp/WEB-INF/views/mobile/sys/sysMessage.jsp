<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="decorator" content="footer"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 设置移动端视图 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>岗位工作平台</title>
    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="portrait">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="portrait">
    <!-- UC强制全屏 -->
    <meta name="full-screen" content="yes">
    <!-- QQ强制全屏 -->
    <meta name="x5-fullscreen" content="true">

    <!-- Bootstrap -->
    <%--<link href="${ctxStatic}/bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="${ctxStatic}/css/common_phone.css" rel="stylesheet">
    <link href="${ctxStatic}/css/style_phone.css" rel="stylesheet">
    <script src="${ctxStatic}/css/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/css/js/cookie.js" type="text/javascript"></script>
    <script src="${ctxStatic}/css/js/HtmlFontSize.js"></script>
</head>
<body>
<script>
    var logined='${logined}';
    var loginOrgId='${loginOrgId}';
    var reply='${reply}';

    //选择机构后回调执行跳转
    function orgRoleCallBack(){
        $("#selectOrg").submit();
    }
    var showWarningNum = 0;
    var htmlWidth = screen.availWidth;//屏幕可用宽度
    var htmlHeigth = screen.availHeight;//屏幕可用高度
    //新页面的属性信息：
    var htmlNew = 'height=' + htmlHeigth * 0.8 + ',width=' + htmlWidth * 0.8 + ',top=' + htmlHeigth * 0.1 + ',left=' + htmlWidth * 0.1 + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no';
    var reOAUrl = function (url) {
        window.open('${oaUrl}');
    }

    var reHealthUrl = function (url) {
        window.open(url);
    }
    var reDoctorFlag = 0;
    $("#reDoctor").on("click",function(){
        reDoctor();
    });

    //获取岗位工作平台数据
    function noReadNews() {
//        getData
        $.get("${ctx}/sys/user/getDataList?workUrlNoIp=/a/service/tool/getData", function (data) {
            var oaHtml = '';
            if (data.length > 0) {
                $.each(data, function (i, act) {
                    var task = act["com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.MSG1"];
                    <%--oaHtml += "<li id='ycNotDo"+(showWarningNum+i+1)+"' style='display: ;'><i></i><a onclick='reOAUrl(\"${oaUrl}/a/oa/oaNews/getAuditNews?id=" + task.id + "\")' href='javascript:;'>【新闻公告】" + task.destination + "&nbsp;&nbsp;---" + task.type + "</a></li>";--%>
                    $("#todayNum").html(task.todayNum);
                    $("#bedDays").html(task.bedDays);
                    $("#hpIncome").html(" "+task.hpIncome+" "+"元");
                    $("#outIncome").html(" "+task.outIncome+" "+"元");
                    $("#regNum").html(task.regNum);
                    $("#total").html(parseFloat(task.outIncome,2)+parseFloat(task.hpIncome,2));
                });

            }
        });
    }
    noReadNews();
</script>
<header>
    <div class="logo">
        <%--<img src="${ctxStatic}/images/logophone.png" alt="中国卫生集团岗位工作平台"/>--%>
    </div>
</header>
<section>
    <!-- 系统信息-->
    <div class="systems_inf">
        <h3><span></span> <a href="javascript:;">系统信息</a></h3>
        <div class="systems_tab">
            <div class="systems_tab_des">
                <div>
                    <ul>
                        <li>
                            <p>日总收入</p>
                            <span id="total">0<%--${outpatient+IncomeHospital}--%></span>
                        </li>
                        <li>
                            <p>门诊收入&nbsp;:&nbsp;<span id="outIncome">&nbsp;0&nbsp;元</span>&nbsp;<img src="${ctxStatic}/images/jiantou2.jpg" alt=""/></p>
                            <p>住院收入&nbsp;:&nbsp;<span id="hpIncome">&nbsp;0&nbsp;元</span>&nbsp;<img src="${ctxStatic}/images/jiantou1.jpg" alt=""/></p>
                        </li>
                    </ul>
                    <a class="look_more" href="javascript:;">查看更多</a>
                    <div class="systems_tab_con">
                        <div class="systems_tab_con_select">
                            <span>单位/万</span>
                            <ul>
                                <li>
                                    <a href="javascript:;">医院</a>
                                </li>
                                <li>
                                    <a class="active" href="javascript:;">科室</a>
                                </li>
                            </ul>
                        </div>
                        <!-- 科室总收入-->
                        <div id="Echart_1" class="a"> </div>
                        <!-- 医院总收入-->
                        <div id="Echart_2" class="a dis_no"> </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="systems_tab">
            <div class="systems_tab_des">
                <div>
                    <ul>
                        <li>
                            <p>今日门诊数</p>
                            <span id="305">305行</span>
                        </li>
                        <li>
                            <p>待诊人数&nbsp;:&nbsp;<span id="308">&nbsp;308行&nbsp;人</span>&nbsp;<img src="${ctxStatic}/images/jiantou2.jpg" alt=""/></p>
                            <p>已诊人数&nbsp;:&nbsp;<span id="309">&nbsp;309行&nbsp;人</span>&nbsp;<img src="${ctxStatic}/images/jiantou1.jpg" alt=""/></p>
                        </li>
                    </ul>
                    <a class="look_more" href="javascript:;">查看更多</a>
                    <div class="systems_tab_con">
                        <div class="systems_tab_con_select">
                            <span>单位/人</span>
                            <ul>
                                <li>
                                    <a href="javascript:;">待诊人数</a>
                                </li>
                                <li>
                                    <a class="active" href="javascript:;">已诊人数</a>
                                </li>
                            </ul>
                        </div>
                        <!-- 科室总收入-->
                        <div id="Echart_3" class="a"> </div>
                        <!-- 医院总收入-->
                        <div id="Echart_4" class="a dis_no"> </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--当日操作-->
    <div class="day_operation">
        <ul>
            <li>
                <a href="#">
                    当日挂号量
                    <span id="regNum">0<%--${numberOfRegistered}--%></span>
                </a>
            </li>
            <li>
                <a href="#">
                    当前住院数
                    <span id="todayNum">0<%----%></span>
                </a>
            </li>
            <li>
                <a href="#">
                    当日预约手术
                    <span id="343">343行<%----%></span>
                </a>
            </li>
        </ul>
    </div>
</section>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<%--<script src="${ctxStatic}/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>--%>
<script src="${ctxStatic}/css/js/index.js"></script>

<script src="${ctxStatic}/css/js/echarts.min.js"></script>
<!-- 科室总收入柱状图-->
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('Echart_1'));

    // 指定图表的配置项和数据
    //        app.title = '坐标轴刻度与标签对齐';

    option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '0%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['内一', '内二', '外科', '儿科', '神经科', '骨科', '眼科'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'月总收入',
                type:'bar',
                barWidth: '60%',
                data:[800, 502, 900, 964, 690, 730, 420]
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
<!-- 医院总收入柱状图-->
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('Echart_2'));

    // 指定图表的配置项和数据
    //        app.title = '坐标轴刻度与标签对齐';

    option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '0%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['双滦医院', '安平博爱', '滦平妇幼', '安平红十字', '邢台医院', '邯郸医院', '北京京顺'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'月总收入',
                type:'bar',
                barWidth: '60%',
                data:[800, 1220, 900, 964, 1700, 730, 1620]
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
<!-- 待诊人数柱状图-->
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('Echart_3'));

    // 指定图表的配置项和数据
    //        app.title = '坐标轴刻度与标签对齐';

    option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '0%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['内一', '内二', '外科', '儿科', '神经科', '骨科', '眼科'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'月总收入',
                type:'bar',
                barWidth: '60%',
                data:[800, 502, 900, 964, 690, 730, 420]
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
<!-- 已诊人数柱状图-->
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('Echart_4'));

    // 指定图表的配置项和数据
    //        app.title = '坐标轴刻度与标签对齐';

    option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '0%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['双滦医院', '安平博爱', '滦平妇幼', '安平红十字', '邢台医院', '邯郸医院', '北京京顺'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'月总收入',
                type:'bar',
                barWidth: '60%',
                data:[800, 1220, 900, 964, 1700, 730, 1620]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
<%--<script src="${ctxStatic}/js/lz.js"></script>--%>
</body>
</html>