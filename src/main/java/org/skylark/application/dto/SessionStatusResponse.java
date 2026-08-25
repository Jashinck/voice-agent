package org.skylark.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for WebRTC session status
 * WebRTC会话状态的响应DTO
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionStatusResponse {
    
    @JsonProperty("session_id")
    private String sessionId;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("active")
    private Boolean active;
    
    @JsonProperty("message")
    private String message;
}
