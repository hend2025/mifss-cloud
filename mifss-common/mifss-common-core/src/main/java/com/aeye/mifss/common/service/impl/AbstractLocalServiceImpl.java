package com.aeye.mifss.common.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.util.PageInfo;
import cn.hsa.hsaf.core.framework.util.PageResult;
import com.aeye.mifss.common.service.ILocalService;
import com.aeye.mifss.common.utils.PageUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AbstractLocalServiceImpl<Entity, BO extends IService<Entity>> extends HsafService
        implements ILocalService<Entity> {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BO boImpl;

    @Override
    public PageResult<Entity> page(PageInfo pageParam, Wrapper queryWrapper) throws Exception {
        IPage<Entity> page = boImpl.page(new Page<Entity>(pageParam.getPageNum(), pageParam.getPageSize()), queryWrapper);
        return PageUtils.pageConvert(page);
    }

    @Override
    public long count(Wrapper<Entity> queryWrapper) {
        return boImpl.count(queryWrapper);
    }

    @Override
    public boolean save(Entity entity) throws Exception {
        return boImpl.save(entity);
    }

    @Override
    public boolean saveOrUpdate(Entity entity) throws Exception {
        return boImpl.saveOrUpdate(entity);
    }

    @Override
    public boolean saveBatch(Collection<Entity> entityList) throws Exception {
        return boImpl.saveBatch(entityList);
    }

    @Override
    public Entity getById(Serializable id) throws Exception {
        return boImpl.getById(id);
    }

    @Override
    public boolean remove(Wrapper wrapper) throws Exception {
        return boImpl.remove(wrapper);
    }

    @Override
    public boolean removeById(Serializable id) throws Exception {
        if (id == null) {
            throw new NullPointerException();
        }
        return boImpl.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) throws Exception {
        return boImpl.removeByIds(idList);
    }

    @Override
    public Entity getOne(Wrapper<Entity> wrapper, boolean throwEx) throws Exception {
        return boImpl.getOne(wrapper, throwEx);
    }

    @Override
    public Entity getListOne(Wrapper<Entity> wrapper) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(1);
        pageInfo.setPageNum(1);
        Entity entity = null;
        PageResult<Entity> pageResult = this.page(pageInfo, wrapper);
        if (pageResult.getData() != null && pageResult.getData().size() > 0) {
            entity = pageResult.getData().get(0);
        }
        return entity;
    }

    @Override
    public List<Entity> list(Wrapper wrapper) throws Exception {
        return boImpl.list(wrapper);
    }

    @Override
    public boolean updateById(Entity entity) throws Exception {
        return boImpl.updateById(entity);
    }

    @Override
    public boolean update(Entity entity, Wrapper<Entity> wrapper) throws Exception {
        return boImpl.update(entity, wrapper);
    }

    @Override
    public boolean updateBatchById(List<Entity> entities) throws Exception {
        return boImpl.updateBatchById(entities);
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Entity> queryWrapper) {
        return boImpl.getMap(queryWrapper);
    };

}
