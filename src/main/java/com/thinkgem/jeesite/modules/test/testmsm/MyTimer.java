package com.thinkgem.jeesite.modules.test.testmsm;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.NoticeUnits;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class MyTimer {

    public void doTimerWork(){

        //待办日程发送短信提醒：待办日程10分钟以前
        List<OaSchedule> agendas =  NoticeUnits.getOaSchedule();
        for(OaSchedule agenda:agendas){
            Date  date =agenda.getScheduleDate();
            Date now_10 = new Date(date.getTime()-600000); //10分钟前的时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//格式化日期格式-日期到分钟
            if(dateFormat.format(new Date()).equals(dateFormat.format(now_10))){
             //  SmsModule.oaScheduleWork(agenda.getLoginId(),agenda.getEmergencyLevel(),dateFormat.format(agenda.getScheduleDate()),agenda.getContent());
//               SmsModule.flowWork(agenda.getLoginId(),agenda.getEmergencyLevel(),"杨瑞东",agenda.getContent());
               // SmsModule.notifyWork(agenda.getLoginId(),dateFormat.format(agenda.getScheduleDate()),agenda.getContent());
               //SmsModule.notifyFastWork(agenda.getLoginId(),dateFormat.format(agenda.getScheduleDate()),agenda.getContent());
            }
        }
    }
}
