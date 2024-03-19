package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SynchroDataUtils;
import com.thinkgem.jeesite.modules.sys.entity.SmsInfo;
import com.thinkgem.jeesite.modules.test.testmsm.SmsModule;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 */
@Service
@Transactional(readOnly = true)
public class SmsService {
    /**
     * 岗位工作平台借助OA发送短信
     *
     * @param type
     */
    @Transactional(readOnly = true)
    public String sendSMS(String type, String inputLine) {
       /* List<Map<String, Object>> result = convertList(inputLine);
        if (StringUtils.isNoneBlank(type) && type.equals("sendSMS")) {
            if (result != null) {
                if ( result.size()>0) {
                    Map<String, Object> map = result.get(0);
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        SmsInfo smsInfo = (SmsInfo) entry.getValue();
                        if (smsInfo != null) {
//                            String message=SmsModule.scode(smsInfo.getPhone(),smsInfo.getCode());//短信发送后获取状态信息
                            smsInfo.setSmsSendResult(message);
                            sendResult(smsInfo);
                            return message;
                        }
                    }
                }
            }
        }*/
        return "";
    }
    /**
     * 将岗位工作平台传递的数据转成list集合
     * @param json
     * @return
     * @author oa
     */
    public List<Map<String, Object>> convertList(String json) {
        Map<String, String[]> replaceMap = new HashMap<>();
        String namespace = SmsInfo.class.getName();
        replaceMap.put("phone", new String[]{namespace, "phone"});
        replaceMap.put("count", new String[]{namespace, "count"});
        replaceMap.put("code", new String[]{namespace, "code"});
        return SynchroDataUtils.getDataByJson(json, replaceMap);
    }

    /**
     * 将岗位工作平台调用OA的短信接口后的状态转为JSONObject型保存
     *
     * @param smsInfo
     * @return
     */
    public JSONObject smsSendResultJsonValue(SmsInfo smsInfo) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("smsSendResult", smsInfo.getSmsSendResult());
        jsonObject.put("phone", smsInfo.getPhone());
        jsonObject.put("code", smsInfo.getCode());
        jsonObject.put("count",smsInfo.getCount()+1);
        return jsonObject;
    }

    /**
     * 利用topicSender将短信发送结果返回到岗位工作平台
     *
     * @param smsInfo
     * @return
     */
    public void sendResult(SmsInfo smsInfo) {
        try {
            smsInfo.setSmsSendResult(smsInfo.getSmsSendResult());
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = smsSendResultJsonValue(smsInfo);
            jsonArray.add(jsonObject);
            String msgId = UUID.randomUUID().toString();
            String json = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
