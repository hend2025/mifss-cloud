# 医保基金综合监管系统 (MIFSS)

Medical Insurance Fund Supervision System - 医保基金综合监管系统

## 系统简介

MIFSS将"事前"的生物特征身份核验、"事中"的智能场景监控（住院/门诊/购药）以及"事后"的大数据反欺诈风控深度融合，构建了"三位一体"的基金安全防控闭环。

## 技术栈

- **JDK**: 1.8
- **框架**: Spring Boot 2.0.9
- **缓存**: Redis
- **消息队列**: RocketMQ/RabbitMQ
- **文件存储**: MinIO
- **ORM**: MyBatis Plus
- **数据库**: MySQL/PostgreSQL/达梦(信创)
- **云平台**: 适配开源云、阿里云、华为云、腾讯云

## 项目结构

```
mifss-cloud/
├── mifss-common/           # 公共通用模块 - 核心底座
│   ├── mifss-common-core       # 核心工具类、Result封装、全局异常
│   ├── mifss-common-redis      # Redis缓存封装
│   ├── mifss-common-security   # 鉴权上下文
│   ├── mifss-common-datascope  # 数据权限
│   ├── mifss-common-file       # 文件存储
│   ├── mifss-common-mq         # 消息队列
│   ├── mifss-common-log        # 审计日志
│   └── mifss-common-swagger    # 接口文档
│
├── mifss-api/              # 内部微服务接口层
│   ├── mifss-api-base          # 基础数据的 FeignClient + DTO
│   ├── mifss-api-bio           # 生物识别的 FeignClient + DTO
│   ├── mifss-api-monitor       # 智能监控的 FeignClient + DTO
│   ├── mifss-api-miaf          # 反欺诈的 FeignClient + DTO
│   ├── mifss-api-chrdise       # 慢病监管的 FeignClient + DTO
│   └── mifss-api-audit         # 医保智审的 FeignClient + DTO
│
├── mifss-modules/          # 核心业务模块组
│   ├── mifss-module-base       # 基础支撑：两定机构、医护、字典、监管方案
│   ├── mifss-module-bio        # 生物识别：人脸/指静脉特征提取与比对
│   ├── mifss-module-monitor    # 智能监控：住院/门诊/购药/视频流分析
│   ├── mifss-module-miaf       # 反欺诈：大数据风控模型、异常行为分析
│   ├── mifss-module-chrdise    # 慢病监管：特定病种全流程管理
│   ├── mifss-module-audit      # 医保智审：规则引擎、审核逻辑
│   └── mifss-module-openapi    # 对外门户：统一网关、第三方对接
│
└── mifss-plugins/          # 插件/外部能力集成
    ├── mifss-plugin-etl        # ETL集成
    └── mifss-plugin-bi         # BI集成

```

## 核心特性

### 多云适配架构
每个业务模块都支持多云部署：
- **core**: 核心业务逻辑，与云平台无关
- **generic**: 开源云适配层
- **ali**: 阿里云适配层  
- **huawei**: 华为云适配层
- **tencent**: 腾讯云适配层

## 快速开始

### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+ / PostgreSQL 10+
- Redis 5.0+

### 构建项目
```bash
mvn clean install
```

### 启动微服务版
```bash
# 启动基础服务
cd mifss-start/mifss-start-base
mvn spring-boot:run

# 启动其他服务...
```

## 业务模块说明

- **base**: 基础支撑模块，管理两定机构、医护人员、字典数据等
- **bio**: 生物识别模块，提供人脸识别、指静脉识别等身份核验能力
- **monitor**: 智能监控模块，实时监控住院、门诊、购药等医疗行为
- **miaf**: 反欺诈模块，基于大数据分析的风控模型和异常行为检测
- **chrdise**: 慢病监管模块，针对特定病种的全流程管理
- **audit**: 医保智审模块，规则引擎驱动的智能审核
- **openapi**: 对外门户模块，统一网关和第三方系统对接

## 许可证

Copyright © 2026 MIFSS Team. All rights reserved.