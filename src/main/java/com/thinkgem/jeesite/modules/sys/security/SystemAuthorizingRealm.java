/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.linkFace.linkface.exception.LinkFaceException;
import com.thinkgem.jeesite.modules.linkFace.linkface.service.SearchService;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.web.LoginController;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 系统安全认证实现类
 * @author oa
 * @version 2014-7-5
 */
@Service
//@DependsOn({"userDao","roleDao","menuDao"})
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SystemService systemService;
	@Autowired
	private HttpServletRequest httpRequest;
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		int activeSessionSize = getSystemService().getSessionDao().getActiveSessions(false).size();
		if (logger.isDebugEnabled()){
			logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
		}

		// 校验登录验证码
//		if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
//			Session session = UserUtils.getSession();
//			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
//			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
//				throw new AuthenticationException("msg:验证码错误, 请重试.");
//			}
//		}
		// 校验用户名密码
		//增加了kisso后本方法中修改的地方只有这一句话，原来是：User user = getSystemService().getUserByLoginName(token.getUsername());
		User user =null;
		if(!token.isLogin()){
			user =  getSystemService().getUserByLoginName(token.getUsername());
		}else{
			if(StringUtils.isBlank(token.getUserlogo())) {
				user = getSystemService().getUser(token.getId());
			}
			else {
				DateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
				String time = date.format(new Date());        //添加时间
				//处理LinkFace的图片 转换成userlogoid
				String baseImg = token.getUserlogo();
				String folderPath=httpRequest.getSession().getServletContext()
						.getRealPath("/")
						+ "linkFace\\";
				String imgFilePath =folderPath + UUID.randomUUID().toString() + time + ".jpg";
				boolean b = getSystemService().transcoding(baseImg, imgFilePath, folderPath);
				if(!b){
					throw new LinkFaceException("msg:上传图片无效，请重新上传");
				}
				JSONObject linkface=null;
				try {
					File file1 = new File(imgFilePath);
					MultipartEntity entity = new MultipartEntity();
					entity.addPart("selfie_file", new FileBody(file1));
					entity.addPart("name", new StringBody(Global.getConfig("linkface.name")));

					SearchService searchService  = new SearchService();
					linkface = searchService.searchImageSearchForFile(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(null == linkface){
					throw new LinkFaceException("msg:该用户不存在");
				}
				if(Double.parseDouble(linkface.get("score").toString()) <= 0.75){
					throw new LinkFaceException("msg:人脸认证未通过");
				}else{
					user = getSystemService().linkFace(linkface.get("person_uuid").toString());
					if(user==null){
						throw new LinkFaceException("msg:人脸认证未通过");
					}
				}
			}
		}

		if (user != null) {
			if (Global.NO.equals(user.getLoginFlag())){
				throw new AuthenticationException("msg:该已帐号禁止登录.");
			}
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
			return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()),
					user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()){
					for (Session session : sessions){
						getSystemService().getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else{
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(),",")){
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()){
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}

	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}

	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermitted(permissions, info);
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}

	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermittedAll(permissions, info);
	}

	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		SsoCredentialsMatcher matcher = new SsoCredentialsMatcher();
		setCredentialsMatcher(matcher);
	}

//	/**
//	 * 清空用户关联权限认证，待下次使用时重新加载
//	 */
//	public void clearCachedAuthorizationInfo(Principal principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
//		clearCachedAuthorizationInfo(principals);
//	}

	/**
	 * 清空所有关联认证
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
//		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//		if (cache != null) {
//			for (Object key : cache.keys()) {
//				cache.remove(key);
//			}
//		}
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private String id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private String mobile;  //手机
		private String email;  //邮箱
		private String cardNo;  //身份证号
		private boolean mobileLogin; // 是否手机登录
		private String password;  //密码

//		private Map<String, Object> cacheMap;

		public Principal(User user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.mobile=user.getMobile();
			this.name = user.getName();
			this.email = user.getEmail();
			this.cardNo = user.getCardNo();
			this.password = user.getPassword();
			this.mobileLogin = mobileLogin;
		}

		public String getId() {
			return id;
		}

        public void setId(String id) {
            this.id = id;
        }

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public String getPassword() {
			return password;
		}

		public String getMobile() {
			return mobile;
		}

		public String getEmail() {
			return email;
		}

		public String getCardNo() {
			return cardNo;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

		public void setPassword(String password) {
			this.password = password;
		}

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) UserUtils.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}

		@Override
		public String toString() {
			return id;
		}

	}
}
