package com.thinkgem.jeesite.modules.sys.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**    private String smsSendResult;//短信发送的结果

 * 
 */
public class SmsInfo extends DataEntity<SmsInfo> {
    private String code;//6位数验证码
    private String phone;//接收者的手机号码
    private String smsSendResult;//短信发送的结果
    private int count;//标识发送了几次

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSmsSendResult() {
        return smsSendResult;
    }

    public void setSmsSendResult(String smsSendResult) {
        this.smsSendResult = smsSendResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
