/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.dao.TransferSetDao;
import com.thinkgem.jeesite.modules.process.entity.TransferSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自由流程节点Service
 * @author oa
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class TransferSetService extends CrudService<TransferSetDao, TransferSet> {

	public TransferSet get(String id) {
		return super.get(id);
	}
	
	public List<TransferSet> findList(TransferSet transferSet) {
		return super.findList(transferSet);
	}

    public List<TransferSet> findAllList(TransferSet transferSet) {
        return dao.findAllList(transferSet);
    }
	
	public Page<TransferSet> findPage(Page<TransferSet> page, TransferSet transferSet) {
		return super.findPage(page, transferSet);
	}
	
	@Transactional(readOnly = false)
	public void save(TransferSet transferSet) {
		super.save(transferSet);
	}
	
	@Transactional(readOnly = false)
	public void delete(TransferSet transferSet) {
		super.delete(transferSet);
	}

    /**
     * 更新流转设定的流程信息的状态 ，标识是否是已经完成的状态
     * @param transferSet
     */
    @Transactional(readOnly = false)
    public void updateFlag(TransferSet transferSet) {
       dao.updateFlag(transferSet);
    }

    /**
     * 根据实例procInstId查询,有customPressId的时候需要将所有的流转信息全部查询
     * @param transferSet
     * @return
     */
    public List<TransferSet> findListByProcInstId(TransferSet transferSet){
        return dao.findListByProcInstId(transferSet);
    }


}