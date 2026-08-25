package org.skylark.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for starting a WebRTC session
 * 启动WebRTC会话的请求DTO
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionStartRequest {
    
    @JsonProperty("client_id")
    private String clientId;
    
    @JsonProperty("audio_config")
    private AudioConfig audioConfig;
    
    /**
     * Audio configuration for the session
     */
    @Data
    @NoArgsConstructor
    public static class AudioConfig {
        @JsonProperty("sample_rate")
        private Integer sampleRate = 16000;
        
        @JsonProperty("channels")
        private Integer channels = 1;
        
        @JsonProperty("bit_depth")
        private Integer bitDepth = 16;
    }
}
