/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 流程审核记录Entity
 * @author oa
 * @version 2023-06-10
 */
public class OaFlowAuditRecord extends DataEntity<OaFlowAuditRecord> {
	
	private static final long serialVersionUID = 1L;
	private String tableName;		// 表名称
	private String recordId;		// 表记录
	private String sigData;		// 审核原文
	private String sig;		// 签名
	private String sigDataBase64;		// base64格式原文
	private String sigServer;		// 个人证书
	private String ts;		// 时间戳
	private String sigPic;		// 签名照片
	private String auditResult;		// 审核结果
	private String auditComment;		// 审核意见
	
	public OaFlowAuditRecord() {
		super();
	}

	public OaFlowAuditRecord(String id){
		super(id);
	}

	@Length(min=1, max=100, message="表名称长度必须介于 1 和 100 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Length(min=1, max=64, message="表记录长度必须介于 1 和 64 之间")
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getSigData() {
		return sigData;
	}

	public void setSigData(String sigData) {
		this.sigData = sigData;
	}
	
	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}
	
	public String getSigDataBase64() {
		return sigDataBase64;
	}

	public void setSigDataBase64(String sigDataBase64) {
		this.sigDataBase64 = sigDataBase64;
	}
	
	public String getSigServer() {
		return sigServer;
	}

	public void setSigServer(String sigServer) {
		this.sigServer = sigServer;
	}
	
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	public String getSigPic() {
		return sigPic;
	}

	public void setSigPic(String sigPic) {
		this.sigPic = sigPic;
	}
	
	@Length(min=0, max=60, message="审核结果长度必须介于 0 和 60 之间")
	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	
	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}
	
}