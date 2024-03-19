<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="${ctxStatic}/auto-complete/bootstrap.autocomplete.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>
    <style>
        ul{list-style: none;}
        #auditFlag{
            display: block !important; width: 100%}
        #s2id_auditFlag{
            display: none;}
        label,input{ width: 100% !important;
            float: left; }
        input{
            height: 30px !important;}
        #btnSubmit{ margin-top: 20px;}
        .input-medium{display: block !important;}
        .select2-input{display: none !important;}
        label{    margin: 0;
            width: 100px;
            display: block;
            margin-bottom: 5px;
            line-height: 36px !important;}
        ul{ list-style: none; margin:0;padding: 0;}
        ul>li{width: 100%;
            float: left;}
        .select2-container{display: none !important;}

        #jbox-content{
            width: 300px;
        }

        select, input[type="file"] {
            width: 80%;
            height: 30px;
            line-height: 24px;
        }
        #cke_top_content{
            display: none;
        }
        #cke_contents_content{
            height: 200px !important;
        }
        #btnMenu{display:none}
        .autoComplete{position: absolute;width: 230px !important;}
        .autoComplete>table>tbody>tr>td:nth-of-type(1){
            display: none;
        }
        .autoComplete>table>thead>tr>th:nth-of-type(1){
            display: none;
        }
    </style>
    <script type="text/javascript">

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
                        //   alert("ok");
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



    </script>
</head>
<body>
<div class="title_Infor">
    <a href="${ctx}/act/task/historicList" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png"/>返回</a>
    <h3>最近已办任务</h3>
</div>
<c:set var="user" value="${fns:getUser()}"/>

<dd id="loginName" style="display: none"> ${user.name}</dd>
<dd id="userId" style="display: none"> ${user.userId}</dd>
<form:form id="searchForm"  modelAttribute="act" action="${ctx}/act/task/toDoTasks" method="post" class="form-horizontal" >
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label style="text-align: left;float: left; width: 23% !important;margin-top: 6px;">表单名称：&nbsp;</label>
            <form:select path="procDefId" maxlength="20" cssStyle="width: 77%; margin-top: 8px;" class="input-medium">
                <form:option value="" label="请选择"/>
                <form:options items="${processList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label style="text-align: left;float: left; width: 23% !important;margin-top: -3px;">申 请 人：</label>
            <input id="applyUserId" name="applyUserId" type="hidden" value="${act.applyUserId}"/>
            <form:input path="CS" id="CS" name="CS" class="span12 required" cssStyle="width: 77% !important;"  onclick="return MyFun();" autocomplete="off" placeholder="输入姓名查询"/>
        </li>
        <li><input id="btnSubmit" class="btn btn-primary" style="margin-top: 6px;" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div class="span6">
    <div class="widget-box wek_box">
        <c:forEach items="${page.list}" var="act">
            <div class="widget-content nopadding updates collapse in bor_N" style="padding: 20px !important; border-bottom: 1px solid #dbdbdb">

                <div class="new-update clearfix pad_T_no">

                    <strong> <a href="${ctx}/oa/flow/form?id=${act.id}&formNo=${act.taskDefKey}&showType=flowView&act.procInsId=${act.taskId}">${act.taskName}</a> </strong><br />
                    <strong>${act.CS} </strong><br />
                    <strong>${act.startTime} </strong>
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