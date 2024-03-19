
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.form.min.js"></script>

    <script>
        $(function(){

            $("#isallday").click(function(){//是否是全天事件
                if($("#sel_start").css("display")=="none"){
                    $("#sel_start,#sel_end").show();
                }else{
                    $("#sel_start,#sel_end").hide();
                }
            });

            $("#isend").click(function(){//是否有结束时间
                if($("#p_endtime").css("display")=="none"){
                    $("#p_endtime").show();
                }else{
                    $("#p_endtime").hide();
                }
                $.fancybox.resize();//调整高度自适应
            });

            $(".btn_ok").on("click", function () {

                $.ajax({

                    url: "${ctx}/oa/oaSchedule/save1",
                    data: {content:$("#event").val(),  scheduleDate:$("#startdate").val()},
                    dataType: "text"

                })
            })

        });

        function showRequest(){
            var events = $("#event").val();
            if(events==''){
                alert("请输入日程内容！");
                $("#event").focus();
                return false;
            }
        }

        function showResponse(responseText, statusText, xhr, $form){
            if(statusText=="success"){
                if(responseText==1){
                    $.fancybox.close();//关闭弹出层
                    $('#calendar').fullCalendar('refetchEvents'); //重新获取所有事件数据
                }else{
                    alert(responseText);
                }
            }else{
                alert(statusText);
            }
        }





    </script>
    <style>
        #fancybox-overlay{
            position: fixed;
            top: 0;
            left: 0;
            bottom: 0;
            right: 0;
            background: #000;
            z-index: 1100;
            display: none;
        }
        /*#fancybox-wrap{*/
        /*width: 470px!important;*/
        /*height: 218px!important;*/
        /*}*/
    </style>
    <style>
        .btn_ok {
            background: #360;
            border: 1px solid #390;
            color: #fff;
        }
        .sub_btn {
            height: 32px;
            line-height: 32px;
            padding-top: 6px;
            border-top: 1px solid #f0f0f0;
            text-align: right;
            position: relative;
        }

        #fancybox-outer {
            position: relative;
            width: 100%;
            height: 85%;
            background: #FFF;


        }

        .fancy{
            margin-left:15px;

        }
    </style>
</head>
<body>

<div id="fancybox-outer" >
    <div class="rl_bg"></div>
    <div class="fancy">
        <h3 style="font-size: 20px">添加日程</h3>
        <form id="add_form" modelAttribute="oaSchedule" action=""  method="post">
            <input type="hidden" name="action" value="add">
            <p>日程内容：<input type="text" class="input" name="content" id="event" style="width:320px"
                           placeholder="记录你将要做的一件事..."></p>
            <p>日程日期：<input type="text" class="input datepicker" name="scheduleDate" id="startdate"
                           value="${date}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"
                    />
       <span id="sel_start" style="display:none"><select name="s_hour">
           <option value="00">00</option>
           ...<!--省略多个option，下同-->
       </select>:
    <select name="s_minute">
        <option value="00" selected>00</option>
        ...
    </select>
    </span>
            </p>
            <p id="p_endtime" style="display:none">结束时间：<input type="text" class="input datepicker"
                                                               name="enddate" id="enddate" value="<?php echo $_GET['date'];?>">
    <span id="sel_end" style="display:none"><select name="e_hour">
        <option value="00">00</option>
        ...
    </select>:
    <select name="e_minute">
        <option value="00" selected>00</option>
        ...
    </select>
    </span>
            </p>

            <div class="sub_btn">
                <input type="submit" class="btn_ok" value="确定">
                <input type="button"  class="btn btn_cancel" value="取消" onClick="$.fancybox.close()"></div>
        </form>
    </div>
</div>
</body>
</html>
