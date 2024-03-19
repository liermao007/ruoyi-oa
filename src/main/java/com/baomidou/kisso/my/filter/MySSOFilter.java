//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.baomidou.kisso.my.filter;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.web.filter.SSOFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class MySSOFilter extends SSOFilter {
    private static final Logger logger = Logger.getLogger("SSOFilter");
    private static String OVERURL = null;

    public MySSOFilter() {
    }

    public void init(FilterConfig config) throws ServletException {
        OVERURL = config.getInitParameter("over.url");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        HttpSession session = req.getSession();
        boolean isOver = HttpUtil.inContainURL(req, OVERURL);
        StringBuffer requestURL = req.getRequestURL();
        if(requestURL.indexOf("/oa/mailInfo/getFile") <= 0 &&requestURL.indexOf("/proxylogin") <= 0 && requestURL.indexOf("/oklogin") <= 0 && requestURL.indexOf("a/login") <= 0 && !isOver) {
            SystemAuthorizingRealm.Principal principal=UserUtils.getPrincipal();
            if(principal==null) {
                logger.fine("logout. request url:" + req.getRequestURL());
                String loginUrl = SSOConfig.getSSOProperties().get("sso.login.url");
                String path = req.getContextPath();
                String retUrl= req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/"+ URLEncoder.encode(SSOConfig.getSSOProperties().get("sso.defined.proxylogin"));
                session.setAttribute("returnUrl",requestURL);
                res.sendRedirect(HttpUtil.encodeRetURL(loginUrl, "ReturnURL", retUrl));
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
