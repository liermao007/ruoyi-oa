<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>保存成功</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
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
        });
    </script>
    <style>
        a:link{
            color: #008000;
            text-decoration:underline
        }
    </style>
    <style>
        #btnMenu{
            display: none;}
        .breadcrumb{ padding:25px 15px}
        .title_Infor{
            width: 100%;
            float: left;
            background-color: #33aafb;
            border-bottom: none;
            margin-top:0px !important;
            line-height: 30px;
            height: 37px;
        }
        .title_Infor>a{
            color: #fff;}
        .title_Infor img{
            height:18px;
        }
        .title_Infor h3{
            width: calc(100% - 100px);
            color: #fff;
            font-size: 18px;
            text-align: center;
            margin: 0;
            float: left;
        }
        #searchForm{
            float: left;}
        .form-horizontal .control-label {
            float: left;
            width: 65px;
            padding-top: 5px;
            text-align: right;
        }

        .controls{

        }
    </style>
</head>
<body>

<!--tou-->
<div class="title_Infor" style="margin-top: 0px;margin-bottom: 20px">
    <a href="${ctx}/sys/menu/tree?parentId=c8e2ec7ac7e6486abb8998c0dfb85669" class="pull-left" style="margin: 4px"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
    <h3>保存成功</h3>

</div>


<div style="background-color: #ffffff">
    <table style="margin-left: 60px;margin-top: 40px" width="400px">
        <tr>
            <td style="width: 53px;height: 45px" rowspan="4" valign="top">
                <img src="${ctxStatic}/tree/css/mailCss/img/mail90.png" style="width: 53px;height: 45px"></td>
            <td style="font-size: 16px; color: #008000;font-weight: 500;height: 30px">您的邮件已保存到草稿箱</td>
        </tr>

        <tr>
            <td style="height: 50px">
                <input type="button" class="btn btn-primary" value="再写一封"  onclick="location='${ctx}/oa/mailInfo/none'" >
            </td>
        </tr>

    </table>
</div>
</body>
</html>