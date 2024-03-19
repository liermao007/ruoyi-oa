package com.thinkgem.jeesite.modules.sys.security;


import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 增加了kisso后增加了这个类。
 */

@Service
public class SsoCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private SystemService systemService ;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken tokenUser= (UsernamePasswordToken) token;
        if(StringUtils.isNotBlank(tokenUser.getUserlogo())){
            tokenUser.setUserlogo("");
            return true;
        }
        if(tokenUser.isLogin()){
            User user=getSystemService().getUser(tokenUser.getId());
            if(user!=null){
                return true;
            }else{
                return false;
            }
        }else{
            User user=getSystemService().getUserByLoginName(tokenUser.getUsername());
            if(user!=null){
                String password = String.valueOf(tokenUser.getPassword());
                boolean isLogin=systemService.validatePassword(password,user.getPassword());
                return isLogin;
            }else{
                return false;
            }
        }
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

}
