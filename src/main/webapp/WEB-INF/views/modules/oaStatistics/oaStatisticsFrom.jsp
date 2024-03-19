<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/8
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>Title</title>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
    <style>
        .line li{
            float: left;
            list-style:none;
            width:30%;
            height: 50px;
            line-height: 50px;
            border:1px solid #ccc;
            text-align :center;
            padding-bottom:auto;
            background-color: ghostwhite
        }
        .lineForEach li{
            float: left;
            list-style:none;
            width:30%;
            height: 50px;
            line-height: 50px;
            border-left:1px solid #ccc;
            border-right:1px solid #ccc;
            border-bottom:1px solid #ccc;
            text-align :center;
            padding-bottom:auto;
            background-color: ghostwhite
        }
        .line1 li{
            float: left;
            list-style:none;
            width:90.35%;
            height: 50px;
            line-height: 50px;
            border-left:1px solid #ccc;
            border-right:1px solid #ccc;
            border-top:1px solid #ccc;
            text-align :center;
            padding-bottom:auto;
            background-color: ghostwhite
        }
        a{text-decoration:none;}
        li a{

        }
        .add{
            font-size: 12px;
            margin-left: 870px;
        }
    </style>
</head>
<body>
<ul>
    <li  style="display:block;">
        <ul class="line1">
            <li>标题</li>
            <li><div>新增</div><div>名表对应</div></li>
        </ul>
    </li>

    <li style="display:block;">
        <ul class="line">
            <li>统计表名称</li>
            <li>操作</li>
        </ul>
    </li>
    <li style="display:block;">
        <c:forEach items="[1,2,3]" var="a" varStatus="i">
            <ul class="lineForEach">
                <li id="MC${i.index}">深圳请假清单${i.count}</li>
                <li><a href="/a/OaStatistics/oaStatistics/field">详情</a>&nbsp;|&nbsp;<a href="#">修改</a>&nbsp;|&nbsp;<a href="#"><i onclick="deleteTable('1',${i.index})">删除</i></a></li>
            </ul>
        </c:forEach>
    </li>
</ul>
</body>
</html>
