/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.oa.entity.*;
import com.thinkgem.jeesite.modules.oa.service.*;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 工作日志Controller
 *
 * @author oa
 * @version 2016-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaSummaryDay")
public class OaSummaryDayController extends BaseController {

    @Autowired
    private OaSummaryDayService oaSummaryDayService;

    @Autowired
    private OaScheduleService oaScheduleService;

    @Autowired
    private OaSummaryWeekService oaSummaryWeekService;

    @Autowired
    private OaAppraiseService oaAppraiseService;

    @Autowired
    private OaWeekAppraiseService oaWeekAppraiseService;
    @Autowired
    private OaSummaryPermissionService oaSummaryPermissionService;

    @ModelAttribute
    public OaSummaryDay get(@RequestParam(required = false) String id) {
        OaSummaryDay entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = oaSummaryDayService.get(id);
        }
        if (entity == null) {
            entity = new OaSummaryDay();
        }
        return entity;
    }

    //@RequiresPermissions("oa:oaSummaryDay:view")
    @RequestMapping(value = {"list", ""})
    public String list(@ModelAttribute("oaSummaryDay") OaSummaryDay oaSummaryDay, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        User user = UserUtils.getUser();
        oaSummaryDay.setLoginId(user.getId());
        String loginId = user.getId();
        //当前日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());
        Date date = sdf.parse(s);


        //如果有日期，点击查询时的操作
        if (oaSummaryDay.getSumDate() != null) {
            Date before = oaSummaryDay.getSumDate();
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
            OaSchedule oaSchedule = new OaSchedule();
            oaSchedule.setScheduleDate(before);
            oaSchedule.setLoginId(user.getId());
            List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
            if (oaSummaryDay != null) {
                oaSummaryDay.setOaScheduleList(list);
                StringBuilder sb = new StringBuilder();
                OaAppraise oaAppraise = new OaAppraise();
                oaAppraise.setEvaluateId(loginId);
                oaAppraise.setAppraiseDate(before);
                List<OaAppraise> appraises = oaAppraiseService.findByEvaluate(oaAppraise);
                if (appraises != null) {
                    for (int i = 0; i < appraises.size(); i++) {
                        sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                    }
                }
                if (sb != null && sb.toString() != "") {
                    oaSummaryDay.setEvaluate(sb.toString());
                }

                oaSummaryDay.setEvaluate(sb.toString());
                model.addAttribute("oaSummaryDay", oaSummaryDay);
            } else {
                OaSummaryDay oaSummaryDay1 = new OaSummaryDay(list, before);
                StringBuilder sb = new StringBuilder();
                OaAppraise oaAppraise = new OaAppraise();
                oaAppraise.setEvaluateId(loginId);
                oaAppraise.setAppraiseDate(before);
                List<OaAppraise> appraises = oaAppraiseService.findByEvaluate(oaAppraise);
                if (appraises != null) {
                    for (int i = 0; i < appraises.size(); i++) {
                        sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                    }
                }
                if (sb != null && sb.toString() != "") {
                    oaSummaryDay1.setEvaluate(sb.toString());
                }

                model.addAttribute("oaSummaryDay", oaSummaryDay1);
            }
        } else {
            oaSummaryDay.setSumDate(date);
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
            if (oaSummaryDay != null) {
                OaSchedule oaSchedule = new OaSchedule();
                oaSchedule.setLoginId(user.getId());
                oaSchedule.setScheduleDate(oaSummaryDay.getSumDate());
                List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
                oaSummaryDay.setOaScheduleList(list);
                StringBuilder sb = new StringBuilder();
                OaAppraise oaAppraise = new OaAppraise();
                oaAppraise.setEvaluateId(loginId);
                oaAppraise.setAppraiseDate(oaSummaryDay.getSumDate());
                List<OaAppraise> appraises = oaAppraiseService.findByEvaluate(oaAppraise);
                if (appraises != null) {
                    for (int i = 0; i < appraises.size(); i++) {
                        sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                    }
                }
                if (sb != null && sb.toString() != "") {
                    oaSummaryDay.setEvaluate(sb.toString());
                }
                model.addAttribute("oaSummaryDay", oaSummaryDay);
            } else {
                OaSummaryDay od = new OaSummaryDay();
                StringBuilder sb = new StringBuilder();
                OaAppraise oaAppraise = new OaAppraise();
                oaAppraise.setEvaluateId(loginId);
                oaAppraise.setAppraiseDate(date);
                List<OaAppraise> appraises = oaAppraiseService.findByEvaluate(oaAppraise);
                if (appraises != null) {
                    for (int i = 0; i < appraises.size(); i++) {
                        sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                    }
                }
                if (sb != null && sb.toString() != "") {
                    od.setEvaluate(sb.toString());
                }
                OaSchedule oaSchedule = new OaSchedule();
                oaSchedule.setScheduleDate(date);
                oaSchedule.setLoginId(user.getId());
                List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
                od.setOaScheduleList(list);
                od.setSumDate(date);
                model.addAttribute("oaSummaryDay", od);
            }
        }
        return "modules/oa/oaSummaryDayList";
    }


    @RequestMapping(value = "complete")
    public String complete(OaSchedule oaSchedule, Model model) {
        List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
        model.addAttribute("result", list);
        return "modules/oa/oaSummaryDayList";
    }


    @RequestMapping(value = "form")
    public String form(OaSummaryDay oaSummaryDay, Model model) {
        model.addAttribute("oaSummaryDay", oaSummaryDay);
        return "modules/oa/oaSummaryDayForm";
    }

    @RequestMapping(value = "form1")
    public String form1(OaSummaryWeek oaSummaryWeek, Model model) {
        model.addAttribute("OaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaSummaryDayForm";
    }

    Date currentFirstDate;
    List dd = new ArrayList();

    //存放列表中的第一天的日期
    String year;

    //格式化日期
    public String formatDate(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        String year = cale.get(Calendar.YEAR) + "-";
        String month = cale.get(Calendar.MONTH) + 1 + "-";
        String day = cale.get(Calendar.DATE) + "";
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return year + month + day + ' ' + weekDays[w];
    }

    public Date addDate(Date date, int n) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.DAY_OF_MONTH, n);
        return cale.getTime();
    }

    public List setDate(Date date) {
        Calendar cale1 = Calendar.getInstance();
        cale1.setTime(date);
        int week = cale1.get(Calendar.DAY_OF_WEEK);
        date = addDate(date, 1 - week);
        currentFirstDate = date;
        dd = new ArrayList();
        for (int i = 0; i < 7; i++) {
            dd.add(formatDate(i == 0 ? date : addDate(date, i)));
        }
        return dd;
    }

    //查询本周周总结
    @RequestMapping(value = "formId")
    public String form(OaSummaryWeek oaSummaryWeek, Model model) throws Exception {
        User user = UserUtils.getUser();
        //获取当前想要格式的日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());
        Date date = sdf.parse(s);
        //根据当前的日期来设置第一天从星期日开始
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        List date1 = setDate(new Date());

        if (oaSummaryWeek.getWeekOfYear() != null) {
            oaSummaryWeek.setWeekOfYear(oaSummaryWeek.getWeekOfYear());
        } else {
            oaSummaryWeek.setWeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR));
        }
        oaSummaryWeek.setLoginId(user.getId());
        oaSummaryWeek = oaSummaryWeekService.findByWeek(oaSummaryWeek);
        List<OaSchedule> list = null;
        List<OaVo> oa = new ArrayList<OaVo>();

        //根据日期拿到每个日期的任务完成和工作总结
        for (int j = 0; j < date1.size(); j++) {
            OaSchedule oaSchedule = new OaSchedule();
            String sum = date1.get(j).toString().substring(0, 10);
            String begin = sdf.format(DateFormat.getDateInstance().parse(sum));
            Date scheduleDate = sdf.parse(begin);
            OaVo oaVo = null;
            oaSchedule.setScheduleDate(scheduleDate);
            oaSchedule.setLoginId(user.getId());
            List<OaSchedule> list1 = oaScheduleService.completeBy(oaSchedule);
            String con = null;
            String app = null;
            StringBuffer cons = new StringBuffer();
            for (int i = 0; i < list1.size(); i++) {
                con = cons.append(list1.get(i).getContent()).append("<br>").toString();
            }

            StringBuilder sb = new StringBuilder();
            OaAppraise oaAppraise = new OaAppraise();
            oaAppraise.setEvaluateId(user.getId());
            oaAppraise.setAppraiseDate(scheduleDate);
            List<OaAppraise> appraises = oaAppraiseService.findByEvaluate(oaAppraise);
            if (appraises != null) {
                for (int i = 0; i < appraises.size(); i++) {
                    app = sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");<br>").toString();
                }
            }
            OaSummaryDay oaSummaryDay = new OaSummaryDay();
            oaSummaryDay.setSumDate(scheduleDate);
            oaSummaryDay.setLoginId(user.getId());
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
            oaVo = new OaVo();
            oaVo.setContent(con);
            oaVo.setAppraise(app);
            oaVo.setDate(date1.get(j).toString());
            if (oaSummaryDay != null) {
                oaVo.setStatus(oaSummaryDay.getContent());
            }
            oa.add(oaVo);
        }
        if (oa != null && oaSummaryWeek != null) {
            oaSummaryWeek.setOaVos(oa);
        } else {
            oaSummaryWeek = new OaSummaryWeek();
            oaSummaryWeek.setOaVos(oa);
        }
        String year = sdf.format(currentFirstDate);
        String year1 = year.substring(0, 4);
        StringBuilder sb = new StringBuilder();
        OaWeekAppraise op = new OaWeekAppraise();
        op.setYear(year1);
        op.setEvaluateId(user.getId());
        if (StringUtils.isNotBlank(oaSummaryWeek.getWeek())) {
            op.setWeek(oaSummaryWeek.getWeek().substring(0, oaSummaryWeek.getWeek().indexOf(".")));
        }
        List<OaWeekAppraise> appraises = oaWeekAppraiseService.findByEvaluate(op);
        if (appraises != null) {
            for (int i = 0; i < appraises.size(); i++) {
                if (StringUtils.equals(appraises.get(i).getFlag(), "1")) {
                    sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                }
            }
        }
        if (sb != null && sb.toString() != "") {
            oaSummaryWeek.setEvaluateContent(sb.toString());
        }

        // oaSummaryWeek.setOaVos(oa);

        oaSummaryWeek.setWeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR));
        model.addAttribute("oaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaSummaryDayForm";
    }

    /**
     * 保存日总结
     *
     * @param oaSummaryDay
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:oaSummaryDay:edit")
    @RequestMapping(value = "save")
    public String save(OaSummaryDay oaSummaryDay, Model model, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        if (!beanValidator(model, oaSummaryDay)) {
            return form(oaSummaryDay, model);
        }
        oaSummaryDay.setLoginId(user.getId());
        oaSummaryDayService.save(oaSummaryDay);
        String id = oaSummaryDay.getId();
        oaSummaryDay = oaSummaryDayService.get(id);
        OaSchedule oaSchedule = new OaSchedule();
        oaSchedule.setScheduleDate(oaSummaryDay.getSumDate());
        oaSchedule.setLoginId(user.getId());
        List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
        oaSummaryDay.setOaScheduleList(list);
        model.addAttribute("oaSummaryDay", oaSummaryDay);
        Date date = oaSummaryDay.getSumDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        addMessage(redirectAttributes, "保存工作日志成功");
        return "redirect:" + Global.getAdminPath() + "/oa/oaSummaryDay/?sumDate=" + str;

    }


    /**
     * 保存周总结
     *
     * @param oaSummaryWeek
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveWeek")
    public String saveWeek(OaSummaryWeek oaSummaryWeek, Model model, RedirectAttributes redirectAttributes) throws Exception {
        User user = UserUtils.getUser();
        if (!beanValidator(model, oaSummaryWeek)) {
            return form1(oaSummaryWeek, model);
        }
        //获取当前想要格式的日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String year = sdf.format(currentFirstDate);
        //拿到当前的年
        String currentYear = year.substring(0, 10);
        String year1 = year.substring(0, 4);
        Date date = sdf.parse(currentYear);
        List date1 = setDate(date);
        oaSummaryWeek.setYear(year1);
        oaSummaryWeek.setWeek(oaSummaryWeek.getWeekOfYear() + "");
        oaSummaryWeek.setSumDate(new Date());
        List<OaSchedule> list = null;
        List<OaVo> oa = new ArrayList<OaVo>();
        //根据日期拿到每个日期的任务完成和工作总结
        for (int j = 0; j < date1.size(); j++) {
            OaSchedule oaSchedule = new OaSchedule();
            String sum = date1.get(j).toString().substring(0, 10);
            String begin = sdf.format(DateFormat.getDateInstance().parse(sum));
            Date scheduleDate = sdf.parse(begin);
            OaVo oaVo = null;
            oaSchedule.setScheduleDate(scheduleDate);
            oaSchedule.setLoginId(user.getId());
            List<OaSchedule> list1 = oaScheduleService.completeBy(oaSchedule);
            String con = null;
            StringBuffer cons = new StringBuffer();
            for (int i = 0; i < list1.size(); i++) {
                con = cons.append(list1.get(i).getContent()).append("<br>").toString();
            }
            OaSummaryDay oaSummaryDay = new OaSummaryDay();
            oaSummaryDay.setSumDate(scheduleDate);
            oaSummaryDay.setLoginId(user.getId());
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
            oaVo = new OaVo();
            oaVo.setContent(con);
            oaVo.setDate(date1.get(j).toString());
            if (oaSummaryDay != null) {
                oaVo.setStatus(oaSummaryDay.getContent());
            }
            oa.add(oaVo);
        }
        oaSummaryWeek.setOaVos(oa);
        oaSummaryWeek.setLoginId(user.getId());
        oaSummaryWeekService.save(oaSummaryWeek);
        addMessage(redirectAttributes, "保存工作日志成功");
        model.addAttribute("oaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaSummaryDayForm";
    }

    @RequiresPermissions("oa:oaSummaryDay:edit")
    @RequestMapping(value = "delete")
    public String delete(OaSummaryDay oaSummaryDay, RedirectAttributes redirectAttributes) {
        oaSummaryDayService.delete(oaSummaryDay);
        addMessage(redirectAttributes, "删除工作日志成功");
        return "redirect:" + Global.getAdminPath() + "/oa/oaSummaryDay/?repage";
    }

    /**
     * 上一周   本周   下一周
     *
     * @param flag
     * @param weekOfYear
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "lackWeek")
    public String lackWeek(@RequestParam("flag") String flag, @RequestParam("weekOfYear") Integer weekOfYear, Model model) throws Exception {
        User user = UserUtils.getUser();
        OaSummaryWeek oaSummaryWeek = new OaSummaryWeek();
        //获取当前想要格式的日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());
        Date date = sdf.parse(s);
        //根据当前的日期来设置第一天从星期日开始
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        List date1 = new ArrayList();
        //本周
        if (StringUtils.equals(flag, "1")) {
            if (weekOfYear == 1) {
                oaSummaryWeek.setWeekOfYear(53);
                date1 = setDate(addDate(currentFirstDate, -7));
                year = date1.get(0).toString();
            } else {
                oaSummaryWeek.setWeekOfYear(weekOfYear - 1);
                date1 = setDate(addDate(currentFirstDate, -7));
                year = date1.get(0).toString();
            }
        } else if (StringUtils.equals(flag, "2")) {
            if (weekOfYear == 53) {
                oaSummaryWeek.setWeekOfYear(1);
            } else {
                oaSummaryWeek.setWeekOfYear(weekOfYear + 1);
            }

            date1 = setDate(addDate(currentFirstDate, 7));
            year = date1.get(0).toString();
        } else {
            oaSummaryWeek.setWeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR));
            date1 = setDate(new Date());
        }
        oaSummaryWeek.setLoginId(user.getId());
        OaSummaryWeek oaSummaryWeek1 = oaSummaryWeekService.findByWeek(oaSummaryWeek);
        if (oaSummaryWeek1 != null) {
            oaSummaryWeek.setContent(oaSummaryWeek1.getContent());
            oaSummaryWeek.setNextPlanContent(oaSummaryWeek1.getNextPlanContent());
            oaSummaryWeek.setNextPlanTitle(oaSummaryWeek1.getNextPlanTitle());
            oaSummaryWeek.setId(oaSummaryWeek1.getId());
            oaSummaryWeek.setEvaluate(oaSummaryWeek1.getEvaluate());
        }
        List<OaVo> oa = new ArrayList<OaVo>();
        //根据日期拿到每个日期的任务完成和工作总结
        for (int j = 0; j < date1.size(); j++) {
            OaSchedule oaSchedule = new OaSchedule();
            String sum = date1.get(j).toString().substring(0, 10);
            String begin = sdf.format(DateFormat.getDateInstance().parse(sum));
            Date scheduleDate = sdf.parse(begin);
            OaVo oaVo = null;
            oaSchedule.setScheduleDate(scheduleDate);
            oaSchedule.setLoginId(user.getId());
            List<OaSchedule> list1 = oaScheduleService.completeBy(oaSchedule);
            String con = null;
            StringBuffer cons = new StringBuffer();
            if (list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    con = cons.append(list1.get(i).getContent()).append("<br>").toString();
                }
            }
            OaSummaryDay oaSummaryDay = new OaSummaryDay();
            oaSummaryDay.setSumDate(scheduleDate);
            oaSummaryDay.setLoginId(user.getId());
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);

            oaVo = new OaVo();
            oaVo.setContent(con);
            oaVo.setDate(date1.get(j).toString());
            if (oaSummaryDay != null) {
                oaVo.setStatus(oaSummaryDay.getContent());
            }
            oa.add(oaVo);
        }
        oaSummaryWeek.setOaVos(oa);
        String year = sdf.format(currentFirstDate);
        String year1 = year.substring(0, 4);
        StringBuilder sb = new StringBuilder();
        OaWeekAppraise op = new OaWeekAppraise();
        op.setYear(year1);
        op.setEvaluateId(user.getId());
        op.setWeek(oaSummaryWeek.getWeekOfYear() + "");
        List<OaWeekAppraise> appraises = oaWeekAppraiseService.findByEvaluate(op);
        if (appraises != null) {
            for (int i = 0; i < appraises.size(); i++) {
                if (StringUtils.equals(appraises.get(i).getFlag(), "1")) {
                    sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                }
            }
        }
        if (sb != null && sb.toString() != "") {
            oaSummaryWeek.setEvaluateContent(sb.toString());
        }
        model.addAttribute("oaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaSummaryDayForm";
    }


    /**
     * 同事评阅
     *
     * @param oaSummaryDay
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "day")
    public String formId(OaSummaryDay oaSummaryDay, Model model) throws Exception {
        oaSummaryDay.setFlag("1");
        //当前日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());
        Date date = sdf.parse(s);
        oaSummaryDay.setSumDate(date);
        model.addAttribute("oaSummaryDay", oaSummaryDay);
        return "modules/oa/oaPermissionList";
    }


    @RequestMapping(value = "loginId")
    public String loginId(@ModelAttribute("m") OaSummaryDay oaSummaryDay, String ids, Page<OaSummaryDay> page, Model model) throws Exception {
        if (StringUtils.isNotBlank(oaSummaryDay.getLoginId()) && !StringUtils.isNotBlank(ids)) {
            model.addAttribute("type", "1");
        }
        if (!StringUtils.isNotBlank(oaSummaryDay.getLoginId()) && StringUtils.isNotBlank(ids)) {
            model.addAttribute("type", "1");
        }


        if (oaSummaryDay.getSumDate() == null) {
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
//            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(date);
            Date date1 = sdf.parse(s);
            oaSummaryDay.setSumDate(date1);
        }
        String loginId = oaSummaryDay.getLoginId();
        Date sumDate = oaSummaryDay.getSumDate();
        oaSummaryDay.setLoginId(loginId);

        //当前日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());

        Date date = sdf.parse(s);

        //如果有日期，点击查询时的操作
        List<OaSummaryPermission> oaSummaryPermissionList;
        if (oaSummaryDay.getSumDate() != null) {
            //oaSummaryDay.setEvaluateManId(UserUtils.getUser().getId());
            if (StringUtils.isNotBlank(oaSummaryDay.getLoginId())) {
                oaSummaryPermissionList = oaSummaryPermissionService.findListByLoginId(oaSummaryDay.getLoginId());
            } else {
                if (StringUtils.isNotBlank(ids)) {
                    oaSummaryPermissionList = oaSummaryPermissionService.findListByLoginId(ids);
                } else {
                    oaSummaryPermissionList = oaSummaryPermissionService.findListByLoginId(UserUtils.getUser().getId());
                }
            }


            Page<OaSummaryDay> list1 = new Page<>();

            for (int i = 0; i < oaSummaryPermissionList.size(); i++) {
                oaSummaryDay.setLoginId(oaSummaryPermissionList.get(i).getEvaluateId());
                OaSummaryDay oaSummaryDay1 = oaSummaryDayService.findByDate(oaSummaryDay);
                if (oaSummaryDay1 == null) {
                    OaSummaryDay oaSummaryDay2 = new OaSummaryDay();
                    oaSummaryDay2.setLoginId(oaSummaryDay.getLoginId());
                    oaSummaryDay2.setEvaluateByNames(oaSummaryPermissionList.get(i).getEvaluateByNames());
                    oaSummaryDay2.setEvaluateMan(oaSummaryPermissionList.get(i).getEvaluateName());
                    oaSummaryDay2.setOfficeName(oaSummaryPermissionList.get(i).getOfficeName());
                    //再次查询当前下面人是否还有下级人员
                    List<OaSummaryPermission> byLoginId = oaSummaryPermissionService.findListByLoginId(oaSummaryDay.getLoginId());
                    if(byLoginId!=null && byLoginId.size()>0){
                        oaSummaryDay2.setIsLower("1");
                    }
                    list1.getList().add(oaSummaryDay2);
                } else {
                    oaSummaryDay1.setEvaluateByNames(oaSummaryPermissionList.get(i).getEvaluateByNames());
                    oaSummaryDay1.setEvaluateMan(oaSummaryPermissionList.get(i).getEvaluateName());
                    oaSummaryDay1.setOfficeName(oaSummaryPermissionList.get(i).getOfficeName());
                    //再次查询当前下面人是否还有下级人员
                    List<OaSummaryPermission> byLoginId = oaSummaryPermissionService.findListByLoginId(oaSummaryPermissionList.get(i).getEvaluateId());
                    if(byLoginId!=null && byLoginId.size()>0){
                        oaSummaryDay1.setIsLower("1");
                    }
                    list1.getList().add(oaSummaryDay1);
                }
            }

            OaAppraise oaAppraise = new OaAppraise();
            oaAppraise.setAppraiseDate(oaSummaryDay.getSumDate());
            List<OaAppraise> appraises = oaAppraiseService.findByEvaluate(oaAppraise);

            String a = null;
            if (list1.getList() != null) {

                for (int i = 0; i < list1.getList().size(); i++) {
                    a = "";
                    if (appraises.size() > 0) {
                        for (int r = 0; r < appraises.size(); r++) {
                            if (appraises.get(r).getEvaluateId().equals(list1.getList().get(i).getLoginId())) {
                                a += appraises.get(r).getContent() + "(<span class='font-weight-red'>" + appraises.get(r).getName() + "</span>);<br>";

                                list1.getList().get(i).setEvaluate(a);
                                model.addAttribute("oaSummaryDay", list1.getList().get(i));
                            } else {
                                if (StringUtils.isNotBlank(oaSummaryDay.getLoginId())) {
                                    model.addAttribute("oaSummaryDay", oaSummaryDay);
                                } else {
                                    oaSummaryDay.setLoginId("");
                                    model.addAttribute("oaSummaryDay", oaSummaryDay);
                                }

                            }
                        }
                    } else {
                        model.addAttribute("oaSummaryDay", oaSummaryDay);
                    }
                }
            }

            SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd");
            String str = sss.format(sumDate);
            model.addAttribute("sumDate", str);
            model.addAttribute("page", list1);
            if (StringUtils.isNotBlank(loginId)) {
                model.addAttribute("ids", loginId);
            } else {
                model.addAttribute("ids", ids);
            }

        } else {
            oaSummaryDay.setSumDate(date);
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
            StringBuilder sb = new StringBuilder();
            OaAppraise oaAppraise = new OaAppraise();
            oaAppraise.setAppraiseDate(date);
            oaAppraise.setEvaluateId(loginId);
            List<OaAppraise> appraises = oaAppraiseService.findByEvaluate(oaAppraise);
            if (appraises != null) {
                for (int i = 0; i < appraises.size(); i++) {
                    if (StringUtils.equals(appraises.get(i).getFlag(), "1")) {
                        sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                    }
                }
            }
            if (oaSummaryDay != null) {
                OaSchedule oaSchedule = new OaSchedule();
                oaSchedule.setLoginId(oaSummaryDay.getLoginId());
                oaSchedule.setScheduleDate(oaSummaryDay.getSumDate());
                List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
                oaSummaryDay.setOaScheduleList(list);
                if (sb != null && sb.toString() != "") {
                    oaSummaryDay.setEvaluate(sb.toString());
                }
                oaSummaryDay.setFlag("1");
                model.addAttribute("oaSummaryDay", oaSummaryDay);
            } else {
                OaSummaryDay os = new OaSummaryDay();
                OaSchedule oaSchedule = new OaSchedule();
                oaSchedule.setScheduleDate(date);
                oaSchedule.setLoginId(loginId);
                List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
                if (sb != null && sb.toString() != "") {
                    os.setEvaluate(sb.toString());
                }
                os.setOaScheduleList(list);
                os.setFlag("1");
                os.setLoginId(loginId);
                os.setSumDate(date);
                model.addAttribute("oaSummaryDay", os);
            }
        }
        return "modules/oa/oaPermissionList";
    }


    /**
     * 保存评阅意见信息
     *
     * @param oaSummaryDay
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:oaSummaryDay:edit")
    @RequestMapping(value = "saveEvel")
    public String saveEvel(OaSummaryDay oaSummaryDay, Model model, RedirectAttributes redirectAttributes) {
        String loginId = oaSummaryDay.getDd();
        User user = UserUtils.getUser();
        if (!beanValidator(model, oaSummaryDay)) {
            return form(oaSummaryDay, model);
        }
        OaAppraise oaAppraise = new OaAppraise();
        oaAppraise.setFlag(oaSummaryDay.getFlag());
        oaAppraise.setLoginId(user.getId());
        oaAppraise.setEvaluateId(oaSummaryDay.getLoginId());
        oaAppraise.setContent(oaSummaryDay.getEvaluateContent());
        oaAppraise.setEvaluateById(user.getId());
        oaAppraise.setAppraiseDate(oaSummaryDay.getAppraiseDate());
        oaAppraiseService.save(oaAppraise);

        StringBuilder sb = new StringBuilder();
        OaAppraise op = new OaAppraise();
        op.setAppraiseDate(oaSummaryDay.getSumDate());
        op.setEvaluateId(loginId);
        List<OaAppraise> appraises = null;
        if (StringUtils.isNotBlank(op.getEvaluateId())) {
            appraises = oaAppraiseService.findByEvaluate1(oaAppraise);
        }
        if (appraises != null) {
            for (int i = 0; i < appraises.size(); i++) {
                if (StringUtils.equals(appraises.get(i).getFlag(), "1")) {
                    sb.append(appraises.get(i).getContent() + "(" + appraises.get(i).getName() + ");");
                }
            }
        }
        if (sb != null && sb.toString() != "") {
            oaSummaryDay.setEvaluate(sb.toString());
        }
        OaSchedule oaSchedule = new OaSchedule();
        oaSchedule.setScheduleDate(oaSummaryDay.getSumDate());
        oaSchedule.setLoginId(loginId);
        List<OaSchedule> list = null;
        if (StringUtils.isNotBlank(oaSchedule.getLoginId())) {
            list = oaScheduleService.completeBy(oaSchedule);
        }
        oaSummaryDay.setOaScheduleList(list);
        oaSummaryDay.setEvaluateContent("");
        oaSummaryDay.setLoginId(loginId);
        model.addAttribute("oaSummaryDay", oaSummaryDay);
        Date date = oaSummaryDay.getAppraiseDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        //  addMessage(redirectAttributes, "保存评阅成功");
        model.addAttribute("message", "保存评阅成功");
        return "redirect:" + Global.getAdminPath() + "/oa/oaSummaryDay/loginId?sumDate=" + str;

    }


    @RequestMapping(value = "formWeek")
    public String formWeek(OaSummaryDay oaSummaryDay, Model model) {
        model.addAttribute("oaSummaryDay", oaSummaryDay);
        return "modules/oa/oaPermissionForm";
    }

    /**
     * 根据用户id查询工作日程，用于在首页中显示
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "oaSummaryDayList")
    public  List<OaSummaryDay> findNewFore(){
        OaSummaryDay oaSummaryDay = new OaSummaryDay();
        oaSummaryDay.setLoginId(UserUtils.getUser().getId());
        List<OaSummaryDay> scheduleList = oaSummaryDayService.findNewFore(oaSummaryDay);
        if(scheduleList == null){
            scheduleList = Lists.newArrayList();
        }
        return scheduleList;
    }
}


