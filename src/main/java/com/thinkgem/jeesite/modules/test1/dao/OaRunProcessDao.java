/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test1.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.test1.entity.OaRunProcess;
import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 已办流程DAO接口
 * @author oa
 * @version 2017-12-18
 */
@MyBatisDao
public interface OaRunProcessDao extends CrudDao<OaRunProcess> {
    /**
     * 回显代理人
     * @param
     * @return
     */
    public List<OaRunProcess> findById(@Param("agentid")String agentid,@Param("byFlag")String byFlag);
    public void update1(@Param("loginName")String loginName,@Param("instId")String instId,@Param("principal")String principal);//修改act_ru_task中的ASSIGNEE_为被代理人的登录名
    public void update2(@Param("loginName")String loginName,@Param("instId")String instId,@Param("principal")String principal);//修改act_hi_actinst中的ASSIGNEE_为被代理人的登录名
    public void update3(@Param("loginName")String loginName,@Param("instId")String instId,@Param("principal")String principal);//修改act_hi_taskinst中的ASSIGNEE_为被代理人的登录名
    public String findInstId(@Param("taskId")String taskId);//在act_ru_task中有id查询出表单实例id
    public String selectId(@Param("instId")String instId);//查询被代理人的Id
    public OaRunProcess selectCheck(OaRunProcess oaRunProcess);
}