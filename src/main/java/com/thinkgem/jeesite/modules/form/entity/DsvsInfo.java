package com.thinkgem.jeesite.modules.form.entity;

import com.thinkgem.jeesite.common.utils.SecurityEngineUtils;

/**
 * @author oa
 */
public class DsvsInfo {

    private String serverCertificate;
    private String signData;
    private String data;
    private String base64Data;
    private String sig;
    private String sigPic;
    private String sigServer;
    private String ts;
    private String certInfo;

    public DsvsInfo() {
    }

    public DsvsInfo(String data) {
        this.data = data;
        this.base64Data = SecurityEngineUtils.base64Encode(data);
//        this.serverCertificate = SecurityEngineUtils.getServerCertificate();
//        this.signData = SecurityEngineUtils.getSignData(data.getBytes());
    }

    public String getServerCertificate() {
        return serverCertificate;
    }

    public void setServerCertificate(String serverCertificate) {
        this.serverCertificate = serverCertificate;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getSigPic() {
        return sigPic;
    }

    public void setSigPic(String sigPic) {
        this.sigPic = sigPic;
    }

    public String getSigServer() {
        return sigServer;
    }

    public void setSigServer(String sigServer) {
        this.sigServer = sigServer;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getCertInfo() {
        return certInfo;
    }

    public void setCertInfo(String certInfo) {
        this.certInfo = certInfo;
    }
}
