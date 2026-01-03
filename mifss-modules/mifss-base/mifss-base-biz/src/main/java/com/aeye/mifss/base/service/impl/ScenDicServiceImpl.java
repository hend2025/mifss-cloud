package com.aeye.mifss.base.service.impl;

import com.aeye.mifss.base.bo.ScenDicBO;
import com.aeye.mifss.base.entity.ScenDicEntity;
import com.aeye.mifss.base.service.ScenDicService;
import com.aeye.mifss.common.service.impl.AbstractRpcServiceImpl;
import com.aeye.mifss.intf.base.dto.ScenDicDTO;
import com.aeye.mifss.intf.base.service.RpcScenDicService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(interfaceClass = RpcScenDicService.class)
public class ScenDicServiceImpl extends AbstractRpcServiceImpl<ScenDicEntity, ScenDicDTO, ScenDicBO>
                implements ScenDicService, RpcScenDicService {

}