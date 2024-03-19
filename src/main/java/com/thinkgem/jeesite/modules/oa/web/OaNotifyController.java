/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.thinkgem.jeesite.common.Result;
import com.thinkgem.jeesite.common.entity.Files;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifySaveReq;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;

import java.io.FileOutputStream;
import java.util.List;

/**
 * 通知通告Controller
 * @author oa
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController extends BaseController {

    @Autowired
    private OaNotifyService oaNotifyService;

    @ModelAttribute
    public OaNotify get(@RequestParam(required=false) String id) {
        OaNotify entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = oaNotifyService.get(id);
        }
        if (entity == null){
            entity = new OaNotify();
        }
        return entity;
    }

    @RequiresPermissions("oa:oaNotify:view")
    @RequestMapping(value = {"list", ""})
    public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
        String companyid=  UserUtils.getUser().getCompany().getId();
        oaNotify.setCompanyId(companyid);
        oaNotify.setCreateBy(UserUtils.getUser());
        Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
        model.addAttribute("page", page);
        return "modules/oa/oaNotifyList";
    }

    @RequiresPermissions("oa:oaNotify:view")
    @RequestMapping(value = "form")
    public String form(OaNotify oaNotify, Model model) {
        if (StringUtils.isNotBlank(oaNotify.getId())){
            oaNotify = oaNotifyService.getRecordList(oaNotify);
        }
        model.addAttribute("oaNotify", oaNotify);
        return "modules/oa/oaNotifyForm";
    }

    @RequiresPermissions("oa:oaNotify:edit")
    @RequestMapping(value = "save")
    public String save(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, oaNotify)){
            return form(oaNotify, model);
        }
        // 如果是修改，则状态为已发布，则不能再进行操作
        if (StringUtils.isNotBlank(oaNotify.getId())){
            OaNotify e = oaNotifyService.get(oaNotify.getId());
            if ("1".equals(e.getStatus())){
                addMessage(redirectAttributes, "已发布，不能操作！");
                return "redirect:" + adminPath + "/oa/oaNotify/form?id="+oaNotify.getId();
            }
        }

        String companyId= UserUtils.getUser().getCompany().getId();
        oaNotify.setCompanyId(companyId);
        oaNotify.setStatus("1");
        oaNotifyService.save(oaNotify);
        addMessage(redirectAttributes, "保存通知'" + oaNotify.getTitle() + "'成功");
        return "redirect:" + adminPath + "/oa/oaNotify/?repage";
    }

    @RequiresPermissions("oa:oaNotify:edit")
    @RequestMapping(value = "delete")
    public String delete(OaNotify oaNotify, RedirectAttributes redirectAttributes) {
        oaNotifyService.delete(oaNotify);
        addMessage(redirectAttributes, "删除通知成功");
        return "redirect:" + adminPath + "/oa/oaNotify/?repage";
    }

    /**
     * 我的通知列表
     */
    @RequestMapping(value = "self")
    public String selfList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNotify.setSelf(true);
        Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
        for (OaNotify notify : page.getList()) {
            if(notify.getCreateBy() != null && notify.getCreateBy().getId() != null) {
                notify.setCreateManName(UserUtils.getUsername(notify.getCreateBy().getId()));
            }
        }
        model.addAttribute("page", page);
        return "modules/oa/oaNotifyList";
    }

    @ResponseBody
    @RequestMapping(value = "selfList")
    public List<OaNotify> oaNewsList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNotify.setSelf(true);
        oaNotify.setReadFlag("0");
        Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
        return page.getList();
    }

    /**
     * 我的通知列表-数据
     */
    @RequiresPermissions("oa:oaNotify:view")
    @RequestMapping(value = "selfData")
    @ResponseBody
    public Page<OaNotify> listData(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNotify.setSelf(true);
        Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
        return page;
    }

    /**
     * 查看我的通知
     */
    @RequestMapping(value = "viewForm")
    public String view(OaNotify oaNotify, Model model) {
        if (StringUtils.isNotBlank(oaNotify.getId())){
            oaNotifyService.updateReadFlag(oaNotify);
            oaNotify = oaNotifyService.getRecordList(oaNotify);
            oaNotify.setCreateManName(UserUtils.get(oaNotify.getCreateBy().getId()).getName());
            model.addAttribute("oaNotify", oaNotify);
            return "modules/oa/oaNotify";
        }
        return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
    }

    /**
     * 查看我的通知
     */
    @RequestMapping(value = "view")
    public String viewList(OaNotify oaNotify, Model model) {
        if (StringUtils.isNotBlank(oaNotify.getId())){
            oaNotifyService.updateReadFlag(oaNotify);
            oaNotify = oaNotifyService.getRecordList(oaNotify);
            model.addAttribute("oaNotify", oaNotify);
            return "modules/oa/oaNotifyForm";
        }
        return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
    }

    /**
     * 查看我的通知-数据
     */
    @RequestMapping(value = "viewData")
    @ResponseBody
    public OaNotify viewData(OaNotify oaNotify, Model model) {
        if (StringUtils.isNotBlank(oaNotify.getId())){
            oaNotifyService.updateReadFlag(oaNotify);
            return oaNotify;
        }
        return null;
    }

    /**
     * 查看我的通知-发送记录
     */
    @RequestMapping(value = "viewRecordData")
    @ResponseBody
    public OaNotify viewRecordData(OaNotify oaNotify, Model model) {
        if (StringUtils.isNotBlank(oaNotify.getId())){
            oaNotify = oaNotifyService.getRecordList(oaNotify);
            return oaNotify;
        }
        return null;
    }

    /**
     * 获取我的通知数目
     */
    @RequestMapping(value = "self/count")
    @ResponseBody
    public String selfCount(OaNotify oaNotify, Model model) {
        oaNotify.setSelf(true);
        oaNotify.setReadFlag("0");
        return String.valueOf(oaNotifyService.findCount(oaNotify));
    }


    @RequestMapping(value = "saveNotice", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody OaNotifySaveReq req) {
        if(CollUtil.isNotEmpty(req.getFiles())) {
            for (Files file : req.getFiles()) {

            }
        }
        return Result.success();
    }
}