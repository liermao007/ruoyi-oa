/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.LeaveApplication;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 请假单统计DAO接口
 * @author oa
 * @version 2017-09-04
 */
@MyBatisDao
public interface LeaveApplicationDao extends CrudDao<LeaveApplication> {

    List<LeaveApplication> findPage(@Param("sql")String sql);
	
}