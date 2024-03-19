/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.process.entity.CustomRuskTaskinst;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自由流程运行表DAO接口
 * @author oa
 * @version 2017-11-02
 */
@MyBatisDao
public interface CustomRuskTaskinstDao extends CrudDao<CustomRuskTaskinst> {

    /**
     * 根据实例id查询运行中的流程的信息
     * @param customRuskTaskinst
     * @return
     */
    public List<CustomRuskTaskinst> findByProInstId(CustomRuskTaskinst customRuskTaskinst);

    /**
     * 根据实例id来删除运行中的流程的相关信息
     * @param procInstId
     * @return
     */
    public Integer deleteByProcInstId(@Param("procInstId") String procInstId);

    /**
     * 根据实例id和是否会签的标识来删除运行中的流程的相关信息
     * @param procInstId
     * @return
     */
    public Integer deleteByProcInstIdAndFlag(@Param("procInstId") String procInstId);
}