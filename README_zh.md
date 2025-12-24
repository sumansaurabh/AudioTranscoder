音频转码器-孵化中
============


引擎使用 [Xuggler](http://www.xuggle.com/xuggler/) 库来转码音频文件。Xuggler 采用 [GPL 版本 3 许可证](https://www.gnu.org/copyleft/gpl.html)。这是必需的，因为预编译版本分发 X264 用于 H264 视频编码。

##### 使用方法
此引擎将音频（mp3、aac、wav 等）转换为

	频率：16 kHz
    深度：16 位
    类型：单声道
    小端字节序

通过 ContentItem 解析的音频流转换为上述格式，并作为流解析到 [SpeechToText Enhancement Engine](https://github.com/sumansaurabh/SpeechToTextEngine)。

##### 注意
此项目正在开发中，您可能会遇到很多问题。