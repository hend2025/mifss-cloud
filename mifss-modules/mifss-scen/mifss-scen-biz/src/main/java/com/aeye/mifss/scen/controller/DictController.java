package com.aeye.mifss.scen.controller;

import com.aeye.mifss.intf.base.dto.ScenDicDTO;
import com.aeye.mifss.intf.base.service.RpcScenDicService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dict")
public class DictController {

    @DubboReference
    private RpcScenDicService scenDicAService;

    @GetMapping("/{dicId}")
    public ScenDicDTO getById(@PathVariable("dicId") String dicId) throws Exception {

        ScenDicDTO dto = null;

        dto = scenDicAService.getByIdRpc(dicId);

        return dto;
    }

}
