/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xc.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.xc.entity.OaXccl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡查处理记录DAO接口
 * @author oa
 * @version 2023-05-31
 */
@MyBatisDao
public interface OaXcclDao extends CrudDao<OaXccl> {

    List<Office> notReportDeptList(@Param("queryDate") String queryDate);
}