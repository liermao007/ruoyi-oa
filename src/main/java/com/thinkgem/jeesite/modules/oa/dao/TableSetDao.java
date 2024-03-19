/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.TableSet;

import java.util.List;

/**
 * 短信设置接口
 * @author oa
 * @version 2017-12-13
 */
@MyBatisDao
public interface TableSetDao extends CrudDao<TableSet> {

    /**
     * 查询所有自定义数据表
     * @return
     */
    List<TableSet> superFindAll();
    /**
     * 查询本机构自定义数据表
     * @param id
     * @return
     */
    List<TableSet> findAll(String id);

    /**
     * 查询本机构自定义数据表
     * @param id
     * @return
     */
    List<TableSet> findLack(String id);
    /**
     * 查询表名是否存在
     * @param id
     * @param cid
     * @return
     */
     String getById(String id, String cid);
    /**
     * 保存
     * @param tableSet
     * @return
     */
    @Override
    int insert(TableSet tableSet);

    /**
     * 删除
     * @param id
     */
    void del(String id);
    /**
     * 查询表状态
     * @param tableName
     * @return
     */
    int findTableIsSend(String tableName);
}