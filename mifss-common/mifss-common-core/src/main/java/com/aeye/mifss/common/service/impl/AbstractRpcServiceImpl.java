package com.aeye.mifss.common.service.impl;

import cn.hsa.hsaf.core.framework.util.PageInfo;
import cn.hsa.hsaf.core.framework.util.PageResult;
import com.aeye.mifss.common.service.ILocalService;
import com.aeye.mifss.common.service.IRpcService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractRpcServiceImpl<Entity, DTO, BO extends IService<Entity>>
        extends AbstractLocalServiceImpl<Entity, BO>
        implements IRpcService<DTO>, ILocalService<Entity> {

    // ==================== 泛型类型获取 ====================

    /**
     * 获取 Entity 的 Class 类型
     */
    @SuppressWarnings("unchecked")
    protected Class<Entity> currentEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 获取 DTO 的 Class 类型
     */
    @SuppressWarnings("unchecked")
    protected Class<DTO> currentDtoClass() {
        return (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    // ==================== DTO <-> Entity 转换方法 ====================

    /**
     * DTO -> Entity
     */
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

    /**
     * Entity -> DTO
     */
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

    /**
     * List<Entity> -> List<DTO>
     */
    protected List<DTO> toDtoList(List<Entity> entities) {
        if (entities == null)
            return null;
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Collection<DTO> -> List<Entity>
     */
    protected List<Entity> toEntityList(Collection<DTO> dtos) {
        if (dtos == null)
            return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    // ==================== Wrapper 转换方法 ====================

    /**
     * 将 QueryWrapper<DTO> 转换为 QueryWrapper<Entity>
     */
    @SuppressWarnings("unchecked")
    protected QueryWrapper<Entity> toEntityQueryWrapper(QueryWrapper<DTO> dtoWrapper) {
        if (dtoWrapper == null) {
            return null;
        }
        QueryWrapper<Entity> entityWrapper = new QueryWrapper<>();
        // 复制 SQL 片段和参数
        String sqlSegment = dtoWrapper.getCustomSqlSegment();
        Map<String, Object> paramNameValuePairs = dtoWrapper.getParamNameValuePairs();
        if (sqlSegment != null && !sqlSegment.isEmpty()) {
            entityWrapper.apply(sqlSegment.replace("ew.paramNameValuePairs.", ""),
                    paramNameValuePairs.values().toArray());
        }
        return entityWrapper;
    }

    /**
     * PageResult<Entity> -> PageResult<DTO>
     */
    protected PageResult<DTO> toDtoPageResult(PageResult<Entity> entityPageResult) {
        if (entityPageResult == null)
            return null;
        PageResult<DTO> dtoPageResult = new PageResult<>();
        dtoPageResult.setData(toDtoList(entityPageResult.getData()));
        dtoPageResult.setRecordCounts(entityPageResult.getRecordCounts());
        dtoPageResult.setPageNum(entityPageResult.getPageNum());
        dtoPageResult.setPageSize(entityPageResult.getPageSize());
        dtoPageResult.setPages(entityPageResult.getPages());
        dtoPageResult.setFirstPage(entityPageResult.isFirstPage());
        dtoPageResult.setLastPage(entityPageResult.isLastPage());
        return dtoPageResult;
    }

    // ==================== IRpcService 接口实现 ====================

    @Override
    public int countRpc(QueryWrapper<DTO> queryWrapper) throws Exception {
        return (int) count(toEntityQueryWrapper(queryWrapper));
    }

    @Override
    public PageResult<DTO> pageRpc(QueryWrapper<DTO> queryWrapper) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(1);
        pageInfo.setPageSize(Integer.MAX_VALUE);
        PageResult<Entity> entityPageResult = page(pageInfo, toEntityQueryWrapper(queryWrapper));
        return toDtoPageResult(entityPageResult);
    }

    @Override
    public DTO saveRpc(DTO dto) throws Exception {
        Entity entity = toEntity(dto);
        save(entity);
        return toDto(entity);
    }

    @Override
    public DTO saveOrUpdateRpc(DTO dto) throws Exception {
        Entity entity = toEntity(dto);
        saveOrUpdate(entity);
        return toDto(entity);
    }

    @Override
    public boolean saveBatchRpc(Collection<DTO> dtoList) throws Exception {
        return saveBatch(toEntityList(dtoList));
    }

    @Override
    public boolean removeRpc(QueryWrapper<DTO> queryWrapper) throws Exception {
        return remove(toEntityQueryWrapper(queryWrapper));
    }

    @Override
    public DTO getByIdRpc(String id) throws Exception {
        return toDto(getById(id));
    }

    @Override
    public DTO getListOneRpc(QueryWrapper<DTO> queryWrapper) throws Exception {
        Entity entity = getListOne(toEntityQueryWrapper(queryWrapper));
        return toDto(entity);
    }

    @Override
    public DTO getOneRpcEx(QueryWrapper<DTO> queryWrapper, boolean throwEx) throws Exception {
        Entity entity = getOne(toEntityQueryWrapper(queryWrapper), throwEx);
        return toDto(entity);
    }

    @Override
    public DTO getOneRpcExLambda(LambdaQueryWrapper<DTO> queryWrapper, boolean throwEx) throws Exception {
        // LambdaQueryWrapper 可以直接获取 SQL 片段，转换为 Entity 的 QueryWrapper
        QueryWrapper<Entity> entityWrapper = new QueryWrapper<>();
        String sqlSegment = queryWrapper.getCustomSqlSegment();
        Map<String, Object> paramNameValuePairs = queryWrapper.getParamNameValuePairs();
        if (sqlSegment != null && !sqlSegment.isEmpty()) {
            entityWrapper.apply(sqlSegment.replace("ew.paramNameValuePairs.", ""),
                    paramNameValuePairs.values().toArray());
        }
        Entity entity = getOne(entityWrapper, throwEx);
        return toDto(entity);
    }

    @Override
    public List<DTO> listRpc(QueryWrapper<DTO> queryWrapper) throws Exception {
        List<Entity> entities = list(toEntityQueryWrapper(queryWrapper));
        return toDtoList(entities);
    }

    @Override
    public boolean updateByIdRpc(DTO dto) throws Exception {
        return updateById(toEntity(dto));
    }

    @Override
    public boolean updateRpc(QueryWrapper<DTO> queryWrapper) throws Exception {
        // 将 QueryWrapper 转为 UpdateWrapper 来实现更新
        UpdateWrapper<Entity> updateWrapper = new UpdateWrapper<>();
        String sqlSegment = queryWrapper.getCustomSqlSegment();
        Map<String, Object> paramNameValuePairs = queryWrapper.getParamNameValuePairs();
        if (sqlSegment != null && !sqlSegment.isEmpty()) {
            updateWrapper.apply(sqlSegment.replace("ew.paramNameValuePairs.", ""),
                    paramNameValuePairs.values().toArray());
        }
        return boImpl.update(updateWrapper);
    }

    @Override
    public boolean updateBatchByIdRpc(List<DTO> dtoList) throws Exception {
        return updateBatchById(toEntityList(dtoList));
    }

}
