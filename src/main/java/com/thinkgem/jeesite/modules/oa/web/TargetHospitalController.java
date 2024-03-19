/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.service.FlowService;
import com.thinkgem.jeesite.modules.oa.units.CommonUtils;
import com.thinkgem.jeesite.modules.oa.units.FreemarkerUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
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
import com.thinkgem.jeesite.modules.oa.entity.TargetHospital;
import com.thinkgem.jeesite.modules.oa.service.TargetHospitalService;

import java.util.List;
import java.util.Map;

/**
 * 标的医院综合评价Controller
 * @author oa
 * @version 2017-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/targetHospital")
public class TargetHospitalController extends BaseController {

	@Autowired
	private TargetHospitalService targetHospitalService;
	
	@ModelAttribute
	public TargetHospital get(@RequestParam(required=false) String id) {
		TargetHospital entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = targetHospitalService.get(id);
		}
		if (entity == null){
			entity = new TargetHospital();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	public String list(TargetHospital targetHospital, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<TargetHospital> page = targetHospitalService.findPage(new Page<TargetHospital>(request, response), targetHospital); 
		model.addAttribute("page", page);
		return "modules/oa/targetHospitalList";
	}

	@RequestMapping(value = "form")
    @ResponseBody
    public void form(String BZ,TargetHospital targetHospital, Model model,HttpServletResponse response)throws Exception{
        User user=UserUtils.getUser();
        targetHospital.setCreateBy(user);
        if(StringUtils.isNotEmpty(BZ)){
            targetHospital.setBz("1");
        }


		List<TargetHospital> targetHospitalList=targetHospitalService.findList(targetHospital);
//        response.setContentType("text/json; charset=utf-8");


        response.getWriter().write(targetHospitalList.toString());
        ;
    }


    @RequestMapping(value = "deleteById")

    public void formById(String id,TargetHospital targetHospital, Model model,HttpServletResponse response)throws Exception{
        User user=UserUtils.getUser();
        targetHospital.setId(id);
        targetHospitalService.delete(targetHospital);
//
    }


	@RequestMapping(value = "save")
	public String save(FlowData flowData, HttpServletRequest request, RedirectAttributes redirectAttributes,HttpServletResponse response) {
//        Map data = request.getParameterMap();
		/*if (!beanValidator(model, targetHospital)){
			return form(targetHospital, model);
		}
		targetHospitalService.save(targetHospital);*/
        Map data = request.getParameterMap();
        FlowData flowParam = new FlowData();
        String html="";
        if (data != null) {

            data = CommonUtils.mapConvert(data);

            String[] filterName = {"tableName", "act.taskId", "act.taskName", "act.taskDefKey",
                    "act.procInsId", "act.flag", "id", ""};
            data = CommonUtils.attributeMapFilter(data, filterName);
            String procDefId = flowData.getAct().getProcDefId();
            flowData.setFlowFlag(procDefId.substring(0, procDefId.indexOf(":")));

            flowData.setDatas(data);
            if (StringUtils.isBlank(flowData.getId())) {
                flowData.preInsert();
                targetHospitalService.saveFlowData(flowData);
            }else {
                flowData.preUpdate();
                targetHospitalService.updateFlowData(flowData);}
        }
		addMessage(redirectAttributes, "保存保存标的医院综合评价成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oa/targetHospital/?repage";
	}
	
	@RequiresPermissions("oa:oa:targetHospital:edit")
	@RequestMapping(value = "delete")
	public String delete(TargetHospital targetHospital, RedirectAttributes redirectAttributes) {
		targetHospitalService.delete(targetHospital);
		addMessage(redirectAttributes, "删除保存标的医院综合评价成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oa/targetHospital/?repage";
	}

}