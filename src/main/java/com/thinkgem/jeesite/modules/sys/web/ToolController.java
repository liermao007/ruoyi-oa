/**
 *
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefCache;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.service.MailInfoService;
import com.thinkgem.jeesite.modules.oa.service.OaNewsService;
import com.thinkgem.jeesite.modules.oa.service.OaScheduleService;
import com.thinkgem.jeesite.modules.process.dao.CustomRuskTaskinstDao;
import com.thinkgem.jeesite.modules.process.entity.CustomRuskTaskinst;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 字典Controller
 *
 * @author oa
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/service/tool")
public class ToolController extends BaseController {

    @Autowired
    private OaScheduleService oaScheduleService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private OaNewsService oaNewsService;

    @Autowired
    private MailInfoService mailInfoService;

    @Autowired
    private ActTaskService actTaskService;

    @Autowired
    private CustomRuskTaskinstDao customRuskTaskinstDao;

    @ModelAttribute
    public OaSchedule get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return oaScheduleService.get(id);
        } else {
            return new OaSchedule();
        }
    }

    /**
     * 查询代办流程
     *
     * @param userId   用户id  :通过用户ID查找到用户的loginName，之后查找对应的代办流程
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getActTaskList")
    @ResponseBody
    public List<Act> list(@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
        User u = systemService.getUser(userId);
        if (u == null || (u != null && StringUtils.isBlank(u.getLoginName()))) {
            return null;
        }
        List<Act> acts = actTaskService.todoList(new Act(), u.getLoginName());
        return acts;
    }

    /**
     * 查询代办自由流程
     *
     * @param userId   用户id  :通过用户ID查找到用户的loginName，之后查找对应的代办自由流程
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getCustomTaskList")
    @ResponseBody
    public List<CustomRuskTaskinst> findCustomList(@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
        User u = systemService.getUser(userId);
        if (u == null || (u != null && StringUtils.isBlank(u.getLoginName()))) {
            return null;
        }
        CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
        customRuskTaskinst.setAssignee(u.getCardNo());
        customRuskTaskinst.setFlagAudit("0");
        List<CustomRuskTaskinst> newsList = customRuskTaskinstDao.findList(customRuskTaskinst);
        return newsList;
    }

    /**
     * 查询代办日程
     *
     * @param userId   用户id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getOaSchedules")
    @ResponseBody
    public List<OaSchedule> oaScheduleList(@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
        OaSchedule oaSchedule = new OaSchedule();
        oaSchedule.setLoginId(userId);
        oaSchedule.setFlag("0");
        oaSchedule.setScheduleDate(new Date());
        List<OaSchedule> l = oaScheduleService.findList(oaSchedule);
        return l;
    }

    /**
     * 查询未读邮件
     *
     * @param userId   用户id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getMailInfos")
    @ResponseBody
    public List<MailInfo> mailInfoList(@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setReadMark("0");
        mailInfo.setOwnId(userId);
        mailInfo.setState("INBOX");
        List<MailInfo> mailInfos = mailInfoService.findList(mailInfo);
        return mailInfos;
    }



    /**
     * 查询未审核新闻通知
     *
     * @param userId   用户id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getNews")
    @ResponseBody
    public List<OaNews> getNews(@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(userId)) {
            OaNews news = new OaNews();
            news.setAuditMan(userId);
            news.setAuditFlag("0");
            List<OaNews> newsList = oaNewsService.findList(news);
            if (newsList == null) {
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return null;
    }


    /**
     * 分页查询起始时间到现在的新闻通知
     *
     * @param request
     * @param response
     * @param model
     * @retur/0.。000000000.0.0n
     */
    @RequestMapping("getNewsList")
    @ResponseBody
    public List<OaNews> getNewsList(@RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "updateDate", required = false) String updateDate, HttpServletRequest request, HttpServletResponse response, Model model) {
        User u = systemService.getUser(userId);
        if (u == null || (u != null && StringUtils.isBlank(u.getLoginName()))) {
            return null;
        }
        OaNews oaNews = new OaNews();
        oaNews.setUserId(userId);
        oaNews.setAuditFlag("1");
        oaNews.setSelf(false);
//        oaNews.setUpdateDate(DateUtils.parseDate(updateDate));
        oaNews.setReadFlag("0");
        Page<OaNews> page = oaNewsService.findListByWork(new Page<OaNews>(request, response), oaNews);
        if (page.getList() != null && page.getList().size() > 0) {
            return page.getList();
        }
        return null;
    }



    /**
     * 查询无直属领导的员工
     */
    @RequestMapping(value = "noLeaderList")
    @ResponseBody
    public List<User> noLeaderList(@RequestParam(value = "orgId", required = false) String orgId, HttpServletRequest request, HttpServletResponse response, Model model) {
        //无直属领导人员
        User user = new User();
        user.setCompanyId(orgId);
        List<User> users = systemService.findPeopleNoLeader(orgId, user);
        return users;
    }

    /**
     * 修改无直属领导的员工
     */
    @RequestMapping(value = "updatePeopleNoDept")
    @ResponseBody
    public String updatePeopleNoDept(@RequestParam(value = "jsonArray", required = false) JSONArray jsonArray,
                                     @RequestParam(value = "orgId", required = false) String orgId,
                                     HttpServletRequest request, HttpServletResponse response, Model model) {
        StringBuilder stringBuilder = new StringBuilder();
        String result = stringBuilder.toString();
        for (int i = 0; i < (jsonArray.size()); i++) {
            if (i < (jsonArray.size()) - 1) {
                stringBuilder = stringBuilder.append(jsonArray.get(i).toString()).append(",");
            } else {
                stringBuilder = stringBuilder.append(jsonArray.get(i).toString());
            }
        }
        int length = jsonArray.size() / 4;
        JSONArray jsonArray1 = JSONArray.fromObject(stringBuilder.toString());
        String[] ids = systemService.convertList(jsonArray1, "id", length);
        String[] leaders = systemService.convertList(jsonArray1, "leader", length);
        String[] depts = systemService.convertList(jsonArray1, "dept", length);
        String[] levels = systemService.convertList(jsonArray1, "level", length);
        Integer count = systemService.updateNoDept(ids, orgId, leaders, depts, levels);
        if (count != null && count > 0) {
            return "1";
        } else {
            return "0";
        }
    }
}
