package com.thinkgem.jeesite.modules.oaStatistics.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 */
public class OaStatisticsField extends DataEntity<OaStatisticsField>{

    private String id;
    private String statisticsId;       //统计id

    private String statisticsTableId;  //统计表ID

    private String fieldName;       //字段名称

    private String dataType;        //数据字典

    private String figureOut;       //合计
    private String controlTypeId;   //控件显示
    private String columnType;      //数据类型
    private String serial;          //序号

    private String flag;            //是否选择，0：为选择  1：已选择

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

    public String getStatisticsTableId() {
        return statisticsTableId;
    }

    public void setStatisticsTableId(String statisticsTableId) {
        this.statisticsTableId = statisticsTableId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getFigureOut() {
        return figureOut;
    }

    public void setFigureOut(String figureOut) {
        this.figureOut = figureOut;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getControlTypeId() {
        return controlTypeId;
    }

    public void setControlTypeId(String controlTypeId) {
        this.controlTypeId = controlTypeId;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
