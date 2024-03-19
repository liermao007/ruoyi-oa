/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.ActRole;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

/**
 * 角色流程对应DAO接口
 * @author oa
 * @version 2018-01-03
 */
@MyBatisDao
public interface ActRoleDao extends CrudDao<ActRole> {


     public ActRole findByRoleId(ActRole actRole);

    public int insert(ActRole actRole);
}