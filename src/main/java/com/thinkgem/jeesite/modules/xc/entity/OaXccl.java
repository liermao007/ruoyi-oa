/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xc.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 巡查处理记录Entity
 * @author oa
 * @version 2023-05-31
 */
public class OaXccl extends DataEntity<OaXccl> {
	
	private static final long serialVersionUID = 1L;
	private Office office;		// sys_office 表的主键
	private String tableId;		// 表
	private String columnName;		// 注释
	private String recordId;		// 数据记录ID
	private String recordProject;		// 巡查项目
	private String recordValue;		// 巡查值
	private String recordRemark;		// 巡查备注
	private String handleOpinion;		// 处理意见
	private String handleUserId;		// 处理人ID
	private String handleTime;		// 处理时间

	private String xcr;
	private String tbks;
	private String xcrq;
	private String handleUsername;

	public OaXccl() {
		super();
	}

	public OaXccl(String id){
		super(id);
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=64, message="表长度必须介于 0 和 64 之间")
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	@Length(min=0, max=30, message="注释长度必须介于 0 和 30 之间")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	@Length(min=0, max=64, message="数据记录ID长度必须介于 0 和 64 之间")
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Length(min=0, max=10, message="巡查值长度必须介于 0 和 10 之间")
	public String getRecordValue() {
		return recordValue;
	}

	public void setRecordValue(String recordValue) {
		this.recordValue = recordValue;
	}
	
	@Length(min=0, max=400, message="巡查备注长度必须介于 0 和 400 之间")
	public String getRecordRemark() {
		return recordRemark;
	}

	public void setRecordRemark(String recordRemark) {
		this.recordRemark = recordRemark;
	}
	
	@Length(min=0, max=400, message="处理意见长度必须介于 0 和 400 之间")
	public String getHandleOpinion() {
		return handleOpinion;
	}

	public void setHandleOpinion(String handleOpinion) {
		this.handleOpinion = handleOpinion;
	}
	
	@Length(min=0, max=64, message="处理人ID长度必须介于 0 和 64 之间")
	public String getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}
	
	@Length(min=0, max=20, message="处理时间长度必须介于 0 和 20 之间")
	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	public String getXcr() {
		return xcr;
	}

	public void setXcr(String xcr) {
		this.xcr = xcr;
	}

	public String getRecordProject() {
		return recordProject;
	}

	public void setRecordProject(String recordProject) {
		this.recordProject = recordProject;
	}

	public String getTbks() {
		return tbks;
	}

	public void setTbks(String tbks) {
		this.tbks = tbks;
	}

	public String getXcrq() {
		return xcrq;
	}

	public void setXcrq(String xcrq) {
		this.xcrq = xcrq;
	}

	public String getHandleUsername() {
		return handleUsername;
	}

	public void setHandleUsername(String handleUsername) {
		this.handleUsername = handleUsername;
	}
}