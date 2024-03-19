/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.process.entity.CustomHiTaskinst;
import com.thinkgem.jeesite.modules.process.service.CustomHiTaskinstService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自由流程历史表Controller
 * @author oa
 * @version 2017-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/process/customHiTaskinst")
public class CustomHiTaskinstController extends BaseController {

	@Autowired
	private CustomHiTaskinstService customHiTaskinstService;
	
	@ModelAttribute
	public CustomHiTaskinst get(@RequestParam(required=false) String id) {
		CustomHiTaskinst entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customHiTaskinstService.get(id);
		}
		if (entity == null){
			entity = new CustomHiTaskinst();
		}
		return entity;
	}
	
	@RequiresPermissions("process:customHiTaskinst:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomHiTaskinst customHiTaskinst, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomHiTaskinst> page = customHiTaskinstService.findPage(new Page<CustomHiTaskinst>(request, response), customHiTaskinst);
		model.addAttribute("page", page);
		return "modules/process/customHiTaskinstList";
	}

	@RequiresPermissions("process:customHiTaskinst:view")
	@RequestMapping(value = "form")
	public String form(CustomHiTaskinst customHiTaskinst, Model model) {
		model.addAttribute("customHiTaskinst", customHiTaskinst);
		return "modules/process/customHiTaskinstForm";
	}

	@RequiresPermissions("process:customHiTaskinst:edit")
	@RequestMapping(value = "save")
	public String save(CustomHiTaskinst customHiTaskinst, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customHiTaskinst)){
			return form(customHiTaskinst, model);
		}
		customHiTaskinstService.save(customHiTaskinst);
		addMessage(redirectAttributes, "保存自由流程历史表成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customHiTaskinst/?repage";
	}
	
	@RequiresPermissions("process:customHiTaskinst:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomHiTaskinst customHiTaskinst, RedirectAttributes redirectAttributes) {
		customHiTaskinstService.delete(customHiTaskinst);
		addMessage(redirectAttributes, "删除自由流程历史表成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customHiTaskinst/?repage";
	}

}