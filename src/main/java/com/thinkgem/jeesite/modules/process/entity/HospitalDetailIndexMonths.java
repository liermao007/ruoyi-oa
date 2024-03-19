/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 医院经营指标月表Entity
 * @author oa
 * @version 2018-06-26
 */
public class HospitalDetailIndexMonths extends DataEntity<HospitalDetailIndexMonths> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构id
	private String masterId;		// 主表id
	private String month;		// 月
	private String groupCashBudgetaryAmountMonths;		// 集团现金月预算金额
	private String groupCashActualAmountMonths;		// 集团现金月实际金额
	private String medicalIncomeBudgetaryAmountMonths;		// 医疗收入月预算金额
	private String medicalIncomeActualAmountMonths;		// 医疗收入月实际金额
	private String costExpenditureBudgetaryAmountMonths;		// 成本支出月预算金额
	private String costExpenditureActualAmountMonths;		// 成本支出月实际金额
	private String profitBudgetaryAmountMonths;		// 利润月预算金额
	private String profitActualAmountMonths;		// 利润月实际金额
	private String operationIndexBudgetaryAmountMonths;		// 现金运营指数月预算金额
	private String operationIndexActualAmountMonths;		// 现金运营指数月实际金额
	private String purchasingRateBudgetaryAmountMonths;		// 主渠道采购率月预算金额
	private String purchasingRateActualAmountMonths;		// 主渠道采购率月实际金额
	private String qualityStandardBudgetaryAmountMonths;		// 质量达标月预算金额
	private String qualityStandardActualAmountMonths;		// 质量达标月实际金额
	
	public HospitalDetailIndexMonths() {
		super();
	}

	public HospitalDetailIndexMonths(String id){
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
	
	@Length(min=0, max=100, message="月长度必须介于 0 和 100 之间")
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getGroupCashBudgetaryAmountMonths() {
		return groupCashBudgetaryAmountMonths;
	}

	public void setGroupCashBudgetaryAmountMonths(String groupCashBudgetaryAmountMonths) {
		this.groupCashBudgetaryAmountMonths = groupCashBudgetaryAmountMonths;
	}
	
	public String getGroupCashActualAmountMonths() {
		return groupCashActualAmountMonths;
	}

	public void setGroupCashActualAmountMonths(String groupCashActualAmountMonths) {
		this.groupCashActualAmountMonths = groupCashActualAmountMonths;
	}
	
	public String getMedicalIncomeBudgetaryAmountMonths() {
		return medicalIncomeBudgetaryAmountMonths;
	}

	public void setMedicalIncomeBudgetaryAmountMonths(String medicalIncomeBudgetaryAmountMonths) {
		this.medicalIncomeBudgetaryAmountMonths = medicalIncomeBudgetaryAmountMonths;
	}
	
	public String getMedicalIncomeActualAmountMonths() {
		return medicalIncomeActualAmountMonths;
	}

	public void setMedicalIncomeActualAmountMonths(String medicalIncomeActualAmountMonths) {
		this.medicalIncomeActualAmountMonths = medicalIncomeActualAmountMonths;
	}
	
	public String getCostExpenditureBudgetaryAmountMonths() {
		return costExpenditureBudgetaryAmountMonths;
	}

	public void setCostExpenditureBudgetaryAmountMonths(String costExpenditureBudgetaryAmountMonths) {
		this.costExpenditureBudgetaryAmountMonths = costExpenditureBudgetaryAmountMonths;
	}
	
	public String getCostExpenditureActualAmountMonths() {
		return costExpenditureActualAmountMonths;
	}

	public void setCostExpenditureActualAmountMonths(String costExpenditureActualAmountMonths) {
		this.costExpenditureActualAmountMonths = costExpenditureActualAmountMonths;
	}
	
	public String getProfitBudgetaryAmountMonths() {
		return profitBudgetaryAmountMonths;
	}

	public void setProfitBudgetaryAmountMonths(String profitBudgetaryAmountMonths) {
		this.profitBudgetaryAmountMonths = profitBudgetaryAmountMonths;
	}
	
	public String getProfitActualAmountMonths() {
		return profitActualAmountMonths;
	}

	public void setProfitActualAmountMonths(String profitActualAmountMonths) {
		this.profitActualAmountMonths = profitActualAmountMonths;
	}
	
	public String getOperationIndexBudgetaryAmountMonths() {
		return operationIndexBudgetaryAmountMonths;
	}

	public void setOperationIndexBudgetaryAmountMonths(String operationIndexBudgetaryAmountMonths) {
		this.operationIndexBudgetaryAmountMonths = operationIndexBudgetaryAmountMonths;
	}
	
	public String getOperationIndexActualAmountMonths() {
		return operationIndexActualAmountMonths;
	}

	public void setOperationIndexActualAmountMonths(String operationIndexActualAmountMonths) {
		this.operationIndexActualAmountMonths = operationIndexActualAmountMonths;
	}
	
	public String getPurchasingRateBudgetaryAmountMonths() {
		return purchasingRateBudgetaryAmountMonths;
	}

	public void setPurchasingRateBudgetaryAmountMonths(String purchasingRateBudgetaryAmountMonths) {
		this.purchasingRateBudgetaryAmountMonths = purchasingRateBudgetaryAmountMonths;
	}
	
	public String getPurchasingRateActualAmountMonths() {
		return purchasingRateActualAmountMonths;
	}

	public void setPurchasingRateActualAmountMonths(String purchasingRateActualAmountMonths) {
		this.purchasingRateActualAmountMonths = purchasingRateActualAmountMonths;
	}
	
	public String getQualityStandardBudgetaryAmountMonths() {
		return qualityStandardBudgetaryAmountMonths;
	}

	public void setQualityStandardBudgetaryAmountMonths(String qualityStandardBudgetaryAmountMonths) {
		this.qualityStandardBudgetaryAmountMonths = qualityStandardBudgetaryAmountMonths;
	}
	
	public String getQualityStandardActualAmountMonths() {
		return qualityStandardActualAmountMonths;
	}

	public void setQualityStandardActualAmountMonths(String qualityStandardActualAmountMonths) {
		this.qualityStandardActualAmountMonths = qualityStandardActualAmountMonths;
	}
	
}