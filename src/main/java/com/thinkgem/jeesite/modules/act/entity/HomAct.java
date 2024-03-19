/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.act.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.act.utils.Variable;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.beans.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作流Entity
 * @author oa
 * @version 2013-11-03
 */
public class HomAct{

	private static final long serialVersionUID = 1L;

	private String taskId; 		// 任务编号
	private String taskName; 	// 任务名称
	private String taskDefKey; 	// 任务定义Key（任务环节标识）

	private String procInsId; 	// 流程实例ID
	private String procDefId; 	// 流程定义ID
	private String procDefKey; 	// 流程定义Key（流程定义标识）

	private String businessTable;	// 业务绑定Table
	private String businessId;		// 业务绑定ID

	private String title; 		// 任务标题
    private String procDefIdFather;     //标识
    private String no;
    private String status; 		// 任务状态（todo/claim/finish）

//	private String procExecUrl; 	// 流程执行（办理）RUL
	private String comment; 	// 任务意见
	private String flag; 		// 意见状态
    private String BZ;
    private String SFCC;      //出差申请表是否出差
    private String CS; //抄送人
    private String CSLoginNames;
    private String startTime;

    private String loginName;
    private String applyUserId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcDefIdFather() {
        return procDefIdFather;
    }

    public void setProcDefIdFather(String procDefIdFather) {
        this.procDefIdFather = procDefIdFather;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getSFCC() {
        return SFCC;
    }

    public void setSFCC(String SFCC) {
        this.SFCC = SFCC;
    }

    public String getCS() {
        return CS;
    }

    public void setCS(String CS) {
        this.CS = CS;
    }

    public String getCSLoginNames() {
        return CSLoginNames;
    }

    public void setCSLoginNames(String CSLoginNames) {
        this.CSLoginNames = CSLoginNames;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }
}


