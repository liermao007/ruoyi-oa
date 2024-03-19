/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

/**
 * 用户和密码（包含验证码）令牌类
 * @author oa
 * @version 2013-5-19
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	private boolean mobileLogin;

	//增加了kisso后本方法中修改的地方只是增加了两个属性id和isLogin，并加上get，set方法
	private String id;
	private boolean isLogin;
	private String userlogo;

	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password,
								 boolean rememberMe, String host, String captcha, boolean mobileLogin,String userlogo) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.userlogo = userlogo;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserlogo() {
		return userlogo;
	}

	public void setUserlogo(String userlogo) {
		this.userlogo = userlogo;
	}
}