package com.thinkgem.jeesite.modules.oaStatistics.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oaStatistics.entity.OaStatisticsTable;

import java.util.List;

/**
 * 
 */
@MyBatisDao
public interface OaStatisticsTableDao extends CrudDao<OaStatisticsTable>{


    public List<OaStatisticsTable> findAll(OaStatisticsTable oaStatisticsTable);

}
