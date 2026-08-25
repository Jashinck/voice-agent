package org.skylark.application.dto.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for Agora WebRTC session connection
 * 声网 WebRTC 会话连接的响应 DTO
 * 
 * <p>Contains the Agora RTC Token, Channel Name, and App ID needed
 * for client-side connection via Agora Web SDK.</p>
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgoraConnectionResponse {

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("channelName")
    private String channelName;

    @JsonProperty("token")
    private String token;

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
