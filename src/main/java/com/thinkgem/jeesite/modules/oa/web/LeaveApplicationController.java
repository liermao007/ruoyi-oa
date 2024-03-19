/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.entity.TravelApplication;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.LeaveApplication;
import com.thinkgem.jeesite.modules.oa.service.LeaveApplicationService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 请假单统计Controller
 * @author oa
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/leaveApplication")
public class LeaveApplicationController extends BaseController {

	@Autowired	private LeaveApplicationService leaveApplicationService;
	
	@ModelAttribute
	public LeaveApplication get(@RequestParam(required=false) String id) {
		LeaveApplication entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leaveApplicationService.get(id);
		}
		if (entity == null){
			entity = new LeaveApplication();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	public String list( String tableName,LeaveApplication leaveApplication, String type, HttpServletRequest request, HttpServletResponse response, Model model) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String qjksrq="";
        String qjjsrq="";
        if(leaveApplication.getQjksrq()!=null){
         qjksrq= sdf.format(leaveApplication.getQjksrq());}
        if(leaveApplication.getQjjsrq()!=null){
            qjjsrq= sdf.format(leaveApplication.getQjjsrq());}
		Page<LeaveApplication> page = leaveApplicationService.findList1(new Page<LeaveApplication>(request, response),type,leaveApplication, tableName,qjksrq,qjjsrq);
        if(page.getList().size()>0){
        page.getList().get(0).setProcDefId(tableName);
        }
		model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("tableName", tableName);
		return "modules/oa/leaveApplicationList";
	}

	@RequiresPermissions("oa:leaveApplication:view")
	@RequestMapping(value = "form")
	public String form(LeaveApplication leaveApplication, Model model) {
		model.addAttribute("leaveApplication", leaveApplication);
		return "modules/oa/leaveApplicationForm";
	}

	@RequiresPermissions("oa:leaveApplication:edit")
	@RequestMapping(value = "save")
	public String save(LeaveApplication leaveApplication, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, leaveApplication)){
			return form(leaveApplication, model);
		}
		leaveApplicationService.save(leaveApplication);
		addMessage(redirectAttributes, "保存保存请假单统计成功");
		return "redirect:"+Global.getAdminPath()+"/oa/leaveApplication/?repage";
	}
	
	@RequiresPermissions("oa:leaveApplication:edit")
	@RequestMapping(value = "delete")
	public String delete(LeaveApplication leaveApplication, RedirectAttributes redirectAttributes) {
		leaveApplicationService.delete(leaveApplication);
		addMessage(redirectAttributes, "删除保存请假单统计成功");
		return "redirect:"+Global.getAdminPath()+"/oa/leaveApplication/?repage";
	}



    /**
     * 导出数据
     *
     * @param
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */

    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(LeaveApplication leaveApplication, HttpServletRequest request,String type, HttpServletResponse response,String tableName, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "请假统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String qjksrq="";
            String qjjsrq="";
            if(leaveApplication.getQjksrq()!=null){
                qjksrq= sdf.format(leaveApplication.getQjksrq());}
            if(leaveApplication.getQjjsrq()!=null){
                qjjsrq= sdf.format(leaveApplication.getQjjsrq());}

            Page<LeaveApplication> page = leaveApplicationService.findList1(new Page<LeaveApplication>(request, response), type,leaveApplication, tableName, qjksrq, qjjsrq);
            for(LeaveApplication leaveApplication1 :page.getList()){
                leaveApplication1.setQjlx(DictUtils.getDictLabel(leaveApplication1.getQjlx(),"slhp_leavle_type",""));
            }
            new ExportExcel("请假统计数据", LeaveApplication.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "请假统计数据失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/oa/travelApplication/list?repage";
    }
}