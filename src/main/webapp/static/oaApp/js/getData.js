/*! jQuery UI - v1.9.1 - 2012-10-25
 * http://jqueryui.com
 * Includes: jquery.ui.core.js, jquery.ui.widget.js, jquery.ui.mouse.js, jquery.ui.draggable.js, jquery.ui.droppable.js, jquery.ui.resizable.js, jquery.ui.selectable.js, jquery.ui.sortable.js, jquery.ui.effect.js, jquery.ui.accordion.js, jquery.ui.autocomplete.js, jquery.ui.button.js, jquery.ui.datepicker.js, jquery.ui.dialog.js, jquery.ui.effect-blind.js, jquery.ui.effect-bounce.js, jquery.ui.effect-clip.js, jquery.ui.effect-drop.js, jquery.ui.effect-explode.js, jquery.ui.effect-fade.js, jquery.ui.effect-fold.js, jquery.ui.effect-highlight.js, jquery.ui.effect-pulsate.js, jquery.ui.effect-scale.js, jquery.ui.effect-shake.js, jquery.ui.effect-slide.js, jquery.ui.effect-transfer.js, jquery.ui.menu.js, jquery.ui.position.js, jquery.ui.progressbar.js, jquery.ui.slider.js, jquery.ui.spinner.js, jquery.ui.tabs.js, jquery.ui.tooltip.js
 * Copyright 2012 jQuery Foundation and other contributors; Licensed MIT */


window.onload = function(){

    var amq = org.activemq.Amq;
    var myDestination='topic://topic_cs_oa';
    amq.init({
        uri: 'a/amq',
        logging: true,
        timeout: 20,
        clientId:(new Date()).getTime().toString() //防止多个浏览器窗口标签共享同一个JSESSIONID
    });
    var myHandler =
        {
            rcvMessage: function(message)
            {
                var now=new Date();
                var month = now.getMonth() + 1;
                var day = now.getDate();
                var sec = now.getSeconds();
                var min =now.getMinutes();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (day >= 1 && day <= 9) {
                    day = "0" + day;
                }
                if (min >= 1 && min <= 9) {
                    min = "0" + min;
                }
                if (sec >= 0 && sec <= 9) {
                    sec = "0" + sec;
                }
                if(message==null){
                    return;}

                alert(message.data);
                return ;

                /* var html='<p align="center" style="font-size:14px;">'+message.data+'</p>'; */
                var list=[];
                ob = JSON.parse(message.data);
                list.push(ob)
                ob.date=now.getFullYear()+"年"+month+"月"+day+"日"+" "+now.getHours() + ":" + min+ ":"+ sec;

                //把list数据填入相应的位置：
                $("#aa").datagrid('loadData',list);
            }
        };
    amq.addListener("wzm",myDestination,myHandler.rcvMessage);


};



