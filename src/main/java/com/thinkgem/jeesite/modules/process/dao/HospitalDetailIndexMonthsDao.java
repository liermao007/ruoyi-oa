/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.process.entity.HospitalDetailIndexMonths;

/**
 * 医院经营指标月表DAO接口
 * @author oa
 * @version 2018-06-26
 */
@MyBatisDao
public interface HospitalDetailIndexMonthsDao extends CrudDao<HospitalDetailIndexMonths> {
	
}