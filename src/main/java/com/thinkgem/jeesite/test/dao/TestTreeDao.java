/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.test.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author oa
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {

}