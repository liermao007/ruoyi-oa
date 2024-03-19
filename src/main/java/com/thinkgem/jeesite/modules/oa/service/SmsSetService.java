package com.thinkgem.jeesite.modules.oa.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.dao.SmsSetDao;
import com.thinkgem.jeesite.modules.oa.entity.SmsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 短信设置Service
 * @author oa
 * @version 2017-12-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmsSetService extends CrudService<SmsSetDao, SmsSet> {
    @Autowired
    private SmsSetDao smsSetDao;
    public SmsSet getById(String id){ return smsSetDao.getById(id); }
    public SmsSet getByIdDel(String id){ return smsSetDao.getByIdDel(id); }
    public List superMechanism(){
        return smsSetDao.superMechanism();
    }
    public List mechanism(String id){
        return smsSetDao.mechanism(id);
    }
    public List findCompany(){
        return smsSetDao.findCompany();
    }
    @Transactional(readOnly = false)
    public int del(String id){
        return smsSetDao.del(id);
    }
    @Transactional(readOnly = false)
    public String findCompanyIsSend(String id){
        return smsSetDao.findCompanyIsSend(id);
    }
}