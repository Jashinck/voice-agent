package org.skylark.application.dto.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for ICE candidate
 * ICE candidate 的请求 DTO
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IceCandidateRequest {
    
    @JsonProperty("candidate")
    private String candidate;
    
    @JsonProperty("sdpMid")
    private String sdpMid;
    
    @JsonProperty("sdpMLineIndex")
    private int sdpMLineIndex;
}
