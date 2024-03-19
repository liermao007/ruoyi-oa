/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 医院经营指标年表Entity
 * @author oa
 * @version 2018-06-26
 */
public class HospitalDetailIndexYear extends DataEntity<HospitalDetailIndexYear> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构id
	private String masterId;		// 主表id
	private String year;		// 年
	private String groupCashBudgetaryAmountYear;		// 集团现金年预算金额
	private String groupCashActualAmountYear;		// 集团现金年实际金额
	private String medicalIncomeBudgetaryAmountYear;		// 医疗收入年预算金额
	private String medicalIncomeActualAmountYear;		// 医疗收入年实际金额
	private String costExpenditureBudgetaryAmountYear;		// 成本支出年预算金额
	private String costExpenditureActualAmountYear;		// 成本支出年实际金额
	private String profitBudgetaryAmountYear;		// 利润年预算金额
	private String profitActualAmountYear;		// 利润年实际金额
	private String operationIndexBudgetaryAmountYear;		// 现金运营指数年预算金额
	private String operationIndexActualAmountYear;		// 现金运营指数年实际金额
	private String purchasingRateBudgetaryAmountYear;		// 主渠道采购率年预算金额
	private String purchasingRateActualAmountYear;		// 主渠道采购率年实际金额
	private String qualityStandardBudgetaryAmountYear;		// 质量达标年预算金额
	private String qualityStandardActualAmountYear;		// 质量达标年实际金额
	
	public HospitalDetailIndexYear() {
		super();
	}

	public HospitalDetailIndexYear(String id){
		super(id);
	}

	@Length(min=0, max=64, message="机构id长度必须介于 0 和 64 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=0, max=64, message="主表id长度必须介于 0 和 64 之间")
	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	
	@Length(min=0, max=100, message="年长度必须介于 0 和 100 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getGroupCashBudgetaryAmountYear() {
		return groupCashBudgetaryAmountYear;
	}

	public void setGroupCashBudgetaryAmountYear(String groupCashBudgetaryAmountYear) {
		this.groupCashBudgetaryAmountYear = groupCashBudgetaryAmountYear;
	}
	
	public String getGroupCashActualAmountYear() {
		return groupCashActualAmountYear;
	}

	public void setGroupCashActualAmountYear(String groupCashActualAmountYear) {
		this.groupCashActualAmountYear = groupCashActualAmountYear;
	}
	
	public String getMedicalIncomeBudgetaryAmountYear() {
		return medicalIncomeBudgetaryAmountYear;
	}

	public void setMedicalIncomeBudgetaryAmountYear(String medicalIncomeBudgetaryAmountYear) {
		this.medicalIncomeBudgetaryAmountYear = medicalIncomeBudgetaryAmountYear;
	}
	
	public String getMedicalIncomeActualAmountYear() {
		return medicalIncomeActualAmountYear;
	}

	public void setMedicalIncomeActualAmountYear(String medicalIncomeActualAmountYear) {
		this.medicalIncomeActualAmountYear = medicalIncomeActualAmountYear;
	}
	
	public String getCostExpenditureBudgetaryAmountYear() {
		return costExpenditureBudgetaryAmountYear;
	}

	public void setCostExpenditureBudgetaryAmountYear(String costExpenditureBudgetaryAmountYear) {
		this.costExpenditureBudgetaryAmountYear = costExpenditureBudgetaryAmountYear;
	}
	
	public String getCostExpenditureActualAmountYear() {
		return costExpenditureActualAmountYear;
	}

	public void setCostExpenditureActualAmountYear(String costExpenditureActualAmountYear) {
		this.costExpenditureActualAmountYear = costExpenditureActualAmountYear;
	}
	
	public String getProfitBudgetaryAmountYear() {
		return profitBudgetaryAmountYear;
	}

	public void setProfitBudgetaryAmountYear(String profitBudgetaryAmountYear) {
		this.profitBudgetaryAmountYear = profitBudgetaryAmountYear;
	}
	
	public String getProfitActualAmountYear() {
		return profitActualAmountYear;
	}

	public void setProfitActualAmountYear(String profitActualAmountYear) {
		this.profitActualAmountYear = profitActualAmountYear;
	}
	
	public String getOperationIndexBudgetaryAmountYear() {
		return operationIndexBudgetaryAmountYear;
	}

	public void setOperationIndexBudgetaryAmountYear(String operationIndexBudgetaryAmountYear) {
		this.operationIndexBudgetaryAmountYear = operationIndexBudgetaryAmountYear;
	}
	
	public String getOperationIndexActualAmountYear() {
		return operationIndexActualAmountYear;
	}

	public void setOperationIndexActualAmountYear(String operationIndexActualAmountYear) {
		this.operationIndexActualAmountYear = operationIndexActualAmountYear;
	}
	
	public String getPurchasingRateBudgetaryAmountYear() {
		return purchasingRateBudgetaryAmountYear;
	}

	public void setPurchasingRateBudgetaryAmountYear(String purchasingRateBudgetaryAmountYear) {
		this.purchasingRateBudgetaryAmountYear = purchasingRateBudgetaryAmountYear;
	}
	
	public String getPurchasingRateActualAmountYear() {
		return purchasingRateActualAmountYear;
	}

	public void setPurchasingRateActualAmountYear(String purchasingRateActualAmountYear) {
		this.purchasingRateActualAmountYear = purchasingRateActualAmountYear;
	}
	
	public String getQualityStandardBudgetaryAmountYear() {
		return qualityStandardBudgetaryAmountYear;
	}

	public void setQualityStandardBudgetaryAmountYear(String qualityStandardBudgetaryAmountYear) {
		this.qualityStandardBudgetaryAmountYear = qualityStandardBudgetaryAmountYear;
	}
	
	public String getQualityStandardActualAmountYear() {
		return qualityStandardActualAmountYear;
	}

	public void setQualityStandardActualAmountYear(String qualityStandardActualAmountYear) {
		this.qualityStandardActualAmountYear = qualityStandardActualAmountYear;
	}
	
}