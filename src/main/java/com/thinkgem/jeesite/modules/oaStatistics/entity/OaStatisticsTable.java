package com.thinkgem.jeesite.modules.oaStatistics.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 
 */
public class OaStatisticsTable extends DataEntity<OaStatisticsTable> {

    private String id;

    private String statisticsId;    //统计id


    private String statisticsName;  //统计表名称

    private String dataTable;       //数据表

    private List<OaStatisticsField> oaStatisticsFields; //已选字段

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(String statisticsId) {
        this.statisticsId = statisticsId;
    }

    public String getStatisticsName() {
        return statisticsName;
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName;
    }

    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    public List<OaStatisticsField> getOaStatisticsFields() {
        return oaStatisticsFields;
    }

    public void setOaStatisticsFields(List<OaStatisticsField> oaStatisticsFields) {
        this.oaStatisticsFields = oaStatisticsFields;
    }

}
