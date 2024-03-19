<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>邮箱联系人</title>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>

    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
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

            $("#checkall").click(
                    function(e){
                        e.stopPropagation();
                        if(this.checked){
                            $("input[name='checkbox']").each(function(){this.checked=true;});
                        }else{
                            $("input[name='checkbox']").each(function(){this.checked=false;});
                        }
                    }
            );

        });


        //彻底删除
        function deleteBy() {
            var checked = false;
            var ids = document.getElementsByName("checkbox");
            var chestr = "";
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }
            chestr=chestr.substring(0,chestr.length-1)
            if (!checked) {
                alert("未选中任何联系人")
                return;
            }

            $(".checkbox").attr("checked",false);

            window.location.href = "${ctx}/oa/mailInfo/phoneWrite?ids=" + chestr;
        }
        //循环定时删除
        window.setInterval(show, 5000);
        function show() {
            document.getElementById("ss").innerHTML = "";
        }




        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            form1.action = '${ctx}/oa/mailInfo/phone';
            $("#form1").submit();
            return false;
        }

    </script>

</head>

<body>


<!--tou-->
<div class="title_Infor">
    <a href="${ctx}/sys/menu/tree?parentId=c8e2ec7ac7e6486abb8998c0dfb85669" class="pull-left"><img src="${ctxStatic}/oaApp/img/liucheng_Icon/fanhui.png" />返回</a>
        <h3>邮箱联系人</h3>
    <a class="pull-right title_R" style="font-weight: bold;color:#FFFFFF;" onclick="deleteBy()">写信</a>
</div>
    <div id="ss"></div>
<!---->
<!---->
<div id="search_1">
    <input type="text" id="phone" placeholder="Search here..." style="color: #000000" onchange="findPhone()"/>
    <button type="submit" class="tip-bottom" title="Search" onclick="findPhone1()"><i style="" class="icon-search icon-white"></i></button>
</div>
<!--功能区-->
<div class="span6">
    <c:forEach items="${offices}" var="office" varStatus="idxStatus">
        <div class="widget-box bor_N" id="div${office.id}">
            <div class="widget-title bg_lo bgC_White">
                <input onclick="findPeople('${office.id}')" name="checkbox" class="checkbox" type="checkbox" style="display: inline-block;position: absolute;top: 15px;z-index: 333">
                <a class="OA_a" data-toggle="collapse" href="#collapseG${office.id}" onclick="findPeople_2('${office.id}')" >
                    <h5 class="ML" style="padding-top: 3px">${office.name}</h5>
                    <div class="pull-right mysely2"><span></span></div>
                </a>
            </div>
        </div>
    </c:forEach>
</div>


<script type="text/javascript">

    function findPhone1(){
        if($("#phone").val() == ""){
            location.reload()
        }else{
            $.ajax({
                type: 'POST',
                contentType: "application/json",
                url: '${ctx}/sys/user/findUserPhone?name='+$("#phone").val(),
                dataType: 'json',
                success: function (d) {
                    $(".span6").html('');
                    var inHtml = "<div class='widget-content nopadding updates in collapse' id='collapseG'> ";
                    for(var i = 0 ;i< d.length;i++){
                        inHtml += "<div class='new-update clearfix' style=' position:relative'> " +
                        "<input  name='checkbox' class='checkbox' value='"+d[i].id+"' type='checkbox' style='display: inline-block;position: absolute;top: 7px;'>" +
                        "<div class='update-done' style='margin-left: 25px'><strong> "+d[i].name+"</strong>  </div> " +
                        "</div> ";
                    }
                    inHtml += "</div>";
                    $(".span6").append(inHtml);
                }
            });
        }
    }
//    点击复选框全选按钮
    function findPeople(id) {
        if(document.getElementById("collapseG"+id)!='null'&&$("#collapseG"+id).html()!=""&&$("#collapseG"+id).html()!=null){
            return;
        }
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '${ctx}/sys/user/findPhone?id=' + id,
            dataType: 'json',
//            cache:false,
            async:false,
            success: function (d) {

                $("#collapseG"+id).remove();
                var inHtml = "<div class='widget-content nopadding updates in collapse' id='collapseG"+id+"'>";
                for(var i = 0 ;i< d.length;i++){
                    console.log(d[i]);
                    inHtml +=
                    "<div class='new-update clearfix Pos_R'> <input class='checkbox_1' type='checkbox' name='checkbox' value="+d[i].id+">"+
                    "<div class='update-done ML_3'><strong> "+d[i].name+"（<span class='disPlay_Ib'>"+d[i].mobile+"</span>）</strong></div> " +
                    "<div class='update-date'></div> " +
                    "</div> ";

                }
                inHtml += "</div>";
                $("#div"+id).append(inHtml);

            }
        });

    }
//下面是点击展开加载数据
    function findPeople_2(id) {
        if(document.getElementById("collapseG"+id)!='null'&&$("#collapseG"+id).html()!=""&&$("#collapseG"+id).html()!=null){
            return;
        }
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '${ctx}/sys/user/findPhone?id=' + id,
            dataType: 'json',
//            cache:false,
//            async:false,
            success: function (d) {
                $("#collapseG"+id).remove();
                var inHtml = "<div class='widget-content nopadding updates in collapse' id='collapseG"+id+"'>";
                for(var i = 0 ;i< d.length;i++){
                    console.log(d[i]);
                    inHtml +=
                            "<div class='new-update clearfix Pos_R'> <input class='checkbox_1' type='checkbox' name='checkbox' value="+d[i].id+">"+
                            "<div class='update-done ML_3'><strong> "+d[i].name+"（<span class='disPlay_Ib'>"+d[i].mobile+"</span>）</strong></div> " +
                            "<div class='update-date'></div> " +
                            "</div> ";

                }
                inHtml += "</div>";
                $("#div"+id).append(inHtml);

            }
        });

    }
    $(".checkbox").each(function(i){
        $(this).on("click",function(e){

            e.preventDefault;
            if($(this).attr("checked"))
            {
                $(this).parents(".widget-box").find(".checkbox_1").attr("checked",true);


            }else
            {
                $(this).parents(".widget-box").find(".checkbox_1").attr("checked",false);


            }

        });

    });






//    function goPage(newURL) {
//        if (newURL != "") {
//            if (newURL == "-") {
//                resetMenu();
//            }
//            else {
//                document.location.href = newURL;
//            }
//        }
//    }
//
//    function resetMenu() {
//        document.gomenu.selector.selectedIndex = 2;
//    }
</script>

</body>
</html>
