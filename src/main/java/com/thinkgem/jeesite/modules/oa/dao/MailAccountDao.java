/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.MailAccount;

/**
 * 邮件帐户设置DAO接口
 * @author oa
 * @version 2016-12-15
 */
@MyBatisDao
public interface MailAccountDao extends CrudDao<MailAccount> {
	
}