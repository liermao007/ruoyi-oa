/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

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
import com.thinkgem.jeesite.modules.oa.entity.OaFlowAuditRecord;
import com.thinkgem.jeesite.modules.oa.service.OaFlowAuditRecordService;

/**
 * 流程审核记录Controller
 * @author oa
 * @version 2023-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaFlowAuditRecord")
public class OaFlowAuditRecordController extends BaseController {

	@Autowired
	private OaFlowAuditRecordService oaFlowAuditRecordService;
	
	@ModelAttribute
	public OaFlowAuditRecord get(@RequestParam(required=false) String id) {
		OaFlowAuditRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaFlowAuditRecordService.get(id);
		}
		if (entity == null){
			entity = new OaFlowAuditRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaFlowAuditRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaFlowAuditRecord oaFlowAuditRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaFlowAuditRecord> page = oaFlowAuditRecordService.findPage(new Page<OaFlowAuditRecord>(request, response), oaFlowAuditRecord); 
		model.addAttribute("page", page);
		return "modules/oa/oaFlowAuditRecordList";
	}

	@RequiresPermissions("oa:oaFlowAuditRecord:view")
	@RequestMapping(value = "form")
	public String form(OaFlowAuditRecord oaFlowAuditRecord, Model model) {
		model.addAttribute("oaFlowAuditRecord", oaFlowAuditRecord);
		return "modules/oa/oaFlowAuditRecordForm";
	}

	@RequiresPermissions("oa:oaFlowAuditRecord:edit")
	@RequestMapping(value = "save")
	public String save(OaFlowAuditRecord oaFlowAuditRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaFlowAuditRecord)){
			return form(oaFlowAuditRecord, model);
		}
		oaFlowAuditRecordService.save(oaFlowAuditRecord);
		addMessage(redirectAttributes, "保存流程审核记录成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFlowAuditRecord/?repage";
	}
	
	@RequiresPermissions("oa:oaFlowAuditRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(OaFlowAuditRecord oaFlowAuditRecord, RedirectAttributes redirectAttributes) {
		oaFlowAuditRecordService.delete(oaFlowAuditRecord);
		addMessage(redirectAttributes, "删除流程审核记录成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaFlowAuditRecord/?repage";
	}

}