package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.service.OaNewsService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/collectoaNews")
public class OaCollectNewsController {

    @Autowired
    private OaNewsService oaNewsService;

    @ModelAttribute
    public OaNews get(@RequestParam(required=false) String id) {
        OaNews entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = oaNewsService.get(id);
        }
        if (entity == null){
            entity = new OaNews();
        }
        return entity;
    }

    @RequiresPermissions("oa:collectoaNews:view")
    @RequestMapping(value = {"list", ""})
    public String list(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<OaNews> page = oaNewsService.findPage(new Page<OaNews>(request, response), oaNews);
        model.addAttribute("page", page);
        return "modules/oa/oaCollectNewsList";
    }


    /**
     * 更多时查看
     * @param oaNews
     * @param
     * @param
     * @param model
     * @return
     */
    @RequiresPermissions("oa:collectoaNews:view")
    @RequestMapping(value = "more")
    public String more(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNews.setAuditFlag("1");
        Page<OaNews> page = oaNewsService.findPage(new Page<OaNews>(request, response), oaNews);
        model.addAttribute("page", page);
        return "modules/oa/oaNewsMore";
    }

    @RequiresPermissions("oa:collectoaNews:view")
    @RequestMapping(value = "form")
    public String form(OaNews oaNews, Model model) {
        if(oaNews == null || oaNews.getAuditFlag() == null){
            oaNews.setAuditFlag("0");
        }
        if(oaNews == null || oaNews.getIsTopic() == null){
            oaNews.setIsTopic("0");
        }
        model.addAttribute("oaNews", oaNews);
        return "modules/oa/oaNewsForm";
    }


    /**
     * 根据ID获取审核新闻信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "getAuditNews")
    public String getAuditNews(String id,Model model){
        OaNews oaNews = get(id);
        oaNews.setAuditManName(UserUtils.get(oaNews.getAuditMan()).getName());
        oaNews.setCreateManName(UserUtils.get(oaNews.getCreateBy().getId()).getName());
        model.addAttribute("oaNews", oaNews);
        return "modules/oa/oaNewsView";
    }

    /**
     *
     * @param id
     * @param auditFlag
     * @return
     */
    @RequestMapping(value = "auditNews")
    public String auditNews(String id,String auditFlag){
        OaNews oaNews = get(id);
        oaNews.setAuditFlag(auditFlag);
        oaNewsService.update(oaNews);
        return "modules/sys/main1";
    }
}
