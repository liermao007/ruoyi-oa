/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 医院经营指标Entity
 * @author oa
 * @version 2018-06-26
 */
public class HospitalMasterIndex extends DataEntity<HospitalMasterIndex> {
	
	private static final long serialVersionUID = 1L;
	private String entryName;		// 项目名称
	private String orgId;		// 机构Id
    private String org;		// 机构id
    private String[] orgIds;//下属机构的总和
    private String masterId;  //主表id


    //旧月数据
    private Double groupCashBudgetaryAmountMonthsOld;		// 旧集团现金月预算金额
    private Double groupCashActualAmountMonthsOld;		// 旧集团现金月实际金额
    private Double medicalIncomeBudgetaryAmountMonthsOld;		// 旧医疗收入月预算金额
    private Double medicalIncomeActualAmountMonthsOld;		// 旧医疗收入月实际金额
    private Double costExpenditureBudgetaryAmountMonthsOld;		// 旧成本支出月预算金额
    private Double costExpenditureActualAmountMonthsOld;		// 旧成本支出月实际金额
    private Double profitBudgetaryAmountMonthsOld;		// 旧利润月预算金额
    private Double profitActualAmountMonthsOld;		// 旧利润月实际金额
    private Double operationIndexBudgetaryAmountMonthsOld;		// 旧现金运营指数月预算金额
    private Double operationIndexActualAmountMonthsOld;		// 旧现金运营指数月实际金额
    private Double purchasingRateBudgetaryAmountMonthsOld;		// 旧主渠道采购率月预算金额
    private Double purchasingRateActualAmountMonthsOld;		// 旧主渠道采购率月实际金额
    private Double qualityStandardBudgetaryAmountMonthsOld;		// 旧质量达标月预算金额
    private Double qualityStandardActualAmountMonthsOld;		// 旧质量达标月实际金额


    private Date month;		// 月
    private Double groupCashBudgetaryAmountMonths;		// 集团现金月预算金额
    private Double groupCashActualAmountMonths;		// 集团现金月实际金额
    private Double medicalIncomeBudgetaryAmountMonths;		// 医疗收入月预算金额
    private Double medicalIncomeActualAmountMonths;		// 医疗收入月实际金额
    private Double costExpenditureBudgetaryAmountMonths;		// 成本支出月预算金额
    private Double costExpenditureActualAmountMonths;		// 成本支出月实际金额
    private Double profitBudgetaryAmountMonths;		// 利润月预算金额
    private Double profitActualAmountMonths;		// 利润月实际金额
    private Double operationIndexBudgetaryAmountMonths;		// 现金运营指数月预算金额
    private Double operationIndexActualAmountMonths;		// 现金运营指数月实际金额
    private Double purchasingRateBudgetaryAmountMonths;		// 主渠道采购率月预算金额
    private Double purchasingRateActualAmountMonths;		// 主渠道采购率月实际金额
    private Double qualityStandardBudgetaryAmountMonths;		// 质量达标月预算金额
    private Double qualityStandardActualAmountMonths;		// 质量达标月实际金额

    private String quarter;		// 季度
    private String yearQuarter; //用于判断当前季度是哪一年的
    private Double groupCashBudgetaryAmountQuarter;		// 集团现金季度预算金额
    private Double groupCashActualAmountQuarter;		// 集团现金季度实际金额
    private Double medicalIncomeBudgetaryAmountQuarter;		// 医疗收入季度预算金额
    private Double medicalIncomeActualAmountQuarter;		// 医疗收入季度实际金额
    private Double costExpenditureBudgetaryAmountQuarter;		// 成本支出季度预算金额
    private Double costExpenditureActualAmountQuarter;		// 成本支出季度实际金额
    private Double profitBudgetaryAmountQuarter;		// 利润季度预算金额
    private Double profitActualAmountQuarter;		// 利润季度实际金额
    private Double operationIndexBudgetaryAmountQuarter;		// 现金运营指数季度预算金额
    private Double operationIndexActualAmountQuarter;		// 现金运营指数季度实际金额
    private Double purchasingRateBudgetaryAmountQuarter;		// 主渠道采购季度预算金额
    private Double purchasingRateActualAmountQuarter;		// 主渠道采购季度实际金额
    private Double qualityStandardBudgetaryAmountQuarter;		// 质量达标季度预算金额
    private Double qualityStandardActualAmountQuarter;		// 质量达标季度实际金额

    private Date year;		// 年
    private Double groupCashBudgetaryAmountYear;		// 集团现金年预算金额
    private Double groupCashActualAmountYear;		// 集团现金年实际金额
    private Double medicalIncomeBudgetaryAmountYear;		// 医疗收入年预算金额
    private Double medicalIncomeActualAmountYear;		// 医疗收入年实际金额
    private Double costExpenditureBudgetaryAmountYear;		// 成本支出年预算金额
    private Double costExpenditureActualAmountYear;		// 成本支出年实际金额
    private Double profitBudgetaryAmountYear;		// 利润年预算金额
    private Double profitActualAmountYear;		// 利润年实际金额
    private Double operationIndexBudgetaryAmountYear;		// 现金运营指数年预算金额
    private Double operationIndexActualAmountYear;		// 现金运营指数年实际金额
    private Double purchasingRateBudgetaryAmountYear;		// 主渠道采购率年预算金额
    private Double purchasingRateActualAmountYear;		// 主渠道采购率年实际金额
    private Double qualityStandardBudgetaryAmountYear;		// 质量达标年预算金额
    private Double qualityStandardActualAmountYear;		// 质量达标年实际金额
	
	public HospitalMasterIndex() {
		super();
	}

	public HospitalMasterIndex(String id){
		super(id);
	}

	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getYearQuarter() {
        return yearQuarter;
    }

    public void setYearQuarter(String yearQuarter) {
        this.yearQuarter = yearQuarter;
    }

    @Length(min=0, max=64, message="机构Id长度必须介于 0 和 64 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

    public Double getGroupCashBudgetaryAmountMonthsOld() {
        return groupCashBudgetaryAmountMonthsOld;
    }

    public void setGroupCashBudgetaryAmountMonthsOld(Double groupCashBudgetaryAmountMonthsOld) {
        this.groupCashBudgetaryAmountMonthsOld = groupCashBudgetaryAmountMonthsOld;
    }

    public Double getGroupCashActualAmountMonthsOld() {
        return groupCashActualAmountMonthsOld;
    }

    public void setGroupCashActualAmountMonthsOld(Double groupCashActualAmountMonthsOld) {
        this.groupCashActualAmountMonthsOld = groupCashActualAmountMonthsOld;
    }

    public Double getMedicalIncomeBudgetaryAmountMonthsOld() {
        return medicalIncomeBudgetaryAmountMonthsOld;
    }

    public void setMedicalIncomeBudgetaryAmountMonthsOld(Double medicalIncomeBudgetaryAmountMonthsOld) {
        this.medicalIncomeBudgetaryAmountMonthsOld = medicalIncomeBudgetaryAmountMonthsOld;
    }

    public Double getMedicalIncomeActualAmountMonthsOld() {
        return medicalIncomeActualAmountMonthsOld;
    }

    public void setMedicalIncomeActualAmountMonthsOld(Double medicalIncomeActualAmountMonthsOld) {
        this.medicalIncomeActualAmountMonthsOld = medicalIncomeActualAmountMonthsOld;
    }

    public Double getCostExpenditureBudgetaryAmountMonthsOld() {
        return costExpenditureBudgetaryAmountMonthsOld;
    }

    public void setCostExpenditureBudgetaryAmountMonthsOld(Double costExpenditureBudgetaryAmountMonthsOld) {
        this.costExpenditureBudgetaryAmountMonthsOld = costExpenditureBudgetaryAmountMonthsOld;
    }

    public Double getCostExpenditureActualAmountMonthsOld() {
        return costExpenditureActualAmountMonthsOld;
    }

    public void setCostExpenditureActualAmountMonthsOld(Double costExpenditureActualAmountMonthsOld) {
        this.costExpenditureActualAmountMonthsOld = costExpenditureActualAmountMonthsOld;
    }

    public Double getProfitBudgetaryAmountMonthsOld() {
        return profitBudgetaryAmountMonthsOld;
    }

    public void setProfitBudgetaryAmountMonthsOld(Double profitBudgetaryAmountMonthsOld) {
        this.profitBudgetaryAmountMonthsOld = profitBudgetaryAmountMonthsOld;
    }

    public Double getProfitActualAmountMonthsOld() {
        return profitActualAmountMonthsOld;
    }

    public void setProfitActualAmountMonthsOld(Double profitActualAmountMonthsOld) {
        this.profitActualAmountMonthsOld = profitActualAmountMonthsOld;
    }

    public Double getOperationIndexBudgetaryAmountMonthsOld() {
        return operationIndexBudgetaryAmountMonthsOld;
    }

    public void setOperationIndexBudgetaryAmountMonthsOld(Double operationIndexBudgetaryAmountMonthsOld) {
        this.operationIndexBudgetaryAmountMonthsOld = operationIndexBudgetaryAmountMonthsOld;
    }

    public Double getOperationIndexActualAmountMonthsOld() {
        return operationIndexActualAmountMonthsOld;
    }

    public void setOperationIndexActualAmountMonthsOld(Double operationIndexActualAmountMonthsOld) {
        this.operationIndexActualAmountMonthsOld = operationIndexActualAmountMonthsOld;
    }

    public Double getPurchasingRateBudgetaryAmountMonthsOld() {
        return purchasingRateBudgetaryAmountMonthsOld;
    }

    public void setPurchasingRateBudgetaryAmountMonthsOld(Double purchasingRateBudgetaryAmountMonthsOld) {
        this.purchasingRateBudgetaryAmountMonthsOld = purchasingRateBudgetaryAmountMonthsOld;
    }

    public Double getPurchasingRateActualAmountMonthsOld() {
        return purchasingRateActualAmountMonthsOld;
    }

    public void setPurchasingRateActualAmountMonthsOld(Double purchasingRateActualAmountMonthsOld) {
        this.purchasingRateActualAmountMonthsOld = purchasingRateActualAmountMonthsOld;
    }

    public Double getQualityStandardBudgetaryAmountMonthsOld() {
        return qualityStandardBudgetaryAmountMonthsOld;
    }

    public void setQualityStandardBudgetaryAmountMonthsOld(Double qualityStandardBudgetaryAmountMonthsOld) {
        this.qualityStandardBudgetaryAmountMonthsOld = qualityStandardBudgetaryAmountMonthsOld;
    }

    public Double getQualityStandardActualAmountMonthsOld() {
        return qualityStandardActualAmountMonthsOld;
    }

    public void setQualityStandardActualAmountMonthsOld(Double qualityStandardActualAmountMonthsOld) {
        this.qualityStandardActualAmountMonthsOld = qualityStandardActualAmountMonthsOld;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Double getGroupCashBudgetaryAmountMonths() {
        return groupCashBudgetaryAmountMonths;
    }

    public void setGroupCashBudgetaryAmountMonths(Double groupCashBudgetaryAmountMonths) {
        this.groupCashBudgetaryAmountMonths = groupCashBudgetaryAmountMonths;
    }

    public Double getGroupCashActualAmountMonths() {
        return groupCashActualAmountMonths;
    }

    public void setGroupCashActualAmountMonths(Double groupCashActualAmountMonths) {
        this.groupCashActualAmountMonths = groupCashActualAmountMonths;
    }

    public Double getMedicalIncomeBudgetaryAmountMonths() {
        return medicalIncomeBudgetaryAmountMonths;
    }

    public void setMedicalIncomeBudgetaryAmountMonths(Double medicalIncomeBudgetaryAmountMonths) {
        this.medicalIncomeBudgetaryAmountMonths = medicalIncomeBudgetaryAmountMonths;
    }

    public Double getMedicalIncomeActualAmountMonths() {
        return medicalIncomeActualAmountMonths;
    }

    public void setMedicalIncomeActualAmountMonths(Double medicalIncomeActualAmountMonths) {
        this.medicalIncomeActualAmountMonths = medicalIncomeActualAmountMonths;
    }

    public Double getCostExpenditureBudgetaryAmountMonths() {
        return costExpenditureBudgetaryAmountMonths;
    }

    public void setCostExpenditureBudgetaryAmountMonths(Double costExpenditureBudgetaryAmountMonths) {
        this.costExpenditureBudgetaryAmountMonths = costExpenditureBudgetaryAmountMonths;
    }

    public Double getCostExpenditureActualAmountMonths() {
        return costExpenditureActualAmountMonths;
    }

    public void setCostExpenditureActualAmountMonths(Double costExpenditureActualAmountMonths) {
        this.costExpenditureActualAmountMonths = costExpenditureActualAmountMonths;
    }

    public Double getProfitBudgetaryAmountMonths() {
        return profitBudgetaryAmountMonths;
    }

    public void setProfitBudgetaryAmountMonths(Double profitBudgetaryAmountMonths) {
        this.profitBudgetaryAmountMonths = profitBudgetaryAmountMonths;
    }

    public Double getProfitActualAmountMonths() {
        return profitActualAmountMonths;
    }

    public void setProfitActualAmountMonths(Double profitActualAmountMonths) {
        this.profitActualAmountMonths = profitActualAmountMonths;
    }

    public Double getOperationIndexBudgetaryAmountMonths() {
        return operationIndexBudgetaryAmountMonths;
    }

    public void setOperationIndexBudgetaryAmountMonths(Double operationIndexBudgetaryAmountMonths) {
        this.operationIndexBudgetaryAmountMonths = operationIndexBudgetaryAmountMonths;
    }

    public Double getOperationIndexActualAmountMonths() {
        return operationIndexActualAmountMonths;
    }

    public void setOperationIndexActualAmountMonths(Double operationIndexActualAmountMonths) {
        this.operationIndexActualAmountMonths = operationIndexActualAmountMonths;
    }

    public Double getPurchasingRateBudgetaryAmountMonths() {
        return purchasingRateBudgetaryAmountMonths;
    }

    public void setPurchasingRateBudgetaryAmountMonths(Double purchasingRateBudgetaryAmountMonths) {
        this.purchasingRateBudgetaryAmountMonths = purchasingRateBudgetaryAmountMonths;
    }

    public Double getPurchasingRateActualAmountMonths() {
        return purchasingRateActualAmountMonths;
    }

    public void setPurchasingRateActualAmountMonths(Double purchasingRateActualAmountMonths) {
        this.purchasingRateActualAmountMonths = purchasingRateActualAmountMonths;
    }

    public Double getQualityStandardBudgetaryAmountMonths() {
        return qualityStandardBudgetaryAmountMonths;
    }

    public void setQualityStandardBudgetaryAmountMonths(Double qualityStandardBudgetaryAmountMonths) {
        this.qualityStandardBudgetaryAmountMonths = qualityStandardBudgetaryAmountMonths;
    }

    public Double getQualityStandardActualAmountMonths() {
        return qualityStandardActualAmountMonths;
    }

    public void setQualityStandardActualAmountMonths(Double qualityStandardActualAmountMonths) {
        this.qualityStandardActualAmountMonths = qualityStandardActualAmountMonths;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Double getGroupCashBudgetaryAmountQuarter() {
        return groupCashBudgetaryAmountQuarter;
    }

    public void setGroupCashBudgetaryAmountQuarter(Double groupCashBudgetaryAmountQuarter) {
        this.groupCashBudgetaryAmountQuarter = groupCashBudgetaryAmountQuarter;
    }

    public Double getGroupCashActualAmountQuarter() {
        return groupCashActualAmountQuarter;
    }

    public void setGroupCashActualAmountQuarter(Double groupCashActualAmountQuarter) {
        this.groupCashActualAmountQuarter = groupCashActualAmountQuarter;
    }

    public Double getMedicalIncomeBudgetaryAmountQuarter() {
        return medicalIncomeBudgetaryAmountQuarter;
    }

    public void setMedicalIncomeBudgetaryAmountQuarter(Double medicalIncomeBudgetaryAmountQuarter) {
        this.medicalIncomeBudgetaryAmountQuarter = medicalIncomeBudgetaryAmountQuarter;
    }

    public Double getMedicalIncomeActualAmountQuarter() {
        return medicalIncomeActualAmountQuarter;
    }

    public void setMedicalIncomeActualAmountQuarter(Double medicalIncomeActualAmountQuarter) {
        this.medicalIncomeActualAmountQuarter = medicalIncomeActualAmountQuarter;
    }

    public Double getCostExpenditureBudgetaryAmountQuarter() {
        return costExpenditureBudgetaryAmountQuarter;
    }

    public void setCostExpenditureBudgetaryAmountQuarter(Double costExpenditureBudgetaryAmountQuarter) {
        this.costExpenditureBudgetaryAmountQuarter = costExpenditureBudgetaryAmountQuarter;
    }

    public Double getCostExpenditureActualAmountQuarter() {
        return costExpenditureActualAmountQuarter;
    }

    public void setCostExpenditureActualAmountQuarter(Double costExpenditureActualAmountQuarter) {
        this.costExpenditureActualAmountQuarter = costExpenditureActualAmountQuarter;
    }

    public Double getProfitBudgetaryAmountQuarter() {
        return profitBudgetaryAmountQuarter;
    }

    public void setProfitBudgetaryAmountQuarter(Double profitBudgetaryAmountQuarter) {
        this.profitBudgetaryAmountQuarter = profitBudgetaryAmountQuarter;
    }

    public Double getProfitActualAmountQuarter() {
        return profitActualAmountQuarter;
    }

    public void setProfitActualAmountQuarter(Double profitActualAmountQuarter) {
        this.profitActualAmountQuarter = profitActualAmountQuarter;
    }

    public Double getOperationIndexBudgetaryAmountQuarter() {
        return operationIndexBudgetaryAmountQuarter;
    }

    public void setOperationIndexBudgetaryAmountQuarter(Double operationIndexBudgetaryAmountQuarter) {
        this.operationIndexBudgetaryAmountQuarter = operationIndexBudgetaryAmountQuarter;
    }

    public Double getOperationIndexActualAmountQuarter() {
        return operationIndexActualAmountQuarter;
    }

    public void setOperationIndexActualAmountQuarter(Double operationIndexActualAmountQuarter) {
        this.operationIndexActualAmountQuarter = operationIndexActualAmountQuarter;
    }

    public Double getPurchasingRateBudgetaryAmountQuarter() {
        return purchasingRateBudgetaryAmountQuarter;
    }

    public void setPurchasingRateBudgetaryAmountQuarter(Double purchasingRateBudgetaryAmountQuarter) {
        this.purchasingRateBudgetaryAmountQuarter = purchasingRateBudgetaryAmountQuarter;
    }

    public Double getPurchasingRateActualAmountQuarter() {
        return purchasingRateActualAmountQuarter;
    }

    public void setPurchasingRateActualAmountQuarter(Double purchasingRateActualAmountQuarter) {
        this.purchasingRateActualAmountQuarter = purchasingRateActualAmountQuarter;
    }

    public Double getQualityStandardBudgetaryAmountQuarter() {
        return qualityStandardBudgetaryAmountQuarter;
    }

    public void setQualityStandardBudgetaryAmountQuarter(Double qualityStandardBudgetaryAmountQuarter) {
        this.qualityStandardBudgetaryAmountQuarter = qualityStandardBudgetaryAmountQuarter;
    }

    public Double getQualityStandardActualAmountQuarter() {
        return qualityStandardActualAmountQuarter;
    }

    public void setQualityStandardActualAmountQuarter(Double qualityStandardActualAmountQuarter) {
        this.qualityStandardActualAmountQuarter = qualityStandardActualAmountQuarter;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Double getGroupCashBudgetaryAmountYear() {
        return groupCashBudgetaryAmountYear;
    }

    public void setGroupCashBudgetaryAmountYear(Double groupCashBudgetaryAmountYear) {
        this.groupCashBudgetaryAmountYear = groupCashBudgetaryAmountYear;
    }

    public Double getGroupCashActualAmountYear() {
        return groupCashActualAmountYear;
    }

    public void setGroupCashActualAmountYear(Double groupCashActualAmountYear) {
        this.groupCashActualAmountYear = groupCashActualAmountYear;
    }

    public Double getMedicalIncomeBudgetaryAmountYear() {
        return medicalIncomeBudgetaryAmountYear;
    }

    public void setMedicalIncomeBudgetaryAmountYear(Double medicalIncomeBudgetaryAmountYear) {
        this.medicalIncomeBudgetaryAmountYear = medicalIncomeBudgetaryAmountYear;
    }

    public Double getMedicalIncomeActualAmountYear() {
        return medicalIncomeActualAmountYear;
    }

    public void setMedicalIncomeActualAmountYear(Double medicalIncomeActualAmountYear) {
        this.medicalIncomeActualAmountYear = medicalIncomeActualAmountYear;
    }

    public Double getCostExpenditureBudgetaryAmountYear() {
        return costExpenditureBudgetaryAmountYear;
    }

    public void setCostExpenditureBudgetaryAmountYear(Double costExpenditureBudgetaryAmountYear) {
        this.costExpenditureBudgetaryAmountYear = costExpenditureBudgetaryAmountYear;
    }

    public Double getCostExpenditureActualAmountYear() {
        return costExpenditureActualAmountYear;
    }

    public void setCostExpenditureActualAmountYear(Double costExpenditureActualAmountYear) {
        this.costExpenditureActualAmountYear = costExpenditureActualAmountYear;
    }

    public Double getProfitBudgetaryAmountYear() {
        return profitBudgetaryAmountYear;
    }

    public void setProfitBudgetaryAmountYear(Double profitBudgetaryAmountYear) {
        this.profitBudgetaryAmountYear = profitBudgetaryAmountYear;
    }

    public Double getProfitActualAmountYear() {
        return profitActualAmountYear;
    }

    public void setProfitActualAmountYear(Double profitActualAmountYear) {
        this.profitActualAmountYear = profitActualAmountYear;
    }

    public Double getOperationIndexBudgetaryAmountYear() {
        return operationIndexBudgetaryAmountYear;
    }

    public void setOperationIndexBudgetaryAmountYear(Double operationIndexBudgetaryAmountYear) {
        this.operationIndexBudgetaryAmountYear = operationIndexBudgetaryAmountYear;
    }

    public Double getOperationIndexActualAmountYear() {
        return operationIndexActualAmountYear;
    }

    public void setOperationIndexActualAmountYear(Double operationIndexActualAmountYear) {
        this.operationIndexActualAmountYear = operationIndexActualAmountYear;
    }

    public Double getPurchasingRateBudgetaryAmountYear() {
        return purchasingRateBudgetaryAmountYear;
    }

    public void setPurchasingRateBudgetaryAmountYear(Double purchasingRateBudgetaryAmountYear) {
        this.purchasingRateBudgetaryAmountYear = purchasingRateBudgetaryAmountYear;
    }

    public Double getPurchasingRateActualAmountYear() {
        return purchasingRateActualAmountYear;
    }

    public void setPurchasingRateActualAmountYear(Double purchasingRateActualAmountYear) {
        this.purchasingRateActualAmountYear = purchasingRateActualAmountYear;
    }

    public Double getQualityStandardBudgetaryAmountYear() {
        return qualityStandardBudgetaryAmountYear;
    }

    public void setQualityStandardBudgetaryAmountYear(Double qualityStandardBudgetaryAmountYear) {
        this.qualityStandardBudgetaryAmountYear = qualityStandardBudgetaryAmountYear;
    }

    public Double getQualityStandardActualAmountYear() {
        return qualityStandardActualAmountYear;
    }

    public void setQualityStandardActualAmountYear(Double qualityStandardActualAmountYear) {
        this.qualityStandardActualAmountYear = qualityStandardActualAmountYear;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String[] orgIds) {
        this.orgIds = orgIds;
    }
}