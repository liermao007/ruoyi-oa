package com.thinkgem.jeesite.modules.oaStatistics.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oaStatistics.dao.OaStatisticsTableDao;
import com.thinkgem.jeesite.modules.oaStatistics.entity.OaStatisticsTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 */
@Service
@Transactional(readOnly = false)
public class OaStatisticsTableService extends CrudService<OaStatisticsTableDao,OaStatisticsTable> {


    public List<OaStatisticsTable> findAll(String statisticsId){
        OaStatisticsTable oaStatisticsTable=new OaStatisticsTable();
        oaStatisticsTable.setStatisticsId(statisticsId);
        return dao.findAll(oaStatisticsTable);
    }

    public String saveTable(OaStatisticsTable oaStatisticsTable){
        oaStatisticsTable.preInsert();
        dao.insert(oaStatisticsTable);
        return oaStatisticsTable.getId();
    }

}
