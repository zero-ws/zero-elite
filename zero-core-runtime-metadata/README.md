# Zero.Core.Runtime.Metadata

> 开发参考：<https://gitee.com/zero-ws/zero-rapid-fabric-runtime>

## 1. 基础说明

```bash
# 环境初始化
mvn -Pdev clean package install
# 编译/调试
mvn clean package install
# 查看引用
git submodule
```

## 2. 模块信息

### 2.1. 服务清单

| 接口              | 服务名   | 说明                               |
|-----------------|-------|----------------------------------|
| `ExceptionDesk` | 异常服务台 | 用于管理所有全局异常专用服务，其他异常会注册模块内执行存储管理。 |

### 2.2. 可用命令

| 命令             | 参数     | 说明            |
|----------------|--------|---------------|
| `failure info` | `all`  | 查看所有异常信息      |
|                | `size` | 查看当前环境中异常信息数量 |