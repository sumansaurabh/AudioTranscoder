# 音频转码器 (Audio Transcoder-incubating)

## 项目简介

本引擎使用 [Xuggler](http://www.xuggle.com/xuggler/) 库对音频文件进行转码。Xuggler 采用 [GPL 第3版许可协议](https://www.gnu.org/copyleft/gpl.html)。由于预编译版本包含用于 H264 视频编码的 X264，因此需要遵守该许可协议。

## 使用方法

该引擎将音频文件（mp3、aac、wav 等）转换为以下格式：

- **采样率**: 16 kHz
- **位深度**: 16 bit
- **声道**: 单声道 (mono)
- **字节序**: 小端序 (little-endian)

通过 ContentItem 解析的音频流会被转换为上述格式，并作为流传递给 [语音转文本增强引擎](https://github.com/sumansaurabh/SpeechToTextEngine)。

## 注意事项

本项目正在开发中，您可能会遇到很多问题。

## 许可证

本项目采用 Apache License 2.0 许可协议。详见 LICENSE 文件。

## 项目结构

```
.
├── enhancement-engines/    # 增强引擎模块
│   └── XugglerTranscoder/ # Xuggler 转码引擎
├── ext/                   # 扩展模块
│   └── xuggle-xuggler-bundle/ # Xuggle OSGi 捆绑包
├── pom.xml               # Maven 主配置文件
└── README.md             # 项目说明文档
```

## 构建项目

本项目使用 Maven 构建工具：

```bash
mvn clean install
```

## 技术栈

- **Apache Stanbol**: 语义增强框架
- **Xuggler**: 音频/视频处理库
- **Maven**: 项目构建管理
- **OSGI**: 模块化运行时环境

## 贡献

欢迎提交问题报告和拉取请求。

## 相关链接

- [Apache Stanbol](http://stanbol.apache.org/)
- [Xuggler 官网](http://www.xuggle.com/xuggler/)
