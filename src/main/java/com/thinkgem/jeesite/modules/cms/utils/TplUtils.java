package com.thinkgem.jeesite.modules.cms.utils;

import com.thinkgem.jeesite.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: songlai
 * Date: 13-8-22
 * Time: 上午10:23
 */
public class TplUtils {


    public static List<String> tplTrim(List<String> list, String prefix, String include, String... excludes) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(include) && !list.contains(include)) {
            if (!tplContain(excludes, include)) {
                int start = include.lastIndexOf("/");
                int end = include.lastIndexOf(".");
                if (start == -1 || end == -1) {
                    throw new RuntimeException("include not contain '/' or '.':" + include);
                }
                result.add(include.substring(start + 1, end));
            }
        }
        return result;
    }
}
