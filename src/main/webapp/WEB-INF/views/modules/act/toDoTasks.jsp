<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>已办任务</title>
    <meta name="decorator" content="default"/>
    <%--<script type="text/javascript" src="${ctxStatic}/oaApp/js/autocomplete.js"></script><!--amq消息中间件js-->--%>
    <%--<script type="text/javascript" src="${ctxStatic}/oaApp/css/autocomplete.css"></script>--%>
    <%--bootstrap自动补全--%>
    <script src="${ctxStatic}/auto-complete/bootstrap.autocomplete.js" type="text/javascript"></script>

    <style>
        #container {
            width: 800px;
            margin: 0 auto;
        }

        #search-box {
            position: relative;
            width: 400px;
            margin: 0 auto;
            display: inline;
        }

        .wrapper {
            width: 750px;
            margin: 0 auto;
        }
        .autocomplete-container{
            width: 240px!important;
        }
        .form-search .ul-form li .proposal{width:100%;}
        .form-search .ul-form li .proposal-list{
            overflow-y: auto;
            height: 108px;
            margin: 0;
            padding-left: 5px;
            display: none;
        }
        /*.autocomplete-container>.input-medium{*/
        /*width: 100px!important;*/
        /*background-color: #ffffff;*/
        /*}*/
        .form-search .ul-form li.twoLi{
            height:auto;
        }
        .form-search .ul-form li.twoLi>label{
            float: left;
        }
        .autoComplete{position: absolute;width: 285px!important;}
        .autoComplete>table>tbody>tr>td:nth-of-type(1){
            display: none;
        }
        .autoComplete>table>thead>tr>th:nth-of-type(1){
            display: none;
        }
    </style>
    <script type="text/javascript">
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function showimage() {
            var loginId= $("#no").val();
            if(loginId.length == 0){
                alert("您还没有已办任务")
            }else{
                document.all.searchForm.submit();
            }

        };
        function MyFun()
        {
//就差ajax请求用户名
            $(function(){
                //step 1：发送ajax请求并得到回调结果
                $.ajax({
                    type:'get',
                    url: "${ctx}/sys/user/toTaskFindListMobile",
                    dataType:'json',
                    data:{
                        "name":$("#loginName").val(),
                        "id":$("#userId").val(),
                        "user":""
                    },
                    error:function(data,status){alert("异常");},
                    success:function(data,status){
//                    alert("ok");
                        //定义一个不限长度的数组
                        //alert("数组")
                        var eqDictAll = [];//设备名称字典
                        //设备名称初始化
                        var eqDictPromise = $.get("/a/sys/user/toTaskFindListMobile", function (data) {
                            eqDictAll = data;
//                        alert(data.length);
                        });
                        var eqDictCols = [ {title: 'id', column: 'id', width: 20}, {
                            title: '姓名', column: 'name', width: 20
                        }];
                        eqDictPromise.done(function () {
                            makeDict("CS", eqDictAll, "name", eqDictCols, "id", "name", "applyUserId", "CS");
//            makeDict("equipName", eqDictAll, "inputCode", eqDictCols, "equipNo", "equipName", "equipNo", "equipName");
                            $("#name").watch(function (value) {
                                $("#id").val(null);
                                $("#name").val(null);
                                $.each(eqDictAll, function (index, item) {
                                    if (item.name == value) {
                                        $("#id").val(item.id);
                                        $("#name").val(item.name);

                                        return;
                                    }
                                });
                            })
                        });
                    }

                })

            })
        }

        //    var proposals = ['杨瑞东', '扬帆', '杨牛', '杨丽', '胡牛', '胡六', '呼气','呵呵呵呵呵呵呵','百度','新浪','a1','a2','a3','a4','b1','b2','b3','b4'];




    </script>
    <script type="text/javascript">

    </script>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<c:set var="user" value="${fns:getUser()}"/>

<dd id="loginName" style="display: none"> ${user.name}</dd>
<dd id="userId" style="display: none"> ${user.userId}</dd>
<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/task/toDoTasks"  method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>表单名称：</label>
            <form:select path="procDefId" maxlength="20" class="input-xlarge">
                <form:option value="" label="请选择"/>
                <form:options items="${processList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
            </form:select>
        </li>

        <li class="twoLi"><label style="margin-top: 8px;">申请人：</label>
            <input id="applyUserId" name="applyUserId" type="hidden" value="${act.applyUserId}"/>
            <form:input path="CS" id="CS" name="CS" class="span12 required" cssStyle="width: 270px"  onclick="return MyFun();" autocomplete="off" placeholder="输入姓名查询"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>表单名称</th>
        <th>申请人</th>
        <th>申请时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <c:forEach items="${page.list}" var="act">
        <tr class="a">
            <td>${act.taskName}</td>
            <td>${act.CS}</td>
            <td>${act.startTime}</td>
            <td> <a class="aa" href="${ctx}/oa/flow/form?id=${act.id}&formNo=${act.taskDefKey}&showType=flowView&act.procInsId=${act.taskId}">详情</a></td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>

<script type="text/javascript">

</script>
</body>
</html>
