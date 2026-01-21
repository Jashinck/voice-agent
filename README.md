# voice-agent
Voice Agent : Based on VAD, ASR, LLM, TTS, RTC

## 🎉 纯Java生态支持 (Pure Java Ecosystem Support)

本项目现已支持**纯Java实现**的Voice Agent系统！所有Python服务（ASR、TTS、VAD）都已转译为Java REST服务。

This project now supports a **pure Java implementation** of the Voice Agent system! All Python services (ASR, TTS, VAD) have been transpiled to Java REST services.

### 架构模式 (Architecture Modes)

#### 模式1: 纯Java模式 (Pure Java Mode) ✨ 新功能
- **优势**: 统一技术栈，无Python依赖，易于部署和维护
- **实现**: 所有服务（ASR、TTS、VAD、LLM）均使用Java实现
- **部署**: 仅需启动Java服务，无需Python环境

#### 模式2: 混合模式 (Hybrid Mode)
- **优势**: 利用Python生态的成熟ML库
- **实现**: ASR/TTS/VAD使用Python微服务，LLM使用Java服务
- **部署**: 使用docker-compose启动所有服务

### 快速开始 (Quick Start)

#### 纯Java模式部署

```bash
# 1. 构建Java服务
cd java-service
mvn clean package -DskipTests

# 2. 配置服务端点（指向Java实现）
# 编辑 config/config.yaml
asr:
  service_url: http://localhost:8080/asr/recognize
vad:
  service_url: http://localhost:8080/vad
tts:
  service_url: http://localhost:8080/tts

# 3. 启动服务
java -jar target/bailing-java.jar config/config.yaml
```

#### 混合模式部署（原有方式）

```bash
# 使用docker-compose启动所有服务
docker-compose up -d
```

### Java服务详情

Java服务实现了与Python服务完全兼容的REST API接口：

- **ASR服务**: `/asr/recognize` - 语音识别
- **TTS服务**: `/tts` - 文本转语音
- **VAD服务**: `/vad` - 语音活动检测

详细API文档请参阅: [Java Services README](java-service/JAVA_SERVICES_README.md)

### 技术栈 (Tech Stack)

#### Java服务
- Spring Boot 3.2.0
- Spring Web (REST API)
- Spring WebFlux (异步HTTP客户端)
- Java 17

#### Python服务（可选）
- Flask (Web框架)
- FunASR (语音识别)
- Edge-TTS (语音合成)
- Silero VAD (语音活动检测)

### 下一步开发 (Next Steps)

Java服务目前提供了占位符实现，需要集成实际的ML库：

1. **ASR**: 集成Vosk、Google Cloud Speech或Azure Speech
2. **TTS**: 集成MaryTTS、Google Cloud TTS或Azure Speech
3. **VAD**: 集成WebRTC VAD或Silero VAD (ONNX Runtime)

详见: [开发指南](java-service/JAVA_SERVICES_README.md#开发指南-development-guide)

## 项目结构 (Project Structure)

```
voice-agent/
├── java-service/          # Java服务（支持纯Java模式）
│   ├── src/main/java/com/bailing/
│   │   ├── controller/    # REST控制器 (ASR, TTS, VAD)
│   │   ├── service/       # 服务实现
│   │   ├── asr/          # ASR适配器
│   │   ├── tts/          # TTS适配器
│   │   ├── vad/          # VAD适配器
│   │   └── ...
│   └── pom.xml
├── python-services/       # Python微服务（可选）
│   ├── asr-service/
│   ├── tts-service/
│   └── vad-service/
├── config/                # 配置文件
└── docker-compose.yml     # Docker编排
```
