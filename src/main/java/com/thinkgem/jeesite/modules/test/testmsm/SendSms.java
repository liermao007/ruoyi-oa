package com.thinkgem.jeesite.modules.test.testmsm;

import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.units.InfoUtils;
import com.thinkgem.jeesite.modules.oa.web.OaScheduleController;
import com.thinkgem.jeesite.modules.sys.utils.NoticeUnits;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 短信发送测试
 */
public class SendSms {

    // 提交的url地址，这个要修改成部署的对应的接口的url
    static String URL = "http://userinterface.vcomcn.com/Opration.aspx";

    /**
     * @param args
     */
    public static void main(String[] args) {


   //  String s =SendSms.sendSmsO("13381022937", "{姓名}您申请的挂靠{机构}{科室名}室已经成功,即刻可以开始通过系统进行执业日程发布、接诊等工作。");
      // System.out.print(s);
//        System.out.print(SendSms.sendSmsS("18706888571,13571830051", "您的验证码为：123456，请在3分钟内输入。"));
    }

    /**
     * 单个号码短信发送s's
     *
     * @param Mobile  号码
     * @param Content 发送内容
     * @return 00发送成功  09无效号码   其它系统发生错误
     */
    public static  String sendSmsO(String companyId,String Mobile, String Content) {
        // 提交的数据
        String pstContent = createSubmitXml(companyId,Mobile, Content);
        return Http.post(URL, pstContent);
    }

    /**
     * 多个号码短信发送
     *
     * @param Mobiles 号码逗号分隔  不能超过50个
     * @param Content 发送内容
     * @return 00发送成功  09无效号码   其它系统发生错误
     */
    public static String sendSmsS(String companyId,String Mobiles, String Content) {
        // 提交的数据
        String pstContent = createSubmitXmlS(companyId,Mobiles, Content);
        return Http.post(URL, pstContent);
    }


    // Mobile 手机号码，群发用逗号分隔
    // Content 短信内容
    public static String createSubmitXml(String companyId,String Mobile, String Content) {
       /* Date now = new Date();
        DateFormat dtFormat = DateFormat.getDateInstance();
        String sendtime = dtFormat.format(now);
        StringBuffer sp = new StringBuffer();

       String Mobile1= UserUtils.getUser().getMobile();
        List<OaSchedule> agendas =  NoticeUnits.getOaSchedule();

        String content=null;
        for(OaSchedule agenda:agendas){
            content =agenda.getContent();
        }


        sp.append(String
                .format("<Group Login_Name=\"%s\" Login_Pwd=\"%s\" InterFaceID=\"0\" OpKind=\"0\">",
                        CommonUtil.smsUserName(), MD5(CommonUtil.smsPassword())));
        sp.append(String.format("<E_Time>%s</E_Time>", sendtime));
        sp.append("<Item>");
        sp.append("<Task>");
        sp.append("<Recive_Phone_Number>" + Mobile1 + "</Recive_Phone_Number>");
        sp.append("<Content><![CDATA[" + content + "]]></Content>");
        sp.append("<Search_ID>1</Search_ID>");
        sp.append("</Task>");
        sp.append("</Item>");
        sp.append("</Group>");
        return sp.toString();*/

        Date now = new Date();
        DateFormat dtFormat = DateFormat.getDateInstance();
        String sendtime = dtFormat.format(now);
        StringBuffer sp = new StringBuffer();
        sp.append(String
                .format("<Group Login_Name=\"%s\" Login_Pwd=\"%s\" InterFaceID=\"0\" OpKind=\"0\">",
                        CommonUtil.smsUserName(companyId), MD5(CommonUtil.smsPassword(companyId))));
        sp.append(String.format("<E_Time>%s</E_Time>", sendtime));
        sp.append("<Item>");
        sp.append("<Task>");
        sp.append("<Recive_Phone_Number>" + Mobile + "</Recive_Phone_Number>");
        sp.append("<Content><![CDATA[" + Content + "]]></Content>");
        sp.append("<Search_ID>1</Search_ID>");
        sp.append("</Task>");
        sp.append("</Item>");
        sp.append("</Group>");
        return sp.toString();
    }

    // Mobile 手机号码，群发用逗号分隔
    // Content 短信内容
    public static String createSubmitXmlS(String companyId,String Mobiles, String Content) {
        Date now = new Date();
        DateFormat dtFormat = DateFormat.getDateInstance();
        String sendtime = dtFormat.format(now);
        StringBuffer sp = new StringBuffer();
        sp.append(String
                .format("<Group Login_Name=\"%s\" Login_Pwd=\"%s\" InterFaceID=\"0\" OpKind=\"51\">",
                        CommonUtil.smsUserName(companyId), MD5(CommonUtil.smsPassword(companyId))));
        sp.append(String.format("<E_Time>%s</E_Time>", sendtime));
        sp.append("<Mobile>" + Mobiles + "</Mobile>");
        sp.append("<Content><![CDATA[" + Content + "]]></Content>");
        sp.append("<ClientID>1</ClientID>");
        sp.append("</Group>");
        return sp.toString();
    }

    public static String MD5(String encryptContent) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(encryptContent.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            result = buf.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {

        }
        return result;
    }
}
