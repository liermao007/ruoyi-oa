package com.thinkgem.jeesite.modules.test1.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.test1.dao.UpdatePrincipalInfoDao;
import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 */
@Service
@Transactional(readOnly = true)
public class UpdatePrincipalInfoService extends CrudService<UpdatePrincipalInfoDao, UpdatePrincipalInfo> {
    @Transactional(readOnly = false)
    public void save(UpdatePrincipalInfo updatePrincipalInfo) {
        super.save(updatePrincipalInfo);
    }
    public List<UpdatePrincipalInfo> findUpdate(String agentid){
        return dao.findUpdate(agentid);
    }
    public String[] find1(String agent){
        return dao.find1(agent);
    }
    public String[] find2(String principal){
        return dao.find2(principal);
    }
    public List<Act> findUpdate1(String agent){
        return dao.findUpdate1(agent);
    }
    public Act findComment(String procinstId){
        return dao.findComment(procinstId);
    }
    public List<UpdatePrincipalInfo> findByPrincipalid(String principalid){
        return dao.findByPrincipalid(principalid);
    }
}
