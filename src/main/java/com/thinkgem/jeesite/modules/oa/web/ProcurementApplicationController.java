/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.oa.entity.LeaveApplication;
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
import com.thinkgem.jeesite.modules.oa.entity.ProcurementApplication;
import com.thinkgem.jeesite.modules.oa.service.ProcurementApplicationService;

/**
 * 采购统计表Controller
 * @author oa
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/procurementApplication")
public class ProcurementApplicationController extends BaseController {

	@Autowired
	private ProcurementApplicationService procurementApplicationService;
	
	@ModelAttribute
	public ProcurementApplication get(@RequestParam(required=false) String id) {
		ProcurementApplication entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = procurementApplicationService.get(id);
		}
		if (entity == null){
			entity = new ProcurementApplication();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:procurementApplication:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProcurementApplication procurementApplication, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProcurementApplication> page = procurementApplicationService.findPage(new Page<ProcurementApplication>(request, response), procurementApplication); 
		model.addAttribute("page", page);
		return "modules/oa/procurementApplicationList";
	}

	@RequiresPermissions("oa:procurementApplication:view")
	@RequestMapping(value = "form")
	public String form(ProcurementApplication procurementApplication, Model model) {
		model.addAttribute("procurementApplication", procurementApplication);
		return "modules/oa/procurementApplicationForm";
	}

	@RequiresPermissions("oa:procurementApplication:edit")
	@RequestMapping(value = "save")
	public String save(ProcurementApplication procurementApplication, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, procurementApplication)){
			return form(procurementApplication, model);
		}
		procurementApplicationService.save(procurementApplication);
		addMessage(redirectAttributes, "保存保存采购统计表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/procurementApplication/?repage";
	}
	
	@RequiresPermissions("oa:procurementApplication:edit")
	@RequestMapping(value = "delete")
	public String delete(ProcurementApplication procurementApplication, RedirectAttributes redirectAttributes) {
		procurementApplicationService.delete(procurementApplication);
		addMessage(redirectAttributes, "删除保存采购统计表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/procurementApplication/?repage";
	}

    /**
     * 导出数据
     *
     * @param LeaveApplication
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(ProcurementApplication procurementApplication, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "出差申请统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ProcurementApplication> page = procurementApplicationService.findProcurementApplication(new Page<ProcurementApplication>(request, response, -1), procurementApplication);
            new ExportExcel("出差申请统计数据", ProcurementApplication.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出出差申请统计数据失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/oa/travelApplication/list?repage";
    }

}