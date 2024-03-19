/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.dao.CustomHiTaskinstDao;
import com.thinkgem.jeesite.modules.process.entity.CustomHiTaskinst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自由流程历史表Service
 * @author oa
 * @version 2017-11-02
 */
@Service
@Transactional(readOnly = true)
public class CustomHiTaskinstService extends CrudService<CustomHiTaskinstDao, CustomHiTaskinst> {

	public CustomHiTaskinst get(String id) {
		return super.get(id);
	}
	
	public List<CustomHiTaskinst> findList(CustomHiTaskinst customHiTaskinst) {
		return super.findList(customHiTaskinst);
	}
	
	public Page<CustomHiTaskinst> findPage(Page<CustomHiTaskinst> page, CustomHiTaskinst customHiTaskinst) {
		return super.findPage(page, customHiTaskinst);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomHiTaskinst customHiTaskinst) {
		super.save(customHiTaskinst);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomHiTaskinst customHiTaskinst) {
		super.delete(customHiTaskinst);
	}

    /**
     * 更新意见
     * @param customHiTaskinst
     * @return
     */
    public Integer updateComment(CustomHiTaskinst customHiTaskinst){
        return dao.updateComment(customHiTaskinst);
    }
	
}