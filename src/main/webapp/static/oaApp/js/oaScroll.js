/**
 * Created by douting on 2017/9/7.
 */

function touchScroll(pageNo,pageSize,time,url,div,noDiv){
        if(time < pageNo){
            $(noDiv).css("display","block");
        return;
    }
    else{
            $(noDiv).css("display","none");
        }
    $(".touchRun").css("display","block");
    setTimeout(function(){
        clearTimeout();
        $(".touchRun").css("display","none");
        getData(pageNo,pageSize,url,div);
    }, 2000);
        //var start_x, start_y, end_x, end_y, move_num;
        //var client_height = $(window).height();
        //$(document).on("touchstart", function(e) {
        //    start_y = e.originalEvent.targetTouches[0].clientY;
        //});
        //$(document).on("touchmove", function(e) {
        //    $(this).removeClass("slow_action");
        //    end_y = e.originalEvent.targetTouches[0].clientY;
        //    move_num = (end_y - start_y).toFixed(2);
        //    if(move_num<0){
        //        if (move_num < -(client_height / 2)) {
        //            $(".span6").css({
        //                "-webkit-transform": "translateY(" + move_num/2 + "px)"
        //            });
        //        }else {
        //            $(".span6").css({
        //                "-webkit-transform": "translateY(" + move_num/2 + "px)",
        //                "-webkit-transtion": "2s all ease"
        //            });
        //        }
        //    }
        //});
        //$(document).on("touchend", function(e) {
        //    var endHeight=0;
        //    if(move_num<0){
        //        if (move_num < -(client_height / 2)) {
        //            $(".span6").css({
        //                "-webkit-transform": "translateY(" + endHeight + "px)"
        //            });
        //        }else {
        //            $(".span6").css({
        //                "-webkit-transform": "translateY(" + endHeight + "px)",
        //                "-webkit-transtion": "2s all ease"
        //            });
        //        }
        //        $(".touchRun").css("display","block");
        //        setTimeout(function(){
        //            clearTimeout();
        //            $(".touchRun").css("display","none");
        //            getData(pageNo,pageSize,url,div);
        //        }, 2000);
        //
        //        $(".span6").css({
        //            "-webkit-transform": "translateY(" + endHeight+ "px)",
        //            "-webkit-transtion": "2s all ease"
        //        });
        //    }
        //    e.stopPropagation();
        //});
}
/* 加载数据*/
function getData(pageNo,pageSize,url,div){
    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: url,
        dataType: 'json',
        success: function(data){
            var result=[];
            console.log(data);
            //不同模块功能对应不同的标志flag
            if(data.length>0&&data[0].flag=="mail"){
                for(var i=0;i<data.length;i++){
                    result.push("<div class='widget-box bor_N'>");
                    result.push("<div class='widget-content nopadding updates'>");
                    result.push("<div class='new-update clearfix Pos_R'><input class='checkbox_1'  name ='checkbox'  value='"+data[i].id+"' type='checkbox' >");
                    result.push("<div class='update-done ML_3 Inbox_Width_80' onclick=sk('"+data[i].id+"')><strong>"+data[i].name+"</strong><span class='Inbox_Con'>"+data[i].theme+"</span></div>");
                    result.push("<div class='update-date Inbox_Width_10'>"+data[i].dataTime+"</div>" );
                    result.push("<div class='update-date Inbox_Width_10'>" );
                    if(data[0].readMark=="1"&&(data[0].state=="INBOX"||data[0].state=="DELETED"||data[0].state=="SENT")){
                        result.push("<img src='/static/tree/css/mailCss/img/mail020.png'/>" );
                    }
                    else if(data[0].readMark=="0"&&(data[0].state=="INBOX"||data[0].state=="DELETED"||data[0].state=="SENT")){
                        result.push("<img src='/static/tree/css/mailCss/img/mail010.png'/>" );
                    }
                    result.push("</div></div></div></div>");}}
            else if(data.length>0&&data[0].flag=="notice"){
                for(var i=0;i<data.length;i++){
                result.push("<div class='widget-content nopadding updates collapse in bor_N'>");
                result.push("<div class='new-update clearfix'>");
                result.push("<div class='update-done'><a title='' href='#'><strong class='fontS_16'>" + data[i].createManName + "</strong></a>");
                result.push("<span>"+data[i].auditManName+"</span></div>");
                result.push("<div class='update-date'><span class='update-day' style='font-size: 12px'>"+data[i].dataTime+"</span>新闻公告</div></div>");
                result.push("<div class='new-update clearfix pad_T_no'>");
                result.push("<a href='/a/oa/oaNews/getMyAuditNews?id="+data[i].id+"'>");
                result.push("<span>状态："+data[i].auditFlag+"</span></div></div>");
                result.push("<h4>"+data[i].title+"</h4></a>");
                result.push("<span class='fontS_14'>"+data[i].content+"</span>");
            }}
            else if(data.length>0&&data[0].flag=="news"){
                for(var i=0;i<data.length;i++){
                result.push("<div class='widget-content nopadding updates collapse in bor_N'>");
                    if(data[i].url==""||data[i].url==null){
                        result.push("<div class='new-update clearfix'><i><img class='img-circle img-polaroid' src='/static/oaApp/img/photoDefault.jpg'> </i>");
                    }
                    else{
                        result.push("<div class='new-update clearfix'><i><img class='img-circle img-polaroid' src='"+data[i].url+"'> </i>");
                    }
                result.push("<div class='update-done'><a title='' href='#'><strong class='fontS_16'>"+data[i].createManName+"</strong></a>");
                result.push("<span>"+data[i].auditManName+"</span></div>");
                result.push("<div class='update-date'><span class='update-day' style='font-size: 12px'>"+data[i].dataTime+"</span>新闻公告</div></div>");
                result.push("<div class='new-update clearfix pad_T_no'>");
                result.push("<a href='/a/oa/oaNews/getMyAuditNews?id="+data[i].id+"'><h4>"+data[i].title+"</h4></a>");
                result.push("<span class='fontS_14'>"+data[i].content+"</span>");
                result.push("<i class='icon-eye-open eys'>&nbsp;&nbsp;</i></a></div></div>");
            }}
            else if(data.length>0&&data[0].flag=="flow"){
                for(var i=0;i<data.length;i++){
                    result.push("<div class='widget-box bor_N'><div class='widget-content nopadding updates'>");
                    result.push("<div class='new-update clearfix Pos_R'>");
                    result.push("<div class='update-done ML_3 Inbox_Width_80' style='width:50% !important;min-width:50% !important;'><strong>"+data[i].dept+"</strong><span class='Inbox_Con'>"+data[i].SQKS+"</span></div>");
                    result.push("<div class='update-date Inbox_Width_10' style='width:40% !important;'>"+data[i].name+"</div>");
                    result.push("<div class='update-date Inbox_Width_10' style='width:40% !important;'>");
                    result.push("<a href='/a/oa/flow/form?id="+data[i].id+"&formNo="+data[i].formNo+"&showType=flowView&act.procInsId="+data[i].procInsId+"'>详情</a>");
                    result.push("<a href='/a/oa/flow/deleteInfo?tableName="+data[i].tableName+"&id="+data[i].id+"&procInsId="+data[i].procInsId+" onclick='return confirm('确认要删除该流程数据吗？')'>删除</a></div>");
                    result.push("</div></div></div>");
                }
            }
            else if(data.length>0&&data[0].flag=="act"){
                for(var i=0;i<data.length;i++){
                result.push("<div class='widget-content nopadding updates collapse in bor_N'>");
                    if(data[i].url==""||data[i].url==null){
                        result.push("<div class='new-update clearfix'><i><img class='img-circle img-polaroid' src='/static/oaApp/img/photoDefault.jpg'> </i>");
                    }
                    else{
                        result.push("<div class='new-update clearfix'><i><img class='img-circle img-polaroid' src='"+data[i].url+"'> </i>");
                    }
                result.push("<div class='update-done'><a title='' href='#'><strong class='fontS_16'>"+data[i].title+"</strong></a><span></span></div>");
                result.push("<div class='update-date'><span class='update-day' style='font-size: 12px'>"+data[i].endTime+"</span>已办流程</div></div>");
                result.push("<div class='new-update clearfix pad_T_no'>");
                result.push("<a href='/a/act/task/form?taskId="+data[i].id+"&taskName="+data[i].urlEncode+"&taskDefKey="+data[i].taskDefinitionKey+"&procInsId="+data[i].processInstanceId+"&procDefId="+data[i].processDefinitionId+"&status="+data[i].status+"'>");
                result.push("<h4>"+data[i].procDefName+"</h4><span class='fontS_14'></span>");
                result.push("<i class='icon-eye-open eys'>&nbsp;&nbsp;28</i></a></a></div></div>");
            }}
            result=result.join("");
            $(div).append(result);
        },
        error: function(){
            //confirm('连接错误，请联系管理员!');
        }
    });
}

