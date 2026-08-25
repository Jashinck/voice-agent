package org.skylark.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * WebRTC Configuration Properties
 * WebRTC 配置属性
 *
 * <p>Type-safe configuration properties for WebRTC, including strategy selection,
 * Kurento, LiveKit, and STUN/TURN server settings.</p>
 *
 * <p>Supports five pluggable strategies: websocket, kurento, livekit, agora, alirtc.
 * Use the {@code webrtc.strategy} property to select the active strategy.</p>
 *
 * @author Skylark Team
 * @version 1.1.0
 */
@Configuration
@ConfigurationProperties(prefix = "webrtc")
@Getter
@Setter
public class WebRTCProperties {

    /**
     * Active WebRTC channel strategy: websocket, kurento, livekit, agora, or alirtc
     * 活动的 WebRTC 通道策略：websocket、kurento、livekit、agora 或 alirtc
     */
    private String strategy = "websocket";

    private final Kurento kurento = new Kurento();
    private final LiveKit livekit = new LiveKit();
    private final Agora agora = new Agora();
    private final AliRtc alirtc = new AliRtc();
    private final Stun stun = new Stun();
    private final Turn turn = new Turn();

    /**
     * Kurento configuration
     * Kurento 配置
     */
    @Getter
    @Setter
    public static class Kurento {
        private String wsUri = "ws://localhost:8888/kurento";
    }

    /**
     * LiveKit configuration
     * LiveKit 配置
     */
    @Getter
    @Setter
    public static class LiveKit {
        private String url = "";
        private String apiKey = "";
        private String apiSecret = "";
    }

    /**
     * STUN server configuration
     * STUN 服务器配置
     */
    @Getter
    @Setter
    public static class Stun {
        private String server = "stun:stun.l.google.com:19302";
    }

    /**
     * TURN server configuration
     * TURN 服务器配置
     */
    @Getter
    @Setter
    public static class Turn {
        private boolean enabled = false;
        private String server = "";
        private String username = "";
        private String password = "";
        private String transport = "udp";

        /**
         * Gets the full TURN URL with credentials
         * 获取带凭证的完整 TURN URL
         *
         * @return TURN URL string in format: turn:server?transport=transport
         */
        public String getTurnUrl() {
            if (server == null || server.trim().isEmpty()) {
                return null;
            }

            String url = server;
            if (!url.startsWith("turn:")) {
                url = "turn:" + url;
            }

            if (transport != null && !transport.trim().isEmpty()) {
                url += "?transport=" + transport;
            }

            return url;
        }
    }

    /**
     * Agora (声网) configuration
     * 声网配置
     */
    @Getter
    @Setter
    public static class Agora {
        private String appId = "";
        private String appCertificate = "";
        private String region = "cn";
        private int sampleRate = 16000;
        private int channels = 1;
        private int tokenExpireSeconds = 3600;
    }

    /**
     * Alibaba Cloud RTC (阿里云音视频通信) configuration
     * [E2] in the full-duplex upgrade roadmap
     */
    @Getter
    @Setter
    public static class AliRtc {
        private String appId = "";
        private String appKey = "";
        /** HMAC-SHA256 signing secret — required for real authInfo generation */
        private String appSecret = "";
        private String region = "cn";
        private int sampleRate = 16000;
        private int channels = 1;
        private int tokenTtlSeconds = 3600;
    }
}
