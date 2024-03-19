/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.dao.OaNewsRecordDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNewsRecord;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.dao.AmqMsgDao;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.MSG;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.dao.OaNewsDao;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

/**
 * 新闻公告Service
 * @author oa
 * @version 2016-11-17
 */
@Service
@Transactional(readOnly = true)
public class OaNewsService extends CrudService<OaNewsDao, OaNews> {

    @Autowired
    private OaNewsRecordDao oaNewsRecordDao;

    @Autowired
    private OaNewsDao oaNewsDao;


    @Autowired
    private AmqMsgDao amqMsgDao;

    public OaNews get(String id) {
        OaNews entity = dao.get(id);
        return entity;
    }
	
	public List<OaNews> findList(OaNews oaNews) {
		return super.findList(oaNews);
	}
	
	/*public Page<OaNews> findPage(Page<OaNews> page, OaNews oaNews)
    {
		return super.findPage(page, oaNews);
	}*/

  /*  public Page<OaNews> getRecordList(Page<OaNews> page, OaNews oaNews) {
        oaNews.setOaNewsRecordList(oaNewsRecordDao.findList(new OaNewsRecord(oaNews)));
        return super.findPage(page, oaNews);
    }*/

    public OaNews getRecordList(OaNews oaNews) {
        oaNews.setOaNewsRecordList(oaNewsRecordDao.findList(new OaNewsRecord(oaNews)));
        return oaNews;
    }

    public Page<OaNews> find(Page<OaNews> page, OaNews oaNews) {
        oaNews.setPage(page);
        page.setList(dao.findList(oaNews));
        return page;
    }


    @Transactional(readOnly = false)
	public void save(OaNews oaNews) {
        if (oaNews.getContent()!=null){
            oaNews.setContent(StringEscapeUtils.unescapeHtml4(
                    oaNews.getContent()));
        }
		super.save(oaNews);
        // 更新发送接受人记录
        //oaNewsRecordDao.deleteByOaNewsId(oaNews.getId());

        if(StringUtils.equalsIgnoreCase(oaNews.getAuditFlag(),"0")){
            try {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject=addJsonValue(oaNews);
                jsonArray.add(jsonObject);
                String id = UUID.randomUUID().toString();
                String json = jsonArray.toString();
                MSG msg = new MSG();
                msg.setId(id);
                msg.setType("topic");
                msg.setDestination("save_oaNews");
                msg.setBody(json.getBytes());
//                amqMsgDao.saveMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        OaNewsRecord oaNewsRecord= new OaNewsRecord();
        oaNewsRecord.setOaNews(oaNews);
        List<OaNewsRecord> oaNewsRecordList= oaNewsRecordDao.findList(oaNewsRecord);
      if( oaNewsRecordList.size()>0){
          oaNewsRecordDao.deleteByOaNewsId(oaNews.getId());
      }
        if (oaNews.getOaNewsRecordList().size() > 0){
            oaNewsRecordDao.insertAll(oaNews.getOaNewsRecordList());
        }
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
    /**
     * 将oaNews中的需要传递到其他系统的数据放到jsonObject中
     * @param oaNews
     * @return
     */
    public JSONObject addJsonValue(OaNews oaNews){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", oaNews.getId());
        jsonObject.put("title",oaNews.getTitle());
        jsonObject.put("content",oaNews.getContent());
        jsonObject.put("files",oaNews.getFiles());
        jsonObject.put("auditFlag",oaNews.getAuditFlag());
        jsonObject.put("auditMan",oaNews.getAuditMan());
        jsonObject.put("isTopic",oaNews.getIsTopic());
        jsonObject.put("companyId",oaNews.getCompanyId());
        jsonObject.put("auditManName",oaNews.getAuditManName());
        jsonObject.put("createManName", oaNews.getCreateManName());
        jsonObject.put("userId", oaNews.getUserId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateString = formatter.format(oaNews.getUpdateDate());
        String createString = formatter.format(oaNews.getCreateDate());
        jsonObject.put("updateDate",updateString);
        jsonObject.put("createDate",createString);
        return jsonObject;
    }

    @Transactional(readOnly = false)
    public void update(OaNews oaNews) {
        oaNews.preUpdate();
        dao.update(oaNews);
    }

    @Transactional(readOnly = false)
    public void delete(OaNews oaNews) {
        super.delete(oaNews);
    }


    public Page<OaNews> findMyWriteNews(Page<OaNews> page, OaNews oaNews) {
        oaNews.setPage(page);
        oaNewsDao.findMyWriteNews(oaNews);
        List<OaNews> list= oaNewsDao.findMyWriteNews(oaNews);
        page.setList(list);
        return page;
    }

    public Page<OaNews> findListByWork(Page<OaNews> page, OaNews oaNews) {
        oaNews.setPage(page);
        List<OaNews> list= oaNewsDao.findListByWork(oaNews);
        page.setList(list);
        return page;
    }

    /**
     * 查询未读新闻
     * @param oaNews
     * @return
     */
    public List<OaNews> findListByWork(OaNews oaNews) {
       return  oaNewsDao.findListByWork(oaNews);
    }

    /**
     *上拉获取更多新闻公告
     * @param page
     * @param oaNews
     * @param list
     * @return
     */
    public List<Map> findTouchNews(Page<OaNews> page, OaNews oaNews,List<Map> list) {
        oaNews.setPage(page);
        page.setList(oaNewsDao.findMyWriteNews(oaNews));
        Map<String,String> dataMap=new HashMap<String,String>();
        List<OaNews> data = page.getList();
        if(data.size()>0){
            for(int i=0; i<data.size(); i++){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String dataTime=sdf.format(data.get(i).getCreateDate());
                String auditFlag="";
                if(StringUtils.equals(data.get(i).getAuditFlag(),"0")){auditFlag="待审核";}
                else if(StringUtils.equals(data.get(i).getAuditFlag(),"1")){auditFlag="已发布";}
                else if(StringUtils.equals(data.get(i).getAuditFlag(),"1")){auditFlag="拒绝发布";}
                dataMap.put("dataTime",dataTime);
                dataMap.put("auditFlag",auditFlag);
                dataMap.put("createManName",data.get(i).getCreateManName());
                dataMap.put("auditManName",data.get(i).getAuditManName());
                dataMap.put("id",data.get(i).getId());
                dataMap.put("title",data.get(i).getTitle());
                dataMap.put("content",data.get(i).getContent());
                dataMap.put("flag","notice");
                list.add(dataMap);
            }
        }
        return list;
    }

    /**
     * 上拉获取更多新闻
     * @param page
     * @param oaNews
     * @param list
     * @return
     */
    public List<Map> getNewsMore(Page<OaNews> page, OaNews oaNews,List<Map> list) {
        page=super.findPage(page,oaNews);
        Map<String,String> dataMap=new HashMap<String,String>();
        for(OaNews each:page.getList()){
            each.setContent(StringUtils.abbr(each.getContent(), 30));
            each.setTitle(StringUtils.abbr(each.getTitle(), 25));
        }
        List<OaNews> data = page.getList();
        if(data.size()>0){
            for(int i=0; i<data.size(); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataTime = sdf.format(data.get(i).getCreateDate());
                dataMap.put("dataTime", dataTime);
                dataMap.put("auditFlag", data.get(i).getAuditFlag());
                dataMap.put("createManName", data.get(i).getCreateManName());
                dataMap.put("auditManName", data.get(i).getAuditManName());
                dataMap.put("id", data.get(i).getId());
                dataMap.put("title", data.get(i).getTitle());
                dataMap.put("content", data.get(i).getContent());
                dataMap.put("url",data.get(i).getPhoto());
                dataMap.put("flag", "news");
                list.add(dataMap);
            }
        }
        return list;
    }


    /**
     * 更新阅读状态
     */
    @Transactional(readOnly = false)
    public void updateReadFlag(OaNews oaNews) {
        OaNewsRecord OaNewsRecord = new OaNewsRecord(oaNews);
        OaNewsRecord.setUser(OaNewsRecord.getCurrentUser());
        OaNewsRecord.setReadDate(new Date());
        OaNewsRecord.setReadFlag("1");
        oaNewsRecordDao.update(OaNewsRecord);
    }



    /**
     * 更新所有置顶消息已经超过7天的置顶
     * @param oaNews
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateTopic(OaNews oaNews){
        return dao.updateTopic(oaNews);
    }
}