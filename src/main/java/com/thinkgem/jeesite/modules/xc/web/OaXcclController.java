/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xc.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.thinkgem.jeesite.modules.sys.entity.Office;
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
import com.thinkgem.jeesite.modules.xc.entity.OaXccl;
import com.thinkgem.jeesite.modules.xc.service.OaXcclService;

import java.util.List;

/**
 * 巡查处理记录Controller
 * @author oa
 * @version 2023-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xc/oaXccl")
public class OaXcclController extends BaseController {

	@Autowired
	private OaXcclService oaXcclService;
	
	@ModelAttribute
	public OaXccl get(@RequestParam(required=false) String id) {
		OaXccl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaXcclService.get(id);
		}
		if (entity == null){
			entity = new OaXccl();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(OaXccl oaXccl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaXccl> page = oaXcclService.findPage(new Page<OaXccl>(request, response), oaXccl); 
		model.addAttribute("page", page);
		return "modules/xc/oaXcclList";
	}

	@RequestMapping(value = {"notReportDeptList"})
	public String notReportDeptList(String queryDate, Model model) {
		if(StrUtil.isBlank(queryDate)) {
			queryDate = DateUtil.today();
		}
		List<Office> list = oaXcclService.notReportDeptList(queryDate);
		model.addAttribute("list", list);
		model.addAttribute("queryDate", queryDate);
		return "modules/xc/notReportList";
	}

	@RequestMapping(value = "form")
	public String form(OaXccl oaXccl, Model model) {
		model.addAttribute("oaXccl", oaXccl);
		return "modules/xc/oaXcclForm";
	}

	@RequestMapping(value = "save")
	public String save(OaXccl oaXccl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaXccl)){
			return form(oaXccl, model);
		}
		oaXcclService.save(oaXccl);
		addMessage(redirectAttributes, "保存巡查处理记录成功");
		return "redirect:"+Global.getAdminPath()+"/xc/oaXccl/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(OaXccl oaXccl, RedirectAttributes redirectAttributes) {
		oaXcclService.delete(oaXccl);
		addMessage(redirectAttributes, "删除巡查处理记录成功");
		return "redirect:"+Global.getAdminPath()+"/xc/oaXccl/?repage";
	}

}