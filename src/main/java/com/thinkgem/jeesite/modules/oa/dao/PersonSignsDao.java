/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.PersonSigns;

/**
 * 个人签名DAO接口
 * @author oa
 * @version 2017-02-06
 */
@MyBatisDao
public interface PersonSignsDao extends CrudDao<PersonSigns> {

    public PersonSigns getLoginId(String loginId);
	
}