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
import com.thinkgem.jeesite.modules.process.entity.HospitalDetailIndexQuarter;
import com.thinkgem.jeesite.modules.process.service.HospitalDetailIndexQuarterService;

/**
 * 医院经营指标季度表Controller
 * @author oa
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/hospitalDetailIndexQuarter")
public class HospitalDetailIndexQuarterController extends BaseController {

	@Autowired
	private HospitalDetailIndexQuarterService hospitalDetailIndexQuarterService;
	
	@ModelAttribute
	public HospitalDetailIndexQuarter get(@RequestParam(required=false) String id) {
		HospitalDetailIndexQuarter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hospitalDetailIndexQuarterService.get(id);
		}
		if (entity == null){
			entity = new HospitalDetailIndexQuarter();
		}
		return entity;
	}
	
	@RequiresPermissions("process:hospitalDetailIndexQuarter:view")
	@RequestMapping(value = {"list", ""})
	public String list(HospitalDetailIndexQuarter hospitalDetailIndexQuarter, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HospitalDetailIndexQuarter> page = hospitalDetailIndexQuarterService.findPage(new Page<HospitalDetailIndexQuarter>(request, response), hospitalDetailIndexQuarter); 
		model.addAttribute("page", page);
		return "modules/process/hospitalDetailIndexQuarterList";
	}

	@RequiresPermissions("process:hospitalDetailIndexQuarter:view")
	@RequestMapping(value = "form")
	public String form(HospitalDetailIndexQuarter hospitalDetailIndexQuarter, Model model) {
		model.addAttribute("hospitalDetailIndexQuarter", hospitalDetailIndexQuarter);
		return "modules/process/hospitalDetailIndexQuarterForm";
	}

	@RequiresPermissions("process:hospitalDetailIndexQuarter:edit")
	@RequestMapping(value = "save")
	public String save(HospitalDetailIndexQuarter hospitalDetailIndexQuarter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hospitalDetailIndexQuarter)){
			return form(hospitalDetailIndexQuarter, model);
		}
		hospitalDetailIndexQuarterService.save(hospitalDetailIndexQuarter);
		addMessage(redirectAttributes, "保存医院经营指标季度表成功");
		return "redirect:"+Global.getAdminPath()+"/process/hospitalDetailIndexQuarter/?repage";
	}
	
	@RequiresPermissions("process:hospitalDetailIndexQuarter:edit")
	@RequestMapping(value = "delete")
	public String delete(HospitalDetailIndexQuarter hospitalDetailIndexQuarter, RedirectAttributes redirectAttributes) {
		hospitalDetailIndexQuarterService.delete(hospitalDetailIndexQuarter);
		addMessage(redirectAttributes, "删除医院经营指标季度表成功");
		return "redirect:"+Global.getAdminPath()+"/process/hospitalDetailIndexQuarter/?repage";
	}

}