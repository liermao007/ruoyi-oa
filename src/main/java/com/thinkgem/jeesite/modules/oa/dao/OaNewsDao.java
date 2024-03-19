/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;

import java.util.List;

/**
 * 新闻公告DAO接口
 * @author oa
 * @version 2016-11-17
 */
@MyBatisDao
public interface OaNewsDao extends CrudDao<OaNews> {
    public List<OaNews> findCollectList(OaNews oaNews);
    public List<OaNews> findMyWriteNews(OaNews oaNews);

    public List<OaNews> findList(OaNews oaNews);

    /**
     * 岗位工作平台使用的接口数据
     * @param oaNews
     * @return
     */
    public List<OaNews> findListByWork(OaNews oaNews);

    public List<OaNews> findListAndRole(OaNews oaNews);

    /**
     * 更新所有置顶消息已经超过7天的置顶
     * @param oaNews
     * @return
     */
    public Integer updateTopic(OaNews oaNews);

}