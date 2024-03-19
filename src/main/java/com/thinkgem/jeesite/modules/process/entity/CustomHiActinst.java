/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 自由流程历史节点表Entity
 * @author oa
 * @version 2017-11-05
 */
public class CustomHiActinst extends DataEntity<CustomHiActinst> {
	
	private static final long serialVersionUID = 1L;
	private String procInstId;		// 实例Id
	private String taskDefKey;		// 任务流程标识
	private String name;		// 任务节点名称
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String assignee;		// 任务执行人
    private String comment; //意见
    private String nodeSort; //排序
	
	public CustomHiActinst() {
		super();
	}

	public CustomHiActinst(String id){
		super(id);
	}

	@Length(min=0, max=64, message="实例Id长度必须介于 0 和 64 之间")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	@Length(min=0, max=100, message="任务流程标识长度必须介于 0 和 100 之间")
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	@Length(min=0, max=100, message="任务节点名称长度必须介于 0 和 100 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=100, message="任务执行人长度必须介于 0 和 100 之间")
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNodeSort() {
        return nodeSort;
    }

    public void setNodeSort(String nodeSort) {
        this.nodeSort = nodeSort;
    }
}