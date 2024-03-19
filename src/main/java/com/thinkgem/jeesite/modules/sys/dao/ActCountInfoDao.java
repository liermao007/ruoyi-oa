/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.ActCountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程信息汇DAO接口
 * @author oa
 * @version 2017-09-28
 */
@MyBatisDao
public interface ActCountInfoDao extends CrudDao<ActCountInfo> {


    public List<ActCountInfo> findInfonByProcInsId(@Param("procInsId")String procInsId);

    public List<ActCountInfo> findInfonByProcInsIds(@Param("procInsId")String procInsId);
	
}