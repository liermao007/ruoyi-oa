/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.dao.CustomRuskTaskinstDao;
import com.thinkgem.jeesite.modules.process.entity.CustomRuskTaskinst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自由流程运行表Service
 * @author oa
 * @version 2017-11-02
 */
@Service
@Transactional(readOnly = true)
public class CustomRuskTaskinstService extends CrudService<CustomRuskTaskinstDao, CustomRuskTaskinst> {

	public CustomRuskTaskinst get(String id) {
		return super.get(id);
	}
	
	public List<CustomRuskTaskinst> findList(CustomRuskTaskinst customRuskTaskinst) {
		return super.findList(customRuskTaskinst);
	}
	
	public Page<CustomRuskTaskinst> findPage(Page<CustomRuskTaskinst> page, CustomRuskTaskinst customRuskTaskinst) {
		return super.findPage(page, customRuskTaskinst);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomRuskTaskinst customRuskTaskinst) {
		super.save(customRuskTaskinst);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomRuskTaskinst customRuskTaskinst) {
		super.delete(customRuskTaskinst);
	}

    /**
     * 根据实例id查询运行中的流程的信息
     * @param customRuskTaskinst
     * @return
     */
    public List<CustomRuskTaskinst> findByProInstId(CustomRuskTaskinst customRuskTaskinst){
        return  dao.findByProInstId(customRuskTaskinst);
    }

    /**
     * 根据实例id来删除运行中的流程的相关信息
     * @param procInstId
     * @return
     */
    public Integer deleteByProcInstId(String procInstId){
        return dao.deleteByProcInstId(procInstId);
    }
	
}