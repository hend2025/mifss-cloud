package com.aeye.mifss.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 本地服务接口，通过委托给 BO（继承自 IService）来实现 CRUD 操作
 *
 * @param <BO>     BO 类型，必须继承 IService
 * @param <Entity> 实体类型
 */
public interface ILocalService<BO extends IService<Entity>, Entity> {

    // ==================== Save 操作 ====================

    boolean save(Entity entity);

    boolean saveBatch(Collection<Entity> entityList);

    boolean saveBatch(Collection<Entity> entityList, int batchSize);

    boolean saveOrUpdateBatch(Collection<Entity> entityList);

    boolean saveOrUpdateBatch(Collection<Entity> entityList, int batchSize);

    // ==================== Remove 操作 ====================

    boolean removeById(Serializable id);

    boolean removeByMap(Map<String, Object> columnMap);

    boolean remove(Wrapper<Entity> queryWrapper);

    boolean removeByIds(Collection<? extends Serializable> idList);

    // ==================== Update 操作 ====================

    boolean updateById(Entity entity);

    boolean update(Wrapper<Entity> updateWrapper);

    boolean update(Entity entity, Wrapper<Entity> updateWrapper);

    boolean updateBatchById(Collection<Entity> entityList);

    boolean updateBatchById(Collection<Entity> entityList, int batchSize);

    boolean saveOrUpdate(Entity entity);

    boolean saveOrUpdate(Entity entity, Wrapper<Entity> updateWrapper);

    // ==================== Get 查询操作 ====================

    Entity getById(Serializable id);

    Entity getOne(Wrapper<Entity> queryWrapper);

    Entity getOne(Wrapper<Entity> queryWrapper, boolean throwEx);

    Map<String, Object> getMap(Wrapper<Entity> queryWrapper);

    <V> V getObj(Wrapper<Entity> queryWrapper, Function<? super Object, V> mapper);

    // ==================== List 查询操作 ====================

    List<Entity> listByIds(Collection<? extends Serializable> idList);

    List<Entity> listByMap(Map<String, Object> columnMap);

    List<Entity> list(Wrapper<Entity> queryWrapper);

    List<Entity> list();

    List<Map<String, Object>> listMaps(Wrapper<Entity> queryWrapper);

    List<Map<String, Object>> listMaps();

    List<Object> listObjs();

    <V> List<V> listObjs(Function<? super Object, V> mapper);

    List<Object> listObjs(Wrapper<Entity> queryWrapper);

    <V> List<V> listObjs(Wrapper<Entity> queryWrapper, Function<? super Object, V> mapper);

    // ==================== Page 分页查询操作 ====================

    <E extends IPage<Entity>> E page(E page, Wrapper<Entity> queryWrapper);

    <E extends IPage<Entity>> E page(E page);

    <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<Entity> queryWrapper);

    <E extends IPage<Map<String, Object>>> E pageMaps(E page);

    // ==================== Count 统计操作 ====================

    long count();

    long count(Wrapper<Entity> queryWrapper);

    // ==================== Mapper 相关 ====================

    BaseMapper<Entity> getBaseMapper();

    Class<Entity> getEntityClass();

}
