package com.aeye.mifss.common.service.impl;

import com.aeye.mifss.common.service.ILocalService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 本地服务实现基类，通过注入 BO 实现 CRUD 操作
 *
 * @param <BO>     BO 类型，必须继承 IService
 * @param <Entity> 实体类型
 */
public class LocalServiceImpl<BO extends IService<Entity>, Entity> implements ILocalService<BO, Entity> {

    @Resource
    protected BO bo;

    // ==================== Save 操作 ====================

    @Override
    public boolean save(Entity entity) {
        return bo.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Entity> entityList) {
        return bo.saveBatch(entityList);
    }

    @Override
    public boolean saveBatch(Collection<Entity> entityList, int batchSize) {
        return bo.saveBatch(entityList, batchSize);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Entity> entityList) {
        return bo.saveOrUpdateBatch(entityList);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Entity> entityList, int batchSize) {
        return bo.saveOrUpdateBatch(entityList, batchSize);
    }

    // ==================== Remove 操作 ====================

    @Override
    public boolean removeById(Serializable id) {
        return bo.removeById(id);
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return bo.removeByMap(columnMap);
    }

    @Override
    public boolean remove(Wrapper<Entity> queryWrapper) {
        return bo.remove(queryWrapper);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return bo.removeByIds(idList);
    }

    // ==================== Update 操作 ====================

    @Override
    public boolean updateById(Entity entity) {
        return bo.updateById(entity);
    }

    @Override
    public boolean update(Wrapper<Entity> updateWrapper) {
        return bo.update(updateWrapper);
    }

    @Override
    public boolean update(Entity entity, Wrapper<Entity> updateWrapper) {
        return bo.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<Entity> entityList) {
        return bo.updateBatchById(entityList);
    }

    @Override
    public boolean updateBatchById(Collection<Entity> entityList, int batchSize) {
        return bo.updateBatchById(entityList, batchSize);
    }

    @Override
    public boolean saveOrUpdate(Entity entity) {
        return bo.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdate(Entity entity, Wrapper<Entity> updateWrapper) {
        return bo.saveOrUpdate(entity, updateWrapper);
    }

    // ==================== Get 查询操作 ====================

    @Override
    public Entity getById(Serializable id) {
        return bo.getById(id);
    }

    @Override
    public Entity getOne(Wrapper<Entity> queryWrapper) {
        return bo.getOne(queryWrapper);
    }

    @Override
    public Entity getOne(Wrapper<Entity> queryWrapper, boolean throwEx) {
        return bo.getOne(queryWrapper, throwEx);
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Entity> queryWrapper) {
        return bo.getMap(queryWrapper);
    }

    @Override
    public <V> V getObj(Wrapper<Entity> queryWrapper, Function<? super Object, V> mapper) {
        return bo.getObj(queryWrapper, mapper);
    }

    // ==================== List 查询操作 ====================

    @Override
    public List<Entity> listByIds(Collection<? extends Serializable> idList) {
        return bo.listByIds(idList);
    }

    @Override
    public List<Entity> listByMap(Map<String, Object> columnMap) {
        return bo.listByMap(columnMap);
    }

    @Override
    public List<Entity> list(Wrapper<Entity> queryWrapper) {
        return bo.list(queryWrapper);
    }

    @Override
    public List<Entity> list() {
        return bo.list();
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<Entity> queryWrapper) {
        return bo.listMaps(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMaps() {
        return bo.listMaps();
    }

    @Override
    public List<Object> listObjs() {
        return bo.listObjs();
    }

    @Override
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return bo.listObjs(mapper);
    }

    @Override
    public List<Object> listObjs(Wrapper<Entity> queryWrapper) {
        return bo.listObjs(queryWrapper);
    }

    @Override
    public <V> List<V> listObjs(Wrapper<Entity> queryWrapper, Function<? super Object, V> mapper) {
        return bo.listObjs(queryWrapper, mapper);
    }

    // ==================== Page 分页查询操作 ====================

    @Override
    public <E extends IPage<Entity>> E page(E page, Wrapper<Entity> queryWrapper) {
        return bo.page(page, queryWrapper);
    }

    @Override
    public <E extends IPage<Entity>> E page(E page) {
        return bo.page(page);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<Entity> queryWrapper) {
        return bo.pageMaps(page, queryWrapper);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page) {
        return bo.pageMaps(page);
    }

    // ==================== Count 统计操作 ====================

    @Override
    public long count() {
        return bo.count();
    }

    @Override
    public long count(Wrapper<Entity> queryWrapper) {
        return bo.count(queryWrapper);
    }

    // ==================== Mapper 相关 ====================

    @Override
    public BaseMapper<Entity> getBaseMapper() {
        return bo.getBaseMapper();
    }

    @Override
    public Class<Entity> getEntityClass() {
        return bo.getEntityClass();
    }

}
