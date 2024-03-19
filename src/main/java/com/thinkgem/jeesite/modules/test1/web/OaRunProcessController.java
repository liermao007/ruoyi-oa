/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test1.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.entity.User;
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
import com.thinkgem.jeesite.modules.test1.entity.OaRunProcess;
import com.thinkgem.jeesite.modules.test1.service.OaRunProcessService;

/**
 * 已办流程Controller
 * @author oa
 * @version 2017-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/test1/oaRunProcess")
public class OaRunProcessController extends BaseController {

    @Autowired
    private OaRunProcessService oaRunProcessService;
    @Autowired
    private SystemService systemService;

    @ModelAttribute
    public OaRunProcess get(@RequestParam(required=false) String id) {
        OaRunProcess entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = oaRunProcessService.get(id);
        }
        if (entity == null){
            entity = new OaRunProcess();
        }
        return entity;
    }

    @RequiresPermissions("test1:oaRunProcess:view")
    @RequestMapping(value = {"list", ""})
    public String list(OaRunProcess oaRunProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaRunProcess.setOrgId(UserUtils.getUser().getCompany().getId());
        oaRunProcess.setByFlag("1");
        Page<OaRunProcess> page = oaRunProcessService.findPage(new Page<OaRunProcess>(request, response), oaRunProcess);
        model.addAttribute("page", page);
        return "modules/test1/oaRunList";
    }

    @RequiresPermissions("test1:oaRunProcess:view")
    @RequestMapping(value = "form")
    public String form(OaRunProcess oaRunProcess, Model model) {
        oaRunProcess.setOrgId(UserUtils.getUser().getCompany().getId());
        oaRunProcess.setByFlag("1");
        oaRunProcess.setOldAgentid(oaRunProcess.getAgentid());
        model.addAttribute("oaRunProcess", oaRunProcess);
        return "modules/test1/oaRunProcessList";
    }
    @RequiresPermissions("test1:oaRunProcess:view")
    @RequestMapping(value ="list1")
    public String list1(OaRunProcess oaRunProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaRunProcess.setOrgId(UserUtils.getUser().getCompany().getId());
        oaRunProcess.setByFlag("0");
        Page<OaRunProcess> page = oaRunProcessService.findPage(new Page<OaRunProcess>(request, response), oaRunProcess);
        model.addAttribute("page", page);
        return "modules/test1/oaRunList1";
    }

    @RequiresPermissions("test1:oaRunProcess:view")
    @RequestMapping(value = "form1")
    public String form1(OaRunProcess oaRunProcess, Model model) {
        oaRunProcess.setOrgId(UserUtils.getUser().getCompany().getId());
        oaRunProcess.setByFlag("0");
        oaRunProcess.setOldAgentid(oaRunProcess.getAgentid());
        model.addAttribute("oaRunProcess", oaRunProcess);
        return "modules/test1/oaRunProcessList1";
    }

    @RequestMapping(value = "selectCheck")
    public String selectCheck(OaRunProcess oaRunProcess, Model model) {
        String flag = oaRunProcess.getByFlag();
        if(oaRunProcess.getAgentid() !=null && oaRunProcess.getAgentid().contains(",")){
            oaRunProcess.setAgentid(oaRunProcess.getAgentid().split(",")[1]);
        }
        if(oaRunProcessService.selectCheck(oaRunProcess) != null){
            OaRunProcess oaRunProcess1 = oaRunProcessService.selectCheck(oaRunProcess);
            oaRunProcess1.setByFlag(flag);
            model.addAttribute("oaRunProcess", oaRunProcess1);
        }else{
            model.addAttribute("oaRunProcess", oaRunProcess);
        }
       if(flag.equals("1")){
           return "modules/test1/oaRunProcessList";
       }else{
           return "modules/test1/oaRunProcessList1";
       }

    }

    @RequiresPermissions("test1:oaRunProcess:edit")
    @RequestMapping(value = "save")
    public String save(OaRunProcess oaRunProcess, Model model, RedirectAttributes redirectAttributes) {
        String flag=oaRunProcess.getByFlag();
        //获取被评阅人的所有id并根据逗号进行拆分
        String[] ids = null;
        String[] idss = null;
        if (!(oaRunProcess.getPrincipalid().indexOf(",") != -1) && (oaRunProcess.getAgentid().indexOf(",") != -1)) {
            String evaluateById = oaRunProcess.getPrincipalid() + ",";
            ids = evaluateById.split(",");
            idss = oaRunProcess.getAgentid().split(",");
        }
        if (!(oaRunProcess.getAgentid().indexOf(",") != -1) && (oaRunProcess.getPrincipalid().indexOf(",") != -1)) {
            String evaluateId = oaRunProcess.getPrincipalid() + ",";
            ids = evaluateId.split(",");
            idss = oaRunProcess.getAgentid().split(",");
        }
        if ((oaRunProcess.getAgentid().indexOf(",") != -1) && (oaRunProcess.getPrincipalid().indexOf(",") != -1)) {
            ids = oaRunProcess.getPrincipalid().split(",");
            idss = oaRunProcess.getAgentid().split(",");
        }
        if (!(oaRunProcess.getAgentid().indexOf(",") != -1) && !(oaRunProcess.getPrincipalid().indexOf(",") != -1)) {
            String evaluateId = oaRunProcess.getAgentid() + ",";
            String evaluateById = oaRunProcess.getPrincipalid() + ",";
            ids = evaluateById.split(",");
            idss = evaluateId.split(",");
        }
        if(oaRunProcess.getOldAgentid() == null || oaRunProcess.getOldAgentid().equals("")){
            oaRunProcess.setOldAgentid(oaRunProcess.getAgentid());
        }
        oaRunProcessService.delete(oaRunProcess);
        for (int i = 0; i < idss.length; i++) {
            for (int j = 0; j < ids.length; j++) {
                OaRunProcess oaRunProcess2 = new OaRunProcess();
                oaRunProcess2.setAgentid(idss[i]);
                oaRunProcess2.setPrincipalid(ids[j]);
                oaRunProcess2.setByFlag(flag);
                OaRunProcess oa = oaRunProcessService.get(oaRunProcess2);
                if (oa == null) {
                    OaRunProcess oaRunProcess1 = new OaRunProcess();
                    oaRunProcess1.setAgent(systemService.getUser(idss[i]).getLoginName());
                    oaRunProcess1.setPrincipal(systemService.getUser(ids[j]).getLoginName());
                    oaRunProcess1.setPrincipalid(ids[j]);
                    oaRunProcess1.setAgentid(idss[i]);
                    oaRunProcess1.setByFlag(flag);
                    oaRunProcess1.setOrgId(UserUtils.getUser().getCompany().getId());
                    oaRunProcessService.save(oaRunProcess1);
                }
            }
        }
        addMessage(redirectAttributes, "保存已办流程成功");
        if(flag.equals("1")){
            return "redirect:"+Global.getAdminPath()+"/test1/oaRunProcess/?repage";
        }else{
            return "redirect:"+Global.getAdminPath()+"/test1/oaRunProcess/list1?repage";
        }

    }

    @RequiresPermissions("test1:oaRunProcess:edit")
    @RequestMapping(value = "delete")
    public String delete(OaRunProcess oaRunProcess, RedirectAttributes redirectAttributes) {
        String flag=oaRunProcess.getByFlag();
        oaRunProcess.setOldAgentid(oaRunProcess.getAgentid());
        oaRunProcessService.delete(oaRunProcess);
        addMessage(redirectAttributes, "删除已办流程成功");
        if(flag.equals("1")){
            return "redirect:"+Global.getAdminPath()+"/test1/oaRunProcess/?repage";
        }else{
            return "redirect:"+Global.getAdminPath()+"/test1/oaRunProcess/list1?repage";
        }
    }

    /**
     * 查询代理人
     *
     * @param oaRunProcess
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "findById")
    public String findById(OaRunProcess oaRunProcess, Model model, RedirectAttributes redirectAttributes, String id) throws Exception {
        oaRunProcess = oaRunProcessService.findById(id);
        oaRunProcess.setPrincipal(id);
        model.addAttribute("oaRunProcess", oaRunProcess);
        return "modules/oa/oaRunProcessList";
    }

}