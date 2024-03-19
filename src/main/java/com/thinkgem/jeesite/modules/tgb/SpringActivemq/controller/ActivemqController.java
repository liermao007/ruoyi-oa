package com.thinkgem.jeesite.modules.tgb.SpringActivemq.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.Order;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.service.MyAmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @author oa
 * @description controller测试
 */

@Controller
@RequestMapping("${adminPath}/my/myAmq")
public class ActivemqController {


    @Autowired
    private MyAmqService myAmqService;

//    @ResponseBody
    @RequestMapping(value = "mySendMsg",method = RequestMethod.GET)
    public void mySendMsg(User order, Model model, HttpServletResponse response,String flag) {
        flag="topic_cs_oa";
        order.setId("734ufrej");
        order.setCardNo("7HSAE7GDE6");
        order.setName("name is cs");
        myAmqService.sendMsg(order, flag);
    }
	@ResponseBody
	@RequestMapping(value = "sendMsg",method = RequestMethod.GET)
	public void sendMsg(Order order, Model model, HttpServletResponse response,String flag) {
//    public void sendMsg(Model model, HttpServletResponse response,String flag) {
		ObjectMapper mapper = new ObjectMapper();
		String json=null;
		flag="caiwu=";
		order.setAddress("地址");
		order.setDingdan_id("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		order.setName("name is cs");
		order.setNum(1);
		order.setShangpin_id("666666666");
		order.setShangpin_money(56);
		order.setShangpin_name("电源");
		order.setState("0");
		order.setUserid("7hftit7yr76fuyg");
		try {
			flag = flag.replace("=", "");//xitong = "caiwu=",去掉=号
			json = mapper.writeValueAsString(order);
			System.out.println("json:"+json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
//        try {
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().print(FreemarkerUtils.process("<tr>...", new HashMap()));
//            response.getWriter().flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	}
	/**
	 * 发送消息到队列
	 * Queue队列：仅有一个订阅者会收到消息，消息一旦被处理就不会存在队列中
	 * @param message
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("queueSender")
	public String queueSender(@RequestParam("message")String message){
		String opt="";
		try {
			opt = "suc";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
	/**
	 * 发送消息到主题
	 * Topic主题 ：放入一个消息，所有订阅者都会收到 
	 * 这个是主题目的地是一对多的
	 * @param message
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("topicSender")
	public String topicSender(@RequestParam("message")String message){
		String opt = "";
		try {
			//topicSender.send("test.topic", message);
			opt = "suc";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
}
