package org.skylark.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for WebRTC session start
 * WebRTC会话启动的响应DTO
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionStartResponse {
    
    @JsonProperty("session_id")
    private String sessionId;
    
    @JsonProperty("websocket_url")
    private String websocketUrl;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("message")
    private String message;
}
