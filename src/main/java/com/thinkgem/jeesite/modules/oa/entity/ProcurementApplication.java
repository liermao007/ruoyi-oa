/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 采购统计表Entity
 * @author oa
 * @version 2017-09-04
 */
public class ProcurementApplication extends DataEntity<ProcurementApplication> {
	
	private static final long serialVersionUID = 1L;
	private String col183;		// 编号
	private String col184;		// 申请人
	private String col185;		// 部门
	private String col186;		// 申请设备（物资）
	private String col187;		// 类别
	private String col188;		// 规格
	private String col189;		// 申请理由
	private String col191;		// 采购人
	private String col192;		// 采购部门
	private String col193;		// 供货渠道/商品信息
	private String procInsId;		// proc_ins_id
	private String procDefId;		// proc_def_id
	private String sfgdzc;		// 是否固定资产
	private String xzbmyj;		// 行政部门意见
	private String bmldyj;		// 部门领导意见
	private String zjlyj;		// 总经理意见
	private String bzsj;		// 备注数据
	private String fjmc;		// 附件名称
	private String fjlj;		// 附件路径
	private String cwjbr;		// 财务经办人
	private String ghqd;		// 供货渠道
	private String bgjj;		// 办公家具
	private String bgyp;		// 办公用品
	private String qt;		// 其他
	private String cwxzbyj;		// 财务行政部意见
	
	public ProcurementApplication() {
		super();
	}

	public ProcurementApplication(String id){
		super(id);
	}


    @JsonIgnore
    @NotNull(message = "编号不能为空")
    @ExcelField(title = "编号", align = 2, sort = 5)
	@Length(min=0, max=100, message="编号长度必须介于 0 和 100 之间")
	public String getCol183() {
		return col183;
	}

	public void setCol183(String col183) {
		this.col183 = col183;
	}


    @JsonIgnore
    @NotNull(message = "申请人不能为空")
    @ExcelField(title = "申请人", align = 2, sort = 10)
	@Length(min=0, max=100, message="申请人长度必须介于 0 和 100 之间")
	public String getCol184() {
		return col184;
	}

	public void setCol184(String col184) {
		this.col184 = col184;
	}


    @JsonIgnore
    @NotNull(message = "部门不能为空")
    @ExcelField(title = "部门", align = 2, sort = 15)
	@Length(min=0, max=100, message="部门长度必须介于 0 和 100 之间")
	public String getCol185() {
		return col185;
	}

	public void setCol185(String col185) {
		this.col185 = col185;
	}


    @JsonIgnore
    @NotNull(message = "申请设备不能为空")
    @ExcelField(title = "申请设备", align = 2, sort = 20)
	@Length(min=0, max=100, message="申请设备（物资）长度必须介于 0 和 100 之间")
	public String getCol186() {
		return col186;
	}

	public void setCol186(String col186) {
		this.col186 = col186;
	}


    @JsonIgnore
    @NotNull(message = "类别不能为空")
    @ExcelField(title = "类别", align = 2, sort = 25)
	@Length(min=0, max=100, message="类别长度必须介于 0 和 100 之间")
	public String getCol187() {
		return col187;
	}

	public void setCol187(String col187) {
		this.col187 = col187;
	}
	
	@Length(min=0, max=100, message="规格长度必须介于 0 和 100 之间")
	public String getCol188() {
		return col188;
	}

	public void setCol188(String col188) {
		this.col188 = col188;
	}

    @JsonIgnore

    @ExcelField(title = "申请理由", align = 2, sort = 30)
	@Length(min=0, max=100, message="申请理由长度必须介于 0 和 100 之间")
	public String getCol189() {
		return col189;
	}

	public void setCol189(String col189) {
		this.col189 = col189;
	}


    @JsonIgnore
    @NotNull(message = "采购人不能为空")
    @ExcelField(title = "采购人", align = 2, sort = 35)
	@Length(min=0, max=100, message="采购人长度必须介于 0 和 100 之间")
	public String getCol191() {
		return col191;
	}

	public void setCol191(String col191) {
		this.col191 = col191;
	}

    @JsonIgnore
    @NotNull(message = "采购部门不能为空")
    @ExcelField(title = "采购部门", align = 2, sort = 40)
	@Length(min=0, max=100, message="采购部门长度必须介于 0 和 100 之间")
	public String getCol192() {
		return col192;
	}

	public void setCol192(String col192) {
		this.col192 = col192;
	}

    @JsonIgnore
    @NotNull(message = "供货渠道不能为空")
    @ExcelField(title = "供货渠道", align = 2, sort = 45)
	@Length(min=0, max=100, message="供货渠道/商品信息长度必须介于 0 和 100 之间")
	public String getCol193() {
		return col193;
	}

	public void setCol193(String col193) {
		this.col193 = col193;
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
	
	@Length(min=0, max=100, message="是否固定资产长度必须介于 0 和 100 之间")
	public String getSfgdzc() {
		return sfgdzc;
	}

	public void setSfgdzc(String sfgdzc) {
		this.sfgdzc = sfgdzc;
	}
	
	public String getXzbmyj() {
		return xzbmyj;
	}

	public void setXzbmyj(String xzbmyj) {
		this.xzbmyj = xzbmyj;
	}
	
	public String getBmldyj() {
		return bmldyj;
	}

	public void setBmldyj(String bmldyj) {
		this.bmldyj = bmldyj;
	}
	
	public String getZjlyj() {
		return zjlyj;
	}

	public void setZjlyj(String zjlyj) {
		this.zjlyj = zjlyj;
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
	
	@Length(min=0, max=500, message="财务经办人长度必须介于 0 和 500 之间")
	public String getCwjbr() {
		return cwjbr;
	}

	public void setCwjbr(String cwjbr) {
		this.cwjbr = cwjbr;
	}
	
	@Length(min=0, max=500, message="供货渠道长度必须介于 0 和 500 之间")
	public String getGhqd() {
		return ghqd;
	}

	public void setGhqd(String ghqd) {
		this.ghqd = ghqd;
	}
	
	@Length(min=0, max=500, message="办公家具长度必须介于 0 和 500 之间")
	public String getBgjj() {
		return bgjj;
	}

	public void setBgjj(String bgjj) {
		this.bgjj = bgjj;
	}
	
	@Length(min=0, max=500, message="办公用品长度必须介于 0 和 500 之间")
	public String getBgyp() {
		return bgyp;
	}

	public void setBgyp(String bgyp) {
		this.bgyp = bgyp;
	}
	
	@Length(min=0, max=500, message="其他长度必须介于 0 和 500 之间")
	public String getQt() {
		return qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}
	
	@Length(min=0, max=500, message="财务行政部意见长度必须介于 0 和 500 之间")
	public String getCwxzbyj() {
		return cwxzbyj;
	}

	public void setCwxzbyj(String cwxzbyj) {
		this.cwxzbyj = cwxzbyj;
	}
	
}