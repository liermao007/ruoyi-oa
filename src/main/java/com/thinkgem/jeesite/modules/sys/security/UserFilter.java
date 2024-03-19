/**
 *
 */
package com.thinkgem.jeesite.modules.sys.security;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static com.thinkgem.jeesite.modules.sys.utils.UserUtils.USER_CACHE;
import static com.thinkgem.jeesite.modules.sys.utils.UserUtils.USER_CACHE_ID_;

/**
 * 
 * 增加了kisso后增加了这个类，在spring-context-shiro.xml中增加的一行shiroFilter过滤userFilter就是对应本类。
 */
@Service
public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {
    private SystemService systemService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            if (subject.getPrincipal() != null) {
                SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
                User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + ((SystemAuthorizingRealm.Principal) subject.getPrincipal()).getId());
                if (user != null && !user.getPassword().equals(principal.getPassword())) {
                    try {
                        UserUtils.clearCache(UserUtils.getUser());
                        subject.logout();
                    } catch (Exception e) { //ignore
                    }
                    return false;
                }
            }
            return subject.getPrincipal() != null;
        }
    }

    /**
     * 获取系统业务对象
     */
    public SystemService getSystemService() {
        if (systemService == null) {
            systemService = SpringContextHolder.getBean(SystemService.class);
        }
        return systemService;
    }
}
