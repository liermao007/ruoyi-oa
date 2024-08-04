package com.baomidou.kisso.my.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.disease.domain.DiseaseMentalHospitalout;
import com.ruoyi.disease.service.IDiseaseMentalHospitaloutService;
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
 * 严重精神障碍患者(含重性精神疾病)出院信息单Controller
 *
 * @author ruoyi
 * @date 2023-05-07
 */
@Controller
@RequestMapping("/disease/hospitalout")
public class DiseaseMentalHospitaloutController extends BaseController {
    private String prefix = "manage/hospitalout";

    @Autowired
    private IDiseaseMentalHospitaloutService diseaseMentalHospitaloutService;
    @Autowired
    private IDiseaseMentalMedicationService diseaseMentalMedicationService;

    @RequiresPermissions("disease:hospitalout:view")
    @GetMapping()
    public String hospitalout() {
        return prefix + "/hospitalout";
    }

    /**
     * 查询严重精神障碍患者(含重性精神疾病)出院信息单列表
     */
    @RequiresPermissions("disease:hospitalout:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DiseaseMentalHospitalout diseaseMentalHospitalout) {
        startPage();
        List<DiseaseMentalHospitalout> list = diseaseMentalHospitaloutService.selectDiseaseMentalHospitaloutList(diseaseMentalHospitalout);
        return getDataTable(list);
    }

    /**
     * 导出严重精神障碍患者(含重性精神疾病)出院信息单列表
     */
    @RequiresPermissions("disease:hospitalout:export")
    @Log(title = "严重精神障碍患者(含重性精神疾病)出院信息单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DiseaseMentalHospitalout diseaseMentalHospitalout) {
        List<DiseaseMentalHospitalout> list = diseaseMentalHospitaloutService.selectDiseaseMentalHospitaloutList(diseaseMentalHospitalout);
        ExcelUtil<DiseaseMentalHospitalout> util = new ExcelUtil<DiseaseMentalHospitalout>(DiseaseMentalHospitalout.class);
        return util.exportExcel(list, "严重精神障碍患者(含重性精神疾病)出院信息单数据");
    }

    /**
     * 新增严重精神障碍患者(含重性精神疾病)出院信息单
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        DiseaseMentalHospitalout info = new DiseaseMentalHospitalout();
        info.setPatientId(ShiroUtils.getCurrentOrgId() + DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomString(4));
        info.setInhospitalMethod("");
        info.setNextMethod("");
        info.setMentallist("");
        info.setEmBehavior("");
        info.setIsDrug("112002");
        info.setIsNextdrug("112002");
        mmap.put("info", info);
        return prefix + "/add";
    }

    /**
     * 新增保存严重精神障碍患者(含重性精神疾病)出院信息单
     */
    @RequiresPermissions("disease:hospitalout:add")
    @Log(title = "严重精神障碍患者(含重性精神疾病)出院信息单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DiseaseMentalHospitalout diseaseMentalHospitalout) {
        return toAjax(diseaseMentalHospitaloutService.insertDiseaseMentalHospitalout(diseaseMentalHospitalout));
    }

    /**
     * 修改严重精神障碍患者(含重性精神疾病)出院信息单
     */
    @RequiresPermissions("disease:hospitalout:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        DiseaseMentalHospitalout diseaseMentalHospitalout = diseaseMentalHospitaloutService.selectDiseaseMentalHospitaloutById(id);
        mmap.put("info", diseaseMentalHospitalout);
        return prefix + "/add";
    }

    /**
     * 修改保存严重精神障碍患者(含重性精神疾病)出院信息单
     */
    @RequiresPermissions("disease:hospitalout:edit")
    @Log(title = "严重精神障碍患者(含重性精神疾病)出院信息单", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult editSave(DiseaseMentalHospitalout diseaseMentalHospitalout) {
        if(diseaseMentalHospitalout.getId() == null) {
            return toAjax(diseaseMentalHospitaloutService.insertDiseaseMentalHospitalout(diseaseMentalHospitalout));
        }
        return toAjax(diseaseMentalHospitaloutService.updateDiseaseMentalHospitalout(diseaseMentalHospitalout));
    }

    /**
     * 删除严重精神障碍患者(含重性精神疾病)出院信息单
     */
    @RequiresPermissions("disease:hospitalout:remove")
    @Log(title = "严重精神障碍患者(含重性精神疾病)出院信息单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(diseaseMentalHospitaloutService.deleteDiseaseMentalHospitaloutByIds(ids));
    }

    @PostMapping("/exportXml")
    @ResponseBody
    public AjaxResult exportToXml(DiseaseMentalHospitalout diseaseMentalHospitalout) {
        return diseaseMentalHospitaloutService.exportToXml(diseaseMentalHospitalout);
    }
}
