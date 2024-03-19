/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.act.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.Act;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 审批DAO接口
 * @author oa
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {


	public int updateProcInsIdByBusinessId(Act act);
    //最近任务
    public List<Act> oaHasToDoTask(Act act);
    public List<Act> doneList(@Param("CS") String  CS);

    /**
     * 根据姓名，登录名模糊查询
     * @param
     * @return
     */
    public List<Act> findListByName(Act user);

    public String  findTaskName(@Param("sql")String sql);

    public List<Act> getByProcInsId(@Param("procInsId")String procInsId);

    public String  findGd(@Param("gd")String gd,@Param("procInsId")String procInsId);
	
}
