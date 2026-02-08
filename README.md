<div align="center">
    <img src="images/logo.png" width="120" height="120" />
</div>

# WanAndroid  ![Android CI](https://github.com/jixiaoyong/WanAndroid/actions/workflows/android.yml/badge.svg)

> **📢 项目声明 (Project Note)**
>
> 本项目是我入行初期编写的一个练手项目。目前核心功能已完善且运行稳定。如无意外，今后将**不再进行维护**。
>
> 衷心感谢 [WanAndroid](https://www.wanandroid.com/) 提供的 API 支持，它见证了我的技术起步。希望此项目能为同样在路上的开发者提供参考。

一个用 Kotlin 编写的 WanAndroid 客户端，支持 Android 5.0 及以上版本。

## 🛠 开发环境 (Build Environment)

* **JDK**: **Java 11** (原因：Gradle 7.0.2 不支持 Java 17+)。
* **Versions**: **Gradle 7.0.2** / **AGP 4.2.2** / **Kotlin 1.4.31**。
* **SDK**: **Compile/Target 30** (原因：兼容性最稳，无需适配 Android 12+)。
* **Apple Silicon**: 已内置 `sqlite-jdbc` 补丁，支持 M1/M2/M3 Mac 直接编译。
* **注意**: 请在 Android Studio 中将 **Gradle JDK** 指定为 **Java 11**。

# 工作计划

目前核心功能开发已完成，计划如下：

- [x] 1. 去除旧版本多余的依赖
- [x] 2. 使用 MVVM 架构、Jetpack
- [x] 3. 重新设计 UI

**联系方式：**

~~如有任何问题，欢迎在 [Issues](https://github.com/jixiaoyong/WanAndroid/issues) 中提出。~~

# License

```
Copyright 2018 - 2026 jixiaoyong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```