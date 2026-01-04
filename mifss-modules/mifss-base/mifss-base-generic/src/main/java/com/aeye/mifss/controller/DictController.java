package com.aeye.mifss.controller;

import com.aeye.mifss.base.entity.ScenDicEntity;
import com.aeye.mifss.base.service.ScenDicService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/dict")
public class DictController {

    @Resource
    private ScenDicService scenDicService;

    @GetMapping("/{dicId}")
    public ScenDicEntity getById(@PathVariable("dicId") String dicId) throws Exception {

        System.out.printf("getDicEntityById dicId=%s\n", dicId);

        ScenDicEntity dto = scenDicService.getOne(new LambdaQueryWrapper<ScenDicEntity>().eq(ScenDicEntity::getDicId, dicId));

        return dto;

    }

}
