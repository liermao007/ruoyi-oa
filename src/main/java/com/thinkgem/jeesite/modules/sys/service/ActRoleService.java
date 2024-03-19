/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import com.thinkgem.jeesite.modules.form.dao.OaFormMasterDao;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.ActRole;
import com.thinkgem.jeesite.modules.sys.dao.ActRoleDao;

/**
 * 角色流程对应Service
 * @author oa
 * @version 2018-01-03
 */
@Service
@Transactional(readOnly = true)
public class ActRoleService extends CrudService<ActRoleDao, ActRole> {

    @Autowired
    private ActRoleDao actRoleDao;

	public ActRole get(String id) {
		return super.get(id);
	}
	
	/*public  List<OaFormMaster> findList(ActRole actRole) {
      String companyId=  UserUtils.getUser().getCompany().getId();
        List<Office> list=  officeDao.findCompanyById(companyId);
        String resultString = "";
        for(Office office : list){
            resultString += office.getId()+",";
        }
      String companyIds=  resultString.substring(0,resultString.length() - 1);
        List<OaFormMaster> oaFormMasterList=  oaFormMasterDao.findListByCompanyIds(companyIds);

		return oaFormMasterList;
	}*/
	
	public Page<ActRole> findPage (Page<ActRole> page, ActRole actRole) {
		return super.findPage(page, actRole);
	}

    public  ActRole findByRoleId(ActRole actRole) {
        return actRoleDao.findByRoleId(actRole);
    }
	
	@Transactional(readOnly = false)
	public void save(ActRole actRole) {
        actRoleDao.insert(actRole);
	}

    @Transactional(readOnly = false)
    public void update(ActRole actRole) {
        dao.update(actRole);
    }
	
	@Transactional(readOnly = false)
	public void delete(ActRole actRole) {
		super.delete(actRole);
	}
	
}