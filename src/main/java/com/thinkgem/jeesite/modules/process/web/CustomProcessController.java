/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.process.entity.CustomComment;
import com.thinkgem.jeesite.modules.process.entity.CustomProcess;
import com.thinkgem.jeesite.modules.process.entity.CustomRuskTaskinst;
import com.thinkgem.jeesite.modules.process.service.CustomCommentService;
import com.thinkgem.jeesite.modules.process.service.CustomProcessService;
import com.thinkgem.jeesite.modules.process.service.CustomRuskTaskinstService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.StringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自由流程信息Controller
 *
 * @author oa
 * @version 2017-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/customProcess")
public class CustomProcessController extends BaseController {

    @Autowired
    private CustomProcessService customProcessService;

    @Autowired
    private CustomCommentService customCommentService;

    @Autowired
    private CustomRuskTaskinstService customRuskTaskinstService;

    @ModelAttribute
    public CustomProcess get(@RequestParam(required = false) String id) {
        CustomProcess entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = customProcessService.get(id);
        }
        if (entity == null) {
            entity = new CustomProcess();
        }
        return entity;
    }

    @RequestMapping(value = "showCustom")
    public String showCustom(CustomProcess customProcess, Model model, RedirectAttributes redirectAttributes) {
        //根据当前登陆人的身份证号查询当前登陆人的所有发起过的自由流程。

       /* customProcess.setOrgId(UserUtils.getUser().getCompany().getId());
        String people = customProcessService.saveA(customProcess);
        //在自由流程提交成功后，需要指定出下一步的审批人是谁，一直到流程结束
        String message = "自由流程提交成功！";
        if(people !=null && people !=""){
            message = message + "下一步审批人："+people;
        }
        addMessage(redirectAttributes, message);*/
        model.addAttribute("customProcess",customProcess);
        return "modules/process/customProcessForm";
    }

    @RequestMapping(value = {"list", ""})
    public String list(CustomProcess customProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(customProcess.getProcInstId())) {
            //首先想要在下一步人点击处理待办流程时回显上一步提交的数据，并且变成不可以修改的文本框并且居中
            //先根据procInstId来查询customProcess表中是否有这条数据
            List<CustomProcess> customProcessList = customProcessService.findList(customProcess);
            //查询当前的任务节点名称是否是start，如果是start则需要表单内容能够重新填写。
            CustomRuskTaskinst c = new CustomRuskTaskinst();
            c.setProcInstId(customProcess.getProcInstId());
            c.setTaskDefKey("start");
            List<CustomRuskTaskinst> taskinsts = customRuskTaskinstService.findByProInstId(c);
            if (customProcessList != null && customProcessList.size() > 0) {
                if(StringUtils.equalsIgnoreCase(customProcess.getType(), "flowView")){
                    customProcessList.get(0).setType("flowView");
                }
                //文本编辑框换行问题
                customProcessList.get(0).setContent(customProcessList.get(0).getContent().replace("\r\n", "<br>"));
                model.addAttribute("customProcess", customProcessList.get(0));
            }
            if (taskinsts != null && taskinsts.size() > 0 && StringUtils.equalsIgnoreCase(taskinsts.get(0).getTaskDefKey(), "start")) {
                if (customProcessList != null && customProcessList.size() > 0) {
//                    customProcessList.get(0).setContent(customProcessList.get(0).getContent().replace("<br>", "\r\n"));
                      model.addAttribute("customProcess", customProcessList.get(0));
                }
            }

            CustomComment customComment = new CustomComment();
            customComment.setId(customProcess.getId());
            customComment.setTaskDefKey(customProcess.getTaskDefKey());
            model.addAttribute("customComment", customComment);
        } else {
            //同时在下一步人还需要能够回显流转设定的信息，并且可以显示添加的功能。
            //还需要显示流转的信息以及执行人的意见
            customProcess.preInsert();
            customProcess.setProcInstId(customProcess.getId());
            customProcess.setId(null);
            model.addAttribute("customProcess", customProcess);
        }
        return "modules/process/customProcessList";
    }

    /**
     * 我发起的流程界面
     * @param customProcess
     * @param model
     * @return
     */
    @RequestMapping(value = "form")
       public String form(CustomProcess customProcess, Model model) {
        model.addAttribute("customProcess", customProcess);
        return "modules/process/customProcessForm";
    }

    /**
     * 已办流程界面
     * @param customProcess
     * @param model
     * @return
     */
    @RequestMapping(value = "historyList")
    public String historyList(CustomProcess customProcess, Model model) {
        model.addAttribute("customProcess", customProcess);
        return "modules/process/customHiTaskinst";
    }


    @RequestMapping(value = "save")
    public String save(CustomProcess customProcess, Model model, RedirectAttributes redirectAttributes) {
        customProcess.setOrgId(UserUtils.getUser().getCompany().getId());
        String people = customProcessService.saveA(customProcess);
        //在自由流程提交成功后，需要指定出下一步的审批人是谁，一直到流程结束
        String message = "自由流程提交成功！";
        if(people !=null && people !=""){
            message = message + "下一步审批人："+people;
        }
        addMessage(redirectAttributes, message);
        return "redirect:" + adminPath + "/act/task/todomain1";
    }

    /**
     * 审批同意的保存方法
     *
     * @param customComment
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveAudit")
    @ResponseBody
    public StringData saveAudit(CustomComment customComment, Model model, RedirectAttributes redirectAttributes,HttpServletRequest httpServletRequest) throws Exception{
        StringData data = new StringData();
        if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "2")) {
            //同意流程信息执行的保存
            customProcessService.saveAudit(customComment,httpServletRequest);
            data.setCode("success");
        }
        return data;
    }

    /**
     * 会签同意以及不同意的保存方法
     *
     * @param customComment
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveApplyAudit")
    @ResponseBody
    public StringData saveApplyAudit(CustomComment customComment, Model model, RedirectAttributes redirectAttributes) {
        StringData data = new StringData();
        //如果flag  为2  就是会签同意    为3 就是会签不同意
        if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "2") || StringUtils.equalsIgnoreCase(customComment.getFlag(), "3")) {
            //会签同意流程信息执行的保存
            customProcessService.saveApplyAudit(customComment);
            data.setCode("success");
        }
        return data;
    }

    /**
     * 非会签同意以及不同意的保存方法
     *
     * @param customComment
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveApplyNoAudit")
    @ResponseBody
    public StringData saveApplyNoAudit(CustomComment customComment, Model model, RedirectAttributes redirectAttributes) {
        StringData data = new StringData();
        //如果flag  为2  就是非会签同意    为3 就是非会签不同意
        if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "2") || StringUtils.equalsIgnoreCase(customComment.getFlag(), "3")) {
            //会签同意流程信息执行的保存
            customProcessService.saveApplyNoAudit(customComment);
            data.setCode("success");
        }
        return data;
    }

    /**
     * 抄送的保存方法
     *
     * @param customComment
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveCsAudit")
    @ResponseBody
    public StringData saveCsAudit(CustomComment customComment, Model model, RedirectAttributes redirectAttributes) {
        StringData data = new StringData();
        if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "4") && customComment.getTaskDefKey().startsWith("apply_cs")) {
            //抄送流程信息执行的保存
            customProcessService.saveCsAudit(customComment);
            data.setCode("success");
        }
        return data;
    }

    /**
     * 审批驳回的保存方法
     *
     * @param customComment
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveRejectAudit")
    @ResponseBody
    public StringData saveRejectAudit(CustomComment customComment, Model model, RedirectAttributes redirectAttributes) {
        StringData data = new StringData();
        if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "0")) {
            //驳回流程信息执行的保存
            customProcessService.saveRejectAudit(customComment);
            data.setCode("success");
        }
        return data;
    }

    /**
     * 销毁申请保存的方法
     *
     * @param customComment
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "destoryAudit")
    @ResponseBody
    public StringData destoryAudit(CustomComment customComment, Model model, RedirectAttributes redirectAttributes) {
        StringData data = new StringData();
        if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "3")) {
            //销毁流程信息
            String code=customProcessService.destoryAudit(customComment);
            if(StringUtils.equalsIgnoreCase(code, "success")){
                data.setCode(code);
            }
        }
        return data;
    }


    /**
     * 撤回自由流程   已经归档的流程不能撤回，执行到归档的流程不能撤回，其他流程均可撤回
     * @param customProcess
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "deleteCustomProcess")
    public String delete(CustomProcess customProcess, RedirectAttributes redirectAttributes) {
    /*    message = "流程已经结束不允许再撤回";
          message =  "流程已经处于归档和抄送状态不允许再撤回";
          message =  "撤回未结束流程成功";
    */
        //根据实例id查询当前流程处于什么节点状态
        //在transferSet表中查询当前实例的id的节点执行到那一步了
        String message =  customProcessService.deleteCustomProcess(customProcess);
        addMessage(redirectAttributes, message);
        return "redirect:" + adminPath + "/process/customProcess/form";
    }

    /**
     * 抄送和同意的跳转页面
     *
     * @param flag     1是审批同意，2是审批抄送，3是销毁申请，0是驳回申请，4是重新提交申请
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"listAudit", ""})
    public String listCs(String flag, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        if (StringUtils.equalsIgnoreCase(flag, "1")) {
            addMessage(redirectAttributes, "审批已通过");
        } else if (StringUtils.equalsIgnoreCase(flag, "2")) {
            addMessage(redirectAttributes, "抄送任务已查看");
        } else if (StringUtils.equalsIgnoreCase(flag, "3")) {
            addMessage(redirectAttributes, "流程已被销毁,请重新申请");
        } else if (StringUtils.equalsIgnoreCase(flag, "0")) {
            addMessage(redirectAttributes, "审批已被驳回");
        } else {
            addMessage(redirectAttributes, "重新提交流程成功");
        }
        return "redirect:" + adminPath + "/act/task/todo/";
    }

}