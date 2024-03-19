package com.thinkgem.jeesite.modules.table.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.test1.entity.OaRunProcess;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义数据源DAO接口
 * @author oa
 * @version 2016-11-24
 */
@MyBatisDao
public interface OaPersonDefineTableDao extends CrudDao<OaPersonDefineTable> {
    /**
     * 根据表名和机构Id查询
     * @param tableName
     * @param officeId
     * @return
     */
    OaPersonDefineTable findByTableName(@Param("tableName")String tableName, @Param("officeId")String officeId);

    List<Map<String,Object>> getFlowInfo1(@Param("sql")String sql);


    Map<String,String> getActReModel(@Param("sql")String sql);

    /**
     * 执行sql
     * @param sql
     */
    int executeSql(@Param("sql")String sql);

    int getCount(@Param("sql")String sql);

    String getMax(@Param("sql")String sql);

    Map<String,Object> getByProcInsId(@Param("sql")String sql);

    List<Map<String,Object>> getFlowInfo(@Param("sql")String sql);
    List<OaPersonDefineTable> findListByLoginName(@Param("loginName")String loginName);
    //List<Act> oaHasToDoTasks(@Param("loginName")String loginName,@Param("no")String no);
    //用来查询代理人集合
    List<OaRunProcess> findListByLoginId(@Param("loginId")String loginId);

    List<OaPersonDefineTable> findListByInstId(@Param("instId")String instId);

    List<Map<String,Object>> getFlowInfo(FlowData flow);
}