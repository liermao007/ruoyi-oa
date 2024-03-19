package com.thinkgem.jeesite.modules.test1.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 */
@MyBatisDao
public interface UpdatePrincipalInfoDao  extends CrudDao<UpdatePrincipalInfo> {
    public List<UpdatePrincipalInfo> findUpdate(@Param("agentid")String agentid);
    public String[] find1(@Param("agent")String agent);
    public String[] find2(@Param("principal")String principal);
    public List<Act> findUpdate1(@Param("agent")String agent);
    public Act findComment(@Param("procinstId")String procinstId);
    public List<UpdatePrincipalInfo> findByPrincipalid(@Param("principalid")String principalid);
}
