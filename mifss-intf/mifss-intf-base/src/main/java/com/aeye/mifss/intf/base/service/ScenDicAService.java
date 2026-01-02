package com.aeye.mifss.intf.base.service;

import com.aeye.mifss.intf.base.dto.ScenDicADTO;
import java.util.List;

/**
 * 场景字典代码表 Dubbo服务接口
 *
 * @author mifss
 */
public interface ScenDicAService {

    /**
     * 根据ID查询字典
     *
     * @param dicId 字典ID
     * @return 字典信息
     */
    ScenDicADTO getById(String dicId);

    /**
     * 根据字典类型代码查询字典列表
     *
     * @param dicTypeCode 字典类型代码
     * @return 字典列表
     */
    List<ScenDicADTO> listByDicTypeCode(String dicTypeCode);

    /**
     * 根据字典代码和类型代码查询字典
     *
     * @param dicCode     字典代码
     * @param dicTypeCode 字典类型代码
     * @return 字典信息
     */
    ScenDicADTO getByDicCodeAndType(String dicCode, String dicTypeCode);

    /**
     * 查询所有有效字典
     *
     * @return 字典列表
     */
    List<ScenDicADTO> listAllValid();

    /**
     * 根据父级字典代码查询子级字典
     *
     * @param prntDicCode 父级字典代码
     * @return 子级字典列表
     */
    List<ScenDicADTO> listByParentCode(String prntDicCode);

    /**
     * 新增字典
     *
     * @param dto 字典信息
     * @return 是否成功
     */
    boolean save(ScenDicADTO dto);

    /**
     * 更新字典
     *
     * @param dto 字典信息
     * @return 是否成功
     */
    boolean update(ScenDicADTO dto);

    /**
     * 删除字典
     *
     * @param dicId 字典ID
     * @return 是否成功
     */
    boolean deleteById(String dicId);
}
