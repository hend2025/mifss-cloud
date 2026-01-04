package com.aeye.mifss.base.service;

import com.aeye.mifss.base.bo.ScenDicBO;
import com.aeye.mifss.base.entity.ScenDicEntity;
import com.aeye.mifss.common.service.ILocalService;

/**
 * 场景字典 本地服务接口
 * 继承 ILocalService 获得本地 CRUD 能力（操作 Entity）
 */
public interface ScenDicService extends ILocalService<ScenDicBO, ScenDicEntity> {

}
