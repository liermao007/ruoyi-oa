package com.thinkgem.jeesite.modules.oa.service;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefUtils;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.oa.dao.FlowDao;
import com.thinkgem.jeesite.modules.oa.dao.OaFlowAuditRecordDao;
import com.thinkgem.jeesite.modules.oa.dao.TargetHospitalDao;
import com.thinkgem.jeesite.modules.oa.dao.TravelApplicationDao;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.entity.OaFlowAuditRecord;
import com.thinkgem.jeesite.modules.oa.units.CommonUtils;
import com.thinkgem.jeesite.modules.oa.units.FlowUtils;
import com.thinkgem.jeesite.modules.oa.units.FreemarkerUtils;
import com.thinkgem.jeesite.modules.oa.units.OConvertUtils;
import com.thinkgem.jeesite.modules.sys.dao.AddDictDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.ActCountInfo;
import com.thinkgem.jeesite.modules.sys.entity.AddDict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.ActCountInfoService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableColumnDao;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import com.thinkgem.jeesite.modules.test.testmsm.SmsModule;
import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import com.thinkgem.jeesite.modules.test1.service.OaRunProcessService;
import com.thinkgem.jeesite.modules.xc.dao.OaXcclDao;
import com.thinkgem.jeesite.modules.xc.entity.OaXccl;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.*;

/**
 * 审批Service
 *
 * @author oa
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class FlowService extends CrudService<FlowDao, FlowData> {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private OaPersonDefineTableDao oaPersonDefineTableDao;
    @Autowired
    private OaPersonDefineTableColumnDao oaPersonDefineTableColumnDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OfficeDao officeDao;
    @Autowired
    private AddDictDao addDictDao;
    @Autowired
    private OaXcclDao oaXcclDao;
    @Autowired
    private OaFlowAuditRecordDao oaFlowAuditRecordDao;
    @Autowired
    private OaRunProcessService oaRunProcessService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ActCountInfoService actCountInfoService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private OaFormMasterService oaFormMasterService;

    @Transactional
    public String save(String startAct, String endAct, String procDefIdFather, FlowData flowData, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        String message = null;
        String procInsId=null;
        if(com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(flowData.getAct().getProcDefIdFather())){
            flowData.getAct().setProcDefId(flowData.getAct().getProcDefIdFather());
        }
        Map data = request.getParameterMap();
        FlowData flowParam = new FlowData();
        String html = "";
        if (data != null) {
            data = CommonUtils.mapConvert(data);
            String[] filterName = {"tableName", "act.taskId", "act.taskName", "act.taskDefKey",
                    "act.procInsId", "act.flag", "id", ""};
            data = CommonUtils.attributeMapFilter(data, filterName);
            String procDefId = flowData.getAct().getProcDefId();
            flowData.setFlowFlag(procDefId.substring(0, procDefId.indexOf(":")));
            flowData.setDatas(data);
            flowData = FlowUtils.savePersonsigns(flowData,"0",false);
            try {
                try {
                    String userId = UserUtils.getUser().getLoginName();
                    String procInsId1=oaRunProcessService.findInstId(flowData.getAct().getTaskId());
                    boolean boolFlag = oaRunProcessService.update0(new UpdatePrincipalInfo(),userId,procInsId1,request);
                    if(boolFlag){
                        flowData = FlowUtils.savePersonsigns(flowData,"2",boolFlag);
                    }
                    boolean b = isRepeatSubmit(request,flowData.getAct().getToken());//判断用户是否是重复提交
                    if(b){
                        message = "请不要重复提交";
                        flowParam.getDatas().put("message", message);
                    }else{
                        request.getSession().removeAttribute("token");//移除session中的token
                        //提交申请保存的方法
                        String proInsId = saveFlow(flowData);
                        if(org.apache.commons.lang3.StringUtils.equalsIgnoreCase(flowData.getAct().getFlag(),"no")){
                            return "-1";
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(proInsId) || org.apache.commons.lang3.StringUtils.isNotEmpty(flowData.getAct().getProcInsId())) {
                            if (org.apache.commons.lang3.StringUtils.isNotEmpty(flowData.getAct().getProcInsId())) {
                                proInsId = flowData.getAct().getProcInsId();
                            }
                            List<Task> tasks = taskService.createTaskQuery().processInstanceId(proInsId).list();
                            for (int i = tasks.size()-1 ; i>=0; i--){
                                if ("apply_cs".equals( tasks.get(i).getTaskDefinitionKey())) {
                                    tasks.remove(i) ;
                                }
                            }
                            String assigneeName = tasks.get(0).getAssignee();   //下一步审批人的登录名
                            User user = new User();
                            if (org.apache.commons.lang3.StringUtils.isNotEmpty(assigneeName)) {
                                user = systemService.getUserByLoginName(assigneeName);
                            }
                            String tableName = flowData.getTableName();
                            OaPersonDefineTable table = oaPersonDefineTableDao.findByTableName(tableName, null);
                            String tableComment = table.getTableComment();
                            if ("预借款申请单".equals(tableComment)) {
                                tableComment = "预借款" + " " + "申请单";
                            }
                            ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(tasks.get(0).getProcessDefinitionId());
                            List<ActivityImpl> activitiList = def.getActivities(); //rs是指RepositoryService的实例
                            String excId = tasks.get(0).getExecutionId();
                            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
                            String activitiId = execution.getActivityId();
                            for (ActivityImpl activityImpl : activitiList) {
                                String id = activityImpl.getId();
                                if (activitiId.equals(id)) {
                                    message = "【<span>" + tableComment + "</span>】提交成功<br>下一步【" + activityImpl.getProperty("name") + "】";
                                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(user.getName())) {
                                        message = message + "审批人是：<span>" + user.getName() + "</span>";
                                        //发送待办流程短信
                                        //  return SendSms.sendSmsO(Mobile, name1 + "您好，您有待办流程：" + name2 + "发起了" + content + "，请您尽快处理。");
                                        Act act = flowData.getAct();
                                        List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), startAct, endAct);
                                        String companyId= UserUtils.getUser().getCompany().getId();
                                        if (histoicFlowList.size() == 0) {
                                            SmsModule.flowWork(companyId,user.getMobile(), user.getName(), UserUtils.getUser().getName(), tableComment);
                                        } else {
                                            String name = histoicFlowList.get(0).getAssigneeName();
                                            procInsId = act.getProcInsId();
                                            // 获取代办流程候选人列表
                                            List<Task> tasks1 = taskService.createTaskQuery().processInstanceId(procInsId).list();
                                            String assigneeName1 = tasks1.get(0).getAssignee();   //下一步审批人的登录名
                                            String tableName1 = flowData.getTableName();
                                            OaPersonDefineTable table1 = oaPersonDefineTableDao.findByTableName(tableName1, null);
                                            String tableComment1 = table1.getTableComment();
                                            if ("预借款申请单".equals(tableComment1)) {
                                                tableComment1 = "预借款" + " " + "申请单";
                                            }
                                            if (org.apache.commons.lang3.StringUtils.isBlank(assigneeName1)) {
                                                List<IdentityLink> IdentityLinks = taskService.getIdentityLinksForTask(tasks.get(0).getId());
                                                if (IdentityLinks.size() == 1) {
                                                    for (IdentityLink il : IdentityLinks) {
                                                        String id1 = il.getUserId();
                                                        User u = new User();
                                                        u.setLoginName(id);
                                                        String name1 = user.getName();
                                                        String Mobile = user.getMobile();
                                                        SmsModule.flowWork(companyId,Mobile, name1, name, tableComment1);
                                                    }
                                                } else {
                                                    String str = "";
                                                    String str1 = "";
                                                    String Mobile = "";
                                                    List<String> Mobiles = new ArrayList<>();
                                                    for (IdentityLink il : IdentityLinks) {
                                                        String id1 = il.getUserId();
                                                        User u = new User();
                                                        u.setLoginName(id);
                                                        String name1 = user.getName();
                                                        str += name1 + "、";
                                                        str1 = str.substring(0, str.length() - 1);
                                                        Mobile = user.getMobile();
                                                        Mobiles.add(Mobile);
                                                    }
                                                    for (int i = 0; i < Mobiles.size(); i++) {
                                                        SmsModule.flowWork(companyId,Mobiles.get(i), str1, name, tableComment1);
                                                        System.out.println(str1 + "您好，您有待办流程：" + name + "发起了" + tableComment + "，请您尽快处理。");
                                                    }
                                                }
                                            } else {
                                                String name1 = null;
                                                String Mobile = null;
                                                for (int i = 1; i < histoicFlowList.size(); i++) {
                                                    name1 = histoicFlowList.get(i).getTaskName();
                                                    Mobile = histoicFlowList.get(i).getFlag();
                                                }
                                                SmsModule.flowWork(companyId,Mobile, name1, name, tableComment1);
                                            }
                                        }
                                    }
                                    response.setContentType("text/html;charset=utf-8");
                                    List<Object[]> processList = ProcessDefUtils.processList(null);
                                    //默认选择第一个流程
                                    StringBuilder selfFlowHTML = new StringBuilder();
                                    for (Object[] objs : processList) {
                                        ProcessDefinition process = (ProcessDefinition) objs[0];
                                        selfFlowHTML.append("<option " + (procDefId.equals(process.getId()) ? "selected=\"selected\"" : "")
                                                + " value=\"" + process.getId() + "\">" + process.getName() + "</option>");
                                    }
                                    String formKey = actTaskService.getFormKey(procDefId, null);
                                    if (org.apache.commons.lang3.StringUtils.isNotBlank(formKey) && !"/404".equals(formKey)) {
                                        OaFormMaster form = oaFormMasterService.findByNo(formKey, null);
                                        if (form != null) {
                                            String tableName1 = form.getTableName();
                                            flowParam.setTableName(tableName1);
                                            flowParam.setFormNo(form.getFormNo());
                                            OaPersonDefineTable table1 = oaPersonDefineTableDao.findByTableName(tableName1, null);

                                            OaPersonDefineTableColumn param = new OaPersonDefineTableColumn();
                                            param.setIsShow("1");
                                            param.setTable(table1);
                                            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findList(param);
                                            StringBuilder theadHTML = new StringBuilder();
                                            StringBuilder tbodyHTML = new StringBuilder();

                                            if(UserAgentUtils.isMobile(request)){
                                                for (OaPersonDefineTableColumn column : columns) {
                                                    tbodyHTML.append("<td>"+column.getColumnComment()+":"+"${item." + column.getColumnName() + "}</td><br/>");
                                                }
                                            }else {
                                                for (OaPersonDefineTableColumn column : columns) {
                                                    theadHTML.append("<th>" + column.getColumnComment() + "</th>");
                                                    tbodyHTML.append("<td>${item." + column.getColumnName() + "}</td>");
                                                }
                                            }


                                            Map<String, String> paramMap = new HashMap<>();
                                            paramMap.put("tableName", form.getTableName());
                                            paramMap.put("procDefId", procDefId);
                                            User user1 = UserUtils.getUser();
                                            if (!user1.isAdmin()) {
                                                paramMap.put("createBy", user1.getId());
                                            }
                                            Page<Map<String, Object>> page = getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                                            List<Map<String, Object>> flowInfo = page.getList();

                                            flowParam.setDatas(new HashMap<String, Object>());
                                            flowParam.getDatas().put("flowInfo", flowInfo);
                                            flowParam.getDatas().put("page", page);
                                            flowParam.getDatas().put("title", form.getTitle());
                                            Component c;
                                            if(UserAgentUtils.isMobile(request)){
                                                c = ComponentUtils.getComponent("myTouchFlow");
                                                html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                                                        .replace("$tbodyHTML$", tbodyHTML.toString());
                                            }
                                            else{
                                                c = ComponentUtils.getComponent("myFlow");
                                                html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                                                        .replace("$tbodyHTML$", tbodyHTML.toString())
                                                        .replace("$theadHTML$", theadHTML.toString());
                                            }
                                            List<ActCountInfo> list=  actCountInfoService.findInfonByProcInsId(procInsId);
                                            ActCountInfo actCountInfo =new ActCountInfo();
                                            if(list.size()!=0){
                                                actCountInfo.setId(list.get(0).getId());
                                            }
                                            actCountInfo.setTableName(table1.getTableComment());
                                            actCountInfo.setProcInstId(proInsId);
                                            actCountInfo.setProcState("0");
                                            actCountInfo.setProcDefId(procDefId);
                                            actCountInfo.setAssignee(user.getLoginName());
                                            actCountInfo.setActName((String) activityImpl.getProperty("name"));
                                            actCountInfo.setLoginName(UserUtils.getUser().getLoginName());
                                            actCountInfo.setCompanyId(UserUtils.getUser().getCompany().getId());
                                            actCountInfo.setCreateBy(table1.getCreateBy());
                                            actCountInfo.setCreateDate(table1.getCreateDate());
                                            actCountInfoService.save(actCountInfo);
                                        }
                                        flowParam.getDatas().put("message", message);
                                    }
                                    break;
                                }
                            }
                        } else {
                            message = "业务提交成功";
                            flowParam.getDatas().put("message", message);
                        }
                    }
                    response.getWriter().print(FreemarkerUtils.process(html, FlowUtils.toMap(flowParam)));
                    response.getWriter().flush();
                    return "success";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = e.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
            }
        }
        if(flowData.getAct().getTaskDefKey().startsWith("edit") && org.apache.commons.lang3.StringUtils.equalsIgnoreCase(flowData.getAct().getFlag(),"yes")){
            message = "审批已通过";
        }
        if(flowData.getAct().getTaskDefKey().startsWith("edit") && org.apache.commons.lang3.StringUtils.equalsIgnoreCase(flowData.getAct().getFlag(),"no")){
            message = "审批已被驳回";
        }
        RedirectMessageUtils.addMessage(redirectAttributes, message);
        return message;
    }

    @Transactional
    public void saveAudit(String startAct, String endAct, FlowData flowData, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(flowData.getAct().getComment()) && org.apache.commons.lang3.StringUtils.equals(flowData.getAct().getTaskDefKey(), "apply_end")) {
            flowData.getAct().setComment("归档");
        }
        String procInsId1=oaRunProcessService.findInstId(flowData.getAct().getTaskId());
        boolean boolFlag = oaRunProcessService.update0(new UpdatePrincipalInfo(),UserUtils.getUser().getLoginName(),procInsId1,request);
        //签收的审批方法
        actTaskService.claim(flowData.getAct().getTaskId(), UserUtils.getUser().getLoginName());
        //在审批的时候添加的个人签名的图片
        flowData = FlowUtils.savePersonsigns(flowData,"1",false);
        if(boolFlag){
            flowData.getAct().setComment(flowData.getAct().getComment()+"(代办)");
        }

        //审批同意
        String flag = auditSave(flowData);
        Act act = flowData.getAct();
        List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), startAct, endAct);
        String name = histoicFlowList.get(0).getAssigneeName();
        String procInsId = act.getProcInsId();
        // 获取代办流程候选人列表
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInsId).list();
        String companyId= UserUtils.getUser().getCompany().getId();

        if (tasks.size() == 1) {
            String assigneeName = tasks.get(0).getAssignee();   //下一步审批人的登录名
            String tableName = flowData.getTableName();
            OaPersonDefineTable table = oaPersonDefineTableDao.findByTableName(tableName, null);
            String tableComment = table.getTableComment();
            if ("预借款申请单".equals(tableComment)) {
                tableComment = "预借款" + " " + "申请单";
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(assigneeName)) {
                List<IdentityLink> IdentityLinks = taskService.getIdentityLinksForTask(tasks.get(0).getId());

                if (IdentityLinks.size() == 1) {
                    for (IdentityLink il : IdentityLinks) {
                        String id = il.getUserId();
                        User u = new User();
                        u.setLoginName(id);
                        List<User> ee = userDao.getByLoginName(u);
                        User user = ee.get(0);
                        String name1 = user.getName();
                        String Mobile = user.getMobile();
                        SmsModule.flowWork(companyId,Mobile, name1, name, tableComment);
                    }
                } else {
                    String str = "";
                    String str1 = "";
                    String Mobile = "";
                    List<String> Mobiles = new ArrayList<>();
                    for (IdentityLink il : IdentityLinks) {
                        String id = il.getUserId();
                        User u = new User();
                        u.setLoginName(id);
                        List<User> ss = userDao.getByLoginName(u);
                        User user = ss.get(0);
                        String name1 = user.getName();
                        str += name1 + "、";
                        str1 = str.substring(0, str.length() - 1);
                        Mobile = user.getMobile();
                        Mobiles.add(Mobile);
                    }
                    for (int i = 0; i < Mobiles.size(); i++) {

                        SmsModule.flowWork(companyId,Mobiles.get(i), str1, name, tableComment);
                    }
                }
            } else {
                String name1 = null;
                String Mobile = null;
                for (int i = 1; i < histoicFlowList.size(); i++) {
                    name1 = histoicFlowList.get(i).getTaskName();
                    Mobile = histoicFlowList.get(i).getFlag();
                }
                SmsModule.flowWork(companyId,Mobile, name1, name, tableComment);
            }
        }

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(flag, "yes") && !(flowData.getAct().getTaskDefKey().startsWith("apply_cs"))) {
            RedirectMessageUtils.addMessage(redirectAttributes, "审批已通过");
        }
        if(flowData.getAct().getTaskDefKey().startsWith("apply_cs")){
            RedirectMessageUtils.addMessage(redirectAttributes, "抄送任务已查看");
        }
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(flag, "no")) {
            RedirectMessageUtils.addMessage(redirectAttributes, "审批已驳回，等待申请人修改");
        }

        List<ActCountInfo> list=  actCountInfoService.findInfonByProcInsId(procInsId);
        if(list != null && list.size()>0){
            ActCountInfo actCountInfo =new ActCountInfo();
            if (org.apache.commons.lang3.StringUtils.equals(flowData.getAct().getTaskDefKey(), "apply_end")) {
                List<ActCountInfo> list1=  actCountInfoService.findInfonByProcInsIds(procInsId);
                actCountInfo.setId(list1.get(0).getId());
                actCountInfo.setProcState("1");
                actCountInfoService.save(actCountInfo);
            }
            else if("no".equals(flag)){
                actCountInfo.setActId(list.get(0).getActId());
                actCountInfo.setAssignee(list.get(0).getAssignee());
                actCountInfo.setActName(list.get(0).getActName());
                actCountInfo.setId(list.get(0).getId());
                actCountInfo.setProcState("5");
                actCountInfoService.save(actCountInfo);
            }else{
                actCountInfo.setActId(list.get(0).getActId());
                actCountInfo.setAssignee(list.get(0).getAssignee());
                actCountInfo.setActName(list.get(0).getActName());
                actCountInfo.setId(list.get(0).getId());
                actCountInfo.setProcState("0");
                actCountInfoService.save(actCountInfo);
            }
        }
    }



    /**
     * 保存附件路径
     *
     * @param file
     * @param name
     * @param signName
     * @param request
     * @throws java.io.IOException
     */
    @Transactional(readOnly = false)
    public String saveContractFile(MultipartFile file, String name, String signName, String id, HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        String filePath = null;
        String realPath = request.getSession().getServletContext().getRealPath("/static" + File.separator + "userfiles");
        String oldName = null;
        oldName = file.getOriginalFilename();
        if (StringUtils.isNotEmpty(oldName)) {
            String filename = oldName.substring(oldName.lastIndexOf("."));
            filename = UserUtils.getUser().getLoginName() + "_" + System.currentTimeMillis() + filename;
            realPath = realPath + File.separator + "attachment";
            File targetFile = new File(realPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            File desFile = new File(realPath + File.separator + filename);
            if (!desFile.exists()) {
                desFile.createNewFile();
            }
            filePath = desFile.getPath();
            FileOutputStream fileOutputStream = new FileOutputStream(desFile);
            String s = filePath.replaceAll("\\\\", "\\/");
            byte[] bytes = new byte[1024];
            while (!(inputStream.read(bytes) == -1)) {
                fileOutputStream.write(bytes);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            filePath = filePath.substring(filePath.lastIndexOf("static") - 1);
        }

        //文件保存成功
        return oldName + "," + filePath;
    }


    /**
     * 表单添加
     *
     * @param flowData
     * @throws Exception
     */
    public String insertTable(FlowData flowData) {
        String tableName = flowData.getTableName();
        Map<String, Object> data = flowData.getDatas();
        if(data.containsKey("COMPANYID")){
            data.put("COMPANYID",UserUtils.getUser().getCompany().getId());}
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
                    if (!entry.getKey().equalsIgnoreCase("BZ")) {
                        insertKey.append(comma + entry.getKey());
                    }
                    if (entry.getValue() != null && entry.getValue().toString().length() > 0) {

                        if (entry.getKey().equalsIgnoreCase("CCR") && tableName.startsWith("JMY")) {
                            userId = entry.getValue().toString();
                            User user1 = new User();
                            user1.setName(userId.replace("'", ""));
                            user1.setCompanyId(UserUtils.getUser().getCompany().getId());
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
                            if ((entry.getKey().equalsIgnoreCase("CCR") || entry.getKey().equalsIgnoreCase("SQR")) && tableName.startsWith("JMY") ) {
                                dept = updateDept(entry.getKey(), entry.getValue().toString());
                            }
                            if (entry.getKey().equalsIgnoreCase("BM")) {
                                if (StringUtils.isNotEmpty(dept)) {
                                    insertValue.append(comma + "'" + dept + "'");
                                } else {
                                    if (!entry.getKey().equalsIgnoreCase("BZ")) {
                                        insertValue.append(comma + entry.getValue());
                                    }
                                }
                            } else {
                                if (entry.getKey().equalsIgnoreCase("FJLJ")) {
                                    String ls = entry.getValue().toString().replace("\\", "\\\\");
                                    insertValue.append(comma + ls);
                                } else {
                                    if (!entry.getKey().equalsIgnoreCase("BZ")) {
                                        insertValue.append(comma + entry.getValue());
                                    }
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
        return userId;
    }


    public String updateDept(String key, String value) {
        User user = new User();
        user.setName(value.replace("\'", ""));
        user.setCompanyId(UserUtils.getUser().getCompany().getId());
        user = userDao.getByName(user);
        Office office = officeDao.get(user.getOffice().getId());
        return office.getName();
    }

    /**
     * 表单添加
     *
     * @param flowData
     * @throws Exception
     */
    public void updateTable(FlowData flowData) {
        String tableName = flowData.getTableName();
        Map<String, Object> data = flowData.getDatas();
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName, null);
        if (defineTable != null && data != null && StringUtils.isNotBlank(tableName) && flowData.getAct().getTaskDefKey().startsWith("modify")) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            dataAdapter(columns, data);
            String comma = "";
            StringBuffer updateValue = new StringBuffer();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                // 判断key是否为表配置的属性
                if (isContainsFieled(columns, entry.getKey())) {
                    if (!(entry.getKey().equalsIgnoreCase("FJLJ") || entry.getKey().equalsIgnoreCase("FJMC"))) {
                        updateValue.append(comma + entry.getKey() + "=");
                    }
                    if (entry.getValue() != null && entry.getValue().toString().length() > 0) {
                        if (entry.getKey().equalsIgnoreCase("BH")) {
                            String sql = "select max(BH) from " + defineTable.getTableName();
                            String max = oaPersonDefineTableDao.getMax(sql);
                            if (StringUtils.isNotEmpty(max)) {
                                String sub = max.substring(1, max.length());
                                int orderValue = Integer.parseInt(sub);
//                                orderValue++;
                                int n = orderValue;
                                NumberFormat formatter = NumberFormat.getNumberInstance();
                                formatter.setMinimumIntegerDigits(6);
                                formatter.setGroupingUsed(false);
                                String s = formatter.format(n);
                                String now = "A" + s;
                                updateValue.append("'" + now + "'");
                                comma = ", ";
                            } else {
                                String ret = "A000001";
                                updateValue.append("'" + ret + "'");
                                comma = ", ";
                            }
                        } else {
                            if (entry.getKey().equalsIgnoreCase("FJMC") || entry.getKey().equalsIgnoreCase("FJLJ")) {
                                if (entry.getKey().equalsIgnoreCase("FJLJ")) {
                                    String lj = entry.getValue().toString().replace("\\", "\\\\");
                                    updateValue.append(comma + entry.getKey() + "=" + lj);
                                    comma = ", ";
                                } else {
                                    updateValue.append(comma + entry.getKey() + "=" + entry.getValue());
                                    comma = ", ";
                                }
                            } else {
                                updateValue.append(entry.getValue());
                                comma = ", ";
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
                                updateValue.append("'" + now + "'");
                                comma = ", ";
                            } else {
                                String ret = "A000001";
                                updateValue.append("'" + ret + "'");
                                comma = ", ";
                            }
                        } else {
                            if (!(entry.getKey().equalsIgnoreCase("FJLJ") || entry.getKey().equalsIgnoreCase("FJMC"))) {
                                updateValue.append("null");
                                comma = ", ";
                            }

                        }
                    }
                }
            }
            updateValue.append(comma + "update_by=" + handleSqlValue(flowData.getUpdateBy(), "varchar"))
                    .append(",update_date=" + handleSqlValue(flowData.getUpdateDate() == null ? null : DateUtils.formatDateTime(flowData.getUpdateDate()), "date"));
            String sql = "UPDATE " + tableName + " SET " + updateValue + " where id='" + flowData.getId() + "'";
            oaPersonDefineTableDao.executeSql(sql);
        } else if (flowData.getAct().getTaskDefKey().startsWith("edit")) {
            OaPersonDefineTable defineTable1 = oaPersonDefineTableDao.findByTableName(tableName, null);
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable1.getId());
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
    }

    public List<Map<String, Object>> getFlowInfo(Map<String, String> paramMap) {
        String sql = getSelectSql(paramMap);
        if (StringUtils.isNotBlank(sql)) {
            return oaPersonDefineTableDao.getFlowInfo(sql);
        }
        return Lists.newArrayList();
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

    public Map<String, Object> getOneInfo(Map<String, String> paramMap) {
        List<Map<String, Object>> infos = getFlowInfo(paramMap);
        if (infos != null && infos.size() > 0) {
            return infos.get(0);
        }
        return Maps.newHashMap();
    }


//
//	public Page<TestAudit> findPage(Page<TestAudit> page, TestAudit testAudit) {
//		testAudit.setPage(page);
//		page.setList(dao.findList(testAudit));
//		return page;
//	}
//
    /**
     * 查询当前登陆人发起的所有流程信息
     *
     * @return
     */
    @Transactional(readOnly = false)
    public List<OaPersonDefineTable> flowList() {
        //先查询出所有的流程表
        List<Map<String,Map<String,Object>>>list = new ArrayList<>();
        OaPersonDefineTable paramObj = new OaPersonDefineTable();
        paramObj.setOffice(UserUtils.getUser().getCompany());
        List<OaPersonDefineTable> oaPersonDefineTables = oaPersonDefineTableDao.findList(paramObj);
        for(OaPersonDefineTable table : oaPersonDefineTables){
            String sql=null;
            String name=UserUtils.getUser().getRoleNames();
            if(StringUtils.equalsIgnoreCase("1",UserUtils.getUser().getId())){
                sql ="select proc_def_id  from " + table.getTableName() + " where del_flag = '0'";
            }else{
                sql ="select proc_def_id  from " + table.getTableName() + " where del_flag = '0' and create_by = '" + UserUtils.getUser().getId() + "'";
            }
            if(StringUtils.isNotBlank(sql)){
                List<Map<String,Object>> sqls = oaPersonDefineTableDao.getFlowInfo(sql);
                if(sqls != null && sqls.size()>0){
                    Map<String,Map<String,Object>> maps = new HashMap<>();
                    maps.put(table.getTableComment(),sqls.get(0));
                    list.add(maps);
                }
            }else{
                continue;
            }

        }
        List<OaPersonDefineTable> stringList =new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            Map<String,Map<String,Object>> map = list.get(i);
            for(Map.Entry<String,Map<String,Object>> entry : map.entrySet()){
                for(Map.Entry<String,Object> m : entry.getValue().entrySet()){
                    OaPersonDefineTable oaPersonDefineTable = new OaPersonDefineTable();
                    oaPersonDefineTable.setTableName(entry.getKey());
                    oaPersonDefineTable.setProcDefId(m.getValue().toString());
                    stringList.add(oaPersonDefineTable);
                }
            }
        }
        return stringList;
    }

    /**
     * 审核新增或编辑
     *
     * @param flowData
     */
    @Transactional(readOnly = false)
    public String saveFlow(FlowData flowData) {
        User user = UserUtils.getUser();
        String companyId = user.getCompany().getId();
        String DEL_FLAG_NORMAL="0";
        Map<String, Object> vars = handlerVar(flowData.getTableName(), flowData.getDatas());
        String parentId = user.getAcName();

        if("1".equals(Global.getConfig("auto.zr"))) {
            String gradeConfig = Global.getConfig("audo.grade.parent");
            if(StrUtil.isNotBlank(gradeConfig)) {
                String[] parentGrade = StrUtil.splitToArray(gradeConfig, "#");
                List<String> submitGrade = StrUtil.split(parentGrade[0], ",");
                if(submitGrade.contains(user.getGrade())) {
                    User firstGrade = UserUtils.getFirstUserByGrades(parentGrade[1]);
                    if(firstGrade == null) {
                        throw new RuntimeException("保存失败：获取科室主任失败");
                    }
                    parentId = firstGrade.getLoginName();
                }
            }
        }

        vars.put("PostGrade", user.getGrade());
        vars.put("parentId", parentId);
        vars.put("dept", user.getAcDeptName());
        vars.put("leader", user.getLevel1Name());
        String zdr = zdr(flowData);
        if(zdr != null){
            vars.put("zdshr", zdr);
        }

        AddDict dict = new AddDict();
        dict.setCompanyId(companyId);
        List<AddDict> dicts = addDictDao.findList(dict);
        if(dicts != null && dicts.size()>0){
            for(int i=0;i<dicts.size();i++){
                AddDict ad19= addDictDao.findByLabel(dicts.get(i).getLabel(), companyId,DEL_FLAG_NORMAL);
                if(ad19!=null){
                    vars.put(ad19.getValue(), ad19.getUserName());
                }
            }
        }


        AddDict ad32= addDictDao.findByLabel("合同管理员", companyId,DEL_FLAG_NORMAL);
        if(ad32!=null){
            vars.put(ad32.getValue(), ad32.getUserName());
        }
        //选择审批人
        if(flowData.getDatas().get("XZSPR")!=null){
            String XZSPR=flowData.getDatas().get("XZSPR").toString();
            if(StringUtils.isNotBlank(XZSPR)){
                User user11=new User();
                user11.setName(XZSPR);
                User user2=userDao.findByName(user11);
                String XZSPRLogimn="";
                if(user2!=null){
                    XZSPRLogimn=user2.getLoginName();
                }
                AddDict xzspr1 = addDictDao.findByLabel("审批人", "",DEL_FLAG_NORMAL);
                xzspr1.setUserName(XZSPRLogimn);
                String spr;
                String sprName;
                if(xzspr1!=null){
                    xzspr1.setUserName(XZSPRLogimn);
                    spr =xzspr1.getValue();
                    sprName =xzspr1.getUserName();
                    vars.put(spr, sprName);
                }
            }
        }


        //选择审批人
        if(flowData.getDatas().get("XZZNKS")!=null){
            String ZNKS=flowData.getDatas().get("XZZNKS").toString();
            if(StringUtils.isNotBlank(ZNKS)){
                User user11=new User();
                user11.setName(ZNKS);
                User user2=userDao.findByName(user11);
                String ZNKSLogimn="";
                if(user2!=null){
                    ZNKSLogimn=user2.getLoginName();
                }
                AddDict znks1 = addDictDao.findByLabel("职能科室", "",DEL_FLAG_NORMAL);
                znks1.setUserName(ZNKSLogimn);
                String znks;
                String znksName;
                if(znks1!=null){
                    znks1.setUserName(ZNKSLogimn);
                    znks =znks1.getValue();
                    znksName =znks1.getUserName();
                    vars.put(znks, znksName);
                }
            }
        }


        //选择审批人
        if(flowData.getDatas().get("XZZGBM")!=null){
            String ZGBM=flowData.getDatas().get("XZZGBM").toString();
            if(StringUtils.isNotBlank(ZGBM)){
                User user11=new User();
                user11.setName(ZGBM);
                User user2=userDao.findByName(user11);
                String ZGBMLogimn="";
                if(user2!=null){
                    ZGBMLogimn=user2.getLoginName();
                }
                AddDict zgbm1 = addDictDao.findByLabel("主管部门", "",DEL_FLAG_NORMAL);
                zgbm1.setUserName(ZGBMLogimn);
                String zgbm;
                String zgbmName;
                if(zgbm1!=null){
                    zgbm1.setUserName(ZGBMLogimn);
                    zgbm =zgbm1.getValue();
                    zgbmName =zgbm1.getUserName();
                    vars.put(zgbm, zgbmName);
                }
            }
        }
        //呈批件中的呈送人
        if(flowData.getDatas().get("CSR")!=null){
            String CSR=flowData.getDatas().get("CSR").toString();
            if(StringUtils.isNotBlank(CSR)){
                User user11=new User();
                user11.setName(CSR);
                User user2=userDao.findByName(user11);
                String CSRLogimn="";
                if(user2!=null){
                    CSRLogimn=user2.getLoginName();
                }
                AddDict csr = addDictDao.findByLabel("呈批件", "",DEL_FLAG_NORMAL);
                csr.setUserName(CSRLogimn);
                String reportName;
                String report;
                if(csr!=null){
                    csr.setUserName(CSRLogimn);
                    report =csr.getValue();
                    reportName =csr.getUserName();
                    vars.put(report, reportName);
                }
            }else{
                vars.put("report","");
            }
        }


        // 申请发起
        if (StringUtils.isBlank(flowData.getId())) {
            flowData.preInsert();
            String name = insertTable(flowData);
            if (StringUtils.isNotEmpty(name)) {
                User u = new User();
                u.setName(name.replace("'", ""));
                u.setCompanyId(UserUtils.getUser().getCompany().getId());
                User user1 = userDao.findByName(u);
                // 启动流程
                return actTaskService.startProcess(flowData.getFlowFlag(), flowData.getTableName(), flowData.getId(), "自定义流程", vars, user1.getLoginName());
            } else {
                return actTaskService.startProcess(flowData.getFlowFlag(), flowData.getTableName(), flowData.getId(), "自定义流程", vars, "");
            }
        }
        // 重新编辑申请
        else {
            flowData.preUpdate();
            updateTable(flowData);
            if (flowData.getAct().getComment() == null) {
                flowData.getAct().setComment("");
            }
            if ((flowData.getAct().getTaskDefKey()).startsWith("edit")) {

                String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
                actTaskService.claim(flowData.getAct().getTaskId(), userId);
                flowData.getAct().setComment(("yes".equals(flowData.getAct().getFlag()) ? "[同意] " : "[驳回] ") + flowData.getAct().getComment());
                OaPersonDefineTable defineTable1 = oaPersonDefineTableDao.findByTableName(flowData.getTableName(), null);
                List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable1.getId());
                for (OaPersonDefineTableColumn column : columns) {
                    if (flowData.getAct().getTaskDefKey().equals(column.getAuditPost())) {
                        String sql = "update " + flowData.getTableName() + " set "
                                + column.getColumnName() + "='" + flowData.getAct().getComment()
                                + "' where id='" + flowData.getId() + "'";
                        oaPersonDefineTableDao.executeSql(sql);
                        break;
                    }
                }

                // 提交流程任务
                Map<String, Object> var = Maps.newHashMap();
                var.put("pass", "yes".equals(flowData.getAct().getFlag()) ? "1" : "0");
                //是否进行抄送
                var.put("assigneesList",assigneeList(flowData));
                actTaskService.complete(flowData.getAct().getTaskId(), flowData.getAct().getProcInsId(), flowData.getAct().getComment(), var);
            } else {
                flowData.getAct().setComment(("yes".equals(flowData.getAct().getFlag()) ? "[重申] " : "[销毁] ") + flowData.getAct().getComment());
                // 完成流程任务
                vars.put("pass", "yes".equals(flowData.getAct().getFlag()) ? "1" : "0");
                actTaskService.complete(flowData.getAct().getTaskId(), flowData.getAct().getProcInsId(), flowData.getAct().getComment(), "自定义流程", vars);
                if ("yes".equals(flowData.getAct().getFlag())) {
                    OaPersonDefineTable table = oaPersonDefineTableDao.findByTableName(flowData.getTableName(), null);
                    OaPersonDefineTableColumn param = new OaPersonDefineTableColumn(table);
                    param.setIsAudit("1");
                    List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findList(param);
                    StringBuilder sb = new StringBuilder("update " + flowData.getTableName() + " set ");
                    String split = "";
                    for (OaPersonDefineTableColumn column : columns) {
                        sb.append(split + column.getColumnName() + "=''");
                        split = ",";
                    }
                    sb.append(" where id='" + flowData.getId() + "'");
                    oaPersonDefineTableDao.executeSql(sb.toString());
                }
            }
        }
        return "";
    }

    /**
     * 审核审批保存
     *
     * @param flowData
     */
    @Transactional(readOnly = false)
    public String auditSave(FlowData flowData) {
        // 设置意见
        if(!(flowData.getAct().getTaskDefKey().startsWith("apply_cs"))){
            flowData.getAct().setComment(("yes".equals(flowData.getAct().getFlag()) ? "[同意]" : "[驳回] ") + flowData.getAct().getComment());
        }
        flowData.preUpdate();
        // 对不同环节的业务逻辑进行操作
        String taskDefKey = flowData.getAct().getTaskDefKey();
        // 审核环节
        if (taskDefKey.startsWith("audit") || (taskDefKey).startsWith("apply_execute") || taskDefKey.startsWith("apply_end") || taskDefKey.startsWith("apply_cs")) {
            OaPersonDefineTable table = oaPersonDefineTableDao.findByTableName(flowData.getTableName(), null);
            OaPersonDefineTableColumn param = new OaPersonDefineTableColumn(table);
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findList(param);
            boolean recordXc = false;
            for (OaPersonDefineTableColumn column : columns) {
                if(!recordXc && flowData.getTableName().equalsIgnoreCase("t_xfaqxcb") && taskDefKey.startsWith("apply_end")) {
                    handleXc(columns, flowData.getId());
                    recordXc = true;
                }
                if ("1".equals(column.getIsAudit()) && StrUtil.split(column.getAuditPost(), ",").contains(taskDefKey)) {
                    String sql = "update " + flowData.getTableName() + " set "
                            + column.getColumnName() + "='" + flowData.getAct().getComment()
                            + "' where id='" + flowData.getId() + "'";
                    oaPersonDefineTableDao.executeSql(sql);
                    break;
                }
            }
            OaFlowAuditRecord record = new OaFlowAuditRecord();
            record.setTableName(flowData.getTableName());
            record.setRecordId(flowData.getId());
            if(SecurityEngineUtils.enableStatus()) {
                record.setSigData(flowData.getDsvsInfo().getData());
                record.setSigDataBase64(flowData.getDsvsInfo().getBase64Data());
                record.setSig(flowData.getDsvsInfo().getSig());
                record.setSigServer(flowData.getDsvsInfo().getSigServer());
                record.setTs(flowData.getDsvsInfo().getTs());
                record.setSigPic(flowData.getDsvsInfo().getSigPic());
            }
            record.setAuditResult(flowData.getAct().getFlag());
            record.setAuditComment(flowData.getAct().getComment());
            record.preInsert();
            oaFlowAuditRecordDao.insert(record);
        } else {
            return "";
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tableName", flowData.getTableName());
        paramMap.put("procInsId", flowData.getAct().getProcInsId());
        paramMap.put("id", flowData.getId());
        Map<String, Object> oneInfo = getOneInfo(paramMap);
        // 提交流程任务
        Map<String, Object> vars = handlerVar(flowData.getTableName(), oneInfo);
        vars.put("pass", "yes".equals(flowData.getAct().getFlag()) ? "1" : "0");

        String zdr = zdr(flowData);
        if(zdr != null){
            vars.put("zdshr", zdr);
        }
        vars.put("assigneesList",assigneeList(flowData));
        actTaskService.complete(flowData.getAct().getTaskId(), flowData.getAct().getProcInsId(), flowData.getAct().getComment(), vars);
        return flowData.getAct().getFlag();
    }

    private void handleXc(List<OaPersonDefineTableColumn> columns, String id) {
        List<Map<String, Object>> datas = oaPersonDefineTableDao.getFlowInfo("select * from t_xfaqxcb where id = '" + id + "'");
        if(!datas.isEmpty()) {
            Map<String, Object> data = datas.get(0);
            for (OaPersonDefineTableColumn column : columns) {
                if("xcjg".equalsIgnoreCase(column.getRemarks())) {
                    String value = (String) data.get(column.getColumnName());
                    if("0".equals(value)) {
                        OaXccl oaXccl = new OaXccl();
                        oaXccl.setTableId(column.getTable().getId());
                        oaXccl.setColumnName(column.getColumnName());
                        oaXccl.setRecordId(id);
                        oaXccl.setRecordProject(column.getColumnComment());
                        oaXccl.setRecordValue(value);
                        if(data.containsKey(data.get(column.getColumnName() + "BZ"))) {
                            oaXccl.setRecordRemark(data.get(column.getColumnName() + "BZ").toString());
                        }
                        oaXccl.preInsert();
                        oaXcclDao.insert(oaXccl);
                    }
                }
            }
        }
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
        } else if ("varchar".equalsIgnoreCase(type) || "varchar2".equalsIgnoreCase(type)) {
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

    private String getSelectSql(Map<String, String> paramMap) {
        String tableName = paramMap.get("tableName");
        String procInsId = paramMap.get("procInsId");
        String id = paramMap.get("id");
        String createBy = paramMap.get("createBy");
        String procDefId = paramMap.get("procDefId");
        String name=paramMap.get("name");
        String dept=paramMap.get("dept");
        String numberDay=paramMap.get("numberDay");
        String fh=paramMap.get("fh");
        String arriveDay=paramMap.get("arriveDay");
        String startDate=paramMap.get("startDate");
        String endDate=paramMap.get("endDate");
        StringBuilder sql = null;
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName, null);
        if (defineTable != null) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            if (columns != null && columns.size() > 0) {
                sql = new StringBuilder("select id,proc_def_id procDefId,proc_ins_id procInsId,create_by");
                for (OaPersonDefineTableColumn column : columns) {
                    if ("DATE".equals(column.getColumnType().toUpperCase())) {
                        sql.append(",replace(date_format(" + column.getColumnName() + ",'%Y-%m-%d'),' 00:00:00','') " + column.getColumnName());
                    } else {
                        sql.append("," + column.getColumnName());
                    }
                }
                sql.append(" from " + tableName + " where del_flag = '0'");
                if (StringUtils.isNotBlank(id)) {
                    sql.append(" and id='" + id + "' ");
                }
                for (OaPersonDefineTableColumn column : columns) {
                    if(StringUtils.equalsIgnoreCase(column.getColumnComment(),"申请人") || StringUtils.equalsIgnoreCase(column.getColumnComment(),"出差人") || StringUtils.equalsIgnoreCase(column.getColumnComment(),"姓名")
                            || StringUtils.equalsIgnoreCase(column.getColumnComment(),"被调薪人")){
                        if(StringUtils.isNotBlank(name)){
                            sql.append(" and "+column.getColumnName()+" like '%" + name + "%' ");
                        }
                    }

                    if(column.getColumnComment().indexOf("天数")!=-1){
                        if(StringUtils.isNotBlank(numberDay)&& StringUtils.isNotBlank(fh)){
                            if(StringUtils.equalsIgnoreCase(fh,"1")){
                                sql.append(" and "+column.getColumnName()+" >=  '" + numberDay + "' ");
                            }else if(StringUtils.equalsIgnoreCase(fh,"2")){
                                sql.append(" and "+column.getColumnName()+"  <=  '" + numberDay + "' ");
                            }else if(StringUtils.isNotBlank(arriveDay) && StringUtils.equalsIgnoreCase(fh,"3")){
                                sql.append(" and "+column.getColumnName()+" >= '" + numberDay + "' and "+column.getColumnName() +"<="+arriveDay);
                            }
                        }

                    }

                    if(column.getColumnComment().indexOf("开始时间")!=-1 || column.getColumnComment().indexOf("开始日期")!=-1 ) {
                        if(StringUtils.isNotBlank(startDate)){
                            sql.append(" and "+column.getColumnName()+" >= '" + startDate + "' ");
                        }
                    }

                    if(column.getColumnComment().indexOf("结束时间")!=-1 || column.getColumnComment().indexOf("结束日期")!=-1 ) {
                        if(StringUtils.isNotBlank(endDate)) {
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
                if (StringUtils.isNotBlank(createBy)) {
                    sql.append(" and create_by='" + createBy + "' ");
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

    private Map<String, Object> handlerVar(String tableName, Map<String, Object> datas) {
        Map<String, Object> vars = Maps.newHashMap();
        if (StringUtils.isNotBlank(tableName) && datas != null && datas.keySet().size() < 65) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.getColumns(tableName);
            if (columns != null && columns.size() > 0) {
                for (OaPersonDefineTableColumn column : columns) {
                    vars.put(column.getColumnName(), datas.get(column.getColumnName()));
                }
            }
        } else {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.getColumns(tableName);
            if (columns != null && columns.size() > 0) {
                for (OaPersonDefineTableColumn column : columns) {
                    if (column.getIsProcess() != null) {
                        vars.put(column.getColumnName(), datas.get(column.getColumnName()));
                    }
                }
            }
        }
        return vars;
    }


    /**
     * 选择是否抄送的方法
     * @param flowData
     * @return
     */
    public List assigneeList(FlowData flowData){
        List<String> assigneeListBefore=new ArrayList<>(); //分配集合中的任务的人员
        String[] userLoginNames = null;
        //抄送任务
        if(flowData.getAct().getCSLoginNames() !=null && StringUtils.isNotBlank(flowData.getAct().getCSLoginNames())){
            userLoginNames = flowData.getAct().getCSLoginNames().split(",");
            Collections.addAll(assigneeListBefore,userLoginNames);
        }
        //驳回抄送
        String SQR = null;
        if(!StringUtils.equalsIgnoreCase(flowData.getAct().getFlag(),"yes")){
            String sql="SELECT * FROM act_hi_identitylink WHERE PROC_INST_ID_='"+flowData.getAct().getProcInsId()+"' and  type_='starter'";
            List<Map<String,Object>> sqls = oaPersonDefineTableDao.getFlowInfo(sql);
            if(sqls != null && sqls.size()>0){
                Map<String,Object> map = sqls.get(0);
                String s=null;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if(StringUtils.equalsIgnoreCase("USER_ID_",entry.getKey())){
                        SQR = entry.getValue().toString();
                        break;
                    }
                }
            }
            List<HistoricActivityInstance> hisActInstList = historyService.createHistoricActivityInstanceQuery().processInstanceId(flowData.getAct().getProcInsId()).activityType("userTask").list();
            if(hisActInstList !=null && hisActInstList.size()>0){
                for(HistoricActivityInstance historicActivityInstance : hisActInstList){
                    if(!(StringUtils.equalsIgnoreCase(historicActivityInstance.getActivityId(),flowData.getAct().getTaskDefKey())) && !(historicActivityInstance.getActivityName().startsWith("抄送任务"))
                            && !(StringUtils.equalsIgnoreCase("modify",historicActivityInstance.getActivityId()))){
                        assigneeListBefore.add(historicActivityInstance.getAssignee());
                    }
                }
            }
        }
        //去除list中的重复数据
        Set set = new  HashSet();
        List assigneeList = new  ArrayList();
        for (String cd:assigneeListBefore) {
            if(set.add(cd)){
                assigneeList.add(cd);
            }
        }
        return assigneeList;
    }

    public String zdr(FlowData flowData){
        if(flowData.getAct().getZDLoginNames() !=null && StringUtils.isNotBlank(flowData.getAct().getZDLoginNames())){
            String[] loginNames = flowData.getAct().getZDLoginNames().split(",");
            if(loginNames.length > 0) {
                return loginNames[0];
            }
        }
        return null;
    }

    /**
     * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致
     * @param request
     * @return
     *         true 用户重复提交了表单
     *         false 用户没有重复提交表单
     */
    private boolean isRepeatSubmit(HttpServletRequest request ,String token) {
        String clientToken = token;
        //1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
        if(clientToken==null){
            return true;
        }
        //取出存储在Session中的token
        String serverToken = (String) request.getSession().getAttribute("token");
        //2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
        if(serverToken==null){
            return true;
        }
        //3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
        if(!clientToken.equals(serverToken)){
            return true;
        }
        return false;
    }
}
