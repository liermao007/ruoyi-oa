/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.sys.dao.AddDictDao;
import com.thinkgem.jeesite.modules.sys.dao.AddPostgradeDao;
import com.thinkgem.jeesite.modules.sys.entity.AddDict;
import com.thinkgem.jeesite.modules.sys.entity.AddPostgrade;
import com.thinkgem.jeesite.modules.sys.utils.AddDictUtils;
import com.thinkgem.jeesite.modules.sys.utils.AddPostgradeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典Service
 * @author oa
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AddPostgradeService extends CrudService<AddPostgradeDao, AddPostgrade> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new AddPostgrade());
	}


    @Autowired
    private AddPostgradeDao adddPostgradeDao;
	@Transactional(readOnly = false)
	public void save(AddPostgrade addDict) {
		super.save(addDict);
		CacheUtils.remove(AddPostgradeUtils.CACHE_ADDPOSTGRADE_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(AddPostgrade addDict) {
		super.delete(addDict);
		CacheUtils.remove(AddPostgradeUtils.CACHE_ADDPOSTGRADE_MAP);
	}

    @Transactional(readOnly = false)
    public List<AddPostgrade> findAlllist(AddPostgrade add) {
     return adddPostgradeDao.findAllList(add);
    }

}
