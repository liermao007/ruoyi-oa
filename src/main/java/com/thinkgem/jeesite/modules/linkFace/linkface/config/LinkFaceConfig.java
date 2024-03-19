package com.thinkgem.jeesite.modules.linkFace.linkface.config;


public abstract class LinkFaceConfig {


    private static String apiSecert = "5fd76ab4b53144719d82afae166dbd24";

    private static String appID = "881cb8558ed741dfa666c95595bcec7f";

    public static String getApiSecert() {
        return apiSecert;
    }

    public static void setApiSecert(String apiSecert) {
        LinkFaceConfig.apiSecert = apiSecert;
    }

    public static String getAppID() {
        return appID;
    }

    public static void setAppID(String appID) {
        LinkFaceConfig.appID = appID;
    }


}
