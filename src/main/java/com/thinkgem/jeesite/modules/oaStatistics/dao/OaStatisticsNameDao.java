package com.thinkgem.jeesite.modules.oaStatistics.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oaStatistics.entity.OaStatisticsName;

/**
 * 
 */
@MyBatisDao
public interface OaStatisticsNameDao extends CrudDao<OaStatisticsName>{


    public int update(OaStatisticsName oaStatisticsName);
}
