/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构DAO接口
 * @author oa
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

    /**
     * 根据机构id查询当前机构下的部门
     * @param parentId  父id   用户的机构id
     * @return
     */
    public List<Office>  findOfficeByOrgId(@Param("parentId")String parentId);

    /**
     * 根据机构id查询当前机构以及所有的下属机构
     * @param parentId  父id   用户的机构id
     * @return
     */
    public List<Office> findCompany(String parentId);

    public List<Office> findCompanyById(String parentId);

    /**
     * 人员树数据权限控制
     * @param
     * @return
     */
    public List<Office> findByCompany(Office ffice);


    /**
     * 根据机构id查询当前机构下的部门
     * @param
     * @return
     */
    public List<Office>  findOfficeUserTreeData(Office office);

    /**
     * 根据部门id查询下级部门
     * @param id  id   部门id
     * @return
     */
    public List<Office>  getIdByParentId(@Param("id")String id);

    /**
     * 查询当前部门下的所有人员的手机号码
     * @param officeId
     * @return
     */
    public List<User> findPhone(@Param("officeId") String[]  officeId);



}
