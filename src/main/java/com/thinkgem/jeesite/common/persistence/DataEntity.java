/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.persistence;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 数据Entity类
 * @author oa
 * @version 2014-05-16
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	
	protected String remarks;	// 备注
	protected User createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected User updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）



	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}
		this.updateDate = new Date();
	}
	
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@JsonIgnore
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	@Length(min=1, max=3)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}



    protected String createByAmq;	// 仅同步数据时使用的创建者
    protected String createDateAmq;	// 仅同步数据时使用的创建日期
    protected String updateByAmq;	// 仅同步数据时使用的更新者
    protected String updateDateAmq;	// 仅同步数据时使用的更新日期
    protected String parentIdAmq;	// 仅同步数据时使用的父节点
    protected String areaIdAmq;	// 仅同步数据时使用的区域id

    public String getCreateByAmq() {
        return createByAmq;
    }

    public void setCreateByAmq(String createByAmq) {
        this.createByAmq = createByAmq;
    }

    public String getCreateDateAmq() {
        return createDateAmq;
    }

    public void setCreateDateAmq(String createDateAmq) {
        this.createDateAmq = createDateAmq;
    }

    public String getUpdateByAmq() {
        return updateByAmq;
    }

    public void setUpdateByAmq(String updateByAmq) {
        this.updateByAmq = updateByAmq;
    }

    public String getUpdateDateAmq() {
        return updateDateAmq;
    }

    public void setUpdateDateAmq(String updateDateAmq) {
        this.updateDateAmq = updateDateAmq;
    }

    public String getParentIdAmq() {
        return parentIdAmq;
    }

    public void setParentIdAmq(String parentIdAmq) {
        this.parentIdAmq = parentIdAmq;
    }

    public String getAreaIdAmq() {
        return areaIdAmq;
    }

    public void setAreaIdAmq(String areaIdAmq) {
        this.areaIdAmq = areaIdAmq;
    }
}
