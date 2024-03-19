<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日总结评阅</title>
    <meta name="decorator" content="footer"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport"/>
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
            $("#inputForm").submit();
        	return false;
        }

        function pickedFunc()
        {
            $("#searchForm").submit();
        }
	</script>
    <script>
        $(function(){
            $(".info_Btn").each(function(i){
                var a=0;
                $(this).on("click",function(){
                    a++;
                    if(a%2!=0){
                        $(".Py_con").eq(i).css("display","block");
                    }else{
                        $(".Py_con").eq(i).css("display","none");
                        a=0;
                    }

                });
//                $(".save_Btn").eq(i).on("click",function(){
//                    $(".Py_con").eq(i).css("display","none");
//                })
            })

        })
    </script>
    <style>
        #btnMenu{
            display: none;}

        #searchForm{
            float: left;}


        body{
            padding: 0 15px;
        }
        .container{
            width:100%;
            overflow: hidden;
            height: auto;
            background: #fff;

        }
        div{
            width:100%; height: auto;
            padding: 10px 0;
        }

        .container_B p{
            line-height: 40px;
        }
        body{
            font-size: 12px !important;
        }
        .info_Btn{
            margin-right:5px;
        }
        .Py_con{
            display: none;
            margin-top: 20px;
        }
        .TxtAr{
            width: 99%;
            margin-top: 20px;
            border: 1px solid ;
            border-color: dodgerblue;
            resize: none;

        }
        .save_Btn{
            width: 100%;
            height: 30px;
            background: dodgerblue;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;display: inline-block;
            margin-top: 20px;
            text-align: center;
            line-height: 40px;
            color:#fff;
        }

        #inputForm{

            margin-bottom: 0;
        }
    </style>
</head>
<body>
<div class="title_Infor">
    <a href="${ctx}/sys/menu/tree?parentId=23b5232adcad43b1a9fb89f02047a756" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>日总结评阅</h3>
    <a style="margin-top: -2px;margin-right: 10px;" href="${ctx}/oa/oaSummaryDay" class="pull-right title_R marT_12"><img src="${ctxStatic}/img/bianjii.png"></a>
</div>
    <form:form id="searchForm" cssStyle="padding: 10px;margin: 0px;width: 100%" modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/loginId?ids=${ids}" method="post"  class="breadcrumb form-search">
       <div style="padding-left: 20px;width: 100%">
            <label style="font-size: 12px; width: 25%">评阅日期：</label>
                <input name="sumDate" id="sumDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " style="width:40%;"
                       value="<fmt:formatDate value="${oaSummaryDay.sumDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:pickedFunc});"/>

       </div>
    </form:form>
	<sys:message content="${message}"/>

    <div class="span6">
        <c:forEach items="${page.list}" var="oaSummaryDays">
            <div class="widget-content nopadding updates collapse in bor_N">
                <div class="new-update clearfix" style="border-bottom: 1px solid #dbdbdb;">
                   <p>姓名：${oaSummaryDays.evaluateByNames}(${oaSummaryDays.officeName})</p>
                    <p>工作总结：${oaSummaryDays.content}</p>
                    <p>评语：${oaSummaryDays.evaluate}</p>
                    <shiro:hasPermission name="oa:personSigns:edit">
                        <form:form id="inputForm" modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/saveEvel?flag=1&appraiseDate=${sumDate}" method="post" class="form-horizontal" cssStyle="text-align: end;">
                            <c:choose>
                                <c:when  test="${type eq 1}">
                                    <shiro:hasPermission name="oa:oaSummaryDay:edit">
                                        <a class="save_Py_Btn" href="${ctx}/oa/oaSummaryDay/loginId?loginId=${oaSummaryDays.loginId}" align="right">
                                            下级职员
                                        </a>
                                    </shiro:hasPermission>
                                </c:when>
                                <c:otherwise>
                                    <a class="info_Btn" align="right" style="text-decoration:underline">我要评阅</a>
                                    <shiro:hasPermission name="oa:oaSummaryDay:edit">
                                        <a class="save_Py_Btn" href="${ctx}/oa/oaSummaryDay/loginId?loginId=${oaSummaryDays.loginId}" align="right">
                                            下级职员
                                        </a>
                                    </shiro:hasPermission>
                                </c:otherwise>
                            </c:choose>
                        </form:form>
                    </shiro:hasPermission>
                    <form:form id="inputForm${oaSummaryDays.loginId}"  modelAttribute="oaSummaryDay" action="${ctx}/oa/oaSummaryDay/saveEvel?flag=1&appraiseDate=${sumDate}" method="post" cssStyle="margin: 0">
                        <shiro:hasPermission name="oa:oaSummaryDay:edit">
                            <p class="Py_con" style="margin-top: 0px;">
                                <textarea name="evaluateContent" id="evaluateCon${oaSummaryDays.loginId}" style="width:84%;margin: 0;float: left;height: 40px;"></textarea>
                                <input class="save_Btn"  id="btnSubmit${oaSummaryDays.loginId}"  type="button" value="确 定" onclick="btnSubmit('${oaSummaryDays.loginId}')" style="width: 16%;height: 40px;margin-top: 0px"/>
                                <input class="TxtAr" type="hidden" name="loginId" id="loginId${oaSummaryDays.loginId}" value="${oaSummaryDays.loginId}" />
                            </p>
                        </shiro:hasPermission>
                    </form:form>
                </div>
            </div>
        </c:forEach>
    </div>



<script>
        function btnSubmit(flag){
            var evaluateCon=$("#evaluateCon"+flag).val();
            var loginId=$("#loginId"+flag).val();
            if(evaluateCon==""){
                return false;
            }
            if(loginId==""){
                return false;
            }
            $("#inputForm"+flag).submit();
        }


</script>
</body>

</html>