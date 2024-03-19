/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.process.entity.CustomComment;
import com.thinkgem.jeesite.modules.process.service.CustomCommentService;
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
 * 自由流程意见Controller
 * @author oa
 * @version 2017-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/customComment")
public class CustomCommentController extends BaseController {

	@Autowired
	private CustomCommentService customCommentService;
	
	@ModelAttribute
	public CustomComment get(@RequestParam(required=false) String id) {
		CustomComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customCommentService.get(id);
		}
		if (entity == null){
			entity = new CustomComment();
		}
		return entity;
	}
	
	@RequiresPermissions("process:customComment:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomComment customComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomComment> page = customCommentService.findPage(new Page<CustomComment>(request, response), customComment);
		model.addAttribute("page", page);
		return "modules/process/customCommentList";
	}

	@RequiresPermissions("process:customComment:view")
	@RequestMapping(value = "form")
	public String form(CustomComment customComment, Model model) {
		model.addAttribute("customComment", customComment);
		return "modules/process/customCommentForm";
	}

	@RequiresPermissions("process:customComment:edit")
	@RequestMapping(value = "save")
	public String save(CustomComment customComment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customComment)){
			return form(customComment, model);
		}
		customCommentService.save(customComment);
		addMessage(redirectAttributes, "保存自由流程意见成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customComment/?repage";
	}
	
	@RequiresPermissions("process:customComment:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomComment customComment, RedirectAttributes redirectAttributes) {
		customCommentService.delete(customComment);
		addMessage(redirectAttributes, "删除自由流程意见成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customComment/?repage";
	}

}