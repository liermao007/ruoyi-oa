/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.AddDict;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AddDictService;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.AddDictUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
@RequestMapping(value = "${adminPath}/sys/adddict")
public class AddDictController extends BaseController {

	@Autowired
	private AddDictService dictService;

    @Autowired
    private SystemService systemService;
	@ModelAttribute
	public AddDict get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
			return dictService.get(id);
		}else{
			return new AddDict();
		}
	}
	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list(AddDict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
       // String id=UserUtils.getUser().getCompany().getId();

        dict.setCompanyId(UserUtils.getUser().getCompany().getId());
        Page<AddDict> page = dictService.findPage(new Page<AddDict>(request, response), dict);
        /*List l = AddDictUtils.getDictList("act_node");
        page.setList(l);*/
        model.addAttribute("page", page);
		return "modules/sys/adddictList";
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "adform")
	public String adForm(AddDict dict, Model model) {
		model.addAttribute("dict", dict);
		return "modules/sys/adAdddictForm";
	}


    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "edform")
    public String edForm(AddDict dict, Model model) {
        model.addAttribute("dict", dict);
        return "modules/sys/edAdddictForm";
    }

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")//@Valid 
	public String save(AddDict dict, Model model, RedirectAttributes redirectAttributes) {

        User u=new User();
        u=UserUtils.getUser();
        String[]  id=dict.getName().split(",");
        String name=null;
        for(int i=0;i<id.length;i++) {
           name=  id[1];

        }
        User u1=new User();
        u1.setName(name);
//        u1.setCompanyId(UserUtils.getUser().getCompany().getId());
        List<User> users= systemService.getListByName(u1);

         String loginName=  users.get(0).getLoginName();
       dict.setCompanyId(u.getCompany().getId());
        dict.setUserName(loginName);
        dict.setName(name);
        dict.setType("act_node");
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/adddict/?repage&type="+dict.getType();
		}
		if (!beanValidator(model, dict)){
			return adForm(dict, model);
		}
		dictService.save(dict);
		addMessage(redirectAttributes, "保存字典'" + dict.getLabel() + "'成功");
		return "redirect:" + adminPath + "/sys/adddict/?repage&type="+dict.getType();
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(AddDict dict, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/adddict/?repage";
		}
		dictService.delete(dict);
		addMessage(redirectAttributes, "删除字典成功");
		return "redirect:" + adminPath + "/sys/adddict/?repage&type="+dict.getType();
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
        AddDict dict = new AddDict();
		dict.setType(type);
		List<AddDict> list = dictService.findList(dict);
		for (int i=0; i<list.size(); i++){
            AddDict e = list.get(i);
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
	public List<AddDict> listData(@RequestParam(required=false) String type) {
        AddDict dict = new AddDict();
		dict.setType(type);
		return dictService.findList(dict);
	}

}
