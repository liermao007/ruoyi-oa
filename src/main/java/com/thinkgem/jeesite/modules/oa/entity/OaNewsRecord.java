package com.thinkgem.jeesite.modules.oa.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 
 */
public class OaNewsRecord extends DataEntity<OaNewsRecord> {

    private static final long serialVersionUID = 1L;
    private OaNews oaNews;		// 通知通告ID
    private User user;		// 接受人
    private String readFlag;		// 阅读标记（0：未读；1：已读）
    private Date readDate;		// 阅读时间


    public OaNewsRecord() {
        super();
    }

    public OaNewsRecord(String id){
        super(id);
    }

    public OaNewsRecord(OaNews oaNews){
        this.oaNews = oaNews;
    }


    public OaNews getOaNews() {
        return oaNews;
    }

    public void setOaNews(OaNews oaNews) {
        this.oaNews = oaNews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Length(min=0, max=1, message="阅读标记（0：未读；1：已读）长度必须介于 0 和 1 之间")
    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
}
