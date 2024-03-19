<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
    <meta name="decorator" content="footer"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <link rel="stylesheet" href="/static/oaApp/css/mail.css" />
</head>
<body style="margin-top: -5px;">
<div class="title_Infor">
    <a href="/a/oa/flow/flowList" class="pull-left"><img src="/static/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3 align="left">自由流程</h3>
</div>
<div class="span6">
    <label id="message" style="color:red; font-weight:bold;">${message}</label>

    <c:forEach items="${fns:findCustomProcess()}" var="customProcess">
        <div class='widget-box bor_N'><div class='widget-content nopadding updates'>
            <div class='new-update clearfix Pos_R'>
                <div class='update-done ML_3 Inbox_Width_80' style="width:100% !important;min-width:100% !important;">
                    <span style="display: inline-block;">
                        姓名：${customProcess.name}<br>
                        部门：${customProcess.deptId}<br>
                        标题：${customProcess.title}<br>
                        标识：${customProcess.recall}<br>
                    </span>
                    <span class='Inbox_Con'></span>
                </div>
                <div class='update-date Inbox_Width_10' style="width:100% !important;text-align: right;">
                    <a style="padding: 5px 10px;border: 1px solid white;" onclick="detailsByProcInstId('${customProcess.procInstId}')">详情</a>
                    <a style="padding: 5px 10px;border: 1px solid white;"  onclick="deleteByProcInstId('${customProcess.procInstId}')">撤回</a></div>
            </div>
        </div>
        </div>
    </c:forEach>
    </table>
</div>
<img class="touchRun" style="position:absolute; bottom:0px;width: 400px;height: 200px;display: none;z-index: 999" src="/static/oaApp/img/loadTouch.gif"/>
<script>


    function detailsByProcInstId(procInstId){
        window.location="/a/process/customProcess/list?procInstId="+procInstId +"&type=flowView";
    }
    function deleteByProcInstId(procInstId){
        if(confirm('确认要撤回该自由流程数据吗？') == true){
            window.location="/a/process/customProcess/deleteCustomProcess?procInstId="+procInstId ;
        }
    }
</script>
</body>
</html>