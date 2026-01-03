package com.aeye.mifss.common.service.impl;

import com.aeye.mifss.common.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<M extends BaseMapper<Entity>, Entity, DTO> extends ServiceImpl<M, Entity>
        implements BaseService<DTO> {

    // 获取泛型 Entity 的 Class 类型
    protected Class<Entity> currentEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    // 获取泛型 DTO 的 Class 类型
    protected Class<DTO> currentDtoClass() {
        return (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

    // DTO -> Entity
    protected Entity toEntity(DTO dto) {
        if (dto == null)
            return null;
        try {
            Entity entity = currentEntityClass().newInstance();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("DTO转Entity失败，请检查Entity是否有无参构造函数", e);
        }
    }

    // Entity -> DTO
    protected DTO toDto(Entity entity) {
        if (entity == null)
            return null;
        try {
            DTO dto = currentDtoClass().newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Entity转DTO失败，请检查DTO是否有无参构造函数", e);
        }
    }

    // List<Entity> -> List<DTO>
    protected List<DTO> toDtoList(List<Entity> entities) {
        if (entities == null)
            return null;
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Collection<DTO> -> List<Entity>
    protected List<Entity> toEntityList(Collection<DTO> dtos) {
        if (dtos == null)
            return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    // ==================== Wrapper 转换方法 ====================

    /**
     * 将 Wrapper<DTO> 转换为 QueryWrapper<Entity>
     * 利用 Wrapper 的 SQL 片段复用查询条件
     */
    @SuppressWarnings("unchecked")
    protected QueryWrapper<Entity> toEntityQueryWrapper(Wrapper<DTO> dtoWrapper) {
        if (dtoWrapper == null) {
            return null;
        }
        QueryWrapper<Entity> entityWrapper = new QueryWrapper<>();
        if (dtoWrapper instanceof AbstractWrapper) {
            AbstractWrapper<DTO, ?, ?> abstractWrapper = (AbstractWrapper<DTO, ?, ?>) dtoWrapper;
            // 复制 SQL 片段和参数
            String sqlSegment = abstractWrapper.getCustomSqlSegment();
            Map<String, Object> paramNameValuePairs = abstractWrapper.getParamNameValuePairs();
            if (sqlSegment != null && !sqlSegment.isEmpty()) {
                // 使用 apply 方法将原始 SQL 条件应用到新的 Wrapper
                entityWrapper.apply(sqlSegment.replace("ew.paramNameValuePairs.", ""),
                        paramNameValuePairs.values().toArray());
            }
        }
        return entityWrapper;
    }

    /**
     * 将 Wrapper<DTO> 转换为 UpdateWrapper<Entity>
     * 利用 Wrapper 的 SQL 片段复用查询条件
     */
    @SuppressWarnings("unchecked")
    protected UpdateWrapper<Entity> toEntityUpdateWrapper(Wrapper<DTO> dtoWrapper) {
        if (dtoWrapper == null) {
            return null;
        }
        UpdateWrapper<Entity> entityWrapper = new UpdateWrapper<>();
        if (dtoWrapper instanceof AbstractWrapper) {
            AbstractWrapper<DTO, ?, ?> abstractWrapper = (AbstractWrapper<DTO, ?, ?>) dtoWrapper;
            String sqlSegment = abstractWrapper.getCustomSqlSegment();
            Map<String, Object> paramNameValuePairs = abstractWrapper.getParamNameValuePairs();
            if (sqlSegment != null && !sqlSegment.isEmpty()) {
                entityWrapper.apply(sqlSegment.replace("ew.paramNameValuePairs.", ""),
                        paramNameValuePairs.values().toArray());
            }
        }
        return entityWrapper;
    }

    /**
     * 将 IPage<DTO> 转换为 Page<Entity>
     */
    protected Page<Entity> toEntityPage(IPage<DTO> dtoPage) {
        if (dtoPage == null) {
            return null;
        }
        return new Page<>(dtoPage.getCurrent(), dtoPage.getSize());
    }

    /**
     * 将 IPage<Entity> 转换为 IPage<DTO>，包含数据转换
     */
    @SuppressWarnings("unchecked")
    protected <E extends IPage<DTO>> E toDtoPage(IPage<Entity> entityPage, E dtoPage) {
        if (entityPage == null) {
            return null;
        }
        dtoPage.setRecords(toDtoList(entityPage.getRecords()));
        dtoPage.setTotal(entityPage.getTotal());
        dtoPage.setPages(entityPage.getPages());
        return dtoPage;
    }

    // ==================== BaseService 接口实现 ====================

    @Override
    public boolean saveRpc(DTO dto) {
        return save(toEntity(dto));
    }

    @Override
    public boolean saveBatchRpc(Collection<DTO> dtoList) {
        return saveBatch(toEntityList(dtoList));
    }

    @Override
    public boolean saveBatchRpc(Collection<DTO> dtoList, int batchSize) {
        return saveBatch(toEntityList(dtoList), batchSize);
    }

    @Override
    public boolean saveOrUpdateBatchRpc(Collection<DTO> dtoList) {
        return saveOrUpdateBatch(toEntityList(dtoList));
    }

    @Override
    public boolean saveOrUpdateBatchRpc(Collection<DTO> dtoList, int batchSize) {
        return saveOrUpdateBatch(toEntityList(dtoList), batchSize);
    }

    @Override
    public boolean removeByIdRpc(Serializable id) {
        return removeById(id);
    }

    @Override
    public boolean removeRpc(Wrapper<DTO> queryWrapper) {
        return remove(toEntityQueryWrapper(queryWrapper));
    }

    @Override
    public boolean removeByIdsRpc(Collection<? extends Serializable> idList) {
        return removeByIds(idList);
    }

    @Override
    public boolean updateByIdRpc(DTO dto) {
        return updateById(toEntity(dto));
    }

    @Override
    public boolean updateRpc(Wrapper<DTO> updateWrapper) {
        return update(toEntityUpdateWrapper(updateWrapper));
    }

    @Override
    public boolean updateRpc(DTO dto, Wrapper<DTO> updateWrapper) {
        return update(toEntity(dto), toEntityUpdateWrapper(updateWrapper));
    }

    @Override
    public boolean updateBatchByIdRpc(Collection<DTO> dtoList) {
        return updateBatchById(toEntityList(dtoList));
    }

    @Override
    public boolean updateBatchByIdRpc(Collection<DTO> dtoList, int batchSize) {
        return updateBatchById(toEntityList(dtoList), batchSize);
    }

    @Override
    public boolean saveOrUpdateRpc(DTO dto) {
        return saveOrUpdate(toEntity(dto));
    }

    @Override
    public DTO getByIdRpc(Serializable id) {
        return toDto(getById(id));
    }

    @Override
    public List<DTO> listByIdsRpc(Collection<? extends Serializable> idList) {
        return toDtoList(listByIds(idList));
    }

    @Override
    public DTO getOneRpc(Wrapper<DTO> queryWrapper) {
        return toDto(getOne(toEntityQueryWrapper(queryWrapper)));
    }

    @Override
    public DTO getOneRpc(Wrapper<DTO> queryWrapper, boolean throwEx) {
        return toDto(getOne(toEntityQueryWrapper(queryWrapper), throwEx));
    }

    @Override
    public long countRpc() {
        return count();
    }

    @Override
    public long countRpc(Wrapper<DTO> queryWrapper) {
        return count(toEntityQueryWrapper(queryWrapper));
    }

    @Override
    public List<DTO> listRpc(Wrapper<DTO> queryWrapper) {
        return toDtoList(list(toEntityQueryWrapper(queryWrapper)));
    }

    @Override
    public List<DTO> listRpc(IPage<DTO> page, Wrapper<DTO> queryWrapper) {
        Page<Entity> entityPage = toEntityPage(page);
        IPage<Entity> result = getBaseMapper().selectPage(entityPage, toEntityQueryWrapper(queryWrapper));
        return toDtoList(result.getRecords());
    }

    @Override
    public List<DTO> listRpc(IPage<DTO> page) {
        Page<Entity> entityPage = toEntityPage(page);
        IPage<Entity> result = getBaseMapper().selectPage(entityPage, null);
        return toDtoList(result.getRecords());
    }

    @Override
    public <E extends IPage<DTO>> E pageRpc(E page, Wrapper<DTO> queryWrapper) {
        Page<Entity> entityPage = toEntityPage(page);
        IPage<Entity> result = getBaseMapper().selectPage(entityPage, toEntityQueryWrapper(queryWrapper));
        return toDtoPage(result, page);
    }

    @Override
    public <E extends IPage<DTO>> E pageRpc(E page) {
        Page<Entity> entityPage = toEntityPage(page);
        IPage<Entity> result = getBaseMapper().selectPage(entityPage, null);
        return toDtoPage(result, page);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMapsRpc(E page) {
        return pageMaps(page);
    }

    @Override
    public boolean saveOrUpdateRpc(DTO dto, Wrapper<DTO> updateWrapper) {
        return saveOrUpdate(toEntity(dto), toEntityUpdateWrapper(updateWrapper));
    }

}