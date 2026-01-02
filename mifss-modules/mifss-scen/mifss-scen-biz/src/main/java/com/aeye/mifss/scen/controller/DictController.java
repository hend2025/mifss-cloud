package com.aeye.mifss.scen.controller;

import com.aeye.mifss.intf.base.dto.ScenDicADTO;
import com.aeye.mifss.intf.base.service.ScenDicAService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dict")
public class DictController {

    @DubboReference
    private ScenDicAService scenDicAService;

    @GetMapping("/{dicId}")
    public ScenDicADTO getById(@PathVariable("dicId") String dicId) {
        return scenDicAService.getById(dicId);
    }

    @GetMapping("/valid")
    public List<ScenDicADTO> listAllValid() {
        return scenDicAService.listAllValid();
    }

}
