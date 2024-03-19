/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.sys.dao.AddDictDao;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.AddDict;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.AddDictUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
public class AddDictService extends CrudService<AddDictDao, AddDict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new AddDict());
	}

	@Transactional(readOnly = false)
	public void save(AddDict addDict) {
		super.save(addDict);
		CacheUtils.remove(AddDictUtils.CACHE_ADDDICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(AddDict addDict) {
		super.delete(addDict);
		CacheUtils.remove(AddDictUtils.CACHE_ADDDICT_MAP);
	}

}
