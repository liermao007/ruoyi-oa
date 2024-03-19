/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xc.service;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.xc.entity.OaXccl;
import com.thinkgem.jeesite.modules.xc.dao.OaXcclDao;

/**
 * 巡查处理记录Service
 * @author oa
 * @version 2023-05-31
 */
@Service
@Transactional(readOnly = true)
public class OaXcclService extends CrudService<OaXcclDao, OaXccl> {

	public OaXccl get(String id) {
		return super.get(id);
	}
	
	public List<OaXccl> findList(OaXccl oaXccl) {
		return super.findList(oaXccl);
	}

	public List<Office> notReportDeptList(String queryDate) {
		return dao.notReportDeptList(queryDate);
	}
	
	public Page<OaXccl> findPage(Page<OaXccl> page, OaXccl oaXccl) {
		return super.findPage(page, oaXccl);
	}
	
	@Transactional(readOnly = false)
	public void save(OaXccl oaXccl) {
		oaXccl.setHandleTime(DateUtil.now());
		oaXccl.setHandleUserId(UserUtils.getUser().getId());
		super.save(oaXccl);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaXccl oaXccl) {
		super.delete(oaXccl);
	}
	
}