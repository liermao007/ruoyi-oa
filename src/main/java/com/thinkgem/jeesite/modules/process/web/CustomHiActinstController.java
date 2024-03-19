/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.process.entity.CustomHiActinst;
import com.thinkgem.jeesite.modules.process.service.CustomHiActinstService;
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
 * 自由流程历史节点表Controller
 * @author oa
 * @version 2017-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/process/customHiActinst")
public class CustomHiActinstController extends BaseController {

	@Autowired
	private CustomHiActinstService customHiActinstService;
	
	@ModelAttribute
	public CustomHiActinst get(@RequestParam(required=false) String id) {
		CustomHiActinst entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customHiActinstService.get(id);
		}
		if (entity == null){
			entity = new CustomHiActinst();
		}
		return entity;
	}
	
	@RequiresPermissions("process:customHiActinst:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomHiActinst customHiActinst, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomHiActinst> page = customHiActinstService.findPage(new Page<CustomHiActinst>(request, response), customHiActinst);
		model.addAttribute("page", page);
		return "modules/process/customHiActinstList";
	}

	@RequiresPermissions("process:customHiActinst:view")
	@RequestMapping(value = "form")
	public String form(CustomHiActinst customHiActinst, Model model) {
		model.addAttribute("customHiActinst", customHiActinst);
		return "modules/process/customHiActinstForm";
	}

	@RequiresPermissions("process:customHiActinst:edit")
	@RequestMapping(value = "save")
	public String save(CustomHiActinst customHiActinst, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customHiActinst)){
			return form(customHiActinst, model);
		}
		customHiActinstService.save(customHiActinst);
		addMessage(redirectAttributes, "保存自由流程历史节点表成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customHiActinst/?repage";
	}
	
	@RequiresPermissions("process:customHiActinst:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomHiActinst customHiActinst, RedirectAttributes redirectAttributes) {
		customHiActinstService.delete(customHiActinst);
		addMessage(redirectAttributes, "删除自由流程历史节点表成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customHiActinst/?repage";
	}

}