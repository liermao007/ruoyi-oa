/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色DAO接口
 * @author oa
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);

	public Role getByUserId(@Param("userId")String userId);

    public List<Role> getByRoleName(@Param("userId")String userId);

	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);

    /**
     * 通过OfficeId获取角色列表，仅返回角色id和name（树查询角色时用）
     * @param role
     * @return
     */
    public List<Role> findRoleByOfficeId(Role role);

    public List<Role> findRoleByDept(Role role);

    public List<Role> getRoleByUserId(@Param("userId")String userId);

}
