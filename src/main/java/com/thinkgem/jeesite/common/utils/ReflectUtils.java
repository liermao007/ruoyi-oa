package com.thinkgem.jeesite.common.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author oa
 * @version 2017/6/27
 */
public class ReflectUtils {

    /**
     * 获取对象值
     *
     * @param t
     * @param fieldName
     * @return
     */
    public static <T> Object getValue(T t, String fieldName) {
        try {
            return PropertyUtils.getProperty(t, fieldName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据命名空间实例化对象
     *
     * @param namespace
     * @return
     */
    public static Object getObject(String namespace) {
        if (namespace != null && namespace.trim().length() > 0) {
            try {
                return Class.forName(namespace).newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据命名空间获取Class
     *
     * @param namespace 命名空间
     * @return
     */
    public static Class getClass(String namespace) {
        if (namespace != null && namespace.trim().length() > 0) {
            try {
                return Class.forName(namespace);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据属性名及命名空间，获取属性
     *
     * @param fieldName 属性名
     * @param clazz     class
     * @return
     */
    public static Field getField(String fieldName, Class clazz) {
        if (fieldName != null && fieldName.trim().length() > 0 && clazz != null) {
            for (Class c = clazz; c != Object.class; c = c.getSuperclass()) {
                try {
                    return c.getDeclaredField(fieldName);
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    /**
     * 根据属性名及命名空间，获取get、set方法
     *
     * @param fieldName 属性名
     * @param type      方法类型， get/set
     * @param clazz     class
     */
    public static Method getMethod(String fieldName, String type, Class clazz) {
        if (fieldName != null && fieldName.trim().length() > 0 && ("get".equals(type) || "set".equals(type)) && clazz != null) {
            PropertyDescriptor descriptor = null;
            try {
                descriptor = new PropertyDescriptor(fieldName, clazz);
                if ("get".equals(type)) {
                    return descriptor.getReadMethod();
                } else {
                    return descriptor.getWriteMethod();
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
