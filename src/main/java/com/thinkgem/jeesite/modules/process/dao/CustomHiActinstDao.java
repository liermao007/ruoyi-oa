/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.process.entity.CustomHiActinst;

import java.util.List;

/**
 * 自由流程历史节点表DAO接口
 * @author oa
 * @version 2017-11-05
 */
@MyBatisDao
public interface CustomHiActinstDao extends CrudDao<CustomHiActinst> {

    /**
     * 根据实例id，任务节点taskDefKey,任务执行人来查询当前流程信息
     * @param customHiActinst
     * @return
     */
    public List<CustomHiActinst> findCustomHiActinst(CustomHiActinst customHiActinst);



    /**
     * 在申请人重新提交申请时，查询任务结束时间为空并且节点信息为start的节点的信息
     * @param customHiActinst
     * @return
     */
    public List<CustomHiActinst> findListByEndTime(CustomHiActinst customHiActinst);

    /**
     * 根据条件查询
     * @param customHiActinst
     * @return
     */
    public List<CustomHiActinst> findAllList(CustomHiActinst customHiActinst);


}