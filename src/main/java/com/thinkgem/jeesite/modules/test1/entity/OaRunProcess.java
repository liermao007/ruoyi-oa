/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test1.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 已办流程Entity
 * @author oa
 * @version 2017-12-18
 */
public class OaRunProcess extends DataEntity<OaRunProcess> {

	private static final long serialVersionUID = 1L;
	private String agent;		// 代理人该字段改为被代理人
	private String agentid;		// 代理人ID该字段改为被代理人ID
	private String principal;		// 被代理人该字段改为代理人
	private String principalid;		// 被代理人ID代理人ID
	private String companyId;//机构id
	private String officeName;
	private String oldAgentid; //存放修改之前的代理人id
	private String byFlag;   //待办为0，已办为1
	private String orgId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getByFlag() {
		return byFlag;
	}

	public void setByFlag(String byFlag) {
		this.byFlag = byFlag;
	}

	public String getOldAgentid() {
		return oldAgentid;
	}

	public void setOldAgentid(String oldAgentid) {
		this.oldAgentid = oldAgentid;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public OaRunProcess() {
		super();
	}

	public OaRunProcess(String id){
		super(id);
	}

	@Length(min=0, max=64, message="agent长度必须介于 0 和 64 之间")
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	@Length(min=0, max=64, message="agentid长度必须介于 0 和 64 之间")
	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	@Length(min=0, max=64, message="principal长度必须介于 0 和 64 之间")
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Length(min=0, max=64, message="principalid长度必须介于 0 和 64 之间")
	public String getPrincipalid() {
		return principalid;
	}

	public void setPrincipalid(String principalid) {
		this.principalid = principalid;
	}

}