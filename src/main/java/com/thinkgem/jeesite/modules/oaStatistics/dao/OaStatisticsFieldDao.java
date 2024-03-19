package com.thinkgem.jeesite.modules.oaStatistics.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oaStatistics.entity.DataParameter;
import com.thinkgem.jeesite.modules.oaStatistics.entity.OaStatisticsField;
import com.thinkgem.jeesite.modules.oaStatistics.entity.ParameterField;

import java.util.List;
import java.util.Map;

/**
 * 
 */
@MyBatisDao
public interface OaStatisticsFieldDao  extends CrudDao<OaStatisticsField>{

    public Integer addList(List<OaStatisticsField> oaStatisticsFields);

    public List<OaStatisticsField> getOaStatisticsTableId(String oaStatisticsTableId);

    //修改统计清单字段
    public Integer updateEliminate(String tableId);

    public Integer updateField(OaStatisticsField oaStatisticsField);

    public List<OaStatisticsField> getFiled(String TJBID);

    public Integer updateAddField(OaStatisticsField oaStatisticsField);


    //获取数据
    //获取选中数据列
    public List<String> getFiledName(String TJBID);

    public List<Map<String,Object>> gettingData(DataParameter dataParameter);

    public List<List<Map<String,Object>>> gettingAllData(List<DataParameter> list);


    public List<List<Map<String,Object>>> details(DataParameter dataParameter);

    public List<List<Map<String,Object>>> date(DataParameter dataParameter);

    public List<OaStatisticsField> fieldType(OaStatisticsField oaStatisticsField);

    //按条件统计后查看详情
    public List<List<Map<String,Object>>> statisticalDetails(ParameterField parameterField);
}
