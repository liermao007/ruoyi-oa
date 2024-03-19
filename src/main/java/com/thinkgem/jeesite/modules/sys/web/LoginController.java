/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.Result;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.wechat.JSSDK_Config;
import com.thinkgem.jeesite.common.wechat.WeChat;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.service.MailInfoService;
import com.thinkgem.jeesite.modules.oa.service.OaNewsService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;
import com.thinkgem.jeesite.modules.sys.utils.NoticeUnits;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 登录Controller
 * @author oa
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private SessionDAO sessionDAO;

    @Autowired
    private MailInfoService mailInfoService;
    @Autowired
    private OaNewsService oaNewsService;

	@Login(action = Action.Skip)
	@RequestMapping(value = "${adminPath}/mobileLogin", method = {RequestMethod.POST})
	@ResponseBody
	public Result<String> mobileLogin(@RequestBody LoginReq login, HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(), login.getPassword().toCharArray(), false, StringUtils.getRemoteAddr(request), null, true, null);
		try {
			subject.login(token);
		} catch (Exception e) {
			return Result.error();
		}
		Session session = subject.getSession();
		return Result.success(subject.getSession().getId().toString());
	}

	@Login(action = Action.Skip)
	@RequestMapping(value = "${adminPath}/mobileLogout", method = {RequestMethod.POST})
	@ResponseBody
	public Result mobileLogout() {
		UserUtils.clearCache(UserUtils.getUser());
		UserUtils.getSubject().logout();
		return Result.success();
	}

	/**
	 * 管理登录
	 */
    @Login(action = Action.Skip)
	@RequestMapping(value = "${adminPath}/login", method = {RequestMethod.GET,RequestMethod.HEAD})
	public String login(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        CacheUtils.put("reuser", "id", id);
        //切换机构时，传递id改变内容
        UserUtils.getUser(id);
        Principal principal = UserUtils.getPrincipal();

		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null/* && !principal.isMobileLogin()*/){

			return "redirect:" + adminPath;
		}
		return "modules/sys/sysLogin";
	}

    /**
     * 管理登录
     */
    @Login(action = Action.Skip)
    @RequestMapping(value = "${adminPath}/loginHrm", method = {RequestMethod.GET,RequestMethod.HEAD})
    @ResponseBody
    public String loginHrm(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        if (logger.isDebugEnabled()){
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
            CookieUtils.setCookie(response, "LOGINED", "false");
        }

        return "success";
    }
	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal= UserUtils.getPrincipal();


		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}",
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}

		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}

		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}

		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(String id,HttpServletRequest request, HttpServletResponse response,Model model) {
//        id=(String)CacheUtils.get("reuser", "id");
        Principal principal= UserUtils.getPrincipal();
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}

        // 收件箱提醒
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setReadMark("0");
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        mailInfo1.setState("INBOX");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        if(delete!=null && delete.size()>0){
            model.addAttribute("remind", delete.size());
        }
        //新闻提醒
        OaNews oaNews=new OaNews();
        String CompanyId = UserUtils.getUser().getCompany().getId();
        oaNews.setCompanyId(CompanyId);
        oaNews.setAuditFlag("1");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        date = calendar.getTime();
        oaNews.setUpdateDate(date);//获取一周内的新闻提醒
        oaNews.setSelf(true);
        List<OaNews> newsList=oaNewsService.findList(oaNews);
        if(newsList!=null && newsList.size()>0){
            model.addAttribute("newsLength", newsList.size());}

        //待审新闻提醒
        User user = UserUtils.getUser();
        oaNews.setAuditMan(user.getId());
        oaNews.setAuditFlag("1");

        oaNews.setUserId("");
        oaNews.setCurrentUser(null);
        List<OaNews> newsAduitlist=  NoticeUnits.getAuditNews();

        if(newsAduitlist!=null && newsAduitlist.size()>0){
        model.addAttribute("newsAduit", newsAduitlist.size());}
        model.addAttribute("user",user);
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	/**
	 * 退出本系统，退回到登录页
	 * @auto zhangyao
	 * @return
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value = "${adminPath}/logMy")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		UserUtils.clearCache(UserUtils.getUser());
		UserUtils.getSubject().logout();
		return "modules/sys/sysLogin";
	}
	/**
	 * 退出本系统，只是返回状态，之后在前台处理：跳转到岗位中
	 * @auto lhl
	 * @return
	 */
	@Login(action = Action.Skip)
	@ResponseBody
	@RequestMapping(value = "${adminPath}/logMyOA")
	public Boolean logoutMyOA(HttpServletRequest request, HttpServletResponse response) {
		UserUtils.clearCache(UserUtils.getUser());
		UserUtils.getSubject().logout();
		return true;
	}
	@Login(action = Action.Skip)
	@ResponseBody
	@RequestMapping(value = "${adminPath}/getWechatConfig")
	public WeChat getWechatConfig(HttpServletRequest request, HttpServletResponse response) {
		try {
			String url=request.getParameter("url");
			Map<String, String> configMap = JSSDK_Config.jsSDK_Sign(url);
			WeChat weChat=WeChat.success(configMap);
			return weChat;
		} catch (Exception e) {
			return WeChat.error();
		}
	}
}
