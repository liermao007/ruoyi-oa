/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.process.entity.CustomHiTaskinst;

import java.util.List;

/**
 * 自由流程历史表DAO接口
 * @author oa
 * @version 2017-11-02
 */
@MyBatisDao
public interface CustomHiTaskinstDao extends CrudDao<CustomHiTaskinst> {
    /**
     * 更新意见
     * @param customHiTaskinst
     * @return
     */
    public Integer updateComment(CustomHiTaskinst customHiTaskinst);

    /**
     * 根据实例id，查询意见并显示在表单中
     * @param customHiTaskinst
     * @return
     */
    public List<CustomHiTaskinst> findListByTable(CustomHiTaskinst customHiTaskinst);


    /**
     * 查询所有
     * @param customHiTaskinst
     * @return
     */
    public List<CustomHiTaskinst> findAllList(CustomHiTaskinst customHiTaskinst);
}