package com.code996.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

/**
 * Git helper utilities
 */
@Slf4j
public class GitHelper {

    /**
     * Clone a Git repository
     */
    public static Git cloneRepository(String gitUrl, File directory, String branch) throws GitAPIException {
        log.info("Cloning repository: {} to {}", gitUrl, directory.getAbsolutePath());

        var cloneCommand = Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(directory)
                .setCloneAllBranches(false);

        if (branch != null && !branch.isEmpty()) {
            cloneCommand.setBranch(branch);
        }

        Git git = cloneCommand.call();
        log.info("Repository cloned successfully");
        return git;
    }

    /**
     * Delete directory recursively
     */
    public static void deleteDirectory(File directory) {
        try {
            if (directory.exists()) {
                FileUtils.deleteDirectory(directory);
                log.info("Directory deleted: {}", directory.getAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Failed to delete directory: {}", directory.getAbsolutePath(), e);
        }
    }

    /**
     * Check if URL is a valid Git URL
     */
    public static boolean isValidGitUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        return url.matches("^(https?://|git@).*(\\.git)?$");
    }

    /**
     * Normalize Git URL
     */
    public static String normalizeGitUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if (!url.endsWith(".git") && !url.contains("github.com") && !url.contains("gitlab.com")) {
            url += ".git";
        }
        return url;
    }
}
