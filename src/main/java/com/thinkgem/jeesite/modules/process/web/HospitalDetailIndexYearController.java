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
import com.thinkgem.jeesite.modules.process.entity.HospitalDetailIndexYear;
import com.thinkgem.jeesite.modules.process.service.HospitalDetailIndexYearService;

/**
 * 医院经营指标年表Controller
 * @author oa
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/hospitalDetailIndexYear")
public class HospitalDetailIndexYearController extends BaseController {

	@Autowired
	private HospitalDetailIndexYearService hospitalDetailIndexYearService;
	
	@ModelAttribute
	public HospitalDetailIndexYear get(@RequestParam(required=false) String id) {
		HospitalDetailIndexYear entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hospitalDetailIndexYearService.get(id);
		}
		if (entity == null){
			entity = new HospitalDetailIndexYear();
		}
		return entity;
	}
	
	@RequiresPermissions("process:hospitalDetailIndexYear:view")
	@RequestMapping(value = {"list", ""})
	public String list(HospitalDetailIndexYear hospitalDetailIndexYear, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HospitalDetailIndexYear> page = hospitalDetailIndexYearService.findPage(new Page<HospitalDetailIndexYear>(request, response), hospitalDetailIndexYear); 
		model.addAttribute("page", page);
		return "modules/process/hospitalDetailIndexYearList";
	}

	@RequiresPermissions("process:hospitalDetailIndexYear:view")
	@RequestMapping(value = "form")
	public String form(HospitalDetailIndexYear hospitalDetailIndexYear, Model model) {
		model.addAttribute("hospitalDetailIndexYear", hospitalDetailIndexYear);
		return "modules/process/hospitalDetailIndexYearForm";
	}

	@RequiresPermissions("process:hospitalDetailIndexYear:edit")
	@RequestMapping(value = "save")
	public String save(HospitalDetailIndexYear hospitalDetailIndexYear, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hospitalDetailIndexYear)){
			return form(hospitalDetailIndexYear, model);
		}
		hospitalDetailIndexYearService.save(hospitalDetailIndexYear);
		addMessage(redirectAttributes, "保存医院经营指标年表成功");
		return "redirect:"+Global.getAdminPath()+"/process/hospitalDetailIndexYear/?repage";
	}
	
	@RequiresPermissions("process:hospitalDetailIndexYear:edit")
	@RequestMapping(value = "delete")
	public String delete(HospitalDetailIndexYear hospitalDetailIndexYear, RedirectAttributes redirectAttributes) {
		hospitalDetailIndexYearService.delete(hospitalDetailIndexYear);
		addMessage(redirectAttributes, "删除医院经营指标年表成功");
		return "redirect:"+Global.getAdminPath()+"/process/hospitalDetailIndexYear/?repage";
	}

}