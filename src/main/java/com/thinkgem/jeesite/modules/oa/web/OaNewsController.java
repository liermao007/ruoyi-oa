/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.service.OaNewsService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.MSG;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 新闻公告Controller
 *
 * @author oa
 * @version 2016-11-17
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNews")
public class OaNewsController extends BaseController {

    @Autowired
    private OaNewsService oaNewsService;



    @ModelAttribute
    public OaNews get(@RequestParam(required = false) String id) {
        OaNews entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = oaNewsService.get(id);
        }
        if (entity == null) {
            entity = new OaNews();
        }
        return entity;
    }

    @RequiresPermissions("oa:oaNews:view")
    @RequestMapping(value = {"list", ""})
    public String list(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {
        String companyid = UserUtils.getUser().getCompany().getId();
        oaNews.setCompanyId(companyid);
        oaNews.setCreateBy(UserUtils.getUser());
        long listAmount = 0;
        Page<OaNews> page = oaNewsService.findMyWriteNews(new Page<OaNews>(request, response), oaNews);
        listAmount = page.getCount();
        model.addAttribute("listAmount", listAmount);
        model.addAttribute("page", page);
        return "modules/oa/oaNewsList";
    }

    /**
     * 上拉获取更多新闻公告
     *
     * @param oaNews
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getNoticeMore")
    @ResponseBody
    public List<Map> getNoticeMore(OaNews oaNews, @RequestParam(value = "pageNo", required = true) int pageNo, @RequestParam(value = "pageSize", required = true) int pageSize) {
        String companyId = UserUtils.getUser().getCompany().getId();
        oaNews.setCompanyId(companyId);
        oaNews.setCreateBy(UserUtils.getUser());
        Page<OaNews> page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        List<Map> list = new ArrayList<>();
        list = oaNewsService.findTouchNews(page, oaNews, list);
        return list;
    }

    @RequestMapping(value = "saveContractFile")
    @ResponseBody
    public String saveContractFile(@RequestParam("file") MultipartFile file, String name, String signName,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String path = "";
        if (StringUtils.isNotBlank(file.getOriginalFilename())) {
            path = oaNewsService.saveContractFile(file, name, signName, request);
        }
        return path;
    }

    /**
     * 更多时查看
     *
     * @param oaNews
     * @param
     * @param
     * @param model
     * @return
     */

    @RequestMapping(value = "more")
    public String more(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNews.setAuditFlag("1");
        oaNews.setSelf(true);
        oaNews.setCompanyId(UserUtils.getUser().getCompany().getId());
        Page<OaNews> page = oaNewsService.findPage(new Page<OaNews>(request, response), oaNews);
        model.addAttribute("page", page);
        return "modules/oa/myOaNewsList";


    }

    @RequiresPermissions("oa:oaNews:view")
    @RequestMapping(value = "form")
    public String form(OaNews oaNews, Model model) {
        if (StringUtils.isNotBlank(oaNews.getId())) {
            oaNews = oaNewsService.getRecordList(oaNews);
        }
        if (StringUtils.isNotBlank(oaNews.getFiles())) {
            oaNews.setFileName(oaNews.getFiles().substring(oaNews.getFiles().lastIndexOf("/") + 1));
        }
        if (oaNews == null || oaNews.getAuditFlag() == null) {
            oaNews.setAuditFlag("0");
        }
        if (oaNews == null || oaNews.getIsTopic() == null) {
            oaNews.setIsTopic("0");
        }
        model.addAttribute("oaNews", oaNews);
        return "modules/oa/oaNewsForm";
    }

    @RequiresPermissions("oa:oaNews:edit")
    @RequestMapping(value = "save")
    public String save(OaNews oaNews, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, oaNews)) {
            return form(oaNews, model);
        }
        if (StringUtils.equalsIgnoreCase(oaNews.getAuditFlag(), "2")) {
            oaNews.setAuditFlag("0");
        }
        String companyId = UserUtils.getUser().getCompany().getId();
        oaNews.setCompanyId(companyId);
        if (StrUtil.isBlank(oaNews.getAuditMan())) {
            oaNews.setAuditFlag("1");
            oaNewsService.save(oaNews);
        } else {
            oaNewsService.save(oaNews);
        }

        addMessage(redirectAttributes, "保存新闻公告成功");
        return "redirect:" + Global.getAdminPath() + "/oa/oaNews/?repage";
    }

    @RequiresPermissions("oa:oaNews:edit")
    @RequestMapping(value = "toUp")
    public String toUp(String id, String type, HttpServletRequest request, HttpServletResponse response, Model model) {
        OaNews oaNews = get(id);
        oaNews.setIsTopic(type);
        oaNewsService.save(oaNews);
        return "redirect:" + Global.getAdminPath() + "/oa/oaNews/?repage";
    }

    @RequiresPermissions("oa:oaNews:edit")
    @RequestMapping(value = "delete")
    public String delete(OaNews oaNews, RedirectAttributes redirectAttributes) {
        oaNewsService.delete(oaNews);
        addMessage(redirectAttributes, "删除新闻公告成功");
        return "redirect:" + Global.getAdminPath() + "/oa/oaNews/?repage";
    }

    /**
     * 根据ID获取审核新闻信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "getAuditNews")
    public String getAuditNews(String id, Model model) {
        OaNews oaNews = get(id);
        if (oaNews.getAuditMan() != "") {
            oaNews.setAuditManName(UserUtils.getOne(oaNews.getAuditMan()).getName());
        }
        oaNews.setCreateManName(UserUtils.getOne(oaNews.getCreateBy().getId()).getName());
        model.addAttribute("oaNews", oaNews);
        return "modules/oa/oaNewsView";
    }


    /**
     * 根据ID获取审核新闻信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "getMyAuditNews")
    public String getMyAuditNews(String id, Model model) {
        OaNews oaNews = get(id);
        if (oaNews.getAuditMan() != "") {
            User user = UserUtils.getOne(oaNews.getAuditMan());
            oaNews.setAuditManName(user != null ? user.getName() : "");
        }
        oaNews.setCreateManName(UserUtils.getOne(oaNews.getCreateBy().getId()).getName());
        //手机端已读新闻不显示
        oaNews.setUserId(UserUtils.getUser().getId());
        //在点击查看新发起的新闻时，更新阅读的状态，表示已阅读
        oaNewsService.updateReadFlag(oaNews);
        oaNewsService.update(oaNews);
        try {
            JSONArray jsonArray = new JSONArray();
            String  infoSzie = addJsonValue()+","+oaNews.getId()+","+UserUtils.getUser().getId();
            String idInfo = UUID.randomUUID().toString();
            String json = jsonArray.toString();
            MSG msg = new MSG();
            msg.setId(idInfo);
            msg.setType("topic");
            msg.setDestination("update_oaNews_flag");
            msg.setBody(json.getBytes());
//                amqMsgDao.saveMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("oaNews", oaNews);
        return "modules/oa/myOaNewsView";
    }

    /**
     * 获取未读邮件的个数
     *
     * @param
     * @return
     */

    public String addJsonValue() {
        String jsonObject = new String();
        OaNews oaNews = new OaNews();
        oaNews.setAuditFlag("1");
        oaNews.setReadFlag("0");
        oaNews.setSelf(true);
        List<OaNews> delete = oaNewsService.findListByWork(oaNews);
        if (delete != null) {
            jsonObject = String.valueOf(delete.size());
        }
        return jsonObject;
    }

    /**
     * @param id
     * @param auditFlag
     * @return
     */
    @RequestMapping(value = "auditNews")
    public String auditNews(String id, String auditFlag) {
        OaNews oaNews = get(id);
        oaNews.setAuditFlag(auditFlag);
        oaNewsService.update(oaNews);
        return "modules/sys/main1";
//        return "redirect:" + adminPath + "";
    }


    /**
     * 我的新闻列表
     */
    @RequiresPermissions("oa:oaNews:view")
    @RequestMapping(value = "self")
    public String selfList(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNews.setAuditFlag("1");
        oaNews.setSelf(true);
        oaNews.setCompanyId(UserUtils.getUser().getCompany().getId());
        long listAmount = 0;
        Page<OaNews> page = oaNewsService.findPage(new Page<OaNews>(request, response), oaNews);
        listAmount = page.getCount();
        model.addAttribute("listAmount", listAmount);
        model.addAttribute("page", page);
        return "modules/oa/myOaNewsList";
    }

    /**
     * 手机端新闻上拉加载功能
     *
     * @param oaNews
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getNewsMore")
    @ResponseBody
    public List<Map> getNewsMore(OaNews oaNews, @RequestParam(value = "pageNo", required = false) int pageNo, @RequestParam(value = "pageSize", required = false) int pageSize) {
        oaNews.setCompanyId(UserUtils.getUser().getCompany().getId());
        if (StringUtils.equals(oaNews.getAuditFlag(), "1")) {
            oaNews.setSelf(true);
        } else if (StringUtils.equals(oaNews.getAuditFlag(), "0")) {
            User user = UserUtils.getUser();
            oaNews.setAuditMan(user.getId());
        }
        Page<OaNews> page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        List<Map> list = new ArrayList<>();
        list = oaNewsService.getNewsMore(page, oaNews, list);
        return list;
    }

    /**
     * OA手机端新闻公告
     */
    @RequiresPermissions("oa:oaNews:view")
    @RequestMapping(value = "selfNews")
    public String selfNews(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {

        return "modules/oa/oaSelfNews";
    }



    /**
     * 首页中查看新闻公告
     *
     * @param oaNews
     * @param
     * @param
     * @param model
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "oaNewsList")
    public List<OaNews> oaNewsList(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNews.setAuditFlag("1");
        oaNews.setSelf(true);
        oaNews.setCompanyId(UserUtils.getUser().getCompany().getId());
        List<OaNews> list = oaNewsService.findList(oaNews);
        return list;
    }


    /**
     * 获取当前用户可审核的新闻公告,首页中查看待审新闻公告
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "auditNewsList")
    public  List<OaNews> getAuditNews(){
        User user = UserUtils.getUser();
        if(user != null){
            OaNews news = new OaNews();
            news.setAuditMan(user.getId());
            news.setAuditFlag("0");
            String companyid=  UserUtils.getUser().getCompany().getId();
            news.setCompanyId(companyid);
            // news.setCompanyId(companyid);

            //以前的新闻公告审核（查出俩条的）
            //List<OaNews> newsList = newsDao.findListAndRole(news);
            List<OaNews> newsList = oaNewsService.findList(news);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }

}