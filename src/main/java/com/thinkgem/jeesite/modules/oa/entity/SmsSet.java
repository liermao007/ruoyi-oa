/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 *
 * @author oa
 * @version 2018-01-15
 */
public class SmsSet extends DataEntity<SmsSet> {

	private static final long serialVersionUID = 1L;
	private String id;
	// 机构id
	private String companyId;
	// 机构名
	private String companyName;
	// 是否发送
	private String isSend;

	@Override
	public String getId() { return id; }
	@Override
	public void setId(String id) { this.id = id; }

	public String getCompanyId() { return companyId; }

	public void setCompanyId(String companyId) { this.companyId = companyId; }

	public String getIsSend() { return isSend; }

	public void setIsSend(String isSend) { this.isSend = isSend; }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}