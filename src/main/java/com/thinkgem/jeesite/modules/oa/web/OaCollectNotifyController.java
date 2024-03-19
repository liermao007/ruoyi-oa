package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;
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

/**
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/collectoaNotify")
public class OaCollectNotifyController {


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

    @RequiresPermissions("oa:collectoaNotify:view")
    @RequestMapping(value = {"list", ""})
    public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
        model.addAttribute("page", page);
        return "modules/oa/oaCollectNotifyList";
    }

    @RequiresPermissions("oa:collectoaNotify:view")
    @RequestMapping(value = "form")
    public String form(OaNotify oaNotify, Model model) {
        if (StringUtils.isNotBlank(oaNotify.getId())){
            oaNotify = oaNotifyService.getRecordList(oaNotify);
        }
        model.addAttribute("oaNotify", oaNotify);
        return "modules/oa/oaNotifyForm";
    }


}
