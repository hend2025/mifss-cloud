package com.aeye.mifss.base.service.impl;

import com.aeye.mifss.base.entity.ScenDicA;
import com.aeye.mifss.base.mapper.ScenDicAMapper;
import com.aeye.mifss.intf.base.dto.ScenDicADTO;
import com.aeye.mifss.intf.base.service.ScenDicAService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 场景字典代码表 Dubbo服务实现
 *
 * @author mifss
 */
@DubboService
public class ScenDicAServiceImpl implements ScenDicAService {

    @Autowired
    private ScenDicAMapper scenDicAMapper;

    @Override
    public ScenDicADTO getById(String dicId) {
        ScenDicA entity = scenDicAMapper.selectById(dicId);
        return convertToDTO(entity);
    }

    @Override
    public List<ScenDicADTO> listByDicTypeCode(String dicTypeCode) {
        LambdaQueryWrapper<ScenDicA> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScenDicA::getDicTypeCode, dicTypeCode)
                .eq(ScenDicA::getValiFlag, "1")
                .orderByAsc(ScenDicA::getSeq);
        List<ScenDicA> list = scenDicAMapper.selectList(wrapper);
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ScenDicADTO getByDicCodeAndType(String dicCode, String dicTypeCode) {
        LambdaQueryWrapper<ScenDicA> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScenDicA::getDicCode, dicCode)
                .eq(ScenDicA::getDicTypeCode, dicTypeCode)
                .eq(ScenDicA::getValiFlag, "1");
        ScenDicA entity = scenDicAMapper.selectOne(wrapper);
        return convertToDTO(entity);
    }

    @Override
    public List<ScenDicADTO> listAllValid() {
        LambdaQueryWrapper<ScenDicA> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScenDicA::getValiFlag, "1")
                .orderByAsc(ScenDicA::getDicTypeCode)
                .orderByAsc(ScenDicA::getSeq);
        List<ScenDicA> list = scenDicAMapper.selectList(wrapper);
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ScenDicADTO> listByParentCode(String prntDicCode) {
        LambdaQueryWrapper<ScenDicA> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScenDicA::getPrntDicCode, prntDicCode)
                .eq(ScenDicA::getValiFlag, "1")
                .orderByAsc(ScenDicA::getSeq);
        List<ScenDicA> list = scenDicAMapper.selectList(wrapper);
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean save(ScenDicADTO dto) {
        ScenDicA entity = convertToEntity(dto);
        return scenDicAMapper.insert(entity) > 0;
    }

    @Override
    public boolean update(ScenDicADTO dto) {
        ScenDicA entity = convertToEntity(dto);
        return scenDicAMapper.updateById(entity) > 0;
    }

    @Override
    public boolean deleteById(String dicId) {
        return scenDicAMapper.deleteById(dicId) > 0;
    }

    /**
     * 实体转DTO
     */
    private ScenDicADTO convertToDTO(ScenDicA entity) {
        if (entity == null) {
            return null;
        }
        ScenDicADTO dto = new ScenDicADTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * DTO转实体
     */
    private ScenDicA convertToEntity(ScenDicADTO dto) {
        if (dto == null) {
            return null;
        }
        ScenDicA entity = new ScenDicA();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
