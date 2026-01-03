package com.aeye.mifss.common.service;

import cn.hsa.hsaf.core.framework.util.PageInfo;
import cn.hsa.hsaf.core.framework.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ILocalService<Entity>{

    PageResult<Entity> page(PageInfo page, Wrapper<Entity> queryWrapper) throws Exception;

    boolean save(Entity entity) throws Exception;

    boolean saveOrUpdate(Entity entity) throws Exception;

    boolean saveBatch(Collection<Entity> entityList) throws Exception;

    Entity getById(Serializable id) throws Exception;

    boolean remove(Wrapper<Entity> wrapper) throws Exception;

    boolean removeById(Serializable id) throws Exception;

    boolean removeByIds(Collection<? extends Serializable> idList) throws Exception;

    Entity getOne(Wrapper<Entity> wrapper, boolean throwEx) throws Exception;

    default Entity getOne(Wrapper<Entity> wrapper) throws Exception{
        return getOne(wrapper, true);
    }

    Entity getListOne(Wrapper<Entity> wrapper) throws Exception;

    List<Entity> list(Wrapper<Entity> wrapper) throws Exception;

    boolean updateById(Entity entity) throws Exception;

    boolean update(Entity entity, Wrapper<Entity> wrapper) throws Exception;

    boolean updateBatchById(List<Entity> entities) throws Exception;

    default long count() {
        return this.count(Wrappers.emptyWrapper());
    }

    long count(Wrapper<Entity> queryWrapper);

    Map getMap(Wrapper<Entity> queryWrapper);

}