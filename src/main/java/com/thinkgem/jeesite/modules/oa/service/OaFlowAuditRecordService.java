/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaFlowAuditRecord;
import com.thinkgem.jeesite.modules.oa.dao.OaFlowAuditRecordDao;

/**
 * 流程审核记录Service
 * @author oa
 * @version 2023-06-10
 */
@Service
@Transactional(readOnly = true)
public class OaFlowAuditRecordService extends CrudService<OaFlowAuditRecordDao, OaFlowAuditRecord> {

	public OaFlowAuditRecord get(String id) {
		return super.get(id);
	}
	
	public List<OaFlowAuditRecord> findList(OaFlowAuditRecord oaFlowAuditRecord) {
		return super.findList(oaFlowAuditRecord);
	}
	
	public Page<OaFlowAuditRecord> findPage(Page<OaFlowAuditRecord> page, OaFlowAuditRecord oaFlowAuditRecord) {
		return super.findPage(page, oaFlowAuditRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(OaFlowAuditRecord oaFlowAuditRecord) {
		super.save(oaFlowAuditRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaFlowAuditRecord oaFlowAuditRecord) {
		super.delete(oaFlowAuditRecord);
	}
	
}