/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 标的医院综合评价Entity
 * @author oa
 * @version 2017-04-06
 */
public class TargetHospital extends DataEntity<TargetHospital> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// proc_ins_id
	private String procDefId;		// proc_def_id
	private String syz;		// 所有制
	private String dj;		// 等级
	private String ylxz;		// 营利性质
	private String zdmj;		// 占地面积
	private String sfyfzyld;		// 是否有发展预留地
	private String ylyf;		// 医疗用房
	private String zkcw;		// 展开床位
	private String nmzl;		// 年门诊量
	private String ncyrc;		// 年出院人次
	private String nssl;		// 年手术量
	private String zjysryzb;		// 中级以上人员占比
	private String zdks;		// 重点科室
	private String sbzz;		// 设备总值
	private String nylsr;		// 年医疗收入
	private String yzb;		// 药占比
	private String zcfzl;		// 资产负债率
	private String dqzw;		// 短期债务
	private String ncjsr;		// 年床均收入
	private String mzcjfy;		// 门诊次均费用
	private String zycjfy;		// 住院次均费用
	private String ybbf;		// 医保拨付
	private String ykph;		// 盈亏平衡
	private String fsqy;		// 辐射区域
	private String sczyl;		// 市场占有率
	private String zfdw;		// 政府定位
	private String qykb;		// 区域口碑
	private String ybdd;		// 医保定点
	private String xzf26;		// 修正分26
	private String xzf25;		// 修正分25
	private String xzf24;		// 修正分24
	private String xzf23;		// 修正分23
	private String xzf22;		// 修正分22
	private String xzf21;		// 修正分21
	private String xzf20;		// 修正分20
	private String xzf19;		// 修正分19
	private String xzf18;		// 修正分18
	private String xzf17;		// 修正分17
	private String xzf16;		// 修正分16
	private String xzf15;		// 修正分15
	private String xzf14;		// 修正分14
	private String xzf13;		// 修正分13
	private String xzf12;		// 修正分12
	private String xzf11;		// xzf11
	private String xzf10;		// 修正分10
	private String xzf9;		// 修正分9
	private String xzf8;		// 修正分8
	private String xzf7;		// 修正分7
	private String xzf6;		// 修正分6
	private String xzf5;		// 修正分5
	private String xzf4;		// 修正分4
	private String xzf3;		// 修正分3
	private String xzf2;		// 修正分2
	private String xzf1;		// 修正分1
	private String xzf27;		// 修正分27
	private String xzf28;		// 修正分28
	private String df2;		// 得分2
	private String df3;		// 得分3
	private String df4;		// 得分4
	private String df5;		// 得分5
	private String df6;		// 得分6
	private String df7;		// 得分7
	private String df8;		// 得分8
	private String df9;		// 得分9
	private String df10;		// 得分10
	private String df11;		// 得分11
	private String df12;		// 得分12
	private String df13;		// 得分13
	private String df14;		// 得分14
	private String df15;		// 得分15
	private String df16;		// 得分16
	private String df17;		// 得分17
	private String df18;		// 得分18
	private String df19;		// 得分19
	private String df20;		// 得分20
	private String df21;		// 得分21
	private String df22;		// 得分22
	private String df23;		// 得分23
	private String df24;		// 得分24
	private String df25;		// 得分25
	private String df26;		// 得分26
	private String df1;		// 得分1
	private String df28;		// 得分28
	private String xzf;		// 修正分
	private String df;		// 得分
	private String df27;		// 得分27
	private String szd;		// 所在地
	private String xzsm;		// 修正说明1
	private String xzsm2;		// 修正说明2
	private String xzsm3;		// 修正说明3
	private String xzsm4;		// 修正说明4
	private String xzsm5;		// 修正说明5
	private String xzsm6;		// 修正说明6
	private String xzsm7;		// 修正说明7
	private String xzsm8;		// 修正说明8
	private String xzsm9;		// 修正说明9
	private String xzsm10;		// 修正说明10
	private String xzsm11;		// 修正说明11
	private String xzsm12;		// 修正说明12
	private String xzsm13;		// 修正说明13
	private String xzsm14;		// 修正说明14
	private String xzsm15;		// 修正说明15
	private String xzsm16;		// 修正说明16
	private String xzsm17;		// 修正说明17
	private String xzsm18;		// 修正说明18
	private String xzsm19;		// 修正说明19
	private String xzsm20;		// 修正说明20
	private String xzsm21;		// 修正说明21
	private String xzsm22;		// 修正说明22
	private String xzsm23;		// 修正说明23
	private String xzsm24;		// 修正说明24
	private String xzsm25;		// 修正说明25
	private String xzsm26;		// 修正说明26
	private String xzsm27;		// 修正说明27
	private String xzsm28;		// 修正说明28
	private String sm1;		// 说明1
	private String sm2;		// 说明2
	private String sm3;		// 说明3
	private String sm4;		// 说明4
	private String sm6;		// sm6
	private String sm7;		// sm7
	private String sm8;		// sm8
	private String sm9;		// sm9
	private String sm10;		// 说明10
	private String sm11;		// 说明11
	private String sm12;		// 说明12
	private String sm13;		// 说明13
	private String sm5;		// 说明5
	private String sm14;		// 说明14
	private String sm15;		// 说明15
	private String sm16;		// 说明16
	private String sm17;		// 说明17
	private String sm18;		// 说明18
	private String sm19;		// 说明19
	private String sm20;		// 说明20
	private String sm21;		// 说明21
	private String fsry;		// 辐射人员
	private String xzf29;		// 修正分29
	private String xzsm29;		// 修正说明29
	private String df29;		// 得分29
	private String sm22;		// sm22
	private String sm23;		// sm23
	private String sm24;		// 说明24
	private String sm29;		// 说明29
	private String yymc;		// 医院名称
	private String bz;		// 标识
	
	public TargetHospital() {
		super();
	}

	public TargetHospital(String id){
		super(id);
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
	
	@Length(min=0, max=100, message="所有制长度必须介于 0 和 100 之间")
	public String getSyz() {
		return syz;
	}

	public void setSyz(String syz) {
		this.syz = syz;
	}
	
	@Length(min=0, max=100, message="等级长度必须介于 0 和 100 之间")
	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}
	
	@Length(min=0, max=100, message="营利性质长度必须介于 0 和 100 之间")
	public String getYlxz() {
		return ylxz;
	}

	public void setYlxz(String ylxz) {
		this.ylxz = ylxz;
	}
	
	@Length(min=0, max=100, message="占地面积长度必须介于 0 和 100 之间")
	public String getZdmj() {
		return zdmj;
	}

	public void setZdmj(String zdmj) {
		this.zdmj = zdmj;
	}
	
	@Length(min=0, max=100, message="是否有发展预留地长度必须介于 0 和 100 之间")
	public String getSfyfzyld() {
		return sfyfzyld;
	}

	public void setSfyfzyld(String sfyfzyld) {
		this.sfyfzyld = sfyfzyld;
	}
	
	@Length(min=0, max=100, message="医疗用房长度必须介于 0 和 100 之间")
	public String getYlyf() {
		return ylyf;
	}

	public void setYlyf(String ylyf) {
		this.ylyf = ylyf;
	}
	
	@Length(min=0, max=100, message="展开床位长度必须介于 0 和 100 之间")
	public String getZkcw() {
		return zkcw;
	}

	public void setZkcw(String zkcw) {
		this.zkcw = zkcw;
	}
	
	@Length(min=0, max=100, message="年门诊量长度必须介于 0 和 100 之间")
	public String getNmzl() {
		return nmzl;
	}

	public void setNmzl(String nmzl) {
		this.nmzl = nmzl;
	}
	
	@Length(min=0, max=100, message="年出院人次长度必须介于 0 和 100 之间")
	public String getNcyrc() {
		return ncyrc;
	}

	public void setNcyrc(String ncyrc) {
		this.ncyrc = ncyrc;
	}
	
	@Length(min=0, max=100, message="年手术量长度必须介于 0 和 100 之间")
	public String getNssl() {
		return nssl;
	}

	public void setNssl(String nssl) {
		this.nssl = nssl;
	}
	
	@Length(min=0, max=100, message="中级以上人员占比长度必须介于 0 和 100 之间")
	public String getZjysryzb() {
		return zjysryzb;
	}

	public void setZjysryzb(String zjysryzb) {
		this.zjysryzb = zjysryzb;
	}
	
	@Length(min=0, max=100, message="重点科室长度必须介于 0 和 100 之间")
	public String getZdks() {
		return zdks;
	}

	public void setZdks(String zdks) {
		this.zdks = zdks;
	}
	
	@Length(min=0, max=100, message="设备总值长度必须介于 0 和 100 之间")
	public String getSbzz() {
		return sbzz;
	}

	public void setSbzz(String sbzz) {
		this.sbzz = sbzz;
	}
	
	@Length(min=0, max=100, message="年医疗收入长度必须介于 0 和 100 之间")
	public String getNylsr() {
		return nylsr;
	}

	public void setNylsr(String nylsr) {
		this.nylsr = nylsr;
	}
	
	@Length(min=0, max=100, message="药占比长度必须介于 0 和 100 之间")
	public String getYzb() {
		return yzb;
	}

	public void setYzb(String yzb) {
		this.yzb = yzb;
	}
	
	@Length(min=0, max=100, message="资产负债率长度必须介于 0 和 100 之间")
	public String getZcfzl() {
		return zcfzl;
	}

	public void setZcfzl(String zcfzl) {
		this.zcfzl = zcfzl;
	}

    public String getFsqy() {
        return fsqy;
    }

    public void setFsqy(String fsqy) {
        this.fsqy = fsqy;
    }

    public String getSczyl() {
        return sczyl;
    }

    public void setSczyl(String sczyl) {
        this.sczyl = sczyl;
    }

    public String getYbdd() {
        return ybdd;
    }

    public void setYbdd(String ybdd) {
        this.ybdd = ybdd;
    }

    @Length(min=0, max=100, message="短期债务长度必须介于 0 和 100 之间")

	public String getDqzw() {
		return dqzw;
	}

	public void setDqzw(String dqzw) {
		this.dqzw = dqzw;
	}
	
	@Length(min=0, max=100, message="年床均收入长度必须介于 0 和 100 之间")
	public String getNcjsr() {
		return ncjsr;
	}

	public void setNcjsr(String ncjsr) {
		this.ncjsr = ncjsr;
	}
	
	@Length(min=0, max=100, message="门诊次均费用长度必须介于 0 和 100 之间")
	public String getMzcjfy() {
		return mzcjfy;
	}

	public void setMzcjfy(String mzcjfy) {
		this.mzcjfy = mzcjfy;
	}
	
	@Length(min=0, max=100, message="住院次均费用长度必须介于 0 和 100 之间")
	public String getZycjfy() {
		return zycjfy;
	}

	public void setZycjfy(String zycjfy) {
		this.zycjfy = zycjfy;
	}
	
	@Length(min=0, max=100, message="医保拨付长度必须介于 0 和 100 之间")
	public String getYbbf() {
		return ybbf;
	}

	public void setYbbf(String ybbf) {
		this.ybbf = ybbf;
	}
	
	@Length(min=0, max=100, message="盈亏平衡长度必须介于 0 和 100 之间")
	public String getYkph() {
		return ykph;
	}

	public void setYkph(String ykph) {
		this.ykph = ykph;
	}

	
	@Length(min=0, max=100, message="市场占有率长度必须介于 0 和 100 之间")
	public String getZfdw() {
		return zfdw;
	}

	public void setZfdw(String zfdw) {
		this.zfdw = zfdw;
	}
	
	@Length(min=0, max=100, message="区域口碑长度必须介于 0 和 100 之间")
	public String getQykb() {
		return qykb;
	}

	public void setQykb(String qykb) {
		this.qykb = qykb;
	}

	
	@Length(min=0, max=100, message="修正分26长度必须介于 0 和 100 之间")
	public String getXzf26() {
		return xzf26;
	}

	public void setXzf26(String xzf26) {
		this.xzf26 = xzf26;
	}
	
	@Length(min=0, max=100, message="修正分25长度必须介于 0 和 100 之间")
	public String getXzf25() {
		return xzf25;
	}

	public void setXzf25(String xzf25) {
		this.xzf25 = xzf25;
	}
	
	@Length(min=0, max=100, message="修正分24长度必须介于 0 和 100 之间")
	public String getXzf24() {
		return xzf24;
	}

	public void setXzf24(String xzf24) {
		this.xzf24 = xzf24;
	}
	
	@Length(min=0, max=100, message="修正分23长度必须介于 0 和 100 之间")
	public String getXzf23() {
		return xzf23;
	}

	public void setXzf23(String xzf23) {
		this.xzf23 = xzf23;
	}
	
	@Length(min=0, max=100, message="修正分22长度必须介于 0 和 100 之间")
	public String getXzf22() {
		return xzf22;
	}

	public void setXzf22(String xzf22) {
		this.xzf22 = xzf22;
	}
	
	@Length(min=0, max=100, message="修正分21长度必须介于 0 和 100 之间")
	public String getXzf21() {
		return xzf21;
	}

	public void setXzf21(String xzf21) {
		this.xzf21 = xzf21;
	}
	
	@Length(min=0, max=100, message="修正分20长度必须介于 0 和 100 之间")
	public String getXzf20() {
		return xzf20;
	}

	public void setXzf20(String xzf20) {
		this.xzf20 = xzf20;
	}
	
	@Length(min=0, max=100, message="修正分19长度必须介于 0 和 100 之间")
	public String getXzf19() {
		return xzf19;
	}

	public void setXzf19(String xzf19) {
		this.xzf19 = xzf19;
	}
	
	@Length(min=0, max=100, message="修正分18长度必须介于 0 和 100 之间")
	public String getXzf18() {
		return xzf18;
	}

	public void setXzf18(String xzf18) {
		this.xzf18 = xzf18;
	}
	
	@Length(min=0, max=100, message="修正分17长度必须介于 0 和 100 之间")
	public String getXzf17() {
		return xzf17;
	}

	public void setXzf17(String xzf17) {
		this.xzf17 = xzf17;
	}
	
	@Length(min=0, max=100, message="修正分16长度必须介于 0 和 100 之间")
	public String getXzf16() {
		return xzf16;
	}

	public void setXzf16(String xzf16) {
		this.xzf16 = xzf16;
	}
	
	@Length(min=0, max=100, message="修正分15长度必须介于 0 和 100 之间")
	public String getXzf15() {
		return xzf15;
	}

	public void setXzf15(String xzf15) {
		this.xzf15 = xzf15;
	}
	
	@Length(min=0, max=100, message="修正分14长度必须介于 0 和 100 之间")
	public String getXzf14() {
		return xzf14;
	}

	public void setXzf14(String xzf14) {
		this.xzf14 = xzf14;
	}
	
	@Length(min=0, max=100, message="修正分13长度必须介于 0 和 100 之间")
	public String getXzf13() {
		return xzf13;
	}

	public void setXzf13(String xzf13) {
		this.xzf13 = xzf13;
	}
	
	@Length(min=0, max=100, message="修正分12长度必须介于 0 和 100 之间")
	public String getXzf12() {
		return xzf12;
	}

	public void setXzf12(String xzf12) {
		this.xzf12 = xzf12;
	}
	
	@Length(min=0, max=100, message="xzf11长度必须介于 0 和 100 之间")
	public String getXzf11() {
		return xzf11;
	}

	public void setXzf11(String xzf11) {
		this.xzf11 = xzf11;
	}
	
	@Length(min=0, max=100, message="修正分10长度必须介于 0 和 100 之间")
	public String getXzf10() {
		return xzf10;
	}

	public void setXzf10(String xzf10) {
		this.xzf10 = xzf10;
	}
	
	@Length(min=0, max=100, message="修正分9长度必须介于 0 和 100 之间")
	public String getXzf9() {
		return xzf9;
	}

	public void setXzf9(String xzf9) {
		this.xzf9 = xzf9;
	}
	
	@Length(min=0, max=100, message="修正分8长度必须介于 0 和 100 之间")
	public String getXzf8() {
		return xzf8;
	}

	public void setXzf8(String xzf8) {
		this.xzf8 = xzf8;
	}
	
	@Length(min=0, max=100, message="修正分7长度必须介于 0 和 100 之间")
	public String getXzf7() {
		return xzf7;
	}

	public void setXzf7(String xzf7) {
		this.xzf7 = xzf7;
	}
	
	@Length(min=0, max=100, message="修正分6长度必须介于 0 和 100 之间")
	public String getXzf6() {
		return xzf6;
	}

	public void setXzf6(String xzf6) {
		this.xzf6 = xzf6;
	}
	
	@Length(min=0, max=100, message="修正分5长度必须介于 0 和 100 之间")
	public String getXzf5() {
		return xzf5;
	}

	public void setXzf5(String xzf5) {
		this.xzf5 = xzf5;
	}
	
	@Length(min=0, max=100, message="修正分4长度必须介于 0 和 100 之间")
	public String getXzf4() {
		return xzf4;
	}

	public void setXzf4(String xzf4) {
		this.xzf4 = xzf4;
	}
	
	public String getXzf3() {
		return xzf3;
	}

	public void setXzf3(String xzf3) {
		this.xzf3 = xzf3;
	}
	
	public String getXzf2() {
		return xzf2;
	}

	public void setXzf2(String xzf2) {
		this.xzf2 = xzf2;
	}
	
	public String getXzf1() {
		return xzf1;
	}

	public void setXzf1(String xzf1) {
		this.xzf1 = xzf1;
	}
	
	public String getXzf27() {
		return xzf27;
	}

	public void setXzf27(String xzf27) {
		this.xzf27 = xzf27;
	}
	
	public String getXzf28() {
		return xzf28;
	}

	public void setXzf28(String xzf28) {
		this.xzf28 = xzf28;
	}
	
	public String getDf2() {
		return df2;
	}

	public void setDf2(String df2) {
		this.df2 = df2;
	}
	
	public String getDf3() {
		return df3;
	}

	public void setDf3(String df3) {
		this.df3 = df3;
	}
	
	public String getDf4() {
		return df4;
	}

	public void setDf4(String df4) {
		this.df4 = df4;
	}
	
	public String getDf5() {
		return df5;
	}

	public void setDf5(String df5) {
		this.df5 = df5;
	}
	
	public String getDf6() {
		return df6;
	}

	public void setDf6(String df6) {
		this.df6 = df6;
	}
	
	public String getDf7() {
		return df7;
	}

	public void setDf7(String df7) {
		this.df7 = df7;
	}
	
	public String getDf8() {
		return df8;
	}

	public void setDf8(String df8) {
		this.df8 = df8;
	}
	
	public String getDf9() {
		return df9;
	}

	public void setDf9(String df9) {
		this.df9 = df9;
	}
	
	public String getDf10() {
		return df10;
	}

	public void setDf10(String df10) {
		this.df10 = df10;
	}
	
	public String getDf11() {
		return df11;
	}

	public void setDf11(String df11) {
		this.df11 = df11;
	}
	
	public String getDf12() {
		return df12;
	}

	public void setDf12(String df12) {
		this.df12 = df12;
	}
	
	public String getDf13() {
		return df13;
	}

	public void setDf13(String df13) {
		this.df13 = df13;
	}
	
	public String getDf14() {
		return df14;
	}

	public void setDf14(String df14) {
		this.df14 = df14;
	}
	
	public String getDf15() {
		return df15;
	}

	public void setDf15(String df15) {
		this.df15 = df15;
	}
	
	public String getDf16() {
		return df16;
	}

	public void setDf16(String df16) {
		this.df16 = df16;
	}
	
	public String getDf17() {
		return df17;
	}

	public void setDf17(String df17) {
		this.df17 = df17;
	}
	
	public String getDf18() {
		return df18;
	}

	public void setDf18(String df18) {
		this.df18 = df18;
	}
	
	public String getDf19() {
		return df19;
	}

	public void setDf19(String df19) {
		this.df19 = df19;
	}
	
	public String getDf20() {
		return df20;
	}

	public void setDf20(String df20) {
		this.df20 = df20;
	}
	
	public String getDf21() {
		return df21;
	}

	public void setDf21(String df21) {
		this.df21 = df21;
	}
	
	public String getDf22() {
		return df22;
	}

	public void setDf22(String df22) {
		this.df22 = df22;
	}
	
	public String getDf23() {
		return df23;
	}

	public void setDf23(String df23) {
		this.df23 = df23;
	}
	
	public String getDf24() {
		return df24;
	}

	public void setDf24(String df24) {
		this.df24 = df24;
	}
	
	public String getDf25() {
		return df25;
	}

	public void setDf25(String df25) {
		this.df25 = df25;
	}
	
	public String getDf26() {
		return df26;
	}

	public void setDf26(String df26) {
		this.df26 = df26;
	}
	
	public String getDf1() {
		return df1;
	}

	public void setDf1(String df1) {
		this.df1 = df1;
	}
	
	public String getDf28() {
		return df28;
	}

	public void setDf28(String df28) {
		this.df28 = df28;
	}
	
	public String getXzf() {
		return xzf;
	}

	public void setXzf(String xzf) {
		this.xzf = xzf;
	}
	
	public String getDf() {
		return df;
	}

	public void setDf(String df) {
		this.df = df;
	}
	
	public String getDf27() {
		return df27;
	}

	public void setDf27(String df27) {
		this.df27 = df27;
	}
	
	@Length(min=0, max=500, message="所在地长度必须介于 0 和 500 之间")
	public String getSzd() {
		return szd;
	}

	public void setSzd(String szd) {
		this.szd = szd;
	}
	
	@Length(min=0, max=300, message="修正说明1长度必须介于 0 和 300 之间")
	public String getXzsm() {
		return xzsm;
	}

	public void setXzsm(String xzsm) {
		this.xzsm = xzsm;
	}
	
	@Length(min=0, max=300, message="修正说明2长度必须介于 0 和 300 之间")
	public String getXzsm2() {
		return xzsm2;
	}

	public void setXzsm2(String xzsm2) {
		this.xzsm2 = xzsm2;
	}
	
	@Length(min=0, max=300, message="修正说明3长度必须介于 0 和 300 之间")
	public String getXzsm3() {
		return xzsm3;
	}

	public void setXzsm3(String xzsm3) {
		this.xzsm3 = xzsm3;
	}
	
	@Length(min=0, max=300, message="修正说明4长度必须介于 0 和 300 之间")
	public String getXzsm4() {
		return xzsm4;
	}

	public void setXzsm4(String xzsm4) {
		this.xzsm4 = xzsm4;
	}
	
	@Length(min=0, max=300, message="修正说明5长度必须介于 0 和 300 之间")
	public String getXzsm5() {
		return xzsm5;
	}

	public void setXzsm5(String xzsm5) {
		this.xzsm5 = xzsm5;
	}
	
	@Length(min=0, max=300, message="修正说明6长度必须介于 0 和 300 之间")
	public String getXzsm6() {
		return xzsm6;
	}

	public void setXzsm6(String xzsm6) {
		this.xzsm6 = xzsm6;
	}
	
	@Length(min=0, max=300, message="修正说明7长度必须介于 0 和 300 之间")
	public String getXzsm7() {
		return xzsm7;
	}

	public void setXzsm7(String xzsm7) {
		this.xzsm7 = xzsm7;
	}
	
	@Length(min=0, max=300, message="修正说明8长度必须介于 0 和 300 之间")
	public String getXzsm8() {
		return xzsm8;
	}

	public void setXzsm8(String xzsm8) {
		this.xzsm8 = xzsm8;
	}
	
	@Length(min=0, max=300, message="修正说明9长度必须介于 0 和 300 之间")
	public String getXzsm9() {
		return xzsm9;
	}

	public void setXzsm9(String xzsm9) {
		this.xzsm9 = xzsm9;
	}
	
	@Length(min=0, max=300, message="修正说明10长度必须介于 0 和 300 之间")
	public String getXzsm10() {
		return xzsm10;
	}

	public void setXzsm10(String xzsm10) {
		this.xzsm10 = xzsm10;
	}
	
	@Length(min=0, max=100, message="修正说明11长度必须介于 0 和 100 之间")
	public String getXzsm11() {
		return xzsm11;
	}

	public void setXzsm11(String xzsm11) {
		this.xzsm11 = xzsm11;
	}
	
	@Length(min=0, max=100, message="修正说明12长度必须介于 0 和 100 之间")
	public String getXzsm12() {
		return xzsm12;
	}

	public void setXzsm12(String xzsm12) {
		this.xzsm12 = xzsm12;
	}
	
	@Length(min=0, max=100, message="修正说明13长度必须介于 0 和 100 之间")
	public String getXzsm13() {
		return xzsm13;
	}

	public void setXzsm13(String xzsm13) {
		this.xzsm13 = xzsm13;
	}
	
	@Length(min=0, max=100, message="修正说明14长度必须介于 0 和 100 之间")
	public String getXzsm14() {
		return xzsm14;
	}

	public void setXzsm14(String xzsm14) {
		this.xzsm14 = xzsm14;
	}
	
	@Length(min=0, max=100, message="修正说明15长度必须介于 0 和 100 之间")
	public String getXzsm15() {
		return xzsm15;
	}

	public void setXzsm15(String xzsm15) {
		this.xzsm15 = xzsm15;
	}
	
	@Length(min=0, max=100, message="修正说明16长度必须介于 0 和 100 之间")
	public String getXzsm16() {
		return xzsm16;
	}

	public void setXzsm16(String xzsm16) {
		this.xzsm16 = xzsm16;
	}
	
	@Length(min=0, max=100, message="修正说明17长度必须介于 0 和 100 之间")
	public String getXzsm17() {
		return xzsm17;
	}

	public void setXzsm17(String xzsm17) {
		this.xzsm17 = xzsm17;
	}
	
	@Length(min=0, max=100, message="修正说明18长度必须介于 0 和 100 之间")
	public String getXzsm18() {
		return xzsm18;
	}

	public void setXzsm18(String xzsm18) {
		this.xzsm18 = xzsm18;
	}
	
	@Length(min=0, max=100, message="修正说明19长度必须介于 0 和 100 之间")
	public String getXzsm19() {
		return xzsm19;
	}

	public void setXzsm19(String xzsm19) {
		this.xzsm19 = xzsm19;
	}
	
	@Length(min=0, max=100, message="修正说明20长度必须介于 0 和 100 之间")
	public String getXzsm20() {
		return xzsm20;
	}

	public void setXzsm20(String xzsm20) {
		this.xzsm20 = xzsm20;
	}
	
	@Length(min=0, max=100, message="修正说明21长度必须介于 0 和 100 之间")
	public String getXzsm21() {
		return xzsm21;
	}

	public void setXzsm21(String xzsm21) {
		this.xzsm21 = xzsm21;
	}
	
	@Length(min=0, max=100, message="修正说明22长度必须介于 0 和 100 之间")
	public String getXzsm22() {
		return xzsm22;
	}

	public void setXzsm22(String xzsm22) {
		this.xzsm22 = xzsm22;
	}
	
	@Length(min=0, max=100, message="修正说明23长度必须介于 0 和 100 之间")
	public String getXzsm23() {
		return xzsm23;
	}

	public void setXzsm23(String xzsm23) {
		this.xzsm23 = xzsm23;
	}
	
	@Length(min=0, max=100, message="修正说明24长度必须介于 0 和 100 之间")
	public String getXzsm24() {
		return xzsm24;
	}

	public void setXzsm24(String xzsm24) {
		this.xzsm24 = xzsm24;
	}
	
	@Length(min=0, max=100, message="修正说明25长度必须介于 0 和 100 之间")
	public String getXzsm25() {
		return xzsm25;
	}

	public void setXzsm25(String xzsm25) {
		this.xzsm25 = xzsm25;
	}
	
	@Length(min=0, max=100, message="修正说明26长度必须介于 0 和 100 之间")
	public String getXzsm26() {
		return xzsm26;
	}

	public void setXzsm26(String xzsm26) {
		this.xzsm26 = xzsm26;
	}
	
	@Length(min=0, max=100, message="修正说明27长度必须介于 0 和 100 之间")
	public String getXzsm27() {
		return xzsm27;
	}

	public void setXzsm27(String xzsm27) {
		this.xzsm27 = xzsm27;
	}
	
	@Length(min=0, max=100, message="修正说明28长度必须介于 0 和 100 之间")
	public String getXzsm28() {
		return xzsm28;
	}

	public void setXzsm28(String xzsm28) {
		this.xzsm28 = xzsm28;
	}
	
	@Length(min=0, max=100, message="说明1长度必须介于 0 和 100 之间")
	public String getSm1() {
		return sm1;
	}

	public void setSm1(String sm1) {
		this.sm1 = sm1;
	}
	
	@Length(min=0, max=100, message="说明2长度必须介于 0 和 100 之间")
	public String getSm2() {
		return sm2;
	}

	public void setSm2(String sm2) {
		this.sm2 = sm2;
	}
	
	@Length(min=0, max=100, message="说明3长度必须介于 0 和 100 之间")
	public String getSm3() {
		return sm3;
	}

	public void setSm3(String sm3) {
		this.sm3 = sm3;
	}
	
	@Length(min=0, max=100, message="说明4长度必须介于 0 和 100 之间")
	public String getSm4() {
		return sm4;
	}

	public void setSm4(String sm4) {
		this.sm4 = sm4;
	}
	
	@Length(min=0, max=100, message="sm6长度必须介于 0 和 100 之间")
	public String getSm6() {
		return sm6;
	}

	public void setSm6(String sm6) {
		this.sm6 = sm6;
	}
	
	@Length(min=0, max=100, message="sm7长度必须介于 0 和 100 之间")
	public String getSm7() {
		return sm7;
	}

	public void setSm7(String sm7) {
		this.sm7 = sm7;
	}
	
	@Length(min=0, max=100, message="sm8长度必须介于 0 和 100 之间")
	public String getSm8() {
		return sm8;
	}

	public void setSm8(String sm8) {
		this.sm8 = sm8;
	}
	
	@Length(min=0, max=100, message="sm9长度必须介于 0 和 100 之间")
	public String getSm9() {
		return sm9;
	}

	public void setSm9(String sm9) {
		this.sm9 = sm9;
	}
	
	@Length(min=0, max=100, message="说明10长度必须介于 0 和 100 之间")
	public String getSm10() {
		return sm10;
	}

	public void setSm10(String sm10) {
		this.sm10 = sm10;
	}
	
	@Length(min=0, max=100, message="说明11长度必须介于 0 和 100 之间")
	public String getSm11() {
		return sm11;
	}

	public void setSm11(String sm11) {
		this.sm11 = sm11;
	}
	
	@Length(min=0, max=100, message="说明12长度必须介于 0 和 100 之间")
	public String getSm12() {
		return sm12;
	}

	public void setSm12(String sm12) {
		this.sm12 = sm12;
	}
	
	@Length(min=0, max=100, message="说明13长度必须介于 0 和 100 之间")
	public String getSm13() {
		return sm13;
	}

	public void setSm13(String sm13) {
		this.sm13 = sm13;
	}
	
	@Length(min=0, max=100, message="说明5长度必须介于 0 和 100 之间")
	public String getSm5() {
		return sm5;
	}

	public void setSm5(String sm5) {
		this.sm5 = sm5;
	}
	
	@Length(min=0, max=100, message="说明14长度必须介于 0 和 100 之间")
	public String getSm14() {
		return sm14;
	}

	public void setSm14(String sm14) {
		this.sm14 = sm14;
	}
	
	@Length(min=0, max=100, message="说明15长度必须介于 0 和 100 之间")
	public String getSm15() {
		return sm15;
	}

	public void setSm15(String sm15) {
		this.sm15 = sm15;
	}
	
	@Length(min=0, max=100, message="说明16长度必须介于 0 和 100 之间")
	public String getSm16() {
		return sm16;
	}

	public void setSm16(String sm16) {
		this.sm16 = sm16;
	}
	
	@Length(min=0, max=100, message="说明17长度必须介于 0 和 100 之间")
	public String getSm17() {
		return sm17;
	}

	public void setSm17(String sm17) {
		this.sm17 = sm17;
	}
	
	@Length(min=0, max=100, message="说明18长度必须介于 0 和 100 之间")
	public String getSm18() {
		return sm18;
	}

	public void setSm18(String sm18) {
		this.sm18 = sm18;
	}
	
	@Length(min=0, max=100, message="说明19长度必须介于 0 和 100 之间")
	public String getSm19() {
		return sm19;
	}

	public void setSm19(String sm19) {
		this.sm19 = sm19;
	}
	
	@Length(min=0, max=100, message="说明20长度必须介于 0 和 100 之间")
	public String getSm20() {
		return sm20;
	}

	public void setSm20(String sm20) {
		this.sm20 = sm20;
	}
	
	@Length(min=0, max=100, message="说明21长度必须介于 0 和 100 之间")
	public String getSm21() {
		return sm21;
	}

	public void setSm21(String sm21) {
		this.sm21 = sm21;
	}
	
	@Length(min=0, max=50, message="辐射人员长度必须介于 0 和 50 之间")
	public String getFsry() {
		return fsry;
	}

	public void setFsry(String fsry) {
		this.fsry = fsry;
	}
	
	public String getXzf29() {
		return xzf29;
	}

	public void setXzf29(String xzf29) {
		this.xzf29 = xzf29;
	}
	
	@Length(min=0, max=100, message="修正说明29长度必须介于 0 和 100 之间")
	public String getXzsm29() {
		return xzsm29;
	}

	public void setXzsm29(String xzsm29) {
		this.xzsm29 = xzsm29;
	}
	
	public String getDf29() {
		return df29;
	}

	public void setDf29(String df29) {
		this.df29 = df29;
	}
	
	@Length(min=0, max=100, message="sm22长度必须介于 0 和 100 之间")
	public String getSm22() {
		return sm22;
	}

	public void setSm22(String sm22) {
		this.sm22 = sm22;
	}
	
	@Length(min=0, max=100, message="sm23长度必须介于 0 和 100 之间")
	public String getSm23() {
		return sm23;
	}

	public void setSm23(String sm23) {
		this.sm23 = sm23;
	}
	
	@Length(min=0, max=100, message="说明24长度必须介于 0 和 100 之间")
	public String getSm24() {
		return sm24;
	}

	public void setSm24(String sm24) {
		this.sm24 = sm24;
	}
	
	@Length(min=0, max=100, message="说明29长度必须介于 0 和 100 之间")
	public String getSm29() {
		return sm29;
	}

	public void setSm29(String sm29) {
		this.sm29 = sm29;
	}
	
	@Length(min=0, max=100, message="医院名称长度必须介于 0 和 100 之间")
	public String getYymc() {
		return yymc;
	}

	public void setYymc(String yymc) {
		this.yymc = yymc;
	}
	
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
}