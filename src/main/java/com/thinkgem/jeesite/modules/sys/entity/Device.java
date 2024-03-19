package com.thinkgem.jeesite.modules.sys.entity;

/**
 * 
 */
public class Device {
    public String type;//设备类型（移动、PC）
    public String system;//设备系统
    public String browser;//浏览器

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
