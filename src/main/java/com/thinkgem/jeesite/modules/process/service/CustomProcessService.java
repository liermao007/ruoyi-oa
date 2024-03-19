/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import cn.hutool.core.codec.Base64;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.PersonSigns;
import com.thinkgem.jeesite.modules.oa.service.PersonSignsService;
import com.thinkgem.jeesite.modules.process.dao.CustomProcessDao;
import com.thinkgem.jeesite.modules.process.dao.CustomRuskTaskinstDao;
import com.thinkgem.jeesite.modules.process.entity.*;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.test.testmsm.SmsModule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自由流程信息Service
 *
 * @author oa
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class CustomProcessService extends CrudService<CustomProcessDao, CustomProcess> {

    @Autowired
    private CustomHiTaskinstService customHiTaskinstService;

    @Autowired
    private CustomRuskTaskinstService customRuskTaskinstService;

    @Autowired
    private TransferSetService transferSetService;

    @Autowired
    private CustomCommentService customCommentService;

    @Autowired
    private CustomHiActinstService customHiActinstService;

    @Autowired
    private PersonSignsService personSignsService;

    @Autowired
    private CustomRuskTaskinstDao customRuskTaskinstDao;


    public CustomProcess get(String id) {
        return super.get(id);
    }

    public List<CustomProcess> findList(CustomProcess customProcess) {
        return super.findList(customProcess);
    }

    public Page<CustomProcess> findPage(Page<CustomProcess> page, CustomProcess customProcess) {
        return super.findPage(page, customProcess);
    }

    /**
     * 用户提交自由流程的时候，需要执行的方法
     *
     * @param customProcess
     */
    @Transactional(readOnly = false)
    public String saveA(CustomProcess customProcess) {
        //记录流转信息中的下一步人
        String people = null;
        //在点击提交自由流程时，custom_process表中插入一条，整体的数据的信息
        super.save(customProcess);

        //将custom_hi_taskinst表中对应的实例id的comment意见删除
        CustomHiTaskinst taskinst1 = new CustomHiTaskinst();
        taskinst1.setProcInstId(customProcess.getProcInstId());
        taskinst1.setComment(null);
        customHiTaskinstService.updateComment(taskinst1);

        //因为如果是重新提交的申请，那么则需要将custom_ru_taskinst中的当前的信息删除。
        CustomRuskTaskinst taskinst = new CustomRuskTaskinst();
        taskinst.setTaskDefKey("start");
        taskinst.setProcInstId(customProcess.getProcInstId());
        List<CustomRuskTaskinst> taskinsts = customRuskTaskinstService.findByProInstId(taskinst);
        if (taskinsts != null && taskinsts.size() > 0 && StringUtils.equalsIgnoreCase(taskinsts.get(0).getTaskDefKey(), "start")) {
            customRuskTaskinstService.delete(taskinsts.get(0));
            CustomHiActinst customHiActinst = new CustomHiActinst();
            customHiActinst.setProcInstId(customProcess.getProcInstId());
            customHiActinst.setTaskDefKey("start");
            customHiActinst.setEndTime(null);
            customHiActinst.setAssignee(UserUtils.getUser().getCardNo());
            List<CustomHiActinst> customHiActinsts = customHiActinstService.findListByEndTime(customHiActinst);
            if (customHiActinsts != null && customHiActinsts.size() > 0 && StringUtils.equalsIgnoreCase(customHiActinsts.get(0).getTaskDefKey(), "start")) {
                customHiActinsts.get(0).setEndTime(new Date());
                customHiActinstService.save(customHiActinsts.get(0));
            }
        } else {
            //同时向custom_hi_taskinst表中插入一条开始创建人创建的信息的数据
            CustomHiTaskinst customHiTaskinst = new CustomHiTaskinst();
            customHiTaskinst.setProcInstId(customProcess.getProcInstId());
            customHiTaskinst.setTaskDefKey("start");
            customHiTaskinst.setStartTime(new Date());
            customHiTaskinst.setEndTime(new Date());
            customHiTaskinst.setName("创建");
            customHiTaskinst.setAssignee(UserUtils.getUser().getCardNo());
            customHiTaskinstService.save(customHiTaskinst);

            //向custom_hi_actinst表中插入一条开始数据，用于在意见中显示
            CustomHiActinst customHiActinst = new CustomHiActinst();
            customHiActinst.setProcInstId(customProcess.getProcInstId());
            customHiActinst.setName("创建");
            customHiActinst.setTaskDefKey("start");
            customHiActinst.setStartTime(new Date());
            customHiActinst.setEndTime(new Date());
            customHiActinst.setAssignee(UserUtils.getUser().getCardNo());
            customHiActinstService.save(customHiActinst);
        }
        //在提交流程的时候，首先先将start开始的节点设置为flag为1
        TransferSet t = new TransferSet();
        t.setProcInstId(customProcess.getProcInstId());
        t.setTaskDefKey("start");
        List<TransferSet> ts = transferSetService.findListByProcInstId(t);
        if (ts != null && ts.size() > 0) {
            ts.get(0).setFlag("1");
            transferSetService.updateFlag(ts.get(0));
        }

        //下面的步骤只适用于刚开始提交申请的时候，下一步的节点是审批的节点--------开始
        //之后根据procInstId去向流转设定中查询拿到除开始节点外的下一步节点，并手动设置taskDefKey的值，例如：audit1等
        TransferSet transferSet = new TransferSet();
        transferSet.setProcInstId(customProcess.getProcInstId());
        List<TransferSet> list = transferSetService.findList(transferSet);
        String companyId = UserUtils.getUser().getCompany().getId();
        //拿到下一步人的信息后，向custom_ru_taskinst中插入一条正在执行的任务的信息。
        if (list != null && list.size() > 0 && list.get(0).getTaskDefKey().startsWith("audit")) {
            CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
            customRuskTaskinst.setProcInstId(customProcess.getProcInstId());
            User user = UserUtils.get(list.get(0).getHandlePersonId());
            if (user != null) {
                customRuskTaskinst.setAssignee(user.getCardNo());
                //设置下一步人信息
                people = user.getName();
                //发送短信
                SmsModule.flowWork(companyId, user.getMobile(), user.getName(), UserUtils.getUser().getName(), "自由流程");
            }
            customRuskTaskinst.setTaskDefKey(list.get(0).getTaskDefKey());
            customRuskTaskinst.setName(list.get(0).getNodeName());
            customRuskTaskinst.setStartTime(new Date());
            customRuskTaskinst.setFlagAudit("0");
            customRuskTaskinstService.save(customRuskTaskinst);

            //向custom_hi_actinst中添加一个记录，用于在意见中知道是下一步的审批人是谁
            CustomHiActinst actinst = new CustomHiActinst();
            actinst.setAssignee(user.getCardNo());
            actinst.setName(list.get(0).getNodeName());
            actinst.setProcInstId(customProcess.getProcInstId());
            actinst.setStartTime(new Date());
            actinst.setTaskDefKey(list.get(0).getTaskDefKey());
            customHiActinstService.save(actinst);
        }
        //下面的步骤只适用于刚开始提交申请的时候，下一步的节点是审批的节点--------结束

        //下面的步骤只适用于刚开始提交申请的时候，下一步的节点是会签的节点--------开始
        //拿到下一步人的信息后，向custom_ru_taskinst中插入正在执行的任务的信息。
        if (list != null && list.size() > 0 && list.get(0).getTaskDefKey().startsWith("apply_audit") || list.get(0).getTaskDefKey().startsWith("apply_no_audit") || list.get(0).getTaskDefKey().startsWith("apply_cs")) {
            String[] name = list.get(0).getHandlePersonId().split(",");
            String names = "";
            for (int j = 0; j < name.length; j++) {
                CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
                customRuskTaskinst.setProcInstId(customProcess.getProcInstId());
                customRuskTaskinst.setTaskDefKey(list.get(0).getTaskDefKey());
                customRuskTaskinst.setName(list.get(0).getNodeName());
                customRuskTaskinst.setStartTime(new Date());
                customRuskTaskinst.setFlagAudit("0");
                CustomHiActinst actinst = new CustomHiActinst();
                actinst.setName(list.get(0).getNodeName());
                actinst.setProcInstId(customProcess.getProcInstId());
                actinst.setStartTime(new Date());
                actinst.setTaskDefKey(list.get(0).getTaskDefKey());
                User user = UserUtils.get(name[j]);
                if (user != null) {
                    customRuskTaskinst.setAssignee(user.getCardNo());
                    names = names + " " + user.getName();
                    if (list.get(0).getTaskDefKey().startsWith("apply_cs")) {
                        people = "抄送：" + names; //设置下一步人信息
                    } else if (list.get(0).getTaskDefKey().startsWith("apply_audit")) {
                        people = "会签：" + names; //设置下一步人信息
                    } else if (list.get(0).getTaskDefKey().startsWith("apply_no_audit")) {
                        people = "非会签：" + names; //设置下一步人信息
                    }
                    //发送短信
                    SmsModule.flowWork(companyId, user.getMobile(), user.getName(), UserUtils.getUser().getName(), "自由流程");
                    customRuskTaskinstService.save(customRuskTaskinst);
                    //向custom_hi_actinst中添加一个记录，用于在意见中知道是下一步的审批人是谁
                    actinst.setAssignee(user.getCardNo());
                    customHiActinstService.save(actinst);
                }
            }

            if (list.get(0).getTaskDefKey().startsWith("apply_cs")) {
                //在提交流程的时候，首先先将start开始的节点设置为flag为1
                TransferSet t1 = new TransferSet();
                t1.setProcInstId(customProcess.getProcInstId());
                t1.setTaskDefKey(list.get(0).getTaskDefKey());
                List<TransferSet> ts1 = transferSetService.findListByProcInstId(t1);
                if (ts1 != null && ts1.size() > 0) {
                    ts1.get(0).setFlag("1");
                    transferSetService.updateFlag(ts1.get(0));
                }
                CustomComment customComment = new CustomComment();
                customComment.setProcInstId(customProcess.getProcInstId());
                setAudit(customComment, list, list.get(1));
                if (list.get(1).getTaskDefKey().startsWith("apply_audit")) {
                    String[] namess = list.get(1).getHandlePersonId().split(",");
                    for (int j = 0; j < namess.length; j++) {
                        CustomHiActinst actinst = new CustomHiActinst();
                        actinst.setName(list.get(1).getNodeName());
                        actinst.setProcInstId(customProcess.getProcInstId());
                        actinst.setStartTime(new Date());
                        actinst.setTaskDefKey(list.get(1).getTaskDefKey());
                        User user = UserUtils.get(namess[j]);
                        if (user != null) {
                            //向custom_hi_actinst中添加一个记录，用于在意见中知道是下一步的审批人是谁
                            actinst.setAssignee(user.getCardNo());
                            customHiActinstService.save(actinst);
                        }
                    }
                }

            }

        }
        //下面的步骤只适用于刚开始提交申请的时候，下一步的节点是会签的节点--------结束
        return people;
    }

    @Transactional(readOnly = false)
    public void delete(CustomProcess customProcess) {
        super.delete(customProcess);
    }

    /**
     * 审批同意的时候，需要执行的方法
     *
     * @param customComment
     */
    @Transactional(readOnly = false)
    public void saveAudit(CustomComment customComment, HttpServletRequest request) throws Exception {
        //目前的同意，只适用于节点类型为”审批“的节点，同时审批人是一个人
        //在custom_hi_taskinst中将custom_rusk_taskinst表中的数据插入进入，并标识成已完成的状态
        CustomRuskTaskinst customRuskTaskinst = customRuskTaskinstService.get(customComment.getId());

        //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
        customComment.setUserId(UserUtils.getUser().getCardNo());
        customComment.setId(null);
        //向意见中加入签名图片和审批的日期
        PersonSigns personSigns = personSignsService.getLoginId(UserUtils.getUser().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        if (personSigns != null) {
            String url = "<img style=\"width:90px;height:60px;\" src=\"" + personSigns.getSignUrl() + "\"/>";
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[同意]" + date + "<br>");
            } else {
                customComment.setComment("[同意]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment() + url;
            customComment.setComment(comment);
        } else {
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[同意]" + date + "<br>");
            } else {
                customComment.setComment("[同意]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment();
            customComment.setComment(comment);
        }
        //向custom_hi_actinst中将当前执行人同意之后时的end_time的值设置成为“当前审批通过的时间”
        CustomHiActinst customHiActinst = new CustomHiActinst();
        customHiActinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiActinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiActinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        List<CustomHiActinst> customHiActinsts = customHiActinstService.findCustomHiActinst(customHiActinst);
        if (customHiActinsts != null && customHiActinsts.size() > 0) {
            customHiActinsts.get(0).setEndTime(new Date());
            customHiActinsts.get(0).setComment(customComment.getComment());
            customHiActinstService.save(customHiActinsts.get(0));
        }

        //更新意见
        CustomHiTaskinst customHiTaskinst = new CustomHiTaskinst();
        customHiTaskinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiTaskinst.setName(customRuskTaskinst.getName());
        customHiTaskinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiTaskinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        customRuskTaskinst.setStartTime(customRuskTaskinst.getStartTime());
        customHiTaskinst.setReason("completed");
        customHiTaskinst.setStartTime(customRuskTaskinst.getStartTime());
        customHiTaskinst.setComment(customComment.getComment());
        List<CustomHiTaskinst> customHiTaskinsts = customHiTaskinstService.findList(customHiTaskinst);
        if (customHiTaskinsts != null && customHiTaskinsts.size() > 0) {
            customHiTaskinsts.get(0).setComment(customComment.getComment());
            customHiTaskinstService.save(customHiTaskinsts.get(0));
        } else {
            customHiTaskinst.setEndTime(new Date());
            customHiTaskinstService.save(customHiTaskinst);
        }
        //加入签名图片结束
        customCommentService.save(customComment);

        //将同一个实例id的 procInstId中custom_rusk_taskinst 表中的这条记录删除
        customRuskTaskinstService.delete(customRuskTaskinst);

        //同时去流程信息中将当前实例id和当前用户的id查询出来记录，将其中的flag标识，至成1
        TransferSet transferSet = new TransferSet();
        transferSet.setProcInstId(customComment.getProcInstId());
        transferSet.setTaskDefKey(customComment.getTaskDefKey());
        transferSet.setHandlePersonCard(UserUtils.getUser().getCardNo());
        List<TransferSet> list = transferSetService.findList(transferSet);
        if (list != null && list.size() > 0) {
            list.get(0).setFlag("1");
            transferSetService.updateFlag(list.get(0));
        }

        //在点击同意所有的数据都处理完成后，之后判断流转信息中的数据是否都处理完成，如果都处理完成则向custom_hi_actinst表中加入一条结束的信息。
        if (customComment != null && StringUtils.equalsIgnoreCase(customComment.getTaskDefKey(), "apply_end")) {

            //将前台传递的base64转成图片，并保存在本地
            if (StringUtils.isNotBlank(customComment.getTbodyHtml())) {
                String strImg = customComment.getTbodyHtml();
                strImg = strImg.substring(22);
                byte[] b = Base64.decode(strImg);
                ByteArrayInputStream bais = new ByteArrayInputStream(b);
                BufferedImage bi = ImageIO.read(bais);
                String realPath = request.getSession().getServletContext().getRealPath("/static" + File.separator + "userfiles");
                realPath = realPath + File.separator + "keep";
                String filename = customComment.getProcInstId();
                File w2 = new File(realPath + "/" + filename + ".png");
                ImageIO.write(bi, "png", w2);
                //结束
            }

            CustomHiActinst hiActinst = new CustomHiActinst();
            hiActinst.setStartTime(new Date());
            hiActinst.setEndTime(new Date());
            hiActinst.setAssignee("33");
            hiActinst.setName("结束");
            hiActinst.setProcInstId(customComment.getProcInstId());
            customHiActinstService.save(hiActinst);
        }


        //查找下一步人的流程信息，并将其设置到此人来审批，在custom_rusk_taskinst中将此人的记录插入
        TransferSet set = new TransferSet();
        set.setProcInstId(customComment.getProcInstId());
        List<TransferSet> transferSets = transferSetService.findList(set);
        if (transferSets != null && transferSets.size() > 0) {
            if (transferSets.get(0).getTaskDefKey().startsWith("audit") || transferSets.get(0).getTaskDefKey().startsWith("apply_end")
                    || transferSets.get(0).getTaskDefKey().startsWith("apply_no_audit")) {
                //设置下一步审批人
                setAudit(customComment, transferSets, transferSets.get(0));
            } else if (transferSets.get(0).getTaskDefKey().startsWith("apply_audit")) {
                setAudit(customComment, transferSets, transferSets.get(0));
                String[] name = transferSets.get(0).getHandlePersonId().split(",");
                for (int j = 0; j < name.length; j++) {
                    User user = UserUtils.get(name[j]);
                    if (user != null) {
                        CustomHiActinst actinst = new CustomHiActinst();
                        actinst.setName(transferSets.get(0).getNodeName());
                        actinst.setProcInstId(customComment.getProcInstId());
                        actinst.setStartTime(new Date());
                        actinst.setTaskDefKey(transferSets.get(0).getTaskDefKey());
                        actinst.setAssignee(user.getCardNo());
                        customHiActinstService.save(actinst);
                    }
                }
            } else if (transferSets.get(0).getTaskDefKey().startsWith("apply_cs")) {
                //如果是抄送的话，就先将flag设置为1
                TransferSet tset = new TransferSet();
                tset.setProcInstId(customComment.getProcInstId());
                tset.setTaskDefKey(transferSets.get(0).getTaskDefKey());
                List<TransferSet> list1 = transferSetService.findList(tset);
                if (list1 != null && list1.size() > 0) {
                    list1.get(0).setFlag("1");
                    transferSetService.updateFlag(list1.get(0));
                }
                //如果下一步人是抄送的功能，那么需要单独处理流转信息。
                //首先先来判断任务节点是apply_cs的情况下，当前的流转信息中的处理人有几人
                String handlePerson = transferSets.get(0).getHandlePersonId();
                if (handlePerson.indexOf(",") != -1) {
                    //不等于-1，那么则表示此字符串中包含逗号，那么则表示此时的处理人有多个
                    //则需要拆分，并分别处理
                    String[] handleIds = handlePerson.split(",");
                    for (int i = 0; i < handleIds.length; i++) {
                        handleTransfer(customComment, handleIds[i], transferSets);
                    }
                } else {
                    //只有一个抄送人的时候
                    handleTransfer(customComment, handlePerson, transferSets);
                }

                //同时在向custom_ru_taskinst中插入多条记录的同时，还需要找到抄送之后的下一步的意见，并且同时将此条节点的信息也一并保存到custom_hi_actinst表中
                setAudit(customComment, transferSets, transferSets.get(1));
                //之后根据处理人的人数来处理抄送的信息。
                //如果有一个人那么直接将流转信息中的这条流转记录，之后在custom_hi_actinst中插入一条记录。
                //如果处理人是多个人的时候，那么需要根据逗号，来拆分出每个处理人，并同时向custom_hi_actinst中插入多条记录。

            }
        }
    }


    /**
     * 非会签同意以及不同意的时候，需要执行的方法
     *
     * @param customComment
     */
    @Transactional(readOnly = false)
    public void saveApplyNoAudit(CustomComment customComment) {
        CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
        customRuskTaskinst.setFlagAudit("0");
        customRuskTaskinst.setProcInstId(customComment.getProcInstId());
        customRuskTaskinst.setTaskDefKey("apply_no_audit");
        List<CustomRuskTaskinst> customs = customRuskTaskinstService.findList(customRuskTaskinst);
        //如果只有一条记录，那么则需要进行下一步的操作
        if (customs != null && customs.size() > 0 && customs.size() == 1) {
            //查询会签结果是同意还是不同意，如果不同意则返回到修改人处，同意则执行下一步
            CustomRuskTaskinst st = customRuskTaskinstService.get(customComment.getId());
            if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "2")) {
                st.setFlagAudit("1");
                Integer c = customRuskTaskinstDao.update(st);
                CustomRuskTaskinst taskinst = customRuskTaskinstService.get(customComment.getId());
                //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                customComment.setUserId(UserUtils.getUser().getCardNo());
                customComment.setId(null);
                CustomComment addComment = addCustomComment(customComment);
                applyAudit(customComment, taskinst, addComment);
            } else {
                //会签流程中其中有一条是不同意的意见
                CustomRuskTaskinst taskinst = customRuskTaskinstService.get(customComment.getId());
                taskinst.setFlagAudit("2");
                Integer c = customRuskTaskinstDao.update(taskinst);
                if (!("null".equals(String.valueOf(c)) && "0".equals(String.valueOf(c)))) {
                    //并向custom_hi_actinst表中加入此条记录，和custom_hi_taskinst表中也加入
                    //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                    customComment.setUserId(UserUtils.getUser().getCardNo());
                    customComment.setId(null);
                    CustomComment addComment = addNoCustomComment(customComment);
                    applyAudit(customComment, taskinst, addComment);
                }
            }

            //在会签判断下一步人的时候，首先需要先判断这个会签是否是通过还去不通过
            CustomRuskTaskinst inst = new CustomRuskTaskinst();
            inst.setProcInstId(customComment.getProcInstId());
            inst.setFlagAudit("1");
            List<CustomRuskTaskinst> byProInstId = customRuskTaskinstService.findByProInstId(inst);
            if (byProInstId != null && byProInstId.size() > 0) {
                //同意，执行下一步人
                //查找下一步人的信息，并将transfer_set中的此条记录的flag标记设置为1
                //并且删除此实例id以及flagAudit为1的数据，在custom_ru_taskinst表中的
                customRuskTaskinstDao.deleteByProcInstId(customComment.getProcInstId());
                //将流转设定中的flag标识设置为1
                TransferSet transferSet = new TransferSet();
                transferSet.setProcInstId(customComment.getProcInstId());
                transferSet.setTaskDefKey(customComment.getTaskDefKey());
                List<TransferSet> sets = transferSetService.findList(transferSet);
                if (sets != null && sets.size() > 0) {
                    sets.get(0).setFlag("1");
                    transferSetService.updateFlag(sets.get(0));
                }
                TransferSet set = new TransferSet();
                set.setProcInstId(customComment.getProcInstId());
                List<TransferSet> transferSets = transferSetService.findList(set);
                //设置下一步审批人的信息
                if (transferSets != null && transferSets.size() > 0) {
                    if (transferSets.get(0).getTaskDefKey().startsWith("apply_cs")) {
                        //如果是抄送的话，就先将flag设置为1
                        TransferSet tset = new TransferSet();
                        tset.setProcInstId(customComment.getProcInstId());
                        tset.setTaskDefKey(transferSets.get(0).getTaskDefKey());
                        List<TransferSet> list1 = transferSetService.findList(tset);
                        if (list1 != null && list1.size() > 0) {
                            list1.get(0).setFlag("1");
                            transferSetService.updateFlag(list1.get(0));
                        }
                        String handlePerson = transferSets.get(0).getHandlePersonId();
                        if (handlePerson.indexOf(",") != -1) {
                            //不等于-1，那么则表示此字符串中包含逗号，那么则表示此时的处理人有多个
                            //则需要拆分，并分别处理
                            String[] handleIds = handlePerson.split(",");
                            for (int i = 0; i < handleIds.length; i++) {
                                handleTransfer(customComment, handleIds[i], transferSets);
                            }
                        } else {
                            //只有一个抄送人的时候
                            handleTransfer(customComment, handlePerson, transferSets);
                        }
                        //同时在向custom_ru_taskinst中插入多条记录的同时，还需要找到抄送之后的下一步的意见，并且同时将此条节点的信息也一并保存到custom_hi_actinst表中
                        setAudit(customComment, transferSets, transferSets.get(1));
                    } else {
                        setAudit(customComment, transferSets, transferSets.get(0));
                    }

                    if (transferSets.get(0).getTaskDefKey().startsWith("apply_audit")) {
                        String[] name = transferSets.get(0).getHandlePersonId().split(",");
                        for (int j = 0; j < name.length; j++) {
                            User user = UserUtils.get(name[j]);
                            if (user != null) {
                                CustomHiActinst actinst = new CustomHiActinst();
                                actinst.setName(transferSets.get(0).getNodeName());
                                actinst.setProcInstId(customComment.getProcInstId());
                                actinst.setStartTime(new Date());
                                actinst.setTaskDefKey(transferSets.get(0).getTaskDefKey());
                                actinst.setAssignee(user.getCardNo());
                                customHiActinstService.save(actinst);
                            }
                        }
                    }
                }


            } else {
                //同时向custom_ru_taskinst表中插入一条关于当前申请人的相关的信息，用于当驳回时，在申请人处可以查看被驳回的记录，并修改以及提交
                customRuskTaskinstService.deleteByProcInstId(customComment.getProcInstId());
                //之后，将transfer_set中的根据procInstId查询的流转信息中的flag标识设置为0，表示没有审批的流程
                TransferSet transferSet = new TransferSet();
                transferSet.setProcInstId(customComment.getProcInstId());
                List<TransferSet> list = transferSetService.findListByProcInstId(transferSet);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag("0");
                        transferSetService.updateFlag(list.get(i));
                    }
                    //向custom_ru_taskinst中加入需要当前申请人来执行的重新提交的申请的记录。
                    CustomRuskTaskinst rusk = new CustomRuskTaskinst();
                    rusk.setStartTime(new Date());
                    rusk.setProcInstId(customComment.getProcInstId());
                    rusk.setTaskDefKey("start");
                    rusk.setName("申请人修改");
                    rusk.setAssignee(list.get(0).getHandlePersonCard());
                    rusk.setFlagAudit("0");
                    customRuskTaskinstService.save(rusk);
                }
                //说明不同意  返回到申请人处
                //驳回之后，将下一步的流程信息插入到custom_hi_actinst中插入下一步的信息
                TransferSet set = new TransferSet();
                set.setProcInstId(customComment.getProcInstId());
                List<TransferSet> transferSets = transferSetService.findListByProcInstId(set);
                if (transferSets != null && transferSets.size() > 0) {
                    User user = UserUtils.get(transferSets.get(0).getHandlePersonId());
                    //在设置完时间之后，向custom_hi_actinst中插入一条下一步人的提交的信息，用于在提交意见中显示使用
                    CustomHiActinst actinst = new CustomHiActinst();
                    actinst.setProcInstId(customComment.getProcInstId());
                    actinst.setStartTime(new Date());
                    actinst.setTaskDefKey(transferSets.get(0).getTaskDefKey());
                    actinst.setName("创建");
                    if (user != null) {
                        actinst.setAssignee(user.getCardNo());
                    }
                    customHiActinstService.save(actinst);
                }
            }
        } else {
            //否则，则还有多条记录只需要将custom_ru_taskinst表中的数据的flagAudit设置成“1”
            //customComment.getId()里面存储的custom_ru_taskinst表中的id
            CustomRuskTaskinst taskinst = customRuskTaskinstService.get(customComment.getId());
            if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "2")) {
                taskinst.setFlagAudit("1");
                Integer c = customRuskTaskinstDao.update(taskinst);
                if (!("null".equals(String.valueOf(c)) && "0".equals(String.valueOf(c)))) {
                    //并向custom_hi_actinst表中加入此条记录，和custom_hi_taskinst表中也加入
                    //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                    customComment.setUserId(UserUtils.getUser().getCardNo());
                    customComment.setId(null);
                    CustomComment addComment = addCustomComment(customComment);
                    applyAudit(customComment, taskinst, addComment);
                }
            } else {
                taskinst.setFlagAudit("2");
                Integer c = customRuskTaskinstDao.update(taskinst);
                if (!("null".equals(String.valueOf(c)) && "0".equals(String.valueOf(c)))) {
                    //并向custom_hi_actinst表中加入此条记录，和custom_hi_taskinst表中也加入
                    //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                    customComment.setUserId(UserUtils.getUser().getCardNo());
                    customComment.setId(null);
                    CustomComment addComment = addNoCustomComment(customComment);
                    applyAudit(customComment, taskinst, addComment);
                }
            }


        }
    }


    /**
     * 会签同意以及不同意的时候，需要执行的方法
     *
     * @param customComment
     */
    @Transactional(readOnly = false)
    public void saveApplyAudit(CustomComment customComment) {
        CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
        customRuskTaskinst.setFlagAudit("0");
        customRuskTaskinst.setProcInstId(customComment.getProcInstId());
        customRuskTaskinst.setTaskDefKey("apply_audit");
        List<CustomRuskTaskinst> customs = customRuskTaskinstService.findList(customRuskTaskinst);
        //如果只有一条记录，那么则需要进行下一步的操作
        if (customs != null && customs.size() > 0 && customs.size() == 1) {
            //查询会签结果是同意还是不同意，如果不同意则返回到修改人处，同意则执行下一步
            CustomRuskTaskinst st = customRuskTaskinstService.get(customComment.getId());
            if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "2")) {
                st.setFlagAudit("1");
                Integer c = customRuskTaskinstDao.update(st);
//            applyAudit(customComment,taskinst);
                CustomRuskTaskinst taskinst = customRuskTaskinstService.get(customComment.getId());
                //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                customComment.setUserId(UserUtils.getUser().getCardNo());
                customComment.setId(null);
                CustomComment addComment = addCustomComment(customComment);
                applyAudit(customComment, taskinst, addComment);
            } else {
                //会签流程中其中有一条是不同意的意见
                CustomRuskTaskinst taskinst = customRuskTaskinstService.get(customComment.getId());
                taskinst.setFlagAudit("2");
                Integer c = customRuskTaskinstDao.update(taskinst);
                if (!("null".equals(String.valueOf(c)) && "0".equals(String.valueOf(c)))) {
                    //并向custom_hi_actinst表中加入此条记录，和custom_hi_taskinst表中也加入
                    //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                    customComment.setUserId(UserUtils.getUser().getCardNo());
                    customComment.setId(null);
                    CustomComment addComment = addNoCustomComment(customComment);
                    applyAudit(customComment, taskinst, addComment);
                }
            }

            //在会签判断下一步人的时候，首先需要先判断这个会签是否是通过还去不通过
            CustomRuskTaskinst inst = new CustomRuskTaskinst();
            inst.setProcInstId(customComment.getProcInstId());
            inst.setFlagAudit("2");
            List<CustomRuskTaskinst> byProInstId = customRuskTaskinstService.findByProInstId(inst);
            if (byProInstId != null && byProInstId.size() > 0) {
                //同时向custom_ru_taskinst表中插入一条关于当前申请人的相关的信息，用于当驳回时，在申请人处可以查看被驳回的记录，并修改以及提交
                customRuskTaskinstService.deleteByProcInstId(customComment.getProcInstId());
                //之后，将transfer_set中的根据procInstId查询的流转信息中的flag标识设置为0，表示没有审批的流程
                TransferSet transferSet = new TransferSet();
                transferSet.setProcInstId(customComment.getProcInstId());
                List<TransferSet> list = transferSetService.findListByProcInstId(transferSet);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag("0");
                        transferSetService.updateFlag(list.get(i));
                    }
                    //向custom_ru_taskinst中加入需要当前申请人来执行的重新提交的申请的记录。
                    CustomRuskTaskinst rusk = new CustomRuskTaskinst();
                    rusk.setStartTime(new Date());
                    rusk.setProcInstId(customComment.getProcInstId());
                    rusk.setTaskDefKey("start");
                    rusk.setName("申请人修改");
                    rusk.setAssignee(list.get(0).getHandlePersonCard());
                    rusk.setFlagAudit("0");
                    //发送短信
                    User user = UserUtils.get(list.get(0).getHandlePersonId());
                    if (user != null) {
                        String companyId = UserUtils.getUser().getCompany().getId();
                        SmsModule.flowWork(companyId, user.getMobile(), user.getName(), UserUtils.getUser().getName(), "自由流程");
                    }
                    customRuskTaskinstService.save(rusk);
                }
                //说明不同意  返回到申请人处
                //驳回之后，将下一步的流程信息插入到custom_hi_actinst中插入下一步的信息
                TransferSet set = new TransferSet();
                set.setProcInstId(customComment.getProcInstId());
                List<TransferSet> transferSets = transferSetService.findListByProcInstId(set);
                if (transferSets != null && transferSets.size() > 0) {
                    User user = UserUtils.get(transferSets.get(0).getHandlePersonId());
                    //在设置完时间之后，向custom_hi_actinst中插入一条下一步人的提交的信息，用于在提交意见中显示使用
                    CustomHiActinst actinst = new CustomHiActinst();
                    actinst.setProcInstId(customComment.getProcInstId());
                    actinst.setStartTime(new Date());
                    actinst.setTaskDefKey(transferSets.get(0).getTaskDefKey());
                    actinst.setName("创建");
                    if (user != null) {
                        actinst.setAssignee(user.getCardNo());
                    }
                    customHiActinstService.save(actinst);
                }
            } else {
                //同意，执行下一步人
                //查找下一步人的信息，并将transfer_set中的此条记录的flag标记设置为1
                //并且删除此实例id以及flagAudit为1的数据，在custom_ru_taskinst表中的
                customRuskTaskinstDao.deleteByProcInstId(customComment.getProcInstId());
                //将流转设定中的flag标识设置为1
                TransferSet transferSet = new TransferSet();
                transferSet.setProcInstId(customComment.getProcInstId());
                transferSet.setTaskDefKey(customComment.getTaskDefKey());
                List<TransferSet> sets = transferSetService.findList(transferSet);
                if (sets != null && sets.size() > 0) {
                    sets.get(0).setFlag("1");
                    transferSetService.updateFlag(sets.get(0));
                }
                TransferSet set = new TransferSet();
                set.setProcInstId(customComment.getProcInstId());
                List<TransferSet> transferSets = transferSetService.findList(set);
                //设置下一步审批人的信息
                if (transferSets != null && transferSets.size() > 0) {
                    if (transferSets.get(0).getTaskDefKey().startsWith("apply_cs")) {
                        //如果是抄送的话，就先将flag设置为1
                        TransferSet tset = new TransferSet();
                        tset.setProcInstId(customComment.getProcInstId());
                        tset.setTaskDefKey(transferSets.get(0).getTaskDefKey());
                        List<TransferSet> list1 = transferSetService.findList(tset);
                        if (list1 != null && list1.size() > 0) {
                            list1.get(0).setFlag("1");
                            transferSetService.updateFlag(list1.get(0));
                        }
                        String handlePerson = transferSets.get(0).getHandlePersonId();
                        if (handlePerson.indexOf(",") != -1) {
                            //不等于-1，那么则表示此字符串中包含逗号，那么则表示此时的处理人有多个
                            //则需要拆分，并分别处理
                            String[] handleIds = handlePerson.split(",");
                            for (int i = 0; i < handleIds.length; i++) {
                                handleTransfer(customComment, handleIds[i], transferSets);
                            }
                        } else {
                            //只有一个抄送人的时候
                            handleTransfer(customComment, handlePerson, transferSets);
                        }
                        //同时在向custom_ru_taskinst中插入多条记录的同时，还需要找到抄送之后的下一步的意见，并且同时将此条节点的信息也一并保存到custom_hi_actinst表中
                        setAudit(customComment, transferSets, transferSets.get(1));
                    } else {
                        setAudit(customComment, transferSets, transferSets.get(0));
                    }
                }
            }
        } else {
            //否则，则还有多条记录只需要将custom_ru_taskinst表中的数据的flagAudit设置成“1”
            //customComment.getId()里面存储的custom_ru_taskinst表中的id
            CustomRuskTaskinst taskinst = customRuskTaskinstService.get(customComment.getId());
            if (StringUtils.equalsIgnoreCase(customComment.getFlag(), "2")) {
                taskinst.setFlagAudit("1");
                Integer c = customRuskTaskinstDao.update(taskinst);
                if (!("null".equals(String.valueOf(c)) && "0".equals(String.valueOf(c)))) {
                    //并向custom_hi_actinst表中加入此条记录，和custom_hi_taskinst表中也加入
                    //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                    customComment.setUserId(UserUtils.getUser().getCardNo());
                    customComment.setId(null);
                    CustomComment addComment = addCustomComment(customComment);
                    applyAudit(customComment, taskinst, addComment);
                }
            } else {
                taskinst.setFlagAudit("2");
                Integer c = customRuskTaskinstDao.update(taskinst);
                if (!("null".equals(String.valueOf(c)) && "0".equals(String.valueOf(c)))) {
                    //并向custom_hi_actinst表中加入此条记录，和custom_hi_taskinst表中也加入
                    //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
                    customComment.setUserId(UserUtils.getUser().getCardNo());
                    customComment.setId(null);
                    CustomComment addComment = addNoCustomComment(customComment);
                    applyAudit(customComment, taskinst, addComment);
                }
            }


        }
    }


    /**
     * 循环执行
     *
     * @param handleId
     * @param transferSets
     */
    private void handleTransfer(CustomComment customComment, String handleId, List<TransferSet> transferSets) {
        CustomRuskTaskinst ruskTaskinst = new CustomRuskTaskinst();
        ruskTaskinst.setProcInstId(customComment.getProcInstId());
        User user = UserUtils.get(handleId);
        if (user != null) {
            ruskTaskinst.setAssignee(user.getCardNo());
        }
        ruskTaskinst.setTaskDefKey(transferSets.get(0).getTaskDefKey());
        ruskTaskinst.setName(transferSets.get(0).getNodeName());
        ruskTaskinst.setStartTime(new Date());
        ruskTaskinst.setFlagAudit("0");
        customRuskTaskinstService.save(ruskTaskinst);

        //在设置完时间之后，向custom_hi_actinst中插入一条下一步人的提交的信息，用于在提交意见中显示使用
        CustomHiActinst actinst = new CustomHiActinst();
        actinst.setProcInstId(customComment.getProcInstId());
        actinst.setStartTime(new Date());
        actinst.setTaskDefKey(transferSets.get(0).getTaskDefKey());
        actinst.setName(transferSets.get(0).getNodeName());
        if (user != null) {
            actinst.setAssignee(user.getCardNo());
        }
        customHiActinstService.save(actinst);

    }

    /**
     * 抄送流程信息，需要执行的方法
     *
     * @param customComment
     */
    @Transactional(readOnly = false)
    public void saveCsAudit(CustomComment customComment) {
        //在custom_hi_taskinst中将custom_rusk_taskinst表中的数据插入进入，并标识成已完成的状态
        CustomRuskTaskinst customRuskTaskinst = customRuskTaskinstService.get(customComment.getId());

        //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
        customComment.setUserId(UserUtils.getUser().getCardNo());
        customComment.setId(null);
        //向意见中加入签名图片和审批的日期
        PersonSigns personSigns = personSignsService.getLoginId(UserUtils.getUser().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        if (personSigns != null) {
            String url = "<img style=\"width:90px;height:60px;\" src=\"" + personSigns.getSignUrl() + "\"/>";
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[已阅]" + date + "<br>");
            } else {
                customComment.setComment("[已阅]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment() + url;
            customComment.setComment(comment);
        } else {
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[已阅]" + date + "<br>");
            } else {
                customComment.setComment("[已阅]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment();
            customComment.setComment(comment);
        }
        CustomHiTaskinst customHiTaskinst = new CustomHiTaskinst();
        customHiTaskinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiTaskinst.setName(customRuskTaskinst.getName());
        customHiTaskinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiTaskinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        customRuskTaskinst.setStartTime(customRuskTaskinst.getStartTime());
        customHiTaskinst.setReason("completed");
        customHiTaskinst.setStartTime(customRuskTaskinst.getStartTime());
        customHiTaskinst.setComment(customComment.getComment());
        List<CustomHiTaskinst> customHiTaskinsts = customHiTaskinstService.findList(customHiTaskinst);
        if (customHiTaskinsts != null && customHiTaskinsts.size() > 0) {
            customHiTaskinstService.save(customHiTaskinsts.get(0));
        } else {
            customHiTaskinst.setEndTime(new Date());
            customHiTaskinstService.save(customHiTaskinst);
        }
        //向custom_hi_actinst中将当前执行人同意之后时的end_time的值设置成为“当前审批通过的时间”
        CustomHiActinst customHiActinst = new CustomHiActinst();
        customHiActinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiActinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiActinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        List<CustomHiActinst> customHiActinsts = customHiActinstService.findCustomHiActinst(customHiActinst);
        if (customHiActinsts != null && customHiActinsts.size() > 0) {
            customHiActinsts.get(0).setEndTime(new Date());
            customHiActinsts.get(0).setComment(customComment.getComment());
            customHiActinstService.save(customHiActinsts.get(0));
        }
        //加入签名图片结束
        customCommentService.save(customComment);
        //将同一个实例id的 procInstId中custom_rusk_taskinst 表中的这条记录删除
        customRuskTaskinstService.delete(customRuskTaskinst);
        //抄送功能的审批，在点击“已阅”之前不可以在向下加入审批的节点。
    }

    /**
     * 审批被驳回时，需要执行的方法
     *
     * @param customComment
     */
    @Transactional(readOnly = false)
    public void saveRejectAudit(CustomComment customComment) {
        //目前的审批驳回，只适用于审批时，被驳回的操作
        CustomRuskTaskinst customRuskTaskinst = customRuskTaskinstService.get(customComment.getId());

        //在点击同意的时候，先将当前审批同意的人的意见信息保存在custonm_comment表中
        customComment.setUserId(UserUtils.getUser().getCardNo());
        customComment.setId(null);
        //向意见中加入签名图片和审批的日期
        PersonSigns personSigns = personSignsService.getLoginId(UserUtils.getUser().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        if (personSigns != null) {
            String url = "<img style=\"width:90px;height:60px;\" src=\"" + personSigns.getSignUrl() + "\"/>";
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[驳回]" + date + "<br>");
            } else {
                customComment.setComment("[驳回]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment() + url;
            customComment.setComment(comment);
        } else {
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[驳回]" + date + "<br>");
            } else {
                customComment.setComment("[驳回]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment();
            customComment.setComment(comment);
        }
        //当审批人点击“驳回”按钮的时候，
        // 首先需要将custom_ru_taskinst中当前执行的记录存入到custom_hi_actinst和custom_hi_taskinst两个表中,根据当前登陆人的节点来查询当前信息，
        //并为end_time设置时间以及意见
        CustomHiTaskinst customHiTaskinst = new CustomHiTaskinst();
        customHiTaskinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiTaskinst.setName(customRuskTaskinst.getName());
        customHiTaskinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiTaskinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        customHiTaskinst.setStartTime(customRuskTaskinst.getStartTime());
        customHiTaskinst.setReason("completed");
        customHiTaskinst.setStartTime(customRuskTaskinst.getStartTime());
        customHiTaskinst.setComment(customComment.getComment());
        List<CustomHiTaskinst> customHiTaskinsts = customHiTaskinstService.findList(customHiTaskinst);
        if (customHiTaskinsts != null && customHiTaskinsts.size() > 0) {
            customHiTaskinsts.get(0).setComment(customComment.getComment());
            customHiTaskinstService.save(customHiTaskinsts.get(0));
        } else {
            customHiTaskinst.setEndTime(new Date());
            customHiTaskinstService.save(customHiTaskinst);
        }
        CustomHiActinst customHiActinst = new CustomHiActinst();
        customHiActinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiActinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiActinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        List<CustomHiActinst> customHiActinsts = customHiActinstService.findCustomHiActinst(customHiActinst);
        if (customHiActinsts != null && customHiActinsts.size() > 0) {
            customHiActinsts.get(0).setEndTime(new Date());
            customHiActinsts.get(0).setComment(customComment.getComment());
            customHiActinstService.save(customHiActinsts.get(0));
        }
        //加入签名图片结束
        customCommentService.save(customComment);
        //同时向custom_ru_taskinst表中插入一条关于当前申请人的相关的信息，用于当驳回时，在申请人处可以查看被驳回的记录，并修改以及提交
        customRuskTaskinstService.delete(customRuskTaskinst);
        //之后，将transfer_set中的根据procInstId查询的流转信息中的flag标识设置为0，表示没有审批的流程
        TransferSet transferSet = new TransferSet();
        transferSet.setProcInstId(customComment.getProcInstId());
        List<TransferSet> list = transferSetService.findListByProcInstId(transferSet);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setFlag("0");
                transferSetService.updateFlag(list.get(i));
            }
            //向custom_ru_taskinst中加入需要当前申请人来执行的重新提交的申请的记录。
            CustomRuskTaskinst rusk = new CustomRuskTaskinst();
            rusk.setStartTime(new Date());
            rusk.setProcInstId(customComment.getProcInstId());
            rusk.setTaskDefKey("start");
            rusk.setName("申请人修改");
            rusk.setAssignee(list.get(0).getHandlePersonCard());
            rusk.setFlagAudit("0");
            //发送短信
            User user = UserUtils.get(list.get(0).getHandlePersonId());
            if (user != null) {
                String companyId = UserUtils.getUser().getCompany().getId();
                SmsModule.flowWork(companyId, user.getMobile(), user.getName(), UserUtils.getUser().getName(), "自由流程");
            }
            customRuskTaskinstService.save(rusk);
        }
        //驳回之后，将下一步的流程信息插入到custom_hi_actinst中插入下一步的信息
        TransferSet set = new TransferSet();
        set.setProcInstId(customComment.getProcInstId());
        List<TransferSet> transferSets = transferSetService.findListByProcInstId(set);
        if (transferSets != null && transferSets.size() > 0) {
            User user = UserUtils.get(transferSets.get(0).getHandlePersonId());
            //在设置完时间之后，向custom_hi_actinst中插入一条下一步人的提交的信息，用于在提交意见中显示使用
            CustomHiActinst actinst = new CustomHiActinst();
            actinst.setProcInstId(customComment.getProcInstId());
            actinst.setStartTime(new Date());
            actinst.setTaskDefKey(transferSet.getTaskDefKey());
            actinst.setName("创建");
            if (user != null) {
                actinst.setAssignee(user.getCardNo());
            }
            customHiActinstService.save(actinst);
        }
    }


    /**
     * 销毁申请
     *
     * @param customComment
     */
    @Transactional(readOnly = false)
    public String destoryAudit(CustomComment customComment) {
        //销毁申请
        //销毁申请首先将custom_ru_taskinst中的当前的实例的id有关的任务全部删除
        CustomRuskTaskinst customRuskTaskinst = new CustomRuskTaskinst();
        customRuskTaskinst.setProcInstId(customComment.getProcInstId());
        List<CustomRuskTaskinst> ruskTaskinsts = customRuskTaskinstService.findByProInstId(customRuskTaskinst);
        //非空判断
        Integer rusk = 0;
        if (ruskTaskinsts != null && ruskTaskinsts.size() > 0) {
            rusk = customRuskTaskinstService.deleteByProcInstId(customComment.getProcInstId());
            //加入提交的意见信息
            //向意见中加入签名图片和审批的日期
            PersonSigns personSigns = personSignsService.getLoginId(UserUtils.getUser().getId());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间
            if (personSigns != null) {
                String url = "<img style=\"width:90px;height:60px;\" src=\"" + personSigns.getSignUrl() + "\"/>";
                if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                    customComment.setComment("[销毁]" + date + "<br>");
                } else {
                    customComment.setComment("[销毁]" + customComment.getComment() + "<br>" + date + "<br>");
                }
                String comment = customComment.getComment() + url;
                customComment.setComment(comment);
            }
            //在销毁申请的时候,需要在custom_hi_actinst中的这条记录的提交意见保存进去。
            if (StringUtils.isNotBlank(customComment.getUserId())) {
                CustomHiActinst customHiActinst = customHiActinstService.get(customComment.getUserId());
                customHiActinst.setEndTime(new Date());
                customHiActinst.setComment(customComment.getComment());
                customHiActinstService.save(customHiActinst);
            }
            //之后在custom_hi_actinst中加入一条结束的语句即可。
            CustomHiActinst customHiActinst = new CustomHiActinst();
            customHiActinst.setName("结束");
            customHiActinst.setStartTime(new Date());
            customHiActinst.setEndTime(new Date());
            customHiActinstService.save(customHiActinst);
        }
        if ("null".equals(String.valueOf(rusk)) || "0".equals(String.valueOf(rusk))) {
            return "error";
        } else {
            return "success";
        }
    }

    /**
     * 撤回自由流程
     *
     * @param customProcess
     * @return
     */
    @Transactional(readOnly = false)
    public String deleteCustomProcess(CustomProcess customProcess) {
        String message = "";
        TransferSet transferSet = new TransferSet();
        if (customProcess != null) {
            transferSet.setProcInstId(customProcess.getProcInstId());
        }
        //判断流程是否已经撤回 ，如果撤回则提示已经撤回不需要在撤回
        CustomProcess process = new CustomProcess();
        process.setProcInstId(customProcess.getProcInstId());
        List<CustomProcess> customProcessList = dao.findList(process);
        if (customProcessList != null && customProcessList.size() > 0 && customProcessList.get(0).getRecall() != null) {
            message = "流程已经撤回";
        } else {
            List<TransferSet> transferSets = transferSetService.findListByProcInstId(transferSet);
            if (transferSets != null && transferSets.size() > 0) {
                for (int i = 0; i < transferSets.size(); i++) {
                    if (StringUtils.equalsIgnoreCase(transferSets.get(i).getTaskDefKey(), "apply_end") && StringUtils.equalsIgnoreCase(transferSets.get(i).getFlag(), "1")) {
                        //流程已经结束，不允许删除
                        message = "流程已经结束,不允许删除";
                        break;
                    } else if (StringUtils.equalsIgnoreCase(transferSets.get(i).getTaskDefKey(), "apply_end") && StringUtils.equalsIgnoreCase(transferSets.get(i).getFlag(), "0")) {
                        //流程处于归档状态，不允许删除
                        message = "流程处于归档状态，不允许删除";
                        break;
                    } else if (!StringUtils.equalsIgnoreCase(transferSets.get(i).getTaskDefKey(), "apply_end") && StringUtils.equalsIgnoreCase(transferSets.get(i).getFlag(), "0")) {
                        //删除未结束的流程成功
                        //先删除 act_ru_taskinst表中这个实例id的所有记录全部删除
                        //将custom_process 表中的recall字段  设置为”已撤回“
                        Integer count = customRuskTaskinstService.deleteByProcInstId(customProcess.getProcInstId());
                        if (!("0".equals(String.valueOf(count)))) {
                            customProcess.setRecall("已撤回");
                            dao.delete(customProcess);
                            //在流转信息中显示“结束”的字样
                            CustomHiActinst customHiActinst = new CustomHiActinst();
                            customHiActinst.setName("结束");
                            customHiActinst.setProcInstId(customProcess.getProcInstId());
                            List<CustomHiActinst> customHiActinsts = customHiActinstService.findAllList(customHiActinst);
                            if (customHiActinsts == null || customHiActinsts.size() == 0) {
                                CustomHiActinst actinst = new CustomHiActinst();
                                actinst.setStartTime(new Date());
                                actinst.setEndTime(new Date());
                                actinst.setAssignee("33");
                                actinst.setName("结束");
                                actinst.setProcInstId(customProcess.getProcInstId());
                                customHiActinstService.save(actinst);
                            }
                            message = "未结束的流程撤回成功";
                            break;
                        }
                    }
                }
            }
        }


        return message;
    }

    /**
     * 添加意见，统一给意见添加个人签名和日期等信息
     *
     * @param customComment 意见
     * @return
     */
    public CustomComment addCustomComment(CustomComment customComment) {
        //向意见中加入签名图片和审批的日期
        PersonSigns personSigns = personSignsService.getLoginId(UserUtils.getUser().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        if (personSigns != null) {
            String url = "<img style=\"width:90px;height:60px;\" src=\"" + personSigns.getSignUrl() + "\"/>";
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[同意]" + date + "<br>");
            } else {
                customComment.setComment("[同意]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment() + url;
            customComment.setComment(comment);
        } else {
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[同意]" + date + "<br>");
            } else {
                customComment.setComment("[同意]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment();
            customComment.setComment(comment);
        }
        return customComment;
    }

    /**
     * 添加意见，统一给驳回意见添加个人签名和日期等信息
     *
     * @param customComment 意见
     * @return
     */
    public CustomComment addNoCustomComment(CustomComment customComment) {
        //向意见中加入签名图片和审批的日期
        PersonSigns personSigns = personSignsService.getLoginId(UserUtils.getUser().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        if (personSigns != null) {
            String url = "<img style=\"width:90px;height:60px;\" src=\"" + personSigns.getSignUrl() + "\"/>";
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[不同意]" + date + "<br>");
            } else {
                customComment.setComment("[不同意]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment() + url;
            customComment.setComment(comment);
        } else {
            if (StringUtils.isEmpty(customComment.getComment()) || customComment.getComment() == "") {
                customComment.setComment("[不同意]" + date + "<br>");
            } else {
                customComment.setComment("[不同意]" + customComment.getComment() + "<br>" + date + "<br>");
            }
            String comment = customComment.getComment();
            customComment.setComment(comment);
        }
        return customComment;
    }


    /**
     * 会签的时候多人以及一人审批需要执行的表的操作
     *
     * @param customComment
     * @param customRuskTaskinst
     */
    public void applyAudit(CustomComment customComment, CustomRuskTaskinst customRuskTaskinst, CustomComment addComment) {
        //向custom_hi_actinst中将当前执行人同意之后时的end_time的值设置成为“当前审批通过的时间”
        CustomHiActinst customHiActinst = new CustomHiActinst();
        customHiActinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiActinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiActinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        List<CustomHiActinst> customHiActinsts = customHiActinstService.findCustomHiActinst(customHiActinst);
        if (customHiActinsts != null && customHiActinsts.size() > 0) {
            customHiActinsts.get(0).setEndTime(new Date());
            customHiActinsts.get(0).setComment(addComment.getComment());
            customHiActinstService.save(customHiActinsts.get(0));
        }
        //更新意见
        CustomHiTaskinst customHiTaskinst = new CustomHiTaskinst();
        customHiTaskinst.setAssignee(customRuskTaskinst.getAssignee());
        customHiTaskinst.setName(customRuskTaskinst.getName());
        customHiTaskinst.setProcInstId(customRuskTaskinst.getProcInstId());
        customHiTaskinst.setTaskDefKey(customRuskTaskinst.getTaskDefKey());
        customHiTaskinst.setReason("completed");
        customHiTaskinst.setStartTime(customRuskTaskinst.getStartTime());
        customHiTaskinst.setComment(addComment.getComment());
        List<CustomHiTaskinst> customHiTaskinsts = customHiTaskinstService.findList(customHiTaskinst);
        if (customHiTaskinsts != null && customHiTaskinsts.size() > 0) {
            customHiTaskinsts.get(0).setComment(addComment.getComment());
            customHiTaskinstService.save(customHiTaskinsts.get(0));
        } else {
            customHiTaskinst.setEndTime(new Date());
            customHiTaskinstService.save(customHiTaskinst);
        }
        //加入签名图片结束
        customCommentService.save(addComment);
    }


    /**
     * 设置下一步的审批人的信息
     *
     * @param customComment
     */
    public void setAudit(CustomComment customComment, List<TransferSet> transferSets, TransferSet transferSet) {
        String companyId = UserUtils.getUser().getCompany().getId();
        if (transferSets != null && transferSets.size() > 0) {
            if (transferSet.getTaskDefKey().startsWith("apply_audit") || transferSet.getTaskDefKey().startsWith("apply_no_audit") || transferSet.getTaskDefKey().startsWith("apply_cs")) {
                String[] name = transferSet.getHandlePersonId().split(",");
                for (int j = 0; j < name.length; j++) {
                    CustomRuskTaskinst taskinst1 = new CustomRuskTaskinst();
                    taskinst1.setProcInstId(customComment.getProcInstId());
                    taskinst1.setTaskDefKey(transferSet.getTaskDefKey());
                    taskinst1.setName(transferSet.getNodeName());
                    taskinst1.setStartTime(new Date());
                    User user = UserUtils.get(name[j]);
                    if (user != null) {
                        taskinst1.setAssignee(user.getCardNo());
                        taskinst1.setFlagAudit("0");
                        //发送短信
                        SmsModule.flowWork(companyId, user.getMobile(), user.getName(), UserUtils.getUser().getName(), "自由流程");
                        customRuskTaskinstService.save(taskinst1);
                    }
                    if (transferSet.getTaskDefKey().startsWith("apply_cs") || transferSet.getTaskDefKey().startsWith("apply_no_audit")) {
                        //在设置完时间之后，向custom_hi_actinst中插入一条下一步人的提交的信息，用于在提交意见中显示使用
                        CustomHiActinst actinst = new CustomHiActinst();
                        actinst.setProcInstId(customComment.getProcInstId());
                        actinst.setStartTime(new Date());
                        actinst.setTaskDefKey(transferSet.getTaskDefKey());
                        actinst.setName(transferSet.getNodeName());
                        if (user != null) {
                            actinst.setAssignee(user.getCardNo());
                        }
                        customHiActinstService.save(actinst);
                    }
                }
            } else if (transferSet.getTaskDefKey().startsWith("audit") || transferSet.getTaskDefKey().startsWith("apply_end")) {
                CustomRuskTaskinst task = new CustomRuskTaskinst();
                task.setProcInstId(customComment.getProcInstId());
                User user = UserUtils.get(transferSet.getHandlePersonId());
                if (user != null) {
                    task.setAssignee(user.getCardNo());
                    //发送短信
                    SmsModule.flowWork(companyId, user.getMobile(), user.getName(), UserUtils.getUser().getName(), "自由流程");
                }
                task.setTaskDefKey(transferSet.getTaskDefKey());
                task.setName(transferSet.getNodeName());
                task.setStartTime(new Date());
                task.setFlagAudit("0");
                customRuskTaskinstService.save(task);

                //在设置完时间之后，向custom_hi_actinst中插入一条下一步人的提交的信息，用于在提交意见中显示使用
                CustomHiActinst actinst = new CustomHiActinst();
                actinst.setProcInstId(customComment.getProcInstId());
                actinst.setStartTime(new Date());
                actinst.setTaskDefKey(transferSet.getTaskDefKey());
                actinst.setName(transferSet.getNodeName());
                if (user != null) {
                    actinst.setAssignee(user.getCardNo());
                }
                customHiActinstService.save(actinst);
            }
        }
    }
}