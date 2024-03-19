/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.process.entity.MedicalOperationReport;

import java.util.List;

/**
 * 医疗运营报表DAO接口
 * @author oa
 * @version 2018-01-08
 */
@MyBatisDao
public interface MedicalOperationReportDao extends CrudDao<MedicalOperationReport> {

    /**
     * 月的累计数据插入
     * @param medicalOperationReport
     * @return
     */
    public Integer insertMonths(MedicalOperationReport medicalOperationReport);

    /**
     * 月的累计数据修改
     * @param medicalOperationReport
     * @return
     */
    public Integer updateMonths(MedicalOperationReport medicalOperationReport);

    /**
     * 年的累计数据插入
     * @param medicalOperationReport
     * @return
     */
    public Integer insertYear(MedicalOperationReport medicalOperationReport);

    /**
     * 年的累计数据修改
     * @param medicalOperationReport
     * @return
     */
    public Integer updateYear(MedicalOperationReport medicalOperationReport);

    /**
     * 查询当前月份的数据是否存在
     * @param medicalOperationReport
     * @return
     */
    public List<MedicalOperationReport> findListByMonths(MedicalOperationReport medicalOperationReport);

    /**
     * 查询当前月份的数据是否存在
     * @param medicalOperationReport
     * @return
     */
    public List<MedicalOperationReport> findListByYear(MedicalOperationReport medicalOperationReport);

    /**
     * 查询日月年合计
     * @param medicalOperationReport
     * @return
     */
    public List<MedicalOperationReport> findAllList(MedicalOperationReport medicalOperationReport);


	
}