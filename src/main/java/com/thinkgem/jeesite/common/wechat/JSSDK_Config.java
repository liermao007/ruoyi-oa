package com.thinkgem.jeesite.common.wechat;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CacheUtils;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.UUID;

import static com.thinkgem.jeesite.modules.sys.utils.UserUtils.USER_CACHE;

/**
 * ClassName: JSSDK_Config
 * @Description: 用户微信前端页面的jssdk配置使用
 * @author oa
 */
public class JSSDK_Config {

    public static HashMap<String, String> jsSDK_Sign(String url) throws Exception {
        String nonce_str = create_nonce_str();
        String timestamp= create_timestamp();
        WeChatToken weChatToken= (WeChatToken) CacheUtils.get(USER_CACHE,"wechat_token");
        String jsapi_ticket= weChatToken.getTicket();
        System.out.println("jsapi_ticket==" + jsapi_ticket);
        // 注意这里参数名必须全部小写，且必须有序
        String  string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp  + "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes("UTF-8"));

        String signature = byteToHex(crypt.digest());
        System.out.println(signature);
        HashMap<String, String> jssdk=new HashMap<String, String>();
        jssdk.put("appId", Global.getConfig("wechat.appId"));
        jssdk.put("timestamp", timestamp);
        jssdk.put("nonceStr", nonce_str);
        jssdk.put("signature", signature);
        jssdk.put("jsapi_ticket", jsapi_ticket);
        return jssdk;

    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
