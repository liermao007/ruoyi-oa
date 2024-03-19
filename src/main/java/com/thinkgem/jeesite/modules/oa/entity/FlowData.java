package com.thinkgem.jeesite.modules.oa.entity;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.modules.form.entity.DsvsInfo;

import java.util.Map;

/**
 * 流程数据
 * @author oa
 * @version 2016-12-19
 */
public class FlowData extends ActEntity<FlowData> {

	private static final long serialVersionUID = 1L;
    private String tableName;
    private Map<String,Object> datas;
    private String formNo;  // 表单编号
    private String flowFlag;
    private String showType; //显示类型  flowForm填写，flowView查看，flowAudit审核
    private String sql;
    private String imgPath;
    private String BZ;     //标识
    private String SFCC;   //是否出差

    private DsvsInfo dsvsInfo;
    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getSFCC() {
        return SFCC;
    }

    public void setSFCC(String SFCC) {
        this.SFCC = SFCC;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getFlowFlag() {
        return flowFlag;
    }

    public void setFlowFlag(String flowFlag) {
        this.flowFlag = flowFlag;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public DsvsInfo getDsvsInfo() {
        return dsvsInfo;
    }

    public void setDsvsInfo(DsvsInfo dsvsInfo) {
        this.dsvsInfo = dsvsInfo;
    }
}