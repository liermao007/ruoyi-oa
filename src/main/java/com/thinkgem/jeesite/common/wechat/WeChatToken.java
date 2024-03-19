package com.thinkgem.jeesite.common.wechat;

import java.io.Serializable;

/**
 * 
 */
public class WeChatToken implements Serializable{
    public String token;
    public String ticket;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
