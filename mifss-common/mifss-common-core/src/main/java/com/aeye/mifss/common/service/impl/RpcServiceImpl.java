package com.aeye.mifss.common.service.impl;

import com.aeye.mifss.common.service.IRpcService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RPC 服务实现基类，通过注入 BO 实现 CRUD 操作
 * 所有方法使用 DTO 类型，内部进行 DTO-Entity 转换
 *
 * @param <BO>     BO 类型，必须继承 IService
 * @param <Entity> 实体类型
 * @param <DTO>    数据传输对象类型
 */
public class RpcServiceImpl<BO extends IService<Entity>, Entity, DTO> implements IRpcService<DTO> {

    @Resource
    protected BO bo;

    /**
     * 获取 Entity 的 Class 类型
     */
    protected Class<Entity> currentEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * 获取 DTO 的 Class 类型
     */
    protected Class<DTO> currentDtoClass() {
        return (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

    // ==================== 转换方法 ====================

    /**
     * DTO 转 Entity
     */
    protected Entity toEntity(DTO dto) {
        if (dto == null) {
            return null;
        }
        try {
            Entity entity = currentEntityClass().getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("DTO to Entity conversion failed", e);
        }
    }

    /**
     * Entity 转 DTO
     */
    protected DTO toDto(Entity entity) {
        if (entity == null) {
            return null;
        }
        try {
            DTO dto = currentDtoClass().getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Entity to DTO conversion failed", e);
        }
    }

    /**
     * DTO 集合转 Entity 集合
     */
    protected List<Entity> toEntityList(Collection<DTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * Entity 集合转 DTO 集合
     */
    protected List<DTO> toDtoList(List<Entity> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Wrapper<DTO> 转 Wrapper<Entity>
     * 由于 Wrapper 内部存储的是 SQL 字符串条件，只要 DTO 和 Entity 字段名一致即可直接转换
     */
    protected Wrapper<Entity> toEntityWrapper(Wrapper<DTO> dtoWrapper) {
        if (dtoWrapper == null) {
            return null;
        }
        // Wrapper 内部使用字符串存储条件，字段名相同时可以直接强转
        return (Wrapper<Entity>) (Wrapper<?>) dtoWrapper;
    }

    // ==================== Save 操作 ====================

    @Override
    public boolean saveRpc(DTO dto) {
        return bo.save(toEntity(dto));
    }

    @Override
    public boolean saveBatchRpc(Collection<DTO> dtoList) {
        return bo.saveBatch(toEntityList(dtoList));
    }

    @Override
    public boolean saveBatchRpc(Collection<DTO> dtoList, int batchSize) {
        return bo.saveBatch(toEntityList(dtoList), batchSize);
    }

    @Override
    public boolean saveOrUpdateBatchRpc(Collection<DTO> dtoList) {
        return bo.saveOrUpdateBatch(toEntityList(dtoList));
    }

    @Override
    public boolean saveOrUpdateBatchRpc(Collection<DTO> dtoList, int batchSize) {
        return bo.saveOrUpdateBatch(toEntityList(dtoList), batchSize);
    }

    // ==================== Remove 操作 ====================

    @Override
    public boolean removeByIdRpc(Serializable id) {
        return bo.removeById(id);
    }

    @Override
    public boolean removeByMapRpc(Map<String, Object> columnMap) {
        return bo.removeByMap(columnMap);
    }

    @Override
    public boolean removeByIdsRpc(Collection<? extends Serializable> idList) {
        return bo.removeByIds(idList);
    }

    @Override
    public boolean removeRpc(Wrapper<DTO> queryWrapper) {
        return bo.remove(toEntityWrapper(queryWrapper));
    }

    // ==================== Update 操作 ====================

    @Override
    public boolean updateByIdRpc(DTO dto) {
        return bo.updateById(toEntity(dto));
    }

    @Override
    public boolean updateBatchByIdRpc(Collection<DTO> dtoList) {
        return bo.updateBatchById(toEntityList(dtoList));
    }

    @Override
    public boolean updateBatchByIdRpc(Collection<DTO> dtoList, int batchSize) {
        return bo.updateBatchById(toEntityList(dtoList), batchSize);
    }

    @Override
    public boolean saveOrUpdateRpc(DTO dto) {
        return bo.saveOrUpdate(toEntity(dto));
    }

    @Override
    public boolean updateRpc(Wrapper<DTO> updateWrapper) {
        return bo.update(toEntityWrapper(updateWrapper));
    }

    @Override
    public boolean updateRpc(DTO dto, Wrapper<DTO> updateWrapper) {
        return bo.update(toEntity(dto), toEntityWrapper(updateWrapper));
    }

    @Override
    public boolean saveOrUpdateRpc(DTO dto, Wrapper<DTO> updateWrapper) {
        return bo.saveOrUpdate(toEntity(dto), toEntityWrapper(updateWrapper));
    }

    // ==================== Get 查询操作 ====================

    @Override
    public DTO getByIdRpc(Serializable id) {
        return toDto(bo.getById(id));
    }

    @Override
    public DTO getOneRpc(Wrapper<DTO> queryWrapper) {
        return toDto(bo.getOne(toEntityWrapper(queryWrapper)));
    }

    @Override
    public DTO getOneRpc(Wrapper<DTO> queryWrapper, boolean throwEx) {
        return toDto(bo.getOne(toEntityWrapper(queryWrapper), throwEx));
    }

    // ==================== List 查询操作 ====================

    @Override
    public List<DTO> listByIdsRpc(Collection<? extends Serializable> idList) {
        return toDtoList(bo.listByIds(idList));
    }

    @Override
    public List<DTO> listByMapRpc(Map<String, Object> columnMap) {
        return toDtoList(bo.listByMap(columnMap));
    }

    @Override
    public List<DTO> listRpc() {
        return toDtoList(bo.list());
    }

    @Override
    public List<DTO> listRpc(Wrapper<DTO> queryWrapper) {
        return toDtoList(bo.list(toEntityWrapper(queryWrapper)));
    }

    // ==================== Page 分页查询操作 ====================

    @Override
    public IPage<DTO> pageRpc(IPage<DTO> page) {
        // 创建 Entity 分页对象
        Page<Entity> entityPage = new Page<>(page.getCurrent(), page.getSize());
        // 执行分页查询
        IPage<Entity> resultPage = bo.page(entityPage);
        // 转换结果
        Page<DTO> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        dtoPage.setRecords(toDtoList(resultPage.getRecords()));
        return dtoPage;
    }

    @Override
    public IPage<DTO> pageRpc(IPage<DTO> page, Wrapper<DTO> queryWrapper) {
        // 创建 Entity 分页对象
        Page<Entity> entityPage = new Page<>(page.getCurrent(), page.getSize());
        // 执行分页查询
        IPage<Entity> resultPage = bo.page(entityPage, toEntityWrapper(queryWrapper));
        // 转换结果
        Page<DTO> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        dtoPage.setRecords(toDtoList(resultPage.getRecords()));
        return dtoPage;
    }

    // ==================== Count 统计操作 ====================

    @Override
    public long countRpc() {
        return bo.count();
    }

    @Override
    public long countRpc(Wrapper<DTO> queryWrapper) {
        return bo.count(toEntityWrapper(queryWrapper));
    }

}
