package com.baomidou.kisso.my.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.disease.domain.DiseaseMentalHospitalcardmzMedication;
import com.ruoyi.disease.service.IDiseaseMentalHospitalcardmzMedicationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 门诊报告卡-用药情况Controller
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Controller
@RequestMapping("/disease/hospitalcardmz/medication")
public class DiseaseMentalHospitalcardmzMedicationController extends BaseController
{
    private String prefix = "disease/hospitalcardmz/medication";

    @Autowired
    private IDiseaseMentalHospitalcardmzMedicationService diseaseMentalHospitalcardmzMedicationService;

    @RequiresPermissions("disease:hospitalcardmz:medication:view")
    @GetMapping()
    public String medication()
    {
        return prefix + "/medication";
    }

    /**
     * 查询门诊报告卡-用药情况列表
     */
    @RequiresPermissions("disease:hospitalcardmz:medication:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DiseaseMentalHospitalcardmzMedication diseaseMentalHospitalcardmzMedication)
    {
        startPage();
        List<DiseaseMentalHospitalcardmzMedication> list = diseaseMentalHospitalcardmzMedicationService.selectDiseaseMentalHospitalcardmzMedicationList(diseaseMentalHospitalcardmzMedication);
        return getDataTable(list);
    }

    /**
     * 导出门诊报告卡-用药情况列表
     */
    @RequiresPermissions("disease:hospitalcardmz:medication:export")
    @Log(title = "门诊报告卡-用药情况", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DiseaseMentalHospitalcardmzMedication diseaseMentalHospitalcardmzMedication)
    {
        List<DiseaseMentalHospitalcardmzMedication> list = diseaseMentalHospitalcardmzMedicationService.selectDiseaseMentalHospitalcardmzMedicationList(diseaseMentalHospitalcardmzMedication);
        ExcelUtil<DiseaseMentalHospitalcardmzMedication> util = new ExcelUtil<DiseaseMentalHospitalcardmzMedication>(DiseaseMentalHospitalcardmzMedication.class);
        return util.exportExcel(list, "门诊报告卡-用药情况数据");
    }

    /**
     * 新增门诊报告卡-用药情况
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存门诊报告卡-用药情况
     */
    @RequiresPermissions("disease:hospitalcardmz:medication:add")
    @Log(title = "门诊报告卡-用药情况", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DiseaseMentalHospitalcardmzMedication diseaseMentalHospitalcardmzMedication)
    {
        return toAjax(diseaseMentalHospitalcardmzMedicationService.insertDiseaseMentalHospitalcardmzMedication(diseaseMentalHospitalcardmzMedication));
    }

    /**
     * 修改门诊报告卡-用药情况
     */
    @RequiresPermissions("disease:hospitalcardmz:medication:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DiseaseMentalHospitalcardmzMedication diseaseMentalHospitalcardmzMedication = diseaseMentalHospitalcardmzMedicationService.selectDiseaseMentalHospitalcardmzMedicationById(id);
        mmap.put("diseaseMentalHospitalcardmzMedication", diseaseMentalHospitalcardmzMedication);
        return prefix + "/edit";
    }

    /**
     * 修改保存门诊报告卡-用药情况
     */
    @RequiresPermissions("disease:hospitalcardmz:medication:edit")
    @Log(title = "门诊报告卡-用药情况", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DiseaseMentalHospitalcardmzMedication diseaseMentalHospitalcardmzMedication)
    {
        return toAjax(diseaseMentalHospitalcardmzMedicationService.updateDiseaseMentalHospitalcardmzMedication(diseaseMentalHospitalcardmzMedication));
    }

    /**
     * 删除门诊报告卡-用药情况
     */
    @RequiresPermissions("disease:hospitalcardmz:medication:remove")
    @Log(title = "门诊报告卡-用药情况", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(diseaseMentalHospitalcardmzMedicationService.deleteDiseaseMentalHospitalcardmzMedicationByIds(ids));
    }
}
