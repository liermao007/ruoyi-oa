/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.SmsSet;

import java.util.List;

/**
 * 短信接口
 * @author oa
 * @date 2018/01/24
 */
@MyBatisDao
public interface SmsSetDao extends CrudDao<SmsSet> {
    /**
     * 查询用户信息
     * @param id
     * @return
     */
    SmsSet getById(String id);
    /**
     * 用于维护
     * @param id
     * @return
     */
    SmsSet getByIdDel(String id);

    /**
     * 查询本机构
     * @param id
     * @return
     */
    List mechanism(String id);
    /**
     * 查询所有机构
     * @return
     */
    List superMechanism();
    /**
     * 查询机构名
     * @return
     */
    List findCompany();
    /**
     * 删除
     * @param id
     * @return
     */
    int del(String id);
    /**
     * 查询机构状态
     * @param id
     * @return
     */
    String findCompanyIsSend(String id);
}