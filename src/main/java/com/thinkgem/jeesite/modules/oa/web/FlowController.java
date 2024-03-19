package com.thinkgem.jeesite.modules.oa.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefUtils;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.DsvsInfo;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.service.FlowService;
import com.thinkgem.jeesite.modules.oa.units.FlowUtils;
import com.thinkgem.jeesite.modules.oa.units.FreemarkerUtils;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.TokenProccessor;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import com.thinkgem.jeesite.modules.test1.service.OaRunProcessService;
import eu.bitwalker.useragentutils.UserAgent;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 流程Controller
 *
 * @author oa
 * @version 2016-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/flow")
public class FlowController extends BaseController {


    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private OaFormMasterService oaFormMasterService;
    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;
    @Autowired
    OaRunProcessService oaRunProcessService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "form")
    public void form(FlowData flow, Act act,String ids, String formNofather,String procDefIdFather ,String procDefId,String title, HttpServletResponse response, HttpServletRequest request,String flag) throws IOException {
        OaFormMaster form;
        if(!com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(flow.getAct().getProcDefId())){
            flow.getAct().setProcDefId(procDefId);

        }
        if(com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(formNofather)){
            form = oaFormMasterService.findByNo(formNofather, null);
        } else {
            form = oaFormMasterService.findByNo(flow.getFormNo(), null);
        }
        if(form == null){
            form = oaFormMasterService.findByNo1(flow.getFormNo(), null);
        }
        flow.getAct().setProcDefIdFather(procDefIdFather);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tableName", form.getTableName());
        paramMap.put("procInsId", flow.getAct().getProcInsId());
        paramMap.put("id", flow.getId());
        if (StringUtils.isNotEmpty(flag)) {
            paramMap.put("BZ", flag);
        }
        flow.setTableName(form.getTableName());

        String view;
        if (StringUtils.isNotBlank(flow.getShowType())) {
            view = flow.getShowType();
            flow.setDatas(flowService.getOneInfo(paramMap));
        } else {
            view = "flowForm";
            // 查看审批申请单
            if (StringUtils.isNotBlank(flow.getId())) {
                // 环节编号
                String taskDefKey = flow.getAct().getTaskDefKey();
                // 查看
                if (flow.getAct().isFinishTask()) {
                    view = "flowView";
                }
                // 修改环节
                else if ("modify".equals(taskDefKey) || (taskDefKey).startsWith("edit")) {
                    view = "flowForm";
                }
                // 审核
                else if (taskDefKey.startsWith("audit") || "apply_end".equals(taskDefKey) || (taskDefKey).startsWith("apply_execute") || (taskDefKey).startsWith("apply_cs")) {
                    view = "flowAudit";
                }
                flow.setDatas(flowService.getOneInfo(paramMap));
            }
        }

        if("1".equals(Global.getConfig("ca.enable")) && "flowAudit".equals(view)) {
            String jsonStr = JSONUtil.toJsonStr(flow.getDatas());
            flow.setDsvsInfo(new DsvsInfo(jsonStr));
        }
        StringBuilder tbodyHTML = new StringBuilder();
        if(StringUtils.isNotBlank(ids)){
            act.setTaskName(title);
            String  id = ids.replace("||", "&");
            List<String>  list1 = Arrays.asList(id.split(","));
            flow.setDatas(new HashMap<String, Object>());
            ArrayList<Map<String,Object>> list =new ArrayList<>();
            for (String s : list1) {
                Map<String,Object> map= new HashMap();
                map.put("url",s);
                map.put("title",title);
                list.add(map);
            }

            tbodyHTML.append("<td><a href="+"${item.url}>${item.title}</a></td>");
            flow.getDatas().put("flowInfo", list);
            flow.getDatas().put("formNo", form.getFormNo());
            if(list1.size()>0){
                StringBuilder s1=new StringBuilder();
                StringBuilder s2=new StringBuilder();

                for(int i=0;i<list1.size();i++)
                {
                    s1.append(list1.get(i));
                    s1.append(",");
                    s2.append(title);
                    s2.append(",");
                };
                flow.getAct().setLines(s1.toString());
                flow.getAct().setLinesName(s2.toString());
            };
        }
        Component c = ComponentUtils.getComponent(view);
        initComponent(form, view, StringUtils.isBlank(flow.getId()));
        initComponent(form, view, StringUtils.isBlank(flow.getId()));
        String initJs = "";
        if (StringUtils.isNotBlank(flow.getId()) && "flowForm".equals(view)) {
            initJs = getInitJs(flow.getDatas(), form.getTableName());
        }
        String html=null;
        //实例id
        String procInsId = flow.getAct().getProcInsId();
        //用于判断是否是归档的流程，如果是归档的流程则只需要查看图片，不需要显示整个的流程。
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
        if(pi != null) {
            Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).active().singleResult();
            String executionId = task.getExecutionId();
            flow.getDatas().put("executionId", executionId);
        }
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInsId).list();
        File f = new File(request.getSession().getServletContext().getRealPath("/static" + File.separator + "userfiles") + "\\keep\\"+procInsId+".png");
        if (pi == null && "flowView".equals(view) && f.exists() == true) {
            html = c.getContent().replace("$flowTableInfo$","<table><tr><td style=\"padding-left:0px\"><img style=\"width:1800px;\" src=\"/static/userfiles/keep/"+procInsId+".png\"></td></tr>" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td height=\"33\" id=\"fjmc1\" style=\"display:none;\" width=\"4%\">" +
                    "&nbsp;</td>" +
                    "<td height=\"33\" width=\"85%\">" +
                    "<p align=\"left\">" +
                    "<b>备注：${BZSJ}</b></p>" +
                    "</td>" +
                    "<td height=\"33\" id=\"fjlj1\" style=\"display:none;\" width=\"11%\">" +
                    "&nbsp;</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</table>"+
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td height=\"33\" id=\"fjmc1\" style=\"display:none;\" width=\"4%\">" +
                    "${FJMC}</td>" +
                    "<td height=\"33\" width=\"85%\">" +
                    "<p align=\"left\" id=\"hhh\">" +
                    "<b>附件：</b></p>" +
                    "</td>" +
                    "<td height=\"33\" id=\"fjlj1\" style=\"display:none;\" width=\"11%\">" +
                    "${FJLJ}</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</table><script>\n" +
                    "$(document).ready(function() {\n" +
                    "var  fjlj1= document.getElementById(\"fjlj1\").innerHTML;\n" +
                    "var  fjmc1= document.getElementById(\"fjmc1\").innerHTML;\n" +
                    "if(fjlj1 != \"\" && fjmc1 != \"\"){\n" +
                    "   var fjlj = fjlj1.substring(0,fjlj1.length)\n" +
                    "   var fjmc = fjmc1.substring(0,fjmc1.length)\n" +
                    "   var mc=fjmc.split(\",\")\n" +
                    "   var lj=fjlj.split(\",\")\n" +
                    "\t\t        \n" +
                    "\t$(\"#hhh\").html(\"\");\n" +
                    "\tfor(var i=0;i<mc.length-1;i++){\n" +
                    "\t      $(\"#hhh\").append(\"附件\"+[i+1]+\"：<a href='\" + lj[i] + \"'>\"+mc[i]+\"</a><br>\");                     \n" +
                    "\t}\n" +
                    "}});</script>");
        }else{
            html = c.getContent().replace("$flowTableInfo$", form.getContent()).replace("$initJs$", initJs);
            html = html.replace("<td", "<td ");

            String token = TokenProccessor.getInstance().makeToken();//创建令牌
            flow.getAct().setToken(token);
            request.getSession().setAttribute("token", token);
        }
        flow.setShowType(view);

        if (StringUtils.isNoneBlank(flag)) {
            flow.getAct().setBZ(flag);
        }
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(FreemarkerUtils.process(html, FlowUtils.toMap(flow)));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "saveContractFile")
    public void saveContractFile(@RequestParam("file") MultipartFile file, String name, String signName, String id,
                                 RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String url = flowService.saveContractFile(file, name, signName, id, request);
        String res = "{error:'" + url + "'}";
        System.out.println(res);
        response.getWriter().write(res);
    }
    @RequestMapping(value = "save")
    public String save(String startAct, String endAct, String procDefIdFather, FlowData flowData, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) throws Exception {
        String message = flowService.save(startAct, endAct, procDefIdFather, flowData, request, redirectAttributes, response);
        if("-1".equals(message)) {
            return "redirect:" + adminPath + "/act/task/todomain1";
        }
        if("success".equals(message)) {
            return null;
        }
        if(UserAgentUtils.isMobile(request)){
            return "redirect:" + adminPath + "/oa/flow/flowList";
        }else{
            myFlow(flowData.getAct().getProcDefId(),"", procDefIdFather, "", message,"",request,response);
            return null;
        }
    }


    /**
     * 审批流程
     * @param startAct
     * @param endAct
     * @param flowData   页面中全部的数据
     * @param model
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveAudit")
    public String saveAudit(String startAct, String endAct, FlowData flowData, Model model, HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes)throws Exception {
        flowService.saveAudit(startAct, endAct, flowData, request, redirectAttributes);
        return "redirect:" + adminPath + "/act/task/todo/";
    }


    @RequestMapping(value = "fastSaveAudit")
    @ResponseBody
    public String fastSaveAudit(@RequestBody List<Map> acts, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        for (Map<String, String> param : acts) {
            Act act = new Act();
            act.setTaskId(param.get("taskId"));
            act.setTaskDefKey(param.get("taskDefKey"));
            act.setProcInsId(param.get("procInsId"));
            act.setProcDefId(param.get("procDefId"));
            act.setTaskName("归档");
            act.setStatus("todo");
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
            FlowData flowData = getFlowData(formKey, act);
            act.setFlag("yes");
            flowData.setAct(act);
            flowService.saveAudit(null, null, flowData, request, redirectAttributes);
        }
        return "1";
    }

    /**
     * 我发起的流程显示的页面
     * @param procDefId
     * @param
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "flowList")
    public String flowList(String procDefId, String type,  String formNo,HttpServletRequest request, HttpServletResponse response,Model model) {
        List<OaPersonDefineTable> list = flowService.flowList();
        Page<OaPersonDefineTable> page = new Page<>();
        page.setList(list);
        model.addAttribute("page",page);
        if(StringUtils.isNotBlank(type)&& "1".equals(type)){
            model.addAttribute("formNo",formNo);
            model.addAttribute("procDefIdFather",procDefId);
            return "modules/oa/lineFlowList";
        }else{
            return "modules/oa/flowList";
        }

    }




    @RequestMapping(value = "myFlow")
    public String myFlow(String procDefId,  String formNo, String procDefIdFather,  String category,String message,String type,HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String html = "";
        String cate = null;
        FlowData flowParam = new FlowData();
        List<Role> roles = roleDao.getByRoleName(UserUtils.getUser().getId());//获取当前人员的职位信息
        if (StringUtils.isNotEmpty(category)) {
            cate = category;
        }
        List<Object[]> processList = ProcessDefUtils.processList(cate);//保存两个对象，流程定义和流程部署
        //默认选择第一个流程
        if (StringUtils.isBlank(procDefId) && processList.size() > 0) {
            ProcessDefinition process = (ProcessDefinition) processList.get(0)[0];
            procDefId = process.getId();//流程定义id
        }
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
                User user = UserUtils.getUser();
                String role1 = UserUtils.getRole().get(0).getName();
                int int1 = role1.indexOf("管理员");
                if ("系统管理员".equals(UserUtils.getUser().getName()) || int1 == -1) {
                    if (!user.isAdmin()) {
                        paramMap.put("createBy", user.getId());
                    }
                    Page<Map<String, Object>> page = flowService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                    for (int i = 0; i < page.getList().size(); i++) {
                        page.getList().get(i).put("procDefId",procDefId);
                        page.getList().get(i).put("listAmount",page.getCount());}
                    List<Map<String, Object>> flowInfo = page.getList();

                    flowParam.setDatas(new HashMap<String, Object>());
                    flowParam.getDatas().put("flowInfo", flowInfo);
                    flowParam.getDatas().put("page", page);
                    flowParam.getDatas().put("procDefId", procDefId);
                    flowParam.getDatas().put("title", form.getTitle());
                    flowParam.getDatas().put("procDefIdFather", procDefIdFather);
                } else {
                    String companyId2 = user.getCompany().getId();
                    Page<Map<String, Object>> page = null;
                    List<Map<String, Object>> flowInfo = new ArrayList<>();
                    if (!UserUtils.getUser().isAdmin() && !role1.endsWith("管理员")) {
                        paramMap.put("createBy", UserUtils.getUser().getId());
                        page = flowService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                        for (int i = 0; i < page.getList().size(); i++) {
                            page.getList().get(i).put("procDefId",procDefId);
                            page.getList().get(i).put("listAmount",page.getCount());
                        }
                        List<Map<String, Object>> flowInfo1 = page.getList();
                        flowInfo.addAll(flowInfo1);
                    } else {
                        page = flowService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                        if (page.getList() != null) {
                            for (int i = 0; i < page.getList().size(); i++) {
                                //拿到每条数据的createBy创建人
                                String person = page.getList().get(i).get("create_by").toString();
                                User user11 = userDao.findByCompanyId(companyId2, person);
                                page.getList().get(i).put("procDefId",procDefId);
                                page.getList().get(i).put("listAmount",page.getCount());
                                if (user11 != null) {
                                    flowInfo.add(page.getList().get(i));
                                }
                            }
                        }
                    }
                    flowParam.setDatas(new HashMap<String, Object>());
                    flowParam.getDatas().put("flowInfo", flowInfo);
                    flowParam.getDatas().put("page", page);
                    flowParam.getDatas().put("procDefId", procDefId);
                    flowParam.getDatas().put("title", form.getTitle());
                }
                Component c;
                if(UserAgentUtils.isMobile(request)){
                    if(StringUtils.isNotBlank(type)&&"1".equals(type)){
                        flowParam.getDatas().put("formNofather",formNo);
                        c = ComponentUtils.getComponent("mylineFlow");

                    }else{c = ComponentUtils.getComponent("myTouchFlow");}

                    html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                            .replace("$tbodyHTML$", tbodyHTML.toString());
                }
                else{
                    if(com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(type)&& "1".equals(type)){
                        flowParam.getDatas().put("formNofather",formNo);
                        c = ComponentUtils.getComponent("mylineFlow");

                    }else{c = ComponentUtils.getComponent("myFlow");}

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
            response.getWriter().print(FreemarkerUtils.process(html, FlowUtils.toMap(flowParam)));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我发起的流程上拉加载
     *
     * @param flowData
     * @param procDefId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "touchFlow")
    @ResponseBody
    public List<Map> touchFlow(FlowData flowData,@RequestParam(value="procDefId", required=false)String procDefId,@RequestParam(value="pageNo", required=false)int pageNo,@RequestParam(value="pageSize", required=false)int pageSize) {
        Page<FlowData> page=new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        String cate = null;
        String category="";
        String tableName="";
        String formNo="";
        List<Role> roles = roleDao.getByRoleName(UserUtils.getUser().getId());//获取当前人员的职位信息
        String companyId = UserUtils.getUser().getCompany().getId();//获取当前人员的单位
        User user1 = userDao.findUserByRoleName(new User());//获取集团管理员的机构id
//        for (Role role : roles) {
//            if (StringUtils.equalsIgnoreCase(companyId, "1")) {
//                category = "";
//            } else if (StringUtils.equalsIgnoreCase(role.getName(), "集团管理员") || StringUtils.equalsIgnoreCase(UserUtils.getUser().getCompany().getId(), user1.getCompanyId())
//                    || StringUtils.equalsIgnoreCase(role.getName(), "深圳管理员") || StringUtils.equalsIgnoreCase(UserUtils.getUser().getCompany().getId(), user1.getCompanyId())) {
//                category = "1";
//            } else {
//                category = "2";
//            }//根据人员职位获取栏目类型 target="view_window"
//        }
        if (StringUtils.isNotEmpty(category)) {
            cate = category;
        }
        List<Object[]> processList = ProcessDefUtils.processList(cate);//保存两个对象，流程定义和流程部署
        //默认选择第一个流程
        if (StringUtils.isBlank(procDefId) && processList.size() > 0) {
            ProcessDefinition process = (ProcessDefinition) processList.get(0)[0];
            procDefId = process.getId();//流程定义id
        }
        String formKey = actTaskService.getFormKey(procDefId, null);//由流程id获取流程节点KEY
        Page<Map<String, Object>> pageList=new Page<>();
        if (StringUtils.isNotBlank(formKey) && !"/404".equals(formKey)) {
            OaFormMaster form = oaFormMasterService.findByNo(formKey, null);//用表单主键获取表单样式
            Map<String, String> paramMap = new HashMap<>();
            if (form != null) {
                paramMap.put("tableName", form.getTableName());
                tableName=form.getTableName();
                formNo=form.getFormNo();
            }
            paramMap.put("procDefId", procDefId);
            User user = UserUtils.getUser();
            String role1 = UserUtils.getRole().get(0).getName();
            int int1 = role1.indexOf("管理员");
            if ("系统管理员".equals(UserUtils.getUser().getName()) || int1 == -1) {
                if (!user.isAdmin()) {
                    paramMap.put("createBy", user.getId());
                }
                pageList = flowService.getPageFlowInfo(page, paramMap);
            } else {
                if (!UserUtils.getUser().isAdmin() && !role1.endsWith("管理员")) {
                    paramMap.put("createBy", UserUtils.getUser().getId());
                    pageList = flowService.getPageFlowInfo(page, paramMap);
                } else {
                    pageList = flowService.getPageFlowInfo(page, paramMap);
                }
            }
        }
        List<Map> list=new ArrayList<>();
        Map<String,String> dataMap=new HashMap<String,String>();
        List<Map<String, Object>> data = pageList.getList();
        if(data.size()>0){
            for(int i=0;i<data.size();i++){
                java.util.Iterator it = data.get(i).entrySet().iterator();
                while(it.hasNext()){
                    java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
                    if(StringUtils.equals(entry.getKey().toString(),"KSFZRHSC")||StringUtils.equals(entry.getKey().toString(),"SQR")||StringUtils.equals(entry.getKey().toString(),"SBR")){
                        dataMap.put("dept",entry.getValue().toString());
                    }
                    if(StringUtils.equals(entry.getKey().toString(),"WPMC1")||StringUtils.equals(entry.getKey().toString(),"XMMC")){
                        dataMap.put("name",entry.getValue().toString());
                    }
                    if(StringUtils.equals(entry.getKey().toString(),"formNo")){
                        dataMap.put("formNo","");
                    }
                    else{
                        dataMap.put(entry.getKey().toString(),entry.getValue().toString());
                    }
                    dataMap.put("flag", "flow");
                    dataMap.put("tableName",tableName);
                    dataMap.put("formNo",formNo);
                }
                if(dataMap.containsKey("name")==false){
                    dataMap.put("name","");
                }
                list.add(dataMap);
            }
        }
        return list;
    }
    /**
     * 删除流程信息
     *
     * @param tableName
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "deleteInfo")
    public void deleteInfo(String procInsId, String tableName, String formNo,  String procDefIdFather, String id,String procDefId, String type,HttpServletRequest request ,HttpServletResponse response,RedirectAttributes redirectAttributes) {
        //删除流程实例，确保删除不彻底
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInsId).list();
        String message=null;
        if (pi == null) {
            message = "流程已经结束不允许再撤回";
        }else if(pi != null && tasks.size()>1 ){
            for(int i=0;i<tasks.size();i++){
                if("apply_end".equals(tasks.get(i).getTaskDefinitionKey())){
                    message =  "流程已经处于归档和抄送状态不允许再撤回";
                    break;
                }else{
                    runtimeService.deleteProcessInstance(procInsId,"");
                    oaPersonDefineTableService.reCallInfo(tableName, id);
                    message =  "撤回未结束流程成功";
                }
            }
        } else if(pi!=null && tasks.size()== 1 && "apply_end".equals(tasks.get(0).getTaskDefinitionKey())){
            message =  "流程已经处于归档和抄送状态不允许再撤回";
        }
        else {
            runtimeService.deleteProcessInstance(procInsId,"");
            oaPersonDefineTableService.reCallInfo(tableName, id);
            message =  "撤回未结束流程成功";
        }

        myFlow(procDefId,"", procDefIdFather, formNo, message,type,request,response);
    }

    /**
     * 表单内存处理掉[...]
     *
     * @param oaFormMaster
     * @param view
     */
    private void initComponent(OaFormMaster oaFormMaster, String view, boolean init) {
        OaPersonDefineTable oaPersonDefineTable = this.oaPersonDefineTableService.findByTableName(oaFormMaster.getTableName(), null);
        List<OaPersonDefineTableColumn> oaPersonDefineTableColumns = this.oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
        String tableContent = oaFormMaster.getContent();
        for (OaPersonDefineTableColumn column : oaPersonDefineTableColumns) {
            if (column != null && !"".equals(column)) {
                String content = "";
                if ("flowForm".equals(view) && !"REMARK".equalsIgnoreCase(column.getColumnType())) {
                    Map<String, Object> columnMap = new HashMap<>();
                    columnMap.put("value", column.getControlTypeId());
                    columnMap.put("columnName", column.getColumnName());
                    columnMap.put("params", column.getRemarks());
                    if (ComponentUtils.chargeMoreData(column.getControlTypeId())) {
                        columnMap.put("optData", DictUtils.getDictList(column.getRemarks()));
                    }
                    content = ComponentUtils.initComponent(columnMap, init);
                    if ("text".equals(column.getControlTypeId()) || "number".equals(column.getControlTypeId()) || "textValue".equals(column.getControlTypeId())) {
                        content = content.replace("<input ", "<input style='width:98%;padding-left:0;padding-right:0;margin:0;border:0;'");
                    } else if ("textarea".equals(column.getControlTypeId())) {
                        content = content.replace("<textarea ", "<textarea style='width:99%;height:99%;padding:0;margin:0;border:0;'");
                    }
                } else if ("REMARK".equalsIgnoreCase(column.getColumnType())) {
                    content = "${" + column.getColumnName() + "}";

                } else {
                    content = "${" + column.getColumnName() + "}";
                }
                tableContent = tableContent.replace("[" + column.getColumnComment() + "]", content);
            }
        }
        oaFormMaster.setContent(tableContent);
    }

    /**
     * flowData中数据放入到Map
     *
     * @param flowData
     * @return
     */


    private String getInitJs(Map<String, Object> map, String tableName) {
        OaPersonDefineTable oaPersonDefineTable = this.oaPersonDefineTableService.findByTableName(tableName, null);
        List<OaPersonDefineTableColumn> columns = this.oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
        StringBuilder sb = new StringBuilder();
        if (columns != null && map != null && columns.size() > 0 && map.keySet().size() > 0) {
            for (OaPersonDefineTableColumn column : columns) {
                if (ComponentUtils.chargeMoreData(column.getControlTypeId()) && map.get(column.getColumnName()) != null) {
                    Component c = ComponentUtils.getComponent(column.getControlTypeId() + "Init");
                    sb.append(c.getContent().replace("${colName}", column.getColumnName()).replace("${colValue}", "${" + column.getColumnName() + "}"));
                }
            }
        }
        return sb.toString();
    }




    /**
     * 流程的统计查询
     *
     * @param procDefId 流程定义id
     * @param category  流程分类
     * @param name      名称
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param dept      部门
     * @param numberDay 天数
     * @param fh
     * @param arriveDay
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "query")
    public String query(String procDefId, String category, String name, String startDate, String endDate, String dept, String numberDay, String fh, String arriveDay, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String html = "";
        String cate = null;
        FlowData flowParam = new FlowData();
        if (StringUtils.isNotEmpty(category)) {
            cate = category;
        }
        List<Object[]> processList = ProcessDefUtils.processList(cate);
        //默认选择第一个流程
        if (StringUtils.isBlank(procDefId)) {
            ProcessDefinition process = (ProcessDefinition) processList.get(0)[0];
            procDefId = process.getId();
        }
        //自定义流程HTML
        StringBuilder selfFlowHTML = new StringBuilder();
        for (Object[] objs : processList) {
            ProcessDefinition process = (ProcessDefinition) objs[0];
            selfFlowHTML.append("<option " + (procDefId.equals(process.getId()) ? "selected=\"selected\"" : "")
                    + " value=\"" + process.getId() + "\">" + process.getName() + "</option>");
        }

        String formKey = actTaskService.getFormKey(procDefId, null);
        if (StringUtils.isNotBlank(formKey) && !"/404".equals(formKey)) {
            OaFormMaster form = oaFormMasterService.findByNo(formKey, null);
            if (form != null) {
                String tableName = form.getTableName();
                flowParam.setTableName(tableName);
                flowParam.setFormNo(form.getFormNo());
                OaPersonDefineTable table = oaPersonDefineTableService.findByTableName(tableName, null);
                OaPersonDefineTableColumn param = new OaPersonDefineTableColumn();
                param.setIsShow("1");
                param.setTable(table);
                List<OaPersonDefineTableColumn> columns = oaPersonDefineTableService.findColumnList(param);
                StringBuilder theadHTML = new StringBuilder();
                StringBuilder tbodyHTML = new StringBuilder();
                for (OaPersonDefineTableColumn column : columns) {
                    theadHTML.append("<th>" + column.getColumnComment() + "</th>");
                    tbodyHTML.append("<td>${item." + column.getColumnName() + "}</td>");
                }

                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("tableName", form.getTableName());
                paramMap.put("procDefId", procDefId);
                paramMap.put("name", name);
                paramMap.put("dept", dept);
                paramMap.put("numberDay", numberDay);
                paramMap.put("fh", fh);
                paramMap.put("arriveDay", arriveDay);
                paramMap.put("startDate", startDate);
                paramMap.put("endDate", endDate);
                Page<Map<String, Object>> page = flowService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                List<Map<String, Object>> flowInfo = page.getList();

                flowParam.setDatas(new HashMap<String, Object>());
                flowParam.getDatas().put("flowInfo", flowInfo);
                flowParam.getDatas().put("page", page);

                Component c = ComponentUtils.getComponent("query");
                html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                        .replace("$tbodyHTML$", tbodyHTML.toString())
                        .replace("$theadHTML$", theadHTML.toString());
            }
        }
        try {
            response.getWriter().print(FreemarkerUtils.process(html, FlowUtils.toMap(flowParam)));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 人力资源管理者查看所有的流程信息
     *
     * @param procDefId
     * @param category
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "hrFlow")
    public String hrFlow(String procDefId, String category, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String html = "";
        String cate = null;
        FlowData flowParam = new FlowData();
        if (StringUtils.isNotEmpty(category)) {
            cate = "1";
        }
        List<Object[]> processList = ProcessDefUtils.processList(cate);
        //默认选择第一个流程
        if (StringUtils.isBlank(procDefId) && processList.size() > 0) {
            ProcessDefinition process = (ProcessDefinition) processList.get(0)[0];
            procDefId = process.getId();
        }
        //自定义流程HTML
        StringBuilder selfFlowHTML = new StringBuilder();
        for (Object[] objs : processList) {
            ProcessDefinition process = (ProcessDefinition) objs[0];
            selfFlowHTML.append("<option " + (procDefId.equals(process.getId()) ? "selected=\"selected\"" : "")
                    + " value=\"" + process.getId() + "\">" + process.getName() + "</option>");
        }
        String formKey = actTaskService.getFormKey(procDefId, null);
        if (StringUtils.isNotBlank(formKey) && !"/404".equals(formKey)) {
            OaFormMaster form = oaFormMasterService.findByNo(formKey, null);
            if (form != null) {
                String tableName = form.getTableName();
                flowParam.setTableName(tableName);
                flowParam.setFormNo(form.getFormNo());
                OaPersonDefineTable table = oaPersonDefineTableService.findByTableName(tableName, null);
                OaPersonDefineTableColumn param = new OaPersonDefineTableColumn();
                param.setIsShow("1");
                param.setTable(table);
                List<OaPersonDefineTableColumn> columns = oaPersonDefineTableService.findColumnList(param);
                StringBuilder theadHTML = new StringBuilder();
                StringBuilder tbodyHTML = new StringBuilder();
                for (OaPersonDefineTableColumn column : columns) {
                    theadHTML.append("<th>" + column.getColumnComment() + "</th>");
                    tbodyHTML.append("<td>${item." + column.getColumnName() + "}</td>");
                }
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("tableName", form.getTableName());
                paramMap.put("procDefId", procDefId);
                User user = UserUtils.getUser();
                String role1 = UserUtils.getRole().get(0).getName();
                int int1 = role1.indexOf("管理员");
                if ("系统管理员".equals(UserUtils.getUser().getName()) || int1 == -1) {
                    if (!user.isAdmin()) {
                        paramMap.put("createBy", user.getId());
                    }
                    Page<Map<String, Object>> page = flowService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                    List<Map<String, Object>> flowInfo = page.getList();
                    flowParam.setDatas(new HashMap<String, Object>());
                    flowParam.getDatas().put("flowInfo", flowInfo);
                    flowParam.getDatas().put("page", page);
                } else {
                    String companyId2 = user.getCompany().getId();
                    Page<Map<String, Object>> page = null;
                    List<Map<String, Object>> flowInfo = new ArrayList<>();
                    if (!UserUtils.getUser().isAdmin() && !role1.endsWith("人事主管")) {
                        paramMap.put("createBy", UserUtils.getUser().getId());
                        page = flowService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                        List<Map<String, Object>> flowInfo1 = page.getList();
                        flowInfo.addAll(flowInfo1);
                    } else {
                        page = flowService.getPageFlowInfo(new Page<FlowData>(request, response), paramMap);
                        if (page.getList() != null) {
                            for (int i = 0; i < page.getList().size(); i++) {
                                //拿到每条数据的createBy创建人
                                String person = page.getList().get(i).get("create_by").toString();
                                User user11 = userDao.findByCompanyId(companyId2, person);
                                if (user11 != null) {
                                    flowInfo.add(page.getList().get(i));
                                }
                            }
                        }
                    }
                    flowParam.setDatas(new HashMap<String, Object>());
                    flowParam.getDatas().put("flowInfo", flowInfo);
                    flowParam.getDatas().put("page", page);
                }
                Component c = ComponentUtils.getComponent("hrFlow");
                html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                        .replace("$tbodyHTML$", tbodyHTML.toString())
                        .replace("$theadHTML$", theadHTML.toString());
            }
        }
        try {
            response.getWriter().print(FreemarkerUtils.process(html, FlowUtils.toMap(flowParam)));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    /**
     * 将页面中的html格式的文件转成图片
     * @param tbodyHtml   从前台页面传递的html
     */
    private void htmlToImage(HttpServletRequest request ,String tbodyHtml,String procInsId) throws Exception{
        byte[] b = Base64.decode(tbodyHtml.substring(22));
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        BufferedImage bi = ImageIO.read(bais);
        String realPath = request.getSession().getServletContext().getRealPath("/static" + File.separator + "userfiles");
        realPath = realPath + File.separator + "keep";
        File w2 = new File(realPath+"/"+procInsId+".png");
        ImageIO.write(bi,"png", w2);
    }

    public FlowData getFlowData(String formNo, Act act) {
        FlowData flow = new FlowData();
        flow.setFormNo(formNo);
        flow.setId(act.getBusinessId());
        if(StrUtil.isBlank(flow.getAct().getProcDefId())){
            flow.getAct().setProcDefId(act.getProcDefId());
        }
        OaFormMaster form = oaFormMasterService.findByNo(flow.getFormNo(), null);
        if(form == null){
            form = oaFormMasterService.findByNo1(flow.getFormNo(), null);
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tableName", form.getTableName());
        paramMap.put("procInsId", flow.getAct().getProcInsId());
        paramMap.put("id", flow.getId());
        flow.setTableName(form.getTableName());

        flow.setDatas(flowService.getOneInfo(paramMap));

        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(act.getProcInsId()).singleResult();
        if(pi != null) {
            Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).active().singleResult();
            String executionId = task.getExecutionId();
            flow.getDatas().put("executionId", executionId);
        }
        return flow;
    }
}
