package org.skylark.application.dto.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for WebRTC session creation
 * WebRTC 会话创建的响应 DTO
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebRTCSessionResponse {
    
    @JsonProperty("sessionId")
    private String sessionId;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("message")
    private String message;
}
