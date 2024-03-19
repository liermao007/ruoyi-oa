/**
 * Created by Wzp on 2017/12/16.
 */
$(function(){;
    //$(".nav_btn>ul>li").on('click',function(){
    //    alert(0)
    //    var liUrl = $(this).find("a").attr("data-href");
    //    alert(liUrl);
    //});
    $('.nav-item>a').on('click',function(){
        if (!$('.nav').hasClass('nav-mini')) {
            if ($(this).next().css('display') == "none") {
                //展开未展开
                $('.nav-item').children('ul').slideUp(300);
                $(this).next('ul').slideDown(300);
                $(this).parent('li').addClass('nav-show').siblings('li').removeClass('nav-show');
            }else{
                //收缩已展开
                $(this).next('ul').slideUp(300);
                $('.nav-item.nav-show').removeClass('nav-show');
            }
        }
    });
    /**主题右侧上部点击事件*/
    $(".main_right .main_top>ul>li").on("click",function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    /*新闻切换*/
    $(".history_new>ul>li").each(function(i){
        $(this).on("click",function(){
            $(".latest_new_art p").html($(this).find("p").html());
        });
    });
    /*首页我的日志和工作日志切换*/
    $(".Job_log_con ul .select_log").each(function(i){
        $(this).on("click",function(){
            $(this).addClass("active").siblings().removeClass("active");
            $(".Job_log_con_show>ul>li").eq(i).addClass("active").siblings().removeClass("active");
        });
    });



});