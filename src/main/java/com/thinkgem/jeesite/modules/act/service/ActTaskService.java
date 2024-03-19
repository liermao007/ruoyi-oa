/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.act.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.util.StrUtil;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableColumnDao;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.test1.entity.OaRunProcess;
//import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import com.thinkgem.jeesite.modules.test1.service.OaRunProcessService;
//import com.thinkgem.jeesite.modules.test1.service.UpdatePrincipalInfoService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.dao.ActDao;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.cmd.CreateAndTakeTransitionCmd;
import com.thinkgem.jeesite.modules.act.service.cmd.JumpTaskCmd;
import com.thinkgem.jeesite.modules.act.service.creator.ChainedActivitiesCreator;
import com.thinkgem.jeesite.modules.act.service.creator.MultiInstanceActivityCreator;
import com.thinkgem.jeesite.modules.act.service.creator.RuntimeActivityDefinitionEntityIntepreter;
import com.thinkgem.jeesite.modules.act.service.creator.SimpleRuntimeActivityDefinitionEntity;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefCache;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 流程定义相关Service
 *
 * @author oa
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true)
public class ActTaskService extends BaseService {

    @Autowired
    private ActDao actDao;

    @Autowired
    OaRunProcessService oaRunProcessService;

    @Autowired
    private OaPersonDefineTableColumnDao oaPersonDefineTableColumnDao;
    @Autowired
    private ProcessEngineFactoryBean processEngineFactory;

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private OaPersonDefineTableDao oaPersonDefineTableDao;

    @Autowired
    private UserDao userDao;

    /**
     * 获取待办列表
     * procDefKey 流程定义标识
     *
     * @param
     * @return
     */
    public List<Act> todoList(Act act, String userId) {
        if (StringUtils.isBlank(userId)) {
            userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
        } else if (userDao.get(userId) != null) {
            userId = userDao.get(userId).getLoginName();
        }


        List<Act> result = new ArrayList<Act>();
        // =============== 等待签收的任务  ===============
        TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
                .includeProcessVariables().active().orderByTaskCreateTime().desc();
        // 设置查询条件
        if (StringUtils.isNotBlank(act.getProcDefKey())) {
            toClaimQuery.processDefinitionKey(act.getProcDefKey());
        }
        if (act.getBeginDate() != null) {
            toClaimQuery.taskCreatedAfter(act.getBeginDate());
        }
        if (act.getEndDate() != null) {
            toClaimQuery.taskCreatedBefore(act.getEndDate());
        }

        // 查询列表
        List<Task> toClaimList = toClaimQuery.list();
        for (Task task : toClaimList) {
            Act e = new Act();
            e.setTask(task);
            e.setVars(task.getProcessVariables());

            Map<String, Object> map = task.getProcessVariables();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object ok = entry.getKey();
                Object ov = entry.getValue() == null ? "" : entry.getValue();
                String key = ok.toString();
                if (key.equals("apply")) {
                    String ff = ov.toString();
                    User user1 = systemService.getUserByLoginName(ff);
                    if (user1 != null) {
                        e.setTitle(user1.getName());
                    }
                }
            }
            e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
            e.setStatus("claim");
            result.add(e);
        }
        // =============== 已经签收的任务  ===============
        TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
                .includeProcessVariables().orderByTaskCreateTime().desc();

        // 设置查询条件
        if (StringUtils.isNotBlank(act.getProcDefKey())) {
            todoTaskQuery.processDefinitionKey(act.getProcDefKey());
        }
        if (act.getBeginDate() != null) {
            todoTaskQuery.taskCreatedAfter(act.getBeginDate());
        }
        if (act.getEndDate() != null) {
            todoTaskQuery.taskCreatedBefore(act.getEndDate());
        }

        // 查询列表
        List<Task> todoList = todoTaskQuery.list();
        for (Task task : todoList) {
            Act e = new Act();
            e.setTask(task);
            e.setVars(task.getProcessVariables());

            Map<String, Object> map = task.getProcessVariables();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object ok = entry.getKey();
                Object ov = entry.getValue() == null ? "" : entry.getValue();
                String key = ok.toString();
                if (key.equals("apply")) {
                    String procInsId = task.getProcessInstanceId();
                    String loginName = handleJMYTable(procInsId);
                    if (StringUtils.isNotEmpty(loginName) && !loginName.equalsIgnoreCase(ov.toString())) {
                        User user1 = systemService.getUserByLoginName(loginName);
                        if (user1 != null) {
                            e.setTitle(user1.getName());
                            e.setPhoto(user1.getPhoto());
                        } else {
                            e.setTitle(ov.toString());
                            e.setPhoto("");
                        }
                    } else {
                        User user1 = systemService.getUserByLoginName( ov.toString());
                        if (user1 != null) {
                            e.setTitle(user1.getName());
                            e.setPhoto(user1.getPhoto());
                        } else {
                            e.setTitle(ov.toString());
                            e.setPhoto("");
                        }
                    }
                }
            }
//			e.setTaskVars(task.getTaskLocalVariables());
//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
            e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
            e.setStatus("todo");
            result.add(e);
        }


        return result;
    }


    /**
     * 处理差旅费报销单和支出凭单的特殊处理
     * @param procInsId
     * @return
     */
    public String  handleJMYTable(String procInsId){
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "";
        sql = "select * from JMY_CLFBXD where proc_ins_id='" + procInsId + "'";
        list = oaPersonDefineTableDao.getFlowInfo(sql);
        if (list == null || list.size() == 0) {
            sql = "select * from JMY_ZCPD where proc_ins_id='" + procInsId + "'";
            list = oaPersonDefineTableDao.getFlowInfo(sql);
        }
        String loginName = "";
        if (list != null && list.size() > 0) {
            Map<String, Object> map1 = list.get(0);
            for (Map.Entry<String, Object> op : map1.entrySet()) {
                if (op.getKey().equalsIgnoreCase("CCR") || op.getKey().equalsIgnoreCase("SQR")) {
                    User user = new User();
                    user.setName(op.getValue().toString());
                    user.setCompanyId(UserUtils.getUser().getCompany().getId());
                    User user1 = userDao.getByName(user);
                    if (user1 != null) {
                        loginName = user1.getLoginName();
                    } else {
                        loginName = op.getValue().toString();
                    }
                    break;
                }
            }
        }
        return loginName;
    }

    public Page<Map<String, Object>> getPageFlowInfo(Page<FlowData> page, Map<String, String> paramMap) {
        FlowData flow = new FlowData();
        flow.setPage(page);
        flow.setSql(getSelectSql(paramMap));
        List<Map<String, Object>> list = oaPersonDefineTableDao.getFlowInfo(flow);
        Page<Map<String, Object>> result = new Page<>(page.getPageNo(), page.getPageSize(), page.getCount());
        result.setList(list);
        return result;
    }

    public List<Act> doneList() {
        String loginName = UserUtils.getUser().getLoginName();
        List<Act> list = actDao.doneList(loginName);
        for (Act act : list) {
            if (!StringUtils.isNotBlank(act.getTaskName())) {
                String sql = "select act_id_ as 'taskId',act_name_ as 'taskName',assignee_ as 'CS' from  act_hi_actinst where proc_ins_id='" + act.getTaskId() + "'";
                List<Act> actList = actDao.getByProcInsId(act.getTaskId());
                ArrayList<String> list2 = new ArrayList<>();
                for (Act act1 : actList) {
                    list2.add(act1.getTaskId());
                }
                if (list2.contains("end") && !list2.contains("apply_end")) {
                    act.setTaskName("结束");
                    act.setComment("已归档");
                } else if (list2.contains("end") && list2.contains("apply_end")) {
                    act.setTaskName("结束");
                    String gd = actDao.findGd("apply_end", act.getTaskId());
                    act.setComment("已归档");
                } else if (!list2.contains("end") && list2.contains("apply_end")) {
                    act.setTaskName("归档");
                    String gd = actDao.findGd("apply_end", act.getTaskId());
                    act.setComment("待归档");
                } else {
                    act.setTaskName("已撤销");
                    act.setComment(act.getCS());
                }

                String sql1 = "select id from " + act.getTaskDefKey() + " where proc_ins_id='" + act.getTaskId() + "'";
                String taskName = actDao.findTaskName(sql1);
                act.setBusinessId(taskName);
            }
        }
        return list;
    }

    private String getSelectSql(Map<String, String> paramMap) {
        String tableName = paramMap.get("tableName");
        String procInsId = paramMap.get("procInsId");
        String id = paramMap.get("id");
        String createBy = paramMap.get("createBy");
        String procDefId = paramMap.get("procDefId");
        String name = paramMap.get("name");
        String dept = paramMap.get("dept");
        String numberDay = paramMap.get("numberDay");
        String fh = paramMap.get("fh");
        String arriveDay = paramMap.get("arriveDay");
        String startDate = paramMap.get("startDate");
        String endDate = paramMap.get("endDate");
        StringBuilder sql = null;
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName, null);
        if (defineTable != null) {

            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            if (columns != null && columns.size() > 0) {
                sql = new StringBuilder("select DISTINCT id,proc_ins_id procInsId,create_by");
                for (OaPersonDefineTableColumn column : columns) {
                    if ("DATE".equals(column.getColumnType().toUpperCase())) {
                        sql.append(",replace(date_format(" + column.getColumnName() + ",'%Y-%m-%d'),' 00:00:00','') " + column.getColumnName());
                    } else {
                        sql.append("," + column.getColumnName());
                    }
                }
//             String loginName=   UserUtils.getUser().getLoginName();
                String loginName = userDao.get(paramMap.get("createBy")).getLoginName();
                sql.append(" from " + tableName + " b  JOIN  act_hi_taskinst h on b.proc_def_id=h.proc_def_id_ \n" +
                        " AND b.proc_ins_id = h.proc_inst_id_\n" +
                        " and h.ASSIGNEE_='" + loginName + "' where del_flag = '0' AND  h.CLAIM_TIME_ IS NOT NULL  AND  h.END_TIME_ IS NOT NULL");
                if (StringUtils.isNotBlank(id)) {
                    sql.append(" and id='" + id + "' ");
                }
                for (OaPersonDefineTableColumn column : columns) {
                    if (StringUtils.equalsIgnoreCase(column.getColumnComment(), "申请人") || StringUtils.equalsIgnoreCase(column.getColumnComment(), "出差人") || StringUtils.equalsIgnoreCase(column.getColumnComment(), "姓名")
                            || StringUtils.equalsIgnoreCase(column.getColumnComment(), "被调薪人")) {
                        if (StringUtils.isNotBlank(name)) {
                            sql.append(" and " + column.getColumnName() + " like '%" + name + "%' ");
                        }
                    }

                    if (column.getColumnComment().indexOf("天数") != -1) {
                        if (StringUtils.isNotBlank(numberDay) && StringUtils.isNotBlank(fh)) {
                            if (StringUtils.equalsIgnoreCase(fh, "1")) {
                                sql.append(" and " + column.getColumnName() + " >=  '" + numberDay + "' ");
                            } else if (StringUtils.equalsIgnoreCase(fh, "2")) {
                                sql.append(" and " + column.getColumnName() + "  <=  '" + numberDay + "' ");
                            } else if (StringUtils.isNotBlank(arriveDay) && StringUtils.equalsIgnoreCase(fh, "3")) {
                                sql.append(" and " + column.getColumnName() + " >= '" + numberDay + "' and " + column.getColumnName() + "<=" + arriveDay);
                            }
                        }

                    }

                    if (column.getColumnComment().indexOf("开始时间") != -1 || column.getColumnComment().indexOf("开始日期") != -1) {
                        if (StringUtils.isNotBlank(startDate)) {
                            sql.append(" and " + column.getColumnName() + " >= '" + startDate + "' ");
                        }
                    }

                    if (column.getColumnComment().indexOf("结束时间") != -1 || column.getColumnComment().indexOf("结束日期") != -1) {
                        if (StringUtils.isNotBlank(endDate)) {
                            sql.append(" and " + column.getColumnName() + " <= '" + endDate + "' ");
                        }
                    }
                }
                if (StringUtils.isNotBlank(dept)) {
                    sql.append(" and BM='" + dept + "' ");
                }
                if (StringUtils.isNotBlank(procInsId)) {
                    sql.append(" and proc_ins_id='" + procInsId + "' ");
                }

                if (StringUtils.isNotBlank(procDefId)) {
                    sql.append(" and proc_def_id='" + procDefId + "' ");
                }
                sql.append("order by CREATE_DATE desc");
            }
        }
        if (sql != null) return sql.toString();
        return null;
    }

    /**
     * 获取已办任务
     * procDefKey 流程定义标识
     *
     * @param page
     * @param
     * @return
     */
    public Page<Act> historicList(Page<Act> page, Act act) {
        String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());

        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished()
                .includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();

        // 设置查询条件
        if (StringUtils.isNotBlank(act.getProcDefKey())) {
            histTaskQuery.processDefinitionKey(act.getProcDefKey());
        }
        if (act.getBeginDate() != null) {
            histTaskQuery.taskCompletedAfter(act.getBeginDate());
        }
        if (act.getEndDate() != null) {
            histTaskQuery.taskCompletedBefore(act.getEndDate());
        }

        // 查询总数
        page.setCount(histTaskQuery.count());

        // 查询列表
        List<HistoricTaskInstance> histList = histTaskQuery.listPage(page.getFirstResult(), page.getMaxResults());
        //处理分页问题
        List<Act> actList = Lists.newArrayList();
        for (HistoricTaskInstance histTask : histList) {
            Act e = new Act();
            e.setHistTask(histTask);
            e.setVars(histTask.getProcessVariables());
            Map<String, Object> map = histTask.getProcessVariables();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object ok = entry.getKey();
                Object ov = entry.getValue() == null ? "" : entry.getValue();
                String key = ok.toString();
                if (key.equals("apply")) {
                    String procInsId = histTask.getProcessInstanceId();
                    String loginName = handleJMYTable(procInsId);
                    if (StringUtils.isNotEmpty(loginName) && !loginName.equalsIgnoreCase(ov.toString())) {
                        User user1 = systemService.getUserByLoginName(loginName);
                        if (user1 != null) {
                            e.setTitle(user1.getName());
                        } else {
                            e.setTitle(ov.toString());
                        }
                    } else {
                        User user1 = systemService.getUserByLoginName(ov.toString());
                        if (user1 != null) {
                            e.setTitle(user1.getName());
                        } else {
                            e.setTitle(ov.toString());
                        }
                    }
                }
            }
            e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
            e.setStatus("finish");
            actList.add(e);
            //page.getList().add(e);
        }
        page.setList(actList);
        return page;
    }


    /**
     * 获取已办任务
     * procDefKey 流程定义标识
     *
     * @param
     * @param
     * @return
     */
    public List<OaPersonDefineTable> historicList1(Act act) {
        String loginName = UserUtils.getUser().getLoginName();
        List<OaPersonDefineTable> oaPersonDefineTables = oaPersonDefineTableDao.findListByLoginName(loginName);
        return oaPersonDefineTables;
    }

    /**
     * 获取已办任务
     *
     * @param
     * @return2018-01-02 11:47:30,604 DEBUG [jeesite.common.persistence.interceptor.PaginationInterceptor] - COUNT SQL: select count(1) from (SELECT         DISTINCT         h.`PROC_INST_ID_`as taskId ,         m.`table_name` as taskDefKey,         m.`ALIAS` as taskName,         date_format(h.START_TIME_, '%Y-%m-%d') as startTime ,         u.name as CS         FROM         act_hi_taskinst h         LEFT JOIN oa_form_master m ON m.form_no =LEFT(h.PROC_DEF_ID_, LOCATE(':',h.PROC_DEF_ID_)-1)         LEFT JOIN act_hi_identitylink a ON a.proc_inst_id_=h.`PROC_INST_ID_` AND a.type_="starter"          LEFT JOIN sys_user u ON u.login_name=a.`USER_ID_`         WHERE h.ASSIGNEE_ =?          ) tmp_count
     */
    public Page<Act> toDoTasks(Page<Act> page, Act act) {
        act.setPage(page);
        String loginName = UserUtils.getUser().getLoginName();
        act.setLoginName(loginName);
        List<Act> list = actDao.oaHasToDoTask(act);
        page.setList(list);
        for (Act act1 : list) {
            String sql = "select id from " + act1.getTaskDefKey() + " where proc_ins_id = " + "'" + act1.getTaskId() + "'";
            String id = oaPersonDefineTableDao.getMax(sql);
            act1.setId(id);
        }
        return page;
    }

    /**
     * 获取被代理人的代理人
     * procDefKey 流程定义标识
     *
     * @param
     * @param
     * @return
     */
    public List<OaRunProcess> historicList2(Act act) {
        String loginId = UserUtils.getUser().getId();
        List<OaRunProcess> oaRunProcess = oaPersonDefineTableDao.findListByLoginId(loginId);

        return oaRunProcess;
    }

    /**
     * 获取被代理人的代理人
     * procDefKey 流程定义标识
     *
     * @param
     * @param
     * @return
     */
    public List<OaPersonDefineTable> historicList3(Act act, String principalid) {
        List<OaPersonDefineTable> list = oaPersonDefineTableDao.findListByLoginName(userDao.get(principalid).getLoginName());
        return list;
    }


    public List<Map> findActTaskPage(Page<Act> page, Act act, List<Map> list) {
        page = historicList(page, act);
        List<Act> data = page.getList();
        Map<String, String> dataMap = new HashMap<String, String>();
        for (int i = 0; i < data.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String dataTime = sdf.format(data.get(i).getHistTask().getEndTime());
            dataMap.put("endTime", dataTime);
            dataMap.put("id", data.get(i).getHistTask().getId());
            dataMap.put("taskDefinitionKey", data.get(i).getHistTask().getTaskDefinitionKey());
            dataMap.put("processInstanceId", data.get(i).getHistTask().getProcessInstanceId());
            dataMap.put("processDefinitionId", data.get(i).getHistTask().getProcessDefinitionId());
            dataMap.put("status", data.get(i).getStatus());
            dataMap.put("flag", "act");
            dataMap.put("procDefName", data.get(i).getProcDef().getName());
            dataMap.put("title", data.get(i).getTitle());
            dataMap.put("urlEncode", Encodes.urlEncode(data.get(i).getHistTask().getName()));
            list.add(dataMap);
        }
        return list;
    }

    /**
     * 获取流转历史列表
     *
     * @param procInsId 流程实例
     * @param startAct  开始活动节点名称
     * @param endAct    结束活动节点名称
     */
    public List<Act> histoicFlowList(String procInsId, String startAct, String endAct) {
        List<Act> actList = Lists.newArrayList();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
                .orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();

        boolean start = false;
        Map<String, Integer> actMap = Maps.newHashMap();

        for (int i = 0; i < list.size(); i++) {

            HistoricActivityInstance histIns = list.get(i);

            // 过滤开始节点前的节点
            if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())) {
                start = true;
            }
            if (StringUtils.isNotBlank(startAct) && !start) {
                continue;
            }

            // 只显示开始节点和结束节点，并且执行人不为空的任务
            if (StringUtils.isNotBlank(histIns.getAssignee())
                    || "startEvent".equals(histIns.getActivityType())
                    || "endEvent".equals(histIns.getActivityType())) {

                // 给节点增加一个序号
                Integer actNum = actMap.get(histIns.getActivityId());
                if (actNum == null) {
                    actMap.put(histIns.getActivityId(), actMap.size());
                }

                Act e = new Act();
                e.setHistIns(histIns);
                // 获取流程发起人名称
                if ("startEvent".equals(histIns.getActivityType())) {
                    List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
//					List<HistoricIdentityLink> il = historyService.getHistoricIdentityLinksForProcessInstance(procInsId);
                    if (il.size() > 0) {
                        if (StringUtils.isNotBlank(il.get(0).getStartUserId())) {
                            User user = UserUtils.getByLoginName(il.get(0).getStartUserId());
                            if (user != null) {
                                e.setAssignee(histIns.getAssignee());
                                e.setAssigneeName(user.getName());
                                e.setStartLoginName(user.getLoginName());
                            }
                        }
                    }
                }
                // 获取任务执行人名称
                if (StringUtils.isNotEmpty(histIns.getAssignee())) {
                    User user = UserUtils.findListByLoginName(histIns.getAssignee());
                    if (user != null) {
                        e.setAssignee(histIns.getAssignee());
                        e.setAssigneeName(user.getName());
                        e.setFlag(user.getMobile());
                        e.setTaskName(user.getName());

                    }
                }
                // 获取意见评论内容
                if (StringUtils.isNotBlank(histIns.getTaskId())) {
                    List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
                    if (commentList.size() > 0) {
                        e.setComment(commentList.get(0).getFullMessage());
                    }
                }
                actList.add(e);
            }

            // 过滤结束节点后的节点
            if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())) {
                boolean bl = false;
                Integer actNum = actMap.get(histIns.getActivityId());
                // 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
                for (int j = i + 1; j < list.size(); j++) {
                    HistoricActivityInstance hi = list.get(j);
                    Integer actNumA = actMap.get(hi.getActivityId());
                    if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())) {
                        bl = true;
                    }
                }
                if (!bl) {
                    break;
                }
            }
        }
        return actList;
    }


    /**
     * 获取流程列表
     *
     * @param category 流程分类
     */
    public Page<Object[]> processList(Page<Object[]> page, String category) {
        /*
         * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().active().orderByProcessDefinitionKey().asc();

        if (StringUtils.isNotEmpty(category)) {
            processDefinitionQuery.processDefinitionCategory(category);
        }

        page.setCount(processDefinitionQuery.count());
        List<Object[]> list = new ArrayList<>();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            list.add(new Object[]{processDefinition, deployment});
        }
        page.setList(list);
        return page;
    }

    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     *
     * @return
     */
    public String getFormKey(String procDefId, String taskDefKey) {
        String formKey = "";
        if (StringUtils.isNotBlank(procDefId)) {
            if (StringUtils.isNotBlank(taskDefKey)) {
                try {
                    formKey = formService.getTaskFormKey(procDefId, taskDefKey);
                } catch (Exception e) {
                    formKey = "";
                }
            }
            if (StringUtils.isBlank(formKey)) {
                formKey = formService.getStartFormKey(procDefId);
            }
            if (StringUtils.isBlank(formKey)) {
                formKey = "/404";
            }
        }
        logger.debug("getFormKey: {}", formKey);
        return formKey;
    }

    /**
     * 获取流程实例对象
     *
     * @param procInsId
     * @return
     */
    @Transactional(readOnly = false)
    public ProcessInstance getProcIns(String procInsId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
    }

    /**
     * 启动流程
     *
     * @param procDefKey    流程定义KEY
     * @param businessTable 业务表表名
     * @param businessId    业务表编号
     * @return 流程实例ID
     */
    @Transactional(readOnly = false)
    public String startProcess(String procDefKey, String businessTable, String businessId) {
        return startProcess(procDefKey, businessTable, businessId, "");
    }

    /**
     * 启动流程
     *
     * @param procDefKey    流程定义KEY
     * @param businessTable 业务表表名
     * @param businessId    业务表编号
     * @param title         流程标题，显示在待办任务标题
     * @return 流程实例ID
     */
    @Transactional(readOnly = false)
    public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
        Map<String, Object> vars = Maps.newHashMap();
        return startProcess(procDefKey, businessTable, businessId, title, vars, "");
    }

    /**
     * 启动流程
     *
     * @param procDefKey    流程定义KEY
     * @param businessTable 业务表表名
     * @param businessId    业务表编号
     * @param title         流程标题，显示在待办任务标题
     * @param vars          流程变量
     * @return 流程实例ID
     */
    @Transactional(readOnly = false)
    public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars, String name) {
        if (StringUtils.equalsIgnoreCase("zhaowj", name) || StringUtils.equalsIgnoreCase("cheng", name) || StringUtils.isEmpty(name)) {
            String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId())
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(userId);
        } else {
            identityService.setAuthenticatedUserId(name);
        }

        // 设置流程变量
        if (vars == null) {
            vars = Maps.newHashMap();
        }

        // 设置流程标题
        if (StringUtils.isNotBlank(title)) {
            vars.put("title", title);
        }

        // 启动流程
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable + ":" + businessId, vars);

        // 更新业务表流程实例ID
        Act act = new Act();
        act.setBusinessTable(businessTable);// 业务表名
        act.setBusinessId(businessId);    // 业务表ID
        act.setProcInsId(procIns.getId());
        actDao.updateProcInsIdByBusinessId(act);
        return act.getProcInsId();
    }

    /**
     * 获取任务
     *
     * @param taskId 任务ID
     */
    public Task getTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    /**
     * 删除任务
     *
     * @param taskId       任务ID
     * @param deleteReason 删除原因
     */
    @Transactional(readOnly = false)
    public void deleteTask(String taskId, String deleteReason) {
        taskService.deleteTask(taskId, deleteReason);
    }

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @param userId 签收用户ID（用户登录名）
     */
    @Transactional(readOnly = false)
    public void claim(String taskId, String userId) {
        taskService.setVariable(taskId, "userId", userId);
        taskService.claim(taskId, userId);
    }

    /**
     * 提交任务, 并保存意见
     *
     * @param taskId    任务ID
     * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
     * @param comment   任务提交意见的内容
     * @param vars      任务变量
     */
    @Transactional(readOnly = false)
    public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars) {
        complete(taskId, procInsId, comment, "", vars);
    }

    /**
     * 提交任务, 并保存意见
     *
     * @param taskId    任务ID
     * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
     * @param comment   任务提交意见的内容
     * @param title     流程标题，显示在待办任务标题
     * @param vars      任务变量
     */
    @Transactional(readOnly = false)
    public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars) {
        // 添加意见
        if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)) {
            taskService.addComment(taskId, procInsId, comment);
        }

        // 设置流程变量
        if (vars == null) {
            vars = Maps.newHashMap();
        }

        // 设置流程标题
        if (StringUtils.isNotBlank(title)) {
            vars.put("title", title);
        }
        if(StringUtils.equals(procInsId,"4a992dabf7b940f5a09d1cd53170bb7b")){
            vars.put("JEXX","");
        }
        if(vars.containsKey("ZTS") && vars.get("ZTS") != null) {
            vars.put("ZTS", Double.parseDouble(vars.get("ZTS").toString()));
        }
        // 提交任务
        taskService.complete(taskId, vars);
    }

    /**
     * 完成第一个任务
     *
     * @param procInsId
     */
    @Transactional(readOnly = false)
    public void completeFirstTask(String procInsId) {
        completeFirstTask(procInsId, null, null, null);
    }

    /**
     * 完成第一个任务
     *
     * @param procInsId
     * @param comment
     * @param title
     * @param vars
     */
    @Transactional(readOnly = false)
    public void completeFirstTask(String procInsId, String comment, String title, Map<String, Object> vars) {
        String userId = UserUtils.getUser().getLoginName();
        Task task = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(procInsId).active().singleResult();
        if (task != null) {
            complete(task.getId(), procInsId, comment, title, vars);
        }
    }

//	/**
//	 * 委派任务
//	 * @param taskId 任务ID
//	 * @param userId 被委派人
//	 */
//	public void delegateTask(String taskId, String userId){
//		taskService.delegateTask(taskId, userId);
//	}
//
//	/**
//	 * 被委派人完成任务
//	 * @param taskId 任务ID
//	 */
//	public void resolveTask(String taskId){
//		taskService.resolveTask(taskId);
//	}
//
//	/**
//	 * 回退任务
//	 * @param taskId
//	 */
//	public void backTask(String taskId){
//		taskService.
//	}

    /**
     * 添加任务意见
     */
    public void addTaskComment(String taskId, String procInsId, String comment) {
        taskService.addComment(taskId, procInsId, comment);
    }

    //////////////////  回退、前进、跳转、前加签、后加签、分裂 移植  https://github.com/bluejoe2008/openwebflow  //////////////////////////////////////////////////

    /**
     * 任务后退一步
     */
    public void taskBack(String procInsId, Map<String, Object> variables) {
        taskBack(getCurrentTask(procInsId), variables);
    }

    /**
     * 任务后退至指定活动
     */
    public void taskBack(TaskEntity currentTaskEntity, Map<String, Object> variables) {
        ActivityImpl activity = (ActivityImpl) ProcessDefUtils
                .getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
                .getIncomingTransitions().get(0).getSource();
        jumpTask(currentTaskEntity, activity, variables);
    }

    /**
     * 任务前进一步
     */
    public void taskForward(String procInsId, Map<String, Object> variables) {
        taskForward(getCurrentTask(procInsId), variables);
    }

    /**
     * 任务前进至指定活动
     */
    public void taskForward(TaskEntity currentTaskEntity, Map<String, Object> variables) {
        ActivityImpl activity = (ActivityImpl) ProcessDefUtils
                .getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
                .getOutgoingTransitions().get(0).getDestination();

        jumpTask(currentTaskEntity, activity, variables);
    }

    /**
     * 跳转（包括回退和向前）至指定活动节点
     */
    public void jumpTask(String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables) {
        jumpTask(getCurrentTask(procInsId), targetTaskDefinitionKey, variables);
    }

    /**
     * 跳转（包括回退和向前）至指定活动节点
     */
    public void jumpTask(String procInsId, String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables) {
        jumpTask(getTaskEntity(currentTaskId), targetTaskDefinitionKey, variables);
    }

    /**
     * 跳转（包括回退和向前）至指定活动节点
     *
     * @param currentTaskEntity       当前任务节点
     * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
     * @throws Exception
     */
    public void jumpTask(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables) {
        ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(),
                targetTaskDefinitionKey);
        jumpTask(currentTaskEntity, activity, variables);
    }

    /**
     * 跳转（包括回退和向前）至指定活动节点
     *
     * @param currentTaskEntity 当前任务节点
     * @param targetActivity    目标任务节点（在模型定义里面的节点名称）
     * @throws Exception
     */
    private void jumpTask(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
        CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
        commandExecutor.execute(new JumpTaskCmd(currentTaskEntity, targetActivity, variables));
    }

    /**
     * 后加签
     */
    @SuppressWarnings("unchecked")
    public ActivityImpl[] insertTasksAfter(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
        List<String> assigneeList = new ArrayList<String>();
        assigneeList.add(Authentication.getAuthenticatedUserId());
        assigneeList.addAll(CollectionUtils.arrayToList(assignees));
        String[] newAssignees = assigneeList.toArray(new String[0]);
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(procDefId);
        ActivityImpl prototypeActivity = ProcessDefUtils.getActivity(processEngine, processDefinition.getId(), targetTaskDefinitionKey);
        return cloneAndMakeChain(processDefinition, procInsId, targetTaskDefinitionKey, prototypeActivity.getOutgoingTransitions().get(0).getDestination().getId(), variables, newAssignees);
    }

    /**
     * 前加签
     */
    public ActivityImpl[] insertTasksBefore(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
        ProcessDefinitionEntity procDef = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(procDefId);
        return cloneAndMakeChain(procDef, procInsId, targetTaskDefinitionKey, targetTaskDefinitionKey, variables, assignees);
    }

    /**
     * 分裂某节点为多实例节点
     */
    public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignee) {
        return splitTask(procDefId, procInsId, targetTaskDefinitionKey, variables, true, assignee);
    }

    /**
     * 分裂某节点为多实例节点
     */
    @SuppressWarnings("unchecked")
    public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, boolean isSequential, String... assignees) {
        SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
        info.setProcessDefinitionId(procDefId);
        info.setProcessInstanceId(procInsId);

        RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);

        radei.setPrototypeActivityId(targetTaskDefinitionKey);
        radei.setAssignees(CollectionUtils.arrayToList(assignees));
        radei.setSequential(isSequential);

        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(procDefId);
        ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(processEngine, processDefinition, info)[0];

        TaskEntity currentTaskEntity = this.getCurrentTask(procInsId);

        CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
        commandExecutor.execute(new CreateAndTakeTransitionCmd(currentTaskEntity, clone, variables));

//		recordActivitiesCreation(info);
        return clone;
    }

    private TaskEntity getCurrentTask(String procInsId) {
        return (TaskEntity) taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
    }

    private TaskEntity getTaskEntity(String taskId) {
        return (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    @SuppressWarnings("unchecked")
    private ActivityImpl[] cloneAndMakeChain(ProcessDefinitionEntity procDef, String procInsId, String prototypeActivityId, String nextActivityId, Map<String, Object> variables, String... assignees) {
        SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
        info.setProcessDefinitionId(procDef.getId());
        info.setProcessInstanceId(procInsId);

        RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);
        radei.setPrototypeActivityId(prototypeActivityId);
        radei.setAssignees(CollectionUtils.arrayToList(assignees));
        radei.setNextActivityId(nextActivityId);

        ActivityImpl[] activities = new ChainedActivitiesCreator().createActivities(processEngine, procDef, info);

        jumpTask(procInsId, activities[0].getId(), variables);
//		recordActivitiesCreation(info);

        return activities;
    }

    /**
     * 读取带跟踪的图片
     *
     * @param executionId 环节ID
     * @return 封装了各种节点信息
     */
    public InputStream tracePhoto(String processDefinitionId, String executionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        List<String> activeActivityIds = Lists.newArrayList();
        if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0) {
            activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        }

        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        return processEngineConfiguration.getProcessDiagramGenerator()
                .generateDiagram(bpmnModel,
                        "png",
                        activeActivityIds,
                        new ArrayList(),
                        processEngineConfiguration.getActivityFontName(),
                        processEngineConfiguration.getLabelFontName(),
                        processEngineConfiguration.getAnnotationFontName(),
                        processEngineConfiguration.getClassLoader(),
                        1.0);
    }

    /**
     * 流程跟踪图信息
     *
     * @param processInstanceId 流程实例ID
     * @return 封装了各种节点信息
     */
    public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
        Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
        Object property = PropertyUtils.getProperty(execution, "activityId");
        String activityId = "";
        if (property != null) {
            activityId = property.toString();
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        List<ActivityImpl> activitiList = processDefinition.getActivities();//获得当前任务的所有节点

        List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
        for (ActivityImpl activity : activitiList) {

            boolean currentActiviti = false;
            String id = activity.getId();

            // 当前节点
            if (id.equals(activityId)) {
                currentActiviti = true;
            }

            Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

            activityInfos.add(activityImageInfo);
        }

        return activityInfos;
    }


    /**
     * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
     *
     * @param activity
     * @param processInstance
     * @param currentActiviti
     * @return
     */
    private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
                                                          boolean currentActiviti) throws Exception {
        Map<String, Object> vars = new HashMap<String, Object>();
        Map<String, Object> activityInfo = new HashMap<String, Object>();
        activityInfo.put("currentActiviti", currentActiviti);
        setPosition(activity, activityInfo);
        setWidthAndHeight(activity, activityInfo);

        Map<String, Object> properties = activity.getProperties();
        vars.put("节点名称", properties.get("name"));
        vars.put("任务类型", ActUtils.parseToZhType(properties.get("type").toString()));

        ActivityBehavior activityBehavior = activity.getActivityBehavior();
        logger.debug("activityBehavior={}", activityBehavior);
        if (activityBehavior instanceof UserTaskActivityBehavior) {

            Task currentTask = null;

            // 当前节点的task
            if (currentActiviti) {
                currentTask = getCurrentTaskInfo(processInstance);
            }

            // 当前任务的分配角色
            UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
            TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
            Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
            if (!candidateGroupIdExpressions.isEmpty()) {

                // 任务的处理角色
                setTaskGroup(vars, candidateGroupIdExpressions);

                // 当前处理人
                if (currentTask != null) {
                    setCurrentTaskAssignee(vars, currentTask);
                }
            }
        }

        vars.put("节点说明", properties.get("documentation"));

        String description = activity.getProcessDefinition().getDescription();
        vars.put("描述", description);

        logger.debug("trace variables: {}", vars);
        activityInfo.put("vars", vars);
        return activityInfo;
    }

    /**
     * 设置任务组
     *
     * @param vars
     * @param candidateGroupIdExpressions
     */
    private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
        String roles = "";
        for (Expression expression : candidateGroupIdExpressions) {
            String expressionText = expression.getExpressionText();
            String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
            roles += roleName;
        }
        vars.put("任务所属角色", roles);
    }

    /**
     * 设置当前处理人信息
     *
     * @param vars
     * @param currentTask
     */
    private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
        String assignee = currentTask.getAssignee();
        if (assignee != null) {
            org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
            String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
            vars.put("当前处理人", userInfo);
        }
    }

    /**
     * 获取当前节点信息
     *
     * @param processInstance
     * @return
     */
    private Task getCurrentTaskInfo(ProcessInstance processInstance) {
        Task currentTask = null;
        try {
            String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
            logger.debug("current activity id: {}", activitiId);

            currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
                    .singleResult();
            logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

        } catch (Exception e) {
            logger.error("can not get property activityId from processInstance: {}", processInstance);
        }
        return currentTask;
    }

    /**
     * 设置宽度、高度属性
     *
     * @param activity
     * @param activityInfo
     */
    private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
        activityInfo.put("width", activity.getWidth());
        activityInfo.put("height", activity.getHeight());
    }

    /**
     * 设置坐标位置
     *
     * @param activity
     * @param activityInfo
     */
    private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
        activityInfo.put("x", activity.getX());
        activityInfo.put("y", activity.getY());
    }

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

}
