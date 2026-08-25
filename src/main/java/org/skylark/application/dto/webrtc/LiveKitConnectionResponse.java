package org.skylark.application.dto.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for LiveKit WebRTC session connection
 * LiveKit WebRTC 会话连接的响应 DTO
 * 
 * <p>Contains the LiveKit access token and server URL needed
 * for client-side connection via LiveKit JS SDK.</p>
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveKitConnectionResponse {
    
    @JsonProperty("sessionId")
    private String sessionId;
    
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("message")
    private String message;
}
