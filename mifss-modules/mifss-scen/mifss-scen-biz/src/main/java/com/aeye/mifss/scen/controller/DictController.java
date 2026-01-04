package com.aeye.mifss.scen.controller;

import com.aeye.mifss.intf.base.dto.ScenDicDTO;
import com.aeye.mifss.intf.base.service.RpcScenDicService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dict")
public class DictController {

    @DubboReference
    private RpcScenDicService rpcScenDicService;

    @GetMapping("/{dicId}")
    public ScenDicDTO getById(@PathVariable("dicId") String dicId) throws Exception {

        System.out.printf("getDicEntityById dicId=%s\n", dicId);

        ScenDicDTO dto = rpcScenDicService.getOneRpc(new LambdaQueryWrapper<ScenDicDTO>().eq(ScenDicDTO::getDicId, dicId));

        return dto;

    }

}
