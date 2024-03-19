/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 角色流程对应Entity
 * @author oa
 * @version 2018-01-03
 */
public class ActRole extends DataEntity<ActRole> {
	
	private static final long serialVersionUID = 1L;
	private String roleId;		// 角色id
	private String actId;		// 流程名称
    private String roleName;

	public ActRole() {
		super();
	}

	public ActRole(String id){
		super(id);
	}

	@Length(min=1, max=64, message="角色id长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Length(min=1, max=64, message="流程名称长度必须介于 1 和 64 之间")
	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}
	
}