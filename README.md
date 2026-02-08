

<img src="https://jixiaoyong.github.io/download/data/wanandroid/WanAndroidIconRound.png" width="120" hegiht="120" align=center />



# WanAndroid  ![WanAndroid Action Badge](https://github.com/jixiaoyong/WanAndroid/workflows/Android%20CI/badge.svg) [![Build Status](https://www.travis-ci.org/jixiaoyong/WanAndroid.svg?branch=develop)](https://www.travis-ci.org/jixiaoyong/WanAndroid)

> **📢 项目声明 (Project Note)**
>
> 本项目是我入行初期编写的一个练手项目。目前核心功能已完善且运行稳定。如无意外，今后将**不再进行维护**。
>
> 衷心感谢 [WanAndroid](https://www.wanandroid.com/) 提供的 API 支持，它见证了我的技术起步。希望此项目能为同样在路上的开发者提供参考。

一个用kotlin语言编写的www.wanandroid.com的客户端，支持Android 5.0及以上版本。



 本分支为*日常开发*版本，各个功能稳定后会合并到[`master`](https://github.com/jixiaoyong/WanAndroid/tree/master)分支。



 



 ## 🛠 开发环境 (Build Environment)



 



 *   **JDK**: **Java 11** (原因：Gradle 7.0.2 不支持 Java 17+)。



 *   **Versions**: **Gradle 7.0.2** / **AGP 4.2.2** / **Kotlin 1.4.31**。



 *   **SDK**: **Compile/Target 30** (原因：兼容性最稳，无需适配 Android 12+)。



 *   **Apple Silicon**: 已内置 `sqlite-jdbc` 补丁，支持 M1/M2/M3 Mac 直接编译。



 *   **注意**: 请在 Android Studio 设置中将 **Gradle JDK** 强制指定为 **Java 11**。



 



 # 工作计划

距离本APP第一个版本发布已经过去快两年了，期间本人学习到了很多有用的知识技巧，也发现了当初编写APP时的诸多问题，于是打算在闲暇时间对其进行重构。

目前主要计划如下：

- [x] 1. 去除旧版本多余的依赖

- [ ] 2. 使用MVVM架构、Jetpack

- [x] 3. 重新设计UI



**我的邮箱: aml4aWFveW9uZzE5OTVAZ21haWwuY29tCg== ,有任何问题都可以email我或者在[issues](https://github.com/jixiaoyong/WanAndroid/issues) 提出。**



# License

 ```
  
   Copyright 2018 - 2021 jixiaoyong

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

