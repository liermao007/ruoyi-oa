package com.thinkgem.jeesite.modules.tgb.SpringActivemq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.dao.AmqMsgDao;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.util.UUID;

/**
 * 发送消息Service
 * @author oa
 * @version 2017-05-27
 */
@Service
@Transactional(readOnly = false)
public class MyAmqService extends BaseService {

    @Autowired
    private AmqMsgDao amqMsgDao;


    @Transactional(readOnly = false)
    public void sendMsg(User user, String flag) {
        ObjectMapper mapper = new ObjectMapper();
        String json=null;
        try {
            flag = flag.replace("=", "");//xitong = "caiwu=",去掉=号
            json = mapper.writeValueAsString(user);
            String id= UUID.randomUUID().toString();
            System.out.println("json:" + json);
            //存消息体
            MSG msg=new MSG();
            msg.setId(id);
            msg.setType("topic");
            msg.setDestination(flag);
            msg.setBody(json.getBytes());
            amqMsgDao.saveMessage(msg);//在消息体表中存入一条数据，为了发生异常时候根据ID进行处理：数据源配置在applicationContext.xml中
//            topicSender.sendOther(flag, json);//发送一条消息到指定的队列（目标）：des = "caiwu"
        } catch (Exception e) {//send()方法抛异常
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void sendOaSchedule(OaSchedule oaSchedule, String flag) {
        ObjectMapper mapper = new ObjectMapper();
        String json=null;
        try {
            flag = flag.replace("=", "");//xitong = "caiwu=",去掉=号
            json = mapper.writeValueAsString(oaSchedule);
            String id= UUID.randomUUID().toString();
            System.out.println("json:" + json);
            //存消息体
            MSG msg=new MSG();
            msg.setId(id);
            msg.setType("topic");
            msg.setDestination(flag);
            msg.setBody(json.getBytes());
            amqMsgDao.saveMessage(msg);//在消息体表中存入一条数据，为了发生异常时候根据ID进行处理：数据源配置在applicationContext.xml中
//            topicSender.sendOther(flag, json);//发送一条消息到指定的队列（目标）：des = "caiwu"
        } catch (Exception e) {//send()方法抛异常
            e.printStackTrace();
        }
    }


}
