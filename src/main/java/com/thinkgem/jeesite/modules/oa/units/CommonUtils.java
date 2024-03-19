/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.units;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.oa.dao.MailInfoDao;
import com.thinkgem.jeesite.modules.oa.dao.OaAuditManDao;
import com.thinkgem.jeesite.modules.oa.dao.OaSummaryPermissionDao;
import com.thinkgem.jeesite.modules.oa.entity.OaAuditMan;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryDay;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.*;

/**
 * 字典工具类
 *
 * @author oa
 * @version 2013-5-29
 */
public class CommonUtils {

    private static OaAuditManDao oaAuditManDao = SpringContextHolder.getBean(OaAuditManDao.class);
    private static OaSummaryPermissionDao oaSummaryPermissionDao = SpringContextHolder.getBean(OaSummaryPermissionDao.class);
    private static MailInfoDao mailInfoDao = SpringContextHolder.getBean(MailInfoDao.class);
    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);

    public static final String CACHE_AUDIT_MAN_List = "oaAuditManAllList";
    public static final String CACHE_ALL_PERMISSION_List = "oaAllPermissionList";

    public static List<OaAuditMan> getAuditManAllList() {
        OaAuditMan oaAuditMan= new OaAuditMan();
        oaAuditMan.setCompanyId(UserUtils.getUser().getCompany().getId());
        List<OaAuditMan>   manList = oaAuditManDao.findList(oaAuditMan);

        return manList;
    }

    /**
     * 查询被评阅人
     *
     * @return
     */
    public static List<User> getAllPermission() {
        List<User> manList = (List<User>) CacheUtils.get(CACHE_ALL_PERMISSION_List);
        User user = UserUtils.getUser();
        if (manList == null) {
            manList = new ArrayList<>();
            String loginId = user.getId();
            List<OaSummaryPermission> list = oaSummaryPermissionDao.findListByLoginId(loginId);
            for (int i = 0; i < list.size(); i++) {
                User u = userDao.get(list.get(i).getEvaluateId());
                manList.add(u);
            }
        }
        return manList;
    }

    /**
     * 查询公司所有的人
     *
     * @return
     */
    public static List<User> getPhone() {
        List<User> manList = (List<User>) CacheUtils.get(CACHE_ALL_PERMISSION_List);
        if (manList == null) {
            manList = new ArrayList<>();
            List<User> list = mailInfoDao.getPhone(new User());
            for (int i = 0; i < list.size(); i++) {
                User u = list.get(i);
                manList.add(u);
            }
        }
        return manList;
    }


    public static Map<String, Object> mapConvert(Map map) {
        Map<String, Object> dataMap = new HashMap<String, Object>(0);
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object ok = entry.getKey();
                Object ov = entry.getValue() == null ? "" : entry.getValue();
                String key = ok.toString();

                String keyval = "";
                String[] value = new String[1];
                if (ov instanceof String[]) {
                    value = (String[]) ov;
                } else {
                    value[0] = ov.toString();
                }

                keyval += value[0];

                //将数字转化为大写数字
                if (key.endsWith("DX")) {
                    if (StringUtils.isNotEmpty(keyval) && keyval.matches("^[0.0-9.0]+$")) {
                        double l;
                        String s = new String(keyval);
                        l = Double.parseDouble(s);
                       s= RmbUtils.change(l);
                        keyval = "";
                        keyval = s;
                    }
                }

                //判断领款人的长度
                if (key.equals("LKR")) {
                    if (StringUtils.isNotEmpty(keyval)) {
                        char[] lkr = keyval.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        sb.append(lkr[0]);
                        for (int i = 1; i < lkr.length; i++) {
                            if (i % 5 == 0) {
                                sb.append("<br>");
                            }
                            sb.append(lkr[i]);
                        }
                        keyval = "";
                        keyval = sb.toString();
                    }
                }


                for (int k = 1; k < value.length; k++) {
                    if (key.equals("FJMC") || key.equals("FJLJ")) {
                        break;
                    }else{
                        keyval += "," + value[k];
                    }
                }
                dataMap.put(key, keyval);
            }
        }
        return dataMap;
    }

    public static Map attributeMapFilter(Map map, String[] filterName) {
        for (int i = 0; i < filterName.length; i++) {
            if (map.containsKey(filterName[i])) map.remove(filterName[i]);
        }
        return map;
    }

}
