package com.aeye.mifss.intf.base.service;

import com.aeye.mifss.common.service.IRpcService;
import com.aeye.mifss.intf.base.dto.ScenDicDTO;

/**
 * 场景字典 RPC 服务接口
 * 给 RPC 消费者使用，只暴露 DTO
 */
public interface RpcScenDicService extends IRpcService<ScenDicDTO> {

}