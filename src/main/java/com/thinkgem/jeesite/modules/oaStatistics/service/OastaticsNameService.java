package com.thinkgem.jeesite.modules.oaStatistics.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oaStatistics.dao.OaStatisticsNameDao;
import com.thinkgem.jeesite.modules.oaStatistics.entity.OaStatisticsName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 */
@Service
@Transactional(readOnly = false)
public class OastaticsNameService extends CrudService<OaStatisticsNameDao,OaStatisticsName> {

    public List<OaStatisticsName> findAllList(OaStatisticsName oaStatisticsName){


        return dao.findAllList(oaStatisticsName);
    }

    public Integer update(OaStatisticsName oaStatisticsName){

        return dao.update(oaStatisticsName);
    }

    public String add(OaStatisticsName oaStatisticsName){
        oaStatisticsName.preInsert();
        dao.insert(oaStatisticsName);
        return oaStatisticsName.getId();
    }
}
