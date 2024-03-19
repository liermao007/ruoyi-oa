<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>已发邮件</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/mail.css" />
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    if (CKEDITOR.instances.content.getData() == "") {
                        top.$.jBox.tip('请填写新闻内容', 'warning');
                    } else {
                        form.submit();
                    }
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });


            $(".check").click(function (e) {
                e.stopPropagation()
            })

            $(".reTr").on('click', function () {
                var id = $(this).attr("data-id")
                window.location.href = '${ctx}/oa/mailInfo/find?id=' + id
            })

            $(".checkOut").click(function (e) {
                e.stopPropagation()
            })


            $(".reOut").on('click', function () {

                var uid = $(this).attr("data-id")
                var name = $(this).attr("data-drop")
                var state=document.getElementById("state").value
                window.location.href = '${ctx}/oa/mailInfo/findMail?id=' + uid + "&name=" + name+'&state='+state;
            })


            $("#checkall").click(
                    function(){
                        if(this.checked){
                            $("input[name='checkbox']").each(function(){this.checked=true;});
                        }else{
                            $("input[name='checkbox']").each(function(){this.checked=false;});
                        }
                    }
            );
        });


        //彻底删除
        function deleteBy() {
            var checked = false;
            var ids = document.getElementsByName("checkbox");
            var chestr = "";
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }
            if (!checked) {
                confirm("未选中任何邮件");
//                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;position: relative;bottom: 50px;left: -250px'>未选中任何邮件</div>";
                return;
            }
            if (confirm('彻底删除后邮件将无法恢复，您确定要删除吗？')) {
                form1.action = '${ctx}/oa/mailInfo/thoroughDelete?ids=' + chestr + '&state=SENT';
                form1.submit();
            }
        }

        //删除
        function deleteB() {
            var checked = false;
            var chestr = "";
            var ids = document.getElementsByName("checkbox");
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }
            if (!checked) {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;position: relative;bottom: 50px;left: -250px'>未选中任何邮件</div>";
                return;
            }
            form1.action = '${ctx}/oa/mailInfo/move?ids=' + chestr + '&state=SENT';
            form1.submit();
        }

        //已读邮件
        function read() {
            var checked = false;
            var chestr = ""
            var ids = document.getElementsByName("checkbox");
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }

            if (!checked) {
                confirm("未选中任何邮件");
//                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;position: relative;bottom: 50px;left: -250px'>未选中任何邮件</div>";
                return;
            }
            form1.action = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=1&state=SENT';
            form1.submit();
        }

        //未读邮件
        function unread() {
            var checked = false;
            var chestr = ""
            var ids = document.getElementsByName("checkbox");
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }

            if (!checked) {
                confirm("未选中任何邮件");
//                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;position: relative;bottom: 50px;left: -250px'>未选中任何邮件</div>";
                return;
            }
            form1.action = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=0&state=SENT';
            form1.submit();
        }

        //移动到已发送
        function send() {
            var checked = false;
            var ids = document.getElementsByName("checkbox");
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                }
            }
            if (!checked) {
                confirm("不能移动到相同的目录");
//                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;position: relative;bottom: 50px;left: -250px'>不能移动到相同的目录</div>";
                return;
            } else {
                confirm("不能移动到相同的目录");
//                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;position: relative;bottom: 50px;left: -250px'>不能移动到相同的目录</div>";
                return;
            }
        }
        //移动到收件箱
        function inbox() {
            var checked = false;
            var chestr = ""
            var ids = document.getElementsByName("checkbox");
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }

            if (!checked) {
                confirm("未选中任何邮件");
//                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;position: relative;bottom: 50px;left: -250px'>未选中任何邮件</div>";
                return;
            }
            form1.action = '${ctx}/oa/mailInfo/remove?ids=' + chestr + '&state=SENT&state1=INBOX';
            form1.submit();
        }
        //循环定时删除
//        window.setInterval(show, 5000);
//        function show() {
//            document.getElementById("ss").innerHTML = "";
//        }

        /**
         *查询内部邮件或者外部邮件
         */
        function find(state) {
            var flag = document.getElementById("type").value
            if (flag == "1") {
                form1.action = '${ctx}/oa/mailInfo/findOut?state='+state;
                form1.submit();
            } else {
                form1.action = '${ctx}/oa/mailInfo/listBySend?state=INBOX';
                form1.submit();
            }
        }

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            form1.action = '${ctx}/oa/mailInfo/listBySend?state=SENT';
            $("#form1").submit();
            return false;
        }

    </script>

</head>
<body>
<!--tou-->
<div class="title_Infor">
    <a href="${ctx}/sys/menu/tree?parentId=c8e2ec7ac7e6486abb8998c0dfb85669" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>已发邮件</h3>
    <a style="margin-top: -2px;margin-right: 10px;" href="${ctx}/oa/mailInfo/none" class="pull-right title_R marT_12"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/bianji.png"></a>


</div>
<form:form id="form1" modelAttribute="mailInfo" action="" method="post"
           class="form-horizontal">

    <div class="select_All">
        <input id="checkall" type="checkbox"  class="checkbox pull-left" style="width: 20px;margin-left: 0px; height: 20px;display: inline-block;top: 5px;">
        <input style="margin-left: 10px;" type="button" value="删除" onclick="deleteB()" class="btn btn-warning">
        <input style="margin-left: 0px;" type="button" value="彻底删除" class="btn-danger" onclick="deleteBy()">
        <input style="margin-left: 0px;" type="button" value="移动到收信箱" class="btn btn-success" onclick="inbox()">
    </div>
    <form:form id="inputForm" modelAttribute="mailInfo" action="${ctx}/oa/mailInfo/move" method="post"
               class="form-horizontal">
        <!--功能区-->
        <div class="span6">
            <c:forEach items="${page.list}" var="mailInfo">
                <div class="widget-box bor_N">
                    <input style="margin-top: 9px;z-index: 999;position:absolute" class="checkbox_1"  name ="checkbox"  value="${mailInfo.id}" type="checkbox" >

                    <div class="widget-content nopadding updates ">
                        <div class="new-update clearfix Pos_R"  onclick="sk('${mailInfo.id}')">
                            <div class="update-done ML_3 Inbox_Width_80" ><strong>${mailInfo.name}</strong> <span class="Inbox_Con"> ${fns:abbr(mailInfo.theme,50)}</span> </div>
                            <div class="update-date Inbox_Width_10"><fmt:formatDate value="${mailInfo.time}" type="both" pattern="MM-dd "/></div>
                            <div class="update-date Inbox_Width_10">
                                <c:if test="${mailInfo.readMark eq '1'}">
                                    <img src="${ctxStatic}/tree/css/mailCss/img/mail020.png"/>
                                </c:if>
                                <c:if test="${mailInfo.readMark eq '0'}">
                                    <img src="${ctxStatic}/tree/css/mailCss/img/mail010.png"/>
                                </c:if>
                            </div>
                        </div>
                    </div>

                </div>
            </c:forEach>
            <img class="touchRun" style="position:absolute; bottom:0px;width: 400px;height: 200px;display: none;z-index: 999" src="${ctxStatic}/oaApp/img/loadTouch.gif"/>
        </div>
    </form:form>
</form:form>

<script>
    $(function(){
        $(".checkbox").each(function(i){
            $(this).on("click",function(){
                if($(this).attr("checked"))
                {
                    $(".checkbox_1").attr("checked",true);
                }else
                {
                    $(".checkbox_1").attr("checked",false);
                }
            });
        });
    })

    function sk(id)
    {
        window.location.href = '${ctx}/oa/mailInfo/find?id=' + id
    }

</script>
<!--下滑添加收信箱-->
<%--<script>--%>
    <%--$(function(){--%>
        <%--$(window).scroll(function(){--%>
            <%--tongyongScroll($(this));--%>
        <%--});--%>
    <%--})--%>

<%--</script>--%>
<script src="${ctxStatic}/oaApp/js/oaScroll.js"></script>
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
            var url='${ctx}/oa/mailInfo/getMailMore?state=SENT&pageNo='+pageNo+'&pageSize='+pageSize;
            touchScroll(pageNo,pageSize,time,url,".span6");
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
