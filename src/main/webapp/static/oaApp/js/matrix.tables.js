
$(document).ready(function(){
	
	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>'
	});
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});

});

//function tongyongScroll(flag){
//    var scrollTop = $(flag).scrollTop();               //滚动条距离顶部的高度
//    var scrollHeight = $(document).height();           //当前页面的总高度
//    var windowHeight = $(flag).height();               //当前可视的页面高度
//
//    if(scrollTop + windowHeight >= scrollHeight-100){        //距离顶部+当前高度 >=文档总高度 即代表滑动到底部
//        $(".touchRun").css("display","block");
//        setTimeout(function(){
//            clearTimeout();
//            $(".touchRun").css("display","none");
//        }, 2000)
//    }
//}
