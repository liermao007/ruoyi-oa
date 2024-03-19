package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

/**
 * @author oa
 */
public class AllPhoneResp {

    private String index;

    private List<UserPhone> phones;

    public AllPhoneResp() {
    }

    public AllPhoneResp(String index, List<UserPhone> phones) {
        this.index = index;
        this.phones = phones;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<UserPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<UserPhone> phones) {
        this.phones = phones;
    }
}
