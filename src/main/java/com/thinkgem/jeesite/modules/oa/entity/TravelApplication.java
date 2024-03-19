/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 出差统计表Entity
 * @author oa
 * @version 2017-09-04
 */
public class TravelApplication extends DataEntity<TravelApplication> {

    private static final long serialVersionUID = 1L;
    private String bh;		// 编号
    private String ccdd;		// 出差地点
	private String procInsId;		// proc_ins_id
	private String procDefId;		// proc_def_id
	private String bzsj;		// 备注数据
	private String fjlj;		// 附件路径
	private String fjmc;		// 附件名称
	private String ccts;		// 出差天数
	private String ccsy;		// 出差事由
	private String syrs;		// 随员人数
	private String cf1;		// 出发1
	private String cf2;		// 出发2
	private String cf3;		// 出发3
	private String cf4;		// 出发4
	private String cf5;		// 出发5
	private String dd1;		// 到达1
	private String dd2;		// 到达2
	private String dd3;		// 到达3
	private String dd4;		// 到达4
	private String dd5;		// 到达5
	private Date ccjssj;		// 出差结束时间
	private String jtgj1;		// 交通工具1
	private String jtgj2;		// 交通工具2
	private String jtgj3;		// 交通工具3
	private String jtgj4;		// 交通工具4
	private String jtgj5;		// 交通工具5
	private String xzbmyj;		// 行政部门意见
	private String bmjlyj;		// 部门经理意见
	private String ccr1;		// 出差人1
	private Date ccrq;		// 出差日期
	private String bm;		// 部门
	private String sfcc;		// 是否出差
	private Date cckssj;		// 出差开始时间
	private String xm;		// 申请人
	private String zjlsp;		// 总经理审批
    private int count;		// 总经理审批
	
	public TravelApplication() {
		super();
	}

	public TravelApplication(String id){
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


    @JsonIgnore
    @NotNull(message = "出差地点不能为空")
    @ExcelField(title = "出差地点", align = 2, sort = 40)
	@Length(min=0, max=100, message="出差地点长度必须介于 0 和 100 之间")
	public String getCcdd() {
		return ccdd;
	}

	public void setCcdd(String ccdd) {
		this.ccdd = ccdd;
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
	
	public String getBzsj() {
		return bzsj;
	}

	public void setBzsj(String bzsj) {
		this.bzsj = bzsj;
	}
	
	public String getFjlj() {
		return fjlj;
	}

	public void setFjlj(String fjlj) {
		this.fjlj = fjlj;
	}
	
	public String getFjmc() {
		return fjmc;
	}

	public void setFjmc(String fjmc) {
		this.fjmc = fjmc;
	}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Length(min=0, max=100, message="出差天数长度必须介于 0 和 100 之间")
	public String getCcts() {
		return ccts;
	}

	public void setCcts(String ccts) {
		this.ccts = ccts;
	}


    @JsonIgnore
    @NotNull(message = "出差事由不能为空")
    @ExcelField(title = "出差事由", align = 2, sort = 50)
	@Length(min=0, max=300, message="出差事由长度必须介于 0 和 100 之间")
	public String getCcsy() {
		return ccsy;
	}

	public void setCcsy(String ccsy) {
		this.ccsy = ccsy;
	}


	@Length(min=0, max=500, message="随员人数长度必须介于 0 和 500 之间")
	public String getSyrs() {
		return syrs;
	}

	public void setSyrs(String syrs) {
		this.syrs = syrs;
	}
	
	@Length(min=0, max=200, message="出发1长度必须介于 0 和 200 之间")
	public String getCf1() {
		return cf1;
	}

	public void setCf1(String cf1) {
		this.cf1 = cf1;
	}
	
	@Length(min=0, max=200, message="出发2长度必须介于 0 和 200 之间")
	public String getCf2() {
		return cf2;
	}

	public void setCf2(String cf2) {
		this.cf2 = cf2;
	}
	
	@Length(min=0, max=200, message="出发3长度必须介于 0 和 200 之间")
	public String getCf3() {
		return cf3;
	}

	public void setCf3(String cf3) {
		this.cf3 = cf3;
	}
	
	@Length(min=0, max=200, message="出发4长度必须介于 0 和 200 之间")
	public String getCf4() {
		return cf4;
	}

	public void setCf4(String cf4) {
		this.cf4 = cf4;
	}
	
	@Length(min=0, max=500, message="出发5长度必须介于 0 和 500 之间")
	public String getCf5() {
		return cf5;
	}

	public void setCf5(String cf5) {
		this.cf5 = cf5;
	}
	
	@Length(min=0, max=500, message="到达1长度必须介于 0 和 500 之间")
	public String getDd1() {
		return dd1;
	}

	public void setDd1(String dd1) {
		this.dd1 = dd1;
	}
	
	@Length(min=0, max=500, message="到达2长度必须介于 0 和 500 之间")
	public String getDd2() {
		return dd2;
	}

	public void setDd2(String dd2) {
		this.dd2 = dd2;
	}
	
	@Length(min=0, max=500, message="到达3长度必须介于 0 和 500 之间")
	public String getDd3() {
		return dd3;
	}

	public void setDd3(String dd3) {
		this.dd3 = dd3;
	}
	
	@Length(min=0, max=500, message="到达4长度必须介于 0 和 500 之间")
	public String getDd4() {
		return dd4;
	}

	public void setDd4(String dd4) {
		this.dd4 = dd4;
	}
	
	@Length(min=0, max=500, message="到达5长度必须介于 0 和 500 之间")
	public String getDd5() {
		return dd5;
	}

	public void setDd5(String dd5) {
		this.dd5 = dd5;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCcjssj() {
		return ccjssj;
	}

	public void setCcjssj(Date ccjssj) {
		this.ccjssj = ccjssj;
	}
	
	@Length(min=0, max=200, message="交通工具1长度必须介于 0 和 200 之间")
	public String getJtgj1() {
		return jtgj1;
	}

	public void setJtgj1(String jtgj1) {
		this.jtgj1 = jtgj1;
	}
	
	@Length(min=0, max=200, message="交通工具2长度必须介于 0 和 200 之间")
	public String getJtgj2() {
		return jtgj2;
	}

	public void setJtgj2(String jtgj2) {
		this.jtgj2 = jtgj2;
	}
	
	@Length(min=0, max=200, message="交通工具3长度必须介于 0 和 200 之间")
	public String getJtgj3() {
		return jtgj3;
	}

	public void setJtgj3(String jtgj3) {
		this.jtgj3 = jtgj3;
	}
	
	@Length(min=0, max=200, message="交通工具4长度必须介于 0 和 200 之间")
	public String getJtgj4() {
		return jtgj4;
	}

	public void setJtgj4(String jtgj4) {
		this.jtgj4 = jtgj4;
	}
	
	@Length(min=0, max=200, message="交通工具5长度必须介于 0 和 200 之间")
	public String getJtgj5() {
		return jtgj5;
	}

	public void setJtgj5(String jtgj5) {
		this.jtgj5 = jtgj5;
	}
	
	@Length(min=0, max=500, message="行政部门意见长度必须介于 0 和 500 之间")
	public String getXzbmyj() {
		return xzbmyj;
	}

	public void setXzbmyj(String xzbmyj) {
		this.xzbmyj = xzbmyj;
	}
	
	@Length(min=0, max=500, message="部门经理意见长度必须介于 0 和 500 之间")
	public String getBmjlyj() {
		return bmjlyj;
	}

	public void setBmjlyj(String bmjlyj) {
		this.bmjlyj = bmjlyj;
	}
	
	@Length(min=0, max=500, message="出差人1长度必须介于 0 和 500 之间")
	public String getCcr1() {
		return ccr1;
	}

	public void setCcr1(String ccr1) {
		this.ccr1 = ccr1;
	}


    @JsonIgnore
    @NotNull(message = "出差日期不能为空")
    @ExcelField(title = "出差日期", align = 2, sort = 40)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCcrq() {
		return ccrq;
	}

	public void setCcrq(Date ccrq) {
		this.ccrq = ccrq;
	}


    @JsonIgnore
    @NotNull(message = "部门不能为空")
    @ExcelField(title = "部门", align = 2, sort = 10)
	@Length(min=0, max=500, message="部门长度必须介于 0 和 500 之间")
	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}
	
	@Length(min=0, max=500, message="是否出差长度必须介于 0 和 500 之间")
	public String getSfcc() {
		return sfcc;
	}

	public void setSfcc(String sfcc) {
		this.sfcc = sfcc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCckssj() {
		return cckssj;
	}

	public void setCckssj(Date cckssj) {
		this.cckssj = cckssj;
	}



    @JsonIgnore
    @NotNull(message = "出差人不能为空")
    @ExcelField(title = "出差人", align = 2, sort = 15)
	@Length(min=0, max=500, message="申请人长度必须介于 0 和 500 之间")

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }
	
	@Length(min=0, max=500, message="总经理审批长度必须介于 0 和 500 之间")
	public String getZjlsp() {
		return zjlsp;
	}

	public void setZjlsp(String zjlsp) {
		this.zjlsp = zjlsp;
	}



	
}