# 🎙️ 百聆 (Bailing) - 智能语音助手

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/)
[![Python](https://img.shields.io/badge/Python-3.10-blue.svg)](https://www.python.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-brightgreen.svg)](https://www.docker.com/)

基于 VAD、ASR、LLM、TTS 和 WebSocket 的智能语音助手系统，支持实时语音对话。

## 📖 目录

- [项目介绍](#-项目介绍)
- [特性列表](#-特性列表)
- [架构图](#-架构图)
- [快速开始](#-快速开始)
- [环境要求](#-环境要求)
- [安装步骤](#-安装步骤)
- [配置说明](#-配置说明)
- [使用文档](#-使用文档)
- [API文档](#-api文档)
- [开发指南](#-开发指南)
- [故障排除](#-故障排除)
- [贡献指南](#-贡献指南)
- [许可证](#-许可证)

## 🌟 项目介绍

百聆（Bailing）是一个完整的智能语音助手解决方案，采用微服务架构设计。系统集成了：

- **VAD (Voice Activity Detection)**: 实时语音活动检测，准确识别说话起止
- **ASR (Automatic Speech Recognition)**: 基于 FunASR 的语音识别，支持中英文
- **LLM (Large Language Model)**: 支持 OpenAI 和 Ollama，提供智能对话能力
- **TTS (Text-to-Speech)**: 基于 Edge-TTS 的高质量语音合成
- **WebSocket**: 实时双向通信，支持音频流传输

## ✨ 特性列表

### 核心功能
- 🎤 **实时语音识别**: 基于 SenseVoice 模型，支持中英文混合识别
- 🔊 **语音合成**: 使用 Edge-TTS，支持多种音色
- 🤖 **智能对话**: 集成 DeepSeek/OpenAI，提供自然语言理解
- 📡 **实时通信**: WebSocket 双向音频流传输
- 🎯 **语音活动检测**: Silero VAD 实时检测说话状态

### 技术特点
- 🐳 **Docker 容器化**: 一键部署所有服务
- 🔧 **微服务架构**: 服务解耦，易于扩展
- 🌐 **跨平台支持**: Linux、macOS、Windows
- 📊 **实时音量可视化**: Web 界面直观展示
- 🔒 **安全可靠**: 环境变量管理敏感信息

### 用户体验
- 💻 **美观的 Web 界面**: 渐变设计，响应式布局
- 📝 **双模式输入**: 支持语音和文字输入
- 💬 **对话历史**: 自动保存和展示聊天记录
- 🎨 **视觉反馈**: 实时状态指示和动画效果

## 🏗️ 架构图

```
┌─────────────────────────────────────────────────────────────────┐
│                         用户界面层                               │
│  ┌──────────────────┐           ┌──────────────────┐           │
│  │  Web客户端       │           │  移动端(未来)    │           │
│  │  (HTML/CSS/JS)   │           │                  │           │
│  └────────┬─────────┘           └──────────────────┘           │
└───────────┼──────────────────────────────────────────────────────┘
            │ WebSocket (ws://localhost:8080/ws/audio)
            │
┌───────────▼──────────────────────────────────────────────────────┐
│                      Java 核心服务层 (:8080)                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  WebSocket Handler  │  Dialogue Manager  │  Config      │   │
│  └──────────────────────────────────────────────────────────┘   │
└───────────┬──────────────────┬──────────────┬────────────────────┘
            │                  │              │
    ┌───────▼───────┐  ┌───────▼──────┐  ┌──▼─────────┐
    │  ASR Service  │  │  VAD Service │  │ TTS Service│
    │   (:8001)     │  │   (:8002)    │  │  (:8003)   │
    │               │  │              │  │            │
    │  FunASR       │  │  Silero VAD  │  │  Edge-TTS  │
    │  SenseVoice   │  │              │  │            │
    └───────────────┘  └──────────────┘  └────────────┘
            │                                    │
            └────────────┬───────────────────────┘
                         │
                ┌────────▼─────────┐
                │   LLM Service    │
                │  DeepSeek/OpenAI │
                │  Ollama(本地)    │
                └──────────────────┘

数据流:
1. 用户语音 → WebSocket → Java Service
2. Java Service → VAD → 检测语音活动
3. Java Service → ASR → 识别文字
4. 文字 → LLM → 生成回复
5. 回复 → TTS → 生成语音
6. 语音 → WebSocket → 用户
```

## 🚀 快速开始

### 一键启动（推荐）

```bash
# 1. 克隆项目
git clone https://github.com/Jashinck/voice-agent.git
cd voice-agent

# 2. 配置环境变量
cp .env.example .env
# 编辑 .env 文件，填入你的 DeepSeek API Key

# 3. 启动所有服务
./start.sh

# 4. 打开 Web 界面
# 在浏览器中打开 web/index.html
```

### 手动启动

```bash
# 启动 Docker 服务
docker-compose up --build -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

## 💻 环境要求

### 必需软件
- **Docker**: >= 20.10
- **Docker Compose**: >= 2.0
- **浏览器**: Chrome/Firefox/Safari (支持 WebSocket)

### 可选软件（开发）
- **Java**: JDK 17+
- **Maven**: 3.8+
- **Python**: 3.10+
- **Node.js**: 16+ (如需构建前端)

### 硬件要求
- **CPU**: 4核+ (推荐)
- **内存**: 8GB+ (推荐 16GB)
- **存储**: 20GB+ 可用空间
- **网络**: 稳定的互联网连接（用于下载模型）

## 📦 安装步骤

### 方式一：Docker 部署（推荐）

#### 1. 准备环境

```bash
# 安装 Docker
curl -fsSL https://get.docker.com | sh

# 安装 Docker Compose
sudo apt-get update
sudo apt-get install docker-compose-plugin
```

#### 2. 配置项目

```bash
# 克隆项目
git clone https://github.com/Jashinck/voice-agent.git
cd voice-agent

# 创建配置文件
cp .env.example .env

# 编辑配置（重要！）
nano .env
```

在 `.env` 文件中配置：

```bash
DEEPSEEK_API_KEY=your_deepseek_api_key_here
MODEL_DIR=iic/SenseVoiceSmall
DEVICE=cpu
TTS_VOICE=zh-CN-XiaoxiaoNeural
```

#### 3. 启动服务

```bash
# 使用启动脚本
./start.sh

# 或手动启动
docker-compose up --build -d
```

#### 4. 验证服务

```bash
# 检查服务状态
docker-compose ps

# 测试健康检查
curl http://localhost:8001/health  # ASR
curl http://localhost:8002/health  # VAD
curl http://localhost:8003/health  # TTS
```

### 方式二：本地开发部署

#### 1. 启动 Python 服务

```bash
# ASR 服务
cd python-services/asr-service
pip install -r requirements.txt
python app.py

# VAD 服务
cd python-services/vad-service
pip install -r requirements.txt
python app.py

# TTS 服务
cd python-services/tts-service
pip install -r requirements.txt
python app.py
```

#### 2. 启动 Java 服务

```bash
cd java-service
mvn clean package
java -jar target/bailing-java.jar config/config.yaml
```

## ⚙️ 配置说明

### 主配置文件 (config/config.yaml)

```yaml
# 运行模式: local(本地) | cloud(云端)
mode: local

# 唤醒词（可选，留空表示不使用）
wake_word: ""

# 是否允许打断（对话中可以打断助手说话）
interrupt: true

# ASR 配置
asr:
  class_name: HttpASRAdapter
  service_url: http://localhost:8001/recognize
  output_file: tmp/asr

# VAD 配置
vad:
  class_name: HttpVADAdapter
  service_url: http://localhost:8002/vad
  sampling_rate: 16000
  threshold: 0.5
  min_silence_duration_ms: 500

# TTS 配置
tts:
  class_name: HttpTTSAdapter
  service_url: http://localhost:8003/tts
  output_file: tmp/tts
  voice: zh-CN-XiaoxiaoNeural  # 可选其他音色

# LLM 配置
llm:
  class_name: OpenAILLM
  model_name: deepseek-chat
  api_key: ${DEEPSEEK_API_KEY}
  url: https://api.deepseek.com/v1

# 对话历史
dialogue:
  history_path: tmp/dialogue_history.json

# 助手记忆
memory:
  path: tmp/memory.json
  name: 百聆
  role: 智能语音助手
  personality: 友好、专业、乐于助人
  capabilities: 我可以进行语音对话，回答问题，帮助您完成各种任务。
```

### 环境变量 (.env)

```bash
# API 密钥
DEEPSEEK_API_KEY=sk-xxx

# ASR 模型配置
MODEL_DIR=iic/SenseVoiceSmall
DEVICE=cpu  # 或 cuda

# TTS 音色
TTS_VOICE=zh-CN-XiaoxiaoNeural
```

### TTS 音色选项

常用中文音色：
- `zh-CN-XiaoxiaoNeural` - 女声（默认，温柔）
- `zh-CN-YunxiNeural` - 男声（成熟稳重）
- `zh-CN-YunyangNeural` - 男声（新闻播音）
- `zh-CN-XiaoyiNeural` - 女声（甜美可爱）
- `zh-CN-YunjianNeural` - 男声（运动活力）

## 📚 使用文档

### Web 界面使用

#### 1. 连接服务器

1. 打开 `web/index.html`
2. 点击 **"连接服务器"** 按钮
3. 等待状态显示 "已连接"

#### 2. 语音对话

```
1. 点击 "🎤 开始录音"
2. 对着麦克风说话
3. 点击 "⏹️ 停止录音"
4. 等待助手回复
```

#### 3. 文字对话

```
1. 在输入框输入文字
2. 按 Enter 或点击 "发送"
3. 查看助手回复
```

### API 使用示例

#### ASR 识别

```bash
curl -X POST http://localhost:8001/recognize \
  -F "file=@audio.wav"
```

响应：
```json
{
  "text": "你好，今天天气怎么样",
  "language": "zh"
}
```

#### VAD 检测

```bash
curl -X POST http://localhost:8002/vad \
  -H "Content-Type: application/json" \
  -d '{
    "audio_data": "base64_encoded_audio",
    "session_id": "user123"
  }'
```

响应：
```json
{
  "status": "start",  // 或 "end" 或 null
  "timestamp": 1234567890
}
```

#### TTS 合成

```bash
curl -X POST http://localhost:8003/tts \
  -H "Content-Type: application/json" \
  -d '{
    "text": "你好，我是百聆",
    "voice": "zh-CN-XiaoxiaoNeural"
  }' \
  --output output.wav
```

## 🔌 API 文档

### WebSocket API

#### 连接端点
```
ws://localhost:8080/ws/audio
```

#### 消息格式

**客户端 → 服务器**

1. 音频数据（Binary）:
```
发送 PCM Int16 原始音频数据
采样率: 16000 Hz
通道数: 1 (Mono)
位深度: 16-bit
```

2. 文本消息（JSON）:
```json
{
  "type": "text",
  "content": "你好"
}
```

**服务器 → 客户端**

1. 文本回复:
```json
{
  "type": "text",
  "content": "助手的回复内容"
}
```

2. 音频回复:
```json
{
  "type": "audio",
  "content": "base64_encoded_wav"
}
```

3. 状态消息:
```json
{
  "type": "status",
  "content": "正在处理..."
}
```

4. 错误消息:
```json
{
  "type": "error",
  "content": "错误描述"
}
```

### REST API

#### ASR Service (Port 8001)

| 端点 | 方法 | 描述 |
|------|------|------|
| `/health` | GET | 健康检查 |
| `/recognize` | POST | 语音识别 |

#### VAD Service (Port 8002)

| 端点 | 方法 | 描述 |
|------|------|------|
| `/health` | GET | 健康检查 |
| `/vad` | POST | VAD 检测 |
| `/reset` | POST | 重置 VAD 状态 |

#### TTS Service (Port 8003)

| 端点 | 方法 | 描述 |
|------|------|------|
| `/health` | GET | 健康检查 |
| `/tts` | POST | 语音合成 |

## 🛠️ 开发指南

### 项目结构

```
voice-agent/
├── config/                 # 配置文件
│   └── config.yaml
├── docker-compose.yml      # Docker 编排配置
├── java-service/           # Java 核心服务
│   ├── src/
│   │   └── main/java/com/bailing/
│   │       ├── BailingApplication.java
│   │       ├── asr/        # ASR 适配器
│   │       ├── vad/        # VAD 适配器
│   │       ├── tts/        # TTS 适配器
│   │       ├── llm/        # LLM 实现
│   │       ├── core/       # 核心逻辑
│   │       └── utils/      # 工具类
│   ├── pom.xml
│   └── Dockerfile
├── python-services/        # Python 微服务
│   ├── asr-service/
│   │   ├── app.py
│   │   ├── requirements.txt
│   │   └── Dockerfile
│   ├── vad-service/
│   │   ├── app.py
│   │   ├── requirements.txt
│   │   └── Dockerfile
│   └── tts-service/
│       ├── app.py
│       ├── requirements.txt
│       └── Dockerfile
├── web/                    # Web 前端
│   └── index.html
├── start.sh               # 启动脚本
├── stop.sh                # 停止脚本
├── .env.example           # 环境变量模板
└── README.md
```

### 添加新的 LLM 提供商

1. 创建新类实现 `LLM` 接口：

```java
public class CustomLLM implements LLM {
    @Override
    public String chat(String userMessage, List<Message> history) {
        // 实现对话逻辑
        return response;
    }
}
```

2. 在 `config.yaml` 中配置：

```yaml
llm:
  class_name: CustomLLM
  # 其他配置...
```

### 添加新的 TTS 引擎

1. 创建新的 TTS 适配器：

```java
public class CustomTTSAdapter implements TTS {
    @Override
    public void synthesize(String text, String outputPath) {
        // 实现语音合成
    }
}
```

2. 更新配置文件引用新的适配器。

### 本地开发调试

#### Java 服务

```bash
cd java-service

# 编译
mvn clean compile

# 运行
mvn spring-boot:run -Dspring-boot.run.arguments=config/config.yaml

# 打包
mvn clean package
```

#### Python 服务

```bash
# 创建虚拟环境
python -m venv venv
source venv/bin/activate  # Linux/Mac
# 或
venv\Scripts\activate  # Windows

# 安装依赖
pip install -r requirements.txt

# 运行
python app.py
```

### 代码风格

- **Java**: 遵循 Google Java Style Guide
- **Python**: 遵循 PEP 8
- **JavaScript**: 使用 ES6+ 标准

## 🔧 故障排除

### 常见问题

#### 1. Docker 容器无法启动

**问题**: `docker-compose up` 失败

**解决方案**:
```bash
# 检查 Docker 服务状态
sudo systemctl status docker

# 清理旧容器和镜像
docker-compose down -v
docker system prune -a

# 重新构建
docker-compose up --build
```

#### 2. ASR 模型下载失败

**问题**: ASR 服务启动时卡在模型下载

**解决方案**:
```bash
# 手动下载模型
mkdir -p models
cd models
# 使用 modelscope 命令行工具下载
modelscope download --model iic/SenseVoiceSmall

# 或配置代理
export HTTP_PROXY=http://your-proxy:port
export HTTPS_PROXY=http://your-proxy:port
```

#### 3. WebSocket 连接失败

**问题**: Web 界面无法连接到 Java 服务

**解决方案**:
```bash
# 检查 Java 服务是否运行
docker-compose logs java-service

# 检查端口是否开放
curl http://localhost:8080/health

# 检查防火墙设置
sudo ufw allow 8080/tcp
```

#### 4. 麦克风无法访问

**问题**: 浏览器提示无法访问麦克风

**解决方案**:
- 确保使用 HTTPS 或 localhost
- 检查浏览器麦克风权限设置
- 尝试使用 Chrome/Firefox 浏览器
- 检查系统麦克风权限

#### 5. API Key 无效

**问题**: LLM 调用失败，提示 API Key 错误

**解决方案**:
```bash
# 检查 .env 文件
cat .env

# 确保格式正确，无空格
DEEPSEEK_API_KEY=sk-xxxxxxxxx

# 重启服务使配置生效
docker-compose restart
```

### 日志查看

```bash
# 查看所有服务日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f asr-service
docker-compose logs -f vad-service
docker-compose logs -f tts-service
docker-compose logs -f java-service

# 查看 Java 服务详细日志
tail -f logs/bailing.log
```

### 性能优化

#### 1. 使用 GPU 加速

修改 `.env`:
```bash
DEVICE=cuda
```

修改 `docker-compose.yml`:
```yaml
asr-service:
  # ...
  deploy:
    resources:
      reservations:
        devices:
          - driver: nvidia
            count: 1
            capabilities: [gpu]
```

#### 2. 调整资源限制

```yaml
services:
  asr-service:
    # ...
    deploy:
      resources:
        limits:
          cpus: '4'
          memory: 8G
```

#### 3. 使用本地 LLM

配置 Ollama:
```yaml
llm:
  class_name: OllamaLLM
  model_name: qwen2.5:7b
  url: http://localhost:11434
```

## 🤝 贡献指南

我们欢迎所有形式的贡献！

### 如何贡献

1. **Fork 项目**
```bash
git clone https://github.com/your-username/voice-agent.git
cd voice-agent
```

2. **创建分支**
```bash
git checkout -b feature/your-feature-name
```

3. **提交更改**
```bash
git add .
git commit -m "Add: your feature description"
```

4. **推送到 GitHub**
```bash
git push origin feature/your-feature-name
```

5. **创建 Pull Request**
- 在 GitHub 上打开 Pull Request
- 描述你的更改
- 等待审核

### 代码规范

- 遵循现有代码风格
- 添加适当的注释
- 更新相关文档
- 确保所有测试通过

### 提交信息规范

```
<type>: <subject>

<body>

<footer>
```

类型（type）:
- `feat`: 新功能
- `fix`: 修复 bug
- `docs`: 文档更新
- `style`: 代码格式调整
- `refactor`: 代码重构
- `test`: 测试相关
- `chore`: 构建/工具相关

示例:
```
feat: Add support for multi-language TTS

- Add language detection
- Add more TTS voice options
- Update configuration schema

Closes #123
```

## 📄 许可证

本项目采用 Apache License 2.0 许可证。详见 [LICENSE](LICENSE) 文件。

```
Copyright 2024 Bailing Voice Agent Contributors

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

## 🙏 致谢

感谢以下开源项目：

- [FunASR](https://github.com/alibaba-damo-academy/FunASR) - 语音识别
- [Silero VAD](https://github.com/snakers4/silero-vad) - 语音活动检测
- [Edge-TTS](https://github.com/rany2/edge-tts) - 语音合成
- [Spring Boot](https://spring.io/projects/spring-boot) - Java 框架
- [Flask](https://flask.palletsprojects.com/) - Python Web 框架

## 📞 联系方式

- 项目主页: https://github.com/Jashinck/voice-agent
- 问题反馈: https://github.com/Jashinck/voice-agent/issues
- 邮箱: (请在 GitHub Issues 中联系)

## 🗺️ 路线图

- [ ] 支持更多 LLM 提供商（Claude, Gemini）
- [ ] 添加移动端 App
- [ ] 支持多轮对话上下文管理
- [ ] 添加语音情绪识别
- [ ] 支持多语言切换
- [ ] 添加插件系统
- [ ] 支持私有化部署一键安装包
- [ ] 添加管理后台界面

---

<div align="center">

**[⬆ 回到顶部](#-百聆-bailing---智能语音助手)**

Made with ❤️ by Bailing Team

如果这个项目对你有帮助，请给我们一个 ⭐️

</div>
