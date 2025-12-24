# Apache Stanbol 音频转码器 (孵化中)

## 项目简介

本项目是 Apache Stanbol 的音频转码增强引擎，使用 [Xuggler](http://www.xuggle.com/xuggler/) 库来转码音频文件。Xuggler 采用 [GPL 第3版许可证](https://www.gnu.org/copyleft/gpl.html)。由于预编译版本分发了用于 H264 视频编码的 X264，因此需要遵守此许可证。

## 功能特性

本引擎可以将各种格式的音频文件（mp3、aac、wav 等）转换为统一的标准格式，以便进行语音识别处理。

### 输出音频格式规格

转码后的音频将具有以下特性：

- **采样频率**: 16 kHz
- **位深度**: 16 bit
- **声道类型**: 单声道 (mono)
- **字节序**: 小端序 (little-endian)

## 工作原理

通过 ContentItem 解析的音频流将被转换为上述标准格式，然后作为数据流传递给 [语音转文本增强引擎](https://github.com/sumansaurabh/SpeechToTextEngine) 进行进一步处理。

## 项目结构

```
stanbol-reactor/
├── enhancement-engines/     # 增强引擎模块
│   └── XugglerTranscoder/  # Xuggler 音频转码器
├── ext/                     # 扩展模块
│   └── xuggle-xuggler-bundle/  # Xuggler 库捆绑包
├── pom.xml                  # Maven 项目配置文件
├── LICENSE                  # 许可证文件
└── README.md               # 英文说明文档
```

## 技术栈

- **构建工具**: Apache Maven
- **许可证**: Apache License 2.0 (项目主体) / GPL v3 (Xuggler 依赖)
- **音频处理库**: Xuggler
- **Java 版本**: 需要 Java 运行环境

## 构建项目

本项目使用 Maven 进行构建管理。

### 前置要求

- Java Development Kit (JDK)
- Apache Maven 3.x

### 构建命令

```bash
# 编译整个项目
mvn clean install

# 仅编译音频转码器模块
cd enhancement-engines/XugglerTranscoder
mvn clean install

# 跳过测试进行构建
mvn clean install -DskipTests
```

## 使用方法

1. 将本引擎集成到 Apache Stanbol 框架中
2. 配置音频输入源（支持 mp3、aac、wav 等格式）
3. 引擎将自动将音频转换为 16kHz、16bit、单声道格式
4. 转换后的音频流可直接用于语音识别处理

## 许可证信息

### 项目许可证

本项目采用 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt) 许可证。

### 依赖库许可证

- **Xuggler**: GPL Version 3 许可证
- 由于使用了 GPL 许可的组件，使用本项目时需要遵守 GPL v3 的相关条款

## 开发状态

⚠️ **重要提示**: 本项目目前处于开发阶段，可能会遇到各种问题。

### 已知限制

- 项目仍在积极开发中
- 部分功能可能不稳定
- 建议在生产环境使用前进行充分测试

## 贡献指南

本项目是 Apache Stanbol 的一部分，欢迎贡献代码。

### 源代码管理

- **版本控制**: Apache SVN
- **仓库地址**: http://svn.apache.org/repos/asf/stanbol/trunk/
- **开发者连接**: https://svn.apache.org/repos/asf/stanbol/trunk/

### 参与贡献

1. 遵循 Apache 软件基金会的贡献准则
2. 提交补丁或改进建议
3. 参与社区讨论

## 相关链接

- [Apache Stanbol 官网](http://stanbol.apache.org/)
- [Xuggler 官网](http://www.xuggle.com/xuggler/)
- [语音转文本引擎](https://github.com/sumansaurabh/SpeechToTextEngine)
- [Apache 软件基金会](https://www.apache.org/)

## 联系方式

如有问题或建议，请通过 Apache Stanbol 社区渠道联系：

- 项目主页: http://stanbol.apache.org/
- 邮件列表: 参见 Apache Stanbol 官网

## 版权信息

Copyright © 2010-2025 Apache Software Foundation

根据 Apache License 2.0 版本授权（"许可证"）；除非遵守许可证，否则您不得使用此文件。您可以在以下网址获取许可证副本：

http://www.apache.org/licenses/LICENSE-2.0

除非适用法律要求或书面同意，否则根据许可证分发的软件是按"原样"分发的，不附带任何明示或暗示的保证或条件。有关许可证下的特定语言管理权限和限制，请参阅许可证。

---

**注意**: 本文档为中文翻译版本，如有歧义，请以英文原版 README.md 为准。
