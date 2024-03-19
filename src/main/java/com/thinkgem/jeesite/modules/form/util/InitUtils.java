package com.thinkgem.jeesite.modules.form.util;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.AddPostgradeUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 组件初始化值
 * @author oa
 * @version 2016-12-24
 */
public class InitUtils {



    /**
     * 获取当前用户姓名
     * @return
     */
    public static String getUsername(String params){
        if(StringUtils.isNotBlank(params)){
            User user = UserUtils.getFirstUserByGrades(params);
            return user != null ? user.getName() : null;
        }
        return UserUtils.getUser().getName();
    }

    public static String getUserSex(){
        return DictUtils.getDictLabel(UserUtils.getUser().getSex(), "sex", "");
    }

    /**
     * 获取当前用户手机号
     * @return
     */
    public static String getUserphone(){
        return UserUtils.getUser().getPhone();
    }

    /**
     * 获取当前用户邮箱
     * @return
     */
    public static String getUseremail(){
        return UserUtils.getUser().getEmail();
    }

    /**
     * 获取当前用户所属单位
     * @return
     */
    public static String getUserOffice(){
        User user = UserUtils.getUser();
        if(user != null && user.getOffice() != null) {
            return user.getOffice().getName();
        }
        return "";
    }

    /**
     * 获取当前用户所属的公司
     * @return
     */
    public static  String getUserCompany(){
        User user = UserUtils.getUser();
        if(user != null && user.getCompany() != null) {
            return user.getCompany().getName();
        }
        return "";
    }

    /**
     * 获取当前用户身份证
     * @return
     */
    public static  String getUserCardNo(){
        return UserUtils.getUser().getCardNo();
    }

    public static  String getUserGrade(){
        return AddPostgradeUtils.getDictLabels(UserUtils.getUser().getGrade(), "post_grade", "");
    }

    /**
     * 获取当前用户角色名称
     * @return
     */
    public static String getUserRoles(){
        User user = UserUtils.getUser();
        if(user != null) {
            List<Role> roles = user.getRoleList();
            StringBuilder sb = new StringBuilder("");
            if(roles != null && roles.size() > 0) {
                String split = "";
                for(Role r : roles) {
                    sb.append(split + r.getName());
                    split = ",";
                }
            }
            return sb.toString();
        }
        return "";
    }
}
