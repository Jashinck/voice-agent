package org.skylark.application.dto.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for AliRTC WebRTC session connection
 * 阿里云 ARTC WebRTC 会话连接的响应 DTO
 *
 * <p>Contains the AliRTC App ID, Channel ID, User ID, and Auth Info (JSON string)
 * needed for client-side connection via the AliRTC Web SDK.</p>
 *
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliRTCConnectionResponse {

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("channelId")
    private String channelId;

    @JsonProperty("userId")
    private String userId;

    /** AuthInfo JSON string containing nonce, timestamp, and HMAC token */
    @JsonProperty("authInfo")
    private String authInfo;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
