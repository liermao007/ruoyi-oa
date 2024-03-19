/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 医疗运营报表Entity
 * @author oa
 * @version 2018-01-08
 */
public class MedicalOperationReport extends DataEntity<MedicalOperationReport> {
	
	private static final long serialVersionUID = 1L;
	private Double dailyIncome;		// 日收入
	private Double dailyOutpatientClinic;		// 日门诊收入
	private Double dailyHospitalization;		// 日住院收入
	private int diurnalUrgency;		// 日门急
	private int dailyOutpatientNumber;		// 日门诊人次
	private int dailyEmergency;		// 日急诊人次
	private int admissionNumber;		// 入院人数
	private int dischargeNumber;		// 出院人数
	private int hospitalPeople;		// 在院人数
	private int operationNumber;		// 日手术台次
	private int diurnalOutpatientOperation;		// 日门诊手术
	private int dailyOperation;		// 日住院手术
	private int doctorsNumber;		// 医生人数
    private Double outpatientVolumeAverage;		// 医均门诊量
	private int bedDay;		// 医均占床日
    private Double totalMedicalIncome;		// 医均总收入
    private int nursesNumber;		// 护士人数
    private int nursesBedDay;		// 护均占床日
    private Double nursesMedicalIncome;		// 护均总收入
    private int medicalPeopleNumber;		// 医技人数
    private Double medicalDepartmentTotalIncome;		// 医技科人均总收入
    private int monthBedDay;		// 月占床日


    private int totalNumHospital;   //医院总人数
    private int doctorNum;          //医生人数
    private Double totalCostHospital;          //医院总成本
    private Double totalCostPeople;          //人力资源总成本
    private int medicalNum ;          //医药护技人数
    private int logisticNum ;          //行政后勤人数

    //大型设备检查人次
    private int ctCheck;  //CT检查人次
    private int ctDoctor;  //CT医生人数
    private int ultrasonicCheck;  //超声检查人次
    private int ultrasonicDoctor;  //超声医生人数
    private int xCheck;   //X射线检查人次
    private int xDoctor;  //X射线医生人数
    private  int nmrCheck;  //核磁检查人次
    private int nmrDoctor;  //核磁医生人数

    //人数
    private int nurseNum;  //护士人数
    private int medicalAuxiliaryNum;  //医辅人数
    private int medicalNumber;  //医技人数
    private int logisticalNumber; //后勤人数
    private int administrativeNumber; //行政人数


    //旧数据
    private Double dailyIncomeOld;		// 旧日收入
    private Double dailyOutpatientClinicOld;		// 旧日门诊收入
    private Double dailyHospitalizationOld;		// 旧日住院收入
    private int diurnalUrgencyOld;		// 旧日门急
    private int dailyOutpatientNumberOld;		// 旧日门诊人次
    private int dailyEmergencyOld;		// 旧日急诊人次
    private int admissionNumberOld;		// 旧入院人数
    private int dischargeNumberOld;		// 旧出院人数
    private int hospitalPeopleOld;		// 旧在院人数
    private int operationNumberOld;		// 旧日手术台次
    private int diurnalOutpatientOperationOld;		//旧 日门诊手术
    private int dailyOperationOld;		// 旧日住院手术
    private int doctorsNumberOld;		// 旧医生人数
    private int bedDayOld;		// 旧医均占床日
    private Double outpatientVolumeAverageOld;		// 旧医均门诊量
    private Double totalMedicalIncomeOld;		// 旧医均总收入
    private int nursesNumberOld;		// 旧护士人数
    private int nursesBedDayOld;		// 旧护均占床日
    private Double nursesMedicalIncomeOld;		// 旧护均总收入
    private int medicalPeopleNumberOld;		// 旧医技人数
    private Double medicalDepartmentTotalIncomeOld;		// 旧医技科人均总收入
    private int monthBedDayOld;		// 旧月占床日

    private int totalNumHospitalOld;   //旧医院总人数
    private int doctorNumOld;          //旧医生人数
    private Double totalCostHospitalOld;          //旧医院总成本
    private Double totalCostPeopleOld;          //旧人力资源总成本
    private int medicalNumOld;          //旧医药护技人数

    //月年
    private Double dailyIncomeMonths;		// 月收入
    private Double dailyOutpatientClinicMonths;		// 月门诊收入
    private Double dailyHospitalizationMonths;		// 月住院收入
    private int diurnalUrgencyMonths;		// 月门急人次
    private int dailyOutpatientNumberMonths;		// 月门诊人次
    private int dailyEmergencyMonths;		// 月急诊人次
    private int admissionNumberMonths;		// 月入院人数
    private int dischargeNumberMonths;		// 月出院人数
    private int hospitalPeopleMonths;		// 月占床日（用在月表中）
    private int operationNumberMonths;		// 月手术台次
    private int diurnalOutpatientOperationMonths;		// 月门诊手术
    private int dailyOperationMonths;		// 月住院手术

    private Double dailyIncomeYear;		// 年收入
    private Double dailyOutpatientClinicYear;		// 年门诊收入
    private Double dailyHospitalizationYear;		// 年住院收入
    private int diurnalUrgencyYear;		// 年门急人次
    private int dailyOutpatientNumberYear;		// 年门诊人次
    private int dailyEmergencyYear;		// 年急诊人次
    private int admissionNumberYear;		// 年入院人数
    private int dischargeNumberYear;		// 年出院人数
    private int hospitalPeopleYear;		// 年占床日
    private int operationNumberYear;		// 年手术台次
    private int diurnalOutpatientOperationYear;		// 年门诊手术
    private int dailyOperationYear;		// 年住院手术

	private Date currentSumDate;		// 当前日期
	private Date currentMonths;		// 当前月份
	private Date currentYear;		// 当前年份
	private String orgId;		// 机构id
	private String org;		// 机构id
    private String[] orgIds;//下属机构的总和
	
	public MedicalOperationReport() {
		super();
	}

	public MedicalOperationReport(String id){
		super(id);
	}


    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Date getCurrentSumDate() {
        return currentSumDate;
    }

    public void setCurrentSumDate(Date currentSumDate) {
        this.currentSumDate = currentSumDate;
    }

    @Length(min=0, max=64, message="机构id长度必须介于 0 和 64 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}






    public Date getCurrentMonths() {
        return currentMonths;
    }

    public void setCurrentMonths(Date currentMonths) {
        this.currentMonths = currentMonths;
    }


    public int getNurseNum() {
        return nurseNum;
    }

    public void setNurseNum(int nurseNum) {
        this.nurseNum = nurseNum;
    }

    public int getMedicalAuxiliaryNum() {
        return medicalAuxiliaryNum;
    }

    public void setMedicalAuxiliaryNum(int medicalAuxiliaryNum) {
        this.medicalAuxiliaryNum = medicalAuxiliaryNum;
    }

    public int getMedicalNumber() {
        return medicalNumber;
    }

    public void setMedicalNumber(int medicalNumber) {
        this.medicalNumber = medicalNumber;
    }

    public int getLogisticalNumber() {
        return logisticalNumber;
    }

    public void setLogisticalNumber(int logisticalNumber) {
        this.logisticalNumber = logisticalNumber;
    }

    public int getAdministrativeNumber() {
        return administrativeNumber;
    }

    public void setAdministrativeNumber(int administrativeNumber) {
        this.administrativeNumber = administrativeNumber;
    }

    public Date getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Date currentYear) {
        this.currentYear = currentYear;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(Double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public Double getDailyOutpatientClinic() {
        return dailyOutpatientClinic;
    }

    public void setDailyOutpatientClinic(Double dailyOutpatientClinic) {
        this.dailyOutpatientClinic = dailyOutpatientClinic;
    }

    public Double getDailyHospitalization() {
        return dailyHospitalization;
    }

    public void setDailyHospitalization(Double dailyHospitalization) {
        this.dailyHospitalization = dailyHospitalization;
    }

    public Double getTotalMedicalIncome() {
        return totalMedicalIncome;
    }

    public void setTotalMedicalIncome(Double totalMedicalIncome) {
        this.totalMedicalIncome = totalMedicalIncome;
    }

    public Double getNursesMedicalIncome() {
        return nursesMedicalIncome;
    }

    public void setNursesMedicalIncome(Double nursesMedicalIncome) {
        this.nursesMedicalIncome = nursesMedicalIncome;
    }

    public Double getMedicalDepartmentTotalIncome() {
        return medicalDepartmentTotalIncome;
    }

    public void setMedicalDepartmentTotalIncome(Double medicalDepartmentTotalIncome) {
        this.medicalDepartmentTotalIncome = medicalDepartmentTotalIncome;
    }

    public Double getDailyIncomeOld() {
        return dailyIncomeOld;
    }

    public void setDailyIncomeOld(Double dailyIncomeOld) {
        this.dailyIncomeOld = dailyIncomeOld;
    }

    public Double getDailyOutpatientClinicOld() {
        return dailyOutpatientClinicOld;
    }

    public void setDailyOutpatientClinicOld(Double dailyOutpatientClinicOld) {
        this.dailyOutpatientClinicOld = dailyOutpatientClinicOld;
    }

    public Double getDailyHospitalizationOld() {
        return dailyHospitalizationOld;
    }

    public void setDailyHospitalizationOld(Double dailyHospitalizationOld) {
        this.dailyHospitalizationOld = dailyHospitalizationOld;
    }

    public Double getTotalMedicalIncomeOld() {
        return totalMedicalIncomeOld;
    }

    public void setTotalMedicalIncomeOld(Double totalMedicalIncomeOld) {
        this.totalMedicalIncomeOld = totalMedicalIncomeOld;
    }



    public Double getNursesMedicalIncomeOld() {
        return nursesMedicalIncomeOld;
    }

    public void setNursesMedicalIncomeOld(Double nursesMedicalIncomeOld) {
        this.nursesMedicalIncomeOld = nursesMedicalIncomeOld;
    }



    public Double getMedicalDepartmentTotalIncomeOld() {
        return medicalDepartmentTotalIncomeOld;
    }

    public void setMedicalDepartmentTotalIncomeOld(Double medicalDepartmentTotalIncomeOld) {
        this.medicalDepartmentTotalIncomeOld = medicalDepartmentTotalIncomeOld;
    }


    public Double getDailyIncomeMonths() {
        return dailyIncomeMonths;
    }

    public void setDailyIncomeMonths(Double dailyIncomeMonths) {
        this.dailyIncomeMonths = dailyIncomeMonths;
    }

    public Double getDailyOutpatientClinicMonths() {
        return dailyOutpatientClinicMonths;
    }

    public void setDailyOutpatientClinicMonths(Double dailyOutpatientClinicMonths) {
        this.dailyOutpatientClinicMonths = dailyOutpatientClinicMonths;
    }

    public Double getDailyHospitalizationMonths() {
        return dailyHospitalizationMonths;
    }

    public void setDailyHospitalizationMonths(Double dailyHospitalizationMonths) {
        this.dailyHospitalizationMonths = dailyHospitalizationMonths;
    }

    public Double getDailyIncomeYear() {
        return dailyIncomeYear;
    }

    public void setDailyIncomeYear(Double dailyIncomeYear) {
        this.dailyIncomeYear = dailyIncomeYear;
    }

    public Double getDailyOutpatientClinicYear() {
        return dailyOutpatientClinicYear;
    }

    public void setDailyOutpatientClinicYear(Double dailyOutpatientClinicYear) {
        this.dailyOutpatientClinicYear = dailyOutpatientClinicYear;
    }

    public Double getDailyHospitalizationYear() {
        return dailyHospitalizationYear;
    }

    public void setDailyHospitalizationYear(Double dailyHospitalizationYear) {
        this.dailyHospitalizationYear = dailyHospitalizationYear;
    }

    public int getDiurnalUrgency() {
        return diurnalUrgency;
    }

    public void setDiurnalUrgency(int diurnalUrgency) {
        this.diurnalUrgency = diurnalUrgency;
    }

    public int getDailyOutpatientNumber() {
        return dailyOutpatientNumber;
    }

    public void setDailyOutpatientNumber(int dailyOutpatientNumber) {
        this.dailyOutpatientNumber = dailyOutpatientNumber;
    }

    public int getDailyEmergency() {
        return dailyEmergency;
    }

    public void setDailyEmergency(int dailyEmergency) {
        this.dailyEmergency = dailyEmergency;
    }

    public int getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(int admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public int getDischargeNumber() {
        return dischargeNumber;
    }

    public void setDischargeNumber(int dischargeNumber) {
        this.dischargeNumber = dischargeNumber;
    }

    public int getHospitalPeople() {
        return hospitalPeople;
    }

    public void setHospitalPeople(int hospitalPeople) {
        this.hospitalPeople = hospitalPeople;
    }

    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

    public int getDiurnalOutpatientOperation() {
        return diurnalOutpatientOperation;
    }

    public void setDiurnalOutpatientOperation(int diurnalOutpatientOperation) {
        this.diurnalOutpatientOperation = diurnalOutpatientOperation;
    }

    public int getDailyOperation() {
        return dailyOperation;
    }

    public void setDailyOperation(int dailyOperation) {
        this.dailyOperation = dailyOperation;
    }

    public int getDoctorsNumber() {
        return doctorsNumber;
    }

    public void setDoctorsNumber(int doctorsNumber) {
        this.doctorsNumber = doctorsNumber;
    }

    public int getBedDay() {
        return bedDay;
    }

    public void setBedDay(int bedDay) {
        this.bedDay = bedDay;
    }

    public Double getOutpatientVolumeAverage() {
        return outpatientVolumeAverage;
    }

    public void setOutpatientVolumeAverage(Double outpatientVolumeAverage) {
        this.outpatientVolumeAverage = outpatientVolumeAverage;
    }

    public int getNursesNumber() {
        return nursesNumber;
    }

    public void setNursesNumber(int nursesNumber) {
        this.nursesNumber = nursesNumber;
    }

    public int getNursesBedDay() {
        return nursesBedDay;
    }

    public void setNursesBedDay(int nursesBedDay) {
        this.nursesBedDay = nursesBedDay;
    }

    public int getMedicalPeopleNumber() {
        return medicalPeopleNumber;
    }

    public void setMedicalPeopleNumber(int medicalPeopleNumber) {
        this.medicalPeopleNumber = medicalPeopleNumber;
    }

    public int getMonthBedDay() {
        return monthBedDay;
    }

    public void setMonthBedDay(int monthBedDay) {
        this.monthBedDay = monthBedDay;
    }

    public int getDiurnalUrgencyOld() {
        return diurnalUrgencyOld;
    }

    public void setDiurnalUrgencyOld(int diurnalUrgencyOld) {
        this.diurnalUrgencyOld = diurnalUrgencyOld;
    }

    public int getDailyOutpatientNumberOld() {
        return dailyOutpatientNumberOld;
    }

    public void setDailyOutpatientNumberOld(int dailyOutpatientNumberOld) {
        this.dailyOutpatientNumberOld = dailyOutpatientNumberOld;
    }

    public int getDailyEmergencyOld() {
        return dailyEmergencyOld;
    }

    public void setDailyEmergencyOld(int dailyEmergencyOld) {
        this.dailyEmergencyOld = dailyEmergencyOld;
    }

    public int getAdmissionNumberOld() {
        return admissionNumberOld;
    }

    public void setAdmissionNumberOld(int admissionNumberOld) {
        this.admissionNumberOld = admissionNumberOld;
    }

    public int getDischargeNumberOld() {
        return dischargeNumberOld;
    }

    public void setDischargeNumberOld(int dischargeNumberOld) {
        this.dischargeNumberOld = dischargeNumberOld;
    }

    public int getHospitalPeopleOld() {
        return hospitalPeopleOld;
    }

    public void setHospitalPeopleOld(int hospitalPeopleOld) {
        this.hospitalPeopleOld = hospitalPeopleOld;
    }

    public int getOperationNumberOld() {
        return operationNumberOld;
    }

    public void setOperationNumberOld(int operationNumberOld) {
        this.operationNumberOld = operationNumberOld;
    }

    public int getDiurnalOutpatientOperationOld() {
        return diurnalOutpatientOperationOld;
    }

    public void setDiurnalOutpatientOperationOld(int diurnalOutpatientOperationOld) {
        this.diurnalOutpatientOperationOld = diurnalOutpatientOperationOld;
    }

    public int getDailyOperationOld() {
        return dailyOperationOld;
    }

    public void setDailyOperationOld(int dailyOperationOld) {
        this.dailyOperationOld = dailyOperationOld;
    }

    public int getDoctorsNumberOld() {
        return doctorsNumberOld;
    }

    public void setDoctorsNumberOld(int doctorsNumberOld) {
        this.doctorsNumberOld = doctorsNumberOld;
    }

    public int getBedDayOld() {
        return bedDayOld;
    }

    public void setBedDayOld(int bedDayOld) {
        this.bedDayOld = bedDayOld;
    }

    public Double getOutpatientVolumeAverageOld() {
        return outpatientVolumeAverageOld;
    }

    public void setOutpatientVolumeAverageOld(Double outpatientVolumeAverageOld) {
        this.outpatientVolumeAverageOld = outpatientVolumeAverageOld;
    }

    public int getNursesNumberOld() {
        return nursesNumberOld;
    }

    public void setNursesNumberOld(int nursesNumberOld) {
        this.nursesNumberOld = nursesNumberOld;
    }

    public int getNursesBedDayOld() {
        return nursesBedDayOld;
    }

    public void setNursesBedDayOld(int nursesBedDayOld) {
        this.nursesBedDayOld = nursesBedDayOld;
    }

    public int getMedicalPeopleNumberOld() {
        return medicalPeopleNumberOld;
    }

    public void setMedicalPeopleNumberOld(int medicalPeopleNumberOld) {
        this.medicalPeopleNumberOld = medicalPeopleNumberOld;
    }

    public int getMonthBedDayOld() {
        return monthBedDayOld;
    }

    public void setMonthBedDayOld(int monthBedDayOld) {
        this.monthBedDayOld = monthBedDayOld;
    }

    public int getDiurnalUrgencyMonths() {
        return diurnalUrgencyMonths;
    }

    public void setDiurnalUrgencyMonths(int diurnalUrgencyMonths) {
        this.diurnalUrgencyMonths = diurnalUrgencyMonths;
    }

    public int getDailyOutpatientNumberMonths() {
        return dailyOutpatientNumberMonths;
    }

    public void setDailyOutpatientNumberMonths(int dailyOutpatientNumberMonths) {
        this.dailyOutpatientNumberMonths = dailyOutpatientNumberMonths;
    }

    public int getDailyEmergencyMonths() {
        return dailyEmergencyMonths;
    }

    public void setDailyEmergencyMonths(int dailyEmergencyMonths) {
        this.dailyEmergencyMonths = dailyEmergencyMonths;
    }

    public int getAdmissionNumberMonths() {
        return admissionNumberMonths;
    }

    public void setAdmissionNumberMonths(int admissionNumberMonths) {
        this.admissionNumberMonths = admissionNumberMonths;
    }

    public int getDischargeNumberMonths() {
        return dischargeNumberMonths;
    }

    public void setDischargeNumberMonths(int dischargeNumberMonths) {
        this.dischargeNumberMonths = dischargeNumberMonths;
    }

    public int getHospitalPeopleMonths() {
        return hospitalPeopleMonths;
    }

    public void setHospitalPeopleMonths(int hospitalPeopleMonths) {
        this.hospitalPeopleMonths = hospitalPeopleMonths;
    }

    public int getOperationNumberMonths() {
        return operationNumberMonths;
    }

    public void setOperationNumberMonths(int operationNumberMonths) {
        this.operationNumberMonths = operationNumberMonths;
    }

    public int getDiurnalOutpatientOperationMonths() {
        return diurnalOutpatientOperationMonths;
    }

    public void setDiurnalOutpatientOperationMonths(int diurnalOutpatientOperationMonths) {
        this.diurnalOutpatientOperationMonths = diurnalOutpatientOperationMonths;
    }

    public int getDailyOperationMonths() {
        return dailyOperationMonths;
    }

    public void setDailyOperationMonths(int dailyOperationMonths) {
        this.dailyOperationMonths = dailyOperationMonths;
    }

    public int getDiurnalUrgencyYear() {
        return diurnalUrgencyYear;
    }

    public void setDiurnalUrgencyYear(int diurnalUrgencyYear) {
        this.diurnalUrgencyYear = diurnalUrgencyYear;
    }

    public int getDailyOutpatientNumberYear() {
        return dailyOutpatientNumberYear;
    }

    public void setDailyOutpatientNumberYear(int dailyOutpatientNumberYear) {
        this.dailyOutpatientNumberYear = dailyOutpatientNumberYear;
    }

    public int getDailyEmergencyYear() {
        return dailyEmergencyYear;
    }

    public void setDailyEmergencyYear(int dailyEmergencyYear) {
        this.dailyEmergencyYear = dailyEmergencyYear;
    }

    public int getAdmissionNumberYear() {
        return admissionNumberYear;
    }

    public void setAdmissionNumberYear(int admissionNumberYear) {
        this.admissionNumberYear = admissionNumberYear;
    }

    public int getDischargeNumberYear() {
        return dischargeNumberYear;
    }

    public void setDischargeNumberYear(int dischargeNumberYear) {
        this.dischargeNumberYear = dischargeNumberYear;
    }

    public int getHospitalPeopleYear() {
        return hospitalPeopleYear;
    }

    public void setHospitalPeopleYear(int hospitalPeopleYear) {
        this.hospitalPeopleYear = hospitalPeopleYear;
    }

    public int getOperationNumberYear() {
        return operationNumberYear;
    }

    public void setOperationNumberYear(int operationNumberYear) {
        this.operationNumberYear = operationNumberYear;
    }

    public int getDiurnalOutpatientOperationYear() {
        return diurnalOutpatientOperationYear;
    }

    public void setDiurnalOutpatientOperationYear(int diurnalOutpatientOperationYear) {
        this.diurnalOutpatientOperationYear = diurnalOutpatientOperationYear;
    }

    public int getDailyOperationYear() {
        return dailyOperationYear;
    }

    public void setDailyOperationYear(int dailyOperationYear) {
        this.dailyOperationYear = dailyOperationYear;
    }

    public String[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String[] orgIds) {
        this.orgIds = orgIds;
    }

    public int getTotalNumHospital() {
        return totalNumHospital;
    }

    public void setTotalNumHospital(int totalNumHospital) {
        this.totalNumHospital = totalNumHospital;
    }

    public int getDoctorNum() {
        return doctorNum;
    }

    public void setDoctorNum(int doctorNum) {
        this.doctorNum = doctorNum;
    }

    public Double getTotalCostHospital() {
        return totalCostHospital;
    }

    public void setTotalCostHospital(Double totalCostHospital) {
        this.totalCostHospital = totalCostHospital;
    }

    public Double getTotalCostPeople() {
        return totalCostPeople;
    }

    public void setTotalCostPeople(Double totalCostPeople) {
        this.totalCostPeople = totalCostPeople;
    }

    public int getTotalNumHospitalOld() {
        return totalNumHospitalOld;
    }

    public void setTotalNumHospitalOld(int totalNumHospitalOld) {
        this.totalNumHospitalOld = totalNumHospitalOld;
    }

    public int getDoctorNumOld() {
        return doctorNumOld;
    }

    public void setDoctorNumOld(int doctorNumOld) {
        this.doctorNumOld = doctorNumOld;
    }

    public Double getTotalCostHospitalOld() {
        return totalCostHospitalOld;
    }

    public void setTotalCostHospitalOld(Double totalCostHospitalOld) {
        this.totalCostHospitalOld = totalCostHospitalOld;
    }

    public Double getTotalCostPeopleOld() {
        return totalCostPeopleOld;
    }

    public void setTotalCostPeopleOld(Double totalCostPeopleOld) {
        this.totalCostPeopleOld = totalCostPeopleOld;
    }

    public int getMedicalNum() {
        return medicalNum;
    }

    public void setMedicalNum(int medicalNum) {
        this.medicalNum = medicalNum;
    }

    public int getMedicalNumOld() {
        return medicalNumOld;
    }

    public void setMedicalNumOld(int medicalNumOld) {
        this.medicalNumOld = medicalNumOld;
    }

    public int getLogisticNum() {
        return logisticNum;
    }

    public void setLogisticNum(int logisticNum) {
        this.logisticNum = logisticNum;
    }

    public int getCtCheck() {
        return ctCheck;
    }

    public void setCtCheck(int ctCheck) {
        this.ctCheck = ctCheck;
    }

    public int getCtDoctor() {
        return ctDoctor;
    }

    public void setCtDoctor(int ctDoctor) {
        this.ctDoctor = ctDoctor;
    }

    public int getUltrasonicCheck() {
        return ultrasonicCheck;
    }

    public void setUltrasonicCheck(int ultrasonicCheck) {
        this.ultrasonicCheck = ultrasonicCheck;
    }

    public int getUltrasonicDoctor() {
        return ultrasonicDoctor;
    }

    public void setUltrasonicDoctor(int ultrasonicDoctor) {
        this.ultrasonicDoctor = ultrasonicDoctor;
    }

    public int getxCheck() {
        return xCheck;
    }

    public void setxCheck(int xCheck) {
        this.xCheck = xCheck;
    }

    public int getxDoctor() {
        return xDoctor;
    }

    public void setxDoctor(int xDoctor) {
        this.xDoctor = xDoctor;
    }

    public int getNmrCheck() {
        return nmrCheck;
    }

    public void setNmrCheck(int nmrCheck) {
        this.nmrCheck = nmrCheck;
    }

    public int getNmrDoctor() {
        return nmrDoctor;
    }

    public void setNmrDoctor(int nmrDoctor) {
        this.nmrDoctor = nmrDoctor;
    }
}