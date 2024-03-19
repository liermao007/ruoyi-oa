/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;

import java.util.List;

/**
 * 保存日程安排DAO接口
 * @author oa
 * @version 2016-11-15
 */
@MyBatisDao
public interface OaScheduleDao extends CrudDao<OaSchedule> {


    public List<OaSchedule> findOaSchedule(OaSchedule oaSchedule);




}