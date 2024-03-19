<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>我发起的流程</title>
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
        #liStyle{
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
<script>
    function lineBy(procDefId){
        $.post('${ctx}/oa/flow/myFlow?formNo=${formNo}&procDefId='+procDefId+'&procDefIdFather=${procDefIdFather}&type=1&data=${data}', {}, function(str){
            layer.open({
                type: 1,
                area: ['1000px', '550px'],
                content: str
            });
        });
    }
</script>
<sys:message content="${message}"/>
<ul style="list-style: none; width: 90%;text-align: center">
    <c:forEach items="${page.list}" var="oaPersonDefineTable">
        <li id="liStyle" >
            <a style="font-size: 16px;" onclick="lineBy('${oaPersonDefineTable.procDefId}')">
                ${oaPersonDefineTable.tableName}
            </a>
        </li>
    </c:forEach>
    <c:if test="${empty page.list}">
        <div class="daiban_Con11">
            <div>
                <img src="${ctxStatic}/oaApp/img/daibanliucheng.png"/>
                <span><font size="5" >您暂时还没有流程，休息一下吧！</font></span>
            </div>
        </div>
    </c:if>
</ul>
</body>

</html>