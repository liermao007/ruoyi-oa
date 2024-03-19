package com.thinkgem.jeesite.modules.oaStatistics.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 */
public class OaStatisticsName extends DataEntity<OaStatisticsName> {

    private String id;
    private String statisticsName;      //统计名称

    private String orgId;


    public String getStatisticsName() {
        return statisticsName;
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
