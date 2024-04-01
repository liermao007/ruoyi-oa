package com.thinkgem.jeesite.modules.oa.entity;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.modules.form.entity.DsvsInfo;
import lombok.Data;

import java.util.Map;

/**
 * 流程数据
 * @author oa
 * @version 2016-12-19
 */
@Data
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
}