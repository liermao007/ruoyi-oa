/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaFlowAuditRecord;

/**
 * 流程审核记录DAO接口
 * @author oa
 * @version 2023-06-10
 */
@MyBatisDao
public interface OaFlowAuditRecordDao extends CrudDao<OaFlowAuditRecord> {
	
}