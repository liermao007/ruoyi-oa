/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SynchroDataUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.process.entity.TransferSet;
import com.thinkgem.jeesite.modules.process.service.TransferSetService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.StringData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自由流程节点Controller
 *
 * @author oa
 * @version 2017-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/transferSet")
public class TransferSetController extends BaseController {

    @Autowired
    private TransferSetService transferSetService;

    @ModelAttribute
    public TransferSet get(@RequestParam(required = false) String id) {
        TransferSet entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = transferSetService.get(id);
        }
        if (entity == null) {
            entity = new TransferSet();
        }
        return entity;
    }

    /**
     * 根据实例procInstId查询，没有customPressId的时候需要过滤开始和结束节点再查询
     * @param transferSet
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"listView", ""})
    @ResponseBody
    public List<TransferSet> listViewTransfet(String procInstId,TransferSet transferSet, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<TransferSet> transferSets = new ArrayList<>();
        if(StringUtils.isNotBlank(procInstId)){
            transferSet.setProcInstId(procInstId);
            transferSets = transferSetService.findList(transferSet);
        }
        return transferSets;
    }

    /**
     * 根据实例procInstId查询,有customPressId的时候需要将所有的流转信息全部查询
     * @param transferSet
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"listAllView", ""})
    @ResponseBody
    public List<TransferSet> listViewAllTransfet(String procInstId,TransferSet transferSet, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<TransferSet> transferSets = new ArrayList<>();
        if(StringUtils.isNotBlank(procInstId)){
            transferSet.setProcInstId(procInstId);
            transferSets = transferSetService.findListByProcInstId(transferSet);
        }
        return transferSets;
    }


    @RequestMapping(value = {"list", ""})
    public String list(TransferSet transferSet, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TransferSet> page = transferSetService.findPage(new Page<TransferSet>(request, response), transferSet);
        model.addAttribute("page", page);
        return "modules/process/transferSetList";
    }

    @RequiresPermissions("process:transferSet:view")
    @RequestMapping(value = "form")
    public String form(TransferSet transferSet, Model model) {
        model.addAttribute("transferSet", transferSet);
        return "modules/process/transferSetForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public StringData save(TransferSet transferSet, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        StringData data = new StringData();
        String param = request.getParameter("param");
        List<Map<String, Object>> result = convertJSONList(param);
        //此字符串用于向里面存入，申请人设定完节点后，审批人在追加审批节点需要用到的字段。
        //将已经审批通过的流转节点的taskDefKey的值保留，在删除之后重新添加时，将其流转信息设置为已审批的状态。
        StringBuilder task = new StringBuilder();
        if (result != null) {
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    TransferSet transfer = (TransferSet) entry.getValue();
                    if(!(StringUtils.equalsIgnoreCase(transfer.getFlag(), "1"))){
                        transferSetService.delete(transfer);
                    }
                }
            }
            System.out.println(task.toString());
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    TransferSet transfer = (TransferSet) entry.getValue();
                    User u = UserUtils.get(transfer.getHandlePersonId());
                    if(u != null){
                        transfer.setHandlePersonCard(u.getCardNo());
                    }
                    transferSetService.save(transfer);
                }
            }
        }
        data.setCode("success");
        return data;
    }

    public List<Map<String, Object>> convertJSONList(String json) {
        Map<String, String[]> replaceMap = new HashMap<>();
        String namespace = TransferSet.class.getName();
        replaceMap.put("id", new String[]{namespace, "id"});
        replaceMap.put("handlePerson", new String[]{namespace, "handlePerson"});
        replaceMap.put("handlePersonId", new String[]{namespace, "handlePersonId"});
        replaceMap.put("nodeName", new String[]{namespace, "nodeName"});
        replaceMap.put("nodeType", new String[]{namespace, "nodeType"});
        replaceMap.put("taskDefKey", new String[]{namespace, "taskDefKey"});
        replaceMap.put("flag", new String[]{namespace, "flag"});
        replaceMap.put("procInstId", new String[]{namespace, "procInstId"});
        return SynchroDataUtils.getDataByJson(json, replaceMap);
    }

    @RequiresPermissions("process:transferSet:edit")
    @RequestMapping(value = "delete")
    public String delete(TransferSet transferSet, RedirectAttributes redirectAttributes) {
        transferSetService.delete(transferSet);
        addMessage(redirectAttributes, "删除保存自由流程节点成功成功");
        return "redirect:" + Global.getAdminPath() + "/process/transferSet/?repage";
    }

}