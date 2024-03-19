/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.oa.entity.PersonSigns;
import com.thinkgem.jeesite.modules.oa.service.PersonSignsService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 个人签名Controller
 * @author oa
 * @version 2017-02-06
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/personSigns")
public class PersonSignsController extends BaseController {

	@Autowired
	private PersonSignsService personSignsService;
	
	@ModelAttribute
	public PersonSigns get(@RequestParam(required=false) String id) {
		PersonSigns entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personSignsService.get(id);
		}
		if (entity == null){
			entity = new PersonSigns();
		}
		return entity;
	}
	
//	@RequiresPermissions("oa:personSigns:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonSigns personSigns, HttpServletRequest request, HttpServletResponse response, Model model) {
        personSigns.setLoginId(UserUtils.getUser().getId());
		Page<PersonSigns> page = personSignsService.findPage(new Page<PersonSigns>(request, response), personSigns);
		model.addAttribute("page", page);
		return "modules/oa/personSignsList";
	}


	@RequiresPermissions("oa:personSigns:view")
	@RequestMapping(value = "form")
	public String form(PersonSigns personSigns, Model model) {
        if(StringUtils.isNotBlank(personSigns.getId())){
            personSigns=personSignsService.get(personSigns.getId());
        }
		model.addAttribute("personSigns", personSigns);
		return "modules/oa/personSignsForm";
	}



	@RequestMapping(value = "saveContractFile")
         public void saveContractFile(@RequestParam("file")MultipartFile file , String name,String signName,String id,
                                      RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        personSignsService.saveContractFile(file, name, signName, id,request);
        response.getWriter().write("{success:true}");
    }

	
	@RequiresPermissions("oa:personSigns:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonSigns personSigns, RedirectAttributes redirectAttributes) {
		personSignsService.delete(personSigns);
		addMessage(redirectAttributes, "删除个人签名成功");
		return "redirect:"+ Global.getAdminPath()+"/oa/personSigns/?repage";
	}

    @RequestMapping(value = "find")
    public String find() {
       String id= UserUtils.getUser().getId();
       PersonSigns personSigns=personSignsService.get(id);
        return personSigns.getSignUrl();
    }

}