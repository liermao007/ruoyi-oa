/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.process.entity.HospitalMasterIndex;

import java.util.List;

/**
 * 医院经营指标DAO接口
 *
 * @author oa
 * @version 2018-06-26
 */
@MyBatisDao
public interface HospitalMasterIndexDao extends CrudDao<HospitalMasterIndex> {


    /**
     * 根据月份查询数据回显
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findListByMonths(HospitalMasterIndex hospitalMasterIndex);

    /**
     * 根据id查询没更新之前的数据，方便处理数据
     *
     * @param id
     * @return
     */
    public HospitalMasterIndex getByOld(String id);



    /**
     * 根据季度查询数据
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findListByQuarter(HospitalMasterIndex hospitalMasterIndex);

    /**
     * 根据年度查询数据
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findListByMaster(HospitalMasterIndex hospitalMasterIndex);


    /**
     * 根据年度查询数据
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findListByYear(HospitalMasterIndex hospitalMasterIndex);

    /**
     * 年的累计数据修改
     * @param hospitalMasterIndex
     * @return
     */
    public Integer updateYear(HospitalMasterIndex hospitalMasterIndex);

    /**
     * 季度的累计数据修改
     * @param hospitalMasterIndex
     * @return
     */
    public Integer updateQuarter(HospitalMasterIndex hospitalMasterIndex);

    /**
     * 月的累计数据修改
     * @param hospitalMasterIndex
     * @return
     */
    public Integer updateMonths(HospitalMasterIndex hospitalMasterIndex);

    /**
     * 季度累计增加
     * @param hospitalMasterIndex
     * @return
     */
    public Integer insertQuarter(HospitalMasterIndex hospitalMasterIndex);

    /**
     * 年累计增加
     * @param hospitalMasterIndex
     * @return
     */
    public Integer insertYear(HospitalMasterIndex hospitalMasterIndex);


    /**
     * 处理数据使用
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findAllListHandler(HospitalMasterIndex hospitalMasterIndex) ;
}
