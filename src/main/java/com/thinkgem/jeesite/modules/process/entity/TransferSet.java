/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 自由流程节点Entity
 * @author oa
 * @version 2017-10-26
 */
public class TransferSet extends DataEntity<TransferSet> {
	
	private static final long serialVersionUID = 1L;
	private String handlePerson;		// 处理人
	private String handlePersonName;		// 处理人名称
	private String nodeType;		// 节点类型，1代表审批，0代表处理（抄送）
	private String nodeName;		// 节点名称
	private String nodeSort;		// 节点排序
	private String procInstId;		// 实例id
    private String handlePersonId; //处理人的id
    private String flag;          //是否审批过
    private String taskDefKey;   //审批标识
    private String handlePersonCard; //处理人的身份证号

	
	public TransferSet() {
		super();
	}

	public TransferSet(String id){
		super(id);
	}

	@Length(min=0, max=200, message="处理人长度必须介于 0 和 200 之间")
	public String getHandlePerson() {
		return handlePerson;
	}

	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}

    public String getHandlePersonName() {
        return handlePersonName;
    }

    public void setHandlePersonName(String handlePersonName) {
        this.handlePersonName = handlePersonName;
    }

    public String getHandlePersonId() {
        return handlePersonId;
    }

    public void setHandlePersonId(String handlePersonId) {
        this.handlePersonId = handlePersonId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Length(min=0, max=10, message="节点类型，1代表审批，0代表处理（抄送）长度必须介于 0 和 10 之间")
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	@Length(min=0, max=50, message="节点名称长度必须介于 0 和 50 之间")
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	@Length(min=0, max=100, message="节点排序长度必须介于 0 和 100 之间")
	public String getNodeSort() {
		return nodeSort;
	}

	public void setNodeSort(String nodeSort) {
		this.nodeSort = nodeSort;
	}
	
	@Length(min=0, max=64, message="实例id长度必须介于 0 和 64 之间")
	public String getProcInstId() {
		return procInstId;
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

    public String getHandlePersonCard() {
        return handlePersonCard;
    }

    public void setHandlePersonCard(String handlePersonCard) {
        this.handlePersonCard = handlePersonCard;
    }
}