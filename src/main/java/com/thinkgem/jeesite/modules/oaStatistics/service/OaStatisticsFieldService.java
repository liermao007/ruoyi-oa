package com.thinkgem.jeesite.modules.oaStatistics.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oaStatistics.dao.OaStatisticsFieldDao;
import com.thinkgem.jeesite.modules.oaStatistics.entity.DataParameter;
import com.thinkgem.jeesite.modules.oaStatistics.entity.OaStatisticsField;
import com.thinkgem.jeesite.modules.oaStatistics.entity.ParameterField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
 */
@Service
@Transactional(readOnly = false)
public class OaStatisticsFieldService extends CrudService<OaStatisticsFieldDao,OaStatisticsField> {

    public int addList(List<OaStatisticsField> list){
        return dao.addList(list);
    }

    public List<OaStatisticsField> getOaStatisticsTableId(String oaStatisticsTableId){

        return dao.getOaStatisticsTableId(oaStatisticsTableId);
    }

    public void updateField(String [] rank,String [] saveId,String tableId){
        dao.updateEliminate(tableId);
        if (saveId != null && saveId.length>0){
            OaStatisticsField oaStatisticsField = new OaStatisticsField();
            for (int i=0;i<saveId.length;i++){
                oaStatisticsField.setFlag("0");
                if (rank != null && rank.length >= i){
                    oaStatisticsField.setSerial(rank[i]);
                }
                oaStatisticsField.setId(saveId[i]);
                dao.update(oaStatisticsField);
        }
        }
    }

    public void updateAddField(String [] checkbox,String tableId){
        if (checkbox != null && checkbox.length>0) {
            OaStatisticsField oaStatisticsField = new OaStatisticsField();
            oaStatisticsField.setStatisticsTableId(tableId);
            for (int i=0;i<checkbox.length;i++){
                oaStatisticsField.setFlag("0");
                oaStatisticsField.setFieldName(checkbox[i]);
                Integer serial = i+1;
                oaStatisticsField.setSerial(serial.toString());
                dao.updateAddField(oaStatisticsField);
            }

        }
    }

    public List<OaStatisticsField> getFiled(String TJBID){

        return dao.getFiled(TJBID);
    }
    //获取数据
    public List<String> getFiledName(String TJBID){

        return dao.getFiledName(TJBID);
    }

    public List<Map<String,Object>> gettingData(DataParameter dataParameter){

        return dao.gettingData(dataParameter);
    }

    public List<List<Map<String,Object>>> gettingAllData(List<DataParameter> list){
        List<List<Map<String,Object>>> lists = dao.gettingAllData(list);
        return lists;
    }

    public Integer serial(String [] fieldId,String [] serial){
        OaStatisticsField oaStatisticsField = new OaStatisticsField();
        Integer ex=0;
        for (int i = 0;i < fieldId.length;i++){
            oaStatisticsField.setId(fieldId[i]);
            oaStatisticsField.setSerial(serial[i]);
            dao.update(oaStatisticsField);
            ex++;
        }
        return ex;
    }


    public List<List<Map<String,Object>>> details(DataParameter dataParameter){

        return dao.details(dataParameter);
    }

    public List<List<Map<String,Object>>> date(DataParameter dataParameter){

        return dao.date(dataParameter);
    }


    //    查询条件配置

    public List<OaStatisticsField> fieldType(OaStatisticsField oaStatisticsField){

        return dao.fieldType(oaStatisticsField);
    }

    public List<List<Map<String,Object>>> statisticalDetails(ParameterField parameterField){

        return dao.statisticalDetails(parameterField);
    }
}
