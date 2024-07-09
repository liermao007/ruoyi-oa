/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.Category;

/**
 * 栏目DAO接口
 * @author oa
 * @version 2013-8-23
 */
@MyBatisDao
public interface CategoryDao extends TreeDao<Category> {
	
	public List<Category> findModule(Category category);
	
//	public List<Category> findByParentIdsLike(Category category);
//	{
//		return find("from Category where parentIds like :p1", new Parameter(parentIds));
//	}

	public List<Category> findByModule(String module);
//	{
//		return find("from Category where delFlag=:p1 and (module='' or module=:p2) order by site.id, sort", 
//				new Parameter(Category.DEL_FLAG_NORMAL, module));
//	}
	
	public List<Category> findByParentId(String parentId, String isMenu);
//	{
//		return find("from Category where delFlag=:p1 and parent.id=:p2 and inMenu=:p3 order by site.id, sort", 
//				new Parameter(Category.DEL_FLAG_NORMAL, parentId, isMenu));
//	}

	public List<Category> findByParentIdAndSiteId(Category entity);
	
	public List<Map<String, Object>> findStats(String sql);
	
}
