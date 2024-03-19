package com.thinkgem.jeesite.modules.test.testmsm;


import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 短信发送测试
 */
public class SmsModule {


    /**
     * 待办日程发送短信提醒：待办日程10分钟以前
     *  模板内容：{姓名}您好，您{时间}有待办日程：{日程内容(自定义内容)}。
     * @param Mobile 手机号
     * @param name 手机号对应用户姓名
     * @param time 待办工作的时间
     * @param content 待办工作的标题
     * @return 00发送成功  09无效号码   其它系统发生错误
     */
    public static String oaScheduleWork(String companyId,String Mobile, String name,String time,String content) {
        return SendSms.sendSmsO(companyId,Mobile, name + "您好，您" + time + "有待办日程：" + content + "。");
    }
    /**
     * 待办日程发送短信提醒：待办日程10分钟以前
     *  模板内容：{姓名1}您好，您有待办流程：{姓名2}发起了{流程名称}，请您尽快处理。
     * @param Mobile 手机号
     * @param name1 手机号对应用户姓名
     * @param name2 发起流程人的姓名
     * @param content 发起的流程名称
     * @return 00发送成功  09无效号码   其它系统发生错误
     */
    public static String flowWork(String companyId,String Mobile, String name1,String name2,String content) {
        return SendSms.sendSmsO(companyId,Mobile, name1 + "您好，" + name2 + "发起了" + content + "，请您尽快处理。立刻审批请点击：http://oa.chinahealth-groups.com/a/login");
    }

    /**
     * 通知：通知面向大众时立即发送短信
     *  模板内容：通知（或者：紧急通知）：请在{时间}参加{会议名称，或者在哪个手术室的手术}
     * @param Mobile 手机号
     * @param time 通知时间
     * @param content 发起的流程名称
     * @return 00发送成功  09无效号码   其它系统发生错误
     */
    public static String notifyWork(String companyId,String Mobile, String time,String content) {
        return SendSms.sendSmsO(companyId,Mobile, "通知：请在" +time + "参加" + content);
    }

    /**
     * 通知：通知面向大众时立即发送短信
     *  模板内容：通知（或者：紧急通知）：请在{时间}参加{会议名称，或者在哪个手术室的手术}
     * @param Mobile 手机号
     * @param time 紧急通知时间
     * @param content 发起的流程名称
     * @return 00发送成功  09无效号码   其它系统发生错误
     */
    public static String notifyFastWork(String companyId,String Mobile, String time,String content) {
        return SendSms.sendSmsO(companyId,Mobile, "紧急通知：请在" +time + "参加" + content);
    }


    /**
     * 动态登录密码发送
     *你的动态密码为：[动态密码]，您正在登录执业医师联盟平台，如非本人操作，请联系我们。有效期十分钟，感谢您的使用！
     * @param Mobile 手机号
     * @param code   验证码
     * @return 00发送成功  09无效号码   其它系统发生错误
     */
    public static String dypassword(String companyId,String Mobile, String code,String servicePhone) {
        //设置客服电话
        if(StringUtils.isEmpty(servicePhone)){
            servicePhone = "";
        }else
        {
            servicePhone = "("+servicePhone+")";
        }
        return SendSms.sendSmsO(companyId,Mobile, "你的动态密码为：" + code + "，您正在登录执业医师联盟平台，如非本人操作，请联系我们" + servicePhone
                + "。有效期十分钟，感谢您的使用!");
    }
   /* *//**
     * 验证码发送
     *
     * @param Mobile 手机号
     * @param code   验证码
     * @return 00发送成功  09无效号码   其它系统发生错误
     *//*
    public static String scode(String Mobile, String code) {
        return SendSms.sendSmsO(Mobile, "验证码：" + code + "，有效期十分钟，感谢您的使用！");
    }

    *//**
     * 预约短信发送
     *
     * @param Mobile 手机号
     * @param code   预约编号  name预约人姓名 dname 医师姓名   date预约时间   adds 地址
     * @return 00发送成功  09无效号码   其它系统发生错误
     *//*
    public static String yuyue(String Mobile, String code, String name, String dname, String date, String adds) {
        return SendSms.sendSmsO(Mobile, "预约编号:" + code + "，" + name + "您已成功预约" + dname + "医师" + date + "诊疗服务，" + adds + "， 请按时前往就诊。");
    }

    *//**
     * 挂靠短信发送
     *
     * @param Mobile 手机号
     * @param name   医师姓名  hname 机构名称 kname 科室名称
     * @return 00发送成功  09无效号码   其它系统发生错误
     *//*
    public static String qianyue(String Mobile, String name, String hname, String kname) {
        return SendSms.sendSmsO(Mobile, name + "您申请的挂靠" + hname + kname + "室已经成功,即刻可以开始通过系统进行执业日程发布、接诊等工作。");
    }

    *//**
     * 日程发布
     *
     * @param Mobile 手机号
     * @param date   时间  adds 地址
     * @return 00发送成功  09无效号码   其它系统发生错误
     *//*
    public static String richeng(String Mobile, String date, String adds) {
        return SendSms.sendSmsO(Mobile, "您" + date + "发布的" + adds + "诊疗日程已经审核通过。");
    }

    *//*public static void main(String[] args){
        dypassword("18629030280","43434","111");
    }*//*
*/
}
