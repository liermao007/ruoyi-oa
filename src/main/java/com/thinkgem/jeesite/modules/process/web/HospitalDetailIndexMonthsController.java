/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.process.entity.HospitalDetailIndexMonths;
import com.thinkgem.jeesite.modules.process.service.HospitalDetailIndexMonthsService;

/**
 * 医院经营指标月表Controller
 * @author oa
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/hospitalDetailIndexMonths")
public class HospitalDetailIndexMonthsController extends BaseController {

	@Autowired
	private HospitalDetailIndexMonthsService hospitalDetailIndexMonthsService;
	
	@ModelAttribute
	public HospitalDetailIndexMonths get(@RequestParam(required=false) String id) {
		HospitalDetailIndexMonths entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hospitalDetailIndexMonthsService.get(id);
		}
		if (entity == null){
			entity = new HospitalDetailIndexMonths();
		}
		return entity;
	}
	
	@RequiresPermissions("process:hospitalDetailIndexMonths:view")
	@RequestMapping(value = {"list", ""})
	public String list(HospitalDetailIndexMonths hospitalDetailIndexMonths, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HospitalDetailIndexMonths> page = hospitalDetailIndexMonthsService.findPage(new Page<HospitalDetailIndexMonths>(request, response), hospitalDetailIndexMonths); 
		model.addAttribute("page", page);
		return "modules/process/hospitalDetailIndexMonthsList";
	}

	@RequiresPermissions("process:hospitalDetailIndexMonths:view")
	@RequestMapping(value = "form")
	public String form(HospitalDetailIndexMonths hospitalDetailIndexMonths, Model model) {
		model.addAttribute("hospitalDetailIndexMonths", hospitalDetailIndexMonths);
		return "modules/process/hospitalDetailIndexMonthsForm";
	}

	@RequiresPermissions("process:hospitalDetailIndexMonths:edit")
	@RequestMapping(value = "save")
	public String save(HospitalDetailIndexMonths hospitalDetailIndexMonths, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hospitalDetailIndexMonths)){
			return form(hospitalDetailIndexMonths, model);
		}
		hospitalDetailIndexMonthsService.save(hospitalDetailIndexMonths);
		addMessage(redirectAttributes, "保存医院经营指标月表成功");
		return "redirect:"+Global.getAdminPath()+"/process/hospitalDetailIndexMonths/?repage";
	}
	
	@RequiresPermissions("process:hospitalDetailIndexMonths:edit")
	@RequestMapping(value = "delete")
	public String delete(HospitalDetailIndexMonths hospitalDetailIndexMonths, RedirectAttributes redirectAttributes) {
		hospitalDetailIndexMonthsService.delete(hospitalDetailIndexMonths);
		addMessage(redirectAttributes, "删除医院经营指标月表成功");
		return "redirect:"+Global.getAdminPath()+"/process/hospitalDetailIndexMonths/?repage";
	}

}