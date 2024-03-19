/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test1.service;

import java.util.List;

import com.thinkgem.jeesite.modules.oa.dao.MailInfoDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.test1.entity.OaRunProcess;
import com.thinkgem.jeesite.modules.test1.dao.OaRunProcessDao;

import javax.servlet.http.HttpServletRequest;

/**
 * 已办流程Service
 * @author oa
 * @version 2017-12-18
 */
@Service
@Transactional(readOnly = true)
public class OaRunProcessService extends CrudService<OaRunProcessDao, OaRunProcess> {
    @Autowired
    private MailInfoDao mailInfoDao;
    @Autowired
    private UpdatePrincipalInfoService updatePrincipalInfoService;

    public OaRunProcess get(String id) {
        return super.get(id);
    }

    public List<OaRunProcess> findList(OaRunProcess oaRunProcess) {
        return super.findList(oaRunProcess);
    }

    public Page<OaRunProcess> findPage(Page<OaRunProcess> page, OaRunProcess oaRunProcess) {
        return super.findPage(page, oaRunProcess);
    }

    @Transactional(readOnly = false)
    public void save(OaRunProcess oaRunProcess) {
        super.save(oaRunProcess);
    }

    @Transactional(readOnly = false)
    public void delete(OaRunProcess oaRunProcess) {
        super.delete(oaRunProcess);
    }

    @Transactional(readOnly = false)
    public OaRunProcess selectCheck(OaRunProcess oaRunProcess) {
        return dao.selectCheck(oaRunProcess);
    }

    @Transactional(readOnly = false)
    public boolean update0(UpdatePrincipalInfo updatePrincipalInfo,String loginName, String instId,HttpServletRequest request){
        User agent = (User)request.getSession().getAttribute("agent");
        if(agent != null){
            updatePrincipalInfo.setPrincipalId(agent.getId());
            updatePrincipalInfo.setPrincipal(agent.getLoginName());
            updatePrincipalInfo.setProcinstId(instId);
            updatePrincipalInfo.setAgentId(UserUtils.getUser().getId());
            updatePrincipalInfo.setAgent(UserUtils.getUser().getLoginName());
            dao.update1(loginName,instId,agent.getLoginName());
            dao.update2(loginName,instId,agent.getLoginName());
            dao.update3(loginName,instId,agent.getLoginName());
            updatePrincipalInfoService.save(updatePrincipalInfo);
            return true;
        }else{
            return false;
        }
    }

    public String findInstId(String taskId){
        return dao.findInstId(taskId);
    }

    /**
     * 根据被代理人   查询被代理人   回显数据
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public OaRunProcess findById(String id) {
        OaRunProcess oaRunProcess = new OaRunProcess();
        StringBuilder sb = new StringBuilder();
        StringBuilder cc = new StringBuilder();
        User user1;
        List<OaRunProcess> list = dao.findById(id,"0");
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                User user = mailInfoDao.getById(list.get(i).getAgentid());
                sb.append(user.getName() + ",");
                cc.append(list.get(i).getAgentid() + ",");
            }
            user1 = mailInfoDao.getById(list.get(0).getPrincipalid());
            String receiverName = sb.toString().substring(0, sb.toString().length() - 1);
            String ccId = cc.toString().substring(0, cc.toString().length() - 1);
            oaRunProcess.setPrincipal(receiverName);
            oaRunProcess.setAgentid(ccId);
            oaRunProcess.setAgent(user1.getName());
        }  else{
            user1 = mailInfoDao.getById(id);
            oaRunProcess.setAgent(user1.getName());
        }
        return oaRunProcess;
    }

}