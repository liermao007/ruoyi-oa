/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.MailAccount;
import com.thinkgem.jeesite.modules.oa.dao.MailAccountDao;

/**
 * 邮件帐户设置Service
 * @author oa
 * @version 2016-12-15
 */
@Service
@Transactional(readOnly = true)
public class MailAccountService extends CrudService<MailAccountDao, MailAccount> {

	public MailAccount get(String id) {
		return super.get(id);
	}
	
	public List<MailAccount> findList(MailAccount mailAccount) {
		return super.findList(mailAccount);
	}
	
	public Page<MailAccount> findPage(Page<MailAccount> page, MailAccount mailAccount) {
		return super.findPage(page, mailAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(MailAccount mailAccount) {
		super.save(mailAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(MailAccount mailAccount) {
		super.delete(mailAccount);
	}
	
}