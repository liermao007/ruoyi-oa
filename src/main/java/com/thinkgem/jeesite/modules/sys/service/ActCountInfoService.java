/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.dao.ActRoleDao;
import com.thinkgem.jeesite.modules.sys.entity.ActRole;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.ActCountInfo;
import com.thinkgem.jeesite.modules.sys.dao.ActCountInfoDao;

/**
 * 流程信息汇Service
 * @author oa
 * @version 2017-09-28
 */
@Service
@Transactional(readOnly = true)
public class ActCountInfoService extends CrudService<ActCountInfoDao, ActCountInfo> {
    @Autowired
    private ActCountInfoDao actCountInfoDao;
    @Autowired
    private ActRoleDao actRoleDao;

	public ActCountInfo get(String id) {
		return super.get(id);
	}
	
	public List<ActCountInfo> findList(ActCountInfo actCountInfo) {
		return super.findList(actCountInfo);
	}
	
	public Page<ActCountInfo> findPage(Page<ActCountInfo> page, ActCountInfo actCountInfo) {
        ActRole actRole=new ActRole();
        List<Role> roleList=  UserUtils.getRole();
        if(roleList.size()>0&&roleList!=null){
            String roleId=  roleList.get(0).getRoleId();
            actRole.setRoleId(roleId);
        }
        String actIds="";
        List<ActRole>  actRoleList=  actRoleDao.findList(actRole);
        for(ActRole  actRole1 :actRoleList){
            actIds+=  "'" +actRole1.getActId()+"'"+",";
        }
        if(actIds.length()>0){
        actCountInfo.setProcDefId(actIds.substring(0,actIds.length()-1));
        }
        if(actRole.getRoleId().equals("1940ad9fff3549db8fd1bd8fb831c96b")){
            actCountInfo.setCompanyId("");
        }
		return super.findPage(page, actCountInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ActCountInfo actCountInfo) {
		super.save(actCountInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ActCountInfo actCountInfo) {
		super.delete(actCountInfo);
	}


    public List<ActCountInfo>  findInfonByProcInsId(String  procInsId) {
        return actCountInfoDao.findInfonByProcInsId(procInsId);
    }

    public List<ActCountInfo>  findInfonByProcInsIds(String  procInsId) {
        return actCountInfoDao.findInfonByProcInsIds(procInsId);
    }
}