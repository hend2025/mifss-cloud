package com.aeye.mifss.common.service;

import cn.hsa.hsaf.core.framework.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

public interface IRpcService<DTO> {

    default int countAllRpc() throws Exception {
        return this.countRpc(Wrappers.emptyWrapper());
    }

    int countRpc(QueryWrapper<DTO> queryWrapper) throws Exception;

    PageResult<DTO> pageRpc(QueryWrapper<DTO> queryWrapper) throws Exception;

    DTO saveRpc(DTO entity) throws Exception;

    DTO saveOrUpdateRpc(DTO entity) throws Exception;

    boolean saveBatchRpc(Collection<DTO> entityList) throws Exception;

    boolean removeRpc(QueryWrapper<DTO> queryWrapper) throws Exception;

    DTO getByIdRpc(@RequestParam(value = "id") String id) throws Exception;

    default DTO getOneRpc(QueryWrapper<DTO> queryWrapper) throws Exception {
        return this.getOneRpcEx(queryWrapper, false);
    }

    default DTO getOneRpc(LambdaQueryWrapper<DTO> queryWrapper) throws Exception {
        return this.getOneRpcExLambda(queryWrapper, false);
    }

    DTO getListOneRpc(QueryWrapper<DTO> queryWrapper) throws Exception;

    DTO getOneRpcEx(QueryWrapper<DTO> queryWrapper, boolean throwEx) throws Exception;

    DTO getOneRpcExLambda(LambdaQueryWrapper<DTO> queryWrapper, boolean throwEx) throws Exception;

    List<DTO> listRpc(QueryWrapper<DTO> queryWrapper) throws Exception;

    default List<DTO> listAllRpc() throws Exception {
        return this.listRpc(Wrappers.emptyWrapper());
    }

    boolean updateByIdRpc(DTO entity) throws Exception;

    boolean updateRpc(QueryWrapper<DTO> queryWrapper) throws Exception;

    boolean updateBatchByIdRpc(List<DTO> entityList) throws Exception;

}
