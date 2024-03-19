/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.AddDict;
import com.thinkgem.jeesite.modules.sys.entity.AddPostgrade;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AddDictService;
import com.thinkgem.jeesite.modules.sys.service.AddPostgradeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import java.util.List;
import java.util.Map;

/**
 * 字典Controller
 * @author oa
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/addPostgrade")
public class AddPostgradeController extends BaseController {

	@Autowired
	private AddPostgradeService addPostgradeService;

    @Autowired
    private SystemService systemService;
	@ModelAttribute
	public AddPostgrade get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
			return addPostgradeService.get(id);
		}else{
			return new AddPostgrade();
		}
	}
	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list(AddPostgrade dict, HttpServletRequest request, HttpServletResponse response, Model model) {
       // String id=UserUtils.getUser().getCompany().getId();

        dict.setCompanyId(UserUtils.getUser().getCompany().getId());
        Page<AddPostgrade> page = addPostgradeService.findPage(new Page<AddPostgrade>(request, response), dict);
        /*List l = AddDictUtils.getDictList("act_node");
        page.setList(l);*/
        model.addAttribute("page", page);
		return "modules/sys/addPostgradeList";
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "form")
	public String adForm(AddPostgrade dict, Model model) {
		model.addAttribute("dict", dict);
		return "modules/sys/addPostgradeForm";
	}


    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "edform")
    public String edForm(AddPostgrade dict, Model model) {
        model.addAttribute("dict", dict);
        return "modules/sys/addPostgradeForm";
    }

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")//@Valid 
	public String save(AddPostgrade dict, Model model, RedirectAttributes redirectAttributes) {

        User u=new User();
        u=UserUtils.getUser();
       dict.setCompanyId(u.getCompany().getId());

        dict.setType("post_grade");
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/addPostgrade/?repage&type="+dict.getType();
		}
		if (!beanValidator(model, dict)){
			return adForm(dict, model);
		}
        addPostgradeService.save(dict);
		addMessage(redirectAttributes, "保存字典'" + dict.getLabel() + "'成功");
		return "redirect:" + adminPath + "/sys/addPostgrade/?repage&type="+dict.getType();
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(AddPostgrade dict, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/addPostgrade/?repage";
		}
        addPostgradeService.delete(dict);
		addMessage(redirectAttributes, "删除字典成功");
		return "redirect:" + adminPath + "/sys/addPostgrade/?repage&type="+dict.getType();
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
        AddPostgrade dict = new AddPostgrade();
		dict.setType(type);
		List<AddPostgrade> list = addPostgradeService.findList(dict);
		for (int i=0; i<list.size(); i++){
            AddPostgrade e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "listData")
	public List<AddPostgrade> listData(@RequestParam(required=false) String type) {
        AddPostgrade dict = new AddPostgrade();
		dict.setType(type);
		return addPostgradeService.findList(dict);
	}

}
