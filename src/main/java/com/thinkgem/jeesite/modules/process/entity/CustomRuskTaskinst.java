/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 自由流程运行表Entity
 * @author oa
 * @version 2017-11-02
 */
public class CustomRuskTaskinst extends DataEntity<CustomRuskTaskinst> {
	
	private static final long serialVersionUID = 1L;
	private String procInstId;		// 实例id
	private String taskDefKey;		// 任务节点
	private String assignee;		// 任务节点执行人
	private String name;		// 任务节点名称
	private Date startTime;		// 开始时间
    private String apply;     //申请人信息
    private String flagAudit;  //是否已经会签完成
	
	public CustomRuskTaskinst() {
		super();
	}

	public CustomRuskTaskinst(String id){
		super(id);
	}

	@Length(min=0, max=64, message="实例id长度必须介于 0 和 64 之间")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	@Length(min=0, max=255, message="任务节点长度必须介于 0 和 255 之间")
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	@Length(min=0, max=255, message="任务节点执行人长度必须介于 0 和 255 之间")
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	@Length(min=0, max=255, message="任务节点名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getFlagAudit() {
        return flagAudit;
    }

    public void setFlagAudit(String flagAudit) {
        this.flagAudit = flagAudit;
    }
}