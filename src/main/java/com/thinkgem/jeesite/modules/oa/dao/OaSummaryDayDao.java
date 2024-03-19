/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryDay;

import java.util.List;

/**
 * 工作日志DAO接口
 * @author oa
 * @version 2016-11-16
 */
@MyBatisDao
public interface OaSummaryDayDao extends CrudDao<OaSummaryDay> {

     public OaSummaryDay findByDate( OaSummaryDay oaSummaryDay);

    public List<OaSummaryDay> findByDate1( OaSummaryDay oaSummaryDay);

    public List<OaSummaryDay> findNewFore( OaSummaryDay oaSummaryDay);

	
}