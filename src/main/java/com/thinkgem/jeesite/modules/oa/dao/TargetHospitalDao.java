/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.TargetHospital;

import java.util.List;

/**
 * 标的医院综合评价DAO接口
 * @author oa
 * @version 2017-04-06
 */
@MyBatisDao
public interface TargetHospitalDao extends CrudDao<TargetHospital> {


    public List<TargetHospital> findListById(TargetHospital targetHospital);

    public TargetHospital getById(TargetHospital targetHospital);
}