/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.thinkgem.jeesite.modules.oa.entity.TargetHospital;
import com.thinkgem.jeesite.modules.oa.entity.TravelApplication;
import com.thinkgem.jeesite.modules.oa.service.TravelApplicationService;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.ast.Instanceof;
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
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;

import java.util.Date;
import java.util.List;

/**
 * 自定义数据源Controller
 * @author oa
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/table/oaPersonDefineTable")
public class OaPersonDefineTableController extends BaseController {

	@Autowired
	private OaPersonDefineTableService oaPersonDefineTableService;

    @Autowired
    private TravelApplicationService travelApplicationService;

    @Autowired
    private RoleDao roleDao;


	
	@ModelAttribute
	public OaPersonDefineTable get(@RequestParam(required=false) String id) {
		OaPersonDefineTable entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaPersonDefineTableService.get(id);
		}
		if (entity == null){
			entity = new OaPersonDefineTable();
		}
		return entity;
	}
	
	@RequiresPermissions("table:oaPersonDefineTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaPersonDefineTable oaPersonDefineTable, HttpServletRequest request, HttpServletResponse response, Model model) {
        if(UserUtils.getUser().getId().equals("1")){
            Office office =new Office();
            office.setId("1");
            oaPersonDefineTable.setOffice(office);
        }else{
            Office office =new Office();
            office.setId(UserUtils.getUser().getCompany().getId());
            oaPersonDefineTable.setOffice(office);
        }
		Page<OaPersonDefineTable> page = oaPersonDefineTableService.findPage(new Page<OaPersonDefineTable>(request, response), oaPersonDefineTable); 
		model.addAttribute("page", page);
		return "modules/table/oaPersonDefineTableList";
	}

	@RequiresPermissions("table:oaPersonDefineTable:view")
	@RequestMapping(value = "form")
	public String form(OaPersonDefineTable oaPersonDefineTable, Model model) {
		model.addAttribute("oaPersonDefineTable", oaPersonDefineTable);
		return "modules/table/oaPersonDefineTableForm";
	}

	@RequiresPermissions("table:oaPersonDefineTable:edit")
	@RequestMapping(value = "save")
	public String save(OaPersonDefineTable oaPersonDefineTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaPersonDefineTable)){
			return form(oaPersonDefineTable, model);
		}

        if(UserUtils.getUser().getId().equals("1")){
            Office office =new Office();
            office.setId("1");
            oaPersonDefineTable.setOffice(office);
        }else{
            Office office =new Office();
            office.setId(UserUtils.getUser().getCompany().getId());
            oaPersonDefineTable.setOffice(office);
        }

		oaPersonDefineTableService.save(oaPersonDefineTable);
		addMessage(redirectAttributes, "保存自定义数据源成功");
		return "redirect:"+Global.getAdminPath()+"/table/oaPersonDefineTable/?repage";
	}
	
	@RequiresPermissions("table:oaPersonDefineTable:edit")
	@RequestMapping(value = "delete")
	public String delete(OaPersonDefineTable oaPersonDefineTable, RedirectAttributes redirectAttributes) {
		oaPersonDefineTableService.delete(oaPersonDefineTable);
		addMessage(redirectAttributes, "删除自定义数据源成功");
		return "redirect:"+Global.getAdminPath()+"/table/oaPersonDefineTable/?repage";
	}

    /**
     * 验证表名是否有效
     * @param oldTableName
     * @param tableName
     * @return
     */
    @ResponseBody
    @RequiresPermissions("table:oaPersonDefineTable:edit")
    @RequestMapping(value = "checkTableName")
    public String checkTableName(String oldTableName, String tableName) {
        if (tableName !=null && tableName.equals(oldTableName)) {
            return "true";
        } else if (tableName !=null && oaPersonDefineTableService.getDbColumns(tableName).size() < 1) {
            return "true";
        }
        return "false";
    }

    /**
     * 获取拼音首字母
     * @param str
     * @return
     */
    @ResponseBody
    @RequiresPermissions("table:oaPersonDefineTable:edit")
    @RequestMapping(value = "getShortPinYin")
    public String getShortPinYin(String str) {
        if(StringUtils.isNotBlank(str)){
            try {
                return PinyinHelper.getShortPinyin(str);
            } catch (PinyinException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    /**
     * 查询表的所有字段名
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectByName")
    public String selectByName(String flag) {
        StringBuilder sb=new StringBuilder();
        String name=null;
        if(StringUtils.isNotBlank(flag)){
           flag="TRAVEL_EXPENSE_CLAIMS";
            OaPersonDefineTable table = oaPersonDefineTableService.findByTableName(flag, null);
            OaPersonDefineTableColumn param = new OaPersonDefineTableColumn();
            param.setTable(table);
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableService.findColumnList(param);
            for (OaPersonDefineTableColumn column : columns) {
                sb.append(column.getColumnName()+",");
            }

            name= sb.substring(0,sb.length()-1).toString();
        }
        return name;
    }

    /**
     *查询出差申请表没有报销的单据
     * @param
     * @return
     */
     @ResponseBody
     @RequestMapping(value = "selectTravel")
     public List<TravelApplication> selectTravel() {
         User user= UserUtils.getUser();
         TravelApplication travelApplication=new TravelApplication();
         travelApplication.setSfcc("1");
         travelApplication.setCreateBy(user);
         List<TravelApplication> travelApplications=travelApplicationService.findList(travelApplication);
         return travelApplications;
    }



    /**
     *根据id查询出差申请单
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectById")
    public TravelApplication selectById(String name) {
        TravelApplication travelApplications=travelApplicationService.get(name);
        return travelApplications;
    }













}