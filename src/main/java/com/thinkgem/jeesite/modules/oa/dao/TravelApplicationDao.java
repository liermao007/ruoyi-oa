/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.LeaveApplication;
import com.thinkgem.jeesite.modules.oa.entity.TravelApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出差统计表DAO接口
 * @author oa
 * @version 2017-09-04
 */
@MyBatisDao
public interface TravelApplicationDao extends CrudDao<TravelApplication> {

    List<TravelApplication> findPage(@Param("sql")String sql);
	
}