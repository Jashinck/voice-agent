# voice-agent
Voice Agent : Based on VAD, ASR, LLM, TTS, RTC

## 🎉 纯Java生态 (Pure Java Ecosystem)

本项目现已完全采用**纯Java实现**的Voice Agent系统！所有服务（ASR、TTS、VAD）都使用Java实现，无需Python依赖。

This project now uses a **pure Java implementation** of the Voice Agent system! All services (ASR, TTS, VAD) are implemented in Java, with no Python dependencies.

### 架构特点 (Architecture Features)

- **统一技术栈**: 全部使用Java实现，无需Python环境
- **简化部署**: 单一Java服务，易于部署和维护
- **直接调用**: 适配器直接调用服务，无需HTTP开销
- **Spring集成**: 使用Spring Boot进行依赖注入和管理

### 快速开始 (Quick Start)

```bash
# 1. 构建Java服务
cd java-service
mvn clean package -DskipTests

# 2. 启动服务（使用纯Java配置）
java -jar target/bailing-java.jar config/config-java-only.yaml
```

### Docker部署

```bash
# 使用docker-compose启动服务
docker-compose up -d
```

### 技术栈 (Tech Stack)

- Spring Boot 3.2.0
- Spring Web (REST API)
- Spring WebFlux (异步HTTP客户端)
- Java 17

### 下一步开发 (Next Steps)

Java服务目前提供了占位符实现，需要集成实际的ML库：

1. **ASR**: 集成Vosk、Google Cloud Speech或Azure Speech
2. **TTS**: 集成MaryTTS、Google Cloud TTS或Azure Speech
3. **VAD**: 集成WebRTC VAD或Silero VAD (ONNX Runtime)

详见: [开发指南](java-service/JAVA_SERVICES_README.md#开发指南-development-guide)

## 项目结构 (Project Structure)

```
voice-agent/
├── java-service/          # Java服务
│   ├── src/main/java/com/bailing/
│   │   ├── service/       # 服务实现 (ASR, TTS, VAD)
│   │   ├── asr/          # ASR适配器
│   │   ├── tts/          # TTS适配器
│   │   ├── vad/          # VAD适配器
│   │   ├── config/       # Spring配置
│   │   └── ...
│   └── pom.xml
├── config/                # 配置文件
│   ├── config-java-only.yaml  # 纯Java配置
│   └── config.yaml            # 备用配置
└── docker-compose.yml     # Docker编排
```
