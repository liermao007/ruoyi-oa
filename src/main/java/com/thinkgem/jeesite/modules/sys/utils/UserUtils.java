/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.opensymphony.module.sitemesh.html.tokenizer.Parser;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.dao.MailInfoDao;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.sys.dao.*;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户工具类
 *
 * @author oa
 * @version 2013-12-05
 */
public class UserUtils {

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    private static MailInfoDao mailInfoDao = SpringContextHolder.getBean(MailInfoDao.class);
    private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);


    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_CARDNO_ = "cardNo_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_ROLE = "role";
    public static final String CACHE_USER = "user";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
    public static final String client_id = "hrm";

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static User get(String id) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userDao.get(id);
            if (user == null) {
                return null;
            }
            getOtherInfo(user, true);
        }
        return user;
    }

    /**
     * 根据ID获取用户，其中也可以获取离职人员
     *
     * @param id
     * @return 取不到返回null
     */
    public static User getOne(String id) {
        User user = userDao.getOne(id);
            if (user == null) {
                return null;
            }
//            getOtherInfo(user, true);

        return user;
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return 取不到返回null
     */
    public static User getByLoginName(String loginName) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (user == null){
           List<User> login = userDao.getByLoginName(new User(null, loginName));
            if(login !=null && login.size()>0){
                user = login.get(0);
            }
            if (user == null){
                return null;
            }
            getOtherInfo(user, true);
        }
        return user;
    }


    /**
     * 根据登录名获取用户从所有人员中获取（包括已经离职的人员）
     * 主要为流程做准备，如果此人已经离职，但之前提过的申请单下面的流转信息也支持显示
     * @param loginName
     * @return 取不到返回null
     */
    public static User findListByLoginName(String loginName) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (user == null){
            List<User> login = userDao.findListUser(new User(null, loginName));
            if(login !=null && login.size()>0){
                user = login.get(0);
            }
            if (user == null){
                return null;
            }
            getOtherInfo(user, true);
        }
        return user;
    }



    // 处理用户其他信息
    public static void getOtherInfo(User user, boolean saveCache) {
        user.setRoleList(roleDao.findList(new Role(user)));
        if (user.getCompany() != null && user.getCompany().getId() != null) {
            user.setCompany(officeDao.get(user.getCompany().getId()));
        }
        if (user.getOffice() != null && user.getOffice().getId() != null) {
            user.setOffice(officeDao.get(user.getOffice().getId()));
        }
        if (saveCache) {
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
    }

    // 处理多用户其他信息
    public static void getOtherInfoForList(List<User> users, boolean saveCache) {
        if (users != null && users.size() > 0) {
            for (User user : users) {
                getOtherInfo(user, saveCache);
            }
        }
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_AREA_LIST);
        removeCache(CACHE_OFFICE_LIST);
        removeCache(CACHE_OFFICE_ALL_LIST);
        UserUtils.clearCache(getUser());
    }

    /**
     * 清除指定用户缓存
     *
     * @param user
     */
    public static void clearCache(User user) {
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        if(StringUtils.isNoneBlank(user.getCardNo())){
            CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getCardNo());
        }
        if(StringUtils.isNoneBlank(user.getPhone())){
            CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getPhone());
        }
        if(StringUtils.isNoneBlank(user.getEmail())){
            CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getEmail());
        }
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName())        ;
        if (user.getOffice() != null && user.getOffice().getId() != null) {
            CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
        }
    }
    /**
     * 更新用户信息
     * @param user
     */
    public static void updateCache(User user){
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
        CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
        CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
    }
    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static User getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    public static User getUser(String id) {
//        String id=(String)CacheUtils.get("reuser", "id");
        Principal principal = getPrincipal(id);
        if (principal != null) {
            User user = get(principal.getId());
            if (user != null) {
                Principal principalTemp = (Principal) SecurityUtils.getSubject().getPrincipal();
                if(StringUtils.isNotBlank(id)){
                    principalTemp.setId(id);
                }
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<Role> getRoleList() {
        @SuppressWarnings("unchecked")
        List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
        if (roleList == null) {
            User user = getUser();
            if (user.isAdmin() || user.getLoginName().endsWith("admin")) {
                roleList = roleDao.findAllList(new Role());
            } else {
                Role role = new Role();
                role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
                roleList = roleDao.findList(role);
            }
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }


    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<Role> getRole() {
           List<Role> role = roleDao.getByRoleName(UserUtils.getUser().getId());
           putCache(CACHE_ROLE, role);
           return role;
    }


    /**
     * 获取集团管理员的机构id
     *
     * @return
     */
    public static User getUserId() {
        @SuppressWarnings("unchecked")
        User user = (User) getCache(CACHE_USER);
        if (user == null) {
            user = userDao.findUserByRoleName(new User());
        }
        putCache(CACHE_USER, user);
        return user;
    }

    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public static List<Menu> getMenuList() {
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
            User user = getUser();
            if (user.isSuperAdmin()) {
                menuList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                menuList = menuDao.findByUserId(m);
            }
            putCache(CACHE_MENU_LIST, menuList);
//        }
        return menuList;
    }

    /**
     * 获取当前用户授权的区域
     *
     * @return
     */
    public static List<Area> getAreaList() {
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>) getCache(CACHE_AREA_LIST);
        if (areaList == null) {
            areaList = areaDao.findAllList(new Area());
            putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
        if (officeList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                officeList = officeDao.findAllList(new Office());
            } else {
                /*Office office = new Office();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				officeList = officeDao.findList(office);*/
                officeList = officeDao.findAllList(new Office());
            }
            putCache(CACHE_OFFICE_LIST, officeList);
        }
        return officeList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeAllList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = officeDao.findAllList(new Office());
        }
        return officeList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {

                Subject subject = SecurityUtils.getSubject();
                Principal principal = (Principal) subject.getPrincipal();
                if (principal != null) {
                    return principal;
                }
//			subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }
    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal(String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                Subject subject = SecurityUtils.getSubject();
//                subject.logout();
                User user = userDao.get(id);
                Principal principal = new Principal(user,false);
                if (principal != null) {
                    return principal;
                }
            }else{
                Subject subject = SecurityUtils.getSubject();
                Principal principal = (Principal) subject.getPrincipal();
                if (principal != null) {
                    return principal;
                }
            }

//			subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {

                return session;
            }
//			subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}

    /**
     * 根据id获取用户姓名
     *
     * @param id
     * @return
     */
    public static String getUsername(String id) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userDao.get(id);
            if (user == null) {
                return null;
            }
        }
        return user.getName();
    }

    /**
     * 根据身份证号获取用户姓名
     *
     * @param cardNo
     * @return
     */
    public static String getUserNameByCardNo(String cardNo) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_CARDNO_ + cardNo);
        List<User> us = new ArrayList<>();
        if (user == null) {
            User u = new User();
            u.setCardNo(cardNo);
            us = userDao.getByCardNo(u);
            if (us == null && us.size() == 0) {
                return null;
            }
        }
        if (us.size() > 0) {
            return us.get(0).getName();
        }
        return "";
    }

    public static List<Role> getCurrentUserRole() {
        Role role = new Role();
        role.getSqlMap().put("dsf", BaseService.dataScopeFilter(getUser().getCurrentUser(), "o", "u"));
        List<Role> roleList = roleDao.findList(role);
        if (roleList == null) roleList = new ArrayList<>();
        return roleList;
    }


    /**
     * 查询当前登录人所在的机构列表
     *
     * @param
     * @return
     */
    public static List<User> getCompanyList() {

        //首先拿到当前登陆人的身份证号作为唯一的验证
        User user = new User();
        user.setCardNo(UserUtils.getUser().getCardNo());
        //根据身份证号查询当前登陆人所在的机构列表
        List<User> users = userDao.getByCardNo(user);
        return users;
    }
    /**
     * 查询被代理人所在的机构列表
     *
     * @param
     * @return
     */
    public static List<User> getCompanyList1(String userId) {

        //首先拿到当前登陆人的身份证号作为唯一的验证
        User user = new User();
        user.setCardNo(UserUtils.get(userId).getCardNo());
        //根据身份证号查询当前登陆人所在的机构列表
        List<User> users = userDao.getByCardNo(user);
        return users;
    }

    public static List<MailInfo> getMailList() {

        //首先拿到当前登陆人的身份证号作为唯一的验证
        MailInfo mailInfo=new MailInfo();
        mailInfo.setDelFlag("0");
        mailInfo.setState("INBOX");
        mailInfo.setFlag("0");
        mailInfo.setOwnId(UserUtils.getUser().getId());
        //根据身份证号查询当前登陆人所在的机构列表
        List<MailInfo> mailInfoList = mailInfoDao.findList(mailInfo);
        return mailInfoList;
    }

    /**
     * 查询被代理人姓名
     *
     * @param
     * @return
     */
    public static String getNameByID(String userId) {

        if ((userId.indexOf(",") != -1)) {
            String evaluateById = userId + ",";
            String[] ids = evaluateById.split(",");
            StringBuffer name = new StringBuffer();
            for(String id:ids){
                name.append(UserUtils.get(id).getName()+",");
            }
            return name.toString();
        }else if(userId == null || userId.equals("")){
            return null;
        }else {
            return UserUtils.get(userId).getName();
        }
    }

    public static User getFirstUserByGrades(String grades) {
        User user = UserUtils.getUser();
        if(StrUtil.isNotBlank(grades)) {
            List<String> gradeList = StrUtil.split(grades, ',');
            List<User> userIds = userDao.selectLoginnameByGradeAndOffice(user.getOffice().getId(), Lists.newArrayList(gradeList));
            return userIds.size() > 0 ? userIds.get(0) : null;
        }
        return null;
    }
}
