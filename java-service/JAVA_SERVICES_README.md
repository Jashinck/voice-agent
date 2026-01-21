# Java Service REST API Implementation

## 概述 (Overview)

本模块提供了Python服务的Java实现，以支持纯Java生态的Voice Agent系统。

This module provides Java implementations of the Python services to support a pure Java ecosystem for the Voice Agent system.

## 服务端点 (Service Endpoints)

### ASR Service (自动语音识别)

**基础URL:** `http://localhost:8080/asr`

#### 健康检查
```
GET /asr/health
```
响应: `{"status": "healthy", "service": "asr"}`

#### 语音识别
```
POST /asr/recognize
Content-Type: multipart/form-data

参数:
- file: 音频文件 (WAV格式)

响应:
{
  "text": "识别的文本",
  "language": "zh"
}
```

### TTS Service (文本转语音)

**基础URL:** `http://localhost:8080/tts`

#### 健康检查
```
GET /tts/health
```
响应: `{"status": "healthy", "service": "tts"}`

#### 语音合成
```
POST /tts
Content-Type: application/json

请求体:
{
  "text": "要合成的文本",
  "voice": "zh-CN-XiaoxiaoNeural" (可选)
}

响应: WAV音频文件
```

#### 语音列表
```
GET /tts/voices
```
响应:
```json
{
  "voices": [
    {
      "name": "zh-CN-XiaoxiaoNeural",
      "gender": "Female",
      "locale": "zh-CN"
    }
  ]
}
```

### VAD Service (语音活动检测)

**基础URL:** `http://localhost:8080/vad`

#### 健康检查
```
GET /vad/health
```
响应: `{"status": "healthy", "service": "vad"}`

#### 语音活动检测
```
POST /vad
Content-Type: application/json

请求体:
{
  "audio_data": "base64编码的音频数据",
  "session_id": "会话ID" (可选, 默认为"default")
}

响应:
{
  "status": "start" | "end" | null,
  "timestamp": 1234567890 (可选)
}
```

#### 重置会话状态
```
POST /vad/reset
Content-Type: application/json

请求体:
{
  "session_id": "会话ID" (可选)
}

响应: {"status": "reset"}
```

#### 清除所有会话
```
POST /vad/clear

响应: {"status": "cleared"}
```

## 实现说明 (Implementation Notes)

### 当前状态 (Current Status)

本实现提供了与Python服务完全兼容的REST API接口。目前的实现包含占位符代码，需要集成实际的Java机器学习库。

This implementation provides REST API interfaces fully compatible with the Python services. The current implementation contains placeholder code that needs to be integrated with actual Java machine learning libraries.

### 建议的集成方案 (Recommended Integration)

#### ASR服务
- **Vosk**: 离线语音识别，支持多种语言
- **Google Cloud Speech-to-Text**: 高质量云服务
- **Azure Speech Services**: 微软语音服务
- **Whisper (ONNX Runtime)**: OpenAI的Whisper模型

#### TTS服务
- **MaryTTS**: Java原生TTS引擎
- **FreeTTS**: 轻量级Java语音合成
- **Google Cloud Text-to-Speech**: 高质量云服务
- **Azure Speech Services**: 微软语音服务
- **Amazon Polly**: AWS语音服务

#### VAD服务
- **WebRTC VAD (via JNI)**: 通过JNI调用WebRTC VAD
- **Silero VAD (via ONNX Runtime)**: 使用ONNX Runtime运行Silero VAD模型
- **Custom Energy-based VAD**: 自定义能量检测算法

### 配置 (Configuration)

在`application.properties`或`application.yml`中配置服务参数:

```yaml
asr:
  model:
    dir: "iic/SenseVoiceSmall"
  device: "cpu"
  temp:
    dir: "temp/asr"

tts:
  voice: "zh-CN-XiaoxiaoNeural"
  temp:
    dir: "temp/tts"

vad:
  sampling:
    rate: 16000
  threshold: 0.5
  min:
    silence:
      duration:
        ms: 500
```

## 部署 (Deployment)

### 方式1: 仅Java服务 (Pure Java)

修改`config/config.yaml`，将服务URL指向Java实现:

```yaml
asr:
  service_url: http://localhost:8080/asr/recognize

vad:
  service_url: http://localhost:8080/vad

tts:
  service_url: http://localhost:8080/tts
```

### 方式2: 混合模式 (Hybrid Mode)

保留现有的Python服务配置，继续使用独立的Python微服务。

## 开发指南 (Development Guide)

### 添加实际ASR实现

编辑`com.bailing.service.ASRService`中的`performRecognition`方法:

```java
private String performRecognition(File audioFile) {
    // 集成实际的ASR库
    // 例如: Vosk, Cloud API等
    return recognizedText;
}
```

### 添加实际TTS实现

编辑`com.bailing.service.TTSService`中的`performSynthesis`方法:

```java
private void performSynthesis(String text, String voice, File outputFile) throws Exception {
    // 集成实际的TTS库
    // 例如: MaryTTS, Cloud API等
}
```

### 添加实际VAD实现

编辑`com.bailing.service.VADService`中的`performDetection`方法:

```java
private String performDetection(byte[] audioBytes, VADState state) {
    // 集成实际的VAD库
    // 例如: WebRTC VAD, Silero VAD等
    return status;
}
```

## 测试 (Testing)

启动服务后，可以使用curl测试各个端点:

```bash
# 测试ASR健康检查
curl http://localhost:8080/asr/health

# 测试TTS健康检查
curl http://localhost:8080/tts/health

# 测试VAD健康检查
curl http://localhost:8080/vad/health

# 测试TTS合成
curl -X POST http://localhost:8080/tts \
  -H "Content-Type: application/json" \
  -d '{"text":"你好世界"}' \
  --output test.wav
```

## 优势 (Advantages)

1. **纯Java生态**: 无需Python依赖，简化部署
2. **统一技术栈**: 所有服务使用相同的语言和框架
3. **更好的类型安全**: Java的静态类型系统提供更好的安全性
4. **易于维护**: 统一的代码库和构建流程
5. **云原生**: 更容易与Java生态的云服务集成

## 下一步 (Next Steps)

1. 选择并集成实际的ASR库
2. 选择并集成实际的TTS库
3. 选择并集成实际的VAD库
4. 添加单元测试和集成测试
5. 优化性能和资源使用
6. 添加监控和日志记录
