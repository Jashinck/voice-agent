package org.skylark.application.dto.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for SDP offer
 * SDP offer 的请求 DTO
 * 
 * @author Skylark Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SdpOfferRequest {
    
    @JsonProperty("sdpOffer")
    private String sdpOffer;
}
