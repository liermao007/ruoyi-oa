/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 医院经营指标季度表Entity
 * @author oa
 * @version 2018-06-26
 */
public class HospitalDetailIndexQuarter extends DataEntity<HospitalDetailIndexQuarter> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构id
	private String masterId;		// 主表id
	private String quarter;		// 季度
	private String groupCashBudgetaryAmountQuarter;		// 集团现金季度预算金额
	private String groupCashActualAmountQuarter;		// 集团现金季度实际金额
	private String medicalIncomeBudgetaryAmountQuarter;		// 医疗收入季度预算金额
	private String medicalIncomeActualAmountQuarter;		// 医疗收入季度实际金额
	private String costExpenditureBudgetaryAmountQuarter;		// 成本支出季度预算金额
	private String costExpenditureActualAmountQuarter;		// 成本支出季度实际金额
	private String profitBudgetaryAmountQuarter;		// 利润季度预算金额
	private String profitActualAmountQuarter;		// 利润季度实际金额
	private String operationIndexBudgetaryAmountQuarter;		// 现金运营指数季度预算金额
	private String operationIndexActualAmountQuarter;		// 现金运营指数季度实际金额
	private String purchasingRateBudgetaryAmountQuarter;		// 主渠道采购季度预算金额
	private String purchasingRateActualAmountQuarter;		// 主渠道采购季度实际金额
	private String qualityStandardBudgetaryAmountQuarter;		// 质量达标季度预算金额
	private String qualityStandardActualAmountQuarter;		// 质量达标季度实际金额
	
	public HospitalDetailIndexQuarter() {
		super();
	}

	public HospitalDetailIndexQuarter(String id){
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
	
	@Length(min=0, max=100, message="季度长度必须介于 0 和 100 之间")
	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	
	public String getGroupCashBudgetaryAmountQuarter() {
		return groupCashBudgetaryAmountQuarter;
	}

	public void setGroupCashBudgetaryAmountQuarter(String groupCashBudgetaryAmountQuarter) {
		this.groupCashBudgetaryAmountQuarter = groupCashBudgetaryAmountQuarter;
	}
	
	public String getGroupCashActualAmountQuarter() {
		return groupCashActualAmountQuarter;
	}

	public void setGroupCashActualAmountQuarter(String groupCashActualAmountQuarter) {
		this.groupCashActualAmountQuarter = groupCashActualAmountQuarter;
	}
	
	public String getMedicalIncomeBudgetaryAmountQuarter() {
		return medicalIncomeBudgetaryAmountQuarter;
	}

	public void setMedicalIncomeBudgetaryAmountQuarter(String medicalIncomeBudgetaryAmountQuarter) {
		this.medicalIncomeBudgetaryAmountQuarter = medicalIncomeBudgetaryAmountQuarter;
	}
	
	public String getMedicalIncomeActualAmountQuarter() {
		return medicalIncomeActualAmountQuarter;
	}

	public void setMedicalIncomeActualAmountQuarter(String medicalIncomeActualAmountQuarter) {
		this.medicalIncomeActualAmountQuarter = medicalIncomeActualAmountQuarter;
	}
	
	public String getCostExpenditureBudgetaryAmountQuarter() {
		return costExpenditureBudgetaryAmountQuarter;
	}

	public void setCostExpenditureBudgetaryAmountQuarter(String costExpenditureBudgetaryAmountQuarter) {
		this.costExpenditureBudgetaryAmountQuarter = costExpenditureBudgetaryAmountQuarter;
	}
	
	public String getCostExpenditureActualAmountQuarter() {
		return costExpenditureActualAmountQuarter;
	}

	public void setCostExpenditureActualAmountQuarter(String costExpenditureActualAmountQuarter) {
		this.costExpenditureActualAmountQuarter = costExpenditureActualAmountQuarter;
	}
	
	public String getProfitBudgetaryAmountQuarter() {
		return profitBudgetaryAmountQuarter;
	}

	public void setProfitBudgetaryAmountQuarter(String profitBudgetaryAmountQuarter) {
		this.profitBudgetaryAmountQuarter = profitBudgetaryAmountQuarter;
	}
	
	public String getProfitActualAmountQuarter() {
		return profitActualAmountQuarter;
	}

	public void setProfitActualAmountQuarter(String profitActualAmountQuarter) {
		this.profitActualAmountQuarter = profitActualAmountQuarter;
	}
	
	public String getOperationIndexBudgetaryAmountQuarter() {
		return operationIndexBudgetaryAmountQuarter;
	}

	public void setOperationIndexBudgetaryAmountQuarter(String operationIndexBudgetaryAmountQuarter) {
		this.operationIndexBudgetaryAmountQuarter = operationIndexBudgetaryAmountQuarter;
	}
	
	public String getOperationIndexActualAmountQuarter() {
		return operationIndexActualAmountQuarter;
	}

	public void setOperationIndexActualAmountQuarter(String operationIndexActualAmountQuarter) {
		this.operationIndexActualAmountQuarter = operationIndexActualAmountQuarter;
	}
	
	public String getPurchasingRateBudgetaryAmountQuarter() {
		return purchasingRateBudgetaryAmountQuarter;
	}

	public void setPurchasingRateBudgetaryAmountQuarter(String purchasingRateBudgetaryAmountQuarter) {
		this.purchasingRateBudgetaryAmountQuarter = purchasingRateBudgetaryAmountQuarter;
	}
	
	public String getPurchasingRateActualAmountQuarter() {
		return purchasingRateActualAmountQuarter;
	}

	public void setPurchasingRateActualAmountQuarter(String purchasingRateActualAmountQuarter) {
		this.purchasingRateActualAmountQuarter = purchasingRateActualAmountQuarter;
	}
	
	public String getQualityStandardBudgetaryAmountQuarter() {
		return qualityStandardBudgetaryAmountQuarter;
	}

	public void setQualityStandardBudgetaryAmountQuarter(String qualityStandardBudgetaryAmountQuarter) {
		this.qualityStandardBudgetaryAmountQuarter = qualityStandardBudgetaryAmountQuarter;
	}
	
	public String getQualityStandardActualAmountQuarter() {
		return qualityStandardActualAmountQuarter;
	}

	public void setQualityStandardActualAmountQuarter(String qualityStandardActualAmountQuarter) {
		this.qualityStandardActualAmountQuarter = qualityStandardActualAmountQuarter;
	}
	
}