package org.skylark.application.dto.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for SDP answer
 * SDP answer 的响应 DTO
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SdpAnswerResponse {
    
    @JsonProperty("sdpAnswer")
    private String sdpAnswer;
}
