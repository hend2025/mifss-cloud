package com.aeye.mifss.base.service.impl;

import com.aeye.mifss.base.bo.ScenDicBO;
import com.aeye.mifss.base.entity.ScenDicEntity;
import com.aeye.mifss.common.service.impl.RpcServiceImpl;
import com.aeye.mifss.intf.base.dto.ScenDicDTO;
import com.aeye.mifss.intf.base.service.RpcScenDicService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 场景字典 本地服务实现
 * 继承 LocalServiceImpl 自动注入 BO，获得本地 CRUD 能力
 */
@DubboService
public class RpcScenDicServiceImpl extends RpcServiceImpl<ScenDicBO, ScenDicEntity, ScenDicDTO>
        implements RpcScenDicService {

}