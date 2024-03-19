/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaAuditMan;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;

import java.util.List;

/**
 * 新闻审核人DAO接口
 * @author oa
 * @version 2016-11-17
 */
@MyBatisDao
public interface OaAuditManDao extends CrudDao<OaAuditMan> {


    public List<OaAuditMan> findListbByCompanyId(OaAuditMan oaAuditMan);

	
}