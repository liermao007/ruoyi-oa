/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.entity.Device;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;

/**
 * 手机端视图拦截器
 * @author oa
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null) {
			String viewName = modelAndView.getViewName();
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			if(viewName.startsWith("modules/") && UserAgentUtils.isMobile(request)){
				modelAndView.setViewName(viewName.replaceFirst("modules", "mobile"));
				/*增加判断安卓系统和微信浏览器，两个条件都为真时mobileType为android，
				* 前台js判断如果mobileType为android采用流方式下载文件*/
				String agent=request.getHeader("User-Agent").toLowerCase();
				Device device=new Device();
				device.setType("mobile");
				if(agent.indexOf("android")>0&&agent.indexOf("micromessenger")>0){
					device.setSystem("android");
				}
				else if(agent.indexOf("ios")>0&&agent.indexOf("iphone")>0&&agent.indexOf("ipad")>0){
					device.setSystem("ios");
				}else {
					device.setSystem(userAgent.getOperatingSystem().getName());
				}
				if(agent.indexOf("micromessenger")>0){
					device.setBrowser("micromessenger");
				}else {
					device.setBrowser(userAgent.getBrowser().getName());
				}
				modelAndView.addObject("device",device);
//				modelAndView.setViewName(viewName.replaceFirst("sysIndex-all", "sysIndex"));
//				modelAndView.setViewName(viewName.replaceFirst("sysIndex-first", "sysIndex"));
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		
	}

}
