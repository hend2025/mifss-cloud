package com.aeye.mifss.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {

    boolean saveRpc(T entity);

    boolean saveBatchRpc(Collection<T> entityList);

    boolean saveBatchRpc(Collection<T> entityList, int batchSize);

    boolean saveOrUpdateBatchRpc(Collection<T> entityList);

    boolean saveOrUpdateBatchRpc(Collection<T> entityList, int batchSize);

    boolean removeByIdRpc(Serializable id);

    boolean removeRpc(Wrapper<T> queryWrapper);

    boolean removeByIdsRpc(Collection<? extends Serializable> idList);

    boolean updateByIdRpc(T entity);

    boolean updateRpc(Wrapper<T> updateWrapper);

    boolean updateRpc(T entity, Wrapper<T> updateWrapper);

    boolean updateBatchByIdRpc(Collection<T> entityList);

    boolean updateBatchByIdRpc(Collection<T> entityList, int batchSize);

    boolean saveOrUpdateRpc(T entity);

    T getByIdRpc(Serializable id);

    List<T> listByIdsRpc(Collection<? extends Serializable> idList);

    T getOneRpc(Wrapper<T> queryWrapper);

    T getOneRpc(Wrapper<T> queryWrapper, boolean throwEx);

    long countRpc();

    long countRpc(Wrapper<T> queryWrapper);

    List<T> listRpc(Wrapper<T> queryWrapper);

    List<T> listRpc(IPage<T> page, Wrapper<T> queryWrapper);

    List<T> listRpc(IPage<T> page);

    <E extends IPage<T>> E pageRpc(E page, Wrapper<T> queryWrapper);

    <E extends IPage<T>> E pageRpc(E page);

    <E extends IPage<Map<String, Object>>> E pageMapsRpc(E page);

    boolean saveOrUpdateRpc(T entity, Wrapper<T> updateWrapper);

}