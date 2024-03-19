package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.oa.dao.*;
import com.thinkgem.jeesite.modules.oa.entity.*;
import com.thinkgem.jeesite.modules.process.dao.CustomHiActinstDao;
import com.thinkgem.jeesite.modules.process.dao.CustomHiTaskinstDao;
import com.thinkgem.jeesite.modules.process.dao.CustomProcessDao;
import com.thinkgem.jeesite.modules.process.dao.CustomRuskTaskinstDao;
import com.thinkgem.jeesite.modules.process.entity.CustomHiActinst;
import com.thinkgem.jeesite.modules.process.entity.CustomHiTaskinst;
import com.thinkgem.jeesite.modules.process.entity.CustomProcess;
import com.thinkgem.jeesite.modules.process.entity.CustomRuskTaskinst;

import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 */
public class NoticeUnits {

    private static OaNewsDao newsDao = SpringContextHolder.getBean(OaNewsDao.class);

    private static OaSummaryDayDao oaSummaryDayDao = SpringContextHolder.getBean(OaSummaryDayDao.class);

    private static OaSummaryPermissionDao oaSummaryPermissionDao = SpringContextHolder.getBean(OaSummaryPermissionDao.class);
    private static ActTaskService actTaskService = SpringContextHolder.getBean(ActTaskService.class);

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);

    private static OaScheduleDao scheduleDao = SpringContextHolder.getBean(OaScheduleDao.class);

    private static OaAppraiseDao oaAppraiseDao = SpringContextHolder.getBean(OaAppraiseDao.class);

    private static OaScheduleDao oaScheduleDao = SpringContextHolder.getBean(OaScheduleDao.class);

    private static CustomRuskTaskinstDao customRuskTaskinstDao = SpringContextHolder.getBean(CustomRuskTaskinstDao.class);

    private static CustomHiTaskinstDao customHiTaskinstDao = SpringContextHolder.getBean(CustomHiTaskinstDao.class);

    private static CustomHiActinstDao customHiActinstDao = SpringContextHolder.getBean(CustomHiActinstDao.class);

    private static CustomProcessDao customProcessDao = SpringContextHolder.getBean(CustomProcessDao.class);


    /**
     * 获取当前用户可审核的新闻公告
     * @return
     */
    public static List<OaNews> getAuditNews(){
        User user = UserUtils.getUser();
        if(user != null){
            OaNews news = new OaNews();
            news.setAuditMan(user.getId());
            news.setAuditFlag("0");
            String companyid=  UserUtils.getUser().getCompany().getId();
            // news.setCompanyId(companyid);

            //以前的新闻公告审核（查出俩条的）
            //List<OaNews> newsList = newsDao.findListAndRole(news);
            List<OaNews> newsList = newsDao.findList(news);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }


    /**
     * 获取当前用户待办的自由流程
     * @return
     */
    public static List<CustomRuskTaskinst> findCustom(){
        User user = UserUtils.getUser();
        if(user != null){
            CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
            customRuskTaskinst.setAssignee(UserUtils.getUser().getCardNo());
            customRuskTaskinst.setFlagAudit("0");
            List<CustomRuskTaskinst> newsList = customRuskTaskinstDao.findList(customRuskTaskinst);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }

    /**
     * 获取自由流程的意见
     * @return
     */
    public static List<CustomHiActinst> findComment(String procinstId){
        User user = UserUtils.getUser();
        if(user != null){
            CustomHiActinst customHiActinst = new CustomHiActinst();
            customHiActinst.setProcInstId(procinstId);
            List<CustomHiActinst> newsList = customHiActinstDao.findList(customHiActinst);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }

    /**
     * 获取当前用户所在的部门
     * @return
     */
    public static String getOffice(){
        User user = UserUtils.getUser();
        if(user != null && user.getOffice() != null) {
            return user.getOffice().getName();
        }
        return "";
    }

    /**
     * 获取自由流程的意见,用于显示在表单中
     * @return
     */
    public static List<CustomHiTaskinst> findTableComment(String procinstId){
        User user = UserUtils.getUser();
        if(user != null){
            CustomHiTaskinst customHiTaskinst = new CustomHiTaskinst();
            customHiTaskinst.setProcInstId(procinstId);
            List<CustomHiTaskinst> newsList = customHiTaskinstDao.findListByTable(customHiTaskinst);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }


    /**
     * 获取当前登陆人所发起的流程
     * @return
     */
    public static List<CustomProcess> findCustomProcess(){
        User user = UserUtils.getUser();
        if(user != null){
            CustomProcess customProcess = new CustomProcess();
            customProcess.setCreateBy(UserUtils.getUser());
            List<CustomProcess> newsList = customProcessDao.findList(customProcess);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }

    /**
     * 获取当前登陆人已办的自由流程
     * @return
     */
    public static List<CustomHiTaskinst> findCustomHiTaskinst(){
        User user = UserUtils.getUser();
        if(user != null){
            CustomHiTaskinst customHiTaskinst = new CustomHiTaskinst();
            customHiTaskinst.setAssignee(UserUtils.getUser().getCardNo());
            List<CustomHiTaskinst> newsList = customHiTaskinstDao.findAllList(customHiTaskinst);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }


    /**
     * 获取待办列表
     * @return
     */
    public static List<Act> getTodo(){
        String userId="";
        List<Act> acts = actTaskService.todoList(new Act(),userId);
        if(acts == null){
            acts = Lists.newArrayList();
        }
        return acts;
    }
    /**
     * 获取待办列表
     * @return
     */
    public static List<Act> getTodo1(String userId){
        List<Act> acts = actTaskService.todoList(new Act(),userId);
        if(acts == null){
            acts = Lists.newArrayList();
        }
        return acts;
    }



    /**
     * 获取最新10条已办列表
     * @return
     */
//    public static List<Act> getdone1(String userId){
//        List<Act> acts = actTaskService.doneList1(userId);
//        if(acts == null){
//            acts = Lists.newArrayList();
//        }
//        return acts;
//    }

    /**
     * 获取最新10条已办列表
     * @return
     */
    public static List<Act> getdone(){
        List<Act> acts = actTaskService.doneList();
        if(acts == null){
            acts = Lists.newArrayList();
        }
        return acts;
    }

    /**
     * 获取当前用户可审核的新闻公告
     * @return
     */
    public static List<OaNews> getAllNews(){
        OaNews news = new OaNews();
        news.setAuditFlag("1");
        news.setSelf(true);
        String companyid=  UserUtils.getUser().getCompany().getId();
       news.setCompanyId(companyid);
        List<OaNews> newsList = newsDao.findList(news);
        if(newsList == null){
            newsList = Lists.newArrayList();
        }
        return newsList;
    }


    /**
     * 获取所有已发布新闻公告并查询关联的角色和用户信息
     * @return
     */
    public static List<OaNews> getFifteenNewsAndRole(){
        OaNews news = new OaNews();
        news.setAuditFlag("1");
        news.setSelf(true);
        List<OaNews> newsList = newsDao.findListAndRole(news);
        if(newsList == null){
            newsList = Lists.newArrayList();
        }
        return newsList;
    }

    /**
     * 获取所有已发布新闻公告并查询关联的角色和用户信息
     * @return
     */
    public static List<OaNews> getAllNewsAndRole(){
        OaNews news = new OaNews();
        news.setAuditFlag("1");
        news.setSelf(true);
        List<OaNews> newsList = newsDao.findList(news);
        if(newsList == null){
            newsList = Lists.newArrayList();
        }
        return newsList;
    }

    //根据用户id查询新闻公告
    public static List<OaNews> getAllCollectNews(){
        OaNews news = new OaNews();
        news.setAuditFlag("1");
        List<OaNews> newsList = newsDao.findCollectList(news);
        if(newsList == null){
            newsList = Lists.newArrayList();
        }
        return newsList;
    }


    //根据用户id查询当日日程
    public static List<OaSchedule> getOaSchedule(){
        OaSchedule schedule = new OaSchedule();
        schedule.setFlag("0");
        schedule.setLoginId(UserUtils.getUser().getId());


        Calendar currentDate = new GregorianCalendar();

        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        schedule.setScheduleDate((Date)currentDate.getTime().clone());

        List<OaSchedule> scheduleList = scheduleDao.findOaSchedule(schedule);
        if(scheduleList == null){
            scheduleList = Lists.newArrayList();
        }
        return scheduleList;
    }

    //根据用户id查询工作日程
    public static List<OaSummaryDay> findNewFore(){
        OaSummaryDay oaSummaryDay = new OaSummaryDay();
        oaSummaryDay.setLoginId(UserUtils.getUser().getId());
        List<OaSummaryDay> scheduleList = oaSummaryDayDao.findNewFore(oaSummaryDay);
        if(scheduleList == null){
            scheduleList = Lists.newArrayList();
        }
        return scheduleList;
    }

    //查询当前登录人的被评阅人
    public static List<OaSummaryPermission> findListByLoginId()throws Exception {
        OaSummaryDay oaSummaryDay = new OaSummaryDay();
        String ids=null;
        Date date=new Date();
        if (oaSummaryDay.getSumDate() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(date);
            Date date1 = sdf.parse(s);
            oaSummaryDay.setSumDate(date1);
        }
        String loginId = oaSummaryDay.getLoginId();
        Date sumDate = oaSummaryDay.getSumDate();
        oaSummaryDay.setLoginId(loginId);
        oaSummaryDay.setSumDate(sumDate);

        //当前日期




        //如果有日期，点击查询时的操作
        List<OaSummaryPermission> oaSummaryPermissionList=null;
        if (oaSummaryDay.getSumDate() != null) {
            //oaSummaryDay.setEvaluateManId(UserUtils.getUser().getId());
            if (StringUtils.isNotBlank(oaSummaryDay.getLoginId())) {
                oaSummaryPermissionList = oaSummaryPermissionDao.findListByLoginId(oaSummaryDay.getLoginId());
            } else {
                if (StringUtils.isNotBlank(ids)) {
                    oaSummaryPermissionList = oaSummaryPermissionDao.findListByLoginId(ids);
                } else {
                    oaSummaryPermissionList = oaSummaryPermissionDao.findListByLoginId(UserUtils.getUser().getId());
                }
            }


            for (OaSummaryPermission oaSummaryPermission:oaSummaryPermissionList) {
                oaSummaryDay.setLoginId(oaSummaryPermission.getEvaluateId());
                OaSummaryDay oaSummaryDay1 = oaSummaryDayDao.findByDate(oaSummaryDay);
                if(oaSummaryDay1!=null){
                oaSummaryPermission.setOfficeName(oaSummaryDay1.getContent());
                }else{
                    oaSummaryPermission.setOfficeName("");
                }
            }
        }

        if(oaSummaryPermissionList == null){
            oaSummaryPermissionList = Lists.newArrayList();
        }
        return oaSummaryPermissionList;
}
}
