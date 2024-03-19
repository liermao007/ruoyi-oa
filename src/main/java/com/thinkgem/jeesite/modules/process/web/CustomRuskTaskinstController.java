/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.process.entity.CustomRuskTaskinst;
import com.thinkgem.jeesite.modules.process.service.CustomRuskTaskinstService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自由流程运行表Controller
 * @author oa
 * @version 2017-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/process/customRuskTaskinst")
public class CustomRuskTaskinstController extends BaseController {

	@Autowired
	private CustomRuskTaskinstService customRuskTaskinstService;
	
	@ModelAttribute
	public CustomRuskTaskinst get(@RequestParam(required=false) String id) {
		CustomRuskTaskinst entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customRuskTaskinstService.get(id);
		}
		if (entity == null){
			entity = new CustomRuskTaskinst();
		}
		return entity;
	}
	
	@RequiresPermissions("process:customRuskTaskinst:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomRuskTaskinst customRuskTaskinst, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomRuskTaskinst> page = customRuskTaskinstService.findPage(new Page<CustomRuskTaskinst>(request, response), customRuskTaskinst);
		model.addAttribute("page", page);
		return "modules/process/customRuskTaskinstList";
	}

	@RequiresPermissions("process:customRuskTaskinst:view")
	@RequestMapping(value = "form")
	public String form(CustomRuskTaskinst customRuskTaskinst, Model model) {
		model.addAttribute("customRuskTaskinst", customRuskTaskinst);
		return "modules/process/customRuskTaskinstForm";
	}

	@RequiresPermissions("process:customRuskTaskinst:edit")
	@RequestMapping(value = "save")
	public String save(CustomRuskTaskinst customRuskTaskinst, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customRuskTaskinst)){
			return form(customRuskTaskinst, model);
		}
		customRuskTaskinstService.save(customRuskTaskinst);
		addMessage(redirectAttributes, "保存自由流程运行表成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customRuskTaskinst/?repage";
	}
	
	@RequiresPermissions("process:customRuskTaskinst:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomRuskTaskinst customRuskTaskinst, RedirectAttributes redirectAttributes) {
		customRuskTaskinstService.delete(customRuskTaskinst);
		addMessage(redirectAttributes, "删除自由流程运行表成功");
		return "redirect:"+ Global.getAdminPath()+"/process/customRuskTaskinst/?repage";
	}


    /**
     * 获取当前用户待办的自由流程，用于在首页显示
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "customRuskTaskList")
    public  List<CustomRuskTaskinst> findCustom(){
        User user = UserUtils.getUser();
        if(user != null){
            CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
            customRuskTaskinst.setAssignee(UserUtils.getUser().getCardNo());
            customRuskTaskinst.setFlagAudit("0");
            List<CustomRuskTaskinst> newsList = customRuskTaskinstService.findList(customRuskTaskinst);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }
}