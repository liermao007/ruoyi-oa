/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 自由流程历史表Entity
 * @author oa
 * @version 2017-11-02
 */
public class CustomHiTaskinst extends DataEntity<CustomHiTaskinst> {
	
	private static final long serialVersionUID = 1L;
	private String procInstId;		// 实例id
	private String taskDefKey;		// 任务节点
	private Date startTime;		// 任务开始时间
	private Date endTime;		// 任务结束时间
	private String assignee;		// 执行人
	private String name;		// 任务节点名称
    private  String reason;  //完成状态
    private String comment;  //流转意见
	
	public CustomHiTaskinst() {
		super();
	}

	public CustomHiTaskinst(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=255, message="执行人长度必须介于 0 和 255 之间")
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}