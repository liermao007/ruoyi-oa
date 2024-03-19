/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 自由流程信息Entity
 * @author oa
 * @version 2017-10-26
 */
public class CustomProcess extends DataEntity<CustomProcess> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private String title;		// 标题
	private String orgId;		// 机构id
	private String deptId;		// 部门id
	private String name;		// 姓名
	private String procInstId;		// 实例id
    private String flag;      //标记是同意还是驳回或者是提交     1 提交  2同意   0   驳回
    private String taskDefKey;  //流程标识
    private String type;  //标识判断是 我发起的流程 或者已办流程   或者是 审批的流程。
    private String recall ; //标记该流程是否被撤回
    private String FJMC;  //附件名称
    private String FJLJ;  //附件路径

	
	public CustomProcess() {
		super();
	}

	public CustomProcess(String id){
		super(id);
	}

	@Length(min=0, max=1000, message="内容长度必须介于 0 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=100, message="标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="机构id长度必须介于 0 和 64 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=0, max=64, message="部门id长度必须介于 0 和 64 之间")
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Length(min=0, max=100, message="姓名长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="实例id长度必须介于 0 和 64 之间")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }

    public String getFJMC() {
        return FJMC;
    }

    public void setFJMC(String FJMC) {
        this.FJMC = FJMC;
    }

    public String getFJLJ() {
        return FJLJ;
    }

    public void setFJLJ(String FJLJ) {
        this.FJLJ = FJLJ;
    }
}