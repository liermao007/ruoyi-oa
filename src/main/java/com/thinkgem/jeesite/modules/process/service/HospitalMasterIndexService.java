/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.entity.HospitalMasterIndex;
import com.thinkgem.jeesite.modules.process.dao.HospitalMasterIndexDao;

/**
 * 医院经营指标Service
 *
 * @author oa
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly = true)
public class HospitalMasterIndexService extends CrudService<HospitalMasterIndexDao, HospitalMasterIndex> {

    public HospitalMasterIndex get(String id) {
        return super.get(id);
    }

    public List<HospitalMasterIndex> findList(HospitalMasterIndex hospitalMasterIndex) {
        return super.findList(hospitalMasterIndex);
    }

    public Page<HospitalMasterIndex> findPage(Page<HospitalMasterIndex> page, HospitalMasterIndex hospitalMasterIndex) {
        return super.findPage(page, hospitalMasterIndex);
    }

    @Transactional(readOnly = false)
    public void saveHospital(HospitalMasterIndex hospitalMasterIndex) throws ParseException {
        //月数据保存   里面的id是月数据表的id
        if (StringUtils.isNotBlank(hospitalMasterIndex.getId())) {
            //根据id查询原有数据的值
            HospitalMasterIndex old = dao.getByOld(hospitalMasterIndex.getId());
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
            String s1 = sdf.format(old.getMonth());  //2015-02-09  format()才是格式化
            hospitalMasterIndex.setOrgId(UserUtils.getUser().getCompany().getId());
            hospitalMasterIndex.setMonth(sdf.parse(s1));
            SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy");
            String s2 = sd.format(old.getMonth());  //2015-02-09  format()才是格式化
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(s1));
            int month = c.get(Calendar.MONTH);
            hospitalMasterIndex.setQuarter(getQuarterByMonth(month));
            //判断当前月份是这一年的第几个季度
            hospitalMasterIndex.setYearQuarter(s2);
            if (old != null) {
                //如果更新数据，那么需要将年和季度的数据先将当前数据减去，在加上新的数据
                List<HospitalMasterIndex> list = dao.findListByQuarter(hospitalMasterIndex);
                List<HospitalMasterIndex> years = dao.findListByYear(hospitalMasterIndex);
                HospitalMasterIndex year = new HospitalMasterIndex();
                if (years != null && years.size() > 0) {
                    year = years.get(0);
                }
                if (list != null && list.size() > 0) {
                    for (HospitalMasterIndex quarter : list) {
                        updateQuarterAndYear(hospitalMasterIndex, quarter, year, old);
//                        break;
                    }
                }
            }
            //更新医疗运营报表
            hospitalMasterIndex.preUpdate();
            dao.updateMonths(hospitalMasterIndex);

        } else {
            //保存季度数据，保存季度数据的同时增加月数据的值
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String s1 = sdf.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
            hospitalMasterIndex.setOrgId(UserUtils.getUser().getCompany().getId());
            SimpleDateFormat sd = new SimpleDateFormat("yyyy");
            String s2 = sd.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(s1));
            int month = c.get(Calendar.MONTH) + 1;
            hospitalMasterIndex.setQuarter(getQuarterByMonth(month));
            //判断当前月份是这一年的第几个季度
            hospitalMasterIndex.setYearQuarter(s2);
            hospitalMasterIndex.setYear(sd.parse(s2));
            List<HospitalMasterIndex> list = dao.findListByQuarter(hospitalMasterIndex);
            List<HospitalMasterIndex> years = dao.findListByYear(hospitalMasterIndex);
            List<HospitalMasterIndex> reports = dao.findList(hospitalMasterIndex);
            if (reports != null && reports.size() > 0) {
                dao.update(hospitalMasterIndex);
                updateQuarterAndYear(hospitalMasterIndex, list.get(0), years.get(0), reports.get(0));
            } else {
                hospitalMasterIndex.preInsert();
                dao.insert(hospitalMasterIndex);
            }
            //季度
            if (list != null && list.size() > 0) {
                for (HospitalMasterIndex m : list) {
                    //已经有当季度的数据
                    //集团现金
                    m.setGroupCashActualAmountQuarter(m.getGroupCashActualAmountQuarter() + hospitalMasterIndex.getGroupCashActualAmountMonths());
                    m.setGroupCashBudgetaryAmountQuarter(m.getGroupCashBudgetaryAmountQuarter() + hospitalMasterIndex.getGroupCashBudgetaryAmountMonths());
                    //医疗收入
                    m.setMedicalIncomeActualAmountQuarter(m.getMedicalIncomeActualAmountQuarter() + hospitalMasterIndex.getMedicalIncomeActualAmountMonths());
                    m.setMedicalIncomeBudgetaryAmountQuarter(m.getMedicalIncomeBudgetaryAmountQuarter() + hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths());
                    //成本支出
                    m.setCostExpenditureActualAmountQuarter(m.getCostExpenditureActualAmountQuarter() + hospitalMasterIndex.getCostExpenditureActualAmountMonths());
                    m.setCostExpenditureBudgetaryAmountQuarter(m.getCostExpenditureBudgetaryAmountQuarter() + hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths());
                    //利润
                    m.setProfitActualAmountQuarter(m.getProfitActualAmountQuarter() + hospitalMasterIndex.getProfitActualAmountMonths());
                    m.setProfitBudgetaryAmountQuarter(m.getProfitBudgetaryAmountQuarter() + hospitalMasterIndex.getProfitBudgetaryAmountMonths());
                    //现金运营指数
                    m.setOperationIndexActualAmountQuarter(m.getOperationIndexActualAmountQuarter() + hospitalMasterIndex.getOperationIndexActualAmountMonths());
                    m.setOperationIndexBudgetaryAmountQuarter(m.getOperationIndexBudgetaryAmountQuarter() + hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths());
                    //主渠道采购率
                    m.setPurchasingRateActualAmountQuarter(m.getPurchasingRateActualAmountQuarter() + hospitalMasterIndex.getPurchasingRateActualAmountMonths());
                    m.setPurchasingRateBudgetaryAmountQuarter(m.getPurchasingRateBudgetaryAmountQuarter() + hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths());
                    //质量达标
                    m.setQualityStandardActualAmountQuarter(m.getQualityStandardActualAmountQuarter() + hospitalMasterIndex.getQualityStandardActualAmountMonths());
                    m.setQualityStandardBudgetaryAmountQuarter(m.getQualityStandardBudgetaryAmountQuarter() + hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths());
                    m.preUpdate();
                    dao.updateQuarter(m);
                    break;
                }
            } else {
                //没有当前季度的数据
                hospitalMasterIndex.setId(null);
                //集团现金
                hospitalMasterIndex.setGroupCashActualAmountQuarter(hospitalMasterIndex.getGroupCashActualAmountMonths());
                hospitalMasterIndex.setGroupCashBudgetaryAmountQuarter(hospitalMasterIndex.getGroupCashBudgetaryAmountMonths());
                //医疗收入
                hospitalMasterIndex.setMedicalIncomeActualAmountQuarter(hospitalMasterIndex.getMedicalIncomeActualAmountMonths());
                hospitalMasterIndex.setMedicalIncomeBudgetaryAmountQuarter(hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths());
                //成本支出
                hospitalMasterIndex.setCostExpenditureActualAmountQuarter(hospitalMasterIndex.getCostExpenditureActualAmountMonths());
                hospitalMasterIndex.setCostExpenditureBudgetaryAmountQuarter(hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths());
                //利润
                hospitalMasterIndex.setProfitActualAmountQuarter(hospitalMasterIndex.getProfitActualAmountMonths());
                hospitalMasterIndex.setProfitBudgetaryAmountQuarter(hospitalMasterIndex.getProfitBudgetaryAmountMonths());
                //现金经营指数
                hospitalMasterIndex.setOperationIndexActualAmountQuarter(hospitalMasterIndex.getOperationIndexActualAmountMonths());
                hospitalMasterIndex.setOperationIndexBudgetaryAmountQuarter(hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths());
                //主渠道采购率
                hospitalMasterIndex.setPurchasingRateActualAmountQuarter(hospitalMasterIndex.getPurchasingRateActualAmountMonths());
                hospitalMasterIndex.setPurchasingRateBudgetaryAmountQuarter(hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths());
                //质量达标
                hospitalMasterIndex.setQualityStandardActualAmountQuarter(hospitalMasterIndex.getQualityStandardActualAmountMonths());
                hospitalMasterIndex.setQualityStandardBudgetaryAmountQuarter(hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths());

                //插入数据
                hospitalMasterIndex.preInsert();
                dao.insertQuarter(hospitalMasterIndex);
            }

            if (years != null && years.size() > 0) {
                for (HospitalMasterIndex m : years) {
                    //已经有当年的数据
                    //集团现金
                    m.setGroupCashActualAmountYear(m.getGroupCashActualAmountYear() + hospitalMasterIndex.getGroupCashActualAmountMonths());
                    m.setGroupCashBudgetaryAmountYear(m.getGroupCashBudgetaryAmountYear() + hospitalMasterIndex.getGroupCashBudgetaryAmountMonths());
                    //医疗收入
                    m.setMedicalIncomeActualAmountYear(m.getMedicalIncomeActualAmountYear() + hospitalMasterIndex.getMedicalIncomeActualAmountMonths());
                    m.setMedicalIncomeBudgetaryAmountYear(m.getMedicalIncomeBudgetaryAmountYear() + hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths());
                    //成本支出
                    m.setCostExpenditureActualAmountYear(m.getCostExpenditureActualAmountYear() + hospitalMasterIndex.getCostExpenditureActualAmountMonths());
                    m.setCostExpenditureBudgetaryAmountYear(m.getCostExpenditureBudgetaryAmountYear() + hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths());
                    //利润
                    m.setProfitActualAmountYear(m.getProfitActualAmountYear() + hospitalMasterIndex.getProfitActualAmountMonths());
                    m.setProfitBudgetaryAmountYear(m.getProfitBudgetaryAmountYear() + hospitalMasterIndex.getProfitBudgetaryAmountMonths());
                    //现金运营指数
                    m.setOperationIndexActualAmountYear(m.getOperationIndexActualAmountYear() + hospitalMasterIndex.getOperationIndexActualAmountMonths());
                    m.setOperationIndexBudgetaryAmountYear(m.getOperationIndexBudgetaryAmountYear() + hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths());
                    //主渠道采购率
                    m.setPurchasingRateActualAmountYear(m.getPurchasingRateActualAmountYear() + hospitalMasterIndex.getPurchasingRateActualAmountMonths());
                    m.setPurchasingRateBudgetaryAmountYear(m.getPurchasingRateBudgetaryAmountYear() + hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths());
                    //质量达标
                    m.setQualityStandardActualAmountYear(m.getQualityStandardActualAmountYear() + hospitalMasterIndex.getQualityStandardActualAmountMonths());
                    m.setQualityStandardBudgetaryAmountYear(m.getQualityStandardBudgetaryAmountYear() + hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths());
                    m.preUpdate();
                    dao.updateYear(m);
                    break;
                }
            } else {
                //没有当年的数据，增加
                hospitalMasterIndex.setId(null);
                //集团现金
                hospitalMasterIndex.setGroupCashActualAmountYear(hospitalMasterIndex.getGroupCashActualAmountMonths());
                hospitalMasterIndex.setGroupCashBudgetaryAmountYear(hospitalMasterIndex.getGroupCashBudgetaryAmountMonths());
                //医疗收入
                hospitalMasterIndex.setMedicalIncomeActualAmountYear(hospitalMasterIndex.getMedicalIncomeActualAmountMonths());
                hospitalMasterIndex.setMedicalIncomeBudgetaryAmountYear(hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths());
                //成本支出
                hospitalMasterIndex.setCostExpenditureActualAmountYear(hospitalMasterIndex.getCostExpenditureActualAmountMonths());
                hospitalMasterIndex.setCostExpenditureBudgetaryAmountYear(hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths());
                //利润
                hospitalMasterIndex.setProfitActualAmountYear(hospitalMasterIndex.getProfitActualAmountMonths());
                hospitalMasterIndex.setProfitBudgetaryAmountYear(hospitalMasterIndex.getProfitBudgetaryAmountMonths());
                //现金经营指数
                hospitalMasterIndex.setOperationIndexActualAmountYear(hospitalMasterIndex.getOperationIndexActualAmountMonths());
                hospitalMasterIndex.setOperationIndexBudgetaryAmountYear(hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths());
                //主渠道采购率
                hospitalMasterIndex.setPurchasingRateActualAmountYear(hospitalMasterIndex.getPurchasingRateActualAmountMonths());
                hospitalMasterIndex.setPurchasingRateBudgetaryAmountYear(hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths());
                //质量达标
                hospitalMasterIndex.setQualityStandardActualAmountYear(hospitalMasterIndex.getQualityStandardActualAmountMonths());
                hospitalMasterIndex.setQualityStandardBudgetaryAmountYear(hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths());

                //插入数据
                hospitalMasterIndex.preInsert();
                dao.insertYear(hospitalMasterIndex);
            }
        }
    }

    /**
     * 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月
     *
     * @param month
     * @return 当前月份是第几季度
     */
    public static String getQuarterByMonth(int month) {
        String months[] = {"1", "2", "3", "4"};
        if (month >= 1 && month <= 3) // 1-3月;0,1,2
            return months[0];
        else if (month >= 4 && month <= 6) // 4-6月;3,4,5
            return months[1];
        else if (month >= 7 && month <= 9) // 7-9月;6,7,8
            return months[2];
        else
            // 10-12月;10,11,12
            return months[3];
    }


    /**
     * 修改医疗运营表的信息
     *
     * @param hospitalMasterIndex
     * @param quarter             季度数据
     * @param year                年份表
     * @param old                 旧数据
     */
    public void updateQuarterAndYear(HospitalMasterIndex hospitalMasterIndex, HospitalMasterIndex quarter, HospitalMasterIndex year, HospitalMasterIndex old) {
        //集团现金
        quarter.setGroupCashActualAmountQuarter(hospitalMasterIndex.getGroupCashActualAmountMonths() + quarter.getGroupCashActualAmountQuarter() - old.getGroupCashActualAmountMonthsOld());
        quarter.setGroupCashBudgetaryAmountQuarter(hospitalMasterIndex.getGroupCashBudgetaryAmountMonths() + quarter.getGroupCashBudgetaryAmountQuarter() - old.getGroupCashBudgetaryAmountMonthsOld());
        year.setGroupCashActualAmountYear(hospitalMasterIndex.getGroupCashActualAmountMonths() + year.getGroupCashActualAmountYear() - old.getGroupCashActualAmountMonthsOld());
        year.setGroupCashBudgetaryAmountYear(hospitalMasterIndex.getGroupCashBudgetaryAmountMonths() + year.getGroupCashBudgetaryAmountYear() - old.getGroupCashBudgetaryAmountMonthsOld());
        //医疗收入
        quarter.setMedicalIncomeActualAmountQuarter(hospitalMasterIndex.getMedicalIncomeActualAmountMonths() + quarter.getMedicalIncomeActualAmountQuarter() - old.getMedicalIncomeActualAmountMonthsOld());
        quarter.setMedicalIncomeBudgetaryAmountQuarter(hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths() + quarter.getMedicalIncomeBudgetaryAmountQuarter() - old.getMedicalIncomeBudgetaryAmountMonthsOld());
        year.setMedicalIncomeActualAmountYear(hospitalMasterIndex.getMedicalIncomeActualAmountMonths() + year.getMedicalIncomeActualAmountYear() - old.getMedicalIncomeActualAmountMonthsOld());
        year.setMedicalIncomeBudgetaryAmountYear(hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths() + quarter.getMedicalIncomeBudgetaryAmountYear() - old.getMedicalIncomeBudgetaryAmountMonthsOld());
        //成本支出
        quarter.setCostExpenditureActualAmountQuarter(hospitalMasterIndex.getCostExpenditureActualAmountMonths() + quarter.getCostExpenditureActualAmountQuarter() - old.getCostExpenditureActualAmountMonthsOld());
        quarter.setCostExpenditureBudgetaryAmountQuarter(hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths() + quarter.getCostExpenditureBudgetaryAmountQuarter() - old.getCostExpenditureBudgetaryAmountMonthsOld());
        year.setCostExpenditureActualAmountYear(hospitalMasterIndex.getCostExpenditureActualAmountMonths() + quarter.getCostExpenditureActualAmountYear() - old.getCostExpenditureActualAmountMonthsOld());
        year.setCostExpenditureBudgetaryAmountYear(hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths() + quarter.getCostExpenditureBudgetaryAmountYear() - old.getCostExpenditureBudgetaryAmountMonthsOld());
        //利润
        quarter.setProfitActualAmountQuarter(hospitalMasterIndex.getProfitActualAmountMonths() + quarter.getProfitActualAmountQuarter() - old.getProfitActualAmountMonthsOld());
        quarter.setProfitBudgetaryAmountQuarter(hospitalMasterIndex.getProfitBudgetaryAmountMonths() + quarter.getProfitBudgetaryAmountQuarter() - old.getProfitBudgetaryAmountMonthsOld());
        year.setProfitActualAmountYear(hospitalMasterIndex.getProfitActualAmountMonths() + quarter.getProfitActualAmountYear() - old.getProfitActualAmountMonthsOld());
        year.setProfitBudgetaryAmountYear(hospitalMasterIndex.getProfitBudgetaryAmountMonths() + quarter.getProfitBudgetaryAmountYear() - old.getProfitBudgetaryAmountMonthsOld());
        //现金运营指数
        quarter.setOperationIndexActualAmountQuarter(hospitalMasterIndex.getOperationIndexActualAmountMonths() + quarter.getOperationIndexActualAmountQuarter() - old.getOperationIndexActualAmountMonthsOld());
        quarter.setOperationIndexBudgetaryAmountQuarter(hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths() + quarter.getOperationIndexBudgetaryAmountQuarter() - old.getOperationIndexBudgetaryAmountMonthsOld());
        year.setOperationIndexActualAmountYear(hospitalMasterIndex.getOperationIndexActualAmountMonths() + quarter.getOperationIndexActualAmountYear() - old.getOperationIndexActualAmountMonthsOld());
        year.setOperationIndexBudgetaryAmountYear(hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths() + quarter.getOperationIndexBudgetaryAmountYear() - old.getOperationIndexBudgetaryAmountMonthsOld());
        //主渠道采购率
        quarter.setPurchasingRateActualAmountQuarter(hospitalMasterIndex.getPurchasingRateActualAmountMonths() + quarter.getPurchasingRateActualAmountQuarter() - old.getPurchasingRateActualAmountMonthsOld());
        quarter.setPurchasingRateBudgetaryAmountQuarter(hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths() + quarter.getPurchasingRateBudgetaryAmountQuarter() - old.getPurchasingRateBudgetaryAmountMonthsOld());
        year.setPurchasingRateActualAmountYear(hospitalMasterIndex.getPurchasingRateActualAmountMonths() + quarter.getPurchasingRateActualAmountYear() - old.getPurchasingRateActualAmountMonthsOld());
        year.setPurchasingRateBudgetaryAmountYear(hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths() + quarter.getPurchasingRateBudgetaryAmountYear() - old.getPurchasingRateBudgetaryAmountMonthsOld());
        //质量达标
        quarter.setQualityStandardActualAmountQuarter(hospitalMasterIndex.getQualityStandardActualAmountMonths() + quarter.getQualityStandardActualAmountQuarter() - old.getQualityStandardActualAmountMonthsOld());
        quarter.setQualityStandardBudgetaryAmountQuarter(hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths() + quarter.getQualityStandardBudgetaryAmountQuarter() - old.getQualityStandardBudgetaryAmountMonthsOld());
        year.setQualityStandardActualAmountYear(hospitalMasterIndex.getQualityStandardActualAmountMonths() + quarter.getQualityStandardActualAmountYear() - old.getQualityStandardActualAmountMonthsOld());
        year.setQualityStandardBudgetaryAmountYear(hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths() + quarter.getQualityStandardBudgetaryAmountYear() - old.getQualityStandardBudgetaryAmountMonthsOld());
        year.preUpdate();
        dao.updateYear(year);
        quarter.preUpdate();
        dao.updateMonths(quarter);
    }

    @Transactional(readOnly = false)
    public void delete(HospitalMasterIndex hospitalMasterIndex) {
        super.delete(hospitalMasterIndex);
    }

    /**
     * 根据月份查询数据回显
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findListByMonths(HospitalMasterIndex hospitalMasterIndex) {
        return dao.findListByMonths(hospitalMasterIndex);
    }

    /**
     * 保存
     *
     * @param hospitalMasterIndex
     * @throws ParseException
     */
    @Transactional(readOnly = false)
    public void saveOtherHospital(HospitalMasterIndex hospitalMasterIndex) throws ParseException {
        //向数据插入的时候
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy");
        //月数据保存   里面的id是月数据表的id
        if (StringUtils.isNotBlank(hospitalMasterIndex.getId())) {
            List<HospitalMasterIndex> masterIndexes = new ArrayList<HospitalMasterIndex>();
            List<HospitalMasterIndex> list = dao.findListByMonths(hospitalMasterIndex);
            for (int i = 0; i < list.size(); i++) {
                HospitalMasterIndex hospitalMasterIndex1 = new HospitalMasterIndex();
                String s1 = sdf.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
                hospitalMasterIndex1.setOrgId(UserUtils.getUser().getCompany().getId());
                hospitalMasterIndex1.setMonth(sdf.parse(s1));
                String s2 = sd.format(hospitalMasterIndex.getMonth());  //2015-02-09  format()才是格式化
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(s1));
                int month = c.get(Calendar.MONTH)+1;
                hospitalMasterIndex1.setQuarter(getQuarterByMonth(month));
                //判断当前月份是这一年的第几个季度
                hospitalMasterIndex1.setYearQuarter(s2);
                if (StringUtils.endsWithIgnoreCase("上交集团现金", list.get(i).getEntryName())) {
                    hospitalMasterIndex1.setId(list.get(i).getId());
                    hospitalMasterIndex1.setMasterId(list.get(i).getMasterId());
                    hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getGroupCashActualAmountMonths());
                    hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getGroupCashBudgetaryAmountMonths());
                    masterIndexes.add(hospitalMasterIndex1);
                } else if (StringUtils.endsWithIgnoreCase("医疗收入", list.get(i).getEntryName())) {
                    hospitalMasterIndex1.setId(list.get(i).getId());
                    hospitalMasterIndex1.setMasterId(list.get(i).getMasterId());
                    hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getMedicalIncomeActualAmountMonths());
                    hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths());
                    masterIndexes.add(hospitalMasterIndex1);
                } else if (StringUtils.endsWithIgnoreCase("成本支出", list.get(i).getEntryName())) {
                    hospitalMasterIndex1.setId(list.get(i).getId());
                    hospitalMasterIndex1.setMasterId(list.get(i).getMasterId());
                    hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getCostExpenditureActualAmountMonths());
                    hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths());
                    masterIndexes.add(hospitalMasterIndex1);
                } else if (StringUtils.endsWithIgnoreCase("利润", list.get(i).getEntryName())) {
                    hospitalMasterIndex1.setId(list.get(i).getId());
                    hospitalMasterIndex1.setMasterId(list.get(i).getMasterId());
                    hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getProfitActualAmountMonths());
                    hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getProfitBudgetaryAmountMonths());
                    masterIndexes.add(hospitalMasterIndex1);
                } else if (StringUtils.endsWithIgnoreCase("现金运营指数", list.get(i).getEntryName())) {
                    hospitalMasterIndex1.setId(list.get(i).getId());
                    hospitalMasterIndex1.setMasterId(list.get(i).getMasterId());
                    hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getOperationIndexActualAmountMonths());
                    hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths());
                    masterIndexes.add(hospitalMasterIndex1);
                } else if (StringUtils.endsWithIgnoreCase("主渠道采购率", list.get(i).getEntryName())) {
                    hospitalMasterIndex1.setId(list.get(i).getId());
                    hospitalMasterIndex1.setMasterId(list.get(i).getMasterId());
                    hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getPurchasingRateActualAmountMonths());
                    hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths());
                    masterIndexes.add(hospitalMasterIndex1);
                } else if (StringUtils.endsWithIgnoreCase("质量达标", list.get(i).getEntryName())) {
                    hospitalMasterIndex1.setId(list.get(i).getId());
                    hospitalMasterIndex1.setMasterId(list.get(i).getMasterId());
                    hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getQualityStandardActualAmountMonths());
                    hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths());
                    masterIndexes.add(hospitalMasterIndex1);
                }
            }
            //更新医疗运营报表
            for (int j = 0; j < masterIndexes.size(); j++) {
                masterIndexes.get(j).preUpdate();
                dao.updateMonths(masterIndexes.get(j));
            }
        } else {
            Date months = hospitalMasterIndex.getMonth();
            List<HospitalMasterIndex> masterIndexes = new ArrayList<HospitalMasterIndex>();
            masterIndexes = handlerData(months, hospitalMasterIndex);


            for (int j = 0; j < masterIndexes.size(); j++) {
                masterIndexes.get(j).preInsert();
                dao.insert(masterIndexes.get(j));
            }
        }
    }


    public List<HospitalMasterIndex> handlerData(Date months, HospitalMasterIndex hospitalMasterIndex) throws ParseException {
        List<HospitalMasterIndex> masterIndexes = new ArrayList<HospitalMasterIndex>();
        List<HospitalMasterIndex> list = dao.findListByMaster(new HospitalMasterIndex());
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy");
        for (int i = 0; i < list.size(); i++) {
            HospitalMasterIndex hospitalMasterIndex1 = new HospitalMasterIndex();
            String s1 = sdf.format(months);  //2015-02-09  format()才是格式化
            hospitalMasterIndex1.setOrgId(UserUtils.getUser().getCompany().getId());
            hospitalMasterIndex1.setMonth(sdf.parse(s1));
            String s2 = sd.format(months);  //2015-02-09  format()才是格式化
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(s1));
            int month = c.get(Calendar.MONTH)+1;
            hospitalMasterIndex1.setQuarter(getQuarterByMonth(month));
            //判断当前月份是这一年的第几个季度
            hospitalMasterIndex1.setYearQuarter(s2);
            if (StringUtils.endsWithIgnoreCase("上交集团现金", list.get(i).getEntryName())) {
                hospitalMasterIndex1.setMasterId(list.get(i).getId());
                hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getGroupCashActualAmountMonths());
                hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getGroupCashBudgetaryAmountMonths());
                masterIndexes.add(hospitalMasterIndex1);
            } else if (StringUtils.endsWithIgnoreCase("医疗收入", list.get(i).getEntryName())) {
                hospitalMasterIndex1.setMasterId(list.get(i).getId());
                hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getMedicalIncomeActualAmountMonths());
                hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getMedicalIncomeBudgetaryAmountMonths());
                masterIndexes.add(hospitalMasterIndex1);
            } else if (StringUtils.endsWithIgnoreCase("成本支出", list.get(i).getEntryName())) {
                hospitalMasterIndex1.setMasterId(list.get(i).getId());
                hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getCostExpenditureActualAmountMonths());
                hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getCostExpenditureBudgetaryAmountMonths());
                masterIndexes.add(hospitalMasterIndex1);
            } else if (StringUtils.endsWithIgnoreCase("利润", list.get(i).getEntryName())) {
                hospitalMasterIndex1.setMasterId(list.get(i).getId());
                hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getProfitActualAmountMonths());
                hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getProfitBudgetaryAmountMonths());
                masterIndexes.add(hospitalMasterIndex1);
            } else if (StringUtils.endsWithIgnoreCase("现金运营指数", list.get(i).getEntryName())) {
                hospitalMasterIndex1.setMasterId(list.get(i).getId());
                hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getOperationIndexActualAmountMonths());
                hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getOperationIndexBudgetaryAmountMonths());
                masterIndexes.add(hospitalMasterIndex1);
            } else if (StringUtils.endsWithIgnoreCase("主渠道采购率", list.get(i).getEntryName())) {
                hospitalMasterIndex1.setMasterId(list.get(i).getId());
                hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getPurchasingRateActualAmountMonths());
                hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getPurchasingRateBudgetaryAmountMonths());
                masterIndexes.add(hospitalMasterIndex1);
            } else if (StringUtils.endsWithIgnoreCase("质量达标", list.get(i).getEntryName())) {
                hospitalMasterIndex1.setMasterId(list.get(i).getId());
                hospitalMasterIndex1.setGroupCashActualAmountMonths(hospitalMasterIndex.getQualityStandardActualAmountMonths());
                hospitalMasterIndex1.setGroupCashBudgetaryAmountMonths(hospitalMasterIndex.getQualityStandardBudgetaryAmountMonths());
                masterIndexes.add(hospitalMasterIndex1);
            }
        }
        return masterIndexes;
    }

    /**
     * 回显数据
     *
     * @param hospitalMasterIndex
     * @return
     */
    public HospitalMasterIndex handlerHXData(HospitalMasterIndex hospitalMasterIndex) {
        HospitalMasterIndex masterIndex = new HospitalMasterIndex();
        masterIndex.setMonth(hospitalMasterIndex.getMonth());
        List<HospitalMasterIndex> reports = dao.findListByMonths(hospitalMasterIndex);
        if (reports != null && reports.size() > 0) {
            for (int i = 0; i < reports.size(); i++) {
                masterIndex.setId(reports.get(0).getId());
                if (StringUtils.endsWithIgnoreCase("上交集团现金", reports.get(i).getEntryName())) {
                    masterIndex.setGroupCashActualAmountMonths(reports.get(i).getGroupCashActualAmountMonths());
                    masterIndex.setGroupCashBudgetaryAmountMonths(reports.get(i).getGroupCashBudgetaryAmountMonths());
                } else if (StringUtils.endsWithIgnoreCase("医疗收入", reports.get(i).getEntryName())) {
                    masterIndex.setMedicalIncomeActualAmountMonths(reports.get(i).getGroupCashActualAmountMonths());
                    masterIndex.setMedicalIncomeBudgetaryAmountMonths(reports.get(i).getGroupCashBudgetaryAmountMonths());
                } else if (StringUtils.endsWithIgnoreCase("成本支出", reports.get(i).getEntryName())) {
                    masterIndex.setCostExpenditureActualAmountMonths(reports.get(i).getGroupCashActualAmountMonths());
                    masterIndex.setCostExpenditureBudgetaryAmountMonths(reports.get(i).getGroupCashBudgetaryAmountMonths());
                } else if (StringUtils.endsWithIgnoreCase("利润", reports.get(i).getEntryName())) {
                    masterIndex.setProfitActualAmountMonths(reports.get(i).getGroupCashActualAmountMonths());
                    masterIndex.setProfitBudgetaryAmountMonths(reports.get(i).getGroupCashBudgetaryAmountMonths());
                } else if (StringUtils.endsWithIgnoreCase("现金运营指数", reports.get(i).getEntryName())) {
                    masterIndex.setOperationIndexActualAmountMonths(reports.get(i).getGroupCashActualAmountMonths());
                    masterIndex.setOperationIndexBudgetaryAmountMonths(reports.get(i).getGroupCashBudgetaryAmountMonths());
                } else if (StringUtils.endsWithIgnoreCase("主渠道采购率", reports.get(i).getEntryName())) {
                   masterIndex.setPurchasingRateActualAmountMonths(reports.get(i).getGroupCashActualAmountMonths());
                   masterIndex.setPurchasingRateBudgetaryAmountMonths(reports.get(i).getGroupCashBudgetaryAmountMonths());
                } else if (StringUtils.endsWithIgnoreCase("质量达标", reports.get(i).getEntryName())) {
                   masterIndex.setQualityStandardActualAmountMonths(reports.get(i).getGroupCashActualAmountMonths());
                    masterIndex.setQualityStandardBudgetaryAmountMonths(reports.get(i).getGroupCashBudgetaryAmountMonths());
                }
            }
        }
        return masterIndex;
    }

    /**
     * 查询全部数据
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findAllList(HospitalMasterIndex hospitalMasterIndex) {
        return dao.findAllList(hospitalMasterIndex);
    }


    /**
     * 处理数据使用
     *
     * @param hospitalMasterIndex
     * @return
     */
    public List<HospitalMasterIndex> findAllListHandler(HospitalMasterIndex hospitalMasterIndex) {
        return dao.findAllListHandler(hospitalMasterIndex);
    }

}