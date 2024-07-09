package com.thinkgem.jeesite.common;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.xian.common.utils.MapstructUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * service 基础接口实现类
 *
 */
public class BaseServiceImpl<M extends BaseMapperPlus<T, V>, T, V, B> implements IBaseService<V, B> {

    @Autowired
    protected M mapper;

    @Override
    public V selectById(Long id) {
        return mapper.selectVoById(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public int deleteById(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public int insert(B bo) {
        Class<T> tClass = (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseServiceImpl.class, 1);
        T entity = MapstructUtils.convert(bo, tClass);
        return mapper.insert(entity);
    }

    @Override
    public int update(B bo) {
        Class<T> tClass = (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseServiceImpl.class, 1);
        T entity = MapstructUtils.convert(bo, tClass);
        return mapper.updateById(entity);
    }
}
