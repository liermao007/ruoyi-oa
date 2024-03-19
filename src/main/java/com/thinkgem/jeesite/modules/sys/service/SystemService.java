/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import cn.hutool.core.codec.Base64;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SynchroDataUtils;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.dao.*;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.dao.AmqMsgDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author oa
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private SessionDAO sessionDao;

    @Autowired
    private AddDictDao addDictDao;

    @Autowired
    private SystemAuthorizingRealm systemRealm;

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    @Autowired
    private IdentityService identityService;

    @Autowired
    private DictDao dictDao;

    @Autowired
    private AmqMsgDao amqMsgDao;

    //-- User Service --//

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    public User getUser(String id) {
        return UserUtils.get(id);
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return
     */
    public User getUserByLoginName(String loginName) {
        return UserUtils.getByLoginName(loginName);
    }

    public User getUserByLoginName(String loginName, String companyId) {
        return UserUtils.getByLoginName(loginName);
    }

    public Page<User> findUser(Page<User> page, User user) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
        // 设置分页参数
        user.setPage(page);
        // 执行分页查询
        List<User> users = userDao.findList(user);
        UserUtils.getOtherInfoForList(users, false);
        page.setList(users);
        return page;
    }

    /**
     * 无分页查询人员列表
     *
     * @param user
     * @return
     */
    public List<User> findUser(User user) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
        List<User> list = userDao.findList(user);
        UserUtils.getOtherInfoForList(list, false);
        return list;
    }


    public Page<User> findPage(Page<User> page, User user) {
        // 设置分页参数
        user.setPage(page);
        // 执行分页查询
        List<User> users = userDao.findList(user);
        UserUtils.getOtherInfoForList(users, false);
        page.setList(users);
        return page;
    }

    public User getByName(User user) {

        return userDao.getByName(user);
    }

    /**
     * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByOfficeId(String officeId) {
        List<User> list = (List<User>) CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
        if (list == null) {
            User user = new User();
            user.setOffice(new Office(officeId));
            list = userDao.findUserByOfficeId(user);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
        }
        return list;
    }


    /**
     * 通过公司ID获取用户列表，仅返回用户id和name（树查询用户时用）
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByCompanyId(String companyId) {

        if (companyId != null) {

            String delFlag = "0";
            List<User> list = userDao.findUserByCompanyId(companyId, delFlag);
            return list;
        }
        return null;
    }


    /**
     * 通过部门ID获取角色列表，仅返回角色id和name（树查询角色时用）
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Role> findRoleByOfficeId(String officeId) {
        List<Role> list = (List<Role>) CacheUtils.get(UserUtils.CACHE_ROLE_LIST, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
        if (list == null) {
            Role role = new Role();
            role.setOffice(new Office(officeId));
            list = roleDao.findRoleByOfficeId(role);
            CacheUtils.put(UserUtils.CACHE_ROLE_LIST, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
        }
        return list;
    }

    @Transactional(readOnly = false)
    public void saveUser(User user) {
        if (StringUtils.isBlank(user.getId())) {
            user.preInsert();
            userDao.insert(user);
        } else {
            // 清除原用户机构用户缓存
            User oldUser = userDao.get(user.getId());
            if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null) {
                CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
            }
            // 更新用户数据
            user.preUpdate();
            userDao.update(user);
        }
        if (StringUtils.isNotBlank(user.getId())) {
            // 更新用户与角色关联
            userDao.deleteUserRole(user);
            if (user.getRoleList() != null && user.getRoleList().size() > 0) {
                userDao.insertUserRole(user);
            } else {
                throw new ServiceException(user.getLoginName() + "没有设置角色！");
            }
            // 将当前用户同步到Activiti
            saveActivitiUser(user);
            // 清除用户缓存
            UserUtils.clearCache(user);
        }
    }


    @Transactional(readOnly = false)
    public void updateUserInfo(User user) {
        user.preUpdate();
        userDao.updateUserInfo(user);
        //修改后的用户同步到其他系统中
        user = UserUtils.get(user.getId());
        user.setHrmFlag("1");
        User u = userDao.getByName(user);
        user.setId(u.getId());
        try {
            JSONArray jsonArray = new JSONArray();

            JSONObject jsonObject = addPhoneJsonValue(user);

            jsonArray.add(jsonObject);
            String msgId = UUID.randomUUID().toString();
            String json = jsonArray.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 清除用户缓存
        UserUtils.clearCache(user);
        CacheUtils.remove("userCache", "id_" + user.getId()); //CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);

//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
    }

    /**
     * 将OA修改的用户同步到其他系统中
     *
     * @param user
     * @return
     */
    public JSONObject addUserJsonValue(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("name", user.getName());
        jsonObject.put("cardNo", user.getCardNo());
        jsonObject.put("orgId", user.getCompany().getId());
        jsonObject.put("deptId", user.getOffice().getId());
        jsonObject.put("phoneNo", user.getPhone());
        jsonObject.put("updateBy", user.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateString = formatter.format(user.getUpdateDate());
        jsonObject.put("updateDate", updateString);
        return jsonObject;
    }

    /**
     * 将OA修改的用户同步到其他系统中
     *
     * @param user
     * @return
     */
    public JSONObject addPwdJsonValue(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("cardNo", user.getCardNo());
        jsonObject.put("password", user.getPassword());
        return jsonObject;
    }

    /**
     * 将OA修改的用户同步到其他系统中
     *
     * @param user
     * @return
     */
    public JSONObject addPhoneJsonValue(User user) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("name", user.getName());
        jsonObject.put("cardNo", user.getCardNo());
        jsonObject.put("orgId", user.getCompany().getId());
        jsonObject.put("deptId", user.getOffice().getId());
        jsonObject.put("phoneNo", user.getPhone());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("password", user.getPassword());//密码
        jsonObject.put("createDate", user.getCreateDate());
        jsonObject.put("updateDate", user.getUpdateDate());
        return jsonObject;
    }


    @Transactional(readOnly = false)
    public void deleteUser(User user) {
        userDao.delete(user);
        // 同步到Activiti
        deleteActivitiUser(user);
        // 清除用户缓存
        UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
    }

    @Transactional(readOnly = false)
    public void updatePasswordById(String id, String loginName, String newPassword) {
        User user = userDao.get(id);
        user.setPassword(entryptPassword(newPassword));
        userDao.updatePasswordByCardNo(user);
        // 清除用户缓存
        user.setLoginName(loginName);
        UserUtils.updateCache(user);
        Subject subject = SecurityUtils.getSubject();
        SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
        principal.setPassword(user.getPassword());

        //修改后的用户同步到其他系统中
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = addPwdJsonValue(user);
            jsonArray.add(jsonObject);
            String msgId = UUID.randomUUID().toString();
            String json = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
    }

    /**
     * 保存带有图片路径的图片
     *
     * @param file
     * @param request
     * @throws java.io.IOException
     */
    @Transactional(readOnly = false)
    public String saveContractFile(MultipartFile file, String id, HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        String filePath = null;
        String realPath = request.getSession().getServletContext().getRealPath("/static" + File.separator + "userfiles");
        System.out.println(file.getOriginalFilename());
        String filename = file.getOriginalFilename();
        filename = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID() + "info" + filename;
        System.out.println(filename);
        File targetFile = new File(realPath + File.separator + "contract");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        File desFile = new File(realPath + File.separator + "contract" + File.separator + filename);
        if (!desFile.exists()) {
            desFile.createNewFile();
        }
        filePath = desFile.getPath();
        System.out.println("路径：" + filePath);
        filePath = filePath.substring(filePath.indexOf("webapp") + 6);
        FileOutputStream fileOutputStream = new FileOutputStream(desFile);
        String s = filePath.replaceAll("\\\\", "\\/");
        byte[] bytes = new byte[1024];
        while (!(inputStream.read(bytes) == -1)) {
            fileOutputStream.write(bytes);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
        //文件保存成功
        User user = new User();
        String path = s.substring(s.lastIndexOf("static") - 1);
        user.setPhoto(path);
        if (StringUtils.equals(id, "")) {
            user.preInsert();
            userDao.insert(user);
        } else {
            user = userDao.get(id);
            user.setPhoto(path);
            userDao.update(user);
            UserUtils.clearCache(user);
            CacheUtils.remove("userCache", "id_" + user.getId());
        }
        return path;
    }

    @Transactional(readOnly = false)
    public void updateUserLoginInfo(User user) {
        // 保存上次登录信息
        user.setOldLoginIp(user.getLoginIp());
        user.setOldLoginDate(user.getLoginDate());
        // 更新本次登录信息
        user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
        user.setLoginDate(new Date());
        userDao.updateLoginInfo(user);
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        String a = Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
        System.out.println(a);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    /**
     * 获得活动会话
     *
     * @return
     */
    public Collection<Session> getActiveSessions() {
        return sessionDao.getActiveSessions(false);
    }

    //-- Role Service --//

    public Role getRole(String id) {
        return roleDao.get(id);
    }

    public Role getRoleByName(String name) {
        Role r = new Role();
        r.setName(name);
        return roleDao.getByName(r);
    }

    public Role getRoleByEnname(String enname) {
        Role r = new Role();
        r.setEnname(enname);
        return roleDao.getByEnname(r);
    }

    public List<Role> findRole(Role role) {
        return roleDao.findList(role);
    }

    public List<Role> findAllRole() {
        return UserUtils.getRoleList();
    }

    /**
     * 根据userId查询角色信息
     *
     * @param userId
     * @return
     */
    public List<Role> getRoleByUserId(String userId) {
        return roleDao.getRoleByUserId(userId);
    }

    @Transactional(readOnly = false)
    public void saveRole(Role role) {
        if (StringUtils.isBlank(role.getId())) {
            role.preInsert();
            roleDao.insert(role);
            // 同步到Activiti
            saveActivitiGroup(role);
        } else {
            role.preUpdate();
            roleDao.update(role);
        }
        // 更新角色与菜单关联
        roleDao.deleteRoleMenu(role);
        if (role.getMenuList().size() > 0) {
            roleDao.insertRoleMenu(role);
        }
        // 更新角色与部门关联
        roleDao.deleteRoleOffice(role);
        if (role.getOfficeList().size() > 0) {
            roleDao.insertRoleOffice(role);
        }
        // 同步到Activiti
        saveActivitiGroup(role);
        // 清除用户角色缓存
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
    }

    @Transactional(readOnly = false)
    public void deleteRole(Role role) {
        roleDao.delete(role);
        // 同步到Activiti
        deleteActivitiGroup(role);
        // 清除用户角色缓存
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
    }

    @Transactional(readOnly = false)
    public Boolean outUserInRole(Role role, User user) {
        List<Role> roles = user.getRoleList();
        for (Role e : roles) {
            if (e.getId().equals(role.getId())) {
                roles.remove(e);
                saveUser(user);
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = false)
    public User assignUserToRole(Role role, User user) {
        if (user == null) {
            return null;
        }
        List<String> roleIds = user.getRoleIdList();
        if (roleIds.contains(role.getId())) {
            return null;
        }
        user.getRoleList().add(role);
        saveUser(user);
        return user;
    }

    //-- Menu Service --//

    public Menu getMenu(String id) {
        return menuDao.get(id);
    }

    public List<Menu> findAllMenu() {
        return UserUtils.getMenuList();
    }

    @Transactional(readOnly = false)
    public void saveMenu(Menu menu) {

        // 获取父节点实体
        menu.setParent(this.getMenu(menu.getParent().getId()));

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = menu.getParentIds();

        // 设置新的父节点串
        menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

        // 保存或更新实体
        if (StringUtils.isBlank(menu.getId())) {
            User user = UserUtils.getUser();
            Role role = roleDao.getByUserId(user.getId());
            menu.preInsert();
            if (!UserUtils.getUser().isAdmin()) {
                String menuId = menu.getId();
                int i = menuDao.insert(menu);
                if (i > 0) {
                    Menu menu1 = new Menu();
                    menu1.setRoleId(role.getRoleId());
                    menu1.setMenuId(menuId);
                    menuDao.insertRoleMenu(menu1);
                }

            } else {
                menuDao.insert(menu);
            }

        } else {
            menu.preUpdate();
            menuDao.update(menu);
        }

        // 更新子节点 parentIds
        Menu m = new Menu();
        m.setParentIds("%," + menu.getId() + ",%");
        List<Menu> list = menuDao.findByParentIdsLike(m);
        for (Menu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
            menuDao.updateParentIds(e);
        }
        // 清除用户菜单缓存
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
        // 清除日志相关缓存
        CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    @Transactional(readOnly = false)
    public void updateMenuSort(Menu menu) {
        menuDao.updateSort(menu);
        // 清除用户菜单缓存
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
        // 清除日志相关缓存
        CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    @Transactional(readOnly = false)
    public void deleteMenu(Menu menu) {
        menuDao.delete(menu);
        // 清除用户菜单缓存
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
        // 清除日志相关缓存
        CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    /**
     * 获取Key加载信息
     */
    public static boolean printKeyLoadMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n======================================================================\r\n");
        sb.append("\r\n                   欢迎使用 OA管理系统                                 \r\n");
        sb.append("\r\n======================================================================\r\n");
        System.out.println(sb.toString());
        return true;
    }

    ///////////////// Synchronized to the Activiti //////////////////

    // 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

    /**
     * 是需要同步Activiti数据，如果从未同步过，则同步数据。
     */
    private static boolean isSynActivitiIndetity = true;

    public void afterPropertiesSet() throws Exception {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        if (isSynActivitiIndetity) {
            isSynActivitiIndetity = false;
            // 同步角色数据
            List<Group> groupList = identityService.createGroupQuery().list();
            if (groupList.size() == 0) {
                Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
                while (roles.hasNext()) {
                    Role role = roles.next();
                    saveActivitiGroup(role);
                }
            }
            // 同步用户数据
            List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
            if (userList.size() == 0) {
                Iterator<User> users = userDao.findAllList(new User()).iterator();
                while (users.hasNext()) {
                    saveActivitiUser(users.next());
                }
            }
        }
    }

    private void saveActivitiGroup(Role role) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        String groupId = role.getEnname();

        // 如果修改了英文名，则删除原Activiti角色
        if (StringUtils.isNotBlank(role.getOldEnname()) && !role.getOldEnname().equals(role.getEnname())) {
            identityService.deleteGroup(role.getOldEnname());
        }

        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) {
            group = identityService.newGroup(groupId);
        }
        group.setName(role.getName());
        group.setType(role.getRoleType());
        identityService.saveGroup(group);

        // 删除用户与用户组关系
        List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery().memberOfGroup(groupId).list();
        for (org.activiti.engine.identity.User activitiUser : activitiUserList) {
            identityService.deleteMembership(activitiUser.getId(), groupId);
        }

        // 创建用户与用户组关系
        List<User> userList = findUser(new User(new Role(role.getId())));
        for (User e : userList) {
            String userId = e.getLoginName();//ObjectUtils.toString(user.getId());
            // 如果该用户不存在，则创建一个
            org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
            if (activitiUser == null) {
                activitiUser = identityService.newUser(userId);
                activitiUser.setFirstName(e.getName());
                activitiUser.setLastName(StringUtils.EMPTY);
                activitiUser.setEmail(e.getEmail());
                activitiUser.setPassword(StringUtils.EMPTY);
                identityService.saveUser(activitiUser);
            }
            identityService.createMembership(userId, groupId);
        }
    }

    public void deleteActivitiGroup(Role role) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        if (role != null) {
            String groupId = role.getEnname();
            identityService.deleteGroup(groupId);
        }
    }

    private void saveActivitiUser(User user) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
        org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
        if (activitiUser == null) {
            activitiUser = identityService.newUser(userId);
        }
        activitiUser.setFirstName(user.getName());
        activitiUser.setLastName(StringUtils.EMPTY);
        activitiUser.setEmail(user.getEmail());
        activitiUser.setPassword(StringUtils.EMPTY);
        identityService.saveUser(activitiUser);

        // 删除用户与用户组关系
        List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group : activitiGroups) {
            identityService.deleteMembership(userId, group.getId());
        }
        // 创建用户与用户组关系
        for (Role role : user.getRoleList()) {
            String groupId = role.getEnname();
            // 如果该用户组不存在，则创建一个
            Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
            if (group == null) {
                group = identityService.newGroup(groupId);
                group.setName(role.getName());
                group.setType(role.getRoleType());
                identityService.saveGroup(group);
            }
            identityService.createMembership(userId, role.getEnname());
        }
    }

    private void deleteActivitiUser(User user) {
        if (!Global.isSynActivitiIndetity()) {
            return;
        }
        if (user != null) {
            String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
            identityService.deleteUser(userId);
        }
    }

    ///////////////// Synchronized to the Activiti end //////////////////

    public Menu selectMenuByName(String name) {
        Menu menu = menuDao.selectMenuByName(name);
        return menu;
    }


    /**
     * OA同步人力资源的数据时，根据传递的身份证号查询用户的id
     *
     * @param user
     * @return
     */
   /* public User findCardNoUser(User user) {
        return userDao.findByName(user);
    }*/


    /**
     * OA同步人力资源的角色数据时，展示时根据部门来查看角色
     *
     * @param role
     * @return
     */
  /*  public List<Role> findRoleByDept(Role role) {
        return roleDao.findRoleByDept(role);
    }*/


    /**
     * OA同步人力资源角色数据，包括原有数据同步和AMQ新增数据同步
     *
     * @param type      判断是新增、修改或者删除操作
     * @param inputLine 人力资源传递的json字符串
     * @return
     * @author oa
     */
   /* @Transactional(readOnly = false)
    public String saveRole(String type, String inputLine) {
        inputLine = inputLine.replaceAll("sort", "enname");
        //将json字符串转换成json数组后，直接将json数组转换成java对象的list集合，之后遍历集合保存到数据库中
        //处理转换JSON中日期到JAVA Date类型的处理方式
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
        JSONArray jArray = JSONArray.fromObject(inputLine);
        List<Role> list1 = (List<Role>) JSONArray.toCollection(jArray, Role.class);
        for (int i = 0; i < list1.size(); i++) {
            Role a = list1.get(i);
            Office office = new Office();
            //设置归属的机构
            office.setId(a.getDeptId());
            a.setOffice(office);
            //设置OA中角色特有的数据
            //设置是否是系统数据
            a.setSysData("0");
            a.setRemarks(a.getEnname());
            //设置是否可用
            a.setUseable("1");
            //设置数据范围，默认是设置的所在公司的数据   为3
            a.setDataScope("3");
            //设置任务分配
            a.setEnname(null);
            a.setRoleType("assignment");
            a = dealWithUser(a);
            if (StringUtils.equalsIgnoreCase("add", type)) {
                //OA同步人力资源数据包括（AMQ新增和webService原有数据）
                saveRoleHrm(a);
            } else if (StringUtils.equalsIgnoreCase("update", type)) {
                System.out.println(a);
                Role of = roleDao.get(a.getId());
                    *//*of.preUpdate();
                    dao.update(of);*//*
            } else if (StringUtils.equalsIgnoreCase("delete", type)) {
                roleDao.delete(a);
            }

        }
        return "success";
    }*/


    /**
     * OA同步人力资源的角色数据
     * 包括角色与部门的对应表
     *
     * @param role
     */
 /*   @Transactional(readOnly = false)
    public void saveRoleHrm(Role role) {
        roleDao.insert(role);
        // 更新角色与部门关联
        roleDao.deleteRoleOffice(role);
        if (role.getOfficeList().size() > 0) {
            roleDao.insertRoleOffice(role);
        }
    }*/

    /**
     * 保存更新人员状态
     *
     * @param type
     * @param inputLine
     * @param orgId
     * @return
     */
    @Transactional(readOnly = false)
    public String savePeopleState(String type, String inputLine, String orgId) {
        List<Map<String, Object>> result;
        if (StringUtils.equalsIgnoreCase("1", UserUtils.getUser().getId())) {
            orgId = "";
        }
        if (StringUtils.isBlank(inputLine)) {
            String urlIP = DictUtils.getDValue("oa_hrm_ip", "");
            String urlString = DictUtils.getDValue("oa_people_data", "");
            String url = urlIP + urlString;
            if (StringUtils.isNotBlank(orgId)) {
                url = url + "?orgId=" + orgId;
            }
            result = convertList(SynchroDataUtils.getJsonByUrl(url));
        } else {
            result = convertList(inputLine);
        }
        if (result != null) {
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    User user = (User) entry.getValue();
                    //判断是否是离职人员，如果是离职人员再次添加到离职的机构时，则修改此人员的del_flag的 ,del_flag 为“05”则表示是离职人员
                    if (StringUtils.isNotBlank(user.getNo()) && StringUtils.equalsIgnoreCase(user.getNo(), "05")) {
                        user.setDelFlag(user.getNo());
                        userDao.updateByCardNo(user);
                    }
                }
            }
        }
        return "";
    }

    /**
     * OA同步人力资源的人员数据
     *
     * @param type
     */
    @Transactional(readOnly = false)
    public String savePeople(String type, String inputLine, String orgId) {
        List<Map<String, Object>> result;
        if (StringUtils.equalsIgnoreCase("1", UserUtils.getUser().getId())) {
            orgId = "";
        }
        if (StringUtils.isBlank(inputLine)) {
            String urlIP = DictUtils.getDValue("oa_hrm_ip", "");
            String urlString = DictUtils.getDValue("oa_people_data", "");
            String url = urlIP + urlString;
            if (StringUtils.isNotBlank(orgId)) {
                url = url + "?orgId=" + orgId;
            }
            result = convertList(SynchroDataUtils.getJsonByUrl(url));
        } else {
            result = convertList(inputLine);
        }

        //修改用户密码：单独处理
        if (StringUtils.isNoneBlank(type) && type.equals("updatePwd")) {
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    User user = (User) entry.getValue();
                    if (user != null && StringUtils.isNoneBlank(user.getId()) && StringUtils.isNoneBlank(user.getPassword())) {
                        // 更新用户密码
                        userDao.updatePasswordByCardNo(user);
                       /*
                        Subject subject= SecurityUtils.getSubject();
                        SystemAuthorizingRealm.Principal principal=(SystemAuthorizingRealm.Principal)subject.getPrincipal();
                        principal.setPassword(user.getPassword());*/
                        UserUtils.updateCache(user);
                        UserUtils.clearCache(user);
                        UserUtils.clearCache(new User(user.getId()));
                        UserUtils.clearCache(UserUtils.getUser());
                        break;
                    }
                }
            }
            return "";
        }

        //删除用户时单独处理
        if (StringUtils.isNoneBlank(type) && type.equals("delete")) {
            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    Map<String, Object> map = result.get(i);
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        User user = (User) entry.getValue();
                        if (user != null) {
                            //根据身份证号和机构ID逻辑删除用户
                            userDao.deleteByCardNo(user);
                            break;
                        }
                    }
                }
            }
            return "";
        }
        //处理系统管理员需要保存的数据
        if (StringUtils.equalsIgnoreCase("1", UserUtils.getUser().getId())) {
            result = adminSaveData(result);
        }
        if (result != null) {
            //如果一个人存在多个机构或者多个多部门
            List<User> uses = userDao.findList(new User());
            Map<String, User> usersMap = Maps.uniqueIndex(uses, new Function<User, String>() {
                @Override
                public String apply(User user) {
                    return user.getCardNo() + user.getCompany().getId() + user.getOffice().getId();
                }
            });
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    User user = (User) entry.getValue();
                    if (user.getCardNo().startsWith("admin")) {
                        user.setPassword("689e59308652ebf4da2663c363db2ab7da87669efdb55ee5f09a95ec");
                        user.setLoginName(user.getCardNo());
                    }
                    if (usersMap.get(user.getCardNo() + user.getCompany().getId() + user.getOffice().getId()) == null) {
                        if (StringUtils.isNotBlank(type) && type.equals("update")) {
                            List<User> users = userDao.getListByName(user);
                            for (int x = 0; x < users.size(); x++) {
                                //机构id相等的同时，如果部门的id不同，并且type类型是“update”则是要修改用户的部门信息等
                                if (!StringUtils.equalsIgnoreCase(user.getOffice().getId(), users.get(x).getOffice().getId())
                                        && user.getCompany().getId().equals(users.get(x).getCompany().getId())) {
                                    User user1 = userDao.getByName(user);
                                    user1.setOffice(user.getOffice());
                                    user1.setLoginName(user.getLoginName());
                                    user1.setCompany(user.getCompany());
                                    user1.setCardNo(user.getCardNo());
                                    user1.setWorkPost(user.getWorkPost());
                                    user.setLeaderNotNull("1");
                                    user1.preUpdate();
                                    userDao.update(user1);
                                    //根据身份证号更新手机号
                                    user.setNo(null);
                                    updatePhone(user, user1);
                                }
                            }
                        } else {
                            user.setPassword("689e59308652ebf4da2663c363db2ab7da87669efdb55ee5f09a95ec");
                            user.setLoginName(user.getCardNo());
                            user.setDelFlag("0");
                            if (StringUtils.isBlank(user.getId())) {
                                user.setHrmFlag("1");
                                user.preInsert();
                            }
                            user.setLeaderNotNull("1");
                            //判断身份证号是否存在，如果存在则加hrm_flag
                            List<User> users = userDao.getByCardNo(user);
                            if (users != null && users.size() > 0) {
                                user.setHrmFlag("1");
                                user.setPassword(users.get(0).getPassword());
                                user.preInsert();
                            }
                            user.setNo(null);
                            userDao.insert(user);
                            //根据身份证号更新手机号
                            updatePhone(user, null);
                        }
                    } else {
                        User updateUser = usersMap.get(user.getCardNo() + user.getCompany().getId() + user.getOffice().getId());
                        updateUser.setOffice(user.getOffice());
                        updateUser.setLoginName(user.getLoginName());
                        updateUser.setCompany(user.getCompany());
                        updateUser.setCardNo(user.getCardNo());
                        updateUser.setWorkPost(user.getWorkPost());
                        updateUser.preUpdate();
                        userDao.update(updateUser);
                        //根据身份证号更新手机号
                        user.setNo(null);
                        updatePhone(user, updateUser);
                    }
                }
            }
            return "success";
        }
        return null;
    }

    /**
     * 根据身份证号来查询更新多机构的用户的手机号
     *
     * @param user       同步过来的人员的信息
     * @param updateUser 从本地OA中查询出来的人员信息
     */
    public void updatePhone(User user, User updateUser) {
        //如果一个人存在多个机构中,那么在修改手机号的时候
        // 需要同步把两个机构的手机号数据全部修改
        User u = new User();
        u.setLoginName(user.getCardNo());
        List<User> users1 = userDao.getByLoginName(u);
        u.setPhone(user.getPhone());
        u.setCardNo(user.getCardNo());
        if (updateUser != null) {
            u.setMobile(updateUser.getMobile());
        } else {
            u.setMobile(user.getPhone());
        }
        if (users1 != null && users1.size() > 0) {
            userDao.updatePhoneByCardNo(u);
        }
    }

    /**
     * 转换，将人力资源传递的数据转成list集合
     *
     * @param json 人力资源地址,或者是AMQ传递的json数据
     * @return
     * @author oa
     */
    public List<Map<String, Object>> convertList(String json) {
        Map<String, String[]> replaceMap = new HashMap<>();
        String namespace = User.class.getName();
        replaceMap.put("id", new String[]{namespace, "id"});
        replaceMap.put("name", new String[]{namespace, "name"});
        replaceMap.put("cardNo", new String[]{namespace, "cardNo"});
        replaceMap.put("orgId", new String[]{namespace, "company.id"});
        replaceMap.put("deptId", new String[]{namespace, "office.id"});
        replaceMap.put("phoneNo", new String[]{namespace, "phone"});
        replaceMap.put("email", new String[]{namespace, "email"});
        replaceMap.put("state", new String[]{namespace, "no"});
        replaceMap.put("workPost", new String[]{namespace, "workPost"});
        replaceMap.put("password", new String[]{namespace, "password"});//密码
        replaceMap.put("createBy", new String[]{namespace, "createBy.id"});
        replaceMap.put("createDate", new String[]{namespace, "createDate"});
        replaceMap.put("updateBy", new String[]{namespace, "updateBy.id"});
        replaceMap.put("updateDate", new String[]{namespace, "updateDate"});
        return SynchroDataUtils.getDataByJson(json, replaceMap);
    }


    /**
     * 如果是系统管理员登陆，那么只同步人力资源中的所有管理员的用户信息
     *
     * @param result 人力资源传递的数据
     * @return
     */
    public List<Map<String, Object>> adminSaveData(List<Map<String, Object>> result) {
        List<Map<String, Object>> re = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> map = result.get(i);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                User user = (User) entry.getValue();
                if (user.getCardNo().startsWith("admin")) {
                    Map<String, Object> maps = new HashMap<>();
                    maps.put(entry.getKey(), user);
                    re.add(maps);
                }
            }
        }
        result = new ArrayList<>();
        result.addAll(re);
        return result;
    }


    /**
     * OA同步人力资源的工作岗位
     *
     * @param type
     */
    @Transactional(readOnly = false)
    public String synchroworkPost(String type, String inputLine, String orgId) {
        List<Map<String, Object>> result = convertWorkPostList(inputLine);
        List<Dict> dicts = dictDao.findList(new Dict());
        Map<String, Dict> dictsMap = Maps.uniqueIndex(dicts, new Function<Dict, String>() {
            @Override
            public String apply(Dict dict) {
                return dict.getId();
            }
        });

        //删除工作岗位
        if (StringUtils.isNoneBlank(type) && type.equals("delete")) {
            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    Map<String, Object> map = result.get(i);
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        Dict dict = (Dict) entry.getValue();
                        if (dict != null) {
                            //根据身份证号和机构ID逻辑删除用户
                            dictDao.delete(dict);
                            break;
                        }
                    }
                }
            }
            return "";
        }

        if (result != null) {
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    Dict dict = (Dict) entry.getValue();
                    if (dict != null && StringUtils.equalsIgnoreCase(dict.getType(), "WORK_POST_DICT")) {
                        if (dictsMap.get(dict.getId()) == null) {
                            //向字典中插入工作岗位
                            dict.setDelFlag("0");
                            dict.setDescription("工作岗位");
                            dict.preInsert();
                            dictDao.insert(dict);
                        } else {
                            //修改字典中的工作岗位
                            Dict dict1 = dictsMap.get(dict.getId());
                            dict1.setValue(dict.getValue());
                            dict1.setLabel(dict.getLabel());
                            dictDao.update(dict1);
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * 转换，将人力资源传递的数据转成list集合
     * 同步工作岗位的数据
     *
     * @param json 人力资源地址,或者是AMQ传递的json数据
     * @return
     * @author oa
     */
    public List<Map<String, Object>> convertWorkPostList(String json) {
        Map<String, String[]> replaceMap = new HashMap<>();
        String namespace = Dict.class.getName();
        replaceMap.put("id", new String[]{namespace, "id"});
        replaceMap.put("type", new String[]{namespace, "type"});
        replaceMap.put("workPost", new String[]{namespace, "value"});
        replaceMap.put("workPostLabel", new String[]{namespace, "label"});
        replaceMap.put("sort", new String[]{namespace, "sort"});
        return SynchroDataUtils.getDataByJson(json, replaceMap);
    }

    /**
     * 根据姓名，登录名模糊查询
     *
     * @param
     * @return
     */
    public List<User> findListByName(User user) {
        return userDao.findListByName(user);
    }

    /**
     * 登录验证人脸识别：做登陆验证之用
     *
     * @return
     * @auto zhaochaozhu
     */
    public User linkFace(String personUuid) {
        return userDao.linkFace(personUuid);
    }


    //图片上传到服务器
    public boolean transcoding(String val, String imagePath, String folderPath) {
        if (val == null) {
            // 图像数据为空
            return false;
        } else {
            try {
                // Base64解码
                byte[] b = Base64.decode(val);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {// 调整异常数据
                        b[i] += 256;
                    }
                }
                //创建文件夹存放
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                // 生成jpeg图片
                OutputStream out = new FileOutputStream(imagePath);
                out.write(b);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    /**
     * 同步人脸识别信息
     *
     * @return
     */
    @Transactional(readOnly = false)
    public void saveLinkFace(String type, String text) {
        if (StringUtils.isNoneBlank(type) && type.equals("addFace")) {
            Map<String, String> data = new HashMap<String, String>();
            // 将json字符串转换成jsonObject
            JSONObject jsonObject = JSONObject.fromObject(text);
            Iterator ite = jsonObject.keys();
            // 遍历jsonObject数据,添加到Map对象
            while (ite.hasNext()) {
                String key = ite.next().toString();
                String value = jsonObject.get(key).toString();
                data.put(key, value);
            }
            User user = new User();
            user.setImageId(data.get("imageId"));
            user.setPersonUuid(data.get("personUuid"));
            user.setCardNo(data.get("cardNo"));
            userDao.insertUserFace(user);
        }
    }

    public List<User> toTaskFindListByName(User user) {
        return userDao.toTaskFindListByName(user);
    }

    public List<User> getListByName(User user) {

        return userDao.getListByName(user);
    }


    /**
     * 查询人员包括离职人员
     *
     * @param id
     * @return
     */
    public User getOne(String id) {
        return userDao.getOne(id);
    }

    /**
     * 查询离职人员
     *
     * @param officeId
     * @return
     */
    public Map<String, List<User>> findDelList(String officeId) {
        List<User> userList = userDao.findDelList(officeId);
        List<User> parentList = new ArrayList<>();
        List<User> approveList = new ArrayList<>();
        //直属领导
        for (User user : userList) {
            Integer count = userDao.findIsLeaders(user.getId(), officeId);
            if (count != null && count > 0) {
                parentList.add(user);
            }
        }


        for (User user : userList) {
            List<AddDict> list = addDictDao.findIsApprove(user.getLoginName(), officeId);
            if (list != null && list.size() > 0) {
                approveList.add(user);
            }
        }

        Map<String, List<User>> map = new LinkedHashMap<>();
        map.put("1", approveList);
        map.put("2", parentList);


        return map;
    }


    /**
     * 查询离职人员
     *
     * @param companyId
     * @return
     */
    public Map<String, List<User>> getEmplyeeList(String id, String companyId) {


        //直属领导

        List<User> parentList = userDao.findIsParent(id, companyId);

        //部门领导
        List<User> deptList = userDao.findIsDept(id, companyId);

        //一级领导
        List<User> leaderList = userDao.findIsLeader(id, companyId);

        Map<String, List<User>> map = new LinkedHashMap<>();
        map.put("2", parentList);
        map.put("3", deptList);
        map.put("4", leaderList);

        return map;
    }


    @Transactional(readOnly = false)
    public int updateLeaveTotal(String key, String id, String loginName, String hrmFlay) {
        String companyId = UserUtils.getUser().getCompany().getId();
        User user = new User();
        user.setId(id);
        user.setCompanyId(companyId);
        user.setRemarks(key);
        user.setHrmFlag(hrmFlay);
        User user1 = userDao.getOne(hrmFlay);
        user.setLoginName(user1.getLoginName());
        if (key.equals("1")) {
            return addDictDao.updateLeave(companyId, loginName, user1.getLoginName(), user1.getName());
        } else {
            return userDao.updateLeaveTotal(user);
        }

    }

    @Transactional(readOnly = false)
    public void updateLeave(String key, String id, String loginName, String hrmFlay, String ids) {
        String companyId = UserUtils.getUser().getCompany().getId();
        User user = new User();
        user.setCompanyId(companyId);
        user.setRemarks(key);
        user.setHrmFlag(hrmFlay);
        user.setId(ids);
        User user1 = userDao.getOne(hrmFlay);
        user.setLoginName(user1.getLoginName());
        userDao.updateLeave(user);
    }


    /**
     * 查询无直属领导人员
     *
     * @param orgId
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public List<User> findPeopleNoLeader(String orgId, User user) {
        List<Office> office = userDao.findAllOffice(orgId);
        if (office.size() < 1) {
            return null;
        }
        User dept = new User();
        User level = new User();
        user.setCompanyId(orgId);
        List<User> users = userDao.findPeopleNoDept(user);
        for (Office office1 : office) {
            if (StringUtils.isNotBlank(office1.getId())) {
                String officeId = office1.getId();
                user = userDao.findAllOfficeLeader(officeId, orgId);
                dept = userDao.findAllOfficeDept(officeId, orgId);
                level = userDao.findAllOfficeLevel(officeId, orgId);
                for (User user2 : users) {
                    //判断此部门的直属领导，如果有，赋值
                    if (user != null && user.getOfficeId().equals(user2.getOfficeId())) {
                        user2.setLeaderShip(user.getId());
                        user2.setAcName(user.getLeaderShip());
                    }
                    //判断此部门的部门领导，如果有，赋值
                    if (dept != null && dept.getOfficeId().equals(user2.getOfficeId())) {
                        user2.setDept(dept.getId());
                        user2.setAcDeptName(dept.getDeptName());
                    }
                    //判断此部门的一级领导，如果有，赋值
                    if (level != null && level.getOfficeId().equals(user2.getOfficeId())) {
                        user2.setLevel1(level.getId());
                        user2.setLevel1Name(level.getAcLevel1());
                    }
                }
            }
        }
        return users;
    }

    /**
     * 转换，将岗位传递的数据转成list集合
     *
     * @param jsonArray
     * @return
     * @author oa
     */
    public String[] convertList(JSONArray jsonArray, String type, int length) {
        List<Map<String, Object>> mapListJson = jsonArray;
        String[] list = new String[length];
        for (int i = 0; i < mapListJson.size(); i++) {
            if ("id".equals(type)) {
                if (mapListJson.get(i).get("id") != null && StringUtils.isNoneBlank(mapListJson.get(i).get("id").toString())) {
                    list[i] = (String) mapListJson.get(i).get("id");
                } else {
                    list[i] = "";
                }
            } else if ("leader".equals(type)) {
                if (mapListJson.get(i).get("leader") != null && StringUtils.isNoneBlank(mapListJson.get(i).get("leader").toString())) {
                    list[i] = (String) mapListJson.get(i).get("leader");
                } else {
                    list[i] = "";
                }
            } else if ("dept".equals(type)) {
                if (mapListJson.get(i).get("dept") != null && StringUtils.isNoneBlank(mapListJson.get(i).get("dept").toString())) {
                    list[i] = (String) mapListJson.get(i).get("dept");
                } else {
                    list[i] = "";
                }
            } else {
                if (mapListJson.get(i).get("level") != null && StringUtils.isNoneBlank(mapListJson.get(i).get("level").toString())) {
                    list[i] = (String) mapListJson.get(i).get("level");
                } else {
                    list[i] = "";
                }
            }
        }
        return list;
    }


    @Transactional(rollbackFor = Exception.class)
    public int updateNoDept(String[] ids, String orgId, String[] leaderShips, String[] depts, String[] levels) {
        User user = new User();
        int result = 0;
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i])) {
                user.setId(ids[i]);
            }
            if (StringUtils.isNotBlank(orgId)) {
                user.setCompanyId(orgId);
            }
            //判断直属领导是否为空
            if (StringUtils.isNotBlank(leaderShips[i])) {
                if (!leaderShips[i].equals("1")) {
                    user.setLeaderName(userDao.get(leaderShips[i]).getLeaderName());
                    user.setAcName(userDao.get(leaderShips[i]).getLoginName());
                    user.setLeaderShip(leaderShips[i]);
                    user.setLeaderNotNull("2");
                } else {
                    user.setLeaderNotNull("0");
                }
            } else {
                user.setLeaderNotNull("0");
            }
            //判断部门领导是否为空
            if (StringUtils.isNotBlank(depts[i])) {
                if (!depts[i].equals("1")) {
                    user.setAcDeptName(userDao.get(depts[i]).getLoginName());
                    user.setDept(depts[i]);
                }
            }
            //判断一级领导是否为空
            if (StringUtils.isNotBlank(levels[i])) {
                if (!levels[i].equals("1")) {
                    user.setLevel1Name(userDao.get(levels[i]).getLoginName());
                    user.setLevel1(levels[i]);
                }
            }
            user.preUpdate();
            result = userDao.updateNoLeader(user);
        }
        return result;
    }

    public List<UserPhone> selectAllPhone() {
        User user = UserUtils.getUser();
        String companyId = user.getCompany() != null ? user.getCompany().getId() : "";
        return userDao.selectAllPhone(companyId);
    }
}