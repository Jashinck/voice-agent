package com.bailing.controller;

import com.bailing.service.ASRService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * ASR (Automatic Speech Recognition) REST Controller
 * 自动语音识别REST控制器
 * 
 * <p>Provides HTTP endpoints for speech recognition functionality,
 * replicating the Python Flask-based ASR service in Java.</p>
 * 
 * <p>Endpoints:</p>
 * <ul>
 *   <li>GET /asr/health - Health check endpoint</li>
 *   <li>POST /asr/recognize - Speech recognition endpoint</li>
 * </ul>
 * 
 * @author Bailing Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/asr")
@CrossOrigin(origins = "*")
public class ASRController {
    
    private static final Logger logger = LoggerFactory.getLogger(ASRController.class);
    
    @Autowired
    private ASRService asrService;
    
    /**
     * Health check endpoint.
     * 
     * @return Health status response
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("service", "asr");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Speech recognition endpoint.
     * 
     * <p>Accepts audio file upload and returns recognized text.</p>
     * 
     * @param file Audio file (WAV format recommended)
     * @return Recognition result with text and language
     */
    @PostMapping("/recognize")
    public ResponseEntity<Map<String, String>> recognize(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "文件名为空");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            
            logger.info("处理音频文件: {}", file.getOriginalFilename());
            
            byte[] audioData = file.getBytes();
            Map<String, String> result = asrService.recognize(audioData);
            
            logger.info("识别结果: {}", result.get("text"));
            
            return ResponseEntity.ok(result);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            logger.error("ASR识别错误: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
