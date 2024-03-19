/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.entity.MedicalOperationReport;
import com.thinkgem.jeesite.modules.process.dao.MedicalOperationReportDao;

/**
 * 医疗运营报表Service
 *
 * @author oa
 * @version 2018-01-08
 */
@Service
@Transactional(readOnly = true)
public class MedicalOperationReportService extends CrudService<MedicalOperationReportDao, MedicalOperationReport> {

    public MedicalOperationReport get(String id) {
        return super.get(id);
    }

    public List<MedicalOperationReport> findList(MedicalOperationReport medicalOperationReport) {
        return super.findList(medicalOperationReport);
    }

    public Page<MedicalOperationReport> findPage(Page<MedicalOperationReport> page, MedicalOperationReport medicalOperationReport) {
        return super.findPage(page, medicalOperationReport);
    }

    /**
     * 日月年的医疗运营报表保存
     *
     * @param medicalOperationReport
     */
    @Transactional(readOnly = false)
    public void saveMedical(MedicalOperationReport medicalOperationReport) throws Exception {
        //日报表保存
        if (StringUtils.isNotBlank(medicalOperationReport.getId())) {
            //根据id查询原有数据的值
            MedicalOperationReport report = dao.get(medicalOperationReport.getId());
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
            String s1 = sdf.format(report.getCurrentSumDate());  //2015-02-09  format()才是格式化
            medicalOperationReport.setOrgId(UserUtils.getUser().getCompany().getId());
            medicalOperationReport.setCurrentMonths(sdf.parse(s1));
            medicalOperationReport.setCurrentSumDate(report.getCurrentSumDate());
            if (report != null) {
                //如果更新数据，那么需要将年月的数据先将当前数据减去，在加上新的数据
                List<MedicalOperationReport> list = dao.findListByMonths(medicalOperationReport);
                List<MedicalOperationReport> years = dao.findListByYear(medicalOperationReport);
                MedicalOperationReport year = new MedicalOperationReport();
                if (years != null && years.size() > 0) {
                    year = years.get(0);
                }
                if (list != null && list.size() > 0) {
                    for (MedicalOperationReport m : list) {
                        updateMonthsAndYear(medicalOperationReport, m, year, report);
//                        break;
                    }
                }
            }
            //更新医疗运营报表
            medicalOperationReport.preUpdate();
            dao.update(medicalOperationReport);

        } else {
            //月报表保存
            //先判断是否有当前月份的数据存在，如果存在就直接更新进入到月份表中
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy");
            String s1 = sdf.format(medicalOperationReport.getCurrentSumDate());  //2015-02-09  format()才是格式化
            String s2 = sd.format(medicalOperationReport.getCurrentSumDate());  //2015-02-09  format()才是格式化
            System.out.println(sdf.parse(s1));
            medicalOperationReport.setCurrentMonths(sdf.parse(s1));
            medicalOperationReport.setCurrentYear(sd.parse(s2));
            medicalOperationReport.setOrgId(UserUtils.getUser().getCompany().getId());
            List<MedicalOperationReport> list = dao.findListByMonths(medicalOperationReport);
            List<MedicalOperationReport> years = dao.findListByYear(medicalOperationReport);
            //判断在增加的时候是否存在当前的日期，如果存在就更新
            List<MedicalOperationReport> reports = dao.findList(medicalOperationReport);
            if (reports != null && reports.size() > 0) {
                dao.update(medicalOperationReport);
                updateMonthsAndYear(medicalOperationReport,list.get(0),years.get(0),reports.get(0));
            }else{
                medicalOperationReport.preInsert();
                dao.insert(medicalOperationReport);
//
//                MedicalOperationReport year = new MedicalOperationReport();
//                if (years != null && years.size() > 0) {
//                    year = years.get(0);
//                }
                if (list != null && list.size() > 0) {
                    for (MedicalOperationReport m : list) {
                        //已经有当月的数据
                        m.setDailyIncomeMonths(medicalOperationReport.getDailyIncome() + m.getDailyIncomeMonths()); //月收入
                        m.setDailyOutpatientClinicMonths(medicalOperationReport.getDailyOutpatientClinic() + m.getDailyOutpatientClinicMonths());//月门诊
                        m.setDailyHospitalizationMonths(medicalOperationReport.getDailyHospitalization() + m.getDailyHospitalizationMonths()); //月住院

                        m.setDiurnalUrgencyMonths(medicalOperationReport.getDiurnalUrgency() + m.getDiurnalUrgencyMonths());  //月门急人次
                        m.setDailyOutpatientNumberMonths(medicalOperationReport.getDailyOutpatientNumber() + m.getDailyOutpatientNumberMonths()); //月门诊人次
                        m.setDailyEmergencyMonths(medicalOperationReport.getDailyEmergency() + m.getDailyEmergencyMonths()); //月急诊人次

                        m.setAdmissionNumberMonths(medicalOperationReport.getAdmissionNumber() + m.getAdmissionNumberMonths()); //月入院人数
                        m.setDischargeNumberMonths(medicalOperationReport.getDischargeNumber() + m.getDischargeNumberMonths());  //月出院人数
                        //月占床日
                        m.setHospitalPeopleMonths(medicalOperationReport.getHospitalPeople() + m.getHospitalPeopleMonths());
//
                        m.setOperationNumberMonths(medicalOperationReport.getOperationNumber() + m.getOperationNumberMonths()); //月手术台次
                        m.setDiurnalOutpatientOperationMonths(medicalOperationReport.getDiurnalOutpatientOperation() + m.getDiurnalOutpatientOperationMonths()); //月门诊手术
                        m.setDailyOperationMonths(medicalOperationReport.getDailyOperation() + m.getDailyOperationMonths());  //月住院手术
                        m.preUpdate();
                        dao.updateMonths(m);
                        break;
                    }
                } else {
                    //没有当月的数据
                    medicalOperationReport.setId(null);
                    medicalOperationReport.setDailyIncomeMonths(medicalOperationReport.getDailyIncome()); //月收入
                    medicalOperationReport.setDailyOutpatientClinicMonths(medicalOperationReport.getDailyOutpatientClinic());//月门诊
                    medicalOperationReport.setDailyHospitalizationMonths(medicalOperationReport.getDailyHospitalization()); //月住院

                    medicalOperationReport.setDiurnalUrgencyMonths(medicalOperationReport.getDiurnalUrgency());  //月门急人次
                    medicalOperationReport.setDailyOutpatientNumberMonths(medicalOperationReport.getDailyOutpatientNumber()); //月门诊人次
                    medicalOperationReport.setDailyEmergencyMonths(medicalOperationReport.getDailyEmergency()); //月急诊人次

                    medicalOperationReport.setAdmissionNumberMonths(medicalOperationReport.getAdmissionNumber()); //月入院人数
                    medicalOperationReport.setDischargeNumberMonths(medicalOperationReport.getDischargeNumber());  //月出院人数
                    //月占床日
                    medicalOperationReport.setHospitalPeopleMonths(medicalOperationReport.getHospitalPeople());

                    medicalOperationReport.setOperationNumberMonths(medicalOperationReport.getOperationNumber()); //月手术台次
                    medicalOperationReport.setDiurnalOutpatientOperationMonths(medicalOperationReport.getDiurnalOutpatientOperation()); //月门诊手术
                    medicalOperationReport.setDailyOperationMonths(medicalOperationReport.getDailyOperation());  //月住院手术
                    //插入数据
                    medicalOperationReport.preInsert();
                    dao.insertMonths(medicalOperationReport);
                }


                if (years != null && years.size() > 0) {
                    for (MedicalOperationReport year : years) {
                        year.setDailyIncomeYear(medicalOperationReport.getDailyIncome() + year.getDailyIncomeYear());  //年收入
                        year.setDailyOutpatientClinicYear(medicalOperationReport.getDailyOutpatientClinic() + year.getDailyOutpatientClinicYear());//年门诊
                        year.setDailyHospitalizationYear(medicalOperationReport.getDailyHospitalization() + year.getDailyHospitalizationYear()); //年住院

                        year.setDiurnalUrgencyYear(medicalOperationReport.getDiurnalUrgency() + year.getDiurnalUrgencyYear());  //年门急人次
                        year.setDailyOutpatientNumberYear(medicalOperationReport.getDailyOutpatientNumber() + year.getDailyOutpatientNumberYear()); //年门诊人次
                        year.setDailyEmergencyYear(medicalOperationReport.getDailyEmergency() + year.getDailyEmergencyYear()); //年急诊人次

                        year.setAdmissionNumberYear(medicalOperationReport.getAdmissionNumber() + year.getAdmissionNumberYear()); //年入院人数
                        year.setDischargeNumberYear(medicalOperationReport.getDischargeNumber() + year.getDischargeNumberYear());  //年出院人数
                        //年占床日
                        year.setHospitalPeopleYear(medicalOperationReport.getHospitalPeople() + year.getHospitalPeopleYear());
//
                        year.setOperationNumberYear(medicalOperationReport.getOperationNumber() + year.getOperationNumberYear()); //年手术台次
                        year.setDiurnalOutpatientOperationYear(medicalOperationReport.getDiurnalOutpatientOperation() + year.getDiurnalOutpatientOperationYear()); //年门诊手术
                        year.setDailyOperationYear(medicalOperationReport.getDailyOperation() + year.getDailyOperationYear());  //年住院手术
                        year.preUpdate();
                        dao.updateYear(year);
                        break;
                    }
                } else {
                    //没有当月的数据
                    medicalOperationReport.setId(null);
                    medicalOperationReport.setDailyIncomeYear(medicalOperationReport.getDailyIncome()); //年收入
                    medicalOperationReport.setDailyOutpatientClinicYear(medicalOperationReport.getDailyOutpatientClinic());//年门诊
                    medicalOperationReport.setDailyHospitalizationYear(medicalOperationReport.getDailyHospitalization()); //年住院

                    medicalOperationReport.setDiurnalUrgencyYear(medicalOperationReport.getDiurnalUrgency());  //年门急人次
                    medicalOperationReport.setDailyOutpatientNumberYear(medicalOperationReport.getDailyOutpatientNumber()); //年门诊人次
                    medicalOperationReport.setDailyEmergencyYear(medicalOperationReport.getDailyEmergency()); //年急诊人次

                    medicalOperationReport.setAdmissionNumberYear(medicalOperationReport.getAdmissionNumber()); //年入院人数
                    medicalOperationReport.setDischargeNumberYear(medicalOperationReport.getDischargeNumber());  //年出院人数
                    //年占床日
                    medicalOperationReport.setHospitalPeopleYear(medicalOperationReport.getHospitalPeople());

                    medicalOperationReport.setOperationNumberYear(medicalOperationReport.getOperationNumber()); //年手术台次
                    medicalOperationReport.setDiurnalOutpatientOperationYear(medicalOperationReport.getDiurnalOutpatientOperation()); //年门诊手术
                    medicalOperationReport.setDailyOperationYear(medicalOperationReport.getDailyOperation());  //年住院手术
                    //插入数据
                    medicalOperationReport.preInsert();
                    dao.insertYear(medicalOperationReport);
                }

            }
            }



    }


    /**
     * 修改医疗运营表的信息
     *
     * @param medicalOperationReport
     * @param m                      月份表
     * @param year                   年份表
     * @param report                 旧数据
     */
    public void updateMonthsAndYear(MedicalOperationReport medicalOperationReport, MedicalOperationReport m, MedicalOperationReport year, MedicalOperationReport report) {
        m.setDailyIncomeMonths(medicalOperationReport.getDailyIncome() + m.getDailyIncomeMonths() - report.getDailyIncomeOld()); //月收入
        m.setDailyOutpatientClinicMonths(medicalOperationReport.getDailyOutpatientClinic() + m.getDailyOutpatientClinicMonths() - report.getDailyOutpatientClinicOld());//月门诊
        m.setDailyHospitalizationMonths(medicalOperationReport.getDailyHospitalization() + m.getDailyHospitalizationMonths() - report.getDailyHospitalizationOld()); //月住院
        year.setDailyIncomeYear(medicalOperationReport.getDailyIncome() + year.getDailyIncomeYear() - report.getDailyIncomeOld());  //年收入
        year.setDailyOutpatientClinicYear(medicalOperationReport.getDailyOutpatientClinic() + year.getDailyOutpatientClinicYear() - report.getDailyOutpatientClinicOld());//年门诊
        year.setDailyHospitalizationYear(medicalOperationReport.getDailyHospitalization() + year.getDailyHospitalizationYear()- report.getDailyHospitalizationOld()); //年住院

        m.setDiurnalUrgencyMonths(medicalOperationReport.getDiurnalUrgency() + m.getDiurnalUrgencyMonths() - report.getDiurnalUrgencyOld());  //月门急人次
        m.setDailyOutpatientNumberMonths(medicalOperationReport.getDailyOutpatientNumber() + m.getDailyOutpatientNumberMonths() - report.getDailyOutpatientNumberOld()); //月门诊人次
        m.setDailyEmergencyMonths(medicalOperationReport.getDailyEmergency() + m.getDailyEmergencyMonths() - report.getDailyEmergencyOld()); //月急诊人次
        year.setDiurnalUrgencyYear(medicalOperationReport.getDiurnalUrgency() + year.getDiurnalUrgencyYear() - report.getDiurnalUrgencyOld());  //年门急人次
        year.setDailyOutpatientNumberYear(medicalOperationReport.getDailyOutpatientNumber() + year.getDailyOutpatientNumberYear() - report.getDailyOutpatientNumberOld()); //年门诊人次
        year.setDailyEmergencyYear(medicalOperationReport.getDailyEmergency() + year.getDailyEmergencyYear() - report.getDailyEmergencyOld()); //年急诊人次

        m.setAdmissionNumberMonths(medicalOperationReport.getAdmissionNumber()  + m.getAdmissionNumberMonths() - report.getAdmissionNumberOld()); //月入院人数
        m.setDischargeNumberMonths(medicalOperationReport.getDischargeNumber() + m.getDischargeNumberMonths() - report.getDischargeNumberOld());  //月出院人数
        m.setHospitalPeopleMonths(medicalOperationReport.getHospitalPeople() + m.getHospitalPeopleMonths() - report.getHospitalPeopleOld());  //月占床日
        year.setAdmissionNumberYear(medicalOperationReport.getAdmissionNumber() + year.getAdmissionNumberYear() - report.getAdmissionNumberOld()); //年入院人数
        year.setDischargeNumberYear(medicalOperationReport.getDischargeNumber() + year.getDischargeNumberYear() - report.getDischargeNumberOld());  //年出院人数
        year.setHospitalPeopleYear(medicalOperationReport.getHospitalPeople() + year.getHospitalPeopleYear() - report.getHospitalPeopleOld());  //年占床日
        //年占床日
//        year.setHospitalPeopleYear(medicalOperationReport.getMonthBedDay() + year.getHospitalPeopleYear() - report.getMonthBedDayOld());
//
        m.setOperationNumberMonths(medicalOperationReport.getOperationNumber() + m.getOperationNumberMonths() - report.getOperationNumberOld()); //月手术台次
        m.setDiurnalOutpatientOperationMonths(medicalOperationReport.getDiurnalOutpatientOperation() + m.getDiurnalOutpatientOperationMonths() - report.getDiurnalOutpatientOperationOld()); //月门诊手术
        m.setDailyOperationMonths(medicalOperationReport.getDailyOperation() + m.getDailyOperationMonths() - report.getDailyOperationOld());  //月住院手术
        year.setOperationNumberYear(medicalOperationReport.getOperationNumber() + year.getOperationNumberYear() - report.getOperationNumberOld()); //年手术台次
        year.setDiurnalOutpatientOperationYear(medicalOperationReport.getDiurnalOutpatientOperation() + year.getDiurnalOutpatientOperationYear() - report.getDiurnalOutpatientOperationOld()); //年门诊手术
        year.setDailyOperationYear(medicalOperationReport.getDailyOperation() + year.getDailyOperationYear() - report.getDailyOperationOld());  //年住院手术
        year.preUpdate();
        dao.updateYear(year);
        m.preUpdate();
        dao.updateMonths(m);
    }

    @Transactional(readOnly = false)
    public void delete(MedicalOperationReport medicalOperationReport) {
        super.delete(medicalOperationReport);
    }


    /**
     * 查询日月年合计
     * @param medicalOperationReport
     * @return
     */
    public List<MedicalOperationReport> findAllList(MedicalOperationReport medicalOperationReport){
        return dao.findAllList(medicalOperationReport);
    }

}