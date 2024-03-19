/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.process.entity.TransferSet;

import java.util.List;

/**
 * 自由流程节点DAO接口
 * @author oa
 * @version 2017-10-26
 */
@MyBatisDao
public interface TransferSetDao extends CrudDao<TransferSet> {

    /**
     * 更改Flag状态用于标识，该审核节点是否已经完成审批
     * @param transferSet
     * @return
     */
    public Integer updateFlag(TransferSet transferSet);

    /**
     * 根据实例procInstId查询,有customPressId的时候需要将所有的流转信息全部查询
     * @param transferSet
     * @return
     */
    public List<TransferSet> findListByProcInstId(TransferSet transferSet);
}