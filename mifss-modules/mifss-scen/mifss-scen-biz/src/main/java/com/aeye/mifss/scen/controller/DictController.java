package com.aeye.mifss.scen.controller;

import com.aeye.mifss.common.wrapper.RpcQueryWrapper;
import com.aeye.mifss.intf.base.dto.ScenDicDTO;
import com.aeye.mifss.intf.base.service.RpcScenDicService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dict")
public class DictController {

    @DubboReference
    private RpcScenDicService rpcScenDicService;

    @GetMapping("/{dicId}")
    public List<ScenDicDTO> getById(@PathVariable("dicId") String dicId) throws Exception {

        List<ScenDicDTO> list = rpcScenDicService.listRpc(
                new RpcQueryWrapper<ScenDicDTO>()
                        .select(ScenDicDTO::getDicId,ScenDicDTO::getDicName)
                        .eq(ScenDicDTO::getDicId, dicId)
                        .orderByAsc(ScenDicDTO::getDicName)
        );

        return list;

    }

}
