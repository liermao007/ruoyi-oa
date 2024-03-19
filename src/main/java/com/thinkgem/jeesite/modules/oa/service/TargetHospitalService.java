/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.units.OConvertUtils;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableColumnDao;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.TargetHospital;
import com.thinkgem.jeesite.modules.oa.dao.TargetHospitalDao;

/**
 * 标的医院综合评价Service
 *
 * @author oa
 * @version 2017-04-06
 */
@Service
@Transactional(readOnly = true)
public class TargetHospitalService extends CrudService<TargetHospitalDao, TargetHospital> {

    @Autowired
    private TargetHospitalDao targetHospitalDao;

    @Autowired
    private OaPersonDefineTableDao oaPersonDefineTableDao;
    @Autowired
    private OaPersonDefineTableColumnDao oaPersonDefineTableColumnDao;

    @Autowired
    private FlowService flowService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OfficeDao officeDao;

    public TargetHospital get(String id) {
        return super.get(id);
    }

    public List<TargetHospital> findList(TargetHospital targetHospital) {
        return super.findList(targetHospital);
    }

    public TargetHospital getById(TargetHospital targetHospital) {
        return targetHospitalDao.getById(targetHospital);
    }


    public List<TargetHospital> findListById(TargetHospital targetHospital) {
        return targetHospitalDao.findListById(targetHospital);
    }

    public Page<TargetHospital> findPage(Page<TargetHospital> page, TargetHospital targetHospital) {
        return super.findPage(page, targetHospital);
    }

    @Transactional(readOnly = false)
    public void save(TargetHospital targetHospital) {
        super.save(targetHospital);
    }

    @Transactional(readOnly = false)
    public void delete(TargetHospital targetHospital) {
        super.delete(targetHospital);
    }

    @Transactional(readOnly = false)
    public void saveFlowData(FlowData flowData) {

        String tableName = flowData.getTableName();
        Map<String, Object> data = flowData.getDatas();
        String userId = "";
        String createById = "";
        String createName = "";
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName, null);
        if (defineTable != null && data != null && StringUtils.isNotBlank(tableName)) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            dataAdapter(columns, data);
            String comma = "";
            String dept = "";
            StringBuffer insertKey = new StringBuffer();
            StringBuffer insertValue = new StringBuffer();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                // 判断key是否为表配置的属性
                if (isContainsFieled(columns, entry.getKey())) {
                    insertKey.append(comma + entry.getKey());

                    if (entry.getValue() != null && entry.getValue().toString().length() > 0) {

                        if (entry.getKey().equalsIgnoreCase("CCR") || entry.getKey().equalsIgnoreCase("SQR")) {
                            userId = entry.getValue().toString();
                            User user1 = new User();
                            user1.setName(userId.replace("'", ""));
                            User create = userDao.findByName(user1);
                            createById = create.getId();
                            createName = create.getLoginName();
                        }


                        if (entry.getKey().equalsIgnoreCase("BH")) {
                            String sql = "select max(BH) from " + defineTable.getTableName();
                            String max = oaPersonDefineTableDao.getMax(sql);
                            if (StringUtils.isNotEmpty(max)) {
                                String sub = max.substring(1, max.length());
                                int orderValue = Integer.parseInt(sub);
                                orderValue++;
                                int n = orderValue;
                                NumberFormat formatter = NumberFormat.getNumberInstance();
                                formatter.setMinimumIntegerDigits(6);
                                formatter.setGroupingUsed(false);
                                String s = formatter.format(n);
                                String now = "A" + s;
                                insertValue.append(comma + "'" + now + "'");
                            } else {
                                String ret = "A000001";
                                insertValue.append(comma + "'" + ret + "'");
                            }
                        } else {
                            if (entry.getKey().equalsIgnoreCase("CCR") ||entry.getKey().equalsIgnoreCase("SQR")) {
                                dept = updateDept(entry.getKey(), entry.getValue().toString());
                            }
                            if (entry.getKey().equalsIgnoreCase("BM")) {
                                if (StringUtils.isNotEmpty(dept)) {
                                    insertValue.append(comma + "'" + dept + "'");
                                } else {
                                    insertValue.append(comma + entry.getValue());

                                }
                            } else {
                                if (entry.getKey().equalsIgnoreCase("FJLJ")) {
                                    String ls = entry.getValue().toString().replace("\\", "\\\\");
                                    insertValue.append(comma + ls);
                                } else {
                                    insertValue.append(comma + entry.getValue());

                                }
                            }


                        }
                    } else {
                        if (entry.getKey().equalsIgnoreCase("BH")) {
                            String sql = "select max(BH) from " + defineTable.getTableName();
                            String max = oaPersonDefineTableDao.getMax(sql);
                            if (StringUtils.isNotEmpty(max)) {
                                String sub = max.substring(1, max.length());
                                int orderValue = Integer.parseInt(sub);
                                orderValue++;
                                int n = orderValue;
                                NumberFormat formatter = NumberFormat.getNumberInstance();
                                formatter.setMinimumIntegerDigits(6);
                                formatter.setGroupingUsed(false);
                                String s = formatter.format(n);
                                String now = "A" + s;
                                insertValue.append(comma + "'" + now + "'");
                            } else {
                                String ret = "A000001";
                                insertValue.append(comma + "'" + ret + "'");
                            }
                        } else {
                            insertValue.append(comma + "null");
                        }

                    }
                    comma = ", ";
                }
            }
            insertKey.append(",id,create_date,update_by,update_date,remarks,del_flag,proc_def_id");
            insertValue.append(comma + handleSqlValue(flowData.getId(), "varchar"))
                    .append(comma + handleSqlValue(DateUtils.formatDateTime(flowData.getCreateDate()), "date"))
                    .append(comma + handleSqlValue(flowData.getUpdateBy(), "varchar"))
                    .append(comma + handleSqlValue(DateUtils.formatDateTime(flowData.getUpdateDate()), "date"))
                    .append(comma + handleSqlValue(flowData.getRemarks(), "varchar"))
                    .append(comma + handleSqlValue(flowData.getDelFlag(), "varchar"))
                    .append(comma + handleSqlValue(flowData.getAct().getProcDefId(), "varchar"));
            if (StringUtils.isNotEmpty(createById)) {
                if (StringUtils.equalsIgnoreCase("zhaowj", createName) || StringUtils.equalsIgnoreCase("cheng", createName)) {
                    insertKey.append(",create_by");
                    insertValue.append(comma + handleSqlValue(flowData.getCreateBy(), "varchar"));
                } else {
                    insertKey.append(",create_by");
                    insertValue.append(comma + handleSqlValue(createById, "varchar"));
                }
            } else {
                insertKey.append(",create_by");
                insertValue.append(comma + handleSqlValue(flowData.getCreateBy(), "varchar"));
            }
            String sql = "INSERT INTO " + tableName + " (" + insertKey + ") VALUES (" + insertValue + ")";
            oaPersonDefineTableDao.executeSql(sql);
        }
//        super.delete(targetHospital);
    }

    public String updateDept(String key, String value) {
        User user = new User();
        user.setName(value.replace("\'", ""));
        user = userDao.getByName(user);
        Office office = officeDao.get(user.getOffice().getId());
        return office.getName();
    }

    @Transactional(readOnly = false)
    public void updateFlowData(FlowData flowData) {

        String tableName = flowData.getTableName();
        Map<String, Object> data = flowData.getDatas();
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName, null);
        if (defineTable != null && data != null && StringUtils.isNotBlank(tableName)) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            dataAdapter(columns, data);
            String comma = "";
            StringBuffer updateValue = new StringBuffer();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                // 判断key是否为表配置的属性
                if (isContainsFieled(columns, entry.getKey())) {
                    updateValue.append(comma + entry.getKey() + "=");
                    if (entry.getValue() != null && entry.getValue().toString().length() > 0) {
                        updateValue.append(entry.getValue());
                    } else {
                        updateValue.append("null");
                    }
                    comma = ", ";
                }
            }
            updateValue.append(comma + "update_by=" + handleSqlValue(flowData.getUpdateBy(), "varchar"))
                    .append(",update_date=" + handleSqlValue(flowData.getUpdateDate() == null ? null : DateUtils.formatDateTime(flowData.getUpdateDate()), "date"));
            String sql = "UPDATE " + tableName + " SET " + updateValue + " where id='" + flowData.getId() + "'";
            oaPersonDefineTableDao.executeSql(sql);
        }
//        super.delete(targetHospital);
    }


    /**
     * 数据类型适配-根据表单配置的字段类型将前台传递的值将map-value转换成相应的类型
     *
     * @param columns
     * @param data    数据
     */
    private Map<String, Object> dataAdapter(List<OaPersonDefineTableColumn> columns, Map<String, Object> data) {
        //step.2 迭代将要持久化的数据
        for (OaPersonDefineTableColumn column : columns) {
            //根据表单配置的字段名 获取 前台数据
            String columnName = column.getColumnName();
            Object beforeV = data.get(columnName);
            //如果值不为空
            if (OConvertUtils.isNotEmpty(beforeV)) {
                //获取字段配置-字段类型
                String type = column.getColumnType();
                //根据类型进行值的适配
                data.put(columnName, handleSqlValue(beforeV, type));
            }
        }
        return data;
    }

    private String handleSqlValue(Object value, String type) {
        if (value == null) return null;
        Object newV = "";
        if ("date".equalsIgnoreCase(type) || "datetime".equalsIgnoreCase(type)) {
            newV = "date_format('" + String.valueOf(value) + "','%Y-%m-%d')";
        } else if ("number".equalsIgnoreCase(type) || "NUMERIC".equalsIgnoreCase(type)) {
            newV = new Double(0);
            try {
                String value1 = String.valueOf(value);
                if (value1.contains(",")) {
                    value1 = String.valueOf(value).replace(",", "");
                }
                newV = Double.parseDouble(value1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("varchar".equalsIgnoreCase(type)) {
            newV = "'" + String.valueOf(value) + "'";
        } else {
            return null;
        }
        return String.valueOf(newV);
    }

    //判断key是否为表配置的属性
    private boolean isContainsFieled(List<OaPersonDefineTableColumn> columns, String fieledName) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getColumnName().equals(fieledName.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

}