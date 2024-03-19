/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 具体发送短信的表Entity
 * @author oa
 * @version 2017-12-11
 */
public class TableSet extends DataEntity<TableSet> {

	private static final long serialVersionUID = 1L;
	private String id;
	// 表Id
	private String tableId;
	// 表名
	private String tableName;
	// 表名
	private String tableComment;
	// 机构id
	private String companyId;
	// 是否发送
	private String isSend;
	@Override
	public String getId() { return id; }
	@Override
	public void setId(String id) { this.id = id; }

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}