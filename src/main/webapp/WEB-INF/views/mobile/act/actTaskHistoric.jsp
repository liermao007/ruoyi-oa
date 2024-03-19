<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>已办任务</title>
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/mail.css" />
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css" />

</head>
<body>
<div class="title_Infor">
    <a href="${ctx}/login" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>已办事项</h3>
</div>


<div class="span6">
    <div class="widget-box wek_box">
        <c:forEach items="${fns:getdone()}" var="act">
            <div class="widget-content nopadding updates collapse in bor_N" style="padding: 20px !important; border-bottom: 1px solid #dbdbdb">

                <div class="new-update clearfix pad_T_no">

                    <strong> <a href="${ctx}/oa/flow/form?id=${act.id}&formNo=${act.taskDefKey}&showType=flowView&act.procInsId=${act.taskId}">${act.title}</a> </strong><br />
                    <strong>待审批/待抄送:${act.comment} </strong><br />

                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $('.new-update').each(function(i){
            $(this).on("click",function(){
                var url =   $('.new-update').eq(i).find("a").attr("href");
//                alert(i)
                window.location.href=url;

            });
        });
    });

</script>
</body>
</html>