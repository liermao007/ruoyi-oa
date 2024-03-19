package com.thinkgem.jeesite.common.utils;

import cn.org.bjca.client.exceptions.*;
import cn.org.bjca.client.security.SecurityEngineDeal;
import com.thinkgem.jeesite.common.config.Global;

import java.io.UnsupportedEncodingException;

/**
 * @author oa
 */
public class SecurityEngineUtils {

    public static void main(String[] args) throws ApplicationNotFoundException, InitException, SVSConnectException, ParameterTooLongException, CommonClientException, UnsupportedEncodingException, ParameterInvalidException, UnkownException {
//        SecurityEngineDeal sed = SecurityEngineDeal.getInstance("SVSDefault");
//        byte[] data = "test".getBytes();
//        String random = getRandom();
//        String signData = getSignData(random.getBytes());
//        String serverCertificate = getServerCertificate();
//        System.out.println(random);
//        System.out.println(serverCertificate);
//        System.out.println(signData);

        System.out.println(base64Encode("test"));

        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("SVSDefault");
//            String data = "eyJjcmVhdGVfYnkiOiJjYzMzMTYyNzc2MzQ0ZTJiYTkzNjk2OGNlNjc4NzQzMyIsIkFRWlJSIjoiudzA7dSxIiwicHJvY0luc0lkIjoiMjFhZjQwNmE4MmQxNGU1ODg5NDFkZTIyMTQxN2MyOTMiLCJUQktTIjoiz7XNs7ncwO0iLCJYQ1JRIjoiMjAyMy0wNi0wOSIsIkFRU1NaU0JaU0ZXSCI6IjEiLCJYQ1IiOiK53MDt1LEiLCJYRlREU0ZUQyI6IjAiLCJpZCI6IjcyMjgzMWU5ZjI4YzQxYmQ4MjUyN2ZjYTlhOWU5MmU0IiwiQVFDS1NGVEMiOiIwIiwicHJvY0RlZklkIjoiWEZBUVhDQjozOmFlNDJiNTZmOGZlZDRmNjA5ODY3MGExZTNlMTEwYWRmIiwiWUpaTVhUU0ZXSCI6IjEifQ==";
//            String sign = "MEYCIQCjgysDcHVoDzyzFS5LoitqcFJdzlKJtJsDuEn28d9oCAIhAJlPerwxd5PrQNNnQHnxDLdHJ5HzadXB4Xj3OIBOPDGW";
//            String cert = "MIIEiTCCBDCgAwIBAgIKGhAAAAAAApMiCzAKBggqgRzPVQGDdTBEMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTENMAsGA1UECwwEQkpDQTEXMBUGA1UEAwwOQmVpamluZyBTTTIgQ0EwHhcNMjIwOTEzMTYwMDAwWhcNMjQwMzE0MTU1OTU5WjBeMQ8wDQYDVQQDDAbpn6nmvo0xDzANBgNVBAcMBuaAgOaflDEPMA0GA1UECAwG5YyX5LqsMQswCQYDVQQGDAJDTjEcMBoGCSqGSIb3DQEJARYNMTIzMTMxQHFxLmNvbTBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABFbCQgggX1F4v/ZBX0VBVJClhf63PNdP7LyOpZY2MKcD3RIO1jaMkVlWjCWLNvnwLKVPH5LvzzrzGr/gjmb8nYCjggLuMIIC6jAfBgNVHSMEGDAWgBQf5s/Uj8UiKpdKKYoV5xbJkjTEtjAdBgNVHQ4EFgQUrQ2ncEt0TlRsh+cemuXk5qXYzCowCwYDVR0PBAQDAgbAMIGfBgNVHR8EgZcwgZQwYaBfoF2kWzBZMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTENMAsGA1UECwwEQkpDQTEXMBUGA1UEAwwOQmVpamluZyBTTTIgQ0ExEzARBgNVBAMTCmNhMjFjcmwyMTgwL6AtoCuGKWh0dHA6Ly9jcmwuYmpjYS5vcmcuY24vY3JsL2NhMjFjcmwyMTguY3JsMCQGCiqBHIbvMgIBAQEEFgwUU0YxMTAyMjcxOTk0MTExNTM4MTkwYAYIKwYBBQUHAQEEVDBSMCMGCCsGAQUFBzABhhdPQ1NQOi8vb2NzcC5iamNhLm9yZy5jbjArBggrBgEFBQcwAoYfaHR0cDovL2NybC5iamNhLm9yZy5jbi9jYWlzc3VlcjBABgNVHSAEOTA3MDUGCSqBHIbvMgICATAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczARBglghkgBhvhCAQEEBAMCAP8wIgYKKoEchu8yAgEBCAQUDBIxMTAyMjcxOTk0MTExNTM4MTkwJAYKKoEchu8yAgECAgQWDBRTRjExMDIyNzE5OTQxMTE1MzgxOTAfBgoqgRyG7zICAQEOBBEMDzEwMjA4MDAyMDQ4NTcwMDAdBggqVoZIAYEwAQQRDA8xMDIwODAwMjA0ODU3MDAwKQYKKoEchu8yAgEBBAQbMUA1MDA5U0YwMTEwMjI3MTk5NDExMTUzODE5MC0GCiqBHIbvMgIBARcEHwwdMUAyMTUwMDlTRjAxMTAyMjcxOTk0MTExNTM4MTkwIAYIKoEc0BQEAQQEFAwSMTEwMjI3MTk5NDExMTUzODE5MBYGCiqBHIbvMgIBAR4ECAwGMTAwNDg3MAoGCCqBHM9VAYN1A0cAMEQCIChe41PhdC0wELy2DYt6rPLu1zANcVTlhdXqed0oBTmUAiAEpEc6bksOGM2G8ZP1+iEr7AdEml1khQpZD0sVSwzHWA==";
//            boolean verifyRes = verifyBase64(cert, data, sign);
//            String ts = getTS(data);
//            System.out.println(verifyRes);

            String certInfoByOid = sed.getCertInfoByOid("MIIEiTCCBDCgAwIBAgIKGhAAAAAAApMiCzAKBggqgRzPVQGDdTBEMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTENMAsGA1UECwwEQkpDQTEXMBUGA1UEAwwOQmVpamluZyBTTTIgQ0EwHhcNMjIwOTEzMTYwMDAwWhcNMjQwMzE0MTU1OTU5WjBeMQ8wDQYDVQQDDAbpn6nmvo0xDzANBgNVBAcMBuaAgOaflDEPMA0GA1UECAwG5YyX5LqsMQswCQYDVQQGDAJDTjEcMBoGCSqGSIb3DQEJARYNMTIzMTMxQHFxLmNvbTBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABFbCQgggX1F4v/ZBX0VBVJClhf63PNdP7LyOpZY2MKcD3RIO1jaMkVlWjCWLNvnwLKVPH5LvzzrzGr/gjmb8nYCjggLuMIIC6jAfBgNVHSMEGDAWgBQf5s/Uj8UiKpdKKYoV5xbJkjTEtjAdBgNVHQ4EFgQUrQ2ncEt0TlRsh+cemuXk5qXYzCowCwYDVR0PBAQDAgbAMIGfBgNVHR8EgZcwgZQwYaBfoF2kWzBZMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTENMAsGA1UECwwEQkpDQTEXMBUGA1UEAwwOQmVpamluZyBTTTIgQ0ExEzARBgNVBAMTCmNhMjFjcmwyMTgwL6AtoCuGKWh0dHA6Ly9jcmwuYmpjYS5vcmcuY24vY3JsL2NhMjFjcmwyMTguY3JsMCQGCiqBHIbvMgIBAQEEFgwUU0YxMTAyMjcxOTk0MTExNTM4MTkwYAYIKwYBBQUHAQEEVDBSMCMGCCsGAQUFBzABhhdPQ1NQOi8vb2NzcC5iamNhLm9yZy5jbjArBggrBgEFBQcwAoYfaHR0cDovL2NybC5iamNhLm9yZy5jbi9jYWlzc3VlcjBABgNVHSAEOTA3MDUGCSqBHIbvMgICATAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczARBglghkgBhvhCAQEEBAMCAP8wIgYKKoEchu8yAgEBCAQUDBIxMTAyMjcxOTk0MTExNTM4MTkwJAYKKoEchu8yAgECAgQWDBRTRjExMDIyNzE5OTQxMTE1MzgxOTAfBgoqgRyG7zICAQEOBBEMDzEwMjA4MDAyMDQ4NTcwMDAdBggqVoZIAYEwAQQRDA8xMDIwODAwMjA0ODU3MDAwKQYKKoEchu8yAgEBBAQbMUA1MDA5U0YwMTEwMjI3MTk5NDExMTUzODE5MC0GCiqBHIbvMgIBARcEHwwdMUAyMTUwMDlTRjAxMTAyMjcxOTk0MTExNTM4MTkwIAYIKoEc0BQEAQQEFAwSMTEwMjI3MTk5NDExMTUzODE5MBYGCiqBHIbvMgIBAR4ECAwGMTAwNDg3MAoGCCqBHM9VAYN1A0cAMEQCIChe41PhdC0wELy2DYt6rPLu1zANcVTlhdXqed0oBTmUAiAEpEc6bksOGM2G8ZP1+iEr7AdEml1khQpZD0sVSwzHWA==", "1.2.156.112562.2.1.1.1");

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String base64Encode(String data) {
        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("SVSDefault");
            return sed.base64Encode(data.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("base64编码失败");
        }
    }

    public static byte[] base64Decode(String data) {
        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("SVSDefault");
            return sed.base64Decode(data);
        } catch (Exception e) {
            throw new RuntimeException("base64解码失败");
        }
    }

    public static String getSignData(byte[] data) {
        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("SVSDefault");
            return sed.signData(data);
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
        }
    }

    public static String getRandom() {
        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("SVSDefault");
            return sed.genRandom(24);
        } catch (Exception e) {
            throw new RuntimeException("获取随机数失败");
        }
    }

    public static String getServerCertificate() {
        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("SVSDefault");
            return sed.getServerCertificate();
        } catch (Exception e) {
            throw new RuntimeException("获取服务器证书失败");
        }
    }

    public static boolean verifyBase64(String cert, String data, String sign) {
        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("SVSDefault");
            return sed.verifySignedData(cert, base64Decode(data), sign);
        } catch (Exception e) {
            throw new RuntimeException("认证失败");
        }
    }

    public static String getTS(String data) {
        SecurityEngineDeal sed = null;
        try {
            sed = SecurityEngineDeal.getInstance("TSSSM2");
            String tsRequest = sed.createTSRequest(base64Decode(data), true);
            return sed.createTS(tsRequest);
        } catch (Exception e) {
            throw new RuntimeException("获取时间戳失败");
        }
    }

    public static boolean enableStatus() {
        return "1".equals(Global.getConfig("ca.enable"));
    }
}
