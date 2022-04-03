# Object Format

<a href="https://github.com/d7z-team/object-format" target="_blank"><img alt="Jenkins" src="https://github.com/d7z-team/object-format/actions/workflows/task-push.yml/badge.svg?branch=main&color=green&style=flat-square"/></a>
<a href="LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/d7z-team/object-format"></a>
<a href="https://jitpack.io/#d7z-team/object-format" target="_blank"> <img alt="JitPack" src="https://img.shields.io/jitpack/v/github/d7z-team/object-format"></a>


> 一套简单通用的数据类型转字符串的方案

## 特性

- 开箱即用，使用简单
- 支持与各种现有的框架组合

## 快速开始

请查看各个模块的 `Test` 用例。

如何引入项目请参照 [Jitpack](https://jitpack.io/#d7z-team/object-format).

### 模块介绍

- [core](./format-core): 核心组件，包含基础数据类型和 `Date` 的实现，未知对象使用 Java 序列化和Base64转换成字符串 .
- [extra-gson](./format-extra-gson): 对象转换扩展模块，未知对象使用 Gson 转换成 json 字符串.
- [extra-jackson](./format-extra-jackson): 对象转换扩展模块，未知对象使用 jackson 转换成 json 字符串.
- [all](./format-all): 兼容模块，使用此模块可快速导入 `core` 和 `extra-gson`.

此项目已适配 Java 模块化 (JSR 376) .

## 更新日志

### 0.6.0

- 新增 spring-boot 支持

### 0.5.0

- 新增 jackson 支持
- 精简 API，与旧版本可能不兼容

### 0.4.0

- 修复 `Java Module` 加载问题
- 迁移 API

### 0.3.0

- 合并 `format-extra-time` 模块到`core`下
- 升级 `gson` 版本至 `2.9.0`
- 重命名模块`format-extra-json` 为 `format-extra-gson`

### 0.2.0

- 新增 `String`、`StringBuilder`、`StringBuffer` 支持
- 新增模块 `format-all` , 可直接导入此模块

### 0.1.1

- 新增 `Date` 对象支持

### 0.1.0

- 迁移后第一个版本

## LICENSE

此项目使用 MIT License ，更多详情请查看 [License文件](./LICENSE)
