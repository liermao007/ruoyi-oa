/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.act.utils.ProcessDefUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.activiti.engine.repository.ProcessDefinition;
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
import com.thinkgem.jeesite.modules.sys.entity.ActCountInfo;
import com.thinkgem.jeesite.modules.sys.service.ActCountInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程信息汇Controller
 * @author oa
 * @version 2017-09-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/actCountInfo")
public class ActCountInfoController extends BaseController {

	@Autowired
	private ActCountInfoService actCountInfoService;
	
	@ModelAttribute
	public ActCountInfo get(@RequestParam(required=false) String id) {
		ActCountInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = actCountInfoService.get(id);
		}
		if (entity == null){
			entity = new ActCountInfo();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(ActCountInfo actCountInfo,String procdefId,HttpServletRequest request, HttpServletResponse response, Model model) {

        if(StringUtils.isNotBlank(actCountInfo.getProcDefId())){
            actCountInfo.setProcDefId("'"+StringUtils.substringBefore(actCountInfo.getProcDefId(),":")+"'");
        }else{
            actCountInfo.setProcDefId(procdefId);
        }
        actCountInfo.setCompanyId(UserUtils.getUser().getCompany().getId());
        String cate = null;
        List<Object[]> processList = ProcessDefUtils.processList(cate);
        ArrayList list=new ArrayList();
        for (Object[] objs : processList) {
            ProcessDefinition  process = (ProcessDefinition) objs[0];
            list.add(process);
        }
        if(StringUtils.isNotBlank(actCountInfo.getLoginName())){
            User user =  UserUtils.get(actCountInfo.getLoginName());
            actCountInfo.setLoginName(user.getLoginName());
        }
        Page<ActCountInfo> page = actCountInfoService.findPage(new Page<ActCountInfo>(request, response), actCountInfo);
		model.addAttribute("page", page);
        model.addAttribute("processList", list);
        model.addAttribute("actCountInfo", actCountInfo);

		return "modules/sys/actCountInfoList";
	}

	@RequiresPermissions("sys:actCountInfo:view")
	@RequestMapping(value = "form")
	public String form(ActCountInfo actCountInfo, Model model) {
		model.addAttribute("actCountInfo", actCountInfo);
		return "modules/sys/actCountInfoForm";
	}

	@RequiresPermissions("sys:actCountInfo:edit")
	@RequestMapping(value = "save")
	public String save(ActCountInfo actCountInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, actCountInfo)){
			return form(actCountInfo, model);
		}
		actCountInfoService.save(actCountInfo);
		addMessage(redirectAttributes, "保存流程信息汇成功");
		return "redirect:"+Global.getAdminPath()+"/sys/actCountInfo/?repage";
	}
	
	@RequiresPermissions("sys:actCountInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ActCountInfo actCountInfo, RedirectAttributes redirectAttributes) {
		actCountInfoService.delete(actCountInfo);
		addMessage(redirectAttributes, "删除流程信息汇成功");
		return "redirect:"+Global.getAdminPath()+"/sys/actCountInfo/?repage";
	}

}