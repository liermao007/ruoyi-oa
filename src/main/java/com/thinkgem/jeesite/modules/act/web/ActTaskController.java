/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.act.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.SynchroDataUtils;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;
import com.thinkgem.jeesite.modules.act.dao.ActDao;
import com.thinkgem.jeesite.modules.act.entity.HomAct;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefUtils;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.units.FreemarkerUtils;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import com.thinkgem.jeesite.modules.test1.dao.OaRunProcessDao;
import com.thinkgem.jeesite.modules.test1.entity.OaRunProcess;
import com.thinkgem.jeesite.modules.test1.entity.UpdatePrincipalInfo;
import com.thinkgem.jeesite.modules.test1.service.UpdatePrincipalInfoService;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.eclipse.jetty.server.HomeBaseWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 流程个人任务相关Controller
 *
 * @author oa
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/task")
public class ActTaskController extends BaseController {
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);


    @Autowired
    private ActDao actDao;
    @Autowired
    private ActTaskService actTaskService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OaRunProcessDao oaRunProcessDao;

    @Autowired
    private OaPersonDefineTableDao oaPersonDefineTableDao;

    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private OaFormMasterService oaFormMasterService;

    @Autowired
    private UpdatePrincipalInfoService updatePrincipalInfoService;

    @Autowired
    private UserDao userDao;


    public void setOaFormMasterService(OaFormMasterService oaFormMasterService) {
        this.oaFormMasterService = oaFormMasterService;
    }

    /**
     * 获取待办列表
     *
     * @return
     */
    @RequestMapping(value = "todomain1")
    public String todomain1(Act act, HttpServletRequest request, HttpServletResponse response, Model model,String agentid) throws Exception {
        List<Act> list = actTaskService.todoList(act,"");
        List<OaRunProcess> list1 = oaRunProcessDao.findById(UserUtils.getUser().getId(),"1");
        for(OaRunProcess oaRunProcess:list1){
            List<Act> temp = actTaskService.todoList(act,oaRunProcess.getPrincipalid());
            for(int i=0;i<temp.size();i++){
                Act act1 = temp.get(i);
                act1.setCSLoginNames(oaRunProcess.getPrincipalid());
                temp.set(i,act1);
            }
            list.addAll(temp);
        }
        model.addAttribute("list", list);
        if (UserUtils.getPrincipal(null).isMobileLogin()) {
            return renderString(response, list);
        }
        return  "modules/act/actTaskTodoList";
    }


    /**
     * 获取待办列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = {"todo", ""})
    public String todoList(Act act,String userId,HttpServletRequest request,  HttpServletResponse response, Model model) throws Exception {
        List<Act> list = actTaskService.todoList(act,userId);

        List<OaRunProcess> list1 = oaRunProcessDao.findById(UserUtils.getUser().getId(),"1");
        for(OaRunProcess oaRunProcess:list1){
            List<Act> temp = actTaskService.todoList(act,oaRunProcess.getPrincipalid());
            for(int i=0;i<temp.size();i++){
                Act act1 = temp.get(i);
                act1.setCSLoginNames(oaRunProcess.getPrincipalid());
                temp.set(i,act1);
            }
            list.addAll(temp);
        }
        if (UserUtils.getPrincipal(null).isMobileLogin()) {
            return renderString(response, list);
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        model.addAttribute("list", list);
        //判断是否是手机端
        if(UserAgentUtils.isMobile(request)){
            return "modules/act/actTaskTodoList";
        }
        return "modules/act/actTaskTodoList";
    }

    /**
     * OA手机端获取待办列表（废掉 不需要写俩个）
     */
//    @RequestMapping(value = {"todo", ""})
//    public String oaSelfTodo(Act act,String userId, HttpServletResponse response, Model model) throws Exception {
//        List<Act> list = actTaskService.todoList(act,userId);
//        model.addAttribute("list", list);
//        if (UserUtils.getPrincipal().isMobileLogin()) {
//            return renderString(response, list);
//        }
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//        if(DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType())){
//            return "modules/sys/sysIndex";
//        }
//        return "modules/oa/oaSelfTodo";
//    }


    /**
     * 获取已办任务
     *
     * @param
     * @param act 流程定义标识
     * @return
     */
    @RequestMapping(value = "historic")
    public String historicList(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        return "redirect:historicList";
//        Page<Act> page = new Page<Act>(request, response);
//        page = actTaskService.historicList(page, act);
//        model.addAttribute("page", page);
////        if (UserUtils.getPrincipal().isMobileLogin()) {
////            return renderString(response, page);
////        }
//        return "modules/act/actTaskHistoricList";
    }


    /**
     * 获取最近已办任务
     * @param act
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "toDoTasks")
    public String toDoTasks(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        String cate = null;
        List<Object[]> processList = ProcessDefUtils.processList(cate);
        ArrayList list=new ArrayList();
        for (Object[] objs : processList) {
            ProcessDefinition  process = (ProcessDefinition) objs[0];
            list.add(process);
        }
        if(StringUtils.isNotBlank(act.getApplyUserId())){
            User user =  UserUtils.get(act.getApplyUserId());
            if(user != null) {
                act.setApplyUserId(user.getLoginName());
            }
        }

        long listAmount = 0;
        Page<Act> page = actTaskService.toDoTasks(new Page<Act>(request,response),act);
        //  listAmount = page.getCount();
        model.addAttribute("processList", list);
        //  model.addAttribute("listAmount",listAmount);
        model.addAttribute("page" ,page);
        return "modules/act/toDoTasks";
    }


    /**
     * 获取已办任务
     *
     * @param
     * @param act 流程定义标识
     * @return
     */
    @RequestMapping(value = "historicList")
    public String historicList1(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        System.err.print(act);
        List<OaPersonDefineTable>  list = actTaskService.historicList1(act);
        List<UpdatePrincipalInfo> Infos= updatePrincipalInfoService.findByPrincipalid(UserUtils.getUser().getId());
        List<OaPersonDefineTable>  list0 = new ArrayList<>();
        for(UpdatePrincipalInfo updatePrincipalInfo:Infos){
            List<OaPersonDefineTable> list3 = oaPersonDefineTableDao.findListByInstId(updatePrincipalInfo.getProcinstId());
            for (int i=0;i<list3.size();i++){
                OaPersonDefineTable oaPersonDefineTable = list3.get(i);
                oaPersonDefineTable.setTableComment("(代理人："+UserUtils.get(updatePrincipalInfo.getAgentId()).getName()+")"+oaPersonDefineTable.getTableComment());
                oaPersonDefineTable.setUpdateType(updatePrincipalInfo.getAgentId());
                list3.set(i,oaPersonDefineTable);
            }
            list0.addAll(list3);
        }
        Page<OaPersonDefineTable> page = new Page<>();
        page.setList(list);
        model.addAttribute("page",page);

        Page<OaPersonDefineTable> page2 = new Page<>();
        page2.setList(list0);
        model.addAttribute("page2",page2);

        List<OaRunProcess> list1 = actTaskService.historicList2(act);
        Page<OaRunProcess> page1 = new Page<>();
        page1.setList(list1);
        model.addAttribute("page1",page1);



        return "modules/act/actTaskHistoricList";
    }

    /**
     * OA手机首页已办事项
     */
    @RequestMapping(value = "actTaskHistoric")
    public String actTaskHistoric(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        return "modules/act/actTaskHistoric";
    }


    /**
     * 获取已办任务中代理人的已办任务
     *
     * @param
     * @param act 流程定义标识
     * @return
     */
    @RequestMapping(value = "historicList3")
    public String historicList3(Act act,String principalid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        List<OaPersonDefineTable>  list = actTaskService.historicList3(act , principalid);
        Page<OaPersonDefineTable> page = new Page<>();
        page.setList(list);
        model.addAttribute("page",page);
        model.addAttribute("principalid",principalid);
        return "modules/act/actTaskHistoricList";
    }
    /**
     * 获取已办任务
     *
     * @param
     * @param流程定义标识
     * @return
     */
    @RequestMapping(value = "myHistoricList")
    public String myHistoricList(String procDefId,String message, HttpServletRequest request, HttpServletResponse response,String principalid) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String html = "";
        FlowData flowParam = new FlowData();
        //自定义流程HTML
        StringBuilder selfFlowHTML = new StringBuilder();
        String formKey = actTaskService.getFormKey(procDefId, null);//由流程id获取流程节点KEY
        if (StringUtils.isNotBlank(formKey) && !"/404".equals(formKey)) {
            OaFormMaster form = oaFormMasterService.findByNo(formKey, null);//用表单主键获取表单样式
            if (form != null) {
                String tableName = form.getTableName();
                flowParam.setTableName(tableName);
                flowParam.setFormNo(form.getFormNo());//组装流程数据类
                OaPersonDefineTable table = oaPersonDefineTableService.findByTableName(tableName, null);//由form的table名来获取对应的table
                OaPersonDefineTableColumn param = new OaPersonDefineTableColumn();
                param.setIsShow("1");
                param.setTable(table);
                List<OaPersonDefineTableColumn> columns = oaPersonDefineTableService.findColumnList(param);//获取数据内容
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
                User user=null;
                if(principalid == null || principalid.equals("")){
                    user = UserUtils.getUser();
                }else{
                    user = userDao.get(principalid);
                }
                String role1 = roleDao.getByRoleName(user.getId()).get(0).getName();
                Integer int1 = role1.indexOf("管理员");
                if (user.getName().equals("系统管理员") || int1 == -1) {
                    if (!user.isAdmin()) {
                        paramMap.put("createBy", user.getId());
                    }
                    Page<Map<String, Object>> page = actTaskService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                    for (int i = 0; i < page.getList().size(); i++) {
                        page.getList().get(i).put("procDefId",procDefId);
                        page.getList().get(i).put("listAmount",page.getCount());
                    }
                    List<Map<String, Object>> flowInfo = page.getList();
                    flowParam.setDatas(new HashMap<>());
                    flowParam.getDatas().put("flowInfo", flowInfo);
                    flowParam.getDatas().put("page", page);
                    flowParam.getDatas().put("procDefId",procDefId );
                    flowParam.getDatas().put("title", form.getTitle());
                } else {
                    String companyId2 = user.getCompany().getId();
                    Page<Map<String, Object>> page = null;
                    List<Map<String, Object>> flowInfo = new ArrayList<>();
                    flowParam.setDatas(new HashMap<>());
                    flowParam.getDatas().put("flowInfo", flowInfo);
                    flowParam.getDatas().put("page", page);
                    flowParam.getDatas().put("procDefId",procDefId );

                }
                Component c;
                if(UserAgentUtils.isMobile(request)){
                    c = ComponentUtils.getComponent("myDoneMobile");
                    html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                            .replace("$tbodyHTML$", tbodyHTML.toString())
                            .replace("$theadHTML$", theadHTML.toString());
                }
                else{
                    c = ComponentUtils.getComponent("myDone");
                    html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                            .replace("$tbodyHTML$", tbodyHTML.toString())
                            .replace("$theadHTML$", theadHTML.toString());
                }

            }
            if(StringUtils.isNotBlank(message)){
                flowParam.getDatas().put("message", message);
            }
        }
        try {
            response.getWriter().print(FreemarkerUtils.process(html, toMap(flowParam)));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * flowData中数据放入到Map
     *
     * @param flowData
     * @return
     */
    private Map<String, Object> toMap(FlowData flowData) {
        Map<String, Object> map = flowData.getDatas();
        if (map == null) {
            map = new HashMap<>();
        } else if (!"flowForm".equals(flowData.getShowType())) {
            OaPersonDefineTable oaPersonDefineTable = this.oaPersonDefineTableService.findByTableName(flowData.getTableName(), null);
            List<OaPersonDefineTableColumn> columns = this.oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
            if (columns.size() > 0) {
                if (map.get("flowInfo") != null) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("flowInfo");
                    for (Map<String, Object> m : list) {
                        format(m, columns);
                    }
                } else {
                    format(map, columns);
                }
            }
        }
        map.put("tableName", flowData.getTableName());
        map.put("id", flowData.getId());
        map.put("formNo", flowData.getFormNo());
        map.put("ctx", Global.getAdminPath());
        Act act = flowData.getAct();
        if (act != null) {
            if (act.getTaskName() != null) {
                act.setTaskName(act.getTaskName());
            }
            map.put("act", act);
        }
        return map;
    }


    private void format(Map<String, Object> map, List<OaPersonDefineTableColumn> columns) {
        if (columns != null && map != null && columns.size() > 0 && map.keySet().size() > 0) {
            for (OaPersonDefineTableColumn column : columns) {
                if (ComponentUtils.chargeMoreData(column.getControlTypeId()) && map.get(column.getColumnName()) != null) {
                    map.put(column.getColumnName(), DictUtils.getDictLabels((String) map.get(column.getColumnName()), column.getRemarks(), ""));
                }
            }
        }
    }

    /**
     * 手机上拉加载已办任务
     * @param act
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getActTaskMore")
    @ResponseBody
    public List<Map> getActTaskMore(Act act,@RequestParam(value="pageNo", required=true)int pageNo,@RequestParam(value="pageSize", required=true)int pageSize) {
        Page<Act> page=new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        List<Map> list=new ArrayList<>();
        list = actTaskService.findActTaskPage(page,act,list);
        return list;
    }
    /**
     * 获取流转历史列表
     *
     * 流程实例
     * @param startAct  开始活动节点名称
     * @param endAct    结束活动节点名称
     */
    @RequestMapping(value = "histoicFlow")
    public String histoicFlow(Act act, String startAct, String endAct, Model model) {
        if (StringUtils.isNotBlank(act.getProcInsId())) {
            List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), startAct, endAct);
            model.addAttribute("histoicFlowList", histoicFlowList);
        }
        return "modules/act/actTaskHistoricFlow";
    }

    /**
     * 获取流程列表
     *
     * @param category 流程分类
     */
    @RequestMapping(value = "process")
    public String processList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Object[]> page = new Page<Object[]>(request, response);
        page = actTaskService.processList(page, category);
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        return "modules/act/actTaskProcessList";
    }

    /**
     * 获取流程表单
     *
     */
    @RequestMapping(value = "form")
    public String form(Act act, HttpServletRequest request, Model model) {

//        将存在CSLoginNames中的ID转换为代理人，并以session的形式发送出去
        request.getSession().setAttribute("agent",UserUtils.get(act.getCSLoginNames()));
        // 获取流程XML上的表单KEY
        String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());
        //查询表名和表id的值
        OaFormMaster oaFormMaster = oaFormMasterService.findByNo(formKey, "");

        if (act.getBusinessId() == null) {
            List<Map<String, Object>> list = oaPersonDefineTableService.findByTable(oaFormMaster.getTableName(), act.getProcInsId());
            if (list != null && list.size() > 0) {
                Map<String, Object> map = list.get(0);
                if (map != null) {
                    Iterator iterator = map.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        if (key.equals("ID") || key.equals("id")) {
                            Object object = map.get(key);
                            act.setBusinessId(object.toString());
                            act.setBusinessTable(oaFormMaster.getTableName());
                        }
                    }
                }
            }
        }
        if (StringUtils.equalsIgnoreCase(formKey, "BIAOdi")) {
            formKey = "/oa/flow/form?formNo=" + formKey + "&flag=1";
        } else {
            formKey = "/oa/flow/form?formNo=" + formKey;
        }

        // 获取流程实例对象
        if (act.getProcInsId() != null) {
            act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
        }


        return "redirect:" + ActUtils.getFormUrl(formKey, act);
    }

    /**
     * 启动流程
     *
     * @param
     * @param
     */
    @RequestMapping(value = "start")
    @ResponseBody
    public String start(Act act, String table, String id, Model model) throws Exception {
        actTaskService.startProcess(act.getProcDefKey(), act.getBusinessId(), act.getBusinessTable(), act.getTitle());
        return "true";//adminPath + "/act/task";
    }

    /**
     * 签收任务
     *
     * @param
     */
    @RequestMapping(value = "claim")
    @ResponseBody
    public String claim(Act act) {
        String userId = UserUtils.getUser().getLoginName();
        actTaskService.claim(act.getTaskId(), userId);
        return "true";
    }

    /**
     * 完成任务
     *
     *  任务ID
     *  流程实例ID，如果为空，则不保存任务提交意见
     *    任务提交意见的内容
     *      任务流程变量，如下
     *                  vars.keys=flag,pass
     *                  vars.values=1,true
     *                  vars.types=S,B  @see com.thinkgem.jeesite.modules.act.utils.PropertyType
     */
    @RequestMapping(value = "complete")
    @ResponseBody
    public String complete(Act act) {
        actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), act.getVars().getVariableMap());
        return "true";//adminPath + "/act/task";
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "trace/photo/{procDefId}/{execId}")
    public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
        InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 输出跟踪流程信息
     *
     * @param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "trace/info/{proInsId}")
    public List<Map<String, Object>> traceInfo(@PathVariable("proInsId") String proInsId) throws Exception {
        List<Map<String, Object>> activityInfos = actTaskService.traceProcess(proInsId);
        return activityInfos;
    }

    /**
     * 显示流程图

     @RequestMapping(value = "processPic")
     public void processPic(String procDefId, HttpServletResponse response) throws Exception {
     ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
     String diagramResourceName = procDef.getDiagramResourceName();
     InputStream imageStream = repositoryService.getResourceAsStream(procDef.getDeploymentId(), diagramResourceName);
     byte[] b = new byte[1024];
     int len = -1;
     while ((len = imageStream.read(b, 0, 1024)) != -1) {
     response.getOutputStream().write(b, 0, len);
     }
     }*/

    /**
     * 获取跟踪信息

     @RequestMapping(value = "processMap")
     public String processMap(String procDefId, String proInstId, Model model)
     throws Exception {
     List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
     ProcessDefinition processDefinition = repositoryService
     .createProcessDefinitionQuery().processDefinitionId(procDefId)
     .singleResult();
     ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
     String processDefinitionId = pdImpl.getId();// 流程标识
     ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
     .getDeployedProcessDefinition(processDefinitionId);
     List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
     List<String> activeActivityIds = runtimeService.getActiveActivityIds(proInstId);
     for (String activeId : activeActivityIds) {
     for (ActivityImpl activityImpl : activitiList) {
     String id = activityImpl.getId();
     if (activityImpl.isScope()) {
     if (activityImpl.getActivities().size() > 1) {
     List<ActivityImpl> subAcList = activityImpl
     .getActivities();
     for (ActivityImpl subActImpl : subAcList) {
     String subid = subActImpl.getId();
     System.out.println("subImpl:" + subid);
     if (activeId.equals(subid)) {// 获得执行到那个节点
     actImpls.add(subActImpl);
     break;
     }
     }
     }
     }
     if (activeId.equals(id)) {// 获得执行到那个节点
     actImpls.add(activityImpl);
     System.out.println(id);
     }
     }
     }
     model.addAttribute("procDefId", procDefId);
     model.addAttribute("proInstId", proInstId);
     model.addAttribute("actImpls", actImpls);
     return "modules/act/actTaskMap";
     }*/

    /**
     * 删除任务
     *
     * @param taskId 流程实例ID
     * @param reason 删除原因
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "deleteTask")
    public String deleteTask(String taskId, String reason, RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(reason)) {
            addMessage(redirectAttributes, "请填写删除原因");
        } else {
            actTaskService.deleteTask(taskId, reason);
            addMessage(redirectAttributes, "删除任务成功，任务ID=" + taskId);
        }
        return "redirect:" + adminPath + "/act/task";
    }


    /**
     * 首页中已办任务显示
     *
     */
    @ResponseBody
    @RequestMapping(value = "actList")
    public List<HomAct> actList() {
        List<Act> acts = actTaskService.doneList();
        List<HomAct> homActs = new ArrayList<>();
        if(acts != null && acts.size()>0){
            for(int i=0;i<acts.size();i++){
                HomAct homAct = new HomAct();
                homAct.setTaskId(acts.get(i).getTaskId());
                homAct.setTaskName(acts.get(i).getTaskName());
                homAct.setTaskDefKey(acts.get(i).getTaskDefKey());
                homAct.setProcDefId(acts.get(i).getProcDefId());
                homAct.setTitle(acts.get(i).getTitle());
                homAct.setComment(acts.get(i).getComment());
                homAct.setCS(acts.get(i).getCS());
                homActs.add(homAct);
            }
            return homActs;
        }
        return null;
    }


    /**
     * 首页中已办任务显示
     *
     */
    @ResponseBody
    @RequestMapping(value = "actTaskList")
    public List<Act> actTaskList() {
        String userId="";
        List<Act> acts = actTaskService.todoList(new Act(),userId);
        if(acts == null){
            acts = Lists.newArrayList();
        }
        return acts;
    }




}
