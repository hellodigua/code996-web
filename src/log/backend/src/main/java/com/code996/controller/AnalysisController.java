package com.code996.controller;

import com.code996.model.AnalysisRequest;
import com.code996.model.AnalysisResult;
import com.code996.model.ApiResponse;
import com.code996.service.GitAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Analysis API Controller
 */
@Slf4j
@RestController
@RequestMapping("/analyze")
@CrossOrigin(origins = "*")
public class AnalysisController {

    @Autowired
    private GitAnalysisService gitAnalysisService;

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.success("Code996 Backend is running!");
    }

    /**
     * Analyze Git repository
     * 
     * POST /api/analyze
     * {
     *   "gitUrl": "https://github.com/user/repo.git",
     *   "branch": "main",
     *   "maxCommits": 5000,
     *   "authorEmail": "user@example.com"
     * }
     */
    @PostMapping
    public ApiResponse<AnalysisResult> analyze(@RequestBody AnalysisRequest request) {
        try {
            log.info("Received analysis request: {}", request.getGitUrl());

            // Validate request
            if (request.getGitUrl() == null || request.getGitUrl().isEmpty()) {
                return ApiResponse.error(400, "Git URL is required");
            }

            // Perform analysis
            AnalysisResult result = gitAnalysisService.analyze(request);

            log.info("Analysis completed successfully for: {}", request.getGitUrl());
            return ApiResponse.success("Analysis completed", result);

        } catch (Exception e) {
            log.error("Analysis failed for: {}", request.getGitUrl(), e);
            return ApiResponse.error(500, "Analysis failed: " + e.getMessage());
        }
    }

    /**
     * Get service information
     */
    @GetMapping("/info")
    public ApiResponse<ServiceInfo> info() {
        ServiceInfo info = new ServiceInfo(
                "Code996 Backend",
                "1.0.0",
                "Git repository online analysis service",
                "POST /api/analyze"
        );
        return ApiResponse.success(info);
    }

    // Inner class for service info
    @lombok.Data
    @lombok.AllArgsConstructor
    private static class ServiceInfo {
        private String name;
        private String version;
        private String description;
        private String endpoint;
    }
}
