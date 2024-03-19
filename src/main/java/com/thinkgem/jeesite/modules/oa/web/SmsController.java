package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.oa.entity.SmsSet;
import com.thinkgem.jeesite.modules.oa.entity.TableSet;
import com.thinkgem.jeesite.modules.oa.service.SmsSetService;
import com.thinkgem.jeesite.modules.oa.service.TableSetService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 短信设置Controller
 *
 * @author oa
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/smsSet")
public class SmsController extends BaseController {

    @Autowired
    private SmsSetService smsSetService;
    @Autowired
    private TableSetService tableSetService;

    /**
     * 短信设置
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"set", ""})
    public String set(Model model) throws Exception {
        List<TableSet> list = null;
        String name1 = UserUtils.getUser().getCompany().getName();
        String id2 = UserUtils.getUser().getCompany().getId();
        String id3 = UserUtils.getUser().getId();
        if("1".equals(id3)){
            list  = smsSetService.superMechanism();
        }else{
            list  = smsSetService.mechanism(id2);
        }
        model.addAttribute("companyId", id2);
        model.addAttribute("name1", name1);
        model.addAttribute("list", list);
        return "modules/oa/smsList";
    }

    /**
     * 不发短信流程
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save")
    public String save(SmsSet smsSet, String [] tableName, String isSend, Model model) throws Exception {
        TableSet ts= new TableSet();
        String[] id_name = smsSet.getCompanyId().split("-_-");
        smsSetService.del(id_name[0]);
        smsSet.setCompanyId(id_name[0]);
        smsSet.setCompanyName(id_name[1]);
        smsSet.setIsSend(isSend);
        smsSetService.save(smsSet);
        tableSetService.del(id_name[0]);
        String[] name_comment = null;
        if(tableName!=null&&tableName.length>0){
            for (int i=0; i<tableName.length; i++){
                name_comment = tableName[i].split("-_-");
                ts.setTableId(name_comment[1]);
                ts.setTableComment(name_comment[0]);
                ts.setTableName(name_comment[1]);
                ts.setIsSend("0");
                ts.setCompanyId(id_name[0]);
                tableSetService.save(ts);
            }
        }
        return set(model);
    }

    /**
     * 添加/修改机构
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "smsSave")
    public String smsSave(String companyId, Model model) throws Exception {
        String id = UserUtils.getUser().getCompany().getId();
        if(companyId.isEmpty()){
            if(smsSetService.getByIdDel(id) == null){
                SmsSet smsSet= new SmsSet();
                smsSet.setCompanyId(id);
                smsSet.setDelFlag("1");
                smsSetService.save(smsSet);
            }
            model.addAttribute("smsSet", smsSetService.getByIdDel(id));
            companyId = id;
        }else{
            model.addAttribute("smsSet", smsSetService.getById(companyId));
        }
        model.addAttribute("list", tableSetService.findAll(companyId));
        model.addAttribute("companyList", smsSetService.findCompany());
        model.addAttribute("company1", UserUtils.getUser().getCompany().getName());
        model.addAttribute("companyId", companyId);
        return "modules/oa/smsForm";
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delete")
    public String delete(String companyId, Model model) throws Exception {
        tableSetService.del(companyId);
        smsSetService.del(companyId);
        return set(model);
    }

    /**
     * 改变机构
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "change")
    public String  change(String companyId, Model model) throws Exception {
        model.addAttribute("list", tableSetService.findAll(companyId));
        model.addAttribute("smsSet", smsSetService.getById(companyId));
        return "modules/oa/smsFormList";
    }
}