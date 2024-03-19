package com.thinkgem.jeesite.modules.oaStatistics.entity;

import java.util.Date;
import java.util.List;

/**
 * 
 */
public class DataParameter {
    private String tableName;
    private List<String> fieldList;
    private String dataId;

    private String []  existingConditions;  //自定义条件数组
    private String []  existingConditionsName;//自定义条件数组(显示)
    private Date startDate; //开始时间
    private Date endDate; //结束时间
    private Date applicationDate; //申请时间
    private String proposer;    //申请人
    private String [] conditionGroupField;        //分组字段
    private String conditionSumField;          //统计字段

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String[] getExistingConditions() {
        return existingConditions;
    }

    public void setExistingConditions(String[] existingConditions) {
        this.existingConditions = existingConditions;
    }

    public String[] getExistingConditionsName() {
        return existingConditionsName;
    }

    public void setExistingConditionsName(String[] existingConditionsName) {
        this.existingConditionsName = existingConditionsName;
    }

    public String getConditionSumField() {
        return conditionSumField;
    }

    public void setConditionSumField(String conditionSumField) {
        this.conditionSumField = conditionSumField;
    }

    public String[] getConditionGroupField() {
        return conditionGroupField;
    }

    public void setConditionGroupField(String[] conditionGroupField) {
        this.conditionGroupField = conditionGroupField;
    }
}
