/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * 新闻公告Entity
 * @author oa
 * @version 2016-11-17
 */
public class OaNews extends DataEntity<OaNews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String files;		// 附件
	private String  auditFlag;		// 审核状态（0待审核，1 已审核发布，2拒绝发布）
	private String auditMan;		// 审核人ID
	private String isTopic;		// 是否置顶（0不置顶，1置顶）
    private String companyId;
    private String auditManName;  //审核人姓名
    private String createManName;  //创建人姓名
    private String userId; //用户id
    private String filesName ;  //附件名称
    private String fjlj ;  //附件路径

    private String photo;//头像
    private boolean isSelf;		// 是否只查询自己的通知

    private String readFlag;	// 本人阅读状态
    private int count;       //手机端上拉加载时候总条数
    //扩展
    private String fileName;//手机头像
    @Transient
    public String getPhoto() {
        return photo;
    }
    @Transient
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Transient
    public String getFileName() {
        return fileName;
    }
    @Transient
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private List<OaNewsRecord> oaNewsRecordList = Lists.newArrayList();

	public OaNews() {
		super();
	}

	public OaNews(String id){
		super(id);
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    @Transactional
    public int getCount() {
        return count;
    }
    @Transactional
    public void setCount(int count) {
        this.count = count;
    }

    @Length(min=1, max=200, message="标题长度必须介于 1 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=2000, message="附件长度必须介于 0 和 2000 之间")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	@Length(min=0, max=1, message="审核状态（0 未审核，1 已审核）长度必须介于 0 和 1 之间")
	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	

	public String getAuditMan() {
		return auditMan;
	}

	public void setAuditMan(String auditMan) {
		this.auditMan = auditMan;
	}
	
	@Length(min=0, max=1, message="是否置顶（0不置顶，1置顶）长度必须介于 0 和 1 之间")
	public String getIsTopic() {
		return isTopic;
	}

	public void setIsTopic(String isTopic) {
		this.isTopic = isTopic;
	}

    public String getAuditManName() {
        return auditManName;
    }

    public void setAuditManName(String auditManName) {
        this.auditManName = auditManName;
    }

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
    }


    public List<OaNewsRecord> getOaNewsRecordList() {
        return oaNewsRecordList;
    }

    public void setOaNewsRecordList(List<OaNewsRecord> oaNewsRecordList) {
        this.oaNewsRecordList = oaNewsRecordList;
    }

    public String getFilesName() {
        return filesName;
    }

    public void setFilesName(String filesName) {
        this.filesName = filesName;
    }

    public String getFjlj() {
        return fjlj;
    }

    public void setFjlj(String fjlj) {
        this.fjlj = fjlj;
    }

    /**
     * 获取通知发送记录用户ID
     * @return
     */
    public String getOaNewsRecordIds() {
        return Collections3.extractToString(oaNewsRecordList, "user.id", ",") ;
    }

    /**
     * 设置通知发送记录用户ID
     * @return
     */
    public void setOaNewsRecordIds(String oaNewsRecord) {
        this.oaNewsRecordList = Lists.newArrayList();
        for (String id : StringUtils.split(oaNewsRecord, ",")){
            OaNewsRecord entity = new OaNewsRecord();
            entity.setId(IdGen.uuid());
            entity.setOaNews(this);
            entity.setUser(new User(id));
            entity.setReadFlag("0");
            this.oaNewsRecordList.add(entity);
        }
    }

    /**
     * 获取通知发送记录用户Name
     * @return
     */
    public String getOaNewsRecordNames() {
        return Collections3.extractToString(oaNewsRecordList, "user.name", ",") ;
    }

    /**
     * 设置通知发送记录用户Name
     * @return
     */
    public void setOaNewsRecordNames(String oaNewsRecord) {

    }


    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }
}