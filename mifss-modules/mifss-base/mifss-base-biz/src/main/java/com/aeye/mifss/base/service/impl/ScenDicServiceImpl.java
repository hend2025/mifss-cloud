package com.aeye.mifss.base.service.impl;

import com.aeye.mifss.base.bo.ScenDicBO;
import com.aeye.mifss.base.entity.ScenDicEntity;
import com.aeye.mifss.base.service.ScenDicService;
import com.aeye.mifss.common.service.impl.LocalServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 场景字典 本地服务实现
 * 继承 LocalServiceImpl 自动注入 BO，获得本地 CRUD 能力
 */
@Service
public class ScenDicServiceImpl extends LocalServiceImpl<ScenDicBO, ScenDicEntity> implements ScenDicService {

}