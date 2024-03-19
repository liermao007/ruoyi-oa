/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.units.InfoUtils;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.service.OaScheduleService;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 保存日程安排Controller
 * @author oa
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaSchedule")
public class OaScheduleController extends BaseController {

	@Autowired
	private OaScheduleService oaScheduleService;
	
	@ModelAttribute
	public OaSchedule get(@RequestParam(required=false) String id) {
		OaSchedule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaScheduleService.get(id);
		}
		if (entity == null){
			entity = new OaSchedule();
		}
		return entity;
	}

    //查询日程显示到首页
    @RequestMapping(value = "calendarEvents")

    public void  calendarEvents(OaSchedule oaSchedule, HttpServletRequest request, HttpServletResponse response, Model model)throws IOException{
       /* oaSchedule.setSelf(true);
        Page<OaSchedule> page = oaScheduleService.find(new Page<OaSchedule>(request, response), oaSchedule);*/
        List<OaSchedule> agendas=InfoUtils.getSchedules(oaSchedule.getFlag());

        StringBuilder sb = new StringBuilder();
        sb.append("[");


        for(OaSchedule agenda: agendas){
            sb.append("{\"id\":" +'"'+ agenda.getId()+'"'+',');
            sb.append(" ");
            sb.append("\"title\":\"" + agenda.getContent()+""+'"'+",");

            sb.append(" ");

            Date date=agenda.getScheduleDate();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = formatter.format(date);
            sb.append("\"start\":\""   + dateString+'"' );

            sb.append("},");
        }
        sb.setLength(sb.length()-1);
        sb.append("]");
        System.out.println(sb);
            response.setCharacterEncoding("UTF-8");
           String data= sb.toString();
           response.getWriter().write(data);


    }
    /**
     * 查询所有的日程安排
     * @param oaSchedule
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("oa:oaSchedule:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaSchedule oaSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user=UserUtils.getUser();
        oaSchedule.setLoginId(user.getId());
		Page<OaSchedule> page = oaScheduleService.findPage(new Page<OaSchedule>(request, response), oaSchedule); 
		model.addAttribute("page", page);
		return "modules/oa/oaScheduleList";
	}


    /**
     * 我的通知列表
     */
    @RequestMapping(value = "self")
    public String selfList(OaSchedule oaSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaSchedule.setSelf(true);
        Page<OaSchedule> page = oaScheduleService.find(new Page<OaSchedule>(request, response), oaSchedule);
        model.addAttribute("page", page);
        return "modules/oa/oaScheduleList";
    }

	@RequiresPermissions("oa:oaSchedule:view")
	@RequestMapping(value = "form")
	public String form(OaSchedule oaSchedule, Model model) {
        if(oaSchedule.getScheduleDate()==null){
            oaSchedule.setScheduleDate(new Date());
        }

		model.addAttribute("oaSchedule", oaSchedule);
		return "modules/oa/oaScheduleForm";
	}

    //弹出框添加日程
    @RequiresPermissions("oa:oaSchedule:view")
    @RequestMapping(value = "form1")
    public String form1(Date date, Model model, HttpServletRequest request) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(date);
        model.addAttribute("date",dateString);
        return "modules/oa/event";
    }

    //弹出框修改日程
    @RequiresPermissions("oa:oaSchedule:view")
    @RequestMapping(value = "form2")
    public String form2(OaSchedule oaSchedule, Model model, HttpServletRequest request) {
         model.addAttribute("oaSchedule", oaSchedule);
        return "modules/oa/eventedit";
    }

    /**
     *日程安排完成
     * @param oaSchedule
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:oaSchedule:edit")
    @RequestMapping(value = "complete")
    public String complete(OaSchedule oaSchedule, RedirectAttributes redirectAttributes) {
        if (StringUtils.isNotBlank(oaSchedule.getId())){
           oaScheduleService.complete(oaSchedule);
        }
        addMessage(redirectAttributes, "日程安排已完成");
        return "redirect:"+Global.getAdminPath()+"/oa/oaSchedule/?repage";
    }



    /**
     * 保存日程安排
     * @param oaSchedule
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("oa:oaSchedule:edit")
	@RequestMapping(value = "save")
	public String save(OaSchedule oaSchedule, Model model, RedirectAttributes redirectAttributes) {
        User user= UserUtils.getUser();
		if (!beanValidator(model, oaSchedule)){
			return form(oaSchedule, model);
		}
        oaSchedule.setLoginId(user.getId());
        if(StringUtils.isEmpty(oaSchedule.getFlag())) {
            oaSchedule.setFlag("0");
        }
        
        oaScheduleService.save(oaSchedule);
		addMessage(redirectAttributes, "保存日程安排成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaSchedule/?repage";
	}

    /**
     * 保存日程安排
     * @param oaSchedule
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:oaSchedule:edit")
    @RequestMapping(value = "save1")
    public void save1(OaSchedule oaSchedule, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {

        User user= UserUtils.getUser();
       /* if (!beanValidator(model, oaSchedule)){
            return form(oaSchedule, model);
        }*/
        oaSchedule.setLoginId(user.getId());
        if(StringUtils.isEmpty(oaSchedule.getFlag())) {
            oaSchedule.setFlag("0");
        }
        oaScheduleService.save(oaSchedule);


    }

    /**
     * 弹框更新日程安排
     * @param oaSchedule
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:oaSchedule:edit")
    @RequestMapping(value = "update")
    public void update(OaSchedule oaSchedule, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {

        User user= UserUtils.getUser();
       /* if (!beanValidator(model, oaSchedule)){
            return form(oaSchedule, model);
        }*/
        oaSchedule.setLoginId(user.getId());
        if(StringUtils.isEmpty(oaSchedule.getFlag())) {
            oaSchedule.setFlag("0");
        }
        oaScheduleService.updateBy(oaSchedule);


    }

    /**
     * 删除日程安排
     * @param oaSchedule
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("oa:oaSchedule:edit")
	@RequestMapping(value = "delete")
	public String delete(OaSchedule oaSchedule, RedirectAttributes redirectAttributes) {
		oaScheduleService.delete(oaSchedule);
		addMessage(redirectAttributes, "删除日程安排成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaSchedule/?repage";
	}

    @RequestMapping(value = "calendar")
    public String calendar( HttpServletRequest request, HttpServletResponse response, Model model) {
        return "mobile/sys/index2";
    }


}