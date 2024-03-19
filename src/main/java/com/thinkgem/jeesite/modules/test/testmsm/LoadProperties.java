package com.thinkgem.jeesite.modules.test.testmsm;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * (读取静态资源文件)
 * className: LoadProperties
 * User: zy
 * Date Time: 2015-03-13 13:38
 */
public class LoadProperties {

    private static Properties prop = null;

    // 资源文件读取位置
    private static String propFileName = "/resources/resource.properties";

    // 日志文件管理
    private static Logger log = Logger.getLogger(LoadProperties.class);

    /**
     * 读取属性文件
     */
    private synchronized static void loadProperties() {
        prop = new Properties();
        try {
            InputStream input = LoadProperties.class
                    .getResourceAsStream(propFileName);
            prop.load(input);
        } catch (IOException e) {
            log.info("读取资源文件失败，" + e.toString());
        }
    }

    public String getProperty(String propName) {
        loadProperties();
        // 判断返回值是否为空 否则出现空指针异常
        //return prop.getProperty(propName).trim();
        return prop.getProperty(propName) == null ? "" : prop.getProperty(propName).trim();
    }

    public void setProperty(String propKey, String value) throws Exception {
        loadProperties();
        OutputStream fos = null;
        fos = new FileOutputStream(LoadProperties.class.getResource(propFileName).getFile());
        prop.setProperty(propKey, value);
        prop.store(fos, "Update '" + propKey + "' value");
        fos.close();// 关闭流
    }

    public String getPropFileName() {
        return propFileName;
    }

    public void setPropFileName(String propFileName) {
        LoadProperties.propFileName = propFileName;
    }

}
