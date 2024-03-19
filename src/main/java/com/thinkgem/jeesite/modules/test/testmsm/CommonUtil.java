package com.thinkgem.jeesite.modules.test.testmsm;


import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * (常用工具类)
 * className: CommonUtil
 * User: zy
 * Date Time: 2015-03-13 13:38
 */
public class CommonUtil {
    /**
     * @param '[]' 传递参数
     * @return java.lang.String 返回类型
     * @throws
     * @Title: fileUploadPath
     * @Description: (获取文件上传路径)
     * @author oa
     * @date 2015-05-20 10:53
     */
    public static String fileUploadPath() {
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("fileUploadPath");
        return path;
    }
    /**
     * 是否是验证码登录
     *
     * @param useruame 用户名
     * @param isFail   计数加1
     * @param clean    计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            // loginFailNum = 3;
           loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(useruame);
        }
        boolean result = loginFailNum.intValue() >= 3;
        return result;
    }
    /**
     * @param  '[]' 传递参数
     * @return java.lang.String 返回类型
     * @throws
     * @Title: fileUploadHttpPath
     * @Description: (获取文件下载路径)
     * @author oa
     * @date 2015-05-20 10:53
     */
    public static String fileUploadHttpPath() {
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("fileUploadHttpPath");
        return path;
    }

    /**
     * @Description: 邮件端口
     * @author oa
     * @date 2015-04-13 13:46
     */
    public static String mailHost() {
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("mailHost");
        return path;
    }

    /**
     * @Description: 邮件账号
     * @author oa
     * @date 2015-04-13 13:46
     */
    public static String mailUserName() {
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("mailUserName");
        return path;
    }

    /**
     * @Description: 邮件密码
     * @author oa
     * @date 2015-04-13 13:46
     */
    public static String mailPassword() {
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("mailPassword");
        return path;
    }

    /**
     * @Description: 短信用户名
     * @author oa
     * @date 2015-04-13 13:46
     */
    public static String smsUserName(String companyId) {
       String name= DictUtils.getDLableByDescription(companyId, " ");
        return name;
    }

    /**
     * @Description: 短信密码
     * @author oa
     * @date 2015-04-13 13:46
     */
    public static String smsPassword(String companyId) {
        String password= DictUtils.getDValueByDescription(companyId," ");
        return password;
    }

    /**
     * @Description: 发件人
     * @author oa
     * @date 2015-04-13 13:46
     */
    public static String mailFrom() {
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("mailFrom");
        return path;
    }

    /**
     * @Description: 用户个人头像
     * @author oa
     * @date 2015-6-4 13:46
     */
    public static String userHead(){
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("userHeadPath");
        return path;
    }

    /**
     * @Description: 系统头像
     * @author oa
     * @date 2015-6-4 13:46
     */
    public static String adminHead(){
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String path = properties.getProperty("adminHeadPath");
        return path;
    }

    /**
     * @Description:
     * @author oa
     * @date 2015-6-4 13:46
     */
    public static String getValue(String name) {
        LoadProperties properties = new LoadProperties();
        properties.setPropFileName("/resource.properties");
        String value = properties.getProperty(name);
        return value;
    }

//    //调用接口方法（itemcode表示map的value值，keyName表示map中key的名称）
//    public static List<BaseDto> getExamList(String url,List<BaseDto> list) {
//        List<BaseDto> patientList=new ArrayList<BaseDto>();
//        HashMap<String, Object> params= new HashMap<String, Object>();
//        for(BaseDto dto:list){
//            params.putAll(dto);
//        }
//        RestUtils<BaseDto,HashMap> restUtils =new RestUtils<BaseDto ,HashMap>();
//        try {
//            Map<String, Object> map = restUtils.findByList(url, BaseDto.class, params, params);
//            patientList= (List<BaseDto>) map.get("body");
//        }catch (Exception e){
//            e.getMessage();
//        }
//        return patientList;
//    }
//
//    //接口保存方法
//    public static String saveExamList(HashMap<String, Object> params) {
//        String str="";
//        RestUtils<BaseDto,HashMap> restUtils =new RestUtils<BaseDto ,HashMap>();
//        Map<String, Object> map = restUtils.findByList("/mpa/examAppItems/hisExamAppointsSave", BaseDto.class, params,params);
//        List<BaseDto> list= (List<BaseDto>) map.get("body");
//        if(list!=null&&list.size()>0){
//            BaseDto dto = list.get(0);
//            str = (String)dto.get("strstate");
//        }
//        return str;
//    }
}
