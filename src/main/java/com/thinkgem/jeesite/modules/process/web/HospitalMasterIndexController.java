/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.process.entity.HospitalMasterIndex;
import com.thinkgem.jeesite.modules.process.service.HospitalMasterIndexService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 医院经营指标Controller
 *
 * @author oa
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/hospitalMasterIndex")
public class HospitalMasterIndexController extends BaseController {

    @Autowired
    private HospitalMasterIndexService hospitalMasterIndexService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute
    public HospitalMasterIndex get(@RequestParam(required = false) String id) {
        HospitalMasterIndex entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hospitalMasterIndexService.get(id);
        }
        if (entity == null) {
            entity = new HospitalMasterIndex();
        }
        return entity;
    }

    @RequiresPermissions("process:hospitalMasterIndex:view")
    @RequestMapping(value = {"list", ""})
    public String list(HospitalMasterIndex hospitalMasterIndex, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        if (hospitalMasterIndex != null && hospitalMasterIndex.getMonth() == null) {
            String s1 = sdf.format(new Date());  //2015-02-09  format()才是格式化
            String s2 = sd.format(new Date());  //2015-02-09  format()才是格式化
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(s1));
            hospitalMasterIndex.setMonth(sdf.parse(s1));
            int month = c.get(Calendar.MONTH) + 1;
            hospitalMasterIndex.setQuarter(getQuarterByMonth(month));
            //判断当前月份是这一年的第几个季度
            hospitalMasterIndex.setYearQuarter(s2);
            hospitalMasterIndex.setYear(sd.parse(s2));
        } else {
            String s1 = sdf.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
            String s2 = sd.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(s1));
            int month = c.get(Calendar.MONTH) + 1;
            hospitalMasterIndex.setQuarter(getQuarterByMonth(month));
            //判断当前月份是这一年的第几个季度
            hospitalMasterIndex.setYearQuarter(s2);
            hospitalMasterIndex.setYear(sd.parse(s2));
        }
        hospitalMasterIndex.setOrgId(UserUtils.getUser().getCompany().getId());
        StringBuilder sb = new StringBuilder();
        String companyId = null;
        if (StringUtils.equalsIgnoreCase("861100468", UserUtils.getUser().getCompany().getId())) {
            companyId = UserUtils.getUser().getCompany().getId();
        } else {
            companyId = UserUtils.getUser().getCompany().getId();
        }
        List<Office> offices = officeService.findCompany(companyId);
        if (offices != null && offices.size() > 0) {
            for (int i = 0; i < offices.size(); i++) {
                Object o = offices.get(i);
                hospitalMasterIndex.setOrgId(((User) o).getId());
                String ms = ((User) o).getId();
                List<HospitalMasterIndex> list = hospitalMasterIndexService.findAllList(hospitalMasterIndex);
                model.addAttribute("list" + ms + "", list);
                if (StringUtils.equalsIgnoreCase(UserUtils.getUser().getCompany().getId(), "861100284")) {
                    model.addAttribute("officeSize" + ms + "", "1");
                } else if (StringUtils.equalsIgnoreCase(UserUtils.getUser().getCompany().getId(), "861100201")) {
                    model.addAttribute("officeSize" + ms + "", "1");
                } else {
                    model.addAttribute("officeSize" + ms + "", offices.size());
                }
            }
        } else {
            List<HospitalMasterIndex> list = hospitalMasterIndexService.findAllList(hospitalMasterIndex);
            model.addAttribute("list", list);
        }
        model.addAttribute("hospitalMasterIndex", hospitalMasterIndex);
        return "modules/process/hospitalMasterIndexList";
    }

    @RequiresPermissions("process:hospitalMasterIndex:view")
    @RequestMapping(value = "form")
    public String form(HospitalMasterIndex hospitalMasterIndex, Model model) {

        try {
            if (hospitalMasterIndex == null) {
                hospitalMasterIndex = new HospitalMasterIndex();
            }
            if (hospitalMasterIndex != null && hospitalMasterIndex.getMonth() == null) {
                hospitalMasterIndex.setMonth(new Date());
            }
            hospitalMasterIndex.setOrgId(UserUtils.getUser().getCompany().getId());
            HospitalMasterIndex hospitalMasterIndex1 = hospitalMasterIndexService.handlerHXData(hospitalMasterIndex);
            model.addAttribute("hospitalMasterIndex", hospitalMasterIndex1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "modules/process/hospitalMasterIndexForm";
    }

    @RequiresPermissions("process:hospitalMasterIndex:edit")
    @RequestMapping(value = "save")
    public String save(HospitalMasterIndex hospitalMasterIndex, Model model, RedirectAttributes redirectAttributes) throws ParseException {
        if (!beanValidator(model, hospitalMasterIndex)) {
            return form(hospitalMasterIndex, model);
        }
        hospitalMasterIndex.setOrgId(UserUtils.getUser().getCompany().getId());
        hospitalMasterIndexService.saveOtherHospital(hospitalMasterIndex);
        addMessage(redirectAttributes, "保存医院经营指标成功");
        return "redirect:" + Global.getAdminPath() + "/process/hospitalMasterIndex/?repage";
    }

    @RequiresPermissions("process:hospitalMasterIndex:edit")
    @RequestMapping(value = "delete")
    public String delete(HospitalMasterIndex hospitalMasterIndex, RedirectAttributes redirectAttributes) {
        hospitalMasterIndexService.delete(hospitalMasterIndex);
        addMessage(redirectAttributes, "删除医院经营指标成功");
        return "redirect:" + Global.getAdminPath() + "/process/hospitalMasterIndex/?repage";
    }

    @ResponseBody
    @RequestMapping(value = "formData")
    public List formData(HospitalMasterIndex hospitalMasterIndex, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        if (hospitalMasterIndex != null && hospitalMasterIndex.getMonth() == null) {
            String s1 = sdf.format(new Date());  //2015-02-09  format()才是格式化
            String s2 = sd.format(new Date());  //2015-02-09  format()才是格式化
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(s1));
            hospitalMasterIndex.setMonth(sdf.parse(s1));
            int month = c.get(Calendar.MONTH) + 1;
            hospitalMasterIndex.setQuarter(getQuarterByMonth(month));
            //判断当前月份是这一年的第几个季度
            hospitalMasterIndex.setYearQuarter(s2);
            hospitalMasterIndex.setYear(sd.parse(s2));
        } else {
            String s1 = sdf.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
            String s2 = sd.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(s1));
            int month = c.get(Calendar.MONTH) + 1;
            hospitalMasterIndex.setQuarter(getQuarterByMonth(month));
            //判断当前月份是这一年的第几个季度
            hospitalMasterIndex.setYearQuarter(s2);
            hospitalMasterIndex.setYear(sd.parse(s2));
        }
        hospitalMasterIndex.setOrgId(UserUtils.getUser().getCompany().getId());
        StringBuilder sb = new StringBuilder();
        String companyId = null;
        if (StringUtils.equalsIgnoreCase("861100468", UserUtils.getUser().getCompany().getId())) {
            companyId = UserUtils.getUser().getCompany().getId();
        } else {
            companyId = UserUtils.getUser().getCompany().getId();
        }
        List<Office> offices = officeService.findCompany(companyId);
        for (int j = 0; j < offices.size(); j++) {
            Object o = offices.get(j);
            sb.append(((User) o).getId() + ",");
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            hospitalMasterIndex.setOrgIds(sb.substring(0, sb.length() - 1).split(","));
        } else {
            String[] orgIds = null;
            orgIds = new String[1];
            orgIds[0] = companyId;
            hospitalMasterIndex.setOrgIds(orgIds);
        }
        List<HospitalMasterIndex> list = hospitalMasterIndexService.findAllListHandler(hospitalMasterIndex);
        return list;
    }


    /**
     * 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月
     *
     * @param month
     * @return 当前月份是第几季度
     */
    public static String getQuarterByMonth(int month) {
        String months[] = {"1", "2", "3", "4"};
        if (month >= 1 && month <= 3) // 1-3月;0,1,2
            return months[0];
        else if (month >= 4 && month <= 6) // 4-6月;3,4,5
            return months[1];
        else if (month >= 7 && month <= 9) // 7-9月;6,7,8
            return months[2];
        else
            return months[3];
    }
}