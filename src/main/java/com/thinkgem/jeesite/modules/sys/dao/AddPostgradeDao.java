/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.AddPostgrade;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 字典DAO接口
 * @author oa
 * @version 2014-05-16
 */
@MyBatisDao
public interface AddPostgradeDao extends CrudDao<AddPostgrade> {

	public List<String> findTypeList(AddPostgrade ddDict);
    public List<AddPostgrade> findAllList(@Param("companyId") String companyId);
    public AddPostgrade findByLabel(@Param("label") String label, @Param("companyId") String companyId, @Param("DEL_FLAG_NORMAL") String DEL_FLAG_NORMAL);



}