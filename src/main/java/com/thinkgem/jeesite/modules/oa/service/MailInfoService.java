package com.thinkgem.jeesite.modules.oa.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.oa.dao.MailInfoDao;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

/**
 * 邮件信息Service
 * @author oa
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class MailInfoService extends CrudService<MailInfoDao, MailInfo> {

    @Autowired
    private MailInfoDao mailInfoDao;

	public MailInfo get(String id) {return super.get(id);
	}
	
	public List<MailInfo> findList(MailInfo mailInfo) {return super.findList(mailInfo);	}
//    public List<MailInfo> findList1(MailInfo mailInfo) {return dao.findList1(mailInfo);	}
	
	public Page<MailInfo> findPage(Page<MailInfo> page, MailInfo mailInfo) {
		return super.findPage(page, mailInfo);
	}

    /**
     * 上拉加载更多邮件
     * @param page
     * @param mailInfo
     * @param list
     * @return
     */
    public List<Map> findTouchPage(Page<MailInfo> page, MailInfo mailInfo,List<Map> list) {
        page=super.findPage(page, mailInfo);
        for(MailInfo each:page.getList()){
            each.setTheme(StringUtils.abbr(each.getTheme(),50));
        }
        List<MailInfo> data = page.getList();
        Map<String,String> dataMap=new HashMap<String,String>();
        if(data.size()>0){
        for(int i=0; i<data.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String dataTime = sdf.format(data.get(i).getTime());
            dataMap.put("dataTime", dataTime);
            dataMap.put("id", data.get(i).getId());
            dataMap.put("name", data.get(i).getName());
            dataMap.put("theme", data.get(i).getTheme());
            dataMap.put("state", mailInfo.getState());
            dataMap.put("readMark", data.get(i).getReadMark());
            dataMap.put("flag","mail");
            list.add(dataMap);
        }}
        return list;
    }
//    public Page<MailInfo> findPage1(Page<MailInfo> page, MailInfo mailInfo,MailInfo entity) {
//        entity.setPage(page);
//        page.setList(dao.findList(entity));
//        return page;
//    }

    public Page<User> findPage1(Page<User> page, User user) {
        user.setPage(page);
        page.setList(mailInfoDao.getPhone(user));
        return page;
    }

//发送内部邮件
	@Transactional(readOnly = false)
	public void send(MailInfo mailInfo) {

        String receiverIds=mailInfo.getReceiverId();
        String id=mailInfo.getId();
        if (mailInfo.getContent()!=null){
            mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                    mailInfo.getContent()));
        }
        String receiverStr = mailInfo.getReceiverId() ;
        if(StringUtils.isNotBlank(mailInfo.getCcId())){
            receiverStr += "," + mailInfo.getCcId();
        }
        String[] receivers = receiverStr.split(",");


        StringBuilder sb=new StringBuilder();
        if(receivers!=null) {
            for(int i=0;i<receivers.length;i++){
                User user=mailInfoDao.getById(receivers[i]);
                sb.append(user.getName()+",");
            }
            String receiverName = sb.toString().substring(0,sb.toString().length()-1);
            mailInfo.setAllId(receiverName);
        }


        //设置收件人
        for(String receiver : receivers){
            mailInfo.setAllReceiver(receiverIds);
            mailInfo.setReceiverId(receiver);
            mailInfo.setOwnId(receiver);
            mailInfo.setReadMark("0");
            mailInfo.setState("INBOX");
            mailInfo.setId(null);
            super.save(mailInfo);
        }


        //设置发件人

        mailInfo.setAllReceiver(receiverIds);
        mailInfo.setOwnId(mailInfo.getSenderId());
        mailInfo.setReadMark("1");
        mailInfo.setState("SENT");
            if(id!=null){
                mailInfo.setId(id);
            }else {
                mailInfo.setId(null);
            }
            super.save(mailInfo);
//        for(String  receiver : receivers){
//            mailInfo.setAllReceiver(receiverIds);
//            mailInfo.setReceiverId(receiver);
//            mailInfo.setOwnId(mailInfo.getSenderId());
//            mailInfo.setReadMark("1");
//            mailInfo.setState("SENT");
//            if(id!=null){
//                mailInfo.setId(id);
//            }else {
//                mailInfo.setId(null);
//            }
//            super.save(mailInfo);
//        }
	}




    /**
     * 查看邮件的详细信息
     * @param id
     * @return
     */
    public MailInfo getMail(String id) {
        MailInfo mailInfo= mailInfoDao.getMail(id);
        StringBuilder sb=new StringBuilder();
        StringBuilder cc=new StringBuilder();
        StringBuilder out=new StringBuilder();
        if(mailInfo.getAllReceiver()!=null) {
            String ids[] = mailInfo.getAllReceiver().split(",");
            for(int i=0;i<ids.length;i++){
                User user=mailInfoDao.getById(ids[i]);
                if (user != null) {
                    sb.append(user.getName() + ",");
                }
            }
            String receiverName = sb.toString().substring(0,sb.toString().length()-1);
            mailInfo.setReceiverNames(receiverName);
        }
        if(mailInfo.getCcId()!=null && StringUtils.isNotEmpty(mailInfo.getCcId())){
            String ids[]=mailInfo.getCcId().split(",");
            for(int i=0;i<ids.length;i++){
                User user=mailInfoDao.getById(ids[i]);
                if (user != null) {
                    cc.append(user.getName() + ",");
                }
            }
            String ccName = cc.toString().substring(0,cc.toString().length()-1);
            mailInfo.setCcNames(ccName);
        }
        if(mailInfo.getOutSide()!=null)
        {

            mailInfo.setOutSide(mailInfo.getOutSide());
        }



        return mailInfo;
    }

    /**
     * 附件上传
     * @param file
     * @param name
     * @param signName
     * @param request
     * @return
     * @throws IOException
     */
    @Transactional(readOnly = false)
    public String saveContractFile(MultipartFile file, String name,String signName, HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        String filePath = null ;
        String realPath = request.getSession().getServletContext().getRealPath("/static"+ File.separator+"userfiles");
        System.out.println(file.getOriginalFilename());
        String filename=file.getOriginalFilename();
//        filename=filename.substring(filename.lastIndexOf("."));
        System.out.println(filename);
        File targetFile = new File(realPath+File.separator+"contract") ;
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        File desFile = new File(realPath+File.separator+"contract"+File.separator+filename) ;
        if (!desFile.exists()){
            desFile.createNewFile() ;
        }
        filePath = desFile.getPath();
        System.out.println("路径："+filePath);
        filePath = filePath.substring(filePath.indexOf("webapp")+6);
        FileOutputStream fileOutputStream = new FileOutputStream(desFile) ;
        String s = filePath.replaceAll("\\\\", "\\/");
        byte[] bytes = new byte[1024] ;
        while(!(inputStream.read(bytes)== -1)){
            fileOutputStream.write(bytes);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
        //文件保存成功
        return s.substring(s.lastIndexOf("static") - 1);
    }


    //保存草稿

    @Transactional(readOnly = false)
    public void saveDrafts(MailInfo mailInfo) {
        if (mailInfo.getContent()!=null){
            mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                    mailInfo.getContent()));
        }
        String str="";
        String str1="";
        if(StringUtils.isNotBlank(mailInfo.getOutSide()))
        {
             str1= str1+mailInfo.getOutSide();
        }
        if(StringUtils.isNotBlank(mailInfo.getReceiverId()) )
        {
            str= str+mailInfo.getReceiverId()+",";
        }
        if(StringUtils.isNotBlank(mailInfo.getCcId()))
        {
            str= str+mailInfo.getCcId()+",";
        }
        mailInfo.setAllReceiver(str);
        StringBuilder sb=new StringBuilder();



        if(StringUtils.isNotBlank(str1)){
            mailInfo.setAllId(str1);
        }

        if(StringUtils.isNotBlank(str)&&StringUtils.isNotBlank(str1)) {
            String ids[]=str.split(",");
            for(int i=0;i<ids.length;i++){
                User user=mailInfoDao.getById(ids[i]);
                sb.append(user.getName()+",");
            }

            String receiverName= sb.toString().substring(0,sb.toString().length()-1);
            mailInfo.setAllId(str1+","+receiverName);
        }
        if(StringUtils.isNotBlank(str)&&StringUtils.isEmpty(str1)) {
            String ids[]=str.split(",");
            for(int i=0;i<ids.length;i++){
                User user=mailInfoDao.getById(ids[i]);
                sb.append(user.getName()+",");
            }

            String receiverName= sb.toString().substring(0,sb.toString().length()-1);
            mailInfo.setAllId(receiverName);
        }

        if(StringUtils.isEmpty(str) && StringUtils.isEmpty(str1)){
            mailInfo.setAllId(null);
        }

       // mailInfo.setAllId(mailInfo.getAllId());
        mailInfo.setOwnId(UserUtils.getUser().getId());
        mailInfo.setReadMark("1");
        mailInfo.setState("DRAFTS");
        super.save(mailInfo);
    }



    /**
     *草稿保存成功后，回显数据
     * @param id
     * @return
     */
    public MailInfo getDrafts(String id){
        MailInfo mailInfo=dao.get(id);
        StringBuilder sb=new StringBuilder();
        StringBuilder cc= new StringBuilder();
        StringBuilder out=new StringBuilder();
        if(mailInfo.getReceiverId()!=null && StringUtils.isNotBlank(mailInfo.getReceiverId())) {
            String ids[]=mailInfo.getReceiverId().split(",");
            for(int i=0;i<ids.length;i++){
                User user=mailInfoDao.getById(ids[i]);
                sb.append(user.getName()+",");
            }
            String receiverName1 = sb.toString().substring(0,sb.toString().length()-1);
            mailInfo.setReceiverNames(receiverName1);
        }
        if(mailInfo.getCcId()!=null && StringUtils.isNotBlank(mailInfo.getCcId())){
            String ids[]=mailInfo.getCcId().split(",");
            for(int i=0;i<ids.length;i++){
                User user=mailInfoDao.getById(ids[i]);
                cc.append(user.getName()+",");
            }
            String receiverName2 = cc.toString().substring(0,cc.toString().length()-1);
            mailInfo.setCcNames(receiverName2);
        }
        if(mailInfo.getOutSide()!=null && StringUtils.isNotBlank(mailInfo.getOutSide())){

            mailInfo.setOutSide(mailInfo.getOutSide());
        }
        return mailInfo;
    }





    @Transactional(readOnly = false)
    public void delete(MailInfo mailInfo) {
        super.delete(mailInfo);
    }

    /**
     * 修改已读标志
     * @param ids
     * @param readMark
     */
    @Transactional(readOnly = false)
    public void readMark(String ids, String readMark){
        String[] idArr = ids.split(",");
        MailInfo mail = null;
        for(int i = 0; i < idArr.length; i++){
            mail = super.get(idArr[i]);
            mail.setReadMark(readMark);
            super.save(mail);
        }
    }

    @Transactional(readOnly = false)
    public void allRead(String ownId){
        dao.allRead(ownId);
    }



    /**
     *联系人选中，点击写信，进入到写信的界面
     * @param id
     * @return
     */
    public MailInfo getWrite(String id){
        if (id.indexOf("on,") >= 0){
            id =  StringUtils.replace(id,"on,","");
            System.out.println(id);
        }

        MailInfo mailInfo =new MailInfo();
        StringBuilder sb=new StringBuilder();
        if(id!=null && StringUtils.isNoneBlank(id)) {
            String ids[]=id.split(",");
            for(int i=0;i<ids.length;i++){
                User user=mailInfoDao.getById(ids[i]);
                if(user!=null){
                    sb.append(user.getName()+",");
                }
            }
            if(sb.toString().length()>1){
                String receiverName = sb.toString().substring(0,sb.toString().length()-1);
                mailInfo.setReceiverNames(receiverName);
            }

        }
        return mailInfo;
    }


}
