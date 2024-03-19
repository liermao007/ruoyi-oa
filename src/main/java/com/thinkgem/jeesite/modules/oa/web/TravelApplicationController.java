/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.TravelApplication;
import com.thinkgem.jeesite.modules.oa.service.TravelApplicationService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 出差统计表Controller
 * @author oa
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/travelApplication")
public class TravelApplicationController extends BaseController {

	@Autowired
	private TravelApplicationService travelApplicationService;
	
	@ModelAttribute
	public TravelApplication get(@RequestParam(required=false) String id) {
		TravelApplication entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = travelApplicationService.get(id);
		}
		if (entity == null){
			entity = new TravelApplication();
		}
		return entity;
}
	

	@RequestMapping(value = {"list", ""})
	public String list(TravelApplication travelApplication, String tableName,  String type, HttpServletRequest request, HttpServletResponse response, Model model) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String ccrq="";
        if(travelApplication.getCcrq()!=null){
      ccrq= sdf.format(travelApplication.getCcrq());}

		Page<TravelApplication> page = travelApplicationService.findList1(new Page<TravelApplication>(request, response),type, travelApplication,tableName, ccrq );
        if(page.getList().size()>0){
            page.getList().get(0).setProcDefId(tableName);
        }

        model.addAttribute("page", page);
        model.addAttribute("tableName", tableName);
        model.addAttribute("type", type);
        return "modules/oa/travelApplicationList";
    }


	@RequiresPermissions("oa:travelApplication:view")
	@RequestMapping(value = "form")
	public String form(TravelApplication travelApplication, Model model) {
		model.addAttribute("travelApplication", travelApplication);
		return "modules/oa/travelApplicationForm";
	}

	@RequiresPermissions("oa:travelApplication:edit")
	@RequestMapping(value = "save")
	public String save(TravelApplication travelApplication, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, travelApplication)){
			return form(travelApplication, model);
		}
		travelApplicationService.save(travelApplication);
		addMessage(redirectAttributes, "保存保存出差统计表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/travelApplication/?repage";
	}
	
	@RequiresPermissions("oa:travelApplication:edit")
	@RequestMapping(value = "delete")
	public String delete(TravelApplication travelApplication, RedirectAttributes redirectAttributes) {
		travelApplicationService.delete(travelApplication);
		addMessage(redirectAttributes, "删除保存出差统计表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/travelApplication/?repage";
	}

    /**
     * 导出数据
     *
     * @param
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */

    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(TravelApplication travelApplication, HttpServletRequest request, HttpServletResponse response,String tableName,String type, RedirectAttributes redirectAttributes,String ccrq) {
        try {
            String fileName = "出差申请统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<TravelApplication> page = travelApplicationService.findList1(new Page<TravelApplication>(request, response, -1), type,travelApplication, tableName,ccrq);
            new ExportExcel("出差申请统计数据", TravelApplication.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出出差申请统计数据失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/oa/travelApplication/list?repage";
    }

}