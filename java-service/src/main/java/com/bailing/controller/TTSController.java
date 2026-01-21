package com.bailing.controller;

import com.bailing.service.TTSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * TTS (Text-to-Speech) REST Controller
 * 文本转语音REST控制器
 * 
 * <p>Provides HTTP endpoints for text-to-speech synthesis,
 * replicating the Python Flask-based TTS service in Java.</p>
 * 
 * <p>Endpoints:</p>
 * <ul>
 *   <li>GET /tts/health - Health check endpoint</li>
 *   <li>POST /tts - Text-to-speech synthesis endpoint</li>
 *   <li>GET /tts/voices - List available voices</li>
 * </ul>
 * 
 * @author Bailing Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/tts")
@CrossOrigin(origins = "*")
public class TTSController {
    
    private static final Logger logger = LoggerFactory.getLogger(TTSController.class);
    
    @Autowired
    private TTSService ttsService;
    
    /**
     * Health check endpoint.
     * 
     * @return Health status response
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("service", "tts");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Text-to-speech synthesis endpoint.
     * 
     * <p>Accepts JSON request with text and optional voice parameter,
     * returns synthesized audio file.</p>
     * 
     * @param request Request body containing text and optional voice
     * @return Audio file (WAV format)
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> synthesize(@RequestBody Map<String, String> request) {
        try {
            String text = request.get("text");
            if (text == null || text.trim().isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "缺少text字段");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            
            String voice = request.getOrDefault("voice", null);
            
            logger.info("TTS请求: {}... (voice: {})", 
                text.length() > 50 ? text.substring(0, 50) : text, voice);
            
            File audioFile = ttsService.synthesize(text, voice);
            
            logger.info("TTS生成完成: {}", audioFile.getAbsolutePath());
            
            Resource resource = new FileSystemResource(audioFile);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("audio/wav"));
            headers.setContentDispositionFormData("attachment", audioFile.getName());
            
            // Schedule file deletion after response is sent
            // Note: In production, consider implementing a cleanup scheduler
            // to periodically delete old temporary files
            final File fileToDelete = audioFile;
            new Thread(() -> {
                try {
                    Thread.sleep(5000); // Wait 5 seconds before deletion
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                        logger.debug("Cleaned up temporary TTS file: {}", fileToDelete.getAbsolutePath());
                    }
                } catch (Exception e) {
                    logger.warn("Failed to cleanup TTS file: {}", fileToDelete.getAbsolutePath(), e);
                }
            }).start();
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            logger.error("TTS生成错误: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * List available voices endpoint.
     * 
     * @return List of available voices
     */
    @GetMapping("/voices")
    public ResponseEntity<Map<String, Object>> listVoices() {
        try {
            Map<String, Object> response = ttsService.listVoices();
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("获取语音列表错误: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
