/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNewsRecord;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecord;

import java.util.List;

/**
 * 通知通告记录DAO接口
 * @author oa
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNewsRecordDao extends CrudDao<OaNewsRecord> {

	/**
	 * 插入通知记录
	 * @param oaNewsRecordList
	 * @return
	 */
	public int insertAll(List<OaNewsRecord> oaNewsRecordList);

    /**
     * 根据新闻ID删除通知记录
     * @param oaNewsId 通知ID
     * @return
     */
    public int deleteByOaNewsId(String oaNewsId);



}