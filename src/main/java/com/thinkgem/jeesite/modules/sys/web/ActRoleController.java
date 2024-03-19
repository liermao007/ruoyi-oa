/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.sys.entity.ActRole;
import com.thinkgem.jeesite.modules.sys.service.ActRoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色流程对应Controller
 * @author oa
 * @version 2018-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/statistics_act_role/actRole")
public class ActRoleController extends BaseController {

	@Autowired
	private ActRoleService actRoleService;

    @Autowired
    private OaFormMasterService oaFormMasterService;
    @Autowired
    private SystemService systemService;
	
	@ModelAttribute
	public ActRole get(@RequestParam(required=false) String id) {
		ActRole entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = actRoleService.get(id);
		}
		if (entity == null){
			entity = new ActRole();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(ActRole actRole, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Role> list = systemService.findAllRole();
        model.addAttribute("list", list);
		return "modules/sys/actRoleList";
	}


	@RequestMapping(value = "form")
	public String form(ActRole actRole, Model model) {
        List<ActRole> actRoleList = actRoleService.findList(actRole);
      String certDlStatusList="";
        for(ActRole actRole1: actRoleList){
            certDlStatusList += actRole1.getActId()+",";
        }
        List<OaFormMaster> list =  oaFormMasterService.findList();
        actRole.setRemarks(UserUtils.getUser().getCompany().getName());
        model.addAttribute("actRole", actRole);
        model.addAttribute("list", list);
        model.addAttribute("certDlStatusList", certDlStatusList);

        return "modules/sys/actRoleForm";
    }


	@RequestMapping(value = "save")
	public String save(ActRole actRole,String roleName, String ids,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, actRole)){
			return form(actRole, model);
		}
        if(StringUtils.isNotBlank(ids)){
        String a []  = ids.split(",");
        for(int i=0;i<a.length;i++) {
            actRole.setActId(a[i]);
            ActRole actRole2 = actRoleService.findByRoleId(actRole);
            if (actRole2 != null) {

                actRoleService.delete(actRole);
                actRoleService.save(actRole);
            } else {
                actRoleService.save(actRole);
            }
        }
        }else{
            actRoleService.delete(actRole);
        }

		addMessage(redirectAttributes, "保存角色流程对应成功");
		return "redirect:"+Global.getAdminPath()+"/statistics_act_role/actRole/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(ActRole actRole, RedirectAttributes redirectAttributes) {
		actRoleService.delete(actRole);
		addMessage(redirectAttributes, "删除角色流程对应成功");
		return "redirect:"+Global.getAdminPath()+"/statistics_act_role/actRole/?repage";
	}

}