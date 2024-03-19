package com.baomidou.kisso.my.controller;

import com.baomidou.kisso.AuthToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.common.SSOProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 代理登录，跨域中间状态处理
 * </p>
 *
 * @author oa
 * @Date 2017/7/11
 */
@Controller
public class ProxyLoginController extends BaseController {

    /**
     * 跨域登录
     */
    @Login(action = Action.Skip)
    @RequestMapping("/proxylogin")
    public String proxylogin(HttpServletRequest request, HttpServletResponse response,Model model) {
        /**
         *
         * 用户自定义配置获取
         *
         * <p>
         * 由于不确定性，kisso 提倡，用户自己定义配置。
         * </p>
         *
         */
        SSOProperties prop = SSOConfig.getSSOProperties();

        //业务系统私钥签名 authToken 自动设置临时会话 cookie 授权后自动销毁
        AuthToken at = SSOHelper.askCiphertext(request, response, prop.get("sso.defined.my_private_key"));
        //askurl 询问 sso 是否登录地址
        model.addAttribute("askurl", prop.get("sso.defined.askurl"));
        //askTxt 询问 token 密文
        model.addAttribute("askData", at.encryptAuthToken());
        String a=at.encryptAuthToken();
        //获取项目路径进行登录
        String path = request.getContextPath();
        String retUrl= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+ prop.get("sso.defined.oklogin");
        //my 确定是否登录地址
        model.addAttribute("okurl",retUrl);
        return "modules/proxylogin";
    }

}