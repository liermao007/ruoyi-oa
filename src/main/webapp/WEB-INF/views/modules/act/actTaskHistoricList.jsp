<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>已办任务</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style type="text/css">
        a:hover{
            color: red;
            text-decoration: none;
        }
        .liStyle{
            float: left;
            width: 25%;
            border: 1px solid #dbdbdb;
            box-sizing:
            border-box;padding: 5px 0px;
        }
        .daiban_Con11{
            /*position: fixed;*/
            top:0;
            left:0;
            height: 100%;
            width:100%;
        }
        .daiban_Con11>div{
            width: 100%;
            -webkit-transform: translate(-50%,-50%);
            -moz-transform: translate(-50%,-50%);
            -ms-transform: translate(-50%,-50%);
            -o-transform: translate(-50%,-50%);
            transform: translate(-50%,-50%);
            position: absolute;
            top:45%;
            left:50%;
            text-align: center;
        }
        .daiban_Con11>div img{
            width: 10%;
        }
        .daiban_Con11>div p{
            margin-top:20px;
            font-size: 1.2em;
            color:#666;
        }
    </style>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<sys:message content="${message}"/>
<div>
<ul style="list-style: none; width: 90%;text-align: center; float: none;">
    <li class="liStyle" >
        <a style="font-size: 16px; color: #ff0000"  href="${ctx}/act/task/toDoTasks">
            最近已办任务
        </a>
    </li><br /><br />
    <c:forEach items="${page1.list}" var="oaRunProcess">
        <c:if test="${principalid == null}">
            <li class="liStyle">
                <a style="font-size: 16px;" href="${ctx}/act/task/historicList3?principalid=${oaRunProcess.principalid}">
                    被代理人:${fns:getNameByID(oaRunProcess.principalid)}
                </a>
            </li>
        </c:if>
    </c:forEach>
    <c:forEach items="${page.list}" var="oaPersonDefineTable">
        <li class="liStyle" >
            <a style="font-size: 16px;" href="${ctx}/act/task/myHistoricList?procDefId=${oaPersonDefineTable.id}&principalid=${principalid}">
                ${oaPersonDefineTable.tableComment}
            </a>
        </li>
    </c:forEach>
    <c:if test="${empty principalid}">
        <c:if test="${fns:findCustomHiTaskinst().size()>0}">
            <li class="liStyle">
                <a style="font-size: 16px;" href="${ctx}/process/customProcess/historyList">
                    自定义流程
                </a>
            </li>
        </c:if>
    </c:if>
    <c:if test="${empty page.list}">
        <c:if test="${empty page1.list}">
            <div class="daiban_Con11">
                <div>
                    <img src="${ctxStatic}/oaApp/img/daibanliucheng.png"/>
                    <span><font size="5" >您暂时还没有已办流程，休息一下吧！</font></span>
                </div>
            </div>
        </c:if>
    </c:if>
</ul>
</div>
<div style="float:none;">
<ul style="list-style: none; width: 90%;text-align: center;">
    <c:forEach items="${page2.list}" var="oaPersonDefineTable">
        <li class="liStyle">
            <a style="font-size: 16px;" href="${ctx}/act/task/myHistoricList?procDefId=${oaPersonDefineTable.id}&principalid=${oaPersonDefineTable.updateType}">
                    ${oaPersonDefineTable.tableComment}
            </a>
        </li>
    </c:forEach>
</ul>
</div>
</body>

</html>