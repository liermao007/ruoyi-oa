/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 自由流程意见Entity
 * @author oa
 * @version 2017-10-26
 */
public class CustomComment extends DataEntity<CustomComment> {
	
	private static final long serialVersionUID = 1L;
	private String comment;		// 意见
	private String procInstId;		// 实例id
    private String flag; //标识
	private String userId;   //用户id
    private String taskDefKey; //流程标识
    private String tbodyHtml; //保存base64图片
	public CustomComment() {
		super();
	}

	public CustomComment(String id){
		super(id);
	}

	@Length(min=0, max=500, message="意见长度必须介于 0 和 500 之间")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Length(min=0, max=64, message="实例id长度必须介于 0 和 64 之间")
	public String getProcInstId() {
		return procInstId;
	}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getTbodyHtml() {
        return tbodyHtml;
    }

    public void setTbodyHtml(String tbodyHtml) {
        this.tbodyHtml = tbodyHtml;
    }
}