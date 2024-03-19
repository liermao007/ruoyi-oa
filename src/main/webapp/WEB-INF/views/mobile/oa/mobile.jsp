<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>智慧岗位工作台</title>
    <meta charset="UTF-8"/>
    <meta name="decorator" content="footer"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>

<style>
    body{
        background: none;
    }
    .span_cicle{font-size: 12px; }
    .myRGB1{ background-color:#ffd267 ; }
    .myRGB2{ background-color:#78aeff ; }
    .myRGB3{ background-color:#aee773 ; }
    .myRGB4{ background-color:#ffb16b ; }
    .myRGB5{ background-color:#aca2fc ; }
    .myRGB6{ background-color:#ff6243 ; }
    .myRGB7{ background-color:#168ee9 ; }
    .myRGB8{ background-color:#ffb416 ; }
    .myRGB9{ background-color:#00aaef ; }
    .myRGB0{ background-color:#29ab91 ; }
</style>
</head>
<body >
<!--tou-->
<div id="header" style="text-align: center">
    <a href="javascript:;" class="pull-left" style="padding-top: 10px; color: #fff; position: absolute;left: 10px;" onclick="history.go(-1)"><img src="/static/oaApp/img/liucheng_Icon/fanhui.png">返回</a>
    <h4 style="padding-top:10px;color:white;" > ${fns:abbr(companyName,25)}</h4>
</div>

<!---->
<c:set value="${parentId}" var="companyId"></c:set>
<c:set value="${companyName}" var="companyName"></c:set>
<div id="search_1">
    <input type="text" id="phone" placeholder="Search here..." style="color: #000000" onchange="findPhone('${companyId}')"/>
    <button type="submit" class="tip-bottom" title="Search" onclick="findPhone('${companyId}')"><i style="" class="icon-search icon-white"></i></button>
</div>

<!--功能区-->
<div class="span6">
    <c:set value="0" var="rgbSuiJi"></c:set>
    <c:forEach items="${users}" var="user" varStatus="idxStatus">
        <c:set value="${rgbSuiJi+1}" var="rgbSuiJi"></c:set>
        <div class="widget-box bor_N marT_0 " id="div${office.id}">
            <div class="widget-title bg_lo bgC_White" style="height: 60px;">
                <a class="OA_a" data-toggle="collapse" href="#collapseG${office.id}" style="border-bottom: 1px solid #f5f5f5;    padding-top: 5px;" >
                    <%--<c:if test="${empty user.loginIp}">--%>
                        <span class="span_cicle myRGB${(rgbSuiJi+1)%10}">${user.name}</span>
                    <%--</c:if>--%>
                 <%--   <c:if test="${not empty user.loginIp}">
                        <span class="span_cicle"><img src="${user.loginIp}" /></span>
                    </c:if>--%>
                    <h5 style="width: 50%; line-height: 30px;">${user.name}</h5>
                    <p class="person_Pn">${user.phone}</p>
                    <a href="tel:${user.phone}" class="Img_Ddh"><img  src='${ctxStatic}/oaApp/img/phone.png'></a>
                        <a href="sms:${user.phone}" class="Img_Fdx"> <img  src='${ctxStatic}/oaApp/img/login_email.png'></a>
                </a>
            </div>
        </div>
    </c:forEach>
</div>

<script type="text/javascript">
    function findPhone(companyId){
        var flag=$("#phone").val();
        if(flag != null){
                $.ajax({
                    type: 'POST',
                    contentType: "application/json",
                    url: '${ctx}/sys/user/findLikeMobile?parentId='+companyId+'&name='+$("#phone").val(),
                    dataType: 'json',
                    success: function (d) {
                        $(".span6").html('');
                         var inHtml = " ";
                        for(var i = 0 ;i< d.length;i++){
                            inHtml += "<div class='widget-box bor_N marT_0 ' id='div${office.id}'><div class='widget-title bg_lo bgC_White' style='height: 60px;'>" +
                            "   <a class='OA_a' data-toggle='collapse' href='#collapseG${office.id}' style='border-bottom: 1px solid #f5f5f5;    padding-top: 5px;' >" +
                            "  <span class='span_cicle myRGB"+(i+1)%10+"'>"+ d[i].name+
                            " </span><h5 style='width: 50%; line-height: 30px;'>"+d[i].name+"</h5>"+
                            " <p class='person_Pn'>"+((d[i].phone&&d[i].phone!=null && d[i].phone != '' && d[i].phone != 'undefined')?(d[i].phone):(""))+"</p>"+
                            "<a href='tel:"+d[i].phone+"' class='Img_Ddh'><img  src='${ctxStatic}/oaApp/img/phone.png'></a>"+
                            "<a href='sms:"+d[i].phone+"' class='Img_Fdx'><img  src='${ctxStatic}/oaApp/img/login_email.png'></a>"+
                            "</a></div> </div>";
                        }
                        $(".span6").append(inHtml);
                    }
                });
            }
    }
</script>

<%--<script type="text/javascript">

    function findPhone(){
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
                        inHtml += "<div class='new-update clearfix'><i class='icon-ok-sign marT_5'></i> " +
                            "<div class='update-done'><strong> "+d[i].name+"（<span class='disPlay_Ib'>"+d[i].mobile+"</span>）</strong>"+
                            ((d[i].phone&&d[i].phone!=null && d[i].phone != '' && d[i].phone != 'undefined')?(" <span>"+d[i].phone +"</span> </div><div class='update-date'><a href='tel:"+d[i].phone+"'><img src='${ctxStatic}/oaApp/img/phone.png'></a>&nbsp;&nbsp;　<a href='sms:"+d[i].phone+"'><img src='${ctxStatic}/oaApp/img/login_email.png'></a></div> "):'</div>')
                            +"</div> ";
                    }
                    inHtml += "</div>";
                    $(".span6").append(inHtml);
                }
            });
        }
    }
    function findPeople(id) {
        if(document.getElementById("collapseG"+id)!='null'&&$("#collapseG"+id).html()!=""&&$("#collapseG"+id).html()!=null){
            return;
        }
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '${ctx}/sys/user/findPhone?id=' + id,
            dataType: 'json',
            success: function (d) {
                $("#collapseG"+id).remove();
                var inHtml = "<div class='widget-content nopadding updates in collapse' id='collapseG"+id+"'> ";
                for(var i = 0 ;i< d.length;i++){
                    inHtml += "<div class='new-update clearfix'><i class='icon-ok-sign marT_5'></i> " +
                    "<div class='update-done'><strong> "+d[i].name+"（<span class='disPlay_Ib'>"+d[i].mobile+"</span>）</strong>"+
                        ((d[i].phone&&d[i].phone!=null && d[i].phone != '' && d[i].phone != 'undefined')?(" <span>"+d[i].phone +"</span> </div><div class='update-date'><a href='tel:"+d[i].phone+"'><img src='${ctxStatic}/oaApp/img/phone.png'></a>&nbsp;&nbsp;　<a href='sms:"+d[i].phone+"'><img src='${ctxStatic}/oaApp/img/login_email.png'></a></div> "):'</div>')
                     +"</div> ";
                }
                inHtml += "</div>";
                $("#div"+id).append(inHtml);
            }
        });
    }
    function goPage(newURL) {
        if (newURL != "") {
            if (newURL == "-") {
                resetMenu();
            }
            else {
                document.location.href = newURL;
            }
        }
    }

    function resetMenu() {
        document.gomenu.selector.selectedIndex = 2;
    }
</script>--%>
</body>
</html>
