/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.AddDict;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典DAO接口
 * @author oa
 * @version 2014-05-16
 */
@MyBatisDao
public interface AddDictDao extends CrudDao<AddDict> {

	public List<String> findTypeList(AddDict ddDict);
    public List<AddDict> findAllList (AddDict ddDict);
    public AddDict findByLabel (@Param("label") String label,@Param("companyId") String companyId,@Param("DEL_FLAG_NORMAL") String DEL_FLAG_NORMAL);


    public List<AddDict> findIsApprove (@Param("loginName") String loginName,@Param("companyId") String companyId);
    public int updateLeave (@Param("companyId") String companyId,@Param("userNmae") String userNmae,@Param("remarks") String remarks,@Param("name") String name);




}
