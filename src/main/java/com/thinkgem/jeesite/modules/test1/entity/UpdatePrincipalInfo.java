package com.thinkgem.jeesite.modules.test1.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 */
public class UpdatePrincipalInfo extends DataEntity<UpdatePrincipalInfo> {
    private String agentId;
    private String agent;
    private String principalId;
    private String principal;
    private String procinstId;
    private String bm;

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getProcinstId() {
        return procinstId;
    }

    public void setProcinstId(String procinstId) {
        this.procinstId = procinstId;
    }
}
