/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 请假单统计Entity
 * @author oa
 * @version 2017-09-04
 */
public class LeaveApplication extends DataEntity<LeaveApplication> {
	
	private static final long serialVersionUID = 1L;
	private String bh;		// 编号
	private Date tbrq;		// 填表日期
	private String xm;		// 申请人
	private String ks;		// 部门
	private String zwwtr;		// 职务委托人
	private String qjlx;		// 请假类型
	private Date qjksrq;		// 请假开始日期
	private Date qjjsrq;		// 请假结束日期
	private String qjsy;		// 请假事由
	private String qjts;		// 请假天数
	private String sjkssj;		// 实际开始时间
	private String xjsj;		// 销假时间
	private String sjxjts;		// 实际休假天数
	private String synjts;		// 使用年假天数
	private String procInsId;		// proc_ins_id
	private String procDefId;		// proc_def_id
	private String xzyj;		// 小组意见
	private String bmyj;		// 部门意见
	private String zjl;		// 总经理
	private String rlzybm;		// 人力资源部门
	private String bzsj;		// 备注数据
	private String fjmc;		// 附件名称
	private String fjlj;		// 附件路径
    private int count;		// 附件路径
	public LeaveApplication() {
		super();
	}

	public LeaveApplication(String id){
		super(id);
	}

    @JsonIgnore

	@Length(min=0, max=100, message="编号长度必须介于 0 和 100 之间")
	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}



	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTbrq() {
		return tbrq;
	}

	public void setTbrq(Date tbrq) {
		this.tbrq = tbrq;
	}

    @ExcelField(title = "姓名", align = 2, sort = 7)
    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    @ExcelField(title = "部门", align = 2, sort = 6)
    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks;
    }

    @JsonIgnore


	@Length(min=0, max=100, message="职务委托人长度必须介于 0 和 100 之间")
	public String getZwwtr() {
		return zwwtr;
	}

	public void setZwwtr(String zwwtr) {
		this.zwwtr = zwwtr;
	}

    @NotNull(message = "请假开始日期不能为空")
    @ExcelField(title = "请假开始日期", align = 2, sort = 10)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getQjksrq() {
        return qjksrq;
    }

    public void setQjksrq(Date qjksrq) {
        this.qjksrq = qjksrq;
    }

    @NotNull(message = "请假结束日期不能为空")
    @ExcelField(title = "请假结束日期", align = 2, sort = 11)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getQjjsrq() {
        return qjjsrq;
    }

    public void setQjjsrq(Date qjjsrq) {
        this.qjjsrq = qjjsrq;
    }

    @NotNull(message = "请假类型不能为空")
    @ExcelField(title = "请假类型", align = 2, sort = 8)
    public String getQjlx() {
        return qjlx;
    }

    public void setQjlx(String qjlx) {
        this.qjlx = qjlx;
    }


    @JsonIgnore

	@Length(min=0, max=200, message="请假事由长度必须介于 0 和 200 之间")
	public String getQjsy() {
		return qjsy;
	}

	public void setQjsy(String qjsy) {
		this.qjsy = qjsy;
	}


    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NotNull(message = "请假天数不能为空")
    @ExcelField(title = "请假天数", align = 2, sort = 9)
	public String getQjts() {
		return qjts;
	}

	public void setQjts(String qjts) {
		this.qjts = qjts;
	}
	
	@Length(min=0, max=100, message="实际开始时间长度必须介于 0 和 100 之间")
	public String getSjkssj() {
		return sjkssj;
	}

	public void setSjkssj(String sjkssj) {
		this.sjkssj = sjkssj;
	}
	
	@Length(min=0, max=100, message="销假时间长度必须介于 0 和 100 之间")
	public String getXjsj() {
		return xjsj;
	}

	public void setXjsj(String xjsj) {
		this.xjsj = xjsj;
	}
	
	public String getSjxjts() {
		return sjxjts;
	}

	public void setSjxjts(String sjxjts) {
		this.sjxjts = sjxjts;
	}
	
	public String getSynjts() {
		return synjts;
	}

	public void setSynjts(String synjts) {
		this.synjts = synjts;
	}
	
	@Length(min=0, max=64, message="proc_ins_id长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=150, message="proc_def_id长度必须介于 1 和 150 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getXzyj() {
		return xzyj;
	}

	public void setXzyj(String xzyj) {
		this.xzyj = xzyj;
	}
	
	public String getBmyj() {
		return bmyj;
	}

	public void setBmyj(String bmyj) {
		this.bmyj = bmyj;
	}
	
	public String getZjl() {
		return zjl;
	}

	public void setZjl(String zjl) {
		this.zjl = zjl;
	}
	
	public String getRlzybm() {
		return rlzybm;
	}

	public void setRlzybm(String rlzybm) {
		this.rlzybm = rlzybm;
	}
	
	public String getBzsj() {
		return bzsj;
	}

	public void setBzsj(String bzsj) {
		this.bzsj = bzsj;
	}
	
	public String getFjmc() {
		return fjmc;
	}

	public void setFjmc(String fjmc) {
		this.fjmc = fjmc;
	}
	
	public String getFjlj() {
		return fjlj;
	}

	public void setFjlj(String fjlj) {
		this.fjlj = fjlj;
	}
	
}