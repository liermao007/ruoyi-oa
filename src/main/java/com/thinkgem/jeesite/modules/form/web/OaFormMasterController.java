/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.act.utils.ProcessDefUtils;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.Components;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaControlTypeService;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑器设计表单Controller
 *
 * @author oa
 * @version 2016-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/form/oaFormMaster")
public class OaFormMasterController extends BaseController {

    @Autowired
    private OaFormMasterService oaFormMasterService;

    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;

    @Autowired
    private RoleDao roleDao;

    @ModelAttribute
    public OaFormMaster get(@RequestParam(required = false) String id) {
        OaFormMaster entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = oaFormMasterService.get(id);
        }
        if (entity == null) {
            entity = new OaFormMaster();
        }
        return entity;
    }

    @RequiresPermissions("form:oaFormMaster:view")
    @RequestMapping(value = {"list", ""})
    public String list(OaFormMaster oaFormMaster, HttpServletRequest request, HttpServletResponse response, Model model) {
            if(UserUtils.getUser().getId().equals("1")){
                    Office office =new Office();
                    office.setId("1");
                    oaFormMaster.setOffice(office);
            }else{
                Office office =new Office();
                office.setId(UserUtils.getUser().getCompany().getId());
                oaFormMaster.setOffice(office);
            }

        Page<OaFormMaster> page = oaFormMasterService.findPage(new Page<OaFormMaster>(request, response), oaFormMaster);
        model.addAttribute("page", page);
        return "modules/form/oaFormMasterList";
    }

    @RequiresPermissions("form:oaFormMaster:view")
    @RequestMapping(value = "form")
    public String form(OaFormMaster oaFormMaster, Model model) {
        model.addAttribute("oaFormMaster", oaFormMaster);
        return "modules/form/oaFormMasterForm";
    }

    @RequiresPermissions("form:oaFormMaster:edit")
    @RequestMapping(value = "save")
    public String save(OaFormMaster oaFormMaster, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, oaFormMaster)) {
            return form(oaFormMaster, model);
        }
        if (oaFormMaster.getContent() != null) {
            oaFormMaster.setContent(StringEscapeUtils.unescapeHtml4(oaFormMaster.getContent()));
        }
        if(UserUtils.getUser().getId().equals("1")){
            Office office =new Office();
            office.setId("1");
            oaFormMaster.setOffice(office);
        }else{
            Office office =new Office();
            office.setId(UserUtils.getUser().getCompany().getId());
            oaFormMaster.setOffice(office);
        }
        oaFormMasterService.save(oaFormMaster);
        addMessage(redirectAttributes, "保存编辑器设计表单成功");
        return "redirect:" + Global.getAdminPath() + "/form/oaFormMaster/?repage";
    }

    @RequiresPermissions("form:oaFormMaster:edit")
    @RequestMapping(value = "delete")
    public String delete(OaFormMaster oaFormMaster, RedirectAttributes redirectAttributes) {
        oaFormMasterService.delete(oaFormMaster);
        addMessage(redirectAttributes, "删除编辑器设计表单成功");
        return "redirect:" + Global.getAdminPath() + "/form/oaFormMaster/?repage";
    }

    /**
     * 验证表单编号是否有效
     *
     * @param oldFormNo
     * @param formNo
     * @return
     */
    @ResponseBody
    @RequiresPermissions("form:oaFormMaster:edit")
    @RequestMapping(value = "checkFormNo")
    public String checkFormNo(String oldFormNo, String formNo) {
        if (formNo != null && formNo.equals(oldFormNo)) {
            return "true";
        } else if (formNo != null && oaFormMasterService.findByNo(formNo, null) == null) {
            return "true";
        }
        return "false";
    }

    @ResponseBody
    @RequestMapping(value = "findForm")
    public OaFormMaster findForm(String form) {
        if (StringUtils.equalsIgnoreCase(form, "1")) {
            form = "入职申请单";
        }
        OaFormMaster oaFormMaster = oaFormMasterService.findForm(form);
        List<Object[]> processList = ProcessDefUtils.processList(null);
        for (Object[] objs : processList) {
            ProcessDefinition process = (ProcessDefinition) objs[0];
            if (StringUtils.equalsIgnoreCase(process.getName(), "入职申请单")) {
                oaFormMaster.setAlias(process.getId());
            }
        }
        return oaFormMaster;
    }
}