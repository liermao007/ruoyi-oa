/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 个人签名Entity
 * @author oa
 * @version 2017-02-06
 */
public class PersonSigns extends DataEntity<PersonSigns> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String signName;		// 签章名称
	private String signUrl;		// 签名图片路径
    private String loginId;    //当前登录人
	
	public PersonSigns() {
		super();
	}

	public PersonSigns(String id){
		super(id);
	}

	@Length(min=0, max=100, message="姓名长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="签章名称长度必须介于 0 和 100 之间")
	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}
	
	@Length(min=0, max=100, message="签名图片路径长度必须介于 0 和 1000 之间")
	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}