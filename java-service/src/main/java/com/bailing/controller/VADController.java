package com.bailing.controller;

import com.bailing.service.VADService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * VAD (Voice Activity Detection) REST Controller
 * 语音活动检测REST控制器
 * 
 * <p>Provides HTTP endpoints for voice activity detection,
 * replicating the Python Flask-based VAD service in Java.</p>
 * 
 * <p>Endpoints:</p>
 * <ul>
 *   <li>GET /vad/health - Health check endpoint</li>
 *   <li>POST /vad - Voice activity detection endpoint</li>
 *   <li>POST /vad/reset - Reset VAD session state</li>
 *   <li>POST /vad/clear - Clear all VAD sessions</li>
 * </ul>
 * 
 * @author Bailing Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/vad")
@CrossOrigin(origins = "*")
public class VADController {
    
    private static final Logger logger = LoggerFactory.getLogger(VADController.class);
    
    @Autowired
    private VADService vadService;
    
    /**
     * Health check endpoint.
     * 
     * @return Health status response
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("service", "vad");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Voice activity detection endpoint.
     * 
     * <p>Accepts JSON request with base64-encoded audio data,
     * returns detection status (start/end/null).</p>
     * 
     * @param request Request body containing audio_data and optional session_id
     * @return Detection result with status and optional timestamp
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> detect(@RequestBody Map<String, String> request) {
        try {
            String audioData = request.get("audio_data");
            if (audioData == null || audioData.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "缺少audio_data字段");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            
            String sessionId = request.getOrDefault("session_id", "default");
            
            Map<String, Object> result = vadService.detect(audioData, sessionId);
            
            return ResponseEntity.ok(result);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            logger.error("VAD检测错误: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Reset VAD session state endpoint.
     * 
     * @param request Request body containing optional session_id
     * @return Reset status response
     */
    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> reset(@RequestBody(required = false) Map<String, String> request) {
        try {
            String sessionId = "default";
            if (request != null && request.containsKey("session_id")) {
                sessionId = request.get("session_id");
            }
            
            vadService.reset(sessionId);
            
            logger.info("VAD状态已重置: {}", sessionId);
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "reset");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("VAD重置错误: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Clear all VAD sessions endpoint.
     * 
     * @return Clear status response
     */
    @PostMapping("/clear")
    public ResponseEntity<Map<String, String>> clear() {
        try {
            vadService.clearAll();
            
            logger.info("所有VAD会话已清除");
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "cleared");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("VAD清除错误: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
