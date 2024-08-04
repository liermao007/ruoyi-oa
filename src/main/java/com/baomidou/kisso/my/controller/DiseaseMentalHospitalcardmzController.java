package com.baomidou.kisso.my.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.disease.domain.DiseaseMentalHospitalcardmz;
import com.ruoyi.disease.service.IDiseaseMentalHospitalcardmzService;
import com.ruoyi.disease.service.IDiseaseMentalMedicationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 门诊报告卡Controller
 *
 * @author ruoyi
 * @date 2023-05-07
 */
@Controller
@RequestMapping("/disease/hospitalcardmz")
public class DiseaseMentalHospitalcardmzController extends BaseController {
    private String prefix = "manage/hospitalcardmz";

    @Autowired
    private IDiseaseMentalHospitalcardmzService diseaseMentalHospitalcardmzService;
    @Autowired
    private IDiseaseMentalMedicationService diseaseMentalMedicationService;

    @RequiresPermissions("disease:hospitalcardmz:view")
    @GetMapping()
    public String hospitalcardmz() {
        return prefix + "/hospitalcardmz";
    }

    /**
     * 查询门诊报告卡列表
     */
    @RequiresPermissions("disease:hospitalcardmz:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DiseaseMentalHospitalcardmz diseaseMentalHospitalcardmz) {
        startPage();
        List<DiseaseMentalHospitalcardmz> list = diseaseMentalHospitalcardmzService.selectDiseaseMentalHospitalcardmzList(diseaseMentalHospitalcardmz);
        return getDataTable(list);
    }

    /**
     * 导出门诊报告卡列表
     */
    @RequiresPermissions("disease:hospitalcardmz:export")
    @Log(title = "门诊报告卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DiseaseMentalHospitalcardmz diseaseMentalHospitalcardmz) {
        List<DiseaseMentalHospitalcardmz> list = diseaseMentalHospitalcardmzService.selectDiseaseMentalHospitalcardmzList(diseaseMentalHospitalcardmz);
        ExcelUtil<DiseaseMentalHospitalcardmz> util = new ExcelUtil<DiseaseMentalHospitalcardmz>(DiseaseMentalHospitalcardmz.class);
        return util.exportExcel(list, "门诊报告卡数据");
    }

    /**
     * 新增门诊报告卡
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        DiseaseMentalHospitalcardmz cardmz = new DiseaseMentalHospitalcardmz();
        cardmz.setCardId(ShiroUtils.getCurrentOrgId() + DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomString(4));
        cardmz.setIsDrug("112002");
        cardmz.setEmBehavior("");
        mmap.put("info", cardmz);
        return prefix + "/add";
    }

    /**
     * 新增保存门诊报告卡
     */
    @RequiresPermissions("disease:hospitalcardmz:add")
    @Log(title = "门诊报告卡", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DiseaseMentalHospitalcardmz diseaseMentalHospitalcardmz) {
        return toAjax(diseaseMentalHospitalcardmzService.insertDiseaseMentalHospitalcardmz(diseaseMentalHospitalcardmz));
    }

    /**
     * 修改门诊报告卡
     */
    @RequiresPermissions("disease:hospitalcardmz:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        DiseaseMentalHospitalcardmz diseaseMentalHospitalcardmz = diseaseMentalHospitalcardmzService.selectDiseaseMentalHospitalcardmzById(id);
        mmap.put("info", diseaseMentalHospitalcardmz);
        return prefix + "/add";
    }

    /**
     * 修改保存门诊报告卡
     */
    @RequiresPermissions("disease:hospitalcardmz:edit")
    @Log(title = "门诊报告卡", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DiseaseMentalHospitalcardmz diseaseMentalHospitalcardmz) {
        if (diseaseMentalHospitalcardmz.getId() == null) {
            return toAjax(diseaseMentalHospitalcardmzService.insertDiseaseMentalHospitalcardmz(diseaseMentalHospitalcardmz));
        }
        return toAjax(diseaseMentalHospitalcardmzService.updateDiseaseMentalHospitalcardmz(diseaseMentalHospitalcardmz));
    }

    /**
     * 删除门诊报告卡
     */
    @RequiresPermissions("disease:hospitalcardmz:remove")
    @Log(title = "门诊报告卡", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(diseaseMentalHospitalcardmzService.deleteDiseaseMentalHospitalcardmzByIds(ids));
    }

    @PostMapping("/exportXml")
    @ResponseBody
    public AjaxResult exportToXml(DiseaseMentalHospitalcardmz diseaseMentalHospitalcardmz) {
        return diseaseMentalHospitalcardmzService.exportToXml(diseaseMentalHospitalcardmz);
    }
}
