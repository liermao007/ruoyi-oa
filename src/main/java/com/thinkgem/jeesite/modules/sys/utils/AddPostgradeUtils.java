/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.AddDictDao;
import com.thinkgem.jeesite.modules.sys.dao.AddPostgradeDao;
import com.thinkgem.jeesite.modules.sys.entity.AddDict;
import com.thinkgem.jeesite.modules.sys.entity.AddPostgrade;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author oa
 * @version 2013-5-29
 *
 */
public class AddPostgradeUtils {
	
	private static AddPostgradeDao dictDao = SpringContextHolder.getBean(AddPostgradeDao.class);

	public static final String CACHE_ADDPOSTGRADE_MAP = "addpostgradeMap";
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (AddPostgrade dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (AddPostgrade dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}

	public static List<AddPostgrade> getDictList(String type){
		Map<String, List<AddPostgrade>> dictMap = (Map<String, List<AddPostgrade>>)CacheUtils.get(CACHE_ADDPOSTGRADE_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (AddPostgrade dict : dictDao.findAllList(new AddPostgrade())){
				List<AddPostgrade> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_ADDPOSTGRADE_MAP, dictMap);
		}
		List<AddPostgrade> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}

	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}
	
}
