package com.thinkgem.jeesite.common.wechat;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.linkFace.plat.common.utils.HttpClientUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.thinkgem.jeesite.modules.sys.utils.UserUtils.USER_CACHE;

@Component
public class WeChatTask {
    @PostConstruct
    public void init(){
        try {
            this.getToken_getTicket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* * @Description: 任务执行体
     * @param @throws Exception
     * @author oa
     * @date 2016年3月10日 下午2:04:37
   */
    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        //获取token执行体
        params.put("grant_type", "client_credential");
        params.put("appid", Global.getConfig("wechat.appId"));
        params.put("secret", Global.getConfig("wechat.appsecret"));
        String jstoken = HttpClientUtils.doGet(
                Global.getConfig("wechat.access_token"), params);
        String access_token = JSONObject.fromObject(jstoken).getString(
                "access_token"); // 获取到token并赋值保存
        CacheUtils.remove(USER_CACHE,"wechat_token");

        //获取jsticket的执行体
        params.clear();
        params.put("access_token", access_token);
        params.put("type", "jsapi");
        String jsticket = HttpClientUtils.doGet(
                Global.getConfig("wechat.jsapi_ticket"), params);
        String jsapi_ticket = JSONObject.fromObject(jsticket).getString(
                "ticket");
        WeChatToken weChatToken=new WeChatToken();
        weChatToken.setToken(access_token);
        weChatToken.setTicket(jsapi_ticket);
        CacheUtils.put(USER_CACHE,"wechat_token", weChatToken); // 获取到js-SDK的ticket并赋值保存

        System.out.println("jsapi_ticket================================================" + jsapi_ticket);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token为=============================="+access_token);
    }
}
