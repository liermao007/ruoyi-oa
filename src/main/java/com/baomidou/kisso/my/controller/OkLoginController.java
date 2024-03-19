package com.baomidou.kisso.my.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 考虑到 浏览器 url 地址长度限制，跨域不采用地址重定向方式、依旧使用  jsonp 跨域。
 *
 * <p>
 * IE6.0               		:url最大长度2083个字符，超过最大长度后无法提交。
 * IE7.0               		:url最大长度2083个字符，超过最大长度后仍然能提交，但是只能传过去2083个字符。
 * firefox 3.0.3     		:url最大长度7764个字符，超过最大长度后无法提交。
 * Opera 9.52       		:url最大长度7648个字符，超过最大长度后无法提交。
 * Google Chrome 2.0.168    :url最大长度7713个字符，超过最大长度后无法提交
 * </p>
 *
 * @author oa
 * @date 2017/7/11
 * @version 1.0.0
 */
@Controller
public class OkLoginController extends BaseController {

    /**
     * 跨域登录成功
     */
    @RequestMapping("/oklogin")
    public String oklogin(HttpServletRequest request, HttpServletResponse response) {
        Object returl = "";

        /**
         * 重定向到拦截的url
         */
        return "redirect:"+returl;
    }
}
