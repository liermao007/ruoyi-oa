/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.dao.CustomHiActinstDao;
import com.thinkgem.jeesite.modules.process.entity.CustomHiActinst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自由流程历史节点表Service
 * @author oa
 * @version 2017-11-05
 */
@Service
@Transactional(readOnly = true)
public class CustomHiActinstService extends CrudService<CustomHiActinstDao, CustomHiActinst> {

	public CustomHiActinst get(String id) {
		return super.get(id);
	}
	
	public List<CustomHiActinst> findList(CustomHiActinst customHiActinst) {
		return super.findList(customHiActinst);
	}
	
	public Page<CustomHiActinst> findPage(Page<CustomHiActinst> page, CustomHiActinst customHiActinst) {
		return super.findPage(page, customHiActinst);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomHiActinst customHiActinst) {
		super.save(customHiActinst);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomHiActinst customHiActinst) {
		super.delete(customHiActinst);
	}

    /**
     * 根据实例id，任务节点taskDefKey,任务执行人来查询当前流程信息
     * @param customHiActinst
     * @return
     */
    public List<CustomHiActinst> findCustomHiActinst(CustomHiActinst customHiActinst){
        return dao.findCustomHiActinst(customHiActinst);
    }

    /**
     * 在申请人重新提交申请时，查询任务结束时间为空并且节点信息为start的节点的信息
     * @param customHiActinst
     * @return
     */
    public List<CustomHiActinst> findListByEndTime(CustomHiActinst customHiActinst){
        return  dao.findListByEndTime(customHiActinst);
    }

    /**
     * 根据条件查询
     * @param customHiActinst
     * @return
     */
    public List<CustomHiActinst> findAllList(CustomHiActinst customHiActinst){
      return   dao.findAllList(customHiActinst);
    }

}