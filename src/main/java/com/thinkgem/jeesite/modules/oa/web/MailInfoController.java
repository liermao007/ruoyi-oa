package com.thinkgem.jeesite.modules.oa.web;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.sun.mail.imap.protocol.FLAGS;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;
import com.thinkgem.jeesite.modules.oa.entity.MailAccount;
import com.thinkgem.jeesite.modules.oa.service.MailAccountService;
import com.thinkgem.jeesite.modules.oa.units.ComparatorMail;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.MSG;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.StringData;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.oa.service.MailInfoService;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import java.util.Date;

/**
 * 邮件信息Controller
 *
 * @author oa
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/mailInfo")
public class MailInfoController extends BaseController {

    @Autowired
    private MailInfoService mailInfoService;

    @Autowired
    private MailAccountService mailAccountService;

    @Autowired
    private DictService dictService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute
    public MailInfo get(@RequestParam(required = false) String id) {
        MailInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = mailInfoService.get(id);
        }
        if (entity == null) {
            entity = new MailInfo();
        }
        return entity;
    }


    /**
     * 发送邮件(内部和外部)
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "send")
    public String send(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes, int out, int in) throws Exception {

        //发送内部、外部邮件

        if (out == 1 && in == 1) {
            mailInfo.setSenderId(UserUtils.getUser().getId());
            mailInfo.setTime(new Date());
            if (!beanValidator(model, mailInfo)) {
                return info(mailInfo, model);
            }
            mailInfo.setFlag("0");
            mailInfoService.send(mailInfo);
//            addMessage(redirectAttributes, "邮件发送成功");
//            return "modules/oa/success";


            MailAccount mailAccount = new MailAccount();
            mailAccount.setLoginId(UserUtils.getUser().getId());
            List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
            if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername() != null && StringUtils.isNotEmpty(mailAccounts.get(0).getPassword())) {
                Session session = getSession(mailAccounts.get(0).getMailSend(), mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(), mailAccounts.get(0).getPort());
                if (mailInfo.getContent() != null) {
                    mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                            mailInfo.getContent().replace("\"", "")));
                }
                //发送邮件
                send(getMessage2(session, mailInfo, mailAccounts.get(0).getUsername()), session);
                //发送成功后   将发送成功的邮件保存在已发送的文件中
                int j = mailAccounts.get(0).getMailAccept().indexOf("qq");
                if (j > 0) {
                    saveSendEmail(mailAccounts.get(0).getMailAccept(), mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                            getMessage2(session, mailInfo, mailAccounts.get(0).getUsername()));
                } else {

                    return "modules/oa/wrong";

                }

                return "modules/oa/success";


            } else {
                return "modules/oa/wrong";
            }
        }


        //只发送内部邮件


        if (out == 0 && in == 1) {
           /*String dictBz= mailInfo.getOutSide();
           String  dictBz1= dictBz.substring(1,dictBz.length());
            Dict dict= new Dict();
            dict.setDescription(dictBz1);
            dict.setDelFlag("0");
           List<String>  labelList= dictService.findListDescription(dict);
            String ids ="";
            String ids1 ="";
           for(String loginName : labelList){

               User user=UserUtils.getByLoginName(loginName);
              String id= user.getId();
              String name = user.getName();
               ids += id+",";
               ids1 = ids.substring(0, ids.length()-1);
           }
            mailInfo.setReceiverId(ids1);*/
            mailInfo.setSenderId(UserUtils.getUser().getId());
            mailInfo.setTime(new Date());

            mailInfo.setFlag("0");
            mailInfoService.send(mailInfo);
            return "modules/oa/success";

        }


        //只发送外部邮件

        if (out == 1 && in == 0) {
            MailAccount mailAccount = new MailAccount();
            mailAccount.setLoginId(UserUtils.getUser().getId());
            List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
            if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername() != null && StringUtils.isNotEmpty(mailAccounts.get(0).getPassword())) {
                Session session = getSession(mailAccounts.get(0).getMailSend(), mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(), mailAccounts.get(0).getPort());
                if (mailInfo.getContent() != null) {
                    mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                            mailInfo.getContent().replace("\"", "")));
                }
                //发送邮件
                send(getMessage2(session, mailInfo, mailAccounts.get(0).getUsername()), session);
                //发送成功后   将发送成功的邮件保存在已发送的文件中
                int j = mailAccounts.get(0).getMailAccept().indexOf("qq");
                if (j > 0) {
                    saveSendEmail(mailAccounts.get(0).getMailAccept(), mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                            getMessage2(session, mailInfo, mailAccounts.get(0).getUsername()));
                } else {

                    return "modules/oa/wrong";

                }

                return "modules/oa/success";


            } else {
                return "modules/oa/wrong";
            }
        }
        return "";
    }

    /**
     * 安卓系统下微信浏览器内下载文件
     * name 文件相对路径
     */
    @Login(action = Action.Skip)
    @RequestMapping(value = {"getFile"})
    public void getFile(HttpServletRequest request, HttpServletResponse response, Model model, String state) {
        String name = request.getParameter("name");
        String dir = Global.getUserfilesBaseDir();
        if (name.indexOf("static/file/") > 0) {
            dir = request.getSession().getServletContext().getRealPath("/");
        }
        File file = new File(dir + Encodes.urlDecode(name));
        String fileRealName = name.substring(name.lastIndexOf("/") + 1, name.length());
        if (file.exists()) {
            try {
                fileRealName = Encodes.urlEncode(fileRealName);
                // 以流的形式下载文件。
                InputStream fis = new BufferedInputStream(new FileInputStream(dir + Encodes.urlDecode(name)));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();
                // 设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileRealName.getBytes()));
                response.addHeader("Content-Length", "" + file.length());
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据邮件人、邮件状态查询已发送,已删除，草稿箱，收件箱的邮件
     *
     * @param mailInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"listBySend"})
    public String listBySend(MailInfo mailInfo, HttpServletRequest request, HttpServletResponse response, Model model, String state) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        if (mailInfo.getState() == null) {
            mailInfo.setState("SENT");
            mailInfo.setFlag("0");
        } else {
            mailInfo.setFlag("0");
            mailInfo.setState(state);
        }
        long listAmount = 0;
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            mailInfo.setState("DELETED");
            Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
            page.setDelete(page.getList().size());
            listAmount = page.getCount();
            model.addAttribute("listAmount", listAmount);
            model.addAttribute("page", page);
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            mailInfo.setState("DRAFTS");
            mailInfo.setReadMark("1");
            Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
            listAmount = page.getCount();
            model.addAttribute("listAmount", listAmount);
            model.addAttribute("page", page);
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            mailInfo.setState("INBOX");
            mailInfo.setReceiverId(mailInfo.getOwnId());
            Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
            page.setDelete(page.getList().size());
            listAmount = page.getCount();
            model.addAttribute("listAmount", listAmount);
            model.addAttribute("page", page);
            return "modules/oa/receiving";
        } else {
            mailInfo.setState("SENT");
            mailInfo.setSenderId(mailInfo.getOwnId());
            Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
            page.setDelete(page.getList().size());
            listAmount = page.getCount();
            model.addAttribute("listAmount", listAmount);
            model.addAttribute("page", page);
            return "modules/oa/sent";
        }

    }

    /**
     * 手机端邮箱上拉加载功能
     *
     * @param mailInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getMailMore")
    @ResponseBody
    public List<Map> getMailMore(MailInfo mailInfo, @RequestParam(value = "pageNo", required = true) int pageNo, @RequestParam(value = "pageSize", required = true) int pageSize) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            mailInfo.setReceiverId(mailInfo.getOwnId());
        } else if (StringUtils.equals(mailInfo.getState(), "SENT")) {
            mailInfo.setSenderId(mailInfo.getOwnId());
        }
        Page<MailInfo> page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        List<Map> list = new ArrayList<>();
        list = mailInfoService.findTouchPage(page, mailInfo, list);
        return list;
    }

    /**
     * 写信
     *
     * @return
     */
    @RequestMapping(value = {"none", ""})
    public String none(Model model) {
        List<String> descriptionList = dictService.findListByType();
        model.addAttribute("descriptionList", descriptionList);
        return "modules/oa/write";
    }


    /**
     * 邮件信息查看
     *
     * @param mailInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "info")
    public String info(MailInfo mailInfo, Model model) {
        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/write";
    }


    /**
     * 查看邮件
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "find")
    public String find(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes, String id, HttpServletRequest request) {

        MailInfo mail = mailInfoService.getMail(id);
        if (StringUtils.equals(mail.getReadMark(), "0")) {
            mail.setReadMark("1");
            mailInfoService.save(mail);
            String mailId= mailInfo.getId();
            try {
                JSONArray jsonArray = new JSONArray();
                String  infoSzie = addJsonValue()+","+mailId+","+UserUtils.getUser().getId();
                String idInfo = UUID.randomUUID().toString();
                String json = jsonArray.toString();
                MSG msg = new MSG();
                msg.setId(idInfo);
                msg.setType("topic");
                msg.setDestination("update_flag");
                msg.setBody(json.getBytes());
//                amqMsgDao.saveMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("mailInfo", mail);
        return "modules/oa/find";
    }

    /**
     * 获取未读邮件的个数
     *
     * @param
     * @return
     */

    public String addJsonValue() {
        String jsonObject = new String();
        MailInfo mailInfo = new MailInfo();
        mailInfo.setReadMark("0");
        mailInfo.setOwnId(UserUtils.getUser().getId());
        mailInfo.setState("INBOX");
        List<MailInfo> delete = mailInfoService.findList(mailInfo);
        if (delete != null) {
           jsonObject = String.valueOf(delete.size());
        }
        return jsonObject;
    }


    /**
     * 回复邮件
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "callback")
    public String callback(MailInfo mailInfo, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, String id) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        MailInfo mail = mailInfoService.getMail(id);
        if (StringUtils.equals(mail.getReadMark(), "0")) {
            mail.setReadMark("1");
            mailInfoService.save(mail);
        }

        String name = mail.getName();
        Date createDate = mail.getCreateDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String data = dateFormat.format(createDate);
        String receiveNames = mail.getReceiverNames();
        String ccNmames = mail.getCcNames();
        String theme = mail.getTheme();
        String content = mail.getContent();

        if (UserAgentUtils.isMobile(request)) {
            String userAgents = request.getHeader("user-agent");
            if (userAgents.indexOf("iPhone") != -1 || userAgents.indexOf("iPad") != -1) {
                StringBuffer updateValue = new StringBuffer();
                updateValue.append("<br/>" + "<div style=\"font-size: 12px;font-family: Arial Narrow;padding:2px 0 2px 0;\">\n" +
                        "\t------------------&nbsp;原始邮件------------------</div>\n" +
                        "<div style=\"font-size: 12px;background:#efefef;padding:4px;\">\n" +
                        "\t<div>\n" +
                        "\t\t<b>发件人:</b>&nbsp;" + name + "</div>\n" +
                        "\t<div>\n" +
                        "\t\t<b>发送时间:</b>&nbsp;" + data + "</div>\n" +
                        "\t<div>\n" +
                        "\t\t<b>收件人:</b>&nbsp;" + receiveNames + "</div>\n" +
                        "\t<div>\n");
                if (StringUtils.isNotBlank(ccNmames)) {
                    updateValue.append(
                            "\t\t<b>抄送:</b>&nbsp;" + ccNmames + "<wbr /></div>\n" +
                                    "\t<div>\n");

                }

                updateValue.append(
                        "\t\t<b>主题:</b>&nbsp;" + theme + "</div>\n" +
                                "</div>\n" +
                                "\t\t" + content + "</div>\n");

                mail.setContent(updateValue.toString());
            } else {
                StringBuffer updateValue = new StringBuffer();
                updateValue.append("\n" +
                        "\n" +
                        "\n" + "------------------原始邮件------------------\n" +
                        "发件人:" + name + "\n" +
                        "发送时间:" + data + "\n" +
                        "收件人:" + receiveNames + "\n" +
                        "\n");
                if (StringUtils.isNotBlank(ccNmames)) {
                    updateValue.append(
                            "抄送:" + ccNmames + "\t");

                }
                if (content.contains("<p>")) {
                    String contents = content.replaceAll("<p>", "");
                    String contentes = contents.replaceAll("</p>", "");
                    String contentss = contentes.replaceAll("&nbsp;", "");
                    updateValue.append(
                            "主题:" + theme + "\n" +
                                    "\t\t" + contentss + "\n");
                }
                mail.setContent(updateValue.toString());
            }
        } else {
            StringBuffer updateValue = new StringBuffer();
            updateValue.append("<br/>" + "<div style=\"font-size: 12px;font-family: Arial Narrow;padding:2px 0 2px 0;\">\n" +
                    "\t------------------&nbsp;原始邮件------------------</div>\n" +
                    "<div style=\"font-size: 12px;background:#efefef;padding:4px;\">\n" +
                    "\t<div>\n" +
                    "\t\t<b>发件人:</b>&nbsp;" + name + "</div>\n" +
                    "\t<div>\n" +
                    "\t\t<b>发送时间:</b>&nbsp;" + data + "</div>\n" +
                    "\t<div>\n" +
                    "\t\t<b>收件人:</b>&nbsp;" + receiveNames + "</div>\n" +
                    "\t<div>\n");
            if (StringUtils.isNotBlank(ccNmames)) {
                updateValue.append(
                        "\t\t<b>抄送:</b>&nbsp;" + ccNmames + "<wbr /></div>\n" +
                                "\t<div>\n");

            }

            updateValue.append(
                    "\t\t<b>主题:</b>&nbsp;" + theme + "</div>\n" +
                            "</div>\n" +
                            "\t\t" + content + "</div>\n");

            mail.setContent(updateValue.toString());
        }
        mail.setFiles("");
        mail.setCcId("");
        mail.setCcNames("");
        model.addAttribute("mailInfo", mail);
        return "modules/oa/callBack";
    }

    /**
     * 转发邮件
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "forward")
    public String forward(MailInfo mailInfo, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, String id) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        MailInfo mail = mailInfoService.getMail(id);
        if (StringUtils.equals(mail.getReadMark(), "0")) {
            mail.setReadMark("1");
            mailInfoService.save(mail);
        }
        String name = mail.getName();
        Date createDate = mail.getCreateDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String data = dateFormat.format(createDate);
        String receiveNames = mail.getReceiverNames();
        String ccNmames = mail.getCcNames();
        String theme = mail.getTheme();
        String content = mail.getContent();

        if (UserAgentUtils.isMobile(request)) {
            String userAgents = request.getHeader("user-agent");
            if (userAgents.indexOf("iPhone") != -1 || userAgents.indexOf("iPad") != -1) {
                //苹果
                StringBuffer updateValue = new StringBuffer();
                updateValue.append("<br/>" + "<div style=\"font-size: 12px;font-family: Arial Narrow;padding:2px 0 2px 0;\">\n" +
                        "\t------------------&nbsp;原始邮件------------------</div>\n" +
                        "<div style=\"font-size: 12px;background:#efefef;padding:4px;\">\n" +
                        "\t<div>\n" +
                        "\t\t<b>发件人:</b>&nbsp;" + name + "</div>\n" +
                        "\t<div>\n" +
                        "\t\t<b>发送时间:</b>&nbsp;" + data + "</div>\n" +
                        "\t<div>\n" +
                        "\t\t<b>收件人:</b>&nbsp;" + receiveNames + "</div>\n" +
                        "\t<div>\n");
                if (StringUtils.isNotBlank(ccNmames)) {
                    updateValue.append(
                            "\t\t<b>抄送:</b>&nbsp;" + ccNmames + "<wbr /></div>\n" +
                                    "\t<div>\n");

                }
                updateValue.append(
                        "\t\t<b>主题:</b>&nbsp;" + theme + "</div>\n" +
                                "</div>\n" +
                                "\t\t" + content + "</div>\n");
                mail.setContent(updateValue.toString());
            } else {
                StringBuffer updateValue = new StringBuffer();
                updateValue.append("\n" +
                        "\n" +
                        "\n" + "------------------原始邮件------------------\n" +
                        "发件人:" + name + "\n" +
                        "发送时间:" + data + "\n" +
                        "收件人:" + receiveNames + "\n" +
                        "\n");
                if (StringUtils.isNotBlank(ccNmames)) {
                    updateValue.append(
                            "抄送:" + ccNmames + "\t");

                }
                if (content.contains("<p>")) {
                    String contents = content.replaceAll("<p>", "");
                    String contentes = contents.replaceAll("</p>", "");
                    String contentss = contentes.replaceAll("&nbsp;", "");
                    updateValue.append(
                            "主题:" + theme + "\n" +
                                    "\t\t" + contentss + "\n");
                }
                mail.setContent(updateValue.toString());
            }
        } else {
            StringBuffer updateValue = new StringBuffer();
            updateValue.append("<br/>" + "<div style=\"font-size: 12px;font-family: Arial Narrow;padding:2px 0 2px 0;\">\n" +
                    "\t------------------&nbsp;原始邮件------------------</div>\n" +
                    "<div style=\"font-size: 12px;background:#efefef;padding:4px;\">\n" +
                    "\t<div>\n" +
                    "\t\t<b>发件人:</b>&nbsp;" + name + "</div>\n" +
                    "\t<div>\n" +
                    "\t\t<b>发送时间:</b>&nbsp;" + data + "</div>\n" +
                    "\t<div>\n" +
                    "\t\t<b>收件人:</b>&nbsp;" + receiveNames + "</div>\n" +
                    "\t<div>\n");
            if (StringUtils.isNotBlank(ccNmames)) {
                updateValue.append(
                        "\t\t<b>抄送:</b>&nbsp;" + ccNmames + "<wbr /></div>\n" +
                                "\t<div>\n");

            }
            updateValue.append(
                    "\t\t<b>主题:</b>&nbsp;" + theme + "</div>\n" +
                            "</div>\n" +
                            "\t\t" + content + "</div>\n");
            mail.setContent(updateValue.toString());
        }


        mail.setCcId("");
        mail.setCcNames("");
        mail.setSenderId("");
        mail.setName("");
        model.addAttribute("mailInfo", mail);
        return "modules/oa/forWard";
    }

    /**
     * 点击人员写信
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "phoneWrite")
    public String phoneWrite(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes, String ids) {
        if (!(ids.indexOf(",") != -1)) {
            ids = ids + ",";
        }
        if (ids.split(",").length >= 1) {
            mailInfo = mailInfoService.getWrite(ids);
            mailInfo.setReceiverId(ids);
            model.addAttribute("mailInfo", mailInfo);
            return "modules/oa/write";
        } else {
            return "modules/oa/write";
        }

    }


    /**
     * 草稿箱查看
     *
     * @return
     */
    @RequestMapping(value = "draftsById")
    public String draftsById(MailInfo mailInfo, Model model, String id) {
        mailInfo = mailInfoService.getDrafts(id);
        if (StringUtils.isNotBlank(mailInfo.getFiles())) {
            mailInfo.setFileName(mailInfo.getFiles().substring(mailInfo.getFiles().lastIndexOf("/") + 1));
        }
        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/write";
    }


    /**
     * 保存邮件到草稿箱
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveDrafts")
    public String saveDrafts(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes) {

        mailInfo.setSenderId(UserUtils.getUser().getId());


        mailInfo.setTime(new Date());
//        if (!beanValidator(model, mailInfo)) {
//            return info(mailInfo, model);
//        }
        mailInfo.setFlag("0");
        mailInfoService.saveDrafts(mailInfo);
        mailInfo = mailInfoService.getDrafts(mailInfo.getId());
        addMessage(redirectAttributes, "邮件成功保存到草稿箱");
        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/saveDraftsSuccess";
    }

    /**
     * 附件上传
     *
     * @param file
     * @param name
     * @param signName
     * @param redirectAttributes
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "saveContractFile")
    @ResponseBody
    public String saveContractFile(@RequestParam("file") MultipartFile file, String name, String signName,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String path = "";
        if (StringUtils.isNotBlank(file.getOriginalFilename())) {
            path = mailInfoService.saveContractFile(file, name, signName, request);
        }
        return path;
    }

    /**
     * 删除邮件  并返回到界面
     *
     * @param mailInfo
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "move")

    public String move(MailInfo mailInfo, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String ids, String state) {
        //将选中的id循环出来根据id查询数据把状态更改成删除的状态 然后重新查询数据返回页面
        String[] id = ids.split(",");
        if (id != null) {
            for (int i = 0; i < id.length; i++) {
                MailInfo mail = mailInfoService.get(id[i]);
                mail.setState("DELETED");
                mailInfoService.save(mail);
            }
            addMessage(redirectAttributes, "删除邮件成功");
        }
        //返回界面
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        mailInfo1.setState(state);
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo1);
        List<MailInfo> list = mailInfoService.findList(mailInfo1);
        mailInfo1.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            List<MailInfo> mailInfos = page.getList();

            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo2 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo2.setFlag("0");
                infos.add(mailInfo2);
            }
            page.setList(infos);
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }

    /**
     * 手机端收件箱删除
     */
    @RequestMapping(value = "moveAll")
    @ResponseBody
    public StringData moveAll(MailInfo mailInfo, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String ids, String state) {
        String[] id = ids.split(",");
        if (id != null) {
            for (int i = 0; i < id.length; i++) {
                MailInfo mail = mailInfoService.get(id[i]);
                mail.setState("DELETED");
                mailInfoService.save(mail);
            }
        }
        StringData data = new StringData();
        data.setCode("success");
        data.setValue("删除成功");
        return data;
    }

    /**
     * 彻底删除邮件  并返回到界面
     *
     * @param mailInfo
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "thoroughDelete")
    public String thoroughDelete(MailInfo mailInfo, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String ids, String state) {
        String[] id = ids.split(",");
        if (id != null) {
            for (int i = 0; i < id.length; i++) {
                mailInfo.setId(id[i]);
                mailInfoService.delete(mailInfo);
            }
            addMessage(redirectAttributes, "彻底删除邮件成功");
        }
        //返回界面
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        mailInfo1.setState(state);
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo1);
        List<MailInfo> list = mailInfoService.findList(mailInfo1);
        mailInfo1.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            List<MailInfo> mailInfos = page.getList();
            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo2 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo2.setFlag("0");
                infos.add(mailInfo2);
            }
            page.setList(infos);
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }

    /**
     * 标记为 (已读/未读)
     *
     * @param
     * @param readMark
     * @return
     */
    @RequestMapping(value = "read")
    public String mark(MailInfo mailInfo, String ids, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String readMark, String state) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(readMark)) {
            mailInfoService.readMark(ids, readMark);
        }
        if (mailInfo.getState() == null) {
            mailInfo.setState("SENT");
            mailInfo.setFlag("0");
        } else {
            mailInfo.setFlag("0");
            mailInfo.setState(mailInfo.getState());
        }

        MailInfo m = new MailInfo();
        m.setOwnId(UserUtils.getUser().getId());
        m.setState(state);
        m.setFlag("0");
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), m);
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setReadMark("0");
        mailInfo1.setOwnId(mailInfo.getOwnId());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            mailInfo1.setState("DELETED");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            mailInfo1.setState("DRAFTS");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            mailInfo1.setState("INBOX");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/receiving";
        } else {
            mailInfo1.setState("SENT");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/sent";
        }
    }


    /**
     * 移动到(收件箱/已发送)
     *
     * @param
     * @param readMark
     * @return
     */
    @RequestMapping(value = "remove")
    public String remove(MailInfo mailInfo, String ids, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String readMark, String state, String state1) {
        mailInfo.setOwnId(UserUtils.getUser().getId());

        String[] id = ids.split(",");
        if (id != null) {
            for (int i = 0; i < id.length; i++) {
                MailInfo mail = mailInfoService.get(id[i]);
                if (StringUtils.equals(state, "DRAFTS")) {
                    mail.setSenderId(UserUtils.getUser().getId());

                }

                mail.setState(state1);
                mailInfoService.save(mail);
            }
            addMessage(redirectAttributes, "移动邮件成功");
        }


        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        mailInfo1.setState(state);
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo1);
        List<MailInfo> list = mailInfoService.findList(mailInfo1);
        mailInfo1.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            List<MailInfo> mailInfos = page.getList();
            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo2 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo1.setFlag("0");
                infos.add(mailInfo2);
            }
            page.setList(infos);
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }


    /**
     * 标记为全部已读
     *
     * @return
     */
    @RequestMapping(value = "allRead")
    public String allRead() {
        mailInfoService.allRead(UserUtils.getUser().getId());
        return "modules/oa/mailInfoList";
    }


    /**
     * 联系人查看
     *
     * @return
     */
    @RequestMapping(value = "phone")
    public String phone(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        Office office = UserUtils.getUser().getCompany();
        user.setCompanyId(office.getId());
        Page<User> page = mailInfoService.findPage1(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        //查询当前用户所属机构的所有的一级科室
        List<Office> offices = officeService.findOfficeByOrgId(UserUtils.getUser().getCompany().getId());
        model.addAttribute("offices", offices);
        return "modules/oa/phone";
    }


    /**
     * 邮件帐户设置
     *
     * @return
     */
    @RequestMapping(value = {"account", ""})
    public String account(MailAccount mailAccount, Model model) {
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        if (mailAccounts.size() > 0) {
            model.addAttribute("mailAccount", mailAccounts.get(0));
        } else {
            model.addAttribute("mailAccount", new MailAccount());
        }
        return "modules/oa/mail_account";
    }


    /**
     * 保存邮件帐户设置
     *
     * @return
     */
    @RequestMapping(value = {"saveAccount", ""})
    public String saveAccount(MailAccount mailAccount, Model model) {
        mailAccount.setLoginId(UserUtils.getUser().getId());
        mailAccountService.save(mailAccount);
        mailAccount = mailAccountService.get(mailAccount.getId());
        model.addAttribute("mailAccount", mailAccount);
        return "modules/oa/mail_account";
    }


    private static Session getSession(String mailSend, final String username, final String password, String port) {
        port = "25";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailSend);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }

    //发送html格式的正文
    private static Message getMessage3(Session session, MailInfo mailInfo, String username) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailInfo.getOutSide()));
        message.setSubject(mailInfo.getTheme());
        message.setContent(mailInfo.getContent(), "text/html");
        return message;
    }


    //含附件信息
    private static Message getMessage2(Session session, MailInfo mailInfo, String username) throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailInfo.getOutSide()));
        message.setSubject(mailInfo.getTheme());
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(mailInfo.getContent(), "text/html;charset=utf8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        if (StringUtils.isNotEmpty(mailInfo.getFiles())) {
            String filename = Global.getUserfilesBaseDir() + URLDecoder.decode(mailInfo.getFiles().replace("|", ""), "utf-8");
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(messageBodyPart);
        }
        message.setContent(multipart);
        message.setFlag(FLAGS.Flag.RECENT, true);
        message.saveChanges();
        return message;
    }

    public void saveSendEmail(String host, String username, String password, Message message) throws Exception {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        String port1 = "993";
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.port", port1);
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", username);
        props.setProperty("mail.imap.port", port1);
        props.setProperty("mail.imap.auth.login.disable", "true");
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("imap");  // 使用imap会话机制，连接服务器
        store.connect(host, username, password);

        Folder folder = (Folder) store.getFolder("已发送");
        if (!folder.exists()) {
            folder.create(Folder.HOLDS_MESSAGES);
        }
        folder.open(Folder.READ_WRITE);
        try {
            folder.appendMessages(new Message[]{message});
            message.setFlag(FLAGS.Flag.RECENT, true);
            message.saveChanges();
        } catch (Exception ignore) {
            System.out.println("error processing message " + ignore.getMessage());
        } finally {
            store.close();
        }

    }


    //发送
    private void send(Message message, Session session) {
        try {
            Transport.send(message);
            //保存邮件到已发送邮件夹
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 接收并查看外部邮件
     *
     * @return
     */
    @RequestMapping(value = {"findOut", ""})
    public String findOut(MailInfo mailInfo, Model model, HttpServletRequest request, HttpServletResponse response, String state) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        PraseMimeMessage praseMimeMessage = new PraseMimeMessage();

        if (StringUtils.equals(state, "SENT")) {
            if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername() != null && mailAccounts.get(0).getPassword() != null) {
                List<MailInfo> list = praseMimeMessage.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                        mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state);
                Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
                ComparatorMail comparator = new ComparatorMail();
                Collections.sort(list, comparator);

                page.setCount(list.size());
                page.setList(list);
                model.addAttribute("page", page);
            } else {
                Page<MailInfo> page = new Page<>();
                page.setCount(0);
                page.setMail("或者没有绑定邮箱账户");
                model.addAttribute("page", page);
            }
            return "modules/oa/sent";
        } else if (StringUtils.equals(state, "INBOX")) {
            if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername() != null && mailAccounts.get(0).getPassword() != null) {
                List<MailInfo> list = praseMimeMessage.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                        mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state);
                Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);


                ComparatorMail comparator = new ComparatorMail();
                Collections.sort(list, comparator);


                page.setCount(list.size());
                page.setList(list);
                model.addAttribute("page", page);
            } else {
                Page<MailInfo> page = new Page<>();
                page.setCount(0);
                page.setMail("或者没有绑定邮箱账户");
                model.addAttribute("page", page);
            }
            return "modules/oa/receiving";
        }
        return null;
    }


    /**
     * 点击查看外部邮件的内容
     *
     * @param mailInfo
     * @param model
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"findMail", ""})
    public String findMail(MailInfo mailInfo, Model model, String id, String name, String state) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername() != null && mailAccounts.get(0).getPassword() != null) {
            PraseMimeMessage praseMimeMessage = new PraseMimeMessage();
            List<MailInfo> list = praseMimeMessage.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                    mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state);
            if (id != null && id != "") {
                id = StringEscapeUtils.unescapeHtml4(id).replace("\"", "");
                if (StringUtils.isEmpty(id)) {
                    name = StringEscapeUtils.unescapeHtml4(name).replace("\"", "");
                    for (int i = 0; i < list.size(); i++) {
                        if (StringUtils.equals(name, list.get(i).getName())) {

                            mailInfo.setContent(list.get(i).getContent());
                            mailInfo.setTheme(list.get(i).getTheme());
                            mailInfo.setFiles(list.get(i).getFiles());
                            mailInfo.setName(list.get(i).getName());
                            mailInfo.setTime(list.get(i).getTime());
                            mailInfo.setFlag("1");
                            mailInfo.setReceiverNames(list.get(i).getReceiverNames());
                            model.addAttribute("mailInfo", mailInfo);
                        }
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    if (StringUtils.equals(id, list.get(i).getUID())) {
                        System.out.println(list.get(i).getUID());
                        mailInfo.setContent(list.get(i).getContent());
                        mailInfo.setTheme(list.get(i).getTheme());
                        mailInfo.setFiles(list.get(i).getFiles());
                        mailInfo.setName(list.get(i).getName());
                        mailInfo.setTime(list.get(i).getTime());
                        mailInfo.setFlag("1");
                        mailInfo.setReceiverNames(list.get(i).getReceiverNames());
                        model.addAttribute("mailInfo", mailInfo);
                    }
                }
            }
        }

        return "modules/oa/find";
    }


    /**
     * 删除外部邮件的内容
     *
     * @param mailInfo
     * @param model
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"deleteMail", ""})
    public String deleteMail(MailInfo mailInfo, Model model, String ids, String name, String state) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        PraseMimeMessage praseMimeMessage = new PraseMimeMessage();
        if (ids != null && ids != "") {
            ids = StringEscapeUtils.unescapeHtml4(ids).replace("\"", "");
            ids = ids.substring(0, ids.length() - 1);
        }
        List<MailInfo> list = praseMimeMessage.mail(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state, ids);

        return "modules/oa/find";
    }



    /**
     * 根据邮件人、邮件状态查询收件箱的邮件，并显示到首页中
     *
     * @param mailInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"mailInfoList"})
    public List<MailInfo> mailInfoList(MailInfo mailInfo, HttpServletRequest request, HttpServletResponse response, Model model, String state) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        if (mailInfo.getState() == null) {
            mailInfo.setState("SENT");
            mailInfo.setFlag("0");
        } else {
            mailInfo.setFlag("0");
            mailInfo.setState(state);
        }
        long listAmount = 0;
       if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            mailInfo.setState("INBOX");
            mailInfo.setReceiverId(mailInfo.getOwnId());
            List<MailInfo> mailInfos =  mailInfoService.findList(mailInfo);
            return mailInfos;
        }
      return null;
    }
}