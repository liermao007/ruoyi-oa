/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.dao.CustomCommentDao;
import com.thinkgem.jeesite.modules.process.entity.CustomComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自由流程意见Service
 * @author oa
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class CustomCommentService extends CrudService<CustomCommentDao, CustomComment> {

	public CustomComment get(String id) {
		return super.get(id);
	}
	
	public List<CustomComment> findList(CustomComment customComment) {
		return super.findList(customComment);
	}
	
	public Page<CustomComment> findPage(Page<CustomComment> page, CustomComment customComment) {
		return super.findPage(page, customComment);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomComment customComment) {
		super.save(customComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomComment customComment) {
		super.delete(customComment);
	}
	
}