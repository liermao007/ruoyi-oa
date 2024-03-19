/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import com.thinkgem.jeesite.modules.oa.entity.LeaveApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.ProcurementApplication;
import com.thinkgem.jeesite.modules.oa.dao.ProcurementApplicationDao;

/**
 * 采购统计表Service
 * @author oa
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class ProcurementApplicationService extends CrudService<ProcurementApplicationDao, ProcurementApplication> {

	public ProcurementApplication get(String id) {
		return super.get(id);
	}
	
	public List<ProcurementApplication> findList(ProcurementApplication procurementApplication) {
		return super.findList(procurementApplication);
	}
	
	public Page<ProcurementApplication> findPage(Page<ProcurementApplication> page, ProcurementApplication procurementApplication) {
		return super.findPage(page, procurementApplication);
	}
	
	@Transactional(readOnly = false)
	public void save(ProcurementApplication procurementApplication) {
		super.save(procurementApplication);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProcurementApplication procurementApplication) {
		super.delete(procurementApplication);
	}


    public Page<ProcurementApplication> findProcurementApplication(Page<ProcurementApplication> page, ProcurementApplication procurementApplication) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        procurementApplication.getSqlMap().put("dsf", dataScopeFilter(procurementApplication.getCurrentUser(), "o", "a"));
        // 设置分页参数
        procurementApplication.setPage(page);
        // 执行分页查询
        List<ProcurementApplication> leaveApplications =super.findList(procurementApplication);

        page.setList(leaveApplications);
        return page;
    }
	
}