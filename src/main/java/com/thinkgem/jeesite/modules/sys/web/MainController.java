package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.service.OaNewsService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.test1.dao.OaRunProcessDao;
import com.thinkgem.jeesite.modules.test1.entity.OaRunProcess;
import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import com.thinkgem.jeesite.modules.test1.service.UpdatePrincipalInfoService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 首页Controller
 * @author oa
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/main")
public class MainController extends BaseController {

    @Autowired
    private OaNewsService oaNewsService;
    @Autowired
    private OaRunProcessDao oaRunProcessDao;
    @Autowired
    private UpdatePrincipalInfoService updatePrincipalInfoService;

    @RequestMapping(value = "form")
    public String form(Model model) {
        //先判断新闻公告中的所有的置顶是否超过一个星期，
        // 如果超过一个星期，那么将置顶的字段修改为不置顶。
        OaNews oaNews =new OaNews();
        oaNews.setIsTopic("0");
        oaNewsService.updateTopic(oaNews);
        List<OaRunProcess> list = oaRunProcessDao.findById(UserUtils.getUser().getId(),"1");//获取当前用户被代理人的ID集合
        for(int i=0;i<list.size();i++){
            OaRunProcess oaRunProcess = list.get(i);
            oaRunProcess.setPrincipal(UserUtils.get(oaRunProcess.getPrincipalid()).getName());
            list.set(i,oaRunProcess);
        }
        model.addAttribute("list", list);
        List<UpdatePrincipalInfo> list1 = updatePrincipalInfoService.findUpdate(UserUtils.getUser().getId());//获取当前用户代理人的集合
        model.addAttribute("list1", list1);

        String[] strs = updatePrincipalInfoService.find2(UserUtils.getUser().getLoginName());
        List<Act> list2 = new ArrayList<Act>();
        for(String str:strs){
            List<Act> list3 = updatePrincipalInfoService.findUpdate1(str);//获取代理人处理过的集合
            for(int i=0;i<list3.size();i++){
                Act act = list3.get(i);
                if(act.getSFCC().equals(UserUtils.getUser().getLoginName())){
                }else{
                    list3.remove(i);
                    i--;
                    continue;
                }
                Act act1 = updatePrincipalInfoService.findComment(act.getTaskId());
               if (act1 != null){
                   act.setComment(act1.getComment());
                   act.setCSLoginNames(UserUtils.getByLoginName(act.getCSLoginNames()).getName());
                   act.setTaskName(act1.getTaskName());
                   list3.set(i,act);
               }
            }
            list2.addAll(list3);
        }

        model.addAttribute("list2", list2);
        return "modules/sys/main1";
    }

}
