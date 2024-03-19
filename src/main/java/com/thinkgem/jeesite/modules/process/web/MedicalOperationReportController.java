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
import com.thinkgem.jeesite.modules.process.entity.MedicalOperationReport;
import com.thinkgem.jeesite.modules.process.service.MedicalOperationReportService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 医疗运营报表Controller
 *
 * @author oa
 * @version 2018-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/process/medicalOperationReport")
public class MedicalOperationReportController extends BaseController {

    @Autowired
    private MedicalOperationReportService medicalOperationReportService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute
    public MedicalOperationReport get(@RequestParam(required = false) String id) {
        MedicalOperationReport entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = medicalOperationReportService.get(id);
        }
        if (entity == null) {
            entity = new MedicalOperationReport();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(MedicalOperationReport medicalOperationReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        //先获取当前登陆人的机构id，判断是否有下属的机构医院
        try {
            StringBuilder sb = new StringBuilder();
            String companyId=null;
            if(StringUtils.equalsIgnoreCase("861100468",UserUtils.getUser().getCompany().getId())){
                companyId =  UserUtils.getUser().getCompany().getId();
            }else{
                companyId = "861100284";
            }
            List<Office> offices = officeService.findCompany(companyId);
            for (int j = 0; j < offices.size(); j++) {
                Object o = offices.get(j);
                sb.append(((User) o).getId() + ",");
            }
            if (StringUtils.isNotBlank(sb.toString())) {
                medicalOperationReport.setOrgIds(sb.substring(0, sb.length() - 1).split(","));
            } else {
                String[] orgIds = null;
                orgIds = new String[1];
                orgIds[0] = companyId;
                medicalOperationReport.setOrgIds(orgIds);
            }
            if (medicalOperationReport != null && medicalOperationReport.getCurrentSumDate() == null) {
                Date today = new Date();
                today.setDate(today.getDate() - 1);
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String s1 = sdf.format(today);
                medicalOperationReport.setCurrentSumDate(sdf.parse(s1));
            }
            medicalOperationReport.setOrgId(UserUtils.getUser().getCompany().getId());
            List<MedicalOperationReport> reports =  medicalOperationReportService.findAllList(medicalOperationReport);
            model.addAttribute("list", reports);
            model.addAttribute("medicalOperationReport", medicalOperationReport);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "modules/process/medicalOperationReportList";
    }

    @RequestMapping(value = "form")
    public String form(MedicalOperationReport medicalOperationReport, Model model) {
        try {
            if (medicalOperationReport == null) {
                medicalOperationReport = new MedicalOperationReport();
            }
            if (medicalOperationReport != null && medicalOperationReport.getCurrentSumDate() == null) {
                Date today = new Date();
                today.setDate(today.getDate() - 1);
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String s1 = sdf.format(today);
                medicalOperationReport.setCurrentSumDate(sdf.parse(s1));
            }
            medicalOperationReport.setOrgId(UserUtils.getUser().getCompany().getId());
            List<MedicalOperationReport> reports = medicalOperationReportService.findList(medicalOperationReport);
            if (reports != null && reports.size() > 0) {
                medicalOperationReport = reports.get(0);
            }else{
                //查询上一天的人数与成本数据
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                MedicalOperationReport operationReport = new MedicalOperationReport();
                operationReport.setCurrentSumDate(medicalOperationReport.getCurrentSumDate());
                if(medicalOperationReport.getCurrentSumDate() != null){
                    Date beginDate = operationReport.getCurrentSumDate();
                    Calendar date = Calendar.getInstance();
                    date.setTime(beginDate);
                    date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
                    Date endDate = sdf.parse(sdf.format(date.getTime()));
                    operationReport.setCurrentSumDate(endDate);
                }
                operationReport.setOrgId(UserUtils.getUser().getCompany().getId());
                List<MedicalOperationReport> report = medicalOperationReportService.findList(operationReport);
                if(report != null && report.size()>0){
                    operationReport = report.get(0);
                    //将值放入到人数与成本、人力资源大型设备检查人次中
                    medicalOperationReport.setTotalNumHospital(operationReport.getTotalNumHospital());
                    medicalOperationReport.setDoctorNum(operationReport.getDoctorNum());
                    medicalOperationReport.setTotalCostHospital(operationReport.getTotalCostHospital());
                    medicalOperationReport.setTotalCostPeople(operationReport.getTotalCostPeople());
                    medicalOperationReport.setMedicalNum(operationReport.getMedicalNum());
                    medicalOperationReport.setCtCheck(operationReport.getCtCheck());
                    medicalOperationReport.setCtDoctor(operationReport.getCtDoctor());
                    medicalOperationReport.setUltrasonicCheck(operationReport.getUltrasonicCheck());
                    medicalOperationReport.setUltrasonicDoctor(operationReport.getUltrasonicDoctor());
                    medicalOperationReport.setxCheck(operationReport.getxCheck());
                    medicalOperationReport.setxDoctor(operationReport.getxDoctor());
                    medicalOperationReport.setNmrCheck(operationReport.getNmrCheck());
                    medicalOperationReport.setNmrDoctor(operationReport.getNmrDoctor());
                }
            }
            model.addAttribute("medicalOperationReport", medicalOperationReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "modules/process/medicalOperationReportForm";
    }

    @RequestMapping(value = "formData")
    @ResponseBody
    public List formData(MedicalOperationReport medicalOperationReport) {
        try {
            StringBuilder sb = new StringBuilder();
            String companyId=null;
            if(StringUtils.equalsIgnoreCase("861100468",UserUtils.getUser().getCompany().getId())){
                companyId =  UserUtils.getUser().getCompany().getId();
            }else{
                companyId = "861100284";
            }
            List<Office> offices = officeService.findCompany(companyId);
            for (int j = 0; j < offices.size(); j++) {
                Object o = offices.get(j);
                sb.append(((User) o).getId() + ",");
            }
            if (StringUtils.isNotBlank(sb.toString())) {
                medicalOperationReport.setOrgIds(sb.substring(0, sb.length() - 1).split(","));
            } else {
                String[] orgIds = null;
                orgIds = new String[1];
                orgIds[0] = companyId;
                medicalOperationReport.setOrgIds(orgIds);
            }
            if (medicalOperationReport != null && medicalOperationReport.getCurrentSumDate() == null) {
                Date today = new Date();
                today.setDate(today.getDate() - 1);
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String s1 = sdf.format(today);
                medicalOperationReport.setCurrentSumDate(sdf.parse(s1));
            }
            medicalOperationReport.setOrgId(UserUtils.getUser().getCompany().getId());
            List<MedicalOperationReport> reports = medicalOperationReportService.findAllList(medicalOperationReport);
            return reports;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "save")
    public String save(MedicalOperationReport medicalOperationReport, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, medicalOperationReport)) {
            return form(medicalOperationReport, model);
        }
        medicalOperationReport.setOrgId(UserUtils.getUser().getCompany().getId());
        medicalOperationReportService.saveMedical(medicalOperationReport);
        addMessage(redirectAttributes, "保存医疗运营数据成功");
        return "redirect:" + Global.getAdminPath() + "/process/medicalOperationReport/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(MedicalOperationReport medicalOperationReport, RedirectAttributes redirectAttributes) {
        medicalOperationReportService.delete(medicalOperationReport);
        addMessage(redirectAttributes, "删除医疗运营数据成功");
        return "redirect:" + Global.getAdminPath() + "/process/medicalOperationReport/?repage";
    }


    /**
     * 获取当前日期的前一天的日期
     *
     * @param date
     * @return
     */
    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

}