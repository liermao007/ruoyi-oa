/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.UserPhone;
import org.apache.ibatis.annotations.Param;

/**
 * 用户DAO接口
 * @author oa
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
     * 根据登录名称查询用户
     * @param
     * @return
     */
    public List<User> getByLoginName(User user);

    /**
     * 根据手机号称查询用户
     * @param
     * @return
     */
    public User getByMobile(User user);

    /**
     * 根据邮箱查询用户
     * @param
     * @return
     */
    public List<User> getByEmail(User user);

    /**
     * 根据身份证号查询用户
     * @param
     * @return
     */
    public List<User> getByCardNo(User user);

    /**
     * 根据姓名查询用户
     * @param
     * @return
     */
    public User getByName(User user);

    /**
     * 根据姓名查询用户集合
     * @param
     * @return
     */
    public List<User> getListByName(User user);


    /**
     * 根据姓名查询用户
     * @param
     * @return
     */
    public User findUserByRoleName(User user);

    /**
     * 根据角色名称查询对应的人员的机构id
     * @param name
     * @param
     * @return
     */
    public User findCompanyByRoleName(@Param("name") String name,@Param("acName") String acName);


    /**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);

    /**
     * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
     * @param
     * @return
     */
    public List<User> findUserByCompanyId(@Param("companyId") String companyId,@Param("delFlag") String delFlag);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordByCardNo(User user);

    /**
     * 更新用户手机号
     * @param user
     * @return
     */
    public int updatePhoneByCardNo(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);

    public int insertUserR(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

    /**
     * 更新用户状态
     * @param user
     * @return
     */
    public int updateByCardNo(User user);

    /**
     * 查询所有用户（包括已经离职的人员）
     * @param
     * @return
     */
    public List<User> findListUser(User user);

    /**
     * 查询用户
     * @param
     * @return
     */
    public User findByName(User user);

    /**
     * 查询当前登陆人所在的公司的人事
     * @param
     * @return
     */
    public List<User> findByUser(@Param("roleName") String roleName,@Param("id") String id);

    /**
     * 查询该用户是否属于此机构中
     * @param
     * @return
     */
    public User findByCompanyId(@Param("companyId") String companyId,@Param("id") String id);


    /**
     * 根据姓名，登录名模糊查询
     * @param user
     * @return
     */
    public List<User> toTaskFindListByName(User user);

    /**
     * 根据姓名，登录名模糊查询
     * @param
     * @return
     */
    public List<User> findListByName(User user);

    /**
     * 根据身份证号和机构ID来逻辑删除用户
     * @param user
     * @return
     */
    public int deleteByCardNo(User user);
	/**
	 * 根据人脸库传回的Id查询User
	 * @param personUuid
	 * @return
	 */
	public User linkFace(@Param("personUuid") String personUuid);
	/**
	 * 同步人脸识别信息
	 * @param user
	 * @return
	 */
	public void insertUserFace(User user);


    /**
     * 查询所有的人员信息。包括离职人员
     * @param
     * @return
     */
    public User getOne(String id);

    /**
     * 查询所有的离职人员
     * @param
     * @return
     */
    public  List<User> findDelList(@Param("officeId")String officeId);


    /**
     * 查询用户是否是直属领导
     * @param
     * @return
     */
    public List<User> findIsParent(@Param("id")String id,@Param("companyId")String companyId);

    /**
     * 查询用户是否是部门领导
     * @param
     * @return
     */
    public List<User> findIsDept(@Param("id")String id,@Param("companyId")String companyId);

    /**
     * 查询用户是否是一级领导
     * @param
     * @return
     */
    public List<User> findIsLeader(@Param("id")String id,@Param("companyId")String companyId);

    public Integer findIsLeaders(@Param("id")String id,@Param("companyId")String companyId);


    /**
     * 离职人员权限统一更新
     * @param
     * @return
     */
    public int updateLeaveTotal(User user);

    /**
     * 离职人员权限明细更新
     * @param
     * @return
     */
    public int updateLeave(User user);

    /**
     * 查询所有无直属领导人员
     *@param user
     * @return
     */
    List<User> findPeopleNoDept(User user);

    /**
     * 查询所有直属领导
     * @param officeId
     * @param companyId
     * @return
     */
    User findAllOfficeLeader (@Param("officeId")String officeId, @Param("companyId")String companyId);

    /**
     * 查询所有部门领导
     * @param officeId
     * @param companyId
     * @return
     */
    User findAllOfficeDept (@Param("officeId")String officeId, @Param("companyId")String companyId);
    /**
     * 查询所有一级领导
     * @param officeId
     * @param companyId
     * @return
     */
    User findAllOfficeLevel (@Param("officeId")String officeId, @Param("companyId")String companyId);
    /**
     * 查询所有部门
     *@param companyId
     * @return
     */
    List findAllOffice (String companyId);


    /**
     * 修改无直属领导人员
     * @param user
     * @return
     */
    int updateNoLeader (User user);

    List<User> selectLoginnameByGradeAndOffice(@Param("officeId")String officeId, @Param("grades")List<String> grades);

    List<UserPhone> selectAllPhone(@Param("companyId")String companyId);
}