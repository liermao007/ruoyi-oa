package com.thinkgem.jeesite.common.wechat;

import java.util.Map;

/**
 * 
 */
public class WeChat {
    public String appId;
    public String timestamp;
    public String nonceStr;
    public String signature;
    public String ticket;
    public String error;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public static WeChat success(Map<String, String> configMap) {
        WeChat weChat=new WeChat();
        if(configMap!=null){
            weChat.setAppId(configMap.get("appId"));
            weChat.setTimestamp(configMap.get("timestamp"));
            weChat.setNonceStr(configMap.get("nonceStr"));
            weChat.setSignature(configMap.get("signature"));
            weChat.setTicket(configMap.get("jsapi_ticket"));
            weChat.setError("no");
            return weChat;
        }
        weChat.setError("token is null");
        return null;
    }

    public static WeChat error() {
        WeChat weChat=new WeChat();
        weChat.setError("token is null");
        return weChat;
    }

}
