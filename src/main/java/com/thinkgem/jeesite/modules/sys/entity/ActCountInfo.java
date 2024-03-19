/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 流程信息汇Entity
 * @author oa
 * @version 2017-09-28
 */
public class ActCountInfo extends DataEntity<ActCountInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInstId;		// proc_inst_id
	private String procDefId;		// proc_def_id
	private String tableName;		// table_name
	private String loginName;		// login_name
	private String companyId;		// company_id
	private String procState;		// proc_state
	private String regectState;		// regect_state
    private String actName;		// regect_state
    private String assignee;		// regect_state
    private String actId;		// regect_state

    private String businessKey;		// regect_state
    	// regect_state
	
	public ActCountInfo() {
		super();
	}

	public ActCountInfo(String id){
		super(id);
	}

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Length(min=1, max=64, message="proc_inst_id长度必须介于 1 和 64 之间")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	@Length(min=1, max=64, message="proc_def_id长度必须介于 1 和 64 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	@Length(min=1, max=64, message="table_name长度必须介于 1 和 64 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Length(min=1, max=64, message="login_name长度必须介于 1 和 64 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=1, max=64, message="company_id长度必须介于 1 和 64 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=20, message="proc_state长度必须介于 0 和 20 之间")
	public String getProcState() {
		return procState;
	}

	public void setProcState(String procState) {
		this.procState = procState;
	}
	
	@Length(min=0, max=20, message="regect_state长度必须介于 0 和 20 之间")
	public String getRegectState() {
		return regectState;
	}

	public void setRegectState(String regectState) {
		this.regectState = regectState;
	}
	
}