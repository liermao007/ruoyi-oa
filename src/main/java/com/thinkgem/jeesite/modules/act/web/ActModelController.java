package com.thinkgem.jeesite.modules.act.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.form.dao.OaFormMasterDao;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableColumnDao;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActModelService;

import java.util.List;

/**
 * 流程模型相关Controller
 *
 * @author oa
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/model")
public class ActModelController extends BaseController {

    @Autowired
    private ActModelService actModelService;


    @Autowired
    private OaPersonDefineTableDao oaPersonDefineTableDao;

    @Autowired
    private OaFormMasterDao oaFormMasterDao;


    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleDao roleDao;


    /**
     * 流程模型列表
     */
    @RequiresPermissions("act:model:edit")
    @RequestMapping(value = {"list", ""})
    public String modelList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {

        List<Role> roleList = UserUtils.getRole();
        for (Role role : roleList) {
            if (role.getRoleId().equals("1940ad9fff3549db8fd1bd8fb831c96b")) {
                category = "";
            } else {
                String companyId = UserUtils.getUser().getCompany().getId();
                category = companyId;
            }
        }

        Page<org.activiti.engine.repository.Model> page = actModelService.modelList(
                new Page<org.activiti.engine.repository.Model>(request, response), category);

        model.addAttribute("page", page);
        model.addAttribute("category", category);

        return "modules/act/actModelList";
    }

    /**
     * 创建模型
     */
    @RequiresPermissions("act:model:edit")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        UserUtils.getUser().getCompany().getId();
        model.addAttribute("companyId", UserUtils.getUser().getCompany().getId());
        return "modules/act/actModelCreate";
    }

    /**
     * 创建模型
     */
    @RequiresPermissions("act:model:edit")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(String name, String key, String description, String category, String tenantId,
                       HttpServletRequest request, HttpServletResponse response) {
        try {
            org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category, tenantId);
            response.sendRedirect(request.getContextPath() + "/act/process-editor/modeler.jsp?modelId=" + modelData.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建模型失败：", e);
        }
    }

    /**
     * 根据Model部署流程
     */
//    @RequiresPermissions("act:model:edit")
    @RequestMapping(value = "deploy")
    public String deploy(String id, RedirectAttributes redirectAttributes) {
        String message = actModelService.deploy(id);
        String tableName = "";
        String procDefId = "";
        if (message.startsWith("部署成功")) {
            //说明流程已经部署成功需要将数据库中的五张表中的流程id修改
            String updateTableNameSql = "";
            int beginIndex = 0;
            int endIndex = 0;
            beginIndex = message.indexOf("=") + 1;
            endIndex = message.indexOf(":");
            tableName = message.substring(beginIndex, endIndex);
            procDefId = message.substring(beginIndex);
            //先统一修改4张表的记录   看看是否能够更改菜单
            //之后在判断数据表中是否有这个表名的数据，如果有则直接修改，
            // 否则的话去oa_form_master表中查询出正确的表名在更改
            String sql = " UPDATE act_hi_actinst SET PROC_DEF_ID_ = '" + procDefId + "' WHERE PROC_DEF_ID_ LIKE '" + tableName + ":%';";
            String sqlTaskinst = "UPDATE act_hi_taskinst SET PROC_DEF_ID_ = '" + procDefId + "' WHERE PROC_DEF_ID_ LIKE '" + tableName + ":%';";
            String sqlTask = "UPDATE act_ru_task SET PROC_DEF_ID_ = '" + procDefId + "' WHERE PROC_DEF_ID_ LIKE '" + tableName + ":%';";
            String sqlPro = "UPDATE act_hi_procinst SET PROC_DEF_ID_ = '" + procDefId + "' WHERE PROC_DEF_ID_ LIKE '" + tableName + ":%';";
            oaPersonDefineTableDao.executeSql(sql);
            oaPersonDefineTableDao.executeSql(sqlTaskinst);
            oaPersonDefineTableDao.executeSql(sqlTask);
            oaPersonDefineTableDao.executeSql(sqlPro);
            //修改自定义表的数据
            String sql1 = "SELECT count(*) FROM information_schema.tables WHERE table_schema='" + Global.getDatabaseName() + "' AND table_name ='" + tableName + "';";
            Integer tab = oaPersonDefineTableDao.executeSql(sql1);
            if (tab > 0) {
                //说明此表存在
                updateTableNameSql = "UPDATE " + tableName + " SET PROC_DEF_ID_ = '" + procDefId + "' WHERE PROC_DEF_ID_ LIKE '" + tableName + ":%';";
            } else {
                OaFormMaster oaFormMaster = new OaFormMaster();
                oaFormMaster.setFormNo(tableName);
                List<OaFormMaster> oaFormMasters = oaFormMasterDao.findAllList(oaFormMaster);
                if (oaFormMasters != null && oaFormMasters.size() > 0) {
                    updateTableNameSql = "UPDATE " + oaFormMasters.get(0).getTableName() + " SET PROC_DEF_ID= '" + procDefId + "' WHERE PROC_DEF_ID LIKE '" + tableName + ":%';";
                }
            }
            oaPersonDefineTableDao.executeSql(updateTableNameSql);
            //先根据procDefId来查询菜单
            String href = "/act/task/form?procDefId=" + tableName;
            List<Menu> menus = menuDao.getByHref(href);
            if (menus != null && menus.size()>0){
                for(Menu menu : menus){
                    menu.setHref("/act/task/form?procDefId=" + procDefId);
                    menuDao.update(menu);
                }
            }
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:" + adminPath + "/act/process";
    }

    /**
     * 导出model的xml文件
     */
    @RequiresPermissions("act:model:edit")
    @RequestMapping(value = "export")
    public void export(String id, HttpServletResponse response) {
        actModelService.export(id, response);
    }

    /**
     * 更新Model分类
     */
    @RequiresPermissions("act:model:edit")
    @RequestMapping(value = "updateCategory")
    public String updateCategory(String id, String category, RedirectAttributes redirectAttributes) {
        actModelService.updateCategory(id, category);
        redirectAttributes.addFlashAttribute("message", "设置成功，模块ID=" + id);
        return "redirect:" + adminPath + "/act/model";
    }

    /**
     * 删除Model
     *
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("act:model:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        actModelService.delete(id);
        redirectAttributes.addFlashAttribute("message", "删除成功，模型ID=" + id);
        return "redirect:" + adminPath + "/act/model";
    }


    /**
     * 删除Model
     *
     * @return
     */

    @RequestMapping(value = "form")
    public String form(String id, Model model) {
        model.addAttribute("id", id);
        return "modules/act/actInfoForm";
    }

    /**
     * 复制Model
     *
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "copy")
    public String copy(String id, String actName, String actEng, RedirectAttributes redirectAttributes) {


        try {
            int a = actModelService.copy(id, actName, actEng);
            if (a == 1) {
                redirectAttributes.addFlashAttribute("message", "复制模型成功");
            } else {
                redirectAttributes.addFlashAttribute("message", "模型已存在！请修改流程标识");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("复制模型失败：", e);
        }

        return "redirect:" + adminPath + "/act/model";
    }
}
