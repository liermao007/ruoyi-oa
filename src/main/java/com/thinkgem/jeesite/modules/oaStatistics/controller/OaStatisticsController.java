package com.thinkgem.jeesite.modules.oaStatistics.controller;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.gen.entity.GenTable;
import com.thinkgem.jeesite.modules.gen.entity.GenTableColumn;
import com.thinkgem.jeesite.modules.gen.service.GenTableService;
import com.thinkgem.jeesite.modules.oaStatistics.entity.*;
import com.thinkgem.jeesite.modules.oaStatistics.service.OaStatisticsFieldService;
import com.thinkgem.jeesite.modules.oaStatistics.service.OaStatisticsTableService;
import com.thinkgem.jeesite.modules.oaStatistics.service.OastaticsNameService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/OaStatistics/oaStatistics")
public class OaStatisticsController extends BaseController {
    @Autowired
    private OastaticsNameService oastaticsNameService;

    @Autowired
    private OaStatisticsTableService oaStatisticsTableService;

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;

    @Autowired
    private OaStatisticsFieldService oaStatisticsFieldService;

    @Autowired
    private OfficeService officeService;


    /**
     * 统计名称遍历页面
     * @param model
     * @return
     */
    @RequestMapping(value = "index")
    public String oaStatisticsIndex(Model model){
        OaStatisticsName oaStatisticsName = new OaStatisticsName();
        User user=UserUtils.getUser();
        if(UserUtils.getUser().getId().equals("1")){
            System.out.println(UserUtils.getUser().getCompany().getId());
        }else{
            oaStatisticsName.setOrgId(UserUtils.getUser().getCompany().getId());
        }
        model.addAttribute("oaStatisticsName",oastaticsNameService.findAllList(oaStatisticsName));
        return "modules/oaStatistics/oaStatisticsName";
    }

    //进入添加统计名称页面
    @RequestMapping(value = "addName")
    public String oaStatisticsAddName(Model model){
        model.addAttribute("oaStatisticsName",new OaStatisticsName());
        return "modules/oaStatistics/oaStatisticsNameAddFrom";
    }

    //进入修改统计名称页面
    @RequestMapping(value = "updateName")
    public String oaStatisticsUpdateName(String id, Model model){
        model.addAttribute("flag","update");
        model.addAttribute("oastaticsName",oastaticsNameService.get(id));
        return "modules/oaStatistics/oaStatisticsNameFrom";
    }

    //添加统计名称
    @RequestMapping(value = "save")
    public String save(HttpServletRequest request,OaStatisticsName oaStatisticsName,Model model,RedirectAttributes redirectAttributes){
        oaStatisticsName.setOrgId(UserUtils.getUser().getCompany().getId());
        String id = oastaticsNameService.add(oaStatisticsName);
        return "redirect:"+adminPath+"/OaStatistics/oaStatistics/addParticulars?TJID="+id+"&TJMC="+oaStatisticsName.getStatisticsName();
    }

    //修改统计名称
    @RequestMapping(value = "update")
    public String  update(OaStatisticsName oaStatisticsName, Model model, RedirectAttributes redirectAttributes){
        oastaticsNameService.update(oaStatisticsName);
        addMessage(redirectAttributes, "修改成功");
        return "redirect:"+adminPath+"/OaStatistics/oaStatistics/index?repage";
    }

    //删除统计
    @RequestMapping(value = "delete")
    public String delete(HttpServletRequest request,String id,Model model,RedirectAttributes redirectAttributes){
        //清除字段表数据
        OaStatisticsField oaStatisticsField = new OaStatisticsField();
        oaStatisticsField.setStatisticsId(id);
        oaStatisticsFieldService.delete(oaStatisticsField);
        //清除table表数据
        OaStatisticsTable oaStatisticsTable = new OaStatisticsTable();
        oaStatisticsTable.setStatisticsId(id);
        oaStatisticsTableService.delete(oaStatisticsTable);
        //清楚name表数据
        OaStatisticsName oaStatisticsName=new OaStatisticsName();
        oaStatisticsName.setId(id);
        oastaticsNameService.delete(oaStatisticsName);
        addMessage(redirectAttributes, "删除成功");
        return "redirect:"+adminPath+"/OaStatistics/oaStatistics/index?repage";
    }

    //进入统计表页面
    @RequestMapping(value = "particulars")
    public String particulars(String id,Model model){
        List<OaStatisticsTable> list = oaStatisticsTableService.findAll(id);
        for (OaStatisticsTable oaStatisticsTable: list){
            oaStatisticsTable.setOaStatisticsFields(oaStatisticsFieldService.getFiled(oaStatisticsTable.getId()));
        }
       model.addAttribute("oastaticsName",list);
        model.addAttribute("TJMC",oastaticsNameService.get(id));
       return "modules/oaStatistics/oaStatisticsParticulars";
    }

    //进入统计表配置页面
    @RequestMapping(value = "field")
    public String field(String id,Model model){
        OaStatisticsTable oaStatisticsTable1 = new OaStatisticsTable();
        oaStatisticsTable1.setId(id);
       OaStatisticsTable oaStatisticsTable = oaStatisticsTableService.get(oaStatisticsTable1);
       List<OaStatisticsField> list = oaStatisticsFieldService.getOaStatisticsTableId(id);
       model.addAttribute("oaStatisticsTable",oaStatisticsTable);
       model.addAttribute("list",list);
       return "modules/oaStatistics/oaStatisticsField";
    }

    //进入添加统计表页面
    @RequestMapping(value = "addParticulars")
    public String addParticulars(String TJID,OaPersonDefineTable oaPersonDefineTable,Model model, HttpServletRequest request, HttpServletResponse response){
//        model.addAttribute("tableList",genTableService.findTableListFormDb(new GenTable()));
        if(UserUtils.getUser().getId().equals("1")){
            Office office =new Office();
            office.setId("1");
            oaPersonDefineTable.setOffice(office);
        }else{
            Office office =new Office();
           String orgId = UserUtils.getUser().getCompany().getId();
            office.setId(orgId);
            oaPersonDefineTable.setOffice(office);
        }

        String orgId = UserUtils.getUser().getCompany().getId();
        List list = new ArrayList();
        if (UserUtils.getUser().getId().equals("1")){
            list = officeService.findList(UserUtils.getUser().getCompany());
        }else {
            list = officeService.findCompany(orgId);
        }
        model.addAttribute("subordinate",list);
        model.addAttribute("table",new OaStatisticsTable());
        model.addAttribute("tableList", oaPersonDefineTableService.findList(oaPersonDefineTable));
        model.addAttribute("TJMC",oastaticsNameService.get(TJID));
        return "modules/oaStatistics/oaStatisticalAddTable";
    }

    //获取本机构数据表  This institution
    @RequestMapping(value = "thisInstitution")
    @ResponseBody
    public List thisInstitution(OaPersonDefineTable oaPersonDefineTable){
        if(UserUtils.getUser().getId().equals("1")){
            Office office =new Office();
            office.setId("1");
            oaPersonDefineTable.setOffice(office);
        }else{
            Office office =new Office();
            String orgId = UserUtils.getUser().getCompany().getId();
            office.setId(orgId);
            oaPersonDefineTable.setOffice(office);
        }
        return oaPersonDefineTableService.findList(oaPersonDefineTable);
    }
    //获取数据表列
    @RequestMapping(value = "columns")
    @ResponseBody
    public OaPersonDefineTable columns(String tableId){
        OaPersonDefineTable oaPersonDefineTable = oaPersonDefineTableService.get(tableId);
        return oaPersonDefineTable;
    }
    //获取下属机构列表
    @RequestMapping(value = "organization")
    @ResponseBody
    public List organization(){
        String orgId = UserUtils.getUser().getCompany().getId();
        List list = new ArrayList();
        if (UserUtils.getUser().getId().equals("1")){
            list = officeService.findList(UserUtils.getUser().getCompany());
        }else {
            list = officeService.findCompany(orgId);
        }
        return list;
    }

//    通过机构ID获取自定义数据表 Agency table
    @RequestMapping(value = "agencyTable")
    @ResponseBody
    public List agencyTable(OaPersonDefineTable oaPersonDefineTable,String orgId){
        Office office =new Office();
        office.setId(orgId);
        oaPersonDefineTable.setOffice(office);
        List list = oaPersonDefineTableService.findList(oaPersonDefineTable);
        return list;
    }

    //添加统计表
    @RequestMapping(value = "saveTable")
    public String saveTable(String tableID,String remarks,String TjmcId, String dataTable, String []checkbox,String TJBMC,RedirectAttributes redirectAttributes){
        //添加统计表名称
        OaStatisticsTable oaStatisticsTable = new OaStatisticsTable();
        oaStatisticsTable.setStatisticsId(TjmcId);
        oaStatisticsTable.setStatisticsName(TJBMC);
        oaStatisticsTable.setDataTable(dataTable);
        oaStatisticsTable.setRemarks(remarks);
        String tableId = oaStatisticsTableService.saveTable(oaStatisticsTable);
//        添加统计表字段（所有）
        OaPersonDefineTable oaPersonDefineTable = oaPersonDefineTableService.get(tableID);

        List<OaStatisticsField> list = new ArrayList<>();
        for (OaPersonDefineTableColumn oaPersonDefineTableColumn:oaPersonDefineTable.getOaPersonDefineTableColumnList()){
            OaStatisticsField oaStatisticsField = new OaStatisticsField();
            oaStatisticsField.setStatisticsId(TjmcId);
            oaStatisticsField.setStatisticsTableId(tableId);
            oaStatisticsField.setFieldName(oaPersonDefineTableColumn.getColumnName());
            oaStatisticsField.setRemarks(oaPersonDefineTableColumn.getColumnComment());
            oaStatisticsField.setDataType(oaPersonDefineTableColumn.getRemarks());
            oaStatisticsField.setControlTypeId(oaPersonDefineTableColumn.getControlTypeId());
            oaStatisticsField.setColumnType(oaPersonDefineTableColumn.getColumnType());
            oaStatisticsField.setFlag("1");
            oaStatisticsField.preInsert();
            list.add(oaStatisticsField);
        }
        oaStatisticsFieldService.addList(list);

        //保存已选中的字段
        oaStatisticsFieldService.updateAddField(checkbox,tableId);
        addMessage(redirectAttributes, "添加成功");
        return "redirect:"+adminPath+"/OaStatistics/oaStatistics/particulars?repage&id="+TjmcId;
    }

    //修改统计表配置
    @RequestMapping(value = "updateField")
    public String updateField(RedirectAttributes redirectAttributes,String TJId,String [] rank,String [] saveId,String tableId){
        oaStatisticsFieldService.updateField(rank,saveId,tableId);
        addMessage(redirectAttributes, "修改成功");
        return "redirect:"+adminPath+"/OaStatistics/oaStatistics/particulars?repage&id="+TJId;
    }

    //删除统计表
    @RequestMapping(value = "deleteTable")
    public String  deleteTable(RedirectAttributes redirectAttributes,String id,String TJID){
        //清除字段表数据
        OaStatisticsField oaStatisticsField = new OaStatisticsField();
        oaStatisticsField.setStatisticsTableId(id);
        oaStatisticsFieldService.delete(oaStatisticsField);
        //清除table表数据
        OaStatisticsTable oaStatisticsTable = new OaStatisticsTable();
        oaStatisticsTable.setId(id);
        oaStatisticsTableService.delete(oaStatisticsTable);
        addMessage(redirectAttributes, "删除成功");
        return "redirect:"+adminPath+"/OaStatistics/oaStatistics/particulars?repage&id="+TJID;

    }


    //获取显示数据（单表）
    @RequestMapping(value = "gettingData")
    public String gettingData(DataParameter dataParameter,String tableName,String tableId,Model model){
        OaStatisticsTable oaStatisticsTable = new OaStatisticsTable();
        oaStatisticsTable.setId(tableId);
        model.addAttribute("table",oaStatisticsTableService.get(oaStatisticsTable));
        model.addAttribute("tableId",tableId);
        dataParameter.setTableName(tableName);
        dataParameter.setStartDate(dataParameter.getStartDate());
        dataParameter.setEndDate(dataParameter.getEndDate());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (dataParameter.getStartDate() != null && !("".equals(dataParameter.getStartDate()))){
            model.addAttribute("startDate",dataParameter.getStartDate());
        }
        if (dataParameter.getEndDate() != null && !("".equals(dataParameter.getEndDate()))){
            model.addAttribute("endDate",dataParameter.getEndDate());
        }
        if (dataParameter.getApplicationDate() != null && !("".equals(dataParameter.getApplicationDate()))){
            model.addAttribute("applicationDate",dataParameter.getApplicationDate());
        }
        if (dataParameter.getProposer() != null && !"".equals(dataParameter.getProposer())){

//        if (dataParameter.getProposer().length()>1){
//            String proposer = dataParameter.getProposer().substring(0,dataParameter.getProposer().length()-1);
//            dataParameter.setProposer(proposer);
            model.addAttribute("proposer",dataParameter.getProposer());
//        }else {
//            dataParameter.setProposer("");
//        }
        }
        List<OaStatisticsField> newFieldList = new ArrayList<>();   //显示字段对象
        List<String> newFieldNameList = new ArrayList<>();          //显示字段名称
        List<OaStatisticsField> fieldList = oaStatisticsFieldService.getFiled(tableId);
        List<String> fieldNameList = oaStatisticsFieldService.getFiledName(tableId);
        model.addAttribute("rawFieldList",fieldList);
        model.addAttribute("rawFieldNameList",fieldNameList);
        if (dataParameter.getConditionSumField() != null && ! ("".equals(dataParameter.getConditionSumField()))){
            for (OaStatisticsField field: fieldList){
                if (field.getFieldName().equals(dataParameter.getConditionSumField())){
//                    field.setFieldName("sum");
                    newFieldList.add(field);
                }
            if (Arrays.asList(dataParameter.getConditionGroupField()).contains(field.getFieldName())){
                newFieldList.add(field);
            }
            }
            for (int i = 0;i < fieldNameList.size();i++){
                if (fieldNameList.get(i).equals(dataParameter.getConditionSumField())){
//                    newFieldNameList.add("sum");

                }
                if (Arrays.asList(dataParameter.getConditionGroupField()).contains(fieldNameList.get(i))){
                    newFieldNameList.add(fieldNameList.get(i));
                }
            }
            model.addAttribute("newFieldNameList",newFieldNameList);
            model.addAttribute("newFieldList",newFieldList);
            model.addAttribute("conditionSumField",dataParameter.getConditionSumField());
        }else {
            model.addAttribute("newFieldNameList",null);
            model.addAttribute("newFieldList",null);
            newFieldList.addAll(fieldList);
            newFieldNameList.addAll(fieldNameList);
        }
        model.addAttribute("existingConditions",dataParameter.getExistingConditions());
        model.addAttribute("existingConditionsName",dataParameter.getExistingConditionsName());
        model.addAttribute("tableName",tableName);
        List fields=oaStatisticsFieldService.getFiledName(tableId);
        dataParameter.setFieldList(fields);
        if (fields != null && fields.size()>0){
            List<Map<String,Object>> list=oaStatisticsFieldService.gettingData(dataParameter);
            model.addAttribute("list",list);
            model.addAttribute("fields",newFieldList);
            model.addAttribute("fieldNames",newFieldNameList);
            return "modules/oaStatistics/oaStatisticaDataList";
        }else {
            return "modules/oaStatistics/oaStatisticaDataList";
        }
    }


    @RequestMapping(value = "gettingSelected")
    @ResponseBody
    public List gettingSelected(String tableId){
        return oaStatisticsFieldService.getFiled(tableId);
    }


    @RequestMapping(value = "gettingAllData")
    public String gettingAllData(DataParameter dataParameter,String TJMCID,Model model){
        List<DataParameter> list = new ArrayList();
        List<OaStatisticsField> fields = new ArrayList<>();
        List<OaStatisticsTable> tables=oaStatisticsTableService.findAll(TJMCID);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (dataParameter.getStartDate() != null && !("".equals(dataParameter.getStartDate()))){
            model.addAttribute("startDate",dataParameter.getStartDate());
        }
        if (dataParameter.getEndDate() != null && !("".equals(dataParameter.getEndDate()))){
            model.addAttribute("endDate",dataParameter.getEndDate());
        }
        if (dataParameter.getApplicationDate() != null && !("".equals(dataParameter.getApplicationDate()))){
            model.addAttribute("applicationDate",dataParameter.getApplicationDate());
        }
        if (dataParameter.getProposer() != null && !("".equals(dataParameter.getProposer()))){
            model.addAttribute("proposer",dataParameter.getProposer());
        }
        if (tables.size()<=1){
            return "redirect:"+adminPath+"/OaStatistics/oaStatistics/gettingData?repage&tableId="+tables.get(0).getId()+"&tableName="+tables.get(0).getDataTable();
        }
        for (OaStatisticsTable oaStatisticsTable:tables){
            List<String> fieldName=oaStatisticsFieldService.getFiledName(oaStatisticsTable.getId());
            DataParameter dataParameter1 = new DataParameter();
            dataParameter1.setTableName(oaStatisticsTable.getDataTable());
            dataParameter1.setFieldList(fieldName);
            dataParameter1.setStartDate(dataParameter.getStartDate());
            dataParameter1.setEndDate(dataParameter.getEndDate());
            dataParameter1.setApplicationDate(dataParameter.getApplicationDate());
            dataParameter1.setProposer(dataParameter.getProposer());
            list.add(dataParameter1);
            fields.addAll(oaStatisticsFieldService.getFiled(oaStatisticsTable.getId()));
        }
        List<List<Map<String,Object>>> data = oaStatisticsFieldService.gettingAllData(list);
        model.addAttribute("TJMCID",TJMCID);
        model.addAttribute("data",data);
        model.addAttribute("fields",oaStatisticsFieldService.getFiled(tables.get(0).getId()));
        return "modules/oaStatistics/oaStatisticaAllDataList";
    }

    @RequestMapping(value = "serial")
    public String serial(RedirectAttributes redirectAttributes,String TJID,String [] fieldId,String [] serial){
        oaStatisticsFieldService.serial(fieldId,serial);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:"+adminPath+"/OaStatistics/oaStatistics/particulars?repage&id="+TJID;
    }

    //查看详情调取数据（废弃，不使用）
    @RequestMapping(value = "details")
    public String details(String tableName,String dataId,Model model){
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        genTable = genTableService.getTableFormDb(genTable);
        List<String> fieldName = new ArrayList<>();
        for (GenTableColumn genTableColumn:genTable.getColumnList()){
            fieldName.add(genTableColumn.getName());
        }
        DataParameter dataParameter = new DataParameter();
        dataParameter.setTableName(tableName);
        dataParameter.setDataId(dataId);
        dataParameter.setFieldList(fieldName);
        List<List<Map<String,Object>>> list = oaStatisticsFieldService.details(dataParameter);
        model.addAttribute("data",list);
        List<GenTableColumn> list1 = genTable.getColumnList();
        model.addAttribute("columnList",list1);
        return "modules/oaStatistics/detailsList";
    }
    @RequestMapping(value = "gettingAllVerify")
    @ResponseBody
    public boolean gettingAllVerify(String TJMCID){
        List<OaStatisticsTable> tables = oaStatisticsTableService.findAll(TJMCID);
        List<Integer> length = new ArrayList<>();
        for (OaStatisticsTable oaStatisticsTable : tables){
            List<String> fieldNames = oaStatisticsFieldService.getFiledName(oaStatisticsTable.getId());
            length.add(fieldNames.size());
        }
        boolean verify = true;
        Integer lastLength = length.get(0);
        if (length.size() > 1){
            for (int i = 1; i<length.size();i++){
                if (lastLength != length.get(i)){
                    return false;
                }
                lastLength = length.get(i);
            }
        }
        return true;
    }


    @RequestMapping(value = "export")
    public String exportFile(String TJMCID,DataParameter dataParameter,String tableName,String tableId,HttpServletResponse response) throws IOException {
        if (TJMCID == null || "".equals(TJMCID)) {       //如果统计名称为空，那么数据为单表数据
            OaStatisticsTable oaStatisticsTable = new OaStatisticsTable();
            oaStatisticsTable.setId(tableId);
            oaStatisticsTable = oaStatisticsTableService.get(oaStatisticsTable);
            dataParameter.setTableName(tableName);
            dataParameter.setStartDate(dataParameter.getStartDate());
            dataParameter.setEndDate(dataParameter.getEndDate());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //数据列名称
            List<String> fieldNames = oaStatisticsFieldService.getFiledName(tableId);
            //数据
            List<Map<String, Object>> list = null;
            //数据列对象
            List<OaStatisticsField> fields = oaStatisticsFieldService.getFiled(tableId);
            ;
            dataParameter.setFieldList(fieldNames);
            if (fieldNames != null && fieldNames.size() > 0) {
                list = oaStatisticsFieldService.gettingData(dataParameter);
            }
            HSSFWorkbook workbook = exportExcel(oaStatisticsTable.getId(),list,fieldNames,fields,null);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" +oaStatisticsTable.getId()+".xls");
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        }

        return null;
    }


    //导出Excel（废弃，不使用此导出方式）
    private HSSFWorkbook exportExcel(String title, List<Map<String, Object>> data, List<String> fieldNames, List<OaStatisticsField> fields, String pattern) {
        if(pattern == null || pattern.equals("")) pattern = "yyy-MM-dd"; //规定日期格式
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 把字体应用到当前的样式
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体

        // 把字体应用到当前的样式
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        int Cell = 0;
        for (short i = 0; i < fieldNames.size(); i++) {
            HSSFCell cell = row.createCell(Cell);
            cell.setCellStyle(style);
            OaStatisticsField field = fields.get(i);
            HSSFRichTextString text = new HSSFRichTextString(field.getRemarks());
            cell.setCellValue(text);
            Cell ++ ;
        }
        //创建行数据     List<List<Map<String, Object>>> data
        //OaStatisticsField field: fields
        for (int j = 0;j<data.size();j++){
            Map<String,Object> map = data.get(j);
            int number = 0;
            HSSFRow rowData = sheet.createRow(j+1);
            for (short i = 0; i < fieldNames.size(); i++) {
//                if (Cell>=255){
//                    break;
//                }
                HSSFCell cell = rowData.createCell(number);
                cell.setCellStyle(style);
                OaStatisticsField field = fields.get(i);
                if ("DATE".equals(field.getColumnType())){
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    HSSFRichTextString text = new HSSFRichTextString(format.format(map.get(field.getFieldName())));
                    cell.setCellValue(text);
                }else {
                    HSSFRichTextString text = new HSSFRichTextString((String) map.get(field.getFieldName()));
                    cell.setCellValue(text);
                }
                number ++ ;
            }
        }
        return workbook;
    }

//    查询条件配置

//    1、用于获取已选/未选字段
    @RequestMapping(value = "fieldType")
    @ResponseBody
    public List<OaStatisticsField> fieldType(String tableId,String flag){
        OaStatisticsField oaStatisticsField = new OaStatisticsField();
        oaStatisticsField.setStatisticsTableId(tableId);
        oaStatisticsField.setFlag(flag);
        return oaStatisticsFieldService.fieldType(oaStatisticsField);
    }

    @RequestMapping(value = "statisticalDetails")
    public String statisticalDetails(String tableId,ParameterField parameterField,Model model){
        model.addAttribute("field",oaStatisticsFieldService.getFiled(tableId));
        parameterField.setFieldList(oaStatisticsFieldService.getFiledName(tableId));
        model.addAttribute("data",oaStatisticsFieldService.statisticalDetails(parameterField));
        model.addAttribute("tableName",parameterField.getTableName());
        return "modules/oaStatistics/statisticalDetails-1";
    }
}
