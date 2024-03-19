package com.thinkgem.jeesite.modules.oaStatistics.entity;

import java.util.List;

/**
 * 
 */
public class ParameterField {
    private String [] parameterField;
    private String tableName;
    private List<String> fieldList;

    public String[] getParameterField() {
        return parameterField;
    }

    public void setParameterField(String[] parameterField) {
        this.parameterField = parameterField;
    }

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
}
