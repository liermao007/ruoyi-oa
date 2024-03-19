package com.thinkgem.jeesite.modules.sys.filter;

/**
 * 
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoFilter implements Filter {
    public GoFilter(){}

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException,ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getServletPath();
        //过滤请求[/a/amq]：消息中间件
        //过滤请求[/a/service/tool]：接口（例如：岗位需要的几种OA数据）
        //过滤请求[/servlet/validateCodeServlet]：登录页验证码
        if(uri.equals("/a/amq")||uri.indexOf("/a/service/tool")>=0||uri.indexOf("/servlet/validateCodeServlet")>=0){
            request.getRequestDispatcher(uri).forward(request, response);//
            /**
             *  用forward这样的方式会跳过后续的所有过滤器处理，如果有某个过滤器仍然需要进行处理，那可以在 web.xml 中配置该过滤器 <filter-mapping>：
             <dispatcher>FORWARD</dispatcher>
             过滤器处理时，不进行后续过滤器链处理（FilterChain#doFilter()），而是直接转发请求给 Servlet（RequestDispatcher#forward()）
             */
        }else{
            chain.doFilter(httpRequest, httpResponse);
        }
    }
    public void destroy() {}
}