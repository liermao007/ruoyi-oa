/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.thinkgem.jeesite.common.Result;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SynchroDataUtils;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.service.MailInfoService;
import com.thinkgem.jeesite.modules.oa.service.PersonSignsService;
import com.thinkgem.jeesite.modules.sys.dao.AddPostgradeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.service.AddPostgradeService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.AddPostgradeUtils;
import com.thinkgem.jeesite.modules.sys.utils.ChineseToEnglish;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.MSG1;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.StringData;
import com.thoughtworks.xstream.mapper.Mapper;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 用户Controller
 *
 * @author oa
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private AddPostgradeService addPostgradeService;
    @Autowired
    private MailInfoService mailInfoService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private PersonSignsService personSignsService;

    @ModelAttribute
    public User get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return systemService.getUser(id);
        } else {
            return new User();
        }
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"index"})
    public String index(User user, Model model) {
        return "modules/sys/userIndex";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (UserUtils.getUser().isSuperAdmin()) {
            user.setName("管理员");
            Page<User> page = systemService.findUser(new Page<User>(request, response), user);
            model.addAttribute("page", page);
            return "modules/sys/userList";
        } else {
            Office office = new Office();
            office.setId(UserUtils.getUser().getCompany().getId());
            user.setCompany(office);
            user.setOffice(user.getOffice());
            Page<User> page = systemService.findPage(new Page<User>(request, response), user);
            model.addAttribute("page", page);
            return "modules/sys/userOrgList";
        }

    }

    @ResponseBody
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"listData"})
    public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        return page;
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "form")
    public String form(User user, Model model) {
        User user1 = systemService.getOne(user.getId());
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }
        if (StringUtils.isNotEmpty(user.getLeaderShip())) {
            user.setLeaderName(user1.getName());
        }
        if (StringUtils.isNotEmpty(user.getDept())) {
            user.setDeptName(user1.getName());
        }
        if (StringUtils.isNotEmpty(user.getLevel1())) {
            user.setAcLevel1(user1.getName());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", systemService.findAllRole());
        return "modules/sys/userForm";
    }

    /**
     * OA同步人力资源的人员数据
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "savePeople")
    public String savePeople(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        String flag = user.getFlag();
        String success = systemService.savePeople("add", "", UserUtils.getUser().getCompany().getId());
        if (StringUtils.equalsIgnoreCase("success", success)) {
            addMessage(redirectAttributes, "同步人力资源人员数据成功");
        }
        addMessage(redirectAttributes, "同步人力资源人员数据成功");
        if (StringUtils.equals(flag, "0")) {
            return "redirect:" + adminPath + "/sys/user/phone?repage";
        } else {
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
    }

    /**
     * 手机端头像上传
     *
     * @param file
     * @param id
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "saveContractFile")
    @ResponseBody
    public String saveContractFile(@RequestParam("file") MultipartFile file, String name, String signName, String id,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        String path = "";
        if (StringUtils.isNotBlank(file.getOriginalFilename())) {
            path = systemService.saveContractFile(file, id, request);
        }
        return path;
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "save")
    public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String flag = user.getFlag();
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            if (StringUtils.equals(flag, "0")) {
                return "redirect:" + adminPath + "/sys/user/phone?repage";
            } else {
                return "redirect:" + adminPath + "/sys/user/list?repage";
            }
        }
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(new Office(request.getParameter("company.id")));
        user.setOffice(new Office(request.getParameter("office.id")));
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidator(model, user)) {
            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        List<String> roleIdList = user.getRoleIdList();
        for (Role r : systemService.findAllRole()) {
            if (roleIdList.contains(r.getId())) {
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);

        User user1 = UserUtils.get(user.getLeaderShip());
        if (user1 != null) {
            user.setAcName(user1.getLoginName());
        }


        User user2 = UserUtils.get(user.getDept());
        if (user2 != null) {
            user.setAcDeptName(user2.getLoginName());
        }

        User user3 = UserUtils.get(user.getLevel1());
        if (user3 != null) {
            user.setLevel1Name(user3.getLoginName());
        }


        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
        if (StringUtils.equals(flag, "0")) {
            return "redirect:" + adminPath + "/sys/user/phone?repage";
        } else {
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        if (UserUtils.getUser().getId().equals(user.getId())) {
            addMessage(
                    redirectAttributes, "删除用户失败, 不允许删除当前用户");
        } else if (User.isAdmin(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
        } else {
            systemService.deleteUser(user);
            addMessage(redirectAttributes, "删除用户成功");
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 导出用户数据
     *
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
            new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 导入用户数据
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<User> list = ei.getDataList(User.class);
            for (User user : list) {
                user.setSex(DictUtils.getDictValue(user.getSex(), "sex", ""));
                user.setGrade(AddPostgradeUtils.getDictValue(user.getGrade(), "post_grade", ""));
                try {
                    if ("true".equals(checkLoginName("", user.getLoginName()))) {
                        user.setPassword(SystemService.entryptPassword("123456"));
                        BeanValidators.validateWithException(validator, user);
                        systemService.saveUser(user);
                        successNum++;
                    } else {
                        failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
                        failureNum++;
                    }
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 下载导入用户数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据导入模板.xlsx";
            List<User> list = Lists.newArrayList();
            list.add(UserUtils.getUser());
            new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 用户信息显示及保存
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "info")
    public String info(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
//        CacheUtils.removeAll();
        User currentUser = UserUtils.getUser();
        User photo = null;
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        if (StringUtils.isNotBlank(user.getName())) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userInfo";
            }
            photo = systemService.getUser(currentUser.getId());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone(user.getPhone());
            currentUser.setMobile(user.getMobile());
            currentUser.setRemarks(user.getRemarks());
            currentUser.setPhoto(photo.getPhoto());
            systemService.updateUserInfo(currentUser);
            UserUtils.clearCache();
            //systemService.updateUserLoginInfo(currentUser);
            model.addAttribute("message", "保存用户信息成功");
        }
        if (photo == null) {
            model.addAttribute("user", currentUser);
        } else {
            model.addAttribute("user", photo);
        }

        model.addAttribute("Global", new Global());

        return "modules/sys/userInfo";
    }

    /**
     * PC端个人头像上传
     *
     * @param file
     * @param redirectAttributes
     * @param request
     * @param response
     * @param model
     * @throws IOException
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "saveContractFilePC")
    public void saveContractFilePC(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String id = UserUtils.getUser().getId();
        systemService.saveContractFile(file, id, request);
//        return UserUtils.getUser().getPhoto();

        response.getWriter().write(UserUtils.getUser().getPhoto());
    }

    /**
     * 返回用户信息
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "infoData")
    public User infoData() {
        return UserUtils.getUser();
    }

    /**
     * 修改个人用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userModifyPwd";
            }
            if (SystemService.validatePassword(oldPassword, user.getPassword())) {
                systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
                model.addAttribute("message", "修改密码成功");
            } else {
                model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }
        model.addAttribute("user", user);
        return "modules/sys/userModifyPwd";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId, String checkAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = systemService.findUserByOfficeId(officeId);
        for (int i = 0; i < list.size(); i++) {
            User e = list.get(i);
            if("1".equals(checkAll) && !"1".equals(e.getCheckAll())) {
                continue;
            }else {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", "u_" + e.getId());
                map.put("pId", officeId);
                map.put("name", StringUtils.replace(e.getName(), " ", ""));
                map.put("grade", StringUtils.replace(e.getGrade(), " ", ""));
                map.put("loginName", StringUtils.replace(e.getLoginName(), " ", ""));
                mapList.add(map);
            }
        }
        return mapList;
    }


    /**
     * 修改id的格式
     *
     * @param officeId
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData1")
    public List<Map<String, Object>> treeData1(@RequestParam(required = false) String officeId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = systemService.findUserByOfficeId(officeId);
        for (int i = 0; i < list.size(); i++) {
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("pId", officeId);
            map.put("name", StringUtils.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }


    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "phone")
    public String findUserList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!user.isAdmin() && UserUtils.getUser().getCompany() != null) {
            String companyId = UserUtils.getUser().getCompany().getId();
            user.setCompanyId(companyId);
        }
        Page<User> page = mailInfoService.findPage1(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        //查询当前用户所属机构的所有的一级科室
        List<Office> offices = officeService.findOfficeByOrgId(UserUtils.getUser().getCompany().getId());
        model.addAttribute("offices", offices);
        return "modules/oa/user";
    }

    /**
     * 手机端联系人，全部修改，另起方法，防止之上的方法在邮箱处使用,
     * 查询当前机构以及当前机构的下属机构
     *
     * @param user
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "mobile")
    public String findListOffice(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        User currentUser = UserUtils.getUser();
        if (!currentUser.isAdmin() && currentUser.getCompany() != null) {
            String companyId = currentUser.getCompany().getId();
            user.setCompanyId(companyId);
        }
        //查询当前用户所属机构的所有的一级科室
        List<Office> offices = officeService.findCompany(currentUser.getCompany().getId());
        model.addAttribute("offices", offices);
        return "modules/oa/user";
    }

    /**
     * 手机端联系人，全部修改，另起方法，防止之上的方法在邮箱处使用
     * 查询当前机构下的所有的联系人
     *
     * @param user
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "findMobile")
    public String findMobileList(@RequestParam("parentId") String parentId, User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        user.setCompanyId(parentId);
        //查询某个机构下的所有的人员
        List<User> users = systemService.findListByName(user);
        Office office = officeService.get(parentId);
        model.addAttribute("parentId", parentId);
        model.addAttribute("companyName", office.getName());
        model.addAttribute("users", users);
        return "modules/oa/mobile";
    }

    /**
     * 模糊查询姓名
     *
     * @param parentId
     * @param name
     * @param user
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "findLikeMobile")
    @ResponseBody
    public List<User> findLikeMobile(@RequestParam("parentId") String parentId, String name, User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        user.setCompanyId(parentId);
        user.setName(name);
        //查询某个机构下的所有的人员
        List<User> users = systemService.findListByName(user);
        return users;
    }


    /**
     * 查询当前所属人的机构人员
     *
     * @param user
     * @param name
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "toTaskFindListMobile", method = RequestMethod.GET)
    @ResponseBody
    public List<User> toTaskFindListMobile(User user, String name, String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!user.isAdmin() && UserUtils.getUser().getCompany() != null) {
            String companyId = UserUtils.getUser().getCompany().getId();
            user.setCompanyId(companyId);
        }
        user.setName(name);
        user.setUserId(userId);
        //查询某个机构下的所有的人员
        List<User> users = systemService.toTaskFindListByName(user);
        return users;
    }


    @ModelAttribute("countryList")
    public List<AddPostgrade> getCountryList() {
        AddPostgrade add = new AddPostgrade();
        add.setDelFlag("0");
        if (UserUtils.getUser().getCompany() != null) {
            add.setCompanyId(UserUtils.getUser().getCompany().getId());
        }
        List<AddPostgrade> countryList = addPostgradeService.findAlllist(add);
        return countryList;
    }


    /**
     * OA手机端需要的返回界面
     *
     * @param user
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "myPhone")
    public String myPhoneOA(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        //查询当前登陆人姓名和角色
        List<Role> roles = systemService.getRoleByUserId(UserUtils.getUser().getId());
        StringBuilder sb = new StringBuilder();
        if (roles != null && roles.size() > 0) {
            for (int i = 0; i < roles.size(); i++) {
                sb.append(roles.get(i).getName() + ",");
            }
        }
        user = systemService.getUser(UserUtils.getUser().getId());
        String d = sb.substring(0, sb.length() - 1);
        Role role = new Role();
        role.setName(d);
        user.setRole(role);
        model.addAttribute("user", user);
        return "modules/sys/me";
    }

    /**
     * OA手机端查询用户的手机号
     *
     * @param user
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "findPhone")
    @ResponseBody
    public List<User> findPhone(User user, String id) {
        List<User> users = officeService.findPhone(id);
        return users;
    }

    /**
     * OA手机端查询某个用户的手机号
     *
     * @param user
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "findUserPhone")
    @ResponseBody
    public List<User> findUserPhone(User user, String id, String name) {
        List<User> users = new ArrayList<>();
        if (StringUtils.isBlank(name)) {
            users = officeService.findPhone(id);
        } else {
            user.setName(name);
            user.setCompanyId(UserUtils.getUser().getCompany().getId());
            users = systemService.findListByName(user);
        }
        return users;
    }


    /**
     * 查询是人力资源的id
     *
     * @param
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "findHrmIp")
    @ResponseBody
    public User findHrmIp() {
        User u = UserUtils.getUser();
        u.setHrmFlag("1");
        User user = systemService.getByName(u);
        return user;
    }

    /**
     * 人脸识别页
     */
    @Login(action = Action.Skip)
//	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET, RequestMethod.HEAD)
    @RequestMapping(value = "getPreview")
    public String getPreview(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/sys/preview";
    }

    /**
     * @return
     */
    @RequestMapping(value = "message")
    public String message(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "modules/sys/sysMessage";
    }

    /**
     * 获取岗位工作平台中的数据
     */
    @ResponseBody
    @RequestMapping(value = "getDataList")
    public List<Map<String, Object>> getDataList(@RequestParam(required = false) String workUrlNoIp, @RequestParam(required = false) String updateDate, HttpServletResponse response) {
        if (StringUtils.isBlank(workUrlNoIp)) {
            return null;
        }
        StringBuilder url = new StringBuilder();
        //访问WORK 获取岗位工作平台数据路径
        url.append(Global.getConfig("sso.work.url")).append(workUrlNoIp).append("?userId=").append(UserUtils.getUser().getId());
        return convertListWorkList(SynchroDataUtils.getJsonByUrl(url.toString()));
    }

    /**
     * 转换，将岗位工作平台传递的数据转成list集合
     *
     * @param data 岗位工作平台收入，住院人数等数据
     * @return
     */
    public List<Map<String, Object>> convertListWorkList(String data) {
        Map<String, String[]> replaceMap = new HashMap<String, String[]>();
        String namespace = MSG1.class.getName();
        replaceMap.put("todayNum", new String[]{namespace, "todayNum"});//今日新入院人数
        replaceMap.put("bedDays", new String[]{namespace, "bedDays"});//占床日
        replaceMap.put("hpIncome", new String[]{namespace, "hpIncome"});//住院收入
        replaceMap.put("outIncome", new String[]{namespace, "outIncome"});//门诊收入
        replaceMap.put("regNum", new String[]{namespace, "regNum"});//当日挂号人数
        return SynchroDataUtils.getDataByJson(data, replaceMap);
    }


    /**
     * 查询离职人员
     */
    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "getLeaveList")
    public String getLeaveList(String type, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (UserUtils.getUser().getLoginName().contains("admin")) {
            Map<String, List<User>> map = systemService.findDelList(UserUtils.getUser().getCompany().getId());
            Set set = map.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
                Object obj = (Object) iterator.next();
                Object value = (Object) map.get(obj);
                remove(value, iterator);
            }

            //无直属领导人员
            Page page = new Page<User>(request, response);
            User user = new User();
            String orgId = UserUtils.getUser().getCompany().getId();
            user.setCompanyId(orgId);
            user.setPage(page);
            List<User> users = systemService.findPeopleNoLeader(orgId, user);
            UserUtils.getOtherInfoForList(users, false);
            model.addAttribute("page", page);

            if (map.size() < 1 && users.size()<1) {
                return null;
            } else {
                UserUtils.getOtherInfoForList(users, false);
                page.setList(users);
                model.addAttribute("page", page);
                model.addAttribute("map", map);
                return "modules/sys/leavePeopleList";
            }
        } else {
            return null;
        }
    }


    /**
     * 查询离职人员下的员工
     */
    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "getEmplyeeList")
    public String getEmplyeeList(String id, String name, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (UserUtils.getUser().getLoginName().contains("admin")) {
            Map<String, List<User>> map = systemService.getEmplyeeList(id, UserUtils.getUser().getCompany().getId());
            Set set = map.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
                Object obj = (Object) iterator.next();
                Object value = (Object) map.get(obj);
                remove(value, iterator);
            }

            if (map.size() < 1) {
                return null;
            } else {
                model.addAttribute("map", map);
                model.addAttribute("name", name);
                return "modules/sys/leaveLeaderList";
            }
        } else {
            return null;
        }
    }

    /**
     * 修改离职人员
     */
    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "updateLeaveTotal")

    public String updateLeaveTotal(String key, String id, String loginName, String hrmFlay, Model model, RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(hrmFlay)) {
            model.addAttribute("message", "请选择修改人员！");
            return "redirect:" + adminPath + "/sys/user/getLeaveList";
        } else {
            Integer count = systemService.updateLeaveTotal(key, id, loginName, hrmFlay);
            if (count != null && count > 0) {
                model.addAttribute("message", "修改成功。");
            } else {
                model.addAttribute("message", "修改失败！");
            }
        }


        return "redirect:" + adminPath + "/sys/user/getLeaveList";

    }

    /**
     * 修改离职人员
     */
    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "updateLeave1")
    @ResponseBody
    public String updateLeave(String key, String id, String loginName, String hrmFlay, String ids, Model model) {

        systemService.updateLeave(key, id, loginName, hrmFlay, ids);
        model.addAttribute("message", "修改用户成功");
        return null;

    }


    private static void remove(Object obj, Iterator iterator) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (StringUtils.isEmpty(str)) {
                iterator.remove();
            }
        } else if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            if (col == null || col.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Map) {
            Map temp = (Map) obj;
            if (temp == null || temp.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array == null || array.length <= 0) {
                iterator.remove();
            }
        } else {
            if (obj == null) {
                iterator.remove();
            }
        }
    }

    /**
     * 查询无直属领导的员工
     */
    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "noLeaderList")
    public String noLeaderList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //无直属领导人员
        Page page = new Page<User>(request, response);
        User user = new User();
        String orgId = UserUtils.getUser().getCompany().getId();
        user.setCompanyId(orgId);
        user.setPage(page);
        List<User> users = systemService.findPeopleNoLeader(orgId, user);
        UserUtils.getOtherInfoForList(users, false);
        page.setList(users);
        model.addAttribute("page", page);
        return "modules/sys/noLeaderList";
    }

    /**
     * 修改没有部门领导人员
     */
    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = "updatePeopleNoDept")
    @ResponseBody
    public String updatePeopleNoDept(String[] id, String[] leader, String[] dept, String[] level, Model model, RedirectAttributes redirectAttributes) {

        Integer count =   systemService.updateNoDept(id,UserUtils.getUser().getCompany().getId(), leader, dept, level);
        if (count != null && count > 0) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 移动端接口
     * @return
     */
    @RequestMapping(value = "getUserInfo")
    @ResponseBody
    public Result getUserInfo() {
        User user = UserUtils.getUser();
        if(user == null) {
            return Result.error();
        }
        UserResp resp = new UserResp();
        resp.setUserId(user.getId());
        resp.setUsername(user.getLoginName());
        resp.setRealname(user.getName());
        resp.setNickname(user.getName());
        resp.setAvatar(user.getPhoto());
        resp.setGender(user.getSex());
        resp.setSign(user.getRemarks());
        resp.setPhone(user.getPhone() == null ? user.getLoginName() : user.getPhone());
        resp.setCover("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
        return Result.success(resp);
    }

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    @ResponseBody
    public Result getUsers() {
        List<UserPhone> users = systemService.selectAllPhone();
        Map<String, List<UserPhone>> phoneMap = users
                .stream()
                .collect(Collectors.groupingBy(user -> PinyinUtil.getFirstLetter(user.getName().substring(0, 1), "").toUpperCase(), TreeMap::new, Collectors.toList()));
        List<AllPhoneResp> result = phoneMap.entrySet().stream().map(entity -> new AllPhoneResp(entity.getKey(), entity.getValue())).collect(Collectors.toList());
        return Result.success(result);
    }

    /**
     * 移动端接口
     * @return
     */
    @RequestMapping(value = "changePass", method = RequestMethod.POST)
    @ResponseBody
    public Result changePass(@RequestBody ChangePassReq req) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(req.getOldPassword()) && StringUtils.isNotBlank(req.getNewPassword())) {
            if (SystemService.validatePassword(req.getOldPassword(), user.getPassword())) {
                systemService.updatePasswordById(user.getId(), user.getLoginName(), req.getNewPassword());
                return Result.success();
            } else {
                return Result.error("旧密码错误");
            }
        }
        return Result.error();
    }
}
