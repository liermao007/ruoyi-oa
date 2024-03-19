package com.thinkgem.jeesite.common.utils;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * 同步数据工具类
 *
 * @author oa
 * @version 2017/6/23
 */
public class SynchroDataUtils {

    /**
     * 根据数据类型和对照参数， 生成对象
     *
     * @param json
     * @param replaceMap
     * @return
     */
    public static List<Map<String, Object>> getDataByJson(String json, Map<String, String[]> replaceMap) {
        if (json == null || json.trim().length() == 0 || replaceMap == null || replaceMap.keySet().size() == 0)
            return null;
        List<Map> lists = getProtogenesisDatas(json);
        List<Map<String, Object>> result = null;
        if (lists != null && lists.size() > 0) {
            result = new ArrayList<>();
            Map<String, List<FieldProperties>> replace = handlerReplaceMap(replaceMap);
            for (int i = 0, len = lists.size(); i < len; i++) {
                result.add(initData(lists.get(i), replace));
            }
        }
        return result;
    }

    /**
     * 根据url获取json数据
     * @param url
     * @return
     */
    public static String getJsonByUrl(String url) {
        if (url != null && url.trim().length() > 0) {
            HttpURLConnection httpConn = null;
            InputStreamReader isr = null;
            try {
                httpConn = (HttpURLConnection) new URL(url).openConnection();
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                isr = new InputStreamReader(httpConn.getInputStream(), "utf-8");
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    return inputLine;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException e) {
                    }
                }
                if (httpConn != null) {
                    httpConn.disconnect();
                }
            }
        }
        return null;
    }

    /**
     * 获取原始数据
     *
     * @param
     * @return
     */
    private static List<Map> json2Map(String inputLine) {
        if (inputLine != null && inputLine.trim().length() > 0) {
            return JSONArray.parseArray(inputLine, Map.class);
        }
        return null;
    }

    /**
     * 根据值和对照Map生成对象
     *
     * @param dataMap
     * @param replaceMap
     * @return
     */
    private static Map<String, Object> initData(Map<String, String> dataMap, Map<String, List<FieldProperties>> replaceMap) {
        Map<String, Object> data = null;
        if (dataMap != null) {
            data = newInstance(replaceMap); // 对象
            if (replaceMap != null) for (String key : replaceMap.keySet()) {
                List<FieldProperties> fields = replaceMap.get(key);
                Object temp = data.get(fields.get(0).namespace);
                FieldProperties field = null;
                for (int i = 0, len = fields.size(); i < len; i++) {
                    field = fields.get(i);
                    try {
                        if (i == len - 1) {
                            field.setMethod.invoke(temp, formatValue(dataMap.get(key), field.fieldType));
                        } else {
                            temp = field.getMethod.invoke(temp);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return data;
    }

    /**
     * 获取对照Map
     *
     * @param replaceMap
     * @return {接口传输的key: Map{对应字段： FieldProperties}}
     */
    private static Map<String, List<FieldProperties>> handlerReplaceMap(Map<String, String[]> replaceMap) {
        Map<String, List<FieldProperties>> fieldMap = new HashMap<>();
        if (replaceMap != null) for (String key : replaceMap.keySet()) {
            Map<String, FieldProperties> paramMap = new LinkedHashMap<>();
            List<FieldProperties> fields = new ArrayList<>();
            String[] args = replaceMap.get(key);
            String[] params = args[1].split("\\.");
            String paramKey = "";
            String namespace = null;
            FieldProperties fp = null;
            for (int i = 0, len = params.length; i < len; i++) {
                if (i > 0) {
                    namespace = paramMap.get(paramKey).fieldType;
                } else {
                    namespace = args[0];
                }
                paramKey += params[i];
                fp = new FieldProperties(params[i], namespace);
                paramMap.put(paramKey, fp);
                fields.add(fp);
            }
            fieldMap.put(key, fields);
        }
        return fieldMap;
    }

    /**
     * 根据对照Map获得实例化对象
     *
     * @param replaceMap
     * @return
     */
    private static Map<String, Object> newInstance(Map<String, List<FieldProperties>> replaceMap) {
        Map<String, Object> result = new HashMap<>();
        if (replaceMap != null) {
            List<FieldProperties> fields = null;
            for (String key : replaceMap.keySet()) {
                fields = replaceMap.get(key);
                FieldProperties field = null;
                Object temp = null;
                for (int i = 0, len = fields.size(); i < len; i++) {
                    field = fields.get(i);
                    if (i == 0 && result.get(field.namespace) == null) {
                        result.put(field.namespace, ReflectUtils.getObject(field.namespace));
                    }
                    if (temp == null) {
                        temp = result.get(field.namespace);
                    }
                    if (i < len - 1) {
                        try {
                            if (field.getMethod.invoke(temp) == null) {
                                field.setMethod.invoke(temp, ReflectUtils.getObject(field.fieldType));
                            }
                            temp = field.getMethod.invoke(temp);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取原始数据
     *
     * @param json
     * @return
     */
    private static List<Map> getProtogenesisDatas(String json) {
        if (json != null && json.trim().length() > 0) {
            return json2Map(json);
        }
        return null;
    }

    /**
     * 转换数据格式
     *
     * @param formatValue
     * @param valueType
     * @return
     */
    private static Object formatValue(Object formatValue, String valueType) {
        if (formatValue != null) {
            String value = formatValue.toString();
            if (valueType != null && valueType.trim().length() > 0 && value.trim().length() > 0) {
                switch (valueType) {
                    case "byte": ;
                    case "java.lang.Byte": {
                        return Byte.parseByte(value);
                    }
                    case "short":
                        ;
                    case "java.lang.Short": {
                        return Short.parseShort(value);
                    }
                    case "int":
                        ;
                    case "java.lang.Integer": {
                        return Integer.parseInt(value);
                    }
                    case "long":
                        ;
                    case "java.lang.Long": {
                        return Long.parseLong(value);
                    }
                    case "float":
                        ;
                    case "java.lang.Float": {
                        return Float.parseFloat(value);
                    }
                    case "double":
                        ;
                    case "java.lang.Double": {
                        return Double.parseDouble(value);
                    }
                    case "boolean":
                        ;
                    case "java.lang.Boolean": {
                        return Boolean.parseBoolean(value);
                    }
                    case "char":
                        ;
                    case "java.lang.Character": {
                        return value.charAt(0);
                    }
                    case "java.sql.Date": ;
                    case "java.util.Date": {
                        return formatValue instanceof Long ? new Date(Long.parseLong(value)) : DateUtils.parseDate(value);
                    }
                }
            }
        }
        return formatValue;
    }

    /**
     * 内部属性类
     */
    private static class FieldProperties {

        String fieldName;
        String fieldType;
        String namespace;
        Field field;
        Method getMethod;
        Method setMethod;
        Class clazz;

        FieldProperties(String fieldName, String namespace) {
            this.fieldName = fieldName;
            this.namespace = namespace;
            if (fieldName != null && fieldName.trim().length() > 0 && namespace != null && namespace.trim().length() > 0) {
                this.clazz = ReflectUtils.getClass(namespace);
                this.field = ReflectUtils.getField(fieldName, clazz);
                this.getMethod = ReflectUtils.getMethod(fieldName, "get", clazz);
                this.setMethod = ReflectUtils.getMethod(fieldName, "set", clazz);
            }
        }
    }
}
