/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.ProcurementApplication;

/**
 * 采购统计表DAO接口
 * @author oa
 * @version 2017-09-04
 */
@MyBatisDao
public interface ProcurementApplicationDao extends CrudDao<ProcurementApplication> {
	
}