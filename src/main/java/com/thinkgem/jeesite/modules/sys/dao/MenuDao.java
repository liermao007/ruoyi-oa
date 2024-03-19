/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单DAO接口
 * @author oa
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);

    public int updateSort(Menu menu);

    public Menu insertTest(Menu menu);
    public int insertRoleMenu(Menu menu);

    public Menu selectMenuByName(@Param("name")String name);

    /**
     * 根据菜单路径模糊查询某条菜单的记录
     * @param href
     * @return
     */
    public List<Menu> getByHref(@Param("href") String href);
	
}
