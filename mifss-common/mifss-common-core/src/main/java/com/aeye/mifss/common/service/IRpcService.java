package com.aeye.mifss.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * RPC 服务接口，所有方法只使用 DTO 类型，方法名后缀为 Rpc
 * 实现类内部将 DTO 转换为 Entity 后调用 BO 方法
 *
 * @param <DTO> 数据传输对象类型
 */
public interface IRpcService<DTO> {

    // ==================== Save 操作 ====================

    /**
     * 插入一条记录
     */
    boolean saveRpc(DTO dto);

    /**
     * 批量插入
     */
    boolean saveBatchRpc(Collection<DTO> dtoList);

    /**
     * 批量插入（指定批次大小）
     */
    boolean saveBatchRpc(Collection<DTO> dtoList, int batchSize);

    /**
     * 批量修改插入
     */
    boolean saveOrUpdateBatchRpc(Collection<DTO> dtoList);

    /**
     * 批量修改插入（指定批次大小）
     */
    boolean saveOrUpdateBatchRpc(Collection<DTO> dtoList, int batchSize);

    // ==================== Remove 操作 ====================

    /**
     * 根据 ID 删除
     */
    boolean removeByIdRpc(Serializable id);

    /**
     * 根据 columnMap 条件删除
     */
    boolean removeByMapRpc(Map<String, Object> columnMap);

    /**
     * 根据 ID 批量删除
     */
    boolean removeByIdsRpc(Collection<? extends Serializable> idList);

    /**
     * 根据条件删除
     */
    boolean removeRpc(Wrapper<DTO> queryWrapper);

    // ==================== Update 操作 ====================

    /**
     * 根据 ID 更新
     */
    boolean updateByIdRpc(DTO dto);

    /**
     * 根据 ID 批量更新
     */
    boolean updateBatchByIdRpc(Collection<DTO> dtoList);

    /**
     * 根据 ID 批量更新（指定批次大小）
     */
    boolean updateBatchByIdRpc(Collection<DTO> dtoList, int batchSize);

    /**
     * 保存或更新
     */
    boolean saveOrUpdateRpc(DTO dto);

    /**
     * 根据条件更新
     */
    boolean updateRpc(Wrapper<DTO> updateWrapper);

    /**
     * 根据条件更新
     */
    boolean updateRpc(DTO dto, Wrapper<DTO> updateWrapper);

    /**
     * 根据条件保存或更新
     */
    boolean saveOrUpdateRpc(DTO dto, Wrapper<DTO> updateWrapper);

    // ==================== Get 查询操作 ====================

    /**
     * 根据 ID 查询
     */
    DTO getByIdRpc(Serializable id);

    /**
     * 根据条件查询一条记录
     */
    DTO getOneRpc(Wrapper<DTO> queryWrapper);

    /**
     * 根据条件查询一条记录
     */
    DTO getOneRpc(Wrapper<DTO> queryWrapper, boolean throwEx);

    // ==================== List 查询操作 ====================

    /**
     * 根据 ID 批量查询
     */
    List<DTO> listByIdsRpc(Collection<? extends Serializable> idList);

    /**
     * 根据 columnMap 查询
     */
    List<DTO> listByMapRpc(Map<String, Object> columnMap);

    /**
     * 查询所有
     */
    List<DTO> listRpc();

    /**
     * 根据条件查询列表
     */
    List<DTO> listRpc(Wrapper<DTO> queryWrapper);

    // ==================== Page 分页查询操作 ====================

    /**
     * 无条件分页查询
     */
    IPage<DTO> pageRpc(IPage<DTO> page);

    /**
     * 分页查询
     */
    IPage<DTO> pageRpc(IPage<DTO> page, Wrapper<DTO> queryWrapper);

    // ==================== Count 统计操作 ====================

    /**
     * 查询总记录数
     */
    long countRpc();

    /**
     * 根据条件查询记录数
     */
    long countRpc(Wrapper<DTO> queryWrapper);

}
