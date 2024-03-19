/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.ActRole;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.dao.OaFormMasterDao;

/**
 * 编辑器设计表单Service
 * @author oa
 * @version 2016-11-18
 */
@Service
@Transactional(readOnly = true)
public class OaFormMasterService extends CrudService<OaFormMasterDao, OaFormMaster> {

    @Autowired
    private OaFormMasterDao oaFormMasterDao;

    @Autowired
    private OfficeDao officeDao;
    public OaFormMaster get(String id) {
        return super.get(id);
    }

    public List<OaFormMaster> findList(OaFormMaster oaFormMaster) {
        return super.findList(oaFormMaster);
    }

    public Page<OaFormMaster> findPage(Page<OaFormMaster> page, OaFormMaster oaFormMaster) {
        if(oaFormMaster.getOffice().getId().equals("1")){
            oaFormMaster.getOffice().setId("");
        }
        return super.findPage(page, oaFormMaster);
    }

    @Transactional(readOnly = false)
    public void save(OaFormMaster oaFormMaster) {
        super.save(oaFormMaster);
    }

    @Transactional(readOnly = false)
    public void delete(OaFormMaster oaFormMaster) {
        super.delete(oaFormMaster);
    }

    public OaFormMaster findForm(String title){
        return   oaFormMasterDao.findForm(title);
    }

    public OaFormMaster findFormContentByTableName(String tableName, String officeId) {
        return oaFormMasterDao.findFormContentByTableName(tableName,officeId);
    }

    public OaFormMaster findByNo(String formNo,String officeId){
        return oaFormMasterDao.findByNo(formNo, officeId);
    }

    public OaFormMaster findByNo1(String tableName,String officeId){
        return oaFormMasterDao.findByNo1(tableName, officeId);
    }

    public  List<OaFormMaster> findList() {
        String companyId=  UserUtils.getUser().getCompany().getId();
        List<Office> list=  officeDao.findCompanyById(companyId);
        String resultString = "";
        for(Office office : list){
            resultString += office.getId()+",";
        }
        String companyIds="";
        if(resultString.length()>0){
             companyIds=  resultString.substring(0,resultString.length() - 1);
        }else {
            companyIds="";
        }

        List<OaFormMaster> oaFormMasterList=  oaFormMasterDao.findListByCompanyIds(companyIds);

        return oaFormMasterList;
    }
}