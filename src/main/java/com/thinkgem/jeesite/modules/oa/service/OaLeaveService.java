/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.dao.OaLeaveDao;
import com.thinkgem.jeesite.modules.oa.entity.OaLeave;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 请假单统计Service
 * @author oa
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class OaLeaveService extends CrudService<OaLeaveDao, OaLeave> {

	public OaLeave get(String id) {
		return super.get(id);
	}
	
	public List<OaLeave> findList(OaLeave oaLeave) {
		return super.findList(oaLeave);
	}
	
	public Page<OaLeave> findPage(Page<OaLeave> page, OaLeave oaLeave) {
		return super.findPage(page, oaLeave);
	}
	
	@Transactional(readOnly = false)
	public void save(OaLeave oaLeave) {
		super.save(oaLeave);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaLeave oaLeave) {
		super.delete(oaLeave);
	}
	
}