/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.RoleListType;

/**
 * 用户Entity
 *
 * @author oa
 * @version 2013-12-05
 */
public class User extends DataEntity<User> {

    private static final long serialVersionUID = 1L;
    private Office company;    // 归属公司
    private Office office;    // 归属部门
    private String loginName;// 登录名
    private String password;// 密码
    private String no;        // 工号
    private String grade;    //职务级别
    private String name;    // 姓名
    private String email;    // 邮箱
    private String phone;    // 电话
    private String mobile;    // 手机
    private String userType;// 用户类型
    private String loginIp;    // 最后登陆IP
    private Date loginDate;    // 最后登陆日期
    private String loginFlag;    // 是否允许登陆
    private String photo;    // 头像
    private String officeName;
    private String flag;   //标记
    private String leaderShip;   //直属领导
    private String cardNo;   //身份证号
    private String hrmFlag; //标识hrm中的id是否存在
    private String workPost;  //工作岗位

    private String companyId;
    private String officeId;

    private String oldLoginName;// 原登录名
    private String newPassword;    // 新密码

    private String oldLoginIp;    // 上次登陆IP
    private Date oldLoginDate;    // 上次登陆日期

    private Role role;    // 根据角色查询用户条件
    private String leaderName;  //直属领导的名字
    private String acName;   //直属领导登录名

    private String dept;         //部门领导id
    private String deptName;     //部门领导姓名
    private String acDeptName;    //部门领导登录名


    private String level2;         //部门领导id
    private String acLevel2;     //部门领导姓名
    private String level2Name;    //部门领导登录名

    private String level1;         //部门领导id
    private String acLevel1;     //部门领导姓名
    private String level1Name;    //部门领导登录名
    private String userId;    //用户id
    private String roleId;    //角色id

    /**
     * 用户直属领导是否可为空
     */
    private String leaderNotNull;


    //当前用户的session id
    private String sessionId;
    //当前用户的ip地址
    private String ip;
    //当前用户第一次访问的时间
    private String firstTime;

    private String checkAll;    //是否审批人员

    private String sex;

    public String getCheckAll() {
        return checkAll;
    }

    public void setCheckAll(String checkAll) {
        this.checkAll = checkAll;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }


    private String imageId;  //人脸库图片ID
    private String personUuid;  //上传人脸照片到人脸库之后返回的照片id：赵朝柱

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPersonUuid() {
        return personUuid;
    }

    public void setPersonUuid(String personUuid) {
        this.personUuid = personUuid;
    }

    private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

    public User() {
        super();
        this.loginFlag = Global.YES;
    }

    public User(String id) {
        super(id);
    }

    public User(String id, String loginName) {
        super(id);
        this.loginName = loginName;
    }

    public User(Role role) {
        super();
        this.role = role;
    }

    @ExcelField(title = "身份证号", align = 1, sort = 50)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAcDeptName() {
        return acDeptName;
    }

    public void setAcDeptName(String acDeptName) {
        this.acDeptName = acDeptName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    @ExcelField(title = "职务级别", align = 1, sort = 50)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLeaderShip() {
        return leaderShip;
    }

    public void setLeaderShip(String leaderShip) {
        this.leaderShip = leaderShip;
    }


    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getAcLevel2() {
        return acLevel2;
    }

    public void setAcLevel2(String acLevel2) {
        this.acLevel2 = acLevel2;
    }

    public String getLevel2Name() {
        return level2Name;
    }

    public void setLevel2Name(String level2Name) {
        this.level2Name = level2Name;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getAcLevel1() {
        return acLevel1;
    }

    public void setAcLevel1(String acLevel1) {
        this.acLevel1 = acLevel1;
    }

    public String getLevel1Name() {
        return level1Name;
    }

    public void setLevel1Name(String level1Name) {
        this.level1Name = level1Name;
    }

    public String getHrmFlag() {
        return hrmFlag;
    }

    public void setHrmFlag(String hrmFlag) {
        this.hrmFlag = hrmFlag;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    @SupCol(isUnique = "true", isHide = "true")
    @ExcelField(title = "ID", type = 1, align = 2, sort = 1)
    public String getId() {
        return id;
    }

    @JsonIgnore
    @NotNull(message = "归属公司不能为空")
    @ExcelField(title = "归属公司", align = 2, sort = 20)
    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    @JsonIgnore
    @NotNull(message = "归属部门不能为空")
    @ExcelField(title = "归属部门", align = 2, sort = 25)
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    @Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
    @ExcelField(title = "登录名", align = 2, sort = 30)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @JsonIgnore
    @Length(min = 1, max = 100, message = "密码长度必须介于 1 和 100 之间")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
    @ExcelField(title = "姓名", align = 2, sort = 40)
    public String getName() {
        return name;
    }

    @Length(min = 1, max = 100, message = "工号长度必须介于 1 和 100 之间")
    @ExcelField(title = "工号", align = 2, sort = 45)
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 200, message = "电话长度必须介于 1 和 200 之间")
    @ExcelField(title = "电话", align = 2, sort = 60)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 200, message = "手机长度必须介于 1 和 200 之间")
    @ExcelField(title = "手机", align = 2, sort = 70)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ExcelField(title = "备注", align = 1, sort = 900)
    public String getRemarks() {
        return remarks;
    }

    @Length(min = 0, max = 100, message = "用户类型长度必须介于 1 和 100 之间")
    @ExcelField(title = "用户类型", align = 2, sort = 80, dictType = "sys_user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @ExcelField(title = "创建时间", type = 0, align = 1, sort = 90)
    public Date getCreateDate() {
        return createDate;
    }

    @ExcelField(title = "最后登录IP", type = 1, align = 1, sort = 100)
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "最后登录日期", type = 1, align = 1, sort = 110)
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getOldLoginName() {
        return oldLoginName;
    }

    public void setOldLoginName(String oldLoginName) {
        this.oldLoginName = oldLoginName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldLoginIp() {
        if (oldLoginIp == null) {
            return loginIp;
        }
        return oldLoginIp;
    }

    public void setOldLoginIp(String oldLoginIp) {
        this.oldLoginIp = oldLoginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOldLoginDate() {
        if (oldLoginDate == null) {
            return loginDate;
        }
        return oldLoginDate;
    }

    public void setOldLoginDate(Date oldLoginDate) {
        this.oldLoginDate = oldLoginDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    @ExcelField(title = "拥有角色", align = 1, sort = 800, fieldType = RoleListType.class)
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @JsonIgnore
    public List<String> getRoleIdList() {
        List<String> roleIdList = Lists.newArrayList();
        for (Role role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        roleList = Lists.newArrayList();
        for (String roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    public String getRoleNames() {
        return Collections3.extractToString(roleList, "name", ",");
    }

    public boolean isAdmin() {
        return getLoginName() != null && getLoginName().endsWith("admin");
    }

    public boolean isSuperAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(String id) {
        return id != null && "1".equals(id);
    }

    public String getLeaderNotNull() {
        return leaderNotNull;
    }

    public void setLeaderNotNull(String leaderNotNull) {
        this.leaderNotNull = leaderNotNull;
    }

    @Override
    public String toString() {
        return id;
    }

    @Length(min = 0, max = 200)
    @ExcelField(title = "性别", align = 1, sort = 50)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}