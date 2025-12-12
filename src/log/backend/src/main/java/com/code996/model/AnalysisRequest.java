package com.code996.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Request model for Git analysis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequest {

    @NotBlank(message = "Git URL cannot be empty")
    @Pattern(regexp = "^(https?://|git@).*\\.git$|^(https?://).*$", 
             message = "Invalid Git URL format")
    private String gitUrl;

    /**
     * Branch name to analyze (default: main/master)
     */
    private String branch;

    /**
     * Maximum commits to analyze (0 = all)
     */
    private Integer maxCommits;

    /**
     * Author email filter (optional)
     */
    private String authorEmail;
}
